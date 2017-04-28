/*******************************************************************************
 * Copyright (c) 2016 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Ericsson Communications - Initial Implementation
 ******************************************************************************/
package org.eclipse.egerrit.internal.ui.table.model;

import org.eclipse.egerrit.internal.model.impl.ChangeMessageInfoImpl;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TreeItem;

/**
 * This class implements the History table UI view column sorter.
 *
 * @since 1.0
 */
public class HistoryTableSorter extends ViewerSorter {

	// ------------------------------------------------------------------------
	// Attributes
	// ------------------------------------------------------------------------

	// The target column
	private int fColumnIndex = 0;

	// ------------------------------------------------------------------------
	// Constructor
	// ------------------------------------------------------------------------

	public HistoryTableSorter(int columnIndex) {
		super();
		this.fColumnIndex = columnIndex;
	}

	// ------------------------------------------------------------------------
	// ViewerSorter
	// ------------------------------------------------------------------------

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ViewerComparator#compare(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Viewer viewer, Object item1, Object item2) {

		int sortDirection = SWT.NONE;
		if (viewer instanceof TableViewer) {
			sortDirection = ((TableViewer) viewer).getTable().getSortDirection();
		} else if (viewer instanceof TreeViewer) {
			sortDirection = ((TreeViewer) viewer).getTree().getSortDirection();
		}

		// The comparison result (< 0, == 0, > 0)
		int result = 0;

		// We are dealing with ChangeInfo but just in case...
		if (viewer instanceof TableViewer && item1 instanceof ChangeMessageInfoImpl
				&& item2 instanceof ChangeMessageInfoImpl) {

			ChangeMessageInfoImpl changeInfo1 = (ChangeMessageInfoImpl) item1;
			ChangeMessageInfoImpl changeInfo2 = (ChangeMessageInfoImpl) item2;

			String val1 = null;
			String val2 = null;

			switch (fColumnIndex) {
			case 0: // Comment
				result = Boolean.toString(changeInfo1.isComment()).compareTo(Boolean.toString(changeInfo2.isComment()));
				break;
			case 1: // date
				val1 = changeInfo1.getDate();
				val2 = changeInfo2.getDate();
				if (val1 != null && val2 != null) {
					result = val1.compareTo(val2);
				}
				break;
			default:
				result = defaultCompare(viewer, item1, item2);
				break;
			}
		} else {
			result = defaultCompare(viewer, item1, item2);
		}

		if (sortDirection != SWT.UP) {
			result = -result;
		}

		return result;
	}

	private int defaultCompare(Viewer aViewer, Object aE1, Object aE2) {

		if (aViewer instanceof TableViewer) {

			return defaultSortTable(aViewer, aE1, aE2);
		}

		else if (aViewer instanceof TreeViewer) {

			return defaultSortTree(aViewer, aE1, aE2);
		}
		return 0;
	}

	/**
	 * @param aViewer
	 * @param aE1
	 * @param aE2
	 * @return
	 */
	private int defaultSortTree(Viewer aViewer, Object aE1, Object aE2) {
		TreeViewer tv = (TreeViewer) aViewer;
		tv.getTree().setSortColumn(tv.getTree().getColumn(fColumnIndex));
		int idx1 = -1;
		int idx2 = -1;

		Object[] listObj = tv.getTree().getItems();

		for (int i = 0; i < listObj.length; i++) {
			Object obj = ((TreeItem) listObj[i]).getData();
			((TreeItem) listObj[i]).setExpanded(true);

			if (obj != null) {
				if (obj.equals(aE1)) {
					idx1 = i;
				} else if (obj.equals(aE2)) {
					idx2 = i;
				}
				if (idx1 > 0 && idx2 > 0) {
					break;
				}
			}
		}

		int order = 0;
		if (idx1 > -1 && idx2 > -1) {
			String str1 = tv.getTree().getItems()[idx1].getText(this.fColumnIndex);
			String str2 = tv.getTree().getItems()[idx2].getText(this.fColumnIndex);
			order = str1.compareTo(str2);
		}
		return order;
	}

	/**
	 * @param aViewer
	 * @param aE1
	 * @param aE2
	 * @return
	 */
	private int defaultSortTable(Viewer aViewer, Object aE1, Object aE2) {
		// We are in a table
		TableViewer tv = (TableViewer) aViewer;
		tv.getTable().setSortColumn(tv.getTable().getColumn(fColumnIndex));

		// Lookup aE1 and aE2
		int idx1 = -1;
		int idx2 = -1;
		for (int i = 0; i < tv.getTable().getItemCount(); i++) {
			Object obj = tv.getElementAt(i);
			if (obj.equals(aE1)) {
				idx1 = i;
			} else if (obj.equals(aE2)) {
				idx2 = i;
			}
			if (idx1 != -1 && idx2 != -1) {
				break;
			}
		}

		// Compare the respective fields
		int order = 0;

		if (idx1 > -1 && idx2 > -1) {
			String str1 = tv.getTable().getItems()[idx1].getText(this.fColumnIndex);
			String str2 = tv.getTable().getItems()[idx2].getText(this.fColumnIndex);
			order = str1.compareTo(str2);
		}
		return order;
	}

	// ------------------------------------------------------------------------
	// Static methods
	// ------------------------------------------------------------------------

	/**
	 * Bind a sorter to each table column
	 *
	 * @param aTableViewer
	 */
	public static void bind(final TableViewer aTableViewer) {
		for (int i = 0; i < aTableViewer.getTable().getColumnCount(); i++) {
			final int columnNum = i;
			TableColumn column = aTableViewer.getTable().getColumn(i);
			column.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(final SelectionEvent e) {
					HistoryTableSorter sorter = new HistoryTableSorter(columnNum);
					Table table = aTableViewer.getTable();
					TableColumn currentColumn = (TableColumn) e.widget;
					table.setSortColumn(currentColumn);
					table.setSortDirection(table.getSortDirection() == SWT.UP ? SWT.DOWN : SWT.UP);
					aTableViewer.setComparator(sorter);
					aTableViewer.setSelection(aTableViewer.getSelection(), true);
				}
			});
		}
	}
}
