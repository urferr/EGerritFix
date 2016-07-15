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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.BadPositionCategoryException;
import org.eclipse.jface.text.DefaultPositionUpdater;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.jface.text.IPositionUpdater;
import org.eclipse.jface.text.ITextInputListener;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.text.link.InclusivePositionUpdater;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.AnnotationModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is used to prevent the user to modify the source code or the comments that have already been published,
 * and it also responsible to add/remove annotations representing the changes. In order to control what the user is
 * typing in, we are monitoring the text widget through the VerifyEvent. In our case we listen to this event before it
 * is actually taken into account which gives an opportunity to veto the changes. Also, because the undo/redo support is
 * coming from jface text and not from the text widget itself, we have to listen to the document changed events.
 */
public class EditionLimiter implements VerifyListener, IDocumentListener {
	private static Logger logger = LoggerFactory.getLogger(EditionLimiter.class);

	private IDocument document;

	private AnnotationModel annotations;

	private TextViewer textViewer;

	private StyledText textWidget;

	//Flag to short-circuit the case where the input is triggered from the document model
	private String lastTextForShortCircuiting = null;

	//flag used to short-circuit the case where the event is coming from the widget
	private boolean triggeredFromWidget = false;

	public EditionLimiter(TextViewer viewer) {
		this.textViewer = viewer;
		textViewer.getTextWidget().addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (((e.stateMask & SWT.CTRL) == SWT.CTRL) && (e.keyCode == 'd')
						|| ((e.stateMask & SWT.COMMAND) == SWT.COMMAND) && (e.keyCode == 'd')) {
					triggeredFromWidget = true;
					StyledText txt = (StyledText) e.getSource();
					int lineLength = 0;
					int lineNo = 0;
					int caretOffset = txt.getCaretOffset();
					if (document == null) {
						initialize();
					}
					try {
						lineNo = document.getLineOfOffset(caretOffset);
						lineLength = document.getLineLength(lineNo);
						if (!isEditableLine(document.getLineOffset(lineNo), 1)) {
							return;
						}
						doit(caretOffset, lineLength - 1, "", false, true); //$NON-NLS-1$
						document.replace(document.getLineOffset(lineNo), lineLength - 1, ""); //$NON-NLS-1$

					} catch (BadLocationException e1) {
						logger.debug("Exception while performing Ctrl-D", e1); //$NON-NLS-1$
						return;
					} finally {
						lastTextForShortCircuiting = null;
						triggeredFromWidget = false;
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// ignore
			}
		});

	}

	private boolean doit(int start, int length, String text, boolean fromDoc, boolean deletionOnly) {
		if (document == null) {
			initialize();
		}
		printAnnotationsCount();
		try {
			//Text is deleted, check where this is happening
			if ("".equals(text)) { //$NON-NLS-1$
				if (!isEditableLine(start, length)) {
					return false;
				}
				Iterator<?> it = annotations.getAnnotationIterator(start, length, true, true);
				Position impactedArea = new Position(start, length);
				while (it.hasNext()) {
					GerritCommentAnnotation comment = (GerritCommentAnnotation) it.next();
					Position commentPosition = annotations.getPosition(comment);

					try {
						if (commentPosition.length == 0
								&& (document.get(start, length).equals(textWidget.getLineDelimiter()))) {
							annotations.removeAnnotation(comment);
							return true;
						}
					} catch (BadLocationException e) {
						//Can't happen
					}
					//Bail if the impacted area is not completely included in the comment
					if (!completelyIncludes(commentPosition, impactedArea)) {
						return false; //we don't want the proposed modification to be performed
					}
				}

				return true;
			}

			if (deletionOnly) {
				return true;
			}
			//The user is typing text in a non-authorized area
			if (!isEditableLine(start, text.length())) {
				int insertionPosition = start;
				String commentText = text.trim();
				if (!fromDoc) {
					//Move insertion point to the next line if we are not inserting at the beginning of the line
					//or move at the end of the comment if we are in a middle of a comment
					if (!isBeginningOfLine(insertionPosition)
							|| (isBeginningOfLine(insertionPosition) && getCurrentAnnotation(start) != null)) {
						GerritCommentAnnotation currentAnnotation = getCurrentAnnotation(start);
						if (currentAnnotation != null) {
							Position position = annotations.getPosition(currentAnnotation);
							insertionPosition = getNextLine(position.getOffset() + position.getLength());
						} else {
							insertionPosition = getNextLine(start);
						}

						if (insertionPosition == -1) {
							//If there is no next line, we first insert one, then compute it's position and proceed as usual
							try {
								document.replace(textWidget.getCharCount(), 0, textWidget.getLineDelimiter());
							} catch (BadLocationException e) {
								// not possible
							}
							insertionPosition = getNextLine(start);
						}
					}
					String insertedText = commentText + textWidget.getLineDelimiter();
					try {
						document.replace(insertionPosition, 0, insertedText);
					} catch (BadLocationException e) {
						logger.debug("Exception inserting " + commentText, e); //$NON-NLS-1$
					}
					textWidget.setCaretOffset(
							insertionPosition + insertedText.length() - textWidget.getLineDelimiter().length());
				}
				annotations.addAnnotation(new GerritCommentAnnotation(null, commentText, 0, 10),
						new Position(insertionPosition, commentText.length()));

				return false;
			}

			//When we reach this point, it means that the modification attempted by the user are authorized
			return true;
		} finally {
			printAnnotationsCount();
		}
	}

	//Returns the offset of the line number after the line of the given offset
	private int getNextLine(int offset) {
		try {
			return textWidget.getOffsetAtLine(textWidget.getLineAtOffset(offset) + 1);
		} catch (java.lang.IllegalArgumentException e) {
			return -1;
		}
	}

	//Check if the offset represents the beginning of a line
	private boolean isBeginningOfLine(int offset) {
		return textWidget.getOffsetAtLine(textWidget.getLineAtOffset(offset)) == offset;
	}

	private boolean completelyIncludes(Position container, Position contained) {
		return container.includes(contained.offset) && container.includes(contained.offset + contained.length - 1);
	}

	//A line is editable if it is a draft comment
	private boolean isEditableLine(int offset, int length) {
		Iterator<?> it = annotations.getAnnotationIterator(offset, length, true, true);
		if (it.hasNext()) {
			GerritCommentAnnotation annotation = (GerritCommentAnnotation) it.next();
			if (annotation.getComment() == null || annotation.getComment().getAuthor() == null) {
				return true;
			}
		}
		return isInsertingAtTheEndOfExistingComment(offset, length);
	}

	private GerritCommentAnnotation getCurrentAnnotation(int offset) {
		Iterator<Annotation> it = annotations.getAnnotationIterator();

		while (it.hasNext()) {
			GerritCommentAnnotation annotation = (GerritCommentAnnotation) it.next();
			Position position = annotations.getPosition(annotation);
			if (offset >= position.getOffset() && offset <= (position.getOffset() + position.getLength())) {
				return annotation;
			}
		}
		return null;
	}

	private boolean isInsertingAtTheEndOfExistingComment(int offset, int length) {
		Iterator<?> it = annotations.getAnnotationIterator();

		while (it.hasNext()) {
			GerritCommentAnnotation annotation = (GerritCommentAnnotation) it.next();
			if (annotation.getComment() == null || annotation.getComment().getAuthor() == null) {
				Position position = annotations.getPosition(annotation);
				if (position.getOffset() + position.getLength() == offset) {
					return true;
				}
			}
		}
		return false;
	}

	private void printAnnotationsCount() {
		try {
			logger.debug("Annotation count " + document.getPositions(IDocument.DEFAULT_CATEGORY).length); //$NON-NLS-1$
		} catch (BadPositionCategoryException e) {
			//ignore
		}
	}

	@Override
	public void verifyText(VerifyEvent e) {
		textWidget = (StyledText) e.widget;
		triggeredFromWidget = true;
		e.doit = doit(e.start, e.end - e.start, e.text, false, false);
		lastTextForShortCircuiting = e.text;
		triggeredFromWidget = false;
	}

	@Override
	public void documentAboutToBeChanged(DocumentEvent event) {
		if (triggeredFromWidget) {
			return;
		}
		if (event.getText().equals(lastTextForShortCircuiting)) {
			return;
		}
		lastTextForShortCircuiting = event.getText();
		doit(event.getOffset(), event.getLength(), event.fText, true, true);
	}

	@Override
	public void documentChanged(DocumentEvent event) {
		if (triggeredFromWidget) {
			return;
		}
		if (event.getText().equals(lastTextForShortCircuiting)) {
			lastTextForShortCircuiting = null;
			return;
		}
		doit(event.getOffset(), event.getLength(), event.fText, true, false);
		lastTextForShortCircuiting = null;
	}

	//Use an InclusivePositionUpdater instead of the default one.
	//This way, when the user types in on the first character of the comment, the comment is grown.
	private void changePositionUpdater() {
		IPositionUpdater[] updaters = document.getPositionUpdaters();
		for (IPositionUpdater updater : updaters) {
			if (updater instanceof DefaultPositionUpdater) {
				try {
					Method getCategory = DefaultPositionUpdater.class.getDeclaredMethod("getCategory", new Class[0]); //$NON-NLS-1$
					getCategory.setAccessible(true);
					if (IDocument.DEFAULT_CATEGORY.equals(getCategory.invoke(updater, new Object[0]))) {
						document.removePositionUpdater(updater);
						break;
					}
				} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		document.addPositionUpdater(new InclusivePositionUpdater(IDocument.DEFAULT_CATEGORY));
	}

	private void initialize() {
		document = textViewer.getDocument();
		document.addDocumentListener(this);
		annotations = ((CommentableCompareItem) textViewer.getDocument()).getEditableComments();
		changePositionUpdater();
		textViewer.addTextInputListener(new ITextInputListener() {

			@Override
			public void inputDocumentChanged(IDocument oldInput, IDocument newInput) {
				//The document is changing (e.g in response to the user having hit saved), we need to reset the listener
				if (oldInput != null) {
					oldInput.removeDocumentListener(EditionLimiter.this);
				}
				document = null;
			}

			@Override
			public void inputDocumentAboutToBeChanged(IDocument oldInput, IDocument newInput) {
				// nothing to do
			}
		});
	}
}
