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

package org.eclipse.egerrit.ui.editors;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.egerrit.core.EGerritCorePlugin;
import org.eclipse.egerrit.core.GerritClient;
import org.eclipse.egerrit.internal.model.CommentInfo;
import org.eclipse.egerrit.ui.internal.utils.UIUtils;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.PlatformUI;

/**
 * This class implements the marker reply done to comment functionality
 *
 * @since 1.0
 */

public class QuickFixReplyDoneToComment implements IMarkerResolution {
	String label;

	QuickFixReplyDoneToComment(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void run(IMarker marker) {
		final Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		try {
			String message = (String) marker.getAttribute("message");
			GerritClient gerritClient = (GerritClient) marker.getAttribute("gerritClient");
			CommentInfo existingComment = (CommentInfo) marker.getAttribute("commentInfo");

			if (MessageDialog.openConfirm(shell, "Reply done to comment", message + "\n\nContinue ?")) {
				UIUtils.postReply(gerritClient, existingComment, "Done", UIUtils.getRevision(existingComment).getId());
			}
		} catch (CoreException e) {
			EGerritCorePlugin.logError(e.getMessage());
			return;
		}
	}

}