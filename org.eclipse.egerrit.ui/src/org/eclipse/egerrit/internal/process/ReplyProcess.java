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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;

import org.eclipse.egerrit.internal.core.EGerritCorePlugin;
import org.eclipse.egerrit.internal.core.GerritClient;
import org.eclipse.egerrit.internal.core.command.SetReviewCommand;
import org.eclipse.egerrit.internal.core.exception.EGerritException;
import org.eclipse.egerrit.internal.core.rest.ReviewInput;
import org.eclipse.egerrit.internal.model.ChangeInfo;
import org.eclipse.egerrit.internal.model.LabelInfo;
import org.eclipse.egerrit.internal.model.RevisionInfo;
import org.eclipse.egerrit.internal.ui.editors.QueryHelpers;
import org.eclipse.egerrit.internal.ui.utils.Messages;
import org.eclipse.egerrit.internal.ui.utils.UIUtils;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.swt.widgets.Shell;

/**
 * This class handle all aspect of the Reply operation
 */
public class ReplyProcess {

	private static final String CODE_REVIEW = "Code-Review"; //$NON-NLS-1$

	private ChangeInfo changeInfo = null;

	private GerritClient gerritClient = null;

	/**
	 * The constructor.
	 */
	public ReplyProcess() {
	}

	public void handleReplyDialog(Shell shell, ChangeInfo changeInfo, GerritClient gerritClient,
			RevisionInfo selectedRevision) {
		UIUtils.replyToChange(shell, selectedRevision, null, gerritClient, false);
		CompletableFuture.runAsync(() -> QueryHelpers.loadBasicInformation(gerritClient, changeInfo))
				.thenRun(() -> changeInfo.setUserSelectedRevision(selectedRevision));
	}

	public void handleReplyPlus2(RevisionInfo selectedRevision) {
		ReviewInput reviewInput = new ReviewInput();
		reviewInput.setDrafts(ReviewInput.DRAFT_PUBLISH);
		Map<String, String> obj = new HashMap<String, String>();
		obj.put(CODE_REVIEW, Messages.ReplyProcess_PlusTwo);

		reviewInput.setLabels(obj);
		postReply(reviewInput, selectedRevision);
	}

	/**
	 * Test if we should allow the CR +2 button to be available
	 *
	 * @param changeInfo
	 * @param gerritClient
	 * @return boolean
	 */
	public boolean isCRPlusTwoAllowed(ChangeInfo changeInfo, GerritClient gerritClient, RevisionInfo revision) {
		//Test if we should allow the +2 button or not
		//Condition:
		//     - User is a committer (maxCRDefined == maxCRPermitted)
		//     - If user permitted, the maxCRPermitted > 0 if not Abandoned
		//     - Selected patch set is the latest
		//     - User cannot submit yet
		this.changeInfo = changeInfo;
		this.gerritClient = gerritClient;

		int maxCRPermitted = findMaxPermitted(CODE_REVIEW);
		int maxCRDefined = findMaxDefinedLabelValue(CODE_REVIEW);
		boolean allowPlus2 = false;
		if (maxCRDefined == maxCRPermitted && maxCRPermitted > 0) {
			allowPlus2 = true;
		}
		//Verify the patchset if we are allowed only, no need to check if not allowed
		if (allowPlus2) {
			String psSelectedID = revision.getId();
			if (!(psSelectedID != null && changeInfo.getLatestPatchSet().getId().compareTo(psSelectedID) == 0)) {
				allowPlus2 = false;
			}
		}
		return (!revision.isSubmitable() && allowPlus2);
	}

	private int findMaxPermitted(String label) {
		int maxPermitted = 0;
		EList<String> listPermitted = null;
		EMap<String, EList<String>> mapLabels = changeInfo.getPermitted_labels();
		if (mapLabels != null) {
			Iterator<Entry<String, EList<String>>> iterator = mapLabels.entrySet().iterator();
			//Get the structure having all the possible options
			while (iterator.hasNext()) {
				Entry<String, EList<String>> permittedlabel = iterator.next();
				listPermitted = permittedlabel.getValue();
				if (permittedlabel.getKey().compareTo(label) == 0) {
					for (String element2 : listPermitted) {
						maxPermitted = Math.max(maxPermitted, new Integer(element2.trim()));
					}
				}
			}
		}
		return maxPermitted;
	}

	private int findMaxDefinedLabelValue(String label) {
		Iterator<Entry<String, LabelInfo>> iterator = changeInfo.getLabels().entrySet().iterator();
		//Get the structure having all the possible options
		int maxDefined = 0;
		while (iterator.hasNext()) {
			Entry<String, LabelInfo> definedlabel = iterator.next();
			if (definedlabel.getKey().compareTo(label) == 0) {
				for (String element2 : definedlabel.getValue().getValues().keySet()) {
					maxDefined = Math.max(maxDefined, new Integer(element2.trim()));
				}
			}
		}
		return maxDefined;
	}

	/**
	 * Post the reply information to the Gerrit server
	 *
	 * @param reviewInput
	 */
	private void postReply(ReviewInput reviewInput, RevisionInfo selectedRevision) {
		if (changeInfo != null && gerritClient != null) {
			SetReviewCommand reviewToEmit = gerritClient.setReview(changeInfo.getId(), selectedRevision.getId());
			reviewToEmit.setCommandInput(reviewInput);

			try {
				reviewToEmit.call();
				CompletableFuture.runAsync(() -> QueryHelpers.loadBasicInformation(gerritClient, changeInfo))
						.thenRun(() -> changeInfo.setUserSelectedRevision(selectedRevision));//keep the user working revision active
			} catch (EGerritException e1) {
				EGerritCorePlugin.logError(gerritClient.getRepository().formatGerritVersion() + e1.getMessage());
			}
		}
	}

}
