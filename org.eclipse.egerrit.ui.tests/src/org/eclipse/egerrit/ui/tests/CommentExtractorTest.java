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

package org.eclipse.egerrit.ui.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.egerrit.core.rest.AccountInfo;
import org.eclipse.egerrit.core.rest.CommentInfo;
import org.eclipse.egerrit.ui.editors.model.CommentExtractor;
import org.eclipse.egerrit.ui.editors.model.GerritCommentAnnotation;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.AnnotationModel;
import org.junit.Test;

public class CommentExtractorTest {
	private IDocument initialDocument;

	private AnnotationModel initialComments;

	private IDocument newDocument;

	private AnnotationModel newComments;

	/*
	 * Test the case where the document has not been changed and no comments were in the original document
	 */
	@Test
	public void noChangeNoComment() {
		CommentExtractor extractor = new CommentExtractor();
		extractor.extractComments(new Document("000000000\n111111111\n222222222\n333333333\n"), null, //$NON-NLS-1$
				new Document("000000000\n111111111\n222222222\n333333333\n"), null); //$NON-NLS-1$
		ArrayList<CommentInfo> newComments = extractor.getAddedComments();
		assertEquals(0, newComments.size());
	}

	/*
	 * Test the case where the document has not been changed and there were comments in the original document
	 */
	@Test
	public void noChangeWithComments() {
		setupOriginalDocument("000000000\naaaa\n111111111\n222222222\n333333333\n", (String[]) null); //$NON-NLS-1$
		setupNewDocument("000000000\naaaa\n111111111\n222222222\n333333333\n", (String[]) null, (String[]) null); //$NON-NLS-1$
		CommentExtractor extractor = new CommentExtractor();
		extractor.extractComments(initialDocument, initialComments, newDocument, newComments);

		assertEquals(0, extractor.getAddedComments().size());
		assertEquals(0, extractor.getModifiedComments().size());
		assertEquals(0, extractor.getRemovedComments().size());
	}

	/*
	 * Test the case where one comment has been added
	 */
	@Test
	public void addOneComment() {
		setupOriginalDocument("000000000\n111111111\n222222222\n333333333\n", (String[]) null); //$NON-NLS-1$
		setupNewDocument("000000000\naaaa\n111111111\n222222222\n333333333\n", (String[]) null, //$NON-NLS-1$
				new String[] { "aaaa\n" } //$NON-NLS-1$
		);
		CommentExtractor extractor = new CommentExtractor();
		extractor.extractComments(initialDocument, initialComments, newDocument, newComments);

		ArrayList<CommentInfo> newComments = extractor.getAddedComments();
		assertEquals(1, newComments.size());
		assertEquals(1, newComments.get(0).getLine());
		assertEquals("aaaa\n", newComments.get(0).getMessage()); //$NON-NLS-1$
		assertEquals(0, extractor.getModifiedComments().size());
		assertEquals(0, extractor.getRemovedComments().size());
	}

	/*
	 * Test the case where one comment has been added on the last line
	 */
	@Test
	public void addOneCommentOnLastLine() {
		setupOriginalDocument("000000000\n111111111\n222222222\n333333333\n", (String[]) null); //$NON-NLS-1$
		setupNewDocument("000000000\n111111111\n222222222\n333333333\naaaa\n", //$NON-NLS-1$
				(String[]) null, new String[] { "aaaa\n" }); //$NON-NLS-1$
		CommentExtractor extractor = new CommentExtractor();
		extractor.extractComments(initialDocument, initialComments, newDocument, newComments);

		ArrayList<CommentInfo> newComments = extractor.getAddedComments();
		assertEquals(1, newComments.size());
		assertEquals(4, newComments.get(0).getLine());
		assertEquals("aaaa\n", newComments.get(0).getMessage()); //$NON-NLS-1$
		assertEquals(0, extractor.getModifiedComments().size());
		assertEquals(0, extractor.getRemovedComments().size());
	}

