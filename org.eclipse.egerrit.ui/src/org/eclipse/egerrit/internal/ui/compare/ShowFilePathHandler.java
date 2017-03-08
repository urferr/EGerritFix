/*******************************************************************************
 * Copyright (c) 2016 Ericsson AB and others.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Guy Perron - Created for Review Gerrit Dashboard project
 *
 ******************************************************************************/
package org.eclipse.egerrit.internal.ui.compare;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.Status;
import org.eclipse.egerrit.internal.ui.table.provider.FileInfoCompareCellLabelProvider;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * This class implements button for the toggle file path layout
 *
 * @since 1.0
 */
public class ShowFilePathHandler extends AbstractHandler {
	@SuppressWarnings("finally")
	@Override
	public Object execute(final ExecutionEvent aEvent) {

		try {
			IEditorInput activeEditorInput = HandlerUtil.getActiveEditorInput(aEvent);
			if (activeEditorInput instanceof GerritMultipleInput) {
				TreeViewer treeViewer = ((GerritMultipleInput) activeEditorInput).getUpperSection().getDiffTreeViewer();
				//Get the label provider for the FilePath column
				IBaseLabelProvider labelProvider = treeViewer
						.getLabelProvider(CompareUpperSectionColumn.FILE_PATH.ordinal());
				if (labelProvider instanceof FileInfoCompareCellLabelProvider) {
					FileInfoCompareCellLabelProvider infoProvider = (FileInfoCompareCellLabelProvider) labelProvider;
					infoProvider.setFileNameFirst(!infoProvider.getFileOrder());
					treeViewer.refresh();
				}
			}
		} finally {
			return Status.OK_STATUS;
		}
	}
}
