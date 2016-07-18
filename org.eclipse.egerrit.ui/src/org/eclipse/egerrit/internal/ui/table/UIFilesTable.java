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
package org.eclipse.egerrit.internal.ui.table;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.property.Properties;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.egerrit.internal.core.GerritClient;
import org.eclipse.egerrit.internal.model.ChangeInfo;
import org.eclipse.egerrit.internal.model.FileInfo;
import org.eclipse.egerrit.internal.model.ModelPackage;
import org.eclipse.egerrit.internal.model.impl.StringToFileInfoImpl;
import org.eclipse.egerrit.internal.ui.editors.ModelLoader;
import org.eclipse.egerrit.internal.ui.editors.OpenCompareEditor;
import org.eclipse.egerrit.internal.ui.editors.QueryHelpers;
import org.eclipse.egerrit.internal.ui.table.model.FilesTableModel;
import org.eclipse.egerrit.internal.ui.table.model.ITableModel;
import org.eclipse.egerrit.internal.ui.table.model.ReviewTableSorter;
import org.eclipse.egerrit.internal.ui.table.provider.DynamicMenuBuilder;
import org.eclipse.egerrit.internal.ui.table.provider.FileTableLabelProvider;
import org.eclipse.egerrit.internal.ui.utils.Messages;
import org.eclipse.egerrit.internal.ui.utils.UIUtils;
import org.eclipse.emf.databinding.EMFProperties;
import org.eclipse.emf.databinding.FeaturePath;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

/**
 * This class implements the files table view.
 *
 * @since 1.0
 */
public class UIFilesTable {

	public static final String FILES_TABLE = "filesTable"; //$NON-NLS-1$

	private final int TABLE_STYLE = (SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);

	private final String EDITOR_KEY = "fileEditortip"; //$NON-NLS-1$

	// ------------------------------------------------------------------------
	// Variables
	// ------------------------------------------------------------------------
	private TableViewer fViewer;

	private IDoubleClickListener fdoubleClickListener;

	private GerritClient fGerritClient;

	private ChangeInfo fChangeInfo;

	private ModelLoader loader;

	private DynamicMenuBuilder dynamicMenu = new DynamicMenuBuilder();

	public UIFilesTable(GerritClient gerritClient, ChangeInfo changeInfo) {
		this.fGerritClient = gerritClient;
		this.fChangeInfo = changeInfo;
		this.loader = ModelLoader.initialize(gerritClient, changeInfo);
		loader.loadCurrentRevision();
	}

	public TableViewer createTableViewerSection(Composite aParent) {
		// Create the table viewer to maintain the list of review files
		fViewer = new TableViewer(aParent, TABLE_STYLE | SWT.MULTI);
		buildAndLayoutTable();
		adjustTableData();//Set the properties for the files table

		// Set the content sorter
		ReviewTableSorter.bind(fViewer);
		fViewer.setComparator(new ReviewTableSorter(2));
		return fViewer;

	}

