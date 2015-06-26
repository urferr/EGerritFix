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

import java.util.List;

import org.eclipse.egerrit.core.model.PropertyChangeModel;

/**
 * The <a href= "http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#relatedchanges-info" >
 * RelatedChangesInfo</a> entity contains information related to a change.
 * <p>
 * This structure is filled by GSON when parsing the corresponding JSON structure in an HTTP response.
 *
 * @since 1.0
 * @author Guy Perron
 */
public class RelatedChangesInfo extends PropertyChangeModel {

	// ------------------------------------------------------------------------
	// The data structure
	// ------------------------------------------------------------------------
	// A list of RelatedChangeAndCommitInfo entities describing the related changes.
	// Sorted by git commit order, newest to oldest. Empty if there are no related changes
	private List<RelatedChangeAndCommitInfo> changes;

	// ------------------------------------------------------------------------
	// The getters
	// ------------------------------------------------------------------------

	/**
	 * @return the changes
	 */
	public List<RelatedChangeAndCommitInfo> getChanges() {
		return changes;
	}

	/**
	 * @param changes
	 *            the changes to set
	 */
	public void setChanges(List<RelatedChangeAndCommitInfo> changes) {
		firePropertyChange("changes", this.changes, this.changes = changes);
	}

	// ------------------------------------------------------------------------
	// Object
	// ------------------------------------------------------------------------

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((changes == null) ? 0 : changes.hashCode());
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
		RelatedChangesInfo other = (RelatedChangesInfo) obj;
		if (changes == null) {
			if (other.changes != null) {
				return false;
			}
		} else if (!changes.equals(other.changes)) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RelatedChangesInfo [changes=" + changes + "]";
	}

}
