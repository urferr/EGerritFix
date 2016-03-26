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

import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.structuremergeviewer.DiffNode;
import org.eclipse.compare.structuremergeviewer.IDiffContainer;
import org.eclipse.egerrit.internal.model.FileInfo;

/**
 * GerritDiffNode is used as input to the compare editor.
 * <p>
 * It is necessary in order to be able to react to saves (e.g. reload the editor)
 */
public class GerritDiffNode extends DiffNode {

	private FileInfo fileInfo;

	private GerritMultipleInput input;

	public GerritDiffNode(int kind) {
		super(kind);
	}

	public GerritDiffNode(IDiffContainer parent, int kind, ITypedElement ancestor, ITypedElement left,
			ITypedElement right) {
		super(parent, kind, ancestor, left, right);
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
		if (fileInfo == null) {
			if (input != null) {
				return input.getName();
			} else {
				return "<no name>"; //$NON-NLS-1$
			}
		} else if (getKind() != GerritDifferences.RENAMED) {
			return fileInfo.getPath();
		}
		return String.format("%s (was %s)", fileInfo.getPath(), fileInfo.getOld_path()); //$NON-NLS-1$
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		//Do nothing. This is just here to make sure databinding is not throwing exception
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		//Do nothing. This is just here to make sure databinding is not throwing exception
	}

	// save the GerritMultipleInput for this instance.
	public void setInput(GerritMultipleInput gerritMultipleInput) {
		input = gerritMultipleInput;

	}
}
