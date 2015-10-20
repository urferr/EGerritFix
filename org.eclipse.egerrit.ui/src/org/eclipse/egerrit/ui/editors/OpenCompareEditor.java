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
import org.eclipse.egerrit.core.GerritClient;
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

	private final GerritClient gerrit;

	private final ChangeInfo changeInfo;

	public OpenCompareEditor(GerritClient gerrit, ChangeInfo changeInfo) {
		this.gerrit = gerrit;
		this.changeInfo = changeInfo;
	}

	/**
	 * Open the compare editor with the two provided FileInfo
	 *
	 * @param left
	 *            the file to show in the left pane
	 * @param right
	 *            the file to show in the right pane
	 */
	public void compareTwoRevisions(FileInfo left, FileInfo right, Runnable saveListener) {
		GerritCompareInput ci = new GerritCompareInput(changeInfo.getId(), left, right, gerrit, saveListener);
		openCompareEditor(ci);
	}

	/**
	 * Open the compare editor with a file from the given commitId, and a FileInfo
	 *
	 * @param projectId
	 *            the name of the gerrit project (this is necessary because we can't traverse our data model completely
	 *            - e.g. we can't go from a FileInfo all the way up to the changeInfo)
	 * @param right
	 *            the file to show in the right pane
	 */
	public void compareAgainstBase(String projectId, FileInfo right, Runnable saveListener) {
		GerritCompareInput ci = new GerritCompareInput(changeInfo.getId(), projectId, right, gerrit, saveListener);
		openCompareEditor(ci);
	}

	/**
	 * Open the compare editor against a file from the workspace, and a FileInfo
	 *
	 * @param right
	 *            the file to show in the right pane
	 */
	public void compareAgainstWorkspace(FileInfo right, Runnable saveListener) {
		File potentialFile = locateFileInLocalGitRepo(right);
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
		GerritCompareInput ci = new GerritCompareInput(workspaceFile, changeInfo.getId(), right, gerrit, saveListener);
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
