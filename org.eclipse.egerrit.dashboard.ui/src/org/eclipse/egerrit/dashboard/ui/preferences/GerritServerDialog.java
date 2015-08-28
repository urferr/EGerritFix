/*******************************************************************************
 * Copyright (c) 2014 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jacques Bouthillier - Initial Implementation
 ******************************************************************************/
package org.eclipse.egerrit.dashboard.ui.preferences;

import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.egerrit.core.GerritCredentials;
import org.eclipse.egerrit.core.GerritRepository;
import org.eclipse.egerrit.core.GerritServerInformation;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * @author Jacques Bouthillier
 * @since 1.0
 */
public class GerritServerDialog extends Dialog {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	private final int fWIDTH = 450;

	private final int fHEIGHT = 250;

	private final String DIALOG_TITLE = "Gerrit Server Selection";

	private final String fOkTooltip = "Save the new Gerrit Server information";

	private final String fCancelTooltip = "Do not save the information";

	private final String fValidateTooltip = "Validate the URI format";

	private final String PORT_TOOLTIP = "-1: port not defined, so the default port is understood";

	private final String PATH_TOOLTIP = "Use / to append a path. ex /r";

	private final String SCHEME_TOOLTIP = "http or https"; //$NON-NLS-1$

	private final String INVALID_MESSAGE = "URI is NOT valid";

	private final String PORT_INVALID = "Port should be a number";

	private final static String TITLE = "Gerrit selection";

	private final String WANT_TO_SAVE = "The information provided doesn't allow a connection to the Gerrit server at the moment,\n"
			+ " do you still want to save it ?";

	// ------------------------------------------------------------------------
	// Variables
	// ------------------------------------------------------------------------

	private Text txtScheme;

	private Text txtHostId;

	private Text txtPath;

	private Text txtServerName;

	private Text txtServerURI;

	private Text txtPort;

	private Text txtUserName;

	private Text txtPassword;

	private Shell shell;

	private static Button ok;

	private static Button cancel;

	private static Button validate;

	//The working workingCopy the dialog works against
	private GerritServerInformation workingCopy;

	//The original version passed in
	private GerritServerInformation original;

	/**
	 * Construct a new dialog, optionally displaying information of a server
	 */
	public GerritServerDialog(Shell shell, GerritServerInformation originalInfo) {
		super(shell);
		setShellStyle(SWT.DIALOG_TRIM | SWT.RESIZE);
		original = originalInfo;
		if (originalInfo != null) {
			this.workingCopy = originalInfo.clone();
		}
	}

