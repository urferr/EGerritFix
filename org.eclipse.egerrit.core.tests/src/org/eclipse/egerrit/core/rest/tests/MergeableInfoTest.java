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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.Reader;
import java.io.StringReader;

import org.eclipse.egerrit.core.command.EMFTypeAdapterFactory;
import org.eclipse.egerrit.internal.model.MergeableInfo;
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
public class MergeableInfoTest {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	// Submit type used for this change, can be MERGE_IF_NECESSARY,
	// FAST_FORWARD_ONLY, REBASE_IF_NECESSARY, MERGE_ALWAYS or CHERRY_PICK.
	private final static String SUBMIT_TYPE = "MERGE_IF_NECESSARY";

	// A list of other branch names where this change could merge cleanly
	private final static String MERGEABLE_INTO = "testbranch";

	// true if this change is cleanly mergeable, false otherwise
	private final static boolean MERGEABLE = false;

	private static final String TO_STRING = "MergeableInfo [" + "submit_type=" + SUBMIT_TYPE + ", mergeable_into="
			+ MERGEABLE_INTO + ", mergeable=" + MERGEABLE + "]";

	private static final int HASH_CODE = -1584385742;

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

	private MergeableInfo fMergeableInfo = null;

	// ------------------------------------------------------------------------
	// Helper methods
	// ------------------------------------------------------------------------

	@Before
	public void setUp() throws Exception {
		gson = new GsonBuilder().registerTypeAdapterFactory(new EMFTypeAdapterFactory()).create();
		json = new JsonObject();
	}

	private void setAllFields() {
		json.addProperty("submit_type", SUBMIT_TYPE);
		json.addProperty("mergeable_into", MERGEABLE_INTO);
		json.addProperty("mergeable", MERGEABLE);
	}

	@After
	public void tearDown() throws Exception {
	}

	// ------------------------------------------------------------------------
	// Test cases
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.MergeableInfo#getSubmitType()}.
	 */
	@Test
	public void testGetSubmitType() {
		json.addProperty("submit_type", SUBMIT_TYPE);
		Reader reader = new StringReader(json.toString());
		fMergeableInfo = gson.fromJson(reader, MergeableInfo.class);

		assertEquals("Wrong submit_type", SUBMIT_TYPE, fMergeableInfo.getSubmit_type());
		assertNull("Wrong mergeable_into", fMergeableInfo.getMergeable_into());
		assertFalse("Wrong mergeable", fMergeableInfo.isMergeable());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.MergeableInfo#getMergeable_into()}.
	 */
	@Test
	public void testGetMergeableInto() {
		json.addProperty("mergeable_into", MERGEABLE_INTO);
		Reader reader = new StringReader(json.toString());
		fMergeableInfo = gson.fromJson(reader, MergeableInfo.class);

		assertNull("Wrong submit_type", fMergeableInfo.getSubmit_type());
		assertEquals("Wrong mergeable_into", MERGEABLE_INTO, fMergeableInfo.getMergeable_into());
		assertFalse("Wrong mergeable", fMergeableInfo.isMergeable());

	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.MergeableInfo#isMergeable()}.
	 */
	@Test
	public void testGetMergeable() {
		json.addProperty("mergeable", MERGEABLE);
		Reader reader = new StringReader(json.toString());
		fMergeableInfo = gson.fromJson(reader, MergeableInfo.class);

		assertNull("Wrong submit_type", fMergeableInfo.getSubmit_type());
		assertNull("Wrong mergeable_into", fMergeableInfo.getMergeable_into());
		assertEquals("Wrong mergeable", MERGEABLE, fMergeableInfo.isMergeable());
	}

	// ------------------------------------------------------------------------
	// Test all
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.MergeableInfo}.
	 */
	@Test
	public void testAllFields() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fMergeableInfo = gson.fromJson(reader, MergeableInfo.class);

		assertEquals("Wrong submit_type", SUBMIT_TYPE, fMergeableInfo.getSubmit_type());
		assertEquals("Wrong mergeable_into", MERGEABLE_INTO, fMergeableInfo.getMergeable_into());
		assertEquals("Wrong mergeable", MERGEABLE, fMergeableInfo.isMergeable());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.MergeableInfo}.
	 */
	@Test
	public void testExtraField() {
		setAllFields();
		json.addProperty("extra", "extra_property");
		Reader reader = new StringReader(json.toString());
		fMergeableInfo = gson.fromJson(reader, MergeableInfo.class);

		assertEquals("Wrong submit_type", SUBMIT_TYPE, fMergeableInfo.getSubmit_type());
		assertEquals("Wrong mergeable_into", MERGEABLE_INTO, fMergeableInfo.getMergeable_into());
		assertEquals("Wrong mergeable", MERGEABLE, fMergeableInfo.isMergeable());

	}

	// ------------------------------------------------------------------------
	// Object
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.MergeableInfo#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fMergeableInfo = gson.fromJson(reader, MergeableInfo.class);

		assertEquals("Wrong hash code", HASH_CODE, fMergeableInfo.hashCode());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.AccountInfo#equals(java.lang.Object)} .
	 */
	// @Test
	public void testEqualsObject() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.MergeableInfo#toString()}.
	 */
	@Test
	public void testToString() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fMergeableInfo = gson.fromJson(reader, MergeableInfo.class);

		assertEquals("Wrong string", TO_STRING, fMergeableInfo.toString());
	}

}
