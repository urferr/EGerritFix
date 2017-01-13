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
import java.util.Map.Entry;
import java.util.TreeMap;

import org.eclipse.compare.CompareEditorInput;
import org.eclipse.compare.internal.MergeSourceViewer;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * This class handle the navigation to the next/previous comments in the compare editor.
 */
public class NextPreviousCommentAnnotationHandler extends AbstractHandler {

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

		MergeSourceViewer mergeSourceViewer = UICompareUtils.extractMergeSourceViewer(editPart.getNavigator(),
				!UICompareUtils.isMirroredOn(editPart));

		if (mergeSourceViewer != null) {
			SourceViewer sourceViewer = mergeSourceViewer.getSourceViewer();
			String commandName = aEvent.getCommand().getId();
			boolean isNext = commandName.contains("NextCommentAnnotationHandler"); //$NON-NLS-1$
			Position requestedCommentPosition = null;
			Point curPos = sourceViewer.getSelectedRange();
			if (!(sourceViewer.getDocument() instanceof PatchSetCompareItem)) {
				return null;
			}
			PatchSetCompareItem patchSetCompareItem = ((PatchSetCompareItem) sourceViewer.getDocument());
			TreeMap<Integer, GerritCommentAnnotation> sortedCommentMap = getSortedGerritAnnotation(patchSetCompareItem);
			if (isNext) {
				requestedCommentPosition = findNextGerritCommentPosition(sortedCommentMap, curPos, patchSetCompareItem);
			} else {
				requestedCommentPosition = findPreviousGerritCommentPosition(sortedCommentMap, curPos,
						patchSetCompareItem);
			}

			//if (requestedCommentPosition == null) means there is no comment in this file yet
			if (!(requestedCommentPosition == null)) {
				int offset = requestedCommentPosition.getOffset();
				int length = requestedCommentPosition.getLength();
				sourceViewer.setSelection(new TextSelection(offset, length), true);
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
	 * Based on the current cursor position, find the next Gerrit Comment annotation position. If the cursor is after
	 * the last comment, loop to select the first comment in the list.
	 *
	 * @param sortedCommentMap
	 * @param curPos
	 * @param patchSetCompareItem
	 * @return Position
	 */
	private Position findNextGerritCommentPosition(TreeMap<Integer, GerritCommentAnnotation> sortedCommentMap,
			Point curPos, PatchSetCompareItem patchSetCompareItem) {

		Entry<Integer, GerritCommentAnnotation> entryMap = sortedCommentMap.higherEntry(curPos.x);
		if (entryMap != null) {
			return setPosition(entryMap);
		}
		return getFirstCommentPosition(sortedCommentMap);
	}

	/**
	 * Based on the current cursor position, find the previous Gerrit Comment annotation position. If the cursor is
	 * before the last comment, loop to select the last comment in the list.
	 *
	 * @param sortedCommentMap
	 * @param curPos
	 * @param patchSetCompareItem
	 * @return Position
	 */
	private Position findPreviousGerritCommentPosition(TreeMap<Integer, GerritCommentAnnotation> sortedCommentMap,
			Point curPos, PatchSetCompareItem patchSetCompareItem) {

		Entry<Integer, GerritCommentAnnotation> entryMap = sortedCommentMap.lowerEntry(curPos.x);
		if (entryMap != null) {
			return setPosition(entryMap);
		}
		return getLastCommentPosition(sortedCommentMap);
	}

	/**
	 * Set a viewer position according to the data available in the GerritCommentAnnotation
	 *
	 * @param entryMap
	 * @return Position
	 */
	private Position setPosition(Entry<Integer, GerritCommentAnnotation> entryMap) {
		if (entryMap != null) {
			Position position = new Position(0, 0);
			position.setOffset(entryMap.getKey());
			position.setLength(entryMap.getValue().getText().length());
			return position;
		}
		return null;
	}

	/**
	 * Get a sorted map of Gerrit Comment annotation
	 *
	 * @param patchSetCompareItem
	 * @return TreeMap<Integer, GerritCommentAnnotation>
	 */
	private TreeMap<Integer, GerritCommentAnnotation> getSortedGerritAnnotation(
			PatchSetCompareItem patchSetCompareItem) {
		TreeMap<Integer, GerritCommentAnnotation> adjustedMap = new TreeMap<Integer, GerritCommentAnnotation>();
		Iterator<?> it = patchSetCompareItem.getEditableComments().getAnnotationIterator();

		while (it.hasNext()) {
			GerritCommentAnnotation annotation = (GerritCommentAnnotation) it.next();
			Position position = patchSetCompareItem.getEditableComments().getPosition(annotation);
			adjustedMap.put(position.offset, annotation);
		}

//		return adjustedMap;// To navigate through each comments

		//To navigate by group of comment
		return getGroupCommentAnnotation(adjustedMap);
	}

	private TreeMap<Integer, GerritCommentAnnotation> getGroupCommentAnnotation(
			TreeMap<Integer, GerritCommentAnnotation> initSortedMap) {
		//To navigate by group
		int lastLine = -1;
		TreeMap<Integer, GerritCommentAnnotation> groupMap = new TreeMap<Integer, GerritCommentAnnotation>();

		Iterator<Entry<Integer, GerritCommentAnnotation>> iterGroup = initSortedMap.entrySet().iterator();
		while (iterGroup.hasNext()) {
			Entry<Integer, GerritCommentAnnotation> entry = iterGroup.next();
			GerritCommentAnnotation annotation = entry.getValue();

			int currentLine = annotation.getComment().getLine();
			if (currentLine != lastLine) {
				lastLine = currentLine;
				groupMap.put(entry.getKey(), annotation);
			}
		}
		return groupMap;
	}

	/**
	 * Return the first Gerrit comment annotation position provided from the annotation model
	 *
	 * @param sortedCommentMap
	 * @return Position
	 */
	private Position getFirstCommentPosition(TreeMap<Integer, GerritCommentAnnotation> sortedCommentMap) {
		Position position = null;
		if (!sortedCommentMap.isEmpty()) {
			int offset = sortedCommentMap.firstKey();
			int length = sortedCommentMap.firstEntry().getValue().getText().length();
			position = new Position(offset, length);
		}
		return position;
	}

	/**
	 * Return the last Gerrit comment annotation position provided from the annotation model
	 *
	 * @param sortedCommentMap
	 * @return Position
	 */
	private Position getLastCommentPosition(TreeMap<Integer, GerritCommentAnnotation> sortedCommentMap) {
		Position position = null;
		if (!sortedCommentMap.isEmpty()) {
			int offset = sortedCommentMap.lastKey();
			int length = sortedCommentMap.lastEntry().getValue().getText().length();
			position = new Position(offset, length);
		}
		return position;
	}
}
