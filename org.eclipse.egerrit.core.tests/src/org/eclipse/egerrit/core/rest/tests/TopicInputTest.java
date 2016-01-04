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
import org.eclipse.egerrit.core.rest.TopicInput;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/**
 * Test suite for {@link org.eclipse.egerrit.core.rest.TopicInput}
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class TopicInputTest {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	private static final String TOPIC = "This is a test topic";

	private static final int HASH_CODE = 1435249459;

	private static final String TO_STRING = "TopicInput [topic=" + TOPIC + "]";

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
	private TopicInput fTopicInput = null;

	// ------------------------------------------------------------------------
	// Helper methods
	// ------------------------------------------------------------------------

	@Before
	public void setUp() throws Exception {
		gson = new GsonBuilder().registerTypeAdapterFactory(new EMFTypeAdapterFactory()).create();
		json = new JsonObject();
	}

	private void setAllFields() {

		json.addProperty("topic", TOPIC);
	}

	@After
	public void tearDown() throws Exception {
	}

	// ------------------------------------------------------------------------
	// Test cases
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.TopicInput#testgetTopic()}.
	 */
	@Test
	public void testGetTopic() {
		json.addProperty("topic", TOPIC);
		Reader reader = new StringReader(json.toString());
		fTopicInput = gson.fromJson(reader, TopicInput.class);

		assertEquals("Wrong topic", TOPIC, fTopicInput.getTopic());
	}

	// ------------------------------------------------------------------------
	// Object
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.TopicInput#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fTopicInput = gson.fromJson(reader, TopicInput.class);
		System.out.println(fTopicInput.hashCode());
		assertEquals("Wrong hash code", HASH_CODE, fTopicInput.hashCode());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.TopicInput#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fTopicInput = gson.fromJson(reader, TopicInput.class);

		assertTrue("Wrong hash code", fTopicInput.equals(fTopicInput));
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.core.rest.TopicInput#toString()}.
	 */
	@Test
	public void testToString() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fTopicInput = gson.fromJson(reader, TopicInput.class);

		assertEquals("Wrong string", TO_STRING, fTopicInput.toString());

	}

}
