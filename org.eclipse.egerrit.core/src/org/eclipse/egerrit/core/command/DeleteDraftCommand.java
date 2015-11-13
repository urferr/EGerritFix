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

import org.apache.http.client.methods.HttpDelete;
import org.eclipse.egerrit.core.GerritRepository;

/**
 * The command: DELETE /changes/{change-id}/revisions/{revision-id}/drafts/{draft-id}
 * <p>
 * http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#delete-draft
 *
 * @since 1.0
 */

public class DeleteDraftCommand extends BaseCommandChangeAndRevision<NoResult> {

	/**
	 * Construct the delete draft command
	 *
	 * @param gerritRepository
	 *            the gerrit repository
	 * @param id
	 *            the change-id
	 * @param revision
	 *            revisions-id
	 * @param draftId
	 *            draft-id
	 */
	public DeleteDraftCommand(GerritRepository gerritRepository, String changeId, String revisionId, String draftId) {
		super(gerritRepository, AuthentificationRequired.YES, HttpDelete.class, String.class, changeId, revisionId);
		setPathFormat("/changes/{change-id}/revisions/{revision-id}/drafts/{draft-id}"); //$NON-NLS-1$
		setSegment("{draft-id}", draftId); //$NON-NLS-1$
	}
}
