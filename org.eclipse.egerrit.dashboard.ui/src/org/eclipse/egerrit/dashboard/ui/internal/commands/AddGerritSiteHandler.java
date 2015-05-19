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
package org.eclipse.egerrit.dashboard.ui.internal.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.egerrit.core.command.ChangeState;
import org.eclipse.egerrit.core.command.ChangeStatus;
import org.eclipse.egerrit.dashboard.ui.preferences.GerritDashboardPreferencePage;
import org.eclipse.egerrit.dashboard.ui.views.GerritTableView;
import org.eclipse.egerrit.dashboard.utils.GerritServerUtility;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.dialogs.PreferencesUtil;

/**
 * This class implements the "Add ..." a new Gerrit project locations.
 *
 * @since 1.0
 */
public class AddGerritSiteHandler extends AbstractHandler {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	// ------------------------------------------------------------------------
	// Variables
	// ------------------------------------------------------------------------

	private GerritServerUtility fServerUtil = null;

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method execute.
	 *
	 * @param aEvent
	 *            ExecutionEvent
	 * @return Object
	 * @see org.eclipse.core.commands.IHandler#execute(ExecutionEvent)
	 */
	public Object execute(final ExecutionEvent aEvent) {

		String menuItemText = ""; //$NON-NLS-1$
		fServerUtil = GerritServerUtility.getInstance();
		Object obj = aEvent.getTrigger();
		GerritTableView reviewTableView = GerritTableView.getActiveView();

		if (obj instanceof Event) {
			Event ev = (Event) obj;
			Widget objWidget = ev.widget;
			if (objWidget instanceof MenuItem) {
				MenuItem menuItem = (MenuItem) objWidget;
				menuItemText = menuItem.getText();
				String stURL = fServerUtil.getMenuSelectionURL(menuItemText);
				// Open the review table first;
				reviewTableView.openView();

				//Verify if we selected the "Add.." button or a pre=defined Gerrit
				if (stURL != null) {
					if (stURL.equals(fServerUtil.getLastSavedGerritServer())) {

						//Initiate the request for the list of reviews with a default query
						reviewTableView.processCommands(ChangeState.IS_WATCHED.getValue() + " " //$NON-NLS-1$
								+ ChangeStatus.OPEN.getValue());// .MY_WATCHED_CHANGES);

						return Status.OK_STATUS; //For now , do not process the dialogue
					} else {
						//Store the new Gerrit server into a file
						fServerUtil.saveLastGerritServer(stURL);

						//Initiate the request for the list of reviews with a default query
						reviewTableView.processCommands(ChangeState.IS_WATCHED.getValue() + " " //$NON-NLS-1$
								+ ChangeStatus.OPEN.getValue());// .MY_WATCHED_CHANGES);

						return Status.OK_STATUS; //For now , do not process the dialogue
					}
				}
			}
		}

		//Open the Dialogue to enter a new Gerrit URL
		Object dialogObj = openDialogue();
		return dialogObj;

	}

	/**
	 * Initiate a JOB to open the Gerrit definition dialogue
	 *
	 * @return Object
	 */
	private Object openDialogue() {
		final Job job = new Job(Messages.AddGerritSiteHandler_commandAdd) {

			@Override
			public boolean belongsTo(Object aFamily) {
				return Messages.AddGerritSiteHandler_uiJOB.equals(aFamily);
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
