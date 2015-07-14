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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
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
import org.eclipse.egerrit.core.Gerrit;
import org.eclipse.egerrit.core.GerritRepository;
import org.eclipse.egerrit.core.command.ChangeOption;
import org.eclipse.egerrit.core.command.GetChangeCommand;
import org.eclipse.egerrit.core.command.GetContentCommand;
import org.eclipse.egerrit.core.command.SetReviewCommand;
import org.eclipse.egerrit.core.exception.EGerritException;
import org.eclipse.egerrit.core.rest.ChangeInfo;
import org.eclipse.egerrit.core.rest.ChangeMessageInfo;
import org.eclipse.egerrit.core.rest.CommitInfo;
import org.eclipse.egerrit.core.rest.ReviewInfo;
import org.eclipse.egerrit.core.rest.ReviewInput;
import org.eclipse.egerrit.core.rest.RevisionInfo;
import org.eclipse.egerrit.ui.EGerritUIPlugin;
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
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;

public class ChangeDetailEditor<ObservableObject> extends EditorPart implements PropertyChangeListener, Observer {
	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String EDITOR_ID = "org.eclipse.egerrit.ui.editors.ChangeDetailEditor"; //$NON-NLS-1$

	private final String TITLE = "Gerrit Server ";

	private static ChangeDetailEditor chDetailEditor = null;

	private SummaryTabView summaryTab = null;

	private HistoryTabView historytab = null;

	private MessageTabView messageTab = null;

	private FilesTabView filesTab = null;

	private final ChangeInfo fChangeInfo = new ChangeInfo();

	private final CommitInfo fCommitInfo = new CommitInfo();

	private Label changeidData;

	private Label statusData;

	private Label shortIdData;

//	private StyledText msgAuthorData;

	private TabFolder tabFolder;

	private ScrolledComposite scrollView;

	private Composite compButton;

	private Button fReply;

	// ------------------------------------------------------------------------
	// Constructor and life cycle
	// ------------------------------------------------------------------------

	/**
	 * The constructor.
	 */
	public ChangeDetailEditor() {
		super();
		chDetailEditor = this;
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
//		RED = parent.getDisplay().getSystemColor(SWT.COLOR_RED);

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
		messageTab.create(tabFolder, fCommitInfo, fChangeInfo);

		filesTab = new FilesTabView();
		filesTab.create(tabFolder, fChangeInfo);
		filesTab.addObserver(chDetailEditor);

		historytab = new HistoryTabView();
		historytab.create(tabFolder, fChangeInfo.getMessages());
		tabFolder.pack();

		//Check for the minimum width
		Point ptHeader = hd.getSize();
		Point ptButton = compButton.getSize();

		int minimumWidth = Math.max(ptHeader.x, ptButton.x);
		minimumWidth = Math.max(minimumWidth, tabFolder.getSize().x);
		int minimumHeight = ptHeader.y + 2 * historytab.getTableHistoryViewer().getTable().getSize().y + ptButton.y
				+ hScrolBarSize.y;
		scrollView.setMinSize(minimumWidth, minimumHeight);
	}

