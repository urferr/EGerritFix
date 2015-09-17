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

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.eclipse.core.runtime.URIUtil;
import org.eclipse.egerrit.core.EGerritCorePlugin;
import org.eclipse.egerrit.core.GerritRepository;
import org.eclipse.egerrit.core.rest.ChangeInfo;
import org.eclipse.egerrit.core.rest.CherryPickInput;

import com.google.gson.Gson;

/**
 * The command: GET /changes/link:#change-id[\{change-id\}]/revisions/link:#revision-id[\{revision-id\}]/comments/ As
 * result ChangeInfo
 * <p>
 *
 * @since 1.0
 */
public class CherryPickRevisionCommand extends QueryCommand<ChangeInfo> {

	// ------------------------------------------------------------------------
	// Attributes
	// ------------------------------------------------------------------------

	private String fChange_id;

	private String fRevision;

	CherryPickInput fCherryPickInfo = new CherryPickInput();

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
	 */
	public CherryPickRevisionCommand(GerritRepository gerritRepository, String id, String revision) {
		super(gerritRepository, ChangeInfo.class);
		this.setId(id);
		this.setRevision(revision);
		requiresAuthentication(true);

	}

	private void setRevision(String revision) {
		fRevision = revision;

	}

	private String getRevision() {
		return fRevision;

	}

	/**
	 * get the value of fChange_id
	 *
	 * @param none
	 * @return fChange_id
	 */
	public String getId() {
		return fChange_id;
	}

	/**
	 * set the value of fChange_id
	 *
	 * @param fChange_id
	 * @return none
	 */
	public void setId(String change_id) {
		this.fChange_id = change_id;
	}

	/**
	 * fCherryPickInfo setter function
	 *
	 * @param CherryPickInput
	 *            the CherryPickInput that is to be created
	 */
	public void setCherryPickInput(CherryPickInput cherryPickInput) {

		fCherryPickInfo = cherryPickInput;

	}

	// ------------------------------------------------------------------------
	// Format the query
	// ------------------------------------------------------------------------

	/*
	 * (non-Javadoc)
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
					.append("/cherrypick") //$NON-NLS-1$
					.toString();
			uriBuilder.setPath(path);
			uri = new URI(URIUtil.toUnencodedString(uriBuilder.build()));
		} catch (URISyntaxException e) {
			EGerritCorePlugin.logError("URI syntax exception", e); //$NON-NLS-1$
		}
		HttpPost httpPost = new HttpPost(uri);

		StringEntity input = null;
		try {
			input = new StringEntity(new Gson().toJson(fCherryPickInfo));
		} catch (UnsupportedEncodingException e) {
			EGerritCorePlugin.logError("URI error encoding CherryPickInput", e); //$NON-NLS-1$
		}

		if (input != null) {
			input.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, GerritCommand.JSON_HEADER));
		}
		httpPost.setEntity(input);

		return httpPost;
	}

}
