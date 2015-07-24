/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Guy Perron - Initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.core.rest;

/**
 * The <a href= "http://gerrit-review.googlesource.com/Documentation/rest-api-accounts.html#submit-info" >SubmitInfo</a>
 * entity contains information about a review.
 * <p>
 * This structure is filled by GSON when parsing the corresponding JSON structure in an HTTP response. The SubmitInfo
 * entity contains information about the change status after submitting.
 */
public class SubmitInfo {

	// ------------------------------------------------------------------------
	// The data structure
	// ------------------------------------------------------------------------

	//The status of the change after submitting, can be MERGED or SUBMITTED.
	// If wait_for_merge in the SubmitInput was set to false the returned status is SUBMITTED and the caller can’t know
	// whether the change could be merged successfully.
	private String status;

	// optional
	private String on_behalf_of;

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @return the on_behalf_of
	 */
	public String getOn_behalf_of() {
		return on_behalf_of;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((on_behalf_of == null) ? 0 : on_behalf_of.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		SubmitInfo other = (SubmitInfo) obj;
		if (on_behalf_of == null) {
			if (other.on_behalf_of != null) {
				return false;
			}
		} else if (!on_behalf_of.equals(other.on_behalf_of)) {
			return false;
		}
		if (status == null) {
			if (other.status != null) {
				return false;
			}
		} else if (!status.equals(other.status)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "SubmitInfo [status=" + status + ", on_behalf_of=" + on_behalf_of + "]";
	}

}