/*******************************************************************************
 * Copyright (c) 2013 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Francois Chouinard - initial API and implementation
 ******************************************************************************/

package org.eclipse.egerrit.internal.dashboard.core;

import org.eclipse.core.runtime.IStatus;

/**
 * An Gerrit Dashboard exception. Same as a QueryException but with an IStatus.
 *
 * @since 1.0
 */
public class GerritQueryException extends Exception {

	private static final long serialVersionUID = 1L;

	private final transient IStatus fStatus;

	public GerritQueryException(IStatus status, String message) {
		super(message);
		fStatus = status;
	}

	public IStatus getStatus() {
		return fStatus;
	}

}
