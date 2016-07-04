/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Description:
 * 	This class implements the Same Topic table.
 *
 * Contributors:
 *   Jacques Bouthillier - Initial Implementation of the same topic table
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
// Definition of the Same topic table {name, width of the column, Resizeable,
// Moveable, Alignment}

public enum SameTopicTableModel implements ITableModel {
	// 			Name 			                               Width 	Resize Moveable Alignment
	ID(Messages.SameTopicTableDefinition_id, 80, false, true, SWT.LEFT), //
	HEADLINE(Messages.SameTopicTableDefinition_headline, 200, true, true, SWT.LEFT);

	private final String fHeader;

	private final int fwidth;

	private final boolean fResize;

	private final boolean fMoveable;

	private final int fAlignment;

	private SameTopicTableModel(String aName, int aWidth, boolean aResize, boolean aMove, int align) {
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
		for (ITableModel st : SameTopicTableModel.values()) {
			listName.add(st.getName());
		}
		return listName.toArray(new String[] {});
	}

	public int getMinimumWidth() {
		int width = 0;
		for (int index = 0; index < SameTopicTableModel.values().length; index++) {
			width += SameTopicTableModel.values()[index].getWidth();
		}
		return width;
	}

	/**
	 * Provide an index array width the default order definition
	 *
	 * @return int[]
	 */
	public static int[] getDefaultOrder() {
		int size = SameTopicTableModel.values().length;
		int[] order = new int[size];
		for (int index = 0; index < size; index++) {
			order[index] = index;
		}
		return order;
	}

}
