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

import org.eclipse.egerrit.core.rest.AccountInfo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/**
 * Test suite for {@link org.eclipse.egerrit.core.rest.AccountInfo}
 * 
 * @since 1.0
 */
@SuppressWarnings("nls")
public class AccountInfoTest {

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
	private AccountInfo fAccountInfo = null;

	// ------------------------------------------------------------------------
	// Helper methods
	// ------------------------------------------------------------------------

	@Before
	public void setUp() throws Exception {
		gson = new GsonBuilder().create();
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
	 * Test method for {@link org.eclipse.egerrit.core.rest.AccountInfo#getAccountId()}.
	 */
	@Test
	public void testGetAccountId() {
		json.addProperty("_account_id", ACCOUNT_ID);
		Reader reader = new StringReader(json.toString());
		fAccountInfo = gson.fromJson(reader, AccountInfo.class);

		assertEquals("Wrong _account_id", (int) Integer.valueOf(ACCOUNT_ID), fAccountInfo.getAccountId());
		assertNull("Wrong name", fAccountInfo.getName());
		assertNull("Wrong email", fAccountInfo.getEmail());
		assertNull("Wrong user_name", fAccountInfo.getUsername());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.AccountInfo#getName()}.
	 */
	@Test
	public void testGetName() {
		json.addProperty("name", NAME);
		Reader reader = new StringReader(json.toString());
		fAccountInfo = gson.fromJson(reader, AccountInfo.class);

		assertEquals("Wrong _account_id", -1, fAccountInfo.getAccountId());
		assertEquals("Wrong name", NAME, fAccountInfo.getName());
		assertNull("Wrong email", fAccountInfo.getEmail());
		assertNull("Wrong user_name", fAccountInfo.getUsername());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.AccountInfo#getEmail()}.
	 */
	@Test
	public void testGetEmail() {
		json.addProperty("email", EMAIL);
		Reader reader = new StringReader(json.toString());
		fAccountInfo = gson.fromJson(reader, AccountInfo.class);

		assertEquals("Wrong _account_id", -1, fAccountInfo.getAccountId());
		assertNull("Wrong name", fAccountInfo.getName());
		assertEquals("Wrong email", EMAIL, fAccountInfo.getEmail());
		assertNull("Wrong user_name", fAccountInfo.getUsername());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.AccountInfo#getUsername()}.
	 */
	@Test
	public void testGetUsername() {
		json.addProperty("username", USER_NAME);
		Reader reader = new StringReader(json.toString());
		fAccountInfo = gson.fromJson(reader, AccountInfo.class);

		assertEquals("Wrong _account_id", -1, fAccountInfo.getAccountId());
		assertNull("Wrong name", fAccountInfo.getName());
		assertNull("Wrong email", fAccountInfo.getEmail());
		assertEquals("Wrong user_name", USER_NAME, fAccountInfo.getUsername());
	}

	// ------------------------------------------------------------------------
	// Test all
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.FileInfo}.
	 */
	@Test
	public void testAllFields() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fAccountInfo = gson.fromJson(reader, AccountInfo.class);

		assertEquals("Wrong _account_id", (int) Integer.valueOf(ACCOUNT_ID), fAccountInfo.getAccountId());
		assertEquals("Wrong name", NAME, fAccountInfo.getName());
		assertEquals("Wrong email", EMAIL, fAccountInfo.getEmail());
		assertEquals("Wrong user_name", USER_NAME, fAccountInfo.getUsername());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.FileInfo}.
	 */
	@Test
	public void testExtraField() {
		setAllFields();
		json.addProperty("extra", "extra_property");
		Reader reader = new StringReader(json.toString());
		fAccountInfo = gson.fromJson(reader, AccountInfo.class);

		assertEquals("Wrong _account_id", (int) Integer.valueOf(ACCOUNT_ID), fAccountInfo.getAccountId());
		assertEquals("Wrong name", NAME, fAccountInfo.getName());
		assertEquals("Wrong email", EMAIL, fAccountInfo.getEmail());
		assertEquals("Wrong user_name", USER_NAME, fAccountInfo.getUsername());
	}

	// ------------------------------------------------------------------------
	// Object
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.AccountInfo#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fAccountInfo = gson.fromJson(reader, AccountInfo.class);

		assertEquals("Wrong hash code", HASH_CODE, fAccountInfo.hashCode());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.AccountInfo#equals(java.lang.Object)}.
	 */
//    @Test
	public void testEqualsObject() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.AccountInfo#toString()}.
	 */
	@Test
	public void testToString() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fAccountInfo = gson.fromJson(reader, AccountInfo.class);

		assertEquals("Wrong string", TO_STRING, fAccountInfo.toString());
	}

}
