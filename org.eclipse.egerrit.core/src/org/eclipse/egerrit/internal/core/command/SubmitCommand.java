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
import org.eclipse.egerrit.internal.core.rest.SubmitInput;
import org.eclipse.egerrit.internal.model.ChangeInfo;

/**
 * The command: POST /changes/{change-id}/submit
 * <p>
 * http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#submit-change
 *
 * @since 1.0
 */
public class SubmitCommand extends BaseCommandChangeWithInput<ChangeInfo, SubmitInput> {
	/**
	 * Construct a command to submit the review
	 *
	 * @param gerritRepository
	 *            the gerrit repository
	 * @param id
	 *            the change-id
	 */
	public SubmitCommand(GerritRepository gerritRepository, String changeId) {
		super(gerritRepository, AuthentificationRequired.YES, HttpPost.class, ChangeInfo.class, changeId);
		setPathFormat("/changes/{change-id}/submit"); //$NON-NLS-1$
	}
}
