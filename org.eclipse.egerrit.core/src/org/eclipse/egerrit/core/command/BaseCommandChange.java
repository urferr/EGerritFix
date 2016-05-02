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

package org.eclipse.egerrit.core.command;

import java.lang.reflect.Type;

import org.apache.http.client.methods.HttpRequestBase;
import org.eclipse.egerrit.core.GerritRepository;

abstract class BaseCommandChange<T> extends BaseCommand<T> {
	private static final String CHANGE_ID = "{change-id}"; //$NON-NLS-1$

	private static final String REVISION_ID = "{revision-id}"; //$NON-NLS-1$

	protected BaseCommandChange(GerritRepository gerritRepository, AuthentificationRequired authRequired,
			Class<? extends HttpRequestBase> operationType, Type returnType, String changeId) {
		super(gerritRepository, authRequired, operationType, returnType);
		setSegment(CHANGE_ID, changeId);
	}

	protected BaseCommandChange(GerritRepository gerritRepository, AuthentificationRequired authRequired,
			Class<? extends HttpRequestBase> operationType, Type returnType, String changeId, String revisionId) {
		super(gerritRepository, authRequired, operationType, returnType);
		setSegment(CHANGE_ID, changeId);
		setSegment(REVISION_ID, revisionId);
	}

	@Override
	protected void setPathFormat(String pathFormat) {
		super.setPathFormat(pathFormat);
		if (!pathFormat.contains(CHANGE_ID)) {
			throw new IllegalArgumentException("The string " + CHANGE_ID + " is not found"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}
}
