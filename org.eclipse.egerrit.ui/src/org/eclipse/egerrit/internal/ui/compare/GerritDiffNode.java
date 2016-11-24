/*******************************************************************************
 * Copyright (c) 2015 Ericsson AB.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson - initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.internal.ui.compare;

import java.beans.PropertyChangeListener;

import org.eclipse.compare.structuremergeviewer.DiffNode;
import org.eclipse.egerrit.internal.model.FileInfo;

/**
 * GerritDiffNode is used as input to the compare editor.
 * <p>
 * It is necessary in order to be able to react to saves (e.g. reload the editor)
 */
public class GerritDiffNode extends DiffNode {

	//The FileInfo object obtained from a server side diff.
	private FileInfo diffFileInfo;

	//The FileInfo object corresponding to the diffFileInfo but found in the revision.
	//This object is used through the compare editor operations like posting/retrieving comments, marking files as viewed, etc.
	private FileInfo fileInfo;

	public GerritDiffNode(int kind) {
		super(kind);
	}

	@Override
	//This method makes the original fireChange method public
	public void fireChange() {
		super.fireChange();
	}

	public void setFileInfo(FileInfo info) {
		fileInfo = info;
	}

	public FileInfo getFileInfo() {
		return fileInfo;
	}

	@Override
	public String getName() {
		return getLabelName(false);
	}

	public String getLabelName(boolean nameFirst) {
		String path = ""; //$NON-NLS-1$
		String oldPath = null;
		if (diffFileInfo != null) {
			path = diffFileInfo.getPath();
			oldPath = diffFileInfo.getOld_path();
		} else {
			path = fileInfo.getPath();
			oldPath = fileInfo.getOld_path();
		}
		if (fileInfo.getPath() != null) {
			path = fileInfo.getPath();
		} else {
			path = ""; //$NON-NLS-1$
		}
		if (nameFirst) {
			int index = path.lastIndexOf("/"); //$NON-NLS-1$
			if (index != -1) {
				String fileName = path.substring(index + 1);
				path = fileName + " - " + path.substring(0, index); //$NON-NLS-1$
			}
		}
		if (getKind() != GerritDifferences.RENAMED) {
			return path;
		}
		return String.format("%s (was %s)", path, oldPath); //$NON-NLS-1$
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		//Do nothing. This is just here to make sure databinding is not throwing exception
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		//Do nothing. This is just here to make sure databinding is not throwing exception
	}

	public FileInfo getDiffFileInfo() {
		return diffFileInfo;
	}

	public void setDiffFileInfo(FileInfo diffFileInfo) {
		this.diffFileInfo = diffFileInfo;
	}
}
