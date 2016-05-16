/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Guy Perron - initial API and implementation
 *     Jacques Bouthillier - Fill the message tab
 *     Jacques Bouthillier - Refactor the class
 *
 *******************************************************************************/

package org.eclipse.egerrit.ui.editors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.conversion.NumberToStringConverter;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.egerrit.core.EGerritCorePlugin;
import org.eclipse.egerrit.core.GerritClient;
import org.eclipse.egerrit.core.command.AbandonCommand;
import org.eclipse.egerrit.core.command.CherryPickRevisionCommand;
import org.eclipse.egerrit.core.command.DeleteDraftChangeCommand;
import org.eclipse.egerrit.core.command.ListBranchesCommand;
import org.eclipse.egerrit.core.command.PublishDraftChangeCommand;
import org.eclipse.egerrit.core.command.RebaseRevisionCommand;
import org.eclipse.egerrit.core.command.RestoreCommand;
import org.eclipse.egerrit.core.command.RevertCommand;
import org.eclipse.egerrit.core.command.SetReviewCommand;
import org.eclipse.egerrit.core.command.SubmitCommand;
import org.eclipse.egerrit.core.exception.EGerritException;
import org.eclipse.egerrit.core.rest.AbandonInput;
import org.eclipse.egerrit.core.rest.CherryPickInput;
import org.eclipse.egerrit.core.rest.RebaseInput;
import org.eclipse.egerrit.core.rest.RestoreInput;
import org.eclipse.egerrit.core.rest.RevertInput;
import org.eclipse.egerrit.core.rest.ReviewInput;
import org.eclipse.egerrit.core.rest.SubmitInput;
import org.eclipse.egerrit.internal.model.ActionConstants;
import org.eclipse.egerrit.internal.model.BranchInfo;
import org.eclipse.egerrit.internal.model.ChangeInfo;
import org.eclipse.egerrit.internal.model.LabelInfo;
import org.eclipse.egerrit.internal.model.ModelPackage;
import org.eclipse.egerrit.internal.model.RevisionInfo;
import org.eclipse.egerrit.ui.EGerritUIPlugin;
import org.eclipse.egerrit.ui.editors.model.ChangeDetailEditorInput;
import org.eclipse.egerrit.ui.internal.table.provider.DeleteDraftRevisionProvider;
import org.eclipse.egerrit.ui.internal.table.provider.PatchSetHandlerProvider;
import org.eclipse.egerrit.ui.internal.tabs.HistoryTabView;
import org.eclipse.egerrit.ui.internal.tabs.MessageTabView;
import org.eclipse.egerrit.ui.internal.tabs.ObservableCollector;
import org.eclipse.egerrit.ui.internal.tabs.SummaryTabView;
import org.eclipse.egerrit.ui.internal.utils.ActiveWorkspaceRevision;
import org.eclipse.egerrit.ui.internal.utils.UIUtils;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.databinding.EMFProperties;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.icu.text.NumberFormat;

public class ChangeDetailEditor extends EditorPart {
	private static Logger logger = LoggerFactory.getLogger(ChangeDetailEditor.class);

	private static final String VERIFIED = "Verified";

	private static final String CODE_REVIEW = "Code-Review";

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String EDITOR_ID = "org.eclipse.egerrit.ui.editors.ChangeDetailEditor"; //$NON-NLS-1$

	public SummaryTabView summaryTab = null;

	private HistoryTabView historytab = null;

	private MessageTabView messageTab = null;

	private ChangeInfo fChangeInfo;

	private GerritClient fGerritClient = null;

	private Text subjectData;

	private Text statusData;

	private Text shortIdData;

	private Composite headerSection;

	private ModelLoader loader;
	// ------------------------------------------------------------------------
	// Constructor and life cycle
	// ------------------------------------------------------------------------

	private EContentAdapter subjectListener;

	private DataBindingContext bindingContext = new DataBindingContext();

	private DeleteDraftRevisionProvider deleteDraftRevision = null;

	private PatchSetHandlerProvider patchSetSelector;

	private HideControlObservable hidableRevertButton;

	private HideControlObservable hidableSubmitButton;

	private ObservableCollector observableCollector;

	/**
	 * The constructor.
	 */
	public ChangeDetailEditor() {
		super();
	}

