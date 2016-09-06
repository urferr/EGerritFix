/*******************************************************************************
 * Copyright (c) 2014 Ericsson AB and others.
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
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.egerrit.internal.dashboard.ui.views.messages"; //$NON-NLS-1$

	public static String GerritTableView_confirmOpenExternalPage;

	public static String GerritTableView_openExternalPage;

	public static String GerritTableView_commandMessage;

	public static String GerritTableView_defineRepository;

	public static String GerritTableView_gerritVersion;

	public static String GerritTableView_missingGitConnector;

	public static String GerritTableView_popupMenu;

	public static String GerritTableView_refreshTable;

	public static String GerritTableView_search;

	public static String GerritTableView_serverNotRead;

	public static String GerritTableView_tooltipSearch;

	public static String GerritTableView_totalReview;

	public static String GerritTableView_warning;

	public static String GerritTableView_warningAnonymous;

	public static String GerritTableView_warningEmptyValue;

	public static String GerritTableView_warningSearchAnonymous;

	public static String GerritTableView_starredName;

	public static String GerritTableView_dashboardUiJob;

	public static String GerritTableView_tooltipAnonymous;

	public static String GerritTableView_tooltipLoggedOnAs;

	public static String GerritTableView_tooltipInvalid;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
