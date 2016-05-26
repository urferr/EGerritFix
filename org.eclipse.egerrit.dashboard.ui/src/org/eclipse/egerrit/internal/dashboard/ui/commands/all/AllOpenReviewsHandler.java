/*******************************************************************************
 * Copyright (c) 2013 Ericsson AB and others.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jacques Bouthillier - Initial implementation of the handler
 *
 ******************************************************************************/
package org.eclipse.egerrit.internal.dashboard.ui.commands.all;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.egerrit.internal.core.command.ChangeStatus;
import org.eclipse.egerrit.internal.dashboard.ui.views.GerritTableView;

/**
 * This class implements the "Add ..." a new Gerrit project locations.
 *
 * @since 1.0
 */

public class AllOpenReviewsHandler extends AbstractHandler {

	/**
	 * Method execute.
	 *
	 * @param aEvent
	 *            ExecutionEvent
	 * @return Object
	 * @see org.eclipse.core.commands.IHandler#execute(ExecutionEvent)
	 */
	public Object execute(final ExecutionEvent aEvent) {
		GerritTableView reviewTableView = GerritTableView.getActiveView(true);

		// see http://gerrit-documentation.googlecode.com/svn/Documentation/2.5.2/user-search.html
		//for All > Open--> status:open (or is:open)

		reviewTableView.processCommands(ChangeStatus.OPEN.getValue());

		return null;

	}

}
