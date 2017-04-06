/*******************************************************************************
 * Copyright (c) 2017 Ericsson AB.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson - initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.internal.ui.editors;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.eclipse.egerrit.internal.core.GerritClient;
import org.eclipse.egerrit.internal.model.ActionConstants;
import org.eclipse.egerrit.internal.model.ChangeInfo;
import org.eclipse.egerrit.internal.process.ReplyProcess;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.PlatformUI;

/**
 * Code to handle the dynamic reply labels associated to a review for a specific user
 */
public class ReplyHandler extends Action {

	private GerritClient fGerritClient;

	private ChangeInfo fChangeInfo;

	private String fLabelText;

	private int maxValue;

	public ReplyHandler(ChangeInfo changeInfo, GerritClient gerritClient, String labelText) {
		this.fGerritClient = gerritClient;
		this.fChangeInfo = changeInfo;
		String actionLabel = labelText;
		if (labelText != ReplyProcess.REPLY_ALL_BUTTONS && labelText != ActionConstants.REPLY.getLiteral()) {
			maxValue = fChangeInfo.getPermittedMaxValue(labelText);
			actionLabel = labelText + "  +" + maxValue; //$NON-NLS-1$
		}
		this.fLabelText = labelText;
		this.setText(actionLabel);
	}

	@Override
	public void run() {
		ReplyProcess replyProcess = new ReplyProcess();
		TreeMap<String, Integer> mapLabels = new TreeMap<>();

		if (fLabelText.equals(ActionConstants.REPLY.getLiteral())) {
			//Deal with the reply dialog
			replyProcess.handleReplyDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), fChangeInfo,
					fGerritClient, fChangeInfo.getUserSelectedRevision(), null);
			return;
		} else if (fLabelText.equals(ReplyProcess.REPLY_ALL_BUTTONS)) {
			//Adjust the list of labels which the current user can set to a maximum value
			String loginUser = fGerritClient.getRepository().getServerInfo().getUserName();
			Map<String, Integer> labelsToSet = fChangeInfo.getLabelsNotAtMax(loginUser);

			//Loop to include all the potential labels
			Iterator<Entry<String, Integer>> iter = labelsToSet.entrySet().iterator();
			while (iter.hasNext()) {
				Entry<String, Integer> entry = iter.next();
				mapLabels.put(entry.getKey(), entry.getValue());
			}
		} else {
			//Selecting a specific label
			mapLabels.put(fLabelText, maxValue);
		}
		if (!mapLabels.isEmpty()) {
			replyProcess.handleReplyVotes(fChangeInfo, mapLabels, fGerritClient);
		}
	}
}
