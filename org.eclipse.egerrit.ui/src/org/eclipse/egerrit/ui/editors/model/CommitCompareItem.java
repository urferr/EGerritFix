/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson - initial API and implementation
 *
 *******************************************************************************/

package org.eclipse.egerrit.ui.editors.model;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.compare.IStreamContentAccessor;
import org.eclipse.compare.ITypedElement;
import org.eclipse.core.runtime.CoreException;

/**
 * {@link ITypedElement} used to present a commit
 *
 * @since 1.0
 */
class CommitCompareItem implements IStreamContentAccessor, ITypedElement {
	private final String contents, fileName, commitId;

	//Takes the commitId, the name of the file presented and its content
	CommitCompareItem(String commitId, String fileName, String contents) {
		this.fileName = fileName;
		this.contents = contents;
		this.commitId = commitId;
	}

	@Override
	public InputStream getContents() throws CoreException {
		return new ByteArrayInputStream(contents == null ? new byte[0] : contents.getBytes());
	}

	@Override
	public org.eclipse.swt.graphics.Image getImage() {
		return null;
	}

	@Override
	//This name is presented in the header of the text editor area
	public String getName() {
		return "Base: " + GerritCompareHelper.extractFilename(fileName) + ' ' //$NON-NLS-1$
				+ '(' + GerritCompareHelper.shortenCommitId(commitId) + ')';
	}

	@Override
	public String getType() {
		return ITypedElement.UNKNOWN_TYPE;
	}
}