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

package org.eclipse.egerrit.core;

import org.eclipse.egerrit.core.exception.EGerritException;

/**
 * Gerrit v2.11 (same as the base class)
 *
 * @since 1.0
 */
public class Gerrit_2_11 extends GerritClient {

	/**
	 * This gerrit version
	 */
	public static final String GERRIT_VERSION = "2.11.0"; //$NON-NLS-1$

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * The constructor
	 *
	 * @param gerritRepository
	 *            the gerrit repository
	 */
	public Gerrit_2_11(GerritRepository gerritRepository) throws EGerritException {
		super(gerritRepository);
	}

	// ------------------------------------------------------------------------
	// Overridden repository queries (i.e. version variants)
	// ------------------------------------------------------------------------

}
