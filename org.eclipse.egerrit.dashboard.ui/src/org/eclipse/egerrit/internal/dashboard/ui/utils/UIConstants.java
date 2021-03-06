/*******************************************************************************
 * Copyright (c) 2013 Ericsson AB and others.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at>
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jacques Bouthillier - Add some definitions for Review Gerrit Dashboard project
 *
 ******************************************************************************/
package org.eclipse.egerrit.internal.dashboard.ui.utils;

/**
 * This class defines various constants used in the Gerrit Dashboard UI plugin.
 *
 * @since 1.0
 */
public class UIConstants {

	public static final String SELECT_SERVER_COMMAND_ID_PARAM = "org.eclipse.egerrit.dashboard.ui.selectCurrentGerrit.serverId"; //$NON-NLS-1$

	public static final String SELECT_SERVER_COMMAND_ID = "org.eclipse.egerrit.dashboard.ui.selectCurrentGerrit"; //$NON-NLS-1$

	public static final String REPLY_COMMAND_VOTE_ID = "org.eclipse.egerrit.dashboard.ui.vote"; //$NON-NLS-1$

	public static final String REPLY_COMMAND_ID_LABEL_PARAM = "org.eclipse.egerrit.dashboard.ui.vote.label"; //$NON-NLS-1$

	public static final String REPLY_COMMAND_ID_VALUE_PARAM = "org.eclipse.egerrit.dashboard.ui.vote.value"; //$NON-NLS-1$

	/**
	 * The default constructor. Do not allow to build an object of this class
	 */
	private UIConstants() {
	}

}
