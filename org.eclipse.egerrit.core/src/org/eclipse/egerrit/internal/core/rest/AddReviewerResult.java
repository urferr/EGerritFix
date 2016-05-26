/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Jacques Bouthillier - Initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.internal.core.rest;

import java.util.List;

import org.eclipse.egerrit.internal.model.ReviewerInfo;

/**
 * The AddReviewerResult entity describes the result of adding a reviewer to a change.
 * <p>
 * This structure is filled by GSON when parsing the corresponding JSON structure in an HTTP response.
 *
 * @since 1.0
 */
public class AddReviewerResult {

	// ------------------------------------------------------------------------
	// The data structure
	// ------------------------------------------------------------------------
	// Optional
	//The newly added reviewers as a list of ReviewerInfo entities.
	private List<ReviewerInfo> reviewers;

	/**
	 * @return the reviewers
	 */
	public List<ReviewerInfo> getReviewers() {
		return reviewers;
	}

	/**
	 * @param reviewers
	 *            the reviewers to set
	 */
	public void setReviewers(List<ReviewerInfo> reviewers) {
		this.reviewers = reviewers;
	}

	// Optional
	// Error message explaining why the reviewer could not be added.
	// If a group was specified in the input and an error is returned, it means that none of the members were added as reviewer.
	private String error;

	// Whether adding the reviewer requires confirmation.
	// By default, false if not set
	private Boolean confirm = false;

	/**
	 * @return the error
	 */
	public String getError() {
		return error;
	}

	/**
	 * @param error
	 *            the error to set
	 */
	public void setError(String error) {
		this.error = error;
	}

	/**
	 * @return the confirm
	 */
	public boolean getConfirm() {
		return confirm;
	}

	/**
	 * @param confirm
	 *            the confirm to set
	 */
	public void setConfirm(Boolean confirm) {
		this.confirm = confirm;
	}

}