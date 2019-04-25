/*******************************************************************************
 * Copyright (c) 2019 Ericsson AB.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Jacques Bouthillier - initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.extensionpoint.definition;

import java.util.Iterator;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.egerrit.internal.core.GerritClient;
import org.eclipse.egerrit.internal.model.FileInfo;
import org.eclipse.egerrit.internal.model.impl.StringToFileInfoImpl;
import org.eclipse.egerrit.internal.ui.compare.GerritDiffNode;
import org.eclipse.egerrit.ui.extension.IExternalCmd.ExternalInfo;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;

/**
 * This class implements the selection of the file in the Files table and open the workspace file.
 *
 * @since 1.0
 */
public class HandleExternalFileSelection {

	private GerritClient fGerritClient;

	private ColumnViewer fViewer;

	public HandleExternalFileSelection(GerritClient gerritClient, ColumnViewer viewer) {
		this.fGerritClient = gerritClient;
		this.fViewer = viewer;
	}

	/**
	 * Open the selected file in the Files table and open the workspace file
	 *
	 * @return boolean
	 * @throws Throwable
	 */
	public boolean showFileSelection() {
		boolean ok = true;
		ISelection selection = fViewer.getSelection();
		if (selection instanceof IStructuredSelection) {

			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			Iterator<?> itr = structuredSelection.iterator();
			while (itr.hasNext()) {
				getReviewInfo(itr);
				notifyContributionHandler();
			}
		}

		return ok;

	}

	private void notifyContributionHandler() {
		EvaluateContributionsHandler eval = new EvaluateContributionsHandler();
		try {
			eval.execute(new ExecutionEvent());
		} catch (ExecutionException e) {
			System.out.println("------- exception on execute() ----------------"); //$NON-NLS-1$
			e.printStackTrace();
		}

	}

	/**
	 * @param itr
	 */
	private void getReviewInfo(Iterator<?> itr) {
		Object element = itr.next();
		if (element == null) {
			return;
		}
		FileInfo fileInfo;
		if (fViewer instanceof TableViewer) {
			fileInfo = ((StringToFileInfoImpl) element).getValue();
		} else {
			fileInfo = ((GerritDiffNode) element).getFileInfo();
		}

		// Fill the Data structure to show
		IResource workspaceFile = ResourcesPlugin.getWorkspace().getRoot();
		ExternalInfo.workspacePath = workspaceFile.getLocationURI().getRawPath();
		ExternalInfo.filePath = fileInfo.getPath();
		ExternalInfo.project = fileInfo.getRevision().getChangeInfo().getProject();
		ExternalInfo.serverName = this.fGerritClient.getRepository().getHostname();
		ExternalInfo.reviewCommit = fileInfo.getRevision().getId();
		ExternalInfo.patchSet = fileInfo.getRevision().getRef();
		ExternalInfo.serverPath = this.fGerritClient.getRepository().getServerInfo().getServerURI();

	}
}
