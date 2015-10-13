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

package org.eclipse.egerrit.ui.internal.table.provider;

import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.egerrit.core.rest.ChangeInfo;

/**
 * This class implements the File table UI label provider.
 *
 * @since 1.0
 */
public class SameTopicTableLabelProvider extends BaseTableLabelProvider {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	public SameTopicTableLabelProvider(IObservableMap[] iObservableMaps) {
		super(iObservableMaps);
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
		if (aObj instanceof ChangeInfo) {
			ChangeInfo changeInfo = (ChangeInfo) aObj;
			switch (aIndex) {
			case 0:
				if (changeInfo.get_number() == -1) {
					return EMPTY_STRING;
				}
				return Integer.toString(changeInfo.get_number());
			case 1:
				return changeInfo.getSubject();
			case 2:
				return EMPTY_STRING;
			default:
				return EMPTY_STRING;
			}
		}
		return EMPTY_STRING;
	}
}
