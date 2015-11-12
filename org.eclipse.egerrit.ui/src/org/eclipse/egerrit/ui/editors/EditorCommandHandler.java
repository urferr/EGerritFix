/*******************************************************************************
 * Copyright (c) 2015 Ericsson AB and others.
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
package org.eclipse.egerrit.ui.editors;

//import static org.junit.Assert.fail;

import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.Status;
import org.eclipse.egerrit.ui.EGerritUIPlugin;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.commands.IElementUpdater;
import org.eclipse.ui.menus.UIElement;

/**
 * This class Handles a command to call the save editor command
 *
 * @since 1.0
 */
public class EditorCommandHandler extends AbstractHandler implements IElementUpdater {

	@SuppressWarnings("finally")
	@Override
	public Object execute(final ExecutionEvent aEvent) {

		IWorkbench workbench = EGerritUIPlugin.getDefault().getWorkbench();
		IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
		IWorkbenchPage page = null;
		if (window != null) {
			page = workbench.getActiveWorkbenchWindow().getActivePage();
		}

		if (page != null) {
//			((ChangeDetailEditor) page.getActiveEditor()).summaryTab.saveTopic();
			((ChangeDetailEditor) page.getActiveEditor()).filesTab.selectRow();
		}

		return Status.OK_STATUS;
	}

	//}

	@Override
	public void updateElement(UIElement element, Map parameters) {
	}
}
