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

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.eclipse.core.runtime.URIUtil;
import org.eclipse.egerrit.core.EGerritCorePlugin;
import org.eclipse.egerrit.core.GerritRepository;
import org.eclipse.egerrit.core.exception.EGerritException;
import org.eclipse.egerrit.core.rest.AddReviewerInput;
import org.eclipse.egerrit.core.rest.AddReviewerResult;

import com.google.gson.Gson;

/**
 * The command: POST /changes/{change-id}/reviewers
 * <p>
 *
 * @since 1.0
 */
public class AddReviewerCommand extends PostCommand<AddReviewerResult> {

	// ------------------------------------------------------------------------
	// Attributes
	// ------------------------------------------------------------------------

	private String fChange_id;

	private AddReviewerInput fAddReviewerInput;

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
	public AddReviewerCommand(GerritRepository gerritRepository, String change_id) {
		super(gerritRepository, AddReviewerResult.class);
		this.setChangeId(change_id);
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
	 * sets setReviewerInput
	 *
	 * @param reviewerInput
	 */
	public void setReviewerInput(AddReviewerInput reviewerInput) {
		fAddReviewerInput = reviewerInput;
	}

	/**
	 * gets ReviewerInput
	 *
	 * @param reviewerInput
	 */
	public AddReviewerInput getReviewerInput() {
		return fAddReviewerInput;
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
					.append("/reviewers") //$NON-NLS-1$
					.toString();
			uriBuilder.setPath(path);
			uri = new URI(URIUtil.toUnencodedString(uriBuilder.build()));
		} catch (URISyntaxException e) {
			EGerritCorePlugin.logError("URI syntax exception", e); //$NON-NLS-1$
		}

		HttpPost httpPost = new HttpPost(uri);

		Gson gson = new Gson();

		// Serialize.
		String json = gson.toJson(fAddReviewerInput);

		StringEntity input = null;
		try {
			input = new StringEntity(json);
		} catch (UnsupportedEncodingException e) {
			EGerritCorePlugin.logError("URI error encoding ReviewInput", e); //$NON-NLS-1$

		}
		if (input != null) {
			input.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, GerritCommand.JSON_HEADER));
		}

		httpPost.setEntity(input);

		return httpPost;
	}

	@Override
	protected boolean handleHttpException(ClientProtocolException exception) throws EGerritException {
		if (exception instanceof HttpResponseException) {
			HttpResponseException httpException = (HttpResponseException) exception;
			if (httpException.getStatusCode() == 422) {
				EGerritException gerritException = new EGerritException(exception.getLocalizedMessage());
				gerritException.setCode(EGerritException.SHOWABLE_MESSAGE);
				throw gerritException;
			}
		}
		return false;
	}
}
