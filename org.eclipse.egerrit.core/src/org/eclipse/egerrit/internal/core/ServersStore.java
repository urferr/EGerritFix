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

package org.eclipse.egerrit.internal.core;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.osgi.service.prefs.BackingStoreException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class ServersStore {
	private static final String EGERRIT_NODE = EGerritCorePlugin.PLUGIN_ID;

	private static final String KEY_SERVERS_LIST = "serversList"; //$NON-NLS-1$

	private static int timestamp = 0;

	/**
	 * The default constructor. Do not allow to build an object of this class
	 */
	private ServersStore() {
	}

	/**
	 * Persists the list of servers
	 *
	 * @param servers
	 */
	public static void saveServers(List<GerritServerInformation> servers) {
		timestamp++;
		String serializedServers = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(servers);
		InstanceScope.INSTANCE.getNode(EGERRIT_NODE).put(KEY_SERVERS_LIST, serializedServers);
		try {
			InstanceScope.INSTANCE.getNode(EGERRIT_NODE).flush();
		} catch (BackingStoreException e) {
			EGerritCorePlugin.logError(e.getLocalizedMessage());
		}
		for (GerritServerInformation server : servers) {
			server.persistPassword();
		}
	}

	/**
	 * Returns the list of all known gerrit repositories
	 *
	 * @return a list of {@link GerritServerInformation}
	 */
	public static List<GerritServerInformation> getAllServers() {
		String serverList = InstanceScope.INSTANCE.getNode(EGERRIT_NODE).get(KEY_SERVERS_LIST, ""); //$NON-NLS-1$
		if (serverList.isEmpty()) {
			return new ArrayList<>();
		}
		return new Gson().fromJson(serverList, new TypeToken<ArrayList<GerritServerInformation>>() {
		}.getType());
	}

	/**
	 * Returns a server object whose identifier matches the identifier passed in
	 */
	public static GerritServerInformation getServer(String identifier) {
		if (identifier == null) {
			return null;
		}
		int id = -1;
		try {
			id = Integer.parseInt(identifier);
		} catch (NumberFormatException e) {
			return null;
		}
		List<GerritServerInformation> servers = ServersStore.getAllServers();
		for (GerritServerInformation server : servers) {
			if (server.hashCode() == id) {
				return server;
			}
		}
		return null;
	}

	//Keep track of the last time the store has seen a modification
	public static int getStoreTimeStamp() {
		return timestamp;
	}
}
