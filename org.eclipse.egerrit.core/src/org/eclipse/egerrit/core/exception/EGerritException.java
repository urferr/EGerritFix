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

package org.eclipse.egerrit.core.exception;

/**
 * The EGerrit exception base class.
 *
 * @since 1.0
 */
public class EGerritException extends Exception {

    // ------------------------------------------------------------------------
    // Attributes
    // ------------------------------------------------------------------------

	/** The unique UID */
	private static final long serialVersionUID = 6095759214041370311L;

    // ------------------------------------------------------------------------
    // Constructors
    // ------------------------------------------------------------------------

    public EGerritException(String message) {
        super(message);
    }

    public EGerritException(Exception exception) {
        this(exception.getMessage());
    }

}
