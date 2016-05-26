/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jacques Bouthillier - Initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.core.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.http.HttpHost;
import org.eclipse.egerrit.internal.core.GerritClient;
import org.eclipse.egerrit.internal.core.GerritCredentials;
import org.eclipse.egerrit.internal.core.GerritFactory;
import org.eclipse.egerrit.internal.core.GerritRepository;
import org.eclipse.egerrit.internal.core.Gerrit_2_11;
import org.eclipse.egerrit.internal.core.command.QueryChangesCommand;
import org.eclipse.egerrit.internal.core.exception.EGerritException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.Version;

/**
 * Test suite for {@link org.eclipse.egerrit.internal.core.Gerrit_2_11}
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class Gerrit_2_11Test {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	// ------------------------------------------------------------------------
	// Helper methods
	// ------------------------------------------------------------------------

	private GerritRepository fRepository;

	@Before
	public void setUp() throws Exception {
		System.err.println("System.getProperty(): " + System.getProperties());
		System.err.println("====-----=====-----======-----====");
		System.err.println("System.getProperty(localhost.test.server http://localhost:2080)"
				+ System.getProperty("localhost.test.server", "http://localhost:2080"));
		System.err.println("System.getProperty(org.eclipse.egerrit.test.all: "
				+ System.getProperty("org.eclipse.egerrit.test.all", "Jacques"));
		fRepository = new GerritRepository(Common.SCHEME, Common.HOST, Common_2_11.PORT, Common_2_11.PATH);
		if (Common.PROXY_HOST != null) {
			fRepository.setProxy(new HttpHost(Common.PROXY_HOST, Common.PROXY_PORT));
		}
		fRepository.setCredentials(new GerritCredentials(Common.USER, Common.PASSWORD));
	}

	@After
	public void tearDown() throws Exception {
	}

	// ------------------------------------------------------------------------
	// Test cases
	// ------------------------------------------------------------------------

	/**
	 * Test method for
	 * {@link org.eclipse.egerrit.internal.core.Gerrit_2_11#Gerrit_2_11(org.eclipse.egerrit.internal.core.GerritRepository)} . and
	 * {@link org.eclipse.egerrit.core.Gerrit#getRepository()}.
	 */
	@Test
	public void testGerrit_2_11() {
		// Run test
		GerritClient gerrit = null;
		try {
			gerrit = GerritFactory.create(fRepository);
		} catch (EGerritException e) {
			fail(e.getMessage());
		}

		// Verify result
		Version version = fRepository.getVersion();
		if (version.getMajor() == 2 && version.getMinor() == 11) {
			assertTrue("Wrong version", gerrit instanceof Gerrit_2_11);
		}
		assertNotNull("Gerrit instance is null", gerrit);
		assertEquals("Wrong repository", fRepository, gerrit.getRepository());
	}

	// ------------------------------------------------------------------------
	// Commands
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.Gerrit#queryChanges()}.
	 */
	@Test
	public void testQueryChanges() {
		// Run test
		GerritClient gerrit = null;
		try {
			gerrit = GerritFactory.create(fRepository);
		} catch (EGerritException e) {
			fail(e.getMessage());
		}

		// Run test
		QueryChangesCommand command = gerrit.queryChanges();

		// Verify result
		assertNotNull("Command instance is null", command);
	}

}
