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

package org.eclipse.egerrit.internal.ui.compare;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.egerrit.internal.core.GerritClient;
import org.eclipse.egerrit.internal.model.FileInfo;

/**
 * Factory class to create compare items
 */
public class CompareItemFactory {

	private static final String COMMIT_MSG = "/COMMIT_MSG"; //$NON-NLS-1$

	private GerritClient gerrit;

	private PatchSetCompareItem newCompareItem = new PatchSetCompareItem();

	public CompareItemFactory(GerritClient gerrit) {
		this.gerrit = gerrit;
	}

	public PatchSetCompareItem createCompareItemFromRevision(String filename, String change_id, FileInfo fileInfo,
			IProgressMonitor monitor) {
		if (fileInfo == null || fileInfo.getRevision() == null) {
			throw new IllegalArgumentException();
		}
		if (COMMIT_MSG.equals(fileInfo.getPath())) {
			newCompareItem = new CommitMsgCompareItem();
		}
		newCompareItem.setGerritConnection(gerrit);
		newCompareItem.setFile(fileInfo);
		return newCompareItem;
	}

	/**
	 * Create a simple compare item
	 */
	public CommitCompareItem createCompareItemFromCommit(String projectId, String commitId, FileInfo fileInfo,
			IProgressMonitor monitor) {
		return new CommitCompareItem(gerrit, projectId, commitId, fileInfo, null, null);
	}

	public CommitCompareItem createCompareItemFromCommit(String projectId, String commitId, FileInfo fileInfo,
			String fileName, IProgressMonitor monitor) {
		return new CommitCompareItem(gerrit, projectId, commitId, fileInfo, fileName, null);
	}

	public CommitCompareItem createCompareItemFromCommit(String projectId, String commitId, FileInfo fileInfo,
			String fileName, String showAsRevision, IProgressMonitor monitor) {
		return new CommitCompareItem(gerrit, projectId, commitId, fileInfo, fileName, showAsRevision);
	}
}
