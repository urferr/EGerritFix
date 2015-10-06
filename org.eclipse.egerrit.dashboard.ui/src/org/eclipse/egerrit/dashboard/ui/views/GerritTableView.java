/*******************************************************************************
 * Copyright (c) 2013 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jacques Bouthillier - Initial Implementation of the plug-in
 *   Francois Chouinard  - Handle gerrit queries and open reviews in editor
 *   Guy Perron          - Add review counter, Add Gerrit button selection
 *   Jacques Bouthillier - Bug 426580 Add the starred functionality
 ******************************************************************************/

package org.eclipse.egerrit.dashboard.ui.views;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.http.client.ClientProtocolException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.egerrit.core.EGerritCorePlugin;
import org.eclipse.egerrit.core.GerritClient;
import org.eclipse.egerrit.core.GerritCredentials;
import org.eclipse.egerrit.core.GerritRepository;
import org.eclipse.egerrit.core.GerritServerInformation;
import org.eclipse.egerrit.core.ServersStore;
import org.eclipse.egerrit.core.command.ChangeOption;
import org.eclipse.egerrit.core.command.ChangeState;
import org.eclipse.egerrit.core.command.ChangeStatus;
import org.eclipse.egerrit.core.command.QueryChangesCommand;
import org.eclipse.egerrit.core.command.QueryCommand;
import org.eclipse.egerrit.core.exception.EGerritException;
import org.eclipse.egerrit.core.rest.ChangeInfo;
import org.eclipse.egerrit.dashboard.core.GerritQuery;
import org.eclipse.egerrit.dashboard.core.GerritQueryException;
import org.eclipse.egerrit.dashboard.ui.GerritUi;
import org.eclipse.egerrit.dashboard.ui.internal.commands.AddGerritSiteHandler;
import org.eclipse.egerrit.dashboard.ui.internal.model.ReviewTableData;
import org.eclipse.egerrit.dashboard.ui.internal.model.UIReviewTable;
import org.eclipse.egerrit.dashboard.ui.internal.utils.SelectionDialog;
import org.eclipse.egerrit.dashboard.ui.internal.utils.UIUtils;
import org.eclipse.egerrit.dashboard.ui.preferences.Utils;
import org.eclipse.egerrit.dashboard.utils.GerritServerUtility;
import org.eclipse.egerrit.ui.editors.ChangeDetailEditor;
import org.eclipse.egerrit.ui.editors.model.ChangeDetailEditorInput;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.services.IServiceLocator;
import org.osgi.framework.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// need our own to handle query result

/**
 * This class initiate a new workbench view. The view shows data obtained from Gerrit Dashboard model. The view is
 * connected to the model using a content provider.
 * <p>
 * The view uses a label provider to define how model objects should be presented in the view.
 *
 * @author Jacques Bouthillier
 * @since 1.0
 */

public class GerritTableView extends ViewPart {

	private static Logger logger = LoggerFactory.getLogger(GerritTableView.class);

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String VIEW_ID = "org.eclipse.egerrit.dashboard.ui.views.GerritTableView"; //$NON-NLS-1$

	private static final int SEARCH_WIDTH = 350;

	private static final int REPO_WIDTH = 350;

	private static final int VERSION_WIDTH = 35;

	//Numbers of menu items in the Search pulldown menu; SEARCH_SIZE_MENU_LIST + 1 will be the max
	private static final int SEARCH_SIZE_MENU_LIST = 4;

	private static final String ADJUST_MY_STARRED_COMMAND_ID = "org.eclipse.egerrit.dashboard.ui.adjustMyStarred"; //$NON-NLS-1$

	public static final String CONNECTOR_KIND = "org.eclipse.egerrit"; //$NON-NLS-1$

	private final String TITLE = "Gerrit Server ";

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	private GerritServerInformation defaultServerInfo = null;

	private static GerritTableView rtv = null;

	private Label fRepositoryVersionResulLabel;

	private Label fReviewsTotalLabel;

	private Label fReviewsTotalResultLabel;

	private Combo fSearchRequestText;

	private Button fSearchRequestBtn;

	private Set<String> fRequestList = new LinkedHashSet<String>();

	private TableViewer fViewer;

	private ReviewTableData fReviewTable = new ReviewTableData();

	private GerritServerUtility fServerUtil = GerritServerUtility.getInstance();

	private List<GerritServerInformation> fMapRepoServer = null;

	private Action doubleClickAction;

	private final LinkedHashSet<Job> fJobs = new LinkedHashSet<Job>();

	private GerritClient gerritClient = null;

	//	GerritClient gerritClient = null;

	// ------------------------------------------------------------------------
	// TableRefreshJob
	// ------------------------------------------------------------------------

//	private TableRefreshJob fTableRefreshJob;

	// Periodical refreshing job
//	private final class TableRefreshJob extends DelayedRefreshJob {
//
//		private TableRefreshJob(TableViewer viewer, String name) {
//			super(viewer, name);
//		}
//
//		@Override
//		protected void doRefresh(Object[] items) {
//			Display.getDefault().syncExec(new Runnable() {
//				@Override
//				public void run() {
//					fViewer.setInput(fReviewTable.getReviews());
//					//Refresh the counter
//					setReviewsTotalResultLabel(Integer.toString(fReviewTable.getReviews().length));
//					fViewer.refresh(false, false);
//				}
//			});
//		}
//	}

	// ------------------------------------------------------------------------
	// Constructor and life cycle
	// ------------------------------------------------------------------------

