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
 *     Francois Chouinard - Rebased on GerritQueryCommand and removed useless code
 *******************************************************************************/

package org.eclipse.egerrit.core.command;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.eclipse.core.runtime.URIUtil;
import org.eclipse.egerrit.core.EGerritCorePlugin;
import org.eclipse.egerrit.core.GerritRepository;
import org.eclipse.egerrit.core.rest.ChangeInfo;
import org.eclipse.egerrit.core.rest.FileInfo;
import org.eclipse.egerrit.core.rest.RevisionInfo;

/**
 * The <a href= "http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#list-changes" >Get Change</a>
 * command. It returns a
 * <a href= "http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#change-info" >ChangeInfo</a>
 * structure.
 * <p>
 *
 * @since 1.0
 */
public class GetChangeCommand extends QueryCommand<ChangeInfo> {

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
	 */
	public GetChangeCommand(GerritRepository gerritRepository, String id) {
		super(gerritRepository, ChangeInfo.class);
		this.setId(id);
		//Setting requires to false, because even though this command can access user specific information
		//It will still succeed when user-specific information is requested by anonymous.
		//It will just not return the requested information
		requiresAuthentication(false);

	}

	// ------------------------------------------------------------------------
	// Query fields
	// ------------------------------------------------------------------------

	/**
	 * @param owner
	 * @return
	 */
	public GetChangeCommand setOwner(String owner) {
		addParameter(OWNER, "owner:" + owner); //$NON-NLS-1$
		return this;
	}

	/**
	 * @param topic
	 * @return
	 */
	public GetChangeCommand setTopic(String topic) {
		addParameter(TOPIC, "topic:" + topic); //$NON-NLS-1$
		return this;
	}

	/**
	 * @param status
	 * @return
	 */
	public GetChangeCommand setStatus(ChangeStatus status) {
		addParameter(STATUS, status.getValue());
		return this;
	}

	/**
	 * @param state
	 * @return
	 */
	public GetChangeCommand setState(ChangeState state) {
		addParameter(STATE, state.getValue());
		return this;
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
			// Set the path
			String path = new StringBuilder(uriBuilder.getPath()).append("/changes/") //$NON-NLS-1$
					.append(getId())
					//;
					.append("/detail") //$NON-NLS-1$
					.toString();
			uriBuilder.setPath(path);

			// Set the query
			String params = buildParametersList();
			if (params.length() > 0) {
				uriBuilder.setParameter("q", buildParametersList()); //$NON-NLS-1$
			}

			// Add the options
			for (String option : fQueryOptions) {
				uriBuilder.addParameter("o", option).build(); //$NON-NLS-1$
			}

			// Add count
			if (fCount > 0) {
				uriBuilder.setParameter("n", Integer.valueOf(fCount).toString()); //$NON-NLS-1$
			}
			uri = new URI(URIUtil.toUnencodedString(uriBuilder.build()));
		} catch (URISyntaxException e) {
			EGerritCorePlugin.logError("URI syntax exception", e); //$NON-NLS-1$
		}

		return new HttpGet(uri);
	}

	// ------------------------------------------------------------------------
	// Getters/setters
	// ------------------------------------------------------------------------

	/**
	 * @return
	 */
	public String getId() {
		return fChange_id;
	}

	/**
	 * @param change_id
	 */
	public void setId(String change_id) {
		this.fChange_id = change_id;
	}

	@Override
	protected ChangeInfo postProcessResult(ChangeInfo changeInfo) {
		Map<String, RevisionInfo> map = changeInfo.getRevisions();
		if (map != null) {
			Iterator<Map.Entry<String, RevisionInfo>> revisions = map.entrySet().iterator();
			while (revisions.hasNext()) {
				Entry<String, RevisionInfo> revisionEntry = revisions.next();
				RevisionInfo theRevision = revisionEntry.getValue();
				theRevision.setId(revisionEntry.getKey());
				Map<String, FileInfo> filesMap = theRevision.getFiles();
				if (filesMap != null) {
					Iterator<Map.Entry<String, FileInfo>> files = filesMap.entrySet().iterator();
					while (files.hasNext()) {
						Entry<String, FileInfo> aFile = files.next();
						aFile.getValue().setOld_path(aFile.getKey());
						aFile.getValue().setContainingRevision(theRevision);
					}
				}
			}
		}
		return changeInfo;
	}
}
