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

import org.apache.http.client.methods.HttpPost;
import org.eclipse.egerrit.core.GerritRepository;
import org.eclipse.egerrit.core.rest.ReviewInput;
import org.eclipse.egerrit.internal.model.ReviewInfo;

/**
 * The command: POST /changes/{change-id}/revisions/{revision-id}/review
 * <p>
 * http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#set-review
 *
 * @since 1.0
 */
public class SetReviewCommand extends BaseCommandChangeAndRevisionWithInput<ReviewInfo, ReviewInput> {
	/**
	 * Construct a command to set a review on a revision
	 *
	 * @param gerritRepository
	 *            the gerrit repository
	 * @param changeId
	 *            the change-id
	 * @param revisionId
	 *            revisions-id
	 */
	public SetReviewCommand(GerritRepository gerritRepository, String changeId, String revisionId) {
		super(gerritRepository, AuthentificationRequired.YES, HttpPost.class, ReviewInfo.class, changeId, revisionId);
		setPathFormat("/changes/{change-id}/revisions/{revision-id}/review"); //$NON-NLS-1$
	}

}
