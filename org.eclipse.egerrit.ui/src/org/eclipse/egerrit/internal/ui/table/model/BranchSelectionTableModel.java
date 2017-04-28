/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson - initial API and implementation
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
// Definition of the Branch Selection with table :name, width of the column, Resizeable,
// Moveable, Alignment
public enum BranchSelectionTableModel implements ITableModel {
	// 			Name 			                               Width 	Resize Moveable Alignment
	Branch(Messages.BranchSelectionTableModel_branch, 140, true, true, SWT.LEFT), //
	Match(Messages.BranchSelectionTableModel_match, 350, true, true, SWT.LEFT);

	private final String fHeader;

	private int fwidth;

	private final boolean fResize;

	private final boolean fMoveable;

	private final int fAlignment;

	private boolean fVisible = true;

	private BranchSelectionTableModel(String aName, int aWidth, boolean aResize, boolean aMove, int align) {
		fHeader = aName;
		fwidth = aWidth;
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
		for (ITableModel st : BranchSelectionTableModel.values()) {
			listName.add(st.getName());
		}
		return listName.toArray(new String[] {});
	}

	/**
	 * return the minimum width for all columns
	 */
	public int getMinimumWidth() {
		int width = 0;
		for (int index = 0; index < BranchSelectionTableModel.values().length; index++) {
			width += BranchSelectionTableModel.values()[index].getWidth();
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

}
