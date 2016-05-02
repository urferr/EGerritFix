/*******************************************************************************
 * Copyright (c) 2016 Ericsson
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

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpPost;
import org.eclipse.egerrit.core.GerritRepository;
import org.eclipse.egerrit.core.exception.EGerritException;
import org.eclipse.egerrit.core.rest.RebaseInput;
import org.eclipse.egerrit.internal.model.ChangeInfo;

/**
 * The command: POST /changes/{change-id}/revisions/{revision-id}/rebase
 * <p>
 * http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#rebase-change
 *
 * @since 1.0
 */
public class RebaseRevisionCommand extends BaseCommandChangeWithInput<ChangeInfo, RebaseInput> {

	/**
	 * The constructor
	 *
	 * @param gerritRepository
	 *            the gerrit repository
	 * @param changeId
	 *            the change-id
	 */
	public RebaseRevisionCommand(GerritRepository gerritRepository, String changeId, String revisionId) {
		super(gerritRepository, AuthentificationRequired.YES, HttpPost.class, ChangeInfo.class, changeId, revisionId);
		setPathFormat("/changes/{change-id}/revisions/{revision-id}/rebase"); //$NON-NLS-1$
	}

	@Override
	protected boolean handleHttpException(ClientProtocolException exception) throws EGerritException {
		if (exception instanceof HttpResponseException) {
			HttpResponseException httpException = (HttpResponseException) exception;
			if (httpException.getStatusCode() == 409) {
				EGerritException gerritException = new EGerritException(exception.getLocalizedMessage());
				gerritException.setCode(EGerritException.SHOWABLE_MESSAGE);
				throw gerritException;
			}
		}
		return false;
	}
}
