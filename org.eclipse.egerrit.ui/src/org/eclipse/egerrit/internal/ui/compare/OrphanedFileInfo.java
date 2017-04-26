/*******************************************************************************
 * Copyright (c) 2016 Ericsson
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson - initial API and implementation
 *******************************************************************************/
package org.eclipse.egerrit.internal.ui.compare;

import org.eclipse.egerrit.internal.model.ModifiedFileInfoImpl;
import org.eclipse.egerrit.internal.model.RevisionInfo;

/**
 * This class is used to represent files that need to be shown in the compare editor but are not found in the list of
 * files for the revision. This weird situation happens when a modified file has been removed during a rebase. You can
 * see an example of this situation at https://git.eclipse.org/r/#/c/83161/1..7/
 */
class OrphanedFileInfo extends ModifiedFileInfoImpl {

	private String filePath;

	private RevisionInfo revisionInfo;

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public String getPath() {
		return filePath;
	}

	public void setRevisionInfo(RevisionInfo revisionInfo) {
		this.revisionInfo = revisionInfo;
	}

	@Override
	public RevisionInfo getRevision() {
		return revisionInfo;
	}
}
