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

package org.eclipse.egerrit.internal.ui.compare;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.egerrit.internal.model.CommentInfo;
import org.eclipse.egerrit.internal.model.ModelFactory;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.BadPositionCategoryException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.AnnotationModel;

//Expectations - when the extractor is being invoked, it is expected that modifications of the original text will have been done
// "properly". This means that any existing line will not have been modified, and that new comments are always added in a new line.
//IDocument line number starts at 0 whereas line numbering in Gerrit starts at 1
//CommentInfo line number is expressed in Gerrit line numbering system.

//The algorithm goes through the differences from top to bottom, and creates comments.
public class CommentExtractor {

	//The annotation model carrying all the comments that existed in the original document
	private AnnotationModel originalCommentsModel;

	//The original annotations represented as a list.
	private ArrayList<GerritCommentAnnotation> originalComments;

	//The document that represents the results of the edition.
	private IDocument newDocument;

	//The annotation model carrying all the annotations contained in the new document
	//Note that all the GerritCommentAnnotation that are new have null has commentInfo
	private AnnotationModel newCommentsModel;

	//The annotations represented as a list.
	private ArrayList<GerritCommentAnnotation> newComments;

	//This counts the number of lines that were not in the document when the comments are not inserted.
	//This is necessary in order to create comments at the appropriate line number.
	private int numberOfRemovedLines = 0;

	private ArrayList<CommentInfo> addedComments = new ArrayList<>();

	private ArrayList<CommentInfo> modifiedComments = new ArrayList<>();

	private ArrayList<CommentInfo> removedComments = new ArrayList<>();

	private IDocument originalDocument;

	/**
	 * Given two documents, compute the comments that have been added
	 *
	 * @param documentWithOriginalComments
	 * @param originalCommentsModel
	 * @param documentWithNewComments
	 */
	public void extractComments(IDocument documentWithOriginalComments, AnnotationModel originalCommentsModel,
			IDocument documentWithNewComments, AnnotationModel commentsModelWithNewComments) {
		//Short-circuit the case where nothing changed.
		if (documentWithOriginalComments.get().equals(documentWithNewComments.get())) {
			return;
		}

		this.originalCommentsModel = originalCommentsModel;
		this.originalComments = toAnnotationList(originalCommentsModel, documentWithOriginalComments);
		this.newDocument = documentWithNewComments;
		this.originalDocument = documentWithOriginalComments;
		this.newCommentsModel = commentsModelWithNewComments;
		this.newComments = toAnnotationList(commentsModelWithNewComments, documentWithNewComments);

		for (GerritCommentAnnotation newComment : newComments) {
			GerritCommentAnnotation match = null;
			if (newComment.getComment() == null) {
				createNewComment(newComment);
				continue;
			}
			if ((match = wasPresentButModified(newComment)) != null) {
				originalComments.remove(match);
				handleModifiedComment(newComment, match);
				continue;
			}
			if ((match = wasPresentAndUnmodified(newComment)) != null) {
				numberOfRemovedLines += numberOfLines(newComment);
				originalComments.remove(match);
				continue;
			}
		}
		handleOldComments();
	}

	private void handleModifiedComment(GerritCommentAnnotation newComment, GerritCommentAnnotation match) {
		numberOfRemovedLines += numberOfLines(newComment);

		//The comment did not change. It probably just moved because of previous insertions so there is nothing to do
		if (extractCommentMessage(originalDocument, originalCommentsModel, match)
				.equals(extractCommentMessage(newDocument, newCommentsModel, newComment))) {
			return;
		}
		modifiedComments.add(modifyComment(newComment));
	}

	private void handleOldComments() {
		for (GerritCommentAnnotation comment : originalComments) {
			removedComments.add(comment.getComment());
		}
	}

	private CommentInfo modifyComment(GerritCommentAnnotation comment) {
		CommentInfo infoToModify = comment.getComment();
		infoToModify.setMessage(extractModifiedComment(comment));
		return infoToModify;
	}

	//This deal with the case where the user typed in more content in an existing comment
	//In this case, we need to remove the "pretty printing" that has been done such as the author name, and the date
	private String extractModifiedComment(GerritCommentAnnotation newComment) {
		String comment = extractCommentMessage(newDocument, newCommentsModel, newComment);
		String name = CommentPrettyPrinter.printName(newComment.getComment());
		String date = CommentPrettyPrinter.printDate(newComment.getComment());
		if (comment.startsWith(name)) {
			comment = comment.substring(name.length());
		}
		int dateIdx = comment.lastIndexOf(date);
		if (dateIdx > 0) {
			if (dateIdx + date.length() + 1 == comment.length()) {
				comment = comment.substring(0, dateIdx);
			} else {
				String end = comment.substring(dateIdx + date.length());
				comment = comment.substring(0, dateIdx) + end;
			}
		}
		return comment.trim();
	}

	private void createNewComment(GerritCommentAnnotation newComment) {
		int lineCommented = getLineNumber(newComment) - numberOfRemovedLines;
		String comment = extractCommentMessage(newDocument, newCommentsModel, newComment);
		numberOfRemovedLines += newDocument.computeNumberOfLines(comment);
		if (comment.trim().length() == 0) {
			return;
		}
		if (lineCommented > 0) {
			GerritCommentAnnotation commentRepliedTo = isAnswerToExistingComment(lineCommented);
			if (commentRepliedTo == null) {
				addedComments.add(newComment(comment, lineCommented));
			} else {
				addedComments.add(newComment(comment, commentRepliedTo));
			}
		} else {
			//Create file comment
			addedComments.add(newComment(comment, 0));
		}
	}

