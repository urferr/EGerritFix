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

package org.eclipse.egerrit.ui.editors;

import java.net.MalformedURLException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.egerrit.core.EGerritCorePlugin;
import org.eclipse.egerrit.core.GerritClient;
import org.eclipse.egerrit.core.command.ChangeOption;
import org.eclipse.egerrit.core.command.GetChangeCommand;
import org.eclipse.egerrit.core.command.QueryChangesCommand;
import org.eclipse.egerrit.core.exception.EGerritException;
import org.eclipse.egerrit.core.rest.ChangeInfo;

/**
 * A helper class wrapping the common server queries.
 */
public class QueryHelpers {
	private final static String TITLE = "Gerrit Server "; //$NON-NLS-1$

	private static final String EXECUTING_QUERY = "Executing query";

	/**
	 * Obtain the id, and the subject corresponding to a given id
	 *
	 * @param gerritClient,
	 *            the client to use to perform the lookup
	 * @param subject,
	 *            the subject of the change info being looked for
	 * @param monitor
	 *            a progress monitor
	 * @return a {@link ChangeInfo} or null if no match was found
	 */
	public static ChangeInfo[] lookupPartialChangeInfoFromSubject(GerritClient gerritClient, String subject,
			IProgressMonitor monitor) throws MalformedURLException {
		try {
			monitor.beginTask(EXECUTING_QUERY, IProgressMonitor.UNKNOWN);

			QueryChangesCommand command = gerritClient.queryChanges();
			command.addOption(ChangeOption.DETAILED_LABELS);
			command.addOption(ChangeOption.CURRENT_ACTIONS);

			try {
				command.addQuery(setFreeText(subject));
				return command.call();
			} catch (EGerritException e) {
				EGerritCorePlugin.logError(gerritClient.getRepository().formatGerritVersion() + e.getMessage());
			}
			return null;
		} finally {
			monitor.done();
		}
	}

	/**
	 * Create a message text from the subject to allow free text query
	 *
	 * @param subject
	 * @return String
	 */
	private static String setFreeText(String subject) {
		// use the subject to query the server
		String query = "message:\"" + subject + "\""; //$NON-NLS-1$ //$NON-NLS-2$
		return query;
	}

	/**
	 * Obtain the id, and the subject corresponding to a given id
	 *
	 * @param gerritClient,
	 *            the client to use to perform the lookup
	 * @param change_id,
	 *            the long id of the change info being looked for
	 * @param monitor
	 *            a progress monitor
	 * @return a {@link ChangeInfo} or null if no match was found
	 */
	public static ChangeInfo lookupPartialChangeInfoFromChangeId(GerritClient gerrit, String change_id,
			IProgressMonitor monitor) {
		try {
			monitor.beginTask(EXECUTING_QUERY, IProgressMonitor.UNKNOWN);

			GetChangeCommand command = null;
			command = gerrit.getChange(change_id);
			command.addOption(ChangeOption.DETAILED_LABELS);
			command.addOption(ChangeOption.CURRENT_ACTIONS);

			try {
				return command.call();
			} catch (EGerritException e) {
				EGerritCorePlugin.logError(gerrit.getRepository().formatGerritVersion() + e.getMessage());
			}
			return null;
		} finally {
			monitor.done();
		}
	}
}
