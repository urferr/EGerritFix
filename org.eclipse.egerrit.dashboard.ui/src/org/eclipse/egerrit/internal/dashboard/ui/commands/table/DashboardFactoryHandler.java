/*******************************************************************************
 * Copyright (c) 2016 Ericsson AB.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson - initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.internal.dashboard.ui.commands.table;

import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.egerrit.internal.core.GerritClient;
import org.eclipse.egerrit.internal.dashboard.ui.views.GerritTableView;
import org.eclipse.egerrit.internal.model.ActionInfo;
import org.eclipse.egerrit.internal.model.ChangeInfo;
import org.eclipse.egerrit.internal.model.RevisionInfo;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.ui.commands.IElementUpdater;
import org.eclipse.ui.menus.UIElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class implements a DEFAULT handler to initialize the required field with the latest patch set files of the
 * selected review
 */
public abstract class DashboardFactoryHandler extends AbstractHandler implements IElementUpdater {
	private static Logger logger = LoggerFactory.getLogger(DashboardFactoryHandler.class);

	protected ChangeInfo changeInfo = null;

	private RevisionInfo latestRevision = null;

	protected GerritClient gerritClient = null;

	@Override
	public void setEnabled(Object evaluationContext) {
		fillDataStructure();
		boolean state = true;
		setBaseEnabled(state);
	}

	@Override
	public void updateElement(UIElement element, Map parameters) {
		fillDataStructure();
	}

	private void fillDataStructure() {
		final GerritTableView reviewTableView = GerritTableView.getActiveView(true);
		final TableViewer viewer = reviewTableView.getTableViewer();
		ISelection tableSelection = null;
		if (viewer != null) {
			tableSelection = viewer.getSelection();
		}

		gerritClient = reviewTableView.getGerritClient();

		if (tableSelection instanceof IStructuredSelection) {
			Object obj = ((IStructuredSelection) tableSelection).getFirstElement();
			if (obj instanceof ChangeInfo) {
				changeInfo = (ChangeInfo) obj;
				latestRevision = changeInfo.getRevision();
			} else {
				logger.debug("The selection does not contain a ChangeInfo"); //$NON-NLS-1$
				changeInfo = null;
				latestRevision = null;
			}
		} else {
			logger.debug("The selection does not contain a ChangeInfo"); //$NON-NLS-1$
			changeInfo = null;
			latestRevision = null;
		}
	}

	public GerritClient getGerritClient() {
		return gerritClient;
	}

	public ChangeInfo getChangeInfo() {
		return changeInfo;
	}

	public RevisionInfo getLatestRevision() {
		return latestRevision;
	}

	public ActionInfo getRevisionAction(String action) {
		return latestRevision != null ? latestRevision.getActions().get(action) : null;
	}
}
