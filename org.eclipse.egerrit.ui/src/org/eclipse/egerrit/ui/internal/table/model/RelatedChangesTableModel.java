/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Description:
 * 	This class implements the Related Changes table.
 *
 * Contributors:
 *   Jacques Bouthillier - Initial Implementation of the related changes table
 ******************************************************************************/

package org.eclipse.egerrit.ui.internal.table.model;

import java.util.ArrayList;

import org.eclipse.swt.SWT;

/**
 * @author Jacques Bouthillier
 * @since 1.0
 */
// ------------------------------------------------------------------------
// Constants
// ------------------------------------------------------------------------
// Definition of the Conflict with table {name, width of the column, Resizeable,
// Moveable, Alignment}
public enum RelatedChangesTableModel implements ITableModel {
	// 			Name 			                               Width 	Resize Moveable Alignment
	ID(Messages.RelatedChangesTableDefinition_id, 80, false, true, SWT.LEFT), //
	HEADLINE(Messages.RelatedChangesTableDefinition_headline, 200, true, true,
			SWT.LEFT), FLAG(Messages.RelatedChangesTableDefinition_flag, 100, false, true, SWT.LEFT);

	private final String fHeader;

	private final int fwidth;

	private final boolean fResize;

	private final boolean fMoveable;

	private final int fAlignment;

	private RelatedChangesTableModel(String aName, int aWidth, boolean aResize, boolean aMove, int align) {
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
		for (ITableModel st : RelatedChangesTableModel.values()) {
			listName.add(st.getName());
		}
		return listName.toArray(new String[] {});
	}

	public int getMinimumWidth() {
		int width = 0;
		for (int index = 0; index < RelatedChangesTableModel.values().length; index++) {
			width += RelatedChangesTableModel.values()[index].getWidth();
		}
		return width;
	}
}
