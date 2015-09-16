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

import java.sql.Timestamp;
import java.util.Iterator;

import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.egerrit.core.rest.CommentInfo;
import org.eclipse.egerrit.core.utils.DisplayFileInfo;

/**
 * This class implements the File table UI label provider.
 *
 * @since 1.0
 */

public class FileTableLabelProvider extends BaseTableLabelProvider {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	private static final String NEW = "new :";

	private static final String COMMENTS = "comments : ";

	private static final String DRAFTS = "drafts : ";

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	public FileTableLabelProvider(IObservableMap[] iObservableMaps) {
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
		// GerritPlugin.Ftracer.traceWarning("getColumnText object: " + aObj
		// + "\tcolumn: " + aIndex);
		if (aObj instanceof DisplayFileInfo) {
			DisplayFileInfo fileInfo = (DisplayFileInfo) aObj;
			switch (aIndex) {
			case 0:
				return EMPTY_STRING;
			case 1:
				return fileInfo.getStatus();
			case 2:
				return fileInfo.getold_path();
			case 3:
				String ret = EMPTY_STRING;
				if (fileInfo.getDraftComments() != null) {
					ret = DRAFTS + fileInfo.getDraftComments().size() + " ";
				}
				if (fileInfo.getNewComments() != null) {
					int newCommentCount = 0;
					Iterator<CommentInfo> iterator = fileInfo.getNewComments().iterator();
					String currentUpdate = EMPTY_STRING;
					String currentUser = fileInfo.getCurrentUser();
					while (iterator.hasNext()) {
						CommentInfo aComment = iterator.next();
						if (aComment.getAuthor().getUsername().compareTo(currentUser) == 0) {
							if (currentUpdate.isEmpty()) {
								currentUpdate = aComment.getUpdated();
								continue;
							}
							if (Timestamp.valueOf(aComment.getUpdated()).after(Timestamp.valueOf(currentUpdate))) {
								currentUpdate = aComment.getUpdated();
							}
						}
					}

					if (!currentUpdate.isEmpty()) {
						Iterator<CommentInfo> iterator2 = fileInfo.getNewComments().iterator();
						while (iterator2.hasNext()) {
							CommentInfo aComment2 = iterator2.next();
							if ((Timestamp.valueOf(aComment2.getUpdated()).after(Timestamp.valueOf(currentUpdate)))) {
								newCommentCount++;
							}
						}
					}

					if (ret.isEmpty()) {
						ret = "                     "; //$NON-NLS-1$
					}
					if (fileInfo.getNewComments().size() - newCommentCount != 0) {
						ret = ret + COMMENTS + (fileInfo.getNewComments().size() - newCommentCount) + " "; //$NON-NLS-1$
					}
					if (newCommentCount != 0) {
						ret = ret + NEW + newCommentCount;
					}
				}
				return ret;

			case 4:
				StringBuilder sb = new StringBuilder();
				sb.append('+');
				sb.append(Integer.toString(fileInfo.getLinesInserted()));
				sb.append('/');
				sb.append('-');
				sb.append(Integer.toString(fileInfo.getLinesDeleted()));
				return sb.toString();
			default:
				return EMPTY_STRING;
			}
		}
		return EMPTY_STRING;
	}
}
