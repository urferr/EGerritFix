/*******************************************************************************
 * Copyright (c) 2015-2016 Ericsson AB.
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

import org.eclipse.egerrit.internal.model.AccountInfo;
import org.eclipse.egerrit.internal.model.CommentInfo;
import org.eclipse.egerrit.internal.model.ModelFactory;
import org.eclipse.egerrit.internal.ui.compare.CommentExtractor;
import org.eclipse.egerrit.internal.ui.compare.GerritCommentAnnotation;
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
				new String[] { "aaaa" } //$NON-NLS-1$
		);
		CommentExtractor extractor = new CommentExtractor();
		extractor.extractComments(initialDocument, initialComments, newDocument, newComments);

		ArrayList<CommentInfo> newComments = extractor.getAddedComments();
		assertEquals(1, newComments.size());
		assertEquals(1, newComments.get(0).getLine());
		assertEquals("aaaa", newComments.get(0).getMessage()); //$NON-NLS-1$
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
				(String[]) null, new String[] { "aaaa" }); //$NON-NLS-1$
		CommentExtractor extractor = new CommentExtractor();
		extractor.extractComments(initialDocument, initialComments, newDocument, newComments);

		ArrayList<CommentInfo> newComments = extractor.getAddedComments();
		assertEquals(1, newComments.size());
		assertEquals(4, newComments.get(0).getLine());
		assertEquals("aaaa", newComments.get(0).getMessage()); //$NON-NLS-1$
		assertEquals(0, extractor.getModifiedComments().size());
		assertEquals(0, extractor.getRemovedComments().size());
	}

	/*
	 * Test the case where one comment is being added and the original document already had a comment
	 */
	@Test
	public void addCommentAfterExistingOne() {
		setupOriginalDocument("000000000\naaaa\n111111111\n222222222\n333333333\n", "aaaa"); //$NON-NLS-1$ //$NON-NLS-2$
		setupNewDocument("000000000\naaaa\n111111111\n222222222\nbbbb\n333333333\n", new String[] { "aaaa" }, //$NON-NLS-1$//$NON-NLS-2$
				new String[] { "bbbb" }); //$NON-NLS-1$
		CommentExtractor extractor = new CommentExtractor();
		extractor.extractComments(initialDocument, initialComments, newDocument, newComments);

		ArrayList<CommentInfo> newComments = extractor.getAddedComments();
		assertEquals(1, newComments.size());
		assertEquals(3, newComments.get(0).getLine());
		assertEquals("bbbb", newComments.get(0).getMessage()); //$NON-NLS-1$
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
				(String[]) null, new String[] { "aaaa\naaaa" }); //$NON-NLS-1$
		CommentExtractor extractor = new CommentExtractor();
		extractor.extractComments(initialDocument, initialComments, newDocument, newComments);

		ArrayList<CommentInfo> newComments = extractor.getAddedComments();
		assertEquals(1, newComments.size());
		assertEquals(1, newComments.get(0).getLine());
		assertEquals("aaaa\naaaa", newComments.get(0).getMessage()); //$NON-NLS-1$
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
				new String[] { "aaaa\naaaa", "cccc" }); //$NON-NLS-1$ //$NON-NLS-2$
		CommentExtractor extractor = new CommentExtractor();
		extractor.extractComments(initialDocument, initialComments, newDocument, newComments);

		ArrayList<CommentInfo> newComments = extractor.getAddedComments();
		assertEquals(2, newComments.size());

		assertEquals(1, newComments.get(0).getLine());
		assertEquals("aaaa\naaaa", newComments.get(0).getMessage()); //$NON-NLS-1$

		assertEquals(3, newComments.get(1).getLine());
		assertEquals("cccc", newComments.get(1).getMessage()); //$NON-NLS-1$
		assertEquals(0, extractor.getModifiedComments().size());
		assertEquals(0, extractor.getRemovedComments().size());
	}

	/*
	 * Test the case where a comment is being replied to
	 */
	@Test
	public void answerToComment() {
		setupOriginalDocument("000000000\nauthorAaa\n111111111\n222222222\n333333333\n", "authorAaa"); //$NON-NLS-1$ //$NON-NLS-2$
		setupNewDocument("000000000\nauthorAaa\nanAnswer\n111111111\n222222222\n333333333\n", //$NON-NLS-1$
				new String[] { "authorAaa" }, //$NON-NLS-1$
				new String[] { "anAnswer" }); //$NON-NLS-1$
		CommentExtractor extractor = new CommentExtractor();
		extractor.extractComments(initialDocument, initialComments, newDocument, newComments);

		ArrayList<CommentInfo> newComments = extractor.getAddedComments();
		assertEquals(1, newComments.size());

		assertEquals(1, newComments.get(0).getLine());
		assertEquals("anAnswer", newComments.get(0).getMessage()); //$NON-NLS-1$
		assertEquals("ID-authorAaa", newComments.get(0).getInReplyTo()); //$NON-NLS-1$
		assertEquals(0, extractor.getModifiedComments().size());
		assertEquals(0, extractor.getRemovedComments().size());
	}

	/*
	 * Test the case where a comment is being replied to
	 */
	@Test
	public void notAnAnswer() {
		setupOriginalDocument("000000000\naaaa\n111111111\n222222222\n333333333\n", "aaaa"); //$NON-NLS-1$ //$NON-NLS-2$
		setupNewDocument("000000000\naaaa\nnotAnswer\n111111111\n222222222\n333333333\n", new String[] { "aaaa" }, //$NON-NLS-1$//$NON-NLS-2$
				new String[] { "notAnswer" }); //$NON-NLS-1$
		CommentExtractor extractor = new CommentExtractor();
		extractor.extractComments(initialDocument, initialComments, newDocument, newComments);

		ArrayList<CommentInfo> newComments = extractor.getAddedComments();
		assertEquals(1, newComments.size());

		assertEquals(1, newComments.get(0).getLine());
		assertEquals("notAnswer", newComments.get(0).getMessage()); //$NON-NLS-1$
		assertNull(newComments.get(0).getInReplyTo());
		assertEquals(0, extractor.getModifiedComments().size());
		assertEquals(0, extractor.getRemovedComments().size());
	}

	/*
	 * Test the case where a multi line comment is being replied to
	 */
	@Test
	public void answerToMultilineComment() {
		setupOriginalDocument("000000000\n111111111\n222222222\nauthoraaaa\nbbbb\n333333333\n", "authoraaaa\nbbbb"); //$NON-NLS-1$ //$NON-NLS-2$
		setupNewDocument("000000000\n111111111\n222222222\nauthoraaaa\nbbbb\ncccc\n333333333\n", //$NON-NLS-1$
				new String[] { "authoraaaa\nbbbb" }, //$NON-NLS-1$
				new String[] { "cccc" }); //$NON-NLS-1$

		CommentExtractor extractor = new CommentExtractor();
		extractor.extractComments(initialDocument, initialComments, newDocument, newComments);

		ArrayList<CommentInfo> newComments = extractor.getAddedComments();
		assertEquals(1, newComments.size());
		assertEquals(3, newComments.get(0).getLine());
		assertEquals("cccc", newComments.get(0).getMessage()); //$NON-NLS-1$
		assertEquals("ID-authoraaaa\nbbbb", newComments.get(0).getInReplyTo()); //$NON-NLS-1$
		assertEquals(0, extractor.getModifiedComments().size());
		assertEquals(0, extractor.getRemovedComments().size());
	}

	/*
	 * Test the case where comments are closed
	 */
	@Test
	public void closingComment() {
		setupOriginalDocument("000000000\nauthoraaaa\n111111111\n222222222\nauthorbbbb\n333333333\n", "authoraaaa"); //$NON-NLS-1$//$NON-NLS-2$
		setupNewDocument("000000000\nauthoraaaa\ndone\n111111111\n222222222\nauthorbbbb\n333333333\n", //$NON-NLS-1$
				new String[] { "authoraaaa", }, //$NON-NLS-1$
				new String[] { "done" }); //$NON-NLS-1$

		CommentExtractor extractor = new CommentExtractor();
		extractor.extractComments(initialDocument, initialComments, newDocument, newComments);

		ArrayList<CommentInfo> newComments = extractor.getAddedComments();
		assertEquals(1, newComments.size());

		assertEquals(1, newComments.get(0).getLine());
		assertEquals("done", newComments.get(0).getMessage()); //$NON-NLS-1$
		assertEquals("ID-authoraaaa", newComments.get(0).getInReplyTo()); //$NON-NLS-1$

		assertEquals(0, extractor.getModifiedComments().size());
		assertEquals(0, extractor.getRemovedComments().size());
	}

	/*
	 * Test comment answer and new commment
	 */
	@Test
	public void answerToCommentThenInsertANewComment() {
		setupOriginalDocument("000000000\nauthor\n111111111\n222222222\n333333333\n", "author"); //$NON-NLS-1$//$NON-NLS-2$
		setupNewDocument("000000000\nauthor\nbbbb\n111111111\n222222222\ncccc\n333333333\n", //$NON-NLS-1$
				new String[] { "author" }, //$NON-NLS-1$
				new String[] { "bbbb", "cccc" }); //$NON-NLS-1$//$NON-NLS-2$

		CommentExtractor extractor = new CommentExtractor();
		extractor.extractComments(initialDocument, initialComments, newDocument, newComments);

		ArrayList<CommentInfo> newComments = extractor.getAddedComments();
		assertEquals(2, newComments.size());

		assertEquals(1, newComments.get(0).getLine());
		assertEquals("bbbb", newComments.get(0).getMessage()); //$NON-NLS-1$

		assertEquals(3, newComments.get(1).getLine());
		assertEquals("cccc", newComments.get(1).getMessage()); //$NON-NLS-1$
		assertEquals(0, extractor.getModifiedComments().size());
		assertEquals(0, extractor.getRemovedComments().size());
	}

	/*
	 * Test new comment then a comment is being answered to
	 */
	@Test
	public void insertANewCommentThenAnswerToExistingOne() {
		setupOriginalDocument("000000000\n111111111\n222222222\ncccc\n333333333\n", "cccc"); //$NON-NLS-1$//$NON-NLS-2$
		setupNewDocument("000000000\naaaa\naaaa\n111111111\n222222222\ncccc\ndddd\n333333333\n", //$NON-NLS-1$
				new String[] { "cccc" }, new String[] { "aaaa\naaaa", "dddd" }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		CommentExtractor extractor = new CommentExtractor();
		extractor.extractComments(initialDocument, initialComments, newDocument, newComments);

		ArrayList<CommentInfo> newComments = extractor.getAddedComments();
		assertEquals(2, newComments.size());

		assertEquals(1, newComments.get(0).getLine());
		assertEquals("aaaa\naaaa", newComments.get(0).getMessage()); //$NON-NLS-1$

		assertEquals(3, newComments.get(1).getLine());
		assertEquals("dddd", newComments.get(1).getMessage()); //$NON-NLS-1$
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
				new String[] { "aaaa\naaaa" }); //$NON-NLS-1$

		CommentExtractor extractor = new CommentExtractor();
		extractor.extractComments(initialDocument, initialComments, newDocument, newComments);

		ArrayList<CommentInfo> newComments = extractor.getAddedComments();
		assertEquals(1, newComments.size());

		assertEquals(0, newComments.get(0).getLine());
		assertEquals("aaaa\naaaa", newComments.get(0).getMessage()); //$NON-NLS-1$
		assertEquals(0, extractor.getModifiedComments().size());
		assertEquals(0, extractor.getRemovedComments().size());
	}

	/*
	 * Open a new comment after one had been closed
	 */
	@Test
	public void createNewCommentAfterClosedComment() {
		setupOriginalDocument("000000000\nauthor\ndone\n111111111\n222222222\n333333333\n", "author", "done"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		setupNewDocument("000000000\nauthor\ndone\nbbbb\n111111111\n222222222\n333333333\n", //$NON-NLS-1$
				new String[] { "author", "done" }, new String[] { "bbbb" }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		//We need to tweak the generated defaults to have done be a reply to "author"
		Iterator<?> it = newComments.getAnnotationIterator();
		while (it.hasNext()) {
			GerritCommentAnnotation comment = (GerritCommentAnnotation) it.next();
			if (comment.getComment() != null && comment.getComment().getMessage().startsWith("done")) { //$NON-NLS-1$
				comment.getComment().setInReplyTo("ID-author"); //$NON-NLS-1$
				comment.getComment().setLine(1);
			}
		}

		Iterator<?> it2 = initialComments.getAnnotationIterator();
		while (it2.hasNext()) {
			GerritCommentAnnotation comment = (GerritCommentAnnotation) it2.next();
			if (comment.getComment() != null && comment.getComment().getMessage().startsWith("done")) { //$NON-NLS-1$
				comment.getComment().setInReplyTo("ID-author"); //$NON-NLS-1$
				comment.getComment().setLine(1);
			}
		}

		CommentExtractor extractor = new CommentExtractor();
		extractor.extractComments(initialDocument, initialComments, newDocument, newComments);

		ArrayList<CommentInfo> newComments = extractor.getAddedComments();
		assertEquals(1, newComments.size());

		assertEquals(1, newComments.get(0).getLine());
		assertEquals("bbbb", newComments.get(0).getMessage()); //$NON-NLS-1$
		assertNull(newComments.get(0).getInReplyTo());
		assertEquals(0, extractor.getModifiedComments().size());
		assertEquals(0, extractor.getRemovedComments().size());
	}

	/*
	 * Test the case where an existing comment is being deleted, and just that.
	 */
	@Test
	public void deleteExistingDraft() {
		setupOriginalDocument("000000000\naaaa\n111111111\n222222222\n333333333\n", "aaaa"); //$NON-NLS-1$ //$NON-NLS-2$
		setupNewDocument("000000000\n111111111\n222222222\n333333333\n", (String[]) null, (String[]) null); //$NON-NLS-1$

		CommentExtractor extractor = new CommentExtractor();
		extractor.extractComments(initialDocument, initialComments, newDocument, newComments);

		ArrayList<CommentInfo> newComments = extractor.getAddedComments();
		ArrayList<CommentInfo> deletedComments = extractor.getRemovedComments();
		ArrayList<CommentInfo> modifiedComments = extractor.getModifiedComments();
		assertEquals(0, newComments.size());
		assertEquals(1, deletedComments.size());
		assertEquals(0, modifiedComments.size());
		assertEquals("aaaa", deletedComments.get(0).getMessage()); //$NON-NLS-1$
	}

	@Test
	public void deleteExistingMultiLineDraft() {
		setupOriginalDocument("000000000\naaaa\nbbbb\n111111111\n222222222\n333333333\n", "aaaa\nbbbb"); //$NON-NLS-1$ //$NON-NLS-2$
		setupNewDocument("000000000\n111111111\n222222222\n333333333\n", (String[]) null, (String[]) null); //$NON-NLS-1$

		CommentExtractor extractor = new CommentExtractor();
		extractor.extractComments(initialDocument, initialComments, newDocument, newComments);

		ArrayList<CommentInfo> newComments = extractor.getAddedComments();
		ArrayList<CommentInfo> deletedComments = extractor.getRemovedComments();
		ArrayList<CommentInfo> modifiedComments = extractor.getModifiedComments();
		assertEquals(0, newComments.size());
		assertEquals(1, deletedComments.size());
		assertEquals(0, modifiedComments.size());
		assertEquals("aaaa\nbbbb", deletedComments.get(0).getMessage()); //$NON-NLS-1$
	}

	@Test
	public void deleteFollowedByAdditionLowerDown() {
		setupOriginalDocument("000000000\naaaa\n111111111\n222222222\n333333333\n", "aaaa"); //$NON-NLS-1$ //$NON-NLS-2$
		setupNewDocument("000000000\n111111111\n222222222\nbbbb\n333333333\n", (String[]) null, //$NON-NLS-1$
				new String[] { "bbbb" }); //$NON-NLS-1$

		CommentExtractor extractor = new CommentExtractor();
		extractor.extractComments(initialDocument, initialComments, newDocument, newComments);

		ArrayList<CommentInfo> newComments = extractor.getAddedComments();
		ArrayList<CommentInfo> deletedComments = extractor.getRemovedComments();
		ArrayList<CommentInfo> modifiedComments = extractor.getModifiedComments();
		assertEquals(1, newComments.size());
		assertEquals(1, deletedComments.size());
		assertEquals(0, modifiedComments.size());
		assertEquals("bbbb", newComments.get(0).getMessage()); //$NON-NLS-1$
		assertEquals(3, newComments.get(0).getLine());
		assertEquals("aaaa", deletedComments.get(0).getMessage()); //$NON-NLS-1$
	}

	@Test
	public void insertOnFirstLineWithCommentsBelow() {
		setupOriginalDocument("000000000\naaaa\nbbbb\n111111111\n222222222\n333333333\n", "aaaa", "bbbb"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		setupNewDocument("000000000\ncccc\naaaa\nbbbb\n111111111\n222222222\n333333333\n", //$NON-NLS-1$
				new String[] { "aaaa", "bbbb" }, new String[] { "cccc" }); //$NON-NLS-1$ //$NON-NLS-2$//$NON-NLS-3$

		CommentExtractor extractor = new CommentExtractor();
		extractor.extractComments(initialDocument, initialComments, newDocument, newComments);

		ArrayList<CommentInfo> newComments = extractor.getAddedComments();
		ArrayList<CommentInfo> deletedComments = extractor.getRemovedComments();
		ArrayList<CommentInfo> modifiedComments = extractor.getModifiedComments();
		assertEquals(1, newComments.size());
		assertEquals(0, deletedComments.size());
		assertEquals(0, modifiedComments.size());
		assertEquals("cccc", newComments.get(0).getMessage()); //$NON-NLS-1$
		assertEquals(1, newComments.get(0).getLine());
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
				try {
					int line = document.getLineOfOffset(offset);
					commentModel.addAnnotation(createGerritComment(comment, line - lineDelta, offset, comment.length()),
							new Position(offset, comment.length()));
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
				commentModel.addAnnotation(new GerritCommentAnnotation(null, ""), //$NON-NLS-1$
						new Position(offset, comment.length()));
			}
		}
		commentModel.connect(document);
		return new Object[] { document, commentModel };
	}

	private GerritCommentAnnotation createGerritComment(String comment, int line, int offset, int length) {
		CommentInfo info = ModelFactory.eINSTANCE.createCommentInfo();
		info.setId("ID-" + comment); //$NON-NLS-1$
		info.setLine(line);
		info.setMessage(comment);
		if (comment.startsWith("author") || comment.startsWith("done")) { //$NON-NLS-1$ //$NON-NLS-2$
			AccountInfo author = ModelFactory.eINSTANCE.createAccountInfo();
			info.setAuthor(author);
		}
		info.setUpdated("2015-09-23 16:05:16.000000000"); //$NON-NLS-1$
		return new GerritCommentAnnotation(info, ""); //$NON-NLS-1$
	}

}
