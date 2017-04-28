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

import java.util.concurrent.CompletableFuture;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.property.Properties;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.egerrit.internal.core.GerritClient;
import org.eclipse.egerrit.internal.model.ChangeInfo;
import org.eclipse.egerrit.internal.model.FileInfo;
import org.eclipse.egerrit.internal.model.ModelPackage;
import org.eclipse.egerrit.internal.model.RevisionInfo;
import org.eclipse.egerrit.internal.model.impl.StringToFileInfoImpl;
import org.eclipse.egerrit.internal.process.OpenCompareProcess;
import org.eclipse.egerrit.internal.ui.editors.ModelLoader;
import org.eclipse.egerrit.internal.ui.editors.QueryHelpers;
import org.eclipse.egerrit.internal.ui.table.filter.CommentsFilter;
import org.eclipse.egerrit.internal.ui.table.filter.CommitMsgFileFilter;
import org.eclipse.egerrit.internal.ui.table.filter.DeletedFilesFilter;
import org.eclipse.egerrit.internal.ui.table.filter.ReviewedFilesFilter;
import org.eclipse.egerrit.internal.ui.table.model.FilesTableModel;
import org.eclipse.egerrit.internal.ui.table.model.ITableModel;
import org.eclipse.egerrit.internal.ui.table.model.ReviewTableSorter;
import org.eclipse.egerrit.internal.ui.table.provider.DynamicMenuBuilder;
import org.eclipse.egerrit.internal.ui.table.provider.FileTableLabelProvider;
import org.eclipse.egerrit.internal.ui.table.provider.HandleFileSelection;
import org.eclipse.egerrit.internal.ui.utils.PersistentStorage;
import org.eclipse.emf.databinding.EMFObservables;
import org.eclipse.emf.databinding.EMFProperties;
import org.eclipse.emf.databinding.FeaturePath;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
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
	private boolean popupEnabled = true;

	private boolean filterDeletedFiles = false;

	private boolean filterCommitMsgFile = false;

	private boolean filterReviewedFile = false;

	private boolean filterCommentedFile = false;

	public static final String FILES_TABLE = "filesTable"; //$NON-NLS-1$

	private static final int TABLE_STYLE = SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION;

	private TableViewer fViewer = null;

	private GerritClient fGerritClient;

	private ChangeInfo fChangeInfo;

	private ModelLoader loader;

	private DynamicMenuBuilder dynamicMenu = new DynamicMenuBuilder();

	private DeletedFilesFilter fileDeleteFilter = new DeletedFilesFilter();

	private CommitMsgFileFilter commitFileFilter = new CommitMsgFileFilter();

	private ReviewedFilesFilter reviewedFileFilter = new ReviewedFilesFilter();

	private CommentsFilter commentedFileFilter = new CommentsFilter();

	private ViewerFilter searchFilter = null;

	private String searchString = ""; //$NON-NLS-1$

	private RevisionInfo fRevisionInfo;

	private String storageSectionName;

	private PersistentStorage persistStorage;

	public UIFilesTable(GerritClient gerritClient, ChangeInfo changeInfo, String name) {
		this.fGerritClient = gerritClient;
		this.fChangeInfo = changeInfo;
		this.storageSectionName = name;
		this.loader = ModelLoader.initialize(gerritClient, changeInfo);
		loader.loadCurrentRevision();
	}

	public UIFilesTable(GerritClient gerritClient, RevisionInfo revisionInfo, String name) {
		this.fGerritClient = gerritClient;
		this.fRevisionInfo = revisionInfo;
		this.storageSectionName = name;
	}

	/**
	 * Create the files table viewer
	 *
	 * @param aParent
	 *            Composite
	 * @param popup
	 *            boolean weather the table is within a pop-up dialog or not
	 * @return TableViewer
	 */
	public TableViewer createTableViewerSection(Composite aParent) {
		// Create the table viewer to maintain the list of review files
		fViewer = new TableViewer(aParent, TABLE_STYLE | SWT.MULTI | SWT.BORDER);
		buildAndLayoutTable();
		adjustTableData();//Set the properties for the files table

		// Set the content sorter
		ReviewTableSorter.bind(fViewer);
		fViewer.setComparator(new ReviewTableSorter(2));
		persistStorage = new PersistentStorage(fViewer, storageSectionName);
		persistStorage.restoreDialogSettings();
		return fViewer;
	}

	/**
	 * Allow setting for a dialog table
	 */
	public void enablePopup(boolean value) {
		popupEnabled = value;
	}

	/**
	 * Add decision weather to set the delete filter or not on the files table
	 *
	 * @param value
	 */
	public void enableDeletedFilesFilter(boolean value) {
		filterDeletedFiles = value;
		if (fViewer != null) {
			if (filterDeletedFiles) {
				fViewer.addFilter(fileDeleteFilter);
			} else {
				fViewer.removeFilter(fileDeleteFilter);
			}
		}
	}

	/**
	 * Add decision weather to set the COMMIT_MSG filter or not on the files table
	 *
	 * @param value
	 */
	public void enableCommitMsgFilter(boolean value) {
		filterCommitMsgFile = value;
		if (fViewer != null) {
			if (filterCommitMsgFile) {
				fViewer.addFilter(commitFileFilter);
			} else {
				fViewer.removeFilter(commitFileFilter);
			}
		}
	}

	/**
	 * Add decision weather to set the Reviewed file filter or not on the files table
	 *
	 * @param value
	 */
	public void enableReviewedFilesFilter(boolean value) {
		filterReviewedFile = value;
		if (fViewer != null) {
			if (filterReviewedFile) {
				fViewer.addFilter(reviewedFileFilter);
			} else {
				fViewer.removeFilter(reviewedFileFilter);
			}
		}
	}

	/**
	 * Add decision weather to set the commented file filter or not on the files table
	 *
	 * @param value
	 */
	public void enableCommentedFilesFilter(boolean value) {
		filterCommentedFile = value;
		if (fViewer != null) {
			if (filterCommentedFile) {
				fViewer.addFilter(commentedFileFilter);
			} else {
				fViewer.removeFilter(commentedFileFilter);
			}
		}
	}

	/**
	 * @param fileString
	 */
	public void filterFileText(String fileString) {
		searchString = fileString.toLowerCase();
		if (fileString.isEmpty()) {
			if (searchFilter != null) {
				fViewer.removeFilter(searchFilter);
				searchFilter = null;
			}
		} else {
			if (searchFilter == null) {
				searchFilter = createSearchFilter();
			}
			fViewer.addFilter(searchFilter);
			//Select the first entry in the table if not empty
			if (fViewer.getTable().getItemCount() >= 1) {
				fViewer.getTable().select(0);
			}
		}
	}

	public ViewerFilter getSearchingFilter() {
		return searchFilter;
	}

	/**
	 * Create a search filter containing a specified string
	 *
	 * @return ViewerFilter
	 */
	private ViewerFilter createSearchFilter() {
		searchFilter = new ViewerFilter() {
			@Override
			public boolean select(Viewer viewer, Object parentElement, Object element) {
				String file = ((StringToFileInfoImpl) element).getKey().toLowerCase();
				if (file.contains(searchString)) {
					return true;
				}
				return false;
			}
		};
		return searchFilter;
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
		ITableModel[] tableInfo = FilesTableModel.values();
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

		//Have the pull-down menu adjusted for the files dialog
		dynamicMenu.addPulldownMenu(fViewer, fGerritClient, popupEnabled);

		//Adjust the filters
		enableDeletedFilesFilter(filterDeletedFiles);
		enableCommitMsgFilter(filterCommitMsgFile);
		enableReviewedFilesFilter(filterReviewedFile);
		enableCommentedFilesFilter(filterCommentedFile);
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
		column.addControlListener(new ControlListener() {

			@Override
			public void controlResized(ControlEvent e) {
				persistStorage.storeDialogSettings();
			}

			@Override
			public void controlMoved(ControlEvent e) {
			}
		});

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

		IDoubleClickListener doubleClickListener = event -> {
			if (!popupEnabled) {
				HandleFileSelection handleSelection = new HandleFileSelection(fGerritClient, fViewer);
				handleSelection.showFileSelection();
			} else {
				IStructuredSelection sel = (IStructuredSelection) event.getSelection();
				Object element = sel.getFirstElement();
				if (element instanceof StringToFileInfoImpl) {
					FileInfo selectedFile = ((StringToFileInfoImpl) element).getValue();
					OpenCompareProcess openCompare = new OpenCompareProcess();
					openCompare.handleOpenCompare(fViewer.getTable().getShell(), fGerritClient, fChangeInfo,
							selectedFile, fChangeInfo.getUserSelectedRevision());
				}
			}
		};

		fViewer.addDoubleClickListener(doubleClickListener);
		if (!fGerritClient.getRepository().getServerInfo().isAnonymous()) {
			fViewer.getTable().addMouseListener(toggleReviewedStateListener());
		}

		//Set the binding for this section
		filesTabDataBindings();
	}

	/**
	 * Select the first element in the table
	 */
	public void setDialogSelection() {
		fViewer.getTable().select(0);
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
		CompletableFuture.runAsync(() -> {
			if (fileInfo.isReviewed()) {
				QueryHelpers.markAsNotReviewed(fGerritClient, fileInfo);
			} else {
				QueryHelpers.markAsReviewed(fGerritClient, fileInfo);
			}
		});
	}

	private void filesTabDataBindings() {
		//Set the FilesViewer
		if (fViewer != null) {
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

			IObservableList revisionFiles = null;
			if (fChangeInfo != null) {
				FeaturePath changerev = FeaturePath.fromList(ModelPackage.Literals.CHANGE_INFO__USER_SELECTED_REVISION,
						ModelPackage.Literals.REVISION_INFO__FILES);
				revisionFiles = EMFProperties.list(changerev).observe(fChangeInfo);
			} else {
				revisionFiles = EMFObservables.observeList(fRevisionInfo, ModelPackage.Literals.REVISION_INFO__FILES);
			}
			fViewer.setInput(revisionFiles);
		}
	}

	public void dispose() {
		loader.dispose();
	}

	public IDialogSettings getDialogSettings() {
		if (persistStorage == null) {
			persistStorage = new PersistentStorage(fViewer, storageSectionName);
		}
		return persistStorage.getDialogSettings(storageSectionName);
	}

}