	//Return the line number where the annotation starts
	private int getLineNumber(GerritCommentAnnotation newComment) {
		Position commentPosition = newCommentsModel.getPosition(newComment);
		try {
			return newDocument.getLineOfOffset(commentPosition.getOffset());
		} catch (BadLocationException e) {
			//Can't happen
			return -1;
		}
	}

	//Given an annotation, return the number of lines it represents
	private int numberOfLines(GerritCommentAnnotation comment) {
		return newDocument.computeNumberOfLines(extractCommentMessage(newDocument, newCommentsModel, comment)) + 1;
	}

	//convert an annotation model to a list
	private static ArrayList<GerritCommentAnnotation> toAnnotationList(AnnotationModel commentsModel,
			IDocument associatedDocument) {
		ArrayList<GerritCommentAnnotation> sortedComments = new ArrayList<>();
		try {
			Position[] positions = associatedDocument.getPositions(IDocument.DEFAULT_CATEGORY);
			for (Position position : positions) {
				Iterator<?> match = commentsModel.getAnnotationIterator(position.getOffset(), position.getLength(),
						false, false);
				if (match.hasNext()) {
					sortedComments.add((GerritCommentAnnotation) match.next());
				}
			}
		} catch (BadPositionCategoryException e) {
			//Can't happen
		}
		return sortedComments;

	}

	//check if the comment existed in the original comments
	private GerritCommentAnnotation wasPresentAndUnmodified(GerritCommentAnnotation newComment) {
		if (newComment.getComment() == null) {
			return null;
		}
		Iterator<?> it = originalCommentsModel.getAnnotationIterator();
		Position positionNewComment = newCommentsModel.getPosition(newComment);
		while (it.hasNext()) {
			GerritCommentAnnotation object = (GerritCommentAnnotation) it.next();
			Position positingExistingComment = originalCommentsModel.getPosition(object);
			if (object.getComment().equals(newComment.getComment())
					&& positingExistingComment.equals(positionNewComment)) {
				return object;
			}
		}
		return null;
	}

	//Find a comment with the same id
	private GerritCommentAnnotation wasPresentButModified(GerritCommentAnnotation newComment) {
		if (newComment.getComment() == null) {
			return null;
		}
		for (GerritCommentAnnotation commentInfo : originalComments) {
			if (commentInfo.getComment().getId().equals(newComment.getComment().getId())) {
				return commentInfo;
			}
		}
		return null;
	}

	/**
	 * Get the list of comments that have been added
	 *
	 * @return list of {@link CommentInfo}
	 */
	public ArrayList<CommentInfo> getAddedComments() {
		return addedComments;
	}

	/**
	 * Get the list of comments that have been modified
	 *
	 * @return list of {@link CommentInfo}
	 */
	public ArrayList<CommentInfo> getModifiedComments() {
		return modifiedComments;
	}

	/**
	 * Get the list of comments that have been removed
	 *
	 * @return list of {@link CommentInfo}
	 */
	public ArrayList<CommentInfo> getRemovedComments() {
		return removedComments;
	}

	private boolean isDone(GerritCommentAnnotation comment) {
		if (comment == null) {
			return false;
		}
		return comment.getComment().getMessage().equalsIgnoreCase("done"); //$NON-NLS-1$
	}

	//Create a comment as a reply of a given one
	private CommentInfo newComment(String comment, GerritCommentAnnotation replyTo) {
		CommentInfo info = ModelFactory.eINSTANCE.createCommentInfo();
		info.setLine(replyTo.getComment().getLine());
		info.setMessage(comment);
		info.setPath(replyTo.getComment().getPath());
		info.setInReplyTo(replyTo.getComment().getId());
		return info;
	}

	//Create a new comment
	private CommentInfo newComment(String comment, int line) {
		CommentInfo info = ModelFactory.eINSTANCE.createCommentInfo();
		info.setLine(line);
		info.setMessage(comment);
		return info;
	}

	//Detect whether the new text at the given line is an answer to an existing comment
	//An comment is a answer if has the same line number, the comment is not a draft and it is done "done"
	private GerritCommentAnnotation isAnswerToExistingComment(int line) {
		GerritCommentAnnotation match = null;
		for (GerritCommentAnnotation comment : newComments) {
			if (comment.getComment() == null) {
				continue;
			}
			if (comment.getComment().getLine() == line && comment.getComment().getAuthor() != null) {
				if (isDone(comment)) {
					if (match != null && match.getComment().getId().equals(comment.getComment().getInReplyTo())) {
						match = null;
					}
				} else {
					match = comment;
				}
			}
		}
		return match;
	}

	private static String extractCommentMessage(IDocument document, AnnotationModel annotationModel,
			GerritCommentAnnotation commentAnnotation) {
		Position position = annotationModel.getPosition(commentAnnotation);
		try {
			return document.get(position.offset, position.length);
		} catch (BadLocationException e) {
			//Can't happen. The comment model have been constructed properly
		}
		return null;
	}
}
