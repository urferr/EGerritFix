/*******************************************************************************
 * Copyright (c) 2013,2016 Ericsson and others
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Ericsson - initial API and implementation
 ******************************************************************************/

package org.eclipse.egerrit.internal.dashboard.ui.views;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CompletableFuture;

import org.apache.http.HttpStatus;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.egerrit.internal.core.EGerritCorePlugin;
import org.eclipse.egerrit.internal.core.GerritClient;
import org.eclipse.egerrit.internal.core.GerritCredentials;
import org.eclipse.egerrit.internal.core.GerritFactory;
import org.eclipse.egerrit.internal.core.GerritRepository;
import org.eclipse.egerrit.internal.core.GerritServerInformation;
import org.eclipse.egerrit.internal.core.ServersStore;
import org.eclipse.egerrit.internal.core.command.ChangeOption;
import org.eclipse.egerrit.internal.core.command.ChangeStatus;
import org.eclipse.egerrit.internal.core.command.QueryChangesCommand;
import org.eclipse.egerrit.internal.core.exception.EGerritException;
import org.eclipse.egerrit.internal.dashboard.core.GerritQueryException;
import org.eclipse.egerrit.internal.dashboard.ui.GerritUi;
import org.eclipse.egerrit.internal.dashboard.ui.completion.SearchContentProposalAdapter;
import org.eclipse.egerrit.internal.dashboard.ui.completion.SearchContentProposalProvider;
import org.eclipse.egerrit.internal.dashboard.ui.model.UIReviewTable;
import org.eclipse.egerrit.internal.dashboard.ui.preferences.Utils;
import org.eclipse.egerrit.internal.dashboard.ui.utils.SelectionDialog;
import org.eclipse.egerrit.internal.dashboard.ui.utils.UIUtils;
import org.eclipse.egerrit.internal.dashboard.utils.GerritServerUtility;
import org.eclipse.egerrit.internal.model.ChangeInfo;
import org.eclipse.egerrit.internal.model.ModelFactory;
import org.eclipse.egerrit.internal.model.Reviews;
import org.eclipse.egerrit.internal.ui.editors.ChangeDetailEditor;
import org.eclipse.egerrit.internal.ui.editors.model.ChangeDetailEditorInput;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;
import org.osgi.framework.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// need our own to handle query result

/**
 * The gerrit dashboard
 */

public class GerritTableView extends ViewPart {
	private static Logger logger = LoggerFactory.getLogger(GerritTableView.class);

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String VIEW_ID = "org.eclipse.egerrit.dashboard.ui.views.GerritTableView"; //$NON-NLS-1$

	private static final int SEARCH_WIDTH = 350;

	//Numbers of menu items in the Search pulldown menu; SEARCH_SIZE_MENU_LIST + 1 will be the max
	private static final int SEARCH_SIZE_MENU_LIST = 4;

	private static final String TITLE = "Gerrit Server "; //$NON-NLS-1$

	private static final String CHECKED_IMAGE = "personSignIn.png"; //$NON-NLS-1$

	private static final String ANONYMOUS_IMAGE = "personAnonymous.png"; //$NON-NLS-1$

	private static final String INVALID_IMAGE = "personInvalid.png"; //$NON-NLS-1$

	private static final String JOB_FAMILY = "DASHBOARD_UI"; //$NON-NLS-1$

	private AddOneServerDialog fAddOneServerDialog = null;

	// For the images

	private static ImageRegistry fImageRegistry = new ImageRegistry();

	static {

		String iconPath = "icons/view16/"; //$NON-NLS-1$

		fImageRegistry.put(CHECKED_IMAGE, GerritUi.getImageDescriptor(iconPath + CHECKED_IMAGE));
		fImageRegistry.put(ANONYMOUS_IMAGE, GerritUi.getImageDescriptor(iconPath + ANONYMOUS_IMAGE));
		fImageRegistry.put(INVALID_IMAGE, GerritUi.getImageDescriptor(iconPath + INVALID_IMAGE));

	}

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	private Composite fTopComposite = null;

