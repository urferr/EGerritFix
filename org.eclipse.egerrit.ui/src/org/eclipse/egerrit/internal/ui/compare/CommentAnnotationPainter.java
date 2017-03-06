/*******************************************************************************
 * Copyright (c) 2016 Ericsson AB.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson - initial API and implementation
 *******************************************************************************/
package org.eclipse.egerrit.internal.ui.compare;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.TextPresentation;
import org.eclipse.jface.text.source.AnnotationModel;
import org.eclipse.jface.text.source.AnnotationPainter;
import org.eclipse.jface.text.source.IAnnotationAccess;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

class CommentAnnotationPainter extends AnnotationPainter {
	private Map<Integer, Color> authorToColor = new HashMap<>();

	private final Color COLOR_ORANGE = new Color(Display.getCurrent(), 242, 145, 10);

	private final Color COLOR_PURPLE = new Color(Display.getCurrent(), 242, 10, 242);

	private final Color[] colors = new Color[] { Display.getCurrent().getSystemColor(SWT.COLOR_CYAN), COLOR_ORANGE,
			COLOR_PURPLE, Display.getCurrent().getSystemColor(SWT.COLOR_GREEN) };

	private int nextColorIdx = 0;

	private ISourceViewer viewer;

	public CommentAnnotationPainter(ISourceViewer sourceViewer, IAnnotationAccess access) {
		super(sourceViewer, access);
		viewer = sourceViewer;

		Object strategyID = new Object();
		HighlightingStrategy paintingStrategy = new AnnotationPainter.HighlightingStrategy();
		addTextStyleStrategy(strategyID, paintingStrategy);
		addAnnotationType(GerritCommentAnnotation.TYPE, strategyID);
		addHighlightAnnotationType(strategyID);
	}

	@Override
	//Override to force the annotation model to be the one that contains the comments
	protected IAnnotationModel findAnnotationModel(ISourceViewer sourceViewer) {
		if (sourceViewer.getDocument() instanceof CommentableCompareItem) {
			return ((CommentableCompareItem) sourceViewer.getDocument()).getEditableComments();
		}
		return null;
	}

	@Override
	public void applyTextPresentation(TextPresentation tp) {
		IRegion activeRegion = tp.getExtent();
		AnnotationModel comments = ((CommentableCompareItem) viewer.getDocument()).getEditableComments();
		Iterator<?> it = comments.getAnnotationIterator();
		while (it.hasNext()) {
			GerritCommentAnnotation object = (GerritCommentAnnotation) it.next();
			Position positingExistingComment = comments.getPosition(object);
			//If the position is not part of the region being redrawn, ignore it
			if (!(activeRegion.getOffset() + activeRegion.getLength() >= positingExistingComment.getOffset()
					&& positingExistingComment.getOffset() + positingExistingComment.getLength() > activeRegion
							.getOffset())) {
				continue;
			}

			StyleRange range;
			Color selectedColor;
			Color colorForAuthor;
			if (object.getComment() == null || object.getComment().getAuthor() == null) {
				//Case for drafts.
				selectedColor = Display.getCurrent().getSystemColor(SWT.COLOR_YELLOW);
			} else {
				colorForAuthor = authorToColor.get(object.getComment().getAuthor().get_account_id());
				if (colorForAuthor == null) {
					colorForAuthor = getNextColor();
					authorToColor.put(object.getComment().getAuthor().get_account_id(), colorForAuthor);
				}
				selectedColor = colorForAuthor;
			}

			range = new StyleRange(positingExistingComment.getOffset(), positingExistingComment.getLength(),
					Display.getCurrent().getSystemColor(SWT.COLOR_BLACK), selectedColor);
			setAnnotationTypeColor(GerritCommentAnnotation.TYPE, selectedColor);
			tp.mergeStyleRange(range);
		}
	}

	private Color getNextColor() {
		Color selectedColor = colors[nextColorIdx];
		if (nextColorIdx == 3) {
			nextColorIdx = 0;
		} else {
			nextColorIdx++;
		}
		return selectedColor;
	}

	@Override
	public void dispose() {
		super.dispose();
		COLOR_ORANGE.dispose();
		COLOR_PURPLE.dispose();
	}
}