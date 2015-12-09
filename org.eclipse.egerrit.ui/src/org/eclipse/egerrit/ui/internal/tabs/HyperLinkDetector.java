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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	final static String regex = "(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]"; //$NON-NLS-1$

	final static Pattern pattern = Pattern.compile(regex);

	@Override
	public IHyperlink[] detectHyperlinks(ITextViewer textViewer, IRegion region, boolean canShowMultipleHyperlinks) {
		Matcher matcher = pattern.matcher(textViewer.getDocument().get());
		ArrayList<IHyperlink> results = new ArrayList<>();
		while (matcher.find()) {
			results.add(
					new URLHyperlink(new Region(matcher.start(), matcher.end() - matcher.start()), matcher.group()));
		}
		if (results.size() == 0) {
			return null;
		}
		return results.toArray(new IHyperlink[results.size()]);
	}

	//This class is copied from org.eclipse.ui.internal.editors.text because the class there is not visible
	final static class URLHyperlink extends org.eclipse.jface.text.hyperlink.URLHyperlink {

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
		}

		/*
		 * @see org.eclipse.jface.text.hyperlink.URLHyperlink#open()
		 * @since 3.1
		 */
		@Override
		public void open() {
			// Create the browser
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
