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

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.client.methods.HttpGet;
import org.eclipse.egerrit.core.GerritRepository;
import org.eclipse.egerrit.core.rest.ChangeInfo;
import org.eclipse.egerrit.core.rest.FileInfo;
import org.eclipse.egerrit.core.rest.RevisionInfo;

/**
 * The command GET /changes/{change-id}/detail
 * <p>
 * http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#get-change-detail
 *
 * @since 1.0
 */
public class GetChangeCommand extends BaseCommandChange<ChangeInfo> {
	/**
	 * Construct a command to get a details about a change
	 *
	 * @param gerritRepository
	 *            the gerrit repository
	 * @param changeId
	 *            The changeId to get the details for
	 */
	public GetChangeCommand(GerritRepository gerritRepository, String changeId) {
		super(gerritRepository, AuthentificationRequired.DEPENDS, HttpGet.class, ChangeInfo.class, changeId);
		setPathFormat("/changes/{change-id}/detail"); //$NON-NLS-1$
	}

	public void addOption(ChangeOption option) {
		addQueryParameter("o", option.getValue()); //$NON-NLS-1$
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
