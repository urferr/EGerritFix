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
import org.eclipse.egerrit.core.GerritClient;
import org.eclipse.egerrit.core.rest.ChangeMessageInfo;
import org.eclipse.egerrit.ui.internal.table.UIHistoryTable;
import org.eclipse.egerrit.ui.internal.table.provider.HistoryTableLabelProvider;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ViewerSupport;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.hyperlink.DefaultHyperlinkPresenter;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.jface.text.hyperlink.IHyperlinkDetector;
import org.eclipse.jface.text.hyperlink.IHyperlinkPresenter;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

/**
 * This class is used in the editor to handle the Gerrit history view <a>http://git.eclipse.org/r</a>
 *
 * @since 1.0
 */
public class HistoryTabView {

	private TableViewer tableHistoryViewer;

	private TextViewerWithLinks msgTextData;

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
					final ChangeMessageInfo changeMessage = (ChangeMessageInfo) element;
					msgTextData.setDocument(new Document(changeMessage.getMessage()));
				}
			}
		});

		msgTextData = new TextViewerWithLinks(sashForm, SWT.BORDER | SWT.WRAP | SWT.MULTI | SWT.V_SCROLL);
		msgTextData.configure(new SourceViewerConfiguration() {
			@Override
			public int getHyperlinkStateMask(ISourceViewer sourceViewer) {
				return SWT.NONE;
			}

			@Override
			public IHyperlinkDetector[] getHyperlinkDetectors(ISourceViewer sourceViewer) {
				return new IHyperlinkDetector[] { new HyperLinkDetector() };
			}

			@Override
			public IHyperlinkPresenter getHyperlinkPresenter(final ISourceViewer sourceViewer) {
				return new DefaultHyperlinkPresenter(new RGB(0, 0, 255)) {
					@Override
					public void inputDocumentChanged(IDocument oldInput, IDocument newInput) {
						super.inputDocumentChanged(oldInput, newInput);
						IHyperlink[] links = new HyperLinkDetector().detectHyperlinks(sourceViewer, null, true);
						if (links != null) {
							showHyperlinks(links);
						}
					}
				};
			}

		});
		msgTextData.resetLinkManager();

		//Set the % of display data.70% table and 30% for the comment message
		sashForm.setWeights(new int[] { 70, 30 });

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
