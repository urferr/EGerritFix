/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Guy Perron - Initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.core.command.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.net.URI;

import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.eclipse.egerrit.core.GerritClient;
import org.eclipse.egerrit.core.GerritCredentials;
import org.eclipse.egerrit.core.GerritFactory;
import org.eclipse.egerrit.core.GerritRepository;
import org.eclipse.egerrit.core.command.ChangeOption;
import org.eclipse.egerrit.core.command.ChangeState;
import org.eclipse.egerrit.core.command.ChangeStatus;
import org.eclipse.egerrit.core.command.GetChangeCommand;
import org.eclipse.egerrit.core.exception.EGerritException;
import org.eclipse.egerrit.core.rest.ChangeInfo;
import org.eclipse.egerrit.core.tests.Common;
import org.eclipse.egerrit.core.tests.support.GitAccess;
import org.eclipse.jgit.api.Git;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for {@link org.eclipse.egerrit.core.command.QueryChangesCommand}
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class GetChangeCommandTest {

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

	private static final String OWNER = "Owner";

	private static final String TOPIC = "Topic";

	private static final ChangeStatus STATUS1 = ChangeStatus.OPEN;

	private static final ChangeStatus STATUS2 = ChangeStatus.REVIEWED;

	private static final ChangeState STATE1 = ChangeState.IS_OPEN;

	private static final ChangeState STATE2 = ChangeState.HAS_STAR;

	private static final ChangeOption OPTION = ChangeOption.DETAILED_LABELS;

	private static final int COUNT = 10;

	// ------------------------------------------------------------------------
	// Helper methods
	// ------------------------------------------------------------------------

	private GerritRepository fRepository;

	private GerritClient fGerrit;

	@Before
	public void setUp() throws Exception {
		fRepository = new GerritRepository(SCHEME, HOST, PORT, PATH);
		if (PROXY_HOST != null) {
			fRepository.setProxy(new HttpHost(PROXY_HOST, PROXY_PORT));
		}
		fRepository.setCredentials(new GerritCredentials(USER, PASSWORD));
		fGerrit = GerritFactory.create(fRepository);
	}

	@After
	public void tearDown() throws Exception {
	}

	// ------------------------------------------------------------------------
	// Test cases
	// ------------------------------------------------------------------------

	/**
	 * Test method for
	 * {@link org.eclipse.egerrit.core.command.GetChangeCommand#GetChangeCommand(org.eclipse.egerrit.core.GerritRepository)}
	 */
	@Test
	public void testGetChangeCommand() {
		// Run test
		GetChangeCommand command = fGerrit.getChange("");

		// Verify result
		assertEquals("Wrong repository", fRepository, command.getRepository());
		assertEquals("Wrong return type", ChangeInfo.class, command.getReturnType());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.GetChangeCommand#formatRequest()}
	 */
	@Test
	public void testFormatRequest() {
		String EXPECTED_RESULT = null;

		// Run test
		GetChangeCommand command = fGerrit.getChange("");
		command.setId("");
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong scheme", Common.SCHEME, uri.getScheme());
		assertEquals("Wrong host", Common.HOST, uri.getHost());
		assertEquals("Wrong port", Common.PORT, uri.getPort());

		assertEquals("Wrong path", fGerrit.getRepository().getPath() + "/changes//detail", uri.getPath());
		assertEquals("Wrong query", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.GetChangeCommand#setOwner(java.lang.String)}
	 */
	@Test
	public void testSetOwner() {
		String EXPECTED_RESULT = "q=" + "owner:" + OWNER;

		// Run test
		GetChangeCommand command = fGerrit.getChange("");
		command.setOwner(OWNER);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong owner", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for
	 * {@link org.eclipse.egerrit.core.command.GetChangeCommand#setStatus(org.eclipse.egerrit.core.command.ChangeStatus)}
	 * .
	 */
	@Test
	public void testSetStatus() {
		String EXPECTED_RESULT = "q=" + STATUS1.getValue();

		// Run test
		GetChangeCommand command = fGerrit.getChange("");
		command.setStatus(STATUS1);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong status", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for
	 * {@link org.eclipse.egerrit.core.command.GetChangeCommand#setStatus(org.eclipse.egerrit.core.command.ChangeStatus)}
	 * .
	 */
	@Test
	public void testSetStatus2() {
		String EXPECTED_RESULT = "q=" + STATUS1.getValue() + "+" + STATUS2.getValue();

		// Run test
		GetChangeCommand command = fGerrit.getChange("");
		command.setStatus(STATUS1);
		command.setStatus(STATUS2);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong status", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for
	 * {@link org.eclipse.egerrit.core.command.GetChangeCommand#setState(org.eclipse.egerrit.core.command.ChangeState)}
	 * .
	 */
	@Test
	public void testSetState() {
		String EXPECTED_RESULT = "q=" + STATE1.getValue();

		// Run test
		GetChangeCommand command = fGerrit.getChange("");
		command.setState(STATE1);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong state", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for
	 * {@link org.eclipse.egerrit.core.command.GetChangeCommand#setState(org.eclipse.egerrit.core.command.ChangeState)}
	 * .
	 */
	@Test
	public void testSetState2() {
		String EXPECTED_RESULT = "q=" + STATE1.getValue() + "+" + STATE2.getValue();

		// Run test
		GetChangeCommand command = fGerrit.getChange("");
		command.setState(STATE1);
		command.setState(STATE2);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong state", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for
	 * {@link org.eclipse.egerrit.core.command.GetChangeCommand#addOption(org.eclipse.egerrit.core.command.ChangeOption)}
	 * .
	 */
	@Test
	public void testSetOption() {
		String EXPECTED_RESULT = "o=" + OPTION.getValue();

		// Run test
		GetChangeCommand command = fGerrit.getChange("");
		command.addOption(OPTION);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong option", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.GetChangeCommand#setCount(int)}.
	 */
	@Test
	public void testSetCount() {
		String EXPECTED_RESULT = "n=" + COUNT;

		// Run test
		GetChangeCommand command = fGerrit.getChange("");
		command.setCount(COUNT);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong count", EXPECTED_RESULT, uri.getQuery());
	}

	// ------------------------------------------------------------------------
	// Combine everything
	// ------------------------------------------------------------------------

	/**
	 * Test a combination of all the parameters/options
	 */
	@Test
	public void testSetAllParameters() {

		// Run test
		GetChangeCommand command = fGerrit.getChange("");
		command.setOwner(OWNER);
		command.setTopic(TOPIC);
		command.setStatus(STATUS1);
		command.setState(STATE1);
		command.addOption(OPTION);
		command.setCount(COUNT);
		URI uri = command.formatRequest().getURI();

		// Verify result
		String query = uri.getQuery();
		assertTrue("Missing owner", query.contains("owner:" + OWNER));
		assertTrue("Missing topic", query.contains("topic:" + TOPIC));
		assertTrue("Missing status", query.contains(STATUS1.getValue()));
		assertTrue("Missing state", query.contains(STATE1.getValue()));

		assertTrue("Missing option", query.contains("o=" + OPTION.getValue()));
		assertTrue("Missing count", query.contains("n=" + COUNT));
	}

	// ------------------------------------------------------------------------
	// Test an actual request
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.GerritCommand#call()}.
	 */
	@Test
	public void testCall() {
		String change_id = null;
		try {
			GitAccess gAccess = new GitAccess();
			Git git = gAccess.getGitProject();

			gAccess.addFile("EGerritTestReviewFile.java", "Hello reviewers community !");
			gAccess.pushFile();

			change_id = gAccess.getChangeId();

		} catch (Exception e1) {
			fail(e1.getMessage());
		}

		// Run test
		GetChangeCommand command = fGerrit.getChange(change_id);
		command.addOption(ChangeOption.DETAILED_LABELS);
		ChangeInfo result = null;
		try {
			result = command.call();
		} catch (EGerritException e) {
			fail(e.getMessage());
		} catch (ClientProtocolException e) {
			fail(e.getMessage());
		}

		// Verify result
		if (result != null) {
			assertEquals(change_id, result.getChange_id());
		}
	}
}
