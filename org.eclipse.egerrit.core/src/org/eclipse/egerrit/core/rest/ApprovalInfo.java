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
 * "http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#approval-info"
 * >ApprovalInfo</a> entity contains information about an approval from a user
 * for a label on a change. ApprovalInfo has the same fields as AccountInfo with
 * 2 additional fields: value and date.
 * <p>
 * This structure is filled by GSON when parsing the corresponding JSON
 * structure in an HTTP response.
 *
 * @since 1.0
 * @author Francois Chouinard
 */
public class ApprovalInfo extends AccountInfo {

    // ------------------------------------------------------------------------
    // The data structure
    // ------------------------------------------------------------------------

    // The vote that the user has given for the label. If present and zero, the
    // user is permitted to vote on the label. If absent, the user is not
    // permitted to vote on that label.
    private Integer value;

    // The time and date describing when the approval was made.
    private String date;

    // ------------------------------------------------------------------------
    // The getters
    // ------------------------------------------------------------------------

    /**
     * @return The vote that the user has given for the label. If present and
     *         zero, the user is permitted to vote on the label. May be null in
     *         which case the user is not permitted to vote on that label.
     */
    public Integer getValue() {
        return value;
    }

    /**
     * @return The time and date describing when the approval was made.
     */
    public String getDate() {
        return date;
    }

    // ------------------------------------------------------------------------
    // Object
    // ------------------------------------------------------------------------

    /* (non-Javadoc)
     * @see org.eclipse.egerrit.core.rest.AccountInfo#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see org.eclipse.egerrit.core.rest.AccountInfo#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        ApprovalInfo other = (ApprovalInfo) obj;
        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date))
            return false;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        return true;
    }

    /* (non-Javadoc)
     * @see org.eclipse.egerrit.core.rest.AccountInfo#toString()
     */
    @Override
    @SuppressWarnings("nls")
    public String toString() {
        return "ApprovalInfo [value=" + value + ", date=" + date + "]";
    }

}
