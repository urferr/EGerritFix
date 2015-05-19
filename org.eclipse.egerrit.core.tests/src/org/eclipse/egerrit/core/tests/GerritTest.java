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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.http.HttpHost;
import org.eclipse.egerrit.core.Gerrit;
import org.eclipse.egerrit.core.GerritCredentials;
import org.eclipse.egerrit.core.GerritFactory;
import org.eclipse.egerrit.core.GerritRepository;
import org.eclipse.egerrit.core.command.QueryChangesCommand;
import org.eclipse.egerrit.core.exception.EGerritException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for {@link org.eclipse.egerrit.Gerrit}
 * 
 * @since 1.0
 */
@SuppressWarnings("nls")
public class GerritTest {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	private final String SCHEME = Common.SCHEME;

	private final String HOST = Common.HOST;

	private final int PORT = Common.PORT;

	private final String PATH = Common.PATH;

	private final String PROXY_HOST = Common.PROXY_HOST;

	private final int PROXY_PORT = Common.PROXY_PORT;

	private final String USER = Common.USER;

	private final String PASSWORD = Common.PASSWORD;

	// ------------------------------------------------------------------------
	// Helper classes
	// ------------------------------------------------------------------------

	private GerritRepository fRepository;

	@Before
	public void setUp() throws Exception {
		fRepository = new GerritRepository(SCHEME, HOST, PORT, PATH);
		if (PROXY_HOST != null) {
			fRepository.setProxy(new HttpHost(PROXY_HOST, PROXY_PORT));
		}
		fRepository.setCredentials(new GerritCredentials(USER, PASSWORD));
		assertNotNull(fRepository);
	}

	@After
	public void tearDown() throws Exception {
	}

	class MyGerrit extends Gerrit {
		protected MyGerrit(GerritRepository gerritRepository) throws EGerritException {
			super(gerritRepository);
		}
	}

	// ------------------------------------------------------------------------
	// Test cases
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.Gerrit#Gerrit(org.eclipse.egerrit.core.GerritRepository)} and
	 * {@link org.eclipse.egerrit.core.Gerrit#getRepository()}.
	 */
	@Test
	public void testGerrit() {
		// Run test
		Gerrit gerrit = null;
		try {
			gerrit = GerritFactory.create(fRepository);
		} catch (EGerritException e) {
			fail(e.getMessage());
		}

		// Verify result
		assertNotNull("Gerrit instance is null", gerrit);
		assertTrue("Wrong version", gerrit instanceof Gerrit);
		assertEquals("Wrong repository", fRepository, gerrit.getRepository());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.Gerrit#Gerrit(org.eclipse.egerrit.core.GerritRepository)} when
	 * repository == null
	 */
	@Test
	public void testGerrit_Null_Repo() {
		// Run test
		Gerrit gerrit = null;
		try {
			gerrit = new MyGerrit(null);
			fail("Exception was not thrown");
		} catch (EGerritException e) {
		}

		// Verify result
		assertNull("Gerrit instance is not null", gerrit);
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
		Gerrit gerrit = null;
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
