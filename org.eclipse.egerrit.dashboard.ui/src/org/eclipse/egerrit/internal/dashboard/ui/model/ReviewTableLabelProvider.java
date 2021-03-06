/*******************************************************************************
 * Copyright (c) 2013, 2017 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jacques Bouthillier - Initial Implementation of the label provider
 *   Marc-Andre Laperle - Add Topic to dashboard
 *   Marc-Andre Laperle - Add Status to dashboard
 ******************************************************************************/

package org.eclipse.egerrit.internal.dashboard.ui.model;

import org.eclipse.egerrit.internal.core.utils.Utils;
import org.eclipse.egerrit.internal.dashboard.ui.GerritUi;
import org.eclipse.egerrit.internal.model.ChangeInfo;
import org.eclipse.egerrit.internal.model.provider.ChangeInfoItemProvider;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

/**
 * This class implements the Dashboard-Gerrit UI view label provider.
 *
 * @since 1.0
 */
public class ReviewTableLabelProvider extends ChangeInfoItemProvider {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	private static final String EMPTY_STRING = ""; //$NON-NLS-1$

	private int fState;

	private int defaultColumn = ReviewTableDefinition.values().length;

	private String[] voteColumns;

	// Names of images used to represent STAR FILLED
	public static final String STAR_FILLED = "starFilled.gif"; //$NON-NLS-1$

	// Names of images used to represent STAR OPEN
	public static final String STAR_OPEN = "starOpen.gif"; //$NON-NLS-1$

	// Constant for the column with colors: CR, IC and V
	private static Display fDisplay = Display.getCurrent();

	//Color used depending on the review state
	private static final Color DEFAULT_COLOR = fDisplay.getSystemColor(SWT.COLOR_LIST_BACKGROUND);

	private static final Color DEFAULT_FOREGROUND_COLOR = fDisplay.getSystemColor(SWT.COLOR_DARK_GRAY);

	private static final Color BOLD_COLOR = fDisplay.getSystemColor(SWT.COLOR_BLACK);

	private static final Color INCOMING_COLOR = fDisplay.getSystemColor(SWT.COLOR_WHITE);

	private static final Color CLOSED_COLOR = fDisplay.getSystemColor(SWT.COLOR_WHITE);

	private static final Color RED_COLOR = fDisplay.getSystemColor(SWT.COLOR_RED);

	private static final Color GREEN_COLOR = fDisplay.getSystemColor(SWT.COLOR_DARK_GREEN);

	// For the images

	private static ImageRegistry fImageRegistry = new ImageRegistry();

	/**
	 * Note: An image registry owns all of the image objects registered with it, and automatically disposes of them the
	 * SWT Display is disposed.
	 */
	static {

		String iconPath = "icons/view16/"; //$NON-NLS-1$

		fImageRegistry.put(STAR_FILLED, GerritUi.getImageDescriptor(iconPath + STAR_FILLED));

		fImageRegistry.put(STAR_OPEN, GerritUi.getImageDescriptor(iconPath + STAR_OPEN));
	}

	public ReviewTableLabelProvider(AdapterFactory adapterFactory, String[] columnDescription) {
		super(adapterFactory);
		voteColumns = columnDescription;
	}

	/**
	 * Return an image representing the state of the ID object
	 *
	 * @param Boolean
	 *            aState
	 * @return Image
	 */
	private Image getReviewId(Boolean aState) {
		if (aState) {
			// True means the star is filled ??
			return fImageRegistry.get(STAR_FILLED);
		} else {
			//
			return fImageRegistry.get(STAR_OPEN);
		}
	}

