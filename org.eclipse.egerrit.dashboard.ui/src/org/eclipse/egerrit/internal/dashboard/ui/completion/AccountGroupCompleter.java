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

import org.eclipse.egerrit.internal.core.GerritClient;

public class AccountGroupCompleter extends ParamCompleter {
	public AccountGroupCompleter() {
		super(new String[] { SearchContentProposalProvider.OWNER_IN, SearchContentProposalProvider.REVIEWER_IN });
	}

	@Override
	public SearchContentProposal[] requestCompletionList(String lastWord, String query, GerritClient client) {
		//TODO
		return null;
	}
}
