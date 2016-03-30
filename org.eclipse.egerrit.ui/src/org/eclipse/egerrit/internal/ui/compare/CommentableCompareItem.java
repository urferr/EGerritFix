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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collections;

import org.apache.commons.codec.binary.StringUtils;
import org.eclipse.compare.IEditableContent;
import org.eclipse.compare.IModificationDate;
import org.eclipse.compare.IStreamContentAccessor;
import org.eclipse.compare.ITypedElement;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.egerrit.core.GerritClient;
import org.eclipse.egerrit.core.command.CreateDraftCommand;
import org.eclipse.egerrit.core.command.DeleteDraftCommand;
import org.eclipse.egerrit.core.command.UpdateDraftCommand;
import org.eclipse.egerrit.core.exception.EGerritException;
import org.eclipse.egerrit.core.rest.CommentInput;
import org.eclipse.egerrit.internal.model.CommentInfo;
import org.eclipse.egerrit.internal.model.FileInfo;
import org.eclipse.egerrit.ui.editors.QueryHelpers;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.AnnotationModel;
import org.eclipse.swt.graphics.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The compare item is the input to the compare editor, and it ALSO is the document that is shown in the compare editor.
 * Finally it is responsible for pushing the new comments to the server
 *
 * @since 1.0
 */
public abstract class CommentableCompareItem extends Document
		implements ITypedElement, IModificationDate, IEditableContent, IStreamContentAccessor {
	private Logger logger = LoggerFactory.getLogger(CommentableCompareItem.class);

	private IDocument originalDocument;

	private AnnotationModel originalComments;

	private AnnotationModel editableComments;

	protected GerritClient gerrit;

//	private final long time;

	protected FileInfo fileInfo;

	private boolean dataLoaded = false; //Indicate whether the data has been retrieved from the server

	private final String commentSide;

	public CommentableCompareItem(String commentSide) {
		this.commentSide = commentSide;
	}

	void setOriginalDocument(IDocument documentWithComments) {
		this.originalDocument = documentWithComments;
	}

	void setOriginalComments(AnnotationModel gerritComments) {
		this.originalComments = gerritComments;
	}

	public void setEditableComments(AnnotationModel gerritComments) {
		this.editableComments = gerritComments;
	}

	void setGerritConnection(GerritClient gerrit) {
		this.gerrit = gerrit;
	}

	void setFile(FileInfo fileInfo) {
		this.fileInfo = fileInfo;
	}

	@Override
	public Image getImage() {
		return null;
	}

	@Override
	public long getModificationDate() {
		//TODO this  needs to be fixed
		return 0;
	}

	@Override
	public String getType() {
		return ITypedElement.UNKNOWN_TYPE;
	}

	@Override
	public boolean isEditable() {
		return !gerrit.getRepository().getServerInfo().isAnonymous() && !"D".equals(fileInfo.getStatus()); //$NON-NLS-1$
	}

	@Override
	// Extracts newly added comments from the content passed in, and publish new comments on the gerrit server
	public void setContent(byte[] newContent) {
		CommentExtractor extractor = new CommentExtractor();
		logger.debug("Sending additions: " + extractor.getAddedComments().size() + " removals: " //$NON-NLS-1$ //$NON-NLS-2$
				+ extractor.getRemovedComments().size() + " modifications: " + extractor.getModifiedComments().size()); //$NON-NLS-1$
		extractor.extractComments(originalDocument, originalComments, this, editableComments);
		for (CommentInfo newComment : extractor.getAddedComments()) {
			CreateDraftCommand publishDraft = gerrit.createDraftComments(getChangeId(), fileInfo.getRevision().getId());
			newComment.setSide(commentSide);
			publishDraft.setCommandInput(newComment);
			newComment.setPath(fileInfo.getPath());
			try {
				logger.debug("Adding comment: " + newComment); //$NON-NLS-1$
				fileInfo.getDraftComments().add(publishDraft.call());
			} catch (EGerritException e) {
				//This exception is handled by GerritCompareInput to properly handle problems while persisting.
				//The throwable is an additional trick that allows to detect, in case of failure, which side failed persisting.
				throw new RuntimeException(CommentableCompareItem.class.getName(),
						new Throwable(String.valueOf(hashCode())));
			}
		}
		for (CommentInfo deletedComment : extractor.getRemovedComments()) {
			DeleteDraftCommand deleteDraft = gerrit.deleteDraft(getChangeId(), fileInfo.getRevision().getId(),
					deletedComment.getId());
			try {
				logger.debug("Deleting comment: " + deletedComment); //$NON-NLS-1$
				deleteDraft.call();
				fileInfo.getDraftComments().remove(deletedComment);
			} catch (EGerritException e) {
				//This exception is handled by GerritCompareInput to properly handle problems while persisting.
				//The throwable is an additional trick that allows to detect, in case of failure, which side failed persisting.
				throw new RuntimeException(CommentableCompareItem.class.getName(),
						new Throwable(String.valueOf(hashCode())));
			}
		}
		for (CommentInfo modifiedComment : extractor.getModifiedComments()) {
			UpdateDraftCommand modifyDraft = gerrit.updateDraftComments(getChangeId(), fileInfo.getRevision().getId(),
					modifiedComment.getId());
			modifyDraft.setCommandInput(CommentInput.fromCommentInfo(modifiedComment));
			try {
				logger.debug("Modifying comment: " + modifiedComment); //$NON-NLS-1$
				modifyDraft.call();
				//Don't need to update the fileInfo structure like we do in other blocks
			} catch (EGerritException e) {
				//This exception is handled by GerritCompareInput to properly handle problems while persisting.
				//The throwable is an additional trick that allows to detect, in case of failure, which side failed persisting.
				throw new RuntimeException(CommentableCompareItem.class.getName(),
						new Throwable(String.valueOf(hashCode())));
			}
		}
	}

	private void loadComments() {
		QueryHelpers.loadComments(gerrit, fileInfo.getRevision());
		QueryHelpers.loadDrafts(gerrit, fileInfo.getRevision());
	}

	protected String getChangeId() {
		return fileInfo.getRevision().getChangeInfo().getId();
	}

	@Override
	public ITypedElement replace(ITypedElement dest, ITypedElement src) {
		return null;
	}

	/**
	 * Return the file info object for which this compare item is created
	 *
	 * @return {@link FileInfo}
	 */
	public FileInfo getFileInfo() {
		return fileInfo;
	}

	/**
	 * Return an annotation model representing the comments
	 *
	 * @return {@link AnnotationModel}
	 */
	public AnnotationModel getEditableComments() {
		return editableComments;
	}

	@Override
	public InputStream getContents() throws CoreException {
		prefetch();
		return new ByteArrayInputStream(get().getBytes());
	}

	private void prefetch() {
		if (dataLoaded) {
			return;
		}
		QueryHelpers.markAsReviewed(gerrit, fileInfo);
		loadComments();
		mergeCommentsInText(StringUtils.newStringUtf8(loadFileContent()));
		dataLoaded = true;
	}

	protected abstract byte[] loadFileContent();

	//Take the original text and merge the comments into it
	//The insertion of comments starts from by last comment and proceed toward the first one. This allows for the insertion line to always be correct.
	private void mergeCommentsInText(String text) {
		//Create a document and an associated annotation model to keep track of the original text w/ comments
		AnnotationModel originalComments = new CommentAnnotationManager();
		Document originalDocument = new Document(text);
		originalDocument.set(text);
		originalComments.connect(originalDocument);
		setOriginalComments(originalComments);
		setOriginalDocument(originalDocument);

		//Editable comments are a copy of the original comments but associated with the document that is presented in the UI
		AnnotationModel editableComments = new CommentAnnotationManager();
		set(text);
		editableComments.connect(this);
		setEditableComments(editableComments);

		if (fileInfo.getAllComments().isEmpty()) {
			return;
		}

		EList<CommentInfo> sortedComments = sortComments(filterComments(fileInfo.getAllComments()));

		for (CommentInfo commentInfo : sortedComments) {
			IRegion lineInfo;
			try {
				int insertionLineInDocument = 0;
				int insertionPosition = 0;
				String lineDelimiter = ""; //$NON-NLS-1$
				if (commentInfo.getLine() > 0) {
					insertionLineInDocument = commentInfo.getLine() - 1;
					lineInfo = originalDocument.getLineInformation(insertionLineInDocument);
					lineDelimiter = originalDocument.getLineDelimiter(insertionLineInDocument);
					insertionPosition = lineInfo.getOffset() + lineInfo.getLength()
							+ (lineDelimiter == null ? 0 : lineDelimiter.length());
				}
				int commentTextIndex = insertionPosition;
				String formattedComment = CommentPrettyPrinter.printComment(commentInfo);
				int commentTextLength = formattedComment.length();
				if (lineDelimiter == null) {
					formattedComment = originalDocument.getDefaultLineDelimiter() + formattedComment;
					commentTextIndex += originalDocument.getDefaultLineDelimiter().length();
				}
				formattedComment += originalDocument.getDefaultLineDelimiter();
				originalDocument.replace(insertionPosition, 0, formattedComment);
				replace(insertionPosition, 0, formattedComment);
				originalComments.addAnnotation(new GerritCommentAnnotation(commentInfo, formattedComment),
						new Position(commentTextIndex, commentTextLength));
				editableComments.addAnnotation(new GerritCommentAnnotation(commentInfo, formattedComment),
						new Position(commentTextIndex, commentTextLength));
			} catch (BadLocationException e) {
				logger.debug("Exception merging text and comments.", e); //$NON-NLS-1$
			}
		}
	}

	protected abstract EList<CommentInfo> filterComments(EList<CommentInfo> eList);

	private static EList<CommentInfo> sortComments(EList<CommentInfo> comments) {
		Collections.sort(comments, (CommentInfo o1, CommentInfo o2) -> {
			if (o1.getLine() == o2.getLine()) {
				return o1.getUpdated().compareTo(o2.getUpdated());
			}
			if (o1.getLine() < o2.getLine()) {
				return -1;
			}
			return 1;
		});
		Collections.reverse(comments);
		return comments;

	}

	public void reset() {
		dataLoaded = false;
	}
}