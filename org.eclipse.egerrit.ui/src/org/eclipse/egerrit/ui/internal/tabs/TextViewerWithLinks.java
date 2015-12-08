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

import org.eclipse.jface.text.hyperlink.HyperlinkManager;
import org.eclipse.swt.widgets.Composite;

/**
 * A text viewer that will always show links
 */
public class TextViewerWithLinks extends org.eclipse.jface.text.source.SourceViewer {
	public TextViewerWithLinks(Composite composite, int styles) {
		super(composite, null, styles);
	}

	public void resetLinkManager() {
		fHyperlinkManager.uninstall();
		fHyperlinkManager = new AlwaysOnLinkManager(HyperlinkManager.FIRST);
		fHyperlinkManager.install(this, fHyperlinkPresenter, fHyperlinkDetectors, fHyperlinkStateMask);
	}
}
