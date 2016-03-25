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

package org.eclipse.egerrit.ui.editors.model;

import java.util.function.Supplier;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.egerrit.internal.model.RevisionInfo;
import org.eclipse.egerrit.ui.EGerritUIPlugin;
import org.eclipse.egerrit.ui.editors.QueryHelpers;
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

	private RevisionInfo revisionInfo;

	private GerritMultipleInput input;

	public ReplyAction(GerritMultipleInput input, RevisionInfo revisionInfo, String base,
			Supplier<TreeViewer> viewerRef) {
		this.viewer = viewerRef;
		this.revisionInfo = revisionInfo;
		this.input = input;

		setText("Reply to the review");
		setDescription("Reply to the review");
		setImageDescriptor(EGerritUIPlugin.getImageDescriptor(ICONS_REPLY));

		String anonymousUserToolTip = "This button is disabled because you are connected anonymously to "
				+ input.gerritClient.getRepository().getServerInfo().getServerURI() + ".";
		if (input.gerritClient.getRepository().getServerInfo().isAnonymous()) {
			setToolTipText(anonymousUserToolTip);
			setEnabled(false);
		} else {
			setToolTipText("Reply to the review");
		}

	}

	@Override
	public void run() {
		try {
			input.saveChanges(new NullProgressMonitor());
		} catch (CoreException e) {
			return;
		}
		UIUtils.replyToChange(viewer.get().getControl().getShell(), revisionInfo, input.gerritClient);
		QueryHelpers.reload(input.gerritClient, revisionInfo.getChangeInfo());
		input.fireInputChange();
	}
}
