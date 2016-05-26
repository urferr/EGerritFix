/*******************************************************************************
 * Copyright (c) 2013 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jacques Bouthillier - Initial Implementation of the plug-in handler
 ******************************************************************************/

package org.eclipse.egerrit.internal.dashboard.ui.commands.my;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egerrit.internal.core.command.ChangeState;
import org.eclipse.egerrit.internal.core.command.ChangeStatus;
import org.eclipse.egerrit.internal.dashboard.ui.views.GerritTableView;

/**
 * This class implements the Dashboard-Gerrit UI my starred changes reviews handler.
 *
 * @since 1.0
 */

public class MyStarredChangesReviewsHandler extends AbstractHandler {

	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	@Override
	public Object execute(ExecutionEvent aEvent) throws ExecutionException {
		GerritTableView reviewTableView = GerritTableView.getActiveView(true);

		// see http://gerrit-documentation.googlecode.com/svn/Documentation/2.5.2/user-search.html
		//for My > Starred Changess--> is:starred
		reviewTableView.processCommands(ChangeState.IS_STARRED.getValue() + " " + ChangeStatus.OPEN.getValue()); //$NON-NLS-1$

		return null;
	}

}
