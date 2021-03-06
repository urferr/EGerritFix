/*******************************************************************************
 * Copyright (c) 2014-2016 Ericsson AB and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Jacques Bouthillier - initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.internal.dashboard.ui.views;

import org.eclipse.osgi.util.NLS;

/**
 * This class implements the internalization of the strings.
 *
 * @since 1.0
 */
class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.egerrit.internal.dashboard.ui.views.messages"; //$NON-NLS-1$

	public static String GerritTableView_confirmOpenExternalPage;

	public static String GerritTableView_openExternalPage;

	public static String GerritTableView_commandMessage;

	public static String GerritTableView_gerritVersion;

	public static String GerritTableView_search;

	public static String GerritTableView_tooltipSearch;

	public static String GerritTableView_totalReview;

	public static String GerritTableView_information;

	public static String GerritTableView_informationAnonymous;

	public static String GerritTableView_tooltipAnonymous;

	public static String GerritTableView_tooltipLoggedOnAs;

	public static String GerritTableView_tooltipInvalid;

	public static String Invalid_Credentials;

	public static String Invalid_server;

	public static String No_Connection;

	public static String Server_connection_401;

	public static String Server_connection_401_title;

	public static String Unsupported_Server_Version;

	public static String Unsupported_server_version;

	public static String Unsupported_server_version_title;

	public static String Welcome_No_Server;

	public static String Welcome_Pick_Server;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
