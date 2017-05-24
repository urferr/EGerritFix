/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jacques Bouthillier - Initial Implementation of the related changes table
 ******************************************************************************/
package org.eclipse.egerrit.internal.ui.table;

import org.eclipse.egerrit.internal.ui.table.model.ITableModel;
import org.eclipse.egerrit.internal.ui.table.model.RelatedChangesTableModel;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

/**
 * This class implements the related changes table view.
 *
 * @since 1.0
 */
public class UIRelatedChangesTable {

	public static final String RELATED_CHANGES_TABLE = "relatedChangesTable"; //$NON-NLS-1$

	private static final int TABLE_STYLE = SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION;

	// ------------------------------------------------------------------------
	// Variables
	// ------------------------------------------------------------------------
	private TableViewer fViewer;

	private Composite fParent;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	public UIRelatedChangesTable(Composite aParent) {
		fParent = aParent;
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	public TableViewer createTableViewerSection() {
		// Create the table viewer to maintain the list of reviews
		fViewer = new TableViewer(fParent, TABLE_STYLE);
		fViewer = buildAndLayoutTable(fViewer);

		return fViewer;
	}

	/**
	 * Create each column for the List of Related changes
	 *
	 * @param aParent
	 * @param aViewer
	 */
	private TableViewer buildAndLayoutTable(final TableViewer aViewer) {
		final Table table = aViewer.getTable();

		//Get the review table definition
		final ITableModel[] tableInfo = RelatedChangesTableModel.values();
		int size = tableInfo.length;
//		logger.debug("Table	Name	Width	Resize Moveable"); //$NON-NLS-1$
		for (int index = 0; index < size; index++) {
			createTableViewerColumn(tableInfo[index]);
		}

		TableLayout tableLayout = new TableLayout();
		table.setLayout(tableLayout);

		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setData(RELATED_CHANGES_TABLE);

		return aViewer;
	}

	/**
	 * Create each column in the review table list
	 *
	 * @param RelatedChangesTableModel
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
	 * return the table viewer
	 *
	 * @return
	 */
	public TableViewer getViewer() {
		return fViewer;
	}
}
