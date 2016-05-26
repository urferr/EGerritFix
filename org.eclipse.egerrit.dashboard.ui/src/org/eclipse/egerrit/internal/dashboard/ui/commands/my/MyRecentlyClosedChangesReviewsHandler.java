/*******************************************************************************
 * Copyright (c) 2013 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Ericsson - Initial Implementation of the plug-in handler
 ******************************************************************************/

package org.eclipse.egerrit.internal.dashboard.ui.commands.my;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egerrit.internal.dashboard.core.GerritQuery;
import org.eclipse.egerrit.internal.dashboard.ui.views.GerritTableView;

/**
 * This class implements the Dashboard-Gerrit UI my recently closed changes reviews handler.
 *
 * @since 1.0
 */

public class MyRecentlyClosedChangesReviewsHandler extends AbstractHandler {

	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	@Override
	public Object execute(ExecutionEvent aEvent) throws ExecutionException {
		// see http://gerrit-documentation.googlecode.com/svn/Documentation/2.5.2/user-search.html
		GerritTableView reviewTableView = GerritTableView.getActiveView(true);
		reviewTableView.processCommands(GerritQuery.MY_RECENTLY_CLOSED_CHANGES);

		return null;
	}

}
