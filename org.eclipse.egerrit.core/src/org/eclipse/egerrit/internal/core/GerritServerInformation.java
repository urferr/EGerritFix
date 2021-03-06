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
package org.eclipse.egerrit.internal.core;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.utils.URIBuilder;
import org.eclipse.equinox.security.storage.ISecurePreferences;
import org.eclipse.equinox.security.storage.SecurePreferencesFactory;
import org.eclipse.equinox.security.storage.StorageException;

import com.google.gson.annotations.Expose;

/**
 * Holds information that represents a Gerrit Server.
 *
 * @since 1.0
 */
public class GerritServerInformation implements Cloneable {
	private static final int DEFAULT_PORT = 29418;

	private static final String DEFAULT_SCHEME = "http"; //$NON-NLS-1$

	@Expose
	private String fServerURI;

	@Expose
	private String fServerScheme;

	@Expose
	private String fHostId;

	@Expose
	private String fServerPath;

	@Expose
	private String fServerName;

	@Expose
	private int fServerPort = DEFAULT_PORT;

	@Expose
	private String fUserName;

	@Expose
	private boolean fPasswordProvided = false;

	private boolean fPasswordChanged = false;

	private String fPassword;

	private static final String KEY_PASSWORD = "password"; //$NON-NLS-1$

	private boolean fSelfSigned = false;

	/**
	 * Constructor of this class
	 * 
	 * @param String
	 *            serverURI
	 * @param String
	 *            serverName
	 * @throws URISyntaxException
	 */
	public GerritServerInformation(String serverURI, String serverName) throws URISyntaxException {
		URI uri = null;
		if (serverURI != null) {
			uri = new URI(serverURI);
		} else {
			uri = new URI(""); //$NON-NLS-1$
		}
		if (uri != null) {
			setSeverInfo(uri);
		}
		setServerURI(serverURI);
		setServerName(serverName != null ? serverName.trim() : ""); //$NON-NLS-1$
	}

	/**
	 * Set the server URI
	 *
	 * @param String
	 *            serverURI
	 */
	public void setServerURI(String serverURI) {
		this.fServerURI = serverURI != null ? serverURI.trim() : ""; //$NON-NLS-1$
		this.fServerURI = removeTrailingSlash(this.fServerURI);
	}

	private String removeTrailingSlash(String server) {
		if (server != null && server.endsWith("/")) { //$NON-NLS-1$
			return server.substring(0, server.length() - 1);
		}
		return server;
	}

	/**
	 * Return all the server information
	 *
	 * @return GerritServerInformation
	 */
	public GerritServerInformation getServerInfo() {
		return this;
	}

	/**
	 * Return the server port
	 *
	 * @return int
	 */
	public int getDefaultPort() {
		return DEFAULT_PORT;
	}

	/**
	 * Return the server scheme
	 *
	 * @return String
	 */
	public String getScheme() {
		return fServerScheme != null ? fServerScheme : DEFAULT_SCHEME;
	}

	/**
	 * Return the host id
	 *
	 * @return String
	 */
	public String getHostId() {
		return fHostId != null ? fHostId : ""; //$NON-NLS-1$
	}

	/**
	 * Return the server path
	 *
	 * @return String
	 */
	public String getPath() {
		return fServerPath != null ? fServerPath : ""; //$NON-NLS-1$
	}

	/**
	 * Return the server name
	 *
	 * @return String
	 */
	public String getName() {
		return fServerName != null ? fServerName : ""; //$NON-NLS-1$
	}

	/**
	 * Return the server URI
	 *
	 * @return String
	 */
	public String getServerURI() {
		return fServerURI != null ? fServerURI : ""; //$NON-NLS-1$
	}

	/**
	 * Return the server port number.
	 *
	 * @return int
	 */
	public int getPort() {
		return fServerPort;
	}

	/**
	 * Set the server scheme (Http, https,..)
	 *
	 * @param String
	 *            text
	 */
	public void setScheme(String text) {
		fServerScheme = text;
		if (fServerScheme != null) {
			getURI();
		}
	}

	/**
	 * Set the server host name
	 *
	 * @param String
	 *            serverId
	 */
	public void setHostId(String serverId) {
		this.fHostId = serverId != null ? serverId.trim() : ""; //$NON-NLS-1$
		getURI();
	}

	/**
	 * Set the server port
	 *
	 * @param int
	 *            port
	 */
	public void setPort(int port) {
		fServerPort = port;
		getURI();
	}

	/**
	 * Set the server path.
	 *
	 * @param String
	 *            text
	 */
	public void setPath(String text) {
		fServerPath = text;
		getURI();
	}

	public void setServerName(String text) {
		fServerName = text;
	}

	/**
	 * Set the user name for this server
	 *
	 * @param String
	 *            userName
	 */
	public void setUserName(String userName) {
		this.fUserName = userName != null ? userName.trim() : ""; //$NON-NLS-1$
	}

	/**
	 * Set the user password for this server
	 *
	 * @param String
	 *            password
	 */
	public void setPassword(String password) {
		this.fPassword = password != null ? password.trim() : ""; //$NON-NLS-1$
		if (this.fPassword.length() > 0) {
			fPasswordProvided = true;
			fPasswordChanged = true;
		}
	}

	/**
	 * Return the user name for this server
	 *
	 * @return String
	 */
	public String getUserName() {
		return fUserName != null ? fUserName : ""; //$NON-NLS-1$
	}

