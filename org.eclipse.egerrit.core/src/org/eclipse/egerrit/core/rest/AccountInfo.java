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
 * "http://gerrit-review.googlesource.com/Documentation/rest-api-accounts.html#account-info"
 * >AccountInfo</a> entity contains information about an account.
 * <p>
 * This structure is filled by GSON when parsing the corresponding JSON
 * structure in an HTTP response.
 *
 * @since 1.0
 * @author Francois Chouinard
 */
public class AccountInfo {

    // ------------------------------------------------------------------------
    // The data structure
    // ------------------------------------------------------------------------

    // The numeric ID of the account
    private int _account_id = -1;

    // The full name of the user.
    // Only set if detailed account information was requested.
    private String name;

    // The email address the user prefers to be contacted through.
    // Only set if detailed account information was requested.
    private String email;

    // The userID of the user.
    // Only set if detailed account information was requested.
    private String username;

    // ------------------------------------------------------------------------
    // The getters
    // ------------------------------------------------------------------------

    /**
     * @return The numeric ID of the account
     */
    public int getAccountId() {
        return _account_id;
    }

    /**
     * @return The full name of the user. May be null.
     */
    public String getName() {
        return name;
    }

    /**
     * @return The preferred e-mail address of the user. May be null.
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return The userID of the user. May be null.
     */
    public String getUsername() {
        return username;
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
        result = prime * result + _account_id;
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result
                + ((username == null) ? 0 : username.hashCode());
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
        AccountInfo other = (AccountInfo) obj;
        if (_account_id != other._account_id)
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
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    @SuppressWarnings("nls")
    public String toString() {
        return "AccountInfo [_account_id=" + _account_id + ", name=" + name
                + ", email=" + email + ", username=" + username + "]";
    }

}
