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

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.eclipse.compare.ICompareNavigator;
import org.eclipse.compare.contentmergeviewer.TextMergeViewer;
import org.eclipse.compare.internal.CompareContentViewerSwitchingPane;
import org.eclipse.compare.internal.CompareEditorInputNavigator;
import org.eclipse.compare.internal.MergeSourceViewer;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.AnnotationModel;
import org.eclipse.jface.viewers.Viewer;

/**
 * This class provides general utility methods used in the Compare UI implementation.
 */
public class UICompareUtils {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	public static final String NEXT_COMMENT_ANNOTATION_COMMAND = "org.eclipse.egerrit.internal.ui.compare.NextCommentAnnotationHandler"; //$NON-NLS-1$

	public static final String NEXT_COMMENT_ANNOTATION_ICON_FILE = "icons/nxtcomment_menu.png"; //$NON-NLS-1$

	public static final String NEXT_COMMENT_ANNOTATION_COMMAND_MNEMONIC = "N"; //$NON-NLS-1$

	public static final String PREVIOUS_COMMENT_ANNOTATION_COMMAND = "org.eclipse.egerrit.internal.ui.compare.PreviousCommentAnnotationHandler"; //$NON-NLS-1$

	public static final String PREVIOUS_COMMENT_ANNOTATION_ICON_FILE = "icons/prevcomment_menu.png"; //$NON-NLS-1$

	public static final String PREVIOUS_COMMENT_ANNOTATION_COMMAND_MNEMONIC = "P"; //$NON-NLS-1$

	private static final String COMPARE_EDITOR_TEXT_CLASS_NAME = "org.eclipse.compare.contentmergeviewer.TextMergeViewer"; //$NON-NLS-1$

	private static final String COMPARE_EDITOR_TEXT_FIELD_LEFT = "fLeft"; //$NON-NLS-1$

	private static final String COMPARE_EDITOR_TEXT_FIELD_RIGHT = "fRight"; //$NON-NLS-1$

	private static final String DEFAULT_OBJECT_CLASS_NAME = "Object"; //$NON-NLS-1$

	public static int CURRENT_ELEMENT = 0;

	/**
	 * Method insertAnnotationNavigationCommands.
	 *
	 * @param aManager
	 *            IToolBarManager
	 * @param aSupport
	 *            IReviewAnnotationSupport
	 */
	public static void insertAnnotationNavigationCommands(IToolBarManager aManager) {
		aManager.add(new Separator());
		final AnnotationContributionItems itemsManager = new AnnotationContributionItems();
		final IContributionItem[] items = itemsManager.getallContributionItems();
		for (IContributionItem item : items) {
			aManager.add(item);
		}
		aManager.update(true);
	}

	public static void setCurrentElement(int element) {
		CURRENT_ELEMENT = element;
	}

	public static int getCurrentElement() {
		return CURRENT_ELEMENT;
	}

	/**
	 * Method extractMergeSourceViewer.
	 *
	 * @param aInput
	 *            ICompareNavigator boolean left or right pane
	 * @return MergeSourceViewer
	 */
	public static MergeSourceViewer extractMergeSourceViewer(ICompareNavigator aNavigator, boolean aIsLeftPane) {

		//Use free form to select position in file
		//NOTE:  This is a dirty hack that involves accessing class and field we shouldn't, but that's
		//       the only way to select the current position in the compare editor.  Hopefully this code can
		//		 be removed later when the Eclipse compare editor allows this.
		if (aNavigator instanceof CompareEditorInputNavigator) {
			final Object[] panes = ((CompareEditorInputNavigator) aNavigator).getPanes();
			for (Object pane : panes) {
				if (pane instanceof CompareContentViewerSwitchingPane) {
					Viewer viewer = ((CompareContentViewerSwitchingPane) pane).getViewer();

					if (viewer instanceof TextMergeViewer) {
						TextMergeViewer textViewer = (TextMergeViewer) viewer;
						Class textViewerClass = textViewer.getClass();
						if (!textViewerClass.getName().equals(COMPARE_EDITOR_TEXT_CLASS_NAME)) {
							do {
								textViewerClass = textViewerClass.getSuperclass();
								if (textViewerClass.getName().equals(DEFAULT_OBJECT_CLASS_NAME)) {
									break;
								}
							} while (!textViewerClass.getName().equals(COMPARE_EDITOR_TEXT_CLASS_NAME));
						}
						try {
							Field field;
							if (aIsLeftPane) {
								field = textViewerClass.getDeclaredField(COMPARE_EDITOR_TEXT_FIELD_LEFT);
							} else {
								field = textViewerClass.getDeclaredField(COMPARE_EDITOR_TEXT_FIELD_RIGHT);
							}
							field.setAccessible(true);
							MergeSourceViewer sourceViewer = (MergeSourceViewer) field.get(textViewer);

							return sourceViewer;

						} catch (SecurityException e) {

							//just continue
						} catch (NoSuchFieldException e) {
							//just continue
						} catch (IllegalArgumentException e) {
							//just continue
						} catch (IllegalAccessException e) {
							//just continue
						}
					}
				}
			}
		}
		return null;
	}

