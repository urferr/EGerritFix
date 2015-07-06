/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Jacques Bouthillier - Initial API and implementation
 *******************************************************************************/
package org.eclipse.egerrit.dashboard.core;

/**
 * Constants for the query type.
 *
 * @since 1.0
 */
public class GerritQuery {

	/**
	 * Query type: my changes
	 */
	public static final String MY_CHANGES = "owner:self OR reviewer:self"; //$NON-NLS-1$

	/**
	 * Query type: my watched changes
	 */
	public static final String MY_WATCHED_CHANGES = "is:watched status:open"; //$NON-NLS-1$

	/**
	 * Query : my starred changes
	 */
	public static final String QUERY_MY_STARRED_CHANGES = "is:starred status:open"; //$NON-NLS-1$

	/**
	 * Query : my drafts changes
	 */
	public static final String QUERY_MY_DRAFTS_CHANGES = "is:draft"; //$NON-NLS-1$

	/**
	 * Query : my drafts comment changes
	 */
	public static final String QUERY_MY_DRAFTS_COMMENTS_CHANGES = "has:draft"; //$NON-NLS-1$

	/**
	 * Query type: all open changes
	 */
	public static final String ALL_OPEN_CHANGES = "status:open"; //$NON-NLS-1$

	/**
	 * Query : all merged changes
	 */
	public static final String QUERY_ALL_MERGED_CHANGES = "status:merged"; //$NON-NLS-1$

	/**
	 * Query : all abandoned changes
	 */
	public static final String QUERY_ALL_ABANDONED_CHANGES = "status:abandoned"; //$NON-NLS-1$

	/**
	 * Query type: open changes by project
	 */
	public static final String OPEN_CHANGES_BY_PROJECT = "open changes by project"; //$NON-NLS-1$

	/**
	 * Query type: custom
	 */
	public static final String CUSTOM = "custom"; //$NON-NLS-1$

}
