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
import org.eclipse.egerrit.internal.ui.utils.Messages;
import org.eclipse.egerrit.internal.ui.utils.UIUtils;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

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
	 */
	public void showFileSelection() {
		ISelection selection = fViewer.getSelection();
		if (selection instanceof IStructuredSelection) {

			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			Iterator<?> itr = structuredSelection.iterator();
			String failedFiles = ""; //$NON-NLS-1$
			while (itr.hasNext()) {
				Object element = itr.next();
				if (element == null) {
					return;
				}
				FileInfo fileInfo;
				if (fViewer instanceof TableViewer) {
					fileInfo = ((StringToFileInfoImpl) element).getValue();
				} else {
					fileInfo = ((GerritDiffNode) element).getFileInfo();
				}
				String status = fileInfo.getStatus();
				if (status.compareTo("D") != 0) { //$NON-NLS-1$
					if (fViewer instanceof TableViewer) {
						if (UIUtils.openSingleFile(((StringToFileInfoImpl) element).getKey(), fGerritClient,
								fileInfo.getRevision(), 0) == false) {
							failedFiles = failedFiles + fileInfo.getPath() + "\n"; //$NON-NLS-1$
						}
					} else {
						if (UIUtils.openSingleFile(((StringToFileInfoImpl) fileInfo.eContainer()).getKey(),
								fGerritClient, fileInfo.getRevision(), 0) == false) {
							failedFiles = failedFiles + fileInfo.getPath() + "\n"; //$NON-NLS-1$
						}
					}
				}
			}
			if (!failedFiles.isEmpty()) {
				Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
				UIUtils.displayInformation(shell, Messages.UIFilesTable_2, failedFiles);
			}
		}

	}
}
