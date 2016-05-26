/*******************************************************************************
 * Copyright (c) 2013 Ericsson
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
import org.eclipse.egerrit.internal.dashboard.ui.GerritUi;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.widgets.Display;

/**
 * This class implements the Gerrit Dashboard UI utility.
 *
 * @since 1.0
 */

public class UIUtils {

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
						org.eclipse.egerrit.internal.dashboard.ui.views.Messages.GerritTableView_defineRepository),
				IStatus.INFO);
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				dialog.open();
			}
		});
	}

}
