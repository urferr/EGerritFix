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
import org.eclipse.egerrit.core.GerritClient;
import org.eclipse.egerrit.internal.model.FileInfo;

/**
 * Factory class to create compare items
 */
public class CompareItemFactory {

	private GerritClient gerrit;

	private PatchSetCompareItem newCompareItem = new PatchSetCompareItem();

	public CompareItemFactory(GerritClient gerrit) {
		this.gerrit = gerrit;
	}

	public PatchSetCompareItem createCompareItemFromRevision(String filename, String change_id, FileInfo fileInfo,
			IProgressMonitor monitor) {
		newCompareItem.setGerritConnection(gerrit);
		if (fileInfo == null) {
			throw new IllegalArgumentException();
		}
		newCompareItem.setFile(fileInfo);
		return newCompareItem;
	}

	/**
	 * Create a simple compare item
	 */
	public CommitCompareItem createCompareItemFromCommit(String projectId, String commitId, FileInfo fileInfo,
			IProgressMonitor monitor) {

		return new CommitCompareItem(gerrit, projectId, commitId, fileInfo);
	}
}
