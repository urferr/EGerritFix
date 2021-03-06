/*******************************************************************************
 * Copyright (c) 2014 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jacques Bouthillier - Initial Implementation
 ******************************************************************************/
package org.eclipse.egerrit.internal.dashboard.ui.preferences;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.egerrit.internal.core.GerritServerInformation;
import org.eclipse.egerrit.internal.core.ServersStore;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * This class represents a preference page that is contributed to the Preferences dialog. By subclassing
 * <samp>FieldEditorPreferencePage</samp>, we can use the field support built into JFace that allows us to create a page
 * that is small and knows how to save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They are stored in the preference store that belongs to the main
 * plug-in class. That way, preferences can be accessed directly via the preference store.
 */
/**
 * @since 1.0
 */
public class GerritDashboardPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	private static final String ID = "org.eclipse.egerrit.dashboard.ui.preferences.GerritDashbardPreferencePage"; //$NON-NLS-1$

	private static final String NEW = Messages.GerritDashboardPreferencePage_0;

	private static final String REMOVE = Messages.GerritDashboardPreferencePage_1;

	private static final String MODIFY = Messages.GerritDashboardPreferencePage_2;

	private static final String TITLE = Messages.GerritDashboardPreferencePage_3;

	private static final String SELECTION_MESSAGE = Messages.GerritDashboardPreferencePage_4;

	private static final String REMOVE_MESSAGE = Messages.GerritDashboardPreferencePage_5;

	private static final String NO_SERVER_SAVED = Messages.GerritDashboardPreferencePage_6;

	private static final String INVALID_SERVER = Messages.GerritDashboardPreferencePage_7;

	private Composite prefsContainer = null;

	private List<GerritServerInformation> listServers = new ArrayList<>();

	private TableViewer serverInfoViewer;

	private Button buttonRemove;

	private Button buttonModify;

	public GerritDashboardPreferencePage() {
		super(GRID);
		setDescription(Messages.GerritDashboardPreferencePage_8);
	}

	@Override
	protected Point doComputeSize() {
		return getControl().computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common GUI blocks needed to manipulate various
	 * types of preferences. Each field editor knows how to save and restore itself.
	 */
	@Override
	public void createFieldEditors() {

		prefsContainer = new Composite(getFieldEditorParent(), SWT.NONE);
		prefsContainer.getParent().addControlListener(new ControlListener() {

			@Override
			public void controlResized(ControlEvent e) {
				prefsContainer.setSize(prefsContainer.getParent().getSize());
			}

			@Override
			public void controlMoved(ControlEvent e) {
				// ignore
			}
		});

		final GridData prefsContainerData = new GridData(SWT.FILL, SWT.FILL, true, true);
		prefsContainer.setLayoutData(prefsContainerData);

		final GridLayout prefsLayout = new GridLayout(3, false);
		prefsContainer.setLayout(prefsLayout);

		final Table entriesTable = new Table(prefsContainer,
				SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.FULL_SELECTION);
		entriesTable.setHeaderVisible(true);
		entriesTable.setLinesVisible(true);
		entriesTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 4));

		serverInfoViewer = new TableViewer(entriesTable);
		GerritServerTableLabelProvider labelProvider = new GerritServerTableLabelProvider();
		GerritServerTableContentProvider contentProvider = new GerritServerTableContentProvider();
		labelProvider.createColumns(serverInfoViewer);
		serverInfoViewer.setContentProvider(contentProvider);
		serverInfoViewer.setLabelProvider(labelProvider);
		serverInfoViewer.addDoubleClickListener(doubleClickListener());
		serverInfoViewer.addSelectionChangedListener(selectionChangedListener());

		TableLayout tableLayout = new TableLayout();
		tableLayout.addColumnData(new ColumnWeightData(150, 50, true));
		tableLayout.addColumnData(new ColumnWeightData(250, 50, true));
		tableLayout.addColumnData(new ColumnWeightData(150, 50, true));

		entriesTable.setLayout(tableLayout);

		//Create the Buttons for the table view
		createButtonBar();

		listServers = ServersStore.getAllServers();
		updateTable();

		if (serverInfoViewer.getTable().getItemCount() > 0) {
			serverInfoViewer.getTable().setSelection(0);
		}
	}

	private ISelectionChangedListener selectionChangedListener() {
		return event -> {
			ISelection tableSelection = serverInfoViewer.getSelection();
			boolean validSelection = (tableSelection instanceof IStructuredSelection)
					&& ((IStructuredSelection) tableSelection).getFirstElement() instanceof GerritServerInformation;
			buttonModify.setEnabled(validSelection);
			buttonRemove.setEnabled(validSelection);
		};
	}

	private void createButtonBar() {
		Composite buttonBar = new Composite(prefsContainer, SWT.NONE);

		buttonBar.setLayout(new GridLayout(1, true));
		Button buttonNew = new Button(buttonBar, SWT.PUSH);
		buttonNew.setText(NEW);
		buttonNew.addSelectionListener(buttonNewListener());
		buttonNew.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		buttonRemove = new Button(buttonBar, SWT.PUSH);
		buttonRemove.setText(REMOVE);
		buttonRemove.addSelectionListener(buttonRemoveListener());
		buttonRemove.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		buttonModify = new Button(buttonBar, SWT.PUSH);
		buttonModify.setText(MODIFY);
		buttonModify.addSelectionListener(buttonModifyListener());
		buttonModify.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		buttonBar.setSize(buttonBar.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}

	private void updateTable() {
		serverInfoViewer.setInput(listServers.toArray(new GerritServerInformation[listServers.size()]));
	}

	@Override
	public boolean performOk() {
		super.performOk();
		ServersStore.saveServers(listServers);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}

	public static String getID() {
		return ID;
	}

	private SelectionListener buttonModifyListener() {
		return new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				ISelection tableSelection = serverInfoViewer.getSelection();
				int selectedIndex = serverInfoViewer.getTable().getSelectionIndex();
				if (tableSelection instanceof IStructuredSelection) {
					Object obj = ((IStructuredSelection) tableSelection).getFirstElement();

					if (obj instanceof GerritServerInformation) {
						processDialogInfo((GerritServerInformation) obj, selectedIndex);
					} else {
						Utils.displayInformation(prefsContainer.getShell(), TITLE,
								SELECTION_MESSAGE + ((Button) e.widget).getText());
					}
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// ignore
			}
		};
	}

	private SelectionListener buttonNewListener() {
		return new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				processDialogInfo(null, -1);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// ignore
			}
		};
	}

	private SelectionListener buttonRemoveListener() {
		return new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				ISelection tableSelection = serverInfoViewer.getSelection();
				if (tableSelection instanceof IStructuredSelection) {
					Object obj = ((IStructuredSelection) tableSelection).getFirstElement();

					if (obj instanceof GerritServerInformation) {
						listServers.remove(obj);
						updateTable();
					} else {
						Utils.displayInformation(null, TITLE, REMOVE_MESSAGE);
					}
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// ignore
			}
		};
	}

	private IDoubleClickListener doubleClickListener() {
		return event -> {
			IStructuredSelection selection = (IStructuredSelection) serverInfoViewer.getSelection();
			if (selection.isEmpty()) {
				return;
			} else {
				Object obj = selection.getFirstElement();
				int selectedIndex = serverInfoViewer.getTable().getSelectionIndex();

				if (obj instanceof GerritServerInformation) {
					processDialogInfo((GerritServerInformation) obj, selectedIndex);
				}
			}
		};
	}

	private void processDialogInfo(GerritServerInformation serverInfo, int selectedIndex) {

		GerritServerDialog dialog = new GerritServerDialog(getFieldEditorParent().getShell(), serverInfo);
		int ret = dialog.open();
		if (ret == IDialogConstants.OK_ID) {
			try {
				if (dialog.getServerInfo() != null && dialog.getServerInfo().isValid()) {
					if (selectedIndex >= 0) {
						listServers.remove(selectedIndex);
						listServers.add(selectedIndex, dialog.getServerInfo());
					} else {
						//Add it at the end
						listServers.add(listServers.size(), dialog.getServerInfo());
					}
					updateTable();
				} else {
					Utils.displayInformation(null, TITLE, NO_SERVER_SAVED);
				}
			} catch (URISyntaxException e) {
				Utils.displayInformation(dialog.getShell(), TITLE, INVALID_SERVER + e.getLocalizedMessage());
			}
		}
	}

}
