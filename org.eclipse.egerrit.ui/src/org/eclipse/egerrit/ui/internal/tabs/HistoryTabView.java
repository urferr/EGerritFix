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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.property.Properties;
import org.eclipse.egerrit.core.GerritClient;
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
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;

/**
 * This class is used in the editor to handle the Gerrit history view
 *
 * @since 1.0
 */
public class HistoryTabView {

	private TableViewer tableHistoryViewer;

	private Link msgTextData;

	// ------------------------------------------------------------------------
	// Constructor and life cycle
	// ------------------------------------------------------------------------

	/**
	 * The constructor.
	 */
	public HistoryTabView() {
	}

	/**
	 * @param fGerritClient
	 * @param tabFolder
	 * @param listMessages
	 *            List<ChangeMessageInfo>
	 */
	public void create(GerritClient gerritClient, TabFolder tabFolder, List<ChangeMessageInfo> listMessages) {
		createControls(tabFolder, listMessages);
	}

	private void createControls(TabFolder tabFolder, List<ChangeMessageInfo> listMessages) {
		TabItem tbtmHistory = new TabItem(tabFolder, SWT.NONE);
		tbtmHistory.setText("History");

		SashForm sashForm = new SashForm(tabFolder, SWT.VERTICAL);
		tbtmHistory.setControl(sashForm);

		UIHistoryTable tableUIHistory = new UIHistoryTable();
		tableUIHistory.createTableViewerSection(sashForm);

		tableHistoryViewer = tableUIHistory.getViewer();
		tableHistoryViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection sel = (IStructuredSelection) event.getSelection();
				Object element = sel.getFirstElement();
				if (element instanceof ChangeMessageInfo) {
					ChangeMessageInfo changeMessage = (ChangeMessageInfo) element;
					msgTextData.setText(setHypertext(changeMessage.getMessage()));
				}
			}
		});

		msgTextData = new Link(sashForm, SWT.BORDER | SWT.WRAP | SWT.MULTI | SWT.V_SCROLL);
		msgTextData.setBackground(tabFolder.getBackground());
		msgTextData.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				openWebrowser(e.text);
			}
		});

		//Set the % of display data.70% table and 30% for the comment message
		sashForm.setWeights(new int[] { 70, 30 });

		//Set the binding for this section
		hisTabDataBindings(listMessages);
	}

	private void openWebrowser(String text) {
		IWorkbenchBrowserSupport workBenchSupport = PlatformUI.getWorkbench().getBrowserSupport();

		try {
			URL url = null;
			try {
				url = new URL(text);
				String id = getEditorId(url);
				//Using NULL as a browser id will create a new editor each time,
				//so we need to see if there is already an editor for this
				workBenchSupport.createBrowser(id).openURL(url);
			} catch (MalformedURLException e) {
			}
		} catch (PartInitException e) {
		}
	}

	/**
	 * Search for a similar page in the eclipse editor
	 *
	 * @param aUrl
	 * @return String
	 */
	private String getEditorId(URL aUrl) {
		//Try to get the editor id
		IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(aUrl.getFile());
		String id = null;
		if (desc != null) {
			id = desc.getId();
		}

		return id;
	}

	private String setHypertext(String fullString) {

		if (!fullString.isEmpty()) {
			int index = fullString.lastIndexOf("https:"); //$NON-NLS-1$

			//If we did not find the https
			if (index == -1) {
				//Test for http instead
				index = fullString.lastIndexOf("http:"); //$NON-NLS-1$
			}

			//Found http or https
			if (index != -1) {
				String linkString = fullString.substring(index);
				int endIndex = linkString.indexOf(' ');
				if (endIndex != -1) {
					//the value end the comment
					linkString = linkString.substring(0, endIndex);//original
				}
				String replacement = "<a>" + linkString + "</a>"; //$NON-NLS-1$ //$NON-NLS-2$
				fullString = fullString.replaceFirst(linkString, replacement);
				return fullString;
			}
		}
		return fullString;
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
