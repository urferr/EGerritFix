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

import org.apache.http.client.methods.HttpGet;
import org.eclipse.egerrit.internal.core.GerritRepository;
import org.eclipse.egerrit.internal.model.IncludedInInfo;

/**
 * The command GET /changes/{change-id}/in
 * <p>
 * http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#get-included-in
 *
 * @since 1.0
 */
public class GetIncludedInCommand extends BaseCommandChange<IncludedInInfo> {
	/**
	 * The constructor
	 *
	 * @param gerritRepository
	 *            the gerrit repository
	 * @param changeId
	 *            the change-id
	 */
	public GetIncludedInCommand(GerritRepository gerritRepository, String changeId) {
		super(gerritRepository, AuthentificationRequired.NO, HttpGet.class, IncludedInInfo.class, changeId);
		setPathFormat("/changes/{change-id}/in"); //$NON-NLS-1$
	}

}
