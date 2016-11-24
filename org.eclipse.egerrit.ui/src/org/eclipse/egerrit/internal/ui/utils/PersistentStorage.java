/*******************************************************************************
 * Copyright (c) 2016 Ericsson AB.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson - initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.internal.ui.utils;

import java.util.Arrays;

import org.eclipse.egerrit.internal.ui.EGerritUIPlugin;
import org.eclipse.egerrit.internal.ui.table.model.ReviewTableSorter;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;

/**
 * This class store/restore persistent data for the files data stored in a TableViewer or a TreeViewer..
 */
public class PersistentStorage {

	private static final String VIEW_COLUMN_ORDER = "egerritViewColumnOrder"; //$NON-NLS-1$

	private static final String VIEW_COLUMN_WIDTH = "egerritViewColumnWidth"; //$NON-NLS-1$

	private static final String COLUMN_SELECTION = "egerritSelectColumn"; //$NON-NLS-1$

	private static final String SORT_DIRECTION = "sortDirection"; //$NON-NLS-1$

	private String fStorageSectionName;

	private Viewer fViewer;

	public PersistentStorage(Viewer viewer, String storageSectionName) {
		this.fStorageSectionName = storageSectionName;
		this.fViewer = viewer;
	}

	/**
	 * Persist data according to a specific section
	 */
	public void storeDialogSettings() {
		if (fViewer == null) {
			return;
		}

		Table table = null;
		Tree tree = null;
		ReviewTableSorter comparator = null;
		if (fViewer instanceof TableViewer) {
			table = ((TableViewer) fViewer).getTable();
			comparator = (ReviewTableSorter) ((TableViewer) fViewer).getComparator();
		}
		if (fViewer instanceof TreeViewer) {
			tree = ((TreeViewer) fViewer).getTree();
			comparator = (ReviewTableSorter) ((TreeViewer) fViewer).getComparator();
		}

		int[] columnOrder = null;
		int numColumn;
		int[] columnWidth = null;
		if (table != null) {
			columnOrder = table.getColumnOrder();
			numColumn = table.getColumnCount();
			columnWidth = new int[numColumn];
			for (int i = 0; i < numColumn; i++) {
				columnWidth[i] = table.getColumn(i).getWidth();
			}
		} else if (tree != null) {
			//the tree one
			columnOrder = tree.getColumnOrder();
			numColumn = tree.getColumnCount();
			columnWidth = new int[numColumn];
			for (int i = 0; i < numColumn; i++) {
				columnWidth[i] = tree.getColumn(i).getWidth();
			}
		}

		if (columnOrder != null) {
			getDialogSettings(fStorageSectionName).put(VIEW_COLUMN_ORDER,
					Arrays.stream(columnOrder).mapToObj(i -> String.valueOf(i)).toArray(String[]::new));
		}
		if (columnWidth != null) {
			getDialogSettings(fStorageSectionName).put(VIEW_COLUMN_WIDTH,
					Arrays.stream(columnWidth).mapToObj(i -> String.valueOf(i)).toArray(String[]::new));
		}

		if (comparator != null) {
			int direction = SWT.NONE;
			if (table != null) {
				direction = table.getSortDirection();
			} else if (tree != null) {
				direction = tree.getSortDirection();
			}
			getDialogSettings(fStorageSectionName).put(COLUMN_SELECTION, comparator.getColumnSorter());
			getDialogSettings(fStorageSectionName).put(SORT_DIRECTION, direction);
		}
	}

	/**
	 * Restore data according to a specific section for a tree or a table viewer
	 */
	public void restoreDialogSettings() {
		if (fViewer == null) {
			return;
		}
		if (fViewer instanceof TableViewer) {
			restoreTableSettings((TableViewer) fViewer);
		} else if (fViewer instanceof TreeViewer) {
			restoreTreeSettings((TreeViewer) fViewer);
		}
	}

	private void restoreTableSettings(TableViewer tableViewer) {
		String[] backedUpValue = getDialogSettings(fStorageSectionName).getArray(VIEW_COLUMN_ORDER);
		if (backedUpValue != null) {
			int[] columnOrder = Arrays.stream(backedUpValue).mapToInt(Integer::parseInt).toArray();
			tableViewer.getTable().setColumnOrder(columnOrder);
		}

		backedUpValue = getDialogSettings(fStorageSectionName).getArray(VIEW_COLUMN_WIDTH);
		if (backedUpValue != null) {
			int[] columnWidth = Arrays.stream(backedUpValue).mapToInt(Integer::parseInt).toArray();
			int numColumn = tableViewer.getTable().getColumns().length;
			for (int i = 0; i < numColumn; i++) {
				tableViewer.getTable().getColumn(i).setWidth(columnWidth[i]);
			}
		}

		//Adjust the column sorting
		int columnSelected = -1;
		try {
			columnSelected = getDialogSettings(fStorageSectionName).getInt(COLUMN_SELECTION);
		} catch (NumberFormatException e) {
			//no data stored yet
		}
		if (columnSelected >= 0) {
			try {
				int direction = getDialogSettings(fStorageSectionName).getInt(SORT_DIRECTION);
				TableColumn tableColumn = tableViewer.getTable().getColumn(columnSelected);
				tableViewer.getTable().setSortColumn(tableColumn);
				tableViewer.getTable().setSortDirection(direction);
				tableViewer.setComparator(new ReviewTableSorter(columnSelected));
			} catch (NumberFormatException e) {
				//no data stored yet
			}
			return;
		}
	}

	private void restoreTreeSettings(TreeViewer treeViewer) {
		String[] backedUpValue = getDialogSettings(fStorageSectionName).getArray(VIEW_COLUMN_ORDER);
		if (backedUpValue != null) {
			int[] columnOrder = Arrays.stream(backedUpValue).mapToInt(Integer::parseInt).toArray();
			treeViewer.getTree().setColumnOrder(columnOrder);
		}

		backedUpValue = getDialogSettings(fStorageSectionName).getArray(VIEW_COLUMN_WIDTH);
		if (backedUpValue != null) {
			int[] columnWidth = Arrays.stream(backedUpValue).mapToInt(Integer::parseInt).toArray();
			int numColumn = treeViewer.getTree().getColumns().length;
			for (int i = 0; i < numColumn; i++) {
				treeViewer.getTree().getColumn(i).setWidth(columnWidth[i]);
			}
		}

		//Adjust the column sorting
		int columnSelected = -1;
		try {
			columnSelected = getDialogSettings(fStorageSectionName).getInt(COLUMN_SELECTION);
		} catch (NumberFormatException e) {
			//no data stored yet
		}
		if (columnSelected >= 0) {
			try {
				int direction = getDialogSettings(fStorageSectionName).getInt(SORT_DIRECTION);
				TreeColumn tableColumn = treeViewer.getTree().getColumn(columnSelected);
				treeViewer.getTree().setSortColumn(tableColumn);
				treeViewer.getTree().setSortDirection(direction);
				treeViewer.setComparator(new ReviewTableSorter(columnSelected));
			} catch (NumberFormatException e) {
				//no data stored yet
			}
			return;
		}
	}

	public IDialogSettings getDialogSettings(String selectSection) {
		IDialogSettings settings = EGerritUIPlugin.getDefault().getDialogSettings();
		IDialogSettings section = settings.getSection(selectSection);
		if (section == null) {
			section = settings.addNewSection(selectSection);
		}
		return section;
	}
}
