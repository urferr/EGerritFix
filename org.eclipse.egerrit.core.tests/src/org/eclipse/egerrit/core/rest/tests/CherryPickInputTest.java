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

import org.eclipse.egerrit.core.command.EMFTypeAdapterFactory;
import org.eclipse.egerrit.core.rest.CherryPickInput;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/**
 * Test suite for {@link org.eclipse.egerrit.internal.model.BranchInfo}
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class CherryPickInputTest {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	private static final String MESSAGE = "test message";

	private static final String DESTINATION = "HEAD";

	private static final int HASH_CODE = 1608591450;

	private static final String TO_STRING = "CherryPickInput [message=" + MESSAGE + ", destination=" + DESTINATION
			+ "]";

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
	private CherryPickInput fCherryPickInput = null;

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
		json.addProperty("destination", DESTINATION);

	}

	@After
	public void tearDown() throws Exception {
	}

	// ------------------------------------------------------------------------
	// Test cases
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.CherryPickInput#getMessage()}.
	 */
	@Test
	public void testGetMessage() {
		json.addProperty("message", MESSAGE);
		Reader reader = new StringReader(json.toString());
		fCherryPickInput = gson.fromJson(reader, CherryPickInput.class);

		assertEquals("Wrong message", MESSAGE, fCherryPickInput.getMessage());

	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.CherryPickInput#getdestination()} .
	 */
	@Test
	public void testGetRevision() {
		json.addProperty("destination", DESTINATION);
		Reader reader = new StringReader(json.toString());
		fCherryPickInput = gson.fromJson(reader, CherryPickInput.class);

		assertEquals("Wrong destination", DESTINATION, fCherryPickInput.getDestination());
	}

	// ------------------------------------------------------------------------
	// Test all
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.CherryPickInput}.
	 */
	@Test
	public void testAllFields() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fCherryPickInput = gson.fromJson(reader, CherryPickInput.class);

		assertEquals("Wrong message", MESSAGE, fCherryPickInput.getMessage());
		assertEquals("Wrong destination", DESTINATION, fCherryPickInput.getDestination());

	}

	// ------------------------------------------------------------------------
	// Object
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.CherryPickInput#hashCode()} .
	 */
	@Test
	public void testHashCode() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fCherryPickInput = gson.fromJson(reader, CherryPickInput.class);

		assertEquals("Wrong hash code", HASH_CODE, fCherryPickInput.hashCode());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.CherryPickInput#equals(java.lang.Object)}.
	 */
	// @Test
	public void testEqualsObject() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fCherryPickInput = gson.fromJson(reader, CherryPickInput.class);

		assertTrue("Wrong hash code", fCherryPickInput.equals(fCherryPickInput));
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.CherryPickInput#toString()} .
	 */
	@Test
	public void testToString() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fCherryPickInput = gson.fromJson(reader, CherryPickInput.class);

		assertEquals("Wrong string", TO_STRING, fCherryPickInput.toString());
	}

}
