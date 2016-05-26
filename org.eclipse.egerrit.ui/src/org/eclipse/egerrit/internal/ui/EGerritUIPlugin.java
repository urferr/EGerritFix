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

package org.eclipse.egerrit.internal.ui;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 *
 * @since 1.0
 */

public class EGerritUIPlugin extends AbstractUIPlugin {

	/** The plug-in ID */
	public static final String PLUGIN_ID = "org.eclipse.egerrit.ui"; //$NON-NLS-1$

	private static EGerritUIPlugin fPlugin;

	/**
	 * The constructor
	 */
	public EGerritUIPlugin() {
		setDefault(this);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static EGerritUIPlugin getDefault() {
		return fPlugin;
	}

	// Sets plug-in instance
	private static void setDefault(EGerritUIPlugin plugin) {
		fPlugin = plugin;
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		setDefault(this);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		fPlugin = null;
		super.stop(context);
	}

	/**
	 * Returns an image descriptor for the image file at the given plug-in relative path
	 *
	 * @param aPth
	 *            the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String aPath) {
		return imageDescriptorFromPlugin(PLUGIN_ID, aPath);
	}

}
