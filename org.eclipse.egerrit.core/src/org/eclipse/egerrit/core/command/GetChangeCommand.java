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
 * command. It returns a <a href=
 * "http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#change-info" >ChangeInfo</a> structure.
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

	public GetChangeCommand setOwner(String owner) {
		addParameter(OWNER, "owner:" + owner); //$NON-NLS-1$
		return this;
	}

	public GetChangeCommand setOwnerGroup(String group) {
		addParameter(OWNERIN, "ownerin:" + group); //$NON-NLS-1$
		return this;
	}

	public GetChangeCommand setReviewer(String reviewer) {
		addParameter(REVIEWER, "reviewer:" + reviewer); //$NON-NLS-1$
		return this;
	}

	public GetChangeCommand setReviewerGroup(String group) {
		addParameter(REVIEWERIN, "reviewerin:" + group); //$NON-NLS-1$
		return this;
	}

	public GetChangeCommand setCommit(String sha1) {
		addParameter(COMMIT, "commit:" + sha1); //$NON-NLS-1$
		return this;
	}

	public GetChangeCommand setProject(String project) {
		addParameter(PROJECT, "project:" + project); //$NON-NLS-1$
		return this;
	}

	public GetChangeCommand setParentProject(String project) {
		addParameter(PARENTPROJECT, "parentproject:" + project); //$NON-NLS-1$
		return this;
	}

	public GetChangeCommand setPrefix(String prefix) {
		addParameter(PREFIX, "prefix:" + prefix); //$NON-NLS-1$
		return this;
	}

	public GetChangeCommand setBranch(String branch) {
		addParameter(BRANCH, "branch:" + branch); //$NON-NLS-1$
		return this;
	}

	public GetChangeCommand setTopic(String topic) {
		addParameter(TOPIC, "topic:" + topic); //$NON-NLS-1$
		return this;
	}

	public GetChangeCommand setReference(String ref) {
		addParameter(REF, "ref:" + ref); //$NON-NLS-1$
		return this;
	}

	public GetChangeCommand setBug(String bug) {
		addParameter(BUG, "bug:" + bug); //$NON-NLS-1$
		return this;
	}

	public GetChangeCommand setLabel(String label) {
		addParameter(LABEL, "label:" + label); //$NON-NLS-1$
		return this;
	}

	public GetChangeCommand setMessage(String message) {
		addParameter(MESSAGE, "message:" + message); //$NON-NLS-1$
		return this;
	}

	public GetChangeCommand setComment(String comment) {
		addParameter(COMMENT, "comment:" + comment); //$NON-NLS-1$
		return this;
	}

	public GetChangeCommand setPath(String path) {
		addParameter(PATH, "path:" + path); //$NON-NLS-1$
		return this;
	}

	public GetChangeCommand setFile(String file) {
		addParameter(FILE, "file:" + file); //$NON-NLS-1$
		return this;
	}

	public GetChangeCommand setStatus(ChangeStatus status) {
		addParameter(STATUS, status.getValue());
		return this;
	}

	public GetChangeCommand setState(ChangeState state) {
		addParameter(STATE, state.getValue());
		return this;
	}

	// ------------------------------------------------------------------------
	// Magical operators
	// ------------------------------------------------------------------------

	public GetChangeCommand setVisibleTo(String userOrGroup) {
		addParameter(VISIBLETO, "visibleto:" + userOrGroup); //$NON-NLS-1$
		return this;
	}

	public GetChangeCommand setVisible() {
		addParameter(ISVISIBLE, "is:visible"); //$NON-NLS-1$
		return this;
	}

	public GetChangeCommand setStarredBy(String user) {
		addParameter(STARREDBY, "starredby:" + user); //$NON-NLS-1$
		return this;
	}

	public GetChangeCommand setWatchedBy(String user) {
		addParameter(WATCHEDBY, "watchedby:" + user); //$NON-NLS-1$
		return this;
	}

	public GetChangeCommand setDraftBy(String user) {
		addParameter(DRAFTBY, "draftby:" + user); //$NON-NLS-1$
		return this;
	}

	public GetChangeCommand setLimit(int limit) {
		addParameter(LIMIT, "limit:" + Integer.valueOf(limit).toString()); //$NON-NLS-1$
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

	public String getId() {
		return fChange_id;
	}

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
