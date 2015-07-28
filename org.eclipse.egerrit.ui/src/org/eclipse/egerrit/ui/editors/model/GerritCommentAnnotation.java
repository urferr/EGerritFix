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

package org.eclipse.egerrit.ui.editors.model;

import org.eclipse.egerrit.core.rest.CommentInfo;
import org.eclipse.jface.text.source.Annotation;

/*
 * Implements an annotation that help us connect the annotation in a document with a gerrit comment
 */
public class GerritCommentAnnotation extends Annotation {
	private CommentInfo comment;

	private String formattedComment;

	/**
	 * Instantiate an annotation with the comment and its textual representation shown in the document
	 *
	 * @param comment
	 * @param formattedComment
	 */
	public GerritCommentAnnotation(CommentInfo comment, String formattedComment) {
		this.comment = comment;
		this.formattedComment = formattedComment;
	}

	@Override
	public String getText() {
		return formattedComment;
	}

	/**
	 * Return the gerrit comment that is represented by this annotation
	 *
	 * @return the comment
	 */
	public CommentInfo getComment() {
		return comment;
	}

}
