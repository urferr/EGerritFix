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

import org.eclipse.egerrit.core.rest.GroupBaseInfo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/**
 * Test suite for {@link org.eclipse.egerrit.core.rest.GroupBaseInfo}
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class GroupBaseInfoTest {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	// The id of the group
	private static String ID = "id";

	// The name of the group
	private static String NAME = "testing";

	private static final int HASH_CODE = -1422341098;

	private static final String TO_STRING = "GroupBaseInfo [id=" + ID + ", name=" + NAME + "]";

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
	private GroupBaseInfo fGroupBaseInfo = null;

	// ------------------------------------------------------------------------
	// Helper methods
	// ------------------------------------------------------------------------

	@Before
	public void setUp() throws Exception {
		gson = new GsonBuilder().create();
		json = new JsonObject();
	}

	private void setAllFields() {
		json.addProperty("id", ID);
		json.addProperty("name", NAME);

	}

	@After
	public void tearDown() throws Exception {
	}

	// ------------------------------------------------------------------------
	// Test cases
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.GroupBaseInfo#getId()}.
	 */
	@Test
	public void testGetId() {
		json.addProperty("id", ID);
		Reader reader = new StringReader(json.toString());
		fGroupBaseInfo = gson.fromJson(reader, GroupBaseInfo.class);

		assertEquals("Wrong ID", ID, fGroupBaseInfo.getId());

	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.GroupBaseInfo#getName()}.
	 */
	@Test
	public void testgetName() {
		json.addProperty("name", NAME);
		Reader reader = new StringReader(json.toString());
		fGroupBaseInfo = gson.fromJson(reader, GroupBaseInfo.class);

		assertEquals("Wrong Name", NAME, fGroupBaseInfo.getName());

	}

	// ------------------------------------------------------------------------
	// Test all
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.GroupBaseInfo}.
	 */
	@Test
	public void testAllFields() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fGroupBaseInfo = gson.fromJson(reader, GroupBaseInfo.class);

		assertEquals("Wrong account", ID, fGroupBaseInfo.getId());
		assertEquals("Wrong group", NAME, fGroupBaseInfo.getName());

	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.GroupBaseInfo}.
	 */
	@Test
	public void testExtraField() {
		setAllFields();
		json.addProperty("extra", "extra_property");
		Reader reader = new StringReader(json.toString());
		fGroupBaseInfo = gson.fromJson(reader, GroupBaseInfo.class);

		assertEquals("Wrong account", ID, fGroupBaseInfo.getId());
		assertEquals("Wrong group", NAME, fGroupBaseInfo.getName());

	}

	// ------------------------------------------------------------------------
	// Object
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.GroupBaseInfo#hashCode()} .
	 */
	@Test
	public void testHashCode() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fGroupBaseInfo = gson.fromJson(reader, GroupBaseInfo.class);

		assertEquals("Wrong hash code", HASH_CODE, fGroupBaseInfo.hashCode());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.GroupBaseInfo#equals(java.lang.Object)}.
	 */
	// @Test
	public void testEqualsObject() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fGroupBaseInfo = gson.fromJson(reader, GroupBaseInfo.class);

		assertTrue("Wrong hash code", fGroupBaseInfo.equals(fGroupBaseInfo));
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.GroupBaseInfo#toString()} .
	 */
	@Test
	public void testToString() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fGroupBaseInfo = gson.fromJson(reader, GroupBaseInfo.class);
		assertEquals("Wrong string", TO_STRING, fGroupBaseInfo.toString());
	}

}
