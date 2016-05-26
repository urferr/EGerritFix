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

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpHost;
import org.eclipse.egerrit.internal.core.GerritCredentials;
import org.eclipse.egerrit.internal.core.GerritRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.Version;

/**
 * Test suite for {@link org.eclipse.egerrit.GerritRepo}
 * 
 * @since 1.0
 */
@SuppressWarnings("nls")
public class GerritRepositoryTest {

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
	// Helper methods
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

	// ------------------------------------------------------------------------
	// Connect to repository
	// ------------------------------------------------------------------------

	/**
	 * Test method for
	 * {@link org.eclipse.egerrit.internal.core.GerritRepository#GerritRepository(java.lang.String, java.lang.String, int, java.lang.String, java.lang.String)}
	 * and {@link org.eclipse.egerrit.internal.core.GerritRepository#getProtocol()} and
	 * {@link org.eclipse.egerrit.internal.core.GerritRepository#getHost()} and
	 * {@link org.eclipse.egerrit.internal.core.GerritRepository#getPort()}. and
	 * {@link org.eclipse.egerrit.internal.core.GerritRepository#getUser()}.
	 */
	@Test
	public void testGerritRepository() {

		// Verify result
		assertEquals("Wrong scheme", Common.SCHEME, fRepository.getScheme());
		assertEquals("Wrong host", Common.HOST, fRepository.getHost().getHostName());
		assertEquals("Wrong port", Common.PORT, fRepository.getPort());
		assertEquals("Wrong user", Common.USER, fRepository.getCredentials().getUsername());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.core.GerritRepository#validateConnection()}
	 */
	@Test
	public void testValidateConnection() {
		// Run test
		fRepository.connect();

		// Verify result
		assertEquals("Wrong version", Common.GERRIT_VERSION, fRepository.getVersion().toString());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.core.GerritRepository#validateConnection()} Failed case
	 */
	@Test
	public void testValidateConnectionFailed() {
		// To be implemented when we connect to a real repository
	}

	// ------------------------------------------------------------------------
	// getVersion()
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.core.GerritRepository#getVersion()}.
	 */
	@Test
	public void testGetVersion() {

		// Run test
		Version version = fRepository.getVersion();

		// Verify result
		assertEquals("Wrong version", Common.GERRIT_VERSION, (version != null) ? version.toString() : null);
	}

	// ------------------------------------------------------------------------
	// getURIBuilder()
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.core.GerritRepository#getURIBuilder()} normal case
	 */
	@Test
	public void testGetGenericURI() {

		String EXPECTED_URI = Common.SCHEME + "://" + Common.HOST + ":" + Common.PORT + Common.PATH + "/a";

		// Run test
		URI uri = null;
		try {
			uri = fRepository.getURIBuilder(true).build();
		} catch (URISyntaxException e) {
		}

		// Verify result
		assertNotNull("URI is null", uri);
		assertEquals("Wrong URI", EXPECTED_URI, uri.toString());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.core.GerritRepository#getURIBuilder()} with invalid format
	 */
	@Test
	public void testGetGenericURIFailed() {

		// Initialize with invalid protocol (scheme)
		HttpHost proxy = (PROXY_HOST != null) ? new HttpHost(PROXY_HOST, PROXY_PORT) : null;
		GerritCredentials creds = new GerritCredentials(USER, PASSWORD);
		GerritRepository repo = new GerritRepository("", HOST, PORT, PATH).setProxy(proxy).setCredentials(creds);
		assertNotNull(repo);

		// Run test
		URI uri = null;
		try {
			uri = repo.getURIBuilder(false).build();
		} catch (URISyntaxException e) {
		}

		// Verify result
		assertNull("URI is not null", uri);
	}

}
