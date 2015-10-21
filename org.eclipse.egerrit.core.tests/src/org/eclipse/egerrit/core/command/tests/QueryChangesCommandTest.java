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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import org.apache.http.HttpHost;
import org.eclipse.egerrit.core.GerritClient;
import org.eclipse.egerrit.core.GerritCredentials;
import org.eclipse.egerrit.core.GerritFactory;
import org.eclipse.egerrit.core.GerritRepository;
import org.eclipse.egerrit.core.command.ChangeOption;
import org.eclipse.egerrit.core.command.ChangeState;
import org.eclipse.egerrit.core.command.ChangeStatus;
import org.eclipse.egerrit.core.command.QueryChangesCommand;
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
public class QueryChangesCommandTest {

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
	 * {@link org.eclipse.egerrit.core.command.QueryChangesCommand#QueryChangesCommand(org.eclipse.egerrit.core.GerritRepository)}
	 */
	@Test
	public void testQueryChangesCommand() {
		// Run test
		QueryChangesCommand command = fGerrit.queryChanges();

		// Verify result
		assertEquals("Wrong repository", fRepository, command.getRepository());
		assertEquals("Wrong return type", ChangeInfo[].class, command.getReturnType());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.QueryChangesCommand#formatRequest()}
	 */
	@Test
	public void testFormatRequest() {
		String EXPECTED_RESULT = null;

		// Run test
		QueryChangesCommand command = fGerrit.queryChanges();
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong scheme", Common.SCHEME, uri.getScheme());
		assertEquals("Wrong host", Common.HOST, uri.getHost());
		assertEquals("Wrong port", Common.PORT, uri.getPort());

		assertEquals("Wrong path", Common.CHANGES_PATH, uri.getPath());
		assertEquals("Wrong query", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.QueryChangesCommand#setOwner(java.lang.String)}
	 */
	@Test
	public void testSetOwner() {
		String EXPECTED_RESULT = "q=" + "owner:" + OWNER;

		// Run test
		QueryChangesCommand command = fGerrit.queryChanges();
		command.addOwner(OWNER);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong owner", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.QueryChangesCommand#setTopic(java.lang.String)} .
	 */
	@Test
	public void testSetTopic() {
		String EXPECTED_RESULT = "q=" + "topic:\"" + TOPIC + "\"";

		// Run test
		QueryChangesCommand command = fGerrit.queryChanges();
		command.addTopic(TOPIC);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong topic", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for
	 * {@link org.eclipse.egerrit.core.command.QueryChangesCommand#setStatus(org.eclipse.egerrit.core.command.ChangeStatus)}
	 * .
	 */
	@Test
	public void testSetStatus() {
		String EXPECTED_RESULT = "q=" + STATUS1.getValue();

		// Run test
		QueryChangesCommand command = fGerrit.queryChanges();
		command.addStatus(STATUS1);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong status", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for
	 * {@link org.eclipse.egerrit.core.command.QueryChangesCommand#setStatus(org.eclipse.egerrit.core.command.ChangeStatus)}
	 * .
	 */
	@Test
	public void testSetStatus2() {
		String EXPECTED_RESULT = "q=" + STATUS1.getValue() + "+" + STATUS2.getValue();

		// Run test
		QueryChangesCommand command = fGerrit.queryChanges();
		command.addStatus(STATUS1);
		command.addStatus(STATUS2);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong status", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for
	 * {@link org.eclipse.egerrit.core.command.QueryChangesCommand#setState(org.eclipse.egerrit.core.command.ChangeState)}
	 * .
	 */
	@Test
	public void testSetState() {
		String EXPECTED_RESULT = "q=" + STATE1.getValue();

		// Run test
		QueryChangesCommand command = fGerrit.queryChanges();
		command.addState(STATE1);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong state", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for
	 * {@link org.eclipse.egerrit.core.command.QueryChangesCommand#setState(org.eclipse.egerrit.core.command.ChangeState)}
	 * .
	 */
	@Test
	public void testSetState2() {
		String EXPECTED_RESULT = "q=" + STATE1.getValue() + "+" + STATE2.getValue();

		// Run test
		QueryChangesCommand command = fGerrit.queryChanges();
		command.addState(STATE1);
		command.addState(STATE2);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong state", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for
	 * {@link org.eclipse.egerrit.core.command.QueryChangesCommand#addOption(org.eclipse.egerrit.core.command.ChangeOption)}
	 * .
	 */
	@Test
	public void testSetOption() {
		String EXPECTED_RESULT = "o=" + OPTION.getValue();

		// Run test
		QueryChangesCommand command = fGerrit.queryChanges();
		command.addOption(OPTION);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong option", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.QueryChangesCommand#setCount(int)} .
	 */
	@Test
	public void testSetCount() {
		String EXPECTED_RESULT = "n=" + COUNT;

		// Run test
		QueryChangesCommand command = fGerrit.queryChanges();
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
		QueryChangesCommand command = fGerrit.queryChanges();
		command.addOwner(OWNER);
		command.addTopic(TOPIC);
		command.addStatus(STATUS1);
		command.addState(STATE1);
		command.addOption(OPTION);
		command.setCount(COUNT);
		URI uri = command.formatRequest().getURI();

		// Verify result
		String query = uri.getQuery();
		assertTrue("Missing owner", query.contains("owner:" + OWNER));
		assertTrue("Missing topic", query.contains("topic:\"" + TOPIC + "\""));
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
	@SuppressWarnings("unused")
	public void testCall() {
		String change_id = null;
		try {
			GitAccess gAccess = new GitAccess();
			Git git = gAccess.getGitProject();

			gAccess.addFile("EGerritTestReviewFile.java", "Hello reviewers community !");
			gAccess.pushFile();

			change_id = gAccess.getChangeId();

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// Run test
		QueryChangesCommand command = fGerrit.queryChanges();
		ChangeInfo[] result = null;
		try {
			result = command.call();
		} catch (EGerritException e) {
			fail(e.getMessage());
		}

		List<ChangeInfo> listChangeInfo = Arrays.asList(result);
		boolean isFound = false;

		for (ListIterator<ChangeInfo> iter = listChangeInfo.listIterator(); iter.hasNext();) {
			ChangeInfo element = iter.next();
			if (element.getChange_id().equals(change_id)) {
				isFound = true;
				break;

			}
		}

		// Verify result
		assertTrue(isFound);

	}

}
