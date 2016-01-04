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
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.egerrit.core.command.EMFTypeAdapterFactory;
import org.eclipse.egerrit.internal.model.AccountInfo;
import org.eclipse.egerrit.internal.model.ApprovalInfo;
import org.eclipse.egerrit.internal.model.LabelInfo;
import org.eclipse.egerrit.internal.model.ModelFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Test suite for {@link org.eclipse.egerrit.internal.model.LabelInfo}
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class LabelInfoTest {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	private static final boolean OPTIONAL = true;

	// LABELS
	private static final AccountInfo APPROVED = ModelFactory.eINSTANCE.createAccountInfo();

	private static final AccountInfo REJECTED = ModelFactory.eINSTANCE.createAccountInfo();

	private static final AccountInfo RECOMMENDED = ModelFactory.eINSTANCE.createAccountInfo();

	private static final AccountInfo DISLIKED = ModelFactory.eINSTANCE.createAccountInfo();

	private static final boolean BLOCKING = true;

	private static final String VALUE = "value";

	private static final String DEFAULT_VALUE = "default value";

	// DETAILED_LABELS
	private static final List<ApprovalInfo> ALL = new LinkedList<ApprovalInfo>();

	private static final Map<String, String> VALUES = new LinkedHashMap<String, String>();

	private static final int HASH_CODE = -940023039;

	private static final String TO_STRING = "LabelInfo [" + "optional=" + OPTIONAL + ", approved=" + APPROVED
			+ ", rejected=" + REJECTED + ", recommended=" + RECOMMENDED + ", disliked=" + DISLIKED + ", blocking="
			+ BLOCKING + ", value=" + VALUE + ", default_value=" + DEFAULT_VALUE + ", all=" + ALL + ", values=" + VALUES
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
	private LabelInfo fLabelInfo = null;

	// ------------------------------------------------------------------------
	// Helper methods
	// ------------------------------------------------------------------------

	@Before
	public void setUp() throws Exception {
		gson = new GsonBuilder().registerTypeAdapterFactory(new EMFTypeAdapterFactory()).create();
		json = new JsonObject();
	}

	private void setAllFields() {
		json.addProperty("optional", OPTIONAL);
		JsonObject approved = new JsonObject();
		json.add("approved", approved);
		JsonObject rejected = new JsonObject();
		json.add("rejected", rejected);
		JsonObject recommended = new JsonObject();
		json.add("recommended", recommended);
		JsonObject disliked = new JsonObject();
		json.add("disliked", disliked);
		json.addProperty("blocking", BLOCKING);
		json.addProperty("value", VALUE);
		json.addProperty("default_value", DEFAULT_VALUE);
		JsonArray all = new JsonArray();
		json.add("all", all);
		JsonObject values = new JsonObject();
		json.add("values", values);
	}

	@After
	public void tearDown() throws Exception {
	}

	// ------------------------------------------------------------------------
	// Test cases
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.LabelInfo#isOptional()}.
	 */
	@Test
	public void testIsOptional() {
		json.addProperty("optional", OPTIONAL);
		Reader reader = new StringReader(json.toString());
		fLabelInfo = gson.fromJson(reader, LabelInfo.class);

		assertTrue("Wrong optional", fLabelInfo.isOptional());
		assertNull("Wrong approved", fLabelInfo.getApproved());
		assertNull("Wrong rejected", fLabelInfo.getRejected());
		assertNull("Wrong recommended", fLabelInfo.getRecommended());
		assertNull("Wrong disliked", fLabelInfo.getDisliked());
		assertFalse("Wrong blocking", fLabelInfo.isBlocking());
		assertNull("Wrong value", fLabelInfo.getValue());
		assertNull("Wrong default_value", fLabelInfo.getDefault_value());
		assertNull("Wrong all", fLabelInfo.getAll());
		assertNull("Wrong values", fLabelInfo.getValues());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.LabelInfo#getApproved()}.
	 */
	@Test
	public void testGetApproved() {
		JsonObject approved = new JsonObject();
		json.add("approved", approved);
		Reader reader = new StringReader(json.toString());
		fLabelInfo = gson.fromJson(reader, LabelInfo.class);

		assertFalse("Wrong optional", fLabelInfo.isOptional());
		assertEquals("Wrong approved", APPROVED, fLabelInfo.getApproved());
		assertNull("Wrong rejected", fLabelInfo.getRejected());
		assertNull("Wrong recommended", fLabelInfo.getRecommended());
		assertNull("Wrong disliked", fLabelInfo.getDisliked());
		assertFalse("Wrong blocking", fLabelInfo.isBlocking());
		assertNull("Wrong value", fLabelInfo.getValue());
		assertNull("Wrong default_value", fLabelInfo.getDefault_value());
		assertNull("Wrong all", fLabelInfo.getAll());
		assertNull("Wrong values", fLabelInfo.getValues());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.LabelInfo#getRejected()}.
	 */
	@Test
	public void testGetRejected() {
		JsonObject rejected = new JsonObject();
		json.add("rejected", rejected);
		Reader reader = new StringReader(json.toString());
		fLabelInfo = gson.fromJson(reader, LabelInfo.class);

		assertFalse("Wrong optional", fLabelInfo.isOptional());
		assertNull("Wrong approved", fLabelInfo.getApproved());
		assertEquals("Wrong rejected", REJECTED, fLabelInfo.getRejected());
		assertNull("Wrong recommended", fLabelInfo.getRecommended());
		assertNull("Wrong disliked", fLabelInfo.getDisliked());
		assertFalse("Wrong blocking", fLabelInfo.isBlocking());
		assertNull("Wrong value", fLabelInfo.getValue());
		assertNull("Wrong default_value", fLabelInfo.getDefault_value());
		assertNull("Wrong all", fLabelInfo.getAll());
		assertNull("Wrong values", fLabelInfo.getValues());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.LabelInfo#getRecommended()}.
	 */
	@Test
	public void testGetRecommended() {
		JsonObject recommended = new JsonObject();
		json.add("recommended", recommended);
		Reader reader = new StringReader(json.toString());
		fLabelInfo = gson.fromJson(reader, LabelInfo.class);

		assertFalse("Wrong optional", fLabelInfo.isOptional());
		assertNull("Wrong approved", fLabelInfo.getApproved());
		assertNull("Wrong rejected", fLabelInfo.getRejected());
		assertEquals("Wrong recommended", RECOMMENDED, fLabelInfo.getRecommended());
		assertNull("Wrong disliked", fLabelInfo.getDisliked());
		assertFalse("Wrong blocking", fLabelInfo.isBlocking());
		assertNull("Wrong value", fLabelInfo.getValue());
		assertNull("Wrong default_value", fLabelInfo.getDefault_value());
		assertNull("Wrong all", fLabelInfo.getAll());
		assertNull("Wrong values", fLabelInfo.getValues());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.LabelInfo#getDisliked()}.
	 */
	@Test
	public void testGetDisliked() {
		JsonObject disliked = new JsonObject();
		json.add("disliked", disliked);
		Reader reader = new StringReader(json.toString());
		fLabelInfo = gson.fromJson(reader, LabelInfo.class);

		assertFalse("Wrong optional", fLabelInfo.isOptional());
		assertNull("Wrong approved", fLabelInfo.getApproved());
		assertNull("Wrong rejected", fLabelInfo.getRejected());
		assertNull("Wrong recommended", fLabelInfo.getRecommended());
		assertEquals("Wrong disliked", DISLIKED, fLabelInfo.getDisliked());
		assertFalse("Wrong blocking", fLabelInfo.isBlocking());
		assertNull("Wrong value", fLabelInfo.getValue());
		assertNull("Wrong default_value", fLabelInfo.getDefault_value());
		assertNull("Wrong all", fLabelInfo.getAll());
		assertNull("Wrong values", fLabelInfo.getValues());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.LabelInfo#isBlocking()}.
	 */
	@Test
	public void testIsBlocking() {
		json.addProperty("blocking", BLOCKING);
		Reader reader = new StringReader(json.toString());
		fLabelInfo = gson.fromJson(reader, LabelInfo.class);

		assertFalse("Wrong optional", fLabelInfo.isOptional());
		assertNull("Wrong approved", fLabelInfo.getApproved());
		assertNull("Wrong rejected", fLabelInfo.getRejected());
		assertNull("Wrong recommended", fLabelInfo.getRecommended());
		assertNull("Wrong disliked", fLabelInfo.getDisliked());
		assertTrue("Wrong blocking", fLabelInfo.isBlocking());
		assertNull("Wrong value", fLabelInfo.getValue());
		assertNull("Wrong default_value", fLabelInfo.getDefault_value());
		assertNull("Wrong all", fLabelInfo.getAll());
		assertNull("Wrong values", fLabelInfo.getValues());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.LabelInfo#getValue()}.
	 */
	@Test
	public void testGetValue() {
		json.addProperty("value", VALUE);
		Reader reader = new StringReader(json.toString());
		fLabelInfo = gson.fromJson(reader, LabelInfo.class);

		assertFalse("Wrong optional", fLabelInfo.isOptional());
		assertNull("Wrong approved", fLabelInfo.getApproved());
		assertNull("Wrong rejected", fLabelInfo.getRejected());
		assertNull("Wrong recommended", fLabelInfo.getRecommended());
		assertNull("Wrong disliked", fLabelInfo.getDisliked());
		assertFalse("Wrong blocking", fLabelInfo.isBlocking());
		assertEquals("Wrong value", VALUE, fLabelInfo.getValue());
		assertNull("Wrong default_value", fLabelInfo.getDefault_value());
		assertNull("Wrong all", fLabelInfo.getAll());
		assertNull("Wrong values", fLabelInfo.getValues());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.LabelInfo#getDefault_value()}.
	 */
	@Test
	public void testgetDefault_value() {
		json.addProperty("default_value", DEFAULT_VALUE);
		Reader reader = new StringReader(json.toString());
		fLabelInfo = gson.fromJson(reader, LabelInfo.class);

		assertFalse("Wrong optional", fLabelInfo.isOptional());
		assertNull("Wrong approved", fLabelInfo.getApproved());
		assertNull("Wrong rejected", fLabelInfo.getRejected());
		assertNull("Wrong recommended", fLabelInfo.getRecommended());
		assertNull("Wrong disliked", fLabelInfo.getDisliked());
		assertFalse("Wrong blocking", fLabelInfo.isBlocking());
		assertNull("Wrong value", fLabelInfo.getValue());
		assertEquals("Wrong default_value", DEFAULT_VALUE, fLabelInfo.getDefault_value());
		assertNull("Wrong all", fLabelInfo.getAll());
		assertNull("Wrong values", fLabelInfo.getValues());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.LabelInfo#getAll()}.
	 */
	@Test
	public void testGetAll() {
		JsonArray all = new JsonArray();
		json.add("all", all);
		Reader reader = new StringReader(json.toString());
		fLabelInfo = gson.fromJson(reader, LabelInfo.class);

		assertFalse("Wrong optional", fLabelInfo.isOptional());
		assertNull("Wrong approved", fLabelInfo.getApproved());
		assertNull("Wrong rejected", fLabelInfo.getRejected());
		assertNull("Wrong recommended", fLabelInfo.getRecommended());
		assertNull("Wrong disliked", fLabelInfo.getDisliked());
		assertFalse("Wrong blocking", fLabelInfo.isBlocking());
		assertNull("Wrong value", fLabelInfo.getValue());
		assertNull("Wrong default_value", fLabelInfo.getDefault_value());
		assertEquals("Wrong all", ALL, fLabelInfo.getAll());
		assertNull("Wrong values", fLabelInfo.getValues());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.LabelInfo#getValues()}.
	 */
	@Test
	public void testGetValues() {
		JsonObject values = new JsonObject();
		json.add("values", values);
		Reader reader = new StringReader(json.toString());
		fLabelInfo = gson.fromJson(reader, LabelInfo.class);

		assertFalse("Wrong optional", fLabelInfo.isOptional());
		assertNull("Wrong approved", fLabelInfo.getApproved());
		assertNull("Wrong rejected", fLabelInfo.getRejected());
		assertNull("Wrong recommended", fLabelInfo.getRecommended());
		assertNull("Wrong disliked", fLabelInfo.getDisliked());
		assertFalse("Wrong blocking", fLabelInfo.isBlocking());
		assertNull("Wrong value", fLabelInfo.getValue());
		assertNull("Wrong default_value", fLabelInfo.getDefault_value());
		assertNull("Wrong all", fLabelInfo.getAll());
		assertEquals("Wrong values", VALUES, fLabelInfo.getValues());
	}

	// ------------------------------------------------------------------------
	// Test all
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.LabelInfo}.
	 */
	@Test
	public void testAllFields() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fLabelInfo = gson.fromJson(reader, LabelInfo.class);

		assertEquals("Wrong optional", OPTIONAL, fLabelInfo.isOptional());
		assertEquals("Wrong approved", APPROVED, fLabelInfo.getApproved());
		assertEquals("Wrong rejected", REJECTED, fLabelInfo.getRejected());
		assertEquals("Wrong recommended", RECOMMENDED, fLabelInfo.getRecommended());
		assertEquals("Wrong disliked", DISLIKED, fLabelInfo.getDisliked());
		assertEquals("Wrong blocking", BLOCKING, fLabelInfo.isBlocking());
		assertEquals("Wrong value", VALUE, fLabelInfo.getValue());
		assertEquals("Wrong default_value", DEFAULT_VALUE, fLabelInfo.getDefault_value());
		assertEquals("Wrong all", ALL, fLabelInfo.getAll());
		assertEquals("Wrong values", VALUES, fLabelInfo.getValues());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.LabelInfo}.
	 */
	@Test
	public void testExtraField() {
		setAllFields();
		json.addProperty("extra", "extra_property");
		Reader reader = new StringReader(json.toString());
		fLabelInfo = gson.fromJson(reader, LabelInfo.class);

		assertEquals("Wrong optional", OPTIONAL, fLabelInfo.isOptional());
		assertEquals("Wrong approved", APPROVED, fLabelInfo.getApproved());
		assertEquals("Wrong rejected", REJECTED, fLabelInfo.getRejected());
		assertEquals("Wrong recommended", RECOMMENDED, fLabelInfo.getRecommended());
		assertEquals("Wrong disliked", DISLIKED, fLabelInfo.getDisliked());
		assertEquals("Wrong blocking", BLOCKING, fLabelInfo.isBlocking());
		assertEquals("Wrong value", VALUE, fLabelInfo.getValue());
		assertEquals("Wrong default_value", DEFAULT_VALUE, fLabelInfo.getDefault_value());
		assertEquals("Wrong all", ALL, fLabelInfo.getAll());
		assertEquals("Wrong values", VALUES, fLabelInfo.getValues());
	}

	// ------------------------------------------------------------------------
	// Object
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.LabelInfo#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fLabelInfo = gson.fromJson(reader, LabelInfo.class);

		assertEquals("Wrong hash code", HASH_CODE, fLabelInfo.hashCode());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.LabelInfo#equals(java.lang.Object)}.
	 */
	// @Test
	public void testEqualsObject() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.LabelInfo#toString()}.
	 */
	@Test
	public void testToString() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fLabelInfo = gson.fromJson(reader, LabelInfo.class);

		assertEquals("Wrong string", TO_STRING, fLabelInfo.toString());
	}

}
