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

import org.eclipse.egerrit.core.rest.ChangeEditMessageInput;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/**
 * Test suite for {@link org.eclipse.egerrit.core.rest.ChangeEditMessageInput}
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class ChangeEditorMessageInputTest {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	private static final String MESSAGE = "This is a test message";

	private static final int HASH_CODE = -1610581813;

	private static final String TO_STRING = "ChangeEditMessageInput [message=" + MESSAGE + "]";

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
	private ChangeEditMessageInput fChangeEditMessageInput = null;

	// ------------------------------------------------------------------------
	// Helper methods
	// ------------------------------------------------------------------------

	@Before
	public void setUp() throws Exception {
		gson = new GsonBuilder().create();
		json = new JsonObject();
	}

	private void setAllFields() {

		json.addProperty("message", MESSAGE);
	}

	@After
	public void tearDown() throws Exception {
	}

	// ------------------------------------------------------------------------
	// Test cases
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ChangeEditMessageInput#testgetMessage()}.
	 */
	@Test
	public void testGetMessage() {
		json.addProperty("message", MESSAGE);
		Reader reader = new StringReader(json.toString());
		fChangeEditMessageInput = gson.fromJson(reader, ChangeEditMessageInput.class);

		assertEquals("Wrong message", MESSAGE, fChangeEditMessageInput.getMessage());
	}

	// ------------------------------------------------------------------------
	// Object
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.RestoreInput#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fChangeEditMessageInput = gson.fromJson(reader, ChangeEditMessageInput.class);

		assertEquals("Wrong hash code", HASH_CODE, fChangeEditMessageInput.hashCode());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ChangeEditMessageInput#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fChangeEditMessageInput = gson.fromJson(reader, ChangeEditMessageInput.class);

		assertTrue("Wrong hash code", fChangeEditMessageInput.equals(fChangeEditMessageInput));
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ChangeEditMessageInput#toString()}.
	 */
	@Test
	public void testToString() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fChangeEditMessageInput = gson.fromJson(reader, ChangeEditMessageInput.class);

		assertEquals("Wrong string", TO_STRING, fChangeEditMessageInput.toString());

	}

}
