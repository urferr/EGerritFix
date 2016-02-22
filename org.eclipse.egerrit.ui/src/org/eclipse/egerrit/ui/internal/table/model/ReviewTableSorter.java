/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jacques Bouthillier - Initial Implementation of the view sorter
 ******************************************************************************/
package org.eclipse.egerrit.ui.internal.table.model;

import org.eclipse.egerrit.ui.internal.table.provider.FileInfoCompareCellLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;

/**
 * This class implements the EGerrit UI view column sorter.
 *
 * @since 1.0
 */
public class ReviewTableSorter extends ViewerComparator {

	// ------------------------------------------------------------------------
	// Attributes
	// ------------------------------------------------------------------------

	// The target column
	private int fColumnIndex = 0;

	// ------------------------------------------------------------------------
	// Constructor
	// ------------------------------------------------------------------------

	public ReviewTableSorter(int columnIndex) {
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

		result = defaultCompare(viewer, item1, item2);

		if (sortDirection != SWT.UP) {
			result = -result;
		}

		return result;
	}

	private int defaultCompare(Viewer aViewer, Object aE1, Object aE2) {

		if (aViewer instanceof TableViewer) {

			// We are in a table
			TableViewer tv = (TableViewer) aViewer;
			tv.getTable().setSortColumn(tv.getTable().getColumn(fColumnIndex));

			// Lookup aE1 and aE2
			int idx1 = -1, idx2 = -1;
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

		else if (aViewer instanceof TreeViewer) {
			TreeViewer tv = (TreeViewer) aViewer;
			tv.getTree().setSortColumn(tv.getTree().getColumn(fColumnIndex));
			FileInfoCompareCellLabelProvider provider = (FileInfoCompareCellLabelProvider) tv
					.getLabelProvider(fColumnIndex);
			return provider.getLabel(aE1, fColumnIndex).compareTo(provider.getLabel(aE2, fColumnIndex));
		}
		return 0;
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
					ReviewTableSorter sorter = new ReviewTableSorter(columnNum);
					Table table = aTableViewer.getTable();
					TableColumn currentColumn = (TableColumn) e.widget;
					table.setSortColumn(currentColumn);
					table.setSortDirection(table.getSortDirection() == SWT.UP ? SWT.DOWN : SWT.UP);
					aTableViewer.setComparator(sorter);
				}
			});
		}
	}

	/**
	 * Bind a sorter to each tree column
	 *
	 * @param aTreeViewer
	 */
	public static void bind(final TreeViewer aTreeViewer) {
		for (int i = 0; i < aTreeViewer.getTree().getColumnCount(); i++) {
			final int columnNum = i;
			TreeColumn column = aTreeViewer.getTree().getColumn(i);
			column.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(final SelectionEvent e) {
					ReviewTableSorter sorter = new ReviewTableSorter(columnNum);
					Tree table = aTreeViewer.getTree();
					TreeColumn currentColumn = (TreeColumn) e.widget;
					table.setSortColumn(currentColumn);
					table.setSortDirection(table.getSortDirection() == SWT.UP ? SWT.DOWN : SWT.UP);
					aTreeViewer.setComparator(sorter);
				}
			});
		}
	}

}
