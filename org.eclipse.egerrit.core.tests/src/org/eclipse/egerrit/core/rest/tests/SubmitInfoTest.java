/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Guy Perron - Initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.core.rest.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.Reader;
import java.io.StringReader;

import org.eclipse.egerrit.core.command.EMFTypeAdapterFactory;
import org.eclipse.egerrit.internal.model.SubmitInfo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/**
 * Test suite for {@link org.eclipse.egerrit.internal.model.SubmitInfo}
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class SubmitInfoTest {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	private static final String STATUS = "MERGED";

	private static final String ON_BEHALF_OF = "user";

	private static final int HASH_CODE = -1912706942;

	private static final String TO_STRING = "SubmitInfo [status=" + STATUS + ", on_behalf_of=" + ON_BEHALF_OF + "]";

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
	private SubmitInfo fSubmitInfo = null;

	// ------------------------------------------------------------------------
	// Helper methods
	// ------------------------------------------------------------------------

	@Before
	public void setUp() throws Exception {
		gson = new GsonBuilder().registerTypeAdapterFactory(new EMFTypeAdapterFactory()).create();
		json = new JsonObject();
	}

	private void setAllFields() {
		json.addProperty("status", STATUS);
		json.addProperty("on_behalf_of", ON_BEHALF_OF);

	}

	@After
	public void tearDown() throws Exception {
	}

	// ------------------------------------------------------------------------
	// Test cases
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.SubmitInfo#getStatus()}.
	 */
	@Test
	public void testGetStatus() {
		json.addProperty("status", STATUS);
		Reader reader = new StringReader(json.toString());
		fSubmitInfo = gson.fromJson(reader, SubmitInfo.class);

		assertEquals("Wrong status", STATUS, fSubmitInfo.getStatus());

	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.SubmitInfo#geton_behalf_of()} .
	 */
	@Test
	public void testOn_Behalf_Of() {
		json.addProperty("on_behalf_of", ON_BEHALF_OF);
		Reader reader = new StringReader(json.toString());
		fSubmitInfo = gson.fromJson(reader, SubmitInfo.class);

		assertEquals("Wrong on_behalf_of", ON_BEHALF_OF, fSubmitInfo.getOn_behalf_of());
	}

	// ------------------------------------------------------------------------
	// Test all
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.SubmitInfo}.
	 */
	@Test
	public void testAllFields() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fSubmitInfo = gson.fromJson(reader, SubmitInfo.class);

		assertEquals("Wrong status", STATUS, fSubmitInfo.getStatus());
		assertEquals("Wrong on_behalf_of", ON_BEHALF_OF, fSubmitInfo.getOn_behalf_of());

	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.SubmitInfo}.
	 */
	@Test
	public void testExtraField() {
		setAllFields();
		json.addProperty("extra", "extra_property");
		Reader reader = new StringReader(json.toString());
		fSubmitInfo = gson.fromJson(reader, SubmitInfo.class);

		assertEquals("Wrong status", STATUS, fSubmitInfo.getStatus());
		assertEquals("Wrong on_behalf_of", ON_BEHALF_OF, fSubmitInfo.getOn_behalf_of());

	}

	// ------------------------------------------------------------------------
	// Object
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.SubmitInfo#hashCode()} .
	 */
	@Test
	public void testHashCode() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fSubmitInfo = gson.fromJson(reader, SubmitInfo.class);

		assertEquals("Wrong hash code", HASH_CODE, fSubmitInfo.hashCode());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.SubmitInfo#equals(java.lang.Object)}.
	 */
	// @Test
	public void testEqualsObject() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fSubmitInfo = gson.fromJson(reader, SubmitInfo.class);

		assertTrue("Wrong hash code", fSubmitInfo.equals(fSubmitInfo));
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.SubmitInfo#toString()} .
	 */
	@Test
	public void testToString() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fSubmitInfo = gson.fromJson(reader, SubmitInfo.class);

		assertEquals("Wrong string", TO_STRING, fSubmitInfo.toString());
	}

}