	@Override
	public void createPartControl(final Composite parent) {
		loader = ModelLoader.initialize(fGerritClient, fChangeInfo);
		loader.loadBasicInformation();
		parent.setLayout(new GridLayout(1, false));
		parent.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND));

		headerSection = headerSection(parent);
		headerSection.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));

		TabFolder tabFolder = new TabFolder(parent, SWT.NONE);
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		tabFolder.setBackground(parent.getBackground());

		historytab = new HistoryTabView();
		historytab.create(fGerritClient, tabFolder, fChangeInfo);

		messageTab = new MessageTabView();
		messageTab.create(fGerritClient, tabFolder, fChangeInfo);

		summaryTab = new SummaryTabView();
		summaryTab.create(fGerritClient, tabFolder, fChangeInfo);

		Composite compButton = buttonSection(parent);
		compButton.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));

		setPartName(((ChangeDetailEditorInput) getEditorInput()).getName());
		observableCollector = new ObservableCollector(bindingContext);
	}

	private Composite headerSection(final Composite parent) {
		Group group_header = new Group(parent, SWT.NONE);
		group_header.setLayout(new GridLayout(10, false));
		group_header.setBackground(parent.getBackground());

		Label lblId = new Label(group_header, SWT.NONE);
		lblId.setText("ID:");

		shortIdData = new Text(group_header, SWT.NONE);
		shortIdData.setEditable(false);
		shortIdData.setBackground(parent.getBackground());
		shortIdData.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));

		statusData = new Text(group_header, SWT.LEFT);
		GridData gd_lblStatus = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		statusData.setLayoutData(gd_lblStatus);
		statusData.setEditable(false);
		statusData.addListener(SWT.Modify, new Listener() {

			@Override
			public void handleEvent(Event event) {
				statusData.getParent().layout();
			}
		});

		Label lblSubject = new Label(group_header, SWT.NONE);
		lblSubject.setText("Subject:");
		GridData gd_Subject = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_Subject.horizontalIndent = 10;
		lblSubject.setLayoutData(gd_Subject);

		subjectData = new Text(group_header, SWT.NONE);
		GridData gd_lblSubjectData = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		subjectData.setLayoutData(gd_lblSubjectData);
		subjectData.setEditable(false);
		subjectData.setBackground(parent.getBackground());
		subjectData.addListener(SWT.Modify, new Listener() {

			@Override
			public void handleEvent(Event event) {
				subjectData.getParent().layout();
			}
		});

		final String ACTIVATION_MESSAGE = "Activate Comment Markers";
		Button activeReview = new Button(group_header, SWT.CHECK);
		activeReview.setSelection(false);
		activeReview.setText(ACTIVATION_MESSAGE);
		activeReview.setToolTipText("Toggle to create markers for each comment in the currently selected revision");

		IObservableValue observeActiveRevisionStateForButtonText = BeanProperties.value("activeRevision")
				.observe(ActiveWorkspaceRevision.getInstance());
		bindingContext.bindValue(WidgetProperties.text().observe(activeReview), observeActiveRevisionStateForButtonText,
				new UpdateValueStrategy(UpdateValueStrategy.POLICY_NEVER), new UpdateValueStrategy() {
					@Override
					public Object convert(Object value) {
						if (value == null) {
							return ACTIVATION_MESSAGE;
						}
						if (((RevisionInfo) value).getChangeInfo().getId().equals(fChangeInfo.getId())) {
							return "Markers for patchset " + (((RevisionInfo) value)).get_number();
						}
						return ACTIVATION_MESSAGE;
					}
				});

		IObservableValue observeActiveRevisionStateForButtonEnablement = BeanProperties.value("activeRevision")
				.observe(ActiveWorkspaceRevision.getInstance());
		bindingContext.bindValue(WidgetProperties.selection().observe(activeReview),
				observeActiveRevisionStateForButtonEnablement,
				new UpdateValueStrategy(UpdateValueStrategy.POLICY_NEVER), new UpdateValueStrategy() {
					@Override
					public Object convert(Object value) {
						if (value == null) {
							return false;
						}
						if (((RevisionInfo) value).getChangeInfo().getId().equals(fChangeInfo.getId())) {
							return true;
						}
						return false;
					}
				});

		activeReview.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (activeReview.getSelection()) {

					ActiveWorkspaceRevision.getInstance().activateCurrentRevision(fGerritClient,
							fChangeInfo.getUserSelectedRevision() != null
									? fChangeInfo.getUserSelectedRevision()
									: fChangeInfo.getRevision());
					try {
						PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(
								"org.eclipse.ui.views.AllMarkersView"); //$NON-NLS-1$

					} catch (PartInitException e1) {
						EGerritCorePlugin
								.logError(fGerritClient.getRepository().formatGerritVersion() + e1.getMessage());
					}

				} else {
					ActiveWorkspaceRevision.getInstance().deactiveCurrentRevision();
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// ignore
			}
		});

		//Add Delete revision button for DRAFT patchset
		deleteDraftRevision = new DeleteDraftRevisionProvider();
		deleteDraftRevision.create(group_header, fGerritClient, fChangeInfo);

		//Create the PatchSetButton
		patchSetSelector = new PatchSetHandlerProvider();
		patchSetSelector.create(group_header, fChangeInfo);

		//Set the binding for this section
		headerSectionDataBindings();
		return group_header;
	}

	private Composite buttonSection(final Composite parent) {
		final int NUMBER_OF_BUTTONS = 8;
		final Composite c = new Composite(parent, SWT.NONE);
		c.setLayout(new GridLayout(NUMBER_OF_BUTTONS, true));

		Button refresh = new Button(c, SWT.PUSH);
		refresh.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		refresh.setText(ActionConstants.REFRESH.getLiteral());
		refresh.setToolTipText("Reload this change from the server.");
		refresh.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				refreshStatus();
			}
		});

		Button submitButton = new Button(c, SWT.PUSH);
		submitButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		submitButton.setText(ActionConstants.SUBMIT.getLiteral());
		submitButton.setEnabled(false);

		Button revertButton = new Button(c, SWT.PUSH);
		revertButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		revertButton.setText(ActionConstants.REVERT.getLiteral());
		revertButton.setEnabled(false);

		//Bind the submit button
		{
			IObservableValue observeSubmitable = EMFProperties
					.value(ModelPackage.Literals.CHANGE_INFO__USER_SELECTED_REVISION)
					.value(ModelPackage.Literals.REVISION_INFO__SUBMITABLE)
					.observe(fChangeInfo);
			bindingContext.bindValue(WidgetProperties.enabled().observe(submitButton), observeSubmitable,
					new UpdateValueStrategy(UpdateValueStrategy.POLICY_NEVER), null);
			bindingContext.bindValue(WidgetProperties.visible().observe(submitButton), observeSubmitable,
					new UpdateValueStrategy(UpdateValueStrategy.POLICY_NEVER), new UpdateValueStrategy() {
						@Override
						public Object convert(Object value) {
							boolean state = ((Boolean) value).booleanValue();
							if (state) {
								return true;
							}
							if (revertButton.isVisible()) {
								return false;
							}
							return true;
						}
					});
		}

		//Bind the revert button
		{
			IObservableValue observeRevertable = EMFProperties.value(ModelPackage.Literals.CHANGE_INFO__REVERTABLE)
					.observe(fChangeInfo);
			bindingContext.bindValue(WidgetProperties.enabled().observe(revertButton), observeRevertable,
					new UpdateValueStrategy(UpdateValueStrategy.POLICY_NEVER), null);
			bindingContext.bindValue(WidgetProperties.visible().observe(revertButton), observeRevertable,
					new UpdateValueStrategy(UpdateValueStrategy.POLICY_NEVER), null);

			//When the revert is not enable, we need to force the submit button to be visible
			bindingContext.bindValue(WidgetProperties.visible().observe(submitButton), observeRevertable,
					new UpdateValueStrategy(UpdateValueStrategy.POLICY_NEVER), new NegateBooleanConverter());

			//We need to force a redraw of the layout to remove the whitespace that would otherwise be left by the buttons being hidden
			hidableSubmitButton = new HideControlObservable(submitButton);
			bindingContext.bindValue(hidableSubmitButton, observeRevertable,
					new UpdateValueStrategy(UpdateValueStrategy.POLICY_NEVER), new NegateBooleanConverter());
			hidableRevertButton = new HideControlObservable(revertButton);
			bindingContext.bindValue(new HideControlObservable(revertButton), observeRevertable, null, null);
		}

		submitButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
				SubmitCommand submitCmd = fGerritClient.submit(fChangeInfo.getId());
				SubmitInput submitInput = new SubmitInput();
				submitInput.setWait_for_merge(true);

				submitCmd.setCommandInput(submitInput);

				try {
					submitCmd.call();
				} catch (EGerritException e3) {
					EGerritCorePlugin.logError(fGerritClient.getRepository().formatGerritVersion() + e3.getMessage());
				}
				refreshStatus();
			}
		});

		revertButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);

				String revertMsg = "Revert \"" + fChangeInfo.getSubject() + "\"\n\n" + "This reverts commit " //$NON-NLS-1$
						+ fChangeInfo.getCurrent_revision() + "\nReview number: " + fChangeInfo.get_number() //$NON-NLS-1$
						+ ".";
				RevertCommand revertCmd = fGerritClient.revert(fChangeInfo.getId());
				RevertInput revertInput = new RevertInput();
				revertInput.setMessage(revertMsg);

				revertCmd.setCommandInput(revertInput);

				ChangeInfo revertResult = null;
				try {
					revertResult = revertCmd.call();
				} catch (EGerritException e3) {
					EGerritCorePlugin.logError(fGerritClient.getRepository().formatGerritVersion() + e3.getMessage());
				}
				openAnotherEditor(revertResult);
			}
		});

		Button fAbandon = new Button(c, SWT.PUSH);
		fAbandon.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		fAbandon.setEnabled(false);
		fAbandon.setText(ActionConstants.ABANDON.getLiteral());

		Button fRestore = new Button(c, SWT.PUSH);
		fRestore.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		fRestore.setEnabled(false);
		fRestore.setText(ActionConstants.RESTORE.getLiteral());
		//Bind the abandon button
		{
			IObservableValue observeAbandonable = EMFProperties.value(ModelPackage.Literals.CHANGE_INFO__ABANDONABLE)
					.observe(fChangeInfo);
			bindingContext.bindValue(WidgetProperties.enabled().observe(fAbandon), observeAbandonable,
					new UpdateValueStrategy(UpdateValueStrategy.POLICY_NEVER), null);
			bindingContext.bindValue(WidgetProperties.visible().observe(fAbandon), observeAbandonable,
					new UpdateValueStrategy(UpdateValueStrategy.POLICY_NEVER), null);
			bindingContext.bindValue(WidgetProperties.visible().observe(fAbandon), observeAbandonable,
					new UpdateValueStrategy(UpdateValueStrategy.POLICY_NEVER), new UpdateValueStrategy() {
						@Override
						public Object convert(Object value) {
							boolean state = ((Boolean) value).booleanValue();
							if (state) {
								return true;
							}
							if (fRestore.isVisible()) {
								//We want to make sure to always show the submit button even if the restore is disabled so we at least get one button
								return false;
							}
							return true;
						}
					});
		}
		//Bind the restore button
		{
			IObservableValue observeRestorable = EMFProperties.value(ModelPackage.Literals.CHANGE_INFO__RESTOREABLE)
					.observe(fChangeInfo);
			bindingContext.bindValue(WidgetProperties.enabled().observe(fRestore), observeRestorable,
					new UpdateValueStrategy(UpdateValueStrategy.POLICY_NEVER), null);
			bindingContext.bindValue(WidgetProperties.visible().observe(fRestore), observeRestorable, null,
					new NegateBooleanConverter());

			//When restore is not enable, we need to force the abandon button to be visible
			bindingContext.bindValue(WidgetProperties.visible().observe(fAbandon), observeRestorable,
					new UpdateValueStrategy(UpdateValueStrategy.POLICY_NEVER), new NegateBooleanConverter());

			//We need to force a redraw of the layout to remove the whitespace that would otherwise be left by the buttons being hidden
			bindingContext.bindValue(new HideControlObservable(fAbandon), observeRestorable,
					new UpdateValueStrategy(UpdateValueStrategy.POLICY_NEVER), new NegateBooleanConverter());
			bindingContext.bindValue(new HideControlObservable(fRestore), observeRestorable, null, null);
		}

		fAbandon.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
				InputDialog inputDialog = new InputDialog(fAbandon.getParent().getShell(), "Abandon message",
						"Enter the abandon message", "", null);
				if (inputDialog.open() != Window.OK) {
					return;
				}

				AbandonCommand abandonCmd = fGerritClient.abandon(fChangeInfo.getId());
				AbandonInput abandonInput = new AbandonInput();
				abandonInput.setMessage(inputDialog.getValue());

				abandonCmd.setCommandInput(abandonInput);

				try {
					abandonCmd.call();
				} catch (EGerritException e3) {
					EGerritCorePlugin.logError(fGerritClient.getRepository().formatGerritVersion() + e3.getMessage());
				}
				refreshStatus();
			}
		});

		fRestore.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
				InputDialog inputDialog = new InputDialog(fAbandon.getParent().getShell(), "Restore message",
						"Enter the restore message", "", null);
				if (inputDialog.open() != Window.OK) {
					return;
				}

				RestoreCommand restoreCmd = fGerritClient.restore(fChangeInfo.getId());
				RestoreInput restoreInput = new RestoreInput();
				restoreInput.setMessage(inputDialog.getValue());

				restoreCmd.setCommandInput(restoreInput);

				try {
					restoreCmd.call();
				} catch (EGerritException e3) {
					EGerritCorePlugin.logError(fGerritClient.getRepository().formatGerritVersion() + e3.getMessage());
				}
				refreshStatus();
			}
		});

		Button rebaseButton = new Button(c, SWT.PUSH);
		rebaseButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		rebaseButton.setText(ActionConstants.REBASE.getLiteral());
		rebaseButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				InputDialog inputDialog = new InputDialog(rebaseButton.getParent().getShell(),
						"Code Review - Rebase Change",
						"Change parent revision (leave empty to rebase on targeted branch)", "", null) {

					public void enableOk(boolean isEnable) {
						getOkButton().setEnabled(isEnable);
					}

					@Override
					protected void createButtonsForButtonBar(Composite parent) {
						super.createButtonsForButtonBar(parent);
						getText().addModifyListener(new ModifyListener() {
							@Override
							public void modifyText(ModifyEvent e) {
								if (!fChangeInfo.getUserSelectedRevision().isRebaseable()) {
									if (!getText().getText().isEmpty()) {
										getOkButton().setEnabled(true);
									} else {
										getOkButton().setEnabled(false);
									}
								}
							}
						});
						getOkButton().setEnabled(fChangeInfo.getUserSelectedRevision().isRebaseable());
						return;
					}

				};

				if (inputDialog.open() != Window.OK) {
					return;
				}
				RebaseRevisionCommand rebaseCmd = fGerritClient.rebase(fChangeInfo.getId(),
						fChangeInfo.getUserSelectedRevision().getId());
				RebaseInput rebaseInput = new RebaseInput();
				rebaseInput.setBase(inputDialog.getValue().trim().length() == 0 ? null : inputDialog.getValue().trim());

				rebaseCmd.setCommandInput(rebaseInput);

				try {
					rebaseCmd.call();
				} catch (EGerritException e1) {
					if (e1.getCode() == EGerritException.SHOWABLE_MESSAGE) {
						MessageDialog.open(MessageDialog.INFORMATION, null, "Rebase failed",
								"Gerrit could not perform the rebase automatically. You need to perform the rebase locally.",
								SWT.NONE);
					} else {
						EGerritCorePlugin
								.logError(fGerritClient.getRepository().formatGerritVersion() + e1.getMessage());
					}
				}
				//After a rebase, we reload and reset the user selected revision
				//Note that here we are not using the model loader because we want a synchronous call so we can set the user selection
				CompletableFuture.runAsync(() -> QueryHelpers.loadBasicInformation(fGerritClient, fChangeInfo))
						.thenRun(() -> fChangeInfo.setUserSelectedRevision(fChangeInfo.getRevision()));
			}
		});

		Button download = new Button(c, SWT.PUSH);
		download.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		download.setText("Download");
		download.addSelectionListener(downloadButtonListener(parent));//checkoutButtonListener(parent));

		Button cherryPickToRemoteBranch = new Button(c, SWT.PUSH);
		cherryPickToRemoteBranch.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		cherryPickToRemoteBranch.setText(ActionConstants.CHERRYPICK.getLiteral());
		IObservableValue cherryPickAble = EMFProperties.value(ModelPackage.Literals.CHANGE_INFO__USER_SELECTED_REVISION)
				.value(ModelPackage.Literals.REVISION_INFO__CHERRYPICKABLE)
				.observe(fChangeInfo);
		bindingContext.bindValue(WidgetProperties.enabled().observe(cherryPickToRemoteBranch), cherryPickAble, null,
				null);

		cherryPickToRemoteBranch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				BranchInfo[] listBranchesCmdResult = listBranches();

				List<String> listBranchesRef = new ArrayList<String>();
				Iterator<BranchInfo> it = Arrays.asList(listBranchesCmdResult).iterator();
				while (it.hasNext()) {
					listBranchesRef.add(it.next().getRef());
				}

				final CherryPickDialog cherryPickDialog = new CherryPickDialog(
						cherryPickToRemoteBranch.getParent().getShell(), listBranchesRef,
						fChangeInfo.getUserSelectedRevision().getCommit().getMessage());
				Display.getDefault().syncExec(new Runnable() {
					public void run() {
						int ret = cherryPickDialog.open();
						if (ret == IDialogConstants.OK_ID) {
							cherryPickRevision(fChangeInfo.getId(), fChangeInfo.getUserSelectedRevision().getId(),
									cherryPickDialog.getBranch(), cherryPickDialog.getMessage());
						}
					}

				});
			}
		});

		Button replyButton = new Button(c, SWT.PUSH | SWT.DROP_DOWN | SWT.ARROW_DOWN);
		replyButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		replyButton.setText(ActionConstants.REPLY.getLiteral());
		if (fGerritClient.getRepository().getServerInfo().isAnonymous()) {
			replyButton.setEnabled(false);
		}
		replyButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				final Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
				org.eclipse.swt.widgets.Menu menu = new org.eclipse.swt.widgets.Menu(shell, SWT.POP_UP);

				final MenuItem itemReply = new MenuItem(menu, SWT.PUSH);
				itemReply.setText(ActionConstants.REPLY.getLiteral());
				itemReply.addSelectionListener(new SelectionListener() {

					@Override
					public void widgetSelected(SelectionEvent e) {
						UIUtils.replyToChange(shell, fChangeInfo.getUserSelectedRevision(), null, fGerritClient);
						refreshStatus();
					}

					@Override
					public void widgetDefaultSelected(SelectionEvent e) {
						// ignore
					}
				});

				MenuItem itemCRPlus2 = new MenuItem(menu, SWT.PUSH);
				itemCRPlus2.setText("Code-Review+2");

				//Test if we should allow the +2 button or not
				//Condition:
				//     - User is a committer (maxCRDefined == maxCRPermitted)
				//     - If user permitted, the maxCRPermitted > 0 if not Abandoned
				//     - Selected patch set is the latest
				//     - User cannot submit yet
				int maxCRPermitted = findMaxPermitted(CODE_REVIEW);
				int maxCRDefined = findMaxDefinedLabelValue(CODE_REVIEW);
				boolean allowPlus2 = false;
				if (maxCRDefined == maxCRPermitted && maxCRPermitted > 0) {
					allowPlus2 = true;
				}
				//Verify the patchset if we are allowed only, no need to check if not allowed
				if (allowPlus2) {
					String psSelectedID = fChangeInfo.getUserSelectedRevision().getId();
					if (!(psSelectedID != null
							&& fChangeInfo.getLatestPatchSet().getId().compareTo(psSelectedID) == 0)) {
						allowPlus2 = false;
					}
				}

				itemCRPlus2.setEnabled(!fChangeInfo.getUserSelectedRevision().isSubmitable() && allowPlus2);

				if (itemCRPlus2.isEnabled()) {
					itemCRPlus2.addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent e) {
							super.widgetSelected(e);
							// Code-Review +2
							ReviewInput reviewInput = new ReviewInput();
							reviewInput.setDrafts(ReviewInput.DRAFT_PUBLISH);
							Map<String, String> obj = new HashMap<String, String>();
							obj.put(CODE_REVIEW, "2");

							reviewInput.setLabels(obj);
							postReply(reviewInput);
						}
					});
				}

				Point loc = replyButton.getLocation();
				Rectangle rect = replyButton.getBounds();

				Point mLoc = new Point(loc.x - 1, loc.y + rect.height);

				menu.setLocation(shell.getDisplay().map(replyButton.getParent(), null, mLoc));

				menu.setVisible(true);
			}
		});

		Button draftButton = new Button(c, SWT.PUSH | SWT.DROP_DOWN | SWT.ARROW_DOWN);
		draftButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		draftButton.setText(ActionConstants.DRAFT.getLiteral());
		IObservableValue observeDeleteable = EMFProperties.value(ModelPackage.Literals.CHANGE_INFO__DELETEABLE)
				.observe(fChangeInfo);
		bindingContext.bindValue(WidgetProperties.enabled().observe(draftButton), observeDeleteable,
				new UpdateValueStrategy(UpdateValueStrategy.POLICY_NEVER), null);
		draftButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				org.eclipse.swt.widgets.Menu menu = new org.eclipse.swt.widgets.Menu(draftButton.getShell(),
						SWT.POP_UP);

				final MenuItem itemPublish = new MenuItem(menu, SWT.PUSH);
				itemPublish.setText(ActionConstants.PUBLISH.getLiteral());
				itemPublish.addSelectionListener(new SelectionListener() {

					@Override
					public void widgetSelected(SelectionEvent e) {
						PublishDraftChangeCommand publishCommand = fGerritClient
								.publishDraftChange(fChangeInfo.getId());
						try {
							publishCommand.call();
							refreshStatus();
						} catch (EGerritException e1) {
							EGerritCorePlugin
									.logError(fGerritClient.getRepository().formatGerritVersion() + e1.getMessage());
						}
					}

					@Override
					public void widgetDefaultSelected(SelectionEvent e) {
						// ignore
					}
				});

				MenuItem itemDeleteDraftChange = new MenuItem(menu, SWT.PUSH);
				itemDeleteDraftChange.setText("Delete");

				itemDeleteDraftChange.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						super.widgetSelected(e);
						if (!MessageDialog.openConfirm(Display.getDefault().getActiveShell(), "Delete draft review",
								"Continue ?")) {
							return;
						}
						DeleteDraftChangeCommand deleteDraftChangeCmd = fGerritClient
								.deleteDraftChange(fChangeInfo.getId());
						try {
							deleteDraftChangeCmd.call();
							IWorkbench workbench = PlatformUI.getWorkbench();
							final IWorkbenchPage activePage = workbench.getActiveWorkbenchWindow().getActivePage();

							IEditorPart editor = activePage.getActiveEditor();
							activePage.closeEditor(editor, false);

							ModelLoader loader = ModelLoader.initialize(fGerritClient, fChangeInfo);
							loader.loadBasicInformation();
							loader.dispose();
						} catch (EGerritException e1) {
							EGerritCorePlugin
									.logError(fGerritClient.getRepository().formatGerritVersion() + e1.getMessage());
						}
					}
				});

				Point loc = draftButton.getLocation();
				Rectangle rect = draftButton.getBounds();

				Point mLoc = new Point(loc.x - 1, loc.y + rect.height);

				menu.setLocation(draftButton.getDisplay().map(draftButton.getParent(), null, mLoc));

				menu.setVisible(true);
			}
		});

		c.setSize(c.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		return c;
	}

	private SelectionListener downloadButtonListener(Composite parent) {
		return new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MenuManager mgr = new MenuManager();
				mgr.add(new CherryPickRevision(fChangeInfo.getUserSelectedRevision(), fGerritClient));
				mgr.add(new CheckoutRevision(fChangeInfo.getUserSelectedRevision(), fGerritClient));
				mgr.createContextMenu(parent).setVisible(true);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				//Nothing to do
			}
		};
	}

	private ChangeInfo cherryPickRevision(String changeId, String revisionId, String branch, String message) {
		CherryPickRevisionCommand cherryPickCmd = fGerritClient.cherryPickRevision(changeId, revisionId);
		CherryPickInput cherryPickInput = new CherryPickInput();
		cherryPickInput.setDestination(branch);
		cherryPickInput.setMessage(message);

		cherryPickCmd.setCommandInput(cherryPickInput);
		ChangeInfo listBranchesCmdResult = null;
		try {
			listBranchesCmdResult = cherryPickCmd.call();
		} catch (EGerritException e3) {
			EGerritCorePlugin.logError(fGerritClient.getRepository().formatGerritVersion() + e3.getMessage());
		}
		return listBranchesCmdResult;
	}

	private BranchInfo[] listBranches() {
		ListBranchesCommand listBranchesCmd = fGerritClient.listBranches(fChangeInfo.getProject());

		BranchInfo[] listBranchesCmdResult = null;
		try {
			listBranchesCmdResult = listBranchesCmd.call();
		} catch (EGerritException e3) {
			EGerritCorePlugin.logError(fGerritClient.getRepository().formatGerritVersion() + e3.getMessage());
		}
		return listBranchesCmdResult;
	}

	/**
	 * Post the reply information to the Gerrit server
	 *
	 * @param reviewInput
	 */
	private void postReply(ReviewInput reviewInput) {
		SetReviewCommand reviewToEmit = fGerritClient.setReview(fChangeInfo.getId(),
				fChangeInfo.getUserSelectedRevision().getId());
		reviewToEmit.setCommandInput(reviewInput);

		try {
			reviewToEmit.call();
			refreshStatus();
		} catch (EGerritException e1) {
			EGerritCorePlugin.logError(fGerritClient.getRepository().formatGerritVersion() + e1.getMessage());
		}
	}

	@Override
	public void setFocus() {
		headerSection.setFocus();
	}

	/**
	 * This is the entry point to fill the editor with the Change info and the gerrit server being used.
	 *
	 * @param GerritClient
	 *            gerritClient
	 * @param ChangeInfo
	 *            element
	 */
	private void setChangeInfo(GerritClient gerritClient, ChangeInfo element) {
		fGerritClient = gerritClient;

		if (fChangeInfo == null) {
			fChangeInfo = element;
			//Initial setting for the user selected revision
			fChangeInfo.setUserSelectedRevision(element.getRevisions().get(element.getCurrent_revision()));
		} else {
			//Refresh only potential modified fields, otherwise the data binding seems not to know which fields are modified
			fChangeInfo.set_number(element.get_number());
			fChangeInfo.setChange_id(element.getChange_id());
			fChangeInfo.setStatus(element.getStatus());
			fChangeInfo.setStarred(element.isStarred());
			fChangeInfo.setCreated(element.getCreated());
			fChangeInfo.setUpdated(element.getUpdated());
			fChangeInfo.setReviewed(element.isReviewed());
			fChangeInfo.setMergeable(element.isMergeable());
			fChangeInfo.setInsertions(element.getInsertions());
			fChangeInfo.setDeletions(element.getDeletions());
			fChangeInfo.setCurrent_revision(element.getCurrent_revision());
		}
	}

	private int findMaxDefinedLabelValue(String label) {
		Iterator<Entry<String, LabelInfo>> iterator = fChangeInfo.getLabels().entrySet().iterator();
		//Get the structure having all the possible options
		int maxDefined = 0;
		while (iterator.hasNext()) {
			Entry<String, LabelInfo> definedlabel = iterator.next();
			if (definedlabel.getKey().compareTo(label) == 0) {
				for (String element2 : definedlabel.getValue().getValues().keySet()) {
					maxDefined = Math.max(maxDefined, new Integer(element2.trim()));
				}
			}
		}
		return maxDefined;
	}

	private int findMaxPermitted(String label) {
		int maxPermitted = 0;
		EList<String> listPermitted = null;
		EMap<String, EList<String>> mapLabels = fChangeInfo.getPermitted_labels();
		if (mapLabels != null) {
			Iterator<Entry<String, EList<String>>> iterator = mapLabels.entrySet().iterator();
			//Get the structure having all the possible options
			while (iterator.hasNext()) {
				Entry<String, EList<String>> permittedlabel = iterator.next();
				listPermitted = permittedlabel.getValue();
				if (permittedlabel.getKey().compareTo(label) == 0) {
					for (String element2 : listPermitted) {
						maxPermitted = Math.max(maxPermitted, new Integer(element2.trim()));
					}
				}
			}
		}
		return maxPermitted;
	}

	/**
	 * Refreshes the ChangeEditor
	 */
	public void refreshStatus() {
		loader.reload();
	}

	protected void headerSectionDataBindings() {
		//Show id
		IObservableValue idObservable = EMFProperties.value(ModelPackage.Literals.CHANGE_INFO__NUMBER)
				.observe(fChangeInfo);
		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setGroupingUsed(false);
		bindingContext.bindValue(WidgetProperties.text().observe(shortIdData), idObservable, null,
				new UpdateValueStrategy().setConverter(NumberToStringConverter.fromInteger(numberFormat, true)));

		//Show subject
		IObservableValue subjectObservable = EMFProperties.value(ModelPackage.Literals.CHANGE_INFO__SUBJECT)
				.observe(fChangeInfo);
		bindingContext.bindValue(WidgetProperties.text().observe(subjectData), subjectObservable, null, null);

		fChangeInfo.eAdapters().add(getSubjectListener());

		//Show status
		IObservableValue statusObserveValue = EMFProperties.value(ModelPackage.Literals.CHANGE_INFO__STATUS)
				.observe(fChangeInfo);
		bindingContext.bindValue(WidgetProperties.text().observe(statusData), statusObserveValue, null, null);
	}

	private EContentAdapter getSubjectListener() {
		subjectListener = new EContentAdapter() {
			@Override
			public void notifyChanged(Notification msg) {
				if (msg.getFeature() == null) {
					return;
				}
				if (msg.getFeature().equals(ModelPackage.Literals.CHANGE_INFO__SUBJECT)) {
					Display.getDefault().asyncExec(() -> setPartName(fChangeInfo.getSubject()));
				}
			}
		};
		return subjectListener;
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		// ignore
	}

	@Override
	public void doSaveAs() {
		// ignore
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		if (!(input instanceof ChangeDetailEditorInput)) {
			System.err.println("Input is not a ChangeDetailEditorInput");
		}
		setSite(site);
		setInput(input);
		setChangeInfo(((ChangeDetailEditorInput) input).getClient(), ((ChangeDetailEditorInput) input).getChange());
	}

	@Override
	public boolean isDirty() {
		// ignore
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		// ignore
		return false;
	}

	/*********************************************/
