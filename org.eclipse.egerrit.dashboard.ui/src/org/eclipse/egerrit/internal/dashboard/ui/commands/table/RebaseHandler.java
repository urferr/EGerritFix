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
import org.eclipse.egerrit.internal.dashboard.ui.preferences.Utils;
import org.eclipse.egerrit.internal.model.ActionConstants;
import org.eclipse.egerrit.internal.model.ActionInfo;
import org.eclipse.egerrit.internal.process.RebaseProcess;
import org.eclipse.egerrit.internal.ui.utils.Messages;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.menus.UIElement;

/**
 * This class implements a handler to Rebase the latest branch of the selected review
 */
public class RebaseHandler extends DashboardFactoryHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// Execute the rebase if we have the information
		if (getChangeInfo() != null && getGerritClient() != null) {
			ActionInfo rebaseInfo = getRevisionAction(ActionConstants.REBASE.getName());
			if (rebaseInfo != null && rebaseInfo.isEnabled()) {
				RebaseProcess rebaseProcess = new RebaseProcess();
				rebaseProcess.handleRebase(HandlerUtil.getActiveShell(event), getChangeInfo(), getLatestRevision(),
						getGerritClient());
			} else {
				Utils.displayInformation(null, Messages.RebaseHandler_title,
						NLS.bind(Messages.RebaseHandler_notNecessary, getChangeInfo().getSubject()));
			}
		}
		return null;
	}

	@Override
	public void setEnabled(Object evaluationContext) {
		super.setEnabled(evaluationContext);
		boolean state = true;
		ActionInfo rebaseInfo = getRevisionAction(ActionConstants.REBASE.getName());
		if (rebaseInfo != null && rebaseInfo.isEnabled()) {
			state = true;
		} else {
			state = false;
		}
		setBaseEnabled(state);
	}

	@Override
	public void updateElement(UIElement element, Map parameters) {
		super.updateElement(element, parameters);
		String message = Messages.RebaseHandler_generalMessage;
		if (getChangeInfo() != null && getGerritClient() != null) {
			message = NLS.bind(Messages.RebaseHandler_specificMessage,
					new Object[] { getLatestRevision().get_number(), getChangeInfo().get_number() });
		}
		element.setTooltip(message);
	}
}
