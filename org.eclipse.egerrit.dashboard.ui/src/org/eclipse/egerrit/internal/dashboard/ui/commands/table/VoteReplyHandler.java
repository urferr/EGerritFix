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

package org.eclipse.egerrit.internal.dashboard.ui.commands.table;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egerrit.internal.model.ChangeInfo;
import org.eclipse.egerrit.internal.process.ReplyProcess;

/**
 * This handler is used to manipulate the dynamic menu items related to the reply option
 */
public class VoteReplyHandler extends DashboardFactoryHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		TreeMap<String, Integer> mapLabels = fillMapMenuLabels(
				event.getParameter("org.eclipse.egerrit.dashboard.ui.vote.label"), //$NON-NLS-1$
				event.getParameter("org.eclipse.egerrit.dashboard.ui.vote.value"), //$NON-NLS-1$
				getChangeInfo());

		if (!mapLabels.isEmpty()) {
			ReplyProcess replyProcess = new ReplyProcess();
			replyProcess.handleReplyVotes(getChangeInfo(), mapLabels, getGerritClient());
		}
		return null;
	}

	/**
	 * Fill the map with selected label from the dynamic reply menu
	 *
	 * @param label
	 * @param value
	 * @param changeInfo
	 * @return TreeMap
	 */
	private TreeMap<String, Integer> fillMapMenuLabels(String label, String value, ChangeInfo changeInfo) {
		TreeMap<String, Integer> mapLabels = new TreeMap<>();

		if (label.equals(ReplyProcess.REPLY_ALL_BUTTONS)) {
			String loginUser = getGerritClient().getRepository().getServerInfo().getUserName();
			Map<String, Integer> labelsToSet = changeInfo.getLabelsNotAtMax(loginUser);

			//Loop to include all the potential buttons
			Iterator<Entry<String, Integer>> iter = labelsToSet.entrySet().iterator();
			while (iter.hasNext()) {
				Entry<String, Integer> entry = iter.next();
				mapLabels.put(entry.getKey(), entry.getValue());
			}
		} else {
			mapLabels.put(label, Integer.parseInt(value));
		}
		return mapLabels;
	}
}
