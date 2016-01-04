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
import org.eclipse.egerrit.internal.model.CommentInfo;

/**
 * The command: PUT /changes/{change-id}/revisions/{revision-id}/drafts
 * <p>
 * http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#create-draft
 *
 * @since 1.0
 */

public class CreateDraftCommand extends BaseCommandChangeAndRevisionWithInput<CommentInfo, CommentInfo> {

	/**
	 * Construct a create draft command
	 *
	 * @param gerritRepository
	 *            - the gerrit repository
	 * @param id
	 *            - the change-id
	 * @param revision
	 *            - the revisions-id
	 */
	public CreateDraftCommand(GerritRepository gerritRepository, String changeId, String revisionId) {
		super(gerritRepository, AuthentificationRequired.YES, HttpPut.class, CommentInfo.class, changeId, revisionId);
		setPathFormat("/changes/{change-id}/revisions/{revision-id}/drafts"); //$NON-NLS-1$
	}

}