	/*
	 * Test the case where one comment is being added and the original document already had a comment
	 */
	@Test
	public void addCommentAfterExistingOne() {
		setupOriginalDocument("000000000\naaaa\n111111111\n222222222\n333333333\n", "aaaa\n"); //$NON-NLS-1$ //$NON-NLS-2$
		setupNewDocument("000000000\naaaa\n111111111\n222222222\nbbbb\n333333333\n", new String[] { "aaaa\n" }, //$NON-NLS-1$//$NON-NLS-2$
				new String[] { "bbbb\n" }); //$NON-NLS-1$
		CommentExtractor extractor = new CommentExtractor();
		extractor.extractComments(initialDocument, initialComments, newDocument, newComments);

		ArrayList<CommentInfo> newComments = extractor.getAddedComments();
		assertEquals(1, newComments.size());
		assertEquals(3, newComments.get(0).getLine());
		assertEquals("bbbb\n", newComments.get(0).getMessage()); //$NON-NLS-1$
		assertEquals(0, extractor.getModifiedComments().size());
		assertEquals(0, extractor.getRemovedComments().size());
	}

	/*
	 * Test the case where a comment has been added on multiple lines
	 */
	@Test
	public void addOneMultiLineComment() {
		setupOriginalDocument("000000000\naaaa\n111111111\n222222222\n333333333\n", (String[]) null); //$NON-NLS-1$
		setupNewDocument("000000000\naaaa\naaaa\n111111111\n222222222\n333333333\n", //$NON-NLS-1$
				(String[]) null, new String[] { "aaaa\naaaa\n" }); //$NON-NLS-1$
		CommentExtractor extractor = new CommentExtractor();
		extractor.extractComments(initialDocument, initialComments, newDocument, newComments);

		ArrayList<CommentInfo> newComments = extractor.getAddedComments();
		assertEquals(1, newComments.size());
		assertEquals(1, newComments.get(0).getLine());
		assertEquals("aaaa\naaaa\n", newComments.get(0).getMessage()); //$NON-NLS-1$
		assertEquals(0, extractor.getModifiedComments().size());
		assertEquals(0, extractor.getRemovedComments().size());
	}

	/*
	 * Test the case where multiple comments have been added at the same time
	 */
	@Test
	public void addMultipleComments() {
		setupOriginalDocument("000000000\n111111111\n222222222\n333333333\n", (String[]) null); //$NON-NLS-1$
		setupNewDocument("000000000\naaaa\naaaa\n111111111\n222222222\ncccc\n333333333\n", (String[]) null, //$NON-NLS-1$
				new String[] { "aaaa\naaaa\n", "cccc\n" }); //$NON-NLS-1$ //$NON-NLS-2$
		CommentExtractor extractor = new CommentExtractor();
		extractor.extractComments(initialDocument, initialComments, newDocument, newComments);

		ArrayList<CommentInfo> newComments = extractor.getAddedComments();
		assertEquals(2, newComments.size());

		assertEquals(1, newComments.get(0).getLine());
		assertEquals("aaaa\naaaa\n", newComments.get(0).getMessage()); //$NON-NLS-1$

		assertEquals(3, newComments.get(1).getLine());
		assertEquals("cccc\n", newComments.get(1).getMessage()); //$NON-NLS-1$
		assertEquals(0, extractor.getModifiedComments().size());
		assertEquals(0, extractor.getRemovedComments().size());
	}

	/*
	 * Test the case where a comment is being replied to
	 */
	@Test
	public void answerToComment() {
		setupOriginalDocument("000000000\nauthorAaa\n111111111\n222222222\n333333333\n", "authorAaa\n"); //$NON-NLS-1$ //$NON-NLS-2$
		setupNewDocument("000000000\nauthorAaa\nanAnswer\n111111111\n222222222\n333333333\n", //$NON-NLS-1$
				new String[] { "authorAaa\n" }, //$NON-NLS-1$
				new String[] { "anAnswer\n" }); //$NON-NLS-1$
		CommentExtractor extractor = new CommentExtractor();
		extractor.extractComments(initialDocument, initialComments, newDocument, newComments);

		ArrayList<CommentInfo> newComments = extractor.getAddedComments();
		assertEquals(1, newComments.size());

		assertEquals(1, newComments.get(0).getLine());
		assertEquals("anAnswer\n", newComments.get(0).getMessage()); //$NON-NLS-1$
		assertEquals("ID-authorAaa\n", newComments.get(0).getInReplyTo()); //$NON-NLS-1$
		assertEquals(0, extractor.getModifiedComments().size());
		assertEquals(0, extractor.getRemovedComments().size());
	}

