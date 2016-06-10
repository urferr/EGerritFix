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
package org.eclipse.egerrit.internal.dashboard.ui.preferences;

import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.egerrit.internal.core.GerritCredentials;
import org.eclipse.egerrit.internal.core.GerritFactory;
import org.eclipse.egerrit.internal.core.GerritRepository;
import org.eclipse.egerrit.internal.core.GerritServerInformation;
import org.eclipse.egerrit.internal.core.exception.EGerritException;
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
import org.eclipse.swt.widgets.ProgressBar;
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

	private final String DIALOG_TITLE = Messages.GerritServerDialog_0;

	private final String fOkTooltip = Messages.GerritServerDialog_1;

	private final String fCancelTooltip = Messages.GerritServerDialog_2;

	private final String HEADER = Messages.GerritServerDialog_3;

	private final String URL_TOOLTIP = Messages.GerritServerDialog_4;

	private final String URL_EXAMPLE_TOOLTIP = Messages.GerritServerDialog_5;

	private final String SHORTNAME_TOOLTIP = Messages.GerritServerDialog_6;

	private final String SHORTNAME_EXAMPLE_TOOLTIP = Messages.GerritServerDialog_7;

	private final String INVALID_MESSAGE = Messages.GerritServerDialog_8;

	private final static String TITLE = Messages.GerritServerDialog_9;

	private final String WANT_TO_SAVE = Messages.GerritServerDialog_10 + Messages.GerritServerDialog_11;

	// ------------------------------------------------------------------------
	// Variables
	// ------------------------------------------------------------------------

	private Text txtServerName;

	private Text txtServerURL;

	private Text txtUserName;

	private Text txtPassword;

	private Shell shell;

	private Button ok;

	private Button cancel;

	//The working workingCopy the dialog works against
	private GerritServerInformation workingCopy;

	//The original version passed in
	private GerritServerInformation original;

	private ProgressBar progressBar;

	GerritServerDialog instance = null;

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
		this.instance = this;
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
		labelServerURI.setText(Messages.GerritServerDialog_12);
		labelServerURI.setToolTipText(URL_TOOLTIP);

		txtServerURL = new Text(composite, SWT.BORDER);
		txtServerURL.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		txtServerURL.setToolTipText(URL_EXAMPLE_TOOLTIP);
		txtServerURL.setText(workingCopy != null ? workingCopy.getServerURI() : ""); //$NON-NLS-1$
		txtServerURL.addFocusListener(serverURIFocusListener());

		//Server Name
		Label labelServerName = new Label(composite, SWT.NONE);
		labelServerName.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
		labelServerName.setText(Messages.GerritServerDialog_13);
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
		labelUser.setText(Messages.GerritServerDialog_14);

		txtUserName = new Text(composite, SWT.BORDER);
		txtUserName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		txtUserName.setText(workingCopy != null ? workingCopy.getUserName() : ""); //$NON-NLS-1$
		txtUserName.addModifyListener(userListener());

		Label labelPassword = new Label(composite, SWT.NONE);
		labelPassword.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
		labelPassword.setText(Messages.GerritServerDialog_15);

		txtPassword = new Text(composite, SWT.BORDER | SWT.PASSWORD);
		txtPassword.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		txtPassword.setText(workingCopy == null ? "" : (workingCopy.isPasswordProvided() ? "********" : "")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		txtPassword.addModifyListener(passwdListener());

		Label separator = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
		separator.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		progressBar = new ProgressBar(composite, SWT.NONE);
		progressBar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		progressBar.setMinimum(0);
		progressBar.setMaximum(100);
		progressBar.setVisible(false);

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
			progressBar.setSelection(0); //Init the progress bar before any actions
			progressBar.setVisible(true);
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
		final Job job = new Job(Messages.GerritServerDialog_30) {

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				// Set total number of work units
				monitor.beginTask(Messages.GerritServerDialog_30, 100);
				try {
					isServerInfoReady();
					if (validateURL(monitor)) {
						completeURLValidation(validConnection(monitor));
					} else {
						if (!ok.isDisposed()) {
							Utils.displayInformation(null, DIALOG_TITLE, INVALID_MESSAGE);
						}
					}
				} catch (URISyntaxException e) {
					setProgress(monitor, 100);
					if (!ok.isDisposed()) {
						//The dialogue is still present, so job is still running
						Utils.displayInformation(shell, DIALOG_TITLE, e.getLocalizedMessage());
					}
				} catch (EGerritException e) {
					setProgress(monitor, 100);
					if (!ok.isDisposed()) {
						//The dialogue is still present, so job is still running
						Utils.displayInformation(shell, DIALOG_TITLE, e.getLocalizedMessage());
					}
				} finally {
					monitor.done();
				}
				return Status.OK_STATUS;
			}
		};
		job.setUser(false);
		job.schedule();
	}

	private void completeURLValidation(boolean b) {
		if (b) {
			super.setReturnCode(IDialogConstants.OK_ID);
			if (!ok.isDisposed()) {
				ok.getDisplay().syncExec(new Runnable() {
					public void run() {
						instance.okPressed();
					}
				});
			}
		} else {

			//Either off line or Invalid data provided
			if (getServerInfo().getHostId().isEmpty()) {
				Utils.displayInformation(null, TITLE, INVALID_MESSAGE);
			} else {
				boolean bool = Utils.queryInformation(null, TITLE, WANT_TO_SAVE);
				if (bool) {
					super.setReturnCode(IDialogConstants.OK_ID);
					if (!ok.isDisposed()) {
						ok.getDisplay().syncExec(new Runnable() {
							public void run() {
								instance.okPressed();
							}
						});
					}
				}
			}
		}
	}

	private void setProgress(IProgressMonitor monitor, int value) {
		monitor.worked(value);
		if (!progressBar.isDisposed()) {
			progressBar.getDisplay().asyncExec(new Runnable() {
				public void run() {
					if (!progressBar.isDisposed()) {
						int toset = progressBar.getSelection() + value;
						if (toset >= 100) {
							progressBar.setVisible(false);
						}
						progressBar.setSelection(toset);
					}
				}
			});
		}
	}

	private boolean validConnection(IProgressMonitor monitor) throws EGerritException {
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
		setProgress(monitor, 10);
		if (!connexionSuccesfull && repo.getStatus() == GerritRepository.SSL_PROBLEM) {
			setProgress(monitor, 100);
			monitor.done();
			throw new EGerritException(
					Messages.GerritServerDialog_16 + workingCopy.getServerURI() + Messages.GerritServerDialog_17);
		}
		if (!connexionSuccesfull && repo.getStatus() == GerritRepository.SSL_INVALID_ROOT_CERTIFICATE) {
			setProgress(monitor, 100);
			monitor.done();
			throw new EGerritException(
					Messages.GerritServerDialog_18 + workingCopy.getServerURI() + Messages.GerritServerDialog_19);
		}

		setProgress(monitor, 10);
		if (!connexionSuccesfull) {
			setProgress(monitor, 100);
			monitor.done();
			throw new EGerritException(Messages.GerritServerDialog_20 + workingCopy.getServerURI()
					+ Messages.GerritServerDialog_21 + INVALID_MESSAGE);
		}

		setProgress(monitor, 10);
		//Test the Version if it is a valid Gerrit Server
		Version version = repo.getVersion();
		if (version.equals(GerritRepository.NO_VERSION)) {
			setProgress(monitor, 100);
			monitor.done();
			throw new EGerritException(
					Messages.GerritServerDialog_22 + GerritFactory.MINIMAL_VERSION + Messages.GerritServerDialog_23);
		} else if (version.compareTo(GerritFactory.MINIMAL_VERSION) < 0) {
			setProgress(monitor, 100);
			monitor.done();
			throw new EGerritException(Messages.GerritServerDialog_24 + workingCopy.getServerURI()
					+ Messages.GerritServerDialog_25 + repo.getVersion() + Messages.GerritServerDialog_26
					+ GerritFactory.MINIMAL_VERSION + Messages.GerritServerDialog_27);
		}

		setProgress(monitor, 10);
		//Second pass to verify with the available credentials
		if (workingCopy != null) {
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
		}

		// Run test connection with a user if provided
		setProgress(monitor, 10);
		if (!repo.getHostname().isEmpty()) {
			connexionSuccesfull = repo.connect();
			if (!connexionSuccesfull) {
				//Test self signed automatically if needed
				boolean bo = true;
				if (workingCopy != null) {
					workingCopy.setSelfSigned(bo);
				}
				repo.acceptSelfSignedCerts(bo);
				connexionSuccesfull = repo.connect();
			}
		}
		setProgress(monitor, 10);
		if (repo.getHttpClient() == null) {
			setProgress(monitor, 100);
			monitor.done();
			String value = workingCopy == null ? "" : workingCopy.getServerURI(); //$NON-NLS-1$
			throw new EGerritException(Messages.GerritServerDialog_28 + value + Messages.GerritServerDialog_29);
		}

		return connexionSuccesfull;
	}

	private Boolean validateURL(IProgressMonitor monitor) throws URISyntaxException {
		Boolean b = true;
		setProgress(monitor, 10);
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
