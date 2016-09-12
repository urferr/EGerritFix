/*******************************************************************************
 * Copyright (c) 2016 Ericsson AB.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson - initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.internal.process;

import java.util.concurrent.CompletableFuture;

import org.eclipse.egerrit.internal.core.EGerritCorePlugin;
import org.eclipse.egerrit.internal.core.GerritClient;
import org.eclipse.egerrit.internal.core.command.SubmitCommand;
import org.eclipse.egerrit.internal.core.exception.EGerritException;
import org.eclipse.egerrit.internal.core.rest.SubmitInput;
import org.eclipse.egerrit.internal.model.ChangeInfo;
import org.eclipse.egerrit.internal.ui.editors.QueryHelpers;
import org.eclipse.egerrit.internal.ui.utils.Messages;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;

/**
 * This class handle all aspect of the Submit operation
 */
public class SubmitProcess {

	public void handleSubmit(ChangeInfo changeInfo, GerritClient gerritClient) {
		SubmitCommand submitCmd = gerritClient.submit(changeInfo.getId());
		SubmitInput submitInput = new SubmitInput();
		submitInput.setWait_for_merge(true);

		submitCmd.setCommandInput(submitInput);

		try {
			submitCmd.call();
			//After a submit, we reload and reset the user selected revision
			//Note that here we are not using the model loader because we want a synchronous call so we can set the user selection
			CompletableFuture.runAsync(() -> QueryHelpers.loadBasicInformation(gerritClient, changeInfo))
					.thenRun(() -> changeInfo.setUserSelectedRevision(changeInfo.getRevision()));
		} catch (EGerritException e3) {
			EGerritCorePlugin.logError(gerritClient.getRepository().formatGerritVersion() + e3.getMessage());
			MessageDialog.open(MessageDialog.INFORMATION, null, Messages.SubmitProcess_failed, e3.getLocalizedMessage(),
					SWT.NONE);
		}

	}

}
