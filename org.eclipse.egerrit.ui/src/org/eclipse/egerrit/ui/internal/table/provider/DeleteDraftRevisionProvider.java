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

package org.eclipse.egerrit.ui.internal.table.provider;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.egerrit.core.EGerritCorePlugin;
import org.eclipse.egerrit.core.GerritClient;
import org.eclipse.egerrit.core.command.DeleteDraftRevisionCommand;
import org.eclipse.egerrit.core.exception.EGerritException;
import org.eclipse.egerrit.internal.model.ChangeInfo;
import org.eclipse.egerrit.internal.model.ModelPackage;
import org.eclipse.egerrit.ui.internal.utils.DataConverter;
import org.eclipse.egerrit.ui.internal.utils.LinkDashboard;
import org.eclipse.emf.databinding.EMFProperties;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

/**
 * @author lmcbout
 */
public class DeleteDraftRevisionProvider {

	private Button fDeleteDraftRevisionButton;

	public void create(Composite parent, GerritClient gerritClient, ChangeInfo changeInfo) {

		fDeleteDraftRevisionButton = new Button(parent, SWT.BORDER);
		fDeleteDraftRevisionButton.setText("Delete Revision");
		GridData gd_deleteDraft = new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1);
		fDeleteDraftRevisionButton.setLayoutData(gd_deleteDraft);
		fDeleteDraftRevisionButton.setToolTipText("Delete Draft Revision");
		fDeleteDraftRevisionButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!MessageDialog.openConfirm(fDeleteDraftRevisionButton.getParent().getShell(),
						"Delete draft revision", "Continue ?")) {
					return;
				}
				DeleteDraftRevisionCommand deleteDraftChangeCmd = gerritClient.deleteDraftRevision(changeInfo.getId(),
						changeInfo.getUserSelectedRevision().getId());
				try {
					deleteDraftChangeCmd.call();
					if (changeInfo.getRevisions().size() == 1) {
						IWorkbench workbench = PlatformUI.getWorkbench();
						final IWorkbenchPage activePage = workbench.getActiveWorkbenchWindow().getActivePage();

						IEditorPart editor = activePage.getActiveEditor();
						activePage.closeEditor(editor, false);
					}
					LinkDashboard linkDash = new LinkDashboard(gerritClient);
					linkDash.invokeRefreshDashboardCommand("", ""); //$NON-NLS-1$ //$NON-NLS-2$
				} catch (EGerritException e1) {
					EGerritCorePlugin.logError(gerritClient.getRepository().formatGerritVersion() + e1.getMessage());
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// ignore
			}
		});
		addBinding(changeInfo);
	}

	/**
	 * Create all potential binding which could affect the delete revision button
	 *
	 * @param changeInfo
	 */
	private void addBinding(ChangeInfo changeInfo) {
		DataBindingContext bindingContext = new DataBindingContext();
		{
			//show when revision number changes
			IObservableValue revisionInfoObserveValue = EMFProperties
					.value(ModelPackage.Literals.CHANGE_INFO__USER_SELECTED_REVISION).observe(changeInfo);
			bindingContext.bindValue(WidgetProperties.text().observe(fDeleteDraftRevisionButton),
					revisionInfoObserveValue, null, new UpdateValueStrategy().setConverter(
							DataConverter.deleteRevisionConverter(changeInfo, fDeleteDraftRevisionButton)));

			//Test on STATUS if status changes when the editor is open
			IObservableValue statusObserveValue = EMFProperties.value(ModelPackage.Literals.CHANGE_INFO__STATUS)
					.observe(changeInfo);
			bindingContext.bindValue(WidgetProperties.text().observe(fDeleteDraftRevisionButton), statusObserveValue,
					null, new UpdateValueStrategy().setConverter(
							DataConverter.deleteRevisionConverter(changeInfo, fDeleteDraftRevisionButton)));

			//Testing the value DRAFT in the revision Info structure
			IObservableValue actionObserveTest = EMFProperties.value(ModelPackage.Literals.REVISION_INFO__DRAFT)
					.observe(changeInfo.getUserSelectedRevision());

			bindingContext.bindValue(WidgetProperties.text().observe(fDeleteDraftRevisionButton), actionObserveTest,
					null, new UpdateValueStrategy().setConverter(
							DataConverter.deleteRevisionConverter(changeInfo, fDeleteDraftRevisionButton)));
		}
	}
}
