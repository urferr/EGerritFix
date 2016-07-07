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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.eclipse.core.runtime.ILogListener;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.egerrit.internal.core.EGerritCorePlugin;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for {@link org.eclipse.egerrit.internal.core.EGerritCorePlugin}
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class EGerritCorePluginTest implements ILogListener {

	// ------------------------------------------------------------------------
	// Attributes
	// ------------------------------------------------------------------------

	// The plug-in under test
	static final EGerritCorePlugin fPlugin = new EGerritCorePlugin();

	// ------------------------------------------------------------------------
	// ILogListener
	// ------------------------------------------------------------------------

	private String fLoggingPlugin = null;

	private IStatus fStatus = null;

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.core.runtime.ILogListener#logging(org.eclipse.core.runtime.IStatus,
	 * java.lang.String)
	 */
	@Override
	public void logging(IStatus status, String plugin) {
		fLoggingPlugin = plugin;
		fStatus = status;
	}

	// ------------------------------------------------------------------------
	// Helper functions
	// ------------------------------------------------------------------------

	@Before
	public void setUp() throws Exception {
		fPlugin.getLog().addLogListener(this);
	}

	@After
	public void tearDown() throws Exception {
		fPlugin.getLog().removeLogListener(this);
		EGerritCorePlugin.getDefault().start(EGerritCorePlugin.getDefault().getBundle().getBundleContext());
		;
	}

	// ------------------------------------------------------------------------
	// Test cases
	// ------------------------------------------------------------------------

	/**
	 * Test the plugin ID.
	 */
	@Test
	public void testEGerritCorePluginId() {
		// Verify result
		assertEquals("Plugin ID", "org.eclipse.egerrit.core", EGerritCorePlugin.PLUGIN_ID);
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.core.EGerritCorePlugin#getDefault()}
	 */
	@Test
	public void testGetDefault() {

		// Run test
		EGerritCorePlugin plugin = EGerritCorePlugin.getDefault();

		// Verify result
		assertEquals("getDefault()", plugin, fPlugin);
	}

	// ------------------------------------------------------------------------
	// log()
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.core.EGerritCorePlugin#log(org.eclipse.core.runtime.IStatus)}
	 */
	@Test
	public void testLog() {

		// Initialize
		fLoggingPlugin = null;
		fStatus = null;

		// Run test
		EGerritCorePlugin.log(Status.OK_STATUS);

		// Verify result
		assertEquals("Wrong plugin", EGerritCorePlugin.PLUGIN_ID, fLoggingPlugin);
		assertEquals("Wrong status", Status.OK_STATUS, fStatus);
	}

	// ------------------------------------------------------------------------
	// logInfo()
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.core.EGerritCorePlugin#logInfo(java.lang.String)}
	 */
	@Test
	public void testLogInfoString() {

		String MESSAGE = "testLogInfo_String";
		//Build the result value
		String version = EGerritCorePlugin.getBundleVersion();
		StringBuilder ret = new StringBuilder();
		ret.append("Plug-in Version: " + version + "\n");
		ret.append(MESSAGE);

		// Initialize
		fLoggingPlugin = null;
		fStatus = null;

		// Run test
		EGerritCorePlugin.logInfo(MESSAGE);

		// Verify result
		assertEquals("Wrong plugin", EGerritCorePlugin.PLUGIN_ID, fLoggingPlugin);
		assertEquals("Wrong message", ret.toString(), fStatus.getMessage());
		assertEquals("Wrong status", IStatus.INFO, fStatus.getSeverity());
		assertNull("Wrong exception", fStatus.getException());
	}

	/**
	 * Test method for
	 * {@link org.eclipse.egerrit.internal.core.EGerritCorePlugin#logInfo(java.lang.String, java.lang.Throwable)}
	 */
	@Test
	public void testLogInfoStringThrowable() {

		String MESSAGE = "testLogInfo_String_Throwable";
		Exception EXCEPTION = new Exception(MESSAGE);
		//Build the result value
		String version = EGerritCorePlugin.getBundleVersion();
		StringBuilder ret = new StringBuilder();
		ret.append("Plug-in Version: " + version + "\n");
		ret.append(MESSAGE);

		// Initialize
		fLoggingPlugin = null;
		fStatus = null;

		// Run test
		EGerritCorePlugin.logInfo(MESSAGE, EXCEPTION);

		// Verify result
		assertEquals("Wrong plugin", EGerritCorePlugin.PLUGIN_ID, fLoggingPlugin);
		assertEquals("Wrong message", ret.toString(), fStatus.getMessage());
		assertEquals("Wrong status", IStatus.INFO, fStatus.getSeverity());
		assertEquals("Wrong exception", EXCEPTION, fStatus.getException());
	}

	// ------------------------------------------------------------------------
	// logWarning()
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.core.EGerritCorePlugin#logWarning(java.lang.String)}
	 */
	@Test
	public void testLogWarningString() {

		String MESSAGE = "testLogWarning_String";
		//Build the result value
		String version = EGerritCorePlugin.getBundleVersion();
		StringBuilder ret = new StringBuilder();
		ret.append("Plug-in Version: " + version + "\n");
		ret.append(MESSAGE);
		// Initialize
		fLoggingPlugin = null;
		fStatus = null;

		// Run test
		EGerritCorePlugin.logWarning(MESSAGE);

		// Verify result
		assertEquals("Wrong plugin", EGerritCorePlugin.PLUGIN_ID, fLoggingPlugin);
		assertEquals("Wrong message", ret.toString(), fStatus.getMessage());
		assertEquals("Wrong status", IStatus.WARNING, fStatus.getSeverity());
		assertNull("Wrong exception", fStatus.getException());
	}

	/**
	 * Test method for
	 * {@link org.eclipse.egerrit.internal.core.EGerritCorePlugin#logWarning(java.lang.String, java.lang.Throwable)}
	 */
	@Test
	public void testLogWarningStringThrowable() {

		String MESSAGE = "testLogWarning_String_Throwable";
		Exception EXCEPTION = new Exception(MESSAGE);
		//Build the result value
		String version = EGerritCorePlugin.getBundleVersion();
		StringBuilder ret = new StringBuilder();
		ret.append("Plug-in Version: " + version + "\n");
		ret.append(MESSAGE);

		// Initialize
		fLoggingPlugin = null;
		fStatus = null;

		// Run test
		EGerritCorePlugin.logWarning(MESSAGE, EXCEPTION);

		// Verify result
		assertEquals("Wrong plugin", EGerritCorePlugin.PLUGIN_ID, fLoggingPlugin);
		assertEquals("Wrong message", ret.toString(), fStatus.getMessage());
		assertEquals("Wrong status", IStatus.WARNING, fStatus.getSeverity());
		assertEquals("Wrong exception", EXCEPTION, fStatus.getException());
	}

	// ------------------------------------------------------------------------
	// logError()
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.core.EGerritCorePlugin#logError(java.lang.String)}
	 */
	@Test
	public void testLogErrorString() {

		String MESSAGE = "testLogError_String";
		//Build the result value
		String version = EGerritCorePlugin.getBundleVersion();
		StringBuilder ret = new StringBuilder();
		ret.append("Plug-in Version: " + version + "\n");
		ret.append(MESSAGE);

		// Initialize
		fLoggingPlugin = null;
		fStatus = null;

		// Run test
		EGerritCorePlugin.logError(MESSAGE);

		// Verify result
		assertEquals("Wrong plugin", EGerritCorePlugin.PLUGIN_ID, fLoggingPlugin);
		assertEquals("Wrong message", ret.toString(), fStatus.getMessage());
		assertEquals("Wrong status", IStatus.ERROR, fStatus.getSeverity());
		assertNull("Wrong exception", fStatus.getException());
	}

	/**
	 * Test method for
	 * {@link org.eclipse.egerrit.internal.core.EGerritCorePlugin#logError(java.lang.String, java.lang.Throwable)}
	 */
	@Test
	public void testLogErrorStringThrowable() {

		String MESSAGE = "testLogError_String_Throwable";
		Exception EXCEPTION = new Exception(MESSAGE);
		//Build the result value
		String version = EGerritCorePlugin.getBundleVersion();
		StringBuilder ret = new StringBuilder();
		ret.append("Plug-in Version: " + version + "\n");
		ret.append(MESSAGE);

		// Initialize
		fLoggingPlugin = null;
		fStatus = null;

		// Run test
		EGerritCorePlugin.logError(MESSAGE, EXCEPTION);

		// Verify result
		assertEquals("Wrong plugin", EGerritCorePlugin.PLUGIN_ID, fLoggingPlugin);
		assertEquals("Wrong message", ret.toString(), fStatus.getMessage());
		assertEquals("Wrong status", IStatus.ERROR, fStatus.getSeverity());
		assertEquals("Wrong exception", EXCEPTION, fStatus.getException());
	}

}
