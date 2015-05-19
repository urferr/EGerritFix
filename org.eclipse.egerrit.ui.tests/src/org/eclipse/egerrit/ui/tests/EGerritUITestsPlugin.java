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

package org.eclipse.egerrit.ui.tests;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 *
 * @since 1.0
 */
public class EGerritUITestsPlugin extends AbstractUIPlugin {

	// ------------------------------------------------------------------------
	// Attributes
	// ------------------------------------------------------------------------

	/** The plug-in ID */
	public static final String PLUGIN_ID = "org.eclipse.egerrit.ui.tests"; //$NON-NLS-1$

	// The shared instance
	private static EGerritUITestsPlugin fPlugin;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * The constructor
	 */
	public EGerritUITestsPlugin() {
		setDefault(this);
	}

	// ------------------------------------------------------------------------
	// Accessors
	// ------------------------------------------------------------------------

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static EGerritUITestsPlugin getDefault() {
		return fPlugin;
	}

	// Sets plug-in instance
	private static void setDefault(EGerritUITestsPlugin plugin) {
		fPlugin = plugin;
	}

	// ------------------------------------------------------------------------
	// Plugin
	// ------------------------------------------------------------------------

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		setDefault(this);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		fPlugin = null;
		super.stop(context);
	}

}
