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

import java.lang.reflect.Type;

import org.apache.http.client.methods.HttpRequestBase;
import org.eclipse.egerrit.internal.core.GerritRepository;

abstract class BaseCommandChangeAndRevisionWithInput<ResultType, InputType>
		extends BaseCommandChangeAndRevision<ResultType> {

	protected BaseCommandChangeAndRevisionWithInput(GerritRepository gerritRepository,
			AuthentificationRequired authRequired, Class<? extends HttpRequestBase> operationType, Type returnType,
			String changeId, String revisionId) {
		super(gerritRepository, authRequired, operationType, returnType, changeId, revisionId);
	}

	public void setCommandInput(InputType commandInput) {
		setInput(commandInput);
	}
}
