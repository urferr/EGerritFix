/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Jacques Bouthillier - Initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.internal.core.command;

import org.apache.http.client.methods.HttpDelete;
import org.eclipse.egerrit.internal.core.GerritRepository;

/**
 * The command: DELETE /changes/{change-id}/reviewers/{account-id}
 * <p>
 * http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#delete-reviewer
 *
 * @since 1.0
 */
// There is no return type, the return value:  HTTP/1.1 204 No Content
public class DeleteReviewerCommand extends BaseCommandChange<NoResult> {

	/**
	 * Construct a delete reviewer command
	 *
	 * @param gerritRepository
	 *            the gerrit repository
	 * @param id
	 *            the change-id
	 */
	public DeleteReviewerCommand(GerritRepository gerritRepository, String changeId, String accountId) {
		super(gerritRepository, AuthentificationRequired.YES, HttpDelete.class, String.class, changeId);
		setPathFormat("/changes/{change-id}/reviewers/{account-id}"); //$NON-NLS-1$
		setSegment("{account-id}", accountId); //$NON-NLS-1$
	}

}
