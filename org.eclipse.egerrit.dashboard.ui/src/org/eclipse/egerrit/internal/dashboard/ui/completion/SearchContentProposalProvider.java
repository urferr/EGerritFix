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

import org.eclipse.egerrit.internal.core.GerritClient;
import org.eclipse.jface.fieldassist.IContentProposal;
import org.eclipse.jface.fieldassist.IContentProposalProvider;

public class SearchContentProposalProvider implements IContentProposalProvider {

	static final String SELF = "self"; //$NON-NLS-1$

	static final String REVIEWER_IN = "reviewerin:"; //$NON-NLS-1$

	static final String OWNER_IN = "ownerin:"; //$NON-NLS-1$

	static final String FROM = "from:"; //$NON-NLS-1$

	static final String COMMITTER = "committer:"; //$NON-NLS-1$

	static final String AUTHOR = "author:"; //$NON-NLS-1$

	static final String REVIEWED_BY = "reviewedby:"; //$NON-NLS-1$

	static final String COMMENT_BY = "commentby:"; //$NON-NLS-1$

	static final String OWNER = "owner:"; //$NON-NLS-1$

	static final String REVIEWER = "reviewer:"; //$NON-NLS-1$

	static final String PARENT_PROJECT = "parentproject:"; //$NON-NLS-1$

	static final String PROJECT = "project:"; //$NON-NLS-1$

	private static final String OWNER_SELF = "owner:self"; //$NON-NLS-1$

	private static final String STATUS_OPEN = "status:open"; //$NON-NLS-1$

	private static final String AGE_1WEEK = "age:1week"; //$NON-NLS-1$

	private static final ParamCompleter[] paramCompleters = new ParamCompleter[] { new ProjectNameCompleter(),
			new AccountCompleter(), new AccountGroupCompleter() };

	private static final List<String> completionList = new ArrayList<>();
	static {

		// Note that the order of these elements matters so as to show
		// to most common choices towards the top of the proposal list.
		// This order only matters after the first case-sensitive letter.

		completionList.add("age:"); //$NON-NLS-1$
		completionList.add(AGE_1WEEK); // Give an example age
		completionList.add("added:"); //$NON-NLS-1$

		completionList.add("AND"); //$NON-NLS-1$
//		completionList.add(AUTHOR)

		completionList.add("branch:"); //$NON-NLS-1$
		completionList.add("bug:"); //$NON-NLS-1$

		completionList.add("change:"); //$NON-NLS-1$
//		completionList.add(COMMITTER)
		completionList.add("commit:"); //$NON-NLS-1$
		completionList.add("comment:"); //$NON-NLS-1$
//		completionList.add(COMMENT_BY)
		completionList.add("conflicts:"); //$NON-NLS-1$

		completionList.add("deleted:"); //$NON-NLS-1$
		completionList.add("delta:"); //$NON-NLS-1$

		completionList.add("file:"); //$NON-NLS-1$
		//		completionList.add(FROM)

		completionList.add("has:"); //$NON-NLS-1$
		completionList.add("has:draft"); //$NON-NLS-1$
//		completionList.add("has:edit"); //$NON-NLS-1$
		completionList.add("has:star"); //$NON-NLS-1$
//		completionList.add("has:stars"); //$NON-NLS-1$
//		if (isNoteDbEnabled())
//			completionList.add("hashtag:");
//

		completionList.add("is:"); //$NON-NLS-1$
		completionList.add("is:open"); //$NON-NLS-1$
		completionList.add("is:merged"); //$NON-NLS-1$
		completionList.add("is:submitted"); //$NON-NLS-1$
		completionList.add("is:abandoned"); //$NON-NLS-1$
		completionList.add("is:closed"); //$NON-NLS-1$
		completionList.add("is:draft"); //$NON-NLS-1$
		completionList.add("is:owner"); //$NON-NLS-1$
		completionList.add("is:mergeable"); //$NON-NLS-1$
		completionList.add("is:pending"); //$NON-NLS-1$
		completionList.add("is:reviewed"); //$NON-NLS-1$
		completionList.add("is:reviewer"); //$NON-NLS-1$
		completionList.add("is:starred"); //$NON-NLS-1$
		completionList.add("is:watched"); //$NON-NLS-1$
//		completionList.add("intopic:"); //$NON-NLS-1$

		completionList.add("label:"); //$NON-NLS-1$
		completionList.add("limit:"); //$NON-NLS-1$
		completionList.add("limit:10"); //$NON-NLS-1$

		completionList.add("message:"); //$NON-NLS-1$

		completionList.add("NOT"); //$NON-NLS-1$

		completionList.add("OR"); //$NON-NLS-1$

		completionList.add(OWNER);
		completionList.add(OWNER_SELF);
		completionList.add(OWNER_IN);

		completionList.add(PROJECT);
		completionList.add("projects:"); //$NON-NLS-1$
		completionList.add(PARENT_PROJECT);

//		completionList.add("query:"); //$NON-NLS-1$

		completionList.add(REVIEWER);
		completionList.add("reviewer:self"); //$NON-NLS-1$
		completionList.add(REVIEWER_IN);
//		completionList.add(REVIEWED_BY)
		completionList.add("ref:"); //$NON-NLS-1$

		completionList.add("status:"); //$NON-NLS-1$
		completionList.add(STATUS_OPEN);
		completionList.add("status:draft"); //$NON-NLS-1$
		completionList.add("status:merged"); //$NON-NLS-1$
		completionList.add("status:abandoned"); //$NON-NLS-1$
		completionList.add("status:pending"); //$NON-NLS-1$
		completionList.add("status:reviewed"); //$NON-NLS-1$
		completionList.add("status:submitted"); //$NON-NLS-1$
		completionList.add("status:closed"); //$NON-NLS-1$
//		completionList.add("star:") //$NON-NLS-1$
		completionList.add("size:"); //$NON-NLS-1$

		completionList.add("topic:"); //$NON-NLS-1$
		completionList.add("tr:"); //$NON-NLS-1$
	}

