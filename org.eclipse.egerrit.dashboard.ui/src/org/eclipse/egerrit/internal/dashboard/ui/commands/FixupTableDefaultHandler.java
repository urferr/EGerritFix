/*******************************************************************************
 * Copyright (c) 2016 Ericsson AB and others.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Ericsson Communications - Created for Review Dashboard-Gerrit project
 *
 ******************************************************************************/
package org.eclipse.egerrit.internal.dashboard.ui.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.egerrit.internal.dashboard.ui.views.GerritTableView;
import org.eclipse.egerrit.internal.ui.compare.CompareUpperSectionColumn;
import org.eclipse.egerrit.internal.ui.editors.ChangeDetailEditor;
import org.eclipse.egerrit.internal.ui.table.UIConflictsWithTable;
import org.eclipse.egerrit.internal.ui.table.UIFilesTable;
import org.eclipse.egerrit.internal.ui.table.UIHistoryTable;
import org.eclipse.egerrit.internal.ui.table.UIRelatedChangesTable;
import org.eclipse.egerrit.internal.ui.table.UIReviewersTable;
import org.eclipse.egerrit.internal.ui.table.UISameTopicTable;
import org.eclipse.egerrit.internal.ui.table.model.ConflictWithTableModel;
import org.eclipse.egerrit.internal.ui.table.model.FilesTableModel;
import org.eclipse.egerrit.internal.ui.table.model.HistoryTableModel;
import org.eclipse.egerrit.internal.ui.table.model.ITableModel;
import org.eclipse.egerrit.internal.ui.table.model.RelatedChangesTableModel;
import org.eclipse.egerrit.internal.ui.table.model.ReviewersTableModel;
import org.eclipse.egerrit.internal.ui.table.model.SameTopicTableModel;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

/**
 * This class implements restoration of a table in its original layout.
 *
 * @since 1.0
 */
public class FixupTableDefaultHandler extends AbstractHandler {

	private static final String COMPARE_EDITOR_ID = "org.eclipse.compare.CompareEditor"; //$NON-NLS-1$

	@Override
	public Object execute(final ExecutionEvent aEvent) {
		Display disp = Display.getCurrent();
		Control control = disp.getFocusControl();
		if (control instanceof Table
				&& (control.getData() != null && control.getData().equals(UIFilesTable.FILES_TABLE))) {
			FilesTableModel[] tableInfo = FilesTableModel.values();
			((Table) control).setColumnOrder(FilesTableModel.getDefaultOrder());
			setTableDefaultWidth(control, tableInfo);
			return null;
		}
		IWorkbenchPage wbp = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		String id = wbp.getActivePartReference().getId();
		if (id.compareTo(GerritTableView.VIEW_ID) == 0) {
			GerritTableView reviewTableView = GerritTableView.getActiveView(true);
			reviewTableView.resetDefault();
		} else {
			Display display = Display.getCurrent();
			Control table = display.getFocusControl();

			if (id.compareTo(ChangeDetailEditor.EDITOR_ID) == 0) {
				handleChangeDetailEditor(table);
			} else if (id.compareTo(COMPARE_EDITOR_ID) == 0) {
				handleCompareEditor(table);
			}
		}
		return null;
	}

	/**
	 * @param table
	 */
	private void handleCompareEditor(Control table) {
		if (table instanceof Tree) {
			CompareUpperSectionColumn[] tableInfo = CompareUpperSectionColumn.values();
			((Tree) table).setColumnOrder(CompareUpperSectionColumn.getDefaultOrder());
			setTableDefaultWidth(table, tableInfo);
		}
	}

	/**
	 * @param table
	 */
	private void handleChangeDetailEditor(Control table) {
		if (table instanceof Table) {
			if (table.getData().toString().compareTo(UIReviewersTable.REVIEWERS_TABLE) == 0) {
				ReviewersTableModel[] tableInfo = ReviewersTableModel.values();
				((Table) table).setColumnOrder(ReviewersTableModel.getDefaultOrder());
				setTableDefaultWidth(table, tableInfo);
			} else if (table.getData().toString().compareTo(UIFilesTable.FILES_TABLE) == 0) {
				FilesTableModel[] tableInfo = FilesTableModel.values();
				((Table) table).setColumnOrder(FilesTableModel.getDefaultOrder());
				setTableDefaultWidth(table, tableInfo);
			} else if (table.getData().toString().compareTo(UIHistoryTable.HISTORY_TABLE) == 0) {
				HistoryTableModel[] tableInfo = HistoryTableModel.values();
				((Table) table).setColumnOrder(HistoryTableModel.getDefaultOrder());
				setTableDefaultWidth(table, tableInfo);
			} else if (table.getData().toString().compareTo(UISameTopicTable.SAME_TOPIC_TABLE) == 0) {
				SameTopicTableModel[] tableInfo = SameTopicTableModel.values();
				((Table) table).setColumnOrder(SameTopicTableModel.getDefaultOrder());
				setTableDefaultWidth(table, tableInfo);
			} else if (table.getData().toString().compareTo(UIRelatedChangesTable.RELATED_CHANGES_TABLE) == 0) {
				RelatedChangesTableModel[] tableInfo = RelatedChangesTableModel.values();
				((Table) table).setColumnOrder(RelatedChangesTableModel.getDefaultOrder());
				setTableDefaultWidth(table, tableInfo);
			} else if (table.getData().toString().compareTo(UIConflictsWithTable.CONFLICTS_WITH_TABLE) == 0) {
				ConflictWithTableModel[] tableInfo = ConflictWithTableModel.values();
				((Table) table).setColumnOrder(ConflictWithTableModel.getDefaultOrder());
				setTableDefaultWidth(table, tableInfo);
			}
		}
	}

	private void setTableDefaultWidth(Control table, ITableModel[] tableInfo) {
		for (int i = 0; i < tableInfo.length; i++) {
			if (table instanceof Table) {
				((Table) table).getColumn(i).setWidth(tableInfo[i].getWidth());
			} else {
				((Tree) table).getColumn(i).setWidth(tableInfo[i].getWidth());
			}
		}
	}
}
