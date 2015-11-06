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

import org.apache.http.client.methods.HttpGet;
import org.eclipse.egerrit.core.GerritRepository;
import org.eclipse.egerrit.core.rest.RelatedChangesInfo;

/**
 * The command GET /changes/{change-id}/revisions/{revision-id}/related
 * <p>
 * http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#get-related-changes
 *
 * @since 1.0
 */
public class GetRelatedChangesCommand extends BaseCommandChangeAndRevision<RelatedChangesInfo> {
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
	public GetRelatedChangesCommand(GerritRepository gerritRepository, String changeId, String revisionId) {
		super(gerritRepository, AuthentificationRequired.NO, HttpGet.class, RelatedChangesInfo.class, changeId,
				revisionId);
		setPathFormat("/changes/{change-id}/revisions/{revision-id}/related"); //$NON-NLS-1$
	}
}
