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
import org.eclipse.egerrit.core.GerritFactory;
import org.eclipse.egerrit.core.GerritRepository;
import org.eclipse.egerrit.core.GerritServerInformation;
import org.eclipse.egerrit.core.exception.EGerritException;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.osgi.framework.Version;

/**
 * @author Jacques Bouthillier
 * @since 1.0
 */
public class GerritServerDialog extends Dialog {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	private final int fWIDTH = 450;

	private final int fHEIGHT = 275;

	private final String DIALOG_TITLE = "Gerrit Server Selection";

	private final String fOkTooltip = "Save the new Gerrit Server information";

	private final String fCancelTooltip = "Do not save the information";

	private final String HEADER = "Enter the http(s) URL of your Gerrit server, and the username and password to connect to it.\n\nThe URL you need to enter is the same than the one you use to connect to your Gerrit server on the web.";

	private final String URL_TOOLTIP = "For example, on Eclipse Gerrit server is https://git.eclipse.org/r/. Another way to determine the URL is to start from the URL of a change (e.g https://git.eclipse.org/r/#/c/58066/) and use as a URL the part up to the #.";

	private final String URL_EXAMPLE_TOOLTIP = "Example: https://git.eclipse.org/r";

	private final String SHORTNAME_TOOLTIP = "Name to show in the Gerrit Server list";

	private final String SHORTNAME_EXAMPLE_TOOLTIP = "Example: Eclipse or Foundation";

	private final String INVALID_MESSAGE = "URL is NOT valid";

	private final static String TITLE = "Gerrit selection";

	private final String WANT_TO_SAVE = "The information provided doesn't allow a connection to the Gerrit server at the moment,\n"
			+ " do you still want to save it ?";

	// ------------------------------------------------------------------------
	// Variables
	// ------------------------------------------------------------------------

	private Text txtServerName;

	private Text txtServerURL;

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
		// Set the minimum size for the window
		composite.getShell().setMinimumSize(fWIDTH, fHEIGHT);

		buildDialog(composite);

