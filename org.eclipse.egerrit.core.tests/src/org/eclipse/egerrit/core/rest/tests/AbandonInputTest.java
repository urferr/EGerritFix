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
import org.eclipse.egerrit.core.rest.AbandonInput;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/**
 * Test suite for {@link org.eclipse.egerrit.core.rest.AbandonInput}
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class AbandonInputTest {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	private static final String MESSAGE = "This is a test message";

	private static final int HASH_CODE = -1610581813;

	private static final String TO_STRING = "AbandonInput [message=" + MESSAGE + "]";

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
	private AbandonInput fAbandonInput = null;

	// ------------------------------------------------------------------------
	// Helper methods
	// ------------------------------------------------------------------------

	@Before
	public void setUp() throws Exception {
		gson = new GsonBuilder().registerTypeAdapterFactory(new EMFTypeAdapterFactory()).create();
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
	 * Test method for {@link org.eclipse.egerrit.core.rest.AbandonInput#testgetMessage()}.
	 */
	@Test
	public void testGetMessage() {
		json.addProperty("message", MESSAGE);
		Reader reader = new StringReader(json.toString());
		fAbandonInput = gson.fromJson(reader, AbandonInput.class);

		assertEquals("Wrong message", MESSAGE, fAbandonInput.getMessage());
	}

	// ------------------------------------------------------------------------
	// Object
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.AbandonInput#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fAbandonInput = gson.fromJson(reader, AbandonInput.class);

		assertEquals("Wrong hash code", HASH_CODE, fAbandonInput.hashCode());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.AbandonInput#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fAbandonInput = gson.fromJson(reader, AbandonInput.class);

		assertTrue("Wrong hash code", fAbandonInput.equals(fAbandonInput));
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.AbandonInput#toString()}.
	 */
	@Test
	public void testToString() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fAbandonInput = gson.fromJson(reader, AbandonInput.class);

		assertEquals("Wrong string", TO_STRING, fAbandonInput.toString());

	}

}
