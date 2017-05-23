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

package org.eclipse.egerrit.internal.ui.editors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.egerrit.internal.core.GerritClient;
import org.eclipse.egerrit.internal.model.RevisionInfo;
import org.eclipse.egerrit.internal.model.impl.StringToFileInfoImpl;
import org.eclipse.egerrit.internal.ui.table.UIFilesTable;
import org.eclipse.egerrit.internal.ui.table.provider.HandleFileSelection;
import org.eclipse.egerrit.internal.ui.utils.Messages;
import org.eclipse.egerrit.internal.ui.utils.UIUtils;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

/**
 * This class is used to open the list of files associated to specific patch-set of the active review
 *
 * @since 1.0
 */
class FilesDialog extends Dialog {

	private static final String FILES_DIALOG = "egerrit.FilesDialog"; //$NON-NLS-1$

	private static final String VIEW_FILTER = "egerritViewFilter"; //$NON-NLS-1$

	private RevisionInfo fRevisionInfo;

	private GerritClient fGerritClient;

	private UIFilesTable tableUIFiles = null;

	private ModifyListener textModifyListener = null;

	private static final int WIDTH = 800;

	private static final int HEIGHT = 500;

	private FileEditorInput fFileInput = null;

	private TableViewer fViewer;

	private List<Button> listFilter = new ArrayList<>(5);

	private static FilesDialog openedDialog;

	/**
	 * The constructor.
	 *
	 * @param revisionInfo
	 * @param gerritClient
	 * @param fileInput
	 */
	FilesDialog(RevisionInfo revisionInfo, GerritClient gerritClient, FileEditorInput fileInput) {
		super(PlatformUI.getWorkbench().getModalDialogShellProvider().getShell());
		setShellStyle(SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.MAX | SWT.RESIZE | getDefaultOrientation());

		fRevisionInfo = revisionInfo;
		fGerritClient = gerritClient;
		fFileInput = fileInput;
		openedDialog = this;
	}

	private static String buildDefaultMessage(RevisionInfo revisionInfo) {
		return NLS.bind(Messages.FileDialog_PatchSet, new Object[] { revisionInfo.getChangeInfo().get_number(),
				revisionInfo.getChangeInfo().getSubject(), UIUtils.getPatchSetString(revisionInfo) });

	}

	@Override
	protected int getShellStyle() {
		return SWT.CLOSE | SWT.MIN | SWT.MAX | SWT.RESIZE;
	}