/*                                           */
/*       Buttons Listener                    */
/*                                           */
	/*********************************************/

	/**
	 * Open another editor after a revert action with the newest changeInfo
	 *
	 * @param changeInfo
	 */
	private void openAnotherEditor(ChangeInfo changeInfo) {
		IWorkbench workbench = EGerritUIPlugin.getDefault().getWorkbench();
		IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
		IWorkbenchPage page = null;
		if (window != null) {
			page = workbench.getActiveWorkbenchWindow().getActivePage();
		}

		if (page != null) {
			try {
				IEditorInput input = new ChangeDetailEditorInput(fGerritClient, changeInfo);
				IEditorPart reusedEditor = page.findEditor(input);
				page.openEditor(input, ChangeDetailEditor.EDITOR_ID);
				if (reusedEditor instanceof ChangeDetailEditor) {
					((ChangeDetailEditor) reusedEditor).refreshStatus();
				}
			} catch (PartInitException e) {
				EGerritCorePlugin.logError(fGerritClient != null
						? fGerritClient.getRepository().formatGerritVersion() + e.getMessage()
						: e.getMessage());
			}
		}
	}

	@Override
	public void dispose() {
		loader.dispose();
		fChangeInfo.eAdapters().remove(subjectListener);
		observableCollector.dispose();
		bindingContext.dispose();
		deleteDraftRevision.dispose();
		patchSetSelector.dispose();
		hidableRevertButton.dispose();
		hidableSubmitButton.dispose();
		historytab.dispose();
		messageTab.dispose();
		summaryTab.dispose();
	}
}