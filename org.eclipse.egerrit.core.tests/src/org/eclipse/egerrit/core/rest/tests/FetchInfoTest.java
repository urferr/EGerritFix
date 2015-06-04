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
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.egerrit.core.rest.FetchInfo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/**
 * Test suite for {@link org.eclipse.egerrit.core.rest.FetchInfo}
 * 
 * @since 1.0
 */
@SuppressWarnings("nls")
public class FetchInfoTest {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	private static final String URL = "https://git.eclispe.org/gitroot/org.eclipse.egerrit";

	private static final String REF = "/refs/changes/10/12345/1";

	private static final String[] KEYS = new String[] { "checkout", "cherry pick" };

	private static final Map<String, String> COMMANDS;

	static {
		COMMANDS = new LinkedHashMap<String, String>();
		COMMANDS.put(KEYS[0],
				"git fetch https://git.eclispe.org/gitroot/org.eclipse.egerrit refs/changes/10/12345/1 && git checkout FETCH_HEAD");
		COMMANDS.put(KEYS[1],
				"git fetch git://git.eclipse.org/gitroot/platform/eclipse.platform.ui refs/changes/10/12345/1 && git cherry-pick FETCH_HEAD");
	}

	private static final int HASH_CODE = 1072344156;

	private static final String TO_STRING = "FetchInfo [" + "url=" + URL + ", ref=" + REF + ", commands=" + COMMANDS
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
	private FetchInfo fFetchInfo = null;

	// ------------------------------------------------------------------------
	// Helper methods
	// ------------------------------------------------------------------------

	@Before
	public void setUp() throws Exception {
		gson = new GsonBuilder().create();
		json = new JsonObject();
	}

	private void setAllFields() {
		json.addProperty("url", URL);
		json.addProperty("ref", REF);
		JsonObject commands = new JsonObject();
		for (String key : KEYS) {
			commands.addProperty(key, COMMANDS.get(key));
		}
		json.add("commands", commands);
	}

	@After
	public void tearDown() throws Exception {
	}

	// ------------------------------------------------------------------------
	// Test cases
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.FetchInfo#getURL()}.
	 */
	@Test
	public void testGetURL() {
		json.addProperty("url", URL);
		Reader reader = new StringReader(json.toString());
		fFetchInfo = gson.fromJson(reader, FetchInfo.class);

		assertEquals("Wrong url", URL, fFetchInfo.getURL());
		assertNull("Wrong ref", fFetchInfo.getRef());
		assertNull("Wrong commands", fFetchInfo.getCommands());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.FetchInfo#getRef()}.
	 */
	@Test
	public void testGetRef() {
		json.addProperty("ref", REF);
		Reader reader = new StringReader(json.toString());
		fFetchInfo = gson.fromJson(reader, FetchInfo.class);

		assertNull("Wrong url", fFetchInfo.getURL());
		assertEquals("Wrong ref", REF, fFetchInfo.getRef());
		assertNull("Wrong commands", fFetchInfo.getCommands());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.FetchInfo#getCommands()}.
	 */
	@Test
	public void testGetCommands() {
		JsonObject commands = new JsonObject();
		for (String key : KEYS) {
			commands.addProperty(key, COMMANDS.get(key));
		}
		json.add("commands", commands);
		Reader reader = new StringReader(json.toString());
		fFetchInfo = gson.fromJson(reader, FetchInfo.class);

		assertNull("Wrong url", fFetchInfo.getURL());
		assertNull("Wrong ref", fFetchInfo.getRef());
		assertEquals("Wrong commands", COMMANDS, fFetchInfo.getCommands());
	}

	// ------------------------------------------------------------------------
	// Test all
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.FetchInfo}.
	 */
	@Test
	public void testAllFields() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fFetchInfo = gson.fromJson(reader, FetchInfo.class);

		assertEquals("Wrong url", URL, fFetchInfo.getURL());
		assertEquals("Wrong ref", REF, fFetchInfo.getRef());
		assertEquals("Wrong commands", COMMANDS, fFetchInfo.getCommands());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.FetchInfo}.
	 */
	@Test
	public void testExtraField() {
		setAllFields();
		json.addProperty("extra", "extra_property");
		Reader reader = new StringReader(json.toString());
		fFetchInfo = gson.fromJson(reader, FetchInfo.class);

		assertEquals("Wrong url", URL, fFetchInfo.getURL());
		assertEquals("Wrong ref", REF, fFetchInfo.getRef());
		assertEquals("Wrong commands", COMMANDS, fFetchInfo.getCommands());
	}

	// ------------------------------------------------------------------------
	// Object
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.FetchInfo#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fFetchInfo = gson.fromJson(reader, FetchInfo.class);

		assertEquals("Wrong hash code", HASH_CODE, fFetchInfo.hashCode());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.FetchInfo#equals(java.lang.Object)}.
	 */
	// @Test
	public void testEqualsObject() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.FetchInfo#toString()}.
	 */
	@Test
	public void testToString() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fFetchInfo = gson.fromJson(reader, FetchInfo.class);

		assertEquals("Wrong hash code", TO_STRING, fFetchInfo.toString());
	}

}
