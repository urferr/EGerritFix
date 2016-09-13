/*******************************************************************************
 * Copyright (c) 2016 Ericsson AB.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson - initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.internal.dashboard.ui.commands.table;

import java.util.Map;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egerrit.internal.ui.editors.CherryPickRevision;
import org.eclipse.egerrit.internal.ui.utils.Messages;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.menus.UIElement;

/**
 * This class implements a handler to CherryPick the current commit with the current local branch
 */
public class CherryPickHandler extends DashboardFactoryHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// Execute the cherryPick current revision with the current local branch
		if (getChangeInfo() != null && getGerritClient() != null) {
			CherryPickRevision cherryPickRevision = new CherryPickRevision(getLatestRevision(), getGerritClient());
			cherryPickRevision.run();
		}

		return null;
	}

	@Override
	public void setEnabled(Object evaluationContext) {
		super.setEnabled(evaluationContext);

		if (getChangeInfo() == null) {
			setBaseEnabled(false);
			return;
		}
	}

	@Override
	public void updateElement(UIElement element, Map parameters) {
		super.updateElement(element, parameters);
		String message = Messages.CherryPickHandler_generalMessage;
		if (getChangeInfo() != null && getGerritClient() != null) {
			message = NLS.bind(Messages.CherryPickHandler_specificMessage,
					new Object[] { getChangeInfo().get_number(), getLatestRevision().get_number() });
		}
		element.setTooltip(message);
	}
}
