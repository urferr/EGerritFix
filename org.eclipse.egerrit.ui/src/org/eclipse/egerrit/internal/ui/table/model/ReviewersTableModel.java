/*******************************************************************************
 * Copyright (c) 2015-2016 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Description:
 * 	This class implements the Reviewers table.
 *
 * Contributors:
 *   Jacques Bouthillier - Initial Implementation of the reviewers table
 ******************************************************************************/

package org.eclipse.egerrit.internal.ui.table.model;

import java.util.ArrayList;

import org.eclipse.egerrit.internal.ui.utils.Messages;
import org.eclipse.swt.SWT;

/**
 * @author Jacques Bouthillier
 * @since 1.0
 */
// ------------------------------------------------------------------------
// Constants
// ------------------------------------------------------------------------
// Definition of the Conflict with table :name, width of the column, Resizeable,
// Moveable, Alignment
public enum ReviewersTableModel implements ITableModel {
	// 			Name 			                               Width 	Resize Moveable Alignment
	SELECT("", 24, false, true, SWT.LEFT), // //$NON-NLS-1$
	ID(Messages.ReviewersTableDefinition_id, 120, true, true, SWT.LEFT), //
	ROLE(Messages.ReviewersTableDefinition_role, 170, true, true, SWT.LEFT);

	private final String fHeader;

	private final int fwidth;

	private final boolean fResize;

	private final boolean fMoveable;

	private final int fAlignment;

	private ReviewersTableModel(String aName, int aWidth, boolean aResize, boolean aMove, int align) {
		fHeader = aName;
		fwidth = aWidth;
		fResize = aResize;
		fMoveable = aMove;
		fAlignment = align;
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
		for (ITableModel st : ReviewersTableModel.values()) {
			listName.add(st.getName());
		}
		return listName.toArray(new String[] {});
	}

	/**
	 * return the minimum width for all columns
	 */
	public int getMinimumWidth() {
		int width = 0;
		for (int index = 0; index < ReviewersTableModel.values().length; index++) {
			width += ReviewersTableModel.values()[index].getWidth();
		}
		return width;
	}

	/**
	 * Provide an index array width the default order definition
	 *
	 * @return int[]
	 */
	public static int[] getDefaultOrder() {
		int size = ReviewersTableModel.values().length;
		int[] order = new int[size];
		for (int index = 0; index < size; index++) {
			order[index] = index;
		}
		return order;
	}
}
