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

package org.eclipse.egerrit.internal.process;

import java.util.concurrent.CompletableFuture;

import org.eclipse.egerrit.internal.core.GerritClient;
import org.eclipse.egerrit.internal.model.ChangeInfo;
import org.eclipse.egerrit.internal.model.FileInfo;
import org.eclipse.egerrit.internal.model.RevisionInfo;
import org.eclipse.egerrit.internal.ui.compare.GerritMultipleInput;
import org.eclipse.egerrit.internal.ui.editors.OpenCompareEditor;
import org.eclipse.egerrit.internal.ui.editors.QueryHelpers;
import org.eclipse.egerrit.internal.ui.utils.Messages;
import org.eclipse.egerrit.internal.ui.utils.UIUtils;
import org.eclipse.swt.widgets.Shell;

/**
 * This class handle all aspect for the Open Compare editor operation
 */
public class OpenCompareProcess {

	private final String EDITOR_KEY = "fileEditortip"; //$NON-NLS-1$

	public void handleOpenCompare(Shell shell, GerritClient gerritClient, ChangeInfo changeInfo, FileInfo selectedFile,
			RevisionInfo selectedRevision) {

		OpenCompareEditor compareEditor;
		if (!gerritClient.getRepository().getServerInfo().isAnonymous()) {
			UIUtils.showDialogTip(EDITOR_KEY, shell, Messages.EGerriTip, Messages.FileTabView_EGerriTipValue);
		}
		//Make sure all the revisions are loaded
		CompletableFuture.runAsync(() -> QueryHelpers.loadBasicInformation(gerritClient, changeInfo, false));

		compareEditor = new OpenCompareEditor(gerritClient, changeInfo);

		String right = selectedRevision.getId();
		compareEditor.compareFiles(GerritMultipleInput.BASE, right, selectedFile);
	}
}
