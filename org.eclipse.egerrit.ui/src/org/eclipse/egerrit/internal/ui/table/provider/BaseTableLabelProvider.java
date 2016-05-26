/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jacques Bouthillier - Initial Implementation of the file label provider
 *   Matthew Khouzam - Pull to a base label provider
 ******************************************************************************/

package org.eclipse.egerrit.internal.ui.table.provider;

import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

/**
 * This class implements the File table UI label provider.
 *
 * @since 1.0
 */
public class BaseTableLabelProvider extends ObservableMapLabelProvider
		implements ITableLabelProvider, ITableColorProvider {

	private static final int INTEGER_BASE = 10;

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Empty string.
	 */
	protected static final String EMPTY_STRING = ""; //$NON-NLS-1$

	// Constant for the column with colors: CR, IC and V
	private static Display fDisplay = Display.getCurrent();

	/**
	 * Color used depending on the review state
	 */
	protected static Color DEFAULT_COLOR = fDisplay.getSystemColor(SWT.COLOR_LIST_BACKGROUND);

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	public BaseTableLabelProvider(IObservableMap[] iObservableMaps) {
		super(iObservableMaps);
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Format the numbering value to display
	 *
	 * @param aSt
	 *            the origin number string
	 * @return String a number string that will start with + if signed, - if unsigned, and return '0' if it is zero
	 */
	protected String formatValue(String aSt) {
		int val = aSt.equals(EMPTY_STRING) ? 0 : Integer.parseInt(aSt, INTEGER_BASE);
		if (val > 0) {
			String st = "+" + aSt; //$NON-NLS-1$
			return st;
		}
		return aSt;
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
		return null;
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
		return null;
	}

	@Override
	public Color getBackground(Object aElement, int aColumnIndex) {

		return DEFAULT_COLOR;
	}

	@Override
	public String getText(Object element) {
		return getColumnText(element, 0);
	}

}
