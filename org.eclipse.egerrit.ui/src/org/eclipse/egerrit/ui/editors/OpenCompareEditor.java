
/*******************************************************************************
 * Copyright (c) 2015 Ericsson AB.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson - initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.ui.editors;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.eclipse.compare.CompareUI;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.egerrit.core.EGerritCorePlugin;
import org.eclipse.egerrit.core.Gerrit;
import org.eclipse.egerrit.core.GerritRepository;
import org.eclipse.egerrit.core.command.GetContentCommand;
import org.eclipse.egerrit.core.exception.EGerritException;
import org.eclipse.egerrit.core.rest.ChangeInfo;
import org.eclipse.egerrit.core.rest.FileInfo;
import org.eclipse.egerrit.ui.editors.model.CompareInput;
import org.eclipse.egerrit.ui.internal.utils.GerritToGitMapping;
import org.eclipse.egerrit.ui.internal.utils.UIUtils;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.URIish;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpenCompareEditor {
	final static Logger logger = LoggerFactory.getLogger(OpenCompareEditor.class);

	private final static String TITLE = "Gerrit Server ";

	private final GerritRepository gerritRepo;

	private final ChangeInfo changeInfo;

	public OpenCompareEditor(GerritRepository gerritRepo, ChangeInfo changeInfo) {
		this.gerritRepo = gerritRepo;
		this.changeInfo = changeInfo;
	}

	public void compareAgainstWorkspace(FileInfo fileInfo) {
		String resRight = getFilesContent(gerritRepo, changeInfo.getChange_id(),
				fileInfo.getContainingRevisionInfo().getId(), fileInfo.getold_path(), new NullProgressMonitor());
		CompareInput ci = new CompareInput();
		if (resRight != null) {
			ci.setRight(StringUtils.newStringUtf8(Base64.decodeBase64(resRight)));
		} else {
			logger.debug("The file has been deleted or its revision could not be retrieved."); //$NON-NLS-1$
			ci.setRight(""); //$NON-NLS-1$
		}

		File potentialFile = locateFileInLocalGitRepo(fileInfo);
		if (potentialFile == null) {
			logger.debug("The corresponding file could not be found in any git repository known by the workspace."); //$NON-NLS-1$
			ci.setLeft(""); //$NON-NLS-1$
		}

		if (potentialFile != null) {
			IFile workspaceFile = getFileFromWorkspace(potentialFile);
			if (workspaceFile == null) {
				logger.debug(
						"The compare editor could not be opened because the corresponding file is not in the workspace."); //$NON-NLS-1$
				ci.setLeft(""); //$NON-NLS-1$
			} else {
				ci.setLeft(getContent(workspaceFile));
			}
		}

		openCompareEditor(ci);
	}

	//This method is protected just so we can use it during the tests
	protected void openCompareEditor(CompareInput input) {
		CompareUI.openCompareEditor(input);
	}

	private String getContent(IFile workspaceFile) {
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
				InputStream is = workspaceFile.getContents(true)) {
			byte[] buffer = new byte[1024];
			int length = 0;
			while ((length = is.read(buffer)) != -1) {
				baos.write(buffer, 0, length);
			}
			return baos.toString(workspaceFile.getCharset(true));
		} catch (IOException | CoreException e) {
			return null;
		}
	}

	private IFile getFileFromWorkspace(File potentialFile) {
		return ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(
				org.eclipse.core.runtime.Path.fromOSString(potentialFile.getAbsolutePath()));

	}

	private File locateFileInLocalGitRepo(FileInfo fileInfo) {
		Repository repo;
		try {
			repo = new GerritToGitMapping(new URIish(gerritRepo.getURIBuilder(false).toString()),
					changeInfo.getProject()).find();
			if (repo == null) {
				return null;
			}
		} catch (IOException | URISyntaxException e) {
			return null;
		}
		File workTree = repo.getWorkTree();
		if (workTree == null) {
			return null;
		}
		File potentialFile = new File(workTree, fileInfo.getold_path());
		if (!potentialFile.exists()) {
			return null;
		}
		return potentialFile;
	}

	static String getFilesContent(GerritRepository gerritRepository, String change_id, String revision_id, String file,
			IProgressMonitor monitor) {
		try {
			monitor.beginTask("Executing query", IProgressMonitor.UNKNOWN);

			Gerrit gerrit = gerritRepository.instantiateGerrit();

			// Create query
			if (gerrit != null) {
				GetContentCommand command = gerrit.getContent(change_id, revision_id, file);

				String res = null;
				try {
					res = command.call();
					return res;
				} catch (EGerritException e) {
					EGerritCorePlugin.logError(e.getMessage());
				} catch (ClientProtocolException e) {
					UIUtils.displayInformation(null, TITLE, e.getLocalizedMessage() + "\n " + command.formatRequest()); //$NON-NLS-1$
				}
			}
		} catch (UnsupportedClassVersionError e) {
			return null;
		} finally {
			monitor.done();
		}

		return null;

	}
}
