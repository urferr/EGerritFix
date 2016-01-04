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
import static org.junit.Assert.fail;

import java.io.Reader;
import java.io.StringReader;

import org.eclipse.egerrit.core.command.EMFTypeAdapterFactory;
import org.eclipse.egerrit.internal.model.FileInfo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/**
 * Test suite for {@link org.eclipse.egerrit.internal.model.FileInfo}
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class FileInfoTest {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	private static final String STATUS = "M";

	private static final String BINARY = "true";

	private static final String OLD_PATH = "/old/path";

	private static final String LINES_INSERTED = "4321";

	private static final String LINES_DELETED = "1234";

	private static final int HASH_CODE = -1833470542;

	private static final String TO_STRING = "FileInfo [" + "status=" + STATUS + ", binary=" + BINARY + ", old_path="
			+ OLD_PATH + ", lines_inserted=" + LINES_INSERTED + ", lines_deleted=" + LINES_DELETED + "]";

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
	private FileInfo fFileInfo = null;

	// ------------------------------------------------------------------------
	// Helper methods
	// ------------------------------------------------------------------------

	@Before
	public void setUp() throws Exception {
		gson = new GsonBuilder().registerTypeAdapterFactory(new EMFTypeAdapterFactory()).create();
		json = new JsonObject();
	}

	private void setAllFields() {
		json.addProperty("status", STATUS);
		json.addProperty("binary", BINARY);
		json.addProperty("old_path", OLD_PATH);
		json.addProperty("lines_inserted", LINES_INSERTED);
		json.addProperty("lines_deleted", LINES_DELETED);
	}

	@After
	public void tearDown() throws Exception {
	}

	// ------------------------------------------------------------------------
	// Test cases
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.FileInfo#getStatus()}.
	 */
	@Test
	public void testGetStatus() {
		json.addProperty("status", STATUS);
		Reader reader = new StringReader(json.toString());
		fFileInfo = gson.fromJson(reader, FileInfo.class);

		assertEquals("Wrong status", STATUS, fFileInfo.getStatus());
		assertFalse("Wrong binary", fFileInfo.isBinary());
		assertNull("Wrong old_path", fFileInfo.getOld_path());
		assertEquals("Wrong lines_inserted", 0, fFileInfo.getLines_inserted());
		assertEquals("Wrong lines_deleted", 0, fFileInfo.getLines_deleted());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.FileInfo#isBinary()} .
	 */
	@Test
	public void testIsBinary() {
		json.addProperty("binary", BINARY);
		Reader reader = new StringReader(json.toString());
		fFileInfo = gson.fromJson(reader, FileInfo.class);

		assertEquals("Wrong status", "M", fFileInfo.getStatus());
		assertEquals("Wrong binary", (boolean) Boolean.valueOf(BINARY), fFileInfo.isBinary());
		assertNull("Wrong old_path", fFileInfo.getOld_path());
		assertEquals("Wrong lines_inserted", 0, fFileInfo.getLines_inserted());
		assertEquals("Wrong lines_deleted", 0, fFileInfo.getLines_deleted());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.FileInfo#getOld_path()}.
	 */
	@Test
	public void testGetOldPath() {
		json.addProperty("old_path", OLD_PATH);
		Reader reader = new StringReader(json.toString());
		fFileInfo = gson.fromJson(reader, FileInfo.class);

		assertEquals("Wrong status", "M", fFileInfo.getStatus());
		assertFalse("Wrong binary", fFileInfo.isBinary());
		assertEquals("Wrong old_path", OLD_PATH, fFileInfo.getOld_path());
		assertEquals("Wrong lines_inserted", 0, fFileInfo.getLines_inserted());
		assertEquals("Wrong lines_deleted", 0, fFileInfo.getLines_deleted());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.FileInfo#getLines_inserted()}.
	 */
	@Test
	public void testgetLines_inserted() {
		json.addProperty("lines_inserted", LINES_INSERTED);
		Reader reader = new StringReader(json.toString());
		fFileInfo = gson.fromJson(reader, FileInfo.class);

		assertEquals("Wrong status", "M", fFileInfo.getStatus());
		assertFalse("Wrong binary", fFileInfo.isBinary());
		assertNull("Wrong old_path", fFileInfo.getOld_path());
		assertEquals("Wrong lines_inserted", (int) Integer.valueOf(LINES_INSERTED), fFileInfo.getLines_inserted());
		assertEquals("Wrong lines_deleted", 0, fFileInfo.getLines_deleted());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.FileInfo#getLines_deleted()}.
	 */
	@Test
	public void testgetLines_deleted() {
		json.addProperty("lines_deleted", LINES_DELETED);
		Reader reader = new StringReader(json.toString());
		fFileInfo = gson.fromJson(reader, FileInfo.class);

		assertEquals("Wrong status", "M", fFileInfo.getStatus());
		assertFalse("Wrong binary", fFileInfo.isBinary());
		assertNull("Wrong old_path", fFileInfo.getOld_path());
		assertEquals("Wrong lines_inserted", 0, fFileInfo.getLines_inserted());
		assertEquals("Wrong lines_deleted", (int) Integer.valueOf(LINES_DELETED), fFileInfo.getLines_deleted());
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
		fFileInfo = gson.fromJson(reader, FileInfo.class);

		assertEquals("Wrong status", STATUS, fFileInfo.getStatus());
		assertEquals("Wrong binary", (boolean) Boolean.valueOf(BINARY), fFileInfo.isBinary());
		assertEquals("Wrong old_path", OLD_PATH, fFileInfo.getOld_path());
		assertEquals("Wrong lines_inserted", (int) Integer.valueOf(LINES_INSERTED), fFileInfo.getLines_inserted());
		assertEquals("Wrong lines_deleted", (int) Integer.valueOf(LINES_DELETED), fFileInfo.getLines_deleted());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.FileInfo}.
	 */
	@Test
	public void testExtraField() {
		setAllFields();
		json.addProperty("extra", "extra_property");
		Reader reader = new StringReader(json.toString());
		fFileInfo = gson.fromJson(reader, FileInfo.class);

		assertEquals("Wrong status", STATUS, fFileInfo.getStatus());
		assertEquals("Wrong binary", (boolean) Boolean.valueOf(BINARY), fFileInfo.isBinary());
		assertEquals("Wrong old_path", OLD_PATH, fFileInfo.getOld_path());
		assertEquals("Wrong lines_inserted", (int) Integer.valueOf(LINES_INSERTED), fFileInfo.getLines_inserted());
		assertEquals("Wrong lines_deleted", (int) Integer.valueOf(LINES_DELETED), fFileInfo.getLines_deleted());
	}

	// ------------------------------------------------------------------------
	// Object
	// ------------------------------------------------------------------------

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.FileInfo#hashCode()} .
	 */
	@Test
	public void testHashCode() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fFileInfo = gson.fromJson(reader, FileInfo.class);

		assertEquals("Wrong hash code", HASH_CODE, fFileInfo.hashCode());
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.FileInfo#equals(java.lang.Object)}.
	 */
	// @Test
	public void testEqualsObject() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.eclipse.egerrit.internal.model.FileInfo#toString()} .
	 */
	@Test
	public void testToString() {
		setAllFields();
		Reader reader = new StringReader(json.toString());
		fFileInfo = gson.fromJson(reader, FileInfo.class);

		assertEquals("Wrong string", TO_STRING, fFileInfo.toString());
	}

}
