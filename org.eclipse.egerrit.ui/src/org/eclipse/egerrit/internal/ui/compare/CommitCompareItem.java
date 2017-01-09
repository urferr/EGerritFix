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

package org.eclipse.egerrit.internal.ui.compare;

import java.util.stream.Collectors;

import org.apache.commons.codec.binary.Base64;
import org.eclipse.compare.IStreamContentAccessor;
import org.eclipse.compare.ITypedElement;
import org.eclipse.egerrit.internal.core.GerritClient;
import org.eclipse.egerrit.internal.core.command.GetContentFromCommitCommand;
import org.eclipse.egerrit.internal.core.exception.EGerritException;
import org.eclipse.egerrit.internal.model.CommentInfo;
import org.eclipse.egerrit.internal.model.FileInfo;
import org.eclipse.egerrit.internal.ui.utils.Messages;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.osgi.util.NLS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link ITypedElement} used to present a commit
 *
 * @since 1.0
 */
class CommitCompareItem extends CommentableCompareItem implements IStreamContentAccessor, ITypedElement {
	private static Logger logger = LoggerFactory.getLogger(CommitCompareItem.class);

	private static final String PARENT = "PARENT"; //$NON-NLS-1$

	private final String projectId, commitId;

	private String showAsRevision;

	private String filePath;

	public CommitCompareItem(GerritClient gerrit, String projectId, String commitId, FileInfo fileInfo, String fileName,
			String showAsRevision) {
		super(PARENT);
		this.gerrit = gerrit;
		this.projectId = projectId;
		this.commitId = commitId;
		this.fileInfo = fileInfo;
		this.showAsRevision = showAsRevision;
	}

	@Override
	//This name is presented in the header of the text editor area
	public String getName() {
		if (showAsRevision != null) {
			return NLS.bind(Messages.CompareElementPatchSetWithCommitId,
					new Object[] { showAsRevision, GerritCompareHelper.extractFilename(getOldPathOrPath()),
							GerritCompareHelper.shortenCommitId(commitId) });
		}
		return NLS.bind(Messages.CompareElementBase, GerritCompareHelper.extractFilename(getOldPathOrPath()),
				GerritCompareHelper.shortenCommitId(commitId));
	}

	private String getOldPathOrPath() {
		if (filePath != null) {
			return filePath;
		}
		if (fileInfo.getOld_path() == null) {
			return fileInfo.getPath();
		}
		return fileInfo.getOld_path();
	}

	@Override
	protected byte[] loadFileContent() {
		GetContentFromCommitCommand getContent = gerrit.getContentFromCommit(projectId, commitId, getOldPathOrPath());
		try {
			String encodedFile = getContent.call();
			setFileType(getContent.getFileMimeType());
			return Base64.decodeBase64(encodedFile);
		} catch (EGerritException e) {
			logger.debug("Exception retrieving commitId", e); //$NON-NLS-1$
		}
		return new byte[0];
	}

	@Override
	protected EList<CommentInfo> filterComments(EList<CommentInfo> eList) {
		return eList.stream().filter(comment -> PARENT.equals(comment.getSide())).collect(
				Collectors.toCollection(BasicEList::new));
	}
}