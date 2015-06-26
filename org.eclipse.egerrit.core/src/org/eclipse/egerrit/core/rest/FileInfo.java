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

import org.eclipse.egerrit.core.model.PropertyChangeModel;

/**
 * The <a href= "http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#file-info" >FileInfo</a>
 * entity contains information about a file in a patch set.
 * <p>
 * This structure is filled by GSON when parsing the corresponding JSON structure in an HTTP response.
 *
 * @since 1.0
 * @author Francois Chouinard
 */
public class FileInfo extends PropertyChangeModel {

	// ------------------------------------------------------------------------
	// The data structure
	// ------------------------------------------------------------------------

	// The status of the file (“A”=Added, “D”=Deleted, “R”=Renamed, “C”=Copied,
	// “W”=Rewritten). Not set if the file was Modified (“M”).
	private String status;

	// Whether the file is binary. Not set if false.
	private boolean binary = false;

	// The old file path. Only set if the file was renamed or copied.
	private String old_path;

	// Number of inserted lines. Not set for binary files or if no lines were
	// inserted.
	private int lines_inserted;

	// Number of deleted lines.Not set for binary files or if no lines were
	// deleted.
	private int lines_deleted;

	private RevisionInfo containedIn;

	// ------------------------------------------------------------------------
	// The getters
	// ------------------------------------------------------------------------

	/**
	 * Constructor to fill the data structure
	 *
	 * @param FileInfo
	 *            fileInfo
	 */
	public FileInfo(FileInfo fileInfo) {
		setAllFields(fileInfo);
	}

	/**
	 * Fill the current structure with already defined data
	 *
	 * @param FileInfo
	 *            fileInfo
	 */
	public void setAllFields(FileInfo fileInfo) {
		status = fileInfo.getStatus();
		binary = fileInfo.binary;
		old_path = fileInfo.getold_path();
		lines_inserted = fileInfo.getLinesInserted();
		lines_deleted = fileInfo.getLinesDeleted();
		containedIn = fileInfo.getContainingRevisionInfo();
	}

	/**
	 * @return The status of the file (“A”=Added, “D”=Deleted, “R”=Renamed, “C”=Copied, “W”=Rewritten). Not set if the
	 *         file was Modified (“M”).
	 */
	public String getStatus() {
		if (status == null) {
			return "M";//if not set, the default is "M"; //$NON-NLS-1$
		} else {
			return status;
		}
	}

	/**
	 * @return Whether the file is binary.
	 */
	public boolean isBinary() {
		return binary;
	}

	/**
	 * @return The old file path. Only set if the file was renamed or copied.
	 */
	public String getold_path() {
		return old_path;
	}

	public void setOld_path(String old_path) {
		firePropertyChange("old_path", this.old_path, this.old_path = old_path);
	}

	public void setContainingRevision(RevisionInfo revision) {
		containedIn = revision;
	}

	public RevisionInfo getContainingRevisionInfo() {
		return containedIn;
	}

	/**
	 * @return Number of inserted lines. 0 for binary files.
	 */
	public int getLinesInserted() {
		return lines_inserted;
	}

	/**
	 * @return Number of deleted lines. 0 for binary files.
	 */
	public int getLinesDeleted() {
		return lines_deleted;
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
		result = prime * result + (binary ? 1231 : 1237);
		result = prime * result + lines_deleted;
		result = prime * result + lines_inserted;
		result = prime * result + ((old_path == null) ? 0 : old_path.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((containedIn == null) ? 0 : containedIn.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 *
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
		FileInfo other = (FileInfo) obj;
		if (binary != other.binary) {
			return false;
		}
		if (lines_deleted != other.lines_deleted) {
			return false;
		}
		if (lines_inserted != other.lines_inserted) {
			return false;
		}
		if (old_path == null) {
			if (other.old_path != null) {
				return false;
			}
		} else if (!old_path.equals(other.old_path)) {
			return false;
		}
		if (containedIn == null) {
			if (other.containedIn != null) {
				return false;
			}
		} else if (!containedIn.equals(other.containedIn)) {
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

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	@SuppressWarnings("nls")
	public String toString() {
		return "FileInfo [status=" + status + ", binary=" + binary + ", old_path=" + old_path + ", lines_inserted="
				+ lines_inserted + ", lines_deleted=" + lines_deleted + "]";
	}

}
