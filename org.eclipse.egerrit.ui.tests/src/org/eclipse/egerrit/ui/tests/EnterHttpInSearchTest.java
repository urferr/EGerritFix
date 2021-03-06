/*******************************************************************************
 * Copyright (c) 2015-2016 Ericsson AB.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson - initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.ui.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.egerrit.internal.core.GerritServerInformation;
import org.eclipse.egerrit.internal.core.ServersStore;
import org.eclipse.egerrit.internal.dashboard.ui.views.GerritTableView;
import org.eclipse.egerrit.internal.dashboard.utils.GerritServerUtility;
import org.eclipse.swt.widgets.Display;
import org.junit.Before;
import org.junit.Test;

public class EnterHttpInSearchTest {

	@Before
	public void cleanUpStore() {
		ServersStore.saveServers(new ArrayList<GerritServerInformation>());
	}

	@Test
	//This tests the case where no server information was known by the plugin
	public void enterANonExistantURL() {
		final GerritTableView reviewTableView = GerritTableView.getActiveView(true);
		reviewTableView.processCommands("https://git.eclipse.org/r/#/c/54796/"); //$NON-NLS-1$
		Display.findDisplay(Thread.currentThread()).asyncExec(new Runnable() {
			@Override
			public void run() {
				assertTrue(1 == ServersStore.getAllServers().size());
				assertTrue(1 == reviewTableView.getTableViewer().getTable().getItemCount());
			}
		});

	}

	@Test
	//This tests the case where the server was already entered
	public void enterAnExistingURL() throws URISyntaxException {
		//Setup, we create a server
		GerritServerInformation anonymousServer = new GerritServerInformation("https://git.eclipse.org/r", "server"); //$NON-NLS-1$ //$NON-NLS-2$
		List<GerritServerInformation> servers = new ArrayList<>();
		servers.add(anonymousServer);
		ServersStore.saveServers(servers);

		//Open the view enter something in it
		final GerritTableView reviewTableView = GerritTableView.getActiveView(true);
		reviewTableView.processCommands("https://git.eclipse.org/r/#/c/54796/"); //$NON-NLS-1$
		Display.findDisplay(Thread.currentThread()).asyncExec(new Runnable() {
			@Override
			public void run() {
				assertTrue(1 == ServersStore.getAllServers().size());
				assertTrue(1 == reviewTableView.getTableViewer().getTable().getItemCount());
			}
		});
	}

	@Test
	public void preferServerWithUsername() throws URISyntaxException {
		//Setup, we create two servers, one anonymous, the other with a user
		GerritServerInformation anonymousServer = new GerritServerInformation("https://git.eclipse.org/r", "server"); //$NON-NLS-1$ //$NON-NLS-2$
		final GerritServerInformation userServer = new GerritServerInformation("https://git.eclipse.org/r", "server2"); //$NON-NLS-1$ //$NON-NLS-2$
		userServer.setUserName("bob"); //$NON-NLS-1$
		List<GerritServerInformation> servers = new ArrayList<>();
		servers.add(anonymousServer);
		servers.add(userServer);
		ServersStore.saveServers(servers);

		//Open the view enter something in it
		final GerritTableView reviewTableView = GerritTableView.getActiveView(true);
		reviewTableView.processCommands("https://git.eclipse.org/r/#/c/54796/"); //$NON-NLS-1$
		Display.findDisplay(Thread.currentThread()).asyncExec(new Runnable() {
			@Override
			public void run() {
				assertTrue(2 == ServersStore.getAllServers().size());
				assertEquals(userServer, GerritServerUtility.getInstance().getLastSavedGerritServer());
				assertTrue(1 == reviewTableView.getTableViewer().getTable().getItemCount());
			}
		});
	}

	@Test
	public void enterTheDashboardURL() {
		final GerritTableView reviewTableView = GerritTableView.getActiveView(true);
		reviewTableView.processCommands("https://git.eclipse.org/r/#/q/status:open"); //$NON-NLS-1$
		Display.findDisplay(Thread.currentThread()).asyncExec(new Runnable() {
			@Override
			public void run() {
				assertTrue(1 == ServersStore.getAllServers().size());
				assertTrue(1 < reviewTableView.getTableViewer().getTable().getItemCount());
			}
		});
	}

	@Test
	public void enterJustTheRootURL() {
		final GerritTableView reviewTableView = GerritTableView.getActiveView(true);
		reviewTableView.processCommands("https://git.eclipse.org/r/"); //$NON-NLS-1$
		Display.findDisplay(Thread.currentThread()).asyncExec(new Runnable() {
			@Override
			public void run() {
				assertTrue(1 == ServersStore.getAllServers().size());
				assertTrue(1 < reviewTableView.getTableViewer().getTable().getItemCount());
			}
		});
	}

}
