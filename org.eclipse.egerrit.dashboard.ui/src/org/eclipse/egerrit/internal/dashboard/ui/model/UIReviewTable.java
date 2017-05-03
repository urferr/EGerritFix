/*******************************************************************************
 * Copyright (c) 2013-2016 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jacques Bouthillier - Initial Implementation of the table view
 ******************************************************************************/
package org.eclipse.egerrit.internal.dashboard.ui.model;

import java.util.Arrays;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.egerrit.internal.dashboard.ui.GerritUi;
import org.eclipse.egerrit.internal.dashboard.ui.commands.table.AdjustMyStarredHandler;
import org.eclipse.egerrit.internal.dashboard.ui.utils.UIUtils;
import org.eclipse.egerrit.internal.dashboard.ui.views.GerritTableView;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider.ColorProvider;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolTip;
import org.eclipse.ui.PlatformUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class handles the creation of the table widget shown in the dashboard.
 */
public class UIReviewTable {
	private static final String DASHBOARD_CONTEXT_MENU = "org.eclipse.egerrit.dashboard.contextMenu"; //$NON-NLS-1$

	private static final String EGERRIT_DASHBOARD = "egerrit.dashboard"; //$NON-NLS-1$

	private static final String VIEW_COLUMN_ORDER = "egerritViewColumnOrder"; //$NON-NLS-1$

	private static final String VIEW_COLUMN_WIDTH = "egerritViewColumnWidth"; //$NON-NLS-1$

	private static Logger logger = LoggerFactory.getLogger(UIReviewTable.class);

	private static final int TABLE_STYLE = SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION;

	private TableViewer fViewer;

	private ComposedAdapterFactory adapterFactory;

	private ColorProvider labelProvider;

	private AdapterFactoryContentProvider contentProvider;

	private int defaultColumn = ReviewTableDefinition.values().length;

	private String[] voteColumns;

	public Composite createTableViewerSection(Composite aParent, String[] voteColumns) {
		this.voteColumns = voteColumns;
		Composite viewerForm = new Composite(aParent, SWT.BORDER | SWT.SHADOW_ETCHED_IN);
		viewerForm.setLayout(new FillLayout());

		// Create the table viewer to maintain the list of reviews
		fViewer = new TableViewer(viewerForm, TABLE_STYLE);
		fViewer = buildAndLayoutTable(fViewer);
		fViewer.getTable().addDisposeListener(e -> storeColumnsSettings());

		// Add a Key event and mouse down listener
		fViewer.getTable().addListener(SWT.MouseDown, mouseButtonListener);

		adapterFactory = new ComposedAdapterFactory();
		adapterFactory.addAdapterFactory(new ModifiedModelItemProviderAdapterFactory(voteColumns));

		contentProvider = new AdapterFactoryContentProvider(adapterFactory);
		fViewer.setContentProvider(contentProvider);

		if (voteColumns != null) {
			setLabelProvider(voteColumns);
		}

		return viewerForm;
	}

	/**
	 * Create the label provider after we defined the dynamic columns
	 *
	 * @param voteColumns
	 */
	private void setLabelProvider(String[] voteColumns) {
		for (String column : voteColumns) {
			createTableViewerLabelColumn(getAcronymLabel(column));
		}

		ReviewTableSorter.bind(fViewer);
		fViewer.setComparator(new ReviewTableSorter(7)); // sort by Updated, descending

		labelProvider = new AdapterFactoryLabelProvider.ColorProvider(adapterFactory, null, null);
		fViewer.setLabelProvider(labelProvider);
		restoreColumnsSettings();
	}

	private String getAcronymLabel(String label) {
		String ret = ""; //$NON-NLS-1$
		if (!label.isEmpty()) {
			String[] arraySt = label.split("-"); //$NON-NLS-1$
			for (String s : arraySt) {
				ret = ret.concat(s.substring(0, 1));
			}
		}
		return ret;
	}

