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
import org.eclipse.egerrit.ui.EGerritUIPlugin;
import org.eclipse.jface.action.Action;

@SuppressWarnings("restriction")
public class NextPreviousFileAction extends Action {
	private static String DOWN_ARROW = "icons/compare_next_file.gif"; //$NON-NLS-1$

	private static String UP_ARROW = "icons/compare_previous_file.gif"; //$NON-NLS-1$

	private int direction;

	private GerritMultipleInput compareInput;

	public NextPreviousFileAction(int direction, GerritMultipleInput compareInput) {
		this.direction = direction;
		this.compareInput = compareInput;
		if (direction == INavigatable.NEXT_CHANGE) {
			setAccelerator(Action.convertAccelerator("]"));
			setDescription("Go the next file in the review");
			setToolTipText("Go the next file in the review");
			setImageDescriptor(EGerritUIPlugin.getImageDescriptor(DOWN_ARROW));
		} else {
			setAccelerator(Action.convertAccelerator("["));
			setDescription("Go the previous file in the review");
			setToolTipText("Go the previous file in the review");
			setImageDescriptor(EGerritUIPlugin.getImageDescriptor(UP_ARROW));
		}
		setEnabled(true);
	}

	//Helper method to return the top left pane
	private Object getTopPane() {
		Object topPane = ((CompareEditorInputNavigator) compareInput.getNavigator()).getPanes()[0];
		return Utilities.getAdapter(topPane, INavigatable.class);
	}

	@Override
	public void run() {
		Object topPane = getTopPane();
		if (topPane instanceof INavigatable) {
			((INavigatable) topPane).selectChange(direction);
		}
	}

}
