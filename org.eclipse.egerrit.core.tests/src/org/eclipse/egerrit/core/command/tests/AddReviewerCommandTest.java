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

package org.eclipse.egerrit.core.command.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.net.URI;

import org.apache.http.HttpHost;
import org.eclipse.egerrit.core.GerritClient;
import org.eclipse.egerrit.core.GerritCredentials;
import org.eclipse.egerrit.core.GerritFactory;
import org.eclipse.egerrit.core.GerritRepository;
import org.eclipse.egerrit.core.command.AddReviewerCommand;
import org.eclipse.egerrit.core.exception.EGerritException;
import org.eclipse.egerrit.core.rest.AddReviewerInput;
import org.eclipse.egerrit.core.rest.AddReviewerResult;
import org.eclipse.egerrit.core.tests.Common;
import org.eclipse.egerrit.core.tests.support.GitAccess;
import org.eclipse.jgit.api.Git;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for {@link org.eclipse.egerrit.core.command.AddReviewerCommand}
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class AddReviewerCommandTest {

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
		fRepository.getCredentials().setHttpCredentials(USER, PASSWORD);

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
	 * {@link org.eclipse.egerrit.core.command.AddReviewerCommand#AddReviewerCommand(org.eclipse.egerrit.core.GerritRepository)}
	 */
	@Test
	public void testAddReviewerCommand() {
		// Run test
		AddReviewerCommand command = fGerrit.addReviewer(null);

		// Verify result
		assertEquals("Wrong repository", fRepository, command.getRepository());
		assertEquals("Wrong return type", AddReviewerResult.class, command.getReturnType());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.AddReviewerCommand#formatRequest()}
	 */
	@Test
	public void testFormatRequest() {
		String EXPECTED_RESULT = null;
		String changeId = "1234";

		// Run test
		AddReviewerCommand command = fGerrit.addReviewer(changeId);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong scheme", SCHEME, uri.getScheme());
		assertEquals("Wrong host", HOST, uri.getHost());
		assertEquals("Wrong port", PORT, uri.getPort());

		assertEquals("Wrong path",
				fGerrit.getRepository().getPath() + "/a/changes/" + command.getChangeId() + "/reviewers",
				uri.getPath());
		assertEquals("Wrong query", EXPECTED_RESULT, uri.getQuery());
	}

	// ------------------------------------------------------------------------
	// Test an actual request
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.AddReviewerCommand#call()}.
	 */
	@Test
	public void testCall() {
		String change_id = null;
		String commit_id = null;
		try {
			GitAccess gAccess = new GitAccess();
			Git git = gAccess.getGitProject();

			gAccess.addFile("EGerritTestReviewFile.java", "Hello reviewers community !\n This is the second line \n");
			gAccess.pushFile();

			change_id = gAccess.getChangeId();
			commit_id = gAccess.getCommitId();

		} catch (Exception e1) {
			fail(e1.getMessage());
		}

		// Run test

		// Add a valid reviewer ...
		AddReviewerCommand command = fGerrit.addReviewer(change_id);
		AddReviewerInput addreviewerInput = new AddReviewerInput();
		addreviewerInput.setReviewer("test1");

		command.setReviewerInput(addreviewerInput);

		AddReviewerResult reviewerCmdResult = null;
		try {
			reviewerCmdResult = command.call();
		} catch (EGerritException e) {
			fail(e.getMessage());
		}

		// Verify result
		assertEquals("Wrong changeId", change_id, command.getChangeId());
		assertEquals("Wrong confirm", false, reviewerCmdResult.getConfirm());

	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.AddReviewerCommand#call()}.
	 */
	@Test
	public void testAddGroupCall() {
		String change_id = null;
		String commit_id = null;
		try {
			GitAccess gAccess = new GitAccess();
			Git git = gAccess.getGitProject();

			gAccess.addFile("EGerritTestReviewFile.java", "Hello reviewers community !\n This is the second line \n");
			gAccess.pushFile();

			change_id = gAccess.getChangeId();
			commit_id = gAccess.getCommitId();

		} catch (Exception e1) {
			fail(e1.getMessage());
		}

		// Run test

		// Add a valid reviewer group. I.e. the current Group defined: "Administrator".
		AddReviewerCommand command = fGerrit.addReviewer(change_id);
		AddReviewerInput addreviewerInput = new AddReviewerInput();
		addreviewerInput.setReviewer("Administrators");

		command.setReviewerInput(addreviewerInput);

		AddReviewerResult reviewerCmdResult = null;
		try {
			reviewerCmdResult = command.call();
		} catch (EGerritException e) {
			fail(e.getMessage());
		}

		// Verify result
		assertEquals("Wrong changeId", command.getChangeId(), change_id);
		assertEquals("Wrong confirm", reviewerCmdResult.getConfirm(), false);

	}

	/********************************************/
	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.AddReviewerCommand#call()}.
	 */
	@Test
	public void testAddUndefinedReviewerCall() {
		String change_id = null;
		String commit_id = null;
		try {
			GitAccess gAccess = new GitAccess();
			Git git = gAccess.getGitProject();

			gAccess.addFile("EGerritTestReviewFile.java", "Hello reviewers community !\n This is the second line \n");
			gAccess.pushFile();

			change_id = gAccess.getChangeId();
			commit_id = gAccess.getCommitId();

		} catch (Exception e1) {
			fail(e1.getMessage());
		}

		// Run test

		// Add a valid reviewer group. I.e. the current Group defined: "Administrator".
		AddReviewerCommand command = fGerrit.addReviewer(change_id);
		AddReviewerInput addreviewerInput = new AddReviewerInput();
		addreviewerInput.setReviewer("Anyone");

		command.setReviewerInput(addreviewerInput);

		try {
			command.call();
		} catch (EGerritException e) {
			if (e.getCode() != EGerritException.SHOWABLE_MESSAGE) {
				fail(e.getMessage());
			}
		}
	}
}
