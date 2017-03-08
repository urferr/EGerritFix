/*******************************************************************************
 * Copyright (c) 2016 Ericsson AB and others.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Ericsson - Created for Review Gerrit Dashboard project
 *
 ******************************************************************************/
package org.eclipse.egerrit.internal.ui.compare;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.Status;
import org.eclipse.egerrit.internal.core.EGerritCorePlugin;
import org.eclipse.egerrit.internal.ui.editors.ChangeDetailEditor;
import org.eclipse.egerrit.internal.ui.editors.model.ChangeDetailEditorInput;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * This class implements a handler to open a review editor from the
 *
 * @since 1.0
 */
public class ShowReviewEditorHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent aEvent) {
		IEditorInput activeEditorInput = HandlerUtil.getActiveEditorInput(aEvent);
		if (activeEditorInput instanceof GerritMultipleInput) {
			GerritMultipleInput gmInput = (GerritMultipleInput) activeEditorInput;
			IEditorInput input = new ChangeDetailEditorInput(gmInput.gerritClient, gmInput.getChangeInfo());

			IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
			IWorkbenchPage page = null;
			if (window != null) {
				page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			}

			if (page != null) {
				try {
					IEditorPart reusedEditor = page.findEditor(input);
					page.openEditor(input, ChangeDetailEditor.EDITOR_ID);
					if (reusedEditor instanceof ChangeDetailEditor) {
						((ChangeDetailEditor) reusedEditor).refreshStatus();
					}
				} catch (PartInitException e) {
					EGerritCorePlugin.logError(gmInput.gerritClient != null
							? gmInput.gerritClient.getRepository().formatGerritVersion() + e.getMessage()
							: e.getMessage());
				}
			}
		}
		return Status.OK_STATUS;
	}

}
