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
import org.eclipse.egerrit.core.Gerrit;
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

	private static final String OWNER_GROUP = "OwnerGroup";

	private static final String REVIEWER = "Reviewer";

	private static final String REVIEWER_GROUP = "ReviewerGroup";

	private static final String COMMIT = "894a3c36b2e660b180a7bd71b72e173f850a80a5";

	private static final String PROJECT = "Project";

	private static final String PARENT_PROJECT = "ParentProject";

	private static final String PREFIX = "Prefix";

	private static final String BRANCH = "Branch";

	private static final String TOPIC = "Topic";

	private static final String REFERENCE = "Reference";

	private static final String BUG = "Bug1234";

	private static final String LABEL = "Label";

	private static final String MESSAGE = "Message";

	private static final String COMMENT = "Comment";

	private static final String FILE_PATH = "Path";

	private static final String FILE = "File";

	private static final ChangeStatus STATUS1 = ChangeStatus.OPEN;

	private static final ChangeStatus STATUS2 = ChangeStatus.REVIEWED;

	private static final ChangeState STATE1 = ChangeState.IS_OPEN;

	private static final ChangeState STATE2 = ChangeState.HAS_STAR;

	private static final String VISIBLE_TO = "anyone";

	private static final String STARRED_BY = "User1";

	private static final String WATCHED_BY = "User2";

	private static final String DRAFT_BY = "User3";

	private static final int LIMIT = 5;

	private static final ChangeOption OPTION = ChangeOption.DETAILED_LABELS;

	private static final int COUNT = 10;

	// ------------------------------------------------------------------------
	// Helper methods
	// ------------------------------------------------------------------------

	private GerritRepository fRepository;

	private Gerrit fGerrit;

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

		assertEquals("Wrong path", fGerrit.getRepository().getPath() + "/changes/", uri.getPath());
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
	 * Test method for {@link org.eclipse.egerrit.core.command.GetChangeCommand#setOwnerGroup(java.lang.String)}.
	 */
	@Test
	public void testSetOwnerGroup() {
		String EXPECTED_RESULT = "q=" + "ownerin:" + OWNER_GROUP;

		// Run test
		GetChangeCommand command = fGerrit.getChange("");
		command.setOwnerGroup(OWNER_GROUP);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong owner group", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.GetChangeCommand#setReviewer(java.lang.String)}.
	 */
	@Test
	public void testSetReviewer() {
		String EXPECTED_RESULT = "q=" + "reviewer:" + REVIEWER;

		// Run test
		GetChangeCommand command = fGerrit.getChange("");
		command.setReviewer(REVIEWER);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong reviewer", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.GetChangeCommand#setReviewerGroup(java.lang.String)} .
	 */
	@Test
	public void testSetReviewerGroup() {
		String EXPECTED_RESULT = "q=" + "reviewerin:" + REVIEWER_GROUP;

		// Run test
		GetChangeCommand command = fGerrit.getChange("");
		command.setReviewerGroup(REVIEWER_GROUP);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong reviewer group", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.GetChangeCommand#setCommit(java.lang.String)}.
	 */
	@Test
	public void testSetCommit() {
		String EXPECTED_RESULT = "q=" + "commit:" + COMMIT;

		// Run test
		GetChangeCommand command = fGerrit.getChange("");
		command.setCommit(COMMIT);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong commit", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.GetChangeCommand#setProject(java.lang.String)}.
	 */
	@Test
	public void testSetProject() {
		String EXPECTED_RESULT = "q=" + "project:" + PROJECT;

		// Run test
		GetChangeCommand command = fGerrit.getChange("");
		command.setProject(PROJECT);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong project", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.GetChangeCommand#setParentProject(java.lang.String)} .
	 */
	@Test
	public void testSetParentProject() {
		String EXPECTED_RESULT = "q=" + "parentproject:" + PARENT_PROJECT;

		// Run test
		GetChangeCommand command = fGerrit.getChange("");
		command.setParentProject(PARENT_PROJECT);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong parent project", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.GetChangeCommand#setPrefix(java.lang.String)}.
	 */
	@Test
	public void testSetPrefix() {
		String EXPECTED_RESULT = "q=" + "prefix:" + PREFIX;

		// Run test
		GetChangeCommand command = fGerrit.getChange("");
		command.setPrefix(PREFIX);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong prefix", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.GetChangeCommand#setBranch(java.lang.String)}.
	 */
	@Test
	public void testSetBranch() {
		String EXPECTED_RESULT = "q=" + "branch:" + BRANCH;

		// Run test
		GetChangeCommand command = fGerrit.getChange("");
		command.setBranch(BRANCH);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong branch", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.GetChangeCommand#setTopic(java.lang.String)}.
	 */
	@Test
	public void testSetTopic() {
		String EXPECTED_RESULT = "q=" + "topic:" + TOPIC;

		// Run test
		GetChangeCommand command = fGerrit.getChange("");
		command.setTopic(TOPIC);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong topic", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.GetChangeCommand#setReference(java.lang.String)}.
	 */
	@Test
	public void testSetReference() {
		String EXPECTED_RESULT = "q=" + "ref:" + REFERENCE;

		// Run test
		GetChangeCommand command = fGerrit.getChange("");
		command.setReference(REFERENCE);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong reference", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.GetChangeCommand#setBug(java.lang.String)}.
	 */
	@Test
	public void testSetBug() {
		String EXPECTED_RESULT = "q=" + "bug:" + BUG;

		// Run test
		GetChangeCommand command = fGerrit.getChange("");
		command.setBug(BUG);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong bug", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.GetChangeCommand#setLabel(java.lang.String)}.
	 */
	@Test
	public void testSetLabel() {
		String EXPECTED_RESULT = "q=" + "label:" + LABEL;

		// Run test
		GetChangeCommand command = fGerrit.getChange("");
		command.setLabel(LABEL);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong label", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.GetChangeCommand#setMessage(java.lang.String)}.
	 */
	@Test
	public void testSetMessage() {
		String EXPECTED_RESULT = "q=" + "message:" + MESSAGE;

		// Run test
		GetChangeCommand command = fGerrit.getChange("");
		command.setMessage(MESSAGE);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong message", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.GetChangeCommand#setComment(java.lang.String)}.
	 */
	@Test
	public void testSetComment() {
		String EXPECTED_RESULT = "q=" + "comment:" + COMMENT;

		// Run test
		GetChangeCommand command = fGerrit.getChange("");
		command.setComment(COMMENT);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong comment", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.GetChangeCommand#setPath(java.lang.String)}.
	 */
	@Test
	public void testSetPath() {
		String EXPECTED_RESULT = "q=" + "path:" + FILE_PATH;

		// Run test
		GetChangeCommand command = fGerrit.getChange("");
		command.setPath(FILE_PATH);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong path", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.GetChangeCommand#setFile(java.lang.String)}.
	 */
	@Test
	public void testSetFile() {
		String EXPECTED_RESULT = "q=" + "file:" + FILE;

		// Run test
		GetChangeCommand command = fGerrit.getChange("");
		command.setFile(FILE);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong file", EXPECTED_RESULT, uri.getQuery());
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
	 * Test method for {@link org.eclipse.egerrit.core.command.GetChangeCommand#setVisibleTo(java.lang.String)}.
	 */
	@Test
	public void testSetVisibleTo() {
		String EXPECTED_RESULT = "q=" + "visibleto:" + VISIBLE_TO;

		// Run test
		GetChangeCommand command = fGerrit.getChange("");
		command.setVisibleTo(VISIBLE_TO);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong visibility to", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.GetChangeCommand#setVisible()}.
	 */
	@Test
	public void testSetVisible() {
		String EXPECTED_RESULT = "q=" + "is:visible";

		// Run test
		GetChangeCommand command = fGerrit.getChange("");
		command.setVisible();
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong visibility", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.GetChangeCommand#setStarredBy(java.lang.String)}.
	 */
	@Test
	public void testSetStarredBy() {
		String EXPECTED_RESULT = "q=" + "starredby:" + STARRED_BY;

		// Run test
		GetChangeCommand command = fGerrit.getChange("");
		command.setStarredBy(STARRED_BY);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong starred by", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.GetChangeCommand#setWatchedBy(java.lang.String)}.
	 */
	@Test
	public void testSetWatchedBy() {
		String EXPECTED_RESULT = "q=" + "watchedby:" + WATCHED_BY;

		// Run test
		GetChangeCommand command = fGerrit.getChange("");
		command.setWatchedBy(WATCHED_BY);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong watched by", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.GetChangeCommand#setDraftBy(java.lang.String)}.
	 */
	@Test
	public void testSetDraftBy() {
		String EXPECTED_RESULT = "q=" + "draftby:" + DRAFT_BY;

		// Run test
		GetChangeCommand command = fGerrit.getChange("");
		command.setDraftBy(DRAFT_BY);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong draft by", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.GetChangeCommand#setLimit(int)}.
	 */
	@Test
	public void testSetLimit() {
		String EXPECTED_RESULT = "q=" + "limit:" + LIMIT;

		// Run test
		GetChangeCommand command = fGerrit.getChange("");
		command.setLimit(LIMIT);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong limit", EXPECTED_RESULT, uri.getQuery());
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
		command.setOwnerGroup(OWNER_GROUP);
		command.setReviewer(REVIEWER);
		command.setReviewerGroup(REVIEWER_GROUP);
		command.setCommit(COMMIT);
		command.setProject(PROJECT);
		command.setParentProject(PARENT_PROJECT);
		command.setPrefix(PREFIX);
		command.setBranch(BRANCH);
		command.setTopic(TOPIC);
		command.setReference(REFERENCE);
		command.setBug(BUG);
		command.setLabel(LABEL);
		command.setMessage(MESSAGE);
		command.setComment(COMMENT);
		command.setPath(FILE_PATH);
		command.setFile(FILE);
		command.setStatus(STATUS1);
		command.setState(STATE1);
		command.setVisibleTo(VISIBLE_TO);
		command.setVisible();
		command.setStarredBy(STARRED_BY);
		command.setWatchedBy(WATCHED_BY);
		command.setDraftBy(DRAFT_BY);
		command.setLimit(LIMIT);
		command.addOption(OPTION);
		command.setCount(COUNT);
		URI uri = command.formatRequest().getURI();

		// Verify result
		String query = uri.getQuery();
		assertTrue("Missing owner", query.contains("owner:" + OWNER));
		assertTrue("Missing owner group", query.contains("ownerin:" + OWNER_GROUP));
		assertTrue("Missing reviewer", query.contains("reviewer:" + REVIEWER));
		assertTrue("Missing reviewer group", query.contains("reviewerin:" + REVIEWER_GROUP));
		assertTrue("Missing commit", query.contains("commit:" + COMMIT));
		assertTrue("Missing project", query.contains("project:" + PROJECT));
		assertTrue("Missing parent project", query.contains("parentproject:" + PARENT_PROJECT));
		assertTrue("Missing prefix", query.contains("prefix:" + PREFIX));
		assertTrue("Missing branch", query.contains("branch:" + BRANCH));
		assertTrue("Missing topic", query.contains("topic:" + TOPIC));
		assertTrue("Missing reference", query.contains("ref:" + REFERENCE));
		assertTrue("Missing bug", query.contains("bug:" + BUG));
		assertTrue("Missing label", query.contains("label:" + LABEL));
		assertTrue("Missing message", query.contains("message:" + MESSAGE));
		assertTrue("Missing comment", query.contains("comment:" + COMMENT));
		assertTrue("Missing path", query.contains("path:" + FILE_PATH));
		assertTrue("Missing file", query.contains("file:" + FILE));
		assertTrue("Missing status", query.contains(STATUS1.getValue()));
		assertTrue("Missing state", query.contains(STATE1.getValue()));
		assertTrue("Missing visibility to", query.contains("visibleto:" + VISIBLE_TO));
		assertTrue("Missing visibility", query.contains("is:visible"));
		assertTrue("Missing starred by", query.contains("starredby:" + STARRED_BY));
		assertTrue("Missing watched by", query.contains("watchedby:" + WATCHED_BY));
		assertTrue("Missing draft by", query.contains("draftby:" + DRAFT_BY));
		assertTrue("Missing limit", query.contains("limit:" + LIMIT));

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
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// Run test
		GetChangeCommand command = fGerrit.getChange(change_id);
		ChangeInfo result = null;
		try {
			result = command.call();
		} catch (EGerritException e) {
			fail(e.getMessage());
		}

		// Verify result
		assertEquals(change_id, result.getChange_id());

	}

}
