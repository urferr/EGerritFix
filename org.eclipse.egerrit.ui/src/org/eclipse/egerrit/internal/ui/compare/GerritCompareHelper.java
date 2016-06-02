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

package org.eclipse.egerrit.internal.ui.compare;

import java.nio.file.Paths;

import org.eclipse.egerrit.internal.model.ChangeInfo;
import org.eclipse.egerrit.internal.model.RevisionInfo;
import org.eclipse.egerrit.internal.ui.utils.Messages;

/**
 * Helper class used in the compare editor
 */
class GerritCompareHelper {

	//Given a commit id, returns a shortened commit id (the first 10 characters)
	static String shortenCommitId(String commitId) {
		return commitId != null ? commitId.substring(0, 10) : commitId;
	}

	//Given a filePath, returns the filename
	static String extractFilename(String filePath) {
		return Paths.get(filePath).getFileName().toString();
	}

	static String resolveShortName(ChangeInfo changeInfo, String toResolve) {
		RevisionInfo match = changeInfo.getRevisions().get(toResolve);
		if (match != null) {
			return Messages.CompareUpperSection_PatchSet + match.get_number();
		}
		return toResolve;
	}
}
