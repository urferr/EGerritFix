/*******************************************************************************
 * Copyright (c) 2013 Ericsson AB and others.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jacques Bouthillier - Created for Review Gerrit Dashboard project
 *
 ******************************************************************************/
package org.eclipse.egerrit.internal.dashboard.ui.commands;

import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.Status;
import org.eclipse.egerrit.internal.core.GerritServerInformation;
import org.eclipse.egerrit.internal.core.ServersStore;
import org.eclipse.egerrit.internal.core.command.ChangeState;
import org.eclipse.egerrit.internal.core.command.ChangeStatus;
import org.eclipse.egerrit.internal.dashboard.core.GerritQuery;
import org.eclipse.egerrit.internal.dashboard.ui.preferences.GerritDashboardPreferencePage;
import org.eclipse.egerrit.internal.dashboard.ui.utils.UIConstants;
import org.eclipse.egerrit.internal.dashboard.ui.views.GerritTableView;
import org.eclipse.egerrit.internal.dashboard.utils.GerritServerUtility;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.ui.commands.IElementUpdater;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.eclipse.ui.menus.UIElement;

/**
 * This class implements the behavior of the blue cylinder in the toolbar, and the selection of an entry in its drop
 * down menu
 *
 * @since 1.0
 */
public class SelectReviewSiteHandler extends AbstractHandler implements IElementUpdater {

	private GerritServerUtility fServerUtil = GerritServerUtility.getInstance();

	@Override
	public Object execute(final ExecutionEvent aEvent) {
		GerritTableView reviewTableView = GerritTableView.getActiveView(true);

		//Now, deal with the case where the user selected a server from the drop down
		GerritServerInformation server = getServer(aEvent.getParameter(UIConstants.SELECT_SERVER_COMMAND_ID_PARAM));
		if (server == null) {
			return Status.OK_STATUS;
		}
		reviewTableView.openView();
		fServerUtil.saveLastGerritServer(server);

		if (!server.getUserName().isEmpty()) {
			reviewTableView.processCommands(GerritQuery.MY_CHANGES);
		} else {
			reviewTableView.processCommands(ChangeState.IS_OPEN.getValue() + " " //$NON-NLS-1$
					+ ChangeStatus.OPEN.getValue());
		}
		return Status.OK_STATUS; //For now , do not process the dialogue
	}

	private GerritServerInformation getServer(String id) {
		//First try to resolve the id. This is only if the invocation corresponds to a server being selected
		GerritServerInformation resolvedServer = ServersStore.getServer(id);
		if (resolvedServer != null) {
			return resolvedServer;
		}
		//No server selected, pick the last used server
		GerritServerInformation savedServer = GerritServerUtility.getInstance().getLastSavedGerritServer();
		if (savedServer != null) {
			return savedServer;
		}
		//No server ever selected, grab the unique server if it exists
		List<GerritServerInformation> allServers = ServersStore.getAllServers();
		if (allServers.size() == 1) {
			return ServersStore.getAllServers().get(0);
		}
		//No server, prompt the user to enter one
		if (allServers.isEmpty()) {
			PreferenceDialog prefDialog = PreferencesUtil.createPreferenceDialogOn(null,
					GerritDashboardPreferencePage.getID(), null, null);
			prefDialog.setBlockOnOpen(true);
			prefDialog.open();
			//By now hopefully we have a new server
			allServers = ServersStore.getAllServers();
			if (allServers.size() == 1) {
				return ServersStore.getAllServers().get(0);
			}
		}
		return null;
	}

	@Override
	public void updateElement(UIElement element, Map parameters) {
		String serverId = (String) parameters.get(UIConstants.SELECT_SERVER_COMMAND_ID_PARAM);
		if (serverId == null) {
			element.setChecked(false);
			return;
		}

		GerritServerInformation savedServer = GerritServerUtility.getInstance().getLastSavedGerritServer();
		if (savedServer == null) {
			element.setChecked(false);
			return;
		}

		GerritServerInformation server = ServersStore.getServer(serverId);
		if (server == null) {
			element.setChecked(false);
			return;
		}

		if (savedServer.equals(server)) {
			element.setChecked(true);
		} else {
			element.setChecked(false);
		}
	}
}
