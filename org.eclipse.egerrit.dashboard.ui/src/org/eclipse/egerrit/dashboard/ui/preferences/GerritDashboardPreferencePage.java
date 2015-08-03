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
package org.eclipse.egerrit.dashboard.ui.preferences;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.egerrit.core.EGerritCorePlugin;
import org.eclipse.egerrit.dashboard.GerritPlugin;
import org.eclipse.egerrit.dashboard.preferences.GerritServerInformation;
import org.eclipse.egerrit.dashboard.preferences.PreferenceConstants;
import org.eclipse.egerrit.dashboard.utils.GerritServerUtility;
import org.eclipse.equinox.security.storage.EncodingUtils;
import org.eclipse.equinox.security.storage.ISecurePreferences;
import org.eclipse.equinox.security.storage.SecurePreferencesFactory;
import org.eclipse.equinox.security.storage.StorageException;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
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

	private final static String ID = "org.eclipse.egerrit.dashboard.ui.preferences.GerritDashbardPreferencePage"; //$NON-NLS-1$

	private final static String NEW = "New...";

	private final static String REMOVE = "Remove";

	private final static String MODIFY = "Modify...";

	private final static String TITLE = "Gerrit selection";

	private final static String SELECTION_MESSAGE = "Need to select a Gerrit Server \nto ";

	private final static String REMOVE_MESSAGE = "Need to select a Gerrit Server \nto remove it";

	private final static String NO_SERVER_SAVED = "No server being saved";

	private final static String INVALID_SERVER = "Invalid server: \n";

	private Composite prefsContainer = null;

	private List<GerritServerInformation> listInfo = new ArrayList<GerritServerInformation>();

	private TableViewer serverInfoViewer;

	public GerritDashboardPreferencePage() {
		super(GRID);
		setPreferenceStore(GerritPlugin.getDefault().getPreferenceStore());
		setDescription("Gerrit Servers defined");
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

		TableLayout tableLayout = new TableLayout();
		tableLayout.addColumnData(new ColumnWeightData(150, 50, true));
		tableLayout.addColumnData(new ColumnWeightData(250, 50, true));
		tableLayout.addColumnData(new ColumnWeightData(150, 50, true));

		entriesTable.setLayout(tableLayout);

		//Create the Buttons for the table view
		createButtonBar();

		updateTable(null);
	}

	private void createButtonBar() {
		ScrolledComposite scrolComposite = new ScrolledComposite(prefsContainer, SWT.V_SCROLL | SWT.H_SCROLL);
		scrolComposite.setLayout(new GridLayout(1, true));
		scrolComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));

		Composite buttonBar = new Composite(scrolComposite, SWT.NONE);
		scrolComposite.setContent(buttonBar);

		buttonBar.setLayout(new GridLayout(1, true));
		Button buttonNew = new Button(buttonBar, SWT.PUSH);
		buttonNew.setText(NEW);
		buttonNew.addSelectionListener(buttonNewListener());

		Button buttonRemove = new Button(buttonBar, SWT.PUSH);
		buttonRemove.setText(REMOVE);
		buttonRemove.addSelectionListener(buttonRemoveListener());

		Button buttonModify = new Button(buttonBar, SWT.PUSH);
		buttonModify.setText(MODIFY);
		buttonModify.addSelectionListener(buttonModifyListener());

		buttonBar.setSize(buttonBar.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		scrolComposite.setMinSize(buttonBar.getSize());
	}

	private void updateTable(List<GerritServerInformation> list) {
		//Get the preference store
		if (list == null) {
			IPreferenceStore store = GerritPlugin.getDefault().getPreferenceStore();
			listInfo = GerritServerUtility.getInstance()
					.createGerritServerInfo(store.getString(PreferenceConstants.P_GERRIT_LISTS));
		} else {
			listInfo = list;
		}

		if (!listInfo.isEmpty()) {
			serverInfoViewer.setInput(listInfo.toArray(new GerritServerInformation[listInfo.size()]));
		}

	}

	private void saveDialogueInfo() {
		GerritServerInformation[] tableItems = (GerritServerInformation[]) serverInfoViewer.getInput();
		IPreferenceStore store = GerritPlugin.getDefault().getPreferenceStore();

		StringBuilder value = new StringBuilder();
		ISecurePreferences securePref = SecurePreferencesFactory.getDefault();
		for (GerritServerInformation item : tableItems) {
			value.append(item.getAllInfo());
			value.append(PreferenceConstants.LIST_SEPARATOR);
			ISecurePreferences serverPreference = securePref
					.node(EncodingUtils.encodeSlashes(GerritServerUtility.getPreferenceKey(item)));
			try {
				serverPreference.put(item.getUserLabel(), item.getUserName(), true);
				serverPreference.put(item.getPasswordLabel(), item.getPassword(), true);
			} catch (StorageException e) {
				EGerritCorePlugin.logError(e.getMessage());
			}

		}

		int lastPosition = value.lastIndexOf(PreferenceConstants.LIST_SEPARATOR);
		if (lastPosition > 0) {
			value.deleteCharAt(lastPosition); //Remove the last separator
		}
		store.putValue(PreferenceConstants.P_GERRIT_LISTS, value.toString());
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
		SelectionListener modifyListener = new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				ISelection tableSelection = serverInfoViewer.getSelection();
				int selectedIndex = serverInfoViewer.getTable().getSelectionIndex();
				if (tableSelection instanceof IStructuredSelection) {
					Object obj = ((IStructuredSelection) tableSelection).getFirstElement();

					if (obj instanceof GerritServerInformation) {
						processDialogueInfo((GerritServerInformation) obj, selectedIndex);
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
		return modifyListener;
	}

	private SelectionListener buttonNewListener() {
		SelectionListener newListener = new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				processDialogueInfo(null, -1);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// ignore
			}
		};
		return newListener;
	}

	private SelectionListener buttonRemoveListener() {
		SelectionListener removeListener = new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				ISelection tableSelection = serverInfoViewer.getSelection();
				int selectedIndex = serverInfoViewer.getTable().getSelectionIndex();
				if (tableSelection instanceof IStructuredSelection) {
					Object obj = ((IStructuredSelection) tableSelection).getFirstElement();

					if (obj instanceof GerritServerInformation) {
						if (selectedIndex > 0) {
							listInfo.remove(selectedIndex);
						}
						updateTable(listInfo);
						saveDialogueInfo();
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
		return removeListener;
	}

	private void processDialogueInfo(GerritServerInformation serverInfo, int selectedIndex) {

		GerritServerDialog dialogue = new GerritServerDialog(getFieldEditorParent().getShell(), serverInfo);
		int ret = dialogue.open();
		if (ret == IDialogConstants.OK_ID) {
			try {
				if (dialogue.getServerInfo() != null && dialogue.getServerInfo().isValid()) {
					if (selectedIndex >= 0) {
						listInfo.remove(selectedIndex);
						listInfo.add(selectedIndex, dialogue.getServerInfo());
					} else {
						//Add it at the end
						listInfo.add(listInfo.size(), dialogue.getServerInfo());
					}
					updateTable(listInfo);
					saveDialogueInfo();

				} else {
					Utils.displayInformation(null, TITLE, NO_SERVER_SAVED);
				}
			} catch (URISyntaxException e) {
				Utils.displayInformation(dialogue.getShell(), TITLE, INVALID_SERVER + e.getLocalizedMessage());
			}
		}
	}

}
