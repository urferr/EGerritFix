/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Guy Perron - initial API and implementation
 *
 *******************************************************************************/

package org.eclipse.egerrit.ui.editors.model;

import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.eclipse.compare.IEditableContent;
import org.eclipse.compare.IModificationDate;
import org.eclipse.compare.ITypedElement;
import org.eclipse.egerrit.core.GerritClient;
import org.eclipse.egerrit.core.command.CreateDraftCommand;
import org.eclipse.egerrit.core.exception.EGerritException;
import org.eclipse.egerrit.core.rest.CommentInfo;
import org.eclipse.egerrit.core.rest.FileInfo;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.source.AnnotationModel;
import org.eclipse.swt.graphics.Image;

/**
 * The compare item is the input to the compare editor, and it ALSO is the document that is shown in the compare editor.
 * Finally it is responsible for pushing the new comments to the server
 *
 * @since 1.0
 */
public class PatchSetCompareItem extends Document implements ITypedElement, IModificationDate, IEditableContent {
	private String fileName;

	private IDocument originalDocument;

	private AnnotationModel originalComments;

	private AnnotationModel editableComments;

	private GerritClient gerrit;

//	private final long time;

	private String change_id;

	private FileInfo fileInfo;

	void setFilename(String name) {
		this.fileName = name;
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

	void setRevision(FileInfo fileInfo) {
		this.fileInfo = fileInfo;
	}

	void setChange_id(String change_id) {
		this.change_id = change_id;
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
	public String getName() {
		return "Patch Set " + fileInfo.getContainingRevisionInfo().getNumber() + ": " //$NON-NLS-1$ //$NON-NLS-2$
				+ GerritCompareHelper.extractFilename(fileName);
	}

	@Override
	public String getType() {
		return ITypedElement.UNKNOWN_TYPE;
	}

	@Override
	public boolean isEditable() {
		return true;
	}

	@Override
	// Extracts newly added comments from the content passed in, and publish new comments on the gerrit server
	public void setContent(byte[] newContent) {
		ArrayList<CommentInfo> newComments = new CommentExtractor().extractComments(originalDocument, originalComments,
				this);
		for (CommentInfo newComment : newComments) {
			CreateDraftCommand publishDraft = gerrit.createDraftComments(change_id,
					fileInfo.getContainingRevisionInfo().getId());
			publishDraft.setCommentInfo(newComment);
			newComment.setPath(fileName);
			try {
				publishDraft.call();
			} catch (EGerritException | ClientProtocolException e) {
				//This exception is handled by GerritCompareInput to properly handle problems while persisting.
				//The throwable is an additional trick that allows to detect, in case of failure, which side failed persisting.
				throw new RuntimeException(PatchSetCompareItem.class.getName(),
						new Throwable(String.valueOf(hashCode())));
			}
		}
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
}
