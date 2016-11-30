/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Francois Chouinard - Initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.core.tests;

/**
 * Common stuff for the test cases
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class Common {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	static String initHost() {
		String host = System.getProperty("EGerritGerritTestServerHost");
		if (host == null || host.trim().length() == 0) {
			return "localhost";
		}
		return host;
	}

	static int initPort() {
		String port = System.getProperty("EGerritGerritTestServerPort");
		if (port == null || port.trim().length() == 0) {
			return 28112;
		}
		return Integer.valueOf(port);
	}

	public static final String SCHEME = "http";

	public static final String HOST = initHost();

	public static final int PORT = initPort();

	public static final String PATH = "";

	public static final String TEST_PROJECT = "egerrit/test";

	public static final String USER = "admin";

	public static final String PASSWORD = "egerritTest";

	public static final String EMAIL = "admin@localhost";

	public static final String GERRIT_VERSION = "2.11.5";

	public static final String CHANGES_PATH = PATH + "/changes/";
}
