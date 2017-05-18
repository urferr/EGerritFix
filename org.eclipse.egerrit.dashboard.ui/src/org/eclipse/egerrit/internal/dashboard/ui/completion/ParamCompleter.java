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

abstract class ParamCompleter {

	private String[] fApplicableStrings;

	ParamCompleter(String[] applicableStrings) {
		fApplicableStrings = applicableStrings.clone();
	}

	boolean isApplicable(String query) {
		String applicableStr = getApplicableString(query);
		return applicableStr != null && query.length() > applicableStr.length();
	}

	private String getApplicableString(String query) {
		for (String applicableStr : fApplicableStrings) {
			if (query.startsWith(applicableStr)) {
				return applicableStr;
			}
		}
		return null;
	}

	SearchContentProposal[] suggest(String lastWord, String query, GerritClient client) {
		String applicableStr = getApplicableString(lastWord);
		if (applicableStr != null) {
			return requestCompletionList(lastWord.substring(applicableStr.length()), query, client);
		}
		return null;
	}

	/**
	 * @return The completion list. Returns null if a list is not available; in a such a case, the caller can decide to
	 *         retain the previous completion list.
	 */
	abstract SearchContentProposal[] requestCompletionList(String lastWord, String query, GerritClient client);
}
