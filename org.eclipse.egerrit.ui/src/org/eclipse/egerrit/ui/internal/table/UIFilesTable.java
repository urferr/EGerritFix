/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jacques Bouthillier - Initial Implementation of the files table
 ******************************************************************************/
package org.eclipse.egerrit.ui.internal.table;

import org.eclipse.egerrit.ui.internal.table.model.FilesTableModel;
import org.eclipse.egerrit.ui.internal.table.model.ITableModel;
import org.eclipse.egerrit.ui.internal.utils.UIUtils;
import org.eclipse.jface.viewers.TableLayout;
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
 * This class implements the files table view.
 *
 * @since 1.0
 */
public class UIFilesTable {

	private final int TABLE_STYLE = (SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);

	// ------------------------------------------------------------------------
	// Variables
	// ------------------------------------------------------------------------
	private TableViewer fViewer;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	public UIFilesTable() {

	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	public TableViewer createTableViewerSection(Composite aParent, GridData layoutData) {
		// Create a form to maintain the search data
		Composite viewerForm = UIUtils.createsGeneralComposite(aParent, SWT.BORDER | SWT.SHADOW_ETCHED_IN);

		GridLayout layout = new GridLayout();
		layout.numColumns = FilesTableModel.values().length;
		layout.makeColumnsEqualWidth = false;
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		viewerForm.setLayout(layout);

		viewerForm.setLayoutData(layoutData);

		// Create the table viewer to maintain the list of reviews
		fViewer = new TableViewer(viewerForm, TABLE_STYLE);
		fViewer = buildAndLayoutTable(fViewer);

		// Set the content provider and the Label provider and the sorter
		//fViewer.setContentProvider(new FileTableContentProvider());

		// Set the viewer for the provider
//		ReviewTableLabelProvider tableProvider = new ReviewTableLabelProvider();
		//fViewer.setLabelProvider(new FileTableLabelProvider());
//		ReviewTableSorter.bind(fViewer);
//		fViewer.setComparator(new ReviewTableSorter(7)); // sort by Updated, descending

		// Create the help context id for the viewer's control
		// PlatformUI
		// .getWorkbench()
		// .getHelpSystem()
		// .setHelp(fViewer.getControl(),
		// "org.eclipse.egerrit.dashboard.ui.viewer");

		//

//		fViewer.addDoubleClickListener(new IDoubleClickListener() {
//
//			@Override
//			public void doubleClick(DoubleClickEvent event) {
//				IStructuredSelection sel = (IStructuredSelection) event.getSelection();
//
//				Object obj = fViewer.getTable().getData();
//				if (obj instanceof Object[]) {
//					Object[] ar = (Object[]) obj;
//					if (ar[0] instanceof FileInfo) {
//						FileInfo fileInfo = (FileInfo) ar[0];
//						Iterator<Map.Entry<String, RevisionInfo>> itr1 = fRevisions.entrySet().iterator();
//						while (itr1.hasNext()) {
//							Entry<String, RevisionInfo> entry = itr1.next();
//							String res = getContent(fGerritRepository, fChangeInfo.getChange_id(), entry.getKey(),
//									fileInfo.getOld_Path(), new NullProgressMonitor());
//						}
//
//					}
//
//				}
//
//			}
//		});

		fViewer.getTable().addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				fViewer.refresh();
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
	 * Create each column for the files list *
	 *
	 * @param aParent
	 * @param aViewer
	 */
	private TableViewer buildAndLayoutTable(final TableViewer aViewer) {
		final Table table = aViewer.getTable();

		//Get the review table definition
		final ITableModel[] tableInfo = FilesTableModel.values();
		int size = tableInfo.length;
//		GerritUi.Ftracer.traceInfo("Table	Name	Width	Resize Moveable"); //$NON-NLS-1$
		for (int index = 0; index < size; index++) {
//			GerritUi.Ftracer.traceInfo("index [ " + index + " ] " + tableInfo[index].getName() + "\t: " //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
//					+ tableInfo[index].getWidth() + "\t: " + tableInfo[index].getResize() + "\t: " //$NON-NLS-1$ //$NON-NLS-2$
//					+ tableInfo[index].getMoveable());
			TableViewerColumn col = createTableViewerColumn(tableInfo[index]);
		}
		GridData gribData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gribData.minimumWidth = tableInfo[0].getWidth();
		gribData.minimumHeight = 200;
		fViewer.getTable().setLayoutData(gribData);

		TableLayout tableLayout = new TableLayout();
		table.setLayout(tableLayout);

		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		return aViewer;
	}

	/**
	 * Create each column in the review table list
	 *
	 * @param FilesTableModel
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

	public TableViewer getViewer() {
		return fViewer;
	}
}