	/**
	 * Return the text associated to the column
	 *
	 * @param Object
	 *            structure of the table
	 * @param int
	 *            column index
	 * @return String text associated to the column
	 */
	@Override
	public String getColumnText(Object aObj, int aIndex) {
		if (aObj instanceof ChangeInfo) {
			ChangeInfo reviewSummary = (ChangeInfo) aObj;
			switch (aIndex) {
			case 0:
				Boolean starred = reviewSummary.isStarred();
				if (starred) {
					return Messages.Starred;
				} else {
					return Messages.Not_Starred;
				}
			case 1:
				return Integer.toString(reviewSummary.get_number());
			case 2:
				return reviewSummary.getSubject();
			case 3:
				String attribute = reviewSummary.getStatus();
				return attribute;
			case 4:
				return reviewSummary.getOwner().getName();
			case 5:
				return reviewSummary.getProject();
			case 6:
				String branch = reviewSummary.getBranch();
				String topic = reviewSummary.getTopic();
				if (topic != null && !topic.isEmpty()) {
					branch += " (" + topic + ")"; //$NON-NLS-1$ //$NON-NLS-2$
				}
				return branch;
			case 7:
				return Utils.prettyPrintDate(reviewSummary.getUpdated());

			default:
				String label = getColumnLabels(aIndex);
				fState = reviewSummary.getMostRelevantVote(label).getValue();

				int min = reviewSummary.getLabelMinValue(label);
				int max = reviewSummary.getLabelMaxValue(label);
				if (max != 0 && fState >= max) {
					return "\u2713"; //Check mark value in hexadecimal //$NON-NLS-1$
				} else if (min != 0 && fState <= min) {
					return "X"; //$NON-NLS-1$
				} else if (fState != 0) {
					String st = fState > 0 ? "+" : ""; //$NON-NLS-1$//$NON-NLS-2$
					st = st + StringConverter.asString(fState);
					return st;
				}
				return EMPTY_STRING;
			}
		}
		return EMPTY_STRING;
	}

	/**
	 * Return the image associated to the column
	 *
	 * @param Object
	 *            structure of the table
	 * @param int
	 *            column index
	 * @return Image Image according to the selected column
	 */
	@Override
	public Image getColumnImage(Object aObj, int aIndex) {
		Image image = null;
		String value = null;
		if (aObj instanceof ChangeInfo) {
			ChangeInfo reviewSummary = (ChangeInfo) aObj;
			switch (aIndex) {
			case 0:
				Boolean starred = reviewSummary.isStarred();
				value = starred.toString(); // Needed for the sorter
				if (null != value && !value.equals(EMPTY_STRING)) {
					return getReviewId(Boolean.valueOf(value.toLowerCase()));
				}
				break;
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			default:
				return image;
			}
		}

		return image;
	}

	/**
	 * Adjust the column color
	 *
	 * @param Object
	 *            ReviewTableListItem
	 * @param int
	 *            columnIndex
	 */
	@Override
	public Object getForeground(Object aElement, int aColumnIndex) {
		if (aElement instanceof ChangeInfo) {
			switch (aColumnIndex) {
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
				ChangeInfo changeInfo = (ChangeInfo) aElement;
				if (changeInfo.isReviewed()) {
					return DEFAULT_FOREGROUND_COLOR;
				} else {
					return BOLD_COLOR;
				}
			default:
				if (fState < 0) {
					return RED_COLOR;
				} else if (fState > 0) {
					return GREEN_COLOR;
				}
			}
		}
		return null;

	}

	@Override
	public Object getBackground(Object aElement, int aColumnIndex) {
		if (aElement instanceof ChangeInfo) {
			ChangeInfo item = (ChangeInfo) aElement;
			//
			// To modify when we can verify the review state
			String state = Boolean.toString(item.isStarred());
			if (state != null) {
				if (state.equals(Boolean.toString(true))) {
					return INCOMING_COLOR;

				} else if (state.equals(Boolean.toString(false))) {
					return CLOSED_COLOR;
				}
			}
		}

		return DEFAULT_COLOR;
	}

	/**
	 * Return the name for a dynamic column
	 *
	 * @param column
	 * @return
	 */
	private String getColumnLabels(int column) {
		int val = column - defaultColumn;//Adjust the dynamic column value
		if (val >= 0) {
			return voteColumns[val];
		}
		return ""; //$NON-NLS-1$
	}

}
