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

import java.lang.reflect.Field;
import java.util.Iterator;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.ICompareInputLabelProvider;
import org.eclipse.compare.contentmergeviewer.TextMergeViewer;
import org.eclipse.compare.internal.MergeSourceViewer;
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
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextPresentationListener;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.TextPresentation;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.AnnotationModel;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.team.ui.synchronize.SaveableCompareEditorInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Input to open a compare editor on a gerrit review 
 * @since 1.0
 */
public class GerritCompareInput extends SaveableCompareEditorInput {
	private Logger logger = LoggerFactory.getLogger(GerritCompareInput.class);

	private String changeId;

	private GerritClient gerrit;

	private IFile left;

	private FileInfo fileInfo;

	private String file;

	private boolean problemSavingChanges = false;

	private GerritDiffNode diffNode;

	//This flag indicates whether the initial syntax coloring should be done.
	//This is necessary so we don't spend our time recoloring things that have been already colored.
	//Also because if we do recolor things, it throw things off when the user starts editing.
	final private Boolean[] performInitialColoring = new Boolean[] { Boolean.FALSE };

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
		performInitialColoring[0] = Boolean.TRUE; //We enable coloring when the input is changed
		try {
			CompareItem right = new CompareItemFactory(gerrit).createCompareItem(file, changeId, fileInfo,
					new NullProgressMonitor());
			((GerritDiffNode) getCompareResult()).setRight(right);
			((GerritDiffNode) getCompareResult()).fireChange();
		} finally {
			performInitialColoring[0] = Boolean.FALSE;
		}
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

	@Override
	//We need this so we can hook the mechanism to color the comments
	public Viewer findContentViewer(Viewer oldViewer, ICompareInput input, Composite parent) {
		Viewer contentViewer = super.findContentViewer(oldViewer, input, parent);
		setupCommentColorer(contentViewer);
		return contentViewer;
	}

	private void setupCommentColorer(Viewer contentViewer) {
		if (!(contentViewer instanceof TextMergeViewer)) {
			return;
		}

		//Navigate from the top level widget of the compare editor down to the right pane of the editor
		//to participate in the coloring of the text: see method applyTextPresentation
		TextMergeViewer textMergeViewer = (TextMergeViewer) contentViewer;
		try {
			Class<TextMergeViewer> clazz = TextMergeViewer.class;
			Field declaredField = clazz.getDeclaredField("fRight"); //$NON-NLS-1$
			declaredField.setAccessible(true);
			MergeSourceViewer rightSourceViewer = (MergeSourceViewer) declaredField.get(textMergeViewer);

			Field sourceViewerField = MergeSourceViewer.class.getDeclaredField("fSourceViewer");
			sourceViewerField.setAccessible(true);
			final SourceViewer sourceViewer = (SourceViewer) sourceViewerField.get(rightSourceViewer);
			sourceViewer.addTextPresentationListener(new ITextPresentationListener() {
				@Override
				//Perform the coloration of comments
				public void applyTextPresentation(TextPresentation textPresentation) {
					if (performInitialColoring[0]) {
						CompareItem coloredDocument = (CompareItem) sourceViewer.getDocument();
						AnnotationModel commentsToColor = coloredDocument.getEditableComments();
						Iterator commentsIterator = commentsToColor.getAnnotationIterator();
						while (commentsIterator.hasNext()) {
							Annotation comment = (Annotation) commentsIterator.next();
							Position commentPosition = commentsToColor.getPosition(comment);
							textPresentation.replaceStyleRange(
									createCommentStyle(commentPosition.getOffset(), commentPosition.getLength()));
						}
					} else {
						IRegion newRegion = textPresentation.getCoverage();
						textPresentation
								.replaceStyleRange(createCommentStyle(newRegion.getOffset(), newRegion.getLength()));
					}
				}
			});
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException t) {
			logger.error("Problem while setting up coloration of comments", t); //$NON-NLS-1$
		}
	}

	private StyleRange createCommentStyle(int start, int length) {
		return new StyleRange(start, length, Display.getCurrent().getSystemColor(SWT.COLOR_BLACK),
				Display.getCurrent().getSystemColor(SWT.COLOR_YELLOW));
	}

	@Override
	public Control createContents(Composite parent) {
		performInitialColoring[0] = Boolean.TRUE; //We enable coloring when the editor is first created
		try {
			return super.createContents(parent);
		} finally {
			performInitialColoring[0] = Boolean.FALSE;
		}
	}
}
