/*******************************************************************************
 * Copyright (c) 2015 Ericsson AB.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Jacques Bouthillier - initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.core.utils;

import java.util.ArrayList;

import org.eclipse.egerrit.core.rest.CommentInfo;
import org.eclipse.egerrit.core.rest.FileInfo;

/**
 * This class is used to gather data from different request and put it together to fill the File table
 *
 * @since 1.0
 */
public class DisplayFileInfo extends FileInfo {

	private ArrayList<CommentInfo> comments;

	private Boolean reviewed = false;

	/**
	 * Constructor with some data already available
	 *
	 * @param FileInfo
	 *            fileInfo
	 */
	public DisplayFileInfo(FileInfo fileInfo) {
		super(fileInfo);
	}

	/**
	 * This method return the list of commentsArrayList<CommentInfo>
	 *
	 * @return ArrayList<CommentInfo>
	 */
	public ArrayList<CommentInfo> getComments() {
		return comments;
	}

	/**
	 * This method receives a list of comments and trigger a property change on it
	 *
	 * @param ArrayList
	 *            <CommentInfo>arrayList
	 */
	public void setComments(ArrayList<CommentInfo> arrayList) {
		this.comments = arrayList;
		firePropertyChange("comments", this.comments, this.comments = arrayList); //$NON-NLS-1$
	}

	/**
	 * This method verify the state of a review file
	 *
	 * @return Boolean
	 */
	public Boolean getReviewed() {
		return reviewed;
	}

	/**
	 * This method receives a flag for the review state of a file and trigger a property change on it.
	 * 
	 * @param reviewed
	 */
	public void setReviewed(Boolean reviewed) {
		this.reviewed = reviewed;
		firePropertyChange("reviewed", this.reviewed, this.reviewed = reviewed); //$NON-NLS-1$
	}

}
