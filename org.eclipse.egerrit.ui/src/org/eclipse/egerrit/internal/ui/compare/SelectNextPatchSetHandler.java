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
package org.eclipse.egerrit.internal.ui.compare;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.ListIterator;
import java.util.Map.Entry;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.Status;
import org.eclipse.egerrit.internal.model.RevisionInfo;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * This class implements the selection of the next patchset in the compare editor
 *
 * @since 1.0
 */
public class SelectNextPatchSetHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent aEvent) {

		IEditorInput activeEditorInput = HandlerUtil.getActiveEditorInput(aEvent);
		if (activeEditorInput instanceof GerritMultipleInput) {
			String commandName = aEvent.getCommand().getId();
			boolean isLeftSide = commandName.contains("selectLeftPatchSet"); //$NON-NLS-1$
			GerritMultipleInput input = (GerritMultipleInput) activeEditorInput;

			ArrayList<RevisionInfo> revisions = new ArrayList<>(input.getChangeInfo().getRevisions().values());

			revisions.sort((o2, o1) -> o1.get_number() - o2.get_number());

			LinkedHashMap<String, String> patchNumber = new LinkedHashMap<>();
			for (ListIterator<RevisionInfo> iter = revisions.listIterator(); iter.hasNext();) {
				RevisionInfo element = iter.next();
				patchNumber.put(Integer.toString(element.get_number()), element.getId());
			}
			patchNumber.put(GerritMultipleInput.WORKSPACE, GerritMultipleInput.WORKSPACE);
			patchNumber.put(GerritMultipleInput.BASE, GerritMultipleInput.BASE);

			Iterator<Entry<String, String>> itr = patchNumber.entrySet().iterator();
			Iterator<Entry<String, String>> first = patchNumber.entrySet().iterator();

			selectNext(isLeftSide, input, itr, first);

		}
		return Status.OK_STATUS;
	}

	/**
	 * @param isLeftSide
	 * @param input
	 * @param itr
	 * @param first
	 */
	private void selectNext(boolean isLeftSide, GerritMultipleInput input, Iterator<Entry<String, String>> itr,
			Iterator<Entry<String, String>> first) {
		boolean found = false;
		while (itr.hasNext()) {
			Entry<String, String> entry = itr.next();
			if (isLeftSide && entry.getValue().compareTo(input.getLeftSide()) == 0) {
				entry = selectFirstOrNext(itr, first);
				input.switchInputs(entry.getValue(), null);
				found = true;
			}
			if (!isLeftSide && (entry).getValue().compareTo(input.getRightSide()) == 0) {
				entry = selectFirstOrNext(itr, first);
				input.switchInputs(null, entry.getValue());
				found = true;
			}
			if (found) {
				break;
			}
		}
	}

	/**
	 * @param itr
	 * @param first
	 * @return
	 */
	private Entry<String, String> selectFirstOrNext(Iterator<Entry<String, String>> itr,
			Iterator<Entry<String, String>> first) {
		Entry<String, String> entry;
		if (itr.hasNext()) {
			entry = itr.next();
		} else {
			entry = first.next();
		}
		return entry;
	}

}
