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
import org.eclipse.egerrit.internal.process.SubmitProcess;
import org.eclipse.egerrit.internal.ui.utils.Messages;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.menus.UIElement;

/**
 * This class implements a handler to SUBMIT the latest branch of the selected review
 */
public class SubmitHandler extends DashboardFactoryHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// Execute the Submit if we have the information
		if (getChangeInfo() != null && getGerritClient() != null) {
			ActionInfo submitInfo = getRevisionAction(ActionConstants.SUBMIT.getName());
			if (submitInfo != null && submitInfo.isEnabled()) {
				SubmitProcess submitProcess = new SubmitProcess();
				submitProcess.handleSubmit(getChangeInfo(), getGerritClient());
			} else {
				Utils.displayInformation(null, Messages.SubmitHandler_title,
						NLS.bind(Messages.SubmitHandler_notAvailable, getChangeInfo().getSubject()));
			}
		}
		return null;
	}

	@Override
	public void setEnabled(Object evaluationContext) {
		super.setEnabled(evaluationContext);
		boolean state = true;
		ActionInfo submitInfo = getRevisionAction(ActionConstants.SUBMIT.getName());
		if (submitInfo != null && submitInfo.isEnabled()) {
			state = true;
		} else {
			state = false;
		}
		setBaseEnabled(state);
	}

	@Override
	public void updateElement(UIElement element, Map parameters) {
		super.updateElement(element, parameters);
		String message = Messages.SubmitHandler_generalMessage;
		if (getChangeInfo() != null && getGerritClient() != null) {
			message = NLS.bind(Messages.SubmitHandler_specificMessage, new Object[] { getChangeInfo().get_number(),
					getLatestRevision().get_number(), getChangeInfo().getBranch() });
		}
		element.setTooltip(message);
	}
}
