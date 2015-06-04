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

import org.eclipse.egerrit.core.rest.RelatedChangeAndCommitInfo;
import org.eclipse.egerrit.core.rest.RelatedChangesInfo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Test suite for {@link org.eclipse.egerrit.core.rest.RelatedChangesInfo}
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class RelatedChangesInfoTest {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	private static final boolean OPTIONAL = true;

	// LABELS

	// DETAILED_LABELS
	private static final List<RelatedChangeAndCommitInfo> CHANGES = new LinkedList<RelatedChangeAndCommitInfo>();

	private static final int HASH_CODE = 32;

	private static final String TO_STRING = "RelatedChangesInfo [" + "changes=" + CHANGES + "]";

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
	private RelatedChangesInfo fRelatedChangesInfo = null;

	// ------------------------------------------------------------------------
	// Helper methods
	// ------------------------------------------------------------------------

	@Before
	public void setUp() throws Exception {
		gson = new GsonBuilder().create();
		json = new JsonObject();
	}

	private void setAllFields() {
		JsonArray changes = new JsonArray();
		json.add("changes", changes);
		JsonObject values = new JsonObject();
		json.add("values", values);
	}

	@After
	public void tearDown() throws Exception {
	}

	// ------------------------------------------------------------------------
	// Test cases
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.LabelInfo#getChanges()}.
	 */
	@Test
	public void testGetChanges() {
		JsonArray changes = new JsonArray();
		json.add("changes", changes);
		Reader reader = new StringReader(json.toString());
		fRelatedChangesInfo = gson.fromJson(reader, RelatedChangesInfo.class);

		assertEquals("Wrong changes", CHANGES, fRelatedChangesInfo.getChanges());
	}

	// ------------------------------------------------------------------------
	// Test all
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.RelatedChangesInfo}.
	 */
	@Test
	public void testExtraField() {
		setAllFields();
		json.addProperty("extra", "extra_property");
		Reader reader = new StringReader(json.toString());
		fRelatedChangesInfo = gson.fromJson(reader, RelatedChangesInfo.class);

		assertEquals("Wrong changes", CHANGES, fRelatedChangesInfo.getChanges());
	}

	// ------------------------------------------------------------------------
	// Object
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.LabelInfo#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fRelatedChangesInfo = gson.fromJson(reader, RelatedChangesInfo.class);
		assertEquals("Wrong hash code", HASH_CODE, fRelatedChangesInfo.hashCode());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.LabelInfo#equals(java.lang.Object)}.
	 */
	// @Test
	public void testEqualsObject() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.LabelInfo#toString()}.
	 */
	@Test
	public void testToString() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fRelatedChangesInfo = gson.fromJson(reader, RelatedChangesInfo.class);

		assertEquals("Wrong string", TO_STRING, fRelatedChangesInfo.toString());
	}

}