	/**
	 * Create each column for the files list *
	 *
	 * @param aParent
	 * @param aViewer
	 */
	private void buildAndLayoutTable() {
		final Table table = fViewer.getTable();

		//Get the review table definition
		final ITableModel[] tableInfo = FilesTableModel.values();
		int size = tableInfo.length;
		for (int index = 0; index < size; index++) {
			createTableViewerColumn(tableInfo[index]);
		}
		GridData gribData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gribData.minimumWidth = tableInfo[0].getWidth();
		fViewer.getTable().setLayoutData(gribData);
		TableLayout tableLayout = new TableLayout();
		table.setLayout(tableLayout);

		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setData(FILES_TABLE);

		dynamicMenu.addPulldownMenu(fViewer, fGerritClient);
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

	/**
	 * @return TableViewer
	 */
	public TableViewer getViewer() {
		return fViewer;
	}

	private void adjustTableData() {
		fViewer.getTable().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 10, 1));
		fdoubleClickListener = new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				IStructuredSelection sel = (IStructuredSelection) event.getSelection();
				Object element = sel.getFirstElement();
				if (element instanceof StringToFileInfoImpl) {
					FileInfo selectedFile = ((StringToFileInfoImpl) element).getValue();
					OpenCompareEditor compareEditor;
					if (!fGerritClient.getRepository().getServerInfo().isAnonymous()) {
						UIUtils.showDialogTip(EDITOR_KEY, fViewer.getControl().getShell(), Messages.EGerriTip,
								Messages.FileTabView_EGerriTipValue);

					}
					compareEditor = new OpenCompareEditor(fGerritClient, fChangeInfo);

					String left = "BASE"; //$NON-NLS-1$
					String right = fChangeInfo.getUserSelectedRevision().getId();
					compareEditor.compareFiles(left, right, selectedFile);
				}
			}
		};

		fViewer.addDoubleClickListener(fdoubleClickListener);
		if (!fGerritClient.getRepository().getServerInfo().isAnonymous()) {
			fViewer.getTable().addMouseListener(toggleReviewedStateListener());
		}

		//Set the binding for this section
		filesTabDataBindings();
	}

	/**
	 * This method is for the command double click on a row
	 *
	 * @return none
	 */
	public void selectRow() {
		if (fViewer.getElementAt(0) != null) {
			fViewer.setSelection(new StructuredSelection(fViewer.getElementAt(0)), true);
			IStructuredSelection selection = (IStructuredSelection) fViewer.getSelection();
			final DoubleClickEvent event = new DoubleClickEvent(fViewer, selection);
			fdoubleClickListener.doubleClick(event);
		}
	}

	/**
	 * This method is the listener to toggle a file's reviewed state
	 *
	 * @return MouseAdapter
	 */
	private MouseAdapter toggleReviewedStateListener() {
		return new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				ViewerCell viewerCell = fViewer.getCell(new Point(e.x, e.y));
				if (viewerCell != null && viewerCell.getColumnIndex() == 0) {
					//Selected the first column, so we can send the delete option
					//Otherwise, do not delete
					ISelection selection = fViewer.getSelection();
					if (selection instanceof IStructuredSelection) {

						IStructuredSelection structuredSelection = (IStructuredSelection) selection;

						Object element = structuredSelection.getFirstElement();

						FileInfo fileInfo = ((StringToFileInfoImpl) element).getValue();
						toggleReviewed(fileInfo);
					}
				}
			}
		};
	}

	private void toggleReviewed(FileInfo fileInfo) {
		if (fileInfo.isReviewed()) {
			QueryHelpers.markAsNotReviewed(fGerritClient, fileInfo);
		} else {
			QueryHelpers.markAsReviewed(fGerritClient, fileInfo);
		}
		fViewer.refresh();
	}

	protected void filesTabDataBindings() {
		//Set the FilesViewer
		if (fViewer != null) {
			FeaturePath changerev = FeaturePath.fromList(ModelPackage.Literals.CHANGE_INFO__USER_SELECTED_REVISION,
					ModelPackage.Literals.REVISION_INFO__FILES);

			IObservableList revisionsChanges = EMFProperties.list(changerev).observe(fChangeInfo);

			final FeaturePath reviewed = FeaturePath.fromList(ModelPackage.Literals.STRING_TO_FILE_INFO__VALUE,
					ModelPackage.Literals.FILE_INFO__REVIEWED);
			final FeaturePath commentsCount = FeaturePath.fromList(ModelPackage.Literals.STRING_TO_FILE_INFO__VALUE,
					ModelPackage.Literals.FILE_INFO__COMMENTS_COUNT);
			final FeaturePath draftCount = FeaturePath.fromList(ModelPackage.Literals.STRING_TO_FILE_INFO__VALUE,
					ModelPackage.Literals.FILE_INFO__DRAFTS_COUNT);
			ObservableListContentProvider contentProvider = new ObservableListContentProvider();
			fViewer.setContentProvider(contentProvider);

			final IObservableMap[] watchedProperties = Properties.observeEach(contentProvider.getKnownElements(),
					new IValueProperty[] { EMFProperties.value(reviewed), EMFProperties.value(commentsCount),
							EMFProperties.value(draftCount) });
			fViewer.setLabelProvider(new FileTableLabelProvider(watchedProperties));
			fViewer.setInput(revisionsChanges);
		}
	}

	public void dispose() {
		loader.dispose();
	}

}
