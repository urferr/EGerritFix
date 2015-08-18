/*******************************************************************************
 * Copyright (c) 2014 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jacques Bouthillier - Initial Implementation
 ******************************************************************************/
package org.eclipse.egerrit.dashboard.preferences;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.utils.URIBuilder;

/**
 * Holds information that represents a Gerrit Server.
 *
 * @since 1.0
 */
public class GerritServerInformation {
	private static final int DEFAULT_PORT = 29418;

	private boolean fSkipURIFormat = false;

	private static final String DEFAULT_SCHEME = "http"; //$NON-NLS-1$

	private String fServerURI;

	private String fServerScheme;

	private String fHostId;

	private String fServerPath;

	private String fServerName;

	private int fServerPort = DEFAULT_PORT;

	private String fUserName;

	private String fPassword;

	public static final String KEY_USER = "user"; //$NON-NLS-1$

	public static final String KEY_PASSWORD = "password"; //$NON-NLS-1$

	private boolean fSelfSigned = false;

	public GerritServerInformation(String serverURI, String serverName) throws URISyntaxException {
		URI uri = null;
		try {
			if (serverURI != null) {
				uri = new URI(serverURI);
			} else {
				uri = new URI(""); //$NON-NLS-1$
			}
		} catch (URISyntaxException e) {
			throw e;
		}
		if (uri != null) {

			setSeverInfo(uri);
		}
		setServerURI(serverURI);
		setServerName(serverName != null ? serverName.trim() : ""); //$NON-NLS-1$
	}

	private void setSkipReformat(Boolean b) {
		fSkipURIFormat = b;
	}

	private Boolean getSkipReformat() {
		return fSkipURIFormat;
	}

	public void setServerURI(String serverURI) {
		this.fServerURI = serverURI != null ? serverURI.trim() : ""; //$NON-NLS-1$
	}

	public GerritServerInformation getServerInfo() {
		return this;
	}

	public int getDefaultPort() {
		return DEFAULT_PORT;
	}

	public String getScheme() {
		return fServerScheme != null ? fServerScheme : DEFAULT_SCHEME;
	}

	public String getHostId() {
		return fHostId != null ? fHostId : ""; //$NON-NLS-1$
	}

	public String getPath() {
		return fServerPath != null ? fServerPath : ""; //$NON-NLS-1$
	}

	public String getName() {
		return fServerName != null ? fServerName : ""; //$NON-NLS-1$
	}

	public String getServerURI() {
		return fServerURI != null ? fServerURI : ""; //$NON-NLS-1$
	}

	public int getPort() {
		return fServerPort;
	}

	public String getAllInfo() {
		return fServerURI + PreferenceConstants.LIST_SEPARATOR + fServerName + PreferenceConstants.LIST_SEPARATOR
				+ getSelfSigned();
	}

	public void setScheme(String text) {
		fServerScheme = text;
		if (fServerScheme != null) {
			getURI();
		}
	}

	public void setHostId(String serverId) {
		this.fHostId = serverId != null ? serverId.trim() : ""; //$NON-NLS-1$
		getURI();
	}

	public void setPort(int port) {
		fServerPort = port;
		getURI();
	}

	public void setPath(String text) {
		fServerPath = text;
		getURI();
	}

	public void setServerName(String text) {
		fServerName = text;
	}

	public void setUserName(String userName) {
		this.fUserName = userName != null ? userName.trim() : ""; //$NON-NLS-1$
	}

	public void setPassword(String password) {
		this.fPassword = password != null ? password.trim() : ""; //$NON-NLS-1$
	}

	public String getUserName() {
		return fUserName != null ? fUserName : ""; //$NON-NLS-1$
	}

	public String getPassword() {
		return fPassword != null ? fPassword : ""; //$NON-NLS-1$
	}

	public void setSeverInfo(URI uri) {
		//Just set the data field, no need to re-compute the URI
		setSkipReformat(true);

		setScheme(uri.getScheme());
		setHostId(uri.getHost());
		setPort(uri.getPort());
		setPath(uri.getPath());
		setUserName(uri.getUserInfo());

		//RE-initiate the URI build
		setSkipReformat(false);
	}

	public void setSelfSigned(boolean signed) {
		fSelfSigned = signed;
	}

	public boolean getSelfSigned() {
		return fSelfSigned;
	}

	public URI getURI() {

		if (getSkipReformat()) {
			return null;
		}
		try {
			URI uri = new URIBuilder().setScheme(getScheme())
					.setHost(getHostId())
					.setPort(getPort())
					.setPath(getPath())
					.build();
			setServerURI(uri.toString());
			return uri;
		} catch (URISyntaxException e) {
			// Do not report the error yet
		}
		return null;
	}

	public boolean isValid() throws URISyntaxException {
		URI uri = new URI(getServerURI());
		return new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(), uri.getPort(), uri.getPath(), uri.getQuery(),
				uri.getFragment()).equals(uri);
	}
}
