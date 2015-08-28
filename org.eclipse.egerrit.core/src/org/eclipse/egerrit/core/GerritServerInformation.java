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
package org.eclipse.egerrit.core;

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
public class GerritServerInformation {
	private static final int DEFAULT_PORT = 29418;

	private boolean fSkipURIFormat = false;

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

	private static final String KEY_USER = "user"; //$NON-NLS-1$

	private static final String KEY_PASSWORD = "password"; //$NON-NLS-1$

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
		return fServerURI + ";" + fServerName + ";" + getSelfSigned();
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
		if (this.fPassword.length() > 0) {
			fPasswordProvided = true;
			fPasswordChanged = true;
		}
	}

	public String getUserName() {
		return fUserName != null ? fUserName : ""; //$NON-NLS-1$
	}

	public void setSeverInfo(URI uri) {
		//Just set the data field, no need to re-compute the URI
		setSkipReformat(true);

		setScheme(uri.getScheme());
		setHostId(uri.getHost());
		setPort(uri.getPort());
		setPath(uri.getPath());
		if (uri.getUserInfo() != null && getUserName().isEmpty()) {
			setUserName(uri.getUserInfo());
		}

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

	String internalGetPassword() {
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

	public boolean isPasswordProvided() {
		return fPasswordProvided;
	}

	@Override
	public GerritServerInformation clone() {
		try {
			GerritServerInformation clone;
			clone = new GerritServerInformation(this.fServerURI, this.fServerName);
			clone.fSkipURIFormat = fSkipURIFormat;
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
