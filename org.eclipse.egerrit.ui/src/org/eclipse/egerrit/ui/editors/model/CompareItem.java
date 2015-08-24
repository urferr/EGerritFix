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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.eclipse.compare.IEditableContent;
import org.eclipse.compare.IModificationDate;
import org.eclipse.compare.IStreamContentAccessor;
import org.eclipse.compare.ITypedElement;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.egerrit.core.Gerrit;
import org.eclipse.egerrit.core.command.CreateDraftCommand;
import org.eclipse.egerrit.core.exception.EGerritException;
import org.eclipse.egerrit.core.rest.CommentInfo;
import org.eclipse.egerrit.core.rest.FileInfo;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.source.AnnotationModel;
import org.eclipse.swt.graphics.Image;

/**
 * @since 1.0
 */
public class CompareItem implements IStreamContentAccessor, ITypedElement, IModificationDate, IEditableContent {
	private String fileName;

	private IDocument documentWithComments;

	private AnnotationModel gerritComments;

	private Gerrit gerrit;

//	private final long time;

	private String change_id;

	private FileInfo fileInfo;

	void setFilename(String name) {
		this.fileName = name;
	}

	void setDocumentWithComments(IDocument documentWithComments) {
		this.documentWithComments = documentWithComments;
	}

	void setGerritComments(AnnotationModel gerritComments) {
		this.gerritComments = gerritComments;
	}

	void setGerritConnection(Gerrit gerrit) {
		this.gerrit = gerrit;
	}

	void setRevision(FileInfo fileInfo) {
		this.fileInfo = fileInfo;
	}

	void setChange_id(String change_id) {
		this.change_id = change_id;
	}

	public InputStream getContents() throws CoreException {
		return new ByteArrayInputStream(documentWithComments.get().getBytes());
	}

	public Image getImage() {
		return null;
	}

	public long getModificationDate() {
		//TODO this  needs to be fixed
		return 0;
	}

	public String getName() {
		return fileName;
	}

	public String getType() {
		return ITypedElement.TEXT_TYPE;
	}

	@Override
	public boolean isEditable() {
		return true;
	}

	@Override
	// Extracts newly added comments from the content passed in, and publish new comments on the gerrit server
	public void setContent(byte[] newContent) {
		ArrayList<CommentInfo> newComments = new CommentExtractor().extractComments(documentWithComments,
				gerritComments, new Document(new String(newContent)));
		for (CommentInfo newComment : newComments) {
			CreateDraftCommand publishDraft = gerrit.createDraftComments(change_id,
					fileInfo.getContainingRevisionInfo().getId());
			publishDraft.setCommentInfo(newComment);
			newComment.setPath(fileName);
			try {
				publishDraft.call();
			} catch (EGerritException e) {
				//This exception is handled by GerritCompareInput to properly handle problems while persisting.
				throw new RuntimeException(CompareItem.class.getName(), e);
			} catch (ClientProtocolException e) {
				throw new RuntimeException(CompareItem.class.getName(), e);
			}
		}
	}

	@Override
	public ITypedElement replace(ITypedElement dest, ITypedElement src) {
		System.out.println("replace called");
		return null;
	}

}
