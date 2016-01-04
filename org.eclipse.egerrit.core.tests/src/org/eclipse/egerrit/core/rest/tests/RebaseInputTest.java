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
import org.eclipse.egerrit.core.rest.RebaseInput;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/**
 * Test suite for {@link org.eclipse.egerrit.core.rest.RebaseInput}
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class RebaseInputTest {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	private static final String BASE = "This is a test base";

	private static final int HASH_CODE = 1708316779;

	private static final String TO_STRING = "RebaseInput [base=" + BASE + "]";

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
	private RebaseInput fRebaseInput = null;

	// ------------------------------------------------------------------------
	// Helper methods
	// ------------------------------------------------------------------------

	@Before
	public void setUp() throws Exception {
		gson = new GsonBuilder().registerTypeAdapterFactory(new EMFTypeAdapterFactory()).create();
		json = new JsonObject();
	}

	private void setAllFields() {

		json.addProperty("base", BASE);
	}

	@After
	public void tearDown() throws Exception {
	}

	// ------------------------------------------------------------------------
	// Test cases
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.RebaseInput#testgetBase()}.
	 */
	@Test
	public void testGetBase() {
		json.addProperty("base", BASE);
		Reader reader = new StringReader(json.toString());
		fRebaseInput = gson.fromJson(reader, RebaseInput.class);

		assertEquals("Wrong base", BASE, fRebaseInput.getBase());
	}

	// ------------------------------------------------------------------------
	// Object
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.RebaseInput#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fRebaseInput = gson.fromJson(reader, RebaseInput.class);

		assertEquals("Wrong hash code", HASH_CODE, fRebaseInput.hashCode());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.RebaseInput#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fRebaseInput = gson.fromJson(reader, RebaseInput.class);

		assertTrue("Wrong hash code", fRebaseInput.equals(fRebaseInput));
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.RebaseInput#toString()}.
	 */
	@Test
	public void testToString() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fRebaseInput = gson.fromJson(reader, RebaseInput.class);

		assertEquals("Wrong string", TO_STRING, fRebaseInput.toString());

	}

}
