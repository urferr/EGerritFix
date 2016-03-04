/*******************************************************************************
 * Copyright (c) 2015 Ericsson AB.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson - initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.ui.internal.tabs;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.egerrit.core.GerritClient;
import org.eclipse.egerrit.internal.model.ChangeInfo;
import org.eclipse.egerrit.internal.model.ChangeMessageInfo;
import org.eclipse.egerrit.internal.model.FileInfo;
import org.eclipse.egerrit.internal.model.RevisionInfo;
import org.eclipse.egerrit.ui.editors.OpenCompareEditor;
import org.eclipse.egerrit.ui.editors.QueryHelpers;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.jface.text.hyperlink.IHyperlinkDetector;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWebBrowser;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;

public class HyperLinkDetector implements IHyperlinkDetector {

	final static String regex = "(http|https|ftp|file)://[a-zA-Z0-9]*.[a-zA-Z0-9]*.[a-zA-Z0-9]*.[a-zA-Z0-9 /]*.[ a-zA-Z0-9]*.[a-zA-Z0-9]*"; //$NON-NLS-1$

	final static Pattern pattern = Pattern.compile(regex);

	private static TableViewer tableViewer;

	private static GerritClient gerritClient;

	private static ChangeInfo changeInfo;

	private static RevisionInfo revInfo = null;

	public HyperLinkDetector(TableViewer tableHistoryViewer, GerritClient gerritClient, ChangeInfo changeInfo) {
		tableViewer = tableHistoryViewer;
		HyperLinkDetector.gerritClient = gerritClient;
		HyperLinkDetector.changeInfo = changeInfo;

		//look for the revision info and load comments associated to it if not already available
		adjustCommentForRevision(tableHistoryViewer, gerritClient, changeInfo);
	}

	/**
	 * @param tableHistoryViewer
	 * @param gerritClient
	 * @param changeInfo
	 */
	private void adjustCommentForRevision(TableViewer tableHistoryViewer, GerritClient gerritClient,
			ChangeInfo changeInfo) {
		ISelection selection = tableHistoryViewer.getSelection();
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			Object element = structuredSelection.getFirstElement();

			if (element instanceof ChangeMessageInfo) {
				ChangeMessageInfo changeMessageInfo = (ChangeMessageInfo) element;
				revInfo = changeInfo.getRevisionByNumber(changeMessageInfo.get_revision_number());
				QueryHelpers.loadComments(gerritClient, revInfo);
			}
		}
	}

	@Override
	public IHyperlink[] detectHyperlinks(ITextViewer textViewer, IRegion region, boolean canShowMultipleHyperlinks) {

		//Set the hyperlink which should open a web browser
		Matcher matcher = pattern.matcher(textViewer.getDocument().get());
		ArrayList<IHyperlink> results = new ArrayList<>();
		while (matcher.find()) {
			results.add(
					new URLHyperlink(new Region(matcher.start(), matcher.end() - matcher.start()), matcher.group()));
		}

		//Set Hyperlink for files having some comments
		setFilesHyperlink(textViewer, results);

		if (results.size() == 0) {
			return null;
		}
		return results.toArray(new IHyperlink[results.size()]);
	}

	/**
	 * Create the hyperlink associated to each file with comments.
	 *
	 * @param textViewer
	 * @param results
	 */
	private void setFilesHyperlink(ITextViewer textViewer, ArrayList<IHyperlink> results) {
		Matcher matcher;
		if (revInfo != null) {
			Iterator<Entry<String, FileInfo>> iterator = revInfo.getFiles().entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, FileInfo> definedFiles = iterator.next();
				FileInfo fileInfo = definedFiles.getValue();
				if (!fileInfo.getComments().isEmpty()) {
					Pattern pattern2 = Pattern.compile(fileInfo.getPath().replaceAll("/", "\\\\/")); //$NON-NLS-1$ //$NON-NLS-2$
					matcher = pattern2.matcher(textViewer.getDocument().get());
					if (matcher.find(0)) {
						Region detectionRegion = new Region(matcher.start(), matcher.end() - matcher.start());
						results.add(new CompareEditorLink(fileInfo, detectionRegion));
					}
				}
			}
		}
	}

	/**
	 * This class allows to open the compare editor for the link associated to the hyperlink
	 */
	final static class CompareEditorLink implements IHyperlink {
		private FileInfo fileInfo;

		private IRegion region;

		public CompareEditorLink(FileInfo fileToOpen, Region region) {
			fileInfo = fileToOpen;
			this.region = region;
		}

		/**
		 * Open a compare editor against the BASE version of the file
		 */
		public void open() {
			OpenCompareEditor compareEditor = new OpenCompareEditor(getGerritClient(), getChangeInfo());
			compareEditor.compareFiles("BASE", fileInfo.getRevision().getId(), fileInfo);
		}

		private GerritClient getGerritClient() {
			return gerritClient;
		}

		private ChangeInfo getChangeInfo() {
			return fileInfo.getRevision().getChangeInfo();
		}

		@Override
		public IRegion getHyperlinkRegion() {
			return region;
		}

		@Override
		public String getTypeLabel() {
			return null;
		}

		@Override
		public String getHyperlinkText() {
			return null;
		}
	}

	//This class is copied from org.eclipse.ui.internal.editors.text
	//It will open a web browser in Eclipse
	final class URLHyperlink extends org.eclipse.jface.text.hyperlink.URLHyperlink {

		private String label = null;

		/**
		 * Creates a new URL hyperlink.
		 *
		 * @param region
		 *            the region
		 * @param urlString
		 *            the URL string
		 */
		public URLHyperlink(IRegion region, String urlString) {
			super(region, urlString);
			label = urlString.substring(urlString.lastIndexOf("/"));//$NON-NLS-1$
		}

		@Override
		public String getTypeLabel() {
			return label;
		}

		/*
		 * @see org.eclipse.jface.text.hyperlink.URLHyperlink#open()
		 * @since 3.1
		 */
		@Override
		public void open() {
			IWorkbenchBrowserSupport support = PlatformUI.getWorkbench().getBrowserSupport();
			IWebBrowser browser;
			try {
				browser = support.createBrowser(null);
			} catch (PartInitException e) {
				super.open();
				return;
			}

			try {
				browser.openURL(new URL(getURLString()));
			} catch (PartInitException e) {
				super.open();
			} catch (MalformedURLException e) {
				super.open();
			}
		}
	}

}