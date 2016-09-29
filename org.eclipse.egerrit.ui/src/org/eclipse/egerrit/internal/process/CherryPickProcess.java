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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.eclipse.egerrit.internal.core.EGerritCorePlugin;
import org.eclipse.egerrit.internal.core.GerritClient;
import org.eclipse.egerrit.internal.core.command.CherryPickRevisionCommand;
import org.eclipse.egerrit.internal.core.command.ListBranchesCommand;
import org.eclipse.egerrit.internal.core.exception.EGerritException;
import org.eclipse.egerrit.internal.core.rest.CherryPickInput;
import org.eclipse.egerrit.internal.model.BranchInfo;
import org.eclipse.egerrit.internal.model.ChangeInfo;
import org.eclipse.egerrit.internal.model.RevisionInfo;
import org.eclipse.egerrit.internal.ui.editors.CherryPickDialog;
import org.eclipse.egerrit.internal.ui.editors.QueryHelpers;
import org.eclipse.egerrit.internal.ui.utils.Messages;
import org.eclipse.egerrit.internal.ui.utils.UIUtils;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * This class handle all aspect of the CherryPick operation to another branch
 */
public class CherryPickProcess {

	private GerritClient fGerritClient = null;

	/**
	 * The constructor.
	 */
	public CherryPickProcess() {
	}

	public void handleCherryPick(Shell shell, GerritClient gerritClient, ChangeInfo changeInfo, RevisionInfo revision) {
		fGerritClient = gerritClient;
		BranchInfo[] listBranchesCmdResult = listBranches(changeInfo);

		List<String> listBranchesRef = new ArrayList<String>();
		Iterator<BranchInfo> it = Arrays.asList(listBranchesCmdResult).iterator();
		while (it.hasNext()) {
			listBranchesRef.add(it.next().getRef());
		}

		final CherryPickDialog cherryPickDialog = new CherryPickDialog(shell, listBranchesRef,
				revision.getCommit().getMessage());
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				boolean isDone = false;
				while (!isDone) {
					Object cherryPickCmdResult = ""; //$NON-NLS-1$
					int ret = cherryPickDialog.open();
					if (ret == IDialogConstants.OK_ID) {
						cherryPickCmdResult = cherryPickRevision(changeInfo.getId(), revision.getId(),
								cherryPickDialog.getBranch(), cherryPickDialog.getMessage());
						if (cherryPickCmdResult instanceof String) {
							MessageDialog.open(MessageDialog.ERROR, null, Messages.CherryPickRevision_9,
									(String) cherryPickCmdResult, SWT.NONE);
						} else {
							ChangeInfo cherryPickedChangeInfo = (ChangeInfo) cherryPickCmdResult;
							if (cherryPickedChangeInfo.getId().equals(changeInfo.getId())) {
								CompletableFuture
										.runAsync(() -> QueryHelpers.loadBasicInformation(gerritClient, changeInfo))
										.thenRun(() -> changeInfo.setUserSelectedRevision(changeInfo.getRevision()));
							} else {
								UIUtils.openAnotherEditor(cherryPickedChangeInfo, fGerritClient);
							}
							isDone = true;
						}
					} else {
						isDone = true;
					}
				}
			}
		});

	}

	private Object cherryPickRevision(String changeId, String revisionId, String branch, String message) {
		CherryPickRevisionCommand cherryPickCmd = fGerritClient.cherryPickRevision(changeId, revisionId);
		CherryPickInput cherryPickInput = new CherryPickInput();
		cherryPickInput.setDestination(branch);
		cherryPickInput.setMessage(message);

		cherryPickCmd.setCommandInput(cherryPickInput);
		ChangeInfo cherryPickResult = null;
		String failureReason = ""; //$NON-NLS-1$
		try {
			cherryPickResult = cherryPickCmd.call();
			if (cherryPickResult == null) {
				return cherryPickCmd.getFailureReason();
			} else {
				return cherryPickResult;
			}
		} catch (EGerritException e3) {
			EGerritCorePlugin.logError(fGerritClient.getRepository().formatGerritVersion() + e3.getMessage());
		}
		return failureReason;
	}

	private BranchInfo[] listBranches(ChangeInfo changeInfo) {
		ListBranchesCommand listBranchesCmd = fGerritClient.listBranches(changeInfo.getProject());

		BranchInfo[] listBranchesCmdResult = null;
		try {
			listBranchesCmdResult = listBranchesCmd.call();
		} catch (EGerritException e3) {
			EGerritCorePlugin.logError(fGerritClient.getRepository().formatGerritVersion() + e3.getMessage());
		}
		return listBranchesCmdResult;
	}

}
