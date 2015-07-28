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

import org.eclipse.egerrit.core.rest.CommentInfo;
import org.eclipse.egerrit.ui.editors.model.CommentExtractor;
import org.eclipse.egerrit.ui.editors.model.GerritCommentAnnotation;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.AnnotationModel;
import org.junit.Test;

public class CommentExtractorTest {
	/*
	 * Test the case where the document has not been changed and no comments were in the original document
	 */
	@Test
	public void noChangeNoComment() {
		CommentExtractor extractor = new CommentExtractor();
		ArrayList<CommentInfo> newComments = extractor.extractComments(
				new Document("000000000\n111111111\n222222222\n333333333\n"), null, //$NON-NLS-1$
				new Document("000000000\n111111111\n222222222\n333333333\n")); //$NON-NLS-1$
		assertEquals(0, newComments.size());
	}

	/*
	 * Test the case where the document has not been changed and there were comments in the original document
	 */
	@Test
	public void noChangeWithComments() {
		CommentExtractor extractor = new CommentExtractor();
		ArrayList<CommentInfo> newComments = extractor.extractComments(
				new Document("000000000\naaaa\n111111111\n222222222\n333333333\n"), null, //$NON-NLS-1$
				new Document("000000000\naaaa\n111111111\n222222222\n333333333\n")); //$NON-NLS-1$
		assertEquals(0, newComments.size());
	}

	/*
	 * Test the case where one comment has been added
	 */
	@Test
	public void addOneComment() {
		CommentExtractor extractor = new CommentExtractor();
		ArrayList<CommentInfo> newComments = extractor.extractComments(
				new Document("000000000\n111111111\n222222222\n333333333\n"), null, //$NON-NLS-1$
				new Document("000000000\naaaa\n111111111\n222222222\n333333333\n")); //$NON-NLS-1$
		assertEquals(1, newComments.size());
		assertEquals(1, newComments.get(0).getLine());
		assertEquals("aaaa\n", newComments.get(0).getMessage()); //$NON-NLS-1$
	}

	/*
	 * Test the case where one comment has been added on the last line
	 */
	@Test
	public void addOneCommentOnLastLine() {
		CommentExtractor extractor = new CommentExtractor();
		ArrayList<CommentInfo> newComments = extractor.extractComments(
				new Document("000000000\n111111111\n222222222\n333333333\n"), null, //$NON-NLS-1$
				new Document("000000000\n111111111\n222222222\n333333333\naaaa\n")); //$NON-NLS-1$
		assertEquals(1, newComments.size());
		assertEquals(4, newComments.get(0).getLine());
		assertEquals("aaaa\n", newComments.get(0).getMessage()); //$NON-NLS-1$
	}

	/*
	 * Test the case where one comment is being added and the original document already had a comment
	 */
	@Test
	public void addCommentAfterExistingOne() {
		CommentExtractor extractor = new CommentExtractor();

		IDocument documentWithComments = new Document("000000000\naaaa\n111111111\n222222222\n333333333\n"); //$NON-NLS-1$
		AnnotationModel commentModel = new AnnotationModel();
		commentModel.addAnnotation(createGerritComment("aaaa\n", 1), new Position(10, 5)); //$NON-NLS-1$
		commentModel.connect(documentWithComments);

		ArrayList<CommentInfo> newComments = extractor.extractComments(documentWithComments, commentModel,
				new Document("000000000\naaaa\n111111111\n222222222\nbbbb\n333333333\n")); //$NON-NLS-1$
		assertEquals(1, newComments.size());
		assertEquals(3, newComments.get(0).getLine());
		assertEquals("bbbb\n", newComments.get(0).getMessage()); //$NON-NLS-1$
	}

	/*
	 * Test the case where a comment has been added on multiple lines
	 */
	@Test
	public void addOneMultiLineComment() {
		CommentExtractor extractor = new CommentExtractor();
		ArrayList<CommentInfo> newComments = extractor.extractComments(
				new Document("000000000\n111111111\n222222222\n333333333\n"), null, //$NON-NLS-1$
				new Document("000000000\naaaa\naaaa\n111111111\n222222222\n333333333\n")); //$NON-NLS-1$
		assertEquals(1, newComments.size());
		assertEquals(1, newComments.get(0).getLine());
		assertEquals("aaaa\naaaa\n", newComments.get(0).getMessage()); //$NON-NLS-1$
	}

	/*
	 * Test the case where multiple comments have been added at the same time
	 */
	@Test
	public void addMultipleComments() {
		CommentExtractor extractor = new CommentExtractor();
		ArrayList<CommentInfo> newComments = extractor.extractComments(
				new Document("000000000\n111111111\n222222222\n333333333\n"), null, //$NON-NLS-1$
				new Document("000000000\naaaa\naaaa\n111111111\n222222222\ncccc\n333333333\n")); //$NON-NLS-1$
		assertEquals(2, newComments.size());

		assertEquals(1, newComments.get(0).getLine());
		assertEquals("aaaa\naaaa\n", newComments.get(0).getMessage()); //$NON-NLS-1$

		assertEquals(3, newComments.get(1).getLine());
		assertEquals("cccc\n", newComments.get(1).getMessage()); //$NON-NLS-1$
	}

