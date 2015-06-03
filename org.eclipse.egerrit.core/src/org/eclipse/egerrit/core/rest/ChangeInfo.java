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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The <a href= "http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#change-info" >ChangeInfo</a>
 * entity contains information about a change.
 * <p>
 * This structure is filled by GSON when parsing the corresponding JSON structure in an HTTP response.
 *
 * @since 1.0
 * @author Francois Chouinard
 */
public class ChangeInfo {

	// ------------------------------------------------------------------------
	// The data structure
	// ------------------------------------------------------------------------
	// used to fire events of registered properties
	private transient PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	// The structure type.
	// "gerritcodereview#change"
	private String kind;

	// The ID of the change in the format "'<project>~<branch>~<Change-Id>'"
	// e.g. "myProject~master~I4982a3771051891899528a94fb47baeeb70582ae"
	private String id;

	// The name of the project.
	// e.g. "myProject"
	private String project;

	// The name of the target branch (the 'refs/heads/' prefix is omitted).
	// e.g. "master"
	private String branch;

	// The topic to which this change belongs (optional).
	// e.g. "EGerrit"
	private String topic;

	// The Change-Id of the change.
	// e.g. "I4982a3771051891899528a94fb47baeeb70582ae"
	private String change_id;

	// The subject of the change (header line of the commit message).
	// e.g. "Implementing Feature X"
	private String subject;

	// The status of the change (NEW, SUBMITTED, MERGED, ABANDONED, DRAFT).
	// e.g. "NEW"
	private String status;

	// The timestamp of when the change was created.
	// e.g. "2014-10-21 01:07:26.225000000"
	private String created;

	// The timestamp of when the change was last updated.
	// e.g. "2014-10-21 01:07:26.225000000",
	private String updated;

	// Whether the calling user has starred this change (not set if false).
	private boolean starred = false;

	// Whether the change was reviewed by the calling user. Only set if reviewed
	// is requested (not set if false).
	private boolean reviewed = false;

	// Whether the change is mergeable. Not set for merged changes, or if the
	// change has not yet been tested.
	private boolean mergeable = false;

	// Number of inserted lines.
	private int insertions = -1;

	// Number of deleted lines.
	private int deletions = -1;

	// e.g. "0030952300000001"
	private String _sortkey;

	// The legacy numeric ID of the change.
	private int _number = -1;

	// The owner of the change as an AccountInfo entity.
	private AccountInfo owner;

	// Actions the caller might be able to perform on this revision.
	// The information is a map of view name to ActionInfo entities. (optional).
	private Map<String, ActionInfo> actions;

	// The labels of the change as a map that maps the label names to LabelInfo
	// entries. Only set if 'labels' or 'detailed labels' are requested.
	private Map<String, LabelInfo> labels;

	// A map of the permitted labels that maps a label name to the list of
	// values that are allowed for that label. Only set if 'detailed labels' are
	// requested.
	private Map<String, String[]> permitted_labels;

	// The reviewers that can be removed by the calling user as a list of
	// AccountInfo entities. Only set if 'detailed labels' are requested.
	private List<AccountInfo> removable_reviewers;

	// Messages associated with the change as a list of ChangeMessageInfo
	// entities.
	// Only set if 'messages' are requested.
	private List<ChangeMessageInfo> messages;

	// The commit ID of the current patch set of this change. Only set if the
	// 'current revision' is requested or if 'all revisions' are requested.
	private String current_revision;

	// All patch sets of this change as a map that maps the commit ID of the
	// patch set to a RevisionInfo entity. Only set if the 'current revision'
	// is requested (in which case it will only contain a key for the
	// 'current revision') or if 'all revisions' are requested.
	private Map<String, RevisionInfo> revisions;

	// Whether the query would deliver more results if not limited.
	// Only set on the last change that is returned. Not set if false.
	private boolean _more_changes = false;

	// A list of ProblemInfo entities describing potential problems with this
	// change. Only set if 'CHECK' is set.
	private List<ProblemInfo> problems;

	// A {change-id} that identifies the base change for a create change
	// operation. Only used for the CreateChange endpoint.
	private String base_change;

	// a calculated compilation of the codeReview values for this review (not part of the REST structure)
	private int codeReviewedTally;

	// a calculated compilation of the verify values for this review (not part of the REST structure)
	private int verifiedTally;

	// ------------------------------------------------------------------------
	// The getters
	// ------------------------------------------------------------------------

