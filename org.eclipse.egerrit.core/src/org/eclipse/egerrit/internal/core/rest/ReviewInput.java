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

package org.eclipse.egerrit.internal.core.rest;

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
	private Map<String, Integer> labels;

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
	public Map<String, Integer> getLabels() {
		return labels;
	}

	/**
	 * @param map
	 *            the labels to set
	 */
	public void setLabels(Map<String, Integer> map) {
		this.labels = map;
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
	public boolean isStrict_Labels() {
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
	public void setOn_behalf_of(String onBehalfOf) {
		this.on_behalf_of = onBehalfOf;
	}
}