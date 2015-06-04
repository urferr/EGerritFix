/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Francois Chouinard - Initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.core.rest;

/**
 * The
 * <a href= "http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#problem-info" >ProblemInfo</a>
 * entity contains information about an approval from a user for a label on a change. ApprovalInfo has the same fields
 * as AccountInfo with 2 additional fields: value and date.
 * <p>
 * This structure is filled by GSON when parsing the corresponding JSON structure in an HTTP response.
 *
 * @since 1.0
 * @author Francois Chouinard
 */
public class ProblemInfo {

	// ------------------------------------------------------------------------
	// The data structure
	// ------------------------------------------------------------------------

	// Plaintext message describing the problem with the change.
	private String message;

	// The status of fixing the problem (FIXED, FIX_FAILED). Only set if a fix
	// was attempted.
	private String status;

	// If status is set, an additional plaintext message describing the outcome
	// of the fix.
	private String outcome;

	// ------------------------------------------------------------------------
	// The getters
	// ------------------------------------------------------------------------

	/**
	 * @return Plaintext message describing the problem with the change.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @return The status of fixing the problem (FIXED, FIX_FAILED). May be null.
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @return If status is set, an additional plaintext message describing the outcome of the fix.
	 */
	public String getOutcome() {
		return outcome;
	}

	// ------------------------------------------------------------------------
	// Object
	// ------------------------------------------------------------------------

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((outcome == null) ? 0 : outcome.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProblemInfo other = (ProblemInfo) obj;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (outcome == null) {
			if (other.outcome != null)
				return false;
		} else if (!outcome.equals(other.outcome))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	@SuppressWarnings("nls")
	public String toString() {
		return "ProblemInfo [message=" + message + ", status=" + status + ", outcome=" + outcome + "]";
	}

}