	private GerritServerInformation defaultServerInfo = null;

	private static GerritTableView rtv = null;

	private CLabel fRepositoryVersionResulLabel;

	private Label fReviewsTotalLabel;

	private Combo fSearchTextBox;

	private Button fSearchRequestBtn;

	private Set<String> fRequestList = new LinkedHashSet<>();

	private TableViewer fViewer;

	private GerritServerUtility fServerUtil = GerritServerUtility.getInstance();

	private List<GerritServerInformation> fMapRepoServer = null;

	private Action doubleClickAction;

	private final LinkedHashSet<Job> fJobs = new LinkedHashSet<>();

	private GerritClient gerritClient = null;

	private Composite parentComposite;

	private Composite searchSection;

	private UIReviewTable reviewTable;

	private SearchContentProposalProvider searchProposalProvider = new SearchContentProposalProvider(() -> {
		initializeDefaultServer();
		if (defaultServerInfo != null) {
			connectToServer(defaultServerInfo);
		}
	});

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

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#dispose()
	 */
	@Override
	public void dispose() {
		cleanJobs();
		if (reviewTable != null) {
			reviewTable.dispose();
		}
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
	 * Refresh the table and other fields that show information related to what is in the table
	 */
	private void refresh(Reviews reviews) {
		Display.getDefault().asyncExec(() -> {
			if (!fViewer.getTable().isDisposed()) {
				fViewer.setInput(reviews);

				//Refresh the counter
				setReviewsTotalResultLabel(Integer.toString(reviews.getAllReviews().size()));
				searchSection.layout(true);
			}
		});
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createPartControl(Composite aParent) {
		parentComposite = aParent;
		if (fServerUtil.getLastSavedGerritServer() == null) {
			createEmptyPage(ServersStore.getAllServers().isEmpty());
		} else {
			createReviewList(null);
		}
	}

	private void createEmptyPage(boolean enterNewServer) {
		removeExistingWidgets();
		Color background = parentComposite.getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND);

		Composite composite = new Composite(parentComposite, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));

		composite.setBackground(background);

		Link link = new Link(composite, SWT.NONE);
		if (enterNewServer) {
			link.setText(Messages.Welcome_No_Server);
			link.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					addOrChangeServerThenLoad();
				}
			});
		} else {
			link.setText(Messages.Welcome_Pick_Server);
			link.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					GerritServerInformation server = askUserToSelectRepo(ServersStore.getAllServers());
					if (server != null) {
						fServerUtil.saveLastGerritServer(server);
						processCommands(ChangeStatus.OPEN.getValue());
					}
				}
			});
		}
		link.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, true, false));
		link.setBackground(background);
	}

	private void createReviewList(String[] voteColumns) {
		String[] reviewVoteColumns;
		if (voteColumns == null) {
			reviewVoteColumns = new String[0];
		} else {
			reviewVoteColumns = voteColumns.clone();
		}
		removeExistingWidgets();
		fTopComposite = new Composite(parentComposite, SWT.NONE);
		fTopComposite.setLayout(new GridLayout(1, true));

		searchSection = createSearchSection(fTopComposite);
		searchSection.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		createTable(reviewVoteColumns);
	}

	private void createTable(String[] voteColumns) {
		if (fViewer != null) {
			//Destroy the table before creating a new one
			fViewer.getControl().dispose();
			fViewer = null;
		}
		Display.getDefault().syncExec(() -> {

			reviewTable = new UIReviewTable();
			reviewTable.createTableViewerSection(fTopComposite, voteColumns)
					.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

			fViewer = reviewTable.getViewer();

			getSite().setSelectionProvider(fViewer);
			makeActions();
			hookDoubleClickAction();

			parentComposite.layout(true); //Here we force a re-layout
			VoteToolTipHandler voteHandler = new VoteToolTipHandler(fTopComposite.getShell(), rtv);
			voteHandler.connect();
		});

	}

	private void removeExistingWidgets() {
		for (Control control : parentComposite.getChildren()) {
			control.dispose();
		}
	}

	/**
	 * Create a group to show the search command and a search text
	 *
	 * @param Composite
	 *            aParent
	 */
	private Composite createSearchSection(Composite aParent) {
		final Composite searchComposite = new Composite(aParent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 4;
		layout.marginTop = 0;
		layout.marginHeight = 0;
		layout.verticalSpacing = 0;
		layout.makeColumnsEqualWidth = false;
		searchComposite.setLayout(layout);

		//Label to display the repository and the version
		fRepositoryVersionResulLabel = new CLabel(searchComposite, SWT.LEFT_TO_RIGHT);
		GridData labelGridData = new GridData(SWT.LEFT, SWT.CENTER, false, false);
		fRepositoryVersionResulLabel.setLayoutData(labelGridData);
		fRepositoryVersionResulLabel.addMouseListener(connectToServerListener());

		//Label to display Total reviews
		fReviewsTotalLabel = new Label(searchComposite, SWT.NONE);
		GridData totalLabelGridData = new GridData(SWT.LEFT, SWT.CENTER, false, false);
		totalLabelGridData.horizontalIndent = 10;
		fReviewsTotalLabel.setLayoutData(totalLabelGridData);

		//Create a SEARCH text data entry
		fSearchTextBox = new Combo(searchComposite, SWT.NONE);
		// Create a content proposal for the search box
		new SearchContentProposalAdapter(fSearchTextBox, searchProposalProvider);

		GridData searchGridData = new GridData(SWT.RIGHT, SWT.CENTER, true, false);
		searchGridData.widthHint = SEARCH_WIDTH;
		searchGridData.minimumWidth = 100;
		fSearchTextBox.setLayoutData(searchGridData);
		fSearchTextBox.setToolTipText(Messages.GerritTableView_tooltipSearch);
		//Get the last saved commands
		fRequestList = fServerUtil.getListLastCommands();
		setSearchText(""); //$NON-NLS-1$

		fSearchTextBox.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				Object obj = e.getSource();
				if (obj instanceof Combo) {
					Combo combo = (Combo) obj;
					//Always let this request go through, even if the user
					//is requesting the same search as before; this gives
					//another way to refresh the dashboard.
					processCommands(combo.getText());
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				//Handle the CR in the search text
				if (fSearchTextBox.getText().trim().length() > 0) {
					processCommands(fSearchTextBox.getText());
				}
			}
		});

		//Create a SEARCH button
		fSearchRequestBtn = new Button(searchComposite, SWT.NONE);
		fSearchRequestBtn.setText(Messages.GerritTableView_search);
		fSearchRequestBtn.setLayoutData(new GridData(SWT.RIGHT, SWT.NONE, false, false));
		fSearchRequestBtn.addListener(SWT.Selection, event -> processCommands(fSearchTextBox.getText()));

		adaptSearchSectionLayout(searchComposite);
		return searchComposite;
	}

	private void adaptSearchSectionLayout(final Composite composite) {
		composite.addControlListener(new ControlListener() {
			boolean searchOnly = false;

			@Override
			public void controlResized(ControlEvent e) {
				Rectangle size = composite.getClientArea();
				if (size.width > 300 && searchOnly) {
					//Show all widgets
					((GridData) fRepositoryVersionResulLabel.getLayoutData()).exclude = false;
					((GridData) fReviewsTotalLabel.getLayoutData()).exclude = false;
					((GridData) fSearchRequestBtn.getLayoutData()).exclude = false;
					fRepositoryVersionResulLabel.setVisible(true);
					fReviewsTotalLabel.setVisible(true);
					fSearchRequestBtn.setVisible(true);
					searchOnly = false;
					composite.layout(true);
					return;
				}
				if (size.width < 300 && !searchOnly) {
					//Hide all widgets except the search drop down
					((GridData) fRepositoryVersionResulLabel.getLayoutData()).exclude = true;
					((GridData) fReviewsTotalLabel.getLayoutData()).exclude = true;
					((GridData) fSearchRequestBtn.getLayoutData()).exclude = true;
					fRepositoryVersionResulLabel.setVisible(false);
					fReviewsTotalLabel.setVisible(false);
					fSearchRequestBtn.setVisible(false);
					searchOnly = true;
					composite.layout(true);
					return;
				}
			}

			@Override
			public void controlMoved(ControlEvent e) {

			}
		});
	}

	/**
	 * This method is the listener to let user connect to a server
	 *
	 * @return MouseAdapter
	 */
	private MouseAdapter connectToServerListener() {
		return new MouseAdapter() {

			@Override
			public void mouseDown(MouseEvent e) {
				if ((gerritClient == null) || (gerritClient.getRepository().getServerInfo().isAnonymous())) {
					addOrChangeServerThenLoad();
				}
			}
		};
	}

	private void makeActions() {
		doubleClickAction = new Action() {
			@Override
			public void run() {

				// -------------------------------------------------
				// Open an editor on the provided server and changeInfo
				// -------------------------------------------------

				ISelection selection = fViewer.getSelection();
				if (!(selection instanceof IStructuredSelection)) {
					return;
				}
				IStructuredSelection structuredSelection = (IStructuredSelection) selection;
				verifySelectionToOpen(structuredSelection);

			}

			/**
			 * @param structuredSelection
			 */
			private void verifySelectionToOpen(IStructuredSelection structuredSelection) {
				Iterator<?> selections = structuredSelection.iterator();
				while (selections.hasNext()) {
					Object element = selections.next();
					if (element instanceof ChangeInfo) {
						openEditorWithInfo((ChangeInfo) element);
					}

				}
			}

			/**
			 * @param changeInfo
			 */
			private void openEditorWithInfo(ChangeInfo changeInfo) {
				IWorkbenchPage page = getActivePage();

				if (page != null) {
					try {
						IEditorInput input = new ChangeDetailEditorInput(gerritClient, changeInfo);
						IEditorPart reusedEditor = page.findEditor(input);
						page.openEditor(input, ChangeDetailEditor.EDITOR_ID);
						if (reusedEditor instanceof ChangeDetailEditor) {
							((ChangeDetailEditor) reusedEditor).refreshStatus();
						}
					} catch (PartInitException e) {
						EGerritCorePlugin.logError(gerritClient != null
								? gerritClient.getRepository().formatGerritVersion() + e.getMessage()
								: e.getMessage());
					}
				}
			}

			/**
			 * @return
			 */
			private IWorkbenchPage getActivePage() {
				IWorkbench workbench = GerritUi.getDefault().getWorkbench();
				IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
				IWorkbenchPage page = null;
				if (window != null) {
					page = workbench.getActiveWorkbenchWindow().getActivePage();
				}
				return page;
			}
		};
	}

	private void hookDoubleClickAction() {
		fViewer.addDoubleClickListener(event -> doubleClickAction.run());
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	@Override
	public void setFocus() {
		parentComposite.setFocus();
	}

	public TableViewer getTableViewer() {
		return fViewer;
	}

	public static GerritTableView getActiveView(boolean forceOpen) {
		IViewPart viewPart = null;
		if (rtv != null) {
			return rtv;
		} else {
			if (!forceOpen) {
				return null;
			}
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
					logger.debug("getActiveView() SHOULD (JUST) CREATED A NEW Table:" + viewPart); //$NON-NLS-1$
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
				page.showView(VIEW_ID);
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
		logger.debug("Process command :   " + aQuery); //$NON-NLS-1$
		defaultServerInfo = null;
		String httpQuery = handleHttpInQuery(aQuery);
		initializeDefaultServer();

		//We should have a Gerrit Server here, otherwise, the user need to define one

		if (defaultServerInfo == null) {
			UIUtils.showNoServerMessage();
			//Only one instance of the pop-up
			if (fAddOneServerDialog == null) {
				fAddOneServerDialog = new AddOneServerDialog();
				Display.getDefault().syncExec(() -> fAddOneServerDialog.promptForNewServer(true));
				defaultServerInfo = fAddOneServerDialog.getServer();
				fAddOneServerDialog = null; //reset the instance
				if (defaultServerInfo == null) {
					logger.debug("No new server entered by the user. "); //$NON-NLS-1$
					return;
				}
			}
		}

		//At this point we have a server, execute the query if we can
		if (httpQuery != null && !"".equals(httpQuery)) { //$NON-NLS-1$
			updateTable(defaultServerInfo, httpQuery);
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
		String latestQuery = aQuery;
		ChangeIdExtractor extractedData = new ChangeIdExtractor(aQuery);
		if (extractedData.getServer() != null) {
			selectServer(extractedData.getServer());
			if (extractedData.getChangeId() != null) {
				latestQuery = "change:" + extractedData.getChangeId(); //$NON-NLS-1$
			} else {
				latestQuery = ChangeStatus.OPEN.getValue();
			}
		}
		return latestQuery;
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
		if (matches.isEmpty()) {
			knownServers.add(searched);
			ServersStore.saveServers(knownServers);
			matches.add(searched);
		}

		return matches;
	}

	/**
	 * Process the command to set the Starred flag on the Gerrit server String taskId boolean starred
	 *
	 * @return void
	 * @throws CoreException
	 */
	public void setStarred(ChangeInfo changeInfo, boolean starred) throws CoreException {
		if (defaultServerInfo == null) {
			UIUtils.showNoServerMessage();
		} else {
			if (starred) {
				CompletableFuture.runAsync(() -> {
					try {
						gerritClient.starChange(changeInfo).call();
					} catch (EGerritException e) {
						EGerritCorePlugin.logInfo(gerritClient.getRepository().formatGerritVersion() + e.getMessage());
					}
				});

			} else {
				CompletableFuture.runAsync(() -> {
					try {
						gerritClient.unstarChange(changeInfo).call();
					} catch (EGerritException e) {
						EGerritCorePlugin.logError(gerritClient.getRepository().formatGerritVersion() + e.getMessage());
					}
				});
			}
		}
	}

	/**
	 * Find the last Gerrit server being used , otherwise consider the Eclipse.org gerrit server version as a default
	 *
	 * @return Version
	 */
	private Version getlastGerritServerVersion() {
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
		if (version != null && version.getMajor() >= 2 && version.getMinor() < 5) {
			ret = true;
		}
		return ret;
	}

	/**
	 * @param server
	 * @param aQueryType
	 */
	private void updateTable(final GerritServerInformation server, final String aQueryType) {
		createReviewList(null);
		String cmdMessage = NLS.bind(Messages.GerritTableView_commandMessage, server.getServerURI(), aQueryType);
		final Job job = new Job(cmdMessage) {

			@Override
			public boolean belongsTo(Object aFamily) {
				return JOB_FAMILY.equals(aFamily);
			}

			@Override
			public IStatus run(final IProgressMonitor aMonitor) {

				// If there is only have one Gerrit server, we can proceed as if it was already used before
				IStatus status = null;
				try {
					status = getReviews(server, aQueryType);
					if (status.isOK()) {
						Display.getDefault().syncExec(() -> setSearchText(aQueryType));
					}
					status = Status.OK_STATUS;
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

	}

	//Display a query string in the search text.
	private void setSearchText(String aSt) {
		if (!fSearchTextBox.isDisposed()) {
			if (aSt != null && aSt != "") { //$NON-NLS-1$
				String[] ar = fSearchTextBox.getItems();
				int index = findSearchBoxIndexCombo(aSt, ar);

				removeElementSearchComboList(ar, index);
				//Add the new text in the combo
				fRequestList.add(aSt.trim());
				//Save the list of commands in file
				fServerUtil.saveLastCommandList(fRequestList);
			}

			adjustSearchBox(aSt);
		}
	}

	/**
	 * @param ar
	 * @param index
	 */
	private void removeElementSearchComboList(String[] ar, int index) {
		if (index != -1) {
			fRequestList.remove(ar[index]);
		} else {
			//Remove the oldest element from the list
			if (fRequestList.size() > SEARCH_SIZE_MENU_LIST) {
				Object obj = fRequestList.iterator().next(); //Should be the first item in the list
				fRequestList.remove(obj);
			}
		}
	}

	/**
	 * @param aSt
	 * @param index
	 * @param ar
	 * @return
	 */
	private int findSearchBoxIndexCombo(String aSt, String[] ar) {
		int index = -1;
		for (int i = 0; i < ar.length; i++) {
			if (ar[i].equals(aSt.trim())) {
				index = i;
				break;
			}
		}
		return index;
	}

	/**
	 * @param aSt
	 */
	private void adjustSearchBox(String aSt) {
		fSearchTextBox.setItems(reverseOrder(fRequestList.toArray(new String[0])));
		if (aSt != null && aSt != "") { //$NON-NLS-1$
			fSearchTextBox.select(0);
		} else {
			//Leave the text empty
			fSearchTextBox.setText(""); //$NON-NLS-1$
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

	private void displayInformation(final String server, final String st) {
		Display.getDefault().syncExec(
				() -> MessageDialog.openInformation(null, NLS.bind(Messages.GerritTableView_information, server), st));
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
		if (!connectToServer(repository)) {
			return Status.CANCEL_STATUS;
		}
		if (!isQueryAuthorized(queryType)) {
			displayInformation(repository.getServerURI(),
					NLS.bind(Messages.GerritTableView_informationAnonymous, queryType));
			return Status.CANCEL_STATUS;
		}

		// Format the query id
		String queryString = queryType;
		IStatus ret = Status.OK_STATUS;
		Reviews shownReviews = ModelFactory.eINSTANCE.createReviews();
		if (gerritClient != null) {
			// Fetch the list of reviews and pre-populate the table
			ChangeInfo[] loadedReviews = getReviewList(queryString);

			// Get the name of the columns for the votes and setup the table
			String[] columns = extractColumnNames(loadedReviews);
			Display.getDefault().syncExec(() -> createReviewList(columns));

			if (loadedReviews != null) {
				for (ChangeInfo review : loadedReviews) {
					shownReviews.getAllReviews().add(review);
				}
			} else {
				shownReviews.getAllReviews().clear();
				ret = new Status(IStatus.ERROR, EGerritCorePlugin.PLUGIN_ID, "Error"); //$NON-NLS-1$
			}
			setRepositoryVersionLabel(defaultServerInfo.getName(),
					gerritClient.getRepository().getVersion().toString());
		} else {
			//Reset the list to prevent bad request
			shownReviews.getAllReviews().clear();
			fServerUtil.resetLastGerritServer();
			setRepositoryVersionLabel(Messages.Invalid_server, Messages.No_Connection);
			ret = new Status(IStatus.ERROR, EGerritCorePlugin.PLUGIN_ID, "Error"); //$NON-NLS-1$
		}
		if (shownReviews.getAllReviews() != null) {
			refresh(shownReviews);
		}
		return ret;
	}

	private boolean connectToServer(GerritServerInformation repository) {
		URI uri = null;
		try {
			if (!repository.getServerURI().isEmpty()) {
				uri = new URI(repository.getServerURI());
			}
		} catch (URISyntaxException e) {
			EGerritCorePlugin.logError(gerritClient != null
					? gerritClient.getRepository().formatGerritVersion() + e.getMessage()
					: e.getMessage());
		}

		if (uri != null) {
			String scheme = uri.getScheme();
			String host = uri.getHost();
			int port = uri.getPort();
			String path = uri.getPath();
			String user = repository.getUserName();
			String passwd = repository.getPassword();
			GerritCredentials creds = new GerritCredentials(user, passwd);
			// Initialize
			GerritRepository gerritRepository = new GerritRepository(scheme, host, port, path);
			gerritRepository.setCredentials(creds);
			gerritRepository.setServerInfo(repository);
			gerritRepository.acceptSelfSignedCerts(repository.getSelfSigned());

			gerritClient = gerritRepository.instantiateGerrit();
			searchProposalProvider.setGerritClient(gerritClient);

			boolean connect = gerritRepository.connect();
			if (connect) {
				return showVersionError(gerritRepository);
			} else if (gerritRepository.getStatus() == HttpStatus.SC_UNAUTHORIZED) {
				UIUtils.showErrorDialog(Messages.Server_connection_401_title,
						NLS.bind(Messages.Server_connection_401, gerritRepository.getPath()));
				return false;
			}
			return connect;
		}
		return false;
	}

	/**
	 * @param gerritRepository
	 */
	private boolean showVersionError(GerritRepository gerritRepository) {
		Version version = gerritRepository.getVersion();

		if (version == null) {
			UIUtils.showErrorDialog(Messages.Invalid_Credentials, "Server " + defaultServerInfo.getServerURI()); //$NON-NLS-1$
			return false;
		} else if (version.equals(GerritRepository.NO_VERSION)) {
			UIUtils.showErrorDialog(Messages.Unsupported_server_version_title,
					NLS.bind(Messages.Unsupported_server_version, GerritFactory.MINIMAL_VERSION));
			return false;
		} else if (version.compareTo(GerritFactory.MINIMAL_VERSION) < 0) {
			UIUtils.showErrorDialog(Messages.Unsupported_Server_Version,
					NLS.bind(Messages.Unsupported_server_version, GerritFactory.MINIMAL_VERSION));
			return false;
		}
		return true;
	}

	private ChangeInfo[] getReviewList(String aQuery) throws GerritQueryException {
		ChangeInfo[] reviews = null;
		try {
			reviews = performQuery(aQuery, new NullProgressMonitor());
		} catch (MalformedURLException e) {
			EGerritCorePlugin.logError(gerritClient != null
					? gerritClient.getRepository().formatGerritVersion() + e.getMessage()
					: e.getMessage());

		}
		return reviews;
	}

	private ChangeInfo[] performQuery(String query, IProgressMonitor monitor) throws MalformedURLException {
		try {

			monitor.beginTask("Executing query", IProgressMonitor.UNKNOWN); //$NON-NLS-1$

			ChangeInfo[] res = null;
			QueryChangesCommand command = gerritClient.queryChanges();
			command.addOption(ChangeOption.LABELS);
			command.addOption(ChangeOption.DETAILED_LABELS);
			command.addOption(ChangeOption.DETAILED_ACCOUNTS);
			command.addOption(ChangeOption.MESSAGES);
			command.addOption(ChangeOption.CURRENT_REVISION);
			command.addOption(ChangeOption.CURRENT_COMMIT);
			command.addOption(ChangeOption.CURRENT_ACTIONS);

			try {
				command.addQuery(query);
				res = command.call();
			} catch (EGerritException e) {
				Utils.displayInformation(null, TITLE, e.getLocalizedMessage());
			}
			return res;
		} finally {
			monitor.done();
		}
	}

	private boolean isQueryAuthorized(String query) {
		if (!query.isEmpty()) {
			if (gerritClient.getRepository().getServerInfo().isAnonymous() && (query.contains(":self") //$NON-NLS-1$
					|| query.contains(":owner") || query.contains("is:reviewer") || query.contains("is:starred") //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
					|| query.contains(":draft"))) { //$NON-NLS-1$
				return false;
			}
		}
		return true;
	}

	private void setRepositoryVersionLabel(String aRepo, String aVersion) {
		Display.getDefault().asyncExec(() -> {
			if (!fRepositoryVersionResulLabel.isDisposed()) {
				// e.g. "Eclipse.org Reviews - Gerrit 2.6.1"
				fRepositoryVersionResulLabel.setText(aRepo);
				if (gerritClient == null) {
					fRepositoryVersionResulLabel.setImage(fImageRegistry.get(INVALID_IMAGE));
					fRepositoryVersionResulLabel.setToolTipText(Messages.GerritTableView_tooltipInvalid);
				} else if (gerritClient.getRepository().getServerInfo().isAnonymous()) {
					fRepositoryVersionResulLabel.setImage(fImageRegistry.get(ANONYMOUS_IMAGE));
					fRepositoryVersionResulLabel.setToolTipText(Messages.GerritTableView_tooltipAnonymous + '\n'
							+ NLS.bind(Messages.GerritTableView_gerritVersion, aVersion));
				} else {
					fRepositoryVersionResulLabel.setImage(fImageRegistry.get(CHECKED_IMAGE));
					fRepositoryVersionResulLabel.setToolTipText(NLS.bind(Messages.GerritTableView_tooltipLoggedOnAs,
							gerritClient.getRepository().getServerInfo().getUserName()) + '\n'
							+ NLS.bind(Messages.GerritTableView_gerritVersion, aVersion));
				}
			}
		});

	}

	private void setReviewsTotalResultLabel(String aSt) {
		if (!fReviewsTotalLabel.isDisposed()) {
			fReviewsTotalLabel.setText(Messages.GerritTableView_totalReview + aSt);
		}
	}

	private GerritServerInformation askUserToSelectRepo(List<GerritServerInformation> servers) {
		GerritServerInformation selection = null;
		SelectionDialog taskSelection = new SelectionDialog(null, servers);
		if (taskSelection.open() == Window.OK) {
			selection = taskSelection.getSelection();
		}

		if (selection != null) {
			createReviewList(null);
		}
		return selection;
	}

	private void addOrChangeServerThenLoad() {
		//Only one instance of the pop-up
		if (fAddOneServerDialog == null) {
			fAddOneServerDialog = new AddOneServerDialog();
			fAddOneServerDialog.promptToModifyServer(defaultServerInfo, true);
			GerritServerInformation serverInfo = fAddOneServerDialog.getServer();
			fAddOneServerDialog = null; //Reset the instance
			if (serverInfo == null) {
				logger.debug("No new server entered by the user."); //$NON-NLS-1$
				return;
			}
			defaultServerInfo = serverInfo; //Set the default server with the latest value

			if (fSearchTextBox == null || fSearchTextBox.getText().isEmpty()) {
				processCommands(ChangeStatus.OPEN.getValue());
			} else {
				processCommands(fSearchTextBox.getText());
			}
		}
	}

	/**
	 * Process a query to update the dashboard table.
	 */
	public void update() {
		rtv.processCommands(fSearchTextBox.getText());
	}

	/**
	 * Perform an update according to the parameter received
	 *
	 * @param query
	 */
	public void update(String query) {
		rtv.processCommands(query);
	}

	/**
	 * Get the defined GerritClient
	 *
	 * @return
	 */
	public GerritClient getGerritClient() {
		return gerritClient;
	}

	/**
	 * Reset the table to its initial value
	 */
	public void resetDefault() {
		reviewTable.resetDefault();
	}

	/**
	 * From the changeInfo, extract and sort the allowed labels
	 *
	 * @return
	 */
	private String[] extractColumnNames(ChangeInfo[] reviews) {
		TreeSet<String> allLabels = new TreeSet<>();
		if (reviews == null || reviews.length == 0) {
			return new String[0];
		}
		for (ChangeInfo changeinfo : reviews) {
			allLabels.addAll(changeinfo.getLabels().keySet());
		}
		return allLabels.toArray(new String[allLabels.size()]);
	}

	/**
	 * @return the UIReviewTable
	 */
	public UIReviewTable getReviewTable() {
		return reviewTable;
	}
}
