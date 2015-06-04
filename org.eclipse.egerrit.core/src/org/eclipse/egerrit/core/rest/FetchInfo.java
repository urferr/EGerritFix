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

import java.util.Map;

/**
 * The <a href= "http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#fetch-info" >FetchInfo</a>
 * entity contains information about how to fetch a patch set via a certain protocol.
 * <p>
 * This structure is filled by GSON when parsing the corresponding JSON structure in an HTTP response.
 *
 * @since 1.0
 * @author Francois Chouinard
 */
public class FetchInfo {

	// ------------------------------------------------------------------------
	// The data structure
	// ------------------------------------------------------------------------

	// The URL of the project.
	private String url;

	// The ref of the patch set.
	private String ref;

	// The download commands for this patch set as a map that maps the command
	// names to the commands. Only set if download commands are requested.
	private Map<String, String> commands;

	// ------------------------------------------------------------------------
	// The getters
	// ------------------------------------------------------------------------

	/**
	 * @return The URL of the project.
	 */
	public String getURL() {
		return url;
	}

	/**
	 * @return The ref of the patch set.
	 */
	public String getRef() {
		return ref;
	}

	/**
	 * @return The download commands for this patch set as a map that maps the command names to the commands. Only set
	 *         if download commands are requested.
	 */
	public Map<String, String> getCommands() {
		return commands;
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
		result = prime * result + ((commands == null) ? 0 : commands.hashCode());
		result = prime * result + ((ref == null) ? 0 : ref.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
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
		FetchInfo other = (FetchInfo) obj;
		if (commands == null) {
			if (other.commands != null)
				return false;
		} else if (!commands.equals(other.commands))
			return false;
		if (ref == null) {
			if (other.ref != null)
				return false;
		} else if (!ref.equals(other.ref))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	@SuppressWarnings("nls")
	public String toString() {
		return "FetchInfo [url=" + url + ", ref=" + ref + ", commands=" + commands + "]";
	}

}
