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
import org.eclipse.egerrit.internal.model.GitPersonInfo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/**
 * Test suite for {@link org.eclipse.egerrit.internal.model.GitPersonInfo}
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class GitPersonInfoTest {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	private static final String NAME = "M. Gerrit";

	private static final String EMAIL = "gerrit@gerrit.org";

	private static final String DATE = "March 15, 44 BC";

	private static final String TZ = "-5";

	private static final int HASH_CODE = -511229410;

	private static final String TO_STRING = "GitPersonInfo [" + "name=" + NAME + ", email=" + EMAIL + ", date=" + DATE
			+ ", tz=" + TZ + "]";

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
	private GitPersonInfo fGitPersonInfo = null;

	// ------------------------------------------------------------------------
	// Helper methods
	// ------------------------------------------------------------------------

	@Before
	public void setUp() throws Exception {
		gson = new GsonBuilder().registerTypeAdapterFactory(new EMFTypeAdapterFactory()).create();
		json = new JsonObject();
	}

	private void setAllFields() {
		json.addProperty("name", NAME);
		json.addProperty("email", EMAIL);
		json.addProperty("date", DATE);
		json.addProperty("tz", TZ);
	}

	@After
	public void tearDown() throws Exception {
	}

	// ------------------------------------------------------------------------
	// Test cases
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.GitPersonInfo#getName()}.
	 */
	@Test
	public void testGetName() {
		json.addProperty("name", NAME);
		Reader reader = new StringReader(json.toString());
		fGitPersonInfo = gson.fromJson(reader, GitPersonInfo.class);

		assertEquals("Wrong name", NAME, fGitPersonInfo.getName());
		assertNull("Wrong email", fGitPersonInfo.getEmail());
		assertNull("Wrong date", fGitPersonInfo.getDate());
		assertEquals("Wrong tz", 0, fGitPersonInfo.getTz());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.GitPersonInfo#getEmail()}.
	 */
	@Test
	public void testGetEmail() {
		json.addProperty("email", EMAIL);
		Reader reader = new StringReader(json.toString());
		fGitPersonInfo = gson.fromJson(reader, GitPersonInfo.class);

		assertNull("Wrong name", fGitPersonInfo.getName());
		assertEquals("Wrong email", EMAIL, fGitPersonInfo.getEmail());
		assertNull("Wrong date", fGitPersonInfo.getDate());
		assertEquals("Wrong tz", 0, fGitPersonInfo.getTz());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.GitPersonInfo#getDate()}.
	 */
	@Test
	public void testGetDate() {
		json.addProperty("date", DATE);
		Reader reader = new StringReader(json.toString());
		fGitPersonInfo = gson.fromJson(reader, GitPersonInfo.class);

		assertNull("Wrong name", fGitPersonInfo.getName());
		assertNull("Wrong email", fGitPersonInfo.getEmail());
		assertEquals("Wrong date", DATE, fGitPersonInfo.getDate());
		assertEquals("Wrong tz", 0, fGitPersonInfo.getTz());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.GitPersonInfo#getTZ()}.
	 */
	@Test
	public void testGetTZ() {
		json.addProperty("tz", TZ);
		Reader reader = new StringReader(json.toString());
		fGitPersonInfo = gson.fromJson(reader, GitPersonInfo.class);

		assertNull("Wrong name", fGitPersonInfo.getName());
		assertNull("Wrong email", fGitPersonInfo.getEmail());
		assertNull("Wrong date", fGitPersonInfo.getDate());
		assertEquals("Wrong tz", (int) Integer.valueOf(TZ), fGitPersonInfo.getTz());
	}

	// ------------------------------------------------------------------------
	// Test all
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.GitPersonInfo}.
	 */
	@Test
	public void testAllFields() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fGitPersonInfo = gson.fromJson(reader, GitPersonInfo.class);

		assertEquals("Wrong name", NAME, fGitPersonInfo.getName());
		assertEquals("Wrong email", EMAIL, fGitPersonInfo.getEmail());
		assertEquals("Wrong date", DATE, fGitPersonInfo.getDate());
		assertEquals("Wrong tz", (int) Integer.valueOf(TZ), fGitPersonInfo.getTz());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.GitPersonInfo}.
	 */
	@Test
	public void testExtraField() {
		setAllFields();
		json.addProperty("extra", "extra_property");
		Reader reader = new StringReader(json.toString());
		fGitPersonInfo = gson.fromJson(reader, GitPersonInfo.class);

		assertEquals("Wrong name", NAME, fGitPersonInfo.getName());
		assertEquals("Wrong email", EMAIL, fGitPersonInfo.getEmail());
		assertEquals("Wrong date", DATE, fGitPersonInfo.getDate());
		assertEquals("Wrong tz", (int) Integer.valueOf(TZ), fGitPersonInfo.getTz());
	}

	// ------------------------------------------------------------------------
	// Object
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.GitPersonInfo#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fGitPersonInfo = gson.fromJson(reader, GitPersonInfo.class);

		assertEquals("Wrong hash code", HASH_CODE, fGitPersonInfo.hashCode());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.GitPersonInfo#equals(java.lang.Object)}.
	 */
//  @Test
	public void testEqualsObject() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.GitPersonInfo#toString()}.
	 */
	@Test
	public void testToString() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fGitPersonInfo = gson.fromJson(reader, GitPersonInfo.class);

		assertEquals("Wrong string", TO_STRING, fGitPersonInfo.toString());
	}

}
