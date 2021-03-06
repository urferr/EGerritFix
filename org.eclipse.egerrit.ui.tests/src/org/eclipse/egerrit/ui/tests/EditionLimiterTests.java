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
import static org.junit.Assert.fail;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.egerrit.internal.model.AccountInfo;
import org.eclipse.egerrit.internal.model.CommentInfo;
import org.eclipse.egerrit.internal.model.ModelFactory;
import org.eclipse.egerrit.internal.ui.compare.CommentAnnotationManager;
import org.eclipse.egerrit.internal.ui.compare.EditionLimiter;
import org.eclipse.egerrit.internal.ui.compare.GerritCommentAnnotation;
import org.eclipse.egerrit.internal.ui.compare.PatchSetCompareItem;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.BadPositionCategoryException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.text.TextViewerUndoManager;
import org.eclipse.jface.text.source.AnnotationModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.keyboard.Keyboard;
import org.eclipse.swtbot.swt.finder.keyboard.KeyboardLayout;
import org.eclipse.swtbot.swt.finder.keyboard.Keystrokes;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferenceConstants;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotStyledText;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runners.Parameterized.Parameter;

//@RunWith(Suite.class)
//@SuiteClasses({ InsertionTest.class, UndoTest.class })
public class EditionLimiterTests {

	IDocument document;

	private TextViewer textViewer;

	public SWTBotStyledText widgetUT;

	private static Display display;

	private Shell shell;

	@Parameter(value = 0)
	public InputData inputData;

	@Parameter(value = 1)
	public Expectations expectations;

	private SWTBot bot;

	@BeforeClass
	public static void initDisplay() {
		display = Display.findDisplay(Thread.currentThread());
		if (display == null) {
			display = new Display();
		}
	}

	public void assertExpectations() {
		System.out.println(widgetUT.getText());
		if (expectations.noModifications) {
			assertEquals(inputData.document, widgetUT.getText());
			return;
		}
		if (expectations.numberOfComments != -1) {
			assertNumberOfComment(expectations.numberOfComments);
		}
		if (expectations.positions != null) {
			for (Position position : expectations.positions) {
				hasPosition(position);
			}
		}
		if (expectations.line != -1) {
			assertEquals(expectations.contentAtLine, widgetUT.getTextOnLine(expectations.line));
		}
	}

	private void hasPosition(Position expected) {
		Position[] positions;
		try {
			positions = document.getPositions(IDocument.DEFAULT_CATEGORY);
			for (Position position : positions) {
				if (expected.equals(position) && !position.isDeleted) {
					return;
				}
			}
			StringBuffer posList = new StringBuffer();
			for (Position position : positions) {
				posList.append(position).append(' ');
			}
			fail("Could not find position: " + expected + " Positions available are " + posList.toString()); //$NON-NLS-1$ //$NON-NLS-2$
		} catch (BadPositionCategoryException e) {
			fail();
		}
	}

	private void assertNumberOfComment(int expected) {
		try {
			int activeCommentCount = 0;
			Position[] positions = document.getPositions(IDocument.DEFAULT_CATEGORY);
			for (Position p : positions) {
				if (!p.isDeleted) {
					activeCommentCount++;
				}
			}
			assertEquals(expected, activeCommentCount);
		} catch (BadPositionCategoryException e) {
			fail();
		}
	}

	@Before
	public void initializeWidgetAndHookLimiter() {
		initializeWidgets();
		initializeDocument();
	}

	private void initializeWidgets() {
		System.setProperty(SWTBotPreferenceConstants.KEY_KEYBOARD_LAYOUT,
				"org.eclipse.swtbot.swt.finder.keyboard.EN_US"); //$NON-NLS-1$

		shell = new Shell(display);
		shell.setLayout(new FillLayout());

		textViewer = new TextViewer(shell, SWT.NONE);
		shell.pack();
		shell.open();

		EditionLimiter limeter = new EditionLimiter(textViewer);
		textViewer.getTextWidget().addVerifyListener(limeter);
		textViewer.setUndoManager(new TextViewerUndoManager(10));
		textViewer.activatePlugins();
		textViewer.setInput(document);
		bot = new SWTBot(shell);
		widgetUT = bot.styledText();
	}

	private void initializeDocument() {
		Object[] result = createDocumentWithComments(inputData.document, inputData.comments);
		document = (IDocument) result[0];
		textViewer.setInput(document);
	}

	public EditionLimiterTests() {
		super();
	}

	@After
	public void destroyWidgets() {
		shell.close();
	}

	private Object[] createDocumentWithComments(String documentText, String... comments) {
		PatchSetCompareItem document = new PatchSetCompareItem();
		document.set(documentText);
		int lineDelta = 0;
		AnnotationModel commentModel = new CommentAnnotationManager();
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
		CommentInfo info = ModelFactory.eINSTANCE.createCommentInfo();
		info.setId("ID-" + comment); //$NON-NLS-1$
		info.setLine(line);
		info.setMessage(comment);
		if (comment.startsWith("author") || comment.startsWith("done")) { //$NON-NLS-1$ //$NON-NLS-2$
			AccountInfo author = ModelFactory.eINSTANCE.createAccountInfo();
			info.setAuthor(author);
		}
		return new GerritCommentAnnotation(info, ""); //$NON-NLS-1$
	}

	public void typeKeystroke(org.eclipse.jface.bindings.keys.KeyStroke... strokes) {
		try {
			widgetUT.setFocus();
			Method keyboardMethod = org.eclipse.swtbot.swt.finder.widgets.AbstractSWTBot.class
					.getDeclaredMethod("keyboard", new Class[0]); //$NON-NLS-1$
			keyboardMethod.setAccessible(true);
			Keyboard keyboard = (Keyboard) keyboardMethod.invoke(widgetUT, null);
			KeyboardLayout layout = KeyboardLayout.getDefaultKeyboardLayout();
			keyboard.typeCharacter(layout.toCharacter(strokes[0]));
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	void processKeys() {
		try {
			widgetUT.widget.setCaretOffset(document.getLineOffset(inputData.line) + inputData.offset);
		} catch (BadLocationException e) {
			fail();
		}
		for (String key : inputData.keys) {
			if (key.equals("<DEL>")) { //$NON-NLS-1$
				typeKeystroke(Keystrokes.DELETE);
				continue;
			}
			if (key.equals("<UNDO>")) { //$NON-NLS-1$
				textViewer.getUndoManager().undo();
				continue;
			}
			widgetUT.insertText(key);
		}
	}
}