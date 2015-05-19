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


/**
 * The <a href=
 * "http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#change-message-info"
 * >ChangeMessageInfo</a> entity contains information about a message attached
 * to a change.
 * <p>
 * This structure is filled by GSON when parsing the corresponding JSON
 * structure in an HTTP response.
 *
 * @since 1.0
 * @author Francois Chouinard
 */
public class ChangeMessageInfo {

    // ------------------------------------------------------------------------
    // The data structure
    // ------------------------------------------------------------------------

    // The ID of the message.
    private String id;

    // The author of the message. Unset if written by the Gerrit system.
    private AccountInfo author;

    // The timestamp this message was posted.
    private String date;

    // The text left by the user.
    private String message;

    // Which patchset (if any) generated this message.
    private Integer _revision_number;

    // ------------------------------------------------------------------------
    // The getters
    // ------------------------------------------------------------------------

    /**
     * @return The ID of the message.
     */
    public String getID() {
        return id;
    }

    /**
     * @return The author of the message. May be null.
     */
    public AccountInfo getAuthor() {
        return author;
    }

    /**
     * @return The timestamp this message was posted.
     */
    public String getDate() {
        return date;
    }

    /**
     * @return The text left by the user.
     */
    public String getMessage() {
        return message;
    }

    /**
     * @return Which patchset (if any) generated this message. May be null.
     */
    public Integer getRevisionNumber() {
        return _revision_number;
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
        result = prime
                * result
                + ((_revision_number == null) ? 0 : _revision_number.hashCode());
        result = prime * result + ((author == null) ? 0 : author.hashCode());
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((message == null) ? 0 : message.hashCode());
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
        ChangeMessageInfo other = (ChangeMessageInfo) obj;
        if (_revision_number == null) {
            if (other._revision_number != null)
                return false;
        } else if (!_revision_number.equals(other._revision_number))
            return false;
        if (author == null) {
            if (other.author != null)
                return false;
        } else if (!author.equals(other.author))
            return false;
        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (message == null) {
            if (other.message != null)
                return false;
        } else if (!message.equals(other.message))
            return false;
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    @SuppressWarnings("nls")
    public String toString() {
        return "ChangeMessageInfo [id=" + id + ", author=" + author + ", date="
                + date + ", message=" + message + ", _revision_number="
                + _revision_number + "]";
    }

}
