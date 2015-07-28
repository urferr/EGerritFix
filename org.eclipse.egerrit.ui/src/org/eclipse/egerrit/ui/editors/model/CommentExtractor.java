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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.compare.rangedifferencer.RangeDifference;
import org.eclipse.compare.rangedifferencer.RangeDifferencer;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.egerrit.core.rest.CommentInfo;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.AnnotationModel;

//Expectations - when the extractor is being invoked, it is expected that modifications of the original text will have been done
// "properly". This means that any existing line will not have been modified, and that new comments are always added in a new line.
//IDocument line number starts at 0 whereas line numbering in Gerrit starts at 1
//CommentInfo line number is expressed in Gerrit line numbering system.

//The algorithm goes through the differences from top to bottom, and creates comments.
public class CommentExtractor {

	//The document with all the comments stored in the text.
	private IDocument documentWithOriginalComments;

	//The original comments represented as annotations over the document. This allows to capture the position of the comment, in the body of the text
	private AnnotationModel commentsModel;

	//The document that represents the results of the edition.
	private IDocument documentWithNewComments;

	//This counts the number of lines that were not in the document when the comments are not inserter.
	//This is necessary in order to create comments at the appropriate line number.
	private int numberOfRemovedLines = 0;

	Set<GerritCommentAnnotation> countedComments = new HashSet<>();

	/**
	 * Given two documents, compute the comments that have been added
	 *
	 * @param documentWithOriginalComments
	 * @param commentsModel
	 * @param documentWithNewComments
	 * @return
	 */
	public ArrayList<CommentInfo> extractComments(IDocument documentWithOriginalComments, AnnotationModel commentsModel,
			IDocument documentWithNewComments) {
		//Short-circuit the case where nothing changed.
		if (documentWithOriginalComments.get().equals(documentWithNewComments.get())) {
			return new ArrayList<>(0);
		}
		ArrayList<CommentInfo> newComments = new ArrayList<>();
		this.commentsModel = commentsModel;
		this.documentWithNewComments = documentWithNewComments;
		this.documentWithOriginalComments = documentWithOriginalComments;
		RangeDifference[] diffs = RangeDifferencer.findDifferences(new NullProgressMonitor(),
				new LineComparator(documentWithOriginalComments), new LineComparator(documentWithNewComments));
		for (RangeDifference aDiff : diffs) {
			newComments.add(createCommentFor(aDiff));
		}
		return newComments;
	}

	private CommentInfo createCommentFor(RangeDifference diff) {
		numberOfRemovedLines += sizeOfPreviousComments(diff.leftStart());
		int newCommentStart = diff.rightStart();
		String comment = extractCommentMessage(documentWithNewComments, diff);
		int lineCommented = diff.rightStart() - numberOfRemovedLines;
		numberOfRemovedLines += documentWithNewComments.computeNumberOfLines(comment);
		if (newCommentStart > 0) {
			GerritCommentAnnotation commentRepliedto = isAnswerToExistingComment(diff.leftStart());
			if (commentRepliedto == null || isDone(commentRepliedto)) {
				return newComment(comment, lineCommented);
			} else {
				return newComment(comment, commentRepliedto);
			}
		}
		return newComment(comment, lineCommented);
	}

	private boolean isDone(GerritCommentAnnotation comment) {
		if (comment == null) {
			return false;
		}
		return comment.getComment().getMessage().equalsIgnoreCase("done\n");
	}

	private int sizeOfPreviousComments(int line) {
		if (commentsModel == null) {
			return 0;
		}
		IRegion lineInfo;
		try {
			lineInfo = documentWithOriginalComments.getLineInformation(line - 1);
		} catch (BadLocationException e) {
			return 0;
		}
		int count = 0;
		Iterator<?> it = commentsModel.getAnnotationIterator(0, lineInfo.getOffset() + lineInfo.getLength(), true,
				true);
		while (it.hasNext()) {
			GerritCommentAnnotation aComment = (GerritCommentAnnotation) it.next();
			if (isCounted(aComment)) {
				continue;
			}
			Position annotationPosition = commentsModel.getPosition(aComment);
			try {
				count += documentWithOriginalComments.computeNumberOfLines(documentWithOriginalComments
						.get(annotationPosition.getOffset(), annotationPosition.getLength()));
				countComment(aComment);
			} catch (BadLocationException e) {
				//Can't happen
			}
		}
		return count;
	}

	private void countComment(GerritCommentAnnotation comment) {
		countedComments.add(comment);
	}

	private boolean isCounted(GerritCommentAnnotation comment) {
		return countedComments.contains(comment);
	}

	//Create a comment as a reply of a given one
	private CommentInfo newComment(String comment, GerritCommentAnnotation replyTo) {
		CommentInfo info = new CommentInfo();
		info.setLine(replyTo.getComment().getLine());
		info.setMessage(comment);
		info.setPath(replyTo.getComment().getPath());
		info.setInReplyTo(replyTo.getComment().getId());
		return info;
	}

	//Create a new comment
	private CommentInfo newComment(String comment, int line) {
		CommentInfo info = new CommentInfo();
		info.setLine(line);
		info.setMessage(comment);
		return info;
	}

	//Detect whether the new text at the given line is an answer to an existing comment
	private GerritCommentAnnotation isAnswerToExistingComment(int line) {
		if (commentsModel == null) {
			return null;
		}
		IRegion lineInfo;
		try {
			lineInfo = documentWithOriginalComments.getLineInformation(line - 1);
		} catch (BadLocationException e) {
			return null;
		}
		Iterator<?> it = commentsModel.getAnnotationIterator(lineInfo.getOffset(), lineInfo.getLength(), true, true);
		if (it.hasNext()) {
			return ((GerritCommentAnnotation) it.next());
		}
		return null;
	}

	private String extractCommentMessage(IDocument doc, RangeDifference diff) {
		StringBuffer comment = new StringBuffer();
		for (int line = diff.rightStart(); line < diff.rightStart() + diff.rightLength(); line++) {
			comment.append(getLineContent(doc, line));
			comment.append('\n');
		}
		return comment.toString();
	}

	private String getLineContent(IDocument doc, int line) {
		try {
			IRegion lineRegion = doc.getLineInformation(line);
			return doc.get(lineRegion.getOffset(), lineRegion.getLength());
		} catch (BadLocationException e) {
			return ""; //Can't happen //$NON-NLS-1$
		}
	}
}
