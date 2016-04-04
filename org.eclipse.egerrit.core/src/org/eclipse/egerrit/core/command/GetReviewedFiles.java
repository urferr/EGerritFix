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

import java.util.Map;

import org.apache.http.client.methods.HttpGet;
import org.eclipse.egerrit.core.GerritRepository;
import org.eclipse.egerrit.internal.model.FileInfo;

import com.google.gson.reflect.TypeToken;

/**
 * The command GET /changes/{change-id}/revisions/{revision-id}/files<base>
 * <p>
 * http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#list-files
 *
 * @since 1.0
 */
public class GetReviewedFiles extends BaseCommandChangeAndRevision<Map<String, FileInfo>> {
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
	public GetReviewedFiles(GerritRepository gerritRepository, String changeId, String revisionId) {
		super(gerritRepository, AuthentificationRequired.NO, HttpGet.class, new TypeToken<Map<String, FileInfo>>() {
		}.getType(), changeId, revisionId);
		setPathFormat("/changes/{change-id}/revisions/{revision-id}/files"); //$NON-NLS-1$
	}

}