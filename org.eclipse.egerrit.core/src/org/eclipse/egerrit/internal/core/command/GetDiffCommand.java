/*******************************************************************************
 * Copyright (c) 2017 Ericsson AB.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson - initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.internal.core.command;

import org.apache.http.client.methods.HttpGet;
import org.eclipse.egerrit.internal.core.GerritRepository;
import org.eclipse.egerrit.internal.model.DiffInfo;

/**
 * The command GET /changes/{change-id}/revisions/{revision-id}/files/{file-id}/diff<base>
 * <p>
 * http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#get-diff
 *
 * @since 1.3
 */
public class GetDiffCommand extends BaseCommandChangeAndRevisionAndFile<DiffInfo> {

	public GetDiffCommand(GerritRepository gerritRepository, String changeId, String revisionId, String fileName,
			int base) {
		super(gerritRepository, AuthentificationRequired.NO, HttpGet.class, DiffInfo.class, changeId, revisionId,
				fileName);
		setSegment("baseRevision", Integer.toString(base));

		if (base == 0) {
			setPathFormat("/changes/{change-id}/revisions/{revision-id}/files/{file-id}/diff?context=ALL&intraline"); //$NON-NLS-1$
		} else {
			setPathFormat(
					"/changes/{change-id}/revisions/{revision-id}/files/{file-id}/diff?base=baseRevision&context=ALL&intraline"); //$NON-NLS-1$
		}
	}
}
