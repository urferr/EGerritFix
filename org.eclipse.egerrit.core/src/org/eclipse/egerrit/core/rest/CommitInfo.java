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

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

/**
 * The <a href= "http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#commit-info" >CommitInfo</a>
 * entity contains information about the commit.
 * <p>
 * This structure is filled by GSON when parsing the corresponding JSON structure in an HTTP response.
 *
 * @since 1.0
 * @author Francois Chouinard
 */
public class CommitInfo {

	// ------------------------------------------------------------------------
	// The data structure
	// ------------------------------------------------------------------------
	// used to fire events of registered properties
	private transient PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	// The commit ID.
	private String commit;

	// The parent commits of this commit. In each parent only the commit and
	// subject fields are populated.
	private List<CommitInfo> parents;

	// The author of the commit.
	private GitPersonInfo author;

	// The committer of the commit.
	private GitPersonInfo committer;

	// The subject of the commit (header line of the commit message).
	private String subject;

	// The commit message.
	private String message;

	// ------------------------------------------------------------------------
	// The getters
	// ------------------------------------------------------------------------

	/**
	 * @return The commit ID.
	 */
	public String getCommit() {
		return commit;
	}

	/**
	 * @return List. The parent commits of this commit. In each parent only the commit and subject fields are populated.
	 */
	public List<CommitInfo> getParents() {
		return parents;
	}

	/**
	 * @return GitPersonInfo The author of the commit.
	 */
	public GitPersonInfo getAuthor() {
		return author;
	}

	/**
	 * @return GitPersonInfo The committer of the commit.
	 */
	public GitPersonInfo getCommitter() {
		return committer;
	}

	/**
	 * @return String The subject of the commit (header line of the commit message).
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @return String The commit message.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Set the subject field
	 *
	 * @param String
	 *            subject
	 */
	public void setSubject(String subject) {
		firePropertyChange("subject", this.subject, this.subject = subject); //$NON-NLS-1$

	}

	/**
	 * Set the message field
	 *
	 * @param String
	 *            message
	 */
	public void setMessage(String message) {
		firePropertyChange("message", this.message, this.message = message); //$NON-NLS-1$

	}

	/**
	 * Set the commit field
	 *
	 * @param String
	 *            commit
	 */
	public void setCommit(String commit) {
		firePropertyChange("commit", this.commit, this.commit = commit); //$NON-NLS-1$
	}

	/**
	 * Set the list of parent for this commit info
	 *
	 * @param List
	 *            <CommitInfo> parents
	 */
	public void setParents(List<CommitInfo> parents) {
		firePropertyChange("parents", this.parents, this.parents = parents); //$NON-NLS-1$
	}

	/**
	 * Set the author structure
	 *
	 * @param GitPersonInfo
	 *            author
	 */
	public void setAuthor(GitPersonInfo author) {
		firePropertyChange("author", this.author, this.author = author); //$NON-NLS-1$
	}

	/**
	 * Set the committer structure
	 *
	 * @param GitPersonInfo
	 *            committer
	 */
	public void setCommitter(GitPersonInfo committer) {
		firePropertyChange("committer", this.committer, this.committer = committer); //$NON-NLS-1$
	}

	/**
	 * Allow to add a property change listener
	 *
	 * @param String
	 *            propertyName
	 * @param PropertyChangeListener
	 *            listener
	 */
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}

	/**
	 * Allow to remove a property change listener
	 *
	 * @param String
	 *            propertyName
	 * @param PropertyChangeListener
	 *            listener
	 */
	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
	}

	/**
	 * Initiate a property change listener
	 *
	 * @param String
	 *            propertyName
	 * @param Object
	 *            oldValue
	 * @param Object
	 *            newValue
	 */
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
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((commit == null) ? 0 : commit.hashCode());
		result = prime * result + ((committer == null) ? 0 : committer.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((parents == null) ? 0 : parents.hashCode());
		result = prime * result + ((subject == null) ? 0 : subject.hashCode());
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
		CommitInfo other = (CommitInfo) obj;
		if (author == null) {
			if (other.author != null) {
				return false;
			}
		} else if (!author.equals(other.author)) {
			return false;
		}
		if (commit == null) {
			if (other.commit != null) {
				return false;
			}
		} else if (!commit.equals(other.commit)) {
			return false;
		}
		if (committer == null) {
			if (other.committer != null) {
				return false;
			}
		} else if (!committer.equals(other.committer)) {
			return false;
		}
		if (message == null) {
			if (other.message != null) {
				return false;
			}
		} else if (!message.equals(other.message)) {
			return false;
		}
		if (parents == null) {
			if (other.parents != null) {
				return false;
			}
		} else if (!parents.equals(other.parents)) {
			return false;
		}
		if (subject == null) {
			if (other.subject != null) {
				return false;
			}
		} else if (!subject.equals(other.subject)) {
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
		return "CommitInfo [commit=" + commit + ", parents=" + parents + ", author=" + author + ", committer="
				+ committer + ", subject=" + subject + ", message=" + message + "]";
	}

	/**
	 * Reset the data fields used in a display
	 */
	public void reset() {
		setCommit(""); //$NON-NLS-1$
		setParents(new ArrayList<CommitInfo>());
		setAuthor(new GitPersonInfo());
		setCommitter(new GitPersonInfo());
		setMessage(""); //$NON-NLS-1$
		setSubject(""); //$NON-NLS-1$
	}
}
