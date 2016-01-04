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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.Reader;
import java.io.StringReader;

import org.eclipse.egerrit.core.command.EMFTypeAdapterFactory;
import org.eclipse.egerrit.internal.model.ActionInfo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/**
 * Test suite for {@link org.eclipse.egerrit.internal.model.ActionInfo}
 * 
 * @since 1.0
 */
@SuppressWarnings("nls")
public class ActionInfoTest {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	private static final String METHOD = "Some method";

	private static final String LABEL = "Some label";

	private static final String TITLE = "Some title";

	private static final String ENABLED = "true";

	private static final int HASH_CODE = 606563257;

	private static final String TO_STRING = "ActionInfo [" + "method=" + METHOD + ", label=" + LABEL + ", title="
			+ TITLE + ", enabled=" + ENABLED + "]";

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
	private ActionInfo fActionInfo = null;

	// ------------------------------------------------------------------------
	// Helper methods
	// ------------------------------------------------------------------------

	@Before
	public void setUp() throws Exception {
		gson = new GsonBuilder().registerTypeAdapterFactory(new EMFTypeAdapterFactory()).create();
		json = new JsonObject();
	}

	private void setAllFields() {
		json.addProperty("method", METHOD);
		json.addProperty("label", LABEL);
		json.addProperty("title", TITLE);
		json.addProperty("enabled", ENABLED);
	}

	@After
	public void tearDown() throws Exception {
	}

	// ------------------------------------------------------------------------
	// Test cases
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.ActionInfo#getMethod()}.
	 */
	@Test
	public void testGetMethod() {
		json.addProperty("method", METHOD);
		Reader reader = new StringReader(json.toString());
		fActionInfo = gson.fromJson(reader, ActionInfo.class);

		assertEquals("Wrong method", METHOD, fActionInfo.getMethod());
		assertNull("Wrong label", fActionInfo.getLabel());
		assertNull("Wrong old_path", fActionInfo.getTitle());
		assertFalse("Wrong enabled", fActionInfo.isEnabled());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.ActionInfo#getLabel()}.
	 */
	@Test
	public void testGetLabel() {
		json.addProperty("label", LABEL);
		Reader reader = new StringReader(json.toString());
		fActionInfo = gson.fromJson(reader, ActionInfo.class);

		assertNull("Wrong method", fActionInfo.getMethod());
		assertEquals("Wrong label", LABEL, fActionInfo.getLabel());
		assertNull("Wrong old_path", fActionInfo.getTitle());
		assertFalse("Wrong enabled", fActionInfo.isEnabled());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.ActionInfo#getTitle()}.
	 */
	@Test
	public void testGetTitle() {
		json.addProperty("title", TITLE);
		Reader reader = new StringReader(json.toString());
		fActionInfo = gson.fromJson(reader, ActionInfo.class);

		assertNull("Wrong method", fActionInfo.getMethod());
		assertNull("Wrong label", fActionInfo.getLabel());
		assertEquals("Wrong old_path", TITLE, fActionInfo.getTitle());
		assertFalse("Wrong enabled", fActionInfo.isEnabled());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.ActionInfo#isEnabled()}.
	 */
	@Test
	public void testIsEnabled() {
		json.addProperty("enabled", ENABLED);
		Reader reader = new StringReader(json.toString());
		fActionInfo = gson.fromJson(reader, ActionInfo.class);

		assertNull("Wrong method", fActionInfo.getMethod());
		assertNull("Wrong label", fActionInfo.getLabel());
		assertNull("Wrong old_path", fActionInfo.getTitle());
		assertTrue("Wrong enabled", fActionInfo.isEnabled());
	}

	// ------------------------------------------------------------------------
	// Test all
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.FileInfo}.
	 */
	@Test
	public void testAllFields() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fActionInfo = gson.fromJson(reader, ActionInfo.class);

		assertEquals("Wrong method", METHOD, fActionInfo.getMethod());
		assertEquals("Wrong label", LABEL, fActionInfo.getLabel());
		assertEquals("Wrong title", TITLE, fActionInfo.getTitle());
		assertTrue("Wrong enabled", fActionInfo.isEnabled());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.FileInfo}.
	 */
	@Test
	public void testExtraField() {
		setAllFields();
		json.addProperty("extra", "extra_property");
		Reader reader = new StringReader(json.toString());
		fActionInfo = gson.fromJson(reader, ActionInfo.class);

		assertEquals("Wrong method", METHOD, fActionInfo.getMethod());
		assertEquals("Wrong label", LABEL, fActionInfo.getLabel());
		assertEquals("Wrong title", TITLE, fActionInfo.getTitle());
		assertTrue("Wrong enabled", fActionInfo.isEnabled());
	}

	// ------------------------------------------------------------------------
	// Object
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.ActionInfo#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fActionInfo = gson.fromJson(reader, ActionInfo.class);

		assertEquals("Wrong hash code", HASH_CODE, fActionInfo.hashCode());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.ActionInfo#equals(java.lang.Object)}.
	 */
//    @Test
	public void testEqualsObject() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.ActionInfo#toString()}.
	 */
	@Test
	public void testToString() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fActionInfo = gson.fromJson(reader, ActionInfo.class);

		assertEquals("Wrong string", TO_STRING, fActionInfo.toString());
	}

}
