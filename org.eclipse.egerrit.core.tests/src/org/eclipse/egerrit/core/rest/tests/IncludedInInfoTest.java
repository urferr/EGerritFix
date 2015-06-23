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
import static org.junit.Assert.fail;

import java.io.Reader;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.egerrit.core.rest.IncludedInInfo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Test suite for {@link org.eclipse.egerrit.core.rest.IncludedInInfo}
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class IncludedInInfoTest {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	private static final List<String> BRANCHES = new LinkedList<String>();

	private static final List<String> TAGS = new LinkedList<String>();

	private static final int HASH_CODE = 993;

	private static final String TO_STRING = "IncludedInInfo [" + "branches=" + BRANCHES + ", tags=" + TAGS + "]";

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
	private IncludedInInfo fIncludedInInfo = null;

	// ------------------------------------------------------------------------
	// Helper methods
	// ------------------------------------------------------------------------

	@Before
	public void setUp() throws Exception {
		gson = new GsonBuilder().create();
		json = new JsonObject();
	}

	private void setAllFields() {
		JsonArray branches = new JsonArray();
		json.add("branches", branches);
		JsonArray tags = new JsonArray();
		json.add("tags", tags);
	}

	@After
	public void tearDown() throws Exception {
	}

	// ------------------------------------------------------------------------
	// Test cases
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.IncludedInInfo#getBranches()}.
	 */
	@Test
	public void testGetBranches() {
		JsonArray branches = new JsonArray();
		json.add("branches", branches);
		Reader reader = new StringReader(json.toString());
		fIncludedInInfo = gson.fromJson(reader, IncludedInInfo.class);

		assertEquals("Wrong branches", BRANCHES, fIncludedInInfo.getBranches());
		assertEquals("Wrong tags", TAGS, fIncludedInInfo.getTags());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.IncludedInInfo#getTags()}.
	 */
	@Test
	public void testGetTags() {
		JsonArray tags = new JsonArray();
		json.add("tags", tags);
		Reader reader = new StringReader(json.toString());
		fIncludedInInfo = gson.fromJson(reader, IncludedInInfo.class);

		assertEquals("Wrong branches", BRANCHES, fIncludedInInfo.getBranches());
		assertEquals("Wrong tags", TAGS, fIncludedInInfo.getTags());
	}

	// ------------------------------------------------------------------------
	// Test all
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.IncludedInInfo}.
	 */
	@Test
	public void testAllFields() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fIncludedInInfo = gson.fromJson(reader, IncludedInInfo.class);

		assertEquals("Wrong branches", BRANCHES, fIncludedInInfo.getBranches());
		assertEquals("Wrong tags", TAGS, fIncludedInInfo.getTags());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.IncludedInInfo}.
	 */
	@Test
	public void testExtraField() {
		setAllFields();
		json.addProperty("extra", "extra_property");
		Reader reader = new StringReader(json.toString());
		fIncludedInInfo = gson.fromJson(reader, IncludedInInfo.class);

		assertEquals("Wrong branches", BRANCHES, fIncludedInInfo.getBranches());
		assertEquals("Wrong tags", TAGS, fIncludedInInfo.getTags());
	}

	// ------------------------------------------------------------------------
	// Object
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.IncludedInInfo#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fIncludedInInfo = gson.fromJson(reader, IncludedInInfo.class);

		assertEquals("Wrong hash code", HASH_CODE, fIncludedInInfo.hashCode());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.IncludedInInfo#equals(java.lang.Object)} .
	 */
	// @Test
	public void testEqualsObject() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ProblemInfo#toString()}.
	 */
	@Test
	public void testToString() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fIncludedInInfo = gson.fromJson(reader, IncludedInInfo.class);

		assertEquals("Wrong string", TO_STRING, fIncludedInInfo.toString());
	}

}
