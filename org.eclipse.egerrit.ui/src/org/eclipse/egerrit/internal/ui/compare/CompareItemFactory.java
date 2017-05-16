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

import org.eclipse.egerrit.internal.core.GerritClient;
import org.eclipse.egerrit.internal.model.FileInfo;
import org.eclipse.egerrit.internal.model.RevisionInfo;

/**
 * Factory class to create compare items
 */
class CompareItemFactory {

	private static final String COMMIT_MSG = "/COMMIT_MSG"; //$NON-NLS-1$

	private GerritClient gerrit;

	private PatchSetCompareItem newCompareItem = new PatchSetCompareItem();

	CompareItemFactory(GerritClient gerrit) {
		this.gerrit = gerrit;
	}

	PatchSetCompareItem createCompareItemFromRevision(FileInfo fileInfo) {
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

	CommitCompareItem createCompareItemFromCommit(RevisionInfo revision, FileInfo file, String fileName,
			int baseRevisionNumber) {
		return new CommitCompareItem(gerrit, revision, file, fileName, baseRevisionNumber);
	}

	CommitCompareItem createCompareItemFromBase(RevisionInfo revision, FileInfo file, String fileName) {
		return new CommitCompareItem(gerrit, revision, file, fileName, 0);
	}
}
