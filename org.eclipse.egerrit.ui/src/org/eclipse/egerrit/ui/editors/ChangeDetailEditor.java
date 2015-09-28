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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;

import org.apache.http.client.ClientProtocolException;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.conversion.NumberToStringConverter;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.egerrit.core.EGerritCorePlugin;
import org.eclipse.egerrit.core.GerritClient;
import org.eclipse.egerrit.core.command.AbandonCommand;
import org.eclipse.egerrit.core.command.ChangeOption;
import org.eclipse.egerrit.core.command.CherryPickRevisionCommand;
import org.eclipse.egerrit.core.command.GetChangeCommand;
import org.eclipse.egerrit.core.command.GetRevisionActionsCommand;
import org.eclipse.egerrit.core.command.ListBranchesCommand;
import org.eclipse.egerrit.core.command.RebaseCommand;
import org.eclipse.egerrit.core.command.RestoreCommand;
import org.eclipse.egerrit.core.command.SetReviewCommand;
import org.eclipse.egerrit.core.command.SubmitCommand;
import org.eclipse.egerrit.core.exception.EGerritException;
import org.eclipse.egerrit.core.rest.AbandonInput;
import org.eclipse.egerrit.core.rest.ActionInfo;
import org.eclipse.egerrit.core.rest.BranchInfo;
import org.eclipse.egerrit.core.rest.ChangeInfo;
import org.eclipse.egerrit.core.rest.ChangeMessageInfo;
import org.eclipse.egerrit.core.rest.CherryPickInput;
import org.eclipse.egerrit.core.rest.CommitInfo;
import org.eclipse.egerrit.core.rest.LabelInfo;
import org.eclipse.egerrit.core.rest.RebaseInput;
import org.eclipse.egerrit.core.rest.RestoreInput;
import org.eclipse.egerrit.core.rest.ReviewInput;
import org.eclipse.egerrit.core.rest.RevisionInfo;
import org.eclipse.egerrit.core.rest.SubmitInput;
import org.eclipse.egerrit.ui.editors.model.ChangeDetailEditorInput;
import org.eclipse.egerrit.ui.internal.tabs.FilesTabView;
import org.eclipse.egerrit.ui.internal.tabs.HistoryTabView;
import org.eclipse.egerrit.ui.internal.tabs.MessageTabView;
import org.eclipse.egerrit.ui.internal.tabs.SummaryTabView;
import org.eclipse.egerrit.ui.internal.utils.GerritToGitMapping;
import org.eclipse.egerrit.ui.internal.utils.UIUtils;
import org.eclipse.egit.ui.internal.fetch.FetchGerritChangeWizard;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
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
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;

public class ChangeDetailEditor<ObservableObject> extends EditorPart implements PropertyChangeListener, Observer {
	private static final String REBASE = "rebase";

	private static final String RESTORE = "restore";

	private static final String ABANDON = "abandon";

	private static final String VERIFIED = "Verified";

	private static final String CODE_REVIEW = "Code-Review";

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String EDITOR_ID = "org.eclipse.egerrit.ui.editors.ChangeDetailEditor"; //$NON-NLS-1$

	private final String TITLE = "Gerrit Server ";

	private SummaryTabView summaryTab = null;

	private HistoryTabView historytab = null;

	private MessageTabView messageTab = null;

	private FilesTabView filesTab = null;

	private final ChangeInfo fChangeInfo = new ChangeInfo();

	private final CommitInfo fCommitInfo = new CommitInfo();

	private GerritClient fGerritClient = null;

	private Text subjectData;

	private Label statusData;

	private Text shortIdData;

//	private StyledText msgAuthorData;

	private TabFolder tabFolder;

	private ScrolledComposite scrollView;

	private Composite compButton;

	private Button fSubmit;

	private Button fAbandon;

	private Button fRestore;

	private Button fRebase;

	private Button fCherryPick;

	private Button fReply;

	private String anonymousUserToolTip;

	// ------------------------------------------------------------------------
	// Constructor and life cycle
	// ------------------------------------------------------------------------

	/**
	 * The constructor.
	 */
	public ChangeDetailEditor() {
		super();
	}

	private void createAdditionalToolbarActions() {
		Action processDownload = new Action("Download") {
			@Override
			public void run() {
				System.out.println("Download pressed !!!!!!!!!!");
			}

		};
		Action processDownloadProtocol = new Action("Download protocol") {
			@Override
			public void run() {
				System.out.println("Download protocol pressed !!!!!!!!!!");
			}

		};
//		this.getViewSite().getActionBars().getToolBarManager().add(processDownload);

//		IMenuManager menuManager = this.getViewSite().getActionBars().getMenuManager();
//		menuManager.add(processDownload);
//		menuManager.add(processDownloadProtocol);
	}

