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

package org.eclipse.egerrit.internal.ui.editors;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.egerrit.internal.core.EGerritCorePlugin;
import org.eclipse.egerrit.internal.core.GerritClient;
import org.eclipse.egerrit.internal.model.ChangeInfo;
import org.eclipse.egerrit.internal.model.RevisionInfo;
import org.eclipse.egerrit.internal.ui.table.model.BranchMatch;
import org.eclipse.egerrit.internal.ui.utils.ActiveWorkspaceRevision;
import org.eclipse.egerrit.internal.ui.utils.Messages;
import org.eclipse.egit.ui.internal.dialogs.CheckoutConflictDialog;
import org.eclipse.egit.ui.internal.fetch.FetchGerritChangeWizard;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jgit.api.CheckoutCommand;
import org.eclipse.jgit.api.CheckoutResult;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class CheckoutRevision extends Action {

	private RevisionInfo revision;

	private GerritClient gerritClient;

	private ChangeInfo changeInfo;

	public CheckoutRevision(RevisionInfo revision, GerritClient gerritClient) {
		this.revision = revision;
		this.gerritClient = gerritClient;
		this.changeInfo = this.revision.getChangeInfo();
		setText(Messages.CheckoutRevision_0);
	}

	@Override
	public void run() {
		Repository localRepo = new FindLocalRepository(gerritClient, changeInfo.getProject()).getRepository();

		if (localRepo == null) {
			Status status = new Status(IStatus.ERROR, EGerritCorePlugin.PLUGIN_ID, Messages.CheckoutRevision_1);
			ErrorDialog.openError(getShell(), Messages.CheckoutRevision_2, Messages.CheckoutRevision_3, status);
			return;
		}

		String psSelected = revision.getRef();
		if ((psSelected == null) || psSelected.isEmpty()) {
			Status status = new Status(IStatus.ERROR, EGerritCorePlugin.PLUGIN_ID, Messages.CheckoutRevision_4);
			ErrorDialog.openError(getShell(), Messages.CheckoutRevision_2, Messages.CheckoutRevision_3, status);
		}

		Map<String, BranchMatch> potentialBranches = findAllPotentialBranches(localRepo);
		if (potentialBranches.size() > 1) {
			branchUiSelection(localRepo, potentialBranches);
		} else {
			if (potentialBranches.isEmpty()) {
				//New selected
				FetchGerritChangeWizard var = new FetchGerritChangeWizard(localRepo, psSelected);
				WizardDialog w = new WizardDialog(getShell(), var);
				w.create();
				w.open();
			} else if (!potentialBranches.entrySet().iterator().next().getValue().equals(BranchMatch.PERFECT_MATCH)) {
				//Only one branch exist, but it is not the perfect match, so we need to allow the end-user to choose
				branchUiSelection(localRepo, potentialBranches);
			} else {
				String branchToCheckout = potentialBranches.keySet().iterator().next();//Get the only element PERFECT_MATCH

				try {
					checkoutBranch(branchToCheckout, localRepo);
				} catch (Exception e) {
				}
			}
		}
		ActiveWorkspaceRevision.getInstance().activateCurrentRevision(gerritClient,
				changeInfo.getUserSelectedRevision());
	}

	private Map<String, BranchMatch> findAllPotentialBranches(Repository localRepo) {
		Git gitRepo = new Git(localRepo);
		String changeIdKey = "Change-Id"; //$NON-NLS-1$
		//Map <Key,value> = Map<Short branch name, commit id>
		Map<String, String> mapBranches = new HashMap<String, String>();
		Map<String, BranchMatch> potentialBranches = null;
		//Map <Key,Map<keycommit, ListChangeIdvalue> = Map<Short branch name, commit id, list of changeId>
		Map<String, Map<String, List<String>>> mapBranchesChangeId = new HashMap<String, Map<String, List<String>>>();
		try (RevWalk walk = new RevWalk(localRepo)) {
			mapBranchNameWithCommitId(gitRepo, changeIdKey, mapBranches, mapBranchesChangeId, walk);
			//Get only potential branches
			potentialBranches = mapPotentialBranch(mapBranchesChangeId);
		} catch (

		GitAPIException e1) {
			e1.printStackTrace();
		}
		gitRepo.close();
		return potentialBranches;
	}

	private void mapBranchNameWithCommitId(Git gitRepo, String changeIdKey, Map<String, String> mapBranches,
			Map<String, Map<String, List<String>>> mapBranchesChangeId, RevWalk walk) throws GitAPIException {
		for (Ref current : gitRepo.branchList().call()) {
			RevCommit commit;
			try {
				commit = walk.parseCommit(current.getObjectId());
				mapBranches.put(Repository.shortenRefName(current.getName()), commit.getName());

				//Map branch -> commitId -> List of changeId
				Map<String, List<String>> mapCommitChangeid = new HashMap<String, List<String>>();
				List<String> footerLines = commit.getFooterLines(changeIdKey);

				mapCommitChangeid.put(commit.getName(), footerLines);
				mapBranchesChangeId.put(Repository.shortenRefName(current.getName()), mapCommitChangeid);
			} catch (IOException e) {
				EGerritCorePlugin.logError(gerritClient.getRepository().formatGerritVersion() + e.getMessage());
			}
		}
	}

	/**
	 * Open the dialog to let the user selects his options
	 *
	 * @param localRepo
	 * @param potentialBranches
	 */
	private void branchUiSelection(Repository localRepo, Map<String, BranchMatch> potentialBranches) {
		BranchSelectionDialog branchSelectDialog = new BranchSelectionDialog(null, potentialBranches, changeInfo);
		int result = branchSelectDialog.open();
		String selectedBranch = branchSelectDialog.getSelectedBranch();
		if (result == IDialogConstants.OK_ID) {
			//New selected
			String psSelected = revision.getRef();
			FetchGerritChangeWizard var = new FetchGerritChangeWizard(localRepo, psSelected);
			WizardDialog w = new WizardDialog(getShell(), var);
			w.create();
			w.open();
		} else if (result == IDialogConstants.CLIENT_ID) { // SWITCH
			try {
				if (selectedBranch != null) {
					checkoutBranch(selectedBranch, localRepo);
				}
			} catch (Exception e) {
			}
		}
	}

	private Map<String, BranchMatch> mapPotentialBranch(Map<String, Map<String, List<String>>> mapBranchesChangeId) {
		String lookingChangeId = revision.getChangeInfo().getChange_id().trim();
		String lookingCommitIdForRevision = revision.getCommit().getCommit().trim();
		Map<String, BranchMatch> mapBranches = new TreeMap<String, BranchMatch>();
		String defaultBranchName = changeInfo.get_number() + "/" //$NON-NLS-1$
				+ changeInfo.getUserSelectedRevision().get_number();
		Iterator<Entry<String, Map<String, List<String>>>> iterBranch = mapBranchesChangeId.entrySet().iterator();
		while (iterBranch.hasNext()) {
			Entry<String, Map<String, List<String>>> entryBranch = iterBranch.next();
			Map<String, List<String>> mapCommitId = entryBranch.getValue();
			Iterator<Entry<String, List<String>>> iteratorcommitId = mapCommitId.entrySet().iterator();
			while (iteratorcommitId.hasNext()) {
				Entry<String, List<String>> entryCommitIds = iteratorcommitId.next();
				List<String> listChangeIds = entryCommitIds.getValue();
				Iterator<String> iterChangeId = listChangeIds.iterator();
				while (iterChangeId.hasNext()) {
					String changeId = iterChangeId.next().trim();
					if (lookingCommitIdForRevision.equals(entryCommitIds.getKey())) {
						mapBranches.put(entryBranch.getKey(), BranchMatch.PERFECT_MATCH);//Perfect match branch with commit Id
						continue;
					}
					if (lookingChangeId.equals(changeId)) {
						mapBranches.put(entryBranch.getKey(), BranchMatch.CHANGE_ID_MATCH);//Potential branch for this changeId, but with some modification on the branch
						continue;
					}
					if (entryBranch.getKey().contains(defaultBranchName)) {
						mapBranches.put(entryBranch.getKey(), BranchMatch.BRANCH_NAME_MATCH);//Perfect match branch
						continue;
					}
				}
			}
		}
		return mapBranches;
	}

	private void checkoutBranch(String branchName, Repository repo) throws Exception {
		CheckoutCommand command = null;
		try (Git gitRepo = new Git(repo)) {
			command = gitRepo.checkout();
			command.setCreateBranch(false);
			command.setName(branchName);
			command.setForce(false);
			command.call();
		} catch (Throwable t) {
			CheckoutResult result = command.getResult();
			new CheckoutConflictDialog(Display.getDefault().getActiveShell(), repo, result.getConflictList()).open();
		}
	}

	private Shell getShell() {
		return Display.getDefault().getActiveShell();
	}
}
