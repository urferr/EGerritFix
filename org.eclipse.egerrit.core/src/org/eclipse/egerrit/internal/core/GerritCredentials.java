/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Francois Chouinard - Initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.internal.core;

import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;

/**
 * This class handle the credentials needed for the EGerrit project.
 *
 * @since 1.0
 */
public class GerritCredentials {

	private Credentials fCredentials = null;

	private boolean fIsOpenIdAuthenticated = false;

	private String fOpenIdProvider = null;

	private boolean fIsHttpAuthenticated = false;

	private Credentials fHttpCredentials = null;

	// ------------------------------------------------------------------------
	//
	// ------------------------------------------------------------------------

	public GerritCredentials() {
	}

	public GerritCredentials(String user, String pwd) {
		setGerritCredentials(user, pwd);
	}

	public Credentials getCredentials() {
		return getGerritCredentials();
	}

	public String getUsername() {
		String name = null;
		if (fCredentials != null) {
			name = fCredentials.getUserPrincipal().getName();
		}
		return name;
	}

	public String getPassword() {
		String password = null;
		if (fCredentials != null) {
			password = fCredentials.getPassword();
		}
		return password;
	}

	// ------------------------------------------------------------------------
	// Gerrit credentials
	// ------------------------------------------------------------------------

	public void setGerritCredentials(String user, String pwd) {
		if (user != null) {
			fCredentials = new UsernamePasswordCredentials(user, pwd);
		}
	}

	public Credentials getGerritCredentials() {
		return fCredentials;
	}

	// ------------------------------------------------------------------------
	// OpenId handling
	// ------------------------------------------------------------------------

	public boolean isOpenIdAuthenticated() {
		return fIsOpenIdAuthenticated;
	}

	public void setOpenIdProvider(String provider) {
		fIsOpenIdAuthenticated = (provider != null);
		if (fIsOpenIdAuthenticated) {
			fOpenIdProvider = provider;
		}
	}

	public String getOpenIdProvider() {
		return fOpenIdProvider;
	}

	// ------------------------------------------------------------------------
	// HTTP credentials
	// ------------------------------------------------------------------------

	public boolean isHttpAuthenticated() {
		return fIsHttpAuthenticated;
	}

	public void setHttpCredentials(String user, String pwd) {
		fIsHttpAuthenticated = (user != null);
		if (fIsHttpAuthenticated) {
			fHttpCredentials = new UsernamePasswordCredentials(user, pwd);
		}
	}

	public Credentials getHttpCredentials() {
		return fHttpCredentials;
	}

}
