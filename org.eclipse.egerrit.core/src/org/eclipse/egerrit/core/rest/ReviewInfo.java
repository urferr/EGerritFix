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

import java.util.Map;

/**
 * The <a href= "http://gerrit-review.googlesource.com/Documentation/rest-api-accounts.html#review-info" >ReviewInfo</a>
 * entity contains information about a review.
 * <p>
 * This structure is filled by GSON when parsing the corresponding JSON structure in an HTTP response.
 */
public class ReviewInfo {

	// ------------------------------------------------------------------------
	// The data structure
	// ------------------------------------------------------------------------

	// The labels of the review as a map that maps the label names to the voting values.
	private Map<String, String> labels;

	/**
	 * @return the labels
	 */
	public Map<String, String> getLabels() {
		return labels;
	}

	/**
	 * @param labels
	 *            the labels to set
	 */
	public void setLabels(Map<String, String> labels) {
		this.labels = labels;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((labels == null) ? 0 : labels.hashCode());
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
		ReviewInfo other = (ReviewInfo) obj;
		if (labels == null) {
			if (other.labels != null) {
				return false;
			}
		} else if (!labels.equals(other.labels)) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		return "ReviewInfo [labels=" + labels + "]";
	}

}