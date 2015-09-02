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

package org.eclipse.egerrit.core.rest;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((confirmed == null) ? 0 : confirmed.hashCode());
		result = prime * result + ((reviewer == null) ? 0 : reviewer.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		AddReviewerInput other = (AddReviewerInput) obj;
		if (confirmed == null) {
			if (other.confirmed != null) {
				return false;
			}
		} else if (!confirmed.equals(other.confirmed)) {
			return false;
		}
		if (reviewer == null) {
			if (other.reviewer != null) {
				return false;
			}
		} else if (!reviewer.equals(other.reviewer)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "ReviewerInput [reviewer=" + reviewer + ", confirmed=" + confirmed + "]";
	}

}