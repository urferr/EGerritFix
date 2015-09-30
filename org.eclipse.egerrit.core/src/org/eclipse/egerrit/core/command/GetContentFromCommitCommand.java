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
import java.util.Map;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.eclipse.core.runtime.URIUtil;
import org.eclipse.egerrit.core.EGerritCorePlugin;
import org.eclipse.egerrit.core.GerritRepository;

/**
 * The <a href= "http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#get-content" >Get Content</a>
 * command. It returns a
 * <a href= "http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#String" ></a> base64 encoded.
 * <p>
 *
 * @since 1.0
 */
public class GetContentFromCommitCommand extends QueryCommand<String> {

	// ------------------------------------------------------------------------
	// Attributes
	// ------------------------------------------------------------------------
	private String fProject_Name;

	private String fCommit_id;

	private String fTargetFile;

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
	 * @param targetFile
	 *            the file to fetch
	 */
	public GetContentFromCommitCommand(GerritRepository gerritRepository, String project, String commit,
			String targetFile) {
		super(gerritRepository, String.class);
		this.setProject_Name(project);
		this.setCommit_id(commit);
		this.settargetFile(targetFile);

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

	/**
	 * @return the fCommit_id
	 */
	public String getCommit_id() {
		return fCommit_id;
	}

	/**
	 * @param fCommit_id
	 *            the fCommit_id to set
	 */
	public void setCommit_id(String commit_id) {
		this.fCommit_id = commit_id;
	}

	private void settargetFile(String file_id) {
		this.fTargetFile = file_id;

	}

	private String gettargetFile() {
		return fTargetFile;

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
			String file = URLEncoder.encode(gettargetFile(), "UTF-8");
			String project = URLEncoder.encode(getProject_Name(), "UTF-8");
			// Set the path
			String path = new StringBuilder(uriBuilder.getPath()).append("/projects/") //$NON-NLS-1$
					.append(project)
					.append("/commits/") //$NON-NLS-1$
					.append(getCommit_id())
					.append("/files/") //$NON-NLS-1$
					.append(file)
					.append("/content") //$NON-NLS-1$
					.toString();
			uriBuilder.setPath(path);
			uri = new URI(URIUtil.toUnencodedString(uriBuilder.build()));
		} catch (URISyntaxException | UnsupportedEncodingException e) {
			EGerritCorePlugin.logError("URI syntax exception", e); //$NON-NLS-1$
		}

		return new HttpGet(uri);
	}

	@Override
	protected boolean expectsJson() {
		return false;
	}

	@Override
	protected Map<String, String> headers() {
		return null;
	}

	//Allow a command to say that errors are expected (e.g. GetContentFromCommitCommand)
	@Override
	protected boolean errorsExpected() {
		return true;
	}
}
