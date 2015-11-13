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
import org.eclipse.egerrit.core.rest.CommentInfo;

/**
 * The command: POST /changes/{change-id}/revisions/{revision-id}/publish
 * <p>
 * http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#publish-draft-revision
 *
 * @since 1.0
 */
public class PublishDraftRevisionCommand extends BaseCommandChangeAndRevisionWithInput<NoResult, CommentInfo> {
	/**
	 * Construct a command to publish a draft
	 *
	 * @param gerritRepository
	 *            the gerrit repository
	 * @param changeId
	 *            the change-id
	 * @param revisionId
	 *            revisions-id
	 */
	public PublishDraftRevisionCommand(GerritRepository gerritRepository, String changeId, String revisionId) {
		super(gerritRepository, AuthentificationRequired.YES, HttpPost.class, String.class, changeId, revisionId);
		setPathFormat("/changes/{change-id}/revisions/{revision-id}/publish"); //$NON-NLS-1$
	}

}
