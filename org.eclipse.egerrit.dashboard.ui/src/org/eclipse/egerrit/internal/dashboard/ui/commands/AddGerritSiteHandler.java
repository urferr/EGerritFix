/*******************************************************************************
 * Copyright (c) 2013 Ericsson AB and others.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jacques Bouthillier - Created for Review Dashboard-Gerrit project
 *
 ******************************************************************************/
package org.eclipse.egerrit.internal.dashboard.ui.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.egerrit.internal.dashboard.ui.preferences.GerritDashboardPreferencePage;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.dialogs.PreferencesUtil;

/**
 * This class implements the "Add ..." a new Gerrit server locations.
 *
 * @since 1.0
 */
public class AddGerritSiteHandler extends AbstractHandler {

	private static final String JOB_FAMILY = "DASHBOARD_UI"; //$NON-NLS-1$

	@Override
	public Object execute(final ExecutionEvent aEvent) {
		//Open the dialog to enter a new Gerrit URL
		Object dialogObj = openDialog();
		return dialogObj;
	}

	/**
	 * Initiate a JOB to open the Gerrit definition dialogue
	 *
	 * @return Object
	 */
	private Object openDialog() {
		final Job job = new Job(Messages.AddGerritSiteHandler_commandAdd) {

			@Override
			public boolean belongsTo(Object aFamily) {
				return JOB_FAMILY.equals(aFamily);
			}

			@Override
			public IStatus run(final IProgressMonitor aMonitor) {
				aMonitor.beginTask(Messages.AddGerritSiteHandler_commandAdd, IProgressMonitor.UNKNOWN);

				Display.getDefault().syncExec(new Runnable() {
					public void run() {
						PreferenceDialog prefDialogue = PreferencesUtil.createPreferenceDialogOn(null,
								GerritDashboardPreferencePage.getID(), null, null);
						prefDialogue.open();
					}
				});

				aMonitor.done();
				return Status.OK_STATUS;
			}
		};
		job.setUser(true);
		job.schedule();
		return null;
	}

}
