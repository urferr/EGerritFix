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

import org.apache.http.client.methods.HttpPost;
import org.eclipse.egerrit.core.GerritRepository;

/**
 * The command POST /changes/{change-id}/edit:publish
 * <p>
 * http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#publish-edit
 *
 * @since 1.0
 */
public class PublishChangeEditCommand extends BaseCommandChange<String> {

	/**
	 * The constructor
	 *
	 * @param gerritRepository
	 *            the gerrit repository
	 * @param changeId
	 *            the change-id
	 */
	public PublishChangeEditCommand(GerritRepository gerritRepository, String changeId) {
		super(gerritRepository, AuthentificationRequired.YES, HttpPost.class, String.class, changeId);
		setPathFormat("/changes/{change-id}/edit:publish"); //$NON-NLS-1$
	}

}
