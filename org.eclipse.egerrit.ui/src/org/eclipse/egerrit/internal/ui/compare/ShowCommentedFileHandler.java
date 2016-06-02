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

import org.eclipse.compare.internal.CompareEditor;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

/**
 * This class implements button to show/hide files with comments
 *
 * @since 1.0
 */
public class ShowCommentedFileHandler extends AbstractHandler {

	private static boolean filterOn = false;

	private static CommentedFilesFilter filter = null;

	private TreeViewer viewer;

	@SuppressWarnings("finally")
	@Override
	public Object execute(final ExecutionEvent aEvent) {

		IWorkbenchPart editor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		if (editor instanceof CompareEditor) {
			if (filter == null) {
				filter = new CommentedFilesFilter();
			}
			GerritMultipleInput input = (GerritMultipleInput) ((CompareEditor) editor).getEditorInput();

			viewer = input.getUpperSection().getDiffTreeViewer();
			if (filterOn) {
				viewer.removeFilter(filter);
			} else {
				viewer.addFilter(filter);
			}
			filterOn = !filterOn;
		}
		return Status.OK_STATUS;
	}

}
