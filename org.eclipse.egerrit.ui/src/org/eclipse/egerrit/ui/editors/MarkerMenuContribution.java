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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.egerrit.core.EGerritCorePlugin;
import org.eclipse.egerrit.internal.model.CommentInfo;
import org.eclipse.egerrit.internal.model.ModelFactory;
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
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * This class manages the marker menu contribution
 *
 * @since 1.0
 */

public class MarkerMenuContribution extends ContributionItem {

	private ITextEditor editor;

	private IVerticalRulerInfo rulerInfo;

	private List<IMarker> markers;

	private static int fCurrentLine = -1;

	public MarkerMenuContribution(ITextEditor editor) {
		this.editor = editor;
		this.rulerInfo = getRulerInfo();
		this.markers = getMarkers();

	}

	private IVerticalRulerInfo getRulerInfo() {
		return editor.getAdapter(IVerticalRulerInfo.class);
	}

	private List<IMarker> getMarkers() {
		List<IMarker> clickedOnMarkers = new ArrayList<IMarker>();
		for (IMarker marker : getAllMarkers()) {
			if (markerHasBeenClicked(marker)) {
				clickedOnMarkers.add(marker);
			}
		}

		return clickedOnMarkers;
	}

	//Determine whether the marker has been clicked using the ruler's mouse listener
	private boolean markerHasBeenClicked(IMarker marker) {
		return (marker.getAttribute(IMarker.LINE_NUMBER, 0)) == (rulerInfo.getLineOfLastMouseButtonActivity() + 1);
	}

	//Get all My Markers for this source file
	private IMarker[] getAllMarkers() {
		try {
			return ((FileEditorInput) editor.getEditorInput()).getFile()
					.findMarkers("org.eclipse.egerrit.ui.commentMarker", true, IResource.DEPTH_ZERO);
		} catch (CoreException e) {
			EGerritCorePlugin.logError(e.getMessage());
		}
		return null;
	}

	//Create a menu item for each marker on the line clicked on
	@Override
	public void fill(Menu menu, int index) {
		fCurrentLine = getRulerInfo().getLineOfLastMouseButtonActivity() + 1;

		boolean isClickedInsideMarker = false;

		if (ActiveWorkspaceRevision.getInstance()
				.isFilePartOfReview(((IFileEditorInput) editor.getEditorInput()).getFile().getFullPath().toString())) {
			for (final IMarker marker : markers) {
				if (fCurrentLine == marker.getAttribute(IMarker.LINE_NUMBER, 0)) {
					isClickedInsideMarker = true;
				}
			}
			if (!isClickedInsideMarker) {
				MenuItem menuItem = new MenuItem(menu, SWT.CHECK, index);
				menuItem.setText("Add comment");
				menuItem.addSelectionListener(createDynamicSelectionListener());
			}
		}

	}

	//Action to be performed when clicking on the menu item is defined here
	private SelectionAdapter createDynamicSelectionListener() {
		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();

				final InputDialog replyDialog = new InputDialog(shell, "Add a comment", "", "", null) {
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