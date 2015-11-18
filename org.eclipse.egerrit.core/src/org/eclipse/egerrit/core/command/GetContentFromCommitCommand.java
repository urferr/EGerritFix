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

import java.util.Map;

import org.apache.http.client.methods.HttpGet;
import org.eclipse.egerrit.core.GerritRepository;

/**
 * The command GET /projects/{project-name}/commits/{commit-id}/files/{file-id}/content
 * <p>
 * http://gerrit-review.googlesource.com/Documentation/rest-api-projects.html#get-content
 *
 * @since 1.0
 */
public class GetContentFromCommitCommand extends BaseCommand<String> {
	/**
	 * Construct a command to retrieve the content of a file from the git repository
	 *
	 * @param gerritRepository
	 *            the gerrit repository
	 * @param projectId
	 *            the project-id
	 * @param commitId
	 *            revisions-id
	 * @param fileId
	 *            the file to fetch
	 */
	public GetContentFromCommitCommand(GerritRepository gerritRepository, String projectId, String commitId,
			String fileId) {
		super(gerritRepository, AuthentificationRequired.NO, HttpGet.class, String.class);
		setPathFormat("/projects/{project-name}/commits/{commit-id}/files/{file-id}/content"); //$NON-NLS-1$
		setSegment("{project-name}", projectId); //$NON-NLS-1$
		setSegment("{commit-id}", commitId); //$NON-NLS-1$
		setSegmentToEncode("{file-id}", fileId); //$NON-NLS-1$
	}

	@Override
	protected boolean expectsJson() {
		return false;
	}

	@Override
	protected Map<String, String> getHeaders() {
		return null;
	}

	//Allow a command to say that errors are expected (e.g. GetContentFromCommitCommand)
	@Override
	protected boolean errorsExpected() {
		return true;
	}
}
