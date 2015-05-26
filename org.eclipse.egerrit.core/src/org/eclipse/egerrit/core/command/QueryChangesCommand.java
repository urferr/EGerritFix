/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Francois Chouinard - Initial API and implementation
 *     Francois Chouinard - Rebased on GerritQueryCommand and removed useless code
 *******************************************************************************/

package org.eclipse.egerrit.core.command;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.eclipse.core.runtime.URIUtil;
import org.eclipse.egerrit.core.EGerritCorePlugin;
import org.eclipse.egerrit.core.GerritRepository;
import org.eclipse.egerrit.core.rest.ChangeInfo;

/**
 * The <a href= "http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#list-changes" >Query
 * Changes</a> command. It returns a list of <a href=
 * "http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#change-info" >ChangeInfo</a> structures.
 * <p>
 * By default, it will return the list of opened changes.
 *
 * @since 1.0
 */
public class QueryChangesCommand extends QueryCommand<ChangeInfo[]> {

	// ------------------------------------------------------------------------
	// Constructor
	// ------------------------------------------------------------------------

	/**
	 * The constructor
	 *
	 * @param gerritRepository
	 *            the gerrit repository
	 */
	public QueryChangesCommand(GerritRepository gerritRepository) {
		super(gerritRepository, ChangeInfo[].class);
	}

	// ------------------------------------------------------------------------
	// Query fields
	// ------------------------------------------------------------------------

	public QueryChangesCommand addOwner(String owner) {
		addParameter(OWNER, "owner:" + owner); //$NON-NLS-1$
		fAuthIsRequired = ("self".equals(owner)); //$NON-NLS-1$
		return this;
	}

	public QueryChangesCommand addOwnerGroup(String group) {
		addParameter(OWNERIN, "ownerin:" + group); //$NON-NLS-1$
		return this;
	}

	public QueryChangesCommand addReviewer(String reviewer) {
		addParameter(REVIEWER, "reviewer:" + reviewer); //$NON-NLS-1$
		fAuthIsRequired = ("self".equals(reviewer)); //$NON-NLS-1$
		return this;
	}

	public QueryChangesCommand addReviewerGroup(String group) {
		addParameter(REVIEWERIN, "reviewerin:" + group); //$NON-NLS-1$
		return this;
	}

	public QueryChangesCommand addCommit(String sha1) {
		addParameter(COMMIT, "commit:" + sha1); //$NON-NLS-1$
		return this;
	}

	public QueryChangesCommand addProject(String project) {
		addParameter(PROJECT, "project:" + project); //$NON-NLS-1$
		return this;
	}

	public QueryChangesCommand addParentProject(String project) {
		addParameter(PARENTPROJECT, "parentproject:" + project); //$NON-NLS-1$
		return this;
	}

	public QueryChangesCommand addPrefix(String prefix) {
		addParameter(PREFIX, "prefix:" + prefix); //$NON-NLS-1$
		return this;
	}

	public QueryChangesCommand addBranch(String branch) {
		addParameter(BRANCH, "branch:" + branch); //$NON-NLS-1$
		return this;
	}

	public QueryChangesCommand addTopic(String topic) {
		addParameter(TOPIC, "topic:" + topic); //$NON-NLS-1$
		return this;
	}

	public QueryChangesCommand addConflicts(String conflicts) {
		addParameter(CONFLICTS, "conflicts:" + conflicts); //$NON-NLS-1$
		return this;
	}

	public QueryChangesCommand addReference(String ref) {
		addParameter(REF, "ref:" + ref); //$NON-NLS-1$
		return this;
	}

	public QueryChangesCommand addBug(String bug) {
		addParameter(BUG, "bug:" + bug); //$NON-NLS-1$
		return this;
	}

	public QueryChangesCommand addLabel(String label) {
		addParameter(LABEL, "label:" + label); //$NON-NLS-1$
		return this;
	}

	public QueryChangesCommand addMessage(String message) {
		addParameter(MESSAGE, "message:" + message); //$NON-NLS-1$
		return this;
	}

	public QueryChangesCommand addComment(String comment) {
		addParameter(COMMENT, "comment:" + comment); //$NON-NLS-1$
		return this;
	}

	public QueryChangesCommand addPath(String path) {
		addParameter(PATH, "path:" + path); //$NON-NLS-1$
		return this;
	}

	public QueryChangesCommand addFile(String file) {
		addParameter(FILE, "file:" + file); //$NON-NLS-1$
		return this;
	}

	public QueryChangesCommand addStatus(ChangeStatus status) {
		addParameter(STATUS, status.getValue());
		return this;
	}

	public QueryChangesCommand addState(ChangeState state) {
		addParameter(STATE, state.getValue());
		fAuthIsRequired |= state == ChangeState.IS_WATCHED;
		return this;
	}

	// ------------------------------------------------------------------------
	// Magical operators
	// ------------------------------------------------------------------------

	public QueryChangesCommand addVisibleTo(String userOrGroup) {
		addParameter(VISIBLETO, "visibleto:" + userOrGroup); //$NON-NLS-1$
		return this;
	}

	public QueryChangesCommand addVisible() {
		addParameter(ISVISIBLE, "is:visible"); //$NON-NLS-1$
		return this;
	}

	public QueryChangesCommand addStarredBy(String user) {
		addParameter(STARREDBY, "starredby:" + user); //$NON-NLS-1$
		return this;
	}

	public QueryChangesCommand addWatchedBy(String user) {
		addParameter(WATCHEDBY, "watchedby:" + user); //$NON-NLS-1$
		return this;
	}

	public QueryChangesCommand addDraftBy(String user) {
		addParameter(DRAFTBY, "draftby:" + user); //$NON-NLS-1$
		return this;
	}

	public QueryChangesCommand addLimit(int limit) {
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
			String path = new StringBuilder(uriBuilder.getPath()).append("/changes/").toString(); //$NON-NLS-1$;
			uriBuilder.setPath(path);

			// Set the query
			String params = buildParametersList();
			if (params.length() > 0) {
				uriBuilder.setParameter("q", params); //$NON-NLS-1$
			}

			// Add the options
			for (String option : fQueryOptions) {
				uriBuilder.setParameter("o", option); //$NON-NLS-1$
			}

			// Add count
			if (fCount > 0) {
				uriBuilder.setParameter("n", Integer.valueOf(fCount).toString()); //$NON-NLS-1$
			}

			try {
				uri = uriBuilder.build();
				if (uri.toString().equals(java.net.URLDecoder.decode(uri.toString(), "UTF-8"))) {
				} else {
					// Create the URI but revert the escaped characters
					uri = new URI(URIUtil.toUnencodedString(uriBuilder.build()));
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (URISyntaxException e) {
			EGerritCorePlugin.logError("URI syntax exception", e); //$NON-NLS-1$
		}

		return new HttpGet(uri);
	}
}
