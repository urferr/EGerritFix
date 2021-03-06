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
import org.eclipse.egerrit.internal.model.ModelHelpers;
import org.eclipse.egerrit.internal.ui.EGerritImages;
import org.eclipse.egerrit.internal.ui.utils.Messages;
import org.eclipse.egerrit.internal.ui.utils.UIUtils;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

/**
 * This class implements the marker reply to comment functionality
 *
 * @since 1.0
 */

class QuickFixModifyDraft extends EGerritQuickFix {
	QuickFixModifyDraft(String label, String completeMessage) {
		super(label, completeMessage);
	}

	public void run(IMarker marker) {
		final Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		try {
			GerritClient gerritClient = (GerritClient) marker.getAttribute(EGerritCommentMarkers.ATTR_GERRIT_CLIENT);
			CommentInfo existingComment = (CommentInfo) marker.getAttribute(EGerritCommentMarkers.ATTR_COMMENT_INFO);

			final InputDialog replyDialog = new InputDialog(shell, Messages.QuickFixModifyDraft_0,
					Messages.QuickFixModifyDraft_1 + UIUtils.getPatchSetString(existingComment),
					existingComment.getMessage(), null) {
				@Override
				protected int getInputTextStyle() {
					return SWT.MULTI | SWT.BORDER | SWT.V_SCROLL;
				}

				@Override
				protected Control createDialogArea(Composite parent) {
					Control res = super.createDialogArea(parent);
					((GridData) this.getText().getLayoutData()).heightHint = 100;
					return res;
				}
			};
			replyDialog.open();
			if (replyDialog.getReturnCode() == IDialogConstants.OK_ID) {
				UIUtils.postReply(gerritClient, existingComment, replyDialog.getValue(),
						ModelHelpers.getRevision(existingComment).getId());
				QueryHelpers.deleteDraft(gerritClient, existingComment);
			}
		} catch (CoreException e) {
			EGerritCorePlugin.logError(e.getMessage());
			return;
		}
	}

	@Override
	public Image getImage() {
		return EGerritImages.get(EGerritImages.EDIT_QUICKFIX);
	}
}