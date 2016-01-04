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

import org.eclipse.egerrit.core.command.EMFTypeAdapterFactory;
import org.eclipse.egerrit.internal.model.ActionInfo;
import org.eclipse.egerrit.internal.model.CommitInfo;
import org.eclipse.egerrit.internal.model.FetchInfo;
import org.eclipse.egerrit.internal.model.FileInfo;
import org.eclipse.egerrit.internal.model.ModelFactory;
import org.eclipse.egerrit.internal.model.RevisionInfo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/**
 * Test suite for {@link org.eclipse.egerrit.internal.model.RevisionInfo}
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

	private static final CommitInfo COMMIT = ModelFactory.eINSTANCE.createCommitInfo();

	private static final Map<String, FileInfo> FILES = new LinkedHashMap<String, FileInfo>();

	private static final Map<String, ActionInfo> ACTIONS = new LinkedHashMap<String, ActionInfo>();

	private static final Boolean REVIEWED = true;

	private static final String ID = null;

	private static final int HASH_CODE = 1397094371;

	private static final String TO_STRING = "RevisionInfo [draft=" + DRAFT + ", has_draft_comments="
			+ HAS_DRAFT_COMMENTS + ", _number=" + NUMBER + ", ref=" + REF + ", fetch=" + FETCH + ", commit=" + COMMIT
			+ ", files=" + FILES + ", actions=" + ACTIONS + ", reviewed=" + REVIEWED + ", revisionId=" + ID + "]";

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
		gson = new GsonBuilder().registerTypeAdapterFactory(new EMFTypeAdapterFactory()).create();
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
		json.addProperty("revisionId", ID);
	}

	@After
	public void tearDown() throws Exception {
	}

	// ------------------------------------------------------------------------
	// Test cases
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.RevisionInfo#isDraft()}.
	 */
	@Test
	public void testIsDraft() {
		json.addProperty("draft", DRAFT);
		Reader reader = new StringReader(json.toString());
		fRevisionInfo = gson.fromJson(reader, RevisionInfo.class);

		assertTrue("Wrong draft", fRevisionInfo.isDraft());
		assertFalse("Wrong has_draft_comments", fRevisionInfo.isHas_draft_comments());
		assertEquals("Wrong _number", 0, fRevisionInfo.get_number());
		assertNull("Wrong ref", fRevisionInfo.getRef());
		assertNull("Wrong fetch", fRevisionInfo.getFetch());
		assertNull("Wrong commit", fRevisionInfo.getCommit());
		assertNull("Wrong files", fRevisionInfo.getFiles());
		assertNull("Wrong actions", fRevisionInfo.getActions());
		assertNull("Wrong reviewed", fRevisionInfo.isReviewed());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.RevisionInfo#isHas_draft_comments()}.
	 */
	@Test
	public void testisHas_draft_comments() {
		json.addProperty("has_draft_comments", HAS_DRAFT_COMMENTS);
		Reader reader = new StringReader(json.toString());
		fRevisionInfo = gson.fromJson(reader, RevisionInfo.class);

		assertFalse("Wrong draft", fRevisionInfo.isDraft());
		assertTrue("Wrong has_draft_comments", fRevisionInfo.isHas_draft_comments());
		assertEquals("Wrong _number", 0, fRevisionInfo.get_number());
		assertNull("Wrong ref", fRevisionInfo.getRef());
		assertNull("Wrong fetch", fRevisionInfo.getFetch());
		assertNull("Wrong commit", fRevisionInfo.getCommit());
		assertNull("Wrong files", fRevisionInfo.getFiles());
		assertNull("Wrong actions", fRevisionInfo.getActions());
		assertNull("Wrong reviewed", fRevisionInfo.isReviewed());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.RevisionInfo#get_number()}.
	 */
	@Test
	public void testget_number() {
		json.addProperty("_number", NUMBER);
		Reader reader = new StringReader(json.toString());
		fRevisionInfo = gson.fromJson(reader, RevisionInfo.class);

		assertFalse("Wrong draft", fRevisionInfo.isDraft());
		assertFalse("Wrong has_draft_comments", fRevisionInfo.isHas_draft_comments());
		assertEquals("Wrong _number", NUMBER, fRevisionInfo.get_number());
		assertNull("Wrong ref", fRevisionInfo.getRef());
		assertNull("Wrong fetch", fRevisionInfo.getFetch());
		assertNull("Wrong commit", fRevisionInfo.getCommit());
		assertNull("Wrong files", fRevisionInfo.getFiles());
		assertNull("Wrong actions", fRevisionInfo.getActions());
		assertNull("Wrong reviewed", fRevisionInfo.isReviewed());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.RevisionInfo#getRef()}.
	 */
	@Test
	public void testGetRef() {
		json.addProperty("ref", REF);
		Reader reader = new StringReader(json.toString());
		fRevisionInfo = gson.fromJson(reader, RevisionInfo.class);

		assertFalse("Wrong draft", fRevisionInfo.isDraft());
		assertFalse("Wrong has_draft_comments", fRevisionInfo.isHas_draft_comments());
		assertEquals("Wrong _number", 0, fRevisionInfo.get_number());
		assertEquals("Wrong ref", REF, fRevisionInfo.getRef());
		assertNull("Wrong fetch", fRevisionInfo.getFetch());
		assertNull("Wrong commit", fRevisionInfo.getCommit());
		assertNull("Wrong files", fRevisionInfo.getFiles());
		assertNull("Wrong actions", fRevisionInfo.getActions());
		assertNull("Wrong reviewed", fRevisionInfo.isReviewed());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.RevisionInfo#getFetch()}.
	 */
	@Test
	public void testGetFetch() {
		JsonObject fetch = new JsonObject();
		json.add("fetch", fetch);
		Reader reader = new StringReader(json.toString());
		fRevisionInfo = gson.fromJson(reader, RevisionInfo.class);

		assertFalse("Wrong draft", fRevisionInfo.isDraft());
		assertFalse("Wrong has_draft_comments", fRevisionInfo.isHas_draft_comments());
		assertEquals("Wrong _number", 0, fRevisionInfo.get_number());
		assertNull("Wrong ref", fRevisionInfo.getRef());
		assertEquals("Wrong fetch", FETCH, fRevisionInfo.getFetch());
		assertNull("Wrong commit", fRevisionInfo.getCommit());
		assertNull("Wrong files", fRevisionInfo.getFiles());
		assertNull("Wrong actions", fRevisionInfo.getActions());
		assertNull("Wrong reviewed", fRevisionInfo.isReviewed());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.RevisionInfo#getCommit()}.
	 */
	@Test
	public void testGetCommit() {
		JsonObject commit = new JsonObject();
		json.add("commit", commit);
		Reader reader = new StringReader(json.toString());
		fRevisionInfo = gson.fromJson(reader, RevisionInfo.class);

		assertFalse("Wrong draft", fRevisionInfo.isDraft());
		assertFalse("Wrong has_draft_comments", fRevisionInfo.isHas_draft_comments());
		assertEquals("Wrong _number", 0, fRevisionInfo.get_number());
		assertNull("Wrong ref", fRevisionInfo.getRef());
		assertNull("Wrong fetch", fRevisionInfo.getFetch());
		assertEquals("Wrong commit", COMMIT, fRevisionInfo.getCommit());
		assertNull("Wrong files", fRevisionInfo.getFiles());
		assertNull("Wrong actions", fRevisionInfo.getActions());
		assertNull("Wrong reviewed", fRevisionInfo.isReviewed());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.RevisionInfo#getFiles()}.
	 */
	@Test
	public void testGetFiles() {
		JsonObject files = new JsonObject();
		json.add("files", files);
		Reader reader = new StringReader(json.toString());
		fRevisionInfo = gson.fromJson(reader, RevisionInfo.class);

		assertFalse("Wrong draft", fRevisionInfo.isDraft());
		assertFalse("Wrong has_draft_comments", fRevisionInfo.isHas_draft_comments());
		assertEquals("Wrong _number", 0, fRevisionInfo.get_number());
		assertNull("Wrong ref", fRevisionInfo.getRef());
		assertNull("Wrong fetch", fRevisionInfo.getFetch());
		assertNull("Wrong commit", fRevisionInfo.getCommit());
		assertEquals("Wrong files", FILES, fRevisionInfo.getFiles());
		assertNull("Wrong actions", fRevisionInfo.getActions());
		assertNull("Wrong reviewed", fRevisionInfo.isReviewed());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.RevisionInfo#getActions()}.
	 */
	@Test
	public void testGetActions() {
		JsonObject actions = new JsonObject();
		json.add("actions", actions);
		Reader reader = new StringReader(json.toString());
		fRevisionInfo = gson.fromJson(reader, RevisionInfo.class);

		assertFalse("Wrong draft", fRevisionInfo.isDraft());
		assertFalse("Wrong has_draft_comments", fRevisionInfo.isHas_draft_comments());
		assertEquals("Wrong _number", 0, fRevisionInfo.get_number());
		assertNull("Wrong ref", fRevisionInfo.getRef());
		assertNull("Wrong fetch", fRevisionInfo.getFetch());
		assertNull("Wrong commit", fRevisionInfo.getCommit());
		assertNull("Wrong files", fRevisionInfo.getFiles());
		assertEquals("Wrong actions", ACTIONS, fRevisionInfo.getActions());
		assertNull("Wrong reviewed", fRevisionInfo.isReviewed());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.RevisionInfo#isReviewed()}.
	 */
	@Test
	public void testisReviewed() {
		json.addProperty("reviewed", REVIEWED);
		Reader reader = new StringReader(json.toString());
		fRevisionInfo = gson.fromJson(reader, RevisionInfo.class);

		assertFalse("Wrong draft", fRevisionInfo.isDraft());
		assertFalse("Wrong has_draft_comments", fRevisionInfo.isHas_draft_comments());
		assertEquals("Wrong _number", 0, fRevisionInfo.get_number());
		assertNull("Wrong ref", fRevisionInfo.getRef());
		assertNull("Wrong fetch", fRevisionInfo.getFetch());
		assertNull("Wrong commit", fRevisionInfo.getCommit());
		assertNull("Wrong files", fRevisionInfo.getFiles());
		assertNull("Wrong actions", fRevisionInfo.getActions());
		assertEquals("Wrong reviewed", REVIEWED, fRevisionInfo.isReviewed());
	}

	// ------------------------------------------------------------------------
	// Test all
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.RevisionInfo}.
	 */
	@Test
	public void testAllFields() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fRevisionInfo = gson.fromJson(reader, RevisionInfo.class);

		assertTrue("Wrong draft", fRevisionInfo.isDraft());
		assertTrue("Wrong has_draft_comments", fRevisionInfo.isHas_draft_comments());
		assertEquals("Wrong _number", 5, fRevisionInfo.get_number());
		assertEquals("Wrong ref", REF, fRevisionInfo.getRef());
		assertEquals("Wrong fetch", FETCH, fRevisionInfo.getFetch());
		assertEquals("Wrong commit", COMMIT, fRevisionInfo.getCommit());
		assertEquals("Wrong files", FILES, fRevisionInfo.getFiles());
		assertEquals("Wrong actions", ACTIONS, fRevisionInfo.getActions());
		assertEquals("Wrong reviewed", REVIEWED, fRevisionInfo.isReviewed());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.RevisionInfo}.
	 */
	@Test
	public void testExtraField() {
		setAllFields();
		json.addProperty("extra", "extra_property");
		Reader reader = new StringReader(json.toString());
		fRevisionInfo = gson.fromJson(reader, RevisionInfo.class);

		assertTrue("Wrong draft", fRevisionInfo.isDraft());
		assertTrue("Wrong has_draft_comments", fRevisionInfo.isHas_draft_comments());
		assertEquals("Wrong _number", 5, fRevisionInfo.get_number());
		assertEquals("Wrong ref", REF, fRevisionInfo.getRef());
		assertEquals("Wrong fetch", FETCH, fRevisionInfo.getFetch());
		assertEquals("Wrong commit", COMMIT, fRevisionInfo.getCommit());
		assertEquals("Wrong files", FILES, fRevisionInfo.getFiles());
		assertEquals("Wrong actions", ACTIONS, fRevisionInfo.getActions());
		assertEquals("Wrong reviewed", REVIEWED, fRevisionInfo.isReviewed());
	}

	// ------------------------------------------------------------------------
	// Object
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.RevisionInfo#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fRevisionInfo = gson.fromJson(reader, RevisionInfo.class);
		assertEquals("Wrong hash code", HASH_CODE, fRevisionInfo.hashCode());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.RevisionInfo#equals(java.lang.Object)} .
	 */
	// @Test
	public void testEqualsObject() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.RevisionInfo#toString()}.
	 */
	@Test
	public void testToString() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fRevisionInfo = gson.fromJson(reader, RevisionInfo.class);

		assertEquals("Wrong string", TO_STRING, fRevisionInfo.toString());
	}

}
