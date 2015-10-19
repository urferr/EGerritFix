/*******************************************************************************
 * Copyright (c) 2013 Ericsson AB and others.
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
package org.eclipse.egerrit.dashboard.ui;

import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.Status;
import org.eclipse.egerrit.dashboard.ui.views.GerritTableView;
import org.eclipse.ui.commands.IElementUpdater;
import org.eclipse.ui.menus.UIElement;

/**
 * This class implements the refresh mechanism
 *
 * @since 1.0
 */
public class RefreshDashboardHandler extends AbstractHandler implements IElementUpdater {

	@Override
	public Object execute(final ExecutionEvent aEvent) {
		GerritTableView reviewTableView = GerritTableView.getActiveView(false);
		if (reviewTableView == null) {
			return Status.OK_STATUS;
		}
		reviewTableView.update();
		return Status.OK_STATUS; //For now , do not process the dialogue
	}

	@Override
	public void updateElement(UIElement element, Map parameters) {
	}
}
