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
import org.eclipse.egerrit.core.rest.AbandonInput;
import org.eclipse.egerrit.core.rest.ChangeInfo;

/**
 * The command: POST /changes/{change-id}/abandon/
 * <p>
 * http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#abandon-change
 *
 * @since 1.0
 */
public class AbandonCommand extends BaseCommandChangeWithInput<ChangeInfo, AbandonInput> {
	/**
	 * Construct an abandon command
	 *
	 * @param gerritRepository
	 *            the gerrit repository
	 * @param id
	 *            the change-id
	 */
	public AbandonCommand(GerritRepository gerritRepository, String id) {
		super(gerritRepository, AuthentificationRequired.YES, HttpPost.class, ChangeInfo.class, id);
		setPathFormat("/changes/{change-id}/abandon/"); //$NON-NLS-1$
	}
}
