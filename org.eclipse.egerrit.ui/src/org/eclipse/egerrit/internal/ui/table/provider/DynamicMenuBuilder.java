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

package org.eclipse.egerrit.internal.ui.table.provider;

import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.compare.structuremergeviewer.DiffTreeViewer;
import org.eclipse.egerrit.internal.core.GerritClient;
import org.eclipse.egerrit.internal.model.FileInfo;
import org.eclipse.egerrit.internal.model.impl.StringToFileInfoImpl;
import org.eclipse.egerrit.internal.ui.compare.GerritDiffNode;
import org.eclipse.egerrit.internal.ui.table.model.FilesTableModel;
import org.eclipse.egerrit.internal.ui.table.model.ITableModel;
import org.eclipse.egerrit.internal.ui.utils.Messages;
import org.eclipse.egerrit.internal.ui.utils.UIUtils;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.PlatformUI;

public class DynamicMenuBuilder {
	private Menu commonMenu;

	private HashMap<ITableModel, TreeViewerColumn> treeViewerColumn = new HashMap<ITableModel, TreeViewerColumn>();

	private FileTableLabelProvider tableLabelProvider;

	public HashMap<ITableModel, TreeViewerColumn> getTreeViewerColumn() {
		return treeViewerColumn;
	}

	public DynamicMenuBuilder() {
	}

	public void addPulldownMenu(ColumnViewer viewer, GerritClient client) {
		MenuManager menuManager = new MenuManager();
		if (viewer instanceof TableViewer) {
			commonMenu = menuManager.createContextMenu(((TableViewer) viewer).getTable());
			((TableViewer) viewer).getTable().setMenu(commonMenu);
		} else if (viewer instanceof DiffTreeViewer) {
			commonMenu = menuManager.createContextMenu(((DiffTreeViewer) viewer).getControl());
			((DiffTreeViewer) viewer).getControl().setMenu(commonMenu);
		}

		menuManager.addMenuListener(new IMenuListener() {
			@Override
			public void menuAboutToShow(IMenuManager manager) {
				addMenuItem(commonMenu, viewer, client);
			}
		});

		menuManager.update(true);
	}

	private void addMenuItem(Menu menu, ColumnViewer viewer, GerritClient client) {
		if (menu.getItemCount() == 0) {

			final MenuItem openFile = new MenuItem(menu, SWT.PUSH);
			openFile.setText(Messages.UIFilesTable_0);
			if (viewer instanceof TableViewer) {
				tableLabelProvider = (FileTableLabelProvider) viewer.getLabelProvider();
				openFile.setSelection(tableLabelProvider.getFileOrder());
			}

			openFile.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {

					ISelection selection = viewer.getSelection();
					if (selection instanceof IStructuredSelection) {

						IStructuredSelection structuredSelection = (IStructuredSelection) selection;
						Iterator itr = structuredSelection.iterator();
						String failedFiles = "";
						while (itr.hasNext()) {
							Object element = itr.next();
							if (element == null) {
								return;
							}
							FileInfo fileInfo;
							if (viewer instanceof TableViewer) {
								fileInfo = ((StringToFileInfoImpl) element).getValue();
							} else {
								fileInfo = ((GerritDiffNode) element).getFileInfo();
							}
							String status = fileInfo.getStatus();
							if (status.compareTo("D") != 0) { //$NON-NLS-1$
								if (viewer instanceof TableViewer) {
									if (UIUtils.openSingleFile(((StringToFileInfoImpl) element).getKey(), client,
											fileInfo.getRevision(), 0) == false) {
										failedFiles = failedFiles + fileInfo.getPath() + "\n"; //$NON-NLS-1$
									}
								} else {
									if (UIUtils.openSingleFile(((StringToFileInfoImpl) fileInfo.eContainer()).getKey(),
											client, fileInfo.getRevision(), 0) == false) {
										failedFiles = failedFiles + fileInfo.getPath() + "\n"; //$NON-NLS-1$
									}
								}
							}
						}
						if (!failedFiles.isEmpty()) {
							Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
							UIUtils.displayInformation(shell, Messages.UIFilesTable_2, failedFiles);
						}
					}
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
				}
			});

			final MenuItem nameFirst = new MenuItem(menu, SWT.CHECK);
			nameFirst.setText(Messages.UIFilesTable_1);
			if (viewer instanceof TableViewer) {
				nameFirst.setSelection(tableLabelProvider.getFileOrder());
			} else {

				nameFirst.setSelection(((FileInfoCompareCellLabelProvider) viewer.getLabelProvider()).getFileOrder());
			}
			nameFirst.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					MenuItem menuItem = (MenuItem) e.getSource();
					if (viewer instanceof TableViewer) {
						tableLabelProvider.setFileNameFirst(menuItem.getSelection());
					} else {
						((FileInfoCompareCellLabelProvider) viewer.getLabelProvider())
								.setFileNameFirst(menuItem.getSelection());
					}
					viewer.refresh();
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
				}
			});
			//Add Menu item to select which column to be visible
			addVisibleColumnSelection(menu, viewer);
		}
	}

	/**
	 * Create the menu item to allow selection on which column should we make visible
	 *
	 * @param menu
	 */
	private void addVisibleColumnSelection(Menu menu, ColumnViewer viewer) {
		new MenuItem(menu, SWT.SEPARATOR);
		final MenuItem visible = new MenuItem(menu, SWT.MENU);
		visible.setText("Visible column");
		final ITableModel[] tableInfo = FilesTableModel.values();
		for (ITableModel element : tableInfo) {
			final MenuItem menuItem = new MenuItem(menu, SWT.CHECK);
			if (element.getName().isEmpty()) {
				menuItem.setText("Column: " + ((FilesTableModel) element).ordinal());
			} else {
				menuItem.setText(element.getName());
			}
			menuItem.setData(element);
			menuItem.setSelection(((FilesTableModel) element).isColumnVisible());
			menuItem.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					MenuItem subMenuItem = (MenuItem) e.getSource();
					FilesTableModel tablemodel = (FilesTableModel) subMenuItem.getData();
					tablemodel.setColumnVisible(subMenuItem.getSelection());
					if (viewer instanceof TableViewer) {
						((TableViewer) viewer).getTable()
								.getColumn(tablemodel.ordinal())
								.setWidth(tablemodel.getWidth());
					} else if (viewer instanceof DiffTreeViewer) {
						TreeViewerColumn columnName = treeViewerColumn.get(tablemodel);
						if (columnName != null) {
							TreeColumn column = columnName.getColumn();
							column.setWidth(tablemodel.getWidth());
						}
					}
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
				}
			});
		}
	}
}