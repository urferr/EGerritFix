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
 *     Jacques Bouthillier - Add PropertyChangeSupport handler for each field
 *******************************************************************************/

package org.eclipse.egerrit.core.rest;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.egerrit.core.model.PropertyChangeModel;

/**
 * The <a href= "http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#includedin-info"
 * >IncludedInInfo </a> entity describes a REST API call the client can make to manipulate a resource.
 * <p>
 * TThe IncludedInInfo entity contains information about the branches a change was merged into and tags it was tagged
 * with
 *
 * @since 1.0
 * @author Guy Perron
 */
public class IncludedInInfo extends PropertyChangeModel {

	// ------------------------------------------------------------------------
	// The data structure
	// ------------------------------------------------------------------------

	// The list of branches this change was merged into. Each branch is listed
	// without the refs/head/ prefix.
	private List<String> branches;

	// The list of tags this change was tagged with. Each tag is listed without
	// the refs/tags/ prefix.
	private List<String> tags;

	// ------------------------------------------------------------------------
	// The getters
	// ------------------------------------------------------------------------
	/**
	 * @return the branches
	 */
	public List<String> getBranches() {
		if (branches == null) {
			branches = new ArrayList<String>();
		}
		return branches;
	}

	/**
	 * @param branches
	 *            the branches to set
	 */
	public void setBranches(List<String> branches) {
		firePropertyChange("branches", this.branches, this.branches = branches);
	}

	/**
	 * @return the tags
	 */
	public List<String> getTags() {
		if (tags == null) {
			tags = new ArrayList<String>();
		}
		return tags;
	}

	/**
	 * @param tags
	 *            the tags to set
	 */
	public void setTags(List<String> tags) {
		firePropertyChange("tags", this.tags, this.tags = tags);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((branches == null) ? 0 : branches.hashCode());
		result = prime * result + ((tags == null) ? 0 : tags.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
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
		IncludedInInfo other = (IncludedInInfo) obj;
		if (branches == null) {
			if (other.branches != null) {
				return false;
			}
		} else if (!branches.equals(other.branches)) {
			return false;
		}
		if (tags == null) {
			if (other.tags != null) {
				return false;
			}
		} else if (!tags.equals(other.tags)) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "IncludedInInfo [branches=" + branches + ", tags=" + tags + "]";
	}

	// ------------------------------------------------------------------------
	// Object
	// ------------------------------------------------------------------------

}
