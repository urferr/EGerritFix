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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.Reader;
import java.io.StringReader;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.egerrit.core.rest.ActionInfo;
import org.eclipse.egerrit.core.rest.CommitInfo;
import org.eclipse.egerrit.core.rest.FetchInfo;
import org.eclipse.egerrit.core.rest.FileInfo;
import org.eclipse.egerrit.core.rest.RevisionInfo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/**
 * Test suite for {@link org.eclipse.egerrit.core.rest.RevisionInfo}
 */
@SuppressWarnings("nls")
public class RevisionInfoTest {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	private static final boolean DRAFT = true;

	private static final boolean HAS_DRAFT_COMMENTS = true;

	private static final int NUMBER = 5;

	private static final String REF = "/refs/changes/10/12345/1";

	private static final Map<String, FetchInfo> FETCH = new LinkedHashMap<String, FetchInfo>();

	private static final CommitInfo COMMIT = new CommitInfo();

	private static final Map<String, FileInfo> FILES = new LinkedHashMap<String, FileInfo>();

	private static final Map<String, ActionInfo> ACTIONS = new LinkedHashMap<String, ActionInfo>();

	private static final Boolean REVIEWED = true;

	private static final int HASH_CODE = 1397094371;

	private static final String TO_STRING = "RevisionInfo [" + "draft=" + DRAFT + ", has_draft_comments="
			+ HAS_DRAFT_COMMENTS + ", _number=" + NUMBER + ", ref=" + REF + ", fetch=" + FETCH + ", commit=" + COMMIT
			+ ", files=" + FILES + ", actions=" + ACTIONS + ", reviewed=" + REVIEWED + "]";

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
	private RevisionInfo fRevisionInfo = null;

	// ------------------------------------------------------------------------
	// Helper methods
	// ------------------------------------------------------------------------

	@Before
	public void setUp() throws Exception {
		gson = new GsonBuilder().create();
		json = new JsonObject();
	}

	private void setAllFields() {
		json.addProperty("draft", DRAFT);
		json.addProperty("has_draft_comments", HAS_DRAFT_COMMENTS);
		json.addProperty("_number", NUMBER);
		json.addProperty("ref", REF);
		JsonObject fetch = new JsonObject();
		json.add("fetch", fetch);
		JsonObject commit = new JsonObject();
		json.add("commit", commit);
		JsonObject files = new JsonObject();
		json.add("files", files);
		JsonObject actions = new JsonObject();
		json.add("actions", actions);
		json.addProperty("reviewed", REVIEWED);
	}

	@After
	public void tearDown() throws Exception {
	}

