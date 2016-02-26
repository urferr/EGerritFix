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

import java.util.stream.Collectors;

import org.apache.commons.codec.binary.Base64;
import org.eclipse.compare.IStreamContentAccessor;
import org.eclipse.compare.ITypedElement;
import org.eclipse.egerrit.core.GerritClient;
import org.eclipse.egerrit.core.command.GetContentFromCommitCommand;
import org.eclipse.egerrit.core.exception.EGerritException;
import org.eclipse.egerrit.internal.model.CommentInfo;
import org.eclipse.egerrit.internal.model.FileInfo;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link ITypedElement} used to present a commit
 *
 * @since 1.0
 */
class CommitCompareItem extends CommentableCompareItem implements IStreamContentAccessor, ITypedElement {
	private static Logger logger = LoggerFactory.getLogger(CommitCompareItem.class);

	private final String projectId, commitId;

	private static final String PARENT = "PARENT"; //$NON-NLS-1$

	CommitCompareItem(GerritClient gerrit, String projectId, String commitId, FileInfo fileInfo) {
		super(PARENT);
		this.gerrit = gerrit;
		this.projectId = projectId;
		this.commitId = commitId;
		this.fileInfo = fileInfo;
	}

	@Override
	//This name is presented in the header of the text editor area
	public String getName() {
		return "Base: " + GerritCompareHelper.extractFilename(getOldPathOrPath()) + ' ' //$NON-NLS-1$
				+ '(' + GerritCompareHelper.shortenCommitId(commitId) + ')';
	}

	private String getOldPathOrPath() {
		if (fileInfo.getOld_path() == null) {
			return fileInfo.getPath();
		}
		return fileInfo.getOld_path();
	}

	@Override
	protected byte[] loadFileContent() {
		GetContentFromCommitCommand getContent = gerrit.getContentFromCommit(projectId, commitId, getOldPathOrPath());
		try {
			return Base64.decodeBase64(getContent.call());
		} catch (EGerritException e) {
			logger.debug("Exception retrieving commitId", e); //$NON-NLS-1$
		}
		return new byte[0];
	}

	@Override
	protected EList<CommentInfo> filterComments(EList<CommentInfo> eList) {
		return eList.stream()
				.filter(comment -> PARENT.equals(comment.getSide()))
				.collect(Collectors.toCollection(BasicEList::new));
	}
}