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

import static org.junit.Assert.assertEquals;

import org.eclipse.egerrit.internal.ui.EGerritUIPlugin;
import org.junit.Test;

/**
 * Test the EGerrit UI plug-in activator
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class EGerritUIPluginTest {

	// ------------------------------------------------------------------------
	// Attributes
	// ------------------------------------------------------------------------

	// The plug-in under test
	private static final EGerritUIPlugin fPlugin = new EGerritUIPlugin();

	// ------------------------------------------------------------------------
	// Test cases
	// ------------------------------------------------------------------------

	/**
	 * Test the plugin ID.
	 */
	@Test
	public void testEGerritUIPluginId() {
		assertEquals("Plugin ID", "org.eclipse.egerrit.ui", EGerritUIPlugin.PLUGIN_ID);
	}

	/**
	 * Test the getDefault() static method.
	 */
	@Test
	public void testGetDefault() {
		EGerritUIPlugin plugin = EGerritUIPlugin.getDefault();
		assertEquals("getDefault()", plugin, fPlugin);
	}

}
