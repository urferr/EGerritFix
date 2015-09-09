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

import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.eclipse.egerrit.core.GerritClient;
import org.eclipse.egerrit.core.GerritCredentials;
import org.eclipse.egerrit.core.GerritFactory;
import org.eclipse.egerrit.core.GerritRepository;
import org.eclipse.egerrit.core.command.CreateDraftCommand;
import org.eclipse.egerrit.core.command.UpdateDraftCommand;
import org.eclipse.egerrit.core.exception.EGerritException;
import org.eclipse.egerrit.core.rest.CommentInfo;
import org.eclipse.egerrit.core.tests.Common;
import org.eclipse.egerrit.core.tests.support.GitAccess;
import org.eclipse.jgit.api.Git;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for {@link org.eclipse.egerrit.core.command.UpdateDraftCommand}
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class UpdateDraftCommandTest {

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
	 * {@link org.eclipse.egerrit.core.command.UpdateDraftCommand#UpdateDraftCommand(org.eclipse.egerrit.core.GerritRepository)}
	 */
	@Test
	public void testUpdateDraftCommand() {
		// Run test
		UpdateDraftCommand command = fGerrit.updateDraftComments("", "", "");

		// Verify result
		assertEquals("Wrong repository", fRepository, command.getRepository());
		assertEquals("Wrong return type", CommentInfo.class, command.getReturnType());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.UpdateDraftCommand#formatRequest()}
	 */
	@Test
	public void testFormatRequest() {
		String EXPECTED_RESULT = null;

		// Run test
		UpdateDraftCommand command = fGerrit.updateDraftComments("", "", "");
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong scheme", Common.SCHEME, uri.getScheme());
		assertEquals("Wrong host", Common.HOST, uri.getHost());
		assertEquals("Wrong port", Common.PORT, uri.getPort());

		assertEquals("Wrong path", fGerrit.getRepository().getPath() + "/a/changes//revisions//drafts/", uri.getPath());
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

		// Create a draft comment
		CreateDraftCommand command = fGerrit.createDraftComments(change_id, commit_id);
		CommentInfo commentInfo = new CommentInfo();
		commentInfo.setLine(2);
		commentInfo.setMessage("This is a test comment");
		commentInfo.setPath("EGerritTestReviewFile.java");
		command.setCommentInfo(commentInfo);

		CommentInfo result = null;
		try {
			result = command.call();
		} catch (EGerritException e) {
			fail(e.getMessage());
		} catch (ClientProtocolException e) {
			fail(e.getMessage());
		}

		// Run test
		UpdateDraftCommand updCommand = fGerrit.updateDraftComments(change_id, commit_id, result.getId());
		CommentInfo updCommentInfo = new CommentInfo();
		updCommentInfo.setLine(2);
		updCommentInfo.setMessage("This is an UPDATED test comment");
		updCommentInfo.setPath("EGerritTestReviewFile.java");
		updCommand.setCommentInfo(updCommentInfo);

		CommentInfo updResult = null;
		try {
			updResult = updCommand.call();
		} catch (EGerritException e) {
			fail(e.getMessage());
		} catch (ClientProtocolException e) {
			fail(e.getMessage());
		}

		// Verify result
		assertEquals(updResult.getLine(), updCommentInfo.getLine());
		assertEquals(updResult.getMessage(), updCommentInfo.getMessage());
		assertEquals(updResult.getPath(), updCommentInfo.getPath());

	}
}
