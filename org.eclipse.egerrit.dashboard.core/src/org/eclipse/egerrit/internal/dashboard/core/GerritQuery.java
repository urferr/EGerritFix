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
package org.eclipse.egerrit.internal.dashboard.core;

/**
 * Constants for the query type.
 *
 * @since 1.0
 */
public class GerritQuery {

	/**
	 * Query type: my changes
	 */
	public static final String MY_CHANGES = "is:open AND (owner:self OR reviewer:self)"; //$NON-NLS-1$

	/**
	 * Query type: my recently closed changes
	 */
	public static final String MY_RECENTLY_CLOSED_CHANGES = "is:closed AND (owner:self OR reviewer:self) AND limit:10"; //$NON-NLS-1$

	/**
	 * The default constructor. Do not allow to build an object of this class
	 */
	private GerritQuery() {
	}

}
