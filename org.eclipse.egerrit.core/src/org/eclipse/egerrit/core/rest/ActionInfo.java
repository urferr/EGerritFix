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
 * The <a href= "http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#action-info" >ActionInfo</a>
 * entity describes a REST API call the client can make to manipulate a resource.
 * <p>
 * This structure is filled by GSON when parsing the corresponding JSON structure in an HTTP response.
 *
 * @since 1.0
 * @author Francois Chouinard
 */
public class ActionInfo {

	// ------------------------------------------------------------------------
	// The data structure
	// ------------------------------------------------------------------------

	// HTTP method to use with the action. Most actions use POST, PUT or DELETE
	// to cause state changes.
	private String method;

	// Short title to display to a user describing the action. In the Gerrit
	// web interface the label is used as the text on the button presented in
	// the UI. Optional.
	private String label;

	// Longer text to display describing the action. In a web UI this should be
	// the title attribute of the element, displaying when the user hovers the
	// mouse. Optional
	private String title;

	// If true the action is permitted at this time and the caller is likely
	// allowed to execute it. This may change if state is updated at the server
	// or permissions are modified. Not present if false.
	private boolean enabled = false;

	// ------------------------------------------------------------------------
	// The getters
	// ------------------------------------------------------------------------

	/**
	 * @return The HTTP method to use with the action.
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * @return The short title describing the action. May be null.
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @return The longer text describing the action. May be null.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return Whether the action is permitted.
	 */
	public boolean isEnabled() {
		return enabled;
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
		result = prime * result + (enabled ? 1231 : 1237);
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result + ((method == null) ? 0 : method.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		ActionInfo other = (ActionInfo) obj;
		if (enabled != other.enabled)
			return false;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (method == null) {
			if (other.method != null)
				return false;
		} else if (!method.equals(other.method))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	@SuppressWarnings("nls")
	public String toString() {
		return "ActionInfo [method=" + method + ", label=" + label + ", title=" + title + ", enabled=" + enabled + "]";
	}

}
