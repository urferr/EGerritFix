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

import org.eclipse.compare.CompareEditorInput;
import org.eclipse.compare.ICompareNavigator;
import org.eclipse.compare.contentmergeviewer.TextMergeViewer;
import org.eclipse.compare.internal.CompareContentViewerSwitchingPane;
import org.eclipse.compare.internal.CompareEditorInputNavigator;
import org.eclipse.compare.internal.MergeSourceViewer;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.Viewer;

/**
 * This class provides general utility methods used in the Compare UI implementation.
 */
class UICompareUtils {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	static final String NEXT_COMMENT_ANNOTATION_COMMAND = "org.eclipse.egerrit.internal.ui.compare.NextCommentAnnotationHandler"; //$NON-NLS-1$

	static final String NEXT_COMMENT_ANNOTATION_COMMAND_MNEMONIC = "N"; //$NON-NLS-1$

	static final String PREVIOUS_COMMENT_ANNOTATION_COMMAND = "org.eclipse.egerrit.internal.ui.compare.PreviousCommentAnnotationHandler"; //$NON-NLS-1$

	static final String PREVIOUS_COMMENT_ANNOTATION_COMMAND_MNEMONIC = "P"; //$NON-NLS-1$

	private static final String COMPARE_EDITOR_TEXT_CLASS_NAME = "org.eclipse.compare.contentmergeviewer.TextMergeViewer"; //$NON-NLS-1$

	private static final String COMPARE_EDITOR_TEXT_FIELD_LEFT = "fLeft"; //$NON-NLS-1$

	private static final String COMPARE_EDITOR_TEXT_FIELD_RIGHT = "fRight"; //$NON-NLS-1$

	private static final String DEFAULT_OBJECT_CLASS_NAME = "Object"; //$NON-NLS-1$

	private static final String MIRRORED_PROPERTY = "MIRRORED"; //$NON-NLS-1$ The constant used by oxygen to indicate the status of the swap side option of the compare editor

	/**
	 * The default constructor. Do not allow to build an object of this class
	 */
	private UICompareUtils() {
	}

	/**
	 * Method insertAnnotationNavigationCommands.
	 *
	 * @param aManager
	 *            IToolBarManager
	 * @param aSupport
	 *            IReviewAnnotationSupport
	 */
	static void insertAnnotationNavigationCommands(IToolBarManager aManager) {
		aManager.add(new Separator());
		final AnnotationContributionItems itemsManager = new AnnotationContributionItems();
		final IContributionItem[] items = itemsManager.getallContributionItems();
		for (IContributionItem item : items) {
			aManager.add(item);
		}
		aManager.update(true);
	}

	/**
	 * Method extractMergeSourceViewer.
	 *
	 * @param aInput
	 *            ICompareNavigator boolean left or right pane
	 * @return MergeSourceViewer
	 */
	public static MergeSourceViewer extractMergeSourceViewer(ICompareNavigator aNavigator, boolean getLeftPane) {

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
						Class<?> textViewerClass = textViewer.getClass();
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
							if (getLeftPane) {
								field = textViewerClass.getDeclaredField(COMPARE_EDITOR_TEXT_FIELD_LEFT);
							} else {
								field = textViewerClass.getDeclaredField(COMPARE_EDITOR_TEXT_FIELD_RIGHT);
							}
							field.setAccessible(true);
							return (MergeSourceViewer) field.get(textViewer);

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

	static boolean isMirroredOn(CompareEditorInput input) {
		return Boolean.TRUE.equals(input.getCompareConfiguration().getProperty(MIRRORED_PROPERTY));
	}
}
