/*******************************************************************************
 * Copyright (c) 2015 Ericsson AB.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson - initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.internal.core.command;

/**
 * This enumeration is used to indicate whether the command requires authentication. We need a "depends" because some
 * commands like QueryChangesCommand can run fine in both mode
 */
enum AuthentificationRequired {
	YES(), NO(), DEPENDS();
}
