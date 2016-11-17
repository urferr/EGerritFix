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
import org.eclipse.egerrit.internal.core.command.RebaseRevisionCommand;
import org.eclipse.egerrit.internal.core.exception.EGerritException;
import org.eclipse.egerrit.internal.core.rest.RebaseInput;
import org.eclipse.egerrit.internal.model.ChangeInfo;
import org.eclipse.egerrit.internal.model.RevisionInfo;
import org.eclipse.egerrit.internal.ui.editors.QueryHelpers;
import org.eclipse.egerrit.internal.ui.editors.RefreshRelatedEditors;
import org.eclipse.egerrit.internal.ui.utils.Messages;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

/**
 * This class handle all aspect of the Rebase operation
 */
public class RebaseProcess {

	/**
	 * The constructor.
	 */
	public RebaseProcess() {
	}

	public void handleRebase(Shell shell, ChangeInfo changeInfo, RevisionInfo toRebase, GerritClient gerritClient) {
		InputDialog inputDialog = new InputDialog(shell, Messages.RebaseProcess_title,
				Messages.RebaseProcess_changeParent, "", null) { //$NON-NLS-1$

			@Override
			protected void createButtonsForButtonBar(Composite parent) {
				super.createButtonsForButtonBar(parent);
				getText().addModifyListener(new ModifyListener() {
					@Override
					public void modifyText(ModifyEvent e) {
						if (!toRebase.isRebaseable()) {
							if (!getText().getText().isEmpty()) {
								getOkButton().setEnabled(true);
							} else {
								getOkButton().setEnabled(false);
								getOkButton().getParent().setToolTipText(
										toRebase.isRebaseable() ? "" : Messages.RebaseProcess_changeIsAlreadyUpToDate); //$NON-NLS-1$
							}
						}
					}
				});
				getOkButton().setEnabled(toRebase.isRebaseable());
				getOkButton().getParent()
						.setToolTipText(toRebase.isRebaseable() ? "" : Messages.RebaseProcess_changeIsAlreadyUpToDate); //$NON-NLS-1$
				return;
			}
		};

		if (inputDialog.open() != Window.OK) {
			return;
		}
		RebaseRevisionCommand rebaseCmd = gerritClient.rebase(changeInfo.getId(), toRebase.getId());
		RebaseInput rebaseInput = new RebaseInput();
		rebaseInput.setBase(inputDialog.getValue().trim().length() == 0 ? null : inputDialog.getValue().trim());

		rebaseCmd.setCommandInput(rebaseInput);

		try {
			rebaseCmd.call();

			//After a rebase, we reload and reset the user selected revision
			//Note that here we are not using the model loader because we want a synchronous call so we can set the user selection
			CompletableFuture.runAsync(() -> QueryHelpers.loadBasicInformation(gerritClient, changeInfo, false))
					.thenRun(() -> changeInfo.setUserSelectedRevision(changeInfo.getRevision()));

			new RefreshRelatedEditors(changeInfo, gerritClient).schedule();
		} catch (EGerritException e1) {
			if (e1.getCode() == EGerritException.SHOWABLE_MESSAGE) {
				MessageDialog.open(MessageDialog.INFORMATION, null, Messages.RebaseProcess_failed,
						Messages.RebaseProcess_notPerform, SWT.NONE);
			} else {
				EGerritCorePlugin.logError(gerritClient.getRepository().formatGerritVersion() + e1.getMessage());
			}
		}
	}

}
