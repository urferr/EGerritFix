/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson - Initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.core.rest;

/**
 * The <a href= "http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#branch-info" > BranchInfo</a>
 * entity contains information about a change.
 * <p>
 * This structure is filled by GSON when parsing the corresponding JSON structure in an HTTP response.
 */
public class BranchInfo {

	// ------------------------------------------------------------------------
	// The data structure
	// ------------------------------------------------------------------------
	// The ref of the branch
	private String ref;

	//The revision to which the branch points..
	private String revision;

	// Whether the calling user can delete this branch.
	private boolean can_delete;

	/**
	 * @return the ref
	 */
	public String getRef() {
		return ref;
	}

	/**
	 * @param ref
	 *            the ref to set
	 */
	public void setRef(String ref) {
		this.ref = ref;
	}

	/**
	 * @return the revision
	 */
	public String getRevision() {
		return revision;
	}

	/**
	 * @param revision
	 *            the revision to set
	 */
	public void setRevision(String revision) {
		this.revision = revision;
	}

	/**
	 * @return the can_delete
	 */
	public boolean isCan_delete() {
		return can_delete;
	}

	/**
	 * @param can_delete
	 *            the can_delete to set
	 */
	public void setCan_delete(boolean can_delete) {
		this.can_delete = can_delete;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (can_delete ? 1231 : 1237);
		result = prime * result + ((ref == null) ? 0 : ref.hashCode());
		result = prime * result + ((revision == null) ? 0 : revision.hashCode());
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
		BranchInfo other = (BranchInfo) obj;
		if (can_delete != other.can_delete) {
			return false;
		}
		if (ref == null) {
			if (other.ref != null) {
				return false;
			}
		} else if (!ref.equals(other.ref)) {
			return false;
		}
		if (revision == null) {
			if (other.revision != null) {
				return false;
			}
		} else if (!revision.equals(other.revision)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "BranchInfo [ref=" + ref + ", revision=" + revision + ", can_delete=" + can_delete + "]";
	}

}
