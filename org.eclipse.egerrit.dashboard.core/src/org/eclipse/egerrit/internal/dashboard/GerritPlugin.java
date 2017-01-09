/*******************************************************************************
 * Copyright (c) 2013 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jacques Bouthillier - Initial Implementation of the plug-in
 ******************************************************************************/

package org.eclipse.egerrit.internal.dashboard;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

/**
 * This class implements the Dashboard-Gerrit.
 *
 * @since 1.0
 */

/**
 * The activator class controls the plug-in life cycle
 */
public class GerritPlugin extends Plugin {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field PLUGIN_ID. (value is ""org.eclipse.egerrit.dashboard.core"")
	 */
	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.egerrit.dashboard.core"; //$NON-NLS-1$

	/**
	 * Field DASHBOARD_VERSION_QUALIFIER. (value is ""qualifier"")
	 */
	private static final String DASHBOARD_VERSION_QUALIFIER = "qualifier"; //$NON-NLS-1$

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	// The shared instance
	private static GerritPlugin Fplugin;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * The constructor
	 */
	public GerritPlugin() {
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method start.
	 *
	 * @param aContext
	 *            BundleContext
	 * @throws Exception
	 * @see org.osgi.framework.BundleActivator#start(BundleContext)
	 */
	@Override
	public void start(BundleContext aContext) throws Exception {
		super.start(aContext);
		Fplugin = this;
	}

	/**
	 * Method stop.
	 *
	 * @param aContext
	 *            BundleContext
	 * @throws Exception
	 * @see org.osgi.framework.BundleActivator#stop(BundleContext)
	 */
	@Override
	public void stop(BundleContext aContext) throws Exception {
		Fplugin = null;
		super.stop(aContext);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static GerritPlugin getDefault() {
		return Fplugin;
	}
}
