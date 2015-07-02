/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Guy Perron - Initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.core.command;

import org.eclipse.egerrit.core.GerritRepository;

/**
 * The Gerrit command base class for puts.
 *
 * @param <T>
 *            the return type which is expected from {@link #call()}
 * @since 1.0
 */
public abstract class PostCommand<T> extends GerritCommand<T> {

	protected PostCommand(GerritRepository gerritRepository, Class<T> returnType) {
		super(gerritRepository, returnType);
	}

}
