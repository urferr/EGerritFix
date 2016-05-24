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

import org.apache.http.client.methods.HttpPut;
import org.eclipse.egerrit.core.GerritRepository;
import org.eclipse.egerrit.core.rest.TopicInput;

/**
 * The command: PUT /changes/{change-id}/topic
 * <p>
 * http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#set-topic
 *
 * @since 1.0
 */

public class SetTopicCommand extends BaseCommandChangeWithInput<NoResult, TopicInput> {
	/**
	 * The constructor
	 *
	 * @param gerritRepository
	 *            the gerrit repository
	 * @param changeId
	 *            the change-id
	 */
	public SetTopicCommand(GerritRepository gerritRepository, String changeId) {
		super(gerritRepository, AuthentificationRequired.YES, HttpPut.class, String.class, changeId);
		setPathFormat("/changes/{change-id}/topic"); //$NON-NLS-1$
	}

}