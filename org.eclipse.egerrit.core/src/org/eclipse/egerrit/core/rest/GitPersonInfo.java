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
 * "http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#git-person-info"
 * >GitPersonInfo</a> entity contains information about the author/committer of
 * a commit.
 * <p>
 * This structure is filled by GSON when parsing the corresponding JSON
 * structure in an HTTP response.
 *
 * @since 1.0
 * @author Francois Chouinard
 */
public class GitPersonInfo {

    // ------------------------------------------------------------------------
    // The data structure
    // ------------------------------------------------------------------------

    // The name of the author/committer.
    private String name;

    // The email address of the author/committer.
    private String email;

    // The timestamp of when this identity was constructed.
    private String date;

    // The timezone offset from UTC of when this identity was constructed.
    private int tz;

    // ------------------------------------------------------------------------
    // The getters
    // ------------------------------------------------------------------------

    /**
     * @return The name of the author/committer.
     */
    public String getName() {
        return name;
    }

    /**
     * @return The email address of the author/committer.
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return The timestamp of when this identity was constructed.
     */
    public String getDate() {
        return date;
    }

    /**
     * @return The timezone offset from UTC of when this identity was
     *         constructed.
     */
    public int getTZ() {
        return tz;
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
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + tz;
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
        GitPersonInfo other = (GitPersonInfo) obj;
        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date))
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (tz != other.tz)
            return false;
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    @SuppressWarnings("nls")
    public String toString() {
        return "GitPersonInfo [name=" + name + ", email=" + email + ", date="
                + date + ", tz=" + tz + "]";
    }

}
