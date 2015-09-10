/*******************************************************************************
 * Copyright (c) 2015 Ericsson AB.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Jacques Bouthillier - initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.ui.internal.tabs;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.property.Properties;
import org.eclipse.egerrit.core.rest.ChangeMessageInfo;
import org.eclipse.egerrit.ui.internal.table.UIHistoryTable;
import org.eclipse.egerrit.ui.internal.table.provider.HistoryTableLabelProvider;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ViewerSupport;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

/**
 * This class is used in the editor to handle the Gerrit history view
 *
 * @since 1.0
 */
public class HistoryTabView {

	private TableViewer tableHistoryViewer;

	private Text msgTextData;

	private ScrolledComposite sc_msgtxt = null;

	// ------------------------------------------------------------------------
	// Constructor and life cycle
	// ------------------------------------------------------------------------

	/**
	 * The constructor.
	 */
	public HistoryTabView() {
	}

	/**
	 * @param tabFolder
	 * @param listMessages
	 *            List<ChangeMessageInfo>
	 */
	public void create(TabFolder tabFolder, List<ChangeMessageInfo> listMessages) {
		historyTab(tabFolder, listMessages);
	}

	private void historyTab(TabFolder tabFolder, List<ChangeMessageInfo> listMessages) {

		TabItem tbtmHistory = new TabItem(tabFolder, SWT.NONE);
		tbtmHistory.setText("History");

		final Group group = new Group(tabFolder, SWT.NONE);
		tbtmHistory.setControl(group);

		GridData grid = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		group.setLayoutData(grid);
		group.setLayout(new GridLayout(1, false));

		SashForm sashForm = new SashForm(group, SWT.VERTICAL);
		sashForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		UIHistoryTable tableUIHistory = new UIHistoryTable();
		tableUIHistory.createTableViewerSection(sashForm, grid);

		tableHistoryViewer = tableUIHistory.getViewer();
		tableHistoryViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection sel = (IStructuredSelection) event.getSelection();
				Object element = sel.getFirstElement();
				if (element instanceof ChangeMessageInfo) {
					ChangeMessageInfo changeMessage = (ChangeMessageInfo) element;
					msgTextData.setText(changeMessage.getMessage());
					//set the size when data change
					sc_msgtxt.setMinSize(msgTextData.computeSize(SWT.DEFAULT, SWT.DEFAULT));
				}
			}
		});

		//Build a text area to display the whole message
		sc_msgtxt = new ScrolledComposite(sashForm, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		sc_msgtxt.setExpandHorizontal(true);
		sc_msgtxt.setExpandVertical(true);
		sc_msgtxt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 3, 1));

		msgTextData = new Text(sc_msgtxt, SWT.BORDER | SWT.WRAP | SWT.MULTI);
		msgTextData.setEditable(false);
		sc_msgtxt.setContent(msgTextData);
		sc_msgtxt.setMinSize(msgTextData.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		sc_msgtxt.addControlListener(new ControlListener() {

			@Override
			public void controlResized(ControlEvent e) {
				// ignore
				sc_msgtxt.setMinSize(msgTextData.computeSize(SWT.DEFAULT, SWT.DEFAULT));
			}

			@Override
			public void controlMoved(ControlEvent e) {
				// ignore

			}
		});

		//Set the binding for this section
		hisTabDataBindings(listMessages);
	}

	protected void hisTabDataBindings(List<ChangeMessageInfo> listMessages) {

		ObservableListContentProvider contentProvider = new ObservableListContentProvider();
		tableHistoryViewer.setContentProvider(contentProvider);

		if (listMessages == null) {
			listMessages = new ArrayList<ChangeMessageInfo>();
		}

		WritableList writeInfoList = new WritableList(listMessages, ChangeMessageInfo.class);
		IObservableMap[] observeMaps = Properties.observeEach(contentProvider.getKnownElements(),
				BeanProperties.values(new String[] { "messages" }));

		ViewerSupport.bind(tableHistoryViewer, writeInfoList, BeanProperties.values(new String[] { "messages" }));
		tableHistoryViewer.setLabelProvider(new HistoryTableLabelProvider(observeMaps));

	}

	/**
	 * @return the tableHistoryViewer
	 */
	public TableViewer getTableHistoryViewer() {
		return tableHistoryViewer;
	}

}
