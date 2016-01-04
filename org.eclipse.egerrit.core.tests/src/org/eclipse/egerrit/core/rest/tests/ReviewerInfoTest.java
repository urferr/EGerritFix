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

import org.eclipse.egerrit.core.command.EMFTypeAdapterFactory;
import org.eclipse.egerrit.internal.model.ReviewerInfo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/**
 * Test suite for {@link org.eclipse.egerrit.internal.model.ReviewerInfo}
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class ReviewerInfoTest {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	private static final String ACCOUNT_ID = "10";

	private static final String NAME = "M. Gerrit";

	private static final String EMAIL = "gerrit@gerrit.org";

	private static final String USER_NAME = "gerrit";

	private static final int HASH_CODE = 1026051886;

	private static final String TO_STRING = "AccountInfo [" + "_account_id=" + ACCOUNT_ID + ", name=" + NAME
			+ ", email=" + EMAIL + ", username=" + USER_NAME + "]";

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
	private ReviewerInfo fReviewerInfo = null;

	// ------------------------------------------------------------------------
	// Helper methods
	// ------------------------------------------------------------------------

	@Before
	public void setUp() throws Exception {
		gson = new GsonBuilder().registerTypeAdapterFactory(new EMFTypeAdapterFactory()).create();
		json = new JsonObject();
	}

	private void setAllFields() {
		json.addProperty("_account_id", ACCOUNT_ID);
		json.addProperty("name", NAME);
		json.addProperty("email", EMAIL);
		json.addProperty("username", USER_NAME);
	}

	@After
	public void tearDown() throws Exception {
	}

	// ------------------------------------------------------------------------
	// Test cases
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.AccountInfo#get_account_id()}.
	 */
	@Test
	public void testget_account_id() {
		json.addProperty("_account_id", ACCOUNT_ID);
		Reader reader = new StringReader(json.toString());
		fReviewerInfo = gson.fromJson(reader, ReviewerInfo.class);

		assertEquals("Wrong _account_id", (int) Integer.valueOf(ACCOUNT_ID), fReviewerInfo.get_account_id());
		assertNull("Wrong name", fReviewerInfo.getName());
		assertNull("Wrong email", fReviewerInfo.getEmail());
		assertNull("Wrong user_name", fReviewerInfo.getUsername());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.AccountInfo#getName()}.
	 */
	@Test
	public void testGetName() {
		json.addProperty("name", NAME);
		Reader reader = new StringReader(json.toString());
		fReviewerInfo = gson.fromJson(reader, ReviewerInfo.class);

		assertEquals("Wrong _account_id", -1, fReviewerInfo.get_account_id());
		assertEquals("Wrong name", NAME, fReviewerInfo.getName());
		assertNull("Wrong email", fReviewerInfo.getEmail());
		assertNull("Wrong user_name", fReviewerInfo.getUsername());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.AccountInfo#getEmail()}.
	 */
	@Test
	public void testGetEmail() {
		json.addProperty("email", EMAIL);
		Reader reader = new StringReader(json.toString());
		fReviewerInfo = gson.fromJson(reader, ReviewerInfo.class);

		assertEquals("Wrong _account_id", -1, fReviewerInfo.get_account_id());
		assertNull("Wrong name", fReviewerInfo.getName());
		assertEquals("Wrong email", EMAIL, fReviewerInfo.getEmail());
		assertNull("Wrong user_name", fReviewerInfo.getUsername());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.AccountInfo#getUsername()}.
	 */
	@Test
	public void testGetUsername() {
		json.addProperty("username", USER_NAME);
		Reader reader = new StringReader(json.toString());
		fReviewerInfo = gson.fromJson(reader, ReviewerInfo.class);

		assertEquals("Wrong _account_id", -1, fReviewerInfo.get_account_id());
		assertNull("Wrong name", fReviewerInfo.getName());
		assertNull("Wrong email", fReviewerInfo.getEmail());
		assertEquals("Wrong user_name", USER_NAME, fReviewerInfo.getUsername());
	}

	// ------------------------------------------------------------------------
	// Test all
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.FileInfo}.
	 */
	@Test
	public void testAllFields() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fReviewerInfo = gson.fromJson(reader, ReviewerInfo.class);

		assertEquals("Wrong _account_id", (int) Integer.valueOf(ACCOUNT_ID), fReviewerInfo.get_account_id());
		assertEquals("Wrong name", NAME, fReviewerInfo.getName());
		assertEquals("Wrong email", EMAIL, fReviewerInfo.getEmail());
		assertEquals("Wrong user_name", USER_NAME, fReviewerInfo.getUsername());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.FileInfo}.
	 */
	@Test
	public void testExtraField() {
		setAllFields();
		json.addProperty("extra", "extra_property");
		Reader reader = new StringReader(json.toString());
		fReviewerInfo = gson.fromJson(reader, ReviewerInfo.class);

		assertEquals("Wrong _account_id", (int) Integer.valueOf(ACCOUNT_ID), fReviewerInfo.get_account_id());
		assertEquals("Wrong name", NAME, fReviewerInfo.getName());
		assertEquals("Wrong email", EMAIL, fReviewerInfo.getEmail());
		assertEquals("Wrong user_name", USER_NAME, fReviewerInfo.getUsername());
	}

	// ------------------------------------------------------------------------
	// Object
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.AccountInfo#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fReviewerInfo = gson.fromJson(reader, ReviewerInfo.class);

		assertEquals("Wrong hash code", HASH_CODE, fReviewerInfo.hashCode());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.AccountInfo#equals(java.lang.Object)} .
	 */
	// @Test
	public void testEqualsObject() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.AccountInfo#toString()}.
	 */
	@Test
	public void testToString() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fReviewerInfo = gson.fromJson(reader, ReviewerInfo.class);

		assertEquals("Wrong string", TO_STRING, fReviewerInfo.toString());
	}

}
