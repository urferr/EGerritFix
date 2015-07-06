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
import org.eclipse.egerrit.core.Gerrit;
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

	private static final String IS = ChangeState.IS_OPEN.getValue();

	private static final String HAS = ChangeState.HAS_STAR.getValue();

	private static final String TR = "Tr4567";

	private static final String CHANGE = "9876";

	private static final String AGE = "1week";

	private static final String SHORTOWNER = "test";

	private static final String SHORTREVIEWER = "tester";

	private static final String SHORTFILE = "File1.java";

	private static final String SHORTPROJECT = "alpha";

	private static final String MANYPROJECT = "alpha,beta";

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
	 * Test method for {@link org.eclipse.egerrit.core.command.QueryChangesCommand#setOwnerGroup(java.lang.String)} .
	 */
	@Test
	public void testSetOwnerGroup() {
		String EXPECTED_RESULT = "q=" + "ownerin:" + OWNER_GROUP;

		// Run test
		QueryChangesCommand command = fGerrit.queryChanges();
		command.addOwnerGroup(OWNER_GROUP);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong owner group", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.QueryChangesCommand#setReviewer(java.lang.String)} .
	 */
	@Test
	public void testSetReviewer() {
		String EXPECTED_RESULT = "q=" + "reviewer:" + REVIEWER;

		// Run test
		QueryChangesCommand command = fGerrit.queryChanges();
		command.addReviewer(REVIEWER);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong reviewer", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.QueryChangesCommand#setReviewerGroup(java.lang.String)} .
	 */
	@Test
	public void testSetReviewerGroup() {
		String EXPECTED_RESULT = "q=" + "reviewerin:" + REVIEWER_GROUP;

		// Run test
		QueryChangesCommand command = fGerrit.queryChanges();
		command.addReviewerGroup(REVIEWER_GROUP);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong reviewer group", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.QueryChangesCommand#setCommit(java.lang.String)} .
	 */
	@Test
	public void testSetCommit() {
		String EXPECTED_RESULT = "q=" + "commit:" + COMMIT;

		// Run test
		QueryChangesCommand command = fGerrit.queryChanges();
		command.addCommit(COMMIT);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong commit", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.QueryChangesCommand#setProject(java.lang.String)} .
	 */
	@Test
	public void testSetProject() {
		String EXPECTED_RESULT = "q=" + "project:" + PROJECT;

		// Run test
		QueryChangesCommand command = fGerrit.queryChanges();
		command.addProject(PROJECT);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong project", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.QueryChangesCommand#setParentProject(java.lang.String)} .
	 */
	@Test
	public void testSetParentProject() {
		String EXPECTED_RESULT = "q=" + "parentproject:" + PARENT_PROJECT;

		// Run test
		QueryChangesCommand command = fGerrit.queryChanges();
		command.addParentProject(PARENT_PROJECT);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong parent project", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.QueryChangesCommand#setPrefix(java.lang.String)} .
	 */
	@Test
	public void testSetPrefix() {
		String EXPECTED_RESULT = "q=" + "prefix:" + PREFIX;

		// Run test
		QueryChangesCommand command = fGerrit.queryChanges();
		command.addPrefix(PREFIX);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong prefix", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.QueryChangesCommand#setBranch(java.lang.String)} .
	 */
	@Test
	public void testSetBranch() {
		String EXPECTED_RESULT = "q=" + "branch:" + BRANCH;

		// Run test
		QueryChangesCommand command = fGerrit.queryChanges();
		command.addBranch(BRANCH);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong branch", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.QueryChangesCommand#setTopic(java.lang.String)} .
	 */
	@Test
	public void testSetTopic() {
		String EXPECTED_RESULT = "q=" + "topic:" + TOPIC;

		// Run test
		QueryChangesCommand command = fGerrit.queryChanges();
		command.addTopic(TOPIC);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong topic", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.QueryChangesCommand#setReference(java.lang.String)} .
	 */
	@Test
	public void testSetReference() {
		String EXPECTED_RESULT = "q=" + "ref:" + REFERENCE;

		// Run test
		QueryChangesCommand command = fGerrit.queryChanges();
		command.addReference(REFERENCE);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong reference", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.QueryChangesCommand#setBug(java.lang.String)} .
	 */
	@Test
	public void testSetBug() {
		String EXPECTED_RESULT = "q=" + "bug:" + BUG;

		// Run test
		QueryChangesCommand command = fGerrit.queryChanges();
		command.addBug(BUG);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong bug", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.QueryChangesCommand#setLabel(java.lang.String)} .
	 */
	@Test
	public void testSetLabel() {
		String EXPECTED_RESULT = "q=" + "label:" + LABEL;

		// Run test
		QueryChangesCommand command = fGerrit.queryChanges();
		command.addLabel(LABEL);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong label", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.QueryChangesCommand#setMessage(java.lang.String)} .
	 */
	@Test
	public void testSetMessage() {
		String EXPECTED_RESULT = "q=" + "message:" + MESSAGE;

		// Run test
		QueryChangesCommand command = fGerrit.queryChanges();
		command.addMessage(MESSAGE);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong message", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.QueryChangesCommand#setComment(java.lang.String)} .
	 */
	@Test
	public void testSetComment() {
		String EXPECTED_RESULT = "q=" + "comment:" + COMMENT;

		// Run test
		QueryChangesCommand command = fGerrit.queryChanges();
		command.addComment(COMMENT);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong comment", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.QueryChangesCommand#setPath(java.lang.String)} .
	 */
	@Test
	public void testSetPath() {
		String EXPECTED_RESULT = "q=" + "path:" + FILE_PATH;

		// Run test
		QueryChangesCommand command = fGerrit.queryChanges();
		command.addPath(FILE_PATH);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong path", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.QueryChangesCommand#setFile(java.lang.String)} .
	 */
	@Test
	public void testSetFile() {
		String EXPECTED_RESULT = "q=" + "file:" + FILE;

		// Run test
		QueryChangesCommand command = fGerrit.queryChanges();
		command.addFile(FILE);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong file", EXPECTED_RESULT, uri.getQuery());
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
	 * Test method for {@link org.eclipse.egerrit.core.command.QueryChangesCommand#setVisibleTo(java.lang.String)} .
	 */
	@Test
	public void testSetVisibleTo() {
		String EXPECTED_RESULT = "q=" + "visibleto:" + VISIBLE_TO;

		// Run test
		QueryChangesCommand command = fGerrit.queryChanges();
		command.addVisibleTo(VISIBLE_TO);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong visibility to", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.QueryChangesCommand#setVisible()} .
	 */
	@Test
	public void testSetVisible() {
		String EXPECTED_RESULT = "q=" + "is:visible";

		// Run test
		QueryChangesCommand command = fGerrit.queryChanges();
		command.addVisible();
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong visibility", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.QueryChangesCommand#setStarredBy(java.lang.String)} .
	 */
	@Test
	public void testSetStarredBy() {
		String EXPECTED_RESULT = "q=" + "starredby:" + STARRED_BY;

		// Run test
		QueryChangesCommand command = fGerrit.queryChanges();
		command.addStarredBy(STARRED_BY);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong starred by", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.QueryChangesCommand#setWatchedBy(java.lang.String)} .
	 */
	@Test
	public void testSetWatchedBy() {
		String EXPECTED_RESULT = "q=" + "watchedby:" + WATCHED_BY;

		// Run test
		QueryChangesCommand command = fGerrit.queryChanges();
		command.addWatchedBy(WATCHED_BY);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong watched by", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.QueryChangesCommand#setDraftBy(java.lang.String)} .
	 */
	@Test
	public void testSetDraftBy() {
		String EXPECTED_RESULT = "q=" + "draftby:" + DRAFT_BY;

		// Run test
		QueryChangesCommand command = fGerrit.queryChanges();
		command.addDraftBy(DRAFT_BY);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong draft by", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.QueryChangesCommand#setLimit(int)} .
	 */
	@Test
	public void testSetLimit() {
		String EXPECTED_RESULT = "q=" + "limit:" + LIMIT;

		// Run test
		QueryChangesCommand command = fGerrit.queryChanges();
		command.addLimit(LIMIT);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong limit", EXPECTED_RESULT, uri.getQuery());
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

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.QueryChangesCommand#addIs(java.lang.String)} .
	 */
	@Test
	public void testaddIs() {
		String EXPECTED_RESULT = "q=" + "is:" + IS;

		// Run test
		QueryChangesCommand command = fGerrit.queryChanges();
		command.addIs(IS);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong is ", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.QueryChangesCommand#addHas(java.lang.String)} .
	 */
	@Test
	public void testaddHas() {
		String EXPECTED_RESULT = "q=" + "has:" + HAS;

		// Run test
		QueryChangesCommand command = fGerrit.queryChanges();
		command.addHas(HAS);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong has ", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.QueryChangesCommand#addTr(java.lang.String)} .
	 */
	@Test
	public void testaddTr() {
		String EXPECTED_RESULT = "q=" + "tr:" + TR;

		// Run test
		QueryChangesCommand command = fGerrit.queryChanges();
		command.addTr(TR);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong TR ", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.QueryChangesCommand#addChange(java.lang.String)} .
	 */
	@Test
	public void testaddChange() {
		String EXPECTED_RESULT = "q=" + "change:" + CHANGE;

		// Run test
		QueryChangesCommand command = fGerrit.queryChanges();
		command.addChange(CHANGE);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong CHANGE ", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.QueryChangesCommand#addAge(java.lang.String)} .
	 */
	@Test
	public void testaddAge() {
		String EXPECTED_RESULT = "q=" + "age:" + AGE;

		// Run test
		QueryChangesCommand command = fGerrit.queryChanges();
		command.addAge(AGE);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong age ", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.QueryChangesCommand#addShortOwner(java.lang.String)} .
	 */
	@Test
	public void testaddShortOwner() {
		String EXPECTED_RESULT = "q=" + "o:" + SHORTOWNER;

		// Run test
		QueryChangesCommand command = fGerrit.queryChanges();
		command.addShortOwner(SHORTOWNER);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong short owner request ", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.QueryChangesCommand#addShortReviewer(java.lang.String)} .
	 */
	@Test
	public void testaddShortReviewer() {
		String EXPECTED_RESULT = "q=" + "r:" + SHORTREVIEWER;

		// Run test
		QueryChangesCommand command = fGerrit.queryChanges();
		command.addShortReviewer(SHORTREVIEWER);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong short reviewer request ", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.QueryChangesCommand#addShortProject(java.lang.String)} .
	 */
	@Test
	public void testaddShortProject() {
		String EXPECTED_RESULT = "q=" + "p:" + SHORTPROJECT;

		// Run test
		QueryChangesCommand command = fGerrit.queryChanges();
		command.addShortProject(SHORTPROJECT);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong short project request ", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.QueryChangesCommand#addManyProjects(java.lang.String)} .
	 */
	@Test
	public void testaddManyProjects() {
		String EXPECTED_RESULT = "q=" + "projects:" + MANYPROJECT;

		// Run test
		QueryChangesCommand command = fGerrit.queryChanges();
		command.addManyProjects(MANYPROJECT);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong many project request ", EXPECTED_RESULT, uri.getQuery());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.QueryChangesCommand#addShortFile(java.lang.String)} .
	 */
	@Test
	public void testaddShortFile() {
		String EXPECTED_RESULT = "q=" + "f:" + SHORTFILE;

		// Run test
		QueryChangesCommand command = fGerrit.queryChanges();
		command.addShortFile(SHORTFILE);
		URI uri = command.formatRequest().getURI();

		// Verify result
		assertEquals("Wrong short project request ", EXPECTED_RESULT, uri.getQuery());
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
		command.addOwnerGroup(OWNER_GROUP);
		command.addReviewer(REVIEWER);
		command.addReviewerGroup(REVIEWER_GROUP);
		command.addCommit(COMMIT);
		command.addProject(PROJECT);
		command.addParentProject(PARENT_PROJECT);
		command.addPrefix(PREFIX);
		command.addBranch(BRANCH);
		command.addTopic(TOPIC);
		command.addReference(REFERENCE);
		command.addBug(BUG);
		command.addLabel(LABEL);
		command.addMessage(MESSAGE);
		command.addComment(COMMENT);
		command.addPath(FILE_PATH);
		command.addFile(FILE);
		command.addStatus(STATUS1);
		command.addState(STATE1);
		command.addVisibleTo(VISIBLE_TO);
		command.addVisible();
		command.addStarredBy(STARRED_BY);
		command.addWatchedBy(WATCHED_BY);
		command.addDraftBy(DRAFT_BY);
		command.addLimit(LIMIT);
		command.addOption(OPTION);
		command.setCount(COUNT);
		command.addTr(TR);
		command.addChange(CHANGE);
		command.addAge(AGE);
		command.addShortFile(SHORTFILE);
		command.addShortOwner(SHORTOWNER);
		command.addShortProject(SHORTPROJECT);
		command.addShortReviewer(SHORTREVIEWER);
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
		assertTrue("Missing age", query.contains("age:" + AGE));
		assertTrue("Missing tr", query.contains("tr:" + TR));
		assertTrue("Missing change", query.contains("change:" + CHANGE));
		assertTrue("Missing short file", query.contains("f:" + SHORTFILE));
		assertTrue("Missing short owner", query.contains("o:" + SHORTOWNER));
		assertTrue("Missing short reviewer", query.contains("r:" + SHORTREVIEWER));
		assertTrue("Missing short project", query.contains("p:" + SHORTPROJECT));
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