	/**
	 * Create each column for the List of Reviews
	 *
	 * @param aParent
	 * @param aViewer
	 */
	private TableViewer buildAndLayoutTable(final TableViewer aViewer) {
		final Table table = aViewer.getTable();

		//Get the review table definition
		ReviewTableDefinition[] tableInfo = ReviewTableDefinition.values();
		int size = tableInfo.length;
		logger.debug("Table	Name	Width	Resize Moveable"); //$NON-NLS-1$
		for (int index = 0; index < size; index++) {
			logger.debug("index [ " + index + " ] " + tableInfo[index].getName() + "\t: " //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
					+ tableInfo[index].getWidth() + "\t: " + tableInfo[index].getResize() + "\t: " //$NON-NLS-1$ //$NON-NLS-2$
					+ tableInfo[index].getMoveable());
			createTableViewerColumn(tableInfo[index]);
		}

		TableLayout tableLayout = new TableLayout();
		table.setLayout(tableLayout);

		String os = org.eclipse.core.runtime.Platform.getOS();
		// nothing to do on windows
		if (!Platform.OS_WIN32.equals(os)) {
			final Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
			final ToolTip tip = new ToolTip(shell, SWT.ICON_INFORMATION);

			table.addListener(SWT.MouseMove, event -> tip.setVisible(false));

			table.addListener(SWT.MouseHover, event -> {
				ViewerCell viewerCell = UIUtils.getAdjustedViewerCell(event, aViewer);
				if (viewerCell != null) {
					int columnIndex = viewerCell.getColumnIndex();
					TableItem item = (TableItem) viewerCell.getViewerRow().getItem();
					Rectangle rect = item.getBounds(columnIndex);
					tip.setMessage(item.getText(columnIndex));
					Point displayPos = item.getParent().toDisplay(rect.x, rect.y);
					tip.setLocation(displayPos.x, displayPos.y);
					tip.setVisible(true);
				}
			});
		}
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		MenuManager menuManager = new MenuManager();
		Menu contextMenu = menuManager.createContextMenu(table);
		table.setMenu(contextMenu);
		GerritTableView.getActiveView(true).getSite().registerContextMenu(DASHBOARD_CONTEXT_MENU, menuManager, aViewer);
		return aViewer;
	}

	/**
	 * Create each column in the review table list
	 *
	 * @param ReviewTableDefinition
	 * @return TableViewerColumn
	 */
	private TableViewerColumn createTableViewerColumn(ReviewTableDefinition aTableInfo) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(fViewer, SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(aTableInfo.getName());
		column.setWidth(aTableInfo.getWidth());
		column.setAlignment(aTableInfo.getAlignment());
		column.setResizable(aTableInfo.getResize());
		column.setMoveable(aTableInfo.getMoveable());
		return viewerColumn;
	}

	private final Listener mouseButtonListener = aEvent -> {
		logger.debug("mouseButtonListener() for " + aEvent.button); //$NON-NLS-1$
		switch (aEvent.type) {
		case SWT.MouseDown:
			// Left Click, Selection over the STAR column only will activate the request to modify it
			if (aEvent.button == 1) {
				Point p = new Point(aEvent.x, aEvent.y);
				ViewerCell viewerCell = fViewer.getCell(p);
				if (viewerCell != null && viewerCell.getColumnIndex() == ReviewTableDefinition.STARRED.ordinal()) {

					// Execute the command to adjust the column: ID with the
					// starred information
					AdjustMyStarredHandler handler = new AdjustMyStarredHandler();
					try {
						handler.execute(new ExecutionEvent());
					} catch (ExecutionException excutionException) {
						logger.error(excutionException.getMessage());
					}
				}
			}

			// For now, button 2 not used
			if (aEvent.button == 2) {

			}

			// Right Click
			if (aEvent.button == 3) {
				// Process the Item table handling
				// processItemSelection()
			}
			break;
		default:
			break;
		}

	};

	public TableViewer getViewer() {
		return fViewer;
	}

