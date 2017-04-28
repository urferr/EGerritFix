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
import org.eclipse.egerrit.internal.model.ActionConstants;
import org.eclipse.egerrit.internal.model.ChangeInfo;
import org.eclipse.egerrit.internal.model.ChangeMessageInfo;
import org.eclipse.egerrit.internal.model.RevisionInfo;
import org.eclipse.egerrit.internal.process.ReplyProcess;
import org.eclipse.egerrit.internal.ui.table.filter.AuthorKindFilter;
import org.eclipse.egerrit.internal.ui.utils.Messages;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.PlatformUI;

public class HistoryTableMenuBuilder {
	private Menu commonMenu;

	private FilterSelectionAction humanOnlyfilter = null;

	private FilterSelectionAction machineOnlyfilter = null;

	private ReplyMessageAction replyMessages = null;

	public void addPulldownMenu(ColumnViewer viewer, GerritClient client) {
		MenuManager menuManager = new MenuManager();
		if (viewer instanceof TableViewer) {
			commonMenu = menuManager.createContextMenu(((TableViewer) viewer).getTable());
			((TableViewer) viewer).getTable().setMenu(commonMenu);
			humanOnlyfilter = new FilterSelectionAction(Messages.UIHistoryTable_0, viewer, new AuthorKindFilter(false));
			machineOnlyfilter = new FilterSelectionAction(Messages.UIHistoryTable_1, viewer,
					new AuthorKindFilter(true));
			replyMessages = new ReplyMessageAction(ActionConstants.REPLY.getLiteral(), viewer, client);
		}
		menuManager.addMenuListener(manager -> addMenuItem(commonMenu, viewer));
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

	private void addMenuItem(Menu menu, ColumnViewer viewer) {
		if ((menu.getItemCount() == 0) && (viewer instanceof TableViewer)) {
			MenuManager menuMgr = new MenuManager();
			menuMgr.add(replyMessages);
			menuMgr.add(new Separator());
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

	private class ReplyMessageAction extends Action {
		private StructuredViewer viewer;

		private GerritClient gerritClient;

		public ReplyMessageAction(String actionName, StructuredViewer viewer, GerritClient client) {
			super(actionName, AS_PUSH_BUTTON);
			this.viewer = viewer;
			this.gerritClient = client;
		}

		@Override
		public void run() {
			ISelection selection = viewer.getSelection();
			if (!selection.isEmpty() && selection instanceof StructuredSelection) {
				IStructuredSelection struct = (IStructuredSelection) selection;
				Object obj = struct.getFirstElement();
				if (obj instanceof ChangeMessageInfo) {
					ChangeMessageInfo messageInfo = (ChangeMessageInfo) obj;
					ReplyProcess replyProcess = new ReplyProcess();
					ChangeInfo changeInfo = (ChangeInfo) messageInfo.eContainer();
					RevisionInfo revisionInfo = changeInfo.getRevisionByNumber(messageInfo.get_revision_number());
					replyProcess.handleReplyDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
							changeInfo, gerritClient, revisionInfo, messageInfo);
				}
			}
		}
	}

}