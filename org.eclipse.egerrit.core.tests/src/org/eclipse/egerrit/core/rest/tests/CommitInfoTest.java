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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.egerrit.core.command.EMFTypeAdapterFactory;
import org.eclipse.egerrit.internal.model.CommitInfo;
import org.eclipse.egerrit.internal.model.GitPersonInfo;
import org.eclipse.egerrit.internal.model.ModelFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Test suite for {@link org.eclipse.egerrit.internal.model.CommitInfo}
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class CommitInfoTest {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	private static final String COMMIT = "10";

	private static final List<CommitInfo> PARENTS = new ArrayList<CommitInfo>();

	private static final GitPersonInfo AUTHOR = ModelFactory.eINSTANCE.createGitPersonInfo();

	private static final GitPersonInfo COMMITTER = ModelFactory.eINSTANCE.createGitPersonInfo();

	private static final String SUBJECT = "gerrit";

	private static final String MESSAGE = "gerrit";

	private static final int HASH_CODE = 273251407;

	private static final String TO_STRING = "CommitInfo [" + "commit=" + COMMIT + ", parents=" + PARENTS + ", author="
			+ AUTHOR + ", committer=" + COMMITTER + ", subject=" + SUBJECT + ", message=" + MESSAGE + "]";

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
	private CommitInfo fCommitInfo = null;

	// ------------------------------------------------------------------------
	// Helper methods
	// ------------------------------------------------------------------------

	@Before
	public void setUp() throws Exception {
		gson = new GsonBuilder().registerTypeAdapterFactory(new EMFTypeAdapterFactory()).create();
		json = new JsonObject();
	}

	private void setAllFields() {
		json.addProperty("commit", COMMIT);
		JsonArray parents = new JsonArray();
		json.add("parents", parents);
		JsonObject author = new JsonObject();
		json.add("author", author);
		JsonObject committer = new JsonObject();
		json.add("committer", committer);
		json.addProperty("subject", SUBJECT);
		json.addProperty("message", MESSAGE);
	}

	@After
	public void tearDown() throws Exception {
	}

	// ------------------------------------------------------------------------
	// Test cases
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.CommitInfo#getCommit()}.
	 */
	@Test
	public void testGetCommit() {
		json.addProperty("commit", COMMIT);
		Reader reader = new StringReader(json.toString());
		fCommitInfo = gson.fromJson(reader, CommitInfo.class);

		assertEquals("Wrong commit", COMMIT, fCommitInfo.getCommit());
		assertEquals("Wrong parents", PARENTS, fCommitInfo.getParents());
		assertEquals("Wrong author", AUTHOR, fCommitInfo.getAuthor());
		assertEquals("Wrong committer", COMMITTER, fCommitInfo.getCommitter());
		assertNull("Wrong subject", fCommitInfo.getSubject());
		assertNull("Wrong message", fCommitInfo.getMessage());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.CommitInfo#getParents()}.
	 */
	@Test
	public void testGetParents() {
		JsonArray parents = new JsonArray();
		json.add("parents", parents);
		Reader reader = new StringReader(json.toString());
		fCommitInfo = gson.fromJson(reader, CommitInfo.class);

		assertNull("Wrong commit", fCommitInfo.getCommit());
		assertEquals("Wrong parents", PARENTS, fCommitInfo.getParents());
		assertEquals("Wrong author", AUTHOR, fCommitInfo.getAuthor());
		assertEquals("Wrong committer", COMMITTER, fCommitInfo.getCommitter());
		assertNull("Wrong subject", fCommitInfo.getSubject());
		assertNull("Wrong message", fCommitInfo.getMessage());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.CommitInfo#getAuthor()}.
	 */
	@Test
	public void testGetAuthor() {
		JsonObject author = new JsonObject();
		json.add("author", author);
		Reader reader = new StringReader(json.toString());
		fCommitInfo = gson.fromJson(reader, CommitInfo.class);

		assertNull("Wrong commit", fCommitInfo.getCommit());
		assertEquals("Wrong parents", PARENTS, fCommitInfo.getParents());
		assertEquals("Wrong author", AUTHOR, fCommitInfo.getAuthor());
		assertEquals("Wrong committer", COMMITTER, fCommitInfo.getCommitter());
		assertNull("Wrong subject", fCommitInfo.getSubject());
		assertNull("Wrong message", fCommitInfo.getMessage());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.CommitInfo#getCommitter()}.
	 */
	@Test
	public void testGetCommitter() {
		JsonObject committer = new JsonObject();
		json.add("committer", committer);
		Reader reader = new StringReader(json.toString());
		fCommitInfo = gson.fromJson(reader, CommitInfo.class);

		assertNull("Wrong commit", fCommitInfo.getCommit());
		assertEquals("Wrong parents", PARENTS, fCommitInfo.getParents());
		assertEquals("Wrong author", AUTHOR, fCommitInfo.getAuthor());
		assertEquals("Wrong committer", COMMITTER, fCommitInfo.getCommitter());
		assertNull("Wrong subject", fCommitInfo.getSubject());
		assertNull("Wrong message", fCommitInfo.getMessage());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.CommitInfo#getSubject()}.
	 */
	@Test
	public void testGetSubject() {
		json.addProperty("subject", SUBJECT);
		Reader reader = new StringReader(json.toString());
		fCommitInfo = gson.fromJson(reader, CommitInfo.class);

		assertNull("Wrong commit", fCommitInfo.getCommit());
		assertEquals("Wrong parents", PARENTS, fCommitInfo.getParents());
		assertEquals("Wrong author", AUTHOR, fCommitInfo.getAuthor());
		assertEquals("Wrong committer", COMMITTER, fCommitInfo.getCommitter());
		assertEquals("Wrong subject", SUBJECT, fCommitInfo.getSubject());
		assertNull("Wrong message", fCommitInfo.getMessage());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.CommitInfo#getMessage()}.
	 */
	@Test
	public void testGetMessage() {
		json.addProperty("message", MESSAGE);
		Reader reader = new StringReader(json.toString());
		fCommitInfo = gson.fromJson(reader, CommitInfo.class);

		assertNull("Wrong commit", fCommitInfo.getCommit());
		assertEquals("Wrong parents", PARENTS, fCommitInfo.getParents());
		assertEquals("Wrong author", AUTHOR, fCommitInfo.getAuthor());
		assertEquals("Wrong committer", COMMITTER, fCommitInfo.getCommitter());
		assertNull("Wrong subject", fCommitInfo.getSubject());
		assertEquals("Wrong message", MESSAGE, fCommitInfo.getMessage());
	}

	// ------------------------------------------------------------------------
	// Test all
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.CommitInfo}.
	 */
	@Test
	public void testAllFields() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fCommitInfo = gson.fromJson(reader, CommitInfo.class);

		assertEquals("Wrong commit", COMMIT, fCommitInfo.getCommit());
		assertEquals("Wrong parents", PARENTS, fCommitInfo.getParents());
		assertEquals("Wrong author", AUTHOR, fCommitInfo.getAuthor());
		assertEquals("Wrong committer", COMMITTER, fCommitInfo.getCommitter());
		assertEquals("Wrong subject", SUBJECT, fCommitInfo.getSubject());
		assertEquals("Wrong message", MESSAGE, fCommitInfo.getMessage());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.CommitInfo}.
	 */
	@Test
	public void testExtraField() {
		setAllFields();
		json.addProperty("extra", "extra_property");
		Reader reader = new StringReader(json.toString());
		fCommitInfo = gson.fromJson(reader, CommitInfo.class);

		assertEquals("Wrong commit", COMMIT, fCommitInfo.getCommit());
		assertEquals("Wrong parents", PARENTS, fCommitInfo.getParents());
		assertEquals("Wrong author", AUTHOR, fCommitInfo.getAuthor());
		assertEquals("Wrong committer", COMMITTER, fCommitInfo.getCommitter());
		assertEquals("Wrong subject", SUBJECT, fCommitInfo.getSubject());
		assertEquals("Wrong message", MESSAGE, fCommitInfo.getMessage());
	}

	// ------------------------------------------------------------------------
	// Object
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.CommitInfo#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fCommitInfo = gson.fromJson(reader, CommitInfo.class);

		assertEquals("Wrong hash code", HASH_CODE, fCommitInfo.hashCode());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.CommitInfo#equals(java.lang.Object)}.
	 */
	// @Test
	public void testEqualsObject() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.CommitInfo#toString()}.
	 */
	@Test
	public void testToString() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fCommitInfo = gson.fromJson(reader, CommitInfo.class);

		assertEquals("Wrong string", TO_STRING, fCommitInfo.toString());
	}

}
