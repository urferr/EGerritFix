/*******************************************************************************
 * Copyright (c) 2013-2016 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jacques Bouthillier - Initial Implementation of the plug-in utility
 ******************************************************************************/

package org.eclipse.egerrit.internal.dashboard.ui.utils;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.egerrit.internal.dashboard.ui.GerritUi;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

/**
 * This class implements the Gerrit Dashboard UI utility.
 *
 * @since 1.0
 */

public class UIUtils {

	private final static String EGERRIT_PREF = "org.eclipse.egerrit.prefs"; //$NON-NLS-1$

	private final static int TITLE_LENGTH = 75;

	/**
	 * Method showErrorDialog.
	 *
	 * @param String
	 *            message
	 * @param String
	 *            reason
	 */
	public static void showErrorDialog(String aMsg, String aReason) {
		final ErrorDialog dialog = new ErrorDialog(null, Messages.UIUtils_dashboardInfo, aMsg,
				new Status(IStatus.INFO, GerritUi.PLUGIN_ID, 0, aReason, null), IStatus.INFO);
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				dialog.open();
			}
		});
	}

	public static void showNoServerMessage() {
		final ErrorDialog dialog = new ErrorDialog(null, Messages.UIUtils_dashboardInfo, null,
				new Status(IStatus.INFO, GerritUi.PLUGIN_ID,
						Messages.UIUtils_configureMessage),
				IStatus.INFO);
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				dialog.open();
			}
		});
	}

	public static String quoteIfNeeded(String s) {
		if (!s.trim().matches("^\\S*$")) { //$NON-NLS-1$
			if (s.endsWith(" ")) { //$NON-NLS-1$
				return "\"" + s.trim() + "\" "; //$NON-NLS-1$ //$NON-NLS-2$
			} else {
				return "\"" + s + "\""; //$NON-NLS-1$ //$NON-NLS-2$
			}
		}
		return s;
	}

	/**
	 * Show a confirmation dialog to the end-user until the toggle is selected
	 *
	 * @param key
	 * @param shell
	 * @param title
	 * @param value
	 * @return
	 */
	public static int showConfirmDialog(String key, Shell shell, String title, String value) {
		Preferences prefs = ConfigurationScope.INSTANCE.getNode(EGERRIT_PREF);

		Preferences editorPrefs = prefs.node(key);
		boolean choice = editorPrefs.getBoolean(key, false);

		if (choice) {
			return SWT.CANCEL;
		}

		//Keep the title length to TITLE_LENGTH characters max
		if (title.length() > TITLE_LENGTH) {
			title = title.substring(0, (TITLE_LENGTH - 3)).concat("..."); //$NON-NLS-1$
		}

		MessageDialogWithToggle dialog = MessageDialogWithToggle.openOkCancelConfirm(shell, title, value,
				Messages.UIUtils_dontShowAgain, false, null, null);

		if (dialog.getToggleState()) {
			editorPrefs.putBoolean(key, true);
			try {
				editorPrefs.flush();
			} catch (BackingStoreException e) {
				//There is not much we can do
			}
		}
		return dialog.getReturnCode();
	}

}
