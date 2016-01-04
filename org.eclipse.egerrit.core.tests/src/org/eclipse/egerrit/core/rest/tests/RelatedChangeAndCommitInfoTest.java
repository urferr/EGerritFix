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
import org.eclipse.egerrit.internal.model.CommitInfo;
import org.eclipse.egerrit.internal.model.ModelFactory;
import org.eclipse.egerrit.internal.model.RelatedChangeAndCommitInfo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/**
 * Test suite for {@link org.eclipse.egerrit.internal.model.RelatedChangeAndCommitInfo}
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class RelatedChangeAndCommitInfoTest {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	// LABELS
	private static final String CHANGE_ID = "change_id";

	private static final CommitInfo COMMIT = ModelFactory.eINSTANCE.createCommitInfo();

	private static final String _CHANGE_NUMBER = "_change_number";

	private static final String _REVISION_NUMBER = "_revision_number";

	private static final String _CURRENT_REVISION_NUMBER = "_current_revision_number";

	// DETAILED_LABELS

	private static final int HASH_CODE = 619378875;

	private static final String TO_STRING = "RelatedChangeAndCommitInfo [" + "change_id=" + CHANGE_ID + ", commit="
			+ COMMIT + ", _change_number=" + _CHANGE_NUMBER + ", _revision_number=" + _REVISION_NUMBER
			+ ", _current_revision_number=" + _CURRENT_REVISION_NUMBER + "]";

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
	private RelatedChangeAndCommitInfo fRelatedChangeAndCommitInfo = null;

	// ------------------------------------------------------------------------
	// Helper methods
	// ------------------------------------------------------------------------

	@Before
	public void setUp() throws Exception {
		gson = new GsonBuilder().registerTypeAdapterFactory(new EMFTypeAdapterFactory()).create();
		json = new JsonObject();
	}

	private void setAllFields() {
		json.addProperty("change_id", CHANGE_ID);
		JsonObject commit = new JsonObject();
		json.add("commit", commit);
		json.addProperty("_change_number", _CHANGE_NUMBER);
		json.addProperty("_revision_number", _REVISION_NUMBER);
		json.addProperty("_current_revision_number", _CURRENT_REVISION_NUMBER);
	}

	@After
	public void tearDown() throws Exception {
	}

	// ------------------------------------------------------------------------
	// Test cases
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.RelatedChangeAndCommitInfo#getChange_id()} .
	 */
	@Test
	public void testGetChangeId() {
		json.addProperty("change_id", CHANGE_ID);
		Reader reader = new StringReader(json.toString());
		fRelatedChangeAndCommitInfo = gson.fromJson(reader, RelatedChangeAndCommitInfo.class);

		assertEquals("Wrong change_id", CHANGE_ID, fRelatedChangeAndCommitInfo.getChange_id());
		assertNull("Wrong commit", fRelatedChangeAndCommitInfo.getCommit());
		assertNull("Wrong _change_number", fRelatedChangeAndCommitInfo.get_change_number());
		assertNull("Wrong _revision_number", fRelatedChangeAndCommitInfo.get_revision_number());
		assertNull("Wrong _current_revision_number", fRelatedChangeAndCommitInfo.get_current_revision_number());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.RelatedChangeAndCommitInfo#getCommit()} .
	 */
	@Test
	public void testGetCommit() {
		JsonObject commit = new JsonObject();
		json.add("commit", commit);
		Reader reader = new StringReader(json.toString());
		fRelatedChangeAndCommitInfo = gson.fromJson(reader, RelatedChangeAndCommitInfo.class);

		assertNull("Wrong change_id", fRelatedChangeAndCommitInfo.getChange_id());
		assertEquals("Wrong commit", COMMIT, fRelatedChangeAndCommitInfo.getCommit());
		assertNull("Wrong _change_number", fRelatedChangeAndCommitInfo.get_change_number());
		assertNull("Wrong _revision_number", fRelatedChangeAndCommitInfo.get_revision_number());
		assertNull("Wrong _current_revision_number", fRelatedChangeAndCommitInfo.get_current_revision_number());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.RelatedChangeAndCommitInfo#get_change_number()} .
	 */
	@Test
	public void testGet_Change_Number() {
		json.addProperty("_change_number", _CHANGE_NUMBER);
		Reader reader = new StringReader(json.toString());
		fRelatedChangeAndCommitInfo = gson.fromJson(reader, RelatedChangeAndCommitInfo.class);

		assertNull("Wrong change_id", fRelatedChangeAndCommitInfo.getChange_id());
		assertNull("Wrong commit", fRelatedChangeAndCommitInfo.getCommit());
		assertEquals("Wrong _change_number", _CHANGE_NUMBER, fRelatedChangeAndCommitInfo.get_change_number());
		assertNull("Wrong _revision_number", fRelatedChangeAndCommitInfo.get_revision_number());
		assertNull("Wrong _current_revision_number", fRelatedChangeAndCommitInfo.get_current_revision_number());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.RelatedChangeAndCommitInfo#get_revision_number()} .
	 */
	@Test
	public void testGet_Revision_Number() {
		json.addProperty("_revision_number", _REVISION_NUMBER);
		Reader reader = new StringReader(json.toString());
		fRelatedChangeAndCommitInfo = gson.fromJson(reader, RelatedChangeAndCommitInfo.class);

		assertNull("Wrong change_id", fRelatedChangeAndCommitInfo.getChange_id());
		assertNull("Wrong commit", fRelatedChangeAndCommitInfo.getCommit());
		assertNull("Wrong _change_number", fRelatedChangeAndCommitInfo.get_change_number());
		assertEquals("Wrong _revision_number", _REVISION_NUMBER, fRelatedChangeAndCommitInfo.get_revision_number());
		assertNull("Wrong _current_revision_number", fRelatedChangeAndCommitInfo.get_current_revision_number());
	}

	/**
	 * Test method for
	 * {@link org.eclipse.egerrit.internal.model.RelatedChangeAndCommitInfo#get_current_revision_number()} .
	 */
	@Test
	public void testGet_Current_Revision_Number() {
		json.addProperty("_current_revision_number", _CURRENT_REVISION_NUMBER);
		Reader reader = new StringReader(json.toString());
		fRelatedChangeAndCommitInfo = gson.fromJson(reader, RelatedChangeAndCommitInfo.class);

		assertNull("Wrong change_id", fRelatedChangeAndCommitInfo.getChange_id());
		assertNull("Wrong commit", fRelatedChangeAndCommitInfo.getCommit());
		assertNull("Wrong _change_number", fRelatedChangeAndCommitInfo.get_change_number());
		assertNull("Wrong _revision_number", fRelatedChangeAndCommitInfo.get_revision_number());
		assertEquals("Wrong _current_revision_number", _CURRENT_REVISION_NUMBER,
				fRelatedChangeAndCommitInfo.get_current_revision_number());
	}

	// ------------------------------------------------------------------------
	// Test all
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.RelatedChangeAndCommitInfo}.
	 */
	@Test
	public void testAllFields() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fRelatedChangeAndCommitInfo = gson.fromJson(reader, RelatedChangeAndCommitInfo.class);

		assertEquals("Wrong change_id", CHANGE_ID, fRelatedChangeAndCommitInfo.getChange_id());
		assertEquals("Wrong _change_number", _CHANGE_NUMBER, fRelatedChangeAndCommitInfo.get_change_number());

		assertEquals("Wrong _revision_number", _REVISION_NUMBER, fRelatedChangeAndCommitInfo.get_revision_number());
		assertEquals("Wrong _current_revision_number", _CURRENT_REVISION_NUMBER,
				fRelatedChangeAndCommitInfo.get_current_revision_number());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.RelatedChangeAndCommitInfo}.
	 */
	@Test
	public void testExtraField() {
		setAllFields();
		json.addProperty("extra", "extra_property");
		Reader reader = new StringReader(json.toString());
		fRelatedChangeAndCommitInfo = gson.fromJson(reader, RelatedChangeAndCommitInfo.class);

		assertEquals("Wrong change_id", CHANGE_ID, fRelatedChangeAndCommitInfo.getChange_id());
		assertEquals("Wrong _change_number", _CHANGE_NUMBER, fRelatedChangeAndCommitInfo.get_change_number());

		assertEquals("Wrong _revision_number", _REVISION_NUMBER, fRelatedChangeAndCommitInfo.get_revision_number());
		assertEquals("Wrong _current_revision_number", _CURRENT_REVISION_NUMBER,
				fRelatedChangeAndCommitInfo.get_current_revision_number());
	}

	// ------------------------------------------------------------------------
	// Object
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.RelatedChangeAndCommitInfo#hashCode()} .
	 */
	@Test
	public void testHashCode() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fRelatedChangeAndCommitInfo = gson.fromJson(reader, RelatedChangeAndCommitInfo.class);
		assertEquals("Wrong hash code", HASH_CODE, fRelatedChangeAndCommitInfo.hashCode());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.RelatedChangeAndCommitInfo#equals(java.lang.Object)} .
	 */
	// @Test
	public void testEqualsObject() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.RelatedChangeAndCommitInfo#toString()} .
	 */
	@Test
	public void testToString() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fRelatedChangeAndCommitInfo = gson.fromJson(reader, RelatedChangeAndCommitInfo.class);
		System.out.println(TO_STRING);
		System.out.println(fRelatedChangeAndCommitInfo.toString());
		assertEquals("Wrong string", TO_STRING, fRelatedChangeAndCommitInfo.toString());
	}

}
