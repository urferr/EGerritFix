/*******************************************************************************
 * Copyright (c) 2016 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson - initial API and implementation
 ******************************************************************************/
package org.eclipse.egerrit.internal.dashboard.ui.completion;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.egerrit.internal.core.EGerritCorePlugin;
import org.eclipse.egerrit.internal.core.GerritClient;
import org.eclipse.egerrit.internal.core.command.ListProjectsCommand;
import org.eclipse.egerrit.internal.core.exception.EGerritException;
import org.eclipse.egerrit.internal.dashboard.ui.utils.UIUtils;
import org.eclipse.egerrit.internal.model.ProjectInfo;

public class ProjectNameCompleter extends ParamCompleter {

	public ProjectNameCompleter() {
		super(new String[] { SearchContentProposalProvider.PROJECT, SearchContentProposalProvider.PARENT_PROJECT });
	}

	@Override
	public SearchContentProposal[] requestCompletionList(String lastWord, String query, GerritClient client) {
		if (lastWord.length() < 3) {
			return null;
		}

		ListProjectsCommand command = client.listProjects();
		command.setMaxNumberOfResults(20);
		command.setQuery(lastWord);

		Map<String, ProjectInfo> res = null;
		try {
			res = command.call();
		} catch (EGerritException e) {
			EGerritCorePlugin.logError(client.getRepository().formatGerritVersion() + e.getMessage());
		}

		if (res == null) {
			return null;
		}
		List<SearchContentProposal> proposals = new ArrayList<>();
		for (Entry<String, ProjectInfo> info : res.entrySet()) {
			String projectName;
			String fullProjectDescription;
			projectName = info.getKey();
			String description = info.getValue().getDescription();
			if (description != null && !description.isEmpty()) {
				fullProjectDescription = projectName + " (" + description + ")"; //$NON-NLS-1$ //$NON-NLS-2$
			} else {
				fullProjectDescription = projectName;
			}
			proposals.add(new SearchContentProposal(
					query.substring(0, query.length() - lastWord.length()) + UIUtils.quoteIfNeeded(projectName),
					fullProjectDescription));
		}

		return proposals.toArray(new SearchContentProposal[proposals.size()]);
	}
}
