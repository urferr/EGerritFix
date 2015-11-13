/*******************************************************************************
 * Copyright (c) 2015 Ericsson AB.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson - initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.core.command;

import org.apache.http.client.methods.HttpGet;
import org.eclipse.egerrit.core.GerritRepository;

/**
 * The command GET /changes/{change-id}/revisions/{revision-id}/files/?reviewed
 * <p>
 * http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#list-files
 *
 * @since 1.0
 */
public class GetReviewedFilesCommand extends BaseCommandChangeAndRevision<String[]> {
	/**
	 * The constructor
	 *
	 * @param gerritRepository
	 *            the gerrit repository
	 * @param revisionId
	 *            the change-id
	 * @param revisionId
	 *            revisions-id
	 */
	public GetReviewedFilesCommand(GerritRepository gerritRepository, String changeId, String revisionId) {
		super(gerritRepository, AuthentificationRequired.YES, HttpGet.class, String[].class, changeId, revisionId);
		setPathFormat("/changes/{change-id}/revisions/{revision-id}/files/?reviewed"); //$NON-NLS-1$
	}

}