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
 * The set of available options when querying for a list of ChangeInfo:s.
 * <p>
 * Implemented as an enum so that unrecognized options are caught at compile
 * time.
 * <p>
 * See <a href=
 * "http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#list_changes"
 * >Query Changes</a>.
 *
 * @since 1.0
 */
public enum ChangeOption {

    // ------------------------------------------------------------------------
    // Query Change options
    // ------------------------------------------------------------------------

    /**
     * See <a href=
     * "http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#labels"
     * >LABELS</a>.
     */
    LABELS ("LABELS"), //$NON-NLS-1$

    /**
     * See <a href=
     * "http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#detailed-labels"
     * >DETAILED_LABELS</a>.
     */
    DETAILED_LABELS ("DETAILED_LABELS"), //$NON-NLS-1$

    /**
     * See <a href=
     * "http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#current-revision"
     * >CURRENT_REVISION</a>.
     */
    CURRENT_REVISION ("CURRENT_REVISION"), //$NON-NLS-1$

    /**
     * See <a href=
     * "http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#all-revisions"
     * >ALL_REVISIONS</a>.
     */
    ALL_REVISIONS ("ALL_REVISIONS"), //$NON-NLS-1$

    /**
     * See <a href=
     * "http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#download_commands"
     * >DOWNLOAD_COMMANDS</a>.
     */
    DOWNLOAD_COMMANDS ("DOWNLOAD_COMMANDS"), //$NON-NLS-1$

    /**
     * See <a href=
     * "http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#draft_comments"
     * >DRAFT_COMMENTS</a>.
     */
    DRAFT_COMMENTS ("DRAFT_COMMENTS"), //$NON-NLS-1$

    /**
     * See <a href=
     * "http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#current_commit"
     * >CURRENT_COMMIT</a>.
     */
    CURRENT_COMMIT ("CURRENT_COMMIT"), //$NON-NLS-1$

    /**
     * See <a href=
     * "http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#all_commits"
     * >ALL_COMMITS</a>.
     */
    ALL_COMMITS ("ALL_COMMITS"), //$NON-NLS-1$

    /**
     * See <a href=
     * "http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#current_files"
     * >CURRENT_FILES</a>.
     */
    CURRENT_FILES ("CURRENT_FILES"), //$NON-NLS-1$

    /**
     * See <a href=
     * "http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#all_files"
     * >ALL_FILES</a>.
     */
    ALL_FILES ("ALL_FILES"), //$NON-NLS-1$

    /**
     * See <a href=
     * "http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#detailed_account"
     * >DETAILED_ACCOUNTS</a>.
     */
    DETAILED_ACCOUNTS ("DETAILED_ACCOUNTS"), //$NON-NLS-1$

    /**
     * See <a href=
     * "http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#messages"
     * >MESSAGES</a>.
     */
    MESSAGES ("MESSAGES"), //$NON-NLS-1$

    /**
     * See <a href=
     * "http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#current_actions"
     * >CURRENT_ACTIONS</a>.
     */
    CURRENT_ACTIONS ("CURRENT_ACTIONS"), //$NON-NLS-1$

    /**
     * See <a href=
     * "http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#reviewed"
     * >REVIEWED</a>.
     */
    REVIEWED ("REVIEWED"), //$NON-NLS-1$

    /**
     * See <a href=
     * "http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#web_links"
     * >WEB_LINKS</a>.
     */
    WEB_LINKS ("WEB_LINKS"), //$NON-NLS-1$

    /**
     * See <a href=
     * "http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#check"
     * >CHECK</a>.
     */
    CHECK ("CHECK"); //$NON-NLS-1$

    // ------------------------------------------------------------------------
    // Enum -> string value handling
    // ------------------------------------------------------------------------

    /** The string value of the enum element */
    private String fOption;

    /**
     * The initializer
     *
     * @param option
     *            the string value of the enum element
     */
    ChangeOption(String option) {
        fOption = option;
    }

    /**
     * @return the corresponding string
     */
    public String getValue() {
        return fOption;
    }

}
