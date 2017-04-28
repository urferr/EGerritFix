/*******************************************************************************
 * Copyright (c) 2015-2016 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jacques Bouthillier - Initial Implementation of the files table
 ******************************************************************************/

package org.eclipse.egerrit.internal.ui.table.model;

import java.util.ArrayList;

import org.eclipse.egerrit.internal.ui.utils.Messages;
import org.eclipse.swt.SWT;

/**
 * This class implements the files table.
 *
 * @since 1.0
 */
// ------------------------------------------------------------------------
// Constants
// ------------------------------------------------------------------------
// Definition of the Conflict with table :name, width of the column, Resizeable,
// Moveable, Alignment
public enum FilesTableModel implements ITableModel {
	// 			Name 			                               Width 	Resize Moveable Alignment
	REVIEWED("", 35, true, true, SWT.LEFT), //$NON-NLS-1$
	MOD_TYPE("", 30, true, true, SWT.LEFT), //$NON-NLS-1$
	FILE_PATH(Messages.FilesTableDefinition_filePath, 250, true, true, SWT.LEFT), //
	COMMENTS(Messages.FilesTableDefinition_comments, 175, true, true, SWT.LEFT), //
	SIZE(Messages.FilesTableDefinition_size, 80, false, true, SWT.LEFT);

	private final String fHeader;

	private int fwidth;

	private final int fInitialwidth;

	private final boolean fResize;

	private final boolean fMoveable;

	private final int fAlignment;

	private boolean fVisible = true;

	private FilesTableModel(String aName, int aWidth, boolean aResize, boolean aMove, int align) {
		fHeader = aName;
		fwidth = aWidth;
		fInitialwidth = aWidth;
		fResize = aResize;
		fMoveable = aMove;
		fAlignment = align;
		fVisible = true;
	}

	/**
	 * return the name
	 */
	public String getName() {
		return fHeader;
	}

	/**
	 * return the column width
	 */
	public int getWidth() {
		return fwidth;
	}

	/**
	 * return boolean if the field is resize-able
	 */
	public boolean getResize() {
		return fResize;
	}

	/**
	 * return boolean if the column is move-able
	 */
	public boolean getMoveable() {
		return fMoveable;
	}

	/**
	 * return the alignment of the field
	 */
	public int getAlignment() {
		return fAlignment;
	}

	/**
	 * return an array with the column name
	 */
	public String[] getColumnName() {
		ArrayList<String> listName = new ArrayList<>();
		for (ITableModel st : FilesTableModel.values()) {
			listName.add(st.getName());
		}
		return listName.toArray(new String[] {});
	}

	/**
	 * return the minimum width for all columns
	 */
	public int getMinimumWidth() {
		int width = 0;
		for (int index = 0; index < FilesTableModel.values().length; index++) {
			width += FilesTableModel.values()[index].getWidth();
		}
		return width;
	}

	/**
	 * return if the column is visible
	 *
	 * @return
	 */
	public boolean isColumnVisible() {
		return fVisible;
	}

	public void setColumnVisible(boolean value) {
		fVisible = value;
		if (value) {
			fwidth = fInitialwidth;
		} else {
			fwidth = 0;
		}
	}

	/**
	 * Provide an index array width the default order definition
	 *
	 * @return int[]
	 */
	public static int[] getDefaultOrder() {
		int size = FilesTableModel.values().length;
		int[] order = new int[size];
		for (int index = 0; index < size; index++) {
			order[index] = index;
		}
		return order;
	}
}
