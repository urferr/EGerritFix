/*******************************************************************************
 * Copyright (c) 2013, 2014 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jacques Bouthillier - Initial Implementation of the file label provider
 ******************************************************************************/

package org.eclipse.egerrit.internal.ui.table.provider;

import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.egerrit.internal.model.ReviewerInfo;
import org.eclipse.egerrit.internal.ui.table.model.ReviewersTableModel;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

/**
 * This class implements the File table UI label provider.
 *
 * @since 1.0
 */
public class ReviewersTableLabelProvider extends BaseTableLabelProvider {

	// Constant for the column with colors: CR, IC and V
	private static Display fDisplay = Display.getCurrent();

	//Color used depending on the review state
	private static Color RED_COLOR = fDisplay.getSystemColor(SWT.COLOR_RED);

	private static Color GREEN_COLOR = fDisplay.getSystemColor(SWT.COLOR_DARK_GREEN);

	private String[] dynamicName;

	private int defaultColumn = ReviewersTableModel.values().length;

	private int fValue;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	public ReviewersTableLabelProvider(IObservableMap[] iObservableMaps, String[] dynamicReviewersColumn) {
		super(iObservableMaps);
		this.dynamicName = dynamicReviewersColumn;
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

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
		if (aObj instanceof ReviewerInfo) {
			ReviewerInfo reviewerInfo = (ReviewerInfo) aObj;
			switch (aIndex) {
			case 0:
				if (reviewerInfo.isDeleteable()) {
					return "x"; //$NON-NLS-1$
				}
				return ""; //$NON-NLS-1$
			case 1:
				return super.getColumnText(aObj, aIndex);
			case 2:
				return super.getColumnText(aObj, aIndex);
			default:
				//Handle all dynamic columns
				String label = getColumnLabels(aIndex);
				if (reviewerInfo.getApprovals() != null && reviewerInfo.getApprovals().containsKey(label)) {
					String tmp = reviewerInfo.getApprovals().get(label);
					if (tmp.trim().contains("0")) { //$NON-NLS-1$
						fValue = 0;
					} else {
						fValue = Integer.valueOf(tmp);
						String convert = fValue > 0 ? "+" : ""; //$NON-NLS-1$//$NON-NLS-2$
						convert = convert + StringConverter.asString(fValue);
						return convert;
					}
				}
				return EMPTY_STRING;
			}
		}
		return EMPTY_STRING;
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
		if (aElement instanceof ReviewerInfo) {
			switch (aColumnIndex) {
			case 0:
			case 1:
			case 2:
				break;
			default:
				if (fValue < 0) {
					return RED_COLOR;
				} else if (fValue > 0) {
					return GREEN_COLOR;
				}
			}
		}
		return null;

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
			return dynamicName[val];
		}
		return ""; //$NON-NLS-1$
	}

}