	/*
	 * Test the case where a comment is being replied to
	 */
	@Test
	public void notAnAnswer() {
		setupOriginalDocument("000000000\naaaa\n111111111\n222222222\n333333333\n", "aaaa\n"); //$NON-NLS-1$ //$NON-NLS-2$
		setupNewDocument("000000000\naaaa\nnotAnswer\n111111111\n222222222\n333333333\n", new String[] { "aaaa\n" }, //$NON-NLS-1$//$NON-NLS-2$
				new String[] { "notAnswer\n" }); //$NON-NLS-1$
		CommentExtractor extractor = new CommentExtractor();
		extractor.extractComments(initialDocument, initialComments, newDocument, newComments);

		ArrayList<CommentInfo> newComments = extractor.getAddedComments();
		assertEquals(1, newComments.size());

		assertEquals(1, newComments.get(0).getLine());
		assertEquals("notAnswer\n", newComments.get(0).getMessage()); //$NON-NLS-1$
		assertNull(newComments.get(0).getInReplyTo());
		assertEquals(0, extractor.getModifiedComments().size());
		assertEquals(0, extractor.getRemovedComments().size());
	}

	/*
	 * Test the case where a multi line comment is being replied to
	 */
	@Test
	public void answerToMultilineComment() {
		setupOriginalDocument("000000000\n111111111\n222222222\nauthoraaaa\nbbbb\n333333333\n", "authoraaaa\nbbbb\n"); //$NON-NLS-1$ //$NON-NLS-2$
		setupNewDocument("000000000\n111111111\n222222222\nauthoraaaa\nbbbb\ncccc\n333333333\n", //$NON-NLS-1$
				new String[] { "authoraaaa\nbbbb\n" }, //$NON-NLS-1$
				new String[] { "cccc\n" }); //$NON-NLS-1$

		CommentExtractor extractor = new CommentExtractor();
		extractor.extractComments(initialDocument, initialComments, newDocument, newComments);

		ArrayList<CommentInfo> newComments = extractor.getAddedComments();
		assertEquals(1, newComments.size());
		assertEquals(3, newComments.get(0).getLine());
		assertEquals("cccc\n", newComments.get(0).getMessage()); //$NON-NLS-1$
		assertEquals("ID-authoraaaa\nbbbb\n", newComments.get(0).getInReplyTo()); //$NON-NLS-1$
		assertEquals(0, extractor.getModifiedComments().size());
		assertEquals(0, extractor.getRemovedComments().size());
	}

	/*
	 * Test the case where comments are closed
	 */
	@Test
	public void closingComment() {
		setupOriginalDocument("000000000\nauthoraaaa\n111111111\n222222222\nauthorbbbb\n333333333\n", "authoraaaa\n"); //$NON-NLS-1$//$NON-NLS-2$
		setupNewDocument("000000000\nauthoraaaa\ndone\n111111111\n222222222\nauthorbbbb\n333333333\n", //$NON-NLS-1$
				new String[] { "authoraaaa\n", }, //$NON-NLS-1$
				new String[] { "done\n" }); //$NON-NLS-1$

		CommentExtractor extractor = new CommentExtractor();
		extractor.extractComments(initialDocument, initialComments, newDocument, newComments);

		ArrayList<CommentInfo> newComments = extractor.getAddedComments();
		assertEquals(1, newComments.size());

		assertEquals(1, newComments.get(0).getLine());
		assertEquals("done\n", newComments.get(0).getMessage()); //$NON-NLS-1$
		assertEquals("ID-authoraaaa\n", newComments.get(0).getInReplyTo()); //$NON-NLS-1$

		assertEquals(0, extractor.getModifiedComments().size());
		assertEquals(0, extractor.getRemovedComments().size());
	}

