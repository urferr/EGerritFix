/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jacques Bouthillier - Initial Implementation of the reviewers table
 ******************************************************************************/
package org.eclipse.egerrit.internal.ui.table;

import org.eclipse.egerrit.internal.ui.table.model.ITableModel;
import org.eclipse.egerrit.internal.ui.table.model.ReviewTableSorter;
import org.eclipse.egerrit.internal.ui.table.model.ReviewersTableModel;
import org.eclipse.egerrit.internal.ui.utils.UIUtils;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

/**
 * This class implements the reviewers table view.
 *
 * @since 1.0
 */
public class UIReviewersTable {

	public static final String REVIEWERS_TABLE = "reviewersTable"; //$NON-NLS-1$

	private static final int TABLE_STYLE = SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION;

	// ------------------------------------------------------------------------
	// Variables
	// ------------------------------------------------------------------------
	private TableViewer fViewer;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	public UIReviewersTable() {
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	public TableViewer createTableViewerSection(Composite aParent, String[] dynamicReviewersColumn) {
		// Create the table viewer to maintain the list of reviews
		fViewer = new TableViewer(aParent, TABLE_STYLE);
		fViewer = buildAndLayoutTable(fViewer, dynamicReviewersColumn);

		// Set the content sorter
		ReviewTableSorter.bind(fViewer);

		return fViewer;

	}

	/**
	 * Create each column for the List of Reviewers
	 *
	 * @param aParent
	 * @param aViewer
	 * @param dynamicReviewersColumn
	 */
	private TableViewer buildAndLayoutTable(final TableViewer aViewer, String[] dynamicReviewersColumn) {
		final Table table = aViewer.getTable();

		//Get the review table definition
		final ITableModel[] tableInfo = ReviewersTableModel.values();
		int size = tableInfo.length;
		for (int index = 0; index < size; index++) {
			createTableViewerColumn(tableInfo[index]);
		}

		if (dynamicReviewersColumn != null) {
			for (String element : dynamicReviewersColumn) {
				createTableViewerLabelColumn(element);
			}
		}
		TableLayout tableLayout = new TableLayout();
		table.setLayout(tableLayout);

		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setData(REVIEWERS_TABLE);

		return aViewer;
	}

	/**
	 * Create each column in the review table list
	 *
	 * @param ReviewersTableModel
	 * @return TableViewerColumn
	 */
	private TableViewerColumn createTableViewerColumn(ITableModel tableInfo) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(fViewer, SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(tableInfo.getName());
		column.setWidth(tableInfo.getWidth());
		column.setAlignment(tableInfo.getAlignment());
		column.setResizable(tableInfo.getResize());
		column.setMoveable(tableInfo.getMoveable());
		return viewerColumn;

	}

	/**
	 * @return the tableViewer
	 */
	public TableViewer getViewer() {
		return fViewer;
	}

	/**
	 * Create each column in the review table list
	 *
	 * @param ReviewTableDefinition
	 * @return TableViewerColumn
	 */
	private TableViewerColumn createTableViewerLabelColumn(String fullName) {
		String label = UIUtils.getAcronymLabel(fullName);
		int width = 28 + 5 * label.length();
		final TableViewerColumn viewerColumn = new TableViewerColumn(fViewer, SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(label);
		column.setWidth(width);
		column.setAlignment(SWT.LEFT);
		column.setResizable(false);
		column.setMoveable(false);
		return viewerColumn;
	}

}
