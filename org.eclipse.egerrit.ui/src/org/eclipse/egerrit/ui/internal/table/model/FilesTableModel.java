/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jacques Bouthillier - Initial Implementation of the files table
 ******************************************************************************/

package org.eclipse.egerrit.ui.internal.table.model;

import java.util.ArrayList;

import org.eclipse.swt.SWT;

/**
 * This class implements the files table.
 *
 * @since 1.0
 */
// ------------------------------------------------------------------------
// Constants
// ------------------------------------------------------------------------
// Definition of the Conflict with table {name, width of the column, Resizeable,
// Moveable, Alignment}
public enum FilesTableModel implements ITableModel {
	// 			Name 			                               Width 	Resize Moveable Alignment
	REVIEWED("", 30, false, true, SWT.LEFT), //
	MOD_TYPE("", 30, true, true, SWT.LEFT), //
	FILE_PATH(Messages.FilesTableDefinition_filePath, 360, true, true, SWT.LEFT), //
	COMMENTS(Messages.FilesTableDefinition_comments, 150, true, true, SWT.LEFT), //
	SIZE(Messages.FilesTableDefinition_size, 120, false, true, SWT.LEFT);

	private final String fHeader;

	private final int fwidth;

	private final boolean fResize;

	private final boolean fMoveable;

	private final int fAlignment;

	private FilesTableModel(String aName, int aWidth, boolean aResize, boolean aMove, int align) {
		fHeader = aName;
		fwidth = aWidth;
		fResize = aResize;
		fMoveable = aMove;
		fAlignment = align;
	}

	public String getName() {
		return fHeader;
	}

	public int getWidth() {
		return fwidth;
	}

	public boolean getResize() {
		return fResize;
	}

	public boolean getMoveable() {
		return fMoveable;
	}

	public int getAlignment() {
		return fAlignment;
	}

	public String[] getColumnName() {
		ArrayList<String> listName = new ArrayList<String>();
		for (ITableModel st : FilesTableModel.values()) {
			listName.add(st.getName());
		}
		return listName.toArray(new String[] {});
	}

	public int getMinimumWidth() {
		int width = 0;
		for (int index = 0; index < FilesTableModel.values().length; index++) {
			width += FilesTableModel.values()[index].getWidth();
		}
		return width;
	}
}
