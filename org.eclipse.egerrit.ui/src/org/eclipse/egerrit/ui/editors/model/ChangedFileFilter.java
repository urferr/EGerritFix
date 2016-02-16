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

import java.util.Map;

import org.eclipse.egerrit.internal.model.FileInfo;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

public class ChangedFileFilter extends ViewerFilter {
	Map<String, FileInfo> toShow = null;

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (toShow == null) {
			return true;
		}
		if (element instanceof GerritDiffNodeTwoRevisions) {
			String path = ((GerritDiffNodeTwoRevisions) element).getFileInfo().getPath();
			if (toShow.containsKey(path)) {
				return true;
			}
		}
		return false;
	}

	public void setFilesToShow(Map<String, FileInfo> toShow) {
		this.toShow = toShow;
	}
}
