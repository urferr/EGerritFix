/*******************************************************************************
 * Copyright (c) 2015 Ericsson.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson - initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.internal.dashboard.ui.preferences;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

/**
 * @author Jacques Bouthillier
 * @since 1.0
 */
public class Utils {

	/**
	 * The default constructor. Do not allow to build an object of this class
	 */
	private Utils() {
	}

	public static void displayInformation(final Shell shell, final String title, final String message) {
		PlatformUI.getWorkbench().getDisplay().syncExec(() -> MessageDialog.openInformation(shell, title, message));
	}

	private static boolean queryReturn;

	/**
	 * Message box asking a question and waiting for an answer
	 *
	 * @param shell
	 * @param title
	 * @param message
	 * @return boolean
	 */
	static boolean queryInformation(final Shell shell, final String title, final String message) {
		PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
			public void run() {
				boolean b = MessageDialog.openQuestion(shell, title, message);
				setValue(b);
			}

			private void setValue(boolean b) {
				queryReturn = b;
			}
		});
		return queryReturn;
	}

}
