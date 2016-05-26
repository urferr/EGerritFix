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

import org.eclipse.compare.INavigatable;
import org.eclipse.compare.internal.CompareEditorInputNavigator;
import org.eclipse.compare.internal.Utilities;
import org.eclipse.egerrit.internal.ui.EGerritImages;
import org.eclipse.egerrit.internal.ui.utils.Messages;
import org.eclipse.jface.action.Action;

@SuppressWarnings("restriction")
public class NextPreviousFileAction extends Action {
	private int direction;

	private GerritMultipleInput compareInput;

	public NextPreviousFileAction(int direction, GerritMultipleInput compareInput) {
		this.direction = direction;
		this.compareInput = compareInput;
		if (direction == INavigatable.NEXT_CHANGE) {
			setAccelerator(Action.convertAccelerator("]")); //$NON-NLS-1$
			setDescription(Messages.NextPreviousFileAction_1);
			setToolTipText(Messages.NextPreviousFileAction_2);
			setImageDescriptor(EGerritImages.getDescriptor(EGerritImages.DOWN_ARROW));
		} else {
			setAccelerator(Action.convertAccelerator("[")); //$NON-NLS-1$
			setDescription(Messages.NextPreviousFileAction_4);
			setToolTipText(Messages.NextPreviousFileAction_5);
			setImageDescriptor(EGerritImages.getDescriptor(EGerritImages.UP_ARROW));
		}
		setEnabled(true);
	}

	//Helper method to return the top left pane
	private Object getTopPane() {
		Object topPane = ((CompareEditorInputNavigator) compareInput.getNavigator()).getPanes()[0];
		if (topPane != null) {
			return Utilities.getAdapter(topPane, INavigatable.class);
		}
		return null;
	}

	@Override
	public void run() {
		Object topPane = getTopPane();
		if (topPane == null) {
			return;
		}
		if (topPane instanceof INavigatable) {
			((INavigatable) topPane).selectChange(direction);
		}
	}

}
