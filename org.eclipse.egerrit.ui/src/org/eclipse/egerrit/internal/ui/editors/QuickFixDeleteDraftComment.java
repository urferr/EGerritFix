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

package org.eclipse.egerrit.internal.ui.editors;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.egerrit.internal.core.EGerritCorePlugin;
import org.eclipse.egerrit.internal.core.GerritClient;
import org.eclipse.egerrit.internal.model.CommentInfo;
import org.eclipse.egerrit.internal.ui.EGerritImages;
import org.eclipse.egerrit.internal.ui.utils.Messages;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

/**
 * This class implements the marker delete a comment functionality
 *
 * @since 1.0
 */

class QuickFixDeleteDraftComment extends EGerritQuickFix {
	QuickFixDeleteDraftComment(String label, String completeMessage) {
		super(label, completeMessage);
	}

	public void run(IMarker marker) {
		final Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		try {
			String message = (String) marker.getAttribute(EGerritCommentMarkers.ATTR_MESSAGE);
			GerritClient gerritClient = (GerritClient) marker.getAttribute(EGerritCommentMarkers.ATTR_GERRIT_CLIENT);
			CommentInfo existingComment = (CommentInfo) marker.getAttribute(EGerritCommentMarkers.ATTR_COMMENT_INFO);

			if (MessageDialog.openConfirm(shell, Messages.QuickFixDeleteDraftComment_0,
					Messages.QuickFixDeleteDraftComment_1 + message)) {
				QueryHelpers.deleteDraft(gerritClient, existingComment);
			}
		} catch (CoreException e) {
			EGerritCorePlugin.logError(e.getMessage());

		}
	}

	@Override
	public Image getImage() {
		return EGerritImages.get(EGerritImages.DELETE_QUICKFIX);
	}
}