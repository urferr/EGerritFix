/*******************************************************************************
 * Copyright (c) 2016 Ericsson AB and others.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Guy Perron - Created for Review Gerrit Dashboard project
 *
 ******************************************************************************/
package org.eclipse.egerrit.internal.ui.editors;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.ListIterator;
import java.util.Map.Entry;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.Status;
import org.eclipse.egerrit.internal.model.RevisionInfo;
import org.eclipse.egerrit.internal.ui.editors.model.ChangeDetailEditorInput;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * This class implements the display of the patchset selected by the user in the ChangeDetailEditor
 *
 * @since 1.0
 */
public class ShowNextPatchSetHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent aEvent) {
		IWorkbenchPart editor = HandlerUtil.getActiveEditor(aEvent);
		if (editor instanceof ChangeDetailEditor) {
			ChangeDetailEditorInput input = (ChangeDetailEditorInput) ((ChangeDetailEditor) editor).getEditorInput();

			ArrayList<RevisionInfo> revisions = new ArrayList<RevisionInfo>(input.getChange().getRevisions().values());

			revisions.sort((o2, o1) -> o1.get_number() - o2.get_number());

			LinkedHashMap<String, RevisionInfo> patchNumber = new LinkedHashMap<String, RevisionInfo>();
			for (ListIterator<RevisionInfo> iter = revisions.listIterator(); iter.hasNext();) {
				RevisionInfo element = iter.next();
				patchNumber.put(new Integer(element.get_number()).toString(), element);
			}

			Iterator<Entry<String, RevisionInfo>> itr = patchNumber.entrySet().iterator();
			Iterator<Entry<String, RevisionInfo>> first = patchNumber.entrySet().iterator();

			while (itr.hasNext()) {
				Entry<String, RevisionInfo> entry = itr.next();
				if (entry.getValue().get_number() == input.getChange().getUserSelectedRevision().get_number()) {
					if (itr.hasNext()) {
						entry = itr.next();
						input.getChange().setUserSelectedRevision(entry.getValue());
						break;
					} else {
						entry = first.next();
						input.getChange().setUserSelectedRevision(entry.getValue());
						break;
					}
				}
			}

		}
		return Status.OK_STATUS;
	}

}
