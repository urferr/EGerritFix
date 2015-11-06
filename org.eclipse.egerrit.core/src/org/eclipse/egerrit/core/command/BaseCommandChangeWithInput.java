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

abstract class BaseCommandChangeWithInput<ReturnType, InputType> extends BaseCommandChange<ReturnType> {

	protected BaseCommandChangeWithInput(GerritRepository gerritRepository, AuthentificationRequired authRequired,
			Class<? extends HttpRequestBase> operationType, Type returnType, String changeId) {
		super(gerritRepository, authRequired, operationType, returnType, changeId);
	}

	public void setCommandInput(InputType commandInput) {
		setInput(commandInput);
	}
}
