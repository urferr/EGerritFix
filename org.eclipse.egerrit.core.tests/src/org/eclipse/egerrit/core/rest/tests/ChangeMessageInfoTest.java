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

import org.eclipse.egerrit.core.command.EMFTypeAdapterFactory;
import org.eclipse.egerrit.internal.model.AccountInfo;
import org.eclipse.egerrit.internal.model.ChangeMessageInfo;
import org.eclipse.egerrit.internal.model.ModelFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/**
 * Test suite for {@link org.eclipse.egerrit.internal.model.ChangeMessageInfo}
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class ChangeMessageInfoTest {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	private static final String ID = "10";

	private static final AccountInfo AUTHOR = ModelFactory.eINSTANCE.createAccountInfo();

	private static final String DATE = "gerrit@gerrit.org";

	private static final String MESSAGE = "gerrit";

	private static final int REVISION_NUMBER = 3;

	private static final int HASH_CODE = -122151328;

	private static final String TO_STRING = "ChangeMessageInfo [" + "id=" + ID + ", author=" + AUTHOR + ", date=" + DATE
			+ ", message=" + MESSAGE + ", _revision_number=" + REVISION_NUMBER + "]";

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
	private ChangeMessageInfo fChangeMessageInfo = null;

	// ------------------------------------------------------------------------
	// Helper methods
	// ------------------------------------------------------------------------

	@Before
	public void setUp() throws Exception {
		gson = new GsonBuilder().registerTypeAdapterFactory(new EMFTypeAdapterFactory()).create();
		json = new JsonObject();
	}

	private void setAllFields() {
		json.addProperty("id", ID);
		JsonObject author = new JsonObject();
		json.add("author", author);
		json.addProperty("date", DATE);
		json.addProperty("message", MESSAGE);
		json.addProperty("_revision_number", REVISION_NUMBER);
	}

	@After
	public void tearDown() throws Exception {
	}

	// ------------------------------------------------------------------------
	// Test cases
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.ChangeMessageInfo#getID()}.
	 */
	@Test
	public void testGetID() {
		json.addProperty("id", ID);
		Reader reader = new StringReader(json.toString());
		fChangeMessageInfo = gson.fromJson(reader, ChangeMessageInfo.class);

		assertEquals("Wrong id", ID, fChangeMessageInfo.getId());
		assertNull("Wrong author", fChangeMessageInfo.getAuthor());
		assertNull("Wrong date", fChangeMessageInfo.getDate());
		assertNull("Wrong message", fChangeMessageInfo.getMessage());
		assertNull("Wrong _revision_number", fChangeMessageInfo.get_revision_number());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.ChangeMessageInfo#getAuthor()}.
	 */
	@Test
	public void testGetAuthor() {
		JsonObject author = new JsonObject();
		json.add("author", author);
		Reader reader = new StringReader(json.toString());
		fChangeMessageInfo = gson.fromJson(reader, ChangeMessageInfo.class);

		assertNull("Wrong id", fChangeMessageInfo.getId());
		assertEquals("Wrong author", AUTHOR, fChangeMessageInfo.getAuthor());
		assertNull("Wrong date", fChangeMessageInfo.getDate());
		assertNull("Wrong message", fChangeMessageInfo.getMessage());
		assertNull("Wrong _revision_number", fChangeMessageInfo.get_revision_number());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.ChangeMessageInfo#getDate()}.
	 */
	@Test
	public void testGetDate() {
		json.addProperty("date", DATE);
		Reader reader = new StringReader(json.toString());
		fChangeMessageInfo = gson.fromJson(reader, ChangeMessageInfo.class);

		assertNull("Wrong id", fChangeMessageInfo.getId());
		assertNull("Wrong author", fChangeMessageInfo.getAuthor());
		assertEquals("Wrong date", DATE, fChangeMessageInfo.getDate());
		assertNull("Wrong message", fChangeMessageInfo.getMessage());
		assertNull("Wrong _revision_number", fChangeMessageInfo.get_revision_number());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.ChangeMessageInfo#getMessage()}.
	 */
	@Test
	public void testGetMessage() {
		json.addProperty("message", MESSAGE);
		Reader reader = new StringReader(json.toString());
		fChangeMessageInfo = gson.fromJson(reader, ChangeMessageInfo.class);

		assertNull("Wrong id", fChangeMessageInfo.getId());
		assertNull("Wrong author", fChangeMessageInfo.getAuthor());
		assertNull("Wrong date", fChangeMessageInfo.getDate());
		assertEquals("Wrong message", MESSAGE, fChangeMessageInfo.getMessage());
		assertNull("Wrong _revision_number", fChangeMessageInfo.get_revision_number());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.ChangeMessageInfo#getRevisionNumber()}.
	 */
	@Test
	public void testGetRevisionNumber() {
		json.addProperty("_revision_number", REVISION_NUMBER);
		Reader reader = new StringReader(json.toString());
		fChangeMessageInfo = gson.fromJson(reader, ChangeMessageInfo.class);

		assertNull("Wrong id", fChangeMessageInfo.getId());
		assertNull("Wrong author", fChangeMessageInfo.getAuthor());
		assertNull("Wrong date", fChangeMessageInfo.getDate());
		assertNull("Wrong message", fChangeMessageInfo.getMessage());
		assertEquals("Wrong _revision_number", REVISION_NUMBER, fChangeMessageInfo.get_revision_number());
	}

	// ------------------------------------------------------------------------
	// Test all
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.ChangeMessageInfo}.
	 */
	@Test
	public void testAllFields() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fChangeMessageInfo = gson.fromJson(reader, ChangeMessageInfo.class);

		assertEquals("Wrong id", ID, fChangeMessageInfo.getId());
		assertEquals("Wrong author", AUTHOR, fChangeMessageInfo.getAuthor());
		assertEquals("Wrong date", DATE, fChangeMessageInfo.getDate());
		assertEquals("Wrong message", MESSAGE, fChangeMessageInfo.getMessage());
		assertEquals("Wrong _revision_number", REVISION_NUMBER, fChangeMessageInfo.get_revision_number());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.ChangeMessageInfo}.
	 */
	@Test
	public void testExtraField() {
		setAllFields();
		json.addProperty("extra", "extra_property");
		Reader reader = new StringReader(json.toString());
		fChangeMessageInfo = gson.fromJson(reader, ChangeMessageInfo.class);

		assertEquals("Wrong id", ID, fChangeMessageInfo.getId());
		assertEquals("Wrong author", AUTHOR, fChangeMessageInfo.getAuthor());
		assertEquals("Wrong date", DATE, fChangeMessageInfo.getDate());
		assertEquals("Wrong message", MESSAGE, fChangeMessageInfo.getMessage());
		assertEquals("Wrong _revision_number", REVISION_NUMBER, fChangeMessageInfo.get_revision_number());
	}

	// ------------------------------------------------------------------------
	// Object
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.ChangeMessageInfo#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fChangeMessageInfo = gson.fromJson(reader, ChangeMessageInfo.class);

		assertEquals("Wrong hash code", HASH_CODE, fChangeMessageInfo.hashCode());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.ChangeMessageInfo#equals(java.lang.Object)}.
	 */
	// @Test
	public void testEqualsObject() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.ChangeMessageInfo#toString()}.
	 */
	@Test
	public void testToString() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fChangeMessageInfo = gson.fromJson(reader, ChangeMessageInfo.class);

		assertEquals("Wrong string", TO_STRING, fChangeMessageInfo.toString());
	}

}
