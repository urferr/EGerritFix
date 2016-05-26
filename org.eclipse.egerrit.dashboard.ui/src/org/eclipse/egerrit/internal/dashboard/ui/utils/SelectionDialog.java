/*******************************************************************************
 * Copyright (c) 2014 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jacques Bouthillier - Initial Implementation of the of the Selection repository
 ******************************************************************************/
package org.eclipse.egerrit.internal.dashboard.ui.utils;

import java.util.List;

import org.eclipse.egerrit.internal.core.GerritServerInformation;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.FormDialog;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.ScrolledForm;

/**
 * This class implements the initial Task Selection dialog.
 *
 * @since 1.0
 */
public class SelectionDialog extends FormDialog {

	private final List<GerritServerInformation> fListGerritServerInformation;

	private GerritServerInformation fSelection = null;

	public SelectionDialog(Shell parent, List<GerritServerInformation> listGerritServerInformation) {
		super(parent);
		this.fListGerritServerInformation = listGerritServerInformation;
	}

	@Override
	protected void createFormContent(final IManagedForm mform) {
		mform.getForm().setText(Messages.SelectionDialog_selectTitle);
		mform.getForm().getShell().setText(Messages.SelectionDialog_shellText);

		final ScrolledForm sform = mform.getForm();
		sform.setExpandHorizontal(true);
		final Composite composite = sform.getBody();

		final GridLayout layout = new GridLayout(1, false);
		composite.setLayout(layout);

		GridDataFactory.fillDefaults().grab(false, false).applyTo(composite);
		for (GerritServerInformation server : fListGerritServerInformation) {
			final Button button = new Button(composite, SWT.RADIO);
			button.setText(server.getName());
			button.setData(server);
			button.setSelection(false);

			button.addListener(SWT.Selection, new Listener() {
				@Override
				public void handleEvent(Event event) {
					setSelection((GerritServerInformation) button.getData());
				}
			});

			GridDataFactory.fillDefaults().span(1, 1).applyTo(button);
		}

		setHelpAvailable(false);
	}

	private void setSelection(GerritServerInformation selectedServer) {
		fSelection = selectedServer;
	}

	public GerritServerInformation getSelection() {
		return fSelection;
	}
}