	/**
	 * @return The structure type ("gerritcodereview#change").
	 */
	public String getKind() {
		return kind;
	}

	/**
	 * @return The ID of the change in the format "'<project>~<branch>~<Change-Id>'".
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return The ID of the change in the format "'<project>~<branch>~<Change-Id>'".
	 */
	public void setId(String id) {
		firePropertyChange("id", this.id, this.id = id);
	}

	/**
	 * @return The name of the project.
	 */
	public String getProject() {
		return project;
	}

	/**
	 * @return The name of the target branch (the 'refs/heads/' prefix is omitted).
	 */
	public String getBranch() {
		return branch;
	}

	/**
	 * @return The topic to which this change belongs . May be null.
	 */
	public String getTopic() {
		return topic;
	}

	/**
	 * @return The topic to which this change belongs . May be null.
	 */
	public void setTopic(String topic) {
		firePropertyChange("topic", this.topic, this.topic = topic);

	}

	/**
	 * @return The Change-Id of the change.
	 */
	public void setChange_id(String change_id) {
		firePropertyChange("change_id", this.change_id, this.change_id = change_id);
	}

	public void setProject(String project) {
		firePropertyChange("project", this.project, this.project = project);

	}

	/**
	 * @return The name of the target branch (the 'refs/heads/' prefix is omitted).
	 */
	public void setBranch(String branch) {
		firePropertyChange("branch", this.branch, this.branch = branch);
	}

	/**
	 * @return The name of the target branch (the 'refs/heads/' prefix is omitted).
	 */
	public void setUpdated(String updated) {
		firePropertyChange("updated", this.updated, this.updated = updated);
	}

	/**
	 * @param current_revision
	 *            the current_revision to set
	 */
	public void setCurrent_revision(String current_revision) {
		firePropertyChange("current_revision", this.current_revision, this.current_revision = current_revision);
	}

	/**
	 * @param labels
	 *            the labels to set
	 */
	public void setLabels(Map<String, LabelInfo> labels) {
		firePropertyChange("labels", this.labels, this.labels = labels);
	}

	/**
	 * @return The Change-Id of the change.
	 */
	public String getChange_id() {
		return change_id;
	}

	/**
	 * @return The subject of the change (header line of the commit message).
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param The
	 *            subject of the change (header line of the commit message).
	 */
	public void setSubject(String subject) {
		firePropertyChange("subject", this.subject, this.subject = subject);
	}

	/**
	 * @return The status of the change (NEW, SUBMITTED, MERGED, ABANDONED, DRAFT).
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @return The status of the change (NEW, SUBMITTED, MERGED, ABANDONED, DRAFT).
	 */
	public void setStatus(String status) {
		firePropertyChange("status", this.status, this.status = status);
	}

	/**
	 * Set The legacy numeric ID of the change.
	 */
	public void setNumber(int _number) {
		firePropertyChange("_number", this._number, this._number = _number);
	}

	/**
	 * Set the messages associated with the change.
	 *
	 * @param list
	 *            of changeMessageInfo
	 * @return void
	 */
	public void setMessages(List<ChangeMessageInfo> messages) {
		firePropertyChange("messages", this.messages, this.messages = messages);
	}

	/**
	 * @param codeReviewedTally
	 *            the codeReviewedTally to set
	 */
	public void setCodeReviewedTally(int codeReviewedTally) {
		this.codeReviewedTally = codeReviewedTally;
	}

	/**
	 * @param verifiedTally
	 *            the verifiedTally to set
	 */
	public void setVerifiedTally(int verifiedTally) {
		this.verifiedTally = verifiedTally;
	}

	/**
	 * @return the codeReviewedTally
	 */
	public int getCodeReviewedTally() {
		return codeReviewedTally;
	}

	/**
	 * @return the verifiedTally
	 */
	public int getVerifiedTally() {
		return verifiedTally;
	}

	/**
	 * @return The timestamp of when the change was created.
	 */
	public String getCreated() {
		return created;
	}

	/**
	 * @return The timestamp of when the change was last updated.
	 */
	public String getUpdated() {
		return updated;
	}

	/**
	 * @return Whether the calling user has starred this change.
	 */
	public boolean isStarred() {
		return starred;
	}

	/**
	 * @return Whether the change was reviewed by the calling user.
	 */
	public boolean isReviewed() {
		return reviewed;
	}

	/**
	 * @return Whether the change is mergeable.
	 */
	public boolean isMergeable() {
		return mergeable;
	}

