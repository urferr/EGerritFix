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

package org.eclipse.egerrit.internal.ui.compare;

import java.util.function.Supplier;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.egerrit.internal.model.RevisionInfo;
import org.eclipse.egerrit.ui.EGerritUIPlugin;
import org.eclipse.egerrit.ui.internal.utils.Messages;
import org.eclipse.egerrit.ui.internal.utils.UIUtils;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.TreeViewer;

/**
 * Provide the action that allows to execute a reply directly from the compare Editor
 */
public class ReplyAction extends Action {
	private static final String ICONS_REPLY = "icons/reply.png"; //$NON-NLS-1$

	//The reference to the viewer
	private Supplier<TreeViewer> viewer;

	private GerritMultipleInput compareInput;

	public ReplyAction(GerritMultipleInput input, Supplier<TreeViewer> viewerRef) {
		this.viewer = viewerRef;
		this.compareInput = input;

		setText(Messages.ReplyAction_0);
		setDescription(Messages.ReplyAction_1);
		setImageDescriptor(EGerritUIPlugin.getImageDescriptor(ICONS_REPLY));

		String anonymousUserToolTip = Messages.ReplyAction_2
				+ input.gerritClient.getRepository().getServerInfo().getServerURI() + Messages.ReplyAction_3;
		if (input.gerritClient.getRepository().getServerInfo().isAnonymous()) {
			setToolTipText(anonymousUserToolTip);
			setEnabled(false);
		} else {
			setToolTipText(Messages.ReplyAction_4);
		}

	}

	@Override
	public void run() {
		try {
			compareInput.saveChanges(new NullProgressMonitor());
		} catch (CoreException e) {
			return;
		}
		RevisionInfo revisionInfo = compareInput.getChangeInfo().getRevisions().get(compareInput.getRightSide());
		UIUtils.replyToChange(viewer.get().getControl().getShell(), revisionInfo, null, compareInput.gerritClient);
		compareInput.fireInputChange();
	}
}
