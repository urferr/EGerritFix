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
import java.util.List;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.ITypedElement;
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
import org.eclipse.egerrit.core.rest.CommitInfo;
import org.eclipse.egerrit.core.rest.FileInfo;
import org.eclipse.egerrit.ui.EGerritUIPlugin;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextInputListener;
import org.eclipse.jface.text.ITextViewerExtension4;
import org.eclipse.jface.text.source.AnnotationPainter;
import org.eclipse.jface.text.source.AnnotationPainter.HighlightingStrategy;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.team.ui.synchronize.SaveableCompareEditorInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Input to open a compare editor on a gerrit review
 *
 * @since 1.0
 */
public class GerritCompareInput extends SaveableCompareEditorInput {
	private Logger logger = LoggerFactory.getLogger(GerritCompareInput.class);

	private String changeId;

	private GerritClient gerrit;

	//Represents a local file in the workspace
	private IFile left;

	//Represents a remote file from a revision presented on the left
	private FileInfo leftInfo;

	//Represents a remote file from a revision file presented on the right
	private FileInfo rightInfo;

	private String file;

	private String projectId;

	//Flag used to detect when a failure happened while saving the comments to the server.
	//It can take 3 values -1 (no problem), 0 problem on the left side, 1 problem on the right side
	private byte problemSavingChanges = -1;

	private GerritDiffNode diffNode;

	/**
	 * Create a compare input used to compare a workspace file with a remove file
	 */
	public GerritCompareInput(IFile left, String changeId, FileInfo right, GerritClient gerrit) {
		super(createEditorConfiguration(true, gerrit), null);
		this.left = left;
		this.changeId = changeId;
		this.rightInfo = right;
		this.file = rightInfo.getold_path();
		this.gerrit = gerrit;
	}

	/**
	 * Create a compare input used to compare two remote files
	 */
	public GerritCompareInput(String changeId, FileInfo left, FileInfo right, GerritClient gerrit) {
		super(createEditorConfiguration(true, gerrit), null);
		this.leftInfo = left;
		this.changeId = changeId;
		this.rightInfo = right;
		this.file = rightInfo.getold_path();
		this.gerrit = gerrit;
	}

	/**
	 * Create a compare input used to compare a file from a commit with a remote file
	 */
	public GerritCompareInput(String changeId, String projectId, FileInfo right, GerritClient gerrit) {
		super(createEditorConfiguration(false, gerrit), null);
		this.changeId = changeId;
		this.projectId = projectId;
		this.rightInfo = right;
		this.file = rightInfo.getold_path();
		this.gerrit = gerrit;
	}

	private IFile getLeft() {
		if (left != null) {
			return left;
		}
		return ResourcesPlugin.getWorkspace().getRoot().getFile(new Path("missing/" + file)); //$NON-NLS-1$
	}

	/**
	 * This method is made public for testing purpose
	 */
	@Override
	public ICompareInput prepareCompareInput(IProgressMonitor pm) {
		ITypedElement[] inputs = createCompareItems(pm);
		diffNode = new GerritDiffNode(null, Differencer.ADDITION, null, inputs[0], inputs[1]);
		return diffNode;
	}

	private ITypedElement[] createCompareItems(IProgressMonitor pm) {
		ITypedElement leftInput = null;
		ITypedElement rightInput = null;

		if (left != null) {
			leftInput = createFileElement(getLeft());
		} else if (leftInfo != null) {
			leftInput = new CompareItemFactory(gerrit).createCompareItem(file, changeId, leftInfo, pm);
		} else if (projectId != null) {
			leftInput = new CompareItemFactory(gerrit).createSimpleCompareItem(projectId, getBaseCommitId(),
					rightInfo.getold_path(), pm);
		}

		rightInput = new CompareItemFactory(gerrit).createCompareItem(file, changeId, rightInfo, pm);
		return new ITypedElement[] { leftInput, rightInput };
	}

	private String getBaseCommitId() {
		List<CommitInfo> parents = rightInfo.getContainingRevisionInfo().getCommit().getParents();
		if (parents == null) {
			return null;
		}
		return parents.get(0).getCommit();
	}

	@Override
	public String getTitle() {
		return file;
	}

	@Override
	protected void fireInputChange() {
		ITypedElement[] inputs = createCompareItems(new NullProgressMonitor());
		((GerritDiffNode) getCompareResult()).setLeft(inputs[0]);
		((GerritDiffNode) getCompareResult()).setRight(inputs[1]);
		((GerritDiffNode) getCompareResult()).fireChange();
	}

