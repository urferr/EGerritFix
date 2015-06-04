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

/**
 * The <a href= "http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#action-info" >ActionInfo</a>
 * entity describes a REST API call the client can make to manipulate a resource.
 * <p>
 * This structure is filled by GSON when parsing the corresponding JSON structure in an HTTP response.
 *
 * @since 1.0
 * @author Guy Perron
 */
public class MergeableInfo {

	// ------------------------------------------------------------------------
	// The data structure
	// ------------------------------------------------------------------------
	// used to fire events of registered properties
	private transient PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	// Submit type used for this change, can be MERGE_IF_NECESSARY, 
	// FAST_FORWARD_ONLY, REBASE_IF_NECESSARY, MERGE_ALWAYS or CHERRY_PICK.
	private String submit_type;

	// A list of other branch names where this change could merge cleanly
	private String mergeable_into;

	// true if this change is cleanly mergeable, false otherwise
	private boolean mergeable = false;

	// ------------------------------------------------------------------------
	// The getters
	// ------------------------------------------------------------------------

	/**
	 * @return the submit_type
	 */
	public String getSubmit_type() {
		return submit_type;
	}

	/**
	 * @param submit_type
	 *            the submit_type to set
	 */
	public void setSubmit_type(String submit_type) {
		firePropertyChange("submit_type", this.submit_type, this.submit_type = submit_type);

	}

	/**
	 * @return the mergeable_into
	 */
	public String getMergeable_into() {
		return mergeable_into;
	}

	/**
	 * @param mergeable_into
	 *            the mergeable_into to set
	 */
	public void setMergeable_into(String mergeable_into) {
		firePropertyChange("mergeable_into", this.mergeable_into, this.mergeable_into = mergeable_into);
	}

	/**
	 * @return the mergeable
	 */
	public boolean isMergeable() {
		return mergeable;
	}

	/**
	 * @param mergeable
	 *            the mergeable to set
	 */
	public void setMergeable(boolean mergeable) {
		firePropertyChange("mergeable", this.mergeable, this.mergeable = mergeable);
	}

	// ------------------------------------------------------------------------
	// Object
	// ------------------------------------------------------------------------
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
	}

	protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mergeable_into == null) ? 0 : mergeable_into.hashCode());
		result = prime * result + (mergeable ? 1231 : 1237);
		result = prime * result + ((submit_type == null) ? 0 : submit_type.hashCode());
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
		MergeableInfo other = (MergeableInfo) obj;
		if (mergeable_into == null) {
			if (other.mergeable_into != null)
				return false;
		} else if (!mergeable_into.equals(other.mergeable_into))
			return false;
		if (mergeable != other.mergeable)
			return false;
		if (submit_type == null) {
			if (other.submit_type != null)
				return false;
		} else if (!submit_type.equals(other.submit_type))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MergeableInfo [submit_type=" + submit_type + ", mergeable_into=" + mergeable_into + ", mergeable="
				+ mergeable + "]";
	}

}
