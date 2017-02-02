/*******************************************************************************
 * Copyright (c) 2015-2017 Ericsson AB.
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
import org.eclipse.egerrit.internal.model.RevisionInfo;

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

	public CommitCompareItem createCompareItemFromCommit(RevisionInfo revision, FileInfo file, String fileName,
			int baseRevisionNumber, IProgressMonitor progressMonitor) {
		return new CommitCompareItem(gerrit, revision, file, fileName, baseRevisionNumber);
	}

	public CommitCompareItem createCompareItemFromBase(RevisionInfo revision, FileInfo file, String fileName,
			IProgressMonitor progressMonitor) {
		return new CommitCompareItem(gerrit, revision, file, fileName, 0);
	}
}
