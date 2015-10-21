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
import java.util.ArrayList;
import java.util.Map;

import org.apache.http.HttpHost;
import org.eclipse.egerrit.core.GerritClient;
import org.eclipse.egerrit.core.GerritCredentials;
import org.eclipse.egerrit.core.GerritFactory;
import org.eclipse.egerrit.core.GerritRepository;
import org.eclipse.egerrit.core.command.CreateDraftCommand;
import org.eclipse.egerrit.core.command.ListCommentsCommand;
import org.eclipse.egerrit.core.command.ListDraftsCommand;
import org.eclipse.egerrit.core.exception.EGerritException;
import org.eclipse.egerrit.core.rest.CommentInfo;
import org.eclipse.egerrit.core.tests.Common;
import org.eclipse.egerrit.core.tests.support.GitAccess;
import org.eclipse.jgit.api.Git;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.reflect.TypeToken;

/**
 * Test suite for {@link org.eclipse.egerrit.core.command.ListDraftsCommand}
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class ListDraftsCommandTest {

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
	 * Test method for {@link org.eclipse.egerrit.core.command.ListCommentsCommand#ListDraftsCommand()}
	 */
	@Test
	public void testListCommentsCommand() {
		// Run test
		ListCommentsCommand command = fGerrit.getListComments("", "");

		// Verify result
		assertEquals("Wrong repository", fRepository, command.getRepository());
		assertEquals("Wrong return type", new TypeToken<Map<String, ArrayList<CommentInfo>>>() {
		}.getType(), command.getReturnType());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.ListDraftsCommand#formatRequest()}
	 */
	@Test
	public void testFormatRequest() {
		String EXPECTED_RESULT = null;

		// Run test
		ListDraftsCommand command = fGerrit.listDraftsComments("", "");
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong scheme", Common.SCHEME, uri.getScheme());
		assertEquals("Wrong host", Common.HOST, uri.getHost());
		assertEquals("Wrong port", Common.PORT, uri.getPort());

		assertEquals("Wrong path", fGerrit.getRepository().getPath() + "/a/changes//revisions//drafts", uri.getPath());
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
			fail(e1.getMessage());
		}

		// create a comment
		CreateDraftCommand command = fGerrit.createDraftComments(change_id, revision_id);
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
		}
		// Run test
		ListDraftsCommand command2 = fGerrit.listDraftsComments(change_id, revision_id);
		Map<String, ArrayList<CommentInfo>> result2 = null;
		try {
			result2 = command2.call();
		} catch (EGerritException e) {
			fail(e.getMessage());
		}

		// Verify result
		assert (result2.size() == 1);

	}

}