	// ------------------------------------------------------------------------
	// Test cases
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.RevisionInfo#isDraft()}.
	 */
	@Test
	public void testIsDraft() {
		json.addProperty("draft", DRAFT);
		Reader reader = new StringReader(json.toString());
		fRevisionInfo = gson.fromJson(reader, RevisionInfo.class);

		assertTrue("Wrong draft", fRevisionInfo.isDraft());
		assertFalse("Wrong has_draft_comments", fRevisionInfo.hasDraftComments());
		assertEquals("Wrong _number", 0, fRevisionInfo.getNumber());
		assertNull("Wrong ref", fRevisionInfo.getRef());
		assertNull("Wrong fetch", fRevisionInfo.getFetch());
		assertNull("Wrong commit", fRevisionInfo.getCommit());
		assertNull("Wrong files", fRevisionInfo.getFiles());
		assertNull("Wrong actions", fRevisionInfo.getActions());
		assertNull("Wrong reviewed", fRevisionInfo.getReviewed());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.RevisionInfo#hasDraftComments()}.
	 */
	@Test
	public void testHasDraftComments() {
		json.addProperty("has_draft_comments", HAS_DRAFT_COMMENTS);
		Reader reader = new StringReader(json.toString());
		fRevisionInfo = gson.fromJson(reader, RevisionInfo.class);

		assertFalse("Wrong draft", fRevisionInfo.isDraft());
		assertTrue("Wrong has_draft_comments", fRevisionInfo.hasDraftComments());
		assertEquals("Wrong _number", 0, fRevisionInfo.getNumber());
		assertNull("Wrong ref", fRevisionInfo.getRef());
		assertNull("Wrong fetch", fRevisionInfo.getFetch());
		assertNull("Wrong commit", fRevisionInfo.getCommit());
		assertNull("Wrong files", fRevisionInfo.getFiles());
		assertNull("Wrong actions", fRevisionInfo.getActions());
		assertNull("Wrong reviewed", fRevisionInfo.getReviewed());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.RevisionInfo#getNumber()}.
	 */
	@Test
	public void testGetNumber() {
		json.addProperty("_number", NUMBER);
		Reader reader = new StringReader(json.toString());
		fRevisionInfo = gson.fromJson(reader, RevisionInfo.class);

		assertFalse("Wrong draft", fRevisionInfo.isDraft());
		assertFalse("Wrong has_draft_comments", fRevisionInfo.hasDraftComments());
		assertEquals("Wrong _number", NUMBER, fRevisionInfo.getNumber());
		assertNull("Wrong ref", fRevisionInfo.getRef());
		assertNull("Wrong fetch", fRevisionInfo.getFetch());
		assertNull("Wrong commit", fRevisionInfo.getCommit());
		assertNull("Wrong files", fRevisionInfo.getFiles());
		assertNull("Wrong actions", fRevisionInfo.getActions());
		assertNull("Wrong reviewed", fRevisionInfo.getReviewed());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.RevisionInfo#getRef()}.
	 */
	@Test
	public void testGetRef() {
		json.addProperty("ref", REF);
		Reader reader = new StringReader(json.toString());
		fRevisionInfo = gson.fromJson(reader, RevisionInfo.class);

		assertFalse("Wrong draft", fRevisionInfo.isDraft());
		assertFalse("Wrong has_draft_comments", fRevisionInfo.hasDraftComments());
		assertEquals("Wrong _number", 0, fRevisionInfo.getNumber());
		assertEquals("Wrong ref", REF, fRevisionInfo.getRef());
		assertNull("Wrong fetch", fRevisionInfo.getFetch());
		assertNull("Wrong commit", fRevisionInfo.getCommit());
		assertNull("Wrong files", fRevisionInfo.getFiles());
		assertNull("Wrong actions", fRevisionInfo.getActions());
		assertNull("Wrong reviewed", fRevisionInfo.getReviewed());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.RevisionInfo#getFetch()}.
	 */
	@Test
	public void testGetFetch() {
		JsonObject fetch = new JsonObject();
		json.add("fetch", fetch);
		Reader reader = new StringReader(json.toString());
		fRevisionInfo = gson.fromJson(reader, RevisionInfo.class);

		assertFalse("Wrong draft", fRevisionInfo.isDraft());
		assertFalse("Wrong has_draft_comments", fRevisionInfo.hasDraftComments());
		assertEquals("Wrong _number", 0, fRevisionInfo.getNumber());
		assertNull("Wrong ref", fRevisionInfo.getRef());
		assertEquals("Wrong fetch", FETCH, fRevisionInfo.getFetch());
		assertNull("Wrong commit", fRevisionInfo.getCommit());
		assertNull("Wrong files", fRevisionInfo.getFiles());
		assertNull("Wrong actions", fRevisionInfo.getActions());
		assertNull("Wrong reviewed", fRevisionInfo.getReviewed());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.RevisionInfo#getCommit()}.
	 */
	@Test
	public void testGetCommit() {
		JsonObject commit = new JsonObject();
		json.add("commit", commit);
		Reader reader = new StringReader(json.toString());
		fRevisionInfo = gson.fromJson(reader, RevisionInfo.class);

		assertFalse("Wrong draft", fRevisionInfo.isDraft());
		assertFalse("Wrong has_draft_comments", fRevisionInfo.hasDraftComments());
		assertEquals("Wrong _number", 0, fRevisionInfo.getNumber());
		assertNull("Wrong ref", fRevisionInfo.getRef());
		assertNull("Wrong fetch", fRevisionInfo.getFetch());
		assertEquals("Wrong commit", COMMIT, fRevisionInfo.getCommit());
		assertNull("Wrong files", fRevisionInfo.getFiles());
		assertNull("Wrong actions", fRevisionInfo.getActions());
		assertNull("Wrong reviewed", fRevisionInfo.getReviewed());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.RevisionInfo#getFiles()}.
	 */
	@Test
	public void testGetFiles() {
		JsonObject files = new JsonObject();
		json.add("files", files);
		Reader reader = new StringReader(json.toString());
		fRevisionInfo = gson.fromJson(reader, RevisionInfo.class);

		assertFalse("Wrong draft", fRevisionInfo.isDraft());
		assertFalse("Wrong has_draft_comments", fRevisionInfo.hasDraftComments());
		assertEquals("Wrong _number", 0, fRevisionInfo.getNumber());
		assertNull("Wrong ref", fRevisionInfo.getRef());
		assertNull("Wrong fetch", fRevisionInfo.getFetch());
		assertNull("Wrong commit", fRevisionInfo.getCommit());
		assertEquals("Wrong files", FILES, fRevisionInfo.getFiles());
		assertNull("Wrong actions", fRevisionInfo.getActions());
		assertNull("Wrong reviewed", fRevisionInfo.getReviewed());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.RevisionInfo#getActions()}.
	 */
	@Test
	public void testGetActions() {
		JsonObject actions = new JsonObject();
		json.add("actions", actions);
		Reader reader = new StringReader(json.toString());
		fRevisionInfo = gson.fromJson(reader, RevisionInfo.class);

		assertFalse("Wrong draft", fRevisionInfo.isDraft());
		assertFalse("Wrong has_draft_comments", fRevisionInfo.hasDraftComments());
		assertEquals("Wrong _number", 0, fRevisionInfo.getNumber());
		assertNull("Wrong ref", fRevisionInfo.getRef());
		assertNull("Wrong fetch", fRevisionInfo.getFetch());
		assertNull("Wrong commit", fRevisionInfo.getCommit());
		assertNull("Wrong files", fRevisionInfo.getFiles());
		assertEquals("Wrong actions", ACTIONS, fRevisionInfo.getActions());
		assertNull("Wrong reviewed", fRevisionInfo.getReviewed());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.RevisionInfo#getReviewed()}.
	 */
	@Test
	public void testGetReviewed() {
		json.addProperty("reviewed", REVIEWED);
		Reader reader = new StringReader(json.toString());
		fRevisionInfo = gson.fromJson(reader, RevisionInfo.class);

		assertFalse("Wrong draft", fRevisionInfo.isDraft());
		assertFalse("Wrong has_draft_comments", fRevisionInfo.hasDraftComments());
		assertEquals("Wrong _number", 0, fRevisionInfo.getNumber());
		assertNull("Wrong ref", fRevisionInfo.getRef());
		assertNull("Wrong fetch", fRevisionInfo.getFetch());
		assertNull("Wrong commit", fRevisionInfo.getCommit());
		assertNull("Wrong files", fRevisionInfo.getFiles());
		assertNull("Wrong actions", fRevisionInfo.getActions());
		assertEquals("Wrong reviewed", REVIEWED, fRevisionInfo.getReviewed());
	}

	// ------------------------------------------------------------------------
	// Test all
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.RevisionInfo}.
	 */
	@Test
	public void testAllFields() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fRevisionInfo = gson.fromJson(reader, RevisionInfo.class);

		assertTrue("Wrong draft", fRevisionInfo.isDraft());
		assertTrue("Wrong has_draft_comments", fRevisionInfo.hasDraftComments());
		assertEquals("Wrong _number", 5, fRevisionInfo.getNumber());
		assertEquals("Wrong ref", REF, fRevisionInfo.getRef());
		assertEquals("Wrong fetch", FETCH, fRevisionInfo.getFetch());
		assertEquals("Wrong commit", COMMIT, fRevisionInfo.getCommit());
		assertEquals("Wrong files", FILES, fRevisionInfo.getFiles());
		assertEquals("Wrong actions", ACTIONS, fRevisionInfo.getActions());
		assertEquals("Wrong reviewed", REVIEWED, fRevisionInfo.getReviewed());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.RevisionInfo}.
	 */
	@Test
	public void testExtraField() {
		setAllFields();
		json.addProperty("extra", "extra_property");
		Reader reader = new StringReader(json.toString());
		fRevisionInfo = gson.fromJson(reader, RevisionInfo.class);

		assertTrue("Wrong draft", fRevisionInfo.isDraft());
		assertTrue("Wrong has_draft_comments", fRevisionInfo.hasDraftComments());
		assertEquals("Wrong _number", 5, fRevisionInfo.getNumber());
		assertEquals("Wrong ref", REF, fRevisionInfo.getRef());
		assertEquals("Wrong fetch", FETCH, fRevisionInfo.getFetch());
		assertEquals("Wrong commit", COMMIT, fRevisionInfo.getCommit());
		assertEquals("Wrong files", FILES, fRevisionInfo.getFiles());
		assertEquals("Wrong actions", ACTIONS, fRevisionInfo.getActions());
		assertEquals("Wrong reviewed", REVIEWED, fRevisionInfo.getReviewed());
	}

	// ------------------------------------------------------------------------
	// Object
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.RevisionInfo#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fRevisionInfo = gson.fromJson(reader, RevisionInfo.class);

		assertEquals("Wrong hash code", HASH_CODE, fRevisionInfo.hashCode());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.RevisionInfo#equals(java.lang.Object)} .
	 */
	// @Test
	public void testEqualsObject() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.RevisionInfo#toString()}.
	 */
	@Test
	public void testToString() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fRevisionInfo = gson.fromJson(reader, RevisionInfo.class);

		assertEquals("Wrong string", TO_STRING, fRevisionInfo.toString());
	}

}
