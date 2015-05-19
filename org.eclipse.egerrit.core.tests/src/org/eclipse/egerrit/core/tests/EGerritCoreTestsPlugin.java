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

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 * 
 * @since 1.0
 */
@SuppressWarnings("nls")
public class EGerritCoreTestsPlugin extends Plugin {

	// ------------------------------------------------------------------------
	// Attributes
	// ------------------------------------------------------------------------

	/**
	 * The plug-in ID
	 */
	public static final String PLUGIN_ID = "org.eclipse.egerrit.core.tests";

	// The shared instance
	private static EGerritCoreTestsPlugin fPlugin;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * The constructor
	 */
	public EGerritCoreTestsPlugin() {
		setDefault(this);
	}

	// ------------------------------------------------------------------------
	// Accessors
	// ------------------------------------------------------------------------

	/**
	 * @return the shared instance
	 */
	public static EGerritCoreTestsPlugin getDefault() {
		return fPlugin;
	}

	// Sets plug-in instance
	private static void setDefault(EGerritCoreTestsPlugin plugin) {
		fPlugin = plugin;
	}

	// ------------------------------------------------------------------------
	// Plugin
	// ------------------------------------------------------------------------

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugin#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		setDefault(this);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		setDefault(null);
		super.stop(context);
	}

}
