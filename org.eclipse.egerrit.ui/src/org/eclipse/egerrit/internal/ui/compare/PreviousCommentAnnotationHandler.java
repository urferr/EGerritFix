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
 * This Class handle the navigation to the previous comment in the compare editor.
 */
public class PreviousCommentAnnotationHandler extends AbstractHandler {

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method execute.
	 *
	 * @param aEvent
	 *            ExecutionEvent
	 * @return Object * @see org.eclipse.core.commands.IHandler#execute(ExecutionEvent)
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
			GerritCommentAnnotation previousGerritCommentAnnotation = findPreviousGerritComment(
					adjustedCommentInSourceModelMap, sourceViewer);

			//if (previousGerritCommentAnnotation == null) means there is no comment in this file yet
			if (previousGerritCommentAnnotation != null) {
				Position modelPosition = sourceModel.getPosition(previousGerritCommentAnnotation);
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
	 * Based on the current cursor position, find the previous Gerrit Comment annotation. If the cursor is before the
	 * last comment, loop to select the last comment in the list.
	 *
	 * @param treeMapComment
	 * @param sourceViewer
	 * @return GerritCommentAnnotation
	 */
	private GerritCommentAnnotation findPreviousGerritComment(Map<Integer, GerritCommentAnnotation> treeMapComment,
			SourceViewer sourceViewer) {
		GerritCommentAnnotation lastCommentAnnotation = null;
		GerritCommentAnnotation previousGerritCommentAnnotation = null;
		Point curPos = sourceViewer.getSelectedRange();
		curPos.x = curPos.x - 1; //set the position -1 to allow loop to previous
		//Find last Gerrit comment
		Object[] keyArray = treeMapComment.keySet().toArray();
		int lastItem = keyArray.length - 1;//<0 if there is no comment yet
		if (lastItem >= 0) {
			lastCommentAnnotation = treeMapComment.get(keyArray[lastItem]);
		}

		Iterator<Entry<Integer, GerritCommentAnnotation>> entrysetIter = treeMapComment.entrySet().iterator();
		while (entrysetIter.hasNext()) {
			Entry<Integer, GerritCommentAnnotation> entry = entrysetIter.next();

			//Test your position according to the next Gerrit comment
			if (entry.getKey() > curPos.x) {
				break;
			}
			//Put the comment until we are at a position greater
			previousGerritCommentAnnotation = entry.getValue();

		}
		if (previousGerritCommentAnnotation == null) {
			//loop to select the last one
			previousGerritCommentAnnotation = lastCommentAnnotation;
		}
		return previousGerritCommentAnnotation;
	}

}
