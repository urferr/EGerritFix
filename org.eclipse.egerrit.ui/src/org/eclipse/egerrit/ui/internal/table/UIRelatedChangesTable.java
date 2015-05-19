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
package org.eclipse.egerrit.ui.internal.table;

import org.eclipse.egerrit.ui.internal.table.model.ITableModel;
import org.eclipse.egerrit.ui.internal.table.model.RelatedChangesTableModel;
import org.eclipse.egerrit.ui.internal.utils.UIUtils;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

/**
 * This class implements the related changes table view.
 *
 * @since 1.0
 */
public class UIRelatedChangesTable {

	private final int TABLE_STYLE = (SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);

	// ------------------------------------------------------------------------
	// Variables
	// ------------------------------------------------------------------------
	private TableViewer fViewer;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	public UIRelatedChangesTable() {

	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	public TableViewer createTableViewerSection(Composite aParent, GridData layoutData) {
		// Create a form to maintain the search data
		Composite viewerForm = UIUtils.createsGeneralComposite(aParent, SWT.BORDER | SWT.SHADOW_ETCHED_IN);

		GridLayout layout = new GridLayout();
		layout.numColumns = RelatedChangesTableModel.values().length;
		layout.makeColumnsEqualWidth = false;
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		viewerForm.setLayout(layout);

		viewerForm.setLayoutData(layoutData);

		// Create the table viewer to maintain the list of reviews
		fViewer = new TableViewer(viewerForm, TABLE_STYLE);
		fViewer = buildAndLayoutTable(fViewer);

		// Set the content provider and the Label provider and the sorter
//		fViewer.setContentProvider(new ReviewTableContentProvider());

		// Set the viewer for the provider
//		ReviewTableLabelProvider tableProvider = new ReviewTableLabelProvider();
//		fViewer.setLabelProvider(tableProvider);
//		ReviewTableSorter.bind(fViewer);
//		fViewer.setComparator(new ReviewTableSorter(7)); // sort by Updated, descending

		// Create the help context id for the viewer's control
		// PlatformUI
		// .getWorkbench()
		// .getHelpSystem()
		// .setHelp(fViewer.getControl(),
		// "org.eclipse.egerrit.dashboard.ui.viewer");

		//
		fViewer.getTable().addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
//				GerritUi.Ftracer.traceInfo("Table selection: " + e.toString()); //$NON-NLS-1$
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		// Add a Key event and mouse down listener
//		fViewer.getTable().addListener(SWT.MouseDown, mouseButtonListener);

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
//		GerritUi.Ftracer.traceInfo("Table	Name	Width	Resize Moveable"); //$NON-NLS-1$
		for (int index = 0; index < size; index++) {
//			GerritUi.Ftracer.traceInfo("index [ " + index + " ] " + tableInfo[index].getName() + "\t: " //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
//					+ tableInfo[index].getWidth() + "\t: " + tableInfo[index].getResize() + "\t: " //$NON-NLS-1$ //$NON-NLS-2$
//					+ tableInfo[index].getMoveable());
			TableViewerColumn col = createTableViewerColumn(tableInfo[index]);

			GridData gribData = new GridData(SWT.FILL, SWT.FILL, true, true);
			gribData.minimumWidth = tableInfo[index].getWidth();
			gribData.minimumHeight = 100;
			col.getColumn().getParent().setLayoutData(gribData);
		}

		TableLayout tableLayout = new TableLayout();
		table.setLayout(tableLayout);

		table.addControlListener(new ControlAdapter() {

			@Override
			public void controlResized(ControlEvent e) {
				table.setRedraw(false);
				Point tableSize = table.getSize();
				Point parentSize = table.getParent().getSize();
				//Adjust the width  according to its parent
				int minimumTableWidth = tableInfo[0].getMinimumWidth();
				int minHeadlineWidth = RelatedChangesTableModel.HEADLINE.getWidth();

				//Adjust the column to take the remaining space
				int scrollWidth = table.getVerticalBar().getSize().x;
				//If not visible, take the extra space
				if (!table.getVerticalBar().isVisible()) {
					scrollWidth = 0;
				}

				int computeExtraWidth = parentSize.x - 10 - (minimumTableWidth) - scrollWidth;
				int newHeadlineWidth = minHeadlineWidth;
				//If extra space, redistribute it to specific column
				if (computeExtraWidth > 0) {
					//Assign all to headline
					newHeadlineWidth = minHeadlineWidth + computeExtraWidth;
				}
				//Headline column
				table.getColumn(RelatedChangesTableModel.HEADLINE.ordinal()).setWidth(newHeadlineWidth);

				table.setSize(parentSize.x - 10, tableSize.y);
				table.setRedraw(true);

			}

		});

		table.setHeaderVisible(true);
		table.setLinesVisible(true);

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

//	private final Listener mouseButtonListener = new Listener() {
//		public void handleEvent(Event aEvent) {
//			GerritUi.Ftracer.traceInfo("mouseButtonListener() for " + aEvent.button); //$NON-NLS-1$
//			switch (aEvent.type) {
//			case SWT.MouseDown:
//				// Left Click
//				if (aEvent.button == 1) {
//
//					// Process the Item table handling
//					processItemSelection();
//
//				}
//				// For now, use button 2 to modify the starred value column 1
//				if (aEvent.button == 2) {
//					// Select the new item in the table
//					Table table = fViewer.getTable();
//					table.deselectAll();
//					Point p = new Point(aEvent.x, aEvent.y);
//					TableItem tbi = fViewer.getTable().getItem(p);
//					if (tbi != null) {
//						table.setSelection(tbi);
//					}
//
//					// Execute the command to adjust the column: ID with the
//					// starred information
//					AdjustMyStarredHandler handler = new AdjustMyStarredHandler();
//					try {
//						handler.execute(new ExecutionEvent());
//					} catch (ExecutionException excutionException) {
////						StatusHandler.log(new Status(IStatus.ERROR, GerritCorePlugin.PLUGIN_ID,
////								excutionException.getMessage(), excutionException));
//						GerritUi.Ftracer.traceError(excutionException.getMessage());
//
//					}
//				}
//				// Right Click
//				if (aEvent.button == 3) {
//					// Process the Item table handling
//					// processItemSelection();
//				}
//				break;
//			default:
//				break;
//			}
//		}
//
//	};

//	/**
//	 * Process the selected data from the item table
//	 */
//	private void processItemSelection() {
//		ISelection tableSelection = fViewer.getSelection();
//		if (!tableSelection.isEmpty()) {
//			if (tableSelection instanceof IStructuredSelection) {
//				Object obj = ((IStructuredSelection) tableSelection).getFirstElement();
//				if (obj instanceof ChangeInfo) {
////					IAttributeContainer item = (IAttributeContainer) obj;
//					GerritUi.Ftracer.traceInfo("Selected table OBJECT selection ID: " //$NON-NLS-1$
//							+ ((ChangeInfo) obj).getChange_id() + "\t subject: " //$NON-NLS-1$
//							+ ((ChangeInfo) obj).getSubject());
////
////							+ item.getAttribute(GerritTask.SHORT_CHANGE_ID) + "\t subject: " //$NON-NLS-1$
////							+ item.getAttribute(GerritTask.SUBJECT));
//				}
//			}
//		}
//	}
}
