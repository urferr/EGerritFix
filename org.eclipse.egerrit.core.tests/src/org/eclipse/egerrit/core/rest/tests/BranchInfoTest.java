/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson - Initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.core.rest.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.Reader;
import java.io.StringReader;

import org.eclipse.egerrit.core.rest.BranchInfo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/**
 * Test suite for {@link org.eclipse.egerrit.core.rest.BranchInfo}
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class BranchInfoTest {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	private static final String REF = "HEAD";

	private static final String REVISION = "user";

	private static final boolean CAN_DELETE = false;

	private static final int HASH_CODE = 73431519;

	private static final String TO_STRING = "BranchInfo [ref=" + REF + ", revision=" + REVISION + ", can_delete="
			+ CAN_DELETE + "]";

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
	private BranchInfo fBranchInfo = null;

	// ------------------------------------------------------------------------
	// Helper methods
	// ------------------------------------------------------------------------

	@Before
	public void setUp() throws Exception {
		gson = new GsonBuilder().create();
		json = new JsonObject();
	}

	private void setAllFields() {
		json.addProperty("ref", REF);
		json.addProperty("revision", REVISION);
		json.addProperty("can_delete", CAN_DELETE);

	}

	@After
	public void tearDown() throws Exception {
	}

	// ------------------------------------------------------------------------
	// Test cases
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.BranchInfo#getRef()}.
	 */
	@Test
	public void testGetRef() {
		json.addProperty("ref", REF);
		Reader reader = new StringReader(json.toString());
		fBranchInfo = gson.fromJson(reader, BranchInfo.class);

		assertEquals("Wrong ref", REF, fBranchInfo.getRef());

	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.BranchInfo#getrevision()} .
	 */
	@Test
	public void testGetRevision() {
		json.addProperty("revision", REVISION);
		Reader reader = new StringReader(json.toString());
		fBranchInfo = gson.fromJson(reader, BranchInfo.class);

		assertEquals("Wrong revision", REVISION, fBranchInfo.getRevision());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.BranchInfo#canDelete()}.
	 */
	@Test
	public void testCanDelete() {
		json.addProperty("can_delete", CAN_DELETE);
		Reader reader = new StringReader(json.toString());
		fBranchInfo = gson.fromJson(reader, BranchInfo.class);

		assertEquals("Wrong can_delete", CAN_DELETE, fBranchInfo.isCan_delete());
	}

	// ------------------------------------------------------------------------
	// Test all
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.BranchInfo}.
	 */
	@Test
	public void testAllFields() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fBranchInfo = gson.fromJson(reader, BranchInfo.class);

		assertEquals("Wrong ref", REF, fBranchInfo.getRef());
		assertEquals("Wrong revision", REVISION, fBranchInfo.getRevision());
		assertEquals("Wrong can_delete", CAN_DELETE, fBranchInfo.isCan_delete());

	}

	// ------------------------------------------------------------------------
	// Object
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.BranchInfo#hashCode()} .
	 */
	@Test
	public void testHashCode() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fBranchInfo = gson.fromJson(reader, BranchInfo.class);

		assertEquals("Wrong hash code", HASH_CODE, fBranchInfo.hashCode());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.BranchInfo#equals(java.lang.Object)}.
	 */
	// @Test
	public void testEqualsObject() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fBranchInfo = gson.fromJson(reader, BranchInfo.class);

		assertTrue("Wrong hash code", fBranchInfo.equals(fBranchInfo));
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.BranchInfo#toString()} .
	 */
	@Test
	public void testToString() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fBranchInfo = gson.fromJson(reader, BranchInfo.class);

		assertEquals("Wrong string", TO_STRING, fBranchInfo.toString());
	}

}
