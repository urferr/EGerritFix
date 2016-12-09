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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.egerrit.internal.core.GerritClient;
import org.eclipse.egerrit.internal.model.ChangeInfo;
import org.eclipse.egerrit.internal.model.RelatedChangeAndCommitInfo;
import org.eclipse.egerrit.internal.ui.editors.model.ChangeDetailEditorInput;
import org.eclipse.egerrit.internal.ui.utils.Messages;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.PlatformUI;

/**
 * Class that, when run, refreshes opened related changes in Egerrit editors.
 */
public class RefreshRelatedEditors extends Job {

	private ChangeInfo fChangeInfo;

	private GerritClient fGerritClient;

	public RefreshRelatedEditors(ChangeInfo changeInfo, GerritClient gerritClient) {
		super(Messages.ChangeDetailEditor_updatingRelatedChanges);
		fChangeInfo = changeInfo;
		fGerritClient = gerritClient;
	}

	@Override
	public IStatus run(IProgressMonitor monitor) {
		/* Get list of related changes and remove current one */
		List<String> changeIds = new ArrayList<String>();
		if (fChangeInfo.getRelatedChanges() != null) {
			for (RelatedChangeAndCommitInfo change : fChangeInfo.getRelatedChanges().getChanges()) {
				if (change.getChange_id() != null && !change.getChange_id().equals(fChangeInfo.getChange_id())) {
					changeIds.add(change.getChange_id());
				}
			}
		}
		if (changeIds.size() == 0) {
			return Status.OK_STATUS;
		}

		//Obtain the list of editors opened
		IEditorReference[][] editorRefs = new IEditorReference[1][];
		Display.getDefault().syncExec(() -> {
			editorRefs[0] = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getEditorReferences();
		});

		/* Iterate over all the open editors in the workbench */
		for (IEditorReference editorRef : editorRefs[0]) {
			/* Check for the opened EGerrit editors */
			IEditorPart part = editorRef.getEditor(false);

			if (part instanceof ChangeDetailEditor) {
				ChangeDetailEditorInput changeInput = (ChangeDetailEditorInput) part.getEditorInput();
				if (!changeInput.getChange().getChange_id().equals(fChangeInfo.getChange_id())) {
					if (changeIds.contains(changeInput.getChange().getChange_id())) {
						QueryHelpers.loadBasicInformation(fGerritClient, changeInput.getChange(), true);
					}
				}
			}
		}
		return Status.OK_STATUS;
	}
}
