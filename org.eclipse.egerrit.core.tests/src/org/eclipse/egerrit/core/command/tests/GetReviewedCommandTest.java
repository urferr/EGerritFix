/*******************************************************************************
 * Copyright (c) 2015 Ericsson AB.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson - initial API and implementation
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
import org.eclipse.egerrit.core.command.GetReviewedFilesCommand;
import org.eclipse.egerrit.core.command.SetReviewedCommand;
import org.eclipse.egerrit.core.exception.EGerritException;
import org.eclipse.egerrit.core.tests.Common;
import org.eclipse.egerrit.core.tests.support.GitAccess;
import org.eclipse.jgit.api.Git;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GetReviewedCommandTest {

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
	 * Test method for {@link org.eclipse.egerrit.core.command.GetReviewedCommand}
	 */
	@Test
	public void testGetReviewedCommand() {
		// Run test
		GetReviewedFilesCommand command = fGerrit.getReviewed("", "");

		// Verify result
		assertEquals("Wrong repository", fRepository, command.getRepository());
		assertEquals("Wrong return type", String[].class, command.getReturnType());
	}

	@Test
	public void testFormatRequest() {
		// Run test
		GetReviewedFilesCommand command = fGerrit.getReviewed("", "");
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong scheme", Common.SCHEME, uri.getScheme());
		assertEquals("Wrong host", Common.HOST, uri.getHost());
		assertEquals("Wrong port", Common.PORT, uri.getPort());

		assertEquals("Wrong path", fGerrit.getRepository().getPath() + "/changes//revisions//files/", uri.getPath());
		assertEquals("Wrong path", "reviewed", uri.getQuery());
	}

	// ------------------------------------------------------------------------
	// Test an actual request
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.GetReviewedCommand#call()}.
	 *
	 * @throws Exception
	 */
	@Test
	public void testCall() throws Exception {
		final String FILENAME = "EGerritTestReviewFile.java"; //$NON-NLS-1$
		String commit_id = null, change_id = null;
		GitAccess gAccess = new GitAccess();
		Git git = gAccess.getGitProject();

		gAccess.addFile(FILENAME, "Hello reviewers {community} !");
		gAccess.pushFile();

		change_id = gAccess.getChangeId();
		commit_id = gAccess.getCommitId();

		// Create a review for setup
		SetReviewedCommand command = fGerrit.setReviewed(change_id, commit_id, FILENAME);
		try {
			command.call();
		} catch (EGerritException e) {
			fail(e.getMessage());
		}

		//Test that the value is indeed here
		GetReviewedFilesCommand getCommand = fGerrit.getReviewed(change_id, commit_id);
		try {
			String[] result = getCommand.call();
			assertEquals(FILENAME, result[0]);
		} catch (EGerritException e) {
			fail(e.getMessage());
		}
	}

}
