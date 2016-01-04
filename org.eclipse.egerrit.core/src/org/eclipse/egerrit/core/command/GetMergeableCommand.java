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

import java.net.HttpURLConnection;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpGet;
import org.eclipse.egerrit.core.GerritRepository;
import org.eclipse.egerrit.core.exception.EGerritException;
import org.eclipse.egerrit.internal.model.ChangeInfo;
import org.eclipse.egerrit.internal.model.MergeableInfo;

/**
 * The command GET /changes/{change-id}/revisions/{revision-id}/mergeable
 * <p>
 * http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#get-mergeable
 *
 * @since 1.0
 */
public class GetMergeableCommand extends BaseCommandChangeAndRevision<MergeableInfo> {

	private ChangeInfo changeInfo;

	/**
	 * The constructor
	 *
	 * @param gerritRepository
	 *            the gerrit repository
	 * @param id
	 *            the change-id
	 * @param revision
	 *            revisions-id
	 */
	public GetMergeableCommand(GerritRepository gerritRepository, ChangeInfo changeInfo, String revisionId) {
		super(gerritRepository, AuthentificationRequired.NO, HttpGet.class, MergeableInfo.class, changeInfo.getId(),
				revisionId == null ? "current" : revisionId); //$NON-NLS-1$
		this.changeInfo = changeInfo;
		setPathFormat("/changes/{change-id}/revisions/{revision-id}/mergeable"); //$NON-NLS-1$
	}

	@Override
	protected boolean handleHttpException(ClientProtocolException e) throws EGerritException {
		HttpResponseException httpException = (HttpResponseException) e;
		if (!((httpException.getStatusCode() == HttpURLConnection.HTTP_CONFLICT)
				&& changeInfo.getStatus().toUpperCase().equals("SUBMITTED"))) { //$NON-NLS-1$
			throw new EGerritException(e.getLocalizedMessage());
		}
		return true;
	}
}