	/**
	 * The constructor.
	 */
	public GerritTableView() {
		super();
		rtv = this;
	}

	public void setReviewTableData(ReviewTableData ReviewTable) {
		fReviewTable = ReviewTable;
	}

	public void setGerritServerUtility(GerritServerUtility ServerUtil) {
		fServerUtil = ServerUtil;

	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#dispose()
	 */
	@Override
	public void dispose() {
//		TasksUiPlugin.getTaskList().removeChangeListener(this);
//		fTableRefreshJob.cancel();
		cleanJobs();
		rtv = null;
	}

	private void cleanJobs() {
		Iterator<Job> iter = fJobs.iterator();
		while (iter.hasNext()) {
			Job job = iter.next();
			job.sleep();
			job.cancel();
		}
		fJobs.clear();
	}

	/**
	 * Refresh the view content
	 */
	private void refresh() {
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				fViewer.setInput(fReviewTable.getReviews());
				//Refresh the counter
				setReviewsTotalResultLabel(Integer.toString(fReviewTable.getReviews().length));
				fViewer.refresh(false, false);
			}
		});
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createPartControl(Composite aParent) {
		ScrolledComposite sc = new ScrolledComposite(aParent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		sc.setExpandHorizontal(true);
		Composite c = new Composite(sc, SWT.NONE);
		sc.setContent(c);
		sc.setExpandVertical(true);

		createSearchSection(c);
		UIReviewTable reviewTable = new UIReviewTable();
		fViewer = reviewTable.createTableViewerSection(c);

		// Setup the view layout
		createLayout(c);

		makeActions();
		hookContextMenu();
		hookDoubleClickAction();

		// Start the periodic refresh job
//		fTableRefreshJob = new TableRefreshJob(fViewer, Messages.GerritTableView_refreshTable);

		sc.setMinSize(c.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}

	private void createLayout(Composite aParent) {

		//Add a listener when the view is resized
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		layout.verticalSpacing = 1;
		layout.makeColumnsEqualWidth = false;

		aParent.setLayout(layout);
	}

	/**
	 * Create a group to show the search command and a search text
	 *
	 * @param Composite
	 *            aParent
	 */
	private void createSearchSection(Composite aParent) {

		final Group formGroup = new Group(aParent, SWT.SHADOW_ETCHED_IN | SWT.H_SCROLL);

		GridData gridDataGroup = new GridData(GridData.FILL_HORIZONTAL);
		formGroup.setLayoutData(gridDataGroup);

		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginTop = 0;
		layout.marginHeight = 0;
		layout.verticalSpacing = 0;
		layout.makeColumnsEqualWidth = false;

		formGroup.setLayout(layout);

		//Left side of the Group
		//Create a form to maintain the search data
		Composite leftSearchForm = UIUtils.createsGeneralComposite(formGroup, SWT.NONE);

		GridData gridDataViewer = new GridData(GridData.FILL_HORIZONTAL);
		leftSearchForm.setLayoutData(gridDataViewer);

		GridLayout leftLayoutForm = new GridLayout();
		leftLayoutForm.numColumns = 3;
		leftLayoutForm.marginHeight = 0;
		leftLayoutForm.makeColumnsEqualWidth = false;
		leftLayoutForm.horizontalSpacing = 0;

		leftSearchForm.setLayout(leftLayoutForm);

		//Label to display the repository and the version
		fRepositoryVersionResulLabel = new Label(leftSearchForm, SWT.NONE);
		fRepositoryVersionResulLabel.setLayoutData(new GridData(REPO_WIDTH, SWT.DEFAULT));

		//Label to display Total reviews
		fReviewsTotalLabel = new Label(leftSearchForm, SWT.NONE);
		fReviewsTotalLabel.setText(Messages.GerritTableView_totalReview);

		fReviewsTotalResultLabel = new Label(leftSearchForm, SWT.NONE);
		fReviewsTotalResultLabel.setLayoutData(new GridData(VERSION_WIDTH, SWT.DEFAULT));

		//Right side of the Group
		Composite rightSsearchForm = UIUtils.createsGeneralComposite(formGroup, SWT.NONE);
		GridData gridDataViewer2 = new GridData(GridData.FILL_HORIZONTAL | GridData.HORIZONTAL_ALIGN_END);
		rightSsearchForm.setLayoutData(gridDataViewer2);
		GridLayout rightLayoutForm = new GridLayout();
		rightLayoutForm.numColumns = 2;
		rightLayoutForm.marginHeight = 0;
		rightLayoutForm.makeColumnsEqualWidth = false;

		rightSsearchForm.setLayout(rightLayoutForm);

		//Create a SEARCH text data entry
		fSearchRequestText = new Combo(rightSsearchForm, SWT.NONE);
		fSearchRequestText.setLayoutData(new GridData(SEARCH_WIDTH, SWT.DEFAULT));
		fSearchRequestText.setToolTipText(Messages.GerritTableView_tooltipSearch);
		//Get the last saved commands
		fRequestList = fServerUtil.getListLastCommands();
		setSearchText(""); //$NON-NLS-1$

		//Handle the CR in the search text
		fSearchRequestText.addListener(SWT.DefaultSelection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				//The shorten request is: "is:open" with 7 characters, so need to process the command if text is smaller
				if (fSearchRequestText.getText().trim().length() > 6) {
					processCommands(fSearchRequestText.getText());

				}
			}
		});

		fSearchRequestText.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				String old = fSearchRequestText.getItem(0);
				Object obj = e.getSource();
				if (obj instanceof Combo) {
					Combo combo = (Combo) obj;
					//Trigger a request only if this is not the last one requested
					if (combo.getText().compareTo(old) != 0) {
						processCommands(combo.getText());
					}
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		//Create a SEARCH button
		fSearchRequestBtn = new Button(rightSsearchForm, SWT.NONE);
		fSearchRequestBtn.setText(Messages.GerritTableView_search);
		fSearchRequestBtn.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				processCommands(fSearchRequestText.getText());
			}
		});

	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager(Messages.GerritTableView_popupMenu);
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				GerritTableView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(fViewer.getControl());
		fViewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, fViewer);
	}

	private void fillContextMenu(IMenuManager manager) {
		CommandContributionItem[] contribItems = buildContributions();
		for (CommandContributionItem contribItem : contribItems) {
			manager.add(contribItem);
		}
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	private void makeActions() {
		doubleClickAction = new Action() {
			@Override
			public void run() {
				FormPage form_Page;

				// -------------------------------------------------
				// Open an editor on the provided server and changeInfo
				// -------------------------------------------------

				ISelection selection = fViewer.getSelection();
				if (!(selection instanceof IStructuredSelection)) {
					return;
				}
				IStructuredSelection structuredSelection = (IStructuredSelection) selection;
				Iterator<?> selections = structuredSelection.iterator();
				while (selections.hasNext()) {
					Object element = selections.next();
					if (element instanceof ChangeInfo) {
						IWorkbench workbench = GerritUi.getDefault().getWorkbench();
						IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
						IWorkbenchPage page = null;
						if (window != null) {
							page = workbench.getActiveWorkbenchWindow().getActivePage();
						}

						if (page != null) {
							try {
								page.openEditor(new ChangeDetailEditorInput(gerritClient, (ChangeInfo) element),
										ChangeDetailEditor.EDITOR_ID);
							} catch (PartInitException e) {
								EGerritCorePlugin.logError(e.getMessage());
							}
						}
					}

				}

			}
		};
	}

	private void hookDoubleClickAction() {
		fViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	@Override
	public void setFocus() {
		fViewer.getControl().setFocus();
	}

	/**
	 * Create a list for commands to add to the table review list menu
	 *
	 * @return CommandContributionItem[]
	 */
	private CommandContributionItem[] buildContributions() {
		IServiceLocator serviceLocator = getViewSite().getActionBars().getServiceLocator();
		CommandContributionItem[] contributionItems = new CommandContributionItem[1];
		CommandContributionItemParameter contributionParameter = new CommandContributionItemParameter(serviceLocator,
				Messages.GerritTableView_starredName, ADJUST_MY_STARRED_COMMAND_ID, CommandContributionItem.STYLE_PUSH);

		contributionParameter.label = Messages.GerritTableView_starredName;
		contributionParameter.visibleEnabled = true;
		contributionItems[0] = new CommandContributionItem(contributionParameter);

		return contributionItems;

	}

	public TableViewer getTableViewer() {
		return fViewer;
	}

	public static GerritTableView getActiveView() {
		IViewPart viewPart = null;
		if (rtv != null) {
			return rtv;
		} else {
			IWorkbench workbench = GerritUi.getDefault().getWorkbench();
			IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
			IWorkbenchPage page = null;
			if (window != null) {
				page = workbench.getActiveWorkbenchWindow().getActivePage();
			}

			if (page != null) {
				viewPart = page.findView(VIEW_ID);
				// The following can occurs in LINUX environment since
				// killing the window call the dispose() method

				if (viewPart == null) {
					try {
						viewPart = page.showView(VIEW_ID, null, org.eclipse.ui.IWorkbenchPage.VIEW_CREATE);
					} catch (PartInitException e) {
						logger.warn(e.getMessage());
					}
					logger.warn("getActiveView() SHOULD (JUST) CREATED A NEW Table:" + viewPart);

				}
			}

			return (GerritTableView) viewPart;
		}
	}

	/**
	 * bring the Gerrit Dashboard view visible to the current workbench
	 */
	public void openView() {
		IWorkbench workbench = GerritUi.getDefault().getWorkbench();
		IWorkbenchPage page = workbench.getActiveWorkbenchWindow().getActivePage();
		IViewPart viewPart = page.findView(VIEW_ID);
		// if the review view is not showed yet,
		if (viewPart == null) {
			try {
				viewPart = page.showView(VIEW_ID);
			} catch (PartInitException e) {
				logger.warn(e.getMessage());
			}
		}
		// if there exists the view, but if not on the top,
		// then brings it to top when the view is already showed.
		else if (!page.isPartVisible(viewPart)) {
			page.bringToTop(viewPart);
		}
	}

	/**
	 * Process the commands based on the Gerrit string
	 *
	 * @param String
	 *            aQuery
	 */
	public void processCommands(String aQuery) {
		logger.debug("Process command :   " + aQuery);
		defaultServerInfo = null;
		aQuery = handleHttpInQuery(aQuery);

		initializeDefaultServer();

		//We should have a Gerrit Server here, otherwise, the user need to define one
		if (defaultServerInfo == null) {
			UIUtils.showErrorDialog(Messages.GerritTableView_defineRepository,
					Messages.GerritTableView_noGerritRepository);
			new AddGerritSiteHandler().execute(null);
			return;
		}

		//At this point we have a server, execute the query if we can
		if (aQuery != null && !aQuery.equals("")) { //$NON-NLS-1$
			updateTable(defaultServerInfo, aQuery);
		}
	}

	private void initializeDefaultServer() {
		//Use the last server if no server got discovered
		if (defaultServerInfo == null) {
			GerritServerInformation lastServer = fServerUtil.getLastSavedGerritServer();
			if (lastServer != null) {
				//Already saved a Gerrit server, so use it
				defaultServerInfo = lastServer;
			}
		}

		//No last server was specified, get a server by prompting the user or picking the first one
		if (defaultServerInfo == null) {
			fMapRepoServer = ServersStore.getAllServers();
			if (fMapRepoServer.size() == 1) {
				defaultServerInfo = fMapRepoServer.get(0);
			} else if (fMapRepoServer.size() > 1) {
				defaultServerInfo = askUserToSelectRepo(ServersStore.getAllServers());
			}
		}

		if (defaultServerInfo != null) {
			fServerUtil.saveLastGerritServer(defaultServerInfo);
		}
	}

	private String handleHttpInQuery(String aQuery) {
		ChangeIdExtractor extractedData = new ChangeIdExtractor(aQuery);
		if (extractedData.getServer() != null) {
			selectServer(extractedData.getServer());
			if (extractedData.getChangeId() != null) {
				aQuery = "change:" + extractedData.getChangeId(); //$NON-NLS-1$
			} else {
				aQuery = "status:open"; //$NON-NLS-1$
			}
		}
		return aQuery;
	}

	private void selectServer(GerritServerInformation server) {
		List<GerritServerInformation> matches = findOrAddMatchingServers(server);
		if (matches.size() == 1) {
			defaultServerInfo = matches.get(0);
			return;
		}
		defaultServerInfo = askUserToSelectRepo(matches);
	}

	private List<GerritServerInformation> findOrAddMatchingServers(GerritServerInformation searched) {
		List<GerritServerInformation> knownServers = ServersStore.getAllServers();
		List<GerritServerInformation> matches = new ArrayList<>();
		List<GerritServerInformation> bestMatches = new ArrayList<>();

		//Best matches are those with the same URI and a username
		//Second best matches are servers with URIs
		for (GerritServerInformation candidate : knownServers) {
			if (candidate.getServerURI().equals(searched.getServerURI())) {
				if (candidate.getUserName().equals(searched.getUserName())) {
					bestMatches.add(0, candidate);
				} else {
					matches.add(candidate);
				}
			}
		}

		//Return the best matches if we have any
		if (!bestMatches.isEmpty()) {
			return bestMatches;
		}

		//No match at all, then we will the one we have
		if (matches.size() == 0) {
			knownServers.add(searched);
			ServersStore.saveServers(knownServers);
			matches.add(searched);
		}

		return matches;
	}

	/**
	 * Process the command to set the Starred flag on the Gerrit server String taskId boolean starred
	 *
	 * @param progressMonitor
	 * @return void
	 * @throws CoreException
	 */
	public void setStarred(String taskID, boolean starred, IProgressMonitor progressMonitor) throws CoreException {
		if (defaultServerInfo == null) {
			UIUtils.showErrorDialog(Messages.GerritTableView_defineRepository,
					Messages.GerritTableView_noGerritRepository);
		} else {
			//		fConnector.setStarred(fTaskRepository, taskID, starred, progressMonitor);
		}
	}

	/**
	 * Find the last Gerrit server being used , otherwise consider the Eclipse.org gerrit server version as a default
	 *
	 * @return Version
	 */
	public Version getlastGerritServerVersion() {
		Version version = null;
		GerritServerInformation lastSaved = fServerUtil.getLastSavedGerritServer();

		if (lastSaved != null) {
			//Already saved a Gerrit server, so use it
			defaultServerInfo = lastSaved;
		}

		if (defaultServerInfo == null) {
			//If we did not find the task Repository
			fMapRepoServer = ServersStore.getAllServers();
			//Verify How many gerrit server are defined
			if (fMapRepoServer.size() == 1) {
				for (GerritServerInformation key : fMapRepoServer) {
					defaultServerInfo = key;
					//Save it for the next query time
					fServerUtil.saveLastGerritServer(key);
					break;
				}

			}
		}

		//We should have a Gerrit Server information here, otherwise, the user need to define one
//		if (defaultServerInfo != null) {
//			if (setConnector(fConnector)) {
//				GerritClient gerritClient = fConnector.getClient(fTaskRepository);
//				try {
//					version = gerritClient.getVersion(new NullProgressMonitor());
//					logger.debug("Selected version: " + version.toString()); //$NON-NLS-1$
//				} catch (GerritException e) {
//					StatusHandler.log(new Status(IStatus.ERROR, GerritCorePlugin.PLUGIN_ID, e.getMessage(), e));
//				}
//			}
		//}
		return version;
	}

	/**
	 * Verify if the Gerrit version is before 2.5
	 *
	 * @return boolean
	 */
	public boolean isGerritVersionBefore_2_5() {
		boolean ret = false;

		Version version = getlastGerritServerVersion();
		if (version != null && version.getMajor() >= 2) {
			if (version.getMinor() < 5) {
				ret = true;
			}
		}
		return ret;
	}

	/**
	 * @param server
	 * @param aQueryType
	 * @return
	 */
	private Object updateTable(final GerritServerInformation server, final String aQueryType) {

		String cmdMessage = NLS.bind(Messages.GerritTableView_commandMessage, server.getServerURI(), aQueryType);
		final Job job = new Job(cmdMessage) {

			@Override
			public boolean belongsTo(Object aFamily) {
				return Messages.GerritTableView_dashboardUiJob.equals(aFamily);
			}

			@Override
			public IStatus run(final IProgressMonitor aMonitor) {

				// If there is only have one Gerrit server, we can proceed as if it was already used before
				IStatus status = null;
				try {
					fReviewTable.createReviewItem(aQueryType, server);
					status = getReviews(server, aQueryType);
					if (status.isOK()) {
						Display.getDefault().syncExec(new Runnable() {
							@Override
							public void run() {
// TODO
								//								if (aQueryType != GerritQuery.CUSTOM) {
//									if (fCurrentQuery != null) {
//										String query = fCurrentQuery.getAttribute(GerritQuery.QUERY_STRING);
//										setSearchText(query);
//									}
//								} else {
								//Record the custom query
								setSearchText(getSearchText());
//								}
//								if (gerritClient == null) {
//									gerritClient = new GerritClient(null, new TaskRepositoryLocation(
//											new TaskRepository(TaskRepository.CATEGORY_REVIEW,
//													defaultServerInfo.getServerURL())));
//								}

								// TODO
//								if (gerritClient != null) {
//									try {
//										setRepositoryVersionLabel(aTaskRepo.getServerName(),
//												gerritClient.getVersion(new NullProgressMonitor()).toString());
//									} catch (GerritException e) {
//										logger.error(e.getMessage());
//
//									}
//								}

							}
						});
					}
				} catch (GerritQueryException e) {
					status = e.getStatus();
					logger.error(e.getMessage());

				}

				aMonitor.done();
				fJobs.remove(this);
				return status;
			}
		};
		//Clean some Jobs if still running
		cleanJobs();

		fJobs.add(job);
		job.setUser(true);
		job.schedule();

		return null;
	}

	private void setSearchText(String aSt) {
		if (!fSearchRequestText.isDisposed()) {
			if (aSt != null && aSt != "") { //$NON-NLS-1$
				int index = -1;
				String[] ar = fSearchRequestText.getItems();
				for (int i = 0; i < ar.length; i++) {
					if (ar[i].equals(aSt.trim())) {
						index = i;
						break;
					}
				}

				if (index != -1) {
					fRequestList.remove(fRequestList.remove(ar[index]));
				} else {
					//Remove the oldest element from the list
					if (fRequestList.size() > SEARCH_SIZE_MENU_LIST) {
						Object obj = fRequestList.iterator().next(); //Should be the first item in the list
						fRequestList.remove(fRequestList.remove(obj));
					}
				}
				//Add the new text in the combo
				fRequestList.add(aSt.trim());
				//Save the list of commands in file
				fServerUtil.saveLastCommandList(fRequestList);

			}

			fSearchRequestText.setItems(reverseOrder(fRequestList.toArray(new String[0])));
			if (aSt != null && aSt != "") { //$NON-NLS-1$
				fSearchRequestText.select(0);
			} else {
				//Leave the text empty
				fSearchRequestText.setText(""); //$NON-NLS-1$
			}
		}
	}

	/**
	 * Take the list of last save queries and reverse the order to have the latest selection to be the first one on the
	 * pull-down menu
	 *
	 * @param aList
	 *            String[]
	 * @return String[] reverse order
	 */
	private String[] reverseOrder(String[] aList) {
		int size = aList.length;
		int index = size - 1;
		String[] rev = new String[size];
		for (int i = 0; i < size; i++) {
			rev[i] = aList[index--];
		}
		return rev;
	}

	private String getSearchText() {
		if (!fSearchRequestText.isDisposed()) {
			final String[] str = new String[1];
			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					str[0] = fSearchRequestText.getText().trim();
					logger.debug("Custom string: " + str[0]); //$NON-NLS-1$
				}
			});
			return str[0];
		}
		return null;
	}

	// ------------------------------------------------------------------------
	// Query handling
	// ------------------------------------------------------------------------

	private void displayWarning(final String st) {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				final MessageDialog dialog = new MessageDialog(null, Messages.GerritTableView_warning, null, st,
						MessageDialog.WARNING, new String[] { IDialogConstants.CANCEL_LABEL }, 0);
				dialog.open();
			}
		});
	}

	/**
	 * Perform the requested query and convert the resulting tasks in GerritTask:s
	 *
	 * @param repository
	 *            the tasks repository
	 * @param queryType
	 *            the query
	 * @return IStatus
	 * @throws GerritQueryException
	 */
	private IStatus getReviews(GerritServerInformation repository, String queryType) throws GerritQueryException {
		if (repository.getUserName() == null || repository.getUserName().isEmpty()) {
			//Test for Anonymous user
			if (queryType.equals(GerritQuery.MY_CHANGES)
					|| queryType.equals(GerritQuery.QUERY_MY_DRAFTS_COMMENTS_CHANGES)) {
				displayWarning(NLS.bind(Messages.GerritTableView_warningAnonymous, queryType));
				return Status.CANCEL_STATUS;
			} else if (queryType == GerritQuery.CUSTOM) {
				int foundSelf = getSearchText().toLowerCase().indexOf("self"); //$NON-NLS-1$
				int foundhasDraft = getSearchText().toLowerCase().indexOf(GerritQuery.QUERY_MY_DRAFTS_COMMENTS_CHANGES);
				if (foundSelf != -1 || foundhasDraft != -1) {
					displayWarning(NLS.bind(Messages.GerritTableView_warningSearchAnonymous, getSearchText()));
					return Status.CANCEL_STATUS;
				}
			}
		}

		// Format the query id
		String queryId = rtv.getTitle() + " - " + queryType; //$NON-NLS-1$

//		RepositoryQuery query = null;
		String queryString = null;

		queryString = queryType;

		// Save query
//		fCurrentQuery = query;
		URI uri = null;
		try {
			uri = new URI(repository.getServerURI());
		} catch (URISyntaxException e) {
			EGerritCorePlugin.logError(e.getMessage());

		}
		String SCHEME = uri.getScheme();
		String HOST = uri.getHost();
		int PORT = uri.getPort();
		String PATH = uri.getPath();
		String USER = repository.getUserName();
		String PASSWORD = repository.getPassword();
		GerritCredentials creds = new GerritCredentials(USER, PASSWORD);
		// Initialize
		GerritRepository gerritRepository = new GerritRepository(SCHEME, HOST, PORT, PATH);
		gerritRepository.setCredentials(creds);
		gerritRepository.setServerInfo(repository);
		gerritRepository.acceptSelfSignedCerts(defaultServerInfo.getSelfSigned());

		gerritClient = gerritRepository.instantiateGerrit();
		ChangeInfo[] reviews = null;
		if (gerritClient != null) {
			// Fetch the list of reviews and pre-populate the table
			reviews = getReviewList(repository, queryString);
		} else {
			//Reset the list to prevent bad request
			reviews = new ChangeInfo[0];
			fServerUtil.resetLastGerritServer();

			Display.getDefault().syncExec(new Runnable() {
				@Override
				public void run() {
					setRepositoryVersionLabel("Invalid Server", "NO connection");
				}
			});

		}
		fReviewTable.init(reviews);
		refresh();

		return Status.OK_STATUS;

	}

	private ChangeInfo[] getReviewList(GerritServerInformation repository, String aQuery) throws GerritQueryException {

		ChangeInfo[] reviews = null;

		try {
			reviews = performQuery(aQuery, new NullProgressMonitor());
		} catch (MalformedURLException e) {
			EGerritCorePlugin.logError(e.getMessage());

		}

		return reviews;
	}

	public ChangeInfo[] performQuery(String query, IProgressMonitor monitor) throws MalformedURLException {
		try {
			monitor.beginTask("Executing query", IProgressMonitor.UNKNOWN);

			ChangeInfo[] res = null;
			QueryChangesCommand command = gerritClient.queryChanges();
			command.addOption(ChangeOption.DETAILED_LABELS);
			command.addOption(ChangeOption.CURRENT_REVISION);
			command.addOption(ChangeOption.CURRENT_FILES);
			command.addOption(ChangeOption.DETAILED_ACCOUNTS);

			try {
				setQuery(query, command);
				res = command.call();
			} catch (EGerritException e) {
				Utils.displayInformation(null, TITLE, e.getLocalizedMessage());
			} catch (ClientProtocolException e) {
				Utils.displayInformation(null, TITLE, e.getLocalizedMessage() + "\n " + query);
			}
			final String queryText = query;
			Display.getDefault().syncExec(new Runnable() {
				@Override
				public void run() {
					setRepositoryVersionLabel(defaultServerInfo.getName(),
							gerritClient.getRepository().getVersion().toString());
					fSearchRequestText.setText(queryText);

				}
			});
			return res;
		} catch (UnsupportedClassVersionError e) {
//			return new Status(IStatus.ERROR, GerritCorePlugin.PLUGIN_ID, "error", e);
			return null;
		} finally {
			monitor.done();
		}
	}

	private void setQuery(String query, QueryChangesCommand command) throws EGerritException {
		if (!query.isEmpty()) {
			if (query.compareTo(GerritQuery.MY_CHANGES) == 0) {
				command.addOwner("self");
			} else if (query.compareTo(GerritQuery.MY_WATCHED_CHANGES) == 0) {
				// is:open is:watched
				command.addState(ChangeState.IS_OPEN);
				command.addState(ChangeState.IS_WATCHED);
			} else if (query.compareTo(GerritQuery.QUERY_MY_STARRED_CHANGES) == 0) {
				// is:starred
				command.addState(ChangeState.IS_STARRED);
			} else if (query.compareTo(GerritQuery.QUERY_MY_DRAFTS_CHANGES) == 0) {
				// is:draft
				command.addState(ChangeState.IS_DRAFT);
			} else if (query.compareTo(GerritQuery.QUERY_MY_DRAFTS_COMMENTS_CHANGES) == 0) {
				// has:draft
				command.addState(ChangeState.HAS_DRAFT);
			} else if (query.compareTo(GerritQuery.ALL_OPEN_CHANGES) == 0) {
				// status:open (or is:open)
				command.addState(ChangeState.IS_OPEN);
			} else if (query.compareTo(GerritQuery.QUERY_ALL_MERGED_CHANGES) == 0) {
				// status:merged
				command.addState(ChangeState.IS_MERGED);
			} else if (query.compareTo(GerritQuery.QUERY_ALL_ABANDONED_CHANGES) == 0) {
				// status:abandoned
				command.addState(ChangeState.IS_ABANDONED);
			} else if (query.compareTo(GerritQuery.OPEN_CHANGES_BY_PROJECT) == 0) {
				// status:open
				command.addState(ChangeState.IS_OPEN);
				command.addProject(""); // TODO get project name
			} else {
				//Custom Queries
				setFreeTextQuery(query, command);
			}

		}
	}

	private void setFreeTextQuery(String query, QueryChangesCommand command) throws EGerritException {
		if (!query.isEmpty()) {
			Map<String, String> map = parseQuery(query);

			Iterator<Map.Entry<String, String>> mapIter = map.entrySet().iterator();
			while (mapIter.hasNext()) {
				Entry<String, String> entryMap = mapIter.next();

				String key = entryMap.getKey().toUpperCase();
				String value = entryMap.getValue();
				if (key.compareTo(QueryCommand.BRANCH) == 0) {
					command.addBranch(value);
				} else if (key.compareTo(QueryCommand.BUG) == 0) {
					command.addBug(value);
				} else if (key.compareTo(QueryCommand.COMMENT) == 0) {
					command.addComment(value);
				} else if (key.compareTo(QueryCommand.COMMIT) == 0) {
					command.addCommit(value);
				} else if (key.compareTo(QueryCommand.FILE) == 0) {
					command.addFile(value);
				} else if (key.compareTo(QueryCommand.SHORTFILE) == 0) {
					command.addShortFile(value);
				} else if (key.compareTo(QueryCommand.LABEL) == 0) {
					command.addLabel(value);
				} else if (key.compareTo(QueryCommand.MESSAGE) == 0) {
					command.addMessage(value);
				} else if (key.compareTo(QueryCommand.OWNER) == 0) {
					command.addOwner(value);
				} else if (key.compareTo(QueryCommand.SHORTOWNER) == 0) {
					command.addShortOwner(value);
				} else if (key.compareTo(QueryCommand.OWNERIN) == 0) {
					command.addOwnerGroup(value);
				} else if (key.compareTo(QueryCommand.PARENTPROJECT) == 0) {
					command.addParentProject(value);
				} else if (key.compareTo(QueryCommand.PATH) == 0) {
					command.addPath(value);
				} else if (key.compareTo(QueryCommand.PREFIX) == 0) {
					command.addPrefix(value);
				} else if (key.compareTo(QueryCommand.PROJECT) == 0) {
					command.addProject(value);
				} else if (key.compareTo(QueryCommand.SHORTPROJECT) == 0) {
					command.addShortProject(value);
				} else if (key.compareTo(QueryCommand.MANYPROJECT) == 0) {
					command.addManyProjects(value);
				} else if (key.compareTo(QueryCommand.REF) == 0) {
					command.addReference(value);
				} else if (key.compareTo(QueryCommand.REVIEWER) == 0) {
					command.addReviewer(value);
				} else if (key.compareTo(QueryCommand.SHORTREVIEWER) == 0) {
					command.addShortReviewer(value);
				} else if (key.compareTo(QueryCommand.REVIEWERIN) == 0) {
					command.addReviewerGroup(value);
				} else if (key.compareTo(QueryCommand.STATE) == 0) {
					command.addState(ChangeState.valueOf(value.toUpperCase())); //need to be as defined in ChangeState
				} else if (key.compareTo(QueryCommand.STATUS) == 0) {
					command.addStatus(ChangeStatus.valueOf(value.toUpperCase()));
				} else if (key.compareTo(QueryCommand.TOPIC) == 0) {
					command.addTopic(value);
				} else if (key.compareTo(QueryCommand.CONFLICTS) == 0) {
					command.addConflicts(value);
				} else if (key.compareTo(QueryCommand.VISIBLETO) == 0) {
					command.addVisibleTo(value);
				} else if (key.compareTo(QueryCommand.ISVISIBLE) == 0) {
					command.addVisible();
				} else if (key.compareTo(QueryCommand.STARREDBY) == 0) {
					command.addStarredBy(value);
				} else if (key.compareTo(QueryCommand.WATCHEDBY) == 0) {
					command.addWatchedBy(value);
				} else if (key.compareTo(QueryCommand.DRAFTBY) == 0) {
					command.addDraftBy(value);
				} else if (key.compareTo(QueryCommand.LIMIT) == 0) {
					command.addLimit(Integer.parseInt(value));
				} else if (key.compareTo(QueryCommand.IS) == 0) {
					command.addIs(value);
				} else if (key.compareTo(QueryCommand.HAS) == 0) {
					command.addHas(value);
				} else if (key.compareTo(QueryCommand.TR) == 0) {
					command.addTr(value);
				} else if (key.compareTo(QueryCommand.CHANGE) == 0) {
					command.addChange(value);
				} else if (key.compareTo(QueryCommand.AGE) == 0) {
					command.addAge(value);
				} else if (key.compareTo(QueryCommand.AND) == 0) {
					command.addAnd(key);
				} else if (key.compareTo(QueryCommand.OR) == 0) {
					command.addOr(key);
				} else {
					throw new EGerritException("Gerrit command invalid: " + entryMap.getKey()); //$NON-NLS-1$
				}

			}
		}
	}

	private Map<String, String> parseQuery(String query) {
		Map<String, String> mapQuery = new LinkedHashMap<String, String>();
		Map<String, String> mapParsing = new LinkedHashMap<String, String>();
		query = initialFreeTextParsing(query, mapParsing);
		if (!query.isEmpty()) {
			//Map all others keywords
			String[] queryArray = query.split(" "); //$NON-NLS-1$

			for (String element : queryArray) {
				if (!element.isEmpty()) {
					String[] value = element.split(":"); //$NON-NLS-1$
					for (int j = 0; j < value.length; j++) {
						if (j + 1 < value.length) {
							mapQuery.put(value[j], value[++j]);
						} else {
							mapQuery.put(value[j], ""); //$NON-NLS-1$
						}
					}
				}
			}
		}

		//Append the free parsing
		if (!mapParsing.isEmpty()) {
			mapQuery.putAll(mapParsing);
		}
		return mapQuery;
	}

	/**
	 * Take the initial query and store the parameters which could handle some free text and possibility having some
	 * spaces
	 *
	 * @param String
	 *            query
	 * @param Map
	 *            mapQuery
	 * @return String modified query which the free text removed and put into the map
	 */
	private String initialFreeTextParsing(String initialQuery, Map<String, String> mapQuery) {
		// Parse to filter the text with possible spaces in the value: topic, message, comment, visibleto
		String COMMENT = "comment:"; //$NON-NLS-1$
		String MESSAGE = "message:"; //$NON-NLS-1$
		String TOPIC = "topic:"; //$NON-NLS-1$
		String VISIBLETO = "visibleto:"; //$NON-NLS-1$
		String OWNER = "owner:"; //$NON-NLS-1$
		String SHORTOWNER = "o:"; //$NON-NLS-1$
		String REVIEWER = "reviewer:"; //$NON-NLS-1$
		String SHORTREVIEWER = "r:"; //$NON-NLS-1$

		if (initialQuery.contains(TOPIC)) {
			initialQuery = adjustQuery(initialQuery, mapQuery, TOPIC);
		}
		if (initialQuery.contains(MESSAGE)) {
			initialQuery = adjustQuery(initialQuery, mapQuery, MESSAGE);
		}
		if (initialQuery.contains(COMMENT)) {
			initialQuery = adjustQuery(initialQuery, mapQuery, COMMENT);
		}
		if (initialQuery.contains(VISIBLETO)) {
			initialQuery = adjustQuery(initialQuery, mapQuery, VISIBLETO);
		}

		if (initialQuery.contains(OWNER)) {
			initialQuery = adjustQuery(initialQuery, mapQuery, OWNER);
		}
		if (initialQuery.contains(SHORTOWNER)) {
			initialQuery = adjustQuery(initialQuery, mapQuery, SHORTOWNER);
		}

		if (initialQuery.contains(REVIEWER)) {
			initialQuery = adjustQuery(initialQuery, mapQuery, REVIEWER);
		}
		if (initialQuery.contains(SHORTREVIEWER)) {
			initialQuery = adjustQuery(initialQuery, mapQuery, SHORTREVIEWER);
		}
		return initialQuery;
	}

	/**
	 * Store the selected string into a map with the associated value and return the query string
	 *
	 * @param initialQuery
	 * @param mapFreeTextQuery
	 * @param searchText
	 * @return
	 */
	private String adjustQuery(String initialQuery, Map<String, String> mapFreeTextQuery, String searchText) {
		String stringValue;
		int init = initialQuery.indexOf(searchText);
		stringValue = initialQuery.substring(init + searchText.length());
		stringValue = extractQueryValueString(stringValue);
		mapFreeTextQuery.put(searchText.substring(0, searchText.length() - 1), stringValue);

		//Remove the search text with its value from the next passing
		String tmp = searchText + stringValue;
		initialQuery = initialQuery.replaceFirst(tmp, "").trim();//Need to remove the last space at the beginning //$NON-NLS-1$
		return initialQuery;
	}

	/**
	 * Parse the string in parameter and return a modified one
	 *
	 * @param String
	 *            stringValue
	 * @return String
	 */
	private String extractQueryValueString(String stringValue) {
		String COLON = ":"; //$NON-NLS-1$
		if (stringValue.contains(COLON)) {
			Pattern p = Pattern.compile(COLON);
			String[] words = p.split(stringValue);
			if (words.length > 1) {
				//We have other data after, so need to rebuild it
				//Extract the last word
				int lastIndex = words[0].lastIndexOf(" ");//Keep string up to the last spaces //$NON-NLS-1$
				stringValue = stringValue.substring(0, lastIndex);
			}
		}
		return stringValue;
	}

	private void setRepositoryVersionLabel(String aRepo, String aVersion) {
		if (!fRepositoryVersionResulLabel.isDisposed()) {
			// e.g. "Eclipse.org Reviews - Gerrit 2.6.1"
			fRepositoryVersionResulLabel.setText(NLS.bind(Messages.GerritTableView_gerritLabel, aRepo, aVersion));
			fRepositoryVersionResulLabel.setToolTipText(Messages.GerritTableView_gerritLabelTooltip);
		}
	}

	private void setReviewsTotalResultLabel(String aSt) {
		if (!fReviewsTotalResultLabel.isDisposed()) {
			fReviewsTotalResultLabel.setText(aSt);
		}
	}

	private GerritServerInformation askUserToSelectRepo(List<GerritServerInformation> servers) {
		GerritServerInformation selection = null;
		SelectionDialog taskSelection = new SelectionDialog(fViewer.getTable().getShell(), servers);
		if (taskSelection.open() == Window.OK) {
			selection = taskSelection.getSelection();
		}
		return selection;
	}

}
