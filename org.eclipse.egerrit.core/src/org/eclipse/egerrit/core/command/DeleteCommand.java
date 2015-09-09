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

package org.eclipse.egerrit.core.command;

import java.lang.reflect.Type;

import org.eclipse.egerrit.core.GerritRepository;

/**
 * The Gerrit command base class for delete.
 *
 * @param <T>
 *            the return type which is expected from {@link #call()}
 * @since 1.0
 */
public abstract class DeleteCommand<T> extends GerritCommand<T> {

	protected DeleteCommand(GerritRepository gerritRepository, Type returnType) {
		super(gerritRepository, returnType);
	}

}