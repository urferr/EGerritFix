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
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.CompareViewerPane;
import org.eclipse.compare.IStreamContentAccessor;
import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.contentmergeviewer.ContentMergeViewer;
import org.eclipse.compare.contentmergeviewer.TextMergeViewer;
import org.eclipse.compare.internal.MergeSourceViewer;
import org.eclipse.compare.structuremergeviewer.DiffNode;
import org.eclipse.compare.structuremergeviewer.ICompareInput;
import org.eclipse.compare.structuremergeviewer.IDiffElement;
import org.eclipse.core.filebuffers.FileBuffers;
import org.eclipse.core.filebuffers.LocationKind;
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
import org.eclipse.emf.common.util.WeakInterningHashSet;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextInputListener;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.AnnotationPainter;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.team.internal.ui.synchronize.LocalResourceTypedElement;
import org.eclipse.team.ui.synchronize.SaveableCompareEditorInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GerritMultipleInput extends SaveableCompareEditorInput {
	private static Logger logger = LoggerFactory.getLogger(GerritMultipleInput.class);

	public static final String WORKSPACE = "WORKSPACE"; //$NON-NLS-1$

	public static final String BASE = "BASE"; //$NON-NLS-1$

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
			public String getName() {
				return GerritMultipleInput.this.getName();
			}

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

		if ((leftSide.equals(BASE) && rightSide.equals(WORKSPACE))
				|| (leftSide.equals(WORKSPACE) && rightSide.equals(BASE))) {
			compareWorkspaceWithBase(monitor);
		} else if (leftSide.equals(BASE) || rightSide.equals(BASE)) {
			compareRevisionWithBase(monitor);
		} else if (leftSide.equals(WORKSPACE) || rightSide.equals(WORKSPACE)) {
			compareRevisionWithWorkspace(monitor);
		} else {
			compareRevisions(monitor);
		}
		return root;
	}

	private void compareWorkspaceWithBase(IProgressMonitor monitor) {
		loadRevision(changeInfo.getRevision().getId());
		RevisionInfo rightRevision = changeInfo.getRevisions().get(changeInfo.getRevision().getId());
		for (FileInfo rightFile : rightRevision.getFiles().values()) {
			GerritDiffNode node = createBaseWorkspaceNode(monitor, rightFile);
			root.add(node);
			setElementToReveal(node, rightFile);
		}
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

	private void compareRevisions(IProgressMonitor monitor) {
		loadRevision(leftSide);
		loadRevision(rightSide);
		Map<String, FileInfo> files = loadRevisionDiff(rightSide, leftSide);

		for (Entry<String, FileInfo> file : files.entrySet()) {
			GerritDiffNode node = createRevisionRevisionNode(monitor, changeInfo.getRevisions().get(leftSide),
					changeInfo.getRevisions().get(rightSide), file.getValue(), file.getKey());
			if (node != null) {
				root.add(node);
				setElementToReveal(node, node.getFileInfo());
			}
		}
	}

	private Map<String, FileInfo> loadRevisionDiff(String referenceRevision, String base) {
		Map<String, FileInfo> files = null;
		try {
			files = gerritClient.getFilesModifiedSince(changeInfo.getId(), referenceRevision, base).call();
		} catch (EGerritException e) {
			logger.debug("An exception occurred while getting the diff between revision " + referenceRevision + " and " //$NON-NLS-1$//$NON-NLS-2$
					+ base, e);
			files = new HashMap<>();
		}
		return files;
	}

	private GerritDiffNode createRevisionRevisionNode(IProgressMonitor monitor, RevisionInfo leftRevision,
			RevisionInfo rightRevision, FileInfo fileToShow, String filePathToShow) {
		int differenceKind = getDifferenceFlag(fileToShow);
		GerritDiffNode node = new GerritDiffNode(differenceKind);
		node.setDiffFileInfo(fileToShow);
		ITypedElement leftFile = null;
		ITypedElement rightFile = null;

		switch (differenceKind) {
		case GerritDifferences.ADDITION:
			node.setFileInfo(leftRevision.getFiles().get(filePathToShow));
			FileInfo potentialRight = rightRevision.getFiles().get(filePathToShow);
			if (potentialRight == null) {
				//Real addition
				leftFile = new CompareItemFactory(gerritClient).createCompareItemFromRevision(filePathToShow,
						changeInfo.getId(), leftRevision.getFiles().get(filePathToShow), monitor);
				rightFile = new EmptyTypedElement(filePathToShow);
			} else {
				//Deal with the case where a deletion that happened between revision 1 and 3 is shown as an addition
				//because we are comparing revision 3 with 1 (E.g. review 83696).
				leftFile = new CompareItemFactory(gerritClient).createCompareItemFromRevision(filePathToShow,
						changeInfo.getId(), leftRevision.getFiles().get(filePathToShow), monitor);
				rightFile = new CompareItemFactory(gerritClient).createCompareItemFromRevision(filePathToShow,
						changeInfo.getId(), rightRevision.getFiles().get(filePathToShow), monitor);
			}
			break;
		case GerritDifferences.RENAMED:
		case GerritDifferences.COPIED:
			if (leftRevision.getFiles().get(filePathToShow) != null) {
				leftFile = new CompareItemFactory(gerritClient).createCompareItemFromRevision(filePathToShow,
						changeInfo.getId(), leftRevision.getFiles().get(filePathToShow), monitor);
				node.setFileInfo(leftRevision.getFiles().get(filePathToShow));
			} else {
				OrphanedFileInfo o = new OrphanedFileInfo();
				o.setFilePath(filePathToShow);
				o.setRevisionInfo(leftRevision);
				String baseCommitId = getBaseCommitId(o);
				if (baseCommitId != null) {
					leftFile = new CompareItemFactory(gerritClient).createCompareItemFromCommit(changeInfo.getProject(),
							baseCommitId, o, filePathToShow, Integer.toString(leftRevision.get_number()), monitor);
				} else {
					leftFile = new EmptyTypedElement(fileToShow.getOld_path());
				}
				node.setFileInfo(o);
			}

			if (rightRevision.getFiles().get(fileToShow.getOld_path()) != null) {
				rightFile = new CompareItemFactory(gerritClient).createCompareItemFromRevision(fileToShow.getOld_path(),
						changeInfo.getId(), rightRevision.getFiles().get(fileToShow.getOld_path()), monitor);
			} else {
				OrphanedFileInfo o = new OrphanedFileInfo();
				o.setFilePath(fileToShow.getOld_path());
				o.setOld_path(fileToShow.getOld_path());
				o.setRevisionInfo(rightRevision);
				o.setStatus(fileToShow.getStatus());
				o.setReviewed(fileToShow.isReviewed());
				String baseCommitId = getBaseCommitId(o);
				if (baseCommitId != null) {
					rightFile = new CompareItemFactory(gerritClient).createCompareItemFromCommit(
							changeInfo.getProject(), baseCommitId, o, fileToShow.getOld_path(),
							Integer.toString(rightRevision.get_number()), monitor);
				} else {
					rightFile = new EmptyTypedElement(fileToShow.getOld_path());
				}
			}
			break;
		case GerritDifferences.CHANGE:
			if (leftRevision.getFiles().get(filePathToShow) == null) {
				leftFile = new CompareItemFactory(gerritClient).createCompareItemFromCommit(changeInfo.getProject(),
						getBaseCommitId(leftRevision), rightRevision.getFiles().get(filePathToShow), null,
						Integer.toString(leftRevision.get_number()), monitor);
			} else {
				leftFile = new CompareItemFactory(gerritClient).createCompareItemFromRevision(filePathToShow,
						changeInfo.getId(), leftRevision.getFiles().get(filePathToShow), monitor);
			}
			if (rightRevision.getFiles().get(filePathToShow) == null) {
				rightFile = new CompareItemFactory(gerritClient).createCompareItemFromCommit(changeInfo.getProject(),
						getBaseCommitId(rightRevision), leftRevision.getFiles().get(filePathToShow), null,
						Integer.toString(rightRevision.get_number()), monitor);
				node.setFileInfo(leftRevision.getFiles().get(filePathToShow));
			} else {
				rightFile = new CompareItemFactory(gerritClient).createCompareItemFromRevision(filePathToShow,
						changeInfo.getId(), rightRevision.getFiles().get(filePathToShow), monitor);
				node.setFileInfo(rightRevision.getFiles().get(filePathToShow));
			}
			break;
		case GerritDifferences.DELETION:
			leftFile = new EmptyTypedElement(filePathToShow);
			rightFile = new CompareItemFactory(gerritClient).createCompareItemFromRevision(filePathToShow,
					changeInfo.getId(), rightRevision.getFiles().get(filePathToShow), monitor);
			node.setFileInfo(rightRevision.getFiles().get(filePathToShow));
			break;
		default:
			return null;
		}
		node.setLeft(leftFile);
		node.setRight(rightFile);
		return node;
	}

	private int getDifferenceFlag(FileInfo file) {
		switch (file.getStatus()) {
		case "A": //$NON-NLS-1$
			return GerritDifferences.ADDITION;
		case "D": //$NON-NLS-1$
			return GerritDifferences.DELETION;
		case "M": //$NON-NLS-1$
			return GerritDifferences.CHANGE;
		case "C": //$NON-NLS-1$
			return GerritDifferences.COPIED;
		case "W": //$NON-NLS-1$
			return GerritDifferences.REWRITTEN;
		case "R": //$NON-NLS-1$
			return GerritDifferences.RENAMED;
		default:
			return GerritDifferences.CHANGE;
		}
	}

	private void compareRevisionWithWorkspace(IProgressMonitor monitor) {
		boolean workspaceOnRight = rightSide.equals(WORKSPACE);
		loadRevision(workspaceOnRight ? leftSide : rightSide);
		RevisionInfo filesToShow = changeInfo.getRevisions().get(workspaceOnRight ? leftSide : rightSide);
		for (FileInfo file : filesToShow.getFiles().values()) {
			GerritDiffNode node = createWorkspaceRevisionNode(monitor, file);
			root.add(node);
			setElementToReveal(node, file);
		}
	}

	private void loadRevision(String revision) {
		//Load the files synchronously so we can start filling the UI and then load the rest in the background
		QueryHelpers.loadFiles(gerritClient, changeInfo.getRevisions().get(revision));

		//Load the revision details to be able to completely fill the upper section of the compare editor
		CompletableFuture.runAsync(
				() -> QueryHelpers.loadRevisionDetails(gerritClient, changeInfo.getRevisions().get(revision)));
	}

	private GerritDiffNode createWorkspaceRevisionNode(IProgressMonitor monitor, FileInfo file) {
		boolean workspaceOnRight = rightSide.equals(WORKSPACE);
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

	private void compareRevisionWithBase(IProgressMonitor monitor) {
		String revisionToCompareAgainst = leftSide.equals(BASE) ? rightSide : leftSide;
		loadRevision(revisionToCompareAgainst);
		RevisionInfo rightRevision = changeInfo.getRevisions().get(revisionToCompareAgainst);
		for (FileInfo rightFile : rightRevision.getFiles().values()) {
			GerritDiffNode node = createBaseRevisionNode(monitor, rightFile);
			root.add(node);
			setElementToReveal(node, rightFile);
		}
	}

	private GerritDiffNode createBaseWorkspaceNode(IProgressMonitor monitor, FileInfo file) {
		String fileName = file.getPath();
		GerritDiffNode node = new GerritDiffNode(getDifferenceFlag(file));
		node.setFileInfo(file);

		//create the node for the workspace file
		IFile workspaceFile = new OpenCompareEditor(gerritClient, changeInfo).getCorrespondingWorkspaceFile(file);
		if (workspaceFile == null) {
			workspaceFile = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path("missing/" + fileName)); //$NON-NLS-1$
		}
		ITypedElement workspaceElement = createFileElement(workspaceFile);

		//create the node for the base file
		String baseCommitId = getBaseCommitId(file);
		ITypedElement baseElement = null;
		if (baseCommitId != null) {
			baseElement = new CompareItemFactory(gerritClient).createCompareItemFromCommit(changeInfo.getProject(),
					baseCommitId, file, monitor); //Here we passing the right file so we have all the context necessary
		} else {
			baseElement = new EmptyTypedElement(""); //$NON-NLS-1$
		}
		if (rightSide.equals(WORKSPACE)) {
			node.setRight(workspaceElement);
			node.setLeft(baseElement);
		} else {
			node.setRight(baseElement);
			node.setLeft(workspaceElement);
		}
		return node;
	}

	private GerritDiffNode createBaseRevisionNode(IProgressMonitor monitor, FileInfo file) {
		String fileName = file.getPath();
		GerritDiffNode node = new GerritDiffNode(getDifferenceFlag(file));
		node.setFileInfo(file);

		PatchSetCompareItem revisionElement = new CompareItemFactory(gerritClient)
				.createCompareItemFromRevision(fileName, changeInfo.getId(), file, monitor);

		String baseCommitId = getBaseCommitId(file);
		ITypedElement baseElement = null;
		if (baseCommitId != null) {
			baseElement = new CompareItemFactory(gerritClient).createCompareItemFromCommit(changeInfo.getProject(),
					baseCommitId, file, monitor); //Here we passing the right file so we have all the context necessary
		} else {
			baseElement = new EmptyTypedElement(""); //$NON-NLS-1$
		}
		if (leftSide.equals(BASE)) {
			node.setLeft(baseElement);
			node.setRight(revisionElement);
		} else {
			node.setLeft(revisionElement);
			node.setRight(baseElement);
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
		return NLS.bind(Messages.CompareEditorTitle,
				new Object[] { changeInfo.get_number(), changeInfo.getSubject(), getComparisonTitle() });
	}

	private String getComparisonTitle() {
		if (UICompareUtils.isMirroredOn(this)) {
			return getUserReadableString(rightSide) + " / " + getUserReadableString(leftSide); //$NON-NLS-1$
		}
		return getUserReadableString(leftSide) + " / " + getUserReadableString(rightSide); //$NON-NLS-1$
	}

	private String getUserReadableString(String string) {
		if (string.equals(BASE) || string.equals(WORKSPACE)) {
			return string;
		}
		return Messages.Patchset + changeInfo.getRevisions().get(string).get_number();
	}

	private String getBaseCommitId(RevisionInfo revision) {
		List<CommitInfo> parents = revision.getCommit().getParents();
		if (parents == null || parents.isEmpty()) {
			return null;
		}
		return parents.get(0).getCommit();
	}

	private String getBaseCommitId(FileInfo fileInfo) {
		return getBaseCommitId(fileInfo.getRevision());
	}

	private LocalResourceTypedElement cachedElement;

	@Override
	//We need this so we can hook the mechanism to color the comments
	public Viewer findContentViewer(Viewer oldViewer, ICompareInput input, Composite parent) {
		Viewer newViewer = super.findContentViewer(oldViewer, input, parent);
		purgeCache();

		//Force a reset of the documents before they get used. This is necessary if the document has already been opened.
		if (input instanceof GerritDiffNode) {
			GerritDiffNode node = (GerritDiffNode) input;

			if (node.getLeft() instanceof CommentableCompareItem) {
				((CommentableCompareItem) node.getLeft()).reset();
			}
			if (node.getRight() instanceof CommentableCompareItem) {
				((CommentableCompareItem) node.getRight()).reset();
			}

			if (node.getLeft() instanceof LocalResourceTypedElement) {
				addToCache((LocalResourceTypedElement) node.getLeft());
			}
			if (node.getRight() instanceof LocalResourceTypedElement) {
				addToCache((LocalResourceTypedElement) node.getRight());
			}
		}

		if (oldViewer == newViewer) {
			return newViewer;
		}
		setupCommentColorer(newViewer, 0);
		setupCommentColorer(newViewer, 1);

		UICompareUtils.insertAnnotationNavigationCommands(CompareViewerPane.getToolBarManager(parent));

		return newViewer;
	}

	//Load the IFile represented by the typedElement in the FileBufferManager
	//This is necessary to avoid the loss of edits (done on workspace files) when swapping sides.
	//Because the compare editor only shows one file at a time, we only add one element.
	private void addToCache(LocalResourceTypedElement typedElement) {
		try {
			cachedElement = typedElement;
			FileBuffers.getTextFileBufferManager().connect(cachedElement.getResource().getFullPath(),
					LocationKind.IFILE, new NullProgressMonitor());
		} catch (CoreException e) {
			//Ignore
		}
	}

	//Remove a file from the FileBufferManager
	private void purgeCache() {
		if (cachedElement != null) {
			try {
				FileBuffers.getTextFileBufferManager().disconnect(cachedElement.getResource().getFullPath(),
						LocationKind.IFILE, new NullProgressMonitor());
			} catch (CoreException e) {
				//Ignore
			}
		}
	}

	@Override
	public Viewer findStructureViewer(Viewer oldViewer, ICompareInput input, Composite parent) {
		if (input instanceof GerritDiffNode) {
			nodeToReveal = (GerritDiffNode) input;
		}
		return super.findStructureViewer(oldViewer, input, parent);
	}

	private WeakInterningHashSet<SourceViewer> decoratedViewers = new WeakInterningHashSet<>(3);

	private CompareUpperSection upperSection;

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

			//This listener is responsible to add / remove the listeners that take care
			//of the coloring and prevent the edition. Those various listeners are different
			//for each document
			sourceViewer.addTextInputListener(new ITextInputListener() {
				final SourceViewer sv = sourceViewer;

				AnnotationPainter commentPainter;

				EditionLimiter editionLimiter;

				VerifyListener blockEdition;

				@Override
				public void inputDocumentChanged(IDocument oldInput, IDocument newInput) {
					sv.setEditable(true);
					if (newInput instanceof CommentableCompareItem) {
						commentPainter = initializeCommentColoring(sv);
						editionLimiter = new EditionLimiter(sv);
						sv.addTextPresentationListener(commentPainter);
						sv.addPainter(commentPainter);
						sv.getTextWidget().addVerifyListener(editionLimiter);
						//Swapping causes loss of the dirty state, which means that saving won't work properly.
						//Therefore we need to force reset the dirty flag based on the edits made to the documents
						setDirty(isDirty((CommentableCompareItem) newInput), side);
					} else {
						if (newInput == null) {
							return;
						}

						/**
						 * This is our way to prevent edition when the compare editor shows the detailed structural
						 * pane. What we refer to "the detailed structural diff" is a pane of the compare editor that
						 * shows structural differences of the file being compared. For example, when you compare a
						 * .properties file, this pane will show a list of the properties being added or removed.<br/>
						 * When the user selects an item in the detailed structural pane, the compare editor framework
						 * recreates a new document from the document already loaded and redisplays this. This causes
						 * EGerrit issues because we have our own type of document which includes additional information
						 * like the position of comments, and losing those means that we display the comments without
						 * the syntax coloring which is confusing to the user. At this point we assume we can't change
						 * the compare fwk, and instead we detect the situation, and warn the user this usecase is not
						 * supported. <br/>
						 */
						if (side == 0) {
							//Left side
							if (!UICompareUtils.isMirroredOn(GerritMultipleInput.this) && leftSide.equals(WORKSPACE)) {
								return;
							}
							if (UICompareUtils.isMirroredOn(GerritMultipleInput.this) && rightSide.equals(WORKSPACE)) {

								return;
							}
						} else {
							if (!UICompareUtils.isMirroredOn(GerritMultipleInput.this) && rightSide.equals(WORKSPACE)) {
								//right side
								return;
							}

							if (UICompareUtils.isMirroredOn(GerritMultipleInput.this) && leftSide.equals(WORKSPACE)) {
								return;
							}
						}
						blockEdition = new VerifyListener() {
							boolean messageShown = false;

							@Override
							public void verifyText(VerifyEvent e) {
								e.doit = false;
								if (!messageShown) {
									MessageDialog.openInformation(null, Messages.UnsupportedInput_Title,
											Messages.UnsupportedInput_Text);
									messageShown = true;
								}
							}
						};
						sv.getTextWidget().addVerifyListener(blockEdition);
					}
				}

				@Override
				public void inputDocumentAboutToBeChanged(IDocument oldInput, IDocument newInput) {
					if (oldInput instanceof CommentableCompareItem) {
						if (commentPainter != null) {
							sv.removePainter(commentPainter);
							sv.removeTextPresentationListener(commentPainter);
						}
						if (editionLimiter != null) {
							sv.getTextWidget().removeVerifyListener(editionLimiter);
						}
					} else {
						if (blockEdition != null) {
							sv.getTextWidget().removeVerifyListener(blockEdition);
						}
					}
				}

				private AnnotationPainter initializeCommentColoring(ISourceViewer viewer) {
					return new CommentAnnotationPainter(viewer, null, GerritMultipleInput.this);
				}

				//Determine if a document is dirty by checking the status of the comment annotations
				private boolean isDirty(CommentableCompareItem doc) {
					Iterator<Annotation> iterator = doc.getEditableComments().getAnnotationIterator();
					while (iterator.hasNext()) {
						GerritCommentAnnotation annotation = (GerritCommentAnnotation) iterator.next();
						if (annotation.getComment() == null) {
							return true;
						}
					}
					return false;
				}

				//Helper method to set the dirty flag on the ContentMergeViewer
				private void setDirty(boolean dirty, int side) {
					try {
						Class<ContentMergeViewer> clazz = ContentMergeViewer.class;
						Method declaredMethod;
						declaredMethod = clazz.getDeclaredMethod(side == 0 ? "setLeftDirty" : "setRightDirty", //$NON-NLS-1$ //$NON-NLS-2$
								boolean.class);
						declaredMethod.setAccessible(true);
						declaredMethod.invoke(textMergeViewer, dirty);
					} catch (NoSuchMethodException | SecurityException | IllegalAccessException
							| IllegalArgumentException | InvocationTargetException e) {
						//Should not happen
					}
				}
			});

		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException t) {
			logger.error("Problem while setting up coloration of comments", t); //$NON-NLS-1$
		}
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
		if (fileToReveal != null && (StringToFileInfoImpl) fileToReveal.eContainer() != null) {
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

		//Here we just refresh the content of the node that has just been saved.
		GerritDiffNode newEntry = null;
		//Deals with the cases 1, 3, 6
		if ((leftSide.equals(BASE) && rightSide.equals(WORKSPACE))
				|| (leftSide.equals(WORKSPACE) && rightSide.equals(BASE))) {
			newEntry = createBaseWorkspaceNode(pm, savedElement.getFileInfo());
		} else if (leftSide.equals(BASE) || rightSide.equals(BASE)) {
			newEntry = createBaseRevisionNode(pm, savedElement.getFileInfo());
		} else if (leftSide.equals(WORKSPACE) || rightSide.equals(WORKSPACE)) {
			newEntry = createWorkspaceRevisionNode(pm, savedElement.getFileInfo());
		} else {
			loadRevision(leftSide);
			loadRevision(rightSide);
			newEntry = createRevisionRevisionNode(pm, changeInfo.getRevisions().get(leftSide),
					changeInfo.getRevisions().get(rightSide), savedElement.getDiffFileInfo(),
					savedElement.getFileInfo().getPath());
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

	public CompareUpperSection getUpperSection() {
		return upperSection;
	}

	@Override
	protected void handleDispose() {
		super.handleDispose();
		purgeCache();
	}

	@Override
	public void registerContextMenu(MenuManager menu, final ISelectionProvider selectionProvider) {
		super.registerContextMenu(menu, selectionProvider);
		registerOpenWorkspaceVersion(menu, selectionProvider);
	}

	private void registerOpenWorkspaceVersion(MenuManager menu, final ISelectionProvider selectionProvider) {
		menu.addMenuListener(new IMenuListener() {
			@Override
			public void menuAboutToShow(IMenuManager manager) {
				if (!(selectionProvider instanceof SourceViewer) && !(getSelectedEdition() instanceof GerritDiffNode)) {
					return;
				}
				manager.insertAfter("file", //$NON-NLS-1$
						new OpenWorkspaceFile((SourceViewer) selectionProvider, (GerritDiffNode) getSelectedEdition(),
								gerritClient));
			}
		});
	}
}
