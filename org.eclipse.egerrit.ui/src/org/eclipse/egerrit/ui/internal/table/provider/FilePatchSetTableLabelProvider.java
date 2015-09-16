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

import java.text.SimpleDateFormat;

import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.egerrit.core.rest.RevisionInfo;
import org.eclipse.egerrit.core.utils.Utils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

/**
 * This class implements the File table UI label provider.
 *
 * @since 1.0
 */

public class FilePatchSetTableLabelProvider extends BaseTableLabelProvider {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	// Constant for the column with colors: CR, IC and V
	private static Display fDisplay = Display.getCurrent();

	private static Color GREEN = fDisplay.getSystemColor(SWT.COLOR_GREEN);

	private static Color BLACK = fDisplay.getSystemColor(SWT.COLOR_BLACK);

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	public FilePatchSetTableLabelProvider(IObservableMap[] iObservableMaps) {
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
		if (aObj instanceof RevisionInfo) {
			RevisionInfo revisionInfo = (RevisionInfo) aObj;
			switch (aIndex) {
			case 0:
				return Boolean.toString(revisionInfo.hasDraftComments());
			case 1:
				return Integer.toString(revisionInfo.getNumber());
			case 2:
				if (revisionInfo.getCommit() != null) {
					return revisionInfo.getCommit().getCommit();
				} else {
					return "Commit Id not set";
				}
			case 3:
				if (revisionInfo.getCommit() != null) {
					return Utils.formatDate(revisionInfo.getCommit().getCommitter().getDate(),
							new SimpleDateFormat("MMM dd, yyyy hh:mm a"));
				} else {
					return "CommitInfo empty";
				}
			case 4:
				StringBuilder sb = new StringBuilder();
				if (revisionInfo.getCommit() != null) {
					if (revisionInfo.getCommit().getAuthor() != null) {
						//Use the Author
						sb.append(revisionInfo.getCommit().getAuthor().getName());
					}
					if (!revisionInfo.getCommit()
							.getAuthor()
							.getName()
							.equals(revisionInfo.getCommit().getCommitter().getName())) {
						//Add the committer if different than the Author
						sb.append("/");
						sb.append(revisionInfo.getCommit().getCommitter().getName());
					}
					return sb.toString();

				} else {
					return "CommitInfo Author null";
				}
			default:
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
		if (aElement instanceof RevisionInfo) {
			RevisionInfo revisionInfo = (RevisionInfo) aElement;
			switch (aColumnIndex) {
			case 0:
				if (revisionInfo.hasDraftComments()) {
					return GREEN;
				} else {
					return BLACK;
				}
			}
		}
		return null;
	}
}
