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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.CompareEditorInput;
import org.eclipse.compare.CompareViewerSwitchingPane;
import org.eclipse.compare.structuremergeviewer.DiffNode;
import org.eclipse.compare.structuremergeviewer.DiffTreeViewer;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.beans.IBeanValueProperty;
import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.masterdetail.IObservableFactory;
import org.eclipse.core.databinding.property.Properties;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.egerrit.internal.model.FileInfo;
import org.eclipse.egerrit.internal.model.ModelPackage;
import org.eclipse.egerrit.internal.model.RevisionInfo;
import org.eclipse.egerrit.internal.ui.EGerritImages;
import org.eclipse.egerrit.internal.ui.editors.QueryHelpers;
import org.eclipse.egerrit.internal.ui.table.model.FilesTableModel;
import org.eclipse.egerrit.internal.ui.table.model.ITableModel;
import org.eclipse.egerrit.internal.ui.table.model.ReviewTableSorter;
import org.eclipse.egerrit.internal.ui.table.provider.DynamicMenuBuilder;
import org.eclipse.egerrit.internal.ui.table.provider.FileInfoCompareCellLabelProvider;
import org.eclipse.emf.databinding.EMFProperties;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.databinding.viewers.ObservableListTreeContentProvider;
import org.eclipse.jface.databinding.viewers.TreeStructureAdvisor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;
import org.eclipse.ui.services.IServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class represents the upper section of the compare editor. This is where we are showing the list of files that
 * are part of the patchset.
 */
public class CompareUpperSection extends CompareViewerSwitchingPane {
	private static Logger logger = LoggerFactory.getLogger(CompareUpperSection.class);

	GerritMultipleInput compareInput;

	DiffTreeViewer viewer;

	Label leftPatch, rightPatch;

	private FileInfoCompareCellLabelProvider labelProvider;

	private DynamicMenuBuilder dynamicMenu = new DynamicMenuBuilder();

	public CompareUpperSection(Composite parent, int style, boolean visibility, CompareEditorInput cei) {
		super(parent, style, visibility);
		compareInput = (GerritMultipleInput) cei;
		//These two values can't be set in createTopLeft() because when it gets called fCompareEditorInput is not set yet
		leftPatch.setText(
				GerritCompareHelper.resolveShortName(compareInput.getChangeInfo(), compareInput.getLeftSide()));
		rightPatch.setText(
				GerritCompareHelper.resolveShortName(compareInput.getChangeInfo(), compareInput.getRightSide()));
		createViewer(this);
	}

	private CompareConfiguration getCompareConfiguration() {
		return compareInput.getCompareConfiguration();
	}

	@Override
	protected Viewer getViewer(Viewer oldViewer, Object input) {
		viewer.setInput(input);
		revealFile();
		return viewer;
	}

	@Override
	public void setInput(Object input) {
		super.setInput(input);
		viewer.setInput(input);
		revealFile();
	}

	private void revealFile() {
		if (compareInput.nodeToReveal != null) {
			StructuredSelection selection = new StructuredSelection(compareInput.nodeToReveal);
			viewer.setSelection(selection, true);

			callFeed1(selection);
		} else {
			//When there is no file to reveal, we force the input to null to hide what was there
			callFeed1(null);
		}
	}

	private void callFeed1(StructuredSelection nodeToReveal) {
		Class<CompareEditorInput> clazz = CompareEditorInput.class;
		Method feed1Method;
		try {
			feed1Method = clazz.getDeclaredMethod("feed1", ISelection.class); //$NON-NLS-1$
			feed1Method.setAccessible(true);
			feed1Method.invoke(compareInput, nodeToReveal);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			logger.debug("Exception revealing the element selected by the user", nodeToReveal); //$NON-NLS-1$
		}
	}

