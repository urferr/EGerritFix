/*******************************************************************************
 * Copyright (c) 2013, 2014 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jacques Bouthillier - Initial Implementation of the conflict with label provider
 ******************************************************************************/

package org.eclipse.egerrit.ui.internal.table.provider;

import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.egerrit.core.rest.ChangeInfo;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

/**
 * This class implements the Conflict with table UI label provider.
 *
 * @since 1.0
 */
public class ConflictWithTableLabelProvider extends ObservableMapLabelProvider
		implements ITableLabelProvider, ITableColorProvider {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	private final String EMPTY_STRING = ""; //$NON-NLS-1$

	// Constant for the column with colors: CR, IC and V
	private static Display fDisplay = Display.getCurrent();

//	private static Color RED = fDisplay.getSystemColor(SWT.COLOR_RED);
//
//	private static Color GREEN = fDisplay.getSystemColor(SWT.COLOR_DARK_GREEN);
//
	//Color used depending on the review state
	private static Color DEFAULT_COLOR = fDisplay.getSystemColor(SWT.COLOR_LIST_BACKGROUND);

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
	public ConflictWithTableLabelProvider(IObservableMap[] iObservableMaps) {
		super(iObservableMaps);
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Return an image representing the state of the object
	 *
	 * @param int
	 *            aState
	 * @return Image
	 */
	private Image getReviewStateImage(int aState) {
		switch (aState) {
		}
		return null;
	}

	/**
	 * Return an image representing the state of the object
	 *
	 * @param int
	 *            aState
	 * @return Image
	 */
	private Image getVerifyStateImage(int aState) {
		switch (aState) {
		default:
			break;
		}
		return null;
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
	@SuppressWarnings("restriction")
	public String getColumnText(Object aObj, int aIndex) {
		// GerritPlugin.Ftracer.traceWarning("getColumnText object: " + aObj
		// + "\tcolumn: " + aIndex);
		if (aObj instanceof ChangeInfo) {
			ChangeInfo changeInfo = (ChangeInfo) aObj;
			switch (aIndex) {
			case 0:
				return changeInfo.getChange_id();
			case 1:
				return changeInfo.getSubject();
			case 2:
				return "";
			default:
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
	@SuppressWarnings("restriction")
	public Image getColumnImage(Object aObj, int aIndex) {
		// GerritPlugin.Ftracer
		// .traceWarning("getColumnImage column: " + aIndex);
		Image image = null;
		String value = null;
		if (aObj instanceof ChangeInfo) {
			ChangeInfo reviewSummary = (ChangeInfo) aObj;
			switch (aIndex) {
			case 0:
				break;
			case 1:
				return image;
			case 2:
				return image;
			case 3:
				return image;
			case 4:
				return image;
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
	public Color getForeground(Object aElement, int aColumnIndex) {
		if (aElement instanceof ChangeInfo) {
			int value = 0;
		}
		return null;
	}

	@Override
	@SuppressWarnings("restriction")
	public Color getBackground(Object aElement, int aColumnIndex) {
		// GerritUi.Ftracer.traceInfo("getBackground column : " +
		// aColumnIndex +
		// " ]: "+ aElement );
		if (aElement instanceof ChangeInfo) {
			ChangeInfo item = (ChangeInfo) aElement;
		}

		return DEFAULT_COLOR;
	}

	@Override
	public String getText(Object element) {
		System.err.println("FileTableLabel getText()");
		return getColumnText(element, 0);
	}

}
