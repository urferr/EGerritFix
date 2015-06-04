/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Francois Chouinard - Initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.core.rest.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.Reader;
import java.io.StringReader;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.egerrit.core.rest.AccountInfo;
import org.eclipse.egerrit.core.rest.ActionInfo;
import org.eclipse.egerrit.core.rest.ChangeInfo;
import org.eclipse.egerrit.core.rest.ChangeMessageInfo;
import org.eclipse.egerrit.core.rest.LabelInfo;
import org.eclipse.egerrit.core.rest.ProblemInfo;
import org.eclipse.egerrit.core.rest.RevisionInfo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Test suite for {@link org.eclipse.egerrit.core.rest.ChangeInfo}
 */
@SuppressWarnings("nls")
public class ChangeInfoTest {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	private static final String KIND = "gerritcodereview#change";

	private static final String ID = "myProject~master~I4982a3771051891899528a94fb47baeeb70582ae";

	private static final String PROJECT = "myProject";

	private static final String BRANCH = "master";

	private static final String TOPIC = "EGerrit";

	private static final String CHANGE_ID = "I4982a3771051891899528a94fb47baeeb70582ae";

	private static final String SUBJECT = "Implementing Feature X";

	private static final String STATUS = "NEW";

	private static final String CREATED = "2014-10-21 01:07:26.225000000";

	private static final String UPDATED = "2014-10-21 01:07:26.225000000";

	public static boolean STARRED = true;

	public static boolean REVIEWED = true;

	public static boolean MERGEABLE = true;

	public static int INSERTIONS = 1234;

	public static int DELETIONS = 432;

	private static final String SORTKEY = "0030952300000001";

	public static int NUMBER = 5;

	public static AccountInfo OWNER = new AccountInfo();

	public static Map<String, ActionInfo> ACTIONS = new LinkedHashMap<String, ActionInfo>();

	public static Map<String, LabelInfo> LABELS = new LinkedHashMap<String, LabelInfo>();

	public static Map<String, String[]> PERMITTED_LABELS = new LinkedHashMap<String, String[]>();

	public static List<AccountInfo> REMOVABLE_REVIEWERS = new LinkedList<AccountInfo>();

	public static List<ChangeMessageInfo> MESSAGES = new LinkedList<ChangeMessageInfo>();

	private static final String CURRENT_REVISION = "0030952300000001";

	private static final Map<String, RevisionInfo> REVISIONS = new LinkedHashMap<String, RevisionInfo>();

	public static boolean MORE_CHANGES = true;

	private static final List<ProblemInfo> PROBLEMS = new LinkedList<ProblemInfo>();

	private static final String BASE_CHANGE = "0030952300000001";

	private static final int HASH_CODE = 816837224;

	private static final String TO_STRING = "ChangeInfo [" + "kind=" + KIND + ", id=" + ID + ", project=" + PROJECT
			+ ", branch=" + BRANCH + ", topic=" + TOPIC + ", change_id=" + CHANGE_ID + ", subject=" + SUBJECT
			+ ", status=" + STATUS + ", created=" + CREATED + ", updated=" + UPDATED + ", starred=" + STARRED
			+ ", reviewed=" + REVIEWED + ", mergeable=" + MERGEABLE + ", insertions=" + INSERTIONS + ", deletions="
			+ DELETIONS + ", _sortkey=" + SORTKEY + ", _number=" + NUMBER + ", owner=" + OWNER + ", actions=" + ACTIONS
			+ ", labels=" + LABELS + ", permitted_labels=" + PERMITTED_LABELS + ", removable_reviewers="
			+ REMOVABLE_REVIEWERS + ", messages=" + MESSAGES + ", current_revision=" + CURRENT_REVISION + ", revisions="
			+ REVISIONS + ", _more_changes=" + MORE_CHANGES + ", problems=" + PROBLEMS + ", base_change=" + BASE_CHANGE
			+ "]";

	// ------------------------------------------------------------------------
	// Attributes
	// ------------------------------------------------------------------------

	/**
	 * The GSON parser
	 */
	private Gson gson;

	/**
	 * The JSON builder
	 */
	private JsonObject json;

	/**
	 * The parsing result
	 */
	private ChangeInfo fChangeInfo = null;

	// ------------------------------------------------------------------------
	// Helper methods
	// ------------------------------------------------------------------------

	@Before
	public void setUp() throws Exception {
		gson = new GsonBuilder().create();
		json = new JsonObject();
	}

	private void setAllFields() {
		json.addProperty("kind", KIND);
		json.addProperty("id", ID);
		json.addProperty("project", PROJECT);
		json.addProperty("branch", BRANCH);
		json.addProperty("topic", TOPIC);
		json.addProperty("change_id", CHANGE_ID);
		json.addProperty("subject", SUBJECT);
		json.addProperty("status", STATUS);
		json.addProperty("created", CREATED);
		json.addProperty("updated", UPDATED);
		json.addProperty("starred", STARRED);
		json.addProperty("reviewed", REVIEWED);
		json.addProperty("mergeable", MERGEABLE);
		json.addProperty("insertions", INSERTIONS);
		json.addProperty("deletions", DELETIONS);
		json.addProperty("_sortkey", SORTKEY);
		json.addProperty("_number", NUMBER);
		JsonObject owner = new JsonObject();
		json.add("owner", owner);
		JsonObject actions = new JsonObject();
		json.add("actions", actions);
		JsonObject labels = new JsonObject();
		json.add("labels", labels);
		JsonObject permitted_labels = new JsonObject();
		json.add("permitted_labels", permitted_labels);
		JsonArray removable_reviewers = new JsonArray();
		json.add("removable_reviewers", removable_reviewers);
		JsonArray messages = new JsonArray();
		json.add("messages", messages);
		json.addProperty("current_revision", CURRENT_REVISION);
		JsonObject revisions = new JsonObject();
		json.add("revisions", revisions);
		json.addProperty("_more_changes", MORE_CHANGES);
		JsonArray problems = new JsonArray();
		json.add("problems", problems);
		json.addProperty("base_change", BASE_CHANGE);
	}

	@After
	public void tearDown() throws Exception {
	}

