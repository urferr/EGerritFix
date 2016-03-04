/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jacques Bouthillier - Initial Implementation of the conflict with table
 ******************************************************************************/

package org.eclipse.egerrit.ui.internal.table.model;

import java.util.ArrayList;

import org.eclipse.swt.SWT;

/**
 * This class implements the history table.
 *
 * @since 1.0
 */
// ------------------------------------------------------------------------
// Constants
// ------------------------------------------------------------------------
// Definition of the history table {date, author, message}
public enum HistoryTableModel implements ITableModel {
	// 			Name 			                               Width 	Resize Moveable Alignment
	COMMENT("", 20, false, true, SWT.LEFT), //
	DATE(Messages.HistoryTableModel_date, 160, false, true, SWT.LEFT), //
	AUTHOR(Messages.HistoryTableModel_author, 180, true, true, SWT.LEFT), //
	MESSAGE(Messages.HistoryTableModel_message, 280, true, true, SWT.LEFT);//

	private final String fHeader;

	private final int fwidth;

	private final boolean fResize;

	private final boolean fMoveable;

	private final int fAlignment;

	private HistoryTableModel(String aName, int aWidth, boolean aResize, boolean aMove, int align) {
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
		for (ITableModel st : HistoryTableModel.values()) {
			listName.add(st.getName());
		}
		return listName.toArray(new String[] {});
	}

	public int getMinimumWidth() {
		int width = 0;
		for (int index = 0; index < HistoryTableModel.values().length; index++) {
			width += HistoryTableModel.values()[index].getWidth();
		}
		return width;
	}
}
