/*******************************************************************************
 * Copyright (c) 2017 Ericsson AB.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson - initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.internal.rcptt.support;

import java.net.URISyntaxException;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.egerrit.internal.core.GerritServerInformation;
import org.eclipse.egerrit.internal.core.ServersStore;
import org.eclipse.egerrit.internal.rcptt.support.egerritecl.AddGerritServer;
import org.eclipse.rcptt.ecl.core.Command;
import org.eclipse.rcptt.ecl.runtime.ICommandService;
import org.eclipse.rcptt.ecl.runtime.IProcess;

public class AddGerritServerService implements ICommandService {

	@Override
	public IStatus service(Command cmd, IProcess arg1) throws InterruptedException, CoreException {
		List<GerritServerInformation> allServers = ServersStore.getAllServers();
		try {
			String serverName = ((AddGerritServer) cmd).getServerName();
			if (serverName == null) {
				return new Status(IStatus.ERROR, "org.eclipse.egerrit.ui.tests", //$NON-NLS-1$
						"ServerName must be specified in add-gerrit-server command"); //$NON-NLS-1$
			}

			String serverURL = ((AddGerritServer) cmd).getServerURL();
			if (serverURL == null) {
				return new Status(IStatus.ERROR, "org.eclipse.egerrit.ui.tests", //$NON-NLS-1$
						"ServerURL must be specified in add-gerrit-server command"); //$NON-NLS-1$
			}

			String userName = ((AddGerritServer) cmd).getUserName();
			if (userName == null) {
				return new Status(IStatus.ERROR, "org.eclipse.egerrit.ui.tests", //$NON-NLS-1$
						"UserName must be specified in add-gerrit-server command"); //$NON-NLS-1$
			}

			GerritServerInformation newServer = new GerritServerInformation(serverURL, serverName);
			newServer.setUserName(userName);
			String userPwd = ((AddGerritServer) cmd).getUserPwd();
			if (userPwd != null) {
				newServer.setPassword(userPwd);
			}
			allServers.add(newServer);
			ServersStore.saveServers(allServers);
		} catch (URISyntaxException e) {
			return new Status(IStatus.ERROR, "org.eclipse.egerrit.ui.tests", //$NON-NLS-1$
					"An error occurred processing the serverURL."); //$NON-NLS-1$
		}
		return org.eclipse.core.runtime.Status.OK_STATUS;
	}

}