	public void dispose() {
		if (labelProvider != null) {
			labelProvider.dispose();
		}
		if (contentProvider != null) {
			contentProvider.dispose();
		}
		if (adapterFactory != null) {
			adapterFactory.dispose();
		}
	}

	private void storeColumnsSettings() {
		if (fViewer == null) {
			return;
		}
		int[] columnOrder = fViewer.getTable().getColumnOrder();
		//Use only the store when there is more than the default columns
		//This is to allow the dynamic column position
		if (columnOrder.length == ReviewTableDefinition.values().length) {
			return;
		}

		int colInTable = fViewer.getTable().getColumnCount();
		int[] columnWidth = new int[colInTable];
		for (int i = 0; i < colInTable; i++) {
			columnWidth[i] = fViewer.getTable().getColumn(i).getWidth();
		}

		getDialogSettings().put(VIEW_COLUMN_ORDER,
				Arrays.stream(columnOrder).mapToObj(i -> String.valueOf(i)).toArray(String[]::new));
		getDialogSettings().put(VIEW_COLUMN_WIDTH,
				Arrays.stream(columnWidth).mapToObj(i -> String.valueOf(i)).toArray(String[]::new));
	}

	private void restoreColumnsSettings() {
		if (fViewer == null) {
			return;
		}
		String[] backedUpValue = getDialogSettings().getArray(VIEW_COLUMN_ORDER);
		if (backedUpValue == null) {
			return;
		}
		int[] columnOrderStored = Arrays.stream(backedUpValue).mapToInt(Integer::parseInt).toArray();
		int columInTable = fViewer.getTable().getColumnCount();
		//Keep default column position until we have data to display in the dashboard
		if (columInTable == ReviewTableDefinition.values().length) {
			return;
		}
		//Keep an array of the last saved value concerning the columns width in the dashboard
		int[] oldvalue = new int[columInTable];
		for (int i = 0; i < columInTable; i++) {
			int width = fViewer.getTable().getColumn(i).getWidth();
			oldvalue[i] = width;
		}

		//Lets deal with the columns order
		//Initialize variables
		int lastStoredColumnSize = columnOrderStored.length;
		int[] nextTableColumn = new int[columInTable];
		if (columInTable < columnOrderStored.length) {
			//More stored columns than what we need now, let reduce the array
			System.arraycopy(columnOrderStored, 0, nextTableColumn, 0, columInTable);
		} else {
			//Table have more columns than the storage
			System.arraycopy(columnOrderStored, 0, nextTableColumn, 0, columnOrderStored.length);
			//Get the extra column
			//Initialize the columns order when there are more columns in the table than what has been stored previously
			for (int i = lastStoredColumnSize; i < nextTableColumn.length; i++) {
				nextTableColumn[i] = i; //init the new dynamic columns at the end of the table
			}
		}

		//Validate the column , it should be between 0 to number of columns
		int nextColumSize = nextTableColumn.length;
		int numberColumnToMove = 0;
		for (int i = 0; i < nextColumSize; i++) {
			if (nextTableColumn[i] >= nextColumSize) {
				numberColumnToMove++;
				//Move by one slot the array
				System.arraycopy(columnOrderStored, i + numberColumnToMove, nextTableColumn, i, nextColumSize - i);
				//need to maintain the counter for next loop if the new number has to move as well
				i--;
			}
		}

		fViewer.getTable().setColumnOrder(nextTableColumn);

		restoreColumnWidth(columnOrderStored, columInTable, oldvalue, lastStoredColumnSize, nextTableColumn);

	}

