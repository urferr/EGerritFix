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

import org.apache.http.client.methods.HttpPut;
import org.eclipse.egerrit.core.GerritRepository;
import org.eclipse.egerrit.core.rest.CommentInfo;

/**
 * The command: PUT /changes/{change-id}/revisions/{revision-id}/drafts/{draft-id}
 * <p>
 * http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#update-draft
 *
 * @since 1.0
 */

public class UpdateDraftCommand extends BaseCommandChangeAndRevisionWithInput<CommentInfo, CommentInfo> {
	/**
	 * Construct a command to update a draft
	 *
	 * @param gerritRepository
	 *            the gerrit repository
	 * @param changeId
	 *            the change-id
	 * @param revisionId
	 *            revisions-id
	 */
	public UpdateDraftCommand(GerritRepository gerritRepository, String changeId, String revisionId, String draftId) {
		super(gerritRepository, AuthentificationRequired.YES, HttpPut.class, CommentInfo.class, changeId, revisionId);
		setPathFormat("/changes/{change-id}/revisions/{revision-id}/drafts/{draft-id}"); //$NON-NLS-1$
		setSegment("{draft-id}", draftId); //$NON-NLS-1$
	}
}
