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
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.egerrit.internal.model.RevisionInfo;
import org.eclipse.egerrit.internal.ui.utils.UIUtils;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

/**
 * This class implements button to show/hide files with comments
 *
 * @since 1.0
 */
public class ReplyHandler extends AbstractHandler {

	private TreeViewer viewer;

	@SuppressWarnings("finally")
	@Override
	public Object execute(final ExecutionEvent aEvent) {

		IWorkbenchPart editor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		if (editor instanceof CompareEditor) {
			GerritMultipleInput input = (GerritMultipleInput) ((CompareEditor) editor).getEditorInput();
			viewer = input.getUpperSection().getDiffTreeViewer();
			try {
				input.saveChanges(new NullProgressMonitor());
			} catch (CoreException e) {
				return IStatus.ERROR;
			}
			RevisionInfo revisionInfo = input.getChangeInfo().getRevisions().get(input.getRightSide());
			UIUtils.replyToChange(viewer.getControl().getShell(), revisionInfo, null, input.gerritClient);
			input.fireInputChange();
		}
		return Status.OK_STATUS;

	}

	@Override
	public boolean isEnabled() {
		IWorkbenchPart editor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		if (editor instanceof CompareEditor) {
			GerritMultipleInput input = (GerritMultipleInput) ((CompareEditor) editor).getEditorInput();
			if (input.gerritClient.getRepository().getServerInfo().isAnonymous()) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

}
