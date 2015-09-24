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

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.eclipse.core.runtime.URIUtil;
import org.eclipse.egerrit.core.EGerritCorePlugin;
import org.eclipse.egerrit.core.GerritRepository;

import com.google.gson.Gson;

/**
 * The command: POST /changes/link:#change-id[\{change-id\}]/edit:publish
 * <p>
 *
 * @since 1.0
 */
public class PublishChangeEditCommand extends PostCommand<String> {

	// ------------------------------------------------------------------------
	// Attributes
	// ------------------------------------------------------------------------

	private String fChange_id;

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
	public PublishChangeEditCommand(GerritRepository gerritRepository, String id) {
		super(gerritRepository, String.class);
		this.setId(id);
		requiresAuthentication(true);

	}

	/**
	 * gets fChange_id
	 *
	 * @return fChange_id of type String
	 */
	public String getId() {
		return fChange_id;
	}

	/**
	 * sets fChange_id
	 *
	 * @param change_id
	 */
	public void setId(String change_id) {
		this.fChange_id = change_id;
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
					.append("/edit:publish") //$NON-NLS-1$
					.toString();
			uriBuilder.setPath(path);
			uri = new URI(URIUtil.toUnencodedString(uriBuilder.build()));
		} catch (URISyntaxException e) {
			EGerritCorePlugin.logError("URI syntax exception", e); //$NON-NLS-1$
		}

		HttpPost httpPost = new HttpPost(uri);

		Gson gson = new Gson();

		// Serialize.
//		String json = gson.toJson(fChangeEditMessageInput);
//
//		StringEntity input = null;
//		try {
//			input = new StringEntity(json);
//		} catch (UnsupportedEncodingException e) {
//			EGerritCorePlugin.logError("URI error encoding ChangeEditMessageInput", e); //$NON-NLS-1$
//
//		}
//		input.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, GerritCommand.JSON_HEADER));
//
//		httpPut.setEntity(input);

		return httpPost;
	}
}
