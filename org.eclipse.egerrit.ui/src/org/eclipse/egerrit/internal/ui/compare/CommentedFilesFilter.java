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

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

public class CommentedFilesFilter extends ViewerFilter {

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (!(element instanceof GerritDiffNode)) {
			return false;
		}

		GerritDiffNode gerritNode = (GerritDiffNode) element;
		if (gerritNode.getLeft() instanceof CommentableCompareItem) {
			CommentableCompareItem leftFile = (CommentableCompareItem) gerritNode.getLeft();
			if (leftFile != null) {
				if ((leftFile.getComments().size() + leftFile.getDrafts().size()) > 0) {
					return true;
				}
			}
		}
		if (gerritNode.getRight() instanceof CommentableCompareItem) {
			CommentableCompareItem rightFile = (CommentableCompareItem) gerritNode.getRight();
			if (rightFile != null) {
				if ((rightFile.getComments().size() + rightFile.getDrafts().size()) > 0) {
					return true;
				}
			}
		}
		return false;
	}

}
