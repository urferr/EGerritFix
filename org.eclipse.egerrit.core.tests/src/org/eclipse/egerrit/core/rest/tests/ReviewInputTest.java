/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Guy Perron - Initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.core.rest.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.egerrit.core.rest.CommentInput;
import org.eclipse.egerrit.core.rest.ReviewInput;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/**
 * Test suite for {@link org.eclipse.egerrit.core.rest.ReviewInput}
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class ReviewInputTest {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	private static final String MESSAGE = "This a sample message";

	private static final Map<String, String> LABELS = new HashMap<String, String>();

	private static final Map<String, CommentInput> COMMENTS = new HashMap<String, CommentInput>();

	private static final boolean STRICT_LABELS = true;

	private static final String DRAFTS = "DELETE";

	private static final String NOTIFY = "ALL";

	private static final String ON_BEHALF_OF = "sampleaccountid";

	private static final int HASH_CODE = 513321652;

	private static final String TO_STRING = "ReviewInput [message=" + MESSAGE + ", labels=" + LABELS + ", comments="
			+ COMMENTS + ", strict_labels=" + STRICT_LABELS + ", drafts=" + DRAFTS + ", notify=" + NOTIFY
			+ ", on_behalf_of=" + ON_BEHALF_OF + "]";

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
	private ReviewInput fReviewInput = null;

	// ------------------------------------------------------------------------
	// Helper methods
	// ------------------------------------------------------------------------

	@Before
	public void setUp() throws Exception {
		gson = new GsonBuilder().create();
		json = new JsonObject();
	}

	private void setAllFields() {
		json.addProperty("message", MESSAGE);
		JsonObject labels = new JsonObject();
		json.add("labels", labels);
		JsonObject comments = new JsonObject();
		json.add("comments", comments);
		json.addProperty("strict_labels", STRICT_LABELS);
		json.addProperty("drafts", DRAFTS);
		json.addProperty("notify", NOTIFY);
		json.addProperty("on_behalf_of", ON_BEHALF_OF);
	}

	@After
	public void tearDown() throws Exception {
	}

	// ------------------------------------------------------------------------
	// Test cases
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ReviewInput#getMessage()}.
	 */
	@Test
	public void testGetMessage() {
		json.addProperty("message", MESSAGE);
		Reader reader = new StringReader(json.toString());
		fReviewInput = gson.fromJson(reader, ReviewInput.class);

		assertEquals("Wrong message", MESSAGE, fReviewInput.getMessage());
		assertNull("Wrong labels", fReviewInput.getLabels());
		assertNull("Wrong coments", fReviewInput.getComments());
		assertFalse("Wrong strict_labels", fReviewInput.isStrict_labels());
		assertNull("Wrong drafts", fReviewInput.getDrafts());
		assertNull("Wrong notify", fReviewInput.getNotify());
		assertNull("Wrong on_behalf_of", fReviewInput.getOn_behalf_of());

	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ReviewInput#getLabels()}.
	 */
	@Test
	public void testGetLabels() {
		JsonObject labels = new JsonObject();
		json.add("labels", labels);
		Reader reader = new StringReader(json.toString());
		fReviewInput = gson.fromJson(reader, ReviewInput.class);

		assertNull("Wrong message", fReviewInput.getMessage());
		assertEquals("Wrong labels", LABELS, fReviewInput.getLabels());
		assertNull("Wrong coments", fReviewInput.getComments());
		assertFalse("Wrong strict_labels", fReviewInput.isStrict_labels());
		assertNull("Wrong drafts", fReviewInput.getDrafts());
		assertNull("Wrong notify", fReviewInput.getNotify());
		assertNull("Wrong on_behalf_of", fReviewInput.getOn_behalf_of());

	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ReviewInput#getComments()}.
	 */
	@Test
	public void testGetComments() {
		JsonObject comments = new JsonObject();
		json.add("comments", comments);
		Reader reader = new StringReader(json.toString());
		fReviewInput = gson.fromJson(reader, ReviewInput.class);

		assertNull("Wrong message", fReviewInput.getMessage());
		assertNull("Wrong labels", fReviewInput.getLabels());
		assertEquals("Wrong coments", COMMENTS, fReviewInput.getComments());
		assertFalse("Wrong strict_labels", fReviewInput.isStrict_labels());
		assertNull("Wrong drafts", fReviewInput.getDrafts());
		assertNull("Wrong notify", fReviewInput.getNotify());
		assertNull("Wrong on_behalf_of", fReviewInput.getOn_behalf_of());

	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ReviewInput#getStrict_labels()}.
	 */
	@Test
	public void testGetStrict_labels() {
		json.addProperty("strict_labels", STRICT_LABELS);
		Reader reader = new StringReader(json.toString());
		fReviewInput = gson.fromJson(reader, ReviewInput.class);

		assertNull("Wrong message", fReviewInput.getMessage());
		assertNull("Wrong labels", fReviewInput.getLabels());
		assertNull("Wrong comments", fReviewInput.getComments());
		assertEquals("Wrong strict_labels", STRICT_LABELS, fReviewInput.isStrict_labels());
		assertNull("Wrong drafts", fReviewInput.getDrafts());
		assertNull("Wrong notify", fReviewInput.getNotify());
		assertNull("Wrong on_behalf_of", fReviewInput.getOn_behalf_of());

	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ReviewInput#getDrafts()}.
	 */
	@Test
	public void testGetDrafts() {
		json.addProperty("drafts", DRAFTS);
		Reader reader = new StringReader(json.toString());
		fReviewInput = gson.fromJson(reader, ReviewInput.class);

		assertNull("Wrong message", fReviewInput.getMessage());
		assertNull("Wrong labels", fReviewInput.getLabels());
		assertNull("Wrong coments", fReviewInput.getComments());
		assertFalse("Wrong strict_labels", fReviewInput.isStrict_labels());
		assertEquals("Wrong drafts", DRAFTS, fReviewInput.getDrafts());
		assertNull("Wrong notify", fReviewInput.getNotify());
		assertNull("Wrong on_behalf_of", fReviewInput.getOn_behalf_of());

	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ReviewInput#getNotify()}.
	 */
	@Test
	public void testGetNotify() {
		json.addProperty("notify", NOTIFY);
		Reader reader = new StringReader(json.toString());
		fReviewInput = gson.fromJson(reader, ReviewInput.class);

		assertNull("Wrong message", fReviewInput.getMessage());
		assertNull("Wrong labels", fReviewInput.getLabels());
		assertNull("Wrong coments", fReviewInput.getComments());
		assertFalse("Wrong strict_labels", fReviewInput.isStrict_labels());
		assertNull("Wrong drafts", fReviewInput.getDrafts());
		assertEquals("Wrong notify", NOTIFY, fReviewInput.getNotify());
		assertNull("Wrong on_behalf_of", fReviewInput.getOn_behalf_of());

	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ReviewInput#getOn_behalf_of()}.
	 */
	@Test
	public void testgetOn_behalf_of() {
		json.addProperty("on_behalf_of", ON_BEHALF_OF);
		Reader reader = new StringReader(json.toString());
		fReviewInput = gson.fromJson(reader, ReviewInput.class);

		assertNull("Wrong message", fReviewInput.getMessage());
		assertNull("Wrong labels", fReviewInput.getLabels());
		assertNull("Wrong coments", fReviewInput.getComments());
		assertFalse("Wrong strict_labels", fReviewInput.isStrict_labels());
		assertNull("Wrong drafts", fReviewInput.getDrafts());
		assertNull("Wrong notify", fReviewInput.getNotify());
		assertEquals("Wrong on_behalf_of", ON_BEHALF_OF, fReviewInput.getOn_behalf_of());

	}

	// ------------------------------------------------------------------------
	// Test all
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ReviewInput}.
	 */
	@Test
	public void testAllFields() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fReviewInput = gson.fromJson(reader, ReviewInput.class);

		assertEquals("labels", LABELS, fReviewInput.getLabels());

	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ReviewInput}.
	 */
	@Test
	public void testExtraField() {
		setAllFields();
		json.addProperty("extra", "extra_property");
		Reader reader = new StringReader(json.toString());
		fReviewInput = gson.fromJson(reader, ReviewInput.class);

		assertEquals("labels", LABELS, fReviewInput.getLabels());
	}

	// ------------------------------------------------------------------------
	// Object
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ReviewInput#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fReviewInput = gson.fromJson(reader, ReviewInput.class);

		assertEquals("Wrong hash code", HASH_CODE, fReviewInput.hashCode());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ReviewInput#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fReviewInput = gson.fromJson(reader, ReviewInput.class);

		assertTrue("Wrong hash code", fReviewInput.equals(fReviewInput));
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ReviewInput#toString()}.
	 */
	@Test
	public void testToString() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fReviewInput = gson.fromJson(reader, ReviewInput.class);

	}

}
