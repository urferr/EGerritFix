/*******************************************************************************
 * Copyright (c) 2013-2016 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jacques Bouthillier - Initial Implementation of the server selection
 ******************************************************************************/

package org.eclipse.egerrit.internal.dashboard.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.core.runtime.IPath;
import org.eclipse.egerrit.internal.core.GerritServerInformation;
import org.eclipse.egerrit.internal.core.ServersStore;
import org.eclipse.egerrit.internal.dashboard.GerritPlugin;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class implements some utility for the Gerrit servers.
 *
 * @since 1.0
 */
public class GerritServerUtility {

	private static Logger logger = LoggerFactory.getLogger(GerritServerUtility.class);
	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field LAST_GERRIT_FILE. (value is ""lastGerrit.txt"")
	 */
	private static final String LAST_GERRIT_FILE = "lastGerrit.txt"; //$NON-NLS-1$

	/**
	 * Field LAST_COMMANDS_FILE. (value is ""lastCommands.txt"")
	 */
	private static final String LAST_COMMANDS_FILE = "lastCommands.txt"; //$NON-NLS-1$

	/**
	 * Field ECLIPSE_GERRIT_DEFAULT. (value is ""https://git.eclipse.org/r/"")
	 */
	private static final String ECLIPSE_GERRIT_DEFAULT = "https://git.eclipse.org/r/"; //$NON-NLS-1$

	/**
	 * Field SLASH. (value is ""/"")
	 */
	private static final char SLASH = '/';

	// ------------------------------------------------------------------------
	// Variables
	// ------------------------------------------------------------------------

	private static GerritServerUtility fInstance = null;

	//In-memory cache of the last user server.
	private GerritServerInformation lastServerCache = null;

	private int serversStoreTimestamp = -1;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
	private GerritServerUtility() {
		fInstance = this;
	}

	/**
	 * Build and return the File storing the persistent data
	 *
	 * @param String
	 *            aFile
	 * @return File
	 */
	private File getLastGerritFile(String aFile) {
		IPath ipath = GerritPlugin.getDefault().getStateLocation();
		String fileName = ipath.append(aFile).toPortableString();
		return new File(fileName);
	}

	/**
	 * Build a URL for Gerrit documentation
	 *
	 * @param aRequest
	 *            specific documentation
	 * @return URL complete URL for the selected site based on the Gerrit server and version
	 * @throws MalformedURLException
	 */
	private URL buildDocumentationURL(String aRequest) throws MalformedURLException {
		StringBuilder sb = new StringBuilder();

		GerritServerInformation lastSaved = getInstance().getLastSavedGerritServer();
		sb.append(lastSaved != null ? lastSaved.getServerURI() : ECLIPSE_GERRIT_DEFAULT);
		if (sb.charAt(sb.length() - 1) != SLASH) {
			sb.append(SLASH);
		}
		sb.append(aRequest);
		return new URL(sb.toString());
	}

	/**
	 * Search for a similar page in the eclipse editor
	 *
	 * @param aUrl
	 * @return String
	 */
	private String getEditorId(URL aUrl) {
		//Try to get the editor id
		IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(aUrl.getFile());
		String id = null;
		if (desc != null) {
			id = desc.getId();
		}

		return id;
	}

	// ------------------------------------------------------------------------
	// Methods Public
	// ------------------------------------------------------------------------
	public static GerritServerUtility getInstance() {
		if (fInstance == null) {
			new GerritServerUtility();
		}
		return fInstance;
	}

	/**
	 * Save the selected Gerrit server
	 *
	 * @param aURL
	 * @return Boolean
	 */
	public boolean saveLastGerritServer(GerritServerInformation server) {
		//Check the cache
		if (lastServerCache != null && lastServerCache.equals(server)) {
			return true;
		}
		//Cache miss, we need to write and reset the in-memory cache
		boolean ok = true;
		File file = getLastGerritFile(LAST_GERRIT_FILE);
		try {
			FileWriter fw = new FileWriter(file);
			BufferedWriter out = new BufferedWriter(fw);
			out.write(Integer.toString(server.hashCode()));
			out.close();
			fw.close();
		} catch (IOException e1) {
			logger.debug("Saved last Gerrit server " + e1); //$NON-NLS-1$
			ok = false;
		}

		return ok;
	}

