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

package org.eclipse.egerrit.core.command;

/**
 * The subset of <a href=
 * "http://gerrit-review.googlesource.com/Documentation/user-search.html#_search_operators"
 * >Search Operators</a> that have the form "status:xxx".
 *
 * @since 1.0
 */
public enum ChangeStatus {

    // ------------------------------------------------------------------------
    // Change statuses
    // ------------------------------------------------------------------------

    /** Review of the change is in progress */
    OPEN ("status:open"), //$NON-NLS-1$

    /** Change is submitted and merge is pending */
    PENDING ("status:pending"), //$NON-NLS-1$

    /** Change has at least one non-zero score, in any approval category, by any user */
    REVIEWED ("status:reviewed"), //$NON-NLS-1$

    /** Change has been submitted but is waiting for a dependency */
    SUBMITTED ("status:submitted"), //$NON-NLS-1$

    /** Change is either 'merged' or 'abandoned' */
    CLOSED ("status:closed"), //$NON-NLS-1$

    /** Change has been merged into the branch */
    MERGED ("status:merged"), //$NON-NLS-1$

    /** Change has been abandoned */
    ABANDONED ("status:abandoned"); //$NON-NLS-1$

    // ------------------------------------------------------------------------
    // Enum -> string value handling
    // ------------------------------------------------------------------------

    /** The string value of the enum element */
    private String fStatus;

    /**
     * The initializer
     *
     * @param status
     *            the string value of the enum element
     */
    ChangeStatus(String status) {
        fStatus = status;
    }

    /**
     * @return the corresponding string
     */
    public String getValue() {
        return fStatus;
    }

}

