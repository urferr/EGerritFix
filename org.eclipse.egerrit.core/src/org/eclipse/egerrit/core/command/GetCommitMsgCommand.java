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
import org.eclipse.egerrit.internal.model.CommitInfo;

/**
 * The command GET /changes/{change-id}/revisions/{revision-id}/commit
 * <p>
 * http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#get-commit
 *
 * @since 1.0
 */
public class GetCommitMsgCommand extends BaseCommandChangeAndRevision<CommitInfo> {

	/**
	 * The constructor
	 *
	 * @param gerritRepository
	 *            the gerrit repository
	 * @param changeId
	 *            the change-id
	 * @param revisionId
	 *            revisions-id
	 */
	public GetCommitMsgCommand(GerritRepository gerritRepository, String changeId, String revisionId) {
		super(gerritRepository, AuthentificationRequired.NO, HttpGet.class, CommitInfo.class, changeId, revisionId);
		setPathFormat("/changes/{change-id}/revisions/{revision-id}/commit"); //$NON-NLS-1$
	}

}
