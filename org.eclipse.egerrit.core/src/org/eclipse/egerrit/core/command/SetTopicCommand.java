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

import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.eclipse.core.runtime.URIUtil;
import org.eclipse.egerrit.core.EGerritCorePlugin;
import org.eclipse.egerrit.core.GerritRepository;
import org.eclipse.egerrit.core.rest.TopicInput;

import com.google.gson.Gson;

/**
 * The command: PUT /changes/link:#change-id[\{change-id\}]/revisions/link:#revision-id[\{revision-id\}]/comments/ As
 * result a map is returned that maps the file path to a list of CommentInfo entries.
 * <p>
 *
 * @since 1.0
 */

public class SetTopicCommand extends PutCommand<String> {

	// ------------------------------------------------------------------------
	// Attributes
	// ------------------------------------------------------------------------

	private String fChange_id;

	private TopicInput fTopicInput;

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
	public SetTopicCommand(GerritRepository gerritRepository, String id) {
		super(gerritRepository, String.class);
		this.setId(id);
		requiresAuthentication(true);
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
	 * fToopicInput setter function
	 *
	 * @param topicInput
	 *            the topicInfo that is to be set
	 */
	public void setTopicInput(TopicInput topicInput) {

		fTopicInput = topicInput;

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
					.append("/topic") //$NON-NLS-1$
					.toString();
			uriBuilder.setPath(path);
			uri = new URI(URIUtil.toUnencodedString(uriBuilder.build()));
		} catch (URISyntaxException e) {
			EGerritCorePlugin.logError("URI syntax exception", e); //$NON-NLS-1$
		}

		HttpPut httpPut = new HttpPut(uri);

		StringEntity input = null;
		try {
			input = new StringEntity(new Gson().toJson(fTopicInput));
		} catch (UnsupportedEncodingException e) {
			EGerritCorePlugin.logError("URI error encoding TopicInput", e); //$NON-NLS-1$
		}

		if (input != null) {
			input.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, GerritCommand.JSON_HEADER));
		}
		httpPut.setEntity(input);

		return httpPut;
	}
}