		return composite;
	}

	private void buildDialog(Composite composite) {
		//Header comments
		Label labelComments = new Label(composite, SWT.WRAP);
		GridData labelData = new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1);
		labelComments.setLayoutData(labelData);
		labelComments.setText(HEADER);
		labelComments.setToolTipText(URL_TOOLTIP);

		//URL
		Label labelServerURI = new Label(composite, SWT.NONE);
		labelServerURI.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
		labelServerURI.setText("URL:");
		labelServerURI.setToolTipText(URL_TOOLTIP);

		txtServerURL = new Text(composite, SWT.BORDER);
		txtServerURL.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		txtServerURL.setToolTipText(URL_EXAMPLE_TOOLTIP);
		txtServerURL.setText(workingCopy != null ? workingCopy.getServerURI() : ""); //$NON-NLS-1$
		txtServerURL.addFocusListener(serverURIFocusListener());

		//Server Name
		Label labelServerName = new Label(composite, SWT.NONE);
		labelServerName.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
		labelServerName.setText("Label:");
		labelServerName.setToolTipText(SHORTNAME_EXAMPLE_TOOLTIP);

		txtServerName = new Text(composite, SWT.BORDER);
		txtServerName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		txtServerName.setToolTipText(SHORTNAME_TOOLTIP);
		txtServerName.setText(workingCopy != null ? workingCopy.getName() : ""); //$NON-NLS-1$
		txtServerName.addModifyListener(serverNameListener());

		Label separatorServerInfoUserInfo = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
		separatorServerInfoUserInfo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		Label labelUser = new Label(composite, SWT.NONE);
		labelUser.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
		labelUser.setText("User:");

		txtUserName = new Text(composite, SWT.BORDER);
		txtUserName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		txtUserName.setText(workingCopy != null ? workingCopy.getUserName() : ""); //$NON-NLS-1$
		txtUserName.addModifyListener(userListener());

		Label labelPassword = new Label(composite, SWT.NONE);
		labelPassword.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
		labelPassword.setText("Password:");

		txtPassword = new Text(composite, SWT.BORDER | SWT.PASSWORD);
		txtPassword.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		txtPassword.setText(workingCopy == null ? "" : (workingCopy.isPasswordProvided() ? "********" : "")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		txtPassword.addModifyListener(passwdListener());

		//Listener to validate settings when enter key is pressed
		composite.addTraverseListener(keyTraversedListener());
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

	private ModifyListener serverNameListener() {
		return new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				isServerInfoReady();
				workingCopy.setServerName(((Text) e.widget).getText());
				txtServerURL.setText(workingCopy.getServerURI());
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
					txtServerName.setText(workingCopy.getName());
					txtUserName.setText(workingCopy.getUserName());
				}
			}
		};
	}

	private TraverseListener keyTraversedListener() {
		return new TraverseListener() {
			@Override
			public void keyTraversed(TraverseEvent e) {
				if (e.keyCode == SWT.CR) {
					validateSettings();
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
			validateSettings();
		}

		// Cancel Button selected
		if (aButtonId == IDialogConstants.CANCEL_ID) {
			workingCopy = original;
			super.setReturnCode(IDialogConstants.CANCEL_ID);
			super.close();
		}
	}

	/**
	 * Validate the settings entered by the user. Display an invalid message if the url is not valid.
	 */
	private void validateSettings() {
		try {
			if (validateURL()) {
				if (validConnection()) {
					super.setReturnCode(IDialogConstants.OK_ID);
					super.okPressed();
				} else {
					//Either off line or Invalid data provided
					if (getServerInfo().getHostId().isEmpty()) {
						Utils.displayInformation(null, TITLE, INVALID_MESSAGE);
					} else {
						boolean bool = Utils.queryInformation(null, TITLE, WANT_TO_SAVE);
						if (bool) {
							super.setReturnCode(IDialogConstants.OK_ID);
							super.okPressed();
						}
					}
				}
			} else {
				Utils.displayInformation(null, DIALOG_TITLE, INVALID_MESSAGE);
			}
		} catch (URISyntaxException e) {
			Utils.displayInformation(shell, DIALOG_TITLE, e.getLocalizedMessage());
		} catch (EGerritException e) {
			Utils.displayInformation(shell, DIALOG_TITLE, e.getLocalizedMessage());
		}
	}

	private boolean validConnection() throws EGerritException {
		// Initialize
		GerritRepository repo;
		boolean connexionSuccesfull = false;
		try {
			repo = new GerritRepository(workingCopy.getServerURI());
			// Run test connection

			if (!repo.getHostname().isEmpty()) {
				connexionSuccesfull = repo.connect();
			}
		} catch (EGerritException e) {
			return false;
		}
		if (!connexionSuccesfull && repo.getStatus() == GerritRepository.SSL_PROBLEM) {
			throw new EGerritException("A problem occurred establishing the SSL connection with "
					+ workingCopy.getServerURI() + ". Please see log file for more details.");
		}
		if (!connexionSuccesfull && repo.getStatus() == GerritRepository.SSL_INVALID_ROOT_CERTIFICATE) {
			throw new EGerritException("A problem occurred establishing the SSL connection with "
					+ workingCopy.getServerURI()
					+ ". The trust chain of the provided certificate could not be verified. Please see log file for more details.");
		}

		if (!connexionSuccesfull) {
			throw new EGerritException("Server " + workingCopy.getServerURI() + " : " + INVALID_MESSAGE);
		}

		//Test the Version if it is a valid Gerrit Server
		Version version = repo.getVersion();
		if (version.equals(GerritRepository.NO_VERSION)) {
			throw new EGerritException(
					"The server you are connecting to is older than 2.8 and this tool can not connect to it. This tool can only connect to server that are more recent than "
							+ GerritFactory.MINIMAL_VERSION + ".");
		} else if (version.compareTo(GerritFactory.MINIMAL_VERSION) < 0) {
			throw new EGerritException("Server " + workingCopy.getServerURI() + " runs version " + repo.getVersion()
					+ " which is older than the minimum " + GerritFactory.MINIMAL_VERSION + " supported by EGerrit.");
		}

		//Second pass to verify with the available credentials
		String password = workingCopy.getPassword();
		if (workingCopy.isPasswordProvided()) {
			if (workingCopy.isPasswordChanged()) {
				password = workingCopy.getPassword();
			} else {
				password = original.getPassword();
				workingCopy.setPassword(password);
			}
		}
		repo.setCredentials(new GerritCredentials(workingCopy.getUserName(), password));
		// Run test connection with a user if provided

		if (!repo.getHostname().isEmpty()) {
			connexionSuccesfull = repo.connect();
			if (!connexionSuccesfull) {
				//Test self signed automatically if needed
				boolean bo = true;
				workingCopy.setSelfSigned(bo);
				repo.acceptSelfSignedCerts(bo);
				connexionSuccesfull = repo.connect();
			}
		}
		if (repo.getHttpClient() == null) {
			throw new EGerritException(
					"Server [ " + workingCopy.getServerURI() + " ] has an invalid User and/or password ");
		}

		return connexionSuccesfull;
	}

	private Boolean validateURL() throws URISyntaxException {
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
