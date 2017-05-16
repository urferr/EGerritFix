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

package org.eclipse.egerrit.internal.ui.table.provider;

import java.util.Iterator;

import org.eclipse.egerrit.internal.core.GerritClient;
import org.eclipse.egerrit.internal.model.FileInfo;
import org.eclipse.egerrit.internal.model.impl.StringToFileInfoImpl;
import org.eclipse.egerrit.internal.ui.compare.GerritDiffNode;
import org.eclipse.egerrit.internal.ui.editors.QueryHelpers;
import org.eclipse.egerrit.internal.ui.utils.Messages;
import org.eclipse.egerrit.internal.ui.utils.UIUtils;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;

/**
 * This class implements the selection of the file in the Files table and open the workspace file.
 *
 * @since 1.0
 */
public class HandleFileSelection {

	private GerritClient fGerritClient;

	private ColumnViewer fViewer;

	public HandleFileSelection(GerritClient gerritClient, ColumnViewer viewer) {
		this.fGerritClient = gerritClient;
		this.fViewer = viewer;
	}

	/**
	 * Open the selected file in the Files table and open the workspace file
	 *
	 * @return boolean
	 */
	public boolean showFileSelection() {
		boolean ok = true;
		ISelection selection = fViewer.getSelection();
		if (selection instanceof IStructuredSelection) {

			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			Iterator<?> itr = structuredSelection.iterator();
			String failedFiles = ""; //$NON-NLS-1$
			while (itr.hasNext()) {
				failedFiles = findFailedFiles(itr);
			}
			if (!failedFiles.isEmpty()) {
				UIUtils.displayInformation(Messages.UIFilesTable_3, Messages.UIFilesTable_2 + '\n' + failedFiles);
				ok = false;
			}
		}
		return ok;

	}

	/**
	 * @param itr
	 * @param failedFiles
	 * @return
	 */
	private String findFailedFiles(Iterator<?> itr) {
		String failedFiles = ""; //$NON-NLS-1$
		Object element = itr.next();
		if (element == null) {
			return ""; //$NON-NLS-1$
		}
		FileInfo fileInfo;
		if (fViewer instanceof TableViewer) {
			fileInfo = ((StringToFileInfoImpl) element).getValue();
		} else {
			fileInfo = ((GerritDiffNode) element).getFileInfo();
		}
		String status = fileInfo.getStatus();
		if ((status.compareTo("D") != 0) //$NON-NLS-1$
				&& (!UIUtils.openSingleFile(fileInfo, fGerritClient, fileInfo.getRevision(), 0))) {
			failedFiles += fileInfo.getPath() + '\n';
		}
		QueryHelpers.markAsReviewed(fGerritClient, fileInfo);
		return failedFiles;
	}
}
