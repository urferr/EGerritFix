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
import org.eclipse.egerrit.core.rest.RelatedChangeAndCommitInfo;

/**
 * This class implements the File table UI label provider.
 *
 * @since 1.0
 */
public class RelatedChangesTableLabelProvider extends BaseTableLabelProvider {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	public RelatedChangesTableLabelProvider(IObservableMap[] iObservableMaps) {
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
		if (aObj instanceof RelatedChangeAndCommitInfo) {
			RelatedChangeAndCommitInfo relatedChangesAndCommitInfo = (RelatedChangeAndCommitInfo) aObj;
			switch (aIndex) {
			case 0:
				return relatedChangesAndCommitInfo.getChange_id();
			case 1:
				return relatedChangesAndCommitInfo.getCommit().getSubject();
			case 2:
				return EMPTY_STRING;
			default:
				return EMPTY_STRING;
			}
		}
		return EMPTY_STRING;
	}

}