	/**
	 * Return the last selected Gerrit server used
	 *
	 * @return the last server remembered {@link GerritServerInformation}
	 */
	public GerritServerInformation getLastSavedGerritServer() {
		if (lastServerCache != null && ServersStore.getStoreTimeStamp() == serversStoreTimestamp) {
			return lastServerCache;
		}

		String lastServerId = null;
		File file = getLastGerritFile(LAST_GERRIT_FILE);
		if (file != null) {
			try {
				FileReader fr = new FileReader(file);
				BufferedReader in = new BufferedReader(fr);
				lastServerId = in.readLine();
				in.close();
				fr.close();
			} catch (IOException e1) {
				//When there is no file,
				//e1.printStackTrace();
			}
		}
		serversStoreTimestamp = ServersStore.getStoreTimeStamp();
		return ServersStore.getServer(lastServerId);
	}

	/**
	 * Reset the last server stored
	 */
	public void resetLastGerritServer() {
		//Check the cache
		lastServerCache = null;
		try {
			saveLastGerritServer(new GerritServerInformation("", "")); //$NON-NLS-1$ //$NON-NLS-2$
		} catch (URISyntaxException e) {
			//e.printStackTrace();
		}
	}

	/**
	 * Open the web browser for the specific documentation
	 *
	 * @param String
	 *            aDocumentation requested documentation
	 */
	public void openWebBrowser(String aDocumentation) {
		if (fInstance == null) {
			fInstance = getInstance();
		}

		IWorkbenchBrowserSupport workBenchSupport = PlatformUI.getWorkbench().getBrowserSupport();
		URL url = null;
		try {
			url = buildDocumentationURL(aDocumentation);
			try {

				//Using NULL as a browser id will create a new editor each time,
				//so we need to see if there is already an editor for this help
				String id = getEditorId(url);
				workBenchSupport.createBrowser(id).openURL(url);
			} catch (PartInitException e) {
				logger.debug("PartInitException openWebBrowser for " + e); //$NON-NLS-1$
			}
		} catch (MalformedURLException e1) {
			logger.debug("MalformedURLException openWebBrowser for " + e1); //$NON-NLS-1$
		}
		logger.debug("openWebBrowser for " + url); //$NON-NLS-1$
	}

	/**
	 * Save the list of the last 5 commands
	 *
	 * @param LinkedHashSet
	 *            <String>
	 * @return Boolean
	 */
	public Boolean saveLastCommandList(Set<String> aCommands) {
		Boolean ok = true;
		File file = getLastGerritFile(LAST_COMMANDS_FILE);
		try {
			FileWriter fw = new FileWriter(file);
			BufferedWriter out = new BufferedWriter(fw);
			Iterator<String> iter = aCommands.iterator();
			while (iter.hasNext()) {
				String s = iter.next();
				out.write(s);
				out.newLine();
			}
			out.close();
			fw.close();
		} catch (IOException e1) {
			logger.debug("Saved last command  IOException " + e1); //$NON-NLS-1$
			ok = false;
		}

		return ok;
	}

	/**
	 * Return the list of the last commands saved
	 *
	 * @return Set
	 */
	public Set<String> getListLastCommands() {
		LinkedHashSet<String> lastCommands = new LinkedHashSet<>();
		File file = getLastGerritFile(LAST_COMMANDS_FILE);
		if (file != null) {
			try {
				FileReader fr = new FileReader(file);
				BufferedReader in = new BufferedReader(fr);
				while (in.ready()) {
					String line = in.readLine();
					lastCommands.add(line);
				}
				in.close();
				fr.close();
			} catch (IOException e1) {
				//When there is no file,
				//e1.printStackTrace();
			}
		}
		return lastCommands;
	}

}
