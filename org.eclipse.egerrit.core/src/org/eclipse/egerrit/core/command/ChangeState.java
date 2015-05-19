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
 * >Search Operators</a> that have the form "has:xxx" and "is:xxx".
 *
 * @since 1.0
 */
public enum ChangeState {

    // ------------------------------------------------------------------------
    // Change states
    // ------------------------------------------------------------------------

    /** Change has a draft comment saved by the current user */
    HAS_DRAFT ("has:draft"), //$NON-NLS-1$

    /** Change has been starred by the current user (same as IS_STARRED) */
    HAS_STAR ("has:star"), //$NON-NLS-1$

    /** Change has been starred by the current user (same as HAS_STAR) */
    IS_STARRED ("is:starred"), //$NON-NLS-1$

    /** Change matches one of the current user’s watch filters */
    IS_WATCHED ("is:watched"), //$NON-NLS-1$

    /** Change has at least one non-zero score, in any approval category, by any user */
    IS_REVIEWED ("is:reviewed"), //$NON-NLS-1$

    /** Current user is the change owner */
    IS_OWNER ("is:owner"), //$NON-NLS-1$

    /** Current user is the change reviewer */
    IS_REVIEWER ("is:reviewer"), //$NON-NLS-1$

    /** Change is open */
    IS_OPEN ("is:open"), //$NON-NLS-1$

    /** Change is submitted, merge pending */
    IS_PENDING ("is:pending"), //$NON-NLS-1$

    /** Change is a draft */
    IS_DRAFT ("is:draft"), //$NON-NLS-1$

    /** Change is either merged or abandoned */
    IS_CLOSED ("is:closed"), //$NON-NLS-1$

    /** Change has been submitted but is waiting for a dependency */
    IS_SUBMITTED ("is:submitted"), //$NON-NLS-1$

    /** Change has been merged into the branch */
    IS_MERGED ("is:merged"), //$NON-NLS-1$

    /** Change has been abandoned */
    IS_ABANDONED ("is:abandoned"), //$NON-NLS-1$

    /** Change has no merge conflict and could be merged in its destination branch */
    IS_MERGEABLE ("is:mergeable"); //$NON-NLS-1$

    // ------------------------------------------------------------------------
    // Enum -> string value handling
    // ------------------------------------------------------------------------

    /** The string value of the enum element */
    private String fState;

    /**
     * The initializer
     *
     * @param state
     *            the string value of the enum element
     */
    ChangeState(String state) {
        fState = state;
    }

    /**
     * @return the corresponding string
     */
    public String getValue() {
        return fState;
    }

}