	@Override
	public void saveChanges(IProgressMonitor monitor) throws CoreException {
		try {
			super.saveChanges(monitor);
		} catch (RuntimeException ex) {
			//This works hand in hand with the PatchSetCompareItem#setContent method which raises a very specific RuntimeException
			if (PatchSetCompareItem.class.getName().equals(ex.getMessage())) {
				if (ex.getCause().getMessage().equals(((GerritDiffNode) getCompareResult()).getLeft())) {
					problemSavingChanges = 0;
					setLeftDirty(true);
				} else {
					problemSavingChanges = 1;
					setRightDirty(true);
				}
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
		if (problemSavingChanges == 0) {
			super.setDirty(true);
			problemSavingChanges = -1;
			setLeftDirty(true);
		} else if (problemSavingChanges == 1) {
			super.setDirty(true);
			problemSavingChanges = -1;
			setRightDirty(true);
		} else {
			super.setDirty(dirty);
		}
	}

	private static CompareConfiguration createEditorConfiguration(boolean leftEditable, GerritClient gerritClient) {
		CompareConfiguration config = new CompareConfiguration();
		config.setDefaultLabelProvider(new GerritCompareInputLabelProvider());
		if (gerritClient.getRepository().getServerInfo().isAnonymous()) {
			config.setRightEditable(false);
			config.setLeftEditable(false);
		} else {
			config.setRightEditable(true);
			config.setLeftEditable(leftEditable);
		}
		return config;
	}

	@Override
	//We need this so we can hook the mechanism to color the comments
	public Viewer findContentViewer(Viewer oldViewer, ICompareInput input, Composite parent) {
		Viewer contentViewer = super.findContentViewer(oldViewer, input, parent);
		if (isCommentable(input.getLeft())) {
			setupCommentColorer(contentViewer, 0);
		}
		if (isCommentable(input.getRight())) {
			setupCommentColorer(contentViewer, 1);
		}
		return contentViewer;
	}

	private boolean isCommentable(ITypedElement element) {
		if (element instanceof PatchSetCompareItem) {
			return true;
		}
		return false;
	}

	private void setupCommentColorer(Viewer contentViewer, int side) {
		if (!(contentViewer instanceof TextMergeViewer)) {
			return;
		}

		//Navigate from the top level widget of the compare editor down to the right pane of the editor
		//to participate in the coloring of the text: see method applyTextPresentation
		TextMergeViewer textMergeViewer = (TextMergeViewer) contentViewer;
		try {
			Class<TextMergeViewer> clazz = TextMergeViewer.class;
			Field declaredField = clazz.getDeclaredField(side == 0 ? "fLeft" : "fRight"); //$NON-NLS-1$ //$NON-NLS-2$
			declaredField.setAccessible(true);
			@SuppressWarnings("restriction")
			MergeSourceViewer rightSourceViewer = (MergeSourceViewer) declaredField.get(textMergeViewer);

			@SuppressWarnings("restriction")
			Field sourceViewerField = MergeSourceViewer.class.getDeclaredField("fSourceViewer"); //$NON-NLS-1$
			sourceViewerField.setAccessible(true);
			final SourceViewer sourceViewer = (SourceViewer) sourceViewerField.get(rightSourceViewer);
			final AnnotationPainter commentPainter = initializeCommentColoring(sourceViewer);

			sourceViewer.addTextInputListener(new ITextInputListener() {
				@Override
				public void inputDocumentChanged(IDocument oldInput, IDocument newInput) {
					if (sourceViewer instanceof ITextViewerExtension4) {
						((ITextViewerExtension4) sourceViewer).addTextPresentationListener(commentPainter);
					}
					sourceViewer.addPainter(commentPainter);
				}

				@Override
				public void inputDocumentAboutToBeChanged(IDocument oldInput, IDocument newInput) {
					if (oldInput != null) {
						sourceViewer.removePainter(commentPainter);
						if (sourceViewer instanceof ITextViewerExtension4) {
							((ITextViewerExtension4) sourceViewer).addTextPresentationListener(commentPainter);
						}
					}
				}
			});

			EditionLimiter editionLimiter = new EditionLimiter(sourceViewer);
			sourceViewer.getTextWidget().addVerifyListener(editionLimiter);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException t) {
			logger.error("Problem while setting up coloration of comments", t); //$NON-NLS-1$
		}
	}

	private AnnotationPainter initializeCommentColoring(ISourceViewer viewer) {
		AnnotationPainter commentPainter = new AnnotationPainter(viewer, null) {
			@Override
			//Override to force the annotation model to be the one that contains the comments
			protected IAnnotationModel findAnnotationModel(ISourceViewer sourceViewer) {
				return ((PatchSetCompareItem) sourceViewer.getDocument()).getEditableComments();
			}
		};
		Object strategyID = new Object();
		HighlightingStrategy paintingStrategy = new AnnotationPainter.HighlightingStrategy();
		commentPainter.addTextStyleStrategy(strategyID, paintingStrategy);
		commentPainter.addAnnotationType(GerritCommentAnnotation.TYPE, strategyID);
		commentPainter.setAnnotationTypeColor(GerritCommentAnnotation.TYPE,
				Display.getCurrent().getSystemColor(SWT.COLOR_YELLOW));
		commentPainter.addHighlightAnnotationType(strategyID);
		return commentPainter;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((changeId == null) ? 0 : changeId.hashCode());
		result = prime * result + ((file == null) ? 0 : file.hashCode());
		result = prime * result + ((left == null) ? 0 : left.hashCode());
		result = prime * result + ((leftInfo == null) ? 0 : leftInfo.hashCode());
		result = prime * result + ((projectId == null) ? 0 : projectId.hashCode());
		result = prime * result + ((rightInfo == null) ? 0 : rightInfo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		GerritCompareInput other = (GerritCompareInput) obj;
		if (changeId == null) {
			if (other.changeId != null) {
				return false;
			}
		} else if (!changeId.equals(other.changeId)) {
			return false;
		}
		if (file == null) {
			if (other.file != null) {
				return false;
			}
		} else if (!file.equals(other.file)) {
			return false;
		}
		if (left == null) {
			if (other.left != null) {
				return false;
			}
		} else if (!left.equals(other.left)) {
			return false;
		}
		if (leftInfo == null) {
			if (other.leftInfo != null) {
				return false;
			}
		} else if (!leftInfo.equals(other.leftInfo)) {
			return false;
		}
		if (projectId == null) {
			if (other.projectId != null) {
				return false;
			}
		} else if (!projectId.equals(other.projectId)) {
			return false;
		}
		if (rightInfo == null) {
			if (other.rightInfo != null) {
				return false;
			}
		} else if (!rightInfo.equals(other.rightInfo)) {
			return false;
		}
		return true;
	}

}
