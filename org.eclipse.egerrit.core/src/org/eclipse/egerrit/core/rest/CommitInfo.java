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

import java.util.List;

/**
 * The <a href=
 * "http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#commit-info"
 * >CommitInfo</a> entity contains information about the commit.
 * <p>
 * This structure is filled by GSON when parsing the corresponding JSON
 * structure in an HTTP response.
 *
 * @since 1.0
 * @author Francois Chouinard
 */
public class CommitInfo {

    // ------------------------------------------------------------------------
    // The data structure
    // ------------------------------------------------------------------------

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
     * @return The parent commits of this commit. In each parent only the commit
     *         and subject fields are populated.
     */
    public List<CommitInfo> getParents() {
        return parents;
    }

    /**
     * @return The author of the commit.
     */
    public GitPersonInfo getAuthor() {
        return author;
    }

    /**
     * @return The committer of the commit.
     */
    public GitPersonInfo getCommitter() {
        return committer;
    }

    /**
     * @return The subject of the commit (header line of the commit message).
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @return The commit message.
     */
    public String getMessage() {
        return message;
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
        result = prime * result + ((author == null) ? 0 : author.hashCode());
        result = prime * result + ((commit == null) ? 0 : commit.hashCode());
        result = prime * result
                + ((committer == null) ? 0 : committer.hashCode());
        result = prime * result + ((message == null) ? 0 : message.hashCode());
        result = prime * result + ((parents == null) ? 0 : parents.hashCode());
        result = prime * result + ((subject == null) ? 0 : subject.hashCode());
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
        CommitInfo other = (CommitInfo) obj;
        if (author == null) {
            if (other.author != null)
                return false;
        } else if (!author.equals(other.author))
            return false;
        if (commit == null) {
            if (other.commit != null)
                return false;
        } else if (!commit.equals(other.commit))
            return false;
        if (committer == null) {
            if (other.committer != null)
                return false;
        } else if (!committer.equals(other.committer))
            return false;
        if (message == null) {
            if (other.message != null)
                return false;
        } else if (!message.equals(other.message))
            return false;
        if (parents == null) {
            if (other.parents != null)
                return false;
        } else if (!parents.equals(other.parents))
            return false;
        if (subject == null) {
            if (other.subject != null)
                return false;
        } else if (!subject.equals(other.subject))
            return false;
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    @SuppressWarnings("nls")
    public String toString() {
        return "CommitInfo [commit=" + commit + ", parents=" + parents
                + ", author=" + author + ", committer=" + committer
                + ", subject=" + subject + ", message=" + message + "]";
    }

}
