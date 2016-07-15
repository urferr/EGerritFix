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

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.compare.CompareEditorInput;
import org.eclipse.compare.internal.MergeSourceViewer;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.text.source.AnnotationModel;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * This class handle the navigation to the next comments in the compare editor.
 */
public class NextCommentAnnotationHandler extends AbstractHandler {

	/**
	 * Method execute.
	 *
	 * @param aEvent
	 *            ExecutionEvent
	 * @return Object
	 * @see org.eclipse.core.commands.IHandler#execute(ExecutionEvent)
	 */
	public Object execute(final ExecutionEvent aEvent) {

		IEditorPart activeEditor = HandlerUtil.getActiveEditor(aEvent);

		CompareEditorInput editPart = (CompareEditorInput) activeEditor.getEditorInput();

		MergeSourceViewer mergeSourceViewer = UICompareUtils.extractMergeSourceViewer(editPart.getNavigator(), false);

		if (mergeSourceViewer != null) {
			SourceViewer sourceViewer = mergeSourceViewer.getSourceViewer();
			AnnotationModel sourceModel = ((CommentableCompareItem) sourceViewer.getDocument()).getEditableComments();

			Map<Integer, GerritCommentAnnotation> adjustedCommentInSourceModelMap = UICompareUtils
					.adjustCommentOffsetInMap(sourceModel);
			GerritCommentAnnotation nextGerritCommentAnnotation = findNextGerritComment(adjustedCommentInSourceModelMap,
					sourceViewer);

			//if (nextGerritCommentAnnotation == null) means there is no comment in this file yet
			if (nextGerritCommentAnnotation != null) {
				Position modelPosition = sourceModel.getPosition(nextGerritCommentAnnotation);
				sourceViewer.setSelection(new TextSelection(modelPosition.getOffset(), modelPosition.length), true);
			}
		}
		return null;
	}

	/**
	 * Method isEnabled.
	 *
	 * @return boolean
	 */
	@Override
	public boolean isEnabled() {

		return true;
	}

	/**
	 * Based on the current cursor position, find the next Gerrit Comment annotation. If the cursor is after the last
	 * comment, loop to select the first comment in the list.
	 *
	 * @param treeMapComment
	 * @param sourceViewer
	 * @return GerritCommentAnnotation
	 */
	private GerritCommentAnnotation findNextGerritComment(Map<Integer, GerritCommentAnnotation> treeMapComment,
			SourceViewer sourceViewer) {
		GerritCommentAnnotation firstCommentAnnotation = null;
		GerritCommentAnnotation nextGerritCommentAnnotation = null;
		Point curPos = sourceViewer.getSelectedRange();

		Iterator<Entry<Integer, GerritCommentAnnotation>> entrysetIter = treeMapComment.entrySet().iterator();
		while (entrysetIter.hasNext()) {
			Entry<Integer, GerritCommentAnnotation> entry = entrysetIter.next();
			if (firstCommentAnnotation == null) {
				//Record the first annotation from the list;
				firstCommentAnnotation = entry.getValue();
			}

			//Test your position according to the next Gerrit comment
			if (entry.getKey() > curPos.x) {
				//Keep this GerritCommentAnnotation
				nextGerritCommentAnnotation = entry.getValue();
				break;
			}

		}
		if (nextGerritCommentAnnotation == null) {
			//loop to select the first one
			nextGerritCommentAnnotation = firstCommentAnnotation;
		}
		return nextGerritCommentAnnotation;
	}

}