	/*
	 * Test the case where a comment is being replied to
	 */
	@Test
	public void answerToComment() {
		CommentExtractor extractor = new CommentExtractor();
		IDocument documentWithComments = new Document("000000000\naaaa\n111111111\n222222222\n333333333\n"); //$NON-NLS-1$
		AnnotationModel commentModel = new AnnotationModel();
		commentModel.addAnnotation(createGerritComment("aaaa\n", 1), new Position(10, 5)); //$NON-NLS-1$
		commentModel.connect(documentWithComments);
		ArrayList<CommentInfo> newComments = extractor.extractComments(documentWithComments, commentModel,
				new Document("000000000\naaaa\nanAnswer\n111111111\n222222222\n333333333\n")); //$NON-NLS-1$
		assertEquals(1, newComments.size());

		assertEquals(1, newComments.get(0).getLine());
		assertEquals("anAnswer\n", newComments.get(0).getMessage()); //$NON-NLS-1$
		assertEquals("abc", newComments.get(0).getInReplyTo()); //$NON-NLS-1$
	}

	/*
	 * Test the case where a multi line comment is being replied to
	 */
	@Test
	public void answerToMultilineComment() {
		CommentExtractor extractor = new CommentExtractor();
		IDocument documentWithComments = new Document("000000000\n111111111\n222222222\naaaa\nbbbb\n333333333\n"); //$NON-NLS-1$
		AnnotationModel commentModel = new AnnotationModel();
		commentModel.addAnnotation(createGerritComment("aaaa\nbbbb\n", 3), new Position(30, 10)); //$NON-NLS-1$
		commentModel.connect(documentWithComments);
		ArrayList<CommentInfo> newComments = extractor.extractComments(documentWithComments, commentModel,
				new Document("000000000\n111111111\n222222222\naaaa\nbbbb\ncccc\n333333333\n")); //$NON-NLS-1$
		assertEquals(1, newComments.size());

		assertEquals(3, newComments.get(0).getLine());
		assertEquals("cccc\n", newComments.get(0).getMessage()); //$NON-NLS-1$
		assertEquals("abc", newComments.get(0).getInReplyTo()); //$NON-NLS-1$
	}

	/*
	 * Test the case where comments are closed
	 */
	@Test
	public void closingMultipleComments() {
		CommentExtractor extractor = new CommentExtractor();
		IDocument documentWithComments = new Document("000000000\naaaa\n111111111\n222222222\nbbbb\n333333333\n"); //$NON-NLS-1$
		AnnotationModel commentModel = new AnnotationModel();
		commentModel.addAnnotation(createGerritComment("aaaa\n", 1), new Position(10, 5)); //$NON-NLS-1$
		commentModel.addAnnotation(createGerritComment("bbbb\n", 3), new Position(35, 5)); //$NON-NLS-1$
		commentModel.connect(documentWithComments);

		ArrayList<CommentInfo> newComments = extractor.extractComments(documentWithComments, commentModel,
				new Document("000000000\naaaa\ndone\n111111111\n222222222\nbbbb\ndone\n333333333\n")); //$NON-NLS-1$
		assertEquals(2, newComments.size());

		assertEquals(1, newComments.get(0).getLine());
		assertEquals("done\n", newComments.get(0).getMessage()); //$NON-NLS-1$
		assertEquals("abc", newComments.get(0).getInReplyTo()); //$NON-NLS-1$

		assertEquals(3, newComments.get(1).getLine());
		assertEquals("done\n", newComments.get(1).getMessage()); //$NON-NLS-1$
		assertEquals("abc", newComments.get(1).getInReplyTo()); //$NON-NLS-1$

	}

	/*
	 * Test comment answer and new commment
	 */
	@Test
	public void anwerToCommentThenInsertANewComment() {
		CommentExtractor extractor = new CommentExtractor();
		IDocument documentWithComments = new Document("000000000\naaaa\n111111111\n222222222\n333333333\n");
		AnnotationModel commentModel = new AnnotationModel();
		commentModel.addAnnotation(createGerritComment("aaaa\n", 1), new Position(10, 5));
		commentModel.connect(documentWithComments);

		ArrayList<CommentInfo> newComments = extractor.extractComments(documentWithComments, commentModel,
				new Document("000000000\naaaa\nbbbb\n111111111\n222222222\ncccc\n333333333\n"));
		assertEquals(2, newComments.size());

		assertEquals(1, newComments.get(0).getLine());
		assertEquals("bbbb\n", newComments.get(0).getMessage()); //$NON-NLS-1$

		assertEquals(3, newComments.get(1).getLine());
		assertEquals("cccc\n", newComments.get(1).getMessage()); //$NON-NLS-1$
	}

