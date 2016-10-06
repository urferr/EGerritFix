/*******************************************************************************
 * Copyright (c) 2015 Ericsson AB.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson - initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.internal.core.command;

import org.eclipse.egerrit.internal.core.GerritRepository;

/**
 * The command GET /changes/{change-id}/revisions/{revision-id}/files/?files=<base>
 * <p>
 * http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#list-files
 *
 * @since 1.0
 */
public class GetModifiedFilesCommand extends GetFilesCommand {
	/**
	 * The constructor
	 *
	 * @param gerritRepository
	 *            the gerrit repository
	 * @param revisionId
	 *            the change-id
	 * @param revisionId
	 *            revisions-id
	 */
	public GetModifiedFilesCommand(GerritRepository gerritRepository, String changeId, String revisionId,
			String compareAgainst) {
		super(gerritRepository, changeId, revisionId);
		if (compareAgainst != null) {
			addQueryParameter("base", compareAgainst); //$NON-NLS-1$
		}
	}

}