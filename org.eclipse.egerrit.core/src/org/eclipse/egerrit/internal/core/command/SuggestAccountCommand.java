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

import org.apache.http.client.methods.HttpGet;
import org.eclipse.egerrit.internal.core.GerritRepository;
import org.eclipse.egerrit.internal.model.AccountInfo;

/**
 * The command: GET /accounts/?q=Joh&n=5
 * <p>
 * https://gerrit-review.googlesource.com/Documentation/rest-api-accounts.html#suggest-account
 */
public class SuggestAccountCommand extends BaseCommand<AccountInfo[]> {

	/**
	 * Construct a suggest account command
	 *
	 * @param gerritRepository
	 *            the gerrit repository
	 */
	public SuggestAccountCommand(GerritRepository gerritRepository) {
		super(gerritRepository, AuthentificationRequired.NO, HttpGet.class, AccountInfo[].class);
		setPathFormat("/accounts/"); //$NON-NLS-1$
	}

	/**
	 * Sets the string to be used to query a list of matching users.
	 */
	public void setQuery(String query) {
		addQueryParameter("q", query); //$NON-NLS-1$
	}

	/**
	 * Sets the maximum number of suggestions. If not set, the default is 10.
	 */
	public void setMaxNumberOfResults(int limit) {
		addQueryParameter("n", Integer.toString(limit)); //$NON-NLS-1$
	}
}
