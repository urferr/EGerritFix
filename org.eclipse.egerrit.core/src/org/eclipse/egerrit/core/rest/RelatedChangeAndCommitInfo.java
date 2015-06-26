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
 * The <a href= "http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#relatedchangeandcommit-info"
 * > RelatedChangeAndCommitInfo</a> entity contains information about a change.
 * <p>
 * This structure is filled by GSON when parsing the corresponding JSON structure in an HTTP response.
 *
 * @since 1.0
 * @author Guy Perron
 */
public class RelatedChangeAndCommitInfo extends PropertyChangeModel {

	// ------------------------------------------------------------------------
	// The data structure
	// ------------------------------------------------------------------------
	// The Change-Id of the change.
	// e.g. "I4982a3771051891899528a94fb47baeeb70582ae"
	private String change_id;

	// The commit as a CommitInfo entity.
	private CommitInfo commit;

	//The change number.
	private String _change_number;

	//The revision number.
	private String _revision_number;

	// The current revision number.
	private String _current_revision_number;

	// ------------------------------------------------------------------------
	// The getters
	// ------------------------------------------------------------------------

	/**
	 * @return the change_id
	 */
	public String getChange_id() {
		return change_id;
	}

	/**
	 * @param change_id
	 *            the change_id to set
	 */
	public void setChange_id(String change_id) {
		firePropertyChange("change_id", this.change_id, this.change_id = change_id);
	}

	/**
	 * @return the commit
	 */
	public CommitInfo getCommit() {
		return commit;
	}

	/**
	 * @param commit
	 *            the commit to set
	 */
	public void setCommit(CommitInfo commit) {
		firePropertyChange("commit", this.commit, this.commit = commit);
	}

	/**
	 * @return the _change_number
	 */
	public String get_change_number() {
		return _change_number;
	}

	/**
	 * @param _change_number
	 *            the _change_number to set
	 */
	public void set_change_number(String _change_number) {
		firePropertyChange("_change_number", this._change_number, this._change_number = _change_number);
	}

	/**
	 * @return the _revision_number
	 */
	public String get_revision_number() {
		return _revision_number;
	}

	/**
	 * @param _revision_number
	 *            the _revision_number to set
	 */
	public void set_revision_number(String _revision_number) {
		firePropertyChange("_revision_number", this._revision_number, this._revision_number = _revision_number);
	}

	/**
	 * @return the _current_revision_number
	 */
	public String get_current_revision_number() {
		return _current_revision_number;
	}

	/**
	 * @param _current_revision_number
	 *            the _current_revision_number to set
	 */
	public void set_current_revision_number(String _current_revision_number) {
		firePropertyChange("_current_revision_number", this._current_revision_number,
				this._current_revision_number = _current_revision_number);
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
		result = prime * result + ((_change_number == null) ? 0 : _change_number.hashCode());
		result = prime * result + ((_revision_number == null) ? 0 : _revision_number.hashCode());
		result = prime * result + ((change_id == null) ? 0 : change_id.hashCode());
		result = prime * result + ((commit == null) ? 0 : commit.hashCode());
		result = prime * result + ((_current_revision_number == null) ? 0 : _current_revision_number.hashCode());
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
		RelatedChangeAndCommitInfo other = (RelatedChangeAndCommitInfo) obj;
		if (_change_number == null) {
			if (other._change_number != null) {
				return false;
			}
		} else if (!_change_number.equals(other._change_number)) {
			return false;
		}
		if (_revision_number == null) {
			if (other._revision_number != null) {
				return false;
			}
		} else if (!_revision_number.equals(other._revision_number)) {
			return false;
		}
		if (change_id == null) {
			if (other.change_id != null) {
				return false;
			}
		} else if (!change_id.equals(other.change_id)) {
			return false;
		}
		if (commit == null) {
			if (other.commit != null) {
				return false;
			}
		} else if (!commit.equals(other.commit)) {
			return false;
		}
		if (_current_revision_number == null) {
			if (other._current_revision_number != null) {
				return false;
			}
		} else if (!_current_revision_number.equals(other._current_revision_number)) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RelatedChangeAndCommitInfo [change_id=" + change_id + ", commit=" + commit + ", _change_number="
				+ _change_number + ", _revision_number=" + _revision_number + ", _current_revision_number="
				+ _current_revision_number + "]";
	}

}
