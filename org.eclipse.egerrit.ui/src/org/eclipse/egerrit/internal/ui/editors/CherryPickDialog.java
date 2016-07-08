/*******************************************************************************
 * Copyright (c) 2015 Ericsson AB.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Guy Perron - initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.internal.ui.editors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.egerrit.internal.ui.utils.Messages;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.fieldassist.ComboContentAdapter;
import org.eclipse.jface.fieldassist.ContentProposal;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.fieldassist.IContentProposal;
import org.eclipse.jface.fieldassist.IContentProposalProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is used in the editor to handle the Cherry-Pick button
 *
 * @since 1.0
 */
public class CherryPickDialog extends Dialog {

	final static Logger logger = LoggerFactory.getLogger(CherryPickDialog.class);

	private String commitMessage;

	private String[] branchesRef;

	private Text msgTextData;

	private Composite buttonComposite;

	private Button cancel;

	private Combo fBranch;

	private String fBranchText;

	private String fMessageText;

	/**
	 * The constructor.
	 */
	public CherryPickDialog(Composite parent, List<String> listBranchesRef, String message) {
		super(parent.getShell());
		setShellStyle(getShellStyle() | SWT.RESIZE);
		logger.debug("Open the Reply dialog."); //$NON-NLS-1$
		this.branchesRef = new String[listBranchesRef.size()];
		this.branchesRef = listBranchesRef.toArray(new String[listBranchesRef.size()]);
		this.commitMessage = message;

	}

	/**
	 * This method allows us to set a title to the dialog
	 *
	 * @param Shell
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(Messages.CherryPickDialog_0);
	}

	/**
	 * Create the reply dialog
	 *
	 * @param parent
	 *            Composite
	 */
	@Override
	protected Control createContents(Composite parent) {

		parent.setLayout(new GridLayout(1, true));

		Label lblBranch = new Label(parent, SWT.LEFT);
		fBranch = new Combo(parent, SWT.NONE);
		addContentProposal(fBranch);

		GridData gd_combo = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 3);
		gd_combo.verticalIndent = 5;
		gd_combo.grabExcessVerticalSpace = false;
		lblBranch.setText(Messages.CherryPickDialog_1);
		lblBranch.setLayoutData(gd_combo);
		fBranch.setLayoutData(gd_combo);
		fBranch.setItems(branchesRef);

		Label lblMessage = new Label(parent, SWT.LEFT);
		lblMessage.setText(Messages.CherryPickDialog_2);
		GridData gridlbl = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 3);
		gridlbl.verticalIndent = 20;
		lblMessage.setLayoutData(gridlbl);

		ScrolledComposite sc_msgtxt = new ScrolledComposite(parent, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		sc_msgtxt.setExpandHorizontal(true);
		sc_msgtxt.setExpandVertical(true);
		GridData grid = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);

		msgTextData = new Text(sc_msgtxt, SWT.WRAP | SWT.MULTI);
		sc_msgtxt.setLayoutData(grid);
		sc_msgtxt.setContent(msgTextData);
		msgTextData.setText(commitMessage);

		//Create the bottom section for the buttons
		createBottomButtons(parent);

		parent.getShell().setMinimumSize(500, 300);

		return parent;
	}

	private void addContentProposal(Combo combo) {
		IContentProposalProvider cp = new IContentProposalProvider() {
			@Override
			public IContentProposal[] getProposals(String contents, int position) {
				List<IContentProposal> resultList = new ArrayList<>();

				for (final String branch : branchesRef) {
					if (branch.indexOf(contents) != -1) {
						resultList.add(new ContentProposal(branch));
					}
				}

				return resultList.toArray(new IContentProposal[resultList.size()]);
			}
		};

		ContentProposalAdapter adapter = new ContentProposalAdapter(combo, new ComboContentAdapter(), cp, null, null);
		// set the acceptance style to always replace the complete content
		adapter.setProposalAcceptanceStyle(ContentProposalAdapter.PROPOSAL_REPLACE);
	}

	/**
	 * @param parent
	 */
	private void createBottomButtons(Composite parent) {
		Label separator = new Label(parent, SWT.HORIZONTAL | SWT.SEPARATOR);
		GridData sepGrid = new GridData(GridData.FILL_HORIZONTAL);
		sepGrid.grabExcessHorizontalSpace = true;
		separator.setLayoutData(sepGrid);

		buttonComposite = new Composite(parent, SWT.NONE);
		GridLayout gridLayout = new GridLayout(4, false);
		buttonComposite.setLayout(gridLayout);

		GridData gd_button = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd_button.grabExcessHorizontalSpace = true;
		buttonComposite.setLayoutData(gd_button);

		//Callback handle by the okPressed()
		createButton(buttonComposite, IDialogConstants.OK_ID, Messages.CherryPickDialog_3, false);

		cancel = createButton(buttonComposite, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, true);
		GridData gd_cancel = new GridData(SWT.RIGHT, SWT.CENTER, false, false);
		gd_cancel.grabExcessHorizontalSpace = true;
		cancel.setLayoutData(gd_cancel);
	}

	@Override
	protected void okPressed() {
		//Fill the data structure
		fBranchText = fBranch.getText();
		fMessageText = msgTextData.getText();

		super.okPressed();
	}

	/**
	 * This method returns the message text.
	 *
	 * @return String
	 */
	public String getMessage() {
		return fMessageText;
	}

	/**
	 * This method returns the branch text.
	 *
	 * @return String
	 */
	public String getBranch() {
		return fBranchText;
	}

}
