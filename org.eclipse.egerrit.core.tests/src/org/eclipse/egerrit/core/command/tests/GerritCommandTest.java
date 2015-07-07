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

package org.eclipse.egerrit.core.command.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpHost;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.eclipse.egerrit.core.GerritCredentials;
import org.eclipse.egerrit.core.GerritRepository;
import org.eclipse.egerrit.core.command.GerritCommand;
import org.eclipse.egerrit.core.tests.Common;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for {@link org.eclipse.egerrit.core.command.GerritCommand}
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class GerritCommandTest {

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

	private class MyReturnType {
	}

	private final String QUERY = "q=some_query";

	private class MyCommand extends GerritCommand<MyReturnType> {
		protected MyCommand(GerritRepository gerritRepository, Class<MyReturnType> returnType) {
			super(gerritRepository, returnType);
		}

		@Override
		public HttpRequestBase formatRequest() {
			URIBuilder uriBuilder = fRepository.getURIBuilder(false);
			URI uri = null;
			try {
				uri = uriBuilder.setPath(Common.CHANGES_PATH).build();
			} catch (URISyntaxException e) {
			}
			return new HttpGet(uri);
		}

		@SuppressWarnings("deprecation")
		public HttpRequestBase formatRequest2() {
			URIBuilder uriBuilder = fRepository.getURIBuilder(false);
			URI uri = null;
			try {
				uri = uriBuilder.setPath(Common.CHANGES_PATH).setQuery(QUERY).build();
			} catch (URISyntaxException e) {
			}
			return new HttpGet(uri);
		}
	}

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
	}

	@After
	public void tearDown() throws Exception {
	}

	// ------------------------------------------------------------------------
	// Test cases
	// ------------------------------------------------------------------------

	/**
	 * Test method for
	 * {@link org.eclipse.egerrit.core.command.GerritCommand#GerritCommand(org.eclipse.egerrit.core.GerritRepository, Type)}
	 * . and {@link org.eclipse.egerrit.core.command.GerritCommand#getRepository()}. and
	 * {@link org.eclipse.egerrit.core.command.GerritCommand#getReturnType()}.
	 */
	@Test
	public void testGerritCommand() {
		// Run test
		MyCommand command = new MyCommand(fRepository, MyReturnType.class);

		// Verify result
		assertEquals("Wrong repository", fRepository, command.getRepository());
		assertEquals("Wrong return type", MyReturnType.class, command.getReturnType());
	}

	/**
	 * Test method for
	 * {@link org.eclipse.egerrit.core.command.GerritCommand#formatRequest(org.apache.http.client.utils.URIBuilder)}.
	 */
	@Test
	public void testFormatRequest() {
		// Run test
		MyCommand command = new MyCommand(fRepository, MyReturnType.class);
		URI uri1 = command.formatRequest().getURI();
		URI uri2 = command.formatRequest2().getURI();

		// Verify result
		assertEquals("Wrong path", Common.CHANGES_PATH, uri1.getPath());
		assertNull("Wrong query", uri1.getQuery());
		assertEquals("Wrong query", QUERY, uri2.getQuery());
	}

}
