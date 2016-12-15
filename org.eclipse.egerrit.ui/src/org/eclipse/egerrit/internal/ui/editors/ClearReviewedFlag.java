/*******************************************************************************
 * Copyright (c) 2016 Ericsson AB.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson - initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.internal.ui.editors;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.eclipse.egerrit.internal.core.GerritClient;
import org.eclipse.egerrit.internal.model.FileInfo;

/**
 * This class implements remove all check mark from the current review.
 *
 * @since 1.0
 */
public class ClearReviewedFlag {

	private GerritClient fGerritClient;

	private List<FileInfo> fListFilesInfo;

	/**
	 * Constructor for the class to remove all check marks for the reviewed files
	 *
	 * @param gerritClient
	 * @param listFilesInfo
	 */
	public ClearReviewedFlag(GerritClient gerritClient, List<FileInfo> listFilesInfo) {
		this.fGerritClient = gerritClient;
		this.fListFilesInfo = listFilesInfo;
	}

	/**
	 * Remove the indicator for the reviewed files from a review
	 */
	public void removeCheckMark() {
		Iterator<FileInfo> it = fListFilesInfo.listIterator();
		while (it.hasNext()) {
			clearFlag(it.next());
		}
	}

	private void clearFlag(FileInfo fileInfo) {
		CompletableFuture.runAsync(() -> {
			if (fileInfo.isReviewed()) {
				QueryHelpers.markAsNotReviewed(fGerritClient, fileInfo);
			}
		});
	}
}
