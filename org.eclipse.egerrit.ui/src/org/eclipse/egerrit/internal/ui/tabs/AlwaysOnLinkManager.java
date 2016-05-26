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

import org.eclipse.jface.text.TextEvent;
import org.eclipse.jface.text.hyperlink.HyperlinkManager;
import org.eclipse.swt.events.MouseEvent;

/**
 * The link manager used by the TextViewerWithLinks. It is responsible for detecting and showing the links when the text
 * input changes
 */
public class AlwaysOnLinkManager extends HyperlinkManager {

	public AlwaysOnLinkManager(DETECTION_STRATEGY detectionStrategy) {
		super(detectionStrategy);
	}

	@Override
	protected void deactivate() {
		//Do nothing
	}

	@Override
	public void textChanged(TextEvent event) {
		//Do nothing
	}

	@Override
	public void mouseMove(MouseEvent event) {
		//Do nothing
	}
}
