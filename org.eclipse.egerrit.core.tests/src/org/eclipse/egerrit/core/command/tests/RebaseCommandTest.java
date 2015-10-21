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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpHost;
import org.eclipse.egerrit.core.GerritClient;
import org.eclipse.egerrit.core.GerritCredentials;
import org.eclipse.egerrit.core.GerritFactory;
import org.eclipse.egerrit.core.GerritRepository;
import org.eclipse.egerrit.core.command.RebaseCommand;
import org.eclipse.egerrit.core.command.SetReviewCommand;
import org.eclipse.egerrit.core.command.SubmitCommand;
import org.eclipse.egerrit.core.exception.EGerritException;
import org.eclipse.egerrit.core.rest.ChangeInfo;
import org.eclipse.egerrit.core.rest.ReviewInfo;
import org.eclipse.egerrit.core.rest.ReviewInput;
import org.eclipse.egerrit.core.rest.SubmitInput;
import org.eclipse.egerrit.core.tests.Common;
import org.eclipse.egerrit.core.tests.support.GitAccess;
import org.eclipse.jgit.api.Git;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for {@link org.eclipse.egerrit.core.command.RebaseCommand}
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class RebaseCommandTest {

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
		//fRepository.getCredentials().setHttpCredentials(USER, PASSWORD);

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
	 * {@link org.eclipse.egerrit.core.command.RebaseCommand#RebaseCommand(org.eclipse.egerrit.core.GerritRepository)}
	 */
	@Test
	public void testRebaseCommand() {
		// Run test
		RebaseCommand command = fGerrit.rebase("");

		// Verify result
		assertEquals("Wrong repository", fRepository, command.getRepository());
		assertEquals("Wrong return type", ChangeInfo.class, command.getReturnType());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.RebaseCommand#formatRequest()}
	 */
	@Test
	public void testFormatRequest() {
		String EXPECTED_RESULT = null;

		// Run test
		RebaseCommand command = fGerrit.rebase("");
		command.setId("");
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong scheme", SCHEME, uri.getScheme());
		assertEquals("Wrong host", HOST, uri.getHost());
		assertEquals("Wrong port", PORT, uri.getPort());

		assertEquals("Wrong path", fGerrit.getRepository().getPath() + "/a/changes//rebase", uri.getPath());
		assertEquals("Wrong query", EXPECTED_RESULT, uri.getQuery());
	}

	// ------------------------------------------------------------------------
	// Test an actual request
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.RebaseCommand#call()}.
	 */
	@Test
	public void testCall() throws Exception {
		String change_id = null;
		String commit_id = null;

		GitAccess gAccess = new GitAccess();
		Git git = gAccess.getGitProject();

		gAccess.addFile("EGerritTestReviewFileRebase.java", "Hello reviewers community!\n\n");
		gAccess.pushFile();

		change_id = gAccess.getChangeId();
		commit_id = gAccess.getCommitId();

		// Code-Review +2
		SetReviewCommand setReviewcommand = fGerrit.setReview(change_id, commit_id);
		ReviewInput reviewInput = new ReviewInput();
		reviewInput.setMessage("This review is NOW ready to go ...");
		Map obj = new HashMap();
		obj.put("Code-Review", "2");

		reviewInput.setLabels(obj);

		setReviewcommand.setReviewInput(reviewInput);

		ReviewInfo result2 = null;
		try {
			result2 = setReviewcommand.call();
		} catch (EGerritException e) {
			fail(e.getMessage());
		}

		// now do test and submit ...
		SubmitCommand submitCmd = fGerrit.submit(change_id);
		SubmitInput submitInput = new SubmitInput();
		submitInput.setWait_for_merge(true);

		submitCmd.setSubmitInput(submitInput);

		ChangeInfo submitCmdResult = null;
		try {
			submitCmdResult = submitCmd.call();
		} catch (EGerritException e) {
			fail(e.getMessage());
		}

		gAccess.modifyFile("EGerritTestReviewFileRebase.java", "Hello reviewers community!\n\n");
		gAccess.pushFile();

		push();

		change_id = gAccess.getChangeId();
		commit_id = gAccess.getCommitId();

		// Run test

		// rebase the change ...
		RebaseCommand rebaseCmd = fGerrit.rebase(change_id);

		ChangeInfo rebaseCmdResult = null;
		try {
			rebaseCmdResult = rebaseCmd.call();
		} catch (EGerritException e) {
			fail(e.getMessage());
		}

		// Verify result
		assertNotNull(rebaseCmdResult.getId());

	}

	/**
	 * submit a change to create a rebase condition for test.
	 */
	public void push() {
		String change_id = null;
		String commit_id = null;
		try {
			GitAccess gAccess = new GitAccess();
			Git git = gAccess.getGitProject();

			gAccess.modifyFile("EGerritTestReviewFileRebase.java", "Hello reviewers community!\n\nMODIFIED");
			gAccess.pushFile();

			change_id = gAccess.getChangeId();
			commit_id = gAccess.getCommitId();

		} catch (Exception e1) {
			fail(e1.getMessage());
		}

		// Code-Review +2
		SetReviewCommand setReviewcommand = fGerrit.setReview(change_id, commit_id);
		ReviewInput reviewInput = new ReviewInput();
		reviewInput.setMessage("This review is NOW ready to go ...");
		Map obj = new HashMap();
		obj.put("Code-Review", "2");

		reviewInput.setLabels(obj);

		setReviewcommand.setReviewInput(reviewInput);

		ReviewInfo result2 = null;
		try {
			result2 = setReviewcommand.call();
		} catch (EGerritException e) {
			fail(e.getMessage());
		}

		// now do test and submit ...
		SubmitCommand submitCmd = fGerrit.submit(change_id);
		SubmitInput submitInput = new SubmitInput();
		submitInput.setWait_for_merge(true);

		submitCmd.setSubmitInput(submitInput);

		ChangeInfo submitCmdResult = null;
		try {
			submitCmdResult = submitCmd.call();
		} catch (EGerritException e) {
			fail(e.getMessage());
		}
	}
}
