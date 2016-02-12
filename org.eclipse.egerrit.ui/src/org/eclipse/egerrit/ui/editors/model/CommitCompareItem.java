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

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.eclipse.compare.IStreamContentAccessor;
import org.eclipse.compare.ITypedElement;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.egerrit.core.GerritClient;
import org.eclipse.egerrit.core.command.GetContentFromCommitCommand;
import org.eclipse.egerrit.core.exception.EGerritException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link ITypedElement} used to present a commit
 *
 * @since 1.0
 */
class CommitCompareItem implements IStreamContentAccessor, ITypedElement {
	private static Logger logger = LoggerFactory.getLogger(CommitCompareItem.class);

	private final String projectId, fileName, commitId;

	private final GerritClient gerrit;

	private String contents;

	CommitCompareItem(GerritClient gerrit, String projectId, String commitId, String fileName) {
		this.gerrit = gerrit;
		this.projectId = projectId;
		this.commitId = commitId;
		this.fileName = fileName;
		this.contents = null;
	}

	@Override
	public InputStream getContents() throws CoreException {
		if (contents == null) {
			loadContent();
		}
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

	private void loadContent() {
		GetContentFromCommitCommand getContent = gerrit.getContentFromCommit(projectId, commitId, fileName);
		try {
			contents = StringUtils.newStringUtf8(Base64.decodeBase64(getContent.call()));
		} catch (EGerritException e) {
			logger.debug("Exception retrieving commitId", e); //$NON-NLS-1$
		}
	}
}