	// ------------------------------------------------------------------------
	// Test cases
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ChangeInfo#getKind()}.
	 */
	@Test
	public void testGetKind() {
		json.addProperty("kind", KIND);
		Reader reader = new StringReader(json.toString());
		fChangeInfo = gson.fromJson(reader, ChangeInfo.class);

		assertEquals("Wrong kind", KIND, fChangeInfo.getKind());
		assertNull("Wrong id", fChangeInfo.getId());
		assertNull("Wrong project", fChangeInfo.getProject());
		assertNull("Wrong branch", fChangeInfo.getBranch());
		assertNull("Wrong topic", fChangeInfo.getTopic());
		assertNull("Wrong change_id", fChangeInfo.getChange_id());
		assertNull("Wrong subject", fChangeInfo.getSubject());
		assertNull("Wrong status", fChangeInfo.getStatus());
		assertNull("Wrong created", fChangeInfo.getCreated());
		assertNull("Wrong updated", fChangeInfo.getUpdated());
		assertFalse("Wrong starred", fChangeInfo.isStarred());
		assertFalse("Wrong reviewed", fChangeInfo.isReviewed());
		assertFalse("Wrong mergeable", fChangeInfo.isMergeable());
		assertEquals("Wrong insertions", -1, fChangeInfo.getInsertions());
		assertEquals("Wrong deletions", -1, fChangeInfo.getDeletions());
		assertNull("Wrong sortkey", fChangeInfo.getSortkey());
		assertEquals("Wrong number", -1, fChangeInfo.getNumber());
		assertNull("Wrong owner", fChangeInfo.getOwner());
		assertNull("Wrong actions", fChangeInfo.getActions());
		assertNull("Wrong labels", fChangeInfo.getLabels());
		assertNull("Wrong permitted_labels", fChangeInfo.getPermittedLabels());
		assertNull("Wrong removable_reviewers", fChangeInfo.getRemovableReviewers());
		assertNull("Wrong messages", fChangeInfo.getMessages());
		assertNull("Wrong current_revision", fChangeInfo.getCurrentRevision());
		assertNull("Wrong revisions", fChangeInfo.getRevisions());
		assertFalse("Wrong more_changes", fChangeInfo.hasMoreChanges());
		assertNull("Wrong problems", fChangeInfo.getProblems());
		assertNull("Wrong base_change", fChangeInfo.getBaseChange());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ChangeInfo#getId()}.
	 */
	@Test
	public void testGetId() {
		json.addProperty("id", ID);
		Reader reader = new StringReader(json.toString());
		fChangeInfo = gson.fromJson(reader, ChangeInfo.class);

		assertNull("Wrong kind", fChangeInfo.getKind());
		assertEquals("Wrong id", ID, fChangeInfo.getId());
		assertNull("Wrong project", fChangeInfo.getProject());
		assertNull("Wrong branch", fChangeInfo.getBranch());
		assertNull("Wrong topic", fChangeInfo.getTopic());
		assertNull("Wrong change_id", fChangeInfo.getChange_id());
		assertNull("Wrong subject", fChangeInfo.getSubject());
		assertNull("Wrong status", fChangeInfo.getStatus());
		assertNull("Wrong created", fChangeInfo.getCreated());
		assertNull("Wrong updated", fChangeInfo.getUpdated());
		assertFalse("Wrong starred", fChangeInfo.isStarred());
		assertFalse("Wrong reviewed", fChangeInfo.isReviewed());
		assertFalse("Wrong mergeable", fChangeInfo.isMergeable());
		assertEquals("Wrong insertions", -1, fChangeInfo.getInsertions());
		assertEquals("Wrong deletions", -1, fChangeInfo.getDeletions());
		assertNull("Wrong sortkey", fChangeInfo.getSortkey());
		assertEquals("Wrong number", -1, fChangeInfo.getNumber());
		assertNull("Wrong owner", fChangeInfo.getOwner());
		assertNull("Wrong actions", fChangeInfo.getActions());
		assertNull("Wrong labels", fChangeInfo.getLabels());
		assertNull("Wrong permitted_labels", fChangeInfo.getPermittedLabels());
		assertNull("Wrong removable_reviewers", fChangeInfo.getRemovableReviewers());
		assertNull("Wrong messages", fChangeInfo.getMessages());
		assertNull("Wrong current_revision", fChangeInfo.getCurrentRevision());
		assertNull("Wrong revisions", fChangeInfo.getRevisions());
		assertFalse("Wrong more_changes", fChangeInfo.hasMoreChanges());
		assertNull("Wrong problems", fChangeInfo.getProblems());
		assertNull("Wrong base_change", fChangeInfo.getBaseChange());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ChangeInfo#getProject()}.
	 */
	@Test
	public void testGetProject() {
		json.addProperty("project", PROJECT);
		Reader reader = new StringReader(json.toString());
		fChangeInfo = gson.fromJson(reader, ChangeInfo.class);

		assertNull("Wrong kind", fChangeInfo.getKind());
		assertNull("Wrong id", fChangeInfo.getId());
		assertEquals("Wrong project", PROJECT, fChangeInfo.getProject());
		assertNull("Wrong branch", fChangeInfo.getBranch());
		assertNull("Wrong topic", fChangeInfo.getTopic());
		assertNull("Wrong change_id", fChangeInfo.getChange_id());
		assertNull("Wrong subject", fChangeInfo.getSubject());
		assertNull("Wrong status", fChangeInfo.getStatus());
		assertNull("Wrong created", fChangeInfo.getCreated());
		assertNull("Wrong updated", fChangeInfo.getUpdated());
		assertFalse("Wrong starred", fChangeInfo.isStarred());
		assertFalse("Wrong reviewed", fChangeInfo.isReviewed());
		assertFalse("Wrong mergeable", fChangeInfo.isMergeable());
		assertEquals("Wrong insertions", -1, fChangeInfo.getInsertions());
		assertEquals("Wrong deletions", -1, fChangeInfo.getDeletions());
		assertNull("Wrong sortkey", fChangeInfo.getSortkey());
		assertEquals("Wrong number", -1, fChangeInfo.getNumber());
		assertNull("Wrong owner", fChangeInfo.getOwner());
		assertNull("Wrong actions", fChangeInfo.getActions());
		assertNull("Wrong labels", fChangeInfo.getLabels());
		assertNull("Wrong permitted_labels", fChangeInfo.getPermittedLabels());
		assertNull("Wrong removable_reviewers", fChangeInfo.getRemovableReviewers());
		assertNull("Wrong messages", fChangeInfo.getMessages());
		assertNull("Wrong current_revision", fChangeInfo.getCurrentRevision());
		assertNull("Wrong revisions", fChangeInfo.getRevisions());
		assertFalse("Wrong more_changes", fChangeInfo.hasMoreChanges());
		assertNull("Wrong problems", fChangeInfo.getProblems());
		assertNull("Wrong base_change", fChangeInfo.getBaseChange());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ChangeInfo#getBranch()}.
	 */
	@Test
	public void testGetBranch() {
		json.addProperty("branch", BRANCH);
		Reader reader = new StringReader(json.toString());
		fChangeInfo = gson.fromJson(reader, ChangeInfo.class);

		assertNull("Wrong kind", fChangeInfo.getKind());
		assertNull("Wrong id", fChangeInfo.getId());
		assertNull("Wrong project", fChangeInfo.getProject());
		assertEquals("Wrong branch", BRANCH, fChangeInfo.getBranch());
		assertNull("Wrong topic", fChangeInfo.getTopic());
		assertNull("Wrong change_id", fChangeInfo.getChange_id());
		assertNull("Wrong subject", fChangeInfo.getSubject());
		assertNull("Wrong status", fChangeInfo.getStatus());
		assertNull("Wrong created", fChangeInfo.getCreated());
		assertNull("Wrong updated", fChangeInfo.getUpdated());
		assertFalse("Wrong starred", fChangeInfo.isStarred());
		assertFalse("Wrong reviewed", fChangeInfo.isReviewed());
		assertFalse("Wrong mergeable", fChangeInfo.isMergeable());
		assertEquals("Wrong insertions", -1, fChangeInfo.getInsertions());
		assertEquals("Wrong deletions", -1, fChangeInfo.getDeletions());
		assertNull("Wrong sortkey", fChangeInfo.getSortkey());
		assertEquals("Wrong number", -1, fChangeInfo.getNumber());
		assertNull("Wrong owner", fChangeInfo.getOwner());
		assertNull("Wrong actions", fChangeInfo.getActions());
		assertNull("Wrong labels", fChangeInfo.getLabels());
		assertNull("Wrong permitted_labels", fChangeInfo.getPermittedLabels());
		assertNull("Wrong removable_reviewers", fChangeInfo.getRemovableReviewers());
		assertNull("Wrong messages", fChangeInfo.getMessages());
		assertNull("Wrong current_revision", fChangeInfo.getCurrentRevision());
		assertNull("Wrong revisions", fChangeInfo.getRevisions());
		assertFalse("Wrong more_changes", fChangeInfo.hasMoreChanges());
		assertNull("Wrong problems", fChangeInfo.getProblems());
		assertNull("Wrong base_change", fChangeInfo.getBaseChange());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ChangeInfo#getTopic()}.
	 */
	@Test
	public void testGetTopic() {
		json.addProperty("topic", TOPIC);
		Reader reader = new StringReader(json.toString());
		fChangeInfo = gson.fromJson(reader, ChangeInfo.class);

		assertNull("Wrong kind", fChangeInfo.getKind());
		assertNull("Wrong id", fChangeInfo.getId());
		assertNull("Wrong project", fChangeInfo.getProject());
		assertNull("Wrong branch", fChangeInfo.getBranch());
		assertEquals("Wrong topic", TOPIC, fChangeInfo.getTopic());
		assertNull("Wrong change_id", fChangeInfo.getChange_id());
		assertNull("Wrong subject", fChangeInfo.getSubject());
		assertNull("Wrong status", fChangeInfo.getStatus());
		assertNull("Wrong created", fChangeInfo.getCreated());
		assertNull("Wrong updated", fChangeInfo.getUpdated());
		assertFalse("Wrong starred", fChangeInfo.isStarred());
		assertFalse("Wrong reviewed", fChangeInfo.isReviewed());
		assertFalse("Wrong mergeable", fChangeInfo.isMergeable());
		assertEquals("Wrong insertions", -1, fChangeInfo.getInsertions());
		assertEquals("Wrong deletions", -1, fChangeInfo.getDeletions());
		assertNull("Wrong sortkey", fChangeInfo.getSortkey());
		assertEquals("Wrong number", -1, fChangeInfo.getNumber());
		assertNull("Wrong owner", fChangeInfo.getOwner());
		assertNull("Wrong actions", fChangeInfo.getActions());
		assertNull("Wrong labels", fChangeInfo.getLabels());
		assertNull("Wrong permitted_labels", fChangeInfo.getPermittedLabels());
		assertNull("Wrong removable_reviewers", fChangeInfo.getRemovableReviewers());
		assertNull("Wrong messages", fChangeInfo.getMessages());
		assertNull("Wrong current_revision", fChangeInfo.getCurrentRevision());
		assertNull("Wrong revisions", fChangeInfo.getRevisions());
		assertFalse("Wrong more_changes", fChangeInfo.hasMoreChanges());
		assertNull("Wrong problems", fChangeInfo.getProblems());
		assertNull("Wrong base_change", fChangeInfo.getBaseChange());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ChangeInfo#getChange_id()}.
	 */
	@Test
	public void testGetChangeId() {
		json.addProperty("change_id", CHANGE_ID);
		Reader reader = new StringReader(json.toString());
		fChangeInfo = gson.fromJson(reader, ChangeInfo.class);

		assertNull("Wrong kind", fChangeInfo.getKind());
		assertNull("Wrong id", fChangeInfo.getId());
		assertNull("Wrong project", fChangeInfo.getProject());
		assertNull("Wrong branch", fChangeInfo.getBranch());
		assertNull("Wrong topic", fChangeInfo.getTopic());
		assertEquals("Wrong change_id", CHANGE_ID, fChangeInfo.getChange_id());
		assertNull("Wrong subject", fChangeInfo.getSubject());
		assertNull("Wrong status", fChangeInfo.getStatus());
		assertNull("Wrong created", fChangeInfo.getCreated());
		assertNull("Wrong updated", fChangeInfo.getUpdated());
		assertFalse("Wrong starred", fChangeInfo.isStarred());
		assertFalse("Wrong reviewed", fChangeInfo.isReviewed());
		assertFalse("Wrong mergeable", fChangeInfo.isMergeable());
		assertEquals("Wrong insertions", -1, fChangeInfo.getInsertions());
		assertEquals("Wrong deletions", -1, fChangeInfo.getDeletions());
		assertNull("Wrong sortkey", fChangeInfo.getSortkey());
		assertEquals("Wrong number", -1, fChangeInfo.getNumber());
		assertNull("Wrong owner", fChangeInfo.getOwner());
		assertNull("Wrong actions", fChangeInfo.getActions());
		assertNull("Wrong labels", fChangeInfo.getLabels());
		assertNull("Wrong permitted_labels", fChangeInfo.getPermittedLabels());
		assertNull("Wrong removable_reviewers", fChangeInfo.getRemovableReviewers());
		assertNull("Wrong messages", fChangeInfo.getMessages());
		assertNull("Wrong current_revision", fChangeInfo.getCurrentRevision());
		assertNull("Wrong revisions", fChangeInfo.getRevisions());
		assertFalse("Wrong more_changes", fChangeInfo.hasMoreChanges());
		assertNull("Wrong problems", fChangeInfo.getProblems());
		assertNull("Wrong base_change", fChangeInfo.getBaseChange());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ChangeInfo#getSubject()}.
	 */
	@Test
	public void testGetSubject() {
		json.addProperty("subject", SUBJECT);
		Reader reader = new StringReader(json.toString());
		fChangeInfo = gson.fromJson(reader, ChangeInfo.class);

		assertNull("Wrong kind", fChangeInfo.getKind());
		assertNull("Wrong id", fChangeInfo.getId());
		assertNull("Wrong project", fChangeInfo.getProject());
		assertNull("Wrong branch", fChangeInfo.getBranch());
		assertNull("Wrong topic", fChangeInfo.getTopic());
		assertNull("Wrong change_id", fChangeInfo.getChange_id());
		assertEquals("Wrong subject", SUBJECT, fChangeInfo.getSubject());
		assertNull("Wrong status", fChangeInfo.getStatus());
		assertNull("Wrong created", fChangeInfo.getCreated());
		assertNull("Wrong updated", fChangeInfo.getUpdated());
		assertFalse("Wrong starred", fChangeInfo.isStarred());
		assertFalse("Wrong reviewed", fChangeInfo.isReviewed());
		assertFalse("Wrong mergeable", fChangeInfo.isMergeable());
		assertEquals("Wrong insertions", -1, fChangeInfo.getInsertions());
		assertEquals("Wrong deletions", -1, fChangeInfo.getDeletions());
		assertNull("Wrong sortkey", fChangeInfo.getSortkey());
		assertEquals("Wrong number", -1, fChangeInfo.getNumber());
		assertNull("Wrong owner", fChangeInfo.getOwner());
		assertNull("Wrong actions", fChangeInfo.getActions());
		assertNull("Wrong labels", fChangeInfo.getLabels());
		assertNull("Wrong permitted_labels", fChangeInfo.getPermittedLabels());
		assertNull("Wrong removable_reviewers", fChangeInfo.getRemovableReviewers());
		assertNull("Wrong messages", fChangeInfo.getMessages());
		assertNull("Wrong current_revision", fChangeInfo.getCurrentRevision());
		assertNull("Wrong revisions", fChangeInfo.getRevisions());
		assertFalse("Wrong more_changes", fChangeInfo.hasMoreChanges());
		assertNull("Wrong problems", fChangeInfo.getProblems());
		assertNull("Wrong base_change", fChangeInfo.getBaseChange());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ChangeInfo#getStatus()}.
	 */
	@Test
	public void testGetStatus() {
		json.addProperty("status", STATUS);
		Reader reader = new StringReader(json.toString());
		fChangeInfo = gson.fromJson(reader, ChangeInfo.class);

		assertNull("Wrong kind", fChangeInfo.getKind());
		assertNull("Wrong id", fChangeInfo.getId());
		assertNull("Wrong project", fChangeInfo.getProject());
		assertNull("Wrong branch", fChangeInfo.getBranch());
		assertNull("Wrong topic", fChangeInfo.getTopic());
		assertNull("Wrong change_id", fChangeInfo.getChange_id());
		assertNull("Wrong subject", fChangeInfo.getSubject());
		assertEquals("Wrong status", STATUS, fChangeInfo.getStatus());
		assertNull("Wrong created", fChangeInfo.getCreated());
		assertNull("Wrong updated", fChangeInfo.getUpdated());
		assertFalse("Wrong starred", fChangeInfo.isStarred());
		assertFalse("Wrong reviewed", fChangeInfo.isReviewed());
		assertFalse("Wrong mergeable", fChangeInfo.isMergeable());
		assertEquals("Wrong insertions", -1, fChangeInfo.getInsertions());
		assertEquals("Wrong deletions", -1, fChangeInfo.getDeletions());
		assertNull("Wrong sortkey", fChangeInfo.getSortkey());
		assertEquals("Wrong number", -1, fChangeInfo.getNumber());
		assertNull("Wrong owner", fChangeInfo.getOwner());
		assertNull("Wrong actions", fChangeInfo.getActions());
		assertNull("Wrong labels", fChangeInfo.getLabels());
		assertNull("Wrong permitted_labels", fChangeInfo.getPermittedLabels());
		assertNull("Wrong removable_reviewers", fChangeInfo.getRemovableReviewers());
		assertNull("Wrong messages", fChangeInfo.getMessages());
		assertNull("Wrong current_revision", fChangeInfo.getCurrentRevision());
		assertNull("Wrong revisions", fChangeInfo.getRevisions());
		assertFalse("Wrong more_changes", fChangeInfo.hasMoreChanges());
		assertNull("Wrong problems", fChangeInfo.getProblems());
		assertNull("Wrong base_change", fChangeInfo.getBaseChange());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ChangeInfo#getCreated()}.
	 */
	@Test
	public void testGetCreated() {
		json.addProperty("created", CREATED);
		Reader reader = new StringReader(json.toString());
		fChangeInfo = gson.fromJson(reader, ChangeInfo.class);

		assertNull("Wrong kind", fChangeInfo.getKind());
		assertNull("Wrong id", fChangeInfo.getId());
		assertNull("Wrong project", fChangeInfo.getProject());
		assertNull("Wrong branch", fChangeInfo.getBranch());
		assertNull("Wrong topic", fChangeInfo.getTopic());
		assertNull("Wrong change_id", fChangeInfo.getChange_id());
		assertNull("Wrong subject", fChangeInfo.getSubject());
		assertNull("Wrong status", fChangeInfo.getStatus());
		assertEquals("Wrong created", CREATED, fChangeInfo.getCreated());
		assertNull("Wrong updated", fChangeInfo.getUpdated());
		assertFalse("Wrong starred", fChangeInfo.isStarred());
		assertFalse("Wrong reviewed", fChangeInfo.isReviewed());
		assertFalse("Wrong mergeable", fChangeInfo.isMergeable());
		assertEquals("Wrong insertions", -1, fChangeInfo.getInsertions());
		assertEquals("Wrong deletions", -1, fChangeInfo.getDeletions());
		assertNull("Wrong sortkey", fChangeInfo.getSortkey());
		assertEquals("Wrong number", -1, fChangeInfo.getNumber());
		assertNull("Wrong owner", fChangeInfo.getOwner());
		assertNull("Wrong actions", fChangeInfo.getActions());
		assertNull("Wrong labels", fChangeInfo.getLabels());
		assertNull("Wrong permitted_labels", fChangeInfo.getPermittedLabels());
		assertNull("Wrong removable_reviewers", fChangeInfo.getRemovableReviewers());
		assertNull("Wrong messages", fChangeInfo.getMessages());
		assertNull("Wrong current_revision", fChangeInfo.getCurrentRevision());
		assertNull("Wrong revisions", fChangeInfo.getRevisions());
		assertFalse("Wrong more_changes", fChangeInfo.hasMoreChanges());
		assertNull("Wrong problems", fChangeInfo.getProblems());
		assertNull("Wrong base_change", fChangeInfo.getBaseChange());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ChangeInfo#getUpdated()}.
	 */
	@Test
	public void testGetUpdated() {
		json.addProperty("updated", UPDATED);
		Reader reader = new StringReader(json.toString());
		fChangeInfo = gson.fromJson(reader, ChangeInfo.class);

		assertNull("Wrong kind", fChangeInfo.getKind());
		assertNull("Wrong id", fChangeInfo.getId());
		assertNull("Wrong project", fChangeInfo.getProject());
		assertNull("Wrong branch", fChangeInfo.getBranch());
		assertNull("Wrong topic", fChangeInfo.getTopic());
		assertNull("Wrong change_id", fChangeInfo.getChange_id());
		assertNull("Wrong subject", fChangeInfo.getSubject());
		assertNull("Wrong status", fChangeInfo.getStatus());
		assertNull("Wrong created", fChangeInfo.getCreated());
		assertEquals("Wrong updated", UPDATED, fChangeInfo.getUpdated());
		assertFalse("Wrong starred", fChangeInfo.isStarred());
		assertFalse("Wrong reviewed", fChangeInfo.isReviewed());
		assertFalse("Wrong mergeable", fChangeInfo.isMergeable());
		assertEquals("Wrong insertions", -1, fChangeInfo.getInsertions());
		assertEquals("Wrong deletions", -1, fChangeInfo.getDeletions());
		assertNull("Wrong sortkey", fChangeInfo.getSortkey());
		assertEquals("Wrong number", -1, fChangeInfo.getNumber());
		assertNull("Wrong owner", fChangeInfo.getOwner());
		assertNull("Wrong actions", fChangeInfo.getActions());
		assertNull("Wrong labels", fChangeInfo.getLabels());
		assertNull("Wrong permitted_labels", fChangeInfo.getPermittedLabels());
		assertNull("Wrong removable_reviewers", fChangeInfo.getRemovableReviewers());
		assertNull("Wrong messages", fChangeInfo.getMessages());
		assertNull("Wrong current_revision", fChangeInfo.getCurrentRevision());
		assertNull("Wrong revisions", fChangeInfo.getRevisions());
		assertFalse("Wrong more_changes", fChangeInfo.hasMoreChanges());
		assertNull("Wrong problems", fChangeInfo.getProblems());
		assertNull("Wrong base_change", fChangeInfo.getBaseChange());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ChangeInfo#isStarred()}.
	 */
	@Test
	public void testIsStarred() {
		json.addProperty("starred", STARRED);
		Reader reader = new StringReader(json.toString());
		fChangeInfo = gson.fromJson(reader, ChangeInfo.class);

		assertNull("Wrong kind", fChangeInfo.getKind());
		assertNull("Wrong id", fChangeInfo.getId());
		assertNull("Wrong project", fChangeInfo.getProject());
		assertNull("Wrong branch", fChangeInfo.getBranch());
		assertNull("Wrong topic", fChangeInfo.getTopic());
		assertNull("Wrong change_id", fChangeInfo.getChange_id());
		assertNull("Wrong subject", fChangeInfo.getSubject());
		assertNull("Wrong status", fChangeInfo.getStatus());
		assertNull("Wrong created", fChangeInfo.getCreated());
		assertNull("Wrong updated", fChangeInfo.getUpdated());
		assertEquals("Wrong starred", STARRED, fChangeInfo.isStarred());
		assertFalse("Wrong reviewed", fChangeInfo.isReviewed());
		assertFalse("Wrong mergeable", fChangeInfo.isMergeable());
		assertEquals("Wrong insertions", -1, fChangeInfo.getInsertions());
		assertEquals("Wrong deletions", -1, fChangeInfo.getDeletions());
		assertNull("Wrong sortkey", fChangeInfo.getSortkey());
		assertEquals("Wrong number", -1, fChangeInfo.getNumber());
		assertNull("Wrong owner", fChangeInfo.getOwner());
		assertNull("Wrong actions", fChangeInfo.getActions());
		assertNull("Wrong labels", fChangeInfo.getLabels());
		assertNull("Wrong permitted_labels", fChangeInfo.getPermittedLabels());
		assertNull("Wrong removable_reviewers", fChangeInfo.getRemovableReviewers());
		assertNull("Wrong messages", fChangeInfo.getMessages());
		assertNull("Wrong current_revision", fChangeInfo.getCurrentRevision());
		assertNull("Wrong revisions", fChangeInfo.getRevisions());
		assertFalse("Wrong more_changes", fChangeInfo.hasMoreChanges());
		assertNull("Wrong problems", fChangeInfo.getProblems());
		assertNull("Wrong base_change", fChangeInfo.getBaseChange());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ChangeInfo#isReviewed()}.
	 */
	@Test
	public void testIsReviewed() {
		json.addProperty("reviewed", REVIEWED);
		Reader reader = new StringReader(json.toString());
		fChangeInfo = gson.fromJson(reader, ChangeInfo.class);

		assertNull("Wrong kind", fChangeInfo.getKind());
		assertNull("Wrong id", fChangeInfo.getId());
		assertNull("Wrong project", fChangeInfo.getProject());
		assertNull("Wrong branch", fChangeInfo.getBranch());
		assertNull("Wrong topic", fChangeInfo.getTopic());
		assertNull("Wrong change_id", fChangeInfo.getChange_id());
		assertNull("Wrong subject", fChangeInfo.getSubject());
		assertNull("Wrong status", fChangeInfo.getStatus());
		assertNull("Wrong created", fChangeInfo.getCreated());
		assertNull("Wrong updated", fChangeInfo.getUpdated());
		assertFalse("Wrong starred", fChangeInfo.isStarred());
		assertEquals("Wrong reviewed", REVIEWED, fChangeInfo.isReviewed());
		assertNull("Wrong base_change", fChangeInfo.getBaseChange());
		assertFalse("Wrong mergeable", fChangeInfo.isMergeable());
		assertEquals("Wrong insertions", -1, fChangeInfo.getInsertions());
		assertEquals("Wrong deletions", -1, fChangeInfo.getDeletions());
		assertNull("Wrong sortkey", fChangeInfo.getSortkey());
		assertEquals("Wrong number", -1, fChangeInfo.getNumber());
		assertNull("Wrong owner", fChangeInfo.getOwner());
		assertNull("Wrong actions", fChangeInfo.getActions());
		assertNull("Wrong labels", fChangeInfo.getLabels());
		assertNull("Wrong permitted_labels", fChangeInfo.getPermittedLabels());
		assertNull("Wrong removable_reviewers", fChangeInfo.getRemovableReviewers());
		assertNull("Wrong messages", fChangeInfo.getMessages());
		assertNull("Wrong current_revision", fChangeInfo.getCurrentRevision());
		assertNull("Wrong revisions", fChangeInfo.getRevisions());
		assertFalse("Wrong more_changes", fChangeInfo.hasMoreChanges());
		assertNull("Wrong problems", fChangeInfo.getProblems());
		assertNull("Wrong base_change", fChangeInfo.getBaseChange());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ChangeInfo#isMergeable()}.
	 */
	@Test
	public void testIsMergeable() {
		json.addProperty("mergeable", MERGEABLE);
		Reader reader = new StringReader(json.toString());
		fChangeInfo = gson.fromJson(reader, ChangeInfo.class);

		assertNull("Wrong kind", fChangeInfo.getKind());
		assertNull("Wrong id", fChangeInfo.getId());
		assertNull("Wrong project", fChangeInfo.getProject());
		assertNull("Wrong branch", fChangeInfo.getBranch());
		assertNull("Wrong topic", fChangeInfo.getTopic());
		assertNull("Wrong change_id", fChangeInfo.getChange_id());
		assertNull("Wrong subject", fChangeInfo.getSubject());
		assertNull("Wrong status", fChangeInfo.getStatus());
		assertNull("Wrong created", fChangeInfo.getCreated());
		assertNull("Wrong updated", fChangeInfo.getUpdated());
		assertFalse("Wrong starred", fChangeInfo.isStarred());
		assertFalse("Wrong reviewed", fChangeInfo.isReviewed());
		assertEquals("Wrong mergeable", MERGEABLE, fChangeInfo.isMergeable());
		assertEquals("Wrong insertions", -1, fChangeInfo.getInsertions());
		assertEquals("Wrong deletions", -1, fChangeInfo.getDeletions());
		assertNull("Wrong sortkey", fChangeInfo.getSortkey());
		assertEquals("Wrong number", -1, fChangeInfo.getNumber());
		assertNull("Wrong owner", fChangeInfo.getOwner());
		assertNull("Wrong actions", fChangeInfo.getActions());
		assertNull("Wrong labels", fChangeInfo.getLabels());
		assertNull("Wrong permitted_labels", fChangeInfo.getPermittedLabels());
		assertNull("Wrong removable_reviewers", fChangeInfo.getRemovableReviewers());
		assertNull("Wrong messages", fChangeInfo.getMessages());
		assertNull("Wrong current_revision", fChangeInfo.getCurrentRevision());
		assertNull("Wrong revisions", fChangeInfo.getRevisions());
		assertFalse("Wrong more_changes", fChangeInfo.hasMoreChanges());
		assertNull("Wrong problems", fChangeInfo.getProblems());
		assertNull("Wrong base_change", fChangeInfo.getBaseChange());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ChangeInfo#getInsertions()}.
	 */
	@Test
	public void testGetInsertions() {
		json.addProperty("insertions", INSERTIONS);
		Reader reader = new StringReader(json.toString());
		fChangeInfo = gson.fromJson(reader, ChangeInfo.class);

		assertNull("Wrong kind", fChangeInfo.getKind());
		assertNull("Wrong id", fChangeInfo.getId());
		assertNull("Wrong project", fChangeInfo.getProject());
		assertNull("Wrong branch", fChangeInfo.getBranch());
		assertNull("Wrong topic", fChangeInfo.getTopic());
		assertNull("Wrong change_id", fChangeInfo.getChange_id());
		assertNull("Wrong subject", fChangeInfo.getSubject());
		assertNull("Wrong status", fChangeInfo.getStatus());
		assertNull("Wrong created", fChangeInfo.getCreated());
		assertNull("Wrong updated", fChangeInfo.getUpdated());
		assertFalse("Wrong starred", fChangeInfo.isStarred());
		assertFalse("Wrong reviewed", fChangeInfo.isReviewed());
		assertFalse("Wrong mergeable", fChangeInfo.isMergeable());
		assertEquals("Wrong insertions", INSERTIONS, fChangeInfo.getInsertions());
		assertEquals("Wrong deletions", -1, fChangeInfo.getDeletions());
		assertNull("Wrong sortkey", fChangeInfo.getSortkey());
		assertEquals("Wrong number", -1, fChangeInfo.getNumber());
		assertNull("Wrong owner", fChangeInfo.getOwner());
		assertNull("Wrong actions", fChangeInfo.getActions());
		assertNull("Wrong labels", fChangeInfo.getLabels());
		assertNull("Wrong permitted_labels", fChangeInfo.getPermittedLabels());
		assertNull("Wrong removable_reviewers", fChangeInfo.getRemovableReviewers());
		assertNull("Wrong messages", fChangeInfo.getMessages());
		assertNull("Wrong current_revision", fChangeInfo.getCurrentRevision());
		assertNull("Wrong revisions", fChangeInfo.getRevisions());
		assertFalse("Wrong more_changes", fChangeInfo.hasMoreChanges());
		assertNull("Wrong problems", fChangeInfo.getProblems());
		assertNull("Wrong base_change", fChangeInfo.getBaseChange());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ChangeInfo#getDeletions()}.
	 */
	@Test
	public void testGetDeletions() {
		json.addProperty("deletions", DELETIONS);
		Reader reader = new StringReader(json.toString());
		fChangeInfo = gson.fromJson(reader, ChangeInfo.class);

		assertNull("Wrong kind", fChangeInfo.getKind());
		assertNull("Wrong id", fChangeInfo.getId());
		assertNull("Wrong project", fChangeInfo.getProject());
		assertNull("Wrong branch", fChangeInfo.getBranch());
		assertNull("Wrong topic", fChangeInfo.getTopic());
		assertNull("Wrong change_id", fChangeInfo.getChange_id());
		assertNull("Wrong subject", fChangeInfo.getSubject());
		assertNull("Wrong status", fChangeInfo.getStatus());
		assertNull("Wrong created", fChangeInfo.getCreated());
		assertNull("Wrong updated", fChangeInfo.getUpdated());
		assertFalse("Wrong starred", fChangeInfo.isStarred());
		assertFalse("Wrong reviewed", fChangeInfo.isReviewed());
		assertFalse("Wrong mergeable", fChangeInfo.isMergeable());
		assertEquals("Wrong insertions", -1, fChangeInfo.getInsertions());
		assertEquals("Wrong deletions", DELETIONS, fChangeInfo.getDeletions());
		assertNull("Wrong sortkey", fChangeInfo.getSortkey());
		assertEquals("Wrong number", -1, fChangeInfo.getNumber());
		assertNull("Wrong owner", fChangeInfo.getOwner());
		assertNull("Wrong actions", fChangeInfo.getActions());
		assertNull("Wrong labels", fChangeInfo.getLabels());
		assertNull("Wrong permitted_labels", fChangeInfo.getPermittedLabels());
		assertNull("Wrong removable_reviewers", fChangeInfo.getRemovableReviewers());
		assertNull("Wrong messages", fChangeInfo.getMessages());
		assertNull("Wrong current_revision", fChangeInfo.getCurrentRevision());
		assertNull("Wrong revisions", fChangeInfo.getRevisions());
		assertFalse("Wrong more_changes", fChangeInfo.hasMoreChanges());
		assertNull("Wrong problems", fChangeInfo.getProblems());
		assertNull("Wrong base_change", fChangeInfo.getBaseChange());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ChangeInfo#getSortkey()}.
	 */
	@Test
	public void testGetSortkey() {
		json.addProperty("_sortkey", SORTKEY);
		Reader reader = new StringReader(json.toString());
		fChangeInfo = gson.fromJson(reader, ChangeInfo.class);

		assertNull("Wrong kind", fChangeInfo.getKind());
		assertNull("Wrong id", fChangeInfo.getId());
		assertNull("Wrong project", fChangeInfo.getProject());
		assertNull("Wrong branch", fChangeInfo.getBranch());
		assertNull("Wrong topic", fChangeInfo.getTopic());
		assertNull("Wrong change_id", fChangeInfo.getChange_id());
		assertNull("Wrong subject", fChangeInfo.getSubject());
		assertNull("Wrong status", fChangeInfo.getStatus());
		assertNull("Wrong created", fChangeInfo.getCreated());
		assertNull("Wrong updated", fChangeInfo.getUpdated());
		assertFalse("Wrong starred", fChangeInfo.isStarred());
		assertFalse("Wrong reviewed", fChangeInfo.isReviewed());
		assertFalse("Wrong mergeable", fChangeInfo.isMergeable());
		assertEquals("Wrong insertions", -1, fChangeInfo.getInsertions());
		assertEquals("Wrong deletions", -1, fChangeInfo.getDeletions());
		assertEquals("Wrong sortkey", SORTKEY, fChangeInfo.getSortkey());
		assertEquals("Wrong number", -1, fChangeInfo.getNumber());
		assertNull("Wrong owner", fChangeInfo.getOwner());
		assertNull("Wrong actions", fChangeInfo.getActions());
		assertNull("Wrong labels", fChangeInfo.getLabels());
		assertNull("Wrong permitted_labels", fChangeInfo.getPermittedLabels());
		assertNull("Wrong removable_reviewers", fChangeInfo.getRemovableReviewers());
		assertNull("Wrong messages", fChangeInfo.getMessages());
		assertNull("Wrong current_revision", fChangeInfo.getCurrentRevision());
		assertNull("Wrong revisions", fChangeInfo.getRevisions());
		assertFalse("Wrong more_changes", fChangeInfo.hasMoreChanges());
		assertNull("Wrong problems", fChangeInfo.getProblems());
		assertNull("Wrong base_change", fChangeInfo.getBaseChange());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ChangeInfo#getNumber()}.
	 */
	@Test
	public void testGetNumber() {
		json.addProperty("_number", NUMBER);
		Reader reader = new StringReader(json.toString());
		fChangeInfo = gson.fromJson(reader, ChangeInfo.class);

		assertNull("Wrong kind", fChangeInfo.getKind());
		assertNull("Wrong id", fChangeInfo.getId());
		assertNull("Wrong project", fChangeInfo.getProject());
		assertNull("Wrong branch", fChangeInfo.getBranch());
		assertNull("Wrong topic", fChangeInfo.getTopic());
		assertNull("Wrong change_id", fChangeInfo.getChange_id());
		assertNull("Wrong subject", fChangeInfo.getSubject());
		assertNull("Wrong status", fChangeInfo.getStatus());
		assertNull("Wrong created", fChangeInfo.getCreated());
		assertNull("Wrong updated", fChangeInfo.getUpdated());
		assertFalse("Wrong starred", fChangeInfo.isStarred());
		assertFalse("Wrong reviewed", fChangeInfo.isReviewed());
		assertFalse("Wrong mergeable", fChangeInfo.isMergeable());
		assertEquals("Wrong insertions", -1, fChangeInfo.getInsertions());
		assertEquals("Wrong deletions", -1, fChangeInfo.getDeletions());
		assertNull("Wrong sortkey", fChangeInfo.getSortkey());
		assertEquals("Wrong number", NUMBER, fChangeInfo.getNumber());
		assertNull("Wrong owner", fChangeInfo.getOwner());
		assertNull("Wrong actions", fChangeInfo.getActions());
		assertNull("Wrong labels", fChangeInfo.getLabels());
		assertNull("Wrong permitted_labels", fChangeInfo.getPermittedLabels());
		assertNull("Wrong removable_reviewers", fChangeInfo.getRemovableReviewers());
		assertNull("Wrong messages", fChangeInfo.getMessages());
		assertNull("Wrong current_revision", fChangeInfo.getCurrentRevision());
		assertNull("Wrong revisions", fChangeInfo.getRevisions());
		assertFalse("Wrong more_changes", fChangeInfo.hasMoreChanges());
		assertNull("Wrong problems", fChangeInfo.getProblems());
		assertNull("Wrong base_change", fChangeInfo.getBaseChange());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ChangeInfo#getOwner()}.
	 */
	@Test
	public void testGetOwner() {
		JsonObject owner = new JsonObject();
		json.add("owner", owner);
		Reader reader = new StringReader(json.toString());
		fChangeInfo = gson.fromJson(reader, ChangeInfo.class);

		assertNull("Wrong kind", fChangeInfo.getKind());
		assertNull("Wrong id", fChangeInfo.getId());
		assertNull("Wrong project", fChangeInfo.getProject());
		assertNull("Wrong branch", fChangeInfo.getBranch());
		assertNull("Wrong topic", fChangeInfo.getTopic());
		assertNull("Wrong change_id", fChangeInfo.getChange_id());
		assertNull("Wrong subject", fChangeInfo.getSubject());
		assertNull("Wrong status", fChangeInfo.getStatus());
		assertNull("Wrong created", fChangeInfo.getCreated());
		assertNull("Wrong updated", fChangeInfo.getUpdated());
		assertFalse("Wrong starred", fChangeInfo.isStarred());
		assertFalse("Wrong reviewed", fChangeInfo.isReviewed());
		assertFalse("Wrong mergeable", fChangeInfo.isMergeable());
		assertEquals("Wrong insertions", -1, fChangeInfo.getInsertions());
		assertEquals("Wrong deletions", -1, fChangeInfo.getDeletions());
		assertNull("Wrong sortkey", fChangeInfo.getSortkey());
		assertEquals("Wrong number", -1, fChangeInfo.getNumber());
		assertEquals("Wrong owner", OWNER, fChangeInfo.getOwner());
		assertNull("Wrong actions", fChangeInfo.getActions());
		assertNull("Wrong labels", fChangeInfo.getLabels());
		assertNull("Wrong permitted_labels", fChangeInfo.getPermittedLabels());
		assertNull("Wrong removable_reviewers", fChangeInfo.getRemovableReviewers());
		assertNull("Wrong messages", fChangeInfo.getMessages());
		assertNull("Wrong current_revision", fChangeInfo.getCurrentRevision());
		assertNull("Wrong revisions", fChangeInfo.getRevisions());
		assertFalse("Wrong more_changes", fChangeInfo.hasMoreChanges());
		assertNull("Wrong problems", fChangeInfo.getProblems());
		assertNull("Wrong base_change", fChangeInfo.getBaseChange());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ChangeInfo#getActions()}.
	 */
	@Test
	public void testGetActions() {
		JsonObject actions = new JsonObject();
		json.add("actions", actions);
		Reader reader = new StringReader(json.toString());
		fChangeInfo = gson.fromJson(reader, ChangeInfo.class);

		assertNull("Wrong kind", fChangeInfo.getKind());
		assertNull("Wrong id", fChangeInfo.getId());
		assertNull("Wrong project", fChangeInfo.getProject());
		assertNull("Wrong branch", fChangeInfo.getBranch());
		assertNull("Wrong topic", fChangeInfo.getTopic());
		assertNull("Wrong change_id", fChangeInfo.getChange_id());
		assertNull("Wrong subject", fChangeInfo.getSubject());
		assertNull("Wrong status", fChangeInfo.getStatus());
		assertNull("Wrong created", fChangeInfo.getCreated());
		assertNull("Wrong updated", fChangeInfo.getUpdated());
		assertFalse("Wrong starred", fChangeInfo.isStarred());
		assertFalse("Wrong reviewed", fChangeInfo.isReviewed());
		assertFalse("Wrong mergeable", fChangeInfo.isMergeable());
		assertEquals("Wrong insertions", -1, fChangeInfo.getInsertions());
		assertEquals("Wrong deletions", -1, fChangeInfo.getDeletions());
		assertNull("Wrong sortkey", fChangeInfo.getSortkey());
		assertEquals("Wrong number", -1, fChangeInfo.getNumber());
		assertNull("Wrong owner", fChangeInfo.getOwner());
		assertEquals("Wrong actions", ACTIONS, fChangeInfo.getActions());
		assertNull("Wrong labels", fChangeInfo.getLabels());
		assertNull("Wrong permitted_labels", fChangeInfo.getPermittedLabels());
		assertNull("Wrong removable_reviewers", fChangeInfo.getRemovableReviewers());
		assertNull("Wrong messages", fChangeInfo.getMessages());
		assertNull("Wrong current_revision", fChangeInfo.getCurrentRevision());
		assertNull("Wrong revisions", fChangeInfo.getRevisions());
		assertFalse("Wrong more_changes", fChangeInfo.hasMoreChanges());
		assertNull("Wrong problems", fChangeInfo.getProblems());
		assertNull("Wrong base_change", fChangeInfo.getBaseChange());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ChangeInfo#getLabels()}.
	 */
	@Test
	public void testGetLabels() {
		JsonObject labels = new JsonObject();
		json.add("labels", labels);
		Reader reader = new StringReader(json.toString());
		fChangeInfo = gson.fromJson(reader, ChangeInfo.class);

		assertNull("Wrong kind", fChangeInfo.getKind());
		assertNull("Wrong id", fChangeInfo.getId());
		assertNull("Wrong project", fChangeInfo.getProject());
		assertNull("Wrong branch", fChangeInfo.getBranch());
		assertNull("Wrong topic", fChangeInfo.getTopic());
		assertNull("Wrong change_id", fChangeInfo.getChange_id());
		assertNull("Wrong subject", fChangeInfo.getSubject());
		assertNull("Wrong status", fChangeInfo.getStatus());
		assertNull("Wrong created", fChangeInfo.getCreated());
		assertNull("Wrong updated", fChangeInfo.getUpdated());
		assertFalse("Wrong starred", fChangeInfo.isStarred());
		assertFalse("Wrong reviewed", fChangeInfo.isReviewed());
		assertFalse("Wrong mergeable", fChangeInfo.isMergeable());
		assertEquals("Wrong insertions", -1, fChangeInfo.getInsertions());
		assertEquals("Wrong deletions", -1, fChangeInfo.getDeletions());
		assertNull("Wrong sortkey", fChangeInfo.getSortkey());
		assertEquals("Wrong number", -1, fChangeInfo.getNumber());
		assertNull("Wrong owner", fChangeInfo.getOwner());
		assertNull("Wrong actions", fChangeInfo.getActions());
		assertEquals("Wrong labels", LABELS, fChangeInfo.getLabels());
		assertNull("Wrong permitted_labels", fChangeInfo.getPermittedLabels());
		assertNull("Wrong removable_reviewers", fChangeInfo.getRemovableReviewers());
		assertNull("Wrong messages", fChangeInfo.getMessages());
		assertNull("Wrong current_revision", fChangeInfo.getCurrentRevision());
		assertNull("Wrong revisions", fChangeInfo.getRevisions());
		assertFalse("Wrong more_changes", fChangeInfo.hasMoreChanges());
		assertNull("Wrong problems", fChangeInfo.getProblems());
		assertNull("Wrong base_change", fChangeInfo.getBaseChange());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ChangeInfo#getPermittedLabels()}.
	 */
	@Test
	public void testGetPermittedLabels() {
		JsonObject permitted_labels = new JsonObject();
		json.add("permitted_labels", permitted_labels);
		Reader reader = new StringReader(json.toString());
		fChangeInfo = gson.fromJson(reader, ChangeInfo.class);

		assertNull("Wrong kind", fChangeInfo.getKind());
		assertNull("Wrong id", fChangeInfo.getId());
		assertNull("Wrong project", fChangeInfo.getProject());
		assertNull("Wrong branch", fChangeInfo.getBranch());
		assertNull("Wrong topic", fChangeInfo.getTopic());
		assertNull("Wrong change_id", fChangeInfo.getChange_id());
		assertNull("Wrong subject", fChangeInfo.getSubject());
		assertNull("Wrong status", fChangeInfo.getStatus());
		assertNull("Wrong created", fChangeInfo.getCreated());
		assertNull("Wrong updated", fChangeInfo.getUpdated());
		assertFalse("Wrong starred", fChangeInfo.isStarred());
		assertFalse("Wrong reviewed", fChangeInfo.isReviewed());
		assertFalse("Wrong mergeable", fChangeInfo.isMergeable());
		assertEquals("Wrong insertions", -1, fChangeInfo.getInsertions());
		assertEquals("Wrong deletions", -1, fChangeInfo.getDeletions());
		assertNull("Wrong sortkey", fChangeInfo.getSortkey());
		assertEquals("Wrong number", -1, fChangeInfo.getNumber());
		assertNull("Wrong owner", fChangeInfo.getOwner());
		assertNull("Wrong actions", fChangeInfo.getActions());
		assertNull("Wrong labels", fChangeInfo.getLabels());
		assertEquals("Wrong permitted_labels", PERMITTED_LABELS, fChangeInfo.getPermittedLabels());
		assertNull("Wrong removable_reviewers", fChangeInfo.getRemovableReviewers());
		assertNull("Wrong messages", fChangeInfo.getMessages());
		assertNull("Wrong current_revision", fChangeInfo.getCurrentRevision());
		assertNull("Wrong revisions", fChangeInfo.getRevisions());
		assertFalse("Wrong more_changes", fChangeInfo.hasMoreChanges());
		assertNull("Wrong problems", fChangeInfo.getProblems());
		assertNull("Wrong base_change", fChangeInfo.getBaseChange());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ChangeInfo#getRemovableReviewers()}.
	 */
	@Test
	public void testGetRemovableReviewers() {
		JsonArray removable_reviewers = new JsonArray();
		json.add("removable_reviewers", removable_reviewers);
		Reader reader = new StringReader(json.toString());
		fChangeInfo = gson.fromJson(reader, ChangeInfo.class);

		assertNull("Wrong kind", fChangeInfo.getKind());
		assertNull("Wrong id", fChangeInfo.getId());
		assertNull("Wrong project", fChangeInfo.getProject());
		assertNull("Wrong branch", fChangeInfo.getBranch());
		assertNull("Wrong topic", fChangeInfo.getTopic());
		assertNull("Wrong change_id", fChangeInfo.getChange_id());
		assertNull("Wrong subject", fChangeInfo.getSubject());
		assertNull("Wrong status", fChangeInfo.getStatus());
		assertNull("Wrong created", fChangeInfo.getCreated());
		assertNull("Wrong updated", fChangeInfo.getUpdated());
		assertFalse("Wrong starred", fChangeInfo.isStarred());
		assertFalse("Wrong reviewed", fChangeInfo.isReviewed());
		assertFalse("Wrong mergeable", fChangeInfo.isMergeable());
		assertEquals("Wrong insertions", -1, fChangeInfo.getInsertions());
		assertEquals("Wrong deletions", -1, fChangeInfo.getDeletions());
		assertNull("Wrong sortkey", fChangeInfo.getSortkey());
		assertEquals("Wrong number", -1, fChangeInfo.getNumber());
		assertNull("Wrong owner", fChangeInfo.getOwner());
		assertNull("Wrong actions", fChangeInfo.getActions());
		assertNull("Wrong labels", fChangeInfo.getLabels());
		assertNull("Wrong permitted_labels", fChangeInfo.getPermittedLabels());
		assertEquals("Wrong removable_reviewers", REMOVABLE_REVIEWERS, fChangeInfo.getRemovableReviewers());
		assertNull("Wrong messages", fChangeInfo.getMessages());
		assertNull("Wrong current_revision", fChangeInfo.getCurrentRevision());
		assertNull("Wrong revisions", fChangeInfo.getRevisions());
		assertFalse("Wrong more_changes", fChangeInfo.hasMoreChanges());
		assertNull("Wrong problems", fChangeInfo.getProblems());
		assertNull("Wrong base_change", fChangeInfo.getBaseChange());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ChangeInfo#getMessages()}.
	 */
	@Test
	public void testGetMessages() {
		JsonArray messages = new JsonArray();
		json.add("messages", messages);
		Reader reader = new StringReader(json.toString());
		fChangeInfo = gson.fromJson(reader, ChangeInfo.class);

		assertNull("Wrong kind", fChangeInfo.getKind());
		assertNull("Wrong id", fChangeInfo.getId());
		assertNull("Wrong project", fChangeInfo.getProject());
		assertNull("Wrong branch", fChangeInfo.getBranch());
		assertNull("Wrong topic", fChangeInfo.getTopic());
		assertNull("Wrong change_id", fChangeInfo.getChange_id());
		assertNull("Wrong subject", fChangeInfo.getSubject());
		assertNull("Wrong status", fChangeInfo.getStatus());
		assertNull("Wrong created", fChangeInfo.getCreated());
		assertNull("Wrong updated", fChangeInfo.getUpdated());
		assertFalse("Wrong starred", fChangeInfo.isStarred());
		assertFalse("Wrong reviewed", fChangeInfo.isReviewed());
		assertFalse("Wrong mergeable", fChangeInfo.isMergeable());
		assertEquals("Wrong insertions", -1, fChangeInfo.getInsertions());
		assertEquals("Wrong deletions", -1, fChangeInfo.getDeletions());
		assertNull("Wrong sortkey", fChangeInfo.getSortkey());
		assertEquals("Wrong number", -1, fChangeInfo.getNumber());
		assertNull("Wrong owner", fChangeInfo.getOwner());
		assertNull("Wrong actions", fChangeInfo.getActions());
		assertNull("Wrong labels", fChangeInfo.getLabels());
		assertNull("Wrong permitted_labels", fChangeInfo.getPermittedLabels());
		assertNull("Wrong removable_reviewers", fChangeInfo.getRemovableReviewers());
		assertEquals("Wrong messages", MESSAGES, fChangeInfo.getMessages());
		assertNull("Wrong current_revision", fChangeInfo.getCurrentRevision());
		assertNull("Wrong revisions", fChangeInfo.getRevisions());
		assertFalse("Wrong more_changes", fChangeInfo.hasMoreChanges());
		assertNull("Wrong problems", fChangeInfo.getProblems());
		assertNull("Wrong base_change", fChangeInfo.getBaseChange());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ChangeInfo#getCurrentRevision()}.
	 */
	@Test
	public void testGetCurrentRevision() {
		json.addProperty("current_revision", CURRENT_REVISION);
		Reader reader = new StringReader(json.toString());
		fChangeInfo = gson.fromJson(reader, ChangeInfo.class);

		assertNull("Wrong kind", fChangeInfo.getKind());
		assertNull("Wrong id", fChangeInfo.getId());
		assertNull("Wrong project", fChangeInfo.getProject());
		assertNull("Wrong branch", fChangeInfo.getBranch());
		assertNull("Wrong topic", fChangeInfo.getTopic());
		assertNull("Wrong change_id", fChangeInfo.getChange_id());
		assertNull("Wrong subject", fChangeInfo.getSubject());
		assertNull("Wrong status", fChangeInfo.getStatus());
		assertNull("Wrong created", fChangeInfo.getCreated());
		assertNull("Wrong updated", fChangeInfo.getUpdated());
		assertFalse("Wrong starred", fChangeInfo.isStarred());
		assertFalse("Wrong reviewed", fChangeInfo.isReviewed());
		assertFalse("Wrong mergeable", fChangeInfo.isMergeable());
		assertEquals("Wrong insertions", -1, fChangeInfo.getInsertions());
		assertEquals("Wrong deletions", -1, fChangeInfo.getDeletions());
		assertNull("Wrong sortkey", fChangeInfo.getSortkey());
		assertEquals("Wrong number", -1, fChangeInfo.getNumber());
		assertNull("Wrong owner", fChangeInfo.getOwner());
		assertNull("Wrong actions", fChangeInfo.getActions());
		assertNull("Wrong labels", fChangeInfo.getLabels());
		assertNull("Wrong permitted_labels", fChangeInfo.getPermittedLabels());
		assertNull("Wrong removable_reviewers", fChangeInfo.getRemovableReviewers());
		assertNull("Wrong messages", fChangeInfo.getMessages());
		assertEquals("Wrong current_revision", CURRENT_REVISION, fChangeInfo.getCurrentRevision());
		assertNull("Wrong revisions", fChangeInfo.getRevisions());
		assertFalse("Wrong more_changes", fChangeInfo.hasMoreChanges());
		assertNull("Wrong problems", fChangeInfo.getProblems());
		assertNull("Wrong base_change", fChangeInfo.getBaseChange());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ChangeInfo#getRevisions()}.
	 */
	@Test
	public void testGetRevisions() {
		JsonObject revisions = new JsonObject();
		json.add("revisions", revisions);
		Reader reader = new StringReader(json.toString());
		fChangeInfo = gson.fromJson(reader, ChangeInfo.class);

		assertNull("Wrong kind", fChangeInfo.getKind());
		assertNull("Wrong id", fChangeInfo.getId());
		assertNull("Wrong project", fChangeInfo.getProject());
		assertNull("Wrong branch", fChangeInfo.getBranch());
		assertNull("Wrong topic", fChangeInfo.getTopic());
		assertNull("Wrong change_id", fChangeInfo.getChange_id());
		assertNull("Wrong subject", fChangeInfo.getSubject());
		assertNull("Wrong status", fChangeInfo.getStatus());
		assertNull("Wrong created", fChangeInfo.getCreated());
		assertNull("Wrong updated", fChangeInfo.getUpdated());
		assertFalse("Wrong starred", fChangeInfo.isStarred());
		assertFalse("Wrong reviewed", fChangeInfo.isReviewed());
		assertFalse("Wrong mergeable", fChangeInfo.isMergeable());
		assertEquals("Wrong insertions", -1, fChangeInfo.getInsertions());
		assertEquals("Wrong deletions", -1, fChangeInfo.getDeletions());
		assertNull("Wrong sortkey", fChangeInfo.getSortkey());
		assertEquals("Wrong number", -1, fChangeInfo.getNumber());
		assertNull("Wrong owner", fChangeInfo.getOwner());
		assertNull("Wrong actions", fChangeInfo.getActions());
		assertNull("Wrong labels", fChangeInfo.getLabels());
		assertNull("Wrong permitted_labels", fChangeInfo.getPermittedLabels());
		assertNull("Wrong removable_reviewers", fChangeInfo.getRemovableReviewers());
		assertNull("Wrong messages", fChangeInfo.getMessages());
		assertNull("Wrong current_revision", fChangeInfo.getCurrentRevision());
		assertEquals("Wrong revisions", REVISIONS, fChangeInfo.getRevisions());
		assertFalse("Wrong more_changes", fChangeInfo.hasMoreChanges());
		assertNull("Wrong problems", fChangeInfo.getProblems());
		assertNull("Wrong base_change", fChangeInfo.getBaseChange());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ChangeInfo#hasMoreChanges()}.
	 */
	@Test
	public void testHasMoreChanges() {
		json.addProperty("_more_changes", MORE_CHANGES);
		Reader reader = new StringReader(json.toString());
		fChangeInfo = gson.fromJson(reader, ChangeInfo.class);

		assertNull("Wrong kind", fChangeInfo.getKind());
		assertNull("Wrong id", fChangeInfo.getId());
		assertNull("Wrong project", fChangeInfo.getProject());
		assertNull("Wrong branch", fChangeInfo.getBranch());
		assertNull("Wrong topic", fChangeInfo.getTopic());
		assertNull("Wrong change_id", fChangeInfo.getChange_id());
		assertNull("Wrong subject", fChangeInfo.getSubject());
		assertNull("Wrong status", fChangeInfo.getStatus());
		assertNull("Wrong created", fChangeInfo.getCreated());
		assertNull("Wrong updated", fChangeInfo.getUpdated());
		assertFalse("Wrong starred", fChangeInfo.isStarred());
		assertFalse("Wrong reviewed", fChangeInfo.isReviewed());
		assertFalse("Wrong mergeable", fChangeInfo.isMergeable());
		assertEquals("Wrong insertions", -1, fChangeInfo.getInsertions());
		assertEquals("Wrong deletions", -1, fChangeInfo.getDeletions());
		assertNull("Wrong sortkey", fChangeInfo.getSortkey());
		assertEquals("Wrong number", -1, fChangeInfo.getNumber());
		assertNull("Wrong owner", fChangeInfo.getOwner());
		assertNull("Wrong actions", fChangeInfo.getActions());
		assertNull("Wrong labels", fChangeInfo.getLabels());
		assertNull("Wrong permitted_labels", fChangeInfo.getPermittedLabels());
		assertNull("Wrong removable_reviewers", fChangeInfo.getRemovableReviewers());
		assertNull("Wrong messages", fChangeInfo.getMessages());
		assertNull("Wrong current_revision", fChangeInfo.getCurrentRevision());
		assertNull("Wrong revisions", fChangeInfo.getRevisions());
		assertEquals("Wrong more_changes", MORE_CHANGES, fChangeInfo.hasMoreChanges());
		assertNull("Wrong problems", fChangeInfo.getProblems());
		assertNull("Wrong base_change", fChangeInfo.getBaseChange());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ChangeInfo#getProblems()}.
	 */
	@Test
	public void testGetProblems() {
		JsonArray problems = new JsonArray();
		json.add("problems", problems);
		Reader reader = new StringReader(json.toString());
		fChangeInfo = gson.fromJson(reader, ChangeInfo.class);

		assertNull("Wrong kind", fChangeInfo.getKind());
		assertNull("Wrong id", fChangeInfo.getId());
		assertNull("Wrong project", fChangeInfo.getProject());
		assertNull("Wrong branch", fChangeInfo.getBranch());
		assertNull("Wrong topic", fChangeInfo.getTopic());
		assertNull("Wrong change_id", fChangeInfo.getChange_id());
		assertNull("Wrong subject", fChangeInfo.getSubject());
		assertNull("Wrong status", fChangeInfo.getStatus());
		assertNull("Wrong created", fChangeInfo.getCreated());
		assertNull("Wrong updated", fChangeInfo.getUpdated());
		assertFalse("Wrong starred", fChangeInfo.isStarred());
		assertFalse("Wrong reviewed", fChangeInfo.isReviewed());
		assertFalse("Wrong mergeable", fChangeInfo.isMergeable());
		assertEquals("Wrong insertions", -1, fChangeInfo.getInsertions());
		assertEquals("Wrong deletions", -1, fChangeInfo.getDeletions());
		assertNull("Wrong sortkey", fChangeInfo.getSortkey());
		assertEquals("Wrong number", -1, fChangeInfo.getNumber());
		assertNull("Wrong owner", fChangeInfo.getOwner());
		assertNull("Wrong actions", fChangeInfo.getActions());
		assertNull("Wrong labels", fChangeInfo.getLabels());
		assertNull("Wrong permitted_labels", fChangeInfo.getPermittedLabels());
		assertNull("Wrong removable_reviewers", fChangeInfo.getRemovableReviewers());
		assertNull("Wrong messages", fChangeInfo.getMessages());
		assertNull("Wrong current_revision", fChangeInfo.getCurrentRevision());
		assertNull("Wrong revisions", fChangeInfo.getRevisions());
		assertFalse("Wrong more_changes", fChangeInfo.hasMoreChanges());
		assertEquals("Wrong problems", PROBLEMS, fChangeInfo.getProblems());
		assertNull("Wrong base_change", fChangeInfo.getBaseChange());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ChangeInfo#getBaseChange()}.
	 */
	@Test
	public void testGetBaseChange() {
		json.addProperty("base_change", BASE_CHANGE);
		Reader reader = new StringReader(json.toString());
		fChangeInfo = gson.fromJson(reader, ChangeInfo.class);

		assertNull("Wrong kind", fChangeInfo.getKind());
		assertNull("Wrong id", fChangeInfo.getId());
		assertNull("Wrong project", fChangeInfo.getProject());
		assertNull("Wrong branch", fChangeInfo.getBranch());
		assertNull("Wrong topic", fChangeInfo.getTopic());
		assertNull("Wrong change_id", fChangeInfo.getChange_id());
		assertNull("Wrong subject", fChangeInfo.getSubject());
		assertNull("Wrong status", fChangeInfo.getStatus());
		assertNull("Wrong created", fChangeInfo.getCreated());
		assertNull("Wrong updated", fChangeInfo.getUpdated());
		assertFalse("Wrong starred", fChangeInfo.isStarred());
		assertFalse("Wrong reviewed", fChangeInfo.isReviewed());
		assertFalse("Wrong mergeable", fChangeInfo.isMergeable());
		assertEquals("Wrong insertions", -1, fChangeInfo.getInsertions());
		assertEquals("Wrong deletions", -1, fChangeInfo.getDeletions());
		assertNull("Wrong sortkey", fChangeInfo.getSortkey());
		assertEquals("Wrong number", -1, fChangeInfo.getNumber());
		assertNull("Wrong owner", fChangeInfo.getOwner());
		assertNull("Wrong actions", fChangeInfo.getActions());
		assertNull("Wrong labels", fChangeInfo.getLabels());
		assertNull("Wrong permitted_labels", fChangeInfo.getPermittedLabels());
		assertNull("Wrong removable_reviewers", fChangeInfo.getRemovableReviewers());
		assertNull("Wrong messages", fChangeInfo.getMessages());
		assertNull("Wrong current_revision", fChangeInfo.getCurrentRevision());
		assertNull("Wrong revisions", fChangeInfo.getRevisions());
		assertFalse("Wrong more_changes", fChangeInfo.hasMoreChanges());
		assertNull("Wrong problems", fChangeInfo.getProblems());
		assertEquals("Wrong base_change", BASE_CHANGE, fChangeInfo.getBaseChange());
	}

	// ------------------------------------------------------------------------
	// Test all
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ChangeInfo}.
	 */
	@Test
	public void testAllFields() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fChangeInfo = gson.fromJson(reader, ChangeInfo.class);

		assertEquals("Wrong kind", KIND, fChangeInfo.getKind());
		assertEquals("Wrong id", ID, fChangeInfo.getId());
		assertEquals("Wrong project", PROJECT, fChangeInfo.getProject());
		assertEquals("Wrong branch", BRANCH, fChangeInfo.getBranch());
		assertEquals("Wrong topic", TOPIC, fChangeInfo.getTopic());
		assertEquals("Wrong change_id", CHANGE_ID, fChangeInfo.getChange_id());
		assertEquals("Wrong subject", SUBJECT, fChangeInfo.getSubject());
		assertEquals("Wrong status", STATUS, fChangeInfo.getStatus());
		assertEquals("Wrong created", CREATED, fChangeInfo.getCreated());
		assertEquals("Wrong updated", UPDATED, fChangeInfo.getUpdated());
		assertEquals("Wrong starred", STARRED, fChangeInfo.isStarred());
		assertEquals("Wrong reviewed", REVIEWED, fChangeInfo.isReviewed());
		assertEquals("Wrong mergeable", MERGEABLE, fChangeInfo.isMergeable());
		assertEquals("Wrong insertions", INSERTIONS, fChangeInfo.getInsertions());
		assertEquals("Wrong deletions", DELETIONS, fChangeInfo.getDeletions());
		assertEquals("Wrong sortkey", SORTKEY, fChangeInfo.getSortkey());
		assertEquals("Wrong number", NUMBER, fChangeInfo.getNumber());

		assertEquals("Wrong owner", OWNER, fChangeInfo.getOwner());
		assertEquals("Wrong actions", ACTIONS, fChangeInfo.getActions());
		assertEquals("Wrong labels", LABELS, fChangeInfo.getLabels());
		assertEquals("Wrong permitted_labels", PERMITTED_LABELS, fChangeInfo.getPermittedLabels());
		assertEquals("Wrong removable_reviewers", REMOVABLE_REVIEWERS, fChangeInfo.getRemovableReviewers());
		assertEquals("Wrong messages", MESSAGES, fChangeInfo.getMessages());

		assertEquals("Wrong current_revision", CURRENT_REVISION, fChangeInfo.getCurrentRevision());
		assertEquals("Wrong revisions", REVISIONS, fChangeInfo.getRevisions());
		assertEquals("Wrong more_changes", MORE_CHANGES, fChangeInfo.hasMoreChanges());
		assertEquals("Wrong problems", PROBLEMS, fChangeInfo.getProblems());
		assertEquals("Wrong base_change", BASE_CHANGE, fChangeInfo.getBaseChange());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ChangeInfo}.
	 */
	@Test
	public void testExtraField() {
		setAllFields();
		json.addProperty("extra", "extra_property");
		Reader reader = new StringReader(json.toString());
		fChangeInfo = gson.fromJson(reader, ChangeInfo.class);

		assertEquals("Wrong kind", KIND, fChangeInfo.getKind());
		assertEquals("Wrong id", ID, fChangeInfo.getId());
		assertEquals("Wrong project", PROJECT, fChangeInfo.getProject());
		assertEquals("Wrong branch", BRANCH, fChangeInfo.getBranch());
		assertEquals("Wrong topic", TOPIC, fChangeInfo.getTopic());
		assertEquals("Wrong change_id", CHANGE_ID, fChangeInfo.getChange_id());
		assertEquals("Wrong subject", SUBJECT, fChangeInfo.getSubject());
		assertEquals("Wrong status", STATUS, fChangeInfo.getStatus());
		assertEquals("Wrong created", CREATED, fChangeInfo.getCreated());
		assertEquals("Wrong updated", UPDATED, fChangeInfo.getUpdated());
		assertEquals("Wrong starred", STARRED, fChangeInfo.isStarred());
		assertEquals("Wrong reviewed", REVIEWED, fChangeInfo.isReviewed());
		assertEquals("Wrong mergeable", MERGEABLE, fChangeInfo.isMergeable());
		assertEquals("Wrong insertions", INSERTIONS, fChangeInfo.getInsertions());
		assertEquals("Wrong deletions", DELETIONS, fChangeInfo.getDeletions());
		assertEquals("Wrong sortkey", SORTKEY, fChangeInfo.getSortkey());
		assertEquals("Wrong number", NUMBER, fChangeInfo.getNumber());

		assertEquals("Wrong owner", OWNER, fChangeInfo.getOwner());
		assertEquals("Wrong actions", ACTIONS, fChangeInfo.getActions());
		assertEquals("Wrong labels", LABELS, fChangeInfo.getLabels());
		assertEquals("Wrong permitted_labels", PERMITTED_LABELS, fChangeInfo.getPermittedLabels());
		assertEquals("Wrong removable_reviewers", REMOVABLE_REVIEWERS, fChangeInfo.getRemovableReviewers());
		assertEquals("Wrong messages", MESSAGES, fChangeInfo.getMessages());

		assertEquals("Wrong current_revision", CURRENT_REVISION, fChangeInfo.getCurrentRevision());
		assertEquals("Wrong revisions", REVISIONS, fChangeInfo.getRevisions());
		assertEquals("Wrong more_changes", MORE_CHANGES, fChangeInfo.hasMoreChanges());
		assertEquals("Wrong problems", PROBLEMS, fChangeInfo.getProblems());
		assertEquals("Wrong base_change", BASE_CHANGE, fChangeInfo.getBaseChange());
	}

	// ------------------------------------------------------------------------
	// Object
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ChangeInfo#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fChangeInfo = gson.fromJson(reader, ChangeInfo.class);

		assertEquals("Wrong hash code", HASH_CODE, fChangeInfo.hashCode());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ChangeInfo#equals(java.lang.Object)}.
	 */
	// @Test
	public void testEqualsObject() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ChangeInfo#toString()}.
	 */
	@Test
	public void testToString() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fChangeInfo = gson.fromJson(reader, ChangeInfo.class);

		assertEquals("Wrong hash code", TO_STRING, fChangeInfo.toString());
	}

}
