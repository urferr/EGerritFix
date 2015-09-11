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
import static org.junit.Assert.fail;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.eclipse.egerrit.core.GerritClient;
import org.eclipse.egerrit.core.GerritCredentials;
import org.eclipse.egerrit.core.GerritFactory;
import org.eclipse.egerrit.core.GerritRepository;
import org.eclipse.egerrit.core.command.CreateDraftCommand;
import org.eclipse.egerrit.core.command.GetContentFromCommitCommand;
import org.eclipse.egerrit.core.command.SetReviewCommand;
import org.eclipse.egerrit.core.command.SubmitCommand;
import org.eclipse.egerrit.core.exception.EGerritException;
import org.eclipse.egerrit.core.rest.ChangeInfo;
import org.eclipse.egerrit.core.rest.CommentInfo;
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
 * Test suite for {@link org.eclipse.egerrit.core.command.GetContentFromCommitCommand}
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class GetContentFromCommitCommandTest {

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
	 * Test method for
	 * {@link org.eclipse.egerrit.core.command.GetChangeCommand#GetChangeCommand(org.eclipse.egerrit.core.GerritRepository)}
	 */
	@Test
	public void testGetChangeCommand() {
		// Run test
		GetContentFromCommitCommand command = fGerrit.getContentFromCommit("", "", "");

		// Verify result
		assertEquals("Wrong repository", fRepository, command.getRepository());
		assertEquals("Wrong return type", String.class, command.getReturnType());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.GetChangeCommand#formatRequest()}
	 */
	@Test
	public void testFormatRequest() {
		String EXPECTED_RESULT = null;

		// Run test
		GetContentFromCommitCommand command = fGerrit.getContentFromCommit("", "", "");
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong scheme", Common.SCHEME, uri.getScheme());
		assertEquals("Wrong host", Common.HOST, uri.getHost());
		assertEquals("Wrong port", Common.PORT, uri.getPort());

		assertEquals("Wrong path", fGerrit.getRepository().getPath() + "/projects//commits//files//content",
				uri.getPath());
		assertEquals("Wrong query", EXPECTED_RESULT, uri.getQuery());
	}

	// ------------------------------------------------------------------------
	// Test an actual request
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.GerritCommand#call()}.
	 *
	 * @throws Exception
	 */
	@Test
	public void testCall() throws Exception {

		String commit_id = null, change_id = null;
		GitAccess gAccess = new GitAccess();
		Git git = gAccess.getGitProject();

		gAccess.addFile("EGerritTestReviewFileTestGetContent.java", "Hello reviewers {community} !");
		gAccess.pushFile();

		change_id = gAccess.getChangeId();
		commit_id = gAccess.getCommitId();

		// Run test
		// create a comment
		CreateDraftCommand command = fGerrit.createDraftComments(change_id, commit_id);
		CommentInfo commentInfo = new CommentInfo();
		commentInfo.setLine(1);
		commentInfo.setMessage("This is a test comment");
		commentInfo.setPath("EGerritTestReviewFileTestGetContent.java");
		command.setCommentInfo(commentInfo);

		CommentInfo result = null;
		try {
			result = command.call();
		} catch (EGerritException e) {
			fail(e.getMessage());
		} catch (ClientProtocolException e) {
			fail(e.getMessage());
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
		} catch (ClientProtocolException e) {
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
		} catch (ClientProtocolException e) {
			fail(e.getMessage());
		}

		// Run test
		GetContentFromCommitCommand getContentcommand = fGerrit.getContentFromCommit(Common.TEST_PROJECT, commit_id,
				"EGerritTestReviewFileTestGetContent.java");
		String getContentResult = null;
		try {
			getContentResult = getContentcommand.call();
		} catch (EGerritException e) {
			fail(e.getMessage());
		}

		// Verify result
		assertEquals("Hello reviewers {community} !", StringUtils.newStringUtf8(Base64.decodeBase64(getContentResult)));

	}

}
