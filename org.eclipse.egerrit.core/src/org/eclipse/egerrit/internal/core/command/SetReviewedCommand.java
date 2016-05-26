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

package org.eclipse.egerrit.internal.core.command;

import org.apache.http.client.methods.HttpPut;
import org.eclipse.egerrit.internal.core.GerritRepository;

/**
 * The command PUT /changes/{change-id}/revisions/{revision-id}/files/{file-id}/reviewed
 * <p>
 * http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#set-reviewed
 *
 * @since 1.0
 */
public class SetReviewedCommand extends BaseCommandChangeAndRevisionAndFile<String> {
	/**
	 * Construct a command to mark a file as reviewed
	 *
	 * @param gerritRepository
	 *            the gerrit repository
	 * @param changeId
	 *            the change-id
	 * @param revisionId
	 *            revisions-id
	 * @param fileId
	 *            the file to fetch
	 */
	public SetReviewedCommand(GerritRepository gerritRepository, String changeId, String revisionId, String fileId) {
		super(gerritRepository, AuthentificationRequired.YES, HttpPut.class, String.class, changeId, revisionId,
				fileId);
		setPathFormat("/changes/{change-id}/revisions/{revision-id}/files/{file-id}/reviewed"); //$NON-NLS-1$
	}

	@Override
	protected boolean expectsJson() {
		return false;
	}
}
