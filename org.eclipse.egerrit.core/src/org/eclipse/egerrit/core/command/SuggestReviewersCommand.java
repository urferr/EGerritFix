/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Marc Khouzam - Initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.core.command;

import org.apache.http.client.methods.HttpGet;
import org.eclipse.egerrit.core.GerritRepository;
import org.eclipse.egerrit.internal.model.SuggestReviewerInfo;

/**
 * The command: GET /changes/{change-id}/suggest_reviewers?q=Joh&n=5
 * <p>
 * http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#suggest-reviewers
 */
public class SuggestReviewersCommand extends BaseCommandChange<SuggestReviewerInfo[]> {

	/**
	 * Construct a suggest reviewer command
	 *
	 * @param gerritRepository
	 *            the gerrit repository
	 * @param id
	 *            the change-id
	 */
	public SuggestReviewersCommand(GerritRepository gerritRepository, String changeId) {
		super(gerritRepository, AuthentificationRequired.YES, HttpGet.class, SuggestReviewerInfo[].class, changeId);
		setPathFormat("/changes/{change-id}/suggest_reviewers"); //$NON-NLS-1$
	}

	/**
	 * Sets the string to be used to query a list of matching reviewers.
	 */
	public void setQuery(String query) {
		addQueryParameter("q", query); //$NON-NLS-1$
	}

	/**
	 * Sets the maximum number of suggestions for reviewers. If not set, the default is 10.
	 */
	public void setMaxNumberOfResults(int limit) {
		addQueryParameter("n", Integer.toString(limit)); //$NON-NLS-1$
	}
}
