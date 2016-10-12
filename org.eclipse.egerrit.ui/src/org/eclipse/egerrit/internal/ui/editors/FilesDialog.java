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
import org.eclipse.egerrit.internal.ui.table.UIFilesTable;
import org.eclipse.egerrit.internal.ui.table.provider.HandleFileSelection;
import org.eclipse.egerrit.internal.ui.utils.Messages;
import org.eclipse.egerrit.internal.ui.utils.UIUtils;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.PlatformUI;

/**
 * This class is used to open the list of files associated to specific patch-set of the active review
 *
 * @since 1.0
 */
public class FilesDialog extends Dialog {

	private RevisionInfo fRevisionInfo;

	private GerritClient fGerritClient;

	private static final int WIDTH = 650;

	private static final int HEIGHT = 400;

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
		Composite composite = (Composite) super.createDialogArea(parent);
		parent.getShell().setText(Messages.FileDialog_title);//Dialog title
		Label label = new Label(composite, SWT.WRAP);
		label.setText(buildDefaultMessage(fRevisionInfo));
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, false);
		data.widthHint = convertHorizontalDLUsToPixels(IDialogConstants.MINIMUM_MESSAGE_AREA_WIDTH);
		label.setLayoutData(data);
		label.setFont(parent.getFont());

		UIFilesTable tableUIFiles = new UIFilesTable(fGerritClient, fRevisionInfo);
		tableUIFiles.enablePopup(false);
		tableUIFiles.enableDeletedFilesFilter(true);
		tableUIFiles.createTableViewerSection(composite);
		tableUIFiles.setDialogSelection();

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
}
