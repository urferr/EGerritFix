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
import org.eclipse.egerrit.internal.model.FileInfo;
import org.eclipse.egerrit.internal.process.OpenCompareProcess;
import org.eclipse.egerrit.internal.ui.editors.QueryHelpers;
import org.eclipse.egerrit.internal.ui.utils.Messages;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.menus.UIElement;

/**
 * This class implements a handler to OPEN the compare editor with the latest patch set files of the selected review
 */
public class OpenCompareHandler extends DashboardFactoryHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// Execute the open compare if we have the information
		if (getChangeInfo() != null && getGerritClient() != null) {
			FileInfo selectedFile = null;
			//Load the files for the latest revision
			QueryHelpers.loadFiles(gerritClient, changeInfo.getRevision());
			EMap<String, FileInfo> files = getLatestRevision().getFiles();

			if (!files.isEmpty()) {
				selectedFile = files.get(0).getValue();//Get the first file of the revision
			}

			OpenCompareProcess openCompare = new OpenCompareProcess();
			openCompare.handleOpenCompare(HandlerUtil.getActiveShell(event), getGerritClient(), getChangeInfo(),
					selectedFile, getLatestRevision());
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
		String message = Messages.OpenCompareHandler_generalMessage;
		if (getChangeInfo() != null && getGerritClient() != null) {
			message = NLS.bind(Messages.OpenCompareHandler_specificMessage,
					new Object[] { getChangeInfo().get_number(), getLatestRevision().get_number() });
		}
		element.setTooltip(message);
	}
}
