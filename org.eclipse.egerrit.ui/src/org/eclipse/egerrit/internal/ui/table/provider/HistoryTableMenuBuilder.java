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
import org.eclipse.egerrit.internal.ui.table.filter.AuthorKindFilter;
import org.eclipse.egerrit.internal.ui.utils.Messages;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Menu;

public class HistoryTableMenuBuilder {
	private Menu commonMenu;

	private FilterSelectionAction humanOnlyfilter = null;

	private FilterSelectionAction machineOnlyfilter = null;

	public void addPulldownMenu(ColumnViewer viewer, GerritClient client) {
		MenuManager menuManager = new MenuManager();
		if (viewer instanceof TableViewer) {
			commonMenu = menuManager.createContextMenu(((TableViewer) viewer).getTable());
			((TableViewer) viewer).getTable().setMenu(commonMenu);
			humanOnlyfilter = new FilterSelectionAction(Messages.UIHistoryTable_0, viewer, new AuthorKindFilter(false));
			machineOnlyfilter = new FilterSelectionAction(Messages.UIHistoryTable_1, viewer,
					new AuthorKindFilter(true));
		}
		menuManager.addMenuListener(new IMenuListener() {
			@Override
			public void menuAboutToShow(IMenuManager manager) {
				addMenuItem(commonMenu, viewer, client);
			}
		});
		menuManager.update(true);
	}

	private class FilterSelectionAction extends Action {
		private ViewerFilter filterInstance;

		private StructuredViewer viewer;

		public FilterSelectionAction(String actionName, StructuredViewer viewer, AuthorKindFilter humanOnlyfilter) {
			super(actionName, AS_CHECK_BOX);
			this.viewer = viewer;
			this.filterInstance = humanOnlyfilter;
		}

		@Override
		public void run() {
			if (isChecked()) {
				viewer.addFilter(filterInstance);
			} else {
				viewer.removeFilter(filterInstance);
			}
		}
	}

	private void addMenuItem(Menu menu, ColumnViewer viewer, GerritClient client) {
		if (menu.getItemCount() == 0) {
			if (viewer instanceof TableViewer) {
				MenuManager menuMgr = new MenuManager();
				menuMgr.add(humanOnlyfilter);
				menuMgr.add(machineOnlyfilter);
				menuMgr.createContextMenu(menu.getShell()).setVisible(true);
				menuMgr.add(new Separator());
				menuMgr.add(new Action(Messages.UIHistoryTable_reset) {
					@Override
					public void run() {
						humanOnlyfilter.setChecked(false);
						humanOnlyfilter.run();
						machineOnlyfilter.setChecked(false);
						machineOnlyfilter.run();
					}
				});
			}
		}
	}
}