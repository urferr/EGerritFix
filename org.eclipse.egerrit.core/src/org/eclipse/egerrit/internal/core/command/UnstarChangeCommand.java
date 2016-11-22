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
import org.eclipse.egerrit.internal.core.exception.EGerritException;
import org.eclipse.egerrit.internal.model.ChangeInfo;

/**
 * The command: DELETE /accounts/{account-id}/starred.changes/{change-id}
 * <p>
 * https://git.eclipse.org/r/Documentation/rest-api-accounts.html#unstar-change
 *
 * @since 1.0
 */
// There is no return type, the return value:  HTTP/1.1 204 No Content
public class UnstarChangeCommand extends BaseCommandChange<NoResult> {

	private ChangeInfo fChangeInfo;

	/**
	 * Construct a unstar change command
	 *
	 * @param gerritRepository
	 *            the gerrit repository
	 * @param id
	 *            the change-id
	 */
	public UnstarChangeCommand(GerritRepository gerritRepository, ChangeInfo changeInfo) {
		super(gerritRepository, AuthentificationRequired.YES, HttpDelete.class, String.class,
				Integer.toString(changeInfo.get_number()));
		fChangeInfo = changeInfo;
		setPathFormat("/accounts/self/starred.changes/{change-id}"); //$NON-NLS-1$
	}

	@Override
	public NoResult call() throws EGerritException {
		boolean previousValue = fChangeInfo.isStarred();
		try {
			fChangeInfo.setStarred(false);
			return super.call();
		} catch (EGerritException e) {
			fChangeInfo.setStarred(previousValue);
			throw e;
		}
	}
}
