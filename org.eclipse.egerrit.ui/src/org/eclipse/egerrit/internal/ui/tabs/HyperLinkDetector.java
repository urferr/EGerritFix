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

package org.eclipse.egerrit.internal.ui.tabs;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.egerrit.internal.core.GerritClient;
import org.eclipse.egerrit.internal.model.ChangeInfo;
import org.eclipse.egerrit.internal.model.FileInfo;
import org.eclipse.egerrit.internal.model.RevisionInfo;
import org.eclipse.egerrit.internal.ui.compare.GerritMultipleInput;
import org.eclipse.egerrit.internal.ui.utils.UIUtils;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.jface.text.hyperlink.IHyperlinkDetector;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWebBrowser;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;

public class HyperLinkDetector implements IHyperlinkDetector {

	final static String regex = "(?:^|[\\W])((http|https|ftp|file):\\/\\/|[A-Za-z][\\.|\\/])(([\\w\\-]+[\\.|\\/]){1,}?([\\w\\-.~]+\\/?)*[\\p{Alnum}.,%_=?&#\\-+()\\[\\]\\*$~@!:/{};']*)"; //$NON-NLS-1$

	final static Pattern pattern = Pattern.compile(regex);

	private GerritClient gerritClient;

	private ChangeInfo changeInfo;

	public HyperLinkDetector(GerritClient gerritClient, ChangeInfo changeInfo) {
		this.gerritClient = gerritClient;
		this.changeInfo = changeInfo;
	}

	@Override
	public IHyperlink[] detectHyperlinks(ITextViewer textViewer, IRegion region, boolean canShowMultipleHyperlinks) {
		if (textViewer.getDocument() == null) {
			return new IHyperlink[0];
		}
		Matcher matcher = pattern.matcher(textViewer.getDocument().get());
		ArrayList<IHyperlink> results = new ArrayList<>();
		while (matcher.find()) {
			results.add(
					new URLHyperlink(new Region(matcher.start(), matcher.end() - matcher.start()), matcher.group()));
		}

		//Set Hyperlink for files having some comments
		detectHyperlinksForFiles(textViewer, results);

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
	private void detectHyperlinksForFiles(ITextViewer textViewer, ArrayList<IHyperlink> results) {
		RevisionInfo revInfo = changeInfo.getUserSelectedRevision();
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
	private class CompareEditorLink implements IHyperlink {
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
			UIUtils.open(gerritClient, fileInfo, getChangeInfo(), GerritMultipleInput.BASE);
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