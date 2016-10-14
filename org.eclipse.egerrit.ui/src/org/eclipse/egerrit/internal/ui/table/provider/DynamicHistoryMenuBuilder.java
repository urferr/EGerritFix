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

import org.eclipse.egerrit.internal.core.GerritClient;
import org.eclipse.egerrit.internal.ui.table.filter.HistoryFileFilter;
import org.eclipse.egerrit.internal.ui.table.filter.HistoryResetFilter;
import org.eclipse.egerrit.internal.ui.utils.Messages;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

public class DynamicHistoryMenuBuilder {
	private Menu commonMenu;

	static protected HistoryFileFilter humanOnlyfilter;

	static protected HistoryFileFilter machineOnlyfilter;

	public void addPulldownMenu(ColumnViewer viewer, GerritClient client) {
		MenuManager menuManager = new MenuManager();
		if (viewer instanceof TableViewer) {
			commonMenu = menuManager.createContextMenu(((TableViewer) viewer).getTable());
			((TableViewer) viewer).getTable().setMenu(commonMenu);
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
			if (viewer instanceof TableViewer) {
				final MenuItem humanOnly = new MenuItem(menu, SWT.CHECK);
				final MenuItem machineOnly = new MenuItem(menu, SWT.CHECK);
				final MenuItem separator = new MenuItem(menu, SWT.SEPARATOR);
				final MenuItem reset = new MenuItem(menu, SWT.CHECK);
				setMenu(humanOnly, viewer, Messages.UIHistoryTable_0, new HistoryFileFilter(false));
				setMenu(machineOnly, viewer, Messages.UIHistoryTable_1, new HistoryFileFilter(true));
				setMenu(reset, viewer, Messages.UIHistoryTable_reset, new HistoryResetFilter());
			}
		}

	}

	private void setMenu(MenuItem menuItem, ColumnViewer viewer, String name, ViewerFilter filter) {

		menuItem.setText(name);
		menuItem.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				if (menuItem.getSelection()) {
					if (filter instanceof HistoryResetFilter) {
						//RESET filter
						viewer.addFilter(filter);
						Menu parent = menuItem.getParent();
						MenuItem[] items = parent.getItems();
						for (MenuItem mi : items) {
							mi.setSelection(false);
						}
						ViewerFilter[] filters = viewer.getFilters();
						for (ViewerFilter filter : filters) {
							viewer.removeFilter(filter);
						}
						viewer.refresh();
					} else {
						viewer.addFilter(filter);
					}
				} else {
					viewer.removeFilter(filter);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

	}
}