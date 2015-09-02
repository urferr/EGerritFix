/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Jacques Bouthillier - Initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.core.command;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.eclipse.core.runtime.URIUtil;
import org.eclipse.egerrit.core.EGerritCorePlugin;
import org.eclipse.egerrit.core.GerritRepository;

/**
 * Deletes a reviewer from a change. The command: DELETE /changes/{change-id}/reviewers/{account-id}
 * <p>
 *
 * @since 1.0
 */
// There is no return type, the return value:  HTTP/1.1 204 No Content
public class DeleteReviewerCommand extends DeleteCommand<String> {

	// ------------------------------------------------------------------------
	// Attributes
	// ------------------------------------------------------------------------

	private String fChange_id;

	private String fAccountId;

	// ------------------------------------------------------------------------
	// Constructor
	// ------------------------------------------------------------------------

	/**
	 * The constructor
	 *
	 * @param gerritRepository
	 *            the gerrit repository
	 * @param id
	 *            the change-id
	 */
	public DeleteReviewerCommand(GerritRepository gerritRepository, String change_id, String accountId) {
		super(gerritRepository, String.class);
		this.setChangeId(change_id);
		this.setAccountId(accountId);
		requiresAuthentication(true);
	}

	/**
	 * gets fChange_id
	 *
	 * @return fChange_id
	 */
	public String getChangeId() {
		return fChange_id;
	}

	/**
	 * sets fChange_id
	 *
	 * @param change_id
	 */
	public void setChangeId(String change_id) {
		this.fChange_id = change_id;
	}

	/**
	 * @return the fAccountId
	 */
	public String getAccountId() {
		return fAccountId;
	}

	/**
	 * @param fAccountId
	 *            the fAccountId to set
	 */
	public void setAccountId(String fAccountId) {
		this.fAccountId = fAccountId;
	}
	// ------------------------------------------------------------------------
	// Format the query
	// ------------------------------------------------------------------------

	/*	 * (non-Javadoc)
	 *
	 * @see org.eclipse.egerrit.core.command.GerritCommand#formatRequest()
	 */
	@Override
	public HttpRequestBase formatRequest() {

		// Get the generic URI
		URIBuilder uriBuilder = getRepository().getURIBuilder(fAuthIsRequired);

		URI uri = null;
		try {
			// Set the path
			String path = new StringBuilder(uriBuilder.getPath()).append("/changes/") //$NON-NLS-1$
					.append(getChangeId())
					.append("/reviewers/") //$NON-NLS-1$
					.append(getAccountId())
					.toString();
			uriBuilder.setPath(path);
			uri = new URI(URIUtil.toUnencodedString(uriBuilder.build()));
		} catch (URISyntaxException e) {
			EGerritCorePlugin.logError("URI syntax exception", e); //$NON-NLS-1$
		}

		HttpDelete httpDelete = new HttpDelete(uri);

		return httpDelete;
	}

}
