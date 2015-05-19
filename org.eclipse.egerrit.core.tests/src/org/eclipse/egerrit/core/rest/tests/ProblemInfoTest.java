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

import org.eclipse.egerrit.core.rest.ProblemInfo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/**
 * Test suite for {@link org.eclipse.egerrit.core.rest.ProblemInfo}
 * 
 * @since 1.0
 */
@SuppressWarnings("nls")
public class ProblemInfoTest {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	private static final String MESSAGE = "Congratulations!";

	private static final String STATUS = "OK";

	private static final String OUTCOME = "You're the next contestant on TPIR";

	private static final int HASH_CODE = -1272391851;

	private static final String TO_STRING = "ProblemInfo [" + "message=" + MESSAGE + ", status=" + STATUS
			+ ", outcome=" + OUTCOME + "]";

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
	private ProblemInfo fProblemInfo = null;

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
		json.addProperty("status", STATUS);
		json.addProperty("outcome", OUTCOME);
	}

	@After
	public void tearDown() throws Exception {
	}

	// ------------------------------------------------------------------------
	// Test cases
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ProblemInfo#getMessage()}.
	 */
	@Test
	public void testGetMessage() {
		json.addProperty("message", MESSAGE);
		Reader reader = new StringReader(json.toString());
		fProblemInfo = gson.fromJson(reader, ProblemInfo.class);

		assertEquals("Wrong message", MESSAGE, fProblemInfo.getMessage());
		assertNull("Wrong status", fProblemInfo.getStatus());
		assertNull("Wrong outcome", fProblemInfo.getOutcome());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ProblemInfo#getStatus()}.
	 */
	@Test
	public void testGetStatus() {
		json.addProperty("status", STATUS);
		Reader reader = new StringReader(json.toString());
		fProblemInfo = gson.fromJson(reader, ProblemInfo.class);

		assertNull("Wrong message", fProblemInfo.getMessage());
		assertEquals("Wrong status", STATUS, fProblemInfo.getStatus());
		assertNull("Wrong outcome", fProblemInfo.getOutcome());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ProblemInfo#getOutcome()}.
	 */
	@Test
	public void testGetOutcome() {
		json.addProperty("outcome", OUTCOME);
		Reader reader = new StringReader(json.toString());
		fProblemInfo = gson.fromJson(reader, ProblemInfo.class);

		assertNull("Wrong message", fProblemInfo.getMessage());
		assertNull("Wrong status", fProblemInfo.getStatus());
		assertEquals("Wrong outcome", OUTCOME, fProblemInfo.getOutcome());
	}

	// ------------------------------------------------------------------------
	// Test all
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.FileInfo}.
	 */
	@Test
	public void testAllFields() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fProblemInfo = gson.fromJson(reader, ProblemInfo.class);

		assertEquals("Wrong message", MESSAGE, fProblemInfo.getMessage());
		assertEquals("Wrong status", STATUS, fProblemInfo.getStatus());
		assertEquals("Wrong outcome", OUTCOME, fProblemInfo.getOutcome());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.FileInfo}.
	 */
	@Test
	public void testExtraField() {
		setAllFields();
		json.addProperty("extra", "extra_property");
		Reader reader = new StringReader(json.toString());
		fProblemInfo = gson.fromJson(reader, ProblemInfo.class);

		assertEquals("Wrong message", MESSAGE, fProblemInfo.getMessage());
		assertEquals("Wrong status", STATUS, fProblemInfo.getStatus());
		assertEquals("Wrong outcome", OUTCOME, fProblemInfo.getOutcome());
	}

	// ------------------------------------------------------------------------
	// Object
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ProblemInfo#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fProblemInfo = gson.fromJson(reader, ProblemInfo.class);

		assertEquals("Wrong hash code", HASH_CODE, fProblemInfo.hashCode());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ProblemInfo#equals(java.lang.Object)}.
	 */
//  @Test
	public void testEqualsObject() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.ProblemInfo#toString()}.
	 */
	@Test
	public void testToString() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fProblemInfo = gson.fromJson(reader, ProblemInfo.class);

		assertEquals("Wrong string", TO_STRING, fProblemInfo.toString());
	}

}