	/**
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite aParent) {
		getShell().setText(DIALOG_TITLE);
		Composite composite = (Composite) super.createDialogArea(aParent);
		GridLayout layout = new GridLayout(2, false);
		layout.marginRight = 5;
		layout.marginLeft = 5;
		composite.setLayout(layout);

		buildDialog(composite);

		// Set the minimum size for the window
		composite.getShell().setMinimumSize(fWIDTH, fHEIGHT);

		return composite;
	}

	private void buildDialog(Composite composite) {
		//Grid data used by all LABEL data entry
		GridData gridDataLBL = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);

		//Grid data used by all text data entry
		GridData gridDataText = new GridData(SWT.LEFT, SWT.CENTER, true, false);
		gridDataText.horizontalAlignment = GridData.FILL;

		//URI
		Label labelServerURI = new Label(composite, SWT.NONE);
		labelServerURI.setLayoutData(gridDataLBL);
		labelServerURI.setText("URI:");

		txtServerURI = new Text(composite, SWT.BORDER);
		txtServerURI.setLayoutData(gridDataText);
		txtServerURI.setText(workingCopy != null ? workingCopy.getServerURI() : ""); //$NON-NLS-1$
		txtServerURI.addFocusListener(serverURIFocusListener());

		//Server Name
		Label labelServerName = new Label(composite, SWT.NONE);
		labelServerName.setLayoutData(gridDataLBL);
		labelServerName.setText("Shortname:");

		txtServerName = new Text(composite, SWT.BORDER);
		txtServerName.setLayoutData(gridDataText);
		txtServerName.setText(workingCopy != null ? workingCopy.getName() : ""); //$NON-NLS-1$
		txtServerName.addModifyListener(serverNameListener());

		Label separatorServerInfoUserInfo = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
		GridData gridDataSep = new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1);
		gridDataSep.horizontalAlignment = SWT.FILL;
		separatorServerInfoUserInfo.setLayoutData(gridDataSep);

		Label labelUser = new Label(composite, SWT.NONE);
		labelUser.setLayoutData(gridDataLBL);
		labelUser.setText("User:");

		txtUserName = new Text(composite, SWT.BORDER);
		txtUserName.setLayoutData(gridDataText);
		txtUserName.setText(workingCopy != null ? workingCopy.getUserName() : ""); //$NON-NLS-1$
		txtUserName.addModifyListener(userListener());

		Label labelPassword = new Label(composite, SWT.NONE);
		labelPassword.setLayoutData(gridDataLBL);
		labelPassword.setText("Password:");

		txtPassword = new Text(composite, SWT.BORDER | SWT.PASSWORD);
		txtPassword.setLayoutData(gridDataText);
		txtPassword.setText(workingCopy == null ? "" : (workingCopy.isPasswordProvided() ? "********" : "")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		txtPassword.addModifyListener(passwdListener());

		Label separatorUserInfoServerDetails = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
		GridData gridDataSep2 = new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1);
		gridDataSep2.horizontalAlignment = SWT.FILL;
		separatorUserInfoServerDetails.setLayoutData(gridDataSep2);

		//Scheme
		Label labelScheme = new Label(composite, SWT.NONE);
		labelScheme.setLayoutData(gridDataLBL);
		labelScheme.setText("Scheme:");
		labelScheme.setToolTipText(SCHEME_TOOLTIP);

		txtScheme = new Text(composite, SWT.BORDER);
		txtScheme.setLayoutData(gridDataText);
		txtScheme.setText(workingCopy != null ? workingCopy.getScheme() : ""); //$NON-NLS-1$
		txtScheme.addModifyListener(serverSchemeListener());

		//Host ID
		Label labelHost = new Label(composite, SWT.NONE);
		labelHost.setLayoutData(gridDataLBL);
		labelHost.setText("Host Id:");

		txtHostId = new Text(composite, SWT.BORDER);
		txtHostId.setLayoutData(gridDataText);
		txtHostId.setText(workingCopy != null ? workingCopy.getHostId() : ""); //$NON-NLS-1$
		txtHostId.addModifyListener(serverIdListener());

		//Server Port
		Label labelPort = new Label(composite, SWT.NONE);
		labelPort.setLayoutData(gridDataLBL);
		labelPort.setText("Port:");
		labelPort.setToolTipText(PORT_TOOLTIP);

		txtPort = new Text(composite, SWT.BORDER);
		txtPort.setLayoutData(gridDataText);
		txtPort.setText(workingCopy != null ? Integer.toString(workingCopy.getPort()) : ""); //$NON-NLS-1$
		txtPort.addModifyListener(serverPortListener());

		//Server Path
		Label labelPath = new Label(composite, SWT.NONE);
		labelPath.setLayoutData(gridDataLBL);
		labelPath.setText("Server Path:");
		labelPath.setToolTipText(PATH_TOOLTIP);

		txtPath = new Text(composite, SWT.BORDER);
		txtPath.setLayoutData(gridDataText);
		txtPath.setText(workingCopy != null ? workingCopy.getPath() : ""); //$NON-NLS-1$
		txtPath.addModifyListener(serverPathListener());
	}

	private void isServerInfoReady() {
		if (workingCopy == null) {
			try {
				workingCopy = new GerritServerInformation(null, null);
			} catch (URISyntaxException e1) {
				Utils.displayInformation(shell, DIALOG_TITLE, e1.getLocalizedMessage());
			}
		}
	}

	//Listener for each field
	private ModifyListener serverSchemeListener() {
		return new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				isServerInfoReady();
				workingCopy.setScheme(((Text) e.widget).getText());
				txtServerURI.setText(workingCopy.getServerURI());
			}

		};
	}

	private ModifyListener serverIdListener() {
		return new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				isServerInfoReady();
				workingCopy.setHostId(((Text) e.widget).getText());
				txtServerURI.setText(workingCopy.getServerURI());
			}
		};
	}

	private ModifyListener serverPortListener() {
		return new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				isServerInfoReady();
				String st = (((Text) e.widget).getText()).trim();
				if (!st.isEmpty()) {
					if ((st.startsWith("-") && st.length() > 1) || !st.startsWith("-")) { //$NON-NLS-1$ //$NON-NLS-2$
						try {
							workingCopy.setPort(Integer.parseInt(st));
							txtServerURI.setText(workingCopy.getServerURI());
						} catch (NumberFormatException e2) {
							Utils.displayInformation(shell, DIALOG_TITLE, e2.getLocalizedMessage());
						}
					} else if (!st.startsWith("-")) { //$NON-NLS-1$
						Utils.displayInformation(shell, DIALOG_TITLE, PORT_INVALID);
					}
				}
			}
		};
	}

	private ModifyListener serverPathListener() {
		return new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				isServerInfoReady();
				workingCopy.setPath(((Text) e.widget).getText());
				txtServerURI.setText(workingCopy.getServerURI());
			}
		};
	}

	private ModifyListener serverNameListener() {
		return new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				isServerInfoReady();
				workingCopy.setServerName(((Text) e.widget).getText());
				txtServerURI.setText(workingCopy.getServerURI());
			}
		};
	}

	private ModifyListener userListener() {
		return new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				isServerInfoReady();
				workingCopy.setUserName(((Text) e.widget).getText());
			}
		};
	}

	private ModifyListener passwdListener() {
		return new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				isServerInfoReady();
				workingCopy.setPassword(((Text) e.widget).getText());
			}
		};
	}

	private FocusListener serverURIFocusListener() {
		return new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				// ignore
			}

			@Override
			public void focusLost(FocusEvent e) {
				String text = ((Text) e.widget).getText().trim();
				if (workingCopy == null) {
					try {
						workingCopy = new GerritServerInformation(text, txtServerName.getText().trim());
					} catch (URISyntaxException e1) {
						Utils.displayInformation(shell, DIALOG_TITLE, e1.getLocalizedMessage());
						return;
					}
				}

				URI uri = null;
				try {
					uri = new URI(text);
				} catch (URISyntaxException e1) {
					workingCopy.setServerURI(text);
					Utils.displayInformation(shell, DIALOG_TITLE, e1.getLocalizedMessage());

				}
				if (uri != null) {
					workingCopy.setSeverInfo(uri);
					//Update the fields with the computed data
					txtScheme.setText(workingCopy.getScheme());
					txtHostId.setText(workingCopy.getHostId());
					txtPath.setText(workingCopy.getPath());
					txtServerName.setText(workingCopy.getName());
					txtPort.setText(Integer.toString(workingCopy.getPort()));
					txtUserName.setText(workingCopy.getUserName());
				}
			}
		};
	}

	@Override
	protected void createButtonsForButtonBar(Composite aParent) {
		ok = createButton(aParent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, false);
		cancel = createButton(aParent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
		ok.setToolTipText(fOkTooltip);
		cancel.setToolTipText(fCancelTooltip);
	}

	@Override
	protected void buttonPressed(int aButtonId) {
		// Ok button selected
		if (aButtonId == IDialogConstants.OK_ID) {
			try {
				if (validateURI()) {
					if (validConnection()) {
						super.setReturnCode(IDialogConstants.OK_ID);
						super.okPressed();
					} else {
						//Either off line or Invalid data provided
						boolean bool = Utils.queryInformation(null, TITLE, WANT_TO_SAVE);
						if (bool) {
							super.setReturnCode(IDialogConstants.OK_ID);
							super.okPressed();
						}
					}
				} else {
					Utils.displayInformation(null, DIALOG_TITLE, INVALID_MESSAGE);
				}
			} catch (URISyntaxException e) {
				Utils.displayInformation(shell, DIALOG_TITLE, e.getLocalizedMessage());
			}
		}

		// Cancel Button selected
		if (aButtonId == IDialogConstants.CANCEL_ID) {
			workingCopy = original;
			super.setReturnCode(IDialogConstants.CANCEL_ID);
			super.close();
		}
	}

	private boolean validConnection() {
		// Initialize
		GerritRepository repo = new GerritRepository(workingCopy.getScheme(), workingCopy.getHostId(),
				workingCopy.getPort(), workingCopy.getPath());
		repo.setCredentials(new GerritCredentials(workingCopy.getUserName(), workingCopy.getPassword()));
		// Run test connection
		boolean b = repo.connect();
		if (!b) {
			//Test self signed automatically if needed
			boolean bo = true;
			workingCopy.setSelfSigned(bo);
			repo.acceptSelfSignedCerts(bo);
			b = repo.connect();
		}
		return b;
	}

	private Boolean validateURI() throws URISyntaxException {
		Boolean b = true;
		if (workingCopy != null) {
			if (!workingCopy.isValid()) {
				b = false;
			}
		} else {
			b = false;
		}
		return b;
	}

	/**
	 * Returns the server information that has been filed in.
	 */
	public GerritServerInformation getServerInfo() {
		return workingCopy;
	}
}