	/*
	 * Test comment answer and new commment
	 */
	@Test
	public void anwerToCommentThenInsertANewComment() {
		setupOriginalDocument("000000000\nauthor\n111111111\n222222222\n333333333\n", "author\n"); //$NON-NLS-1$//$NON-NLS-2$
		setupNewDocument("000000000\nauthor\nbbbb\n111111111\n222222222\ncccc\n333333333\n", //$NON-NLS-1$
				new String[] { "author\n" }, //$NON-NLS-1$
				new String[] { "bbbb\n", "cccc\n" }); //$NON-NLS-1$//$NON-NLS-2$

		CommentExtractor extractor = new CommentExtractor();
		extractor.extractComments(initialDocument, initialComments, newDocument, newComments);

		ArrayList<CommentInfo> newComments = extractor.getAddedComments();
		assertEquals(2, newComments.size());

		assertEquals(1, newComments.get(0).getLine());
		assertEquals("bbbb\n", newComments.get(0).getMessage()); //$NON-NLS-1$

		assertEquals(3, newComments.get(1).getLine());
		assertEquals("cccc\n", newComments.get(1).getMessage()); //$NON-NLS-1$
		assertEquals(0, extractor.getModifiedComments().size());
		assertEquals(0, extractor.getRemovedComments().size());
	}

