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

package org.eclipse.egerrit.ui.editors.model;

import java.util.Collection;

import org.eclipse.egerrit.internal.model.FileInfo;
import org.eclipse.egerrit.internal.model.RevisionInfo;
import org.eclipse.egerrit.ui.EGerritUIPlugin;
import org.eclipse.egerrit.ui.internal.utils.UIUtils;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Label;

public class SwitchPatchAction extends Action {
	private GerritMultipleInput input;

	private boolean leftSide; //Indicate whether this change is for the left side of the compare editor

	private String inputToSwitchTo; //The string that will be passed to change the compare editor input

	private Label labelToUpdate; //The text area that we update to present name of the last selected item

	private String shortLabel; //text to sho in the labelToUpdate

	public SwitchPatchAction(GerritMultipleInput gerritMultipleInput, RevisionInfo revision, Label labelToUpdate,
			boolean left) {
		super();
		this.input = gerritMultipleInput;
		this.leftSide = left;
		this.inputToSwitchTo = revision.getId();
		this.labelToUpdate = labelToUpdate;
		this.shortLabel = "Patch set " + revision.get_number();
		boolean hasComments = hasComments(revision);
		setText(UIUtils.revisionToString(revision));
		if (hasComments) {
			setImageDescriptor(EGerritUIPlugin.getImageDescriptor("icons/showComments.gif"));
		}
	}

	public SwitchPatchAction(GerritMultipleInput gerritMultipleInput, String target, Label labelToUpdate,
			boolean left) {
		super(target);
		this.input = gerritMultipleInput;
		this.leftSide = left;
		this.inputToSwitchTo = target;
		this.labelToUpdate = labelToUpdate;
		this.shortLabel = target;
	}

	private boolean hasComments(RevisionInfo revisionInfo) {
		Collection<FileInfo> files = revisionInfo.getFiles().values();
		return files.stream().filter(f -> f.getCommentsCount() > 0).findFirst().isPresent();
	}

	@Override
	public void run() {
		if (SwitchPatchAction.this.leftSide) {
			SwitchPatchAction.this.input.switchInputs(inputToSwitchTo, null);
		} else {
			SwitchPatchAction.this.input.switchInputs(null, inputToSwitchTo);
		}
		labelToUpdate.setText(shortLabel);
	}

}
