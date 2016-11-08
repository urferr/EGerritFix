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

import java.util.function.Supplier;

import org.eclipse.egerrit.internal.ui.EGerritImages;
import org.eclipse.egerrit.internal.ui.utils.Messages;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.TreeViewer;

/**
 * Implements the toolbar action to filter the files with comments
 */
public class ShowCommentedFileAction extends Action {
	private boolean filterOn = false;

	private Supplier<TreeViewer> viewer;

	private CommentedFilesFilter filter;

	public ShowCommentedFileAction(Supplier<TreeViewer> viewerRef) {
		this.viewer = viewerRef;
		this.filter = new CommentedFilesFilter();
		setDescription(Messages.ShowCommentedFileAction_0);
		setToolTipText(Messages.ShowCommentedFileAction_1);
		setChecked(filterOn);
		setImageDescriptor(EGerritImages.getDescriptor(EGerritImages.COMMENT_FILTER));
		setActionDefinitionId("org.eclipse.egerrit.internal.ui.compare.showCommentedFile"); //$NON-NLS-1$
	}

	@Override
	public void run() {
		if (filterOn) {
			viewer.get().removeFilter(filter);
		} else {
			viewer.get().addFilter(filter);
		}
		filterOn = !filterOn;
		setChecked(filterOn);
	}
}