	/**
	 * Set the server information
	 *
	 * @param URI
	 *            uri
	 */
	public void setSeverInfo(URI uri) {
		setScheme(uri.getScheme());
		setHostId(uri.getHost());
		setPort(uri.getPort());
		setPath(removeTrailingSlash(uri.getPath()));
		if (uri.getUserInfo() != null && getUserName().isEmpty()) {
			setUserName(uri.getUserInfo());
		}
	}

	/**
	 * Set the flag for self signing
	 *
	 * @param boolean
	 *            signed
	 */
	public void setSelfSigned(boolean signed) {
		fSelfSigned = signed;
	}

	/**
	 * Verify if the the flag for signing is set
	 *
	 * @return boolean
	 */
	public boolean getSelfSigned() {
		return fSelfSigned;
	}

	private URI getURI() {
		try {
			URI uri = new URIBuilder().setScheme(getScheme())
					.setHost(getHostId())
					.setPort(getPort())
					.setPath(getPath())
					.build();
			if (uri.getHost() != null) {
				setServerURI(uri.toString());
				return uri;
			} else {
				return null;
			}
		} catch (URISyntaxException e) {
			// Do not report the error yet
		}
		return null;
	}

	/**
	 * Test to see if the server has a valid URI
	 *
	 * @return boolean
	 * @throws URISyntaxException
	 */
	public boolean isValid() throws URISyntaxException {
		URI uri = new URI(getServerURI());
		return new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(), uri.getPort(), uri.getPath(), uri.getQuery(),
				uri.getFragment()).equals(uri);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fServerName == null) ? 0 : fServerName.hashCode());
		result = prime * result + ((fServerURI == null) ? 0 : fServerURI.hashCode());
		result = prime * result + ((fUserName == null) ? 0 : fUserName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		GerritServerInformation other = (GerritServerInformation) obj;
		if (fServerName == null) {
			if (other.fServerName != null) {
				return false;
			}
		} else if (!fServerName.equals(other.fServerName)) {
			return false;
		}
		if (fServerURI == null) {
			if (other.fServerURI != null) {
				return false;
			}
		} else if (!fServerURI.equals(other.fServerURI)) {
			return false;
		}
		if (fUserName == null) {
			if (other.fUserName != null) {
				return false;
			}
		} else if (!fUserName.equals(other.fUserName)) {
			return false;
		}
		return true;
	}

	/**
	 * Return the password
	 *
	 * @return String
	 */
	public String getPassword() {
		if (getUserName().isEmpty()) {
			return null;
		}
		if (!isPasswordProvided()) {
			return null;
		}
		if (!internalGetPassword().isEmpty()) {
			return internalGetPassword();
		}

		ISecurePreferences securePref = SecurePreferencesFactory.getDefault();
		ISecurePreferences serverPreference = securePref
				.node(org.eclipse.equinox.security.storage.EncodingUtils.encodeSlashes(getPreferenceKey()));
		try {
			return serverPreference.get(GerritServerInformation.KEY_PASSWORD, internalGetPassword());
		} catch (StorageException e) {
			return null;
		}
	}

	private String internalGetPassword() {
		return fPassword != null ? fPassword : ""; //$NON-NLS-1$
	}

	void persistPassword() {
		if (!(isPasswordProvided() && fPasswordChanged)) {
			return;
		}
		ISecurePreferences securePref = SecurePreferencesFactory.getDefault();
		ISecurePreferences serverPreference = securePref
				.node(org.eclipse.equinox.security.storage.EncodingUtils.encodeSlashes(getPreferenceKey()));
		try {
			serverPreference.put(GerritServerInformation.KEY_PASSWORD, getPassword(), true);
			serverPreference.flush();
			fPasswordChanged = false;
		} catch (StorageException e) {
			EGerritCorePlugin.logError(e.getMessage());
		} catch (IOException e) {
			EGerritCorePlugin.logError(e.getMessage());
		}
	}

	private String getPreferenceKey() {
		return EGerritCorePlugin.PLUGIN_ID + '/' + getServerURI() + getName();
	}

	/**
	 * Verify if the password has been provided
	 *
	 * @return boolean
	 */
	public boolean isPasswordProvided() {
		return fPasswordProvided;
	}

	/**
	 * Verify if the password has changed
	 *
	 * @return boolean
	 */
	public boolean isPasswordChanged() {
		return fPasswordChanged;
	}

	/**
	 * Returns a boolean indicating if a user is provided for this connection
	 */
	public boolean isAnonymous() {
		return fUserName == null || fUserName.length() == 0;
	}

	@Override
	public GerritServerInformation clone() {
		try {
			GerritServerInformation clone;
			clone = new GerritServerInformation(this.fServerURI, this.fServerName);
			clone.fServerURI = fServerURI;
			clone.fServerScheme = fServerScheme;
			clone.fHostId = fHostId;
			clone.fServerPath = fServerPath;
			clone.fServerName = fServerName;
			clone.fServerPort = fServerPort;
			clone.fUserName = fUserName;
			clone.fPasswordProvided = fPasswordProvided;
			clone.fPasswordChanged = fPasswordChanged;
			clone.fPassword = fPassword;
			clone.fSelfSigned = fSelfSigned;
			return clone;
		} catch (URISyntaxException e) {
			//Can't happen since we are starting from a proper instance
		}
		throw new IllegalStateException("Failed to clone " + this); //$NON-NLS-1$
	}
}
