/*******************************************************************************
 * Copyright (c) 2016 Ericsson
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson - initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.internal.dashboard.ui.completion;

import org.eclipse.jface.fieldassist.ComboContentAdapter;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.fieldassist.IContentProposalProvider;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Combo;

/**
 * Class used to attach content proposal behavior to the search box for the GerritTableView.
 */
public class SearchContentProposalAdapter extends ContentProposalAdapter {

	public SearchContentProposalAdapter(Combo control, IContentProposalProvider provider) {
		super(control, new ComboContentAdapter(), provider, null, null);
		setProposalAcceptanceStyle(ContentProposalAdapter.PROPOSAL_REPLACE);
		addMouseListener(control);
		addKeyListener(control);
	}

	private void addMouseListener(Combo control) {
		// Add a mouse listener for the specific case where the user first
		// clicks in the search box and we want to provide some proposals

		control.addMouseListener(new MouseListener() {
			@Override
			public void mouseUp(MouseEvent e) {
				if (control.getListVisible()) {
					// Don't pop-up any proposals if the user
					// is accessing the drop-down list
					return;
				}

				// Before the user types anything, we provide some proposals
				if (control.getText().trim().isEmpty()) {
					openProposalPopup();
				}
			}

			@Override
			public void mouseDown(MouseEvent e) {
				// ignore
			}

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// ignore
			}
		});
	}

	private void addKeyListener(Combo control) {
		// Key-pressed listener to trigger the proposals when pressing backspace
		// until the content is empty.  In this case we still want to show suggestions
		control.addKeyListener(new KeyListener() {
			@Override
			public void keyReleased(KeyEvent event) {
				// If the user deletes everything, we still provide some proposals
				// which the default proposal logic does not trigger, so we trigger
				// it ourselves
				if (control.getText().isEmpty()) {
					openProposalPopup();
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// ignore
			}
		});
	}
}