	@Override
	public void createPartControl(final Composite parent) {
		anonymousUserToolTip = "This button is disabled because you are connected anonymously to "
				+ fGerritClient.getRepository().getServerInfo().getServerURI() + ".";
		createAdditionalToolbarActions();
		GridLayout gl_parent = new GridLayout(1, false);
		parent.setLayout(gl_parent);

		parent.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1));
		scrollView = new ScrolledComposite(parent, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrollView.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		scrollView.setExpandHorizontal(true);
		scrollView.setExpandVertical(true);

		Composite composite = new Composite(scrollView, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		composite.setLayout(new GridLayout(1, false));

		scrollView.setContent(composite);

		Point fontSize = UIUtils.computeFontSize(composite);

		Composite hd = headerSection(composite, fontSize);

		tabFolder = new TabFolder(composite, SWT.NONE);
		tabFolder.setLayout(new GridLayout(1, false));
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		//Create the button section before filling the tabfolder
		compButton = buttonSection(composite);

		summaryTab = new SummaryTabView();
		summaryTab.create(tabFolder, fChangeInfo);

		Point hScrolBarSize = scrollView.getHorizontalBar().getSize();

		messageTab = new MessageTabView();
		messageTab.create(fGerritClient, tabFolder, fCommitInfo, fChangeInfo);

		filesTab = new FilesTabView();
		filesTab.create(fGerritClient, tabFolder, fChangeInfo);
		filesTab.addObserver(this);

		historytab = new HistoryTabView();
		historytab.create(tabFolder, fChangeInfo.getMessages());
		tabFolder.pack();

		//Check for the minimum width
		Point ptHeader = hd.getSize();
		Point ptButton = compButton.getSize();

		int minimumWidth = Math.max(ptHeader.x, ptButton.x);
		minimumWidth = Math.max(minimumWidth, tabFolder.getSize().x);
		int minimumHeight;
		if (historytab != null) {
			minimumHeight = ptHeader.y + 2 * historytab.getTableHistoryViewer().getTable().getSize().y + ptButton.y
					+ hScrolBarSize.y;
		} else {
			minimumHeight = ptHeader.y + 2 * 5 + ptButton.y + hScrolBarSize.y;
		}
		scrollView.setMinSize(minimumWidth, minimumHeight);
		setPartName(((ChangeDetailEditorInput) getEditorInput()).getName());

		//This query fill the current revision
		setCurrentRevisionAndMessageTab(fGerritClient, fChangeInfo.getChange_id());

		//Queries to fill the Summary Review tab data
		if (summaryTab != null) {
			summaryTab.setTabs(fGerritClient, fChangeInfo);
		}

		buttonsEnablement();
		if (messageTab != null) {
			messageTab.setfMessageBuffer(fCommitInfo.getMessage());
		}

	}

	private Composite headerSection(final Composite parent, Point fontSize) {
		Group group_header = new Group(parent, SWT.NONE);
		GridData gd_group_header = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
		gd_group_header.minimumWidth = 33;
		group_header.setLayoutData(gd_group_header);
		group_header.setLayout(new GridLayout(5, false));

		Label lblId = new Label(group_header, SWT.NONE);
		lblId.setText("ID:");

		shortIdData = new Text(group_header, SWT.NONE);
		GridData gd_lblShortId = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		int maxShortChangeId = 10; //Max number of chars
		int idWidth = fontSize.x * maxShortChangeId;
		gd_lblShortId.minimumWidth = idWidth;
		gd_lblShortId.widthHint = idWidth;
		shortIdData.setLayoutData(gd_lblShortId);
		shortIdData.setEditable(false);
		shortIdData.setBackground(parent.getBackground());

		Label lblSubject = new Label(group_header, SWT.NONE);
		GridData gd_Subject = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_Subject.horizontalIndent = 10;
		lblSubject.setLayoutData(gd_Subject);
		lblSubject.setText("Subject:");

		subjectData = new Text(group_header, SWT.NONE);
		GridData gd_lblSubjectData = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblSubjectData.horizontalIndent = 10;
		int maxSubjectLen = 90; //Max number of chars
		int changeWidth = fontSize.x * maxSubjectLen;
		gd_lblSubjectData.minimumWidth = changeWidth;
		gd_lblSubjectData.widthHint = changeWidth;
		subjectData.setLayoutData(gd_lblSubjectData);
		subjectData.setText("subject");
		subjectData.setEditable(false);
		subjectData.setBackground(parent.getBackground());

		statusData = new Label(group_header, SWT.RIGHT);
		GridData gd_lblStatus = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
		gd_lblStatus.horizontalIndent = 10;
		int maxStatus = 20; //Max number of chars
		int statusWidth = fontSize.x * maxStatus;
		gd_lblStatus.minimumWidth = statusWidth;
		gd_lblStatus.widthHint = statusWidth;
		statusData.setLayoutData(gd_lblStatus);
		statusData.setText("Status");
		group_header.pack();

		//Set the binding for this section
		headerSectionDataBindings();
		return group_header;
	}

	private Composite buttonSection(final Composite parent) {
		final Composite c = new Composite(parent, SWT.NONE);
		c.setLayout(new GridLayout(8, true));
		c.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 8, 1));

		fSubmit = new Button(c, SWT.PUSH);
		fSubmit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		fSubmit.setText("Submit");
		fSubmit.setEnabled(false);
		if (fGerritClient.getRepository().getServerInfo().isAnonymous()) {
			fSubmit.setToolTipText(anonymousUserToolTip);
			fSubmit.addListener(SWT.MouseHover, new Listener() {

				@Override
				public void handleEvent(Event event) {
					// ignore

				}
			});
		} else {
			fSubmit.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					super.widgetSelected(e);

					SubmitCommand submitCmd = fGerritClient.submit(fChangeInfo.getChange_id());
					SubmitInput submitInput = new SubmitInput();
					submitInput.setWait_for_merge(false);

					submitCmd.setSubmitInput(submitInput);

					try {
						submitCmd.call();
					} catch (EGerritException e3) {
						EGerritCorePlugin.logError(e3.getMessage());
					} catch (ClientProtocolException e3) {
						UIUtils.displayInformation(null, TITLE,
								e3.getLocalizedMessage() + "\n " + submitCmd.formatRequest()); //$NON-NLS-1$
					}
					refreshStatus();
				}
			});
		}
		fAbandon = new Button(c, SWT.PUSH);
		fAbandon.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		fAbandon.setText("Abandon");
		fAbandon.setEnabled(false);
		if (fGerritClient.getRepository().getServerInfo().isAnonymous()) {
			fAbandon.setToolTipText(anonymousUserToolTip);
		} else {
			fAbandon.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					super.widgetSelected(e);

					InputDialog inputDialog = new InputDialog(fAbandon.getParent().getShell(), "Abandon message", "",
							"", null);
					if (inputDialog.open() != Window.OK) {
						return;
					}

					AbandonCommand abandonCmd = fGerritClient.abandon(fChangeInfo.getChange_id());
					AbandonInput abandonInput = new AbandonInput();
					abandonInput.setMessage(inputDialog.getValue());

					abandonCmd.setAbandonInput(abandonInput);

					ChangeInfo abandonCmdResult = null;
					try {
						abandonCmdResult = abandonCmd.call();
					} catch (EGerritException e3) {
						EGerritCorePlugin.logError(e3.getMessage());
					} catch (ClientProtocolException e3) {
						UIUtils.displayInformation(null, TITLE,
								e3.getLocalizedMessage() + "\n " + abandonCmd.formatRequest()); //$NON-NLS-1$
					}
					refreshStatus();
				}
			});
		}
		fRestore = new Button(c, SWT.PUSH);
		fRestore.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		fRestore.setText("Restore");
		fRestore.setEnabled(false);
		if (fGerritClient.getRepository().getServerInfo().isAnonymous()) {
			fRestore.setToolTipText(anonymousUserToolTip);
		} else {
			fRestore.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					super.widgetSelected(e);

					InputDialog inputDialog = new InputDialog(fAbandon.getParent().getShell(), "Restore message", "",
							"", null);
					if (inputDialog.open() != Window.OK) {
						return;
					}

					RestoreCommand restoreCmd = fGerritClient.restore(fChangeInfo.getChange_id());
					RestoreInput restoreInput = new RestoreInput();
					restoreInput.setMessage(inputDialog.getValue());

					restoreCmd.setRestoreInput(restoreInput);

					ChangeInfo restoreCmdResult = null;
					try {
						restoreCmdResult = restoreCmd.call();
					} catch (EGerritException e3) {
						EGerritCorePlugin.logError(e3.getMessage());
					} catch (ClientProtocolException e3) {
						UIUtils.displayInformation(null, TITLE,
								e3.getLocalizedMessage() + "\n " + restoreCmd.formatRequest()); //$NON-NLS-1$
					}
					refreshStatus();
				}
			});
		}
		fRebase = new Button(c, SWT.PUSH);
		fRebase.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		fRebase.setText("Rebase");
		if (fGerritClient.getRepository().getServerInfo().isAnonymous()) {
			fRebase.setToolTipText(anonymousUserToolTip);
			fRebase.setEnabled(false);
		} else {
			fRebase.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					super.widgetSelected(e);

					InputDialog inputDialog = new InputDialog(fRebase.getParent().getShell(),
							"Code Review - Rebase Change", "Change parent revision", "", null);
					if (inputDialog.open() != Window.OK) {
						return;
					}

					RebaseCommand rebaseCmd = fGerritClient.rebase(fChangeInfo.getChange_id());
					RebaseInput rebaseInput = new RebaseInput();
					rebaseInput.setBase(inputDialog.getValue());

					rebaseCmd.setRebaseInput(rebaseInput);

					ChangeInfo rebaseCmdResult = null;
					try {
						rebaseCmdResult = rebaseCmd.call();
					} catch (EGerritException e3) {
						EGerritCorePlugin.logError(e3.getMessage());
					} catch (ClientProtocolException e3) {
						UIUtils.displayInformation(null, TITLE,
								e3.getLocalizedMessage() + "\n " + rebaseCmd.formatRequest()); //$NON-NLS-1$
					}
					refreshStatus();
				}
			});
		}

		Button checkout = new Button(c, SWT.PUSH);
		checkout.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		checkout.setText("Checkout");
		checkout.addSelectionListener(checkoutButtonListener(parent));

		fCherryPick = new Button(c, SWT.PUSH);
		fCherryPick.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		fCherryPick.setText("Cherry-Pick");
		if (fGerritClient.getRepository().getServerInfo().isAnonymous()) {
			fCherryPick.setToolTipText(anonymousUserToolTip);
			fCherryPick.setEnabled(false);
		} else {
			fCherryPick.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					super.widgetSelected(e);

					BranchInfo[] listBranchesCmdResult = listBranches();

					List<String> listBranchesRef = new ArrayList();
					Iterator<BranchInfo> it = Arrays.asList(listBranchesCmdResult).iterator();
					while (it.hasNext()) {
						listBranchesRef.add(it.next().getRef());
					}

					final CherryPickDialog cherryPickDialog = new CherryPickDialog(fCherryPick.getParent().getShell(),
							listBranchesRef, fCommitInfo.getMessage());
					Display.getDefault().syncExec(new Runnable() {
						public void run() {
							int ret = cherryPickDialog.open();
							if (ret == IDialogConstants.OK_ID) {
								cherryPickRevision(fChangeInfo.getChange_id(), fChangeInfo.getCurrentRevision(),
										cherryPickDialog.getBranch(), cherryPickDialog.getMessage());
							}
						}

					});
				}
			});
		}

		fReply = new Button(c, SWT.PUSH | SWT.DROP_DOWN | SWT.ARROW_DOWN);
		fReply.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		fReply.setText("Reply...");
		if (fGerritClient.getRepository().getServerInfo().isAnonymous()) {
			fReply.setToolTipText(anonymousUserToolTip);
			fReply.setEnabled(false);
		} else {
			fReply.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					super.widgetSelected(e);
					final Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
					org.eclipse.swt.widgets.Menu menu = new org.eclipse.swt.widgets.Menu(shell, SWT.POP_UP);

					final MenuItem itemReply = new MenuItem(menu, SWT.PUSH);
					itemReply.setText("Reply...");
					itemReply.addSelectionListener(new SelectionListener() {

						@Override
						public void widgetSelected(SelectionEvent e) {
							// ignore
							final ReplyDialog replyDialog = new ReplyDialog(itemReply.getParent().getShell(),
									fChangeInfo.getPermittedLabels(), fChangeInfo.getLabels());
							Display.getDefault().syncExec(new Runnable() {
								public void run() {
									int ret = replyDialog.open();
									if (ret == IDialogConstants.OK_ID) {
										//Fill the data structure for the reply
										ReviewInput reviewInput = new ReviewInput();
										reviewInput.setMessage(replyDialog.getMessage());
										reviewInput.setLabels(replyDialog.getRadiosSelection());
										reviewInput.setDrafts(ReviewInput.DRAFT_PUBLISH);
										// JB which field e-mail	reviewInput.setNotify(replyDialog.getEmail());
										//Send the data
										postReply(reviewInput);
									}

								}
							});
						}

						@Override
						public void widgetDefaultSelected(SelectionEvent e) {
							// ignore
						}
					});

					MenuItem itemCRPlus2 = new MenuItem(menu, SWT.PUSH);
					itemCRPlus2.setText("Code-Review+2");
					String latestPatchSet = filesTab != null ? filesTab.getLatestPatchSet() : "";
					itemCRPlus2
							.setEnabled(fChangeInfo.getCodeReviewedTally() != 2 && fChangeInfo.getVerifiedTally() >= 1
									&& latestPatchSet.compareTo(fChangeInfo.getCurrentRevision()) == 0);
					if (itemCRPlus2.isEnabled()) {
						itemCRPlus2.addSelectionListener(new SelectionAdapter() {
							@Override
							public void widgetSelected(SelectionEvent e) {
								super.widgetSelected(e);
								// Code-Review +2
								ReviewInput reviewInput = new ReviewInput();
								reviewInput.setDrafts(ReviewInput.DRAFT_PUBLISH);
								Map obj = new HashMap();
								obj.put(CODE_REVIEW, "2");

								reviewInput.setLabels(obj);
								postReply(reviewInput);
							}
						});
					}

					Point loc = fReply.getLocation();
					Rectangle rect = fReply.getBounds();

					Point mLoc = new Point(loc.x - 1, loc.y + rect.height);

					menu.setLocation(shell.getDisplay().map(fReply.getParent(), null, mLoc));

					menu.setVisible(true);
				}
			});
		}
		c.setSize(c.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		return c;
	}

	private ChangeInfo cherryPickRevision(String changeId, String revisionId, String branch, String message) {
		CherryPickRevisionCommand cherryPickCmd = fGerritClient.cherryPickRevision(changeId, revisionId);
		CherryPickInput cherryPickInput = new CherryPickInput();
		cherryPickInput.setDestination(branch);
		cherryPickInput.setMessage(message);

		cherryPickCmd.setCherryPickInput(cherryPickInput);
		ChangeInfo listBranchesCmdResult = null;
		try {
			listBranchesCmdResult = cherryPickCmd.call();
		} catch (EGerritException e3) {
			EGerritCorePlugin.logError(e3.getMessage());
		} catch (ClientProtocolException e3) {
			UIUtils.displayInformation(null, TITLE, e3.getLocalizedMessage() + "\n " + cherryPickCmd.formatRequest()); //$NON-NLS-1$
		}
		return listBranchesCmdResult;
	}

	private BranchInfo[] listBranches() {
		ListBranchesCommand listBranchesCmd = fGerritClient.listBranches(fChangeInfo.getProject());

		BranchInfo[] listBranchesCmdResult = null;
		try {
			listBranchesCmdResult = listBranchesCmd.call();
		} catch (EGerritException e3) {
			EGerritCorePlugin.logError(e3.getMessage());
		} catch (ClientProtocolException e3) {
			UIUtils.displayInformation(null, TITLE, e3.getLocalizedMessage() + "\n " + listBranchesCmd.formatRequest()); //$NON-NLS-1$
		}
		return listBranchesCmdResult;
	}

	/**
	 * Post the reply information to the Gerrit server
	 *
	 * @param reviewInput
	 */
	private void postReply(ReviewInput reviewInput) {
		SetReviewCommand reviewToEmit = fGerritClient.setReview(fChangeInfo.getChange_id(),
				fChangeInfo.getCurrentRevision());
		reviewToEmit.setReviewInput(reviewInput);

		try {
			reviewToEmit.call();
			refreshStatus();
		} catch (EGerritException e1) {
			EGerritCorePlugin.logError(e1.getMessage());
		} catch (ClientProtocolException e1) {
			UIUtils.displayInformation(null, TITLE, e1.getLocalizedMessage() + "\n " + reviewToEmit.formatRequest()); //$NON-NLS-1$
		}
	}

	@Override
	public void setFocus() {
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
		fChangeInfo.reset();

		//Fill the data structure
		fChangeInfo.setNumber(element.get_number());
		fChangeInfo.setId(element.getId());
		fChangeInfo.setChange_id(element.getChange_id());
		fChangeInfo.setStatus(element.getStatus());
		fChangeInfo.setSubject(element.getSubject());
		fChangeInfo.setProject(element.getProject());
		fChangeInfo.setBranch(element.getBranch());
		fChangeInfo.setUpdated(element.getUpdated());
		fChangeInfo.setTopic(element.getTopic());
		fChangeInfo.setLabels(element.getLabels());
		fChangeInfo.setOwner(element.getOwner());
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
		String[] listPermitted = null;
		Map<String, String[]> mapLabels = fChangeInfo.getPermittedLabels();
		if (mapLabels != null) {
			Iterator<Map.Entry<String, String[]>> iterator = mapLabels.entrySet().iterator();
			//Get the structure having all the possible options
			while (iterator.hasNext()) {
				Entry<String, String[]> permittedlabel = iterator.next();
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

	private void refreshStatus() {
		ChangeInfo changeInfo = refreshChangeInfo(fGerritClient, fChangeInfo.getChange_id(), new NullProgressMonitor());

		//Reset the whole window
		setChangeInfo(fGerritClient, changeInfo);

		//This query fills the current revision
		setCurrentRevisionAndMessageTab(fGerritClient, fChangeInfo.getChange_id());

		//Queries to fill the Summary Review tab data
		summaryTab.setTabs(fGerritClient, fChangeInfo);

		buttonsEnablement();

	}

	/**
	 * The logic that controls when bottom buttons are enabled or not
	 */
	private void buttonsEnablement() {
		submitButtonEnablement();
		abandonrestoreButtonEnablement();
		rebaseButtonEnablement();
		if (messageTab != null) {
			messageTab.editingAllowed(saveButtonEnablement());
		}
	}

	private void rebaseButtonEnablement() {
		fRebase.setEnabled(false);

		Map<String, ActionInfo> actions = getRevisionActions();
		if (actions != null) {
			ActionInfo rebase = actions.get(REBASE);

			if (rebase != null) {
				fRebase.setEnabled(true);
			}
		}

	}

	private Map<String, ActionInfo> getRevisionActions() {
		GetRevisionActionsCommand getRevisionActionsCmd = fGerritClient.getRevisionActions(fChangeInfo.getId(),
				fChangeInfo.getCurrentRevision());

		Map<String, ActionInfo> getRevisionActionsCmdResult = null;
		try {
			getRevisionActionsCmdResult = getRevisionActionsCmd.call();
		} catch (EGerritException e3) {
			EGerritCorePlugin.logError(e3.getMessage());
		} catch (ClientProtocolException e3) {
			UIUtils.displayInformation(null, TITLE,
					e3.getLocalizedMessage() + "\n " + getRevisionActionsCmd.formatRequest()); //$NON-NLS-1$
		}
		return getRevisionActionsCmdResult;
	}

	/**
	 * This method set the state of the Submit button. By default, we set the submit button to true. The following code
	 * can set the "Submit" button to false. when we meet the conditions that follows. So when the state is set to
	 * false, no need to continue, we can just return at that point.
	 */
	private void submitButtonEnablement() {
		fSubmit.setEnabled(true);

		int maxCRPermitted = findMaxPermitted(CODE_REVIEW);
		int maxVPermitted = findMaxPermitted(VERIFIED);
		if (findMaxDefinedLabelValue(CODE_REVIEW) != maxCRPermitted) {
			fSubmit.setEnabled(false);
			return;
		}

		LabelInfo lblInfoVerify = fChangeInfo.getLabels().get(VERIFIED);
		if (lblInfoVerify != null) {
			if (lblInfoVerify.isBlocking()) {
				fSubmit.setEnabled(false);
				return;
			} else {
				if (fChangeInfo.getVerifiedTally() <= 0) {
					fSubmit.setEnabled(false);
					return;
				}
			}
		}

		LabelInfo lblInfoCR = fChangeInfo.getLabels().get(CODE_REVIEW);
		if (lblInfoCR != null) {
			if (lblInfoCR.isBlocking()) {
				fSubmit.setEnabled(false);
				return;
			} else {
				if (fChangeInfo.getCodeReviewedTally() <= 0) {
					fSubmit.setEnabled(false);
					return;
				}
			}
		} else {
			fSubmit.setEnabled(false);
			return;

		}

	}

	private void abandonrestoreButtonEnablement() {

		Map<String, ActionInfo> actions = fChangeInfo.getActions();
		if (actions != null) {
			ActionInfo abandon = actions.get(ABANDON);
			ActionInfo restore = actions.get(RESTORE);

			if (abandon != null && abandon.isEnabled()) {
				fAbandon.setEnabled(true);
				fRestore.setEnabled(false);
			} else if (restore != null && restore.isEnabled()) {
				fAbandon.setEnabled(false);
				fRestore.setEnabled(true);
			} else {
				fAbandon.setEnabled(false);
				fRestore.setEnabled(false);
			}

		}
	}

	private boolean saveButtonEnablement() {

		if (fGerritClient.getRepository()
				.getCredentials()
				.getUsername()
				.compareTo(fChangeInfo.getOwner().getUsername()) == 0
				|| fGerritClient.getRepository()
						.getCredentials()
						.getUsername()
						.compareTo(fChangeInfo.getOwner().getEmail()) == 0) {
			return true;
		}

		int maxCRPermitted = findMaxPermitted(CODE_REVIEW);
		if (findMaxDefinedLabelValue(CODE_REVIEW) != maxCRPermitted) {
			return false;
		}

		return true;
	}

	/************************************************************* */
/*                                                             */
/* Section to SET the data structure                           */
/*                                                             */
	/************************************************************* */

	/**
	 * Fill the data related to the current revision, labels and messages. Also, the data for the Message tab is
	 * available with this request
	 *
	 * @param GerritClient
	 *            gerritClient
	 * @param String
	 *            change_id
	 */
	private void setCurrentRevisionAndMessageTab(GerritClient gerritClient, String change_id) {
		ChangeInfo res = queryMessageTab(gerritClient, change_id, new NullProgressMonitor());

		if (res != null) {
			fChangeInfo.setCurrent_revision(res.getCurrentRevision());

			fChangeInfo.setLabels(res.getLabels());
			fChangeInfo.setMessages(res.getMessages());
			fChangeInfo.setPermittedLabels(res.getPermittedLabels());
			fChangeInfo.setStatus(res.getStatus());
			fChangeInfo.setActions(res.getActions());

			//Set the file tab view
			if (filesTab != null) {
				filesTab.setTabs(res.getRevisions(), res.getCurrentRevision());
			}
			setCurrentCommitInfo(res.getCurrentRevision());

			//Display the History tab
			Collections.sort(fChangeInfo.getMessages(), new Comparator<ChangeMessageInfo>() {

				@Override
				public int compare(ChangeMessageInfo rev1, ChangeMessageInfo rev2) {
					return rev2.getDate().compareTo(rev1.getDate());
				}
			});

			WritableList writeInfoList = new WritableList(fChangeInfo.getMessages(), ChangeMessageInfo.class);
			if (historytab != null) {
				historytab.getTableHistoryViewer().setInput(writeInfoList);
				historytab.getTableHistoryViewer().refresh();
			}
		}

	}

	/**
	 * Set the data structure according to the selected revision. It could be the current revision or any path set
	 * revision. It will trigger the update for the message tab structure
	 *
	 * @param Object
	 *            revision structure or a revision string
	 */
	private void setCurrentCommitInfo(Object revision) {
		RevisionInfo match = null;
		if (revision instanceof RevisionInfo) {
			match = (RevisionInfo) revision;
		} else if (revision instanceof String) {
			match = filesTab != null ? filesTab.getRevisions().get(revision) : null;
		}
		//Need to initialize the variables first
		fCommitInfo.reset();

		if (match != null) {
			fCommitInfo.setCommit(match.getId());
			fCommitInfo.setMessage(match.getCommit().getMessage());
			fCommitInfo.setParents(match.getCommit().getParents());
			fCommitInfo.setAuthor(match.getCommit().getAuthor());
			fCommitInfo.setCommitter(match.getCommit().getCommitter());
		}
	}

	/***************************************************************/
/*                                                             */
/* Section to QUERY the data structure                         */
/*                                                             */
	/************************************************************* */

	private ChangeInfo queryMessageTab(GerritClient gerrit, String change_id, IProgressMonitor monitor) {
		try {
			monitor.beginTask("Executing query", IProgressMonitor.UNKNOWN);
			// Create query

			GetChangeCommand command = gerrit.getChange(fChangeInfo.getId());
			command.addOption(ChangeOption.DETAILED_LABELS);
			command.addOption(ChangeOption.ALL_FILES);
			command.addOption(ChangeOption.ALL_REVISIONS);
			command.addOption(ChangeOption.ALL_COMMITS);
			command.addOption(ChangeOption.DRAFT_COMMENTS);
			command.addOption(ChangeOption.REVIEWED);
			command.addOption(ChangeOption.MESSAGES);
			command.addOption(ChangeOption.DOWNLOAD_COMMANDS);
			command.addOption(ChangeOption.CURRENT_ACTIONS);

			ChangeInfo res = null;
			try {
				res = command.call();
			} catch (EGerritException e) {
				EGerritCorePlugin.logError(e.getMessage());
			} catch (ClientProtocolException e) {
				UIUtils.displayInformation(null, TITLE, e.getLocalizedMessage() + "\n " + command.formatRequest()); //$NON-NLS-1$
			}
			return res;
		} catch (UnsupportedClassVersionError e) {
			return null;
		} finally {
			monitor.done();
		}
	}

	/**
	 * @param gerritClient
	 * @param change_id
	 * @param monitor
	 * @return
	 */
	private ChangeInfo refreshChangeInfo(GerritClient gerrit, String change_id, IProgressMonitor monitor) {
		try {

			monitor.beginTask("Executing query", IProgressMonitor.UNKNOWN);

			GetChangeCommand command = null;
			command = gerrit.getChange(fChangeInfo.getId());

			command.addOption(ChangeOption.DETAILED_LABELS);
			command.addOption(ChangeOption.CURRENT_ACTIONS);

			ChangeInfo res = null;
			try {
				res = command.call();
			} catch (EGerritException e) {
				EGerritCorePlugin.logError(e.getMessage());
			} catch (ClientProtocolException e) {
				UIUtils.displayInformation(null, TITLE, e.getLocalizedMessage() + "\n " + command.formatRequest()); //$NON-NLS-1$
			}
			return res;
		} catch (UnsupportedClassVersionError e) {
			return null;
		} finally {
			monitor.done();
		}
	}

	/************************************************************* */
/*                                                             */
/* Section adjust the DATA binding                             */
/*                                                             */
	/************************************************************* */

	protected DataBindingContext headerSectionDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();

		//
		IObservableValue observeTextLblLblidObserveWidget = WidgetProperties.text().observe(shortIdData);
		IObservableValue idFChangeInfoObserveValue = BeanProperties.value("_number").observe(fChangeInfo);
		bindingContext.bindValue(observeTextLblLblidObserveWidget, idFChangeInfoObserveValue, null,
				new UpdateValueStrategy().setConverter(NumberToStringConverter.fromInteger(true)));
		//
		IObservableValue observeTextLblChangeid_1ObserveWidget = WidgetProperties.text().observe(subjectData);
		IObservableValue changeIdFChangeInfoObserveValue = BeanProperties.value("subject").observe(fChangeInfo);
		bindingContext.bindValue(observeTextLblChangeid_1ObserveWidget, changeIdFChangeInfoObserveValue, null, null);
		//
		IObservableValue observeTextLblStatusObserveWidget = WidgetProperties.text().observe(statusData);
		IObservableValue statusFChangeInfoObserveValue = BeanProperties.value("status").observe(fChangeInfo);
		bindingContext.bindValue(observeTextLblStatusObserveWidget, statusFChangeInfoObserveValue, null, null);

		return bindingContext;
	}

	private void addFillLabel(Group group, int count) {
		//Add label for positioning
		for (int i = 0; i < count; i++) {
//			Label lab1 = new Label(group, SWT.NONE);
			Label lab1 = new Label(group, SWT.BORDER);
			GridData grid = new GridData();
			grid.horizontalAlignment = SWT.LEFT;
			grid.horizontalSpan = 1;
			grid.verticalAlignment = SWT.LEFT;
			grid.grabExcessHorizontalSpace = true;
			grid.grabExcessVerticalSpace = false;
			lab1.setLayoutData(grid);
			lab1.setText("Label " + (i + 1));
		}

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
	 * @param Compopsite
	 *            parent
	 * @return the listener when the Checkout button is triggered
	 */
	private SelectionAdapter checkoutButtonListener(final Composite parent) {
		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {

				Repository localRepo = findLocalRepo(fGerritClient, summaryTab.getProject());

				if (localRepo != null) {
					//Find the current selected Patch set reference in the table
					String psSelected = filesTab.getSelectedPatchSet();

					if ((psSelected != null) && !psSelected.isEmpty()) {
						FetchGerritChangeWizard fetchOp = new FetchGerritChangeWizard(localRepo, psSelected);
						fetchOp.addPages();
						WizardDialog wizardDialog = new WizardDialog(parent.getShell(), fetchOp);
						wizardDialog.create();
						wizardDialog.open();
					} else {
						Status status = new Status(IStatus.ERROR, EGerritCorePlugin.PLUGIN_ID, "No patchset selected");
						ErrorDialog.openError(parent.getShell(), "Error", "Operation could not be performed", status);
					}
				} else {
					Status status = new Status(IStatus.ERROR, EGerritCorePlugin.PLUGIN_ID, "No repository found");
					ErrorDialog.openError(parent.getShell(), "Error", "Operation could not be performed", status);
				}
			}
		};
	}

	/**
	 * @return a Selection listener to any button not ready yet until implementation
	 */
	private SelectionAdapter notAvailableListener() {
		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				if (event.getSource() instanceof Button) {
					Button btn = (Button) event.getSource();
					UIUtils.notInplementedDialog(btn.getText());
				} else {
					MenuItem mnuItem = (MenuItem) event.getSource();
					UIUtils.notInplementedDialog(mnuItem.getText());
				}
			}

		};
	}

	/*********************************************/
/*                                           */
/*       Utility                             */
/*                                           */
	/*********************************************/

	/**
	 * @param GerritClient
	 *            gerritClient
	 * @param String
	 *            projectName
	 * @return Repository The local repository associated to the current project and Gerrit Repository
	 */
	private Repository findLocalRepo(GerritClient gerrit, String projectName) {
		GerritToGitMapping gerritToGitMap = null;
		try {
			gerritToGitMap = new GerritToGitMapping(new URIish(gerrit.getRepository().getURIBuilder(false).toString()),
					projectName);
		} catch (URISyntaxException e2) {
			EGerritCorePlugin.logError(e2.getMessage());
		}
		Repository jgitRepo = null;
		try {
			jgitRepo = gerritToGitMap.find();
		} catch (IOException e2) {
			EGerritCorePlugin.logError(e2.getMessage());
		}
		return jgitRepo;
	}

	/**
	 * @param Observable
	 * @param Object
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg0 instanceof FilesTabView) {
			FilesTabView fileTabView = (FilesTabView) arg0;

			//Adjust the commit info for the Message Tab
			setCurrentCommitInfo(fileTabView.getCurrentRevision());
		}
	}

	/**
	 * @param PropertyChangeEvent
	 *            evt
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// ignore
	}
}