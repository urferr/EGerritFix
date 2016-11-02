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

import org.eclipse.compare.structuremergeviewer.DiffTreeViewer;
import org.eclipse.egerrit.internal.core.GerritClient;
import org.eclipse.egerrit.internal.ui.table.model.ITableModel;
import org.eclipse.egerrit.internal.ui.utils.Messages;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

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
					HandleFileSelection handleSelection = new HandleFileSelection(client, viewer);
					handleSelection.showFileSelection();
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
		}
	}
}