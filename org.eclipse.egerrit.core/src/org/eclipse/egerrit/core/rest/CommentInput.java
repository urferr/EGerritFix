/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson - initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.core.rest;

import java.sql.Timestamp;

import org.eclipse.egerrit.internal.model.CommentRange;

/**
 * Data model object for <a
 * href="https://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#comment-input">CommentInput</a> and
 * <a href="https://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#comment-info">CommentInput</a>.
 */
public class CommentInput {

	// The URL encoded UUID of the comment if an existing draft comment should be updated.
	private String id;

	//The path of the file for which the inline comment should be added.
	// Doesn’t need to be set if contained in a map where the key is the file path.
	private String path;

	// The side on which the comment should be added.
	//Allowed values are REVISION and PARENT.
	// If not set, the default is REVISION.
	private String side;

	// The number of the line for which the comment should be added.
	// 0 if it is a file comment.
	// If neither line nor range is set, a file comment is added.
	// If range is set, this value is ignored in favor of the end_line of the range.
	private int line;

	// The range of the comment as a CommentRange entity.
	private CommentRange range;

	// The URL encoded UUID of the comment to which this comment is a reply.
	private String in_reply_to;

	// The timestamp of this comment.
	// Accepted but ignored.
	private Timestamp updated;

	//The comment message.
	// If not set and an existing draft comment is updated, the existing draft comment is deleted.
	private String message;

	public String getId() {
		return id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int number) {
		this.line = number;
	}

	public void setSide(String side) {
		this.side = side;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