	private Runnable fConnectRequest;

	// A cache of the previous content to avoid fetching completions unnecessarily
	private String fPreviousSearchContent;

	private GerritClient fGerritClient;

	public SearchContentProposalProvider(Runnable connectRequest) {
		fConnectRequest = connectRequest;
	}

	public void setGerritClient(GerritClient client) {
		fGerritClient = client;
	}

	private boolean isSignedIn() {
		if (fGerritClient == null) {
			return false;
		}
		return !fGerritClient.getRepository().getServerInfo().isAnonymous();
	}

	@Override
	public IContentProposal[] getProposals(String contents, int position) {
		// If the content has changed or the content is empty, then we look for proposals.
		// We look for proposal every time the content is empty because we want to show
		// the default proposals as soon as the user clicks in the search box, even
		// if nothing is typed yet (and the previous content was also empty)
		if (contents != null && (contents.isEmpty() || !contents.equals(fPreviousSearchContent))) {
			fPreviousSearchContent = contents;

			// Strip empty characters that are at the end and _after_ the position
			// of the caret. If we don't, and there is an empty char at the end but
			// after the caret, completion will not be triggered and the user may
			// not understand why.
			// We must keep an empty char that is before the caret, as it delimits
			// the start of another word, and therefore another completion
			while (position < contents.length() && contents.charAt(contents.length() - 1) == ' ') {
				contents = contents.substring(0, contents.length() - 1);
			}

			return getProposals(contents);
		}
		return null;
	}

	private SearchContentProposal[] getProposals(String contents) {
		if (contents.trim().isEmpty()) {
			return getDefaultProposals();
		}

		String lastWord = getLastWord(contents);
		if (lastWord == null) {
			// Starting a new word - don't show completionList yet.
			return null;
		}

		// First run through any applicable suggestion logic
		for (ParamCompleter completer : paramCompleters) {
			if (completer.isApplicable(lastWord)) {
				if (fGerritClient == null && fConnectRequest != null) {
					fConnectRequest.run();
				}

				if (fGerritClient == null) {
					// Unable to make the connection.  Don't use any paramSuggester and move on.
					break;
				}

				return completer.suggest(lastWord, contents, fGerritClient);
			}
		}

		// If no suggestion logic was applicable, then we simply
		// look through our hard-coded completion list
		List<SearchContentProposal> proposals = new ArrayList<>();
		for (String suggestion : completionList) {
			if (lastWord.length() < suggestion.length() && suggestion.startsWith(lastWord)) {
				if (suggestion.contains(SELF) && !isSignedIn()) {
					continue;
				}
				proposals.add(new SearchContentProposal(
						contents.substring(0, contents.length() - lastWord.length()) + suggestion, suggestion));
			}
		}
		return proposals.toArray(new SearchContentProposal[proposals.size()]);
	}

	private SearchContentProposal[] getDefaultProposals() {
		List<SearchContentProposal> proposals = new ArrayList<>();

		// Show some default completionList when there is no text.
		proposals.add(new SearchContentProposal(STATUS_OPEN, STATUS_OPEN));
		proposals.add(new SearchContentProposal(AGE_1WEEK, AGE_1WEEK));
		if (isSignedIn()) {
			proposals.add(new SearchContentProposal(OWNER_SELF, OWNER_SELF));
		}
		return proposals.toArray(new SearchContentProposal[proposals.size()]);
	}

	private static String getLastWord(String query) {
		int lastSpace = query.lastIndexOf(' ');
		// If the last character is the space, then we don't
		// have a word to complete
		if (lastSpace == query.length() - 1) {
			return null;
		}
		// No space? Return the entire text of the query
		if (lastSpace == -1) {
			return query;
		}
		// Return the text from the last space
		return query.substring(lastSpace + 1);
	}
}
