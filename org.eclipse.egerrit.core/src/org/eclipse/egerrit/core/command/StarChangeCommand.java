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

package org.eclipse.egerrit.core.command;

import org.apache.http.client.methods.HttpPut;
import org.eclipse.egerrit.core.GerritRepository;

/**
 * The command: PUT /accounts/{account-id}/starred.changes/{change-id}
 * <p>
 * https://git.eclipse.org/r/Documentation/rest-api-accounts.html#star-change
 *
 * @since 1.0
 */
// There is no return type, the return value:  HTTP/1.1 204 No Content
public class StarChangeCommand extends BaseCommandChange<NoResult> {

	/**
	 * Construct a star change command
	 *
	 * @param gerritRepository
	 *            the gerrit repository
	 * @param id
	 *            the change-id
	 */
	public StarChangeCommand(GerritRepository gerritRepository, int numericChangeId) {
		super(gerritRepository, AuthentificationRequired.YES, HttpPut.class, String.class,
				Integer.toString(numericChangeId));
		setPathFormat("/accounts/self/starred.changes/{change-id}"); //$NON-NLS-1$
	}

}
