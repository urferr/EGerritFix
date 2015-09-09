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

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.eclipse.core.runtime.URIUtil;
import org.eclipse.egerrit.core.EGerritCorePlugin;
import org.eclipse.egerrit.core.GerritRepository;

/**
 * The command: DELETE
 * /changes/link:#change-id[\{change-id\}]/revisions/link:#revision-id[\{revision-id\}]/drafts/[\{draft-id\}] As result
 * a string is returned with the return code
 * <p>
 *
 * @since 1.0
 */

public class DeleteDraftCommand extends DeleteCommand<String> {

	// ------------------------------------------------------------------------
	// Attributes
	// ------------------------------------------------------------------------

	private String fChange_id;

	private String fRevision;

	private String fDraft_id;

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
	 * @param revision
	 *            revisions-id
	 * @param draftId
	 *            draft-id
	 */
	public DeleteDraftCommand(GerritRepository gerritRepository, String id, String revision, String draftId) {
		super(gerritRepository, String.class);
		this.setId(id);
		this.setRevision(revision);
		this.setDraftId(draftId);
		requiresAuthentication(true);
	}

	private void setRevision(String revision) {
		fRevision = revision;

	}

	private String getRevision() {
		return fRevision;

	}

	/**
	 * change_id getter function
	 *
	 * @return change_id the current change_id
	 */
	public String getId() {
		return fChange_id;
	}

	/**
	 * change_id setter function
	 *
	 * @param change_id
	 *            the change_id of the comment that is to be created
	 */

	public void setId(String change_id) {
		this.fChange_id = change_id;
	}

	/**
	 * @return the fDraft_id
	 */
	public String getDraftId() {
		return fDraft_id;
	}

	/**
	 * @param fDraft_id
	 *            the fDraft_id to set
	 */
	public void setDraftId(String draft_id) {
		this.fDraft_id = draft_id;
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
					.append(getId())
					.append("/revisions/") //$NON-NLS-1$
					.append(getRevision())
					.append("/drafts/") //$NON-NLS-1$
					.append(getDraftId())
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
