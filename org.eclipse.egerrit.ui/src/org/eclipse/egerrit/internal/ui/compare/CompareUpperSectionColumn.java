package org.eclipse.egerrit.internal.ui.compare;

import java.util.ArrayList;

import org.eclipse.egerrit.internal.ui.table.model.ITableModel;
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
public enum CompareUpperSectionColumn implements ITableModel {
	// 			Name 			                               Width 	Resize Moveable Alignment
	REVIEWED("", 35, true, true, SWT.LEFT), //$NON-NLS-1$
	MOD_TYPE("", 30, true, true, SWT.LEFT), //$NON-NLS-1$
	FILE_PATH(Messages.FilesTableDefinition_filePath, 250, true, true, SWT.LEFT), //
	COMMENTS(Messages.FilesTableDefinition_comments, 175, true, true, SWT.LEFT), //
	OTHERCOMMENTS(Messages.FilesTableDefinition_against_comments, 175, true, true, SWT.LEFT), //
	SIZE(Messages.FilesTableDefinition_size, 80, false, true, SWT.LEFT);

	private final String fHeader;

	private int fwidth;

	private final int fInitialwidth;

	private final boolean fResize;

	private final boolean fMoveable;

	private final int fAlignment;

	private boolean fVisible = true;

	private CompareUpperSectionColumn(String aName, int aWidth, boolean aResize, boolean aMove, int align) {
		fHeader = aName;
		fwidth = aWidth;
		fInitialwidth = aWidth;
		fResize = aResize;
		fMoveable = aMove;
		fAlignment = align;
		fVisible = true;
	}

	@Override
	public String getName() {
		return fHeader;
	}

	@Override
	public int getWidth() {
		return fwidth;
	}

	@Override
	public boolean getResize() {
		return fResize;
	}

	@Override
	public boolean getMoveable() {
		return fMoveable;
	}

	@Override
	public int getAlignment() {
		return fAlignment;
	}

	@Override
	public String[] getColumnName() {
		ArrayList<String> listName = new ArrayList<String>();
		for (ITableModel st : CompareUpperSectionColumn.values()) {
			listName.add(st.getName());
		}
		return listName.toArray(new String[] {});
	}

	@Override
	public int getMinimumWidth() {
		int width = 0;
		for (int index = 0; index < CompareUpperSectionColumn.values().length; index++) {
			width += CompareUpperSectionColumn.values()[index].getWidth();
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

	/**
	 * Provide an index array width the default order definition
	 *
	 * @return int[]
	 */
	public static int[] getDefaultOrder() {
		int size = CompareUpperSectionColumn.values().length;
		int[] order = new int[size];
		for (int index = 0; index < size; index++) {
			order[index] = index;
		}
		return order;
	}
}
