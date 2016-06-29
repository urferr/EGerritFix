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
// Definition of the Conflict with table {name, width of the column, Resizeable,
// Moveable, Alignment}
public enum BranchSelectionTableModel implements ITableModel {
	// 			Name 			                               Width 	Resize Moveable Alignment
	Branch(Messages.BranchSelectionTableModel_branch, 140, true, true, SWT.LEFT), //
	Match(Messages.BranchSelectionTableModel_match, 80, true, true, SWT.LEFT);

	private final String fHeader;

	private int fwidth;

	private final int fInitialwidth;

	private final boolean fResize;

	private final boolean fMoveable;

	private final int fAlignment;

	private boolean fVisible = true;

	private BranchSelectionTableModel(String aName, int aWidth, boolean aResize, boolean aMove, int align) {
		fHeader = aName;
		fwidth = aWidth;
		fInitialwidth = aWidth;
		fResize = aResize;
		fMoveable = aMove;
		fAlignment = align;
		fVisible = true;
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
		for (ITableModel st : BranchSelectionTableModel.values()) {
			listName.add(st.getName());
		}
		return listName.toArray(new String[] {});
	}

	public int getMinimumWidth() {
		int width = 0;
		for (int index = 0; index < BranchSelectionTableModel.values().length; index++) {
			width += BranchSelectionTableModel.values()[index].getWidth();
		}
		return width;
	}

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
}
