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

package org.eclipse.egerrit.ui.internal.utils;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.egerrit.core.EGerritCorePlugin;
import org.eclipse.egerrit.core.GerritClient;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.services.IServiceLocator;

/**
 * This class is used to execute a command on the EGerrit Dashboard
 *
 * @since 1.0
 */
public class LinkDashboard {

	private GerritClient fGerritClient;

	/**
	 * The constructor.
	 */
	public LinkDashboard(GerritClient GerritClient) {
		this.fGerritClient = GerritClient;
	}

	/**
	 * Invoke a handler to update the Dashboard
	 *
	 * @param key
	 * @param value
	 */
	public void invokeRefreshDashboardCommand(String key, String value) {
		// Optionally pass a ExecutionEvent instance, default no-param arg creates blank event
		Map<String, String> parameters = new HashMap<String, String>();
		if ((key != null && !key.isEmpty()) || (value != null && !value.isEmpty())) {
			parameters.put(key, value);
		}
		invokeRefreshDashboardCommand(parameters);
	}

	/**
	 * Invoke a handler to update the Dashboard
	 *
	 * @param Map
	 */
	public void invokeRefreshDashboardCommand(Map<String, String> parameters) {
		IServiceLocator serviceLocator = PlatformUI.getWorkbench();
		ICommandService commandService = (ICommandService) serviceLocator.getService(ICommandService.class);

		try {
			// Lookup command with its ID
			Command command = commandService.getCommand("org.eclipse.egerrit.dashboard.refresh"); //$NON-NLS-1$

			ExecutionEvent executionEvent = new ExecutionEvent(command, parameters, null, null);
			command.executeWithChecks(executionEvent);
		} catch (NotDefinedException | NotEnabledException | NotHandledException
				| org.eclipse.core.commands.ExecutionException e) {

			EGerritCorePlugin.logError(fGerritClient.getRepository().formatGerritVersion() + e.getMessage());

		}
	}
}
