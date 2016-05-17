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

package org.eclipse.egerrit.ui.internal.table.provider;

import org.eclipse.egerrit.internal.model.ChangeInfo;
import org.eclipse.egerrit.internal.model.RevisionInfo;
import org.eclipse.egerrit.ui.EGerritUIPlugin;
import org.eclipse.egerrit.ui.internal.utils.UIUtils;
import org.eclipse.jface.action.Action;

public class SwitchCurrentPathsetAction extends Action {
	private ChangeInfo changeInfo;

	private String revisionIdToSwitchTo;

	public SwitchCurrentPathsetAction(ChangeInfo changeInfo, RevisionInfo revision) {
		super();
		this.changeInfo = changeInfo;
		this.revisionIdToSwitchTo = revision.getId();
		boolean hasComments = revision.isCommented();
		setText(UIUtils.revisionToString(revision));
		if (hasComments) {
			setImageDescriptor(EGerritUIPlugin.getImageDescriptor("icons/showComments.gif")); //$NON-NLS-1$
		}
	}

	@Override
	public void run() {
		RevisionInfo toSwitchTo = changeInfo.getRevisions().get(revisionIdToSwitchTo);
		changeInfo.setUserSelectedRevision(toSwitchTo);
	}
}
