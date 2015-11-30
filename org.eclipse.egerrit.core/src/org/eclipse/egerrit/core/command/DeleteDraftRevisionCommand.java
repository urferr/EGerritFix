/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson - Initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.core.command;

import org.apache.http.client.methods.HttpDelete;
import org.eclipse.egerrit.core.GerritRepository;

/**
 * The command: DELETE /changes/{change-id}/revisions/{revision-id}
 * <p>
 * http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#delete-draft-revision
 *
 * @since 1.0
 */

public class DeleteDraftRevisionCommand extends BaseCommandChangeAndRevision<NoResult> {

	/**
	 * Construct the delete draft revision command
	 *
	 * @param gerritRepository
	 *            the gerrit repository
	 * @param id
	 *            the change-id
	 * @param revision
	 *            revisions-id
	 */
	public DeleteDraftRevisionCommand(GerritRepository gerritRepository, String changeId, String revisionId) {
		super(gerritRepository, AuthentificationRequired.YES, HttpDelete.class, String.class, changeId, revisionId);
		setPathFormat("/changes/{change-id}/revisions/{revision-id}"); //$NON-NLS-1$
	}
}
