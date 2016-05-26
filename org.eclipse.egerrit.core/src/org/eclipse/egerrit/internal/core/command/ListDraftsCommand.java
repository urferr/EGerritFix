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

package org.eclipse.egerrit.internal.core.command;

import java.util.ArrayList;
import java.util.Map;

import org.apache.http.client.methods.HttpGet;
import org.eclipse.egerrit.internal.core.GerritRepository;
import org.eclipse.egerrit.internal.model.CommentInfo;

import com.google.gson.reflect.TypeToken;

/**
 * The command: GET /changes/{change-id}/revisions/{revision-id}/drafts/
 * <p>
 * http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#list-drafts
 *
 * @since 1.0
 */
public class ListDraftsCommand extends BaseCommandChangeAndRevision<Map<String, ArrayList<CommentInfo>>> {
	/**
	 * Construct a command to list drafts comments
	 *
	 * @param gerritRepository
	 *            the gerrit repository
	 * @param changeId
	 *            the change-id
	 * @param revisionId
	 *            revisions-id
	 */
	public ListDraftsCommand(GerritRepository gerritRepository, String changeId, String revisionId) {
		super(gerritRepository, AuthentificationRequired.YES, HttpGet.class,
				new TypeToken<Map<String, ArrayList<CommentInfo>>>() {
				}.getType(), changeId, revisionId);
		setPathFormat("/changes/{change-id}/revisions/{revision-id}/drafts/"); //$NON-NLS-1$
	}

}