	/**
	 * Create a comment map based on the Gerrit Comment annotation
	 *
	 * @param sourceModel
	 * @return Map<Integer, GerritCommentAnnotation>
	 */
	private static Map<Integer, GerritCommentAnnotation> createSortedGerritCommentmap(AnnotationModel sourceModel) {
		Map<Integer, GerritCommentAnnotation> treeMap = new TreeMap<Integer, GerritCommentAnnotation>();
		Iterator<Annotation> annotIterator = sourceModel.getAnnotationIterator();
		while (annotIterator.hasNext()) {
			Annotation annotation = annotIterator.next();
			GerritCommentAnnotation gerritComment = (GerritCommentAnnotation) annotation;
			int key = gerritComment.getPosition().getOffset();
			if (treeMap.containsKey(key)) {
				GerritCommentAnnotation alreadyThere = treeMap.get(key);
				String oldTimeUpdate = alreadyThere.getComment().getUpdated();
				String newTimeUpdate = gerritComment.getComment().getUpdated();
				if (oldTimeUpdate.compareToIgnoreCase(newTimeUpdate) > 0) {
					//Means this new comment should come first
					treeMap.put(gerritComment.getPosition().getOffset(), gerritComment);
				}
			} else {
				treeMap.put(gerritComment.getPosition().getOffset(), gerritComment);
			}
		}
		printMap("Sorted map", treeMap); //$NON-NLS-1$

		return treeMap;
	}

	/**
	 * Adjust the Gerrit comment annotation with the source model displayed offset.
	 *
	 * @param sourceModel
	 * @return Map<Integer, GerritCommentAnnotation>
	 */
	public static Map<Integer, GerritCommentAnnotation> adjustCommentOffsetInMap(AnnotationModel sourceModel) {

		Map<Integer, GerritCommentAnnotation> initMap = createSortedGerritCommentmap(sourceModel);
		Map<Integer, GerritCommentAnnotation> adjustedMap = new TreeMap<Integer, GerritCommentAnnotation>();
		Iterator<Entry<Integer, GerritCommentAnnotation>> iter = initMap.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<Integer, GerritCommentAnnotation> entry = iter.next();
			Position adjustedPosition = sourceModel.getPosition(entry.getValue());
			adjustedMap.put(adjustedPosition.getOffset(), entry.getValue());
		}
		printMap("Sorted map ADJUSTED", adjustedMap); //$NON-NLS-1$

		return adjustedMap;
	}

	/**
	 * Print the selected map.
	 *
	 * @param mapName
	 * @param map
	 */
	private static void printMap(String mapName, Map<Integer, GerritCommentAnnotation> map) {
//		System.out.println("----------------- " + mapName + " ------------");
//		Iterator<Entry<Integer, GerritCommentAnnotation>> entrysetIter = map.entrySet().iterator();
//		while (entrysetIter.hasNext()) {
//			Entry<Integer, GerritCommentAnnotation> entry = entrysetIter.next();
//			System.out.println(mapName + " : " + entry.getKey() + "\t line: " + entry.getValue().getComment().getLine()
//					+ "\t comment:" + entry.getValue().getText());
//		}
//		System.out.println("----------------- " + mapName + " END ------------");
	}
}
