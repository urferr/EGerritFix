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
import static org.junit.Assert.fail;

import org.eclipse.egerrit.core.rest.AccountInfo;
import org.eclipse.egerrit.core.rest.CommentInfo;
import org.eclipse.egerrit.ui.editors.model.EditionLimiter;
import org.eclipse.egerrit.ui.editors.model.GerritCommentAnnotation;
import org.eclipse.egerrit.ui.editors.model.PatchSetCompareItem;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.BadPositionCategoryException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.text.source.AnnotationModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferenceConstants;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotStyledText;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class EditionLimiterTest {

	private IDocument newDocument;

	private AnnotationModel newComments;

	private TextViewer textViewer;

	private SWTBotStyledText widgetUT;

	private static Display display;

	private Shell shell;

	@BeforeClass
	public static void initDisplay() {
		display = new Display();
	}

	@Before
	public void initializeWidgetAndHookLimiter() {
		System.setProperty(SWTBotPreferenceConstants.KEY_KEYBOARD_LAYOUT,
				"org.eclipse.swtbot.swt.finder.keyboard.EN_US");

		shell = new Shell(display);
		shell.setLayout(new FillLayout());

		textViewer = new TextViewer(shell, SWT.NONE);
		shell.pack();
		shell.open();

		EditionLimiter limeter = new EditionLimiter(textViewer);
		textViewer.getTextWidget().addVerifyListener(limeter);
		textViewer.setInput(newDocument);
		SWTBot bot = new SWTBot(shell);
		widgetUT = bot.styledText();
	}

	@After
	public void destroyWidgets() {
		shell.close();
	}

	@Test
	public void insertCharacterInTheMiddle() {
		setupNewDocument("000000000\n111111111\n222222222\n333333333", (String[]) null); //$NON-NLS-1$
		widgetUT.insertText(0, 5, "a"); //$NON-NLS-1$
		assertEquals(5, widgetUT.getLineCount());
		assertEquals("a", widgetUT.getTextOnLine(1));//$NON-NLS-1$
		assertNumberOfComment(1);
		assertPositionIs(new Position(10, 2));
	}

	@Test
	public void insertCharacterAtTheEndOfTheLine() {
		setupNewDocument("000000000\n111111111\n222222222\n333333333", (String[]) null); //$NON-NLS-1$
		widgetUT.insertText(0, 9, "a");//$NON-NLS-1$
		assertEquals(5, widgetUT.getLineCount());
		assertEquals("a", widgetUT.getTextOnLine(1));//$NON-NLS-1$
		assertNumberOfComment(1);
		assertPositionIs(new Position(10, 2));
	}

	@Test
	public void insertEnterThenCharacterInTheMiddle() {
		setupNewDocument("000000000\n111111111\n222222222\n333333333", (String[]) null); //$NON-NLS-1$
		widgetUT.insertText(0, 5, "\n");//$NON-NLS-1$
		widgetUT.insertText("a");//$NON-NLS-1$
		assertEquals(5, widgetUT.getLineCount());
		assertEquals("a", widgetUT.getTextOnLine(1));//$NON-NLS-1$
		assertNumberOfComment(1);
		assertPositionIs(new Position(10, 2));
	}

	@Test
	public void insertEnterThenCharacterAtTheEndOfTheLine() {
		setupNewDocument("000000000\n111111111\n222222222\n333333333", (String[]) null); //$NON-NLS-1$
		widgetUT.insertText(0, 9, "\n");//$NON-NLS-1$
		widgetUT.insertText("a");//$NON-NLS-1$
		assertEquals(5, widgetUT.getLineCount());
		assertEquals("a", widgetUT.getTextOnLine(1));//$NON-NLS-1$
		assertNumberOfComment(1);
		assertPositionIs(new Position(10, 2));
	}

	@Test
	public void pasteStringWithoutDelimiter() {
		setupNewDocument("000000000\n111111111\n222222222\n333333333", (String[]) null); //$NON-NLS-1$
		widgetUT.insertText(0, 5, "abc");//$NON-NLS-1$
		assertEquals(5, widgetUT.getLineCount());
		assertEquals("abc", widgetUT.getTextOnLine(1));//$NON-NLS-1$
		assertNumberOfComment(1);
		assertPositionIs(new Position(10, 4));
	}

	//enterCharacterOnTheLastLine

	//pasteStringWithoutDelimiter
	//pasteStringWithTwoDelimiters-beginning, end | two at end, etc.
	//paste in the middle begining, end

	//type a, undo
	//type in a character
	//type in a character

	private void assertNumberOfComment(int expected) {
		try {
			assertEquals(expected, newDocument.getPositions(IDocument.DEFAULT_CATEGORY).length);
		} catch (BadPositionCategoryException e) {
			fail();
		}
	}

	private void assertPositionIs(Position expected) {
		Position[] positions;
		try {
			positions = newDocument.getPositions(IDocument.DEFAULT_CATEGORY);
			if (positions.length != 1) {
				fail();
			}
			assertEquals(expected, positions[0]);
		} catch (BadPositionCategoryException e) {
			fail();
		}
	}

	private void setupNewDocument(String documentText, String... comments) {
		Object[] result = createDocumentWithComments(documentText, comments);
		newDocument = (IDocument) result[0];
		newComments = (AnnotationModel) result[1];
		textViewer.setInput(newDocument);
	}

	private Object[] createDocumentWithComments(String documentText, String... comments) {
		PatchSetCompareItem document = new PatchSetCompareItem();
		document.set(documentText);
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
		commentModel.connect(document);
		document.setEditableComments(commentModel);
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
		return new GerritCommentAnnotation(info, "");
	}

}
