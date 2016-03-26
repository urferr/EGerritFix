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

import org.eclipse.egerrit.ui.EGerritUIPlugin;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.TreeViewer;

/**
 * Implements the toolbar action to filter the files with comments
 */
public class ShowCommentedFileAction extends Action {
	private static final String COMMENT_FILTER = "icons/showComments.gif"; //$NON-NLS-1$

	private boolean filterOn = false;

	private Supplier<TreeViewer> viewer;

	private CommentedFilesFilter filter;

	public ShowCommentedFileAction(Supplier<TreeViewer> viewerRef) {
		this.viewer = viewerRef;
		this.filter = new CommentedFilesFilter();
		setDescription("Show/hide the files without comments and drafts");
		setToolTipText("Show/hide the files without comments and drafts");
		setChecked(filterOn);
		setImageDescriptor(EGerritUIPlugin.getImageDescriptor(COMMENT_FILTER));
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