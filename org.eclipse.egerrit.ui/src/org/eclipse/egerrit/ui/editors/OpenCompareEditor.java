
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

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.eclipse.compare.CompareUI;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.egerrit.core.Gerrit;
import org.eclipse.egerrit.core.rest.ChangeInfo;
import org.eclipse.egerrit.core.rest.FileInfo;
import org.eclipse.egerrit.ui.editors.model.GerritCompareInput;
import org.eclipse.egerrit.ui.internal.utils.GerritToGitMapping;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.URIish;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpenCompareEditor {
	final static Logger logger = LoggerFactory.getLogger(OpenCompareEditor.class);

	private final Gerrit gerrit;

	private final ChangeInfo changeInfo;

	public OpenCompareEditor(Gerrit gerrit, ChangeInfo changeInfo) {
		this.gerrit = gerrit;
		this.changeInfo = changeInfo;
	}

	public void compareAgainstWorkspace(FileInfo fileInfo) {
		File potentialFile = locateFileInLocalGitRepo(fileInfo);
		IFile workspaceFile = null;
		if (potentialFile == null) {
			logger.debug("The corresponding file could not be found in any git repository known by the workspace."); //$NON-NLS-1$
		}

		if (potentialFile != null) {
			workspaceFile = getFileFromWorkspace(potentialFile);
			if (workspaceFile == null) {
				logger.debug(
						"The compare editor could not be opened because the corresponding file is not in the workspace."); //$NON-NLS-1$
			}
		}
		GerritCompareInput ci = new GerritCompareInput(workspaceFile, changeInfo.getChange_id(), fileInfo, gerrit);
		openCompareEditor(ci);

	}

	//This method is protected just so we can use it during the tests
	protected void openCompareEditor(GerritCompareInput input) {
		CompareUI.openCompareEditor(input);
	}

	private IFile getFileFromWorkspace(File potentialFile) {
		return ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(
				org.eclipse.core.runtime.Path.fromOSString(potentialFile.getAbsolutePath()));

	}

	private File locateFileInLocalGitRepo(FileInfo fileInfo) {
		Repository repo;
		try {
			repo = new GerritToGitMapping(new URIish(gerrit.getRepository().getURIBuilder(false).toString()),
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
}
