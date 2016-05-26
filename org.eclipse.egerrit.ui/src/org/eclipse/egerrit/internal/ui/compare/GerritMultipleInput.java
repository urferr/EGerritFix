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

import java.beans.PropertyChangeListener;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.CompareViewerPane;
import org.eclipse.compare.IStreamContentAccessor;
import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.contentmergeviewer.TextMergeViewer;
import org.eclipse.compare.internal.MergeSourceViewer;
import org.eclipse.compare.structuremergeviewer.DiffNode;
import org.eclipse.compare.structuremergeviewer.ICompareInput;
import org.eclipse.compare.structuremergeviewer.IDiffElement;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.egerrit.internal.core.GerritClient;
import org.eclipse.egerrit.internal.core.exception.EGerritException;
import org.eclipse.egerrit.internal.model.ChangeInfo;
import org.eclipse.egerrit.internal.model.CommitInfo;
import org.eclipse.egerrit.internal.model.FileInfo;
import org.eclipse.egerrit.internal.model.RevisionInfo;
import org.eclipse.egerrit.internal.model.impl.StringToFileInfoImpl;
import org.eclipse.egerrit.internal.ui.EGerritUIPlugin;
import org.eclipse.egerrit.internal.ui.editors.OpenCompareEditor;
import org.eclipse.egerrit.internal.ui.editors.QueryHelpers;
import org.eclipse.egerrit.internal.ui.utils.Messages;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.common.util.WeakInterningHashSet;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextInputListener;
import org.eclipse.jface.text.source.AnnotationPainter;
import org.eclipse.jface.text.source.AnnotationPainter.HighlightingStrategy;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.team.internal.ui.synchronize.LocalResourceTypedElement;
import org.eclipse.team.ui.synchronize.SaveableCompareEditorInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GerritMultipleInput extends SaveableCompareEditorInput {

	private static Logger logger = LoggerFactory.getLogger(GerritMultipleInput.class);

	private GerritDiffNode root;

	private ChangeInfo changeInfo;

	private FileInfo fileToReveal; //The file requested by the user

	DiffNode nodeToReveal; //The node representing the file we want to reveal

	private String leftSide; //workspace, base, revision number

	private String rightSide; //workspace, base, revision number

	GerritClient gerritClient;

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
		this.gerritClient = gerrit;

		root = new GerritDiffNode(GerritDifferences.NO_CHANGE) {

			@Override
			public boolean hasChildren() {
				return true;
			}

			@Override
			public void addPropertyChangeListener(PropertyChangeListener listener) {
				//Do nothing. This is just here to make sure databinding is not throwing exception
			}

			@Override
			public void removePropertyChangeListener(PropertyChangeListener listener) {
				//Do nothing. This is just here to make sure databinding is not throwing exception
			}

		};
		root.setInput(GerritMultipleInput.this);
	}

	private static CompareConfiguration initConfiguration() {
		CompareConfiguration config = new CompareConfiguration();
		config.setDefaultLabelProvider(new GerritCompareInputLabelProvider());
		return config;
	}

	@Override
	//It supports the following scenarios
	//base <-> workspace
	//base <-> revision
	//revision <-> revision
	//workspace <-> base
	protected Object prepareInput(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
		resetRoot();
		nodeToReveal = null;
		if (leftSide.equals(rightSide)) {
			return root;
		}

		if (leftSide.equals("BASE")) { //$NON-NLS-1$
			computeDifferencesWithBase(monitor);
		} else if (leftSide.equals("WORKSPACE")) { //$NON-NLS-1$
			computeDifferencesWithWorkspace(monitor, false);
		} else if (rightSide.equals("WORKSPACE")) { //$NON-NLS-1$
			computeDifferencesWithWorkspace(monitor, true);
		} else {
			computeDifferencesBetweenRevisions(monitor);
		}
		return root;
	}

	void switchInputs(String left, String right) {
		try {
			//Short-circuit if nothing has changed
			if (left == null && rightSide.equals(right)) {
				return;
			}
			if (right == null && leftSide.equals(left)) {
				return;
			}
			//First, remember the currently opened file.
			Object selectedElement = getSelectedEdition();
			if (selectedElement != null) {
				fileToReveal = ((GerritDiffNode) selectedElement).getFileInfo();
			}
			//Reset the input
			if (left != null) {
				this.leftSide = left;
			}
			if (right != null) {
				this.rightSide = right;
			}

			//Compute the diffs
			prepareInput(new NullProgressMonitor());
			upperSection.setInput(root);
		} catch (InvocationTargetException | InterruptedException e) {
			logger.error("Problem while switching input to " + left + " " + right, e); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	public void resetRoot() {
		IDiffElement[] children = root.getChildren();
		for (IDiffElement child : children) {
			root.remove(child);
		}
	}

	private void computeDifferencesBetweenRevisions(IProgressMonitor monitor) {
		loadRevision(leftSide);
		loadRevision(rightSide);
		Map<String, FileInfo> files = loadRevisionDiff();

		EMap<String, FileInfo> leftFiles = changeInfo.getRevisions().get(leftSide).getFiles();
		EMap<String, FileInfo> rightFiles = changeInfo.getRevisions().get(rightSide).getFiles();
		for (Entry<String, FileInfo> file : files.entrySet()) {
			GerritDiffNode node = createRevisionRevisionNode(monitor, leftFiles, rightFiles, file.getValue(),
					file.getKey());
			if (node != null) {
				root.add(node);
				setElementToReveal(node, node.getFileInfo());
			}
		}
	}

	private Map<String, FileInfo> loadRevisionDiff() {
		Map<String, FileInfo> files = null;
		try {
			files = gerritClient.getFilesModifiedSince(changeInfo.getId(), rightSide, leftSide).call();
		} catch (EGerritException e) {
			logger.debug(
					"An exception occurred while getting the diff between revision " + leftSide + " and " + rightSide, //$NON-NLS-1$ //$NON-NLS-2$
					e);
		}
		return files;
	}

	private GerritDiffNode createRevisionRevisionNode(IProgressMonitor monitor, EMap<String, FileInfo> leftFiles,
			EMap<String, FileInfo> rightFiles, FileInfo fileToShow, String filePathToShow) {
		String fileName = filePathToShow;
		GerritDiffNode node = new GerritDiffNode(getDifferenceFlag(fileToShow));
		FileInfo referenceFile = null;
		FileInfo matchForRight = rightFiles.get(fileName);
		FileInfo matchForLeft = leftFiles.get(matchForRight != null ? getOldPathOrPath(matchForRight) : filePathToShow);
		if (matchForRight == null && matchForLeft == null) {
			logger.debug("File " + filePathToShow + " found in either revision " + leftSide + " or " + rightSide); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
			return null;
		}
		referenceFile = matchForRight != null ? matchForRight : matchForLeft;

		if (matchForRight != null) {
			node.setRight(new CompareItemFactory(gerritClient).createCompareItemFromRevision(fileName,
					changeInfo.getId(), matchForRight, monitor));
			node.setFileInfo(matchForRight);
		} else {
			node.setRight(new CompareItemFactory(gerritClient).createCompareItemFromCommit(changeInfo.getProject(),
					getBaseCommitId(referenceFile), referenceFile, monitor));
			node.setFileInfo(referenceFile);
		}

		if (matchForLeft != null) {
			node.setLeft(new CompareItemFactory(gerritClient).createCompareItemFromRevision(fileName,
					changeInfo.getId(), matchForLeft, monitor));
		} else if (!fileToShow.getStatus().equals("A")) { //$NON-NLS-1$
			//The file is not in the other revision, and it is not added, so compare against the base
			String baseCommitId = getBaseCommitId(referenceFile);
			if (baseCommitId != null) {
				node.setLeft(new CompareItemFactory(gerritClient).createCompareItemFromCommit(changeInfo.getProject(),
						getBaseCommitId(referenceFile), referenceFile, monitor));
			} else {
				node.setLeft(new EmptyTypedElement(filePathToShow));
			}
		} else {
			node.setLeft(new EmptyTypedElement(filePathToShow));
		}
		return node;
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

	private void computeDifferencesWithWorkspace(IProgressMonitor monitor, boolean workspaceOnRight) {
		loadRevision(workspaceOnRight ? leftSide : rightSide);
		RevisionInfo filesToShow = changeInfo.getRevisions().get(workspaceOnRight ? leftSide : rightSide);
		for (FileInfo file : filesToShow.getFiles().values()) {
			GerritDiffNode node = createWorkspaceRevisionNode(monitor, file, workspaceOnRight);
			root.add(node);
			setElementToReveal(node, file);
		}
	}

	private void loadRevision(String revision) {
		//Load the files synchronously so we can start filling the UI and then load the rest in the background
		QueryHelpers.loadFiles(gerritClient, changeInfo.getRevisions().get(revision));
	}

	private GerritDiffNode createWorkspaceRevisionNode(IProgressMonitor monitor, FileInfo file,
			boolean workspaceOnRight) {
		String fileName = file.getPath();
		GerritDiffNode node = new GerritDiffNode(getDifferenceFlag(file));
		node.setFileInfo(file);

		PatchSetCompareItem revisionFile = new CompareItemFactory(gerritClient).createCompareItemFromRevision(fileName,
				changeInfo.getId(), file, monitor);
		IFile workspaceFile = new OpenCompareEditor(gerritClient, changeInfo).getCorrespondingWorkspaceFile(file);
		if (workspaceFile == null) {
			workspaceFile = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path("missing/" + fileName)); //$NON-NLS-1$
		}
		ITypedElement workspaceNode = createFileElement(workspaceFile);
		if (workspaceOnRight) {
			node.setRight(workspaceNode);
			node.setLeft(revisionFile);
		} else {
			node.setRight(revisionFile);
			node.setLeft(workspaceNode);
		}
		return node;
	}

	private void computeDifferencesWithBase(IProgressMonitor monitor) {
		String actualRightSide = rightSide.equals("WORKSPACE") ? changeInfo.getRevision().getId() : rightSide; //$NON-NLS-1$
		loadRevision(actualRightSide);
		RevisionInfo rightRevision = changeInfo.getRevisions().get(actualRightSide);
		for (FileInfo rightFile : rightRevision.getFiles().values()) {
			GerritDiffNode node = null;
			if (rightSide.equals("WORKSPACE")) { //$NON-NLS-1$
				node = createBaseWorkspaceNode(monitor, rightFile);
			} else {
				node = createBaseRevisionNode(monitor, rightFile);
			}
			root.add(node);
			setElementToReveal(node, rightFile);
		}
	}

	private GerritDiffNode createBaseWorkspaceNode(IProgressMonitor monitor, FileInfo rightFile) {
		String fileName = rightFile.getPath();
		GerritDiffNode node = new GerritDiffNode(getDifferenceFlag(rightFile));

		//create the node for the workspace file
		IFile workspaceFile = new OpenCompareEditor(gerritClient, changeInfo).getCorrespondingWorkspaceFile(rightFile);
		if (workspaceFile == null) {
			workspaceFile = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path("missing/" + fileName)); //$NON-NLS-1$
		}
		node.setRight(createFileElement(workspaceFile));
		node.setFileInfo(rightFile);

		//create the node for the base file
		String baseCommitId = getBaseCommitId(rightFile);
		if (baseCommitId != null) {
			node.setLeft(new CompareItemFactory(gerritClient).createCompareItemFromCommit(changeInfo.getProject(),
					baseCommitId, rightFile, monitor)); //Here we passing the right file so we have all the context necessary
		} else {
			node.setLeft(new EmptyTypedElement("")); //$NON-NLS-1$
		}
		return node;
	}

	private GerritDiffNode createBaseRevisionNode(IProgressMonitor monitor, FileInfo rightFile) {
		String fileName = rightFile.getPath();
		GerritDiffNode node = new GerritDiffNode(getDifferenceFlag(rightFile));
		node.setFileInfo(rightFile);
		node.setRight(new CompareItemFactory(gerritClient).createCompareItemFromRevision(fileName, changeInfo.getId(),
				rightFile, monitor));

		String baseCommitId = getBaseCommitId(rightFile);
		if (baseCommitId != null) {
			node.setLeft(new CompareItemFactory(gerritClient).createCompareItemFromCommit(changeInfo.getProject(),
					baseCommitId, rightFile, monitor)); //Here we passing the right file so we have all the context necessary
		} else {
			node.setLeft(new EmptyTypedElement("")); //$NON-NLS-1$
		}
		return node;
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
		return Messages.GerritMultipleInput_1 + changeInfo.get_number() + " - " + changeInfo.getSubject() + " -- " //$NON-NLS-1$//$NON-NLS-2$
				+ getComparisonTitle();
	}

	private String getComparisonTitle() {
		String result = ""; //$NON-NLS-1$
		if (leftSide.equals("BASE") || leftSide.equals("WORKSPACE")) { //$NON-NLS-1$ //$NON-NLS-2$
			result += leftSide;
		} else {
			result += Messages.GerritMultipleInput_7 + changeInfo.getRevisions().get(leftSide).get_number();
		}
		if (rightSide.equals("WORKSPACE")) { //$NON-NLS-1$
			result += Messages.GerritMultipleInput_9;
		} else {
			result += Messages.GerritMultipleInput_10 + changeInfo.getRevisions().get(rightSide).get_number();
		}
		return result;
	}

	private String getBaseCommitId(FileInfo fileInfo) {
		List<CommitInfo> parents = fileInfo.getRevision().getCommit().getParents();
		if (parents == null || parents.isEmpty()) {
			return null;
		}
		return parents.get(0).getCommit();
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
		if (element instanceof CommentableCompareItem) {
			return true;
		}
		return false;
	}

	private WeakInterningHashSet<SourceViewer> decoratedViewers = new WeakInterningHashSet<>(3);

	private CompareViewerPane upperSection;

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
					if (oldInput instanceof CommentableCompareItem) {
						((CommentableCompareItem) oldInput).reset();
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
				if (sourceViewer.getDocument() instanceof CommentableCompareItem) {
					return ((CommentableCompareItem) sourceViewer.getDocument()).getEditableComments();
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
			forceSaveWorkspaceFile(monitor);
		} catch (RuntimeException ex) {
			//This works hand in hand with the PatchSetCompareItem#setContent method which raises a very specific RuntimeException
			if (CommentableCompareItem.class.getName().equals(ex.getMessage())) {
				if (ex.getCause().getMessage().equals(((GerritDiffNode) getSelectedEdition()).getLeft())) {
					problemSavingChanges = 0;
					setLeftDirty(true);
				} else {
					problemSavingChanges = 1;
					setRightDirty(true);
				}
				throw new CoreException(new org.eclipse.core.runtime.Status(IStatus.ERROR, EGerritUIPlugin.PLUGIN_ID,
						Messages.GerritMultipleInput_11));
			} else {
				//When it is not our exception we just pass it on.
				throw ex;
			}
		}
		if (postSaveListener != null) {
			postSaveListener.run();
		}
	}

	private void forceSaveWorkspaceFile(IProgressMonitor monitor) throws CoreException {
		//Force persist the files that are in the workspace.
		//This is necessary because the Saveable returned by default is not properly set
		if (getSelectedEdition() != null) {
			ITypedElement forceLeft = ((GerritDiffNode) getSelectedEdition()).getLeft();
			if (forceLeft instanceof LocalResourceTypedElement) {
				((LocalResourceTypedElement) forceLeft).commit(monitor);
			}
			ITypedElement forceright = ((GerritDiffNode) getSelectedEdition()).getRight();
			if (forceright instanceof LocalResourceTypedElement) {
				((LocalResourceTypedElement) forceright).commit(monitor);
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

	@Override
	public String getName() {
		if (fileToReveal != null) {
			return ((StringToFileInfoImpl) fileToReveal.eContainer()).getKey();
		}
		return ""; //$NON-NLS-1$
	}

	@Override
	public void fireInputChange() {
		//There are 6 cases to deal with. The table below captures all the permutations.
		//R stands for revision, W for workspace and B for base
		//  left	right
		//1	R		R
		//2	R		W
		//3	B		R
		//4	B		W
		//5	W		W
		//6 W		R
		GerritDiffNode savedElement = (GerritDiffNode) getSelectedEdition();
		IProgressMonitor pm = new NullProgressMonitor();

		boolean workspaceOnRight = rightSide.equals("WORKSPACE"); //$NON-NLS-1$

		//Here we just refresh the content of the node that has just been saved.
		GerritDiffNode newEntry = null;
		//Deals with the cases 1, 3, 6
		if (!workspaceOnRight) {
			if (leftSide.equals("BASE")) { //$NON-NLS-1$
				newEntry = createBaseRevisionNode(pm, savedElement.getFileInfo());
			} else if (leftSide.equals("WORKSPACE")) { //$NON-NLS-1$
				newEntry = createWorkspaceRevisionNode(pm, savedElement.getFileInfo(), false);
			} else {
				loadRevision(leftSide);
				loadRevision(rightSide);
				newEntry = createRevisionRevisionNode(pm, changeInfo.getRevisions().get(leftSide).getFiles(),
						changeInfo.getRevisions().get(rightSide).getFiles(), savedElement.getFileInfo(),
						savedElement.getFileInfo().getPath());
			}
		}

		//Deals with 2, 4 (no-op), 5
		if (workspaceOnRight) {
			if (leftSide.equals("BASE")) { //$NON-NLS-1$
				newEntry = createBaseWorkspaceNode(pm, savedElement.getFileInfo());
			} else if (leftSide.equals("WORKSPACE")) { //$NON-NLS-1$
				newEntry = null;
			} else {
				newEntry = createWorkspaceRevisionNode(pm, savedElement.getFileInfo(), true);
			}
		}
		if (newEntry != null) {
			savedElement.setRight(newEntry.getRight());
			savedElement.setLeft(newEntry.getLeft());
		}

		savedElement.fireChange();
	}

	@Override
	protected ICompareInput prepareCompareInput(IProgressMonitor monitor)
			throws InvocationTargetException, InterruptedException {
		// ignore
		return null;
	}

	@Override
	protected CompareViewerPane createStructureInputPane(final Composite parent) {
		upperSection = new CompareUpperSection(parent, SWT.BORDER | SWT.FLAT, true, this);
		return upperSection;
	}

	public ChangeInfo getChangeInfo() {
		return changeInfo;
	}

	public String getLeftSide() {
		return leftSide;
	}

	public String getRightSide() {
		return rightSide;
	}
}
