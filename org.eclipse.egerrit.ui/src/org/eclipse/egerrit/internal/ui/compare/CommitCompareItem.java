/*******************************************************************************
 * Copyright (c) 2015-2017 Ericsson
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
import org.eclipse.egerrit.internal.core.command.GetDiffCommand;
import org.eclipse.egerrit.internal.core.exception.EGerritException;
import org.eclipse.egerrit.internal.model.ChangeInfo;
import org.eclipse.egerrit.internal.model.CommentInfo;
import org.eclipse.egerrit.internal.model.DiffContent;
import org.eclipse.egerrit.internal.model.DiffInfo;
import org.eclipse.egerrit.internal.model.FileInfo;
import org.eclipse.egerrit.internal.model.RevisionInfo;
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

	private ChangeInfo changeInfo;

	private int baseRevision; //There are situations where we show the revision number even though we are showing the base

	private String filePath;

	private String revisionId;

	private int revisionNumber;

	public CommitCompareItem(GerritClient gerrit, RevisionInfo revision, FileInfo fileInfo, String fileName,
			int baseRevision) {
		super(PARENT);
		this.gerrit = gerrit;
		changeInfo = revision.getChangeInfo();
		this.revisionId = revision.getId();
		this.revisionNumber = revision.get_number();
		this.fileInfo = fileInfo;
		if (fileInfo == null) {
			throw new IllegalStateException();
		}
		this.filePath = fileName;
		if (filePath == null) {
			filePath = fileInfo.getPath();
			if (filePath == null) {
				throw new IllegalStateException();
			}
		}
		if (baseRevision > 0) {
			this.baseRevision = baseRevision;
		}
	}

	@Override
	//This needs to return the name of the file.
	public String getName() {
		return GerritCompareHelper.extractFilename(getOldPathOrPath());
	}

	@Override
	public String getUserReadableName() {
		if (baseRevision > 0) {
			return NLS.bind(Messages.CompareElementPatchSetWithCommitId, new Object[] { baseRevision, getName(),
					GerritCompareHelper.shortenCommitId(getBaseCommitId(fileInfo)) });
		}
		return NLS.bind(Messages.CompareElementBase, getName(),
				GerritCompareHelper.shortenCommitId(getBaseCommitId(fileInfo)));
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
		GetDiffCommand getDiff = gerrit.getDiff(changeInfo.getChange_id(), revisionId, filePath, baseRevision);
		try {
			DiffInfo info = getDiff.call();
			if (contentSkipped(info) || info.isBinary()) {
				return getBinary();
			}
			return recreateFile(info);
		} catch (EGerritException e) {
			logger.debug("Exception retrieving content through diff", e); //$NON-NLS-1$
			return new byte[0];
		}
	}

	private boolean contentSkipped(DiffInfo info) {
		for (DiffContent content : info.getContent()) {
			if (content.getSkip() != 0) {
				return true;
			}
		}
		return false;
	}

	private byte[] getBinary() {
		GetContentFromCommitCommand getContent = gerrit.getContentFromCommit(changeInfo.getProject(),
				getBaseCommitId(fileInfo), getOldPathOrPath());
		try {
			String encodedFile = getContent.call();
			setFileType(getContent.getFileMimeType());
			return Base64.decodeBase64(encodedFile);
		} catch (EGerritException e) {
			logger.debug("Exception retrieving commitId", e); //$NON-NLS-1$
		}
		return new byte[0];
	}

	//Go over all the diffContents and create a byte array representing the file
	private byte[] recreateFile(DiffInfo info) {
		EList<DiffContent> content = info.getContent();
		EList<String> allStrings = new BasicEList<>();
		for (DiffContent diffContent : content) {
			if (revisionNumber > baseRevision) {
				allStrings.addAll(diffContent.getA());
			}
			allStrings.addAll(diffContent.getAb());
			if (revisionNumber < baseRevision) {
				allStrings.addAll(diffContent.getB());
			}
		}

		//Pre-compute the size of the StringBuilder to avoid resizes
		int count = 0;
		for (String string : allStrings) {
			count += string.length() + 1;
		}
		StringBuilder sb = new StringBuilder(count);
		for (String string : allStrings) {
			sb.append(string);
			sb.append('\n');
		}
		return sb.toString().getBytes();
	}

	@Override
	protected EList<CommentInfo> filterComments(EList<CommentInfo> eList) {
		return eList.stream().filter(comment -> PARENT.equals(comment.getSide())).collect(
				Collectors.toCollection(BasicEList::new));
	}

	private String getBaseCommitId(FileInfo fileInfo) {
		return fileInfo.getRevision().getBaseCommit();
	}
}