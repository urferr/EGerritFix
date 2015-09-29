/*******************************************************************************
 * Copyright (c) 2014 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jacques Bouthillier - Initial implementation
 *   Marc-Andre Laperle - Add Topic to dashboard
 ******************************************************************************/
package org.eclipse.egerrit.dashboard;

import org.eclipse.osgi.util.NLS;

/**
 * This class implements the internalization of the strings.
 *
 * @since 1.0
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.egerrit.dashboard.messages"; //$NON-NLS-1$

	public static String GerritPlugin_started;

	public static String GerritPlugin_stopped;

	public static String GerritPlugin_Version;

	public static String GerritTask_branch;

	public static String GerritTask_topic;

	public static String GerritTask_changeID;

	public static String GerritTask_owner;

	public static String GerritTask_project;

	public static String GerritTask_shortID;

	public static String GerritTask_star;

	public static String GerritTask_subject;

	public static String GerritTask_status;

	public static String GerritTask_taskID;

	public static String GerritTask_updated;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
