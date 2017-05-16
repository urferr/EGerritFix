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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.compare.structuremergeviewer.DiffTreeViewer;
import org.eclipse.compare.structuremergeviewer.IDiffElement;
import org.eclipse.egerrit.internal.core.GerritClient;
import org.eclipse.egerrit.internal.model.FileInfo;
import org.eclipse.egerrit.internal.model.impl.StringToFileInfoImpl;
import org.eclipse.egerrit.internal.ui.compare.GerritDiffNode;
import org.eclipse.egerrit.internal.ui.editors.ClearReviewedFlag;
import org.eclipse.egerrit.internal.ui.table.model.ITableModel;
import org.eclipse.egerrit.internal.ui.utils.Messages;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.TableItem;

public class DynamicMenuBuilder {
	private Menu commonMenu;

	private HashMap<ITableModel, TreeViewerColumn> treeViewerColumn = new HashMap<>();

	private FileTableLabelProvider tableLabelProvider;

	public HashMap<ITableModel, TreeViewerColumn> getTreeViewerColumn() {
		return treeViewerColumn;
	}

	public DynamicMenuBuilder() {
	}

	public void addPulldownMenu(ColumnViewer viewer, GerritClient client, boolean available) {
		MenuManager menuManager = new MenuManager();
		if (viewer instanceof TableViewer) {
			commonMenu = menuManager.createContextMenu(((TableViewer) viewer).getTable());
			((TableViewer) viewer).getTable().setMenu(commonMenu);
		} else if (viewer instanceof DiffTreeViewer) {
			commonMenu = menuManager.createContextMenu(((DiffTreeViewer) viewer).getControl());
			((DiffTreeViewer) viewer).getControl().setMenu(commonMenu);
		}

		menuManager.addMenuListener(manager -> addMenuItem(commonMenu, viewer, client, available));
		menuManager.update(true);
	}

	/**
	 * Select the menu item according to the available flag
	 *
	 * @param menu
	 * @param viewer
	 * @param client
	 * @param available
	 */
	private void addMenuItem(Menu menu, ColumnViewer viewer, GerritClient client, boolean available) {
		if (menu.getItemCount() == 0) {

			if (available) {
				//Menu item: Open Workspace file
				openFileMenuItem(menu, viewer, client);
			}

			//Menu item: Remove all Check Marks
			removeCheckMarkMenuItem(menu, viewer, client);

			if (available) {
				//Menu item: Separator
				new MenuItem(menu, SWT.SEPARATOR);

				//Menu item: Show File name first
				showFileNameFirstMenuItem(menu, viewer);
			}
		}
	}

	/**
	 * @param menu
	 * @param viewer
	 */
	private void showFileNameFirstMenuItem(Menu menu, ColumnViewer viewer) {
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
	}

	/**
	 * @param menu
	 * @param viewer
	 * @param client
	 */
	private void removeCheckMarkMenuItem(Menu menu, ColumnViewer viewer, GerritClient client) {
		final MenuItem removeCheckMark = new MenuItem(menu, SWT.PUSH);
		removeCheckMark.setText(Messages.UIFilesTable_clearReviewedFlag);
		removeCheckMark.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				List<FileInfo> listFiles = new ArrayList<>();
				if (viewer instanceof TableViewer) {
					TableItem[] tableItems = ((TableViewer) viewer).getTable().getItems();
					for (TableItem tableItem : tableItems) {
						listFiles.add(((StringToFileInfoImpl) tableItem.getData()).getValue());
					}
				} else {
					//Looking at the tree table in the upper section of the compare editor
					Object obj = viewer.getInput();
					if (obj instanceof GerritDiffNode) {
						GerritDiffNode diffNode = (GerritDiffNode) obj;
						IDiffElement[] children = diffNode.getChildren();
						for (IDiffElement element : children) {
							listFiles.add(((GerritDiffNode) element).getFileInfo());
						}
					}
				}
				ClearReviewedFlag reviewCheck = new ClearReviewedFlag(client, listFiles);
				reviewCheck.removeCheckMark();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
	}

	/**
	 * @param menu
	 * @param viewer
	 * @param client
	 */
	private void openFileMenuItem(Menu menu, ColumnViewer viewer, GerritClient client) {
		final MenuItem openFile = new MenuItem(menu, SWT.PUSH);
		openFile.setText(Messages.UIFilesTable_0);
		if (viewer instanceof TableViewer) {
			tableLabelProvider = (FileTableLabelProvider) viewer.getLabelProvider();
			openFile.setSelection(tableLabelProvider.getFileOrder());
		}

		openFile.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				HandleFileSelection handleSelection = new HandleFileSelection(client, viewer);//Open the workspace file from the context menu
				handleSelection.showFileSelection();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
	}
}