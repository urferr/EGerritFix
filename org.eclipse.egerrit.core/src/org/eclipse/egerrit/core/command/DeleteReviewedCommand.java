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
 * DELETE /changes/{change-id}/revisions/{revision-id}/files/{file-id}/reviewed
 * <p>
 * http://http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#delete-reviewed
 *
 * @since 1.0
 */
public class DeleteReviewedCommand extends BaseCommandChangeAndRevisionAndFile<String> {
	/**
	 * Construct a delete command
	 *
	 * @param gerritRepository
	 *            the gerrit repository
	 * @param id
	 *            the change-id
	 * @param revision
	 *            revisions-id
	 * @param targetFile
	 *            the file to fetch
	 */
	public DeleteReviewedCommand(GerritRepository gerritRepository, String changeId, String revisionId, String fileId) {
		super(gerritRepository, AuthentificationRequired.YES, HttpDelete.class, String.class, changeId, revisionId,
				fileId);
		setPathFormat("/changes/{change-id}/revisions/{revision-id}/files/{file-id}/reviewed"); //$NON-NLS-1$
	}

	@Override
	protected boolean expectsJson() {
		return false;
	}
}
