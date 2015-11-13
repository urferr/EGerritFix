/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Guy Perron - Initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.core.command;

import org.apache.http.client.methods.HttpGet;
import org.eclipse.egerrit.core.GerritRepository;
import org.eclipse.egerrit.core.rest.ReviewerInfo;

/**
 * The command GET /changes/{change-id}/reviewers/
 * <p>
 * http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#list-reviewers
 *
 * @since 1.0
 */
public class ListReviewersCommand extends BaseCommandChange<ReviewerInfo[]> {
	/**
	 * Construct a command that list the reviewers
	 *
	 * @param gerritRepository
	 *            the gerrit repository
	 * @param changeId
	 *            the change-id
	 */
	public ListReviewersCommand(GerritRepository gerritRepository, String changeId) {
		super(gerritRepository, AuthentificationRequired.NO, HttpGet.class, ReviewerInfo[].class, changeId);
		setPathFormat("/changes/{change-id}/reviewers/"); //$NON-NLS-1$
	}

}
