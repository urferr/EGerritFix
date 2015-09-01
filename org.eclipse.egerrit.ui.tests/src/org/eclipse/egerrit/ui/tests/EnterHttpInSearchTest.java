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

package org.eclipse.egerrit.ui.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.egerrit.core.GerritServerInformation;
import org.eclipse.egerrit.core.ServersStore;
import org.eclipse.egerrit.dashboard.ui.views.GerritTableView;
import org.eclipse.egerrit.dashboard.utils.GerritServerUtility;
import org.eclipse.swt.widgets.Display;
import org.junit.Before;
import org.junit.Test;

public class EnterHttpInSearchTest {

	@Before
	public void cleanUpStore() {
		ServersStore.saveServers(new ArrayList<GerritServerInformation>());
	}

	@Test
	public void enterANonExistantURL() {
		final GerritTableView reviewTableView = GerritTableView.getActiveView();
		reviewTableView.processCommands("https://git.eclipse.org/r/#/c/54796/");
		Display.findDisplay(Thread.currentThread()).asyncExec(new Runnable() {
			@Override
			public void run() {
				assertTrue(1 == ServersStore.getAllServers().size());
				assertTrue(1 == reviewTableView.getTableViewer().getTable().getItemCount());
			}
		});

	}

	@Test
	public void enterAnExistingURL() throws URISyntaxException {
		//Setup, we create a server
		GerritServerInformation anonymousServer = new GerritServerInformation("https://git.eclipse.org/r", "server");
		List<GerritServerInformation> servers = new ArrayList<>();
		servers.add(anonymousServer);
		ServersStore.saveServers(servers);

		//Open the view enter something in it
		final GerritTableView reviewTableView = GerritTableView.getActiveView();
		reviewTableView.processCommands("https://git.eclipse.org/r/#/c/54796/");
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
		GerritServerInformation anonymousServer = new GerritServerInformation("https://git.eclipse.org/r", "server");
		final GerritServerInformation userServer = new GerritServerInformation("https://git.eclipse.org/r", "server2");
		userServer.setUserName("bob");
		List<GerritServerInformation> servers = new ArrayList<>();
		servers.add(anonymousServer);
		servers.add(userServer);
		ServersStore.saveServers(servers);

		//Open the view enter something in it
		final GerritTableView reviewTableView = GerritTableView.getActiveView();
		reviewTableView.processCommands("https://git.eclipse.org/r/#/c/54796/");
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
	public void enterJustAURL() {
		final GerritTableView reviewTableView = GerritTableView.getActiveView();
		reviewTableView.processCommands("https://git.eclipse.org/r/#/c/54796/");
		Display.findDisplay(Thread.currentThread()).asyncExec(new Runnable() {
			@Override
			public void run() {
				assertTrue(1 < reviewTableView.getTableViewer().getTable().getItemCount());
			}
		});
	}

}