	/*
	 * Test new comment then a comment is being answered to
	 */
	@Test
	public void insertANewCommentThenAnswerToExistingOne() {
		setupOriginalDocument("000000000\n111111111\n222222222\ncccc\n333333333\n", "cccc\n"); //$NON-NLS-1$//$NON-NLS-2$
		setupNewDocument("000000000\naaaa\naaaa\n111111111\n222222222\ncccc\ndddd\n333333333\n", //$NON-NLS-1$
				new String[] { "cccc\n" }, new String[] { "aaaa\naaaa\n", "dddd\n" }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		CommentExtractor extractor = new CommentExtractor();
		extractor.extractComments(initialDocument, initialComments, newDocument, newComments);

		ArrayList<CommentInfo> newComments = extractor.getAddedComments();
		assertEquals(2, newComments.size());

		assertEquals(1, newComments.get(0).getLine());
		assertEquals("aaaa\naaaa\n", newComments.get(0).getMessage()); //$NON-NLS-1$

		assertEquals(3, newComments.get(1).getLine());
		assertEquals("dddd\n", newComments.get(1).getMessage()); //$NON-NLS-1$
		assertEquals(0, extractor.getModifiedComments().size());
		assertEquals(0, extractor.getRemovedComments().size());
	}

	/*
	 * Test case where a file comment is added
	 */
	@Test
	public void createFileComment() {
		setupOriginalDocument("000000000\n111111111\n222222222\n333333333\n", (String[]) null); //$NON-NLS-1$
		setupNewDocument("aaaa\naaaa\n000000000\n111111111\n222222222\n333333333\n", (String[]) null, //$NON-NLS-1$
				new String[] { "aaaa\naaaa\n" }); //$NON-NLS-1$

		CommentExtractor extractor = new CommentExtractor();
		extractor.extractComments(initialDocument, initialComments, newDocument, newComments);

		ArrayList<CommentInfo> newComments = extractor.getAddedComments();
		assertEquals(1, newComments.size());

		assertEquals(0, newComments.get(0).getLine());
		assertEquals("aaaa\naaaa\n", newComments.get(0).getMessage()); //$NON-NLS-1$
		assertEquals(0, extractor.getModifiedComments().size());
		assertEquals(0, extractor.getRemovedComments().size());
	}

	/*
	 * Open a new comment after one had been closed
	 */
	@Test
	public void createNewCommentAfterClosedComment() {
		setupOriginalDocument("000000000\nauthor\ndone\n111111111\n222222222\n333333333\n", "author\n", "done\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		setupNewDocument("000000000\nauthor\ndone\nbbbb\n111111111\n222222222\n333333333\n", //$NON-NLS-1$
				new String[] { "author\n", "done\n" }, new String[] { "bbbb\n" }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		//We need to tweak the generated defaults to have done be a reply to "author"
		Iterator<?> it = newComments.getAnnotationIterator();
		while (it.hasNext()) {
			GerritCommentAnnotation comment = (GerritCommentAnnotation) it.next();
			if (comment.getComment() != null && comment.getComment().getMessage().startsWith("done")) { //$NON-NLS-1$
				comment.getComment().setInReplyTo("ID-author\n"); //$NON-NLS-1$
			}
		}

		Iterator<?> it2 = initialComments.getAnnotationIterator();
		while (it2.hasNext()) {
			GerritCommentAnnotation comment = (GerritCommentAnnotation) it2.next();
			if (comment.getComment() != null && comment.getComment().getMessage().startsWith("done")) { //$NON-NLS-1$
				comment.getComment().setInReplyTo("ID-author\n"); //$NON-NLS-1$
			}
		}

		CommentExtractor extractor = new CommentExtractor();
		extractor.extractComments(initialDocument, initialComments, newDocument, newComments);

		ArrayList<CommentInfo> newComments = extractor.getAddedComments();
		assertEquals(1, newComments.size());

		assertEquals(1, newComments.get(0).getLine());
		assertEquals("bbbb\n", newComments.get(0).getMessage()); //$NON-NLS-1$
		assertNull(newComments.get(0).getInReplyTo());
		assertEquals(0, extractor.getModifiedComments().size());
		assertEquals(0, extractor.getRemovedComments().size());
	}

	/*
	 * Test the case where an existing comment is being deleted, and just that.
	 */
	@Test
	public void deleteExistingDraft() {
		setupOriginalDocument("000000000\naaaa\n111111111\n222222222\n333333333\n", "aaaa\n"); //$NON-NLS-1$ //$NON-NLS-2$
		setupNewDocument("000000000\n111111111\n222222222\n333333333\n", (String[]) null, (String[]) null); //$NON-NLS-1$

		CommentExtractor extractor = new CommentExtractor();
		extractor.extractComments(initialDocument, initialComments, newDocument, newComments);

		ArrayList<CommentInfo> newComments = extractor.getAddedComments();
		ArrayList<CommentInfo> deletedComments = extractor.getRemovedComments();
		ArrayList<CommentInfo> modifiedComments = extractor.getModifiedComments();
		assertEquals(0, newComments.size());
		assertEquals(1, deletedComments.size());
		assertEquals(0, modifiedComments.size());
		assertEquals("aaaa\n", deletedComments.get(0).getMessage()); //$NON-NLS-1$
	}

	@Test
	public void deleteExistingMultiLineDraft() {
		setupOriginalDocument("000000000\naaaa\nbbbb\n111111111\n222222222\n333333333\n", "aaaa\nbbbb\n"); //$NON-NLS-1$ //$NON-NLS-2$
		setupNewDocument("000000000\n111111111\n222222222\n333333333\n", (String[]) null, (String[]) null); //$NON-NLS-1$

		CommentExtractor extractor = new CommentExtractor();
		extractor.extractComments(initialDocument, initialComments, newDocument, newComments);

		ArrayList<CommentInfo> newComments = extractor.getAddedComments();
		ArrayList<CommentInfo> deletedComments = extractor.getRemovedComments();
		ArrayList<CommentInfo> modifiedComments = extractor.getModifiedComments();
		assertEquals(0, newComments.size());
		assertEquals(1, deletedComments.size());
		assertEquals(0, modifiedComments.size());
		assertEquals("aaaa\nbbbb\n", deletedComments.get(0).getMessage()); //$NON-NLS-1$
	}

	@Test
	public void deleteFollowedByAdditionLowerDown() {
		setupOriginalDocument("000000000\naaaa\n111111111\n222222222\n333333333\n", "aaaa\n"); //$NON-NLS-1$ //$NON-NLS-2$
		setupNewDocument("000000000\n111111111\n222222222\nbbbb\n333333333\n", (String[]) null, //$NON-NLS-1$
				new String[] { "bbbb\n" }); //$NON-NLS-1$

		CommentExtractor extractor = new CommentExtractor();
		extractor.extractComments(initialDocument, initialComments, newDocument, newComments);

		ArrayList<CommentInfo> newComments = extractor.getAddedComments();
		ArrayList<CommentInfo> deletedComments = extractor.getRemovedComments();
		ArrayList<CommentInfo> modifiedComments = extractor.getModifiedComments();
		assertEquals(1, newComments.size());
		assertEquals(1, deletedComments.size());
		assertEquals(0, modifiedComments.size());
		assertEquals("bbbb\n", newComments.get(0).getMessage()); //$NON-NLS-1$
		assertEquals(3, newComments.get(0).getLine());
		assertEquals("aaaa\n", deletedComments.get(0).getMessage()); //$NON-NLS-1$
	}

	private void setupOriginalDocument(String documentText, String... comments) {
		Object[] result = createDocumentWithComments(documentText, comments, (String[]) null);
		initialDocument = (IDocument) result[0];
		initialComments = (AnnotationModel) result[1];
	}

	private void setupNewDocument(String documentText, String[] comments, String[] newCommentsAsStrings) {
		Object[] result = createDocumentWithComments(documentText, comments, newCommentsAsStrings);
		newDocument = (IDocument) result[0];
		newComments = (AnnotationModel) result[1];
	}

	private Object[] createDocumentWithComments(String documentText, String[] comments, String[] newComments) {
		IDocument document = new Document(documentText);
		int lineDelta = 0;
		AnnotationModel commentModel = new AnnotationModel();
		if (comments != null) {
			for (String comment : comments) {
				int offset = documentText.indexOf(comment);
				if (offset == -1) {
					throw new IllegalStateException("Could not find comment in document " + comment); //$NON-NLS-1$
				}
				int line = -1;
				try {
					line = document.getLineOfOffset(offset);
					commentModel.addAnnotation(createGerritComment(comment, line - lineDelta),
							new Position(offset, comment.length()));
					lineDelta += document.getNumberOfLines(offset, comment.length()) - 1;
				} catch (BadLocationException e) {
					//Should not happen
					throw new IllegalStateException("Problem getting comment details " + comment); //$NON-NLS-1$
				}
			}
		}
		if (newComments != null) {
			for (String comment : newComments) {
				int offset = documentText.indexOf(comment);
				if (offset == -1) {
					throw new IllegalStateException("Could not find comment in document " + comment); //$NON-NLS-1$
				}
				int line = -1;
				try {
					line = document.getLineOfOffset(offset);
					commentModel.addAnnotation(new GerritCommentAnnotation(null, ""),
							new Position(offset, comment.length()));
					lineDelta += document.getNumberOfLines(offset, comment.length()) - 1;
				} catch (BadLocationException e) {
					//Should not happen
					throw new IllegalStateException("Problem getting comment details " + comment); //$NON-NLS-1$
				}
			}
		}
		commentModel.connect(document);
		return new Object[] { document, commentModel };
	}

	private GerritCommentAnnotation createGerritComment(String comment, int line) {
		CommentInfo info = new CommentInfo();
		info.setId("ID-" + comment);
		info.setLine(line);
		info.setMessage(comment);
		if (comment.startsWith("author") || comment.startsWith("done")) {
			AccountInfo author = new AccountInfo();
			info.setAuthor(author);
		}
		info.setUpdated("2015-09-23 16:05:16.000000000");
		return new GerritCommentAnnotation(info, "");
	}

}
