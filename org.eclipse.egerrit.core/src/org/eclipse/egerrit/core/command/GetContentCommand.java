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
 *     Francois Chouinard - Rebased on GerritQueryCommand and removed useless code
 *******************************************************************************/

package org.eclipse.egerrit.core.command;

import java.util.Map;

import org.apache.http.client.methods.HttpGet;
import org.eclipse.egerrit.core.GerritRepository;

/**
 * The command GET /changes/{change-id}/revisions/{revision-id}/files/{file-id}/content
 * <p>
 * http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#get-content
 *
 * @since 1.0
 */
public class GetContentCommand extends BaseCommandChangeAndRevisionAndFile<String> {

	/**
	 * The constructor
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
	public GetContentCommand(GerritRepository gerritRepository, String changeId, String revisionId, String fileId) {
		super(gerritRepository, AuthentificationRequired.NO, HttpGet.class, String.class, changeId, revisionId, fileId);
		setPathFormat("/changes/{change-id}/revisions/{revision-id}/files/{file-id}/content"); //$NON-NLS-1$
	}

	@Override
	protected boolean expectsJson() {
		return false;
	}

	@Override
	protected Map<String, String> getHeaders() {
		return null;
	}
}
