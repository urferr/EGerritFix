/*******************************************************************************
 * Copyright (c) 2017 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson - initial API and implementation
 ******************************************************************************/

package org.eclipse.egerrit.internal.ui.table.provider;

import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.egerrit.internal.model.RelatedChangeAndCommitInfo;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * This class implements the Related Changes table UI label provider.
 *
 * @since 1.0
 */
public class RelatedChangesTableLabelProvider extends LabelProvider implements ITableLabelProvider {

	/**
	 * Empty string.
	 */
	protected static final String EMPTY_STRING = ""; //$NON-NLS-1$
	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	public RelatedChangesTableLabelProvider(IObservableMap[] iObservableMaps) {
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
		if (aObj instanceof RelatedChangeAndCommitInfo) {
			RelatedChangeAndCommitInfo relatedChangeInfo = (RelatedChangeAndCommitInfo) aObj;
			switch (aIndex) {
			case 0:
				return relatedChangeInfo.get_change_number();
			case 1:
				return relatedChangeInfo.getCommit().getSubject();
			case 2:
				return relatedChangeInfo.getStatus();
			default:
				return EMPTY_STRING;
			}
		}
		return EMPTY_STRING;
	}

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		// ignore
		return null;
	}

}
