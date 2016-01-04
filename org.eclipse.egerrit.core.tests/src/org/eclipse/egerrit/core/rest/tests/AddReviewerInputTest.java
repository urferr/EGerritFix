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
import static org.junit.Assert.assertTrue;

import java.io.Reader;
import java.io.StringReader;

import org.eclipse.egerrit.core.command.EMFTypeAdapterFactory;
import org.eclipse.egerrit.core.rest.AddReviewerInput;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/**
 * Test suite for {@link org.eclipse.egerrit.core.rest.AddReviewerInput}
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class AddReviewerInputTest {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	private static final String REVIEWER = "reviewer1";

	private static final boolean CONFIRMED = true;

	private static final int HASH_CODE = 493079454;

	private static final String TO_STRING = "ReviewerInput [reviewer=" + REVIEWER + ", confirmed=" + CONFIRMED + "]";

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
	private AddReviewerInput fAddReviewerInput = null;

	// ------------------------------------------------------------------------
	// Helper methods
	// ------------------------------------------------------------------------

	@Before
	public void setUp() throws Exception {
		gson = new GsonBuilder().registerTypeAdapterFactory(new EMFTypeAdapterFactory()).create();
		json = new JsonObject();
	}

	private void setAllFields() {

		json.addProperty("reviewer", REVIEWER);
		json.addProperty("confirmed", CONFIRMED);
	}

	@After
	public void tearDown() throws Exception {
	}

	// ------------------------------------------------------------------------
	// Test cases
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.AddReviewerInput#testgetReviewer()}.
	 */
	@Test
	public void testGetReviewer() {
		json.addProperty("reviewer", REVIEWER);
		Reader reader = new StringReader(json.toString());
		fAddReviewerInput = gson.fromJson(reader, AddReviewerInput.class);

		assertEquals("Wrong user", REVIEWER, fAddReviewerInput.getReviewer());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.AddReviewerInput#testgetConfirmed()}.
	 */
	@Test
	public void testgetConfirmed() {
		json.addProperty("confirmed", CONFIRMED);
		Reader reader = new StringReader(json.toString());
		fAddReviewerInput = gson.fromJson(reader, AddReviewerInput.class);

		assertEquals("Wrong confirmed", CONFIRMED, fAddReviewerInput.getConfirmed());
	}
	// ------------------------------------------------------------------------
	// Object
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.AddReviewerInput#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fAddReviewerInput = gson.fromJson(reader, AddReviewerInput.class);

		assertEquals("Wrong hash code", HASH_CODE, fAddReviewerInput.hashCode());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.AddReviewerInput#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fAddReviewerInput = gson.fromJson(reader, AddReviewerInput.class);

		assertTrue("Wrong equal", fAddReviewerInput.equals(fAddReviewerInput));
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.AddReviewerInput#toString()}.
	 */
	@Test
	public void testToString() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fAddReviewerInput = gson.fromJson(reader, AddReviewerInput.class);

		assertEquals("Wrong string", TO_STRING, fAddReviewerInput.toString());

	}

}
