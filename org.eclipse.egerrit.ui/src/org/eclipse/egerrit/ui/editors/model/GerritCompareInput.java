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
import org.eclipse.compare.ICompareInputLabelProvider;
import org.eclipse.compare.structuremergeviewer.Differencer;
import org.eclipse.compare.structuremergeviewer.ICompareInput;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.egerrit.core.GerritClient;
import org.eclipse.egerrit.core.rest.FileInfo;
import org.eclipse.egerrit.ui.EGerritUIPlugin;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.team.ui.synchronize.SaveableCompareEditorInput;

/**
 * @since 1.0
 */
public class GerritCompareInput extends SaveableCompareEditorInput {

	private String changeId;

	private GerritClient gerrit;

	private IFile left;

	private FileInfo fileInfo;

	private String file;

	private boolean problemSavingChanges = false;

	private GerritDiffNode diffNode;

	public GerritCompareInput(IFile left, String changeId, FileInfo info, GerritClient gerrit) {
		super(createEditorConfiguration(), null);
		this.left = left;
		this.changeId = changeId;
		this.fileInfo = info;
		this.file = fileInfo.getold_path();
		this.gerrit = gerrit;
	}

	private IFile getLeft() {
		if (left != null) {
			return left;
		}
		return ResourcesPlugin.getWorkspace().getRoot().getFile(new Path("missing/" + file)); //$NON-NLS-1$
	}

	@Override
	/**
	 * This method is made public for testing purpose
	 */
	public ICompareInput prepareCompareInput(IProgressMonitor pm) {
		CompareItem right = new CompareItemFactory(gerrit).createCompareItem(file, changeId, fileInfo, pm);
		diffNode = new GerritDiffNode(null, Differencer.ADDITION, null, createFileElement(getLeft()), right);
		return diffNode;
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
		CompareItem right = new CompareItemFactory(gerrit).createCompareItem(file, changeId, fileInfo,
				new NullProgressMonitor());
		((GerritDiffNode) getCompareResult()).setRight(right);
		((GerritDiffNode) getCompareResult()).fireChange();
	}

	@Override
	public void saveChanges(IProgressMonitor monitor) throws CoreException {
		try {
			super.saveChanges(monitor);
		} catch (RuntimeException ex) {
			//This works hand in hand with the CompareItem#setContent method which raises a very specific RuntimeException
			if (CompareItem.class.getName().equals(ex.getMessage())) {
				problemSavingChanges = true;
				setRightDirty(true);
				throw new CoreException(new org.eclipse.core.runtime.Status(IStatus.ERROR, EGerritUIPlugin.PLUGIN_ID,
						"A problem occurred while sending the changes to the gerrit server"));
			} else {
				//When it is not our exception we just pass it on.
				throw ex;
			}
		}
	}

	@Override
	public void setDirty(boolean dirty) {
		if (problemSavingChanges) {
			super.setDirty(true);
			problemSavingChanges = false;
			setRightDirty(true);
		} else {
			super.setDirty(dirty);
		}
	}

	private static CompareConfiguration createEditorConfiguration() {
		CompareConfiguration config = new CompareConfiguration();
		config.setDefaultLabelProvider(new ICompareInputLabelProvider() {

			@Override
			public void removeListener(ILabelProviderListener listener) {
				// ignore
			}

			@Override
			public boolean isLabelProperty(Object element, String property) {
				// ignore
				return false;
			}

			@Override
			public void dispose() {
				// ignore
			}

			@Override
			public void addListener(ILabelProviderListener listener) {
				// ignore
			}

			@Override
			public String getText(Object element) {
				// ignore
				return null;
			}

			@Override
			public Image getImage(Object element) {
				// ignore
				return null;
			}

			@Override
			public String getRightLabel(Object input) {
				if (!(input instanceof GerritDiffNode)) {
					return null;
				}
				if (((GerritDiffNode) input).getRight() instanceof CompareItem) {
					return getLabel((CompareItem) ((GerritDiffNode) input).getRight());
				}
				return null;

			}

			@Override
			public Image getRightImage(Object input) {
				// ignore
				return null;
			}

			@Override
			public String getLeftLabel(Object input) {
				if (!(input instanceof GerritDiffNode)) {
					return null;
				}
				if (((GerritDiffNode) input).getLeft() instanceof CompareItem) {
					return getLabel((CompareItem) ((GerritDiffNode) input).getLeft());
				}
				return ((GerritDiffNode) input).getRight().getName();
			}

			private String getLabel(CompareItem left) {
				FileInfo fileInfo = left.getFileInfo();
				return "Patch set " + fileInfo.getContainingRevisionInfo().getNumber() + " - " //$NON-NLS-2$
						+ fileInfo.getold_path();
			}

			@Override
			public Image getLeftImage(Object input) {
				// ignore
				return null;
			}

			@Override
			public String getAncestorLabel(Object input) {
				// ignore
				return null;
			}

			@Override
			public Image getAncestorImage(Object input) {
				// ignore
				return null;
			}
		});
		config.setRightEditable(true);
		return config;
	}
}
