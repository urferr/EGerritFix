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

import org.apache.http.client.methods.HttpPost;
import org.eclipse.egerrit.internal.core.GerritRepository;
import org.eclipse.egerrit.internal.core.rest.CherryPickInput;
import org.eclipse.egerrit.internal.model.ChangeInfo;

/**
 * The command: POST /changes/{change-id}/revisions/{revision-id}/cherrypick
 * <p>
 * http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#cherry-pick
 *
 * @since 1.0
 */
public class CherryPickRevisionCommand extends BaseCommandChangeAndRevisionWithInput<ChangeInfo, CherryPickInput> {

	public CherryPickRevisionCommand(GerritRepository gerritRepository, String changeId, String revisionId) {
		super(gerritRepository, AuthentificationRequired.YES, HttpPost.class, ChangeInfo.class, changeId, revisionId);
		setPathFormat("/changes/{change-id}/revisions/{revision-id}/cherrypick"); //$NON-NLS-1$
	}

	@Override
	protected boolean errorsExpected() {
		return true;
	}
}