	/**
	 * @param columnOrderStored
	 * @param columInTable
	 * @param oldvalue
	 * @param lastStoredColumnSize
	 * @param nextTableColumn
	 */
	private void restoreColumnWidth(int[] columnOrderStored, int columInTable, int[] oldvalue, int lastStoredColumnSize,
			int[] nextTableColumn) {
		String[] backedUpValue;
		//Lets deal now with the columns width
		backedUpValue = getDialogSettings().getArray(VIEW_COLUMN_WIDTH);
		if (backedUpValue == null) {
			return;
		}
		int[] columnWidth = Arrays.stream(backedUpValue).mapToInt(Integer::parseInt).toArray();
		int totalSize = 0;
		if (columInTable < columnWidth.length) {
			//More store column than what we need now, let reduce the array
			System.arraycopy(columnWidth, 0, nextTableColumn, 0, columInTable);
		} else {
			//Table have more columns than the storage
			System.arraycopy(columnWidth, 0, nextTableColumn, 0, columnWidth.length);
			//Add the extra columns width from the table data
			for (int i = columnWidth.length; i < nextTableColumn.length; i++) {
				nextTableColumn[i] = oldvalue[i];
			}
		}
		for (int i = 0; i < nextTableColumn.length; i++) {
			totalSize = totalSize + nextTableColumn[i];
			fViewer.getTable().getColumn(i).setWidth(nextTableColumn[i]);
		}

		//Reset if total size is small number (50 or less )pixels.
		if (totalSize <= 50) {
			resetDefault();
		}

		//Read and adjust the last column defined before the dynamic labels
		//Needs to be reset to allow to see the dynamic columns in the client area
		lastStoredColumnSize--; //decrease by one to read the array
		if (lastStoredColumnSize < fViewer.getTable().getColumnCount()) {
			int defaultWidth = oldvalue[lastStoredColumnSize];//column before the new labels
			int testCol = columnOrderStored[lastStoredColumnSize];
			if (testCol < ReviewTableDefinition.values().length) {
				defaultWidth = ReviewTableDefinition.values()[testCol].getWidth();//column before the dynamic labels
			}
			fViewer.getTable().getColumn(testCol).setWidth(defaultWidth);
		}
	}

	/**
	 * Reset the column width and order, then save it for the next time
	 */
	public void resetDefault() {
		Table table = fViewer.getTable();
		ReviewTableDefinition[] tableInfo = ReviewTableDefinition.values();
		int defaultLength = tableInfo.length;
		int colInTable = table.getColumnCount();
		int[] newOrder = new int[colInTable];
		//Reset the column order
		for (int i = 0; i < colInTable; i++) {
			newOrder[i] = i;
		}
		table.setColumnOrder(newOrder);//Re-set the initial column order, plus the LABELS at the end
		//Use the default length defined for the table without the dynamic columns
		for (int i = 0; i < defaultLength; i++) {
			table.getColumn(i).setWidth(tableInfo[i].getWidth());
		}
		//For the dynamic columns, defined the minimum value
		for (int i = defaultLength; i < colInTable; i++) {
			table.getColumn(i).pack();
		}

		storeColumnsSettings();
	}

	private IDialogSettings getDialogSettings() {
		IDialogSettings settings = GerritUi.getDefault().getDialogSettings();
		IDialogSettings section = settings.getSection(EGERRIT_DASHBOARD);
		if (section == null) {
			section = settings.addNewSection(EGERRIT_DASHBOARD);
		}
		return section;
	}

	/**
	 * Create each column in the review table list
	 *
	 * @param ReviewTableDefinition
	 * @return TableViewerColumn
	 */
	private void createTableViewerLabelColumn(String label) {
		int width = 28 + 5 * label.length();
		final TableViewerColumn viewerColumn = new TableViewerColumn(fViewer, SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(label);
		column.setWidth(width);
		column.setAlignment(SWT.LEFT);
		column.setResizable(true);
		column.setMoveable(true);
	}

	/**
	 * Return the name for a dynamic column
	 *
	 * @param column
	 * @return
	 */
	public String getColumnLabel(int column) {
		int val = column - defaultColumn;//Adjust the dynamic column value
		if (val >= 0) {
			return voteColumns[val];
		}
		return ""; //$NON-NLS-1$
	}
}
