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
import static org.junit.Assert.assertNull;

import java.io.Reader;
import java.io.StringReader;

import org.eclipse.egerrit.core.rest.AccountInfo;
import org.eclipse.egerrit.core.rest.CommentInfo;
import org.eclipse.egerrit.core.rest.CommentRange;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/**
 * Test suite for {@link org.eclipse.egerrit.core.rest.CommentInfo}
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class CommentInfoTest {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	private static final String ID = "myProject~master~I4982a3771051891899528a94fb47baeeb70582ae";

	private static final String PATH = "path/1";

	private static final String SIDE = "REVISION";

	private static final String LINE = "12";

	private static final CommentRange RANGE = new CommentRange();

	private static final String INREPLYTO = "abc";

	private static final String MESSAGE = "This is a test message";

	private static final String UPDATED = "2015-06-12 01:07:26.225000000";

	private static final AccountInfo AUTHOR = new AccountInfo();;

	private static final int HASH_CODE = 359458368;

	private static final String TO_STRING = "CommentInfo [id=" + ID + ", path=" + PATH + ", side=" + SIDE + ", line="
			+ LINE + ", range=" + RANGE + ", inReplyTo=" + INREPLYTO + ", message=" + MESSAGE + ", updated=" + UPDATED
			+ ", author=" + AUTHOR + "]";

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
	private CommentInfo fCommentInfo = null;

	// ------------------------------------------------------------------------
	// Helper methods
	// ------------------------------------------------------------------------

	@Before
	public void setUp() throws Exception {
		gson = new GsonBuilder().create();
		json = new JsonObject();
	}

	private void setAllFields() {
		json.addProperty("id", ID);
		json.addProperty("path", PATH);
		json.addProperty("side", SIDE);
		json.addProperty("line", LINE);
		JsonObject range = new JsonObject();
		json.add("range", range);

		json.addProperty("inReplyTo", INREPLYTO);
		json.addProperty("message", MESSAGE);
		json.addProperty("updated", UPDATED);
		JsonObject author = new JsonObject();
		json.add("author", author);
	}

	@After
	public void tearDown() throws Exception {
	}

	// ------------------------------------------------------------------------
	// Test cases
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.CommentInfo#getId()}.
	 */
	@Test
	public void testGetId() {
		json.addProperty("id", ID);
		Reader reader = new StringReader(json.toString());
		fCommentInfo = gson.fromJson(reader, CommentInfo.class);

		assertEquals("Wrong id", ID, fCommentInfo.getId());

		assertNull("Wrong path", fCommentInfo.getPath());
		assertNull("Wrong side", fCommentInfo.getSide());
		assertEquals("Wrong line", 0, fCommentInfo.getLine());
		assertNull("Wrong range", fCommentInfo.getRange());
		assertNull("Wrong in reply to", fCommentInfo.getInReplyTo());
		assertNull("Wrong message", fCommentInfo.getMessage());
		assertNull("Wrong updated", fCommentInfo.getUpdated());
		assertNull("Wrong owner", fCommentInfo.getAuthor());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.CommentInfo#getPath()}.
	 */
	@Test
	public void testGetPath() {
		json.addProperty("path", PATH);
		Reader reader = new StringReader(json.toString());
		fCommentInfo = gson.fromJson(reader, CommentInfo.class);

		assertNull("Wrong id", fCommentInfo.getId());

		assertEquals("Wrong path", PATH, fCommentInfo.getPath());
		assertNull("Wrong side", fCommentInfo.getSide());
		assertEquals("Wrong line", 0, fCommentInfo.getLine());
		assertNull("Wrong range", fCommentInfo.getRange());
		assertNull("Wrong in reply to", fCommentInfo.getInReplyTo());
		assertNull("Wrong message", fCommentInfo.getMessage());
		assertNull("Wrong updated", fCommentInfo.getUpdated());
		assertNull("Wrong owner", fCommentInfo.getAuthor());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.CommentInfo#getSide()}.
	 */
	@Test
	public void testGetSide() {
		json.addProperty("side", SIDE);
		Reader reader = new StringReader(json.toString());
		fCommentInfo = gson.fromJson(reader, CommentInfo.class);

		assertNull("Wrong id", fCommentInfo.getId());
		assertNull("Wrong path", fCommentInfo.getPath());
		assertEquals("Wrong side", SIDE, fCommentInfo.getSide());
		assertEquals("Wrong line", 0, fCommentInfo.getLine());
		assertNull("Wrong range", fCommentInfo.getRange());
		assertNull("Wrong in reply to", fCommentInfo.getInReplyTo());
		assertNull("Wrong message", fCommentInfo.getMessage());
		assertNull("Wrong updated", fCommentInfo.getUpdated());
		assertNull("Wrong owner", fCommentInfo.getAuthor());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.CommentInfo#getLine()}.
	 */
	@Test
	public void testGetLine() {
		json.addProperty("line", LINE);
		Reader reader = new StringReader(json.toString());
		fCommentInfo = gson.fromJson(reader, CommentInfo.class);

		assertNull("Wrong id", fCommentInfo.getId());
		assertNull("Wrong path", fCommentInfo.getPath());
		assertNull("Wrong side", fCommentInfo.getSide());

		assertEquals("Wrong line", (int) Integer.valueOf(LINE), fCommentInfo.getLine());
		assertNull("Wrong range", fCommentInfo.getRange());
		assertNull("Wrong in reply to", fCommentInfo.getInReplyTo());
		assertNull("Wrong message", fCommentInfo.getMessage());
		assertNull("Wrong updated", fCommentInfo.getUpdated());
		assertNull("Wrong owner", fCommentInfo.getAuthor());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.CommentInfo#getRange()}.
	 */
	@Test
	public void testGetRange() {
		JsonObject range = new JsonObject();
		json.add("range", range);

		Reader reader = new StringReader(json.toString());
		fCommentInfo = gson.fromJson(reader, CommentInfo.class);

		assertNull("Wrong id", fCommentInfo.getId());
		assertNull("Wrong path", fCommentInfo.getPath());
		assertNull("Wrong side", fCommentInfo.getSide());
		assertEquals("Wrong line", 0, fCommentInfo.getLine());
		assertEquals("Wrong range", RANGE, fCommentInfo.getRange());
		assertNull("Wrong in reply to", fCommentInfo.getInReplyTo());
		assertNull("Wrong message", fCommentInfo.getMessage());
		assertNull("Wrong updated", fCommentInfo.getUpdated());
		assertNull("Wrong owner", fCommentInfo.getAuthor());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.CommentInfo#getInReplyTo()}.
	 */
	@Test
	public void testGetInreplyTo() {
		json.addProperty("inReplyTo", INREPLYTO);
		Reader reader = new StringReader(json.toString());
		fCommentInfo = gson.fromJson(reader, CommentInfo.class);

		assertNull("Wrong id", fCommentInfo.getId());
		assertNull("Wrong path", fCommentInfo.getPath());
		assertNull("Wrong side", fCommentInfo.getSide());
		assertEquals("Wrong line", 0, fCommentInfo.getLine());
		assertNull("Wrong range", fCommentInfo.getRange());
		assertEquals("Wrong in reply to", INREPLYTO, fCommentInfo.getInReplyTo());
		assertNull("Wrong message", fCommentInfo.getMessage());
		assertNull("Wrong updated", fCommentInfo.getUpdated());
		assertNull("Wrong owner", fCommentInfo.getAuthor());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.CommentInfo#getMessage()}.
	 */
	@Test
	public void testGetMessage() {
		json.addProperty("message", MESSAGE);
		Reader reader = new StringReader(json.toString());
		fCommentInfo = gson.fromJson(reader, CommentInfo.class);

		assertNull("Wrong id", fCommentInfo.getId());
		assertNull("Wrong path", fCommentInfo.getPath());
		assertNull("Wrong side", fCommentInfo.getSide());
		assertEquals("Wrong line", 0, fCommentInfo.getLine());
		assertNull("Wrong range", fCommentInfo.getRange());
		assertNull("Wrong in reply to", fCommentInfo.getInReplyTo());
		assertEquals("Wrong message", MESSAGE, fCommentInfo.getMessage());
		assertNull("Wrong updated", fCommentInfo.getUpdated());
		assertNull("Wrong owner", fCommentInfo.getAuthor());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.CommentInfo#getUpdated()}.
	 */
	@Test
	public void testGetUpdated() {
		json.addProperty("updated", UPDATED);
		Reader reader = new StringReader(json.toString());
		fCommentInfo = gson.fromJson(reader, CommentInfo.class);

		assertNull("Wrong id", fCommentInfo.getId());
		assertNull("Wrong path", fCommentInfo.getPath());
		assertNull("Wrong side", fCommentInfo.getSide());
		assertEquals("Wrong line", 0, fCommentInfo.getLine());
		assertNull("Wrong range", fCommentInfo.getRange());
		assertNull("Wrong in reply to", fCommentInfo.getInReplyTo());
		assertNull("Wrong message", fCommentInfo.getMessage());
		assertEquals("Wrong updated", UPDATED, fCommentInfo.getUpdated());
		assertNull("Wrong owner", fCommentInfo.getAuthor());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.CommentInfo#getAuthor()}.
	 */
	@Test
	public void testGetAuthor() {
		JsonObject author = new JsonObject();
		json.add("author", author);
		Reader reader = new StringReader(json.toString());
		fCommentInfo = gson.fromJson(reader, CommentInfo.class);

		assertNull("Wrong id", fCommentInfo.getId());
		assertNull("Wrong path", fCommentInfo.getPath());
		assertNull("Wrong side", fCommentInfo.getSide());
		assertEquals("Wrong line", 0, fCommentInfo.getLine());
		assertNull("Wrong range", fCommentInfo.getRange());
		assertNull("Wrong in reply to", fCommentInfo.getInReplyTo());
		assertNull("Wrong message", fCommentInfo.getMessage());
		assertNull("Wrong updated", fCommentInfo.getUpdated());
		assertEquals("Wrong owner", AUTHOR, fCommentInfo.getAuthor());
	}

	// ------------------------------------------------------------------------
	// Test all
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.CommentInfo}.
	 */
	@Test
	public void testAllFields() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fCommentInfo = gson.fromJson(reader, CommentInfo.class);

		assertEquals("Wrong id", ID, fCommentInfo.getId());
		assertEquals("Wrong path", PATH, fCommentInfo.getPath());
		assertEquals("Wrong side", SIDE, fCommentInfo.getSide());
		assertEquals("Wrong line", (int) Integer.valueOf(LINE), fCommentInfo.getLine());
		assertEquals("Wrong range", RANGE, fCommentInfo.getRange());
		assertEquals("Wrong in reply to", INREPLYTO, fCommentInfo.getInReplyTo());
		assertEquals("Wrong message", MESSAGE, fCommentInfo.getMessage());
		assertEquals("Wrong updated", UPDATED, fCommentInfo.getUpdated());
		assertEquals("Wrong owner", AUTHOR, fCommentInfo.getAuthor());

	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.CommentInfo}.
	 */
	@Test
	public void testExtraField() {
		setAllFields();
		json.addProperty("extra", "extra_property");
		Reader reader = new StringReader(json.toString());
		fCommentInfo = gson.fromJson(reader, CommentInfo.class);

		assertEquals("Wrong id", ID, fCommentInfo.getId());
		assertEquals("Wrong path", PATH, fCommentInfo.getPath());
		assertEquals("Wrong side", SIDE, fCommentInfo.getSide());
		assertEquals("Wrong line", (int) Integer.valueOf(LINE), fCommentInfo.getLine());
		assertEquals("Wrong range", RANGE, fCommentInfo.getRange());
		assertEquals("Wrong in reply to", INREPLYTO, fCommentInfo.getInReplyTo());
		assertEquals("Wrong message", MESSAGE, fCommentInfo.getMessage());
		assertEquals("Wrong updated", UPDATED, fCommentInfo.getUpdated());
		assertEquals("Wrong owner", AUTHOR, fCommentInfo.getAuthor());

	}

	// ------------------------------------------------------------------------
	// Object
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.CommentInfo#hashCode()} .
	 */
	@Test
	public void testHashCode() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fCommentInfo = gson.fromJson(reader, CommentInfo.class);

		assertEquals("Wrong hash code", HASH_CODE, fCommentInfo.hashCode());
	}

//	/**
//	 * Test method for {@link org.eclipse.egerrit.core.rest.CommentInfo#equals(java.lang.Object)}.
//	 */
//	// @Test
//	public void testEqualsObject() {
//		fail("Not yet implemented");
//	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.CommentInfo#toString()} .
	 */
	@Test
	public void testToString() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fCommentInfo = gson.fromJson(reader, CommentInfo.class);
		assertEquals("Wrong string", TO_STRING, fCommentInfo.toString());
	}

}
