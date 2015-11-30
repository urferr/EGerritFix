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

/**
 * The command POST /changes/{change-id}/publish
 * <p>
 * http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#publish-draft-change
 *
 * @since 1.0
 */
public class PublishDraftChangeCommand extends BaseCommandChange<String> {

	/**
	 * The constructor
	 *
	 * @param gerritRepository
	 *            the gerrit repository
	 * @param changeId
	 *            the change-id
	 */
	public PublishDraftChangeCommand(GerritRepository gerritRepository, String changeId) {
		super(gerritRepository, AuthentificationRequired.YES, HttpPost.class, String.class, changeId);
		setPathFormat("/changes/{change-id}/publish"); //$NON-NLS-1$
	}

}
