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

package org.eclipse.egerrit.dashboard.ui.views;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.URIUtil;
import org.eclipse.egerrit.core.GerritServerInformation;

/**
 * Extracts server and/or changeid information out of a URL. The following URLs are supported
 * https://git.eclipse.org/r/, https://git.eclipse.org/r/#/c/38382/,
 * https://git.eclipse.org/r/#/c/38382/2/packages/org.eclipse.epp.package.java.mylyn.feature/build.properties
 */
public class ChangeIdExtractor {
	//The server found
	private GerritServerInformation server = null;

	//The changeId found
	private String changeId = null;

	/**
	 * Create a changeIdExtractor using the specified server address as input Invoking this will cause the server and
	 * change id to be extracted.
	 *
	 * @param serverAddress
	 */
	public ChangeIdExtractor(String serverAddress) {
		if (!isURL(serverAddress)) {
			return;
		}
		extractServer(serverAddress);
		if (server != null) {
			extractChangeId(serverAddress);
		}
	}

	private void extractChangeId(String serverAddress) {
		final String CHANGE_STRING = "/#/c/"; //$NON-NLS-1$
		Pattern pattern = Pattern.compile(server.getServerURI() + CHANGE_STRING + "([0-9]+).*"); //$NON-NLS-1$
		Matcher matcher = pattern.matcher(serverAddress);
		if (matcher.matches()) {
			changeId = matcher.group(1);
		} else { //Handle the second format found when Hudson put information in message
			if (serverAddress.endsWith("/")) {
				//Remove the "/" at the end if it exists
				serverAddress = serverAddress.substring(0, serverAddress.length() - 1);
			}
			int lastSlashIndex = serverAddress.lastIndexOf('/') + 1;
			changeId = serverAddress.substring(lastSlashIndex);
		}
	}

	private boolean isURL(String text) {
		try {
			URI uri = URIUtil.fromString(text);
			if (uri.getScheme() == null || uri.getHost() == null) {
				return false;
			}
		} catch (URISyntaxException e) {
			return false;
		}
		return true;
	}

	/**
	 * Returns a gerrit server information object representing the server that has been found, or null if none.
	 *
	 * @return a {@link GerritServerInformation}
	 */
	public GerritServerInformation getServer() {
		return server;
	}

	/**
	 * Return the change id extracted or null if none had been provided
	 *
	 * @return a string representing the change id
	 */
	public String getChangeId() {
		return changeId;
	}

	private void extractServer(String serverAddress) {
		int sharpSign = serverAddress.indexOf('#');
		if (sharpSign != -1) {
			serverAddress = serverAddress.substring(0, sharpSign);
		} else {
			//Handle the second format found when Hudson put information in message
			if (serverAddress.endsWith("/")) {
				//Remove the "/" at the end if it exists
				serverAddress = serverAddress.substring(0, serverAddress.length() - 1);
			}
			int lastSlashIndex = serverAddress.lastIndexOf('/') + 1;
			serverAddress = serverAddress.substring(0, lastSlashIndex);
		}

		try {
			server = new GerritServerInformation(serverAddress, serverAddress);
			return;
		} catch (URISyntaxException e) {
			//Ignore because by that time we will have already checked it is a valid URI
		}
	}
}