	private Composite headerSection(final Composite parent, Point fontSize) {
		Group group_header = new Group(parent, SWT.NONE);
		GridData gd_group_header = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
		gd_group_header.minimumWidth = 33;
		group_header.setLayoutData(gd_group_header);
		group_header.setLayout(new GridLayout(5, false));

		Label lblId = new Label(group_header, SWT.NONE);
		lblId.setText("ID:");

		shortIdData = new Label(group_header, SWT.NONE);
		GridData gd_lblShortId = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		int maxShortChangeId = 10; //Max number of chars
		int idWidth = fontSize.x * maxShortChangeId;
		gd_lblShortId.minimumWidth = idWidth;
		gd_lblShortId.widthHint = idWidth;
		shortIdData.setLayoutData(gd_lblShortId);

		Label lblChange = new Label(group_header, SWT.NONE);
		GridData gd_lblChange = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblChange.horizontalIndent = 20;
		lblChange.setLayoutData(gd_lblChange);
		lblChange.setText("Change:");

		changeidData = new Label(group_header, SWT.NONE);
		GridData gd_lblChangeid = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblChangeid.horizontalIndent = 10;
		int maxChangeId = 50; //Max number of chars
		int changeWidth = fontSize.x * maxChangeId;
		gd_lblChangeid.minimumWidth = changeWidth;
		gd_lblChangeid.widthHint = changeWidth;
		changeidData.setLayoutData(gd_lblChangeid);
		changeidData.setText("changeid");

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

		Button submit = new Button(c, SWT.PUSH);
		submit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		submit.setText("Submit");
		submit.addSelectionListener(notAvailableListener());

		Button abandon = new Button(c, SWT.PUSH);
		abandon.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		abandon.setText("Abandon");
		abandon.addSelectionListener(notAvailableListener());

		Button restore = new Button(c, SWT.PUSH);
		restore.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		restore.setText("Restore");
		restore.addSelectionListener(notAvailableListener());

		Button rebase = new Button(c, SWT.PUSH);
		rebase.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		rebase.setText("Rebase");
		rebase.addSelectionListener(notAvailableListener());

		Button checkout = new Button(c, SWT.PUSH);
		checkout.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		checkout.setText("Checkout");
		checkout.addSelectionListener(checkoutButtonListener(parent));

		Button pull = new Button(c, SWT.PUSH);
		pull.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		pull.setText("Pull");
		pull.addSelectionListener(notAvailableListener());

		Button cherrypick = new Button(c, SWT.PUSH);
		cherrypick.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		cherrypick.setText("Cherry-Pick");
		cherrypick.addSelectionListener(notAvailableListener());

		fReply = new Button(c, SWT.PUSH | SWT.DROP_DOWN | SWT.ARROW_DOWN);
		fReply.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		fReply.setText("Reply...");
		fReply.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
				Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
				org.eclipse.swt.widgets.Menu menu = new org.eclipse.swt.widgets.Menu(shell, SWT.POP_UP);

				MenuItem itemReply = new MenuItem(menu, SWT.PUSH);
				itemReply.setText("Reply...");
				itemReply.addSelectionListener(notAvailableListener());

				MenuItem itemCRPlus2 = new MenuItem(menu, SWT.PUSH);
				itemCRPlus2.setText("Code-Review+2");
				String latestPatchSet = filesTab.getLatestPatchSet();
				itemCRPlus2.setEnabled(fChangeInfo.getCodeReviewedTally() != 2 && fChangeInfo.getVerifiedTally() >= 1
						&& latestPatchSet.compareTo(fChangeInfo.getCurrentRevision()) == 0);
				if (itemCRPlus2.isEnabled()) {
					itemCRPlus2.addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent e) {
							super.widgetSelected(e);
							// Code-Review +2
							Gerrit gerrit = null;
							try {
								gerrit = filesTab.getGerritRepository().instantiateGerrit();
							} catch (EGerritException e2) {
								EGerritCorePlugin.logError(e2.getMessage());
							}

							SetReviewCommand command2 = gerrit.setReview(fChangeInfo.getChange_id(),
									fChangeInfo.getCurrentRevision());
							ReviewInput reviewInput = new ReviewInput();
							Map obj = new HashMap();
							obj.put("Code-Review", "2");

							reviewInput.setLabels(obj);

							command2.setReviewInput(reviewInput);

							ReviewInfo result2 = null;
							try {
								result2 = command2.call();
							} catch (EGerritException e1) {
								EGerritCorePlugin.logError(e1.getMessage());
							} catch (ClientProtocolException e1) {
								UIUtils.displayInformation(null, TITLE,
										e1.getLocalizedMessage() + "\n " + command2.formatRequest()); //$NON-NLS-1$
							}
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

		c.setSize(c.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		return c;
	}

	@Override
	public void setFocus() {
	}

	/**
	 * This is the entry pint to fill the editor with the Change info and the gerrit server being used.
	 *
	 * @param GerritRepository
	 *            gerritRepository
	 * @param ChangeInfo
	 *            element
	 */
	public void setChangeInfo(GerritRepository gerritRepository, ChangeInfo element) {
		filesTab.setGerritRepository(gerritRepository);
		fChangeInfo.reset();

		//Fill the data structure
		fChangeInfo.setNumber(element.get_number());
		fChangeInfo.setId(element.getId());
		fChangeInfo.setChange_id(element.getChange_id());
		fChangeInfo.setStatus(element.getStatus());
		fChangeInfo.setProject(element.getProject());
		fChangeInfo.setBranch(element.getBranch());
		fChangeInfo.setUpdated(element.getUpdated());
		fChangeInfo.setTopic(element.getTopic());
		fChangeInfo.setCodeReviewedTally(element.getCodeReviewedTally());
		fChangeInfo.setVerifiedTally(element.getVerifiedTally());

		//This query fill the current revision
		setCurrentRevisionAndMessageTab(gerritRepository, element.getChange_id());

		//Queries to fill the Summary Review tab data
		summaryTab.setTabs(gerritRepository, element);

		buttonEnablement();

	}

	/**
	 * The logic that controls when bottom buttons are enabled or not
	 */
	private void buttonEnablement() {
//		fReply.setEnabled(fChangeInfo.getCodeReviewedTally() != 2);
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
	 * @param GerritRepository
	 *            gerritRepository
	 * @param String
	 *            change_id
	 */
	private void setCurrentRevisionAndMessageTab(GerritRepository gerritRepository, String change_id) {
		ChangeInfo res = queryMessageTab(gerritRepository, change_id, new NullProgressMonitor());

		if (res != null) {
			fChangeInfo.setCurrent_revision(res.getCurrentRevision());
			fChangeInfo.setLabels(res.getLabels());
			fChangeInfo.setMessages(res.getMessages());

			//Set the file tab view
			filesTab.setTabs(res.getRevisions(), res.getCurrentRevision());
			setCurentCommitInfo(res.getCurrentRevision());

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
	private void setCurentCommitInfo(Object revision) {
		RevisionInfo match = null;
		if (revision instanceof RevisionInfo) {
			match = (RevisionInfo) revision;
		} else if (revision instanceof String) {
			match = filesTab.getRevisions().get(revision);
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

	private String getFilesContent(GerritRepository gerritRepository, String change_id, String revision_id, String file,
			IProgressMonitor monitor) {
		try {
			monitor.beginTask("Executing query", IProgressMonitor.UNKNOWN);

			Gerrit gerrit = gerritRepository.instantiateGerrit();

			// Create query
			if (gerrit != null) {
				GetContentCommand command = gerrit.getContent(change_id, revision_id, file);

				String res = null;
				try {
					res = command.call();
					return res;
				} catch (EGerritException e) {
					EGerritCorePlugin.logError(e.getMessage());
				} catch (ClientProtocolException e) {
					UIUtils.displayInformation(null, TITLE, e.getLocalizedMessage() + "\n " + command.formatRequest()); //$NON-NLS-1$
				}
			}
		} catch (UnsupportedClassVersionError e) {
			return null;
		} finally {
			monitor.done();
		}

		return null;

	}

	/**
	 * @param gerritRepository
	 * @param change_id
	 * @param monitor
	 * @return
	 */
	private ChangeInfo queryMessageTab(GerritRepository gerritRepository, String change_id, IProgressMonitor monitor) {
		try {

			monitor.beginTask("Executing query", IProgressMonitor.UNKNOWN);

			Gerrit gerrit = gerritRepository.instantiateGerrit();

			// Create query
			if (gerrit != null) {
				GetChangeCommand command = gerrit.getChange(change_id);
				command.addOption(ChangeOption.ALL_FILES);
				command.addOption(ChangeOption.ALL_REVISIONS);
				command.addOption(ChangeOption.ALL_COMMITS);
				command.addOption(ChangeOption.DRAFT_COMMENTS);
				command.addOption(ChangeOption.REVIEWED);
				command.addOption(ChangeOption.MESSAGES);
				command.addOption(ChangeOption.DOWNLOAD_COMMANDS);

				ChangeInfo res = null;
				try {
					res = command.call();
				} catch (EGerritException e) {
					EGerritCorePlugin.logError(e.getMessage());
				} catch (ClientProtocolException e) {
					UIUtils.displayInformation(null, TITLE, e.getLocalizedMessage() + "\n " + command.formatRequest()); //$NON-NLS-1$
				}
				return res;
			}
		} catch (UnsupportedClassVersionError e) {
			return null;
		} finally {
			monitor.done();
		}
		return null;
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
		IObservableValue observeTextLblChangeid_1ObserveWidget = WidgetProperties.text().observe(changeidData);
		IObservableValue changeIdFChangeInfoObserveValue = BeanProperties.value("change_id").observe(fChangeInfo);
		bindingContext.bindValue(observeTextLblChangeid_1ObserveWidget, changeIdFChangeInfoObserveValue, null, null);
		//
		IObservableValue observeTextLblStatusObserveWidget = WidgetProperties.text().observe(statusData);
		IObservableValue statusFChangeInfoObserveValue = BeanProperties.value("status").observe(fChangeInfo);
		bindingContext.bindValue(observeTextLblStatusObserveWidget, statusFChangeInfoObserveValue, null, null);

		return bindingContext;
	}

	/**
	 * @return ChangeDetailEditor
	 */
	public static ChangeDetailEditor getActiveEditor() {
		IEditorPart editorPart = null;
		if (chDetailEditor != null) {
			return chDetailEditor;
		} else {
			IWorkbench workbench = EGerritUIPlugin.getDefault().getWorkbench();
			IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
			IWorkbenchPage page = null;
			if (window != null) {
				page = workbench.getActiveWorkbenchWindow().getActivePage();
			}
			try {
				editorPart = page.openEditor(new org.eclipse.egerrit.ui.editors.model.ChangeDetailEditorInput(),
						EDITOR_ID);
			} catch (PartInitException e) {
//				GerritUi.Ftracer.traceWarning(e.getMessage());
			}
			return (ChangeDetailEditor) editorPart;

		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#dispose()
	 */
	@Override
	public void dispose() {
		IEditorPart editorPart = null;
		IWorkbench workbench = EGerritUIPlugin.getDefault().getWorkbench();
		IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
		IWorkbenchPage page = null;
		if (window != null) {
			page = workbench.getActiveWorkbenchWindow().getActivePage();
		}
		page.closeEditor(chDetailEditor, false);

		chDetailEditor = null;
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

				Repository localRepo = null;
				try {
					localRepo = findLocalRepo(filesTab.getGerritRepository(), summaryTab.getProject());
				} catch (EGerritException e) {
					EGerritCorePlugin.logError(e.getLocalizedMessage(), e);
				}

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
	 * @param GerritRepository
	 *            gerritRepo
	 * @param String
	 *            projectName
	 * @return Repository The local repository associated to the current project and Gerrit Repository
	 */
	private Repository findLocalRepo(GerritRepository gerritRepo, String projectName) {
		GerritToGitMapping gerritToGitMap = null;
		try {
			gerritToGitMap = new GerritToGitMapping(new URIish(gerritRepo.getURIBuilder(false).toString()),
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
			setCurentCommitInfo(fileTabView.getCurrentRevision());
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
