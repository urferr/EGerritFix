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
	// Member variables
	// ------------------------------------------------------------------------

	// The shared instance
	private static GerritPlugin fPlugin;

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
		fPlugin = this;
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
		fPlugin = null;
		super.stop(aContext);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static GerritPlugin getDefault() {
		return fPlugin;
	}
}
