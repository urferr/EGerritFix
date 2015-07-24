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

import org.eclipse.egerrit.core.rest.SubmitInput;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/**
 * Test suite for {@link org.eclipse.egerrit.core.rest.SubmitInput}
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class SubmitInputTest {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	private static final boolean WAIT_FOR_MERGE = true;

	private static final int HASH_CODE = 1262;

	private static final String TO_STRING = "SubmitInput [wait_for_merge=" + WAIT_FOR_MERGE + "]";

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
	private SubmitInput fSubmitInput = null;

	// ------------------------------------------------------------------------
	// Helper methods
	// ------------------------------------------------------------------------

	@Before
	public void setUp() throws Exception {
		gson = new GsonBuilder().create();
		json = new JsonObject();
	}

	private void setAllFields() {

		json.addProperty("wait_for_merge", WAIT_FOR_MERGE);
	}

	@After
	public void tearDown() throws Exception {
	}

	// ------------------------------------------------------------------------
	// Test cases
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.SubmitInput#isWait_for_merge()}.
	 */
	@Test
	public void testIsWait_for_merge() {
		json.addProperty("wait_for_merge", WAIT_FOR_MERGE);
		Reader reader = new StringReader(json.toString());
		fSubmitInput = gson.fromJson(reader, SubmitInput.class);

		assertEquals("Wrong strict_labels", WAIT_FOR_MERGE, fSubmitInput.isWait_for_merge());
	}

	// ------------------------------------------------------------------------
	// Object
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.SubmitInput#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fSubmitInput = gson.fromJson(reader, SubmitInput.class);

		assertEquals("Wrong hash code", HASH_CODE, fSubmitInput.hashCode());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.SubmitInput#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fSubmitInput = gson.fromJson(reader, SubmitInput.class);

		assertTrue("Wrong hash code", fSubmitInput.equals(fSubmitInput));
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.SubmitInput#toString()}.
	 */
	@Test
	public void testToString() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fSubmitInput = gson.fromJson(reader, SubmitInput.class);

		assertEquals("Wrong string", TO_STRING, fSubmitInput.toString());

	}

}
