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
import java.util.ArrayList;
import java.util.List;

import org.eclipse.egerrit.core.rest.AddReviewerResult;
import org.eclipse.egerrit.core.rest.ReviewInfo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Test suite for {@link org.eclipse.egerrit.core.rest.AddReviewerInput}
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class AddReviewerResultTest {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	private static final ReviewInfo REVIEWINFO1 = new ReviewInfo();

	private static final ReviewInfo REVIEWINFO2 = new ReviewInfo();

	private static final List<ReviewInfo> REVIEWERS = new ArrayList<ReviewInfo>();

	private static final String ERROR = "";

	private static final boolean CONFIRMED = true;

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
	private AddReviewerResult fAddReviewerResult = null;

	// ------------------------------------------------------------------------
	// Helper methods
	// ------------------------------------------------------------------------

	@Before
	public void setUp() throws Exception {
		gson = new GsonBuilder().create();
		json = new JsonObject();
	}

	private void setAllFields() {
		REVIEWERS.add(REVIEWINFO1);
		REVIEWERS.add(REVIEWINFO2);

		json.addProperty("error", ERROR);
		json.addProperty("confirmed", CONFIRMED);
		JsonArray list_reviewers = new JsonArray();
		list_reviewers.add(gson.toJsonTree(REVIEWINFO1));
		list_reviewers.add(gson.toJsonTree(REVIEWINFO2));
		json.add("reviewers", list_reviewers);

	}

	@After
	public void tearDown() throws Exception {
	}

	// ------------------------------------------------------------------------
	// Test cases
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.AddReviewerResult#testgetConfirmed()}.
	 */
	@Test
	public void testgetConfirmed() {
		json.addProperty("confirm", CONFIRMED);
		Reader reader = new StringReader(json.toString());
		fAddReviewerResult = gson.fromJson(reader, AddReviewerResult.class);

		assertEquals("Wrong confirmed", CONFIRMED, fAddReviewerResult.getConfirm());
	}
	// ------------------------------------------------------------------------
	// Object
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.AddReviewerResult#getError()}.
	 */
	@Test
	public void testgetError() {
		json.addProperty("error", ERROR);
		Reader reader = new StringReader(json.toString());
		fAddReviewerResult = gson.fromJson(reader, AddReviewerResult.class);

		assertEquals("Wrong error", ERROR, fAddReviewerResult.getError());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.AddReviewerResult#getReviewers()}.
	 */
	@Test
	public void testGetEmptyReviewer() {
		REVIEWERS.clear();
		JsonArray reviewers = new JsonArray();
		json.add("reviewers", reviewers);
		Reader reader = new StringReader(json.toString());
		fAddReviewerResult = gson.fromJson(reader, AddReviewerResult.class);

		assertEquals("Wrong reviewer", REVIEWERS, fAddReviewerResult.getReviewers());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.AddReviewerResult#getReviewers(java.lang.Object)}.
	 */
	@Test
	public void testgetListReviewers() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fAddReviewerResult = gson.fromJson(reader, AddReviewerResult.class);

		assertEquals("Wrong reviewers", REVIEWERS, fAddReviewerResult.getReviewers());
	}

}
