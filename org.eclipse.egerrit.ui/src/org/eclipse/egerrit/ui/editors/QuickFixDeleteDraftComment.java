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
import org.eclipse.egerrit.core.command.DeleteDraftCommand;
import org.eclipse.egerrit.core.exception.EGerritException;
import org.eclipse.egerrit.internal.model.CommentInfo;
import org.eclipse.egerrit.internal.model.FileInfo;
import org.eclipse.egerrit.internal.ui.compare.CommentableCompareItem;
import org.eclipse.egerrit.ui.internal.utils.UIUtils;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.PlatformUI;

/**
 * This class implements the marker delete a comment functionality
 *
 * @since 1.0
 */

public class QuickFixDeleteDraftComment implements IMarkerResolution {
	String label;

	QuickFixDeleteDraftComment(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void run(IMarker marker) {
		final Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		try {
			String message = (String) marker.getAttribute("message"); //$NON-NLS-1$
			GerritClient gerritClient = (GerritClient) marker.getAttribute("gerritClient"); //$NON-NLS-1$
			CommentInfo existingComment = (CommentInfo) marker.getAttribute("commentInfo"); //$NON-NLS-1$
			FileInfo fileInfo = (FileInfo) marker.getAttribute("fileInfo"); //$NON-NLS-1$

			if (MessageDialog.openConfirm(shell, "Delete draft comment", message + "\n\nContinue ?")) {
				deleteDraftComment(gerritClient, existingComment, fileInfo);
			}
		} catch (CoreException e) {
			EGerritCorePlugin.logError(e.getMessage());

		}
	}

	private void deleteDraftComment(GerritClient gerritClient, CommentInfo comment, FileInfo fileInfo) {
		DeleteDraftCommand deleteDraft = gerritClient.deleteDraft(UIUtils.getRevision(comment).getChangeInfo().getId(),
				fileInfo.getRevision().getId(), comment.getId());
		try {
			deleteDraft.call();
			fileInfo.getDraftComments().remove(comment);
		} catch (EGerritException e) {
			throw new RuntimeException(CommentableCompareItem.class.getName(),
					new Throwable(String.valueOf(hashCode())));
		}
	}

}