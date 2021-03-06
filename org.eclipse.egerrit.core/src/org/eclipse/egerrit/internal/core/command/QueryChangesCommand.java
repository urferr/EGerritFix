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

package org.eclipse.egerrit.internal.core.command;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.http.client.methods.HttpGet;
import org.eclipse.egerrit.internal.core.GerritRepository;
import org.eclipse.egerrit.internal.core.exception.EGerritException;
import org.eclipse.egerrit.internal.model.ChangeInfo;

/**
 * The command GET /changes
 * <p>
 * http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#list-changes
 *
 * @since 1.0
 */
public class QueryChangesCommand extends BaseCommand<ChangeInfo[]> {
	private String queryString;

	/**
	 * Construct a command to query for changes
	 *
	 * @param gerritRepository
	 *            the gerrit repository
	 */
	public QueryChangesCommand(GerritRepository gerritRepository) {
		super(gerritRepository, AuthentificationRequired.DEPENDS, HttpGet.class, ChangeInfo[].class);
		setPathFormat("/changes/"); //$NON-NLS-1$
	}

	/**
	 * Adjust the query string for the command
	 *
	 * @param String
	 *            query
	 */
	public void addQuery(String query) {
		if (queryString == null) {
			queryString = query;
		} else {
			queryString += "+" + query; //$NON-NLS-1$
		}
	}

	/**
	 * Set the limit for the query result
	 *
	 * @param Int
	 *            limit
	 */
	public void setMaxNumberOfResults(int limit) {
		addQueryParameter("n", Integer.toString(limit)); //$NON-NLS-1$
	}

	/**
	 * Set the number of result to skip
	 *
	 * @param int
	 *            skip
	 */
	public void setSkipNumberOfResults(int skip) {
		addQueryParameter("start", Integer.toString(skip)); //$NON-NLS-1$
	}

	/**
	 * Add different options for the query
	 *
	 * @param ChangeOption
	 *            options
	 */
	public void addOption(ChangeOption... options) {
		for (ChangeOption opt : options) {
			addQueryParameter("o", opt.getValue()); //$NON-NLS-1$
		}
	}

	/**
	 * Set the topic for the query
	 *
	 * @param String
	 *            topic
	 */
	public void addTopic(String topic) {
		addQuery("topic:" + topic); //$NON-NLS-1$
	}

	/**
	 * Set the change Id for the query conflicts
	 *
	 * @param String
	 *            changeId
	 */
	public void addConflicts(String changeId) {
		addQuery("conflicts:" + changeId); //$NON-NLS-1$
	}

	/**
	 * Adjust the status query parameter
	 *
	 * @param ChangeStatus
	 *            status
	 */
	public void addStatus(ChangeStatus status) {
		addQuery(status.getValue());
	}

	/**
	 * Add the query if review is Mergeable
	 */
	public void addMergeable() {
		addQuery("is:mergeable"); //$NON-NLS-1$
	}

	@Override
	//We only need to override this method because we want to force only one query string
	public ChangeInfo[] call() throws EGerritException {
		if (queryString != null) {
			try {
				//We forcefully decode the query string because when we construct the final URL in BaseCommand, the parameters are encoded.
				//And if the changeId was not decoded then some characters would be encoded a second time which would break the query.
				addQueryParameter("q", URLDecoder.decode(queryString, "UTF-8")); //$NON-NLS-1$ //$NON-NLS-2$
			} catch (UnsupportedEncodingException e) {
				throw new IllegalStateException("Problem decoding query string " + queryString); //$NON-NLS-1$
			}
		}
		return super.call();
	}
}
