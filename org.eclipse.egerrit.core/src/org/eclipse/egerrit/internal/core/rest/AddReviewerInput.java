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

/**
 * 'POST /changes/{change-id}/reviewers' Adds one user or all members of one group as reviewer to the change. The
 * reviewer to be added to the change must be provided in the request body as a ReviewerInput entity.
 *
 * @since 1.0
 */
public class AddReviewerInput {

	// ------------------------------------------------------------------------
	// The data structure
	// ------------------------------------------------------------------------
	private String reviewer;

	private Boolean confirmed;

	/**
	 * @return the reviewer
	 */
	public String getReviewer() {
		return reviewer;
	}

	/**
	 * @param reviewer
	 *            the reviewer to set
	 */
	public void setReviewer(String reviewer) {
		this.reviewer = reviewer;
	}

	/**
	 * @return the confirmed
	 */
	public Boolean getConfirmed() {
		return confirmed;
	}

	/**
	 * @param confirmed
	 *            the confirmed to set
	 */
	public void setConfirmed(Boolean confirmed) {
		this.confirmed = confirmed;
	}
}