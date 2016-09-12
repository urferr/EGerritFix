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

import org.eclipse.egerrit.internal.core.EGerritCorePlugin;
import org.eclipse.egerrit.internal.core.GerritClient;
import org.eclipse.egerrit.internal.core.command.CherryPickRevisionCommand;
import org.eclipse.egerrit.internal.core.command.ListBranchesCommand;
import org.eclipse.egerrit.internal.core.exception.EGerritException;
import org.eclipse.egerrit.internal.core.rest.CherryPickInput;
import org.eclipse.egerrit.internal.model.BranchInfo;
import org.eclipse.egerrit.internal.model.ChangeInfo;
import org.eclipse.egerrit.internal.ui.editors.CherryPickDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * This class handle all aspect of the CherryPick operation to another branch
 */
public class CherryPickProcess {

	private GerritClient fGerritClient = null;

	private ChangeInfo fChangeInfo = null;

	/**
	 * The constructor.
	 */
	public CherryPickProcess() {
	}

	public void handleCherryPick(Shell shell, GerritClient gerritClient, ChangeInfo changeInfo) {
		fGerritClient = gerritClient;
		fChangeInfo = changeInfo;
		BranchInfo[] listBranchesCmdResult = listBranches();

		List<String> listBranchesRef = new ArrayList<String>();
		Iterator<BranchInfo> it = Arrays.asList(listBranchesCmdResult).iterator();
		while (it.hasNext()) {
			listBranchesRef.add(it.next().getRef());
		}

		final CherryPickDialog cherryPickDialog = new CherryPickDialog(shell, listBranchesRef,
				fChangeInfo.getUserSelectedRevision().getCommit().getMessage());
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				int ret = cherryPickDialog.open();
				if (ret == IDialogConstants.OK_ID) {
					cherryPickRevision(fChangeInfo.getId(), fChangeInfo.getUserSelectedRevision().getId(),
							cherryPickDialog.getBranch(), cherryPickDialog.getMessage());
				}
			}
		});
	}

	private ChangeInfo cherryPickRevision(String changeId, String revisionId, String branch, String message) {
		CherryPickRevisionCommand cherryPickCmd = fGerritClient.cherryPickRevision(changeId, revisionId);
		CherryPickInput cherryPickInput = new CherryPickInput();
		cherryPickInput.setDestination(branch);
		cherryPickInput.setMessage(message);

		cherryPickCmd.setCommandInput(cherryPickInput);
		ChangeInfo listBranchesCmdResult = null;
		try {
			listBranchesCmdResult = cherryPickCmd.call();
		} catch (EGerritException e3) {
			EGerritCorePlugin.logError(fGerritClient.getRepository().formatGerritVersion() + e3.getMessage());
		}
		return listBranchesCmdResult;
	}

	private BranchInfo[] listBranches() {
		ListBranchesCommand listBranchesCmd = fGerritClient.listBranches(fChangeInfo.getProject());

		BranchInfo[] listBranchesCmdResult = null;
		try {
			listBranchesCmdResult = listBranchesCmd.call();
		} catch (EGerritException e3) {
			EGerritCorePlugin.logError(fGerritClient.getRepository().formatGerritVersion() + e3.getMessage());
		}
		return listBranchesCmdResult;
	}

}
