/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Ericsson - Initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.core.command.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.net.URI;

import org.apache.http.HttpHost;
import org.eclipse.egerrit.core.GerritClient;
import org.eclipse.egerrit.core.GerritCredentials;
import org.eclipse.egerrit.core.GerritFactory;
import org.eclipse.egerrit.core.GerritRepository;
import org.eclipse.egerrit.core.command.CherryPickRevisionCommand;
import org.eclipse.egerrit.core.exception.EGerritException;
import org.eclipse.egerrit.core.rest.ChangeInfo;
import org.eclipse.egerrit.core.rest.CherryPickInput;
import org.eclipse.egerrit.core.tests.Common;
import org.eclipse.egerrit.core.tests.support.GitAccess;
import org.eclipse.jgit.api.Git;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for {@link org.eclipse.egerrit.core.command.CherryPickRevisionCommand}
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class CherryPickRevisionCommandTest {

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
	 * Test method for {@link org.eclipse.egerrit.core.command.CherryPickRevisionCommand#CherryPickRevisionCommand()}
	 */
	@Test
	public void testCherryPickRevisionCommand() {
		// Run test
		CherryPickRevisionCommand command = fGerrit.cherryPickRevision("", "");

		// Verify result
		assertEquals("Wrong repository", fRepository, command.getRepository());
		assertEquals("Wrong return type", ChangeInfo.class, command.getReturnType());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.CherryPickRevisionCommand#formatRequest()}
	 */
	@Test
	public void testFormatRequest() {
		String EXPECTED_RESULT = null;

		// Run test
		CherryPickRevisionCommand command = fGerrit.cherryPickRevision("", "");
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong scheme", Common.SCHEME, uri.getScheme());
		assertEquals("Wrong host", Common.HOST, uri.getHost());
		assertEquals("Wrong port", Common.PORT, uri.getPort());

		assertEquals("Wrong path", fGerrit.getRepository().getPath() + "/a/changes//revisions//cherrypick",
				uri.getPath());
		assertEquals("Wrong query", EXPECTED_RESULT, uri.getQuery());
	}

	// ------------------------------------------------------------------------
	// Test an actual request
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.GerritCommand#call()}.
	 */
	@Test
	public void testCall() {

		String revision_id = null, change_id = null;
		try {
			GitAccess gAccess = new GitAccess();
			Git git = gAccess.getGitProject();

			gAccess.addFile("EGerritTestReviewFile.java", "Hello reviewers community !");
			gAccess.pushFile();

			change_id = gAccess.getChangeId();

			revision_id = gAccess.getCommitId();

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// Run test
		CherryPickRevisionCommand command = fGerrit.cherryPickRevision(change_id, revision_id);
		CherryPickInput cherryPickInput = new CherryPickInput();
		cherryPickInput.setMessage("Implementing Feature X");
		cherryPickInput.setDestination("HEAD");
		command.setCherryPickInput(cherryPickInput);
		ChangeInfo result = null;
		try {
			result = command.call();
		} catch (EGerritException e) {
			fail(e.getMessage());
		}
		// Verify result
		assert (true);

	}
}
