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

package org.eclipse.egerrit.internal.dashboard.ui.utils;

import org.eclipse.osgi.util.NLS;

/**
 * This class implements the internalization of the strings.
 *
 * @since 1.0
 */
class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.egerrit.internal.dashboard.ui.utils.messages"; //$NON-NLS-1$

	public static String SelectionDialog_shellText;

	public static String SelectionDialog_selectTitle;

	public static String UIUtils_configureMessage;

	public static String UIUtils_dashboardInfo;

	public static String UIUtils_dashboardInformation;

	public static String UIUtils_dontShowAgain;

	public static String UIUtils_methodNotReady;

	public static String UIUtils_notImplemented;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
