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

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.eclipse.core.runtime.URIUtil;
import org.eclipse.egerrit.core.EGerritCorePlugin;
import org.eclipse.egerrit.core.GerritRepository;
import org.eclipse.egerrit.core.rest.ProjectAccessInfo;

/**
 * The <a href=
 * "https://git.eclipse.org/r/Documentation/rest-api-access.html#project-access-info"
 * >Get Content</a> command. It returns a <a href=
 * "https://git.eclipse.org/r/Documentation/rest-api-access.html#project-access-info"
 * ></a> The command: 'GET /access/?project={project-name}' or Request: GET
 * /access/?project=MyProject&project=All-Projects HTTP/1.0
 * <p>
 *
 * @since 1.0
 */
public class GetAccessRightsCommand extends QueryCommand<ProjectAccessInfo> {

	// ------------------------------------------------------------------------
	// Attributes
	// ------------------------------------------------------------------------

	private String fChange_id;

	private String fRevision;

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
	public GetAccessRightsCommand(GerritRepository gerritRepository, String id,
			String revision) {
		super(gerritRepository, ProjectAccessInfo.class);
		this.setId(id);
		this.setRevision(revision);

	}

	private void setRevision(String revision) {
		fRevision = revision;

	}

	private String getRevision() {
		return fRevision;

	}

	public String getId() {
		return fChange_id;
	}

	public void setId(String change_id) {
		this.fChange_id = change_id;
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
			String path = new StringBuilder(uriBuilder.getPath())
					.append("/changes/").append(getId()) //$NON-NLS-1$
					.append("/revisions/").append(getRevision())//$NON-NLS-1$
					.append("/commit") //$NON-NLS-1$
					.toString();
			uriBuilder.setPath(path);
			uri = new URI(URIUtil.toUnencodedString(uriBuilder.build()));
		} catch (URISyntaxException e) {
			EGerritCorePlugin.logError("URI syntax exception", e); //$NON-NLS-1$
		}

		return new HttpGet(uri);
	}

}
