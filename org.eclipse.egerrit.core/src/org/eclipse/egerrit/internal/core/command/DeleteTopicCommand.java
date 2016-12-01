/*******************************************************************************
 * Copyright (c) 2016 Ericsson
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson - Initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.internal.core.command;

import org.apache.http.client.methods.HttpDelete;
import org.eclipse.egerrit.internal.core.GerritRepository;

/**
 * DELETE /changes/{change-id}/topic
 * <p>
 * https://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#delete-topic
 *
 * @since 1.0
 */
public class DeleteTopicCommand extends BaseCommandChange<NoResult> {

	public DeleteTopicCommand(GerritRepository gerritRepository, String changeId) {
		super(gerritRepository, AuthentificationRequired.YES, HttpDelete.class, String.class, changeId);
		setPathFormat("/changes/{change-id}/topic"); //$NON-NLS-1$
	}

	@Override
	protected boolean expectsJson() {
		return false;
	}
}
