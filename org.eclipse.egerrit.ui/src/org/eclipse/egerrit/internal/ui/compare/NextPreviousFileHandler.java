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

import org.eclipse.compare.INavigatable;
import org.eclipse.compare.internal.CompareEditor;
import org.eclipse.compare.internal.CompareEditorInputNavigator;
import org.eclipse.compare.internal.Utilities;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

/**
 * This class implements the command handler for the file navigation in the compare editor
 *
 * @since 1.0
 */
public class NextPreviousFileHandler extends AbstractHandler {

	private GerritMultipleInput compareInput;

//Helper method to return the top left pane
	private Object getTopPane() {
		Object topPane = ((CompareEditorInputNavigator) compareInput.getNavigator()).getPanes()[0];
		if (topPane != null) {
			return Utilities.getAdapter(topPane, INavigatable.class);
		}
		return null;
	}

	private void move(int direction) {
		Object topPane = getTopPane();
		if (topPane == null) {
			return;
		}
		if (topPane instanceof INavigatable) {
			((INavigatable) topPane).selectChange(direction);
		}
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchPart editor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		if (editor instanceof CompareEditor) {
			String commandName = event.getCommand().getId();
			boolean isNext = commandName.contains("selectNextFile"); //$NON-NLS-1$
			compareInput = (GerritMultipleInput) ((CompareEditor) editor).getEditorInput();
			if (isNext) {
				move(INavigatable.NEXT_CHANGE);
			} else {
				move(INavigatable.PREVIOUS_CHANGE);
			}
		}
		return Status.OK_STATUS;
	}
}
