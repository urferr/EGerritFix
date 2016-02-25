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

import java.beans.PropertyChangeListener;
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
import org.eclipse.compare.structuremergeviewer.IDiffElement;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.beans.IBeanValueProperty;
import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.masterdetail.IObservableFactory;
import org.eclipse.core.databinding.property.Properties;
import org.eclipse.core.databinding.property.value.IValueProperty;
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
import org.eclipse.egerrit.internal.model.ModelPackage;
import org.eclipse.egerrit.internal.model.RevisionInfo;
import org.eclipse.egerrit.internal.model.impl.StringToFileInfoImpl;
import org.eclipse.egerrit.ui.EGerritUIPlugin;
import org.eclipse.egerrit.ui.editors.OpenCompareEditor;
import org.eclipse.egerrit.ui.editors.QueryHelpers;
import org.eclipse.egerrit.ui.internal.table.model.FilesTableModel;
import org.eclipse.egerrit.ui.internal.table.model.ITableModel;
import org.eclipse.egerrit.ui.internal.table.model.ReviewTableSorter;
import org.eclipse.egerrit.ui.internal.table.provider.FileInfoCompareCellLabelProvider;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.common.util.WeakInterningHashSet;
import org.eclipse.emf.databinding.EMFProperties;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.databinding.viewers.ObservableListTreeContentProvider;
import org.eclipse.jface.databinding.viewers.TreeStructureAdvisor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextInputListener;
import org.eclipse.jface.text.source.AnnotationPainter;
import org.eclipse.jface.text.source.AnnotationPainter.HighlightingStrategy;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.team.ui.synchronize.SaveableCompareEditorInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GerritMultipleInput extends SaveableCompareEditorInput {

	private static Logger logger = LoggerFactory.getLogger(GerritMultipleInput.class);

	private GerritDiffNode root;

	private ChangeInfo changeInfo;

	private TreeViewer viewer;

	private FileInfo fileToReveal; //The file requested by the user

	private DiffNode nodeToReveal; //The node representing the file we want to reveal

	private String leftSide; //workspace, base, revision number

	private String rightSide; //workspace, base, revision number

	private GerritClient gerritClient;

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
			@SuppressWarnings("unused")
			public void addPropertyChangeListener(PropertyChangeListener listener) {
				//Do nothing. This is just here to make sure databinding is not throwing exception
			}

			@Override
			@SuppressWarnings("unused")
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
			GerritDiffNode node = createRevisionRevisionNode(monitor, leftFiles, rightFile);
			root.add(node);
			setElementToReveal(node, rightFile);
		}
	}

	private GerritDiffNode createRevisionRevisionNode(IProgressMonitor monitor, EMap<String, FileInfo> leftFiles,
			FileInfo rightFile) {
		String fileName = rightFile.getPath();
		GerritDiffNode node = new GerritDiffNode(getDifferenceFlag(rightFile));
		node.setRight(new CompareItemFactory(gerritClient).createCompareItemFromRevision(fileName, changeInfo.getId(),
				rightFile, monitor));
		node.setFileInfo(rightFile);
		FileInfo leftFile = getFileWithName(getOldPathOrPath(rightFile), leftFiles);
		if (leftFile != null) {
			node.setLeft(new CompareItemFactory(gerritClient).createCompareItemFromRevision(fileName,
					changeInfo.getId(), leftFile, monitor));
		} else if (!rightFile.getStatus().equals("A")) { //$NON-NLS-1$
			//The file is not in the other revision, and it is not added, so compare against the base
			node.setLeft(new CompareItemFactory(gerritClient).createCompareItemFromCommit(changeInfo.getProject(),
					getBaseCommitId(rightFile), rightFile, monitor));
		} else {
			node.setLeft(new EmptyTypedElement(rightFile.getPath()));
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

	private void computeDifferencesWithWorkspace(IProgressMonitor monitor) {
		RevisionInfo rightRevision = changeInfo.getRevisions().get(rightSide);
		for (FileInfo rightFile : rightRevision.getFiles().values()) {
			GerritDiffNode node = createWorkspaceRevisionNode(monitor, rightFile);
			root.add(node);
			setElementToReveal(node, rightFile);
		}
	}

	private GerritDiffNode createWorkspaceRevisionNode(IProgressMonitor monitor, FileInfo rightFile) {
		String fileName = rightFile.getPath();
		GerritDiffNode node = new GerritDiffNode(getDifferenceFlag(rightFile));
		node.setFileInfo(rightFile);
		node.setRight(new CompareItemFactory(gerritClient).createCompareItemFromRevision(fileName, changeInfo.getId(),
				rightFile, monitor));

		IFile workspaceFile = new OpenCompareEditor(gerritClient, changeInfo).getCorrespondingWorkspaceFile(rightFile);
		if (workspaceFile == null) {
			workspaceFile = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path("missing/" + fileName));
		}
		node.setLeft(createFileElement(workspaceFile));
		return node;
	}

	private void computeDifferencesWithBase(IProgressMonitor monitor) {
		RevisionInfo rightRevision = changeInfo.getRevisions().get(rightSide);
		for (FileInfo rightFile : rightRevision.getFiles().values()) {
			GerritDiffNode node = createBaseRevisionNode(monitor, rightFile);
			root.add(node);
			setElementToReveal(node, rightFile);
		}
	}

	private GerritDiffNode createBaseRevisionNode(IProgressMonitor monitor, FileInfo rightFile) {
		String fileName = rightFile.getPath();
		GerritDiffNode node = new GerritDiffNode(getDifferenceFlag(rightFile));
		node.setFileInfo(rightFile);
		node.setRight(new CompareItemFactory(gerritClient).createCompareItemFromRevision(fileName, changeInfo.getId(),
				rightFile, monitor));

		node.setLeft(new CompareItemFactory(gerritClient).createCompareItemFromCommit(changeInfo.getProject(),
				getBaseCommitId(rightFile), rightFile, monitor)); //Here we passing the right file so we have all the context necessary
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

	private static class TreeFactoryImpl implements IObservableFactory {
		public IObservableList<IDiffElement> createObservable(final Object target) {
			return BeanProperties.list("children").observe(target);
		}
	}

	private static class TreeStructureAdvisorImpl extends TreeStructureAdvisor {
		@Override
		public Object getParent(Object element) {
			if (element instanceof DiffNode) {
				return ((DiffNode) element).getParent();
			}
			return null;
		}

		@Override
		public Boolean hasChildren(Object element) {
			if (element instanceof DiffNode && (((DiffNode) element).getChildren().length > 0)) {
				return Boolean.TRUE;
			}
			return super.hasChildren(element);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Viewer createDiffViewer(Composite parent) {
		viewer = new DiffTreeViewer(new Tree(parent, SWT.FULL_SELECTION), getCompareConfiguration()) {
			@SuppressWarnings("restriction")
			@Override
			protected void createToolItems(ToolBarManager toolbarManager) {
				// ignore
				toolbarManager.appendToGroup("navigation", new NextPreviousFileAction(INavigatable.NEXT_CHANGE, //$NON-NLS-1$
						(CompareEditorInputNavigator) getNavigator()));
				toolbarManager.appendToGroup("navigation", new NextPreviousFileAction(INavigatable.PREVIOUS_CHANGE, //$NON-NLS-1$
						(CompareEditorInputNavigator) getNavigator()));
				toolbarManager.appendToGroup("modes", new ShowFilePathAction(() -> viewer));
				toolbarManager.appendToGroup("modes", new ShowCommentedFileAction(() -> viewer));
				toolbarManager.appendToGroup("modes", new ShowUnchangedFilesAction(gerritClient,
						changeInfo.getRevisions().get(rightSide), leftSide, () -> viewer));
				toolbarManager.appendToGroup("merge", new ReplyAction(gerritClient, GerritMultipleInput.this,
						changeInfo.getRevisions().get(rightSide), leftSide, () -> viewer));
			}
		};
		//Set a content provider
		ObservableListTreeContentProvider cp = new ObservableListTreeContentProvider(new TreeFactoryImpl(),
				new TreeStructureAdvisorImpl());
		viewer.setContentProvider(cp);

		//Create the tree columns with the Cell provider
		final ITableModel[] tableInfo = FilesTableModel.values();
		int size = tableInfo.length;

		for (int index = 0; index < size; index++) {
			createTreeViewerColumn(tableInfo[index]);
		}

		viewer.getTree().setHeaderVisible(true);
		viewer.getTree().setLinesVisible(true);
		viewer.getTree().setData(CompareUI.COMPARE_VIEWER_TITLE, getComparisonTitle());

		//Add a mouse listener to toggle the review state
		if (!gerritClient.getRepository().getServerInfo().isAnonymous()) {
			viewer.getTree().addMouseListener(toggleReviewedStateListener());
		}

		// Set the content sorter
		ReviewTableSorter.bind(viewer);
		viewer.setComparator(new ReviewTableSorter(2)); // sort by File Path, descending. This way we are sorted like in the files tab

		IBeanValueProperty fileInfo = PojoProperties.value("fileInfo"); //$NON-NLS-1$
		IValueProperty reviewedFlag = fileInfo.value(EMFProperties.value(ModelPackage.Literals.FILE_INFO__REVIEWED));
		IValueProperty comments = fileInfo.value(EMFProperties.value(ModelPackage.Literals.FILE_INFO__COMMENTS_COUNT));
		IValueProperty draftComments = fileInfo
				.value(EMFProperties.value(ModelPackage.Literals.FILE_INFO__DRAFTS_COUNT));
		final IObservableMap[] watchedProperties = Properties.observeEach(cp.getKnownElements(),
				new IValueProperty[] { reviewedFlag, comments, draftComments });
		viewer.setLabelProvider(new FileInfoCompareCellLabelProvider(watchedProperties));
		viewer.setInput(root);

		if (nodeToReveal != null) {
			StructuredSelection selection = new StructuredSelection(nodeToReveal);
			viewer.setSelection(selection, true);

			callFeed1(selection);
		}
		return viewer;

	}

	/**
	 * Create each column in the review tree list
	 *
	 * @param FilesTableModel
	 * @return TreeViewerColumn
	 */
	private TreeViewerColumn createTreeViewerColumn(ITableModel tableInfo) {
		TreeViewerColumn treeColumViewer = new TreeViewerColumn(viewer, SWT.NONE);
		final TreeColumn column = treeColumViewer.getColumn();

		column.setText(tableInfo.getName());
		column.setWidth(tableInfo.getWidth());
		column.setAlignment(tableInfo.getAlignment());
		column.setResizable(tableInfo.getResize());
		column.setMoveable(tableInfo.getMoveable());
		return treeColumViewer;
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
	public String getName() {
		return ((StringToFileInfoImpl) fileToReveal.eContainer()).getKey();
	}

	@Override
	public void fireInputChange() {
		GerritDiffNode savedElement = (GerritDiffNode) getSelectedEdition();
		IProgressMonitor pm = new NullProgressMonitor();

		GerritDiffNode newEntry = null;
		if (leftSide.equals("BASE")) {
			newEntry = createBaseRevisionNode(pm, savedElement.getFileInfo());
		} else if (leftSide.equals("WORKSPACE")) {
			newEntry = createWorkspaceRevisionNode(pm, savedElement.getFileInfo());
		} else {
			newEntry = createRevisionRevisionNode(pm, changeInfo.getRevisions().get(leftSide).getFiles(),
					savedElement.getFileInfo());
		}
		savedElement.setRight(newEntry.getRight());
		savedElement.setLeft(newEntry.getLeft());

		savedElement.fireChange();
	}

	@Override
	protected ICompareInput prepareCompareInput(IProgressMonitor monitor)
			throws InvocationTargetException, InterruptedException {
		// ignore
		return null;
	}

	/**
	 * This method is the listener to toggle a file's reviewed state
	 *
	 * @return MouseAdapter
	 */
	private MouseAdapter toggleReviewedStateListener() {
		return new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				ViewerCell viewerCell = viewer.getCell(new Point(e.x, e.y));
				if (viewerCell != null && viewerCell.getColumnIndex() == 0) {
					//Selected the first column, so we can send the delete option
					//Otherwise, do not delete
					ISelection selection = viewer.getSelection();
					if (selection instanceof IStructuredSelection) {
						IStructuredSelection structuredSelection = (IStructuredSelection) selection;
						Object element = structuredSelection.getFirstElement();
						FileInfo fileInfo = ((GerritDiffNode) element).getFileInfo();
						toggleReviewed(fileInfo);
					}
				}
			}
		};
	}

	private void toggleReviewed(FileInfo fileInfo) {
		if (fileInfo.isReviewed()) {
			QueryHelpers.markAsNotReviewed(gerritClient, fileInfo);
		} else {
			QueryHelpers.markAsReviewed(gerritClient, fileInfo);
		}
	}
}
