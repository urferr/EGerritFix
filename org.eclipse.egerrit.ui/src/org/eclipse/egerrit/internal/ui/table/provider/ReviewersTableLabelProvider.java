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
import org.eclipse.egerrit.internal.ui.utils.UIUtils;

/**
 * This class implements the File table UI label provider.
 *
 * @since 1.0
 */
public class ReviewersTableLabelProvider extends BaseTableLabelProvider {

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	public ReviewersTableLabelProvider(IObservableMap[] iObservableMaps) {
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
		if (aObj instanceof ReviewerInfo) {
			ReviewerInfo reviewerInfo = (ReviewerInfo) aObj;
			switch (aIndex) {
			case 0:
				if (UIUtils.isRemoveable(reviewerInfo)) {
					return "x"; //$NON-NLS-1$
				}
				return ""; //$NON-NLS-1$
			case 1:
				return super.getColumnText(aObj, aIndex);
			case 2:
				return super.getColumnText(aObj, aIndex);
			case 3:
				if (reviewerInfo.getApprovals() != null && reviewerInfo.getApprovals().containsKey("Code-Review")) { //$NON-NLS-1$
					return reviewerInfo.getApprovals().get("Code-Review"); //$NON-NLS-1$
				}
			case 4:
				if (reviewerInfo.getApprovals() != null && reviewerInfo.getApprovals().containsKey("Verified")) { //$NON-NLS-1$
					return reviewerInfo.getApprovals().get("Verified"); //$NON-NLS-1$
				}
			default:
				return EMPTY_STRING;
			}
		}
		return EMPTY_STRING;
	}
}