	/**
	 * @return The number of inserted lines
	 */
	public int getInsertions() {
		return insertions;
	}

	/**
	 * @return The number of deleted lines.
	 */
	public int getDeletions() {
		return deletions;
	}

	/**
	 * @return The sort key
	 */
	public String getSortkey() {
		return _sortkey;
	}

	/**
	 * @return The legacy numeric ID of the change.
	 */
	public int getNumber() {
		return _number;
	}

	/**
	 * @return The owner of the change.
	 */
	public AccountInfo getOwner() {
		return owner;
	}

	/**
	 * @return The actions the caller might be able to perform on this revision. May be null.
	 */
	public Map<String, ActionInfo> getActions() {
		return actions;
	}

	/**
	 * @return The labels of the change. May be null.
	 */
	public Map<String, LabelInfo> getLabels() {
		return labels;
	}

	/**
	 * @return The map of the permitted labels and values allowed for each label. May be null.
	 */
	public Map<String, String[]> getPermittedLabels() {
		return permitted_labels;
	}

	/**
	 * @return The reviewers that can be removed by the calling user. May be null.
	 */
	public List<AccountInfo> getRemovableReviewers() {
		return removable_reviewers;
	}

	/**
	 * @return The messages associated with the change. May be null.
	 */
	public List<ChangeMessageInfo> getMessages() {
		return messages;
	}

	/**
	 * @return The commit ID of the current patch set of this change. May be null.
	 */
	public String getCurrentRevision() {
		return current_revision;
	}

	/**
	 * @return The commit ID:s of all the patch sets of this change. May be null.
	 */
	public Map<String, RevisionInfo> getRevisions() {
		return revisions;
	}

	/**
	 * @return Whether more results are pending at the server. May be null.
	 */
	public boolean hasMoreChanges() {
		return _more_changes;
	}

	/**
	 * @return A list of potential problems with this change. May be null.
	 */
	public List<ProblemInfo> getProblems() {
		return problems;
	}

