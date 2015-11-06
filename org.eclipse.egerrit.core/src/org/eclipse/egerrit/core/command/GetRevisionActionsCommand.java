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

import java.util.Map;

import org.apache.http.client.methods.HttpGet;
import org.eclipse.egerrit.core.GerritRepository;
import org.eclipse.egerrit.core.rest.ActionInfo;

import com.google.gson.reflect.TypeToken;

/**
 * The command GET /changes/{change-id}/revisions/{revision-id}/actions
 * <p>
 * http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#get-revision-actions
 *
 * @since 1.0
 */
public class GetRevisionActionsCommand extends BaseCommandChangeAndRevision<Map<String, ActionInfo>> {

	/**
	 * Construct a get revision action command
	 *
	 * @param gerritRepository
	 *            the gerrit repository
	 * @param changeId
	 *            the change-id
	 * @param revisionId
	 *            revisions-id
	 */
	public GetRevisionActionsCommand(GerritRepository gerritRepository, String changeId, String revisionId) {
		super(gerritRepository, AuthentificationRequired.NO, HttpGet.class, new TypeToken<Map<String, ActionInfo>>() {
		}.getType(), changeId, revisionId);
		setPathFormat("/changes/{change-id}/revisions/{revision-id}/actions"); //$NON-NLS-1$
	}
}
