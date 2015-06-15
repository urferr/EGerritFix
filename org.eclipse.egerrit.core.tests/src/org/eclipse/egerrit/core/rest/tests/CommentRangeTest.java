/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Jacques Bouthillier - Initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.core.rest.tests;

import static org.junit.Assert.assertEquals;

import java.io.Reader;
import java.io.StringReader;

import org.eclipse.egerrit.core.rest.CommentRange;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/**
 * Test suite for {@link org.eclipse.egerrit.core.rest.CommentRange}
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class CommentRangeTest {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	private static final int START_LINE = 10;

	private static final int START_CHARACTER = 123;

	private static final int END_LINE = 15;

	private static final int END_CHARACTER = 156;

	private static final int HASH_CODE = 416734;

	private static final String TO_STRING = "Range[startLine=" + START_LINE + ", startCharacter=" + START_CHARACTER
			+ ", endLine=" + END_LINE + ", endCharacter=" + END_CHARACTER + "]";

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
	private CommentRange fCommentRange = null;

	// ------------------------------------------------------------------------
	// Helper methods
	// ------------------------------------------------------------------------

	@Before
	public void setUp() throws Exception {
		gson = new GsonBuilder().create();
		json = new JsonObject();
	}

	private void setAllFields() {
		json.addProperty("startLine", START_LINE);
		json.addProperty("startCharacter", START_CHARACTER);
		json.addProperty("endLine", END_LINE);
		json.addProperty("endCharacter", END_CHARACTER);
		JsonObject range = new JsonObject();
		json.add("range", range);
	}

	@After
	public void tearDown() throws Exception {
	}

	// ------------------------------------------------------------------------
	// Test cases
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.CommentRange#getStartLine()}.
	 */
	@Test
	public void testGetStartLine() {
		json.addProperty("startLine", START_LINE);
		Reader reader = new StringReader(json.toString());
		fCommentRange = gson.fromJson(reader, CommentRange.class);

		assertEquals("Wrong start line", START_LINE, fCommentRange.getStartLine());
		assertEquals("Wrong start character", 0, fCommentRange.getStartCharacter());
		assertEquals("Wrong end line", 0, fCommentRange.getEndLine());
		assertEquals("Wrong end character", 0, fCommentRange.getEndCharacter());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.CommentRange#getStartCharacter()}.
	 */
	@Test
	public void testGetStartCharacter() {
		json.addProperty("startCharacter", START_CHARACTER);
		Reader reader = new StringReader(json.toString());
		fCommentRange = gson.fromJson(reader, CommentRange.class);

		assertEquals("Wrong start line", 0, fCommentRange.getStartLine());
		assertEquals("Wrong start character", START_CHARACTER, fCommentRange.getStartCharacter());
		assertEquals("Wrong end line", 0, fCommentRange.getEndLine());
		assertEquals("Wrong end character", 0, fCommentRange.getEndCharacter());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.CommentRange#getEndLine()}.
	 */
	@Test
	public void testGetEndLine() {
		json.addProperty("endLine", END_LINE);
		Reader reader = new StringReader(json.toString());
		fCommentRange = gson.fromJson(reader, CommentRange.class);

		assertEquals("Wrong start line", 0, fCommentRange.getStartLine());
		assertEquals("Wrong start character", 0, fCommentRange.getStartCharacter());
		assertEquals("Wrong end line", END_LINE, fCommentRange.getEndLine());
		assertEquals("Wrong end character", 0, fCommentRange.getEndCharacter());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.CommentRange#getEndCharacter()}.
	 */
	@Test
	public void testGetEndCharacter() {
		json.addProperty("endCharacter", END_CHARACTER);
		Reader reader = new StringReader(json.toString());
		fCommentRange = gson.fromJson(reader, CommentRange.class);

		assertEquals("Wrong start line", 0, fCommentRange.getStartLine());
		assertEquals("Wrong start character", 0, fCommentRange.getStartCharacter());
		assertEquals("Wrong end line", 0, fCommentRange.getEndLine());
		assertEquals("Wrong end character", END_CHARACTER, fCommentRange.getEndCharacter());
	}

	// ------------------------------------------------------------------------
	// Test all
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.CommentRange}.
	 */
	@Test
	public void testAllFields() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fCommentRange = gson.fromJson(reader, CommentRange.class);

		assertEquals("Wrong start line", START_LINE, fCommentRange.getStartLine());
		assertEquals("Wrong start character", START_CHARACTER, fCommentRange.getStartCharacter());
		assertEquals("Wrong end line", END_LINE, fCommentRange.getEndLine());
		assertEquals("Wrong end character", END_CHARACTER, fCommentRange.getEndCharacter());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.CommentRange}.
	 */
	@Test
	public void testExtraField() {
		setAllFields();
		json.addProperty("extra", "extra_property");
		Reader reader = new StringReader(json.toString());
		fCommentRange = gson.fromJson(reader, CommentRange.class);

		assertEquals("Wrong start line", START_LINE, fCommentRange.getStartLine());
		assertEquals("Wrong start character", START_CHARACTER, fCommentRange.getStartCharacter());
		assertEquals("Wrong end line", END_LINE, fCommentRange.getEndLine());
		assertEquals("Wrong end character", END_CHARACTER, fCommentRange.getEndCharacter());
	}

	// ------------------------------------------------------------------------
	// Object
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.CommentRange#hashCode()} .
	 */
	@Test
	public void testHashCode() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fCommentRange = gson.fromJson(reader, CommentRange.class);

		assertEquals("Wrong hash code", HASH_CODE, fCommentRange.hashCode());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.CommentInfo#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fCommentRange = gson.fromJson(reader, CommentRange.class);

		assertEquals("Wrong equal", true, fCommentRange.equals(fCommentRange));

	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.CommentRange#toString()} .
	 */
	@Test
	public void testToString() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fCommentRange = gson.fromJson(reader, CommentRange.class);

		assertEquals("Wrong string", TO_STRING, fCommentRange.toString());
	}

}