	/**
	 * Create the reply dialog
	 *
	 * @param parent
	 *            Composite
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		parent.getShell().setText(Messages.FileDialog_title);//Set the dialog title

		Composite composite = (Composite) super.createDialogArea(parent);

		Label label = new Label(composite, SWT.WRAP);
		label.setText(buildDefaultMessage(fRevisionInfo));
		GridData data = new GridData(SWT.FILL, SWT.TOP, true, false);
		label.setLayoutData(data);

		createTextMsgHandling(composite);
		tableUIFiles = new UIFilesTable(fGerritClient, fRevisionInfo, FILES_DIALOG);
		tableUIFiles.enablePopup(false);
		tableUIFiles.createTableViewerSection(composite);
		fViewer = tableUIFiles.getViewer();
		//Create a second listener to close the dialog
		fViewer.addDoubleClickListener(event -> {
			if (tableUIFiles.isSelectionOk()) {
				storeDialogSettings();
				FilesDialog.this.close();
			}
		});
		tableUIFiles.setDialogSelection();
		createFilterArea(composite, tableUIFiles);
		restoreDialogSettings();

		// Add a control listener to initialize a default minimum size
		parent.getShell().addControlListener(new ControlListener() {

			@Override
			public void controlResized(ControlEvent e) {
				Point size = parent.getShell().computeSize(WIDTH, HEIGHT);
				parent.getShell().setMinimumSize(size);
				parent.getShell().setSize(size);

				parent.getShell().removeControlListener(this);
			}

			@Override
			public void controlMoved(ControlEvent e) {
			}
		});

		//Add Navigation Listener
		Control[] children = getChildren(composite);
		addNavigationListener(children);
		for (Control element : children) {
			if (element instanceof Composite) {
				Control[] subChildren = getChildren((Composite) element);
				addNavigationListener(subChildren);
			}
		}
		return parent;
	}

	private void addNavigationListener(Control[] control) {
		for (Control element : control) {
			element.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					handleKeyboardEvent(e);
				}
			});
		}
	}

	private Control[] getChildren(Composite composite) {
		return composite.getChildren();
	}

	@Override
	protected void buttonPressed(int buttonId) {
		if (buttonId == IDialogConstants.OK_ID) {
			HandleFileSelection handleSelection = new HandleFileSelection(fGerritClient, fViewer);
			boolean selectionOk = handleSelection.showFileSelection();
			if (!selectionOk) {
				return; //Keep the FileDialog open since the selected file is not available in the workspace
			}
			storeDialogSettings();
		}

		super.buttonPressed(buttonId);
	}

	private void createFilterArea(Composite parent, UIFilesTable tableUIFiles) {
		Composite composite = new Composite(parent, SWT.BORDER);
		composite.setLayout(new GridLayout(4, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		listFilter.add(createDeleteFilterHandling(composite, tableUIFiles));
		listFilter.add(createCommitMsgHandling(composite, tableUIFiles));
		listFilter.add(createReviewedFilesHandling(composite, tableUIFiles));
		listFilter.add(createCommentedFilesHandling(composite, tableUIFiles));
	}

	private Button createDeleteFilterHandling(Composite composite, UIFilesTable tableUIFiles) {
		Button deleteFilter = new Button(composite, SWT.CHECK);
		deleteFilter.setText(Messages.FilesDialogDeleteFilter);
		deleteFilter.setSelection(false);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, false);
		deleteFilter.setLayoutData(data);
		deleteFilter.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (deleteFilter.getSelection()) {
					tableUIFiles.enableDeletedFilesFilter(false);
				} else {
					tableUIFiles.enableDeletedFilesFilter(true);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// ignore
			}
		});
		return deleteFilter;
	}

	private Button createCommitMsgHandling(Composite composite, UIFilesTable tableUIFiles) {
		Button commitMsgFilter = new Button(composite, SWT.CHECK);
		commitMsgFilter.setText(Messages.FilesDialogCommitMessageFilter);
		commitMsgFilter.setSelection(true);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, false);
		commitMsgFilter.setLayoutData(data);
		commitMsgFilter.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				//When selected, show the file, --> remove the filter
				if (commitMsgFilter.getSelection()) {
					tableUIFiles.enableCommitMsgFilter(false);
					int index = getcommitMessageIndex(fViewer.getTable());
					if (index >= 0) {
						fViewer.getTable().setSelection(index);
						fViewer.setSelection(fViewer.getSelection(), true);
					}
				} else {
					//if toggle not selected, hide the file --> enable the filter
					tableUIFiles.enableCommitMsgFilter(true);
					if (fViewer.getTable().getItemCount() >= 1) {
						fViewer.getTable().setSelection(0);
						fViewer.setSelection(fViewer.getSelection(), true);
					}
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// ignore
			}
		});
		return commitMsgFilter;
	}

	private int getcommitMessageIndex(Table table) {
		TableItem[] items = table.getItems();
		int size = items.length;
		String commitMsg = "COMMIT_MSG".toLowerCase(); //$NON-NLS-1$
		for (int index = 0; index < size; index++) {
			StringToFileInfoImpl data = (StringToFileInfoImpl) items[index].getData();
			if (data.getKey().toLowerCase().contains(commitMsg)) {
				return index;
			}
		}
		return -1;
	}

	private Button createReviewedFilesHandling(Composite composite, UIFilesTable tableUIFiles) {
		Button reviewedFilesFilter = new Button(composite, SWT.CHECK);
		reviewedFilesFilter.setText(Messages.FilesDialogReviewedFilesFilter);
		reviewedFilesFilter.setSelection(true);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, false);
		reviewedFilesFilter.setLayoutData(data);

		reviewedFilesFilter.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (reviewedFilesFilter.getSelection()) {
					tableUIFiles.enableReviewedFilesFilter(false);
				} else {
					tableUIFiles.enableReviewedFilesFilter(true);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// ignore
			}
		});
		tableUIFiles.enableReviewedFilesFilter(false);
		return reviewedFilesFilter;
	}

	private Button createCommentedFilesHandling(Composite composite, UIFilesTable tableUIFiles) {
		Button commentedFilesFilter = new Button(composite, SWT.CHECK);
		commentedFilesFilter.setText(Messages.FilesDialogCommentedFilesFilter);
		commentedFilesFilter.setSelection(false);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, false);
		commentedFilesFilter.setLayoutData(data);
		commentedFilesFilter.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (commentedFilesFilter.getSelection()) {
					tableUIFiles.enableCommentedFilesFilter(true);
				} else {
					tableUIFiles.enableCommentedFilesFilter(false);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// ignore
			}
		});
		return commentedFilesFilter;
	}

	private void createTextMsgHandling(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		composite.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND));

		Text textMsgFilter = new Text(composite, SWT.BORDER);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, false);
		textMsgFilter.setLayoutData(data);
		textMsgFilter.setText(Messages.TypeFilterText);
		textMsgFilter.setToolTipText(Messages.TypeFilterText);
		textMsgFilter.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				if (textMsgFilter.getText().isEmpty() && textModifyListener != null) {
					textMsgFilter.removeModifyListener(textModifyListener);
					textModifyListener = null;
				}
				ViewerFilter searchFilter = tableUIFiles.getSearchingFilter();
				if (searchFilter == null) {
					textMsgFilter.append(Messages.TypeFilterText);
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
				ViewerFilter searchFilter = tableUIFiles.getSearchingFilter();
				if (searchFilter == null) {
					textMsgFilter.setText(""); //$NON-NLS-1$
					textMsgFilter.addModifyListener(modifyListener(textMsgFilter));
				}
			}
		});

		Button clearBtn = new Button(composite, SWT.Arm);
		data = new GridData(SWT.RIGHT, SWT.FILL, false, false);
		clearBtn.setLayoutData(data);
		clearBtn.setText(Messages.FilterClear);
		clearBtn.setToolTipText(Messages.FilterClearTooltip);
		clearBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				textMsgFilter.setText(""); //$NON-NLS-1$
				textMsgFilter.notifyListeners(SWT.FocusOut, new Event());
			}
		});
	}

	private void handleKeyboardEvent(KeyEvent e) {
		if (e.keyCode == SWT.ARROW_DOWN || e.keyCode == SWT.ARROW_UP) {
			//Setting the focus will allow the command "SHIFT + ARROW_DOWN or ARROW_UP
			//to select other elements from the table after the second hit
			//Just the ARROW_DOWN or ARROW_UP allows the selection of the next/previous element in the table
			fViewer.getTable().setFocus();
		}

		//Key enter
		if (e.keyCode == SWT.CR) {
			buttonPressed(IDialogConstants.OK_ID);
		}
	}

	private ModifyListener modifyListener(Text textMsgFilter) {
		textModifyListener = event -> {

			if (tableUIFiles != null) {
				tableUIFiles.filterFileText(textMsgFilter.getText().trim());
			}
		};
		return textModifyListener;
	}

	private int findActiveFileInReview() {
		if (tableUIFiles == null) {
			create();
		}
		String filepath = fFileInput.getFile().getFullPath().toString().trim();
		if (filepath.startsWith("/")) { //$NON-NLS-1$
			filepath = filepath.substring(1, filepath.length());
		}
		TableItem[] tableItems = fViewer.getTable().getItems();
		for (int index = 0; index < tableItems.length; index++) {
			if (tableItems[index].getData() instanceof StringToFileInfoImpl) {
				StringToFileInfoImpl entry = (StringToFileInfoImpl) tableItems[index].getData();
				if (filepath.endsWith(entry.getKey().trim())) {
					return index;
				}
			}
		}

		return -1;//Did not find the selected file
	}

	private void revealElementAt(int currentIndex) {
		Table table = fViewer.getTable();
		table.deselectAll();
		int maxTableItem = table.getItemCount() - 1; //index start at 0
		int nextIndex = currentIndex + 1;
		if (nextIndex < 0) {
			//Happen when moving backward and select the first element
			nextIndex = 0;
		}
		if (nextIndex <= maxTableItem) {
			table.setSelection(nextIndex);
		} else {
			//Select the last item in the table (Even the Commit MSG )
			table.setSelection(maxTableItem);
		}
		table.showSelection();
	}

	@Override
	public int open() {
		if (fFileInput != null) {
			int currentIndex = findActiveFileInReview();
			if (currentIndex != -1) {
				//Automatically select the next file
				revealElementAt(currentIndex);
			}
		}
		return super.open();
	}

	private void storeDialogSettings() {
		//Adjust the filter
		String[] arrayBoolean = new String[listFilter.size()];
		for (int i = 0; i < listFilter.size(); i++) {
			arrayBoolean[i] = String.valueOf(listFilter.get(i).getSelection());
		}
		//Store specific data for the dialog
		tableUIFiles.getDialogSettings().put(VIEW_FILTER, arrayBoolean);
		//Store the table data
		tableUIFiles.getStorageSettings().storeDialogSettings();
	}

	private void restoreDialogSettings() {
		//Just the filter, the column are taken care of by the UIFiles
		String[] backedUpValue = tableUIFiles.getDialogSettings().getArray(VIEW_FILTER);
		if (backedUpValue != null) {
			for (int i = 0; i < backedUpValue.length; i++) {
				listFilter.get(i).setSelection(StringConverter.asBoolean(backedUpValue[i]));
				listFilter.get(i).notifyListeners(SWT.Selection, new Event());
			}
		}
	}

	@Override
	public boolean close() {
		openedDialog = null;
		return super.close();
	}

	public static FilesDialog openedDialog() {
		return openedDialog;
	}

	void selectNextFile() {
		revealElementAt(fViewer.getTable().getSelectionIndex());
	}

	void selectPreviousFile() {
		revealElementAt(fViewer.getTable().getSelectionIndex() - 2);
	}
}
