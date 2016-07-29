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

import org.eclipse.egerrit.internal.core.EGerritCorePlugin;
import org.eclipse.egerrit.internal.core.GerritClient;
import org.eclipse.egerrit.internal.core.GerritRepository;
import org.eclipse.egerrit.internal.core.command.SuggestAccountCommand;
import org.eclipse.egerrit.internal.core.exception.EGerritException;
import org.eclipse.egerrit.internal.dashboard.ui.utils.UIUtils;
import org.eclipse.egerrit.internal.model.AccountInfo;

public class AccountCompleter extends ParamCompleter {

	public AccountCompleter() {
		super(new String[] { SearchContentProposalProvider.OWNER, SearchContentProposalProvider.REVIEWER,
				SearchContentProposalProvider.COMMENT_BY, SearchContentProposalProvider.REVIEWED_BY,
				SearchContentProposalProvider.AUTHOR, SearchContentProposalProvider.COMMITTER,
				SearchContentProposalProvider.FROM });
	}

	@Override
	public SearchContentProposal[] requestCompletionList(String lastWord, String query, GerritClient client) {
		List<SearchContentProposal> proposals = new ArrayList<>();
		// Add "self" as a possible user if logged in
		GerritRepository currentRepository = client.getRepository();
		if (SearchContentProposalProvider.SELF.startsWith(lastWord)
				&& !currentRepository.getServerInfo().isAnonymous()) {
			proposals.add(new SearchContentProposal(
					query.substring(0, query.length() - lastWord.length()) + SearchContentProposalProvider.SELF,
					SearchContentProposalProvider.SELF));
		}

		if (lastWord.length() >= 3) {
			// Only query the server until we have at least three characters
			SuggestAccountCommand command = client.suggestAccount();
			command.setMaxNumberOfResults(20);
			command.setQuery(lastWord);

			AccountInfo[] res = null;
			try {
				res = command.call();
			} catch (EGerritException e) {
				EGerritCorePlugin.logError(client.getRepository().formatGerritVersion() + e.getMessage());
			}

			if (res != null) {
				for (AccountInfo info : res) {
					String idString;
					idString = info.getName() + " <" + info.getEmail() //$NON-NLS-1$
							+ ">"; //$NON-NLS-1$
					proposals.add(new SearchContentProposal(
							query.substring(0, query.length() - lastWord.length()) + UIUtils.quoteIfNeeded(idString),
							idString));
				}
			}
		}

		return proposals.toArray(new SearchContentProposal[proposals.size()]);
	}
}
