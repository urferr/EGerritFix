/*******************************************************************************
 * Copyright (c) 2013,2015 Ericsson
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

package org.eclipse.egerrit.internal.dashboard.ui.views;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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
import org.eclipse.egerrit.internal.core.command.QueryChangesCommand;
import org.eclipse.egerrit.internal.core.command.StarChangeCommand;
import org.eclipse.egerrit.internal.core.command.UnstarChangeCommand;
import org.eclipse.egerrit.internal.core.exception.EGerritException;
import org.eclipse.egerrit.internal.dashboard.core.GerritQuery;
import org.eclipse.egerrit.internal.dashboard.core.GerritQueryException;
import org.eclipse.egerrit.internal.dashboard.ui.GerritUi;
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
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
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
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
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

	//Numbers of menu items in the Search pulldown menu; SEARCH_SIZE_MENU_LIST + 1 will be the max
	private static final int SEARCH_SIZE_MENU_LIST = 4;

	private static final String ADJUST_MY_STARRED_COMMAND_ID = "org.eclipse.egerrit.dashboard.ui.adjustMyStarred"; //$NON-NLS-1$

	private final String TITLE = "Gerrit Server ";

	public static final String CHECKED_IMAGE = "personSignIn.png"; //$NON-NLS-1$

	public static final String ANONYMOUS_IMAGE = "personAnonymous.png"; //$NON-NLS-1$

	public static final String INVALID_IMAGE = "personInvalid.png"; //$NON-NLS-1$
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

	private GerritServerInformation defaultServerInfo = null;

	private static GerritTableView rtv = null;

	private CLabel fRepositoryVersionResulLabel;

	private Label fReviewsTotalLabel;

	private Combo fSearchRequestText;

	private Button fSearchRequestBtn;

	private Set<String> fRequestList = new LinkedHashSet<String>();

	private TableViewer fViewer;

	private GerritServerUtility fServerUtil = GerritServerUtility.getInstance();

	private List<GerritServerInformation> fMapRepoServer = null;

	private Action doubleClickAction;

	private final LinkedHashSet<Job> fJobs = new LinkedHashSet<Job>();

	private GerritClient gerritClient = null;

	private Composite parentComposite;

	private boolean listShown = false;

	private Composite searchSection;

	private UIReviewTable reviewTable;

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

	public void setGerritServerUtility(GerritServerUtility ServerUtil) {
		fServerUtil = ServerUtil;

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
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				if (!fViewer.getTable().isDisposed()) {
					fViewer.setInput(reviews);
					//Refresh the counter
					setReviewsTotalResultLabel(Integer.toString(reviews.getAllReviews().size()));
					searchSection.layout(true);
				}
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
			createEmptyPage(ServersStore.getAllServers().size() == 0);
		} else {
			createReviewList();
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
			link.setText("Welcome to the Gerrit dashboard. To get started, please configure a <a>Gerrit Server</a>.");
			link.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					addOrChangeServerThenLoad();
				}
			});
		} else {
			link.setText("Welcome to the Gerrit dashboard. <a>Select a server</a> you have already entered.");
			link.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					GerritServerInformation server = askUserToSelectRepo(ServersStore.getAllServers());
					if (server != null) {
						fServerUtil.saveLastGerritServer(server);
						processCommands("status:open");
					}
				}
			});
		}
		link.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, true, false));
		link.setBackground(background);
	}

	private void createReviewList() {
		if (listShown) {
			return;
		}
		removeExistingWidgets();
		Composite topComposite = new Composite(parentComposite, SWT.NONE);
		topComposite.setLayout(new GridLayout(1, true));

		searchSection = createSearchSection(topComposite);
		searchSection.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		reviewTable = new UIReviewTable();
		reviewTable.createTableViewerSection(topComposite).setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		fViewer = reviewTable.getViewer();

		makeActions();
//		hookContextMenu();
		hookDoubleClickAction();

		listShown = true;
		parentComposite.layout(true); //Here we force a re-layout
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
		fSearchRequestText = new Combo(searchComposite, SWT.NONE);
		GridData searchGridData = new GridData(SWT.RIGHT, SWT.CENTER, true, false);
		searchGridData.widthHint = SEARCH_WIDTH;
		searchGridData.minimumWidth = 100;
		fSearchRequestText.setLayoutData(searchGridData);
		fSearchRequestText.setToolTipText(Messages.GerritTableView_tooltipSearch);
		//Get the last saved commands
		fRequestList = fServerUtil.getListLastCommands();
		setSearchText(""); //$NON-NLS-1$

		fSearchRequestText.addSelectionListener(new SelectionListener() {

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
				if (fSearchRequestText.getText().trim().length() > 0) {
					processCommands(fSearchRequestText.getText());
				}
			}
		});

		//Create a SEARCH button
		fSearchRequestBtn = new Button(searchComposite, SWT.NONE);
		fSearchRequestBtn.setText(Messages.GerritTableView_search);
		fSearchRequestBtn.setLayoutData(new GridData(SWT.RIGHT, SWT.NONE, false, false));
		fSearchRequestBtn.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				processCommands(fSearchRequestText.getText());
			}
		});

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
								IEditorInput input = new ChangeDetailEditorInput(gerritClient, (ChangeInfo) element);
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
		parentComposite.setFocus();
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
					logger.debug("getActiveView() SHOULD (JUST) CREATED A NEW Table:" + viewPart);

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
		logger.debug("Process command :   " + aQuery); //$NON-NLS-1$
		defaultServerInfo = null;
		aQuery = handleHttpInQuery(aQuery);
		initializeDefaultServer();

		//We should have a Gerrit Server here, otherwise, the user need to define one

		if (defaultServerInfo == null) {
			UIUtils.showNoServerMessage();
			final AddOneServerDialog addOneServer = new AddOneServerDialog();
			Display.getDefault().syncExec(new Runnable() {
				@Override
				public void run() {
					addOneServer.promptForNewServer(true);
				}
			});
			defaultServerInfo = addOneServer.getServer();
			if (defaultServerInfo == null) {
				logger.debug("No new server entered by the user. ");
				return;
			}
		}

		//At this point we have a server, execute the query if we can
		if (aQuery != null && !aQuery.equals("")) { //$NON-NLS-1$
			updateTable(defaultServerInfo, aQuery);
		}
		String SCHEME = defaultServerInfo.getScheme();
		String HOST = defaultServerInfo.getHostId();
		int PORT = defaultServerInfo.getPort();
		String PATH = defaultServerInfo.getPath();
		String USER = defaultServerInfo.getUserName();
		String PASSWORD = defaultServerInfo.getPassword();
		GerritCredentials creds = new GerritCredentials(USER, PASSWORD);
		// Initialize
		GerritRepository gerritRepository = new GerritRepository(SCHEME, HOST, PORT, PATH);
		gerritRepository.setCredentials(creds);
		gerritRepository.setServerInfo(defaultServerInfo);
		gerritRepository.acceptSelfSignedCerts(defaultServerInfo.getSelfSigned());
		boolean connect = gerritRepository.connect();
		if (connect) {
			Version version = gerritRepository.getVersion();

			if (version == null) {
				UIUtils.showErrorDialog("Invalid Gerrit server and/or User/Password",
						"Server " + defaultServerInfo.getServerURI());
			} else if (version.equals(GerritRepository.NO_VERSION)) {
				UIUtils.showErrorDialog("Unsupported Gerrit server version",
						"The server you are connecting to is older than 2.8 and this tool can not connect to it. This tool can only connect to server that are more recent than "
								+ GerritFactory.MINIMAL_VERSION + ".");
			} else if (version.compareTo(GerritFactory.MINIMAL_VERSION) < 0) {
				UIUtils.showErrorDialog("Unsupported Gerrit server version",
						"Server " + gerritRepository.getPath() + " runs version " + version.toString()
								+ " which is older than the minimum " + GerritFactory.MINIMAL_VERSION
								+ " supported by EGerrit.");
			}

		} else if (gerritRepository.getStatus() == 401) {
			UIUtils.showErrorDialog("Cannot connect to selected Gerrit server ", //$NON-NLS-1$
					"Server " + gerritRepository.getPath() + " Authentication failure "); //$NON-NLS-1$ //$NON-NLS-2$

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
	public void setStarred(ChangeInfo changeInfo, boolean starred, IProgressMonitor progressMonitor)
			throws CoreException {
		if (defaultServerInfo == null) {
			UIUtils.showNoServerMessage();
		} else {
			if (starred) {
				StarChangeCommand starChangeCmd = gerritClient.starChange(changeInfo.get_number());
				try {
					starChangeCmd.call();
				} catch (EGerritException e1) {
					EGerritCorePlugin.logError(gerritClient.getRepository().formatGerritVersion() + e1.getMessage());
				}

			} else {
				UnstarChangeCommand unstarChangeCmd = gerritClient.unstarChange(changeInfo.get_number());
				try {
					unstarChangeCmd.call();
				} catch (EGerritException e1) {
					EGerritCorePlugin.logError(gerritClient.getRepository().formatGerritVersion() + e1.getMessage());
				}

			}
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
		createReviewList();
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
					status = getReviews(server, aQueryType);
					if (status.isOK()) {
						Display.getDefault().syncExec(new Runnable() {
							@Override
							public void run() {
								setSearchText(getSearchText());
							}
						});
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
		String queryString = queryType;

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
		IStatus ret = Status.OK_STATUS;
		if (uri != null) {
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
		}
		Reviews shownReviews = ModelFactory.eINSTANCE.createReviews();
		if (gerritClient != null) {
			// Fetch the list of reviews and pre-populate the table
			ChangeInfo[] loadedReviews = getReviewList(repository, queryString);
			if (loadedReviews != null) {
				for (ChangeInfo changeInfo : loadedReviews) {
					shownReviews.getAllReviews().add(changeInfo);
				}
			} else {
				shownReviews.getAllReviews().clear();
				ret = new Status(IStatus.ERROR, EGerritCorePlugin.PLUGIN_ID, "Error");
			}
		} else {
			//Reset the list to prevent bad request
			shownReviews.getAllReviews().clear();
			fServerUtil.resetLastGerritServer();

			Display.getDefault().syncExec(new Runnable() {
				@Override
				public void run() {
					setRepositoryVersionLabel("Invalid Server", "NO connection");
				}
			});
			ret = new Status(IStatus.ERROR, EGerritCorePlugin.PLUGIN_ID, "Error");

		}
		refresh(shownReviews);
		return ret;
	}

	private ChangeInfo[] getReviewList(GerritServerInformation repository, String aQuery) throws GerritQueryException {
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

	public ChangeInfo[] performQuery(String query, IProgressMonitor monitor) throws MalformedURLException {
		try {
			monitor.beginTask("Executing query", IProgressMonitor.UNKNOWN);

			ChangeInfo[] res = null;
			QueryChangesCommand command = gerritClient.queryChanges();
			command.addOption(ChangeOption.DETAILED_LABELS, ChangeOption.CURRENT_REVISION, ChangeOption.CURRENT_FILES,
					ChangeOption.DETAILED_ACCOUNTS, ChangeOption.CURRENT_COMMIT, ChangeOption.REVIEWED);

			try {
				setQuery(query, command);
				res = command.call();
			} catch (EGerritException e) {
				Utils.displayInformation(null, TITLE, e.getLocalizedMessage());
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
		} finally {
			monitor.done();
		}
	}

	private void setQuery(String query, QueryChangesCommand command) throws EGerritException {
		if (!query.isEmpty()) {
			if (gerritClient.getRepository().getServerInfo().isAnonymous() && (query.contains(":self") //$NON-NLS-1$
					|| query.contains(":owner") || query.contains("is:reviewer") || query.contains("is:starred"))) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				throw new EGerritException("The query \"" + query + "\" can not be executed by anonymous users.");
			}
			command.addQuery(query);
		}
	}

	private void setRepositoryVersionLabel(String aRepo, String aVersion) {
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
			fRepositoryVersionResulLabel.layout(true);
		}
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
			createReviewList();
		}
		return selection;
	}

	private void addOrChangeServerThenLoad() {
		final AddOneServerDialog addOneServer = new AddOneServerDialog();
		addOneServer.promptToModifyServer(defaultServerInfo, true);
		defaultServerInfo = addOneServer.getServer();
		if (defaultServerInfo == null) {
			logger.debug("No new server entered by the user.");
			return;
		}
		if (fSearchRequestText == null || fSearchRequestText.getText().isEmpty()) {
			processCommands("status:open");
		} else {
			processCommands(fSearchRequestText.getText());
		}
	}

	public void update() {
		rtv.processCommands(fSearchRequestText.getText());
	}

	/**
	 * Perform an update according to the parameter received
	 *
	 * @param query
	 */
	public void update(String query) {
		rtv.processCommands(query);
	}
}
