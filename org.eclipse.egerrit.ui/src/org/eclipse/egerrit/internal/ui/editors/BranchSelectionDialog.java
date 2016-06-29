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

package org.eclipse.egerrit.internal.ui.editors;

import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.egerrit.internal.model.ChangeInfo;
import org.eclipse.egerrit.internal.ui.table.model.BranchMatch;
import org.eclipse.egerrit.internal.ui.table.model.BranchSelectionTableModel;
import org.eclipse.egerrit.internal.ui.table.model.ITableModel;
import org.eclipse.egerrit.internal.ui.table.model.ReviewTableSorter;
import org.eclipse.egerrit.internal.ui.table.provider.BranchListLabelProvider;
import org.eclipse.egerrit.internal.ui.utils.Messages;
import org.eclipse.egerrit.internal.ui.utils.UIUtils;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

/**
 * This class implements the selection of a Branch from the list of local branches.
 *
 * @since 1.0
 */
public class BranchSelectionDialog extends Dialog {

	private final int TABLE_STYLE = (SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);

	private TableViewer fViewer;

	private Map<String, BranchMatch> potentialBranches;

	private String selectedBranch = null;

	private Action doubleClickAction;

	private ChangeInfo changeInfo;

	private final static int WIDTH = 400;

	public BranchSelectionDialog(Shell parent, Map<String, BranchMatch> potentialBranches2, ChangeInfo changeInfo) {
		super(parent);
		setShellStyle(SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.MAX | SWT.RESIZE | getDefaultOrientation());
		this.potentialBranches = potentialBranches2;
		this.changeInfo = changeInfo;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		createTableViewerSection(parent);
		parent.getShell().setText(Messages.BranchSelectionTableModel_title);//Dialog title
		//set the dialog size
		org.eclipse.swt.graphics.Point minSize = parent.getShell().computeSize(WIDTH, SWT.DEFAULT);
		parent.getShell().setSize(minSize);
		parent.getShell().setMinimumSize(WIDTH, minSize.y + 80);// + 80 to allow for the button to be visible
		return super.createDialogArea(parent);
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, Messages.BranchSelectionTableModel_new, false);
		createButton(parent, IDialogConstants.CLIENT_ID, Messages.BranchSelectionTableModel_switch, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

	@Override
	protected void buttonPressed(int buttonId) {
		if (IDialogConstants.OK_ID == buttonId) {
			buttonNewPressed();
		} else if (IDialogConstants.CLIENT_ID == buttonId) {
			buttonSwitchPressed();
		} else if (IDialogConstants.CANCEL_ID == buttonId) {
			cancelPressed();
		}
	}

	protected void buttonNewPressed() {
		setReturnCode(IDialogConstants.OK_ID);
		close();
	}

	protected void buttonSwitchPressed() {
		setReturnCode(IDialogConstants.CLIENT_ID);
		close();
	}

	/**
	 * Create a table of local branches to select from.
	 *
	 * @param aParent
	 * @return
	 */
	private TableViewer createTableViewerSection(Composite aParent) {
		GridLayout layout = new GridLayout(2, false);
		aParent.setLayout(layout);
		// Create the table viewer to maintain the list of review files
		Label labelHeader = new Label(aParent, SWT.NONE | SWT.WRAP);
		labelHeader.setText(NLS.bind(Messages.BranchSelectionTableModel_question,
				changeInfo.getUserSelectedRevision().get_number(), changeInfo.getSubject()));
		labelHeader.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

		fViewer = new TableViewer(aParent, TABLE_STYLE);
		buildAndLayoutTable();

		// Set the content sorter
		ReviewTableSorter.bind(fViewer);
		fViewer.setComparator(new ReviewTableSorter(1));
		//set the content provider
		fViewer.setContentProvider(new ArrayContentProvider());
		fViewer.setLabelProvider(new BranchListLabelProvider());

		fViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				ISelection selection = event.getSelection();
				if (selection instanceof StructuredSelection) {
					Entry entry = (Entry) ((StructuredSelection) selection).getFirstElement();
					selectedBranch = (String) entry.getKey();
				}
			}
		});

		fViewer.setInput(potentialBranches.entrySet().toArray());
		//See if we have one perfect match
		int foundMatchAt = findPerfectMatch();
		if (foundMatchAt >= 0) {
			selectPerfectBranch(foundMatchAt);
		}

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
		final ITableModel[] tableInfo = BranchSelectionTableModel.values();
		int size = tableInfo.length;
		for (int index = 0; index < size; index++) {
			createTableViewerColumn(tableInfo[index]);
		}
		GridData gribData = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 2);
		gribData.minimumWidth = tableInfo[0].getWidth();
		Point fontsize = UIUtils.computeFontSize(table);
		gribData.minimumHeight = fontsize.y * 10;//Set a minimum height to 10 lines

		fViewer.getTable().setLayoutData(gribData);
		TableLayout tableLayout = new TableLayout();
		table.setLayout(tableLayout);

		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		makeActions();
		hookDoubleClickAction();
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
	 * Set the selected Perfect branch at the provided index
	 *
	 * @param int
	 *            index
	 */
	private void selectPerfectBranch(int index) {
		fViewer.getTable().select(index);
		fViewer.getTable().showSelection();
		ISelection selection = fViewer.getSelection();
		if (selection instanceof StructuredSelection) {
			Entry<?, ?> entry = (Entry<?, ?>) ((StructuredSelection) selection).getFirstElement();
			selectedBranch = (String) entry.getKey();
		}
	}

	/**
	 * From the list of potential branch matching, find the first one having a perfect match, so with same commit id
	 *
	 * @return int index
	 */
	private int findPerfectMatch() {
		int found = -1;
		TableItem[] tabItems = fViewer.getTable().getItems();
		for (int index = 0; index < tabItems.length; index++) {
			if (tabItems[index].getText(BranchSelectionTableModel.Match.ordinal())
					.equals(BranchMatch.PERFECT_MATCH.getValue())) {
				return index;
			}
		}
		return found;
	}

	/**
	 * Create the actions available on the table
	 */
	private void makeActions() {
		doubleClickAction = new Action() {
			@Override
			public void run() {

				// -------------------------------------------------
				// Select the branch and checkout that branch
				// -------------------------------------------------
				//Proceed to checkout that branch like the switch command
				buttonPressed(IDialogConstants.CLIENT_ID);
			}
		};
	}

	/**
	 * Hook the double click action
	 */
	private void hookDoubleClickAction() {
		fViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}

	/**
	 * Return the selected branch from the table
	 *
	 * @return String
	 */
	public String getSelectedBranch() {
		return selectedBranch;
	}
}
