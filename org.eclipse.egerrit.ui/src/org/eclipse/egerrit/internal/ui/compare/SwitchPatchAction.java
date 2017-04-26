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

package org.eclipse.egerrit.internal.ui.compare;

import org.eclipse.egerrit.internal.model.RevisionInfo;
import org.eclipse.egerrit.internal.ui.EGerritImages;
import org.eclipse.egerrit.internal.ui.utils.UIUtils;
import org.eclipse.jface.action.Action;

class SwitchPatchAction extends Action {
	private GerritMultipleInput input;

	private boolean leftSide; //Indicate whether this change is for the left side of the compare editor

	private String inputToSwitchTo; //The string that will be passed to change the compare editor input

	SwitchPatchAction(GerritMultipleInput gerritMultipleInput, RevisionInfo revision, boolean left) {
		super();
		this.input = gerritMultipleInput;
		this.leftSide = left;
		this.inputToSwitchTo = revision.getId();
		boolean hasComments = revision.isCommented();
		setText(UIUtils.revisionToString(revision));
		if (hasComments) {
			setImageDescriptor(EGerritImages.getDescriptor(EGerritImages.COMMENT_FILTER));
		}
	}

	SwitchPatchAction(GerritMultipleInput gerritMultipleInput, String target, boolean left) {
		super(target);
		this.input = gerritMultipleInput;
		this.leftSide = left;
		this.inputToSwitchTo = target;
	}

	@Override
	public void run() {
		if (SwitchPatchAction.this.leftSide) {
			SwitchPatchAction.this.input.switchInputs(inputToSwitchTo, null);
		} else {
			SwitchPatchAction.this.input.switchInputs(null, inputToSwitchTo);
		}
	}

}
