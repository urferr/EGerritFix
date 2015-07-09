/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Guy Perron - initial API and implementation
 *
 *******************************************************************************/

package org.eclipse.egerrit.ui.editors.model;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.structuremergeviewer.DiffNode;
import org.eclipse.compare.structuremergeviewer.Differencer;
import org.eclipse.compare.structuremergeviewer.ICompareInput;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.egerrit.core.GerritRepository;
import org.eclipse.egerrit.ui.editors.OpenCompareEditor;
import org.eclipse.team.ui.synchronize.SaveableCompareEditorInput;

/**
 * @since 1.0
 */
public class GerritCompareInput extends SaveableCompareEditorInput {

	private String changeId;

	private String revisionId;

	private GerritRepository gerrit;

	private IFile left;

	private String file;

	public GerritCompareInput(IFile left, String changeId, String revisionId, String file, GerritRepository gerrit) {
		super(new CompareConfiguration(), null);
		this.left = left;
		this.changeId = changeId;
		this.revisionId = revisionId;
		this.file = file;
		this.gerrit = gerrit;
	}

	private IFile getLeft() {
		if (left != null) {
			return left;
		}
		return ResourcesPlugin.getWorkspace().getRoot().getFile(new Path("missing/" + file)); //$NON-NLS-1$
	}

	@Override
	//Note this method is made public for testing purpose
	public ICompareInput prepareCompareInput(IProgressMonitor pm) {
		CompareItem right = new CompareItem(file,
				OpenCompareEditor.getFilesContent(gerrit, changeId, revisionId, file, pm), 0);
		return new DiffNode(null, Differencer.ADDITION, null, createFileElement(getLeft()), right);
	}

	@Override
	public String getTitle() {
		if (left != null) {
			return super.getTitle();
		}
		return file + " / (no matching file in workspace)";
	}

	@Override
	protected void fireInputChange() {
		// TODO Need to see if we want to do something with this
	}
}
