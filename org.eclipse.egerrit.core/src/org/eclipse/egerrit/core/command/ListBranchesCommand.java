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
import java.net.URLEncoder;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.eclipse.core.runtime.URIUtil;
import org.eclipse.egerrit.core.EGerritCorePlugin;
import org.eclipse.egerrit.core.GerritRepository;
import org.eclipse.egerrit.core.rest.BranchInfo;

/**
 * The <a href= "http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#list-branches" >List
 * Branches</a> command. It returns a <a href=
 * "http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#BranchInfo" ></a> as list.
 * <p>
 *
 * @since 1.0
 */
public class ListBranchesCommand extends QueryCommand<BranchInfo[]> {

	// ------------------------------------------------------------------------
	// Attributes
	// ------------------------------------------------------------------------
	private String fProject_Name;

	// ------------------------------------------------------------------------
	// Constructor
	// ------------------------------------------------------------------------

	/**
	 * The constructor
	 *
	 * @param gerritRepository
	 *            the gerrit repository
	 * @param id
	 *            the project
	 */
	public ListBranchesCommand(GerritRepository gerritRepository, String project) {
		super(gerritRepository, BranchInfo[].class);
		this.setProject_Name(project);

	}

	/**
	 * @return the fProject_Name
	 */
	public String getProject_Name() {
		return fProject_Name;
	}

	/**
	 * @param fProject_Name
	 *            the fProject_Name to set
	 */
	public void setProject_Name(String fProject_Name) {
		this.fProject_Name = fProject_Name;
	}

	// ------------------------------------------------------------------------
	// Format the query
	// ------------------------------------------------------------------------

	/* (non-Javadoc)
	 * @see org.eclipse.egerrit.core.command.GerritCommand#formatRequest()
	 */
	@Override
	public HttpRequestBase formatRequest() {

		// Get the generic URI
		URIBuilder uriBuilder = getRepository().getURIBuilder(fAuthIsRequired);

		URI uri = null;
		try {
			String project = URLEncoder.encode(getProject_Name(), "UTF-8");
			// Set the path
			String path = new StringBuilder(uriBuilder.getPath()).append("/projects/") //$NON-NLS-1$
					.append(project)
					.append("/branches") //$NON-NLS-1$
					.toString();
			uriBuilder.setPath(path);
			uri = new URI(URIUtil.toUnencodedString(uriBuilder.build()));
		} catch (URISyntaxException | UnsupportedEncodingException e) {
			EGerritCorePlugin.logError("URI syntax exception", e); //$NON-NLS-1$
		}

		return new HttpGet(uri);
	}

}
