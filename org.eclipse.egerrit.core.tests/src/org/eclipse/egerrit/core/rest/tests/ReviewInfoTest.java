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
import static org.junit.Assert.assertTrue;

import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.egerrit.core.rest.ReviewInfo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/**
 * Test suite for {@link org.eclipse.egerrit.core.rest.ReviewInfo}
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class ReviewInfoTest {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	private static final Map<String, String> LABELS = new HashMap<String, String>();

	private static final int HASH_CODE = 31;

	private static final String TO_STRING = "ReviewInfo [" + "labels=" + LABELS + "]";

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
	private ReviewInfo fReviewInfo = null;

	// ------------------------------------------------------------------------
	// Helper methods
	// ------------------------------------------------------------------------

	@Before
	public void setUp() throws Exception {
		gson = new GsonBuilder().create();
		json = new JsonObject();
	}

	private void setAllFields() {
		JsonObject labels = new JsonObject();
		json.add("labels", labels);
	}

	@After
	public void tearDown() throws Exception {
	}

	// ------------------------------------------------------------------------
	// Test cases
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ReviewInfo#getLabels()}.
	 */
	@Test
	public void testGetLabels() {
		JsonObject labels = new JsonObject();
		json.add("labels", labels);
		Reader reader = new StringReader(json.toString());
		fReviewInfo = gson.fromJson(reader, ReviewInfo.class);

		assertEquals("Wrong labels", LABELS, fReviewInfo.getLabels());

	}

	// ------------------------------------------------------------------------
	// Test all
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ReviewInfo}.
	 */
	@Test
	public void testAllFields() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fReviewInfo = gson.fromJson(reader, ReviewInfo.class);

		assertEquals("labels", LABELS, fReviewInfo.getLabels());

	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ReviewInfo}.
	 */
	@Test
	public void testExtraField() {
		setAllFields();
		json.addProperty("extra", "extra_property");
		Reader reader = new StringReader(json.toString());
		fReviewInfo = gson.fromJson(reader, ReviewInfo.class);

		assertEquals("labels", LABELS, fReviewInfo.getLabels());
	}

	// ------------------------------------------------------------------------
	// Object
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ReviewInfo#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fReviewInfo = gson.fromJson(reader, ReviewInfo.class);

		assertEquals("Wrong hash code", HASH_CODE, fReviewInfo.hashCode());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ReviewInfo#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fReviewInfo = gson.fromJson(reader, ReviewInfo.class);

		assertTrue(fReviewInfo.equals(fReviewInfo));
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ReviewInfo#toString()}.
	 */
	@Test
	public void testToString() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fReviewInfo = gson.fromJson(reader, ReviewInfo.class);

		assertEquals("Wrong string", TO_STRING, fReviewInfo.toString());
	}

}
