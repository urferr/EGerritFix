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

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.CompareEditorInput;
import org.eclipse.compare.CompareUI;
import org.eclipse.compare.INavigatable;
import org.eclipse.compare.IStreamContentAccessor;
import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.contentmergeviewer.TextMergeViewer;
import org.eclipse.compare.internal.CompareEditorInputNavigator;
import org.eclipse.compare.internal.MergeSourceViewer;
import org.eclipse.compare.structuremergeviewer.DiffNode;
import org.eclipse.compare.structuremergeviewer.DiffTreeViewer;
import org.eclipse.compare.structuremergeviewer.ICompareInput;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.egerrit.core.GerritClient;
import org.eclipse.egerrit.internal.model.ChangeInfo;
import org.eclipse.egerrit.internal.model.CommitInfo;
import org.eclipse.egerrit.internal.model.FileInfo;
import org.eclipse.egerrit.internal.model.RevisionInfo;
import org.eclipse.egerrit.ui.EGerritUIPlugin;
import org.eclipse.egerrit.ui.editors.OpenCompareEditor;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.common.util.WeakInterningHashSet;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextInputListener;
import org.eclipse.jface.text.source.AnnotationPainter;
import org.eclipse.jface.text.source.AnnotationPainter.HighlightingStrategy;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.team.ui.synchronize.SaveableCompareEditorInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GerritMultipleInput extends SaveableCompareEditorInput {

	private static Logger logger = LoggerFactory.getLogger(GerritMultipleInput.class);

	private DiffNode root;

	private ChangeInfo changeInfo;

	private TreeViewer viewer;

	private FileInfo fileToReveal; //The file requested by the user

	private DiffNode nodeToReveal; //The node representing the file we want to reveal

	private String leftSide; //workspace, base, revision number

	private String rightSide; //workspace, base, revision number

	private GerritClient gerrit;

	//Flag used to detect when a failure happened while saving the comments to the server.
	//It can take 3 values -1 (no problem), 0 problem on the left side, 1 problem on the right side
	private byte problemSavingChanges = -1;

	private Runnable postSaveListener;

	/**
	 * Define an input for the compare editor. Left side represents what to display in the left side of the editor
	 * (workspace, revisionId, base) Right side represents what to display in the left side of the editor (workspace,
	 * revisionId, base)
	 */
	public GerritMultipleInput(String leftSide, String rightSide, ChangeInfo changeInfo, GerritClient gerrit,
			FileInfo toReveal) {
		super(initConfiguration(), null);

		Assert.isNotNull(leftSide);
		Assert.isNotNull(rightSide);
		Assert.isNotNull(changeInfo);

		this.leftSide = leftSide;
		this.rightSide = rightSide;
		this.changeInfo = changeInfo;
		this.fileToReveal = toReveal;
		this.gerrit = gerrit;

		root = new DiffNode(GerritDifferences.NO_CHANGE) {
			@Override
			public boolean hasChildren() {
				return true;
			}
		};
	}

	private static CompareConfiguration initConfiguration() {
		CompareConfiguration config = new CompareConfiguration();
		config.setDefaultLabelProvider(new GerritCompareInputLabelProvider());
		return config;
	}

	@Override
	protected Object prepareInput(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
		if (leftSide.equals("BASE")) {
			computeDifferencesWithBase(monitor);
		} else if (leftSide.equals("WORKSPACE")) {
			computeDifferencesWithWorkspace(monitor);
		} else {
			computeDifferencesBetweenRevisions(monitor);
		}
		return root;
	}

	private void computeDifferencesBetweenRevisions(IProgressMonitor monitor) {
		RevisionInfo leftRevision = changeInfo.getRevisions().get(leftSide);
		RevisionInfo rightRevision = changeInfo.getRevisions().get(rightSide);
		EMap<String, FileInfo> leftFiles = leftRevision.getFiles();
		for (FileInfo rightFile : rightRevision.getFiles().values()) {
			String fileName = rightFile.getPath();
			GerritDiffNodeTwoRevisions node = new GerritDiffNodeTwoRevisions(getDifferenceFlag(rightFile));
			node.setRight(new CompareItemFactory(gerrit).createCompareItemFromRevision(fileName, changeInfo.getId(),
					rightFile, monitor));
			node.setFileInfo(rightFile);
			FileInfo leftFile = getFileWithName(getOldPathOrPath(rightFile), leftFiles);
			if (leftFile != null) {
				node.setLeft(new CompareItemFactory(gerrit).createCompareItemFromRevision(fileName, changeInfo.getId(),
						leftFile, monitor));
				node.setOtherFileInfo(leftFile);
			} else {
				node.setLeft(new EmptyTypedElement(rightFile.getPath()));
			}
			root.add(node);
			setElementToReveal(node, rightFile);
		}
	}

	private int getDifferenceFlag(FileInfo file) {
		switch (file.getStatus()) {
		case "A": //$NON-NLS-1$
			return GerritDifferences.ADDITION;
		case "R": //$NON-NLS-1$
			return GerritDifferences.RENAMED;
		case "D": //$NON-NLS-1$
			return GerritDifferences.DELETION;
		case "C": //$NON-NLS-1$
			return GerritDifferences.CHANGE;
		default:
			return GerritDifferences.CHANGE;
		}
	}

	private String getOldPathOrPath(FileInfo file) {
		if (file.getOld_path() == null) {
			return file.getPath();
		}
		return file.getOld_path();
	}

	private void computeDifferencesWithWorkspace(IProgressMonitor monitor) {
		RevisionInfo rightRevision = changeInfo.getRevisions().get(rightSide);
		for (FileInfo rightFile : rightRevision.getFiles().values()) {
			String fileName = rightFile.getPath();
			GerritDiffNode node = new GerritDiffNode(getDifferenceFlag(rightFile));
			node.setFileInfo(rightFile);
			node.setRight(new CompareItemFactory(gerrit).createCompareItemFromRevision(fileName, changeInfo.getId(),
					rightFile, monitor));

			IFile workspaceFile = new OpenCompareEditor(gerrit, changeInfo).getCorrespondingWorkspaceFile(rightFile);
			if (workspaceFile == null) {
				workspaceFile = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path("missing/" + fileName));
			}
			node.setLeft(createFileElement(workspaceFile));
			root.add(node);
			setElementToReveal(node, rightFile);
		}
	}

	private void computeDifferencesWithBase(IProgressMonitor monitor) {
		RevisionInfo rightRevision = changeInfo.getRevisions().get(rightSide);
		for (FileInfo rightFile : rightRevision.getFiles().values()) {
			String fileName = rightFile.getPath();
			GerritDiffNode node = new GerritDiffNode(getDifferenceFlag(rightFile));
			node.setFileInfo(rightFile);
			node.setRight(new CompareItemFactory(gerrit).createCompareItemFromRevision(fileName, changeInfo.getId(),
					rightFile, monitor));

			node.setLeft(new CompareItemFactory(gerrit).createCompareItemFromCommit(changeInfo.getProject(),
					getBaseCommitId(rightFile), getOldPathOrPath(rightFile), monitor));
			root.add(node);
			setElementToReveal(node, rightFile);
		}
	}

	//Test if the given node representing the given file should be revealed in the UI
	private void setElementToReveal(GerritDiffNode node, FileInfo file) {
		if (fileToReveal == null) {
			return;
		}
		if (file.getPath().equals(fileToReveal.getPath())) {
			nodeToReveal = node;
			preloadNode(node);
		}
	}

	private void preloadNode(GerritDiffNode node) {
		if (node.getLeft() instanceof IStreamContentAccessor) {
			try {
				((IStreamContentAccessor) node.getLeft()).getContents();
			} catch (CoreException e) {
				logger.debug("Problem preloading left", e); //$NON-NLS-1$
			}
		}

		if (node.getRight() instanceof IStreamContentAccessor) {
			try {
				((IStreamContentAccessor) node.getRight()).getContents();
			} catch (CoreException e) {
				logger.debug("Problem preloading right", e); //$NON-NLS-1$
			}
		}

	}

	@Override
	public String getTitle() {
		return "Compare " + changeInfo.get_number() + " - " + changeInfo.getSubject() + " -- " + getComparisonTitle();
	}

	private String getComparisonTitle() {
		String result = "";
		if (leftSide.equals("BASE") || leftSide.equals("WORKSPACE")) {
			result += leftSide;
		} else {
			result += "Revision " + changeInfo.getRevisions().get(leftSide).get_number();
		}
		result += " / Revision " + changeInfo.getRevisions().get(rightSide).get_number();
		return result;
	}

	private String getBaseCommitId(FileInfo fileInfo) {
		List<CommitInfo> parents = fileInfo.getRevision().getCommit().getParents();
		if (parents == null) {
			return null;
		}
		return parents.get(0).getCommit();
	}

	private FileInfo getFileWithName(String fileName, EMap<String, FileInfo> files) {
		FileInfo result = files.get(fileName);
		if (result != null) {
			return result;
		}

		//Look for the file with the old name
		for (FileInfo file : files.values()) {
			if (fileName.equals(file.getOld_path())) {
				return file;
			}
		}
		return null;
	}

	@Override
	public Viewer createDiffViewer(Composite parent) {
		viewer = new DiffTreeViewer(parent, getCompareConfiguration()) {
			@SuppressWarnings("restriction")
			@Override
			protected void createToolItems(ToolBarManager toolbarManager) {
				// ignore
				toolbarManager.appendToGroup("navigation", new NextPreviousFileAction(INavigatable.NEXT_CHANGE, //$NON-NLS-1$
						(CompareEditorInputNavigator) getNavigator()));
				toolbarManager.appendToGroup("navigation", new NextPreviousFileAction(INavigatable.PREVIOUS_CHANGE, //$NON-NLS-1$
						(CompareEditorInputNavigator) getNavigator()));
				toolbarManager.appendToGroup("modes", new ShowCommentedFileAction(() -> viewer));
			}
		};
		viewer.setLabelProvider(new FileInfoLabelProvider());
		viewer.getTree().setData(CompareUI.COMPARE_VIEWER_TITLE, getComparisonTitle());
//		viewer.setFilters(getFilters());
		viewer.setInput(root);
		if (nodeToReveal != null) {

			StructuredSelection selection = new StructuredSelection(nodeToReveal);
			viewer.setSelection(selection, true);

			callFeed1(selection);
		}
		return viewer;

	}

	private void callFeed1(StructuredSelection nodeToReveal) {
		Class<CompareEditorInput> clazz = CompareEditorInput.class;
		Method feed1Method;
		try {
			feed1Method = clazz.getDeclaredMethod("feed1", ISelection.class); //$NON-NLS-1$
			feed1Method.setAccessible(true);
			feed1Method.invoke(this, nodeToReveal);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			logger.debug("Exception revealing the element selected by the user", nodeToReveal); //$NON-NLS-1$
		}
	}

	class FileInfoLabelProvider extends LabelProvider {

		public FileInfoLabelProvider() {
		}

		@Override
		public String getText(Object element) {
			return ((DiffNode) element).getName();
		}

		@Override
		public Image getImage(Object element) {
			return null;
		}
	}

	@Override
	//We need this so we can hook the mechanism to color the comments
	public Viewer findContentViewer(Viewer oldViewer, ICompareInput input, Composite parent) {
		Viewer newViewer = super.findContentViewer(oldViewer, input, parent);
		if (oldViewer == newViewer) {
			return newViewer;
		}

		if (isCommentable(input.getLeft())) {
			setupCommentColorer(newViewer, 0);
		}
		if (isCommentable(input.getRight())) {
			setupCommentColorer(newViewer, 1);
		}
		return newViewer;
	}

	private boolean isCommentable(ITypedElement element) {
		if (element instanceof PatchSetCompareItem) {
			return true;
		}
		return false;
	}

	private WeakInterningHashSet<SourceViewer> decoratedViewers = new WeakInterningHashSet<>(3);

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
			if (decoratedViewers.contains(sourceViewer)) {
				return;
			}
			decoratedViewers.add(sourceViewer);
			final AnnotationPainter commentPainter = initializeCommentColoring(sourceViewer);

			//Need to see by adding a breakpoint in the painter/limiter
			sourceViewer.addTextInputListener(new ITextInputListener() {
				EditionLimiter editionLimiter = new EditionLimiter(sourceViewer);

				@Override
				public void inputDocumentChanged(IDocument oldInput, IDocument newInput) {
					sourceViewer.addTextPresentationListener(commentPainter);
					sourceViewer.addPainter(commentPainter);
					sourceViewer.getTextWidget().addVerifyListener(editionLimiter);
					if (oldInput instanceof PatchSetCompareItem) {
						((PatchSetCompareItem) oldInput).reset();
					}
				}

				@Override
				public void inputDocumentAboutToBeChanged(IDocument oldInput, IDocument newInput) {
					if (oldInput != null) {
						sourceViewer.removePainter(commentPainter);
						sourceViewer.addTextPresentationListener(commentPainter);
						sourceViewer.getTextWidget().removeVerifyListener(editionLimiter);
					}
				}
			});

		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException t) {
			logger.error("Problem while setting up coloration of comments", t); //$NON-NLS-1$
		}
	}

	private AnnotationPainter initializeCommentColoring(ISourceViewer viewer) {
		AnnotationPainter commentPainter = new AnnotationPainter(viewer, null) {
			@Override
			//Override to force the annotation model to be the one that contains the comments
			protected IAnnotationModel findAnnotationModel(ISourceViewer sourceViewer) {
				if (sourceViewer.getDocument() instanceof PatchSetCompareItem) {
					return ((PatchSetCompareItem) sourceViewer.getDocument()).getEditableComments();
				}
				return null;
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
	public void saveChanges(IProgressMonitor monitor) throws CoreException {
		try {
			super.saveChanges(monitor);
		} catch (RuntimeException ex) {
			//This works hand in hand with the PatchSetCompareItem#setContent method which raises a very specific RuntimeException
			if (PatchSetCompareItem.class.getName().equals(ex.getMessage())) {
				if (ex.getCause().getMessage().equals(((GerritDiffNode) getSelectedEdition()).getLeft())) {
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
		if (postSaveListener != null) {
			postSaveListener.run();
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

	@Override
	protected void fireInputChange() {
		GerritDiffNode savedElement = (GerritDiffNode) getSelectedEdition();
		savedElement.setRight(
				new CompareItemFactory(gerrit).createCompareItemFromRevision(savedElement.getFileInfo().getPath(),
						changeInfo.getId(), savedElement.getFileInfo(), new NullProgressMonitor()));

		if (!(savedElement.getLeft() instanceof EmptyTypedElement)) {
			//TODO Review / reuse the logic from the code that creates the revision infos?
			if (savedElement instanceof GerritDiffNodeTwoRevisions) {
				GerritDiffNodeTwoRevisions twoRevionsNode = (GerritDiffNodeTwoRevisions) savedElement;
				twoRevionsNode.setLeft(new CompareItemFactory(gerrit).createCompareItemFromRevision(
						twoRevionsNode.getOtherFileInfo().getPath(), changeInfo.getId(),
						twoRevionsNode.getOtherFileInfo(), new NullProgressMonitor()));
			}
		}
		savedElement.fireChange();
	}

	@Override
	protected ICompareInput prepareCompareInput(IProgressMonitor monitor)
			throws InvocationTargetException, InterruptedException {
		// ignore
		return null;
	}
}
