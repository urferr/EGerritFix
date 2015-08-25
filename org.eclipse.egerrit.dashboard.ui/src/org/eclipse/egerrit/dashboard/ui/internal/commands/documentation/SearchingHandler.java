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

package org.eclipse.egerrit.dashboard.ui.internal.commands.documentation;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egerrit.dashboard.ui.GerritUi;
import org.eclipse.egerrit.dashboard.utils.GerritServerUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class implements the Dashboard-Gerrit UI documentation searching reviews handler.
 *
 * @since : 1.0
 */

public class SearchingHandler extends AbstractHandler {
	private static Logger logger = LoggerFactory.getLogger(SearchingHandler.class);

	private final String SEARCHING_DOCUMENTATION = "Documentation/user-search.html"; //$NON-NLS-1$

	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	@Override
	public Object execute(ExecutionEvent aEvent) throws ExecutionException {
		logger.debug("Search the documentation SearchingHandler  "); //$NON-NLS-1$

		GerritServerUtility.getInstance().openWebBrowser(SEARCHING_DOCUMENTATION);

		return null;
	}

}
