/*******************************************************************************
 * Copyright (c) 2017 Ericsson AB.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson - initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.internal.ui.compare;

import org.eclipse.core.resources.IResource;
import org.eclipse.egerrit.internal.core.GerritClient;
import org.eclipse.egerrit.internal.model.FileInfo;
import org.eclipse.egerrit.internal.ui.utils.Messages;
import org.eclipse.egerrit.internal.ui.utils.UIUtils;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.team.internal.ui.synchronize.LocalResourceTypedElement;

public class OpenWorkspaceFile extends Action {

	private SourceViewer sourceViewer;

	private GerritClient gerritClient;

	//This is the node that provides the input to the source viewer
	private GerritDiffNode node;

	public OpenWorkspaceFile(SourceViewer sourceViewer, GerritDiffNode node, GerritClient gerritClient) {
		super(Messages.Compare_OpenFile);
		this.sourceViewer = sourceViewer;
		this.gerritClient = gerritClient;
		this.node = node;
	}

	@Override
	//Get the fileinfo that backs the editor input and try to open it in the workspace.
	public void run() {
		boolean openSucceeded = false;
		Object editorInput = sourceViewer.getInput();

		//get the line number to show
		int selectedLine = 0;
		if (sourceViewer.getSelection() instanceof ITextSelection) {
			ITextSelection selection = (ITextSelection) sourceViewer.getSelection();
			selectedLine = selection.getStartLine();
		}

		//open the file. Handles both FileInfo and WorkspaceFiles
		String requestedPath = null;
		if (editorInput instanceof CommentableCompareItem) {
			FileInfo fileShown = ((CommentableCompareItem) editorInput).getFileInfo();
			requestedPath = fileShown.getPath();
			openSucceeded = UIUtils.openSingleFile(fileShown, gerritClient, fileShown.getRevision(), selectedLine);
		} else if (editorInput instanceof IDocument && getWorkspaceNode() != null) {
			IResource file = getWorkspaceNode().getResource();
			requestedPath = file.getFullPath().toOSString();
			openSucceeded = UIUtils.openSingleFile(file, selectedLine);
		} else {
			//We don't know what the user clicked on. Best effort to try to figure out the name of the file the user wanted.
			requestedPath = getPotentialFileName();
		}
		if (!openSucceeded) {
			UIUtils.displayInformation(Messages.UIFilesTable_3, Messages.UIFilesTable_2 + '\n' + requestedPath);
		}
	}

	private LocalResourceTypedElement getWorkspaceNode() {
		if (node.getLeft() instanceof LocalResourceTypedElement) {
			return (LocalResourceTypedElement) node.getLeft();
		}
		if (node.getRight() instanceof LocalResourceTypedElement) {
			return (LocalResourceTypedElement) node.getRight();
		}
		return null;
	}

	private String getPotentialFileName() {
		if (node.getLeft() instanceof CommentableCompareItem) {
			return ((CommentableCompareItem) node.getLeft()).getFileInfo().getPath();
		}
		if (node.getRight() instanceof CommentableCompareItem) {
			return ((CommentableCompareItem) node.getLeft()).getFileInfo().getPath();
		}
		//Should not happen
		return null;
	}
}