	/*
	 * Test new comment then a comment is being answered to
	 */
	@Test
	public void insertANewCommentThenAnswerToExistingOne() {
		CommentExtractor extractor = new CommentExtractor();
		IDocument documentWithComments = new Document("000000000\n111111111\n222222222\ncccc\n333333333\n");
		AnnotationModel commentModel = new AnnotationModel();
		commentModel.addAnnotation(createGerritComment("cccc\n", 3), new Position(30, 5));
		commentModel.connect(documentWithComments);

		ArrayList<CommentInfo> newComments = extractor.extractComments(documentWithComments, commentModel,
				new Document("000000000\naaaa\naaaa\n111111111\n222222222\ncccc\ndddd\n333333333\n"));
		assertEquals(2, newComments.size());

		assertEquals(1, newComments.get(0).getLine());
		assertEquals("aaaa\naaaa\n", newComments.get(0).getMessage()); //$NON-NLS-1$

		assertEquals(3, newComments.get(1).getLine());
		assertEquals("dddd\n", newComments.get(1).getMessage()); //$NON-NLS-1$
	}

	/*
	 * Test case where a file comment is added
	 */
	@Test
	public void createFileComment() {
		CommentExtractor extractor = new CommentExtractor();

		ArrayList<CommentInfo> newComments = extractor.extractComments(
				new Document("000000000\n111111111\n222222222\n333333333\n"), null,
				new Document("aaaa\naaaa\n000000000\n111111111\n222222222\n333333333\n"));
		assertEquals(1, newComments.size());

		assertEquals(0, newComments.get(0).getLine());
		assertEquals("aaaa\naaaa\n", newComments.get(0).getMessage()); //$NON-NLS-1$
	}

	/*
	 * Test the case where a second answer is added to an already existing answer
	 */
	@Test
	public void createSecondAnswer() {
		CommentExtractor extractor = new CommentExtractor();
		IDocument documentWithComments = new Document("000000000\naaaa\n111111111\n222222222\n333333333\n"); //$NON-NLS-1$
		AnnotationModel commentModel = new AnnotationModel();
		commentModel.addAnnotation(createGerritComment("aaaa\n", 1), new Position(10, 5)); //$NON-NLS-1$
		commentModel.connect(documentWithComments);
		ArrayList<CommentInfo> newComments = extractor.extractComments(documentWithComments, commentModel,
				new Document("000000000\naaaa\nbbbb\ncccc\n111111111\n222222222\n333333333\n")); //$NON-NLS-1$
		assertEquals(1, newComments.size());

		assertEquals(1, newComments.get(0).getLine());
		assertEquals("bbbb\ncccc\n", newComments.get(0).getMessage()); //$NON-NLS-1$
		assertEquals("abc", newComments.get(0).getInReplyTo()); //$NON-NLS-1$
	}

	/*
	 * Open a new comment after one had been closed
	 */
	@Test
	public void createNewCommentAfterClosedComment() {
		CommentExtractor extractor = new CommentExtractor();
		IDocument documentWithComments = new Document("000000000\naaaa\ndone\n111111111\n222222222\n333333333\n"); //$NON-NLS-1$
		AnnotationModel commentModel = new AnnotationModel();
		commentModel.addAnnotation(createGerritComment("aaaa\n", 1), new Position(10, 5)); //$NON-NLS-1$
		commentModel.addAnnotation(createGerritComment("done\n", 1), new Position(15, 5)); //$NON-NLS-1$
		commentModel.connect(documentWithComments);
		ArrayList<CommentInfo> newComments = extractor.extractComments(documentWithComments, commentModel,
				new Document("000000000\naaaa\ndone\nbbbb\n111111111\n222222222\n333333333\n")); //$NON-NLS-1$
		assertEquals(1, newComments.size());

		assertEquals(1, newComments.get(0).getLine());
		assertEquals("bbbb\n", newComments.get(0).getMessage()); //$NON-NLS-1$
		assertNull(newComments.get(0).getInReplyTo());
	}

	private GerritCommentAnnotation createGerritComment(String comment, int line) {
		CommentInfo info = new CommentInfo();
		info.setId("abc");
		info.setLine(line);
		info.setMessage(comment);
		return new GerritCommentAnnotation(info, "");
	}

	//Future test cases
	//Modification d'un commentaire en draft
//	ext.extractComments(new Document("000000000\n111111111\n222222222\n333333333\n"),
//			new Document("000000000\naaaaaa\n111111111\n222222222\n333333333\n"),
//			new Document("000000000\nbbbbbb\n111111111\n222222222\n333333333\n"));

}
