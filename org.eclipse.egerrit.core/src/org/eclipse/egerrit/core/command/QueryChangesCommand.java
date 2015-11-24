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
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.client.methods.HttpGet;
import org.eclipse.egerrit.core.GerritRepository;
import org.eclipse.egerrit.core.exception.EGerritException;
import org.eclipse.egerrit.core.rest.ChangeInfo;
import org.eclipse.egerrit.core.rest.FileInfo;
import org.eclipse.egerrit.core.rest.RevisionInfo;

/**
 * The command GET /changes
 * <p>
 * http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#list-changes
 *
 * @since 1.0
 */
public class QueryChangesCommand extends BaseCommand<ChangeInfo[]> {
	String queryString;

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

	public void addQuery(String query) {
		if (queryString == null) {
			queryString = query;
		} else {
			queryString += "+" + query; //$NON-NLS-1$
		}
	}

	public void setMaxNumberOfResults(int limit) {
		addQueryParameter("n", Integer.toString(limit)); //$NON-NLS-1$
	}

	public void setSkipNumberOfResults(int skip) {
		addQueryParameter("start", Integer.toString(skip)); //$NON-NLS-1$
	}

	public void addOption(ChangeOption... options) {
		for (ChangeOption opt : options) {
			addQueryParameter("o", opt.getValue()); //$NON-NLS-1$
		}
	}

	public void addTopic(String topic) {
		addQuery("topic:" + topic); //$NON-NLS-1$
	}

	public void addConflicts(String changeId) {
		addQuery("conflicts:" + changeId); //$NON-NLS-1$
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

	@Override
	protected ChangeInfo[] postProcessResult(ChangeInfo[] results) {
		for (ChangeInfo changeInfo : results) {
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
		}
		return results;
	}
}
