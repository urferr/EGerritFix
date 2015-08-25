/*******************************************************************************
 * Copyright (c) 2013 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jacques Bouthillier - Initial Implementation of the server selection
 ******************************************************************************/

package org.eclipse.egerrit.dashboard.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IPath;
import org.eclipse.egerrit.core.EGerritCorePlugin;
import org.eclipse.egerrit.dashboard.GerritPlugin;
import org.eclipse.egerrit.dashboard.preferences.GerritServerInformation;
import org.eclipse.egerrit.dashboard.preferences.PreferenceConstants;
import org.eclipse.equinox.security.storage.EncodingUtils;
import org.eclipse.equinox.security.storage.ISecurePreferences;
import org.eclipse.equinox.security.storage.SecurePreferencesFactory;
import org.eclipse.equinox.security.storage.StorageException;
import org.eclipse.jface.preference.IPreferenceStore;
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

	Logger logger = LoggerFactory.getLogger(GerritServerUtility.class);
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
	private final String ECLIPSE_GERRIT_DEFAULT = "https://git.eclipse.org/r/"; //$NON-NLS-1$

	/**
	 * Field SLASH. (value is ""/"")
	 */
	private final String SLASH = "/"; //$NON-NLS-1$

	// ------------------------------------------------------------------------
	// Variables
	// ------------------------------------------------------------------------

	private static GerritServerUtility fInstance = null;

	private List<GerritServerInformation> fGerritServer = new ArrayList<GerritServerInformation>();

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
	protected GerritServerUtility() {
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
		File file = new File(fileName);
		return file;
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

		String lastSaved = getInstance().getLastSavedGerritServer();
		if (lastSaved == null) {
			//Use Default, so ECLIPSE_GERRIT_DEFAULT
			lastSaved = ECLIPSE_GERRIT_DEFAULT;
		}
		if (!lastSaved.endsWith(SLASH)) {
			lastSaved = lastSaved.concat(SLASH);
		}
		sb.append(lastSaved);
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
	 * Return the mapping of the available Gerrit server used in the user workspace
	 *
	 * @return Map<Repository, String>
	 */
	public List<GerritServerInformation> getGerritMapping() {
		if (fGerritServer == null) {
			fGerritServer = new ArrayList<GerritServerInformation>();
		}

		fGerritServer.clear();
		IPreferenceStore store = GerritPlugin.getDefault().getPreferenceStore();
		String allInfo = store.getString(PreferenceConstants.P_GERRIT_LISTS);
		//Create the parseString and also adjust the other method

		fGerritServer = createGerritServerInfo(allInfo);
		return fGerritServer;

	}

	public List<String> getAllServerName() {
		IPreferenceStore store = GerritPlugin.getDefault().getPreferenceStore();
		String allInfo = store.getString(PreferenceConstants.P_GERRIT_LISTS);
		//Create the parseString and also adjust the other method

		List<GerritServerInformation> list = createGerritServerInfo(allInfo);

		List<String> allInfoList = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			allInfoList.add(list.get(i).getAllInfo());
		}

		return allInfoList;
	}

	public List<GerritServerInformation> createGerritServerInfo(String stringList) {

		ISecurePreferences securePref = SecurePreferencesFactory.getDefault();
		String[] list = stringList.split(PreferenceConstants.LIST_SEPARATOR);
		fGerritServer.clear();
		GerritServerInformation gerritInfo = null;
		if (list.length > 0 && !list[0].isEmpty()) {
			for (int i = 0; i < list.length; i = i + 3) {
				String serverName = ""; //$NON-NLS-1$
				boolean selfSigned = false;

				if ((i + 1) < list.length) {
					serverName = list[i + 1];
				}

				if ((i + 2) < list.length) {
					String s;
					s = list[i + 2];
					boolean b = Boolean.valueOf(s);
					if (b) {
						selfSigned = b;
					}
				}

				try {
					gerritInfo = new GerritServerInformation(list[i], serverName);
					gerritInfo.setSelfSigned(selfSigned);
				} catch (URISyntaxException e1) {
					EGerritCorePlugin.logError(e1.getMessage());
				}
				if (gerritInfo != null) {
					ISecurePreferences serverPreference = securePref
							.node(EncodingUtils.encodeSlashes(getPreferenceKey(gerritInfo)));
					String user;
					try {
						user = serverPreference.get(GerritServerInformation.KEY_USER, ""); //$NON-NLS-1$
						String password = serverPreference.get(GerritServerInformation.KEY_PASSWORD, ""); //$NON-NLS-1$
						logger.debug("GerritServerUtility.createGerritServerInfo() URL: " //$NON-NLS-1$
								+ gerritInfo.getServerURI() + "\n\t user: " + user); //$NON-NLS-1$
						gerritInfo.setUserName(user);
						gerritInfo.setPassword(password);
					} catch (StorageException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					fGerritServer.add(gerritInfo);

				}
			}
		}

		return fGerritServer;
	}

	/**
	 * Save the selected Gerrit server URL
	 *
	 * @param aURL
	 * @return Boolean
	 */
	public Boolean saveLastGerritServer(String aURL) {
		Boolean ok = true;
		File file = getLastGerritFile(LAST_GERRIT_FILE);
		try {
			FileWriter fw = new FileWriter(file);
			BufferedWriter out = new BufferedWriter(fw);
			out.write(aURL);
			out.close();
		} catch (IOException e1) {
			e1.printStackTrace();
			ok = false;
		}

		return ok;
	}

	/**
	 * Return the last selected Gerrit server used
	 *
	 * @return String
	 */
	public String getLastSavedGerritServer() {
		String lastGerritURL = null;
		File file = getLastGerritFile(LAST_GERRIT_FILE);
		if (file != null) {
			try {
				FileReader fr = new FileReader(file);
				BufferedReader in = new BufferedReader(fr);
				lastGerritURL = in.readLine();
				in.close();
			} catch (IOException e1) {
				//When there is no file,
				//e1.printStackTrace();
			}
		}
		return lastGerritURL;
	}

	/**
	 * Get the Gerrit URL based on the provided string
	 *
	 * @param Menu
	 *            string aSt
	 * @return URL as a string
	 */
	public String getMenuSelectionURL(String aSt) {
		String uriStr = null;
		fGerritServer = getGerritMapping();
		if (!fGerritServer.isEmpty()) {
			for (GerritServerInformation key : fGerritServer) {
				//If there is no server name, then we test for the server URI
				if (key.getName().equals(aSt) || key.getServerURI().equals(aSt)) {
					uriStr = key.getServerURI();

					logger.debug("Map Key: " + key.getName() + "\t URL: " //$NON-NLS-1$//$NON-NLS-2$
							+ key.getServerURI());
					return uriStr;
				}
			}
		}

		return uriStr;
	}

	/**
	 * Get the Gerrit Server Repository
	 *
	 * @param string
	 *            aSt
	 * @return GerritServerInformation
	 */
	public GerritServerInformation getServerRepo(String aStURL) {
		fGerritServer = getGerritMapping();
		if (aStURL != null && !fGerritServer.isEmpty()) {
			for (GerritServerInformation key : fGerritServer) {
				if (key.getServerURI().equals(aStURL)) {

					logger.debug("Key label : " + key.getName() + "\t URL: " //$NON-NLS-1$ //$NON-NLS-2$
							+ key.getServerURI());
					return key;
				}
			}
		}

		return null;
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
				e.printStackTrace();
			}
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
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
		} catch (IOException e1) {
			e1.printStackTrace();
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
		LinkedHashSet<String> lastCommands = new LinkedHashSet<String>();
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
			} catch (IOException e1) {
				//When there is no file,
				//e1.printStackTrace();
			}
		}
		return lastCommands;
	}

	/**
	 * Builds a preference key from a server information @ return String the constructed key
	 */
	public static String getPreferenceKey(GerritServerInformation serverInfo) {
		return GerritPlugin.PLUGIN_ID + '/' + serverInfo.getServerURI() + serverInfo.getName();
	}
}
