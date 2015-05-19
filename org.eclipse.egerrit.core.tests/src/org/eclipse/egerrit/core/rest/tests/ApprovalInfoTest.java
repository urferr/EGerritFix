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

import org.eclipse.egerrit.core.rest.ApprovalInfo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/**
 * Test suite for {@link org.eclipse.egerrit.core.rest.ApprovalInfo}
 * 
 * @since 1.0
 */
@SuppressWarnings("nls")
public class ApprovalInfoTest {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	private static final String VALUE = "-2";

	private static final String DATE = "Sept 11, 2001";

	private static final int HASH_CODE = 2055867139;

	private static final String TO_STRING = "ApprovalInfo [" + "value=" + VALUE + ", date=" + DATE + "]";

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
	private ApprovalInfo fApprovalInfo = null;

	// ------------------------------------------------------------------------
	// Helper methods
	// ------------------------------------------------------------------------

	@Before
	public void setUp() throws Exception {
		gson = new GsonBuilder().create();
		json = new JsonObject();
	}

	private void setAllFields() {
		json.addProperty("value", VALUE);
		json.addProperty("date", DATE);
	}

	@After
	public void tearDown() throws Exception {
	}

	// ------------------------------------------------------------------------
	// Test cases
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ApprovalInfo#getValue()}.
	 */
	@Test
	public void testGetValue() {
		json.addProperty("value", VALUE);
		Reader reader = new StringReader(json.toString());
		fApprovalInfo = gson.fromJson(reader, ApprovalInfo.class);

		assertEquals("Wrong value", Integer.valueOf(VALUE), fApprovalInfo.getValue());
		assertNull("Wrong date", fApprovalInfo.getDate());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ApprovalInfo#getDate()}.
	 */
	@Test
	public void testGetDate() {
		json.addProperty("date", DATE);
		Reader reader = new StringReader(json.toString());
		fApprovalInfo = gson.fromJson(reader, ApprovalInfo.class);

		assertNull("Wrong value", fApprovalInfo.getValue());
		assertEquals("Wrong date", DATE, fApprovalInfo.getDate());
	}

	// ------------------------------------------------------------------------
	// Test all
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ApprovalInfo}.
	 */
	@Test
	public void testAllFields() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fApprovalInfo = gson.fromJson(reader, ApprovalInfo.class);

		assertEquals("Wrong value", Integer.valueOf(VALUE), fApprovalInfo.getValue());
		assertEquals("Wrong date", DATE, fApprovalInfo.getDate());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ApprovalInfo}.
	 */
	@Test
	public void testExtraField() {
		setAllFields();
		json.addProperty("extra", "extra_property");
		Reader reader = new StringReader(json.toString());
		fApprovalInfo = gson.fromJson(reader, ApprovalInfo.class);

		assertEquals("Wrong value", Integer.valueOf(VALUE), fApprovalInfo.getValue());
		assertEquals("Wrong date", DATE, fApprovalInfo.getDate());
	}

	// ------------------------------------------------------------------------
	// Object
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ApprovalInfo#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fApprovalInfo = gson.fromJson(reader, ApprovalInfo.class);

		assertEquals("Wrong hash code", HASH_CODE, fApprovalInfo.hashCode());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ActionInfo#equals(java.lang.Object)}.
	 */
	// @Test
	public void testEqualsObject() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ApprovalInfo#toString()}.
	 */
	@Test
	public void testToString() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fApprovalInfo = gson.fromJson(reader, ApprovalInfo.class);

		assertEquals("Wrong string", TO_STRING, fApprovalInfo.toString());
	}

}
