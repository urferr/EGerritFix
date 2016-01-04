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
import org.eclipse.egerrit.internal.model.AccountInfo;
import org.eclipse.egerrit.internal.model.GroupBaseInfo;
import org.eclipse.egerrit.internal.model.ModelFactory;
import org.eclipse.egerrit.internal.model.SuggestReviewerInfo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/**
 * Test suite for {@link org.eclipse.egerrit.internal.model.SuggestReviewerInfo}
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class SuggestReviewerInfoTest {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	private static AccountInfo ACCOUNT = ModelFactory.eINSTANCE.createAccountInfo();

	private static GroupBaseInfo GROUP = ModelFactory.eINSTANCE.createGroupBaseInfo();

	private static final int HASH_CODE = 27707552;

	private static final String TO_STRING = "SuggestReviewerInfo [" + ACCOUNT + GROUP + "]";

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
	private SuggestReviewerInfo fSuggestedInfo = null;

	// ------------------------------------------------------------------------
	// Helper methods
	// ------------------------------------------------------------------------

	@Before
	public void setUp() throws Exception {
		gson = new GsonBuilder().registerTypeAdapterFactory(new EMFTypeAdapterFactory()).create();
		json = new JsonObject();
	}

	private void setAllFields() {
		JsonObject account = new JsonObject();
		json.add("account", account);
		JsonObject group = new JsonObject();
		json.add("group", group);

	}

	@After
	public void tearDown() throws Exception {
	}

	// ------------------------------------------------------------------------
	// Test cases
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.SuggestReviewerInfo#getAccount()}.
	 */
	@Test
	public void testGetAccount() {
		JsonObject account = new JsonObject();
		json.add("account", account);
		Reader reader = new StringReader(json.toString());
		fSuggestedInfo = gson.fromJson(reader, SuggestReviewerInfo.class);

		assertEquals("Wrong account", ACCOUNT, fSuggestedInfo.getAccount());

	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.SuggestReviewerInfo#getGroup()} .
	 */
	@Test
	public void testGetGroup() {
		JsonObject group = new JsonObject();
		json.add("group", group);
		Reader reader = new StringReader(json.toString());
		fSuggestedInfo = gson.fromJson(reader, SuggestReviewerInfo.class);

		assertEquals("Wrong group", GROUP, fSuggestedInfo.getGroup());
	}

	// ------------------------------------------------------------------------
	// Test all
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.SuggestReviewerInfo}.
	 */
	@Test
	public void testAllFields() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fSuggestedInfo = gson.fromJson(reader, SuggestReviewerInfo.class);

		assertEquals("Wrong account", ACCOUNT, fSuggestedInfo.getAccount());
		assertEquals("Wrong group", GROUP, fSuggestedInfo.getGroup());

	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.SuggestReviewerInfo}.
	 */
	@Test
	public void testExtraField() {
		setAllFields();
		json.addProperty("extra", "extra_property");
		Reader reader = new StringReader(json.toString());
		fSuggestedInfo = gson.fromJson(reader, SuggestReviewerInfo.class);

		assertEquals("Wrong account", ACCOUNT, fSuggestedInfo.getAccount());
		assertEquals("Wrong group", GROUP, fSuggestedInfo.getGroup());

	}

	// ------------------------------------------------------------------------
	// Object
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.SuggestReviewerInfo#hashCode()} .
	 */
	@Test
	public void testHashCode() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fSuggestedInfo = gson.fromJson(reader, SuggestReviewerInfo.class);

		assertEquals("Wrong hash code", HASH_CODE, fSuggestedInfo.hashCode());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.SuggestReviewerInfo#equals(java.lang.Object)}.
	 */
	// @Test
	public void testEqualsObject() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fSuggestedInfo = gson.fromJson(reader, SuggestReviewerInfo.class);

		assertTrue("Wrong hash code", fSuggestedInfo.equals(fSuggestedInfo));
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.SuggestReviewerInfo#toString()} .
	 */
	@Test
	public void testToString() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fSuggestedInfo = gson.fromJson(reader, SuggestReviewerInfo.class);
		assertEquals("Wrong string", TO_STRING, fSuggestedInfo.toString());
	}

}
