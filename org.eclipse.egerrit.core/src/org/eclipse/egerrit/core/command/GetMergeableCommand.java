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

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.eclipse.core.runtime.URIUtil;
import org.eclipse.egerrit.core.EGerritCorePlugin;
import org.eclipse.egerrit.core.GerritRepository;
import org.eclipse.egerrit.core.exception.EGerritException;
import org.eclipse.egerrit.core.rest.ChangeInfo;
import org.eclipse.egerrit.core.rest.MergeableInfo;

/**
 * The <a href= "http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#mergeable-info" >Get
 * Mergeable</a> command. It returns a
 * <a href= "http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#MergeableInfo" ></a>
 * <p>
 *
 * @since 1.0
 */
public class GetMergeableCommand extends QueryCommand<MergeableInfo> {

	// ------------------------------------------------------------------------
	// Attributes
	// ------------------------------------------------------------------------

	private ChangeInfo change;

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
	public GetMergeableCommand(GerritRepository gerritRepository, ChangeInfo info) {
		super(gerritRepository, MergeableInfo.class);
		this.setChangeInfo(info);

	}

	public ChangeInfo getChangeInfo() {
		return change;
	}

	public void setChangeInfo(ChangeInfo info) {
		this.change = info;
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
					.append(getChangeInfo().getId())
					.append("/revisions/current")//$NON-NLS-1$
					.append("/mergeable") //$NON-NLS-1$
					.toString();
			uriBuilder.setPath(path);
			uri = new URI(URIUtil.toUnencodedString(uriBuilder.build()));
		} catch (URISyntaxException e) {
			EGerritCorePlugin.logError("URI syntax exception", e); //$NON-NLS-1$
		}

		return new HttpGet(uri);
	}

	@Override
	protected boolean handleHttpException(ClientProtocolException e) throws EGerritException {
		HttpResponseException httpException = (HttpResponseException) e;
		if (!((httpException.getStatusCode() == HttpURLConnection.HTTP_CONFLICT)
				&& change.getStatus().toUpperCase().equals("SUBMITTED"))) { //$NON-NLS-1$
			throw new EGerritException(e.getLocalizedMessage());
		}
		return true;
	}
}
