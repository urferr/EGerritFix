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

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;
import java.util.Map;

/**
 * The <a href=
 * "http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#relatedchanges-info"
 * >RelatedChangesInfo</a> entity contains information related to a change.
 * <p>
 * This structure is filled by GSON when parsing the corresponding JSON
 * structure in an HTTP response.
 *
 * @since 1.0
 * @author Guy Perron
 */
public class RelatedChangesInfo {

	// ------------------------------------------------------------------------
	// The data structure
	// ------------------------------------------------------------------------
	// used to fire events of registered properties
	private transient PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(
			this);

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
	 * @param changes the changes to set
	 */
	public void setChanges(List<RelatedChangeAndCommitInfo> changes) {
		firePropertyChange("changes", this.changes, this.changes = changes);
	}

	public void addPropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(propertyName,
				listener);
	}

	protected void firePropertyChange(String propertyName, Object oldValue,
			Object newValue) {
		propertyChangeSupport.firePropertyChange(propertyName, oldValue,
				newValue);
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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RelatedChangesInfo other = (RelatedChangesInfo) obj;
		if (changes == null) {
			if (other.changes != null)
				return false;
		} else if (!changes.equals(other.changes))
			return false;
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
