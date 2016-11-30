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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.http.HttpHost;
import org.eclipse.egerrit.internal.core.GerritClient;
import org.eclipse.egerrit.internal.core.GerritCredentials;
import org.eclipse.egerrit.internal.core.GerritFactory;
import org.eclipse.egerrit.internal.core.GerritRepository;
import org.eclipse.egerrit.internal.core.Gerrit_2_9;
import org.eclipse.egerrit.internal.core.command.QueryChangesCommand;
import org.eclipse.egerrit.internal.core.exception.EGerritException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.Version;

/**
 * Test suite for {@link org.eclipse.egerrit.internal.core.Gerrit_2_9}
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class Gerrit_2_9Test {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	// ------------------------------------------------------------------------
	// Helper methods
	// ------------------------------------------------------------------------

	private GerritRepository fRepository;

	@Before
	public void setUp() throws Exception {
		fRepository = new GerritRepository(Common.SCHEME, Common.HOST, Common_2_9.PORT, Common_2_9.PATH);
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
	 * {@link org.eclipse.egerrit.internal.core.Gerrit_2_9#Gerrit_2_9(org.eclipse.egerrit.internal.core.GerritRepository)}
	 * . and {@link org.eclipse.egerrit.internal.core.GerritClient#getRepository()}.
	 */
	@Test
	public void testGerrit_2_9() {
		// Run test
		GerritClient gerrit = null;
		try {
			gerrit = GerritFactory.create(fRepository);
		} catch (EGerritException e) {
			fail(e.getMessage());
		}

		// Verify result
		Version version = fRepository.getVersion();
		if (version.getMajor() == 2 && version.getMinor() == 9) {
			assertTrue("Wrong version", gerrit instanceof Gerrit_2_9);
		}
		assertNotNull("Gerrit instance is null", gerrit);
		assertEquals("Wrong repository", fRepository, gerrit.getRepository());
	}

	// ------------------------------------------------------------------------
	// Commands
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.core.GerritClient#queryChanges()}.
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
