/*******************************************************************************
 * Copyright (c) 2015-2016 Ericsson AB.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson - initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.ui.tests;

import static org.junit.Assert.assertEquals;

import org.eclipse.egerrit.internal.model.ChangeInfo;
import org.eclipse.egerrit.internal.model.ModelFactory;
import org.eclipse.egerrit.internal.ui.tabs.HyperLinkDetector;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.BeforeClass;
import org.junit.Test;

public class HyperLinkDetectorTest {
	private static Display display;

	private Shell shell;

	@BeforeClass
	public static void initDisplay() {
		display = Display.findDisplay(Thread.currentThread());
		if (display == null) {
			display = new Display();
		}
	}

	@Test
	//Test the case where the URL is a local file
	public void fileUrlTest() {
		ChangeInfo ci = ModelFactory.eINSTANCE.createChangeInfo();
		String testData = "The file of interest resides here: file://home/root/documents/project/sourcefile.java have a look at it."; //$NON-NLS-1$
		String expected = "Open ' file://home/root/documents/project/sourcefile.java' in a browser"; //$NON-NLS-1$
		HyperLinkDetector detector = new HyperLinkDetector(null, ci);
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());

		TextViewer textViewer = new TextViewer(shell, SWT.NONE);

		IDocument document = new Document();
		document.set(testData);

		textViewer.setDocument(document);

		IHyperlink[] result = detector.detectHyperlinks(textViewer, null, true);
		assertEquals(expected, result[0].getHyperlinkText());
	}

	@Test
	//Test the case where the string contains 2 urls
	public void twoURLsTest() {
		ChangeInfo ci = ModelFactory.eINSTANCE.createChangeInfo();
		String testData = "Patch Set 1:" //$NON-NLS-1$
				+ "BTW, it doesn't look like http://istmffastyet.dorsal.polymtl.ca/ still updates" //$NON-NLS-1$
				+ "It also looks like http://jenkins.dorsal.polymtl.ca/ is down?"; //$NON-NLS-1$

		String expected1 = "Open ' http://istmffastyet.dorsal.polymtl.ca/' in a browser"; //$NON-NLS-1$
		String expected2 = "Open ' http://jenkins.dorsal.polymtl.ca/' in a browser"; //$NON-NLS-1$
		HyperLinkDetector detector = new HyperLinkDetector(null, ci);
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());

		TextViewer textViewer = new TextViewer(shell, SWT.NONE);

		IDocument document = new Document();
		document.set(testData);

		textViewer.setDocument(document);

		IHyperlink[] result = detector.detectHyperlinks(textViewer, null, true);
		assertEquals(expected1, result[0].getHyperlinkText());
		assertEquals(expected2, result[1].getHyperlinkText());
	}

	@Test
	//Test the case where the string contains 1 url
	public void normalURLTest() {
		ChangeInfo ci = ModelFactory.eINSTANCE.createChangeInfo();
		String testData = "Patch Set 1: Code-Review+1" + "Build Successful " //$NON-NLS-1$ //$NON-NLS-2$
				+ "https://hudson.eclipse.org/tracecompass/job/tracecompass-gerrit/8538/ : SUCCESS"; //$NON-NLS-1$

		String expected = "Open ' https://hudson.eclipse.org/tracecompass/job/tracecompass-gerrit/8538/' in a browser"; //$NON-NLS-1$
		HyperLinkDetector detector = new HyperLinkDetector(null, ci);
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());

		TextViewer textViewer = new TextViewer(shell, SWT.NONE);

		IDocument document = new Document();
		document.set(testData);

		textViewer.setDocument(document);

		IHyperlink[] result = detector.detectHyperlinks(textViewer, null, true);
		assertEquals(expected, result[0].getHyperlinkText());
	}

}
