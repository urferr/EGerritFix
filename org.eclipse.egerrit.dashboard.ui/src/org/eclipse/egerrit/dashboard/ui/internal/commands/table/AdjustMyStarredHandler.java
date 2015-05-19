/*******************************************************************************
 * Copyright (c) 2013 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *   Jacques Bouthillier - Initial Implementation of the selection of My starred handler
 *   Jacques Bouthillier - Bug 426580 Add the starred functionality
 ******************************************************************************/
package org.eclipse.egerrit.dashboard.ui.internal.commands.table;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.egerrit.core.rest.ChangeInfo;
import org.eclipse.egerrit.dashboard.ui.internal.utils.UIUtils;
import org.eclipse.egerrit.dashboard.ui.views.GerritTableView;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Display;

/**
 * This class implements the adjustment of the selection of the My starred handler. in the review table view
 *
 * @since : 1.0
 */
public class AdjustMyStarredHandler extends AbstractHandler {

	private ChangeInfo fItem = null;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.
	 * ExecutionEvent)
	 */
	@SuppressWarnings("restriction")
	@Override
	public Object execute(ExecutionEvent aEvent) throws ExecutionException {
		final GerritTableView reviewTableView = GerritTableView.getActiveView();
		final TableViewer viewer = reviewTableView.getTableViewer();
		final ISelection tableSelection = viewer.getSelection();

		if (!isEnabled()) {
			return null;
		}

		final Job job = new Job(Messages.AdjustMyStarredHandler_commandMessage) {

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				if (tableSelection instanceof IStructuredSelection) {
					Object obj = ((IStructuredSelection) tableSelection).getFirstElement();
					if (obj instanceof ChangeInfo) {
						fItem = (ChangeInfo) obj;

						try {
							// Update the Gerrit Server
							reviewTableView.setStarred(fItem.getChange_id(), !Boolean.valueOf(fItem.isStarred()),
									monitor);

							// Toggle the STARRED value for the Dashboard
//
//							TODO
							//							fItem.setAttribute(GerritTask.IS_STARRED,
//									Boolean.toString(!Boolean.valueOf(fItem.isStarred())));
						} catch (CoreException e) {
							UIUtils.showErrorDialog(e.getMessage(), e.getStatus().getException().getMessage());
						}

						Display.getDefault().asyncExec(new Runnable() {
							public void run() {
								viewer.update(fItem, null);
							}
						});
					}
				}
				return Status.OK_STATUS;
			}
		};
		job.setUser(false);
		job.schedule();

		return null;
	}

	@Override
	public boolean isEnabled() {
		final GerritTableView reviewTableView = GerritTableView.getActiveView();
		final TableViewer viewer = reviewTableView.getTableViewer();
		final ISelection tableSelection = viewer.getSelection();

		if (tableSelection.isEmpty()) {
			return false;
		}

		return true;
	}
}
