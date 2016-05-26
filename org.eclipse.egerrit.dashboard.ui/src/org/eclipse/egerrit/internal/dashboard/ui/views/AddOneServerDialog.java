/*******************************************************************************
 * Copyright (c) 2015 Ericsson AB.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson - initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.internal.dashboard.ui.views;

import java.util.List;

import org.eclipse.egerrit.internal.core.GerritServerInformation;
import org.eclipse.egerrit.internal.core.ServersStore;
import org.eclipse.egerrit.internal.dashboard.ui.preferences.GerritServerDialog;
import org.eclipse.egerrit.internal.dashboard.utils.GerritServerUtility;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.ui.PlatformUI;

/**
 * A helper class opening a dialog to enter/modify one server
 */
public class AddOneServerDialog {
	private GerritServerInformation server;

	/**
	 * Open a dialog prompting the user to enter a new server
	 *
	 * @param saveAsDefaultServer
	 *            set to true to cause the new server to be remebered as the last server
	 */
	public void promptForNewServer(boolean saveAsDefaultServer) {
		promptToModifyServer(null, saveAsDefaultServer);
	}

	/**
	 * Open a dialog prompting the user to modify the server passed a parameter
	 *
	 * @param saveAsDefaultServer
	 *            set to true to cause the new server to be remebered as the last server
	 */
	public void promptToModifyServer(GerritServerInformation serverToEdit, boolean saveAsDefaultServer) {
		List<GerritServerInformation> listServers;
		GerritServerDialog dialog = new GerritServerDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(),
				serverToEdit);
		int ret = dialog.open();
		if (ret == IDialogConstants.OK_ID) {
			server = dialog.getServerInfo();
			// get current list
			listServers = ServersStore.getAllServers();
			// remove the old one
			listServers.remove(serverToEdit);
			// add new one
			listServers.add(server);
			// save it
			ServersStore.saveServers(listServers);
			if (saveAsDefaultServer) {
				GerritServerUtility.getInstance().saveLastGerritServer(server);
			}
		} else {
			server = null;
		}
	}

	/**
	 * Return a gerrit server for the information input in the dialog
	 * 
	 * @return a gerrit server object or null if the user has cancelled
	 */
	public GerritServerInformation getServer() {
		return server;
	}
}
