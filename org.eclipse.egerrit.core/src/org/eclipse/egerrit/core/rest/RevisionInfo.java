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
import java.util.Map;

/**
 * The
 * <a href= "http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#revision-info" >RevisionInfo</a>
 * entity contains information about a patch set. Not all fields are returned by default. Additional fields are obtained
 * by adding o parameters to the Query Changes.
 * <p>
 * This structure is filled by GSON when parsing the corresponding JSON structure in an HTTP response.
 *
 * @since 1.0
 * @author Francois Chouinard
 */
public class RevisionInfo {

	// ------------------------------------------------------------------------
	// The data structure
	// ------------------------------------------------------------------------

	// used to fire events of registered properties
	private transient PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	// Whether the patch set is a draft.
	private boolean draft = false;

	// Whether the patch set has one or more draft comments by the calling user.
	// Only set if DRAFT_COMMENTS option is requested.
	private boolean has_draft_comments = false;

	// The patch set number.
	private int _number;

	// The Git reference for the patch set.
	private String ref;

	// Information about how to fetch this patch set. The fetch information is
	// provided as a map that maps the protocol name (“git”, “http”, “ssh”) to
	// FetchInfo entities. This information is only included if a plugin
	// implementing the download commands interface is installed.
	private Map<String, FetchInfo> fetch;

	// The commit of the patch set.
	private CommitInfo commit;

	// The files of the patch set as a map that maps the file names to FileInfo
	// entities. Only set if CURRENT_FILES or ALL_FILES option is requested.
	private Map<String, FileInfo> files;

	// Actions the caller might be able to perform on this revision.
	// The information is a map of view name to ActionInfo entities.
	public Map<String, ActionInfo> actions;

	// Indicates whether the caller is authenticated and has commented on the
	// current revision. Only set if REVIEWED option is requested.
	private Boolean reviewed;

	// ------------------------------------------------------------------------
	// The getters
	// ------------------------------------------------------------------------

	/**
	 * @return Whether the patch set is a draft.
	 */
	public boolean isDraft() {
		return draft;
	}

	/**
	 * @return Whether the patch set has one or more draft comments by the calling user. Only set if DRAFT_COMMENTS
	 *         option was requested.
	 */
	public boolean hasDraftComments() {
		return has_draft_comments;
	}

	/**
	 * @return The patch set number.
	 */
	public int getNumber() {
		return _number;
	}

	/**
	 * @return The Git reference for the patch set.
	 */
	public String getRef() {
		return ref;
	}

	/**
	 * @return Information about how to fetch this patch set. The fetch information is provided as a map that maps the
	 *         protocol name (“git”, “http”, “ssh”) to FetchInfo entities. This information is only included if a plugin
	 *         implementing the download commands interface is installed.
	 */
	public Map<String, FetchInfo> getFetch() {
		return fetch;
	}

	/**
	 * @return The commit of the patch set. May be null.
	 */
	public CommitInfo getCommit() {
		return commit;
	}

	/**
	 * @return The files of the patch set as a map that maps the file names to FileInfo entities. Only set if
	 *         CURRENT_FILES or ALL_FILES option was requested.
	 */
	public Map<String, FileInfo> getFiles() {
		return files;
	}

	public void setFiles(Map<String, FileInfo> files) {
		firePropertyChange("files", this.files, this.files = files);
	}

	/**
	 * @return Actions the caller might be able to perform on this revision. The information is a map of view name to
	 *         ActionInfo entities. May be null.
	 */
	public Map<String, ActionInfo> getActions() {
		return actions;
	}

	/**
	 * @return Indicates whether the caller is authenticated and has commented on the current revision. Only set if
	 *         REVIEWED option is requested. May be null.
	 */
	public Boolean getReviewed() {
		return reviewed;
	}

	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
	}

	protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
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
		result = prime * result + _number;
		result = prime * result + ((actions == null) ? 0 : actions.hashCode());
		result = prime * result + ((commit == null) ? 0 : commit.hashCode());
		result = prime * result + (draft ? 1231 : 1237);
		result = prime * result + ((fetch == null) ? 0 : fetch.hashCode());
		result = prime * result + ((files == null) ? 0 : files.hashCode());
		result = prime * result + (has_draft_comments ? 1231 : 1237);
		result = prime * result + ((ref == null) ? 0 : ref.hashCode());
		result = prime * result + ((reviewed == null) ? 0 : reviewed.hashCode());
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
		RevisionInfo other = (RevisionInfo) obj;
		if (_number != other._number)
			return false;
		if (actions == null) {
			if (other.actions != null)
				return false;
		} else if (!actions.equals(other.actions))
			return false;
		if (commit == null) {
			if (other.commit != null)
				return false;
		} else if (!commit.equals(other.commit))
			return false;
		if (draft != other.draft)
			return false;
		if (fetch == null) {
			if (other.fetch != null)
				return false;
		} else if (!fetch.equals(other.fetch))
			return false;
		if (files == null) {
			if (other.files != null)
				return false;
		} else if (!files.equals(other.files))
			return false;
		if (has_draft_comments != other.has_draft_comments)
			return false;
		if (ref == null) {
			if (other.ref != null)
				return false;
		} else if (!ref.equals(other.ref))
			return false;
		if (reviewed == null) {
			if (other.reviewed != null)
				return false;
		} else if (!reviewed.equals(other.reviewed))
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
		return "RevisionInfo [draft=" + draft + ", has_draft_comments=" + has_draft_comments + ", _number=" + _number
				+ ", ref=" + ref + ", fetch=" + fetch + ", commit=" + commit + ", files=" + files + ", actions="
				+ actions + ", reviewed=" + reviewed + "]";
	}

}
