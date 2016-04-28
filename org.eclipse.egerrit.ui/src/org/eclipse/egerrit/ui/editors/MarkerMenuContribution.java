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

import org.eclipse.egerrit.internal.model.CommentInfo;
import org.eclipse.egerrit.internal.model.ModelFactory;
import org.eclipse.egerrit.internal.model.RevisionInfo;
import org.eclipse.egerrit.ui.internal.utils.ActiveWorkspaceRevision;
import org.eclipse.jface.action.ContributionItem;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.text.source.IVerticalRulerInfo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * This class shows a menu to let the user enter a gerrit comment when the user clicks in the vertical ruler
 *
 * @since 1.0
 */

public class MarkerMenuContribution extends ContributionItem {

	private ITextEditor editor;

	private int fCurrentLine = -1;

	public MarkerMenuContribution(ITextEditor editor) {
		this.editor = editor;
	}

	private IVerticalRulerInfo getRulerInfo() {
		return editor.getAdapter(IVerticalRulerInfo.class);
	}

	@Override
	public void fill(Menu menu, int index) {
		fCurrentLine = getRulerInfo().getLineOfLastMouseButtonActivity() + 1;

		IEditorInput editorInput = editor.getEditorInput();
		if (!(editorInput instanceof IFileEditorInput)) {
			return;
		}
		if (ActiveWorkspaceRevision.getInstance()
				.isFilePartOfReview(((IFileEditorInput) editorInput).getFile().getFullPath().toString())) {
			MenuItem menuItem = new MenuItem(menu, SWT.CHECK, index);
			menuItem.setText("Add Gerrit comment");
			menuItem.addSelectionListener(createDynamicSelectionListener());
		}
	}

	//Action to be performed when clicking on the menu item is defined here
	private SelectionAdapter createDynamicSelectionListener() {
		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
				RevisionInfo revision = ActiveWorkspaceRevision.getInstance().getActiveRevision();
				final InputDialog replyDialog = new InputDialog(shell, "Add a comment",
						"Enter the comment for patchset " + revision.get_number() + "/"
								+ revision.getChangeInfo().getRevisions().size(),
						"", null) {
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
					CommentInfo newComment = ModelFactory.eINSTANCE.createCommentInfo();

					newComment.setPath(((IFileEditorInput) editor.getEditorInput()).getFile().getFullPath().toString());
					newComment.setMessage(replyDialog.getValue());
					newComment.setLine(fCurrentLine);

					ActiveWorkspaceRevision.getInstance().newComment(newComment);
				}
			}
		};
	}

}