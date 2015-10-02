/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Guy Perron - Initial Implementation
 ******************************************************************************/
package org.eclipse.egerrit.ui.internal.table;

import org.eclipse.egerrit.ui.internal.table.model.HistoryTableModel;
import org.eclipse.egerrit.ui.internal.table.model.ITableModel;
import org.eclipse.egerrit.ui.internal.table.model.ReviewTableSorter;
import org.eclipse.egerrit.ui.internal.utils.UIUtils;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

/**
 * This class implements the history table view.
 *
 * @since 1.0
 */
public class UIHistoryTable {

	private final int TABLE_STYLE = (SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);

	// ------------------------------------------------------------------------
	// Variables
	// ------------------------------------------------------------------------
	private TableViewer fViewer;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	public UIHistoryTable() {

	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	public TableViewer createTableViewerSection(Composite aParent, GridData layoutData) {
		// Create a form
		Composite viewerForm = UIUtils.createsGeneralComposite(aParent, SWT.BORDER | SWT.SHADOW_ETCHED_IN);

		GridLayout layout = new GridLayout();
		layout.numColumns = HistoryTableModel.values().length;
		layout.makeColumnsEqualWidth = false;
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		viewerForm.setLayout(layout);

		viewerForm.setLayoutData(layoutData);

		// Create the table viewer
		fViewer = new TableViewer(viewerForm, TABLE_STYLE);
		fViewer = buildAndLayoutTable(fViewer);

		// Set the content sorter
		ReviewTableSorter.bind(fViewer);

		//
		fViewer.getTable().addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		return fViewer;

	}

	/**
	 * Create each column for the Conflicts with table
	 *
	 * @param aParent
	 * @param aViewer
	 */
	private TableViewer buildAndLayoutTable(final TableViewer aViewer) {
		final Table table = aViewer.getTable();

		//Get the review table definition
		ITableModel[] tableInfo = HistoryTableModel.values();
		int size = tableInfo.length;
//		logger.debug("Table	Name	Width	Resize Moveable"); //$NON-NLS-1$
		for (int index = 0; index < size; index++) {
//			logger.debug("index [ " + index + " ] " + tableInfo[index].getName() + "\t: " //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
//					+ tableInfo[index].getWidth() + "\t: " + tableInfo[index].getResize() + "\t: " //$NON-NLS-1$ //$NON-NLS-2$
//					+ tableInfo[index].getMoveable());
			TableViewerColumn col = createTableViewerColumn(tableInfo[index]);

			GridData gribData = new GridData(SWT.FILL, SWT.FILL, true, true);
			gribData.minimumWidth = tableInfo[index].getWidth();
			gribData.minimumHeight = 100;
			col.getColumn().getParent().setLayoutData(gribData);
		}

		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		return aViewer;
	}

	/**
	 * Create each column in the review table list
	 *
	 * @param ConflictWithTableModel
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

	public TableViewer getViewer() {
		return fViewer;
	}

}
