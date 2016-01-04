/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson - Initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.core.command;

import org.apache.http.client.methods.HttpPost;
import org.eclipse.egerrit.core.GerritRepository;
import org.eclipse.egerrit.core.rest.RevertInput;
import org.eclipse.egerrit.internal.model.ChangeInfo;

/**
 * The command: POST /changes/{change-id}/revert
 * <p>
 * http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#revert-change
 *
 * @since 1.0
 */
public class RevertCommand extends BaseCommandChangeWithInput<ChangeInfo, RevertInput> {
	/**
	 * Construct a command to revert a review
	 *
	 * @param gerritRepository
	 *            the gerrit repository
	 * @param id
	 *            the change-id
	 */
	public RevertCommand(GerritRepository gerritRepository, String changeId) {
		super(gerritRepository, AuthentificationRequired.YES, HttpPost.class, ChangeInfo.class, changeId);
		setPathFormat("/changes/{change-id}/revert"); //$NON-NLS-1$
	}
}