	private void createViewer(Composite parent) {
		viewer = new DiffTreeViewer(new Tree(parent, SWT.FULL_SELECTION), getCompareConfiguration()) {
			@Override
			protected void createToolItems(ToolBarManager toolbarManager) {
				IServiceLocator serviceLocator = PlatformUI.getWorkbench();

				CommandContributionItemParameter showReviewEditorContributionParameter = new CommandContributionItemParameter(
						serviceLocator, null, "org.eclipse.egerrit.internal.ui.compare.showReviewEditor", //$NON-NLS-1$
						CommandContributionItem.STYLE_PUSH);
				showReviewEditorContributionParameter.icon = EGerritImages
						.getDescriptor(EGerritImages.SHOW_REVIEW_EDITOR_IMAGE);

				toolbarManager.appendToGroup("merge", //$NON-NLS-1$
						new CommandContributionItem(showReviewEditorContributionParameter));

				CommandContributionItemParameter replyContributionParameter = new CommandContributionItemParameter(
						serviceLocator, null, "org.eclipse.egerrit.internal.ui.compare.reply", //$NON-NLS-1$
						CommandContributionItem.STYLE_PUSH);
				replyContributionParameter.icon = EGerritImages.getDescriptor(EGerritImages.REPLY);

				toolbarManager.appendToGroup("merge", new CommandContributionItem(replyContributionParameter)); //$NON-NLS-1$

				CommandContributionItemParameter showCommentContributionParameter = new CommandContributionItemParameter(
						serviceLocator, null, "org.eclipse.egerrit.internal.ui.compare.showCommentedFile", //$NON-NLS-1$
						CommandContributionItem.STYLE_PUSH);
				showCommentContributionParameter.icon = EGerritImages.getDescriptor(EGerritImages.COMMENT_FILTER);

				toolbarManager.appendToGroup("modes", new CommandContributionItem(showCommentContributionParameter));

				CommandContributionItemParameter showFileContributionParameter = new CommandContributionItemParameter(
						serviceLocator, null, "org.eclipse.egerrit.internal.ui.compare.showFilePath", //$NON-NLS-1$
						CommandContributionItem.STYLE_PUSH);
				showFileContributionParameter.icon = EGerritImages.getDescriptor(EGerritImages.TOGGLE_FILEPATH);

				toolbarManager.appendToGroup("modes", new CommandContributionItem(showFileContributionParameter));

				CommandContributionItemParameter nextContributionParameter = new CommandContributionItemParameter(
						serviceLocator, null, "org.eclipse.egerrit.internal.ui.compare.selectNextFile", //$NON-NLS-1$
						CommandContributionItem.STYLE_PUSH);
				nextContributionParameter.icon = EGerritImages.getDescriptor(EGerritImages.DOWN_ARROW);

				toolbarManager.appendToGroup("navigation", new CommandContributionItem(nextContributionParameter));

				CommandContributionItemParameter previousContributionParameter = new CommandContributionItemParameter(
						serviceLocator, null, "org.eclipse.egerrit.internal.ui.compare.selectPreviousFile", //$NON-NLS-1$
						CommandContributionItem.STYLE_PUSH);
				previousContributionParameter.icon = EGerritImages.getDescriptor(EGerritImages.UP_ARROW);

				toolbarManager.appendToGroup("navigation", new CommandContributionItem(previousContributionParameter));

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
			TreeViewerColumn val = createTreeViewerColumn(tableInfo[index]);
			dynamicMenu.getTreeViewerColumn().put(tableInfo[index], val);
		}

		viewer.getTree().setHeaderVisible(true);
		viewer.getTree().setLinesVisible(true);

		//Add a mouse listener to toggle the review state
		if (!compareInput.gerritClient.getRepository().getServerInfo().isAnonymous()) {
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
		labelProvider = new FileInfoCompareCellLabelProvider(watchedProperties);
		viewer.setLabelProvider(labelProvider);
		dynamicMenu.addPulldownMenu(viewer, compareInput.gerritClient);
	}

	private void fillMenuItemForChangeInfo(MenuManager menu, boolean side) {
		ArrayList<RevisionInfo> revisions = new ArrayList<RevisionInfo>(
				compareInput.getChangeInfo().getRevisions().values());
		revisions.sort((o1, o2) -> o2.get_number() - o1.get_number());
		revisions.stream().forEach(rev -> menu.add(new SwitchPatchAction(compareInput, rev, side)));
	}

	@Override
	//This method is responsible for creating the drop downs for where the user can select the patchset
	protected Control createTopLeft(Composite p) {
		final Composite composite = new Composite(p, SWT.NONE) {
			@Override
			public Point computeSize(int wHint, int hHint, boolean changed) {
				return super.computeSize(wHint, Math.max(24, hHint), changed);
			}
		};

		composite.setLayout(new GridLayout(5, false));

		//Setup the text and button to select the left revision
		leftPatch = new Label(composite, SWT.LEFT | SWT.CENTER);
		GridData gridData = new GridData(SWT.LEFT, SWT.CENTER, false, false);
		gridData.widthHint = 90;
		leftPatch.setLayoutData(gridData);

		Button leftPatchSelector = new Button(composite, SWT.ARROW | SWT.DOWN);
		GridData gridDataLeftPathSelector = new GridData(SWT.LEFT, SWT.CENTER, false, false);
		gridDataLeftPathSelector.heightHint = 20;
		gridDataLeftPathSelector.widthHint = 20;
		leftPatchSelector.setLayoutData(gridDataLeftPathSelector);

		leftPatchSelector.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MenuManager mgr = new MenuManager();
				fillMenuItemForChangeInfo(mgr, true);
				mgr.add(new SwitchPatchAction(compareInput, "WORKSPACE", true)); //$NON-NLS-1$
				mgr.add(new SwitchPatchAction(compareInput, "BASE", true)); //$NON-NLS-1$
				mgr.createContextMenu(composite).setVisible(true);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				//Nothing to do
			}
		});

