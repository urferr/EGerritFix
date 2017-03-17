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
import org.eclipse.egerrit.internal.model.RevisionInfo;
import org.eclipse.egerrit.internal.ui.editors.QueryHelpers;
import org.eclipse.egerrit.internal.ui.utils.Messages;
import org.eclipse.egerrit.internal.ui.utils.UIUtils;
import org.eclipse.swt.widgets.Shell;

/**
 * This class handle all aspect of the Reply operation
 */
public class ReplyProcess {

	public static final String REPLY_ALL_BUTTONS = Messages.ReplyProcess_AllButtons;

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
		CompletableFuture.runAsync(() -> QueryHelpers.loadBasicInformation(gerritClient, changeInfo, false))
				.thenRun(() -> changeInfo.setUserSelectedRevision(selectedRevision));
	}

	/**
	 * Handles the reply votes set to its maximum
	 *
	 * @param changeInfo
	 * @param allowedButton
	 * @param gerritClient
	 */
	public void handleReplyVotes(ChangeInfo changeInfo, Map<String, Integer> allowedButton, GerritClient gerritClient) {
		this.gerritClient = gerritClient;
		this.changeInfo = changeInfo;
		RevisionInfo selectedRevision = changeInfo.getRevision();
		ReviewInput reviewInput = new ReviewInput();
		reviewInput.setDrafts(ReviewInput.DRAFT_PUBLISH);
		Map<String, Integer> obj = new HashMap<String, Integer>();

		//Loop to include all the potential buttons
		Iterator<Entry<String, Integer>> iter = allowedButton.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, Integer> entry = iter.next();
			obj.put(entry.getKey(), entry.getValue());
		}

		reviewInput.setLabels(obj);
		postReply(reviewInput, selectedRevision);
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
				CompletableFuture.runAsync(() -> QueryHelpers.loadBasicInformation(gerritClient, changeInfo, false))
						.thenRun(() -> changeInfo.setUserSelectedRevision(selectedRevision));//keep the user working revision active
			} catch (EGerritException e1) {
				EGerritCorePlugin.logError(gerritClient.getRepository().formatGerritVersion() + e1.getMessage());
			}
		}
	}

}
