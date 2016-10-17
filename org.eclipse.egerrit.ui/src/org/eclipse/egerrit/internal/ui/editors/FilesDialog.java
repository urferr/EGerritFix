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

import org.eclipse.egerrit.internal.core.GerritClient;
import org.eclipse.egerrit.internal.model.RevisionInfo;
import org.eclipse.egerrit.internal.model.impl.StringToFileInfoImpl;
import org.eclipse.egerrit.internal.ui.table.UIFilesTable;
import org.eclipse.egerrit.internal.ui.table.provider.HandleFileSelection;
import org.eclipse.egerrit.internal.ui.utils.Messages;
import org.eclipse.egerrit.internal.ui.utils.UIUtils;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
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

/**
 * This class is used to open the list of files associated to specific patch-set of the active review
 *
 * @since 1.0
 */
public class FilesDialog extends Dialog {

	private RevisionInfo fRevisionInfo;

	private GerritClient fGerritClient;

	private UIFilesTable tableUIFiles = null;

	private ModifyListener textModifyListener = null;

	private static final int WIDTH = 800;

	private static final int HEIGHT = 500;

	/**
	 * The constructor.
	 */
	public FilesDialog(RevisionInfo revisionInfo, GerritClient gerritClient) {
		super(PlatformUI.getWorkbench().getModalDialogShellProvider().getShell());
		setShellStyle(SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.MAX | SWT.RESIZE | getDefaultOrientation());

		fRevisionInfo = revisionInfo;
		fGerritClient = gerritClient;
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
		tableUIFiles = new UIFilesTable(fGerritClient, fRevisionInfo);
		tableUIFiles.enablePopup(false);
		tableUIFiles.enableDeletedFilesFilter(true);
		tableUIFiles.enableCommitMsgFilter(true);
		tableUIFiles.createTableViewerSection(composite);
		tableUIFiles.setDialogSelection();
		createFilterArea(composite, tableUIFiles);

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

		tableUIFiles.getViewer().getTable().addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == SWT.SPACE) {
					HandleFileSelection handleSelection = new HandleFileSelection(fGerritClient,
							tableUIFiles.getViewer());
					handleSelection.showFileSelection();
					getOKButton().notifyListeners(SWT.Selection, new Event());
				}
			}
		});
		return parent;
	}

	private void createFilterArea(Composite parent, UIFilesTable tableUIFiles) {
		Composite composite = new Composite(parent, SWT.BORDER);
		composite.setLayout(new GridLayout(4, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		createDeleteFilterHandling(composite, tableUIFiles);
		createCommitMsgHandling(composite, tableUIFiles);
		createReviewedFilesHandling(composite, tableUIFiles);
		createCommentedFilesHandling(composite, tableUIFiles);
	}

	private void createDeleteFilterHandling(Composite composite, UIFilesTable tableUIFiles) {
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
	}

	private void createCommitMsgHandling(Composite composite, UIFilesTable tableUIFiles) {
		Button commitMsgFilter = new Button(composite, SWT.CHECK);
		commitMsgFilter.setText(Messages.FilesDialogCommitMessageFilter);
		commitMsgFilter.setSelection(false);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, false);
		commitMsgFilter.setLayoutData(data);
		commitMsgFilter.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (commitMsgFilter.getSelection()) {
					tableUIFiles.enableCommitMsgFilter(false);
					int index = getcommitMessageIndex(tableUIFiles.getViewer().getTable());
					if (index >= 0) {
						tableUIFiles.getViewer().getTable().setSelection(index);
						tableUIFiles.getViewer().setSelection(tableUIFiles.getViewer().getSelection(), true);
					}
				} else {
					tableUIFiles.enableCommitMsgFilter(true);
					if (tableUIFiles.getViewer().getTable().getItemCount() >= 1) {
						tableUIFiles.getViewer().getTable().setSelection(0);
						tableUIFiles.getViewer().setSelection(tableUIFiles.getViewer().getSelection(), true);
					}
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// ignore
			}
		});
	}

	private int getcommitMessageIndex(Table table) {
		TableItem[] items = table.getItems();
		int size = items.length;
		String COMMIT_MSG = "COMMIT_MSG".toLowerCase();
		for (int index = 0; index < size; index++) {
			StringToFileInfoImpl data = (StringToFileInfoImpl) items[index].getData();
			if (data.getKey().toLowerCase().contains(COMMIT_MSG)) {
				return index;
			}
		}
		return -1;
	}

	private void createReviewedFilesHandling(Composite composite, UIFilesTable tableUIFiles) {
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
	}

	private void createCommentedFilesHandling(Composite composite, UIFilesTable tableUIFiles) {
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

	private ModifyListener modifyListener(Text textMsgFilter) {
		textModifyListener = new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				if (tableUIFiles != null) {
					tableUIFiles.filterFileText(textMsgFilter.getText().trim());
				}
			}
		};
		return textModifyListener;
	}

}
