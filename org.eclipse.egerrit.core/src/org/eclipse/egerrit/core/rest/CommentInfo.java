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

/**
 * The <a href= "http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#change-info" >CommentInfo</a>
 * entity contains information about a comment.
 * <p>
 * This structure is filled by GSON when parsing the corresponding JSON structure in an HTTP response.
 *
 * @since 1.0
 * @author Guy Perron
 */
public class CommentInfo {

/*
 * The URL encoded UUID of the comment.
 */
	private String id;

	/*
	 * The path of the file for which the inline comment was done.
	Not set if returned in a map where the key is the file path.
	 */
	private String path;

	/*
	 * The side on which the comment was added.
	Allowed values are REVISION and PARENT.
	If not set, the default is REVISION.
	 */
	private String side;

	/*
	 * The number of the line for which the comment was done.
	If range is set, this equals the end line of the range.
	If neither line nor range is set, it’s a file comment.
	 */
	private int line;

	/*
	 * The range of the comment as a CommentRange entity
	 */
	private CommentRange range;

	/*
	 * The URL encoded UUID of the comment to which this comment is a reply.
	 */
	private String inReplyTo;

	/*
	 * The comment message.
	 */
	private String message;

	/*
	 * The timestamp of when this comment was written
	 */
	private String updated;

	/*
	 * The author of the message as an AccountInfo entity.
	Unset for draft comments, assumed to be the calling user.
	 */
	private AccountInfo author;

	/**
	 * @return String
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param String
	 *            id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return String
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param String
	 *            path
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return String
	 */
	public String getSide() {
		return side;
	}

	/**
	 * @param String
	 *            side
	 */
	public void setSide(String side) {
		this.side = side;
	}

	/**
	 * @return int
	 */
	public int getLine() {
		return line;
	}

	/**
	 * @param int line
	 */
	public void setLine(int line) {
		this.line = line;
	}

	/**
	 * @return CommentRange
	 */
	public CommentRange getRange() {
		return range;
	}

	/**
	 * @param CommentRange
	 *            range
	 */
	public void setRange(CommentRange range) {
		this.range = range;
	}

	/**
	 * @return String
	 */
	public String getInReplyTo() {
		return inReplyTo;
	}

	/**
	 * @param String
	 *            inReplyTo
	 */
	public void setInReplyTo(String inReplyTo) {
		this.inReplyTo = inReplyTo;
	}

	/**
	 * @return String
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param String
	 *            message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return String
	 */
	public String getUpdated() {
		return updated;
	}

	/**
	 * @param String
	 *            updated
	 */
	public void setUpdated(String updated) {
		this.updated = updated;
	}

	/**
	 * @return AccountInfo
	 */
	public AccountInfo getAuthor() {
		return author;
	}

	/**
	 * @param AccountInfo
	 *            author
	 */
	public void setAuthor(AccountInfo author) {
		this.author = author;
	}

	/**
	 * @return int
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((inReplyTo == null) ? 0 : inReplyTo.hashCode());
		result = prime * result + line;
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result + ((range == null) ? 0 : range.hashCode());
		result = prime * result + ((side == null) ? 0 : side.hashCode());
		result = prime * result + ((updated == null) ? 0 : updated.hashCode());
		return result;
	}

	/**
	 * @return int
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
		CommentInfo other = (CommentInfo) obj;
		if (author == null) {
			if (other.author != null) {
				return false;
			}
		} else if (!author.equals(other.author)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (inReplyTo == null) {
			if (other.inReplyTo != null) {
				return false;
			}
		} else if (!inReplyTo.equals(other.inReplyTo)) {
			return false;
		}
		if (line != other.line) {
			return false;
		}
		if (message == null) {
			if (other.message != null) {
				return false;
			}
		} else if (!message.equals(other.message)) {
			return false;
		}
		if (path == null) {
			if (other.path != null) {
				return false;
			}
		} else if (!path.equals(other.path)) {
			return false;
		}
		if (range == null) {
			if (other.range != null) {
				return false;
			}
		} else if (!range.equals(other.range)) {
			return false;
		}
		if (side == null) {
			if (other.side != null) {
				return false;
			}
		} else if (!side.equals(other.side)) {
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

	@Override
	public String toString() {
		return "CommentInfo [id=" + id + ", path=" + path + ", side=" + side + ", line=" + line + ", range=" + range
				+ ", inReplyTo=" + inReplyTo + ", message=" + message + ", updated=" + updated + ", author=" + author
				+ "]";
	}

}
