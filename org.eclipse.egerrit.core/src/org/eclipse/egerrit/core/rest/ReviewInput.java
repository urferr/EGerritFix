/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Guy Perron - Initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.core.rest;

import java.util.Map;

/**
 * The <a href= "http://gerrit-review.googlesource.com/Documentation/rest-api-accounts.html#review-info" >ReviewInfo</a>
 * entity contains information about a review.
 * <p>
 * This structure is filled by GSON when parsing the corresponding JSON structure in an HTTP response.
 */
public class ReviewInput {

	// ------------------------------------------------------------------------
	// The data structure
	// ------------------------------------------------------------------------

	// The message to be added as review comment.
	private String message;

	// The labels of the review as a map that maps the label names to the voting values.
	private Map<String, String> labels;

	// The comments that should be added as a map that maps a file path to a list of
	// CommentInput entities
	private Map<String, CommentInput> comments;

	// Whether all labels are required to be within the user’s permitted ranges based
	// on access controls.
	// If true, attempting to use a label not granted to the user will fail the entire
	// modify operation early.
	// If false, the operation will execute anyway, but the proposed labels will be
	// modified to be the "best" value allowed by the access controls.
	private boolean strict_labels;

	// Draft handling that defines how draft comments are handled that are already
	// in the database but that were not also described in this input.
	// Allowed values are DELETE, PUBLISH and KEEP.
	// If not set, the default is DELETE.
	private String drafts;

	public static final String DRAFT_DELETE = "DELETE"; //$NON-NLS-1$

	public static final String DRAFT_PUBLISH = "PUBLISH"; //$NON-NLS-1$

	public static final String DRAFT_KEEP = "KEEP"; //$NON-NLS-1$

	// Notify handling that defines to whom email notifications should be sent after
	// the review is stored.
	// Allowed values are NONE, OWNER, OWNER_REVIEWERS and ALL.
	// If not set, the default is ALL.
	private String notify;

	// {account-id} the review should be posted on behalf of. To use this option
	// the caller must have been granted labelAs-NAME permission for all keys of labels.
	private String on_behalf_of;

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the labels
	 */
	public Map<String, String> getLabels() {
		return labels;
	}

	/**
	 * @param labels
	 *            the labels to set
	 */
	public void setLabels(Map<String, String> labels) {
		this.labels = labels;
	}

	/**
	 * @return the comments
	 */
	public Map<String, CommentInput> getComments() {
		return comments;
	}

	/**
	 * @param comments
	 *            the comments to set
	 */
	public void setComments(Map<String, CommentInput> comments) {
		this.comments = comments;
	}

	/**
	 * @return the strict_labels
	 */
	public boolean isStrict_labels() {
		return strict_labels;
	}

	/**
	 * @param strict_labels
	 *            the strict_labels to set
	 */
	public void setStrict_labels(boolean strict_labels) {
		this.strict_labels = strict_labels;
	}

	/**
	 * @return the drafts
	 */
	public String getDrafts() {
		return drafts;
	}

	/**
	 * @param drafts
	 *            the drafts to set
	 */
	public void setDrafts(String drafts) {
		this.drafts = drafts;
	}

	/**
	 * @return the notify
	 */
	public String getNotify() {
		return notify;
	}

	/**
	 * @param notify
	 *            the notify to set
	 */
	public void setNotify(String notify) {
		this.notify = notify;
	}

	/**
	 * @return the on_behalf_of
	 */
	public String getOn_behalf_of() {
		return on_behalf_of;
	}

	/**
	 * @param on_behalf_of
	 *            the on_behalf_of to set
	 */
	public void setOn_behalf_of(String on_behalf_of) {
		this.on_behalf_of = on_behalf_of;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comments == null) ? 0 : comments.hashCode());
		result = prime * result + ((drafts == null) ? 0 : drafts.hashCode());
		result = prime * result + ((labels == null) ? 0 : labels.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((notify == null) ? 0 : notify.hashCode());
		result = prime * result + ((on_behalf_of == null) ? 0 : on_behalf_of.hashCode());
		result = prime * result + (strict_labels ? 1231 : 1237);
		return result;
	}

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
		ReviewInput other = (ReviewInput) obj;
		if (comments == null) {
			if (other.comments != null) {
				return false;
			}
		} else if (!comments.equals(other.comments)) {
			return false;
		}
		if (drafts == null) {
			if (other.drafts != null) {
				return false;
			}
		} else if (!drafts.equals(other.drafts)) {
			return false;
		}
		if (labels == null) {
			if (other.labels != null) {
				return false;
			}
		} else if (!labels.equals(other.labels)) {
			return false;
		}
		if (message == null) {
			if (other.message != null) {
				return false;
			}
		} else if (!message.equals(other.message)) {
			return false;
		}
		if (notify == null) {
			if (other.notify != null) {
				return false;
			}
		} else if (!notify.equals(other.notify)) {
			return false;
		}
		if (on_behalf_of == null) {
			if (other.on_behalf_of != null) {
				return false;
			}
		} else if (!on_behalf_of.equals(other.on_behalf_of)) {
			return false;
		}
		if (strict_labels != other.strict_labels) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "ReviewInput [message=" + message + ", labels=" + labels + ", comments=" + comments + ", strict_labels="
				+ strict_labels + ", drafts=" + drafts + ", notify=" + notify + ", on_behalf_of=" + on_behalf_of + "]";
	}

}