		Label separator = new Label(composite, SWT.CENTER);
		separator.setText("  /  "); //$NON-NLS-1$

		//Setup the text and button to select the right revision
		rightPatch = new Label(composite, SWT.NONE);
		GridData rightPatchData = new GridData(SWT.LEFT, SWT.CENTER, false, false);
		rightPatchData.widthHint = 90;
		rightPatch.setLayoutData(rightPatchData);

		Button rightPatchSelector = new Button(composite, SWT.DROP_DOWN | SWT.ARROW | SWT.DOWN);
		GridData gridDataRightPatchSelector = new GridData(SWT.LEFT, SWT.CENTER, false, false);
		gridDataRightPatchSelector.heightHint = 20;
		gridDataRightPatchSelector.widthHint = 20;
		rightPatchSelector.setLayoutData(gridDataRightPatchSelector);
		rightPatchSelector.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MenuManager mgr = new MenuManager();
				fillMenuItemForChangeInfo(mgr, false);
				mgr.add(new SwitchPatchAction(compareInput, "WORKSPACE", false)); //$NON-NLS-1$
				mgr.createContextMenu(composite).setVisible(true);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				//Nothing to do
			}
		});
		return composite;
	}

	@Override
	protected boolean inputChanged(Object input) {
		return getInput() != input;
	}

	@Override
	public void setText(String label) {
		//Do nothing
	}

	@Override
	public void setImage(Image image) {
		Composite c = (Composite) getTopLeft();
		Control[] children = c.getChildren();
		for (Control element : children) {
			if (element instanceof CLabel) {
				CLabel cl = (CLabel) element;
				if (cl != null && !cl.isDisposed()) {
					cl.setImage(image);
				}
				return;
			}
		}
	}

	@Override
	public void addMouseListener(MouseListener listener) {
		Composite c = (Composite) getTopLeft();
		Control[] children = c.getChildren();
		for (Control element : children) {
			if (element instanceof CLabel) {
				CLabel cl = (CLabel) element;
				cl.addMouseListener(listener);
			}
		}
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
			QueryHelpers.markAsNotReviewed(compareInput.gerritClient, fileInfo);
		} else {
			QueryHelpers.markAsReviewed(compareInput.gerritClient, fileInfo);
		}
	}

	private static class TreeFactoryImpl implements IObservableFactory {
		public IObservableList createObservable(final Object target) {
			return BeanProperties.list("children").observe(target); //$NON-NLS-1$
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

	public void setLeftLabel(String left) {
		leftPatch.setText(left);
	}

	public void setRightLabel(String right) {
		rightPatch.setText(right);
	}

	public DiffTreeViewer getDiffTreeViewer() {
		return viewer;

	}
}
