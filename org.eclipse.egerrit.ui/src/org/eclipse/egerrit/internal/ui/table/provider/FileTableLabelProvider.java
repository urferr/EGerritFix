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

import java.sql.Timestamp;
import java.util.Iterator;

import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.egerrit.internal.model.CommentInfo;
import org.eclipse.egerrit.internal.model.FileInfo;
import org.eclipse.egerrit.internal.model.impl.StringToFileInfoImpl;
import org.eclipse.egerrit.internal.ui.EGerritImages;
import org.eclipse.egerrit.internal.ui.utils.Messages;
import org.eclipse.swt.graphics.Image;

/**
 * This class implements the File table UI label provider.
 *
 * @since 1.0
 */

public class FileTableLabelProvider extends BaseTableLabelProvider {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	private static final String NEW = Messages.FileTableLabelProvider_0;

	private static final String COMMENTS = Messages.FileTableLabelProvider_1;

	private static final String DRAFTS = Messages.FileTableLabelProvider_2;

	//Layout selection for the file path. Also as a default value
	private boolean nameFirst = true;

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
		if (aObj instanceof StringToFileInfoImpl) {
			FileInfo fileInfo = ((StringToFileInfoImpl) aObj).getValue();
			switch (aIndex) {
			case 0:
				return EMPTY_STRING;
			case 1:
				return fileInfo.getStatus();
			case 2:
				String path = null;
				if (nameFirst) {
					path = fileInfo.getPath();
					int index = path.lastIndexOf('/');
					if (index != -1) {
						String fileName = path.substring(index + 1);
						String firstName = fileName + " - " + path.substring(0, index); //$NON-NLS-1$
						path = firstName;
					}
				} else {
					path = fileInfo.getPath();
				}
				if (fileInfo.getOld_path() != null) {
					path += Messages.FileTableLabelProvider_3 + fileInfo.getOld_path()
							+ Messages.FileTableLabelProvider_4;
				}
				return path;
			case 3:
				String ret = EMPTY_STRING;
				if (fileInfo.getDraftComments() != null && !fileInfo.getDraftComments().isEmpty()) {
					ret = DRAFTS + fileInfo.getDraftComments().size() + " "; //$NON-NLS-1$
				}
				if (fileInfo.getComments() != null) {
					int newCommentCount = 0;
					Iterator<CommentInfo> iterator = fileInfo.getComments().iterator();
					String currentUpdate = EMPTY_STRING;
					//EMF to fix
					String currentUser = "";//fileInfo.getCurrentUser(); //$NON-NLS-1$
					while (iterator.hasNext()) {
						CommentInfo aComment = iterator.next();
						String authorName = aComment.getAuthor().getUsername();
						if (currentUser != null && authorName != null && currentUser.compareTo(authorName) == 0) {
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
						Iterator<CommentInfo> iterator2 = fileInfo.getComments().iterator();
						while (iterator2.hasNext()) {
							CommentInfo aComment2 = iterator2.next();
							if (Timestamp.valueOf(aComment2.getUpdated()).after(Timestamp.valueOf(currentUpdate))) {
								newCommentCount++;
							}
						}
					}

					if (ret.isEmpty()) {
						ret = "                     "; //$NON-NLS-1$
					}
					if (fileInfo.getComments().size() - newCommentCount != 0) {
						ret = ret + COMMENTS + (fileInfo.getComments().size() - newCommentCount) + " "; //$NON-NLS-1$
					}
					if (newCommentCount != 0) {
						ret = ret + NEW + newCommentCount;
					}
				}
				return ret;

			case 4:
				StringBuilder sb = new StringBuilder();
				sb.append('+');
				sb.append(Integer.toString(fileInfo.getLines_inserted()));
				sb.append('/');
				sb.append('-');
				sb.append(Integer.toString(fileInfo.getLines_deleted()));
				return sb.toString();
			default:
				return EMPTY_STRING;
			}
		}
		return EMPTY_STRING;
	}

	/**
	 * Return an image representing the reviewed state of the object
	 *
	 * @param int
	 *            aState
	 * @return Image
	 */
	private Image getReviewedStateImage(boolean aState) {
		if (aState) {
			return EGerritImages.get(EGerritImages.CHECKED_IMAGE);
		} else {
			return null;
		}
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
		Image image = null;
		String value = null;
		if (aObj instanceof StringToFileInfoImpl) {
			switch (aIndex) {
			case 0:
				Boolean isReviewed = ((StringToFileInfoImpl) aObj).getValue().isReviewed();
				value = isReviewed.toString(); // Needed for the sorter
				if (null != value && !value.equals(EMPTY_STRING)) {
					return getReviewedStateImage(Boolean.valueOf(value.toLowerCase()));
				}
				break;
			case 1:
			case 2:
			case 3:
			case 4:
			default:
				return image;
			}
		}

		return image;
	}

	/**
	 * Adjust the format of the FilePath
	 *
	 * @param b
	 */
	public void setFileNameFirst(boolean b) {
		nameFirst = b;
	}

	/**
	 * Get the file name order
	 *
	 * @return
	 */
	public boolean getFileOrder() {
		return nameFirst;
	}
}
