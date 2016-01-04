/*******************************************************************************
 * Copyright (c) 2013, 2014 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jacques Bouthillier - Initial Implementation of the history label provider
 ******************************************************************************/

package org.eclipse.egerrit.ui.internal.table.provider;

import java.text.SimpleDateFormat;

import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.egerrit.core.utils.Utils;
import org.eclipse.egerrit.internal.model.ChangeMessageInfo;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;

/**
 * This class implements the History table UI label provider.
 *
 * @since 1.0
 */
public class HistoryTableLabelProvider extends ObservableMapLabelProvider implements ITableLabelProvider {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	private final String EMPTY_STRING = ""; //$NON-NLS-1$

	private final SimpleDateFormat formatTimeOut = new SimpleDateFormat("yyyy MMM dd hh:mm a"); //$NON-NLS-1$

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
	public HistoryTableLabelProvider(IObservableMap[] iObservableMaps) {
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
		if (aObj instanceof ChangeMessageInfo) {
			ChangeMessageInfo changeMessageInfo = (ChangeMessageInfo) aObj;
			switch (aIndex) {
			case 0:
				return Utils.formatDate(changeMessageInfo.getDate(), formatTimeOut);
			case 1:
				if (changeMessageInfo.getAuthor() != null) {
					return changeMessageInfo.getAuthor().getName();
				}
				break;
			case 2:
				return changeMessageInfo.getMessage().replaceAll("[\\t\\n\\r]", " "); //$NON-NLS-1$//$NON-NLS-2$
			default:
				return EMPTY_STRING;
			}
		}
		return EMPTY_STRING;
	}
}
