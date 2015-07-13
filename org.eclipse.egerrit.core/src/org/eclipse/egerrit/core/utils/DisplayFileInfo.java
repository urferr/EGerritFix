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

	private ArrayList<CommentInfo> newComments;

	private ArrayList<CommentInfo> draftComments;

	private String currentUser;

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
	 * This method return the list of newComments commentsArrayList<CommentInfo>
	 *
	 * @return ArrayList<CommentInfo>
	 */
	public ArrayList<CommentInfo> getNewComments() {
		return newComments;
	}

	/**
	 * This method return the list of draftComments commentsArrayList<CommentInfo>
	 *
	 * @return ArrayList<CommentInfo>
	 */
	public ArrayList<CommentInfo> getDraftComments() {
		return draftComments;
	}

	/**
	 * This method receives a list of comments and trigger a property change on it
	 *
	 * @param ArrayList
	 *            <CommentInfo>arrayList
	 */
	public void setNewComments(ArrayList<CommentInfo> arrayList) {
		this.newComments = arrayList;
		firePropertyChange("newComments", this.newComments, this.newComments = arrayList); //$NON-NLS-1$
	}

	/**
	 * This method receives a list of draft comments and triggers a property change on it
	 *
	 * @param ArrayList
	 *            <CommentInfo>arrayList
	 */
	public void setDraftComments(ArrayList<CommentInfo> arrayList) {
		this.draftComments = arrayList;
		firePropertyChange("draftComments", this.draftComments, this.draftComments = arrayList); //$NON-NLS-1$
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

	/**
	 * @return the currentUser
	 */
	public String getCurrentUser() {
		return currentUser;
	}

	/**
	 * @param currentUser
	 *            the currentUser to set
	 */
	public void setCurrentUser(String currentUser) {
		this.currentUser = currentUser;
	}
}