	/**
	 * @return A {change-id} that identifies the base change for a create change operation. May be null.
	 */
	public String getBaseChange() {
		return base_change;
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
		result = prime * result + (_more_changes ? 1231 : 1237);
		result = prime * result + _number;
		result = prime * result + ((_sortkey == null) ? 0 : _sortkey.hashCode());
		result = prime * result + ((actions == null) ? 0 : actions.hashCode());
		result = prime * result + ((base_change == null) ? 0 : base_change.hashCode());
		result = prime * result + ((branch == null) ? 0 : branch.hashCode());
		result = prime * result + ((change_id == null) ? 0 : change_id.hashCode());
		result = prime * result + ((created == null) ? 0 : created.hashCode());
		result = prime * result + ((current_revision == null) ? 0 : current_revision.hashCode());
		result = prime * result + deletions;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + insertions;
		result = prime * result + ((kind == null) ? 0 : kind.hashCode());
		result = prime * result + ((labels == null) ? 0 : labels.hashCode());
		result = prime * result + (mergeable ? 1231 : 1237);
		result = prime * result + ((messages == null) ? 0 : messages.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		result = prime * result + ((permitted_labels == null) ? 0 : permitted_labels.hashCode());
		result = prime * result + ((problems == null) ? 0 : problems.hashCode());
		result = prime * result + ((project == null) ? 0 : project.hashCode());
		result = prime * result + ((removable_reviewers == null) ? 0 : removable_reviewers.hashCode());
		result = prime * result + (reviewed ? 1231 : 1237);
		result = prime * result + ((revisions == null) ? 0 : revisions.hashCode());
		result = prime * result + (starred ? 1231 : 1237);
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((subject == null) ? 0 : subject.hashCode());
		result = prime * result + ((topic == null) ? 0 : topic.hashCode());
		result = prime * result + ((updated == null) ? 0 : updated.hashCode());
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
		ChangeInfo other = (ChangeInfo) obj;
		if (_more_changes != other._more_changes) {
			return false;
		}
		if (_number != other._number) {
			return false;
		}
		if (_sortkey == null) {
			if (other._sortkey != null) {
				return false;
			}
		} else if (!_sortkey.equals(other._sortkey)) {
			return false;
		}
		if (actions == null) {
			if (other.actions != null) {
				return false;
			}
		} else if (!actions.equals(other.actions)) {
			return false;
		}
		if (base_change == null) {
			if (other.base_change != null) {
				return false;
			}
		} else if (!base_change.equals(other.base_change)) {
			return false;
		}
		if (branch == null) {
			if (other.branch != null) {
				return false;
			}
		} else if (!branch.equals(other.branch)) {
			return false;
		}
		if (change_id == null) {
			if (other.change_id != null) {
				return false;
			}
		} else if (!change_id.equals(other.change_id)) {
			return false;
		}
		if (created == null) {
			if (other.created != null) {
				return false;
			}
		} else if (!created.equals(other.created)) {
			return false;
		}
		if (current_revision == null) {
			if (other.current_revision != null) {
				return false;
			}
		} else if (!current_revision.equals(other.current_revision)) {
			return false;
		}
		if (deletions != other.deletions) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (insertions != other.insertions) {
			return false;
		}
		if (kind == null) {
			if (other.kind != null) {
				return false;
			}
		} else if (!kind.equals(other.kind)) {
			return false;
		}
		if (labels == null) {
			if (other.labels != null) {
				return false;
			}
		} else if (!labels.equals(other.labels)) {
			return false;
		}
		if (mergeable != other.mergeable) {
			return false;
		}
		if (messages == null) {
			if (other.messages != null) {
				return false;
			}
		} else if (!messages.equals(other.messages)) {
			return false;
		}
		if (owner == null) {
			if (other.owner != null) {
				return false;
			}
		} else if (!owner.equals(other.owner)) {
			return false;
		}
		if (permitted_labels == null) {
			if (other.permitted_labels != null) {
				return false;
			}
		} else if (!permitted_labels.equals(other.permitted_labels)) {
			return false;
		}
		if (problems == null) {
			if (other.problems != null) {
				return false;
			}
		} else if (!problems.equals(other.problems)) {
			return false;
		}
		if (project == null) {
			if (other.project != null) {
				return false;
			}
		} else if (!project.equals(other.project)) {
			return false;
		}
		if (removable_reviewers == null) {
			if (other.removable_reviewers != null) {
				return false;
			}
		} else if (!removable_reviewers.equals(other.removable_reviewers)) {
			return false;
		}
		if (reviewed != other.reviewed) {
			return false;
		}
		if (revisions == null) {
			if (other.revisions != null) {
				return false;
			}
		} else if (!revisions.equals(other.revisions)) {
			return false;
		}
		if (starred != other.starred) {
			return false;
		}
		if (status == null) {
			if (other.status != null) {
				return false;
			}
		} else if (!status.equals(other.status)) {
			return false;
		}
		if (subject == null) {
			if (other.subject != null) {
				return false;
			}
		} else if (!subject.equals(other.subject)) {
			return false;
		}
		if (topic == null) {
			if (other.topic != null) {
				return false;
			}
		} else if (!topic.equals(other.topic)) {
			return false;
		}
		if (updated == null) {
			if (other.updated != null) {
				return false;
			}
		} else if (!updated.equals(other.updated)) {
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
		return "ChangeInfo [kind=" + kind + ", id=" + id + ", project=" + project + ", branch=" + branch + ", topic="
				+ topic + ", change_id=" + change_id + ", subject=" + subject + ", status=" + status + ", created="
				+ created + ", updated=" + updated + ", starred=" + starred + ", reviewed=" + reviewed + ", mergeable="
				+ mergeable + ", insertions=" + insertions + ", deletions=" + deletions + ", _sortkey=" + _sortkey
				+ ", _number=" + _number + ", owner=" + owner + ", actions=" + actions + ", labels=" + labels
				+ ", permitted_labels=" + permitted_labels + ", removable_reviewers=" + removable_reviewers
				+ ", messages=" + messages + ", current_revision=" + current_revision + ", revisions=" + revisions
				+ ", _more_changes=" + _more_changes + ", problems=" + problems + ", base_change=" + base_change + "]";
	}

	/**
	 * This method is used to reset all data fields we display.
	 */
	public void reset() {
		setId(""); //$NON-NLS-1$
		setTopic(""); //$NON-NLS-1$
		setChange_id(""); //$NON-NLS-1$
		setProject(""); //$NON-NLS-1$
		setBranch(""); //$NON-NLS-1$
		setUpdated(""); //$NON-NLS-1$
		setCurrent_revision(""); //$NON-NLS-1$
		setLabels(null);
		setSubject(""); //$NON-NLS-1$
		setStatus(""); //$NON-NLS-1$
		setNumber(-1);
		setMessages(new ArrayList<ChangeMessageInfo>());
	}
}
