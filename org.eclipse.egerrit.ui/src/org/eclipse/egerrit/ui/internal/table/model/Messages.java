/*******************************************************************************
 * Copyright (c) 2015 Ericsson AB and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Jacques Bouthillier - initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.ui.internal.table.model;

import org.eclipse.osgi.util.NLS;

/**
 * This class implements the internalization of the strings.
 *
 * @since 1.0
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.egerrit.ui.internal.table.model.messages"; //$NON-NLS-1$

	public static String ConfictWithTableDefinition_id;

	public static String ConfictWithTableDefinition_headline;

	public static String SameTopicTableDefinition_id;

	public static String SameTopicTableDefinition_headline;

	public static String RelatedChangesTableDefinition_id;

	public static String RelatedChangesTableDefinition_headline;

	public static String RelatedChangesTableDefinition_flag;

	public static String ReviewersTableDefinition_id;

	public static String ReviewersTableDefinition_role;

	public static String ReviewersTableDefinition_cr;

	public static String ReviewersTableDefinition_v;

	public static String FilesTableDefinition_filePath;

	public static String FilesTableDefinition_comments;

	public static String FilesTableDefinition_size;

	public static String PatchSetsTableDefinition_patchSet;

	public static String PatchSetsTableDefinition_commit;

	public static String PatchSetsTableDefinition_date;

	public static String PatchSetsTableDefinition_author;

	public static String MergeIfNecessary;

	public static String FastForwardOnly;

	public static String RebaseIfNecessary;

	public static String MergeAlways;

	public static String CherryPick;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
