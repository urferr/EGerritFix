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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.egerrit.core.EGerritCorePlugin;
import org.eclipse.egerrit.core.Gerrit;
import org.eclipse.egerrit.core.GerritCredentials;
import org.eclipse.egerrit.core.GerritFactory;
import org.eclipse.egerrit.core.GerritRepository;
import org.eclipse.egerrit.core.command.ChangeOption;
import org.eclipse.egerrit.core.command.ChangeState;
import org.eclipse.egerrit.core.command.QueryChangesCommand;
import org.eclipse.egerrit.core.exception.EGerritException;
import org.eclipse.egerrit.core.rest.ChangeInfo;
import org.eclipse.egerrit.dashboard.core.GerritQuery;
import org.eclipse.egerrit.dashboard.core.GerritQueryException;
import org.eclipse.egerrit.dashboard.preferences.GerritServerInformation;
import org.eclipse.egerrit.dashboard.ui.GerritUi;
import org.eclipse.egerrit.dashboard.ui.internal.model.ReviewTableData;
import org.eclipse.egerrit.dashboard.ui.internal.model.UIReviewTable;
import org.eclipse.egerrit.dashboard.ui.internal.utils.SelectionDialog;
import org.eclipse.egerrit.dashboard.ui.internal.utils.UIUtils;
import org.eclipse.egerrit.dashboard.ui.preferences.Utils;
import org.eclipse.egerrit.dashboard.utils.GerritServerUtility;
import org.eclipse.egerrit.ui.editors.ChangeDetailEditor;
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

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	public static final String MY_CHANGES_STRING = "owner:self OR reviewer:self"; //$NON-NLS-1$

	public static final String MY_WATCHED_CHANGES_STRING = "is:watched status:open"; //$NON-NLS-1$

	public static final String ALL_OPEN_CHANGES_STRING = "status:open"; //$NON-NLS-1$

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

	private GerritRepository gerritRepository = null;

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

		// Listen on query results
//		TasksUiPlugin.getTaskList().addChangeListener(this);

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
					processCommands(GerritQuery.CUSTOM);

				}
			}
		});

		//Create a SEARCH button
		fSearchRequestBtn = new Button(rightSsearchForm, SWT.NONE);
		fSearchRequestBtn.setText(Messages.GerritTableView_search);
		fSearchRequestBtn.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				processCommands(GerritQuery.CUSTOM);
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
				// Open an editor with the detailed task information
				// -------------------------------------------------

				// Retrieve the single table selection ("the" task)
				ISelection selection = fViewer.getSelection();
				if //		if (taskRepository.getConnectorKind().equals(LocalRepositoryConnector.CONNECTOR_KIND)) {
//				getHeaderForm().getForm().setText(Messages.TaskEditor_Task_ + task.getSummary());
//				} else {
				(!(selection instanceof IStructuredSelection)) {
					return;
				}
				IStructuredSelection structuredSelection = (IStructuredSelection) selection;
				if (structuredSelection.size() != 1) {
					return;
				}
				Object element = structuredSelection.getFirstElement();
				if (element instanceof ChangeInfo) {
					System.err.println("Command deactivated HERE in GerritTableview.makeactions() Could use convertGerritInfoToTaskRepository () temporary");
					ChangeDetailEditor cDE = ChangeDetailEditor.getActiveEditor();

					cDE.setChangeInfo(gerritRepository, (ChangeInfo) element);
					IWorkbench workbench = GerritUi.getDefault().getWorkbench();
					IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
					IWorkbenchPage page = null;
					if (window != null) {
						page = workbench.getActiveWorkbenchWindow().getActivePage();
					}

					if (page != null) {
						try {
							page.openEditor(cDE.getEditorInput(), ChangeDetailEditor.EDITOR_ID);
						} catch (PartInitException e) {
							EGerritCorePlugin.logError(e.getMessage());
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
						GerritUi.Ftracer.traceWarning(e.getMessage());
					}
					GerritUi.Ftracer.traceWarning("getActiveView() SHOULD (JUST) CREATED A NEW Table:" + viewPart);

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
				GerritUi.Ftracer.traceWarning(e.getMessage());
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
		GerritUi.Ftracer.traceInfo("Process command :   " + aQuery);
		String lastSaved = fServerUtil.getLastSavedGerritServer();
		if (lastSaved != null) {
			//Already saved a Gerrit server, so use it
			defaultServerInfo = fServerUtil.getServerRepo(lastSaved);
		}

		if (defaultServerInfo == null) {
			//If we did not find the task Repository
			fMapRepoServer = GerritServerUtility.getInstance().getGerritMapping();
			//Verify How many gerrit server are defined
			if (fMapRepoServer.size() == 1) {
				for (GerritServerInformation key : fMapRepoServer) {
					//Save it for the next query time
					fServerUtil.saveLastGerritServer(key.getServerURI());
					break;
				}

			} else if (fMapRepoServer.size() > 1) {
				List<GerritServerInformation> listServerInfo = new ArrayList<GerritServerInformation>();
				for (GerritServerInformation key : fMapRepoServer) {
					listServerInfo.add(key);
				}
				defaultServerInfo = getSelectedRepositoryURL(listServerInfo);
				if (defaultServerInfo != null) {
					//Save it for the next query time
					fServerUtil.saveLastGerritServer(defaultServerInfo.getServerURI());
				}
			}
		}

		//We should have a Gerrit Server here, otherwise, the user need to define one
		if (defaultServerInfo == null) {
			UIUtils.showErrorDialog(Messages.GerritTableView_defineRepository,
					Messages.GerritTableView_noGerritRepository);
		} else {
			if (aQuery != null && !aQuery.equals("")) { //$NON-NLS-1$
				updateTable(defaultServerInfo, aQuery);
			}
		}
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
		String lastSaved = fServerUtil.getLastSavedGerritServer();

		if (lastSaved != null) {
			//Already saved a Gerrit server, so use it
			defaultServerInfo = fServerUtil.getServerRepo(lastSaved);
		}

		if (defaultServerInfo == null) {
			//If we did not find the task Repository
			fMapRepoServer = GerritServerUtility.getInstance().getGerritMapping();
			//Verify How many gerrit server are defined
			if (fMapRepoServer.size() == 1) {
				for (GerritServerInformation key : fMapRepoServer) {
					defaultServerInfo = key;
					//Save it for the next query time
					fServerUtil.saveLastGerritServer(key.getServerURI());
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
//					GerritUi.Ftracer.traceInfo("Selected version: " + version.toString()); //$NON-NLS-1$
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
	 * @param aTaskRepo
	 * @param aQueryType
	 * @return
	 */
	private Object updateTable(final GerritServerInformation aTaskRepo, final String aQueryType) {

		String cmdMessage = NLS.bind(Messages.GerritTableView_commandMessage, aTaskRepo.getServerURI(), aQueryType);
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
					fReviewTable.createReviewItem(aQueryType, aTaskRepo);
					status = getReviews(aTaskRepo, aQueryType);
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
								System.out.println("defaultServerInfo.getServerURL() is "
										+ defaultServerInfo.getServerURI());
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
//										GerritUi.Ftracer.traceError(e.getMessage());
//
//									}
//								}

							}
						});
					}
				} catch (GerritQueryException e) {
					status = e.getStatus();
					GerritUi.Ftracer.traceError(e.getMessage());

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
					GerritUi.Ftracer.traceInfo("Custom string: " + str[0]); //$NON-NLS-1$
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

//		IRepositoryModel repositoryModel = TasksUi.getRepositoryModel();
//		query = (RepositoryQuery) repositoryModel.createRepositoryQuery(convertGerritInfoToTaskRepository(repository));
//		query.setSummary(queryId);
//		query.setAttribute(GerritQuery.TYPE, queryType);
//		query.setAttribute(GerritQuery.PROJECT, null);
//		if (queryType == GerritQuery.CUSTOM) {
		if (queryType == "custom") { //$NON-NLS-1$
//			query.setAttribute(GerritQuery.QUERY_STRING, getSearchText());

			queryString = getSearchText();
		} else {
			String st = matchQueryTypeRequest(queryType);
//			query.setAttribute(GerritQuery.QUERY_STRING, st);
			queryString = st;

		}

//		if (query.getAttribute(GerritQuery.QUERY_STRING).isEmpty()) {
//			displayWarning(Messages.GerritTableView_warningEmptyValue);
//			return Status.CANCEL_STATUS;
//		}

		// Save query
//		fCurrentQuery = query;
		URI uri = null;
		try {
			uri = new URI(defaultServerInfo.getServerURI());
		} catch (URISyntaxException e) {
			EGerritCorePlugin.logError(e.getMessage());

		}
		String SCHEME = uri.getScheme();
		String HOST = uri.getHost();
		int PORT = uri.getPort();
		String PATH = uri.getPath();
		String USER = defaultServerInfo.getUserName();
		String PASSWORD = defaultServerInfo.getPassword();
		GerritCredentials creds = new GerritCredentials(USER, PASSWORD);
		// Initialize
		gerritRepository = new GerritRepository(SCHEME, HOST, PORT, PATH);
		gerritRepository.setCredentials(creds);
		gerritRepository.acceptSelfSignedCerts(defaultServerInfo.getSelfSigned());

		// Fetch the list of reviews and pre-populate the table
		ChangeInfo[] reviews = getReviewList(repository, queryString);

		fReviewTable.init(reviews);
		refresh();

		return Status.OK_STATUS;

	}

	/**
	 * We need to use the define in GerritQuery.java for the missing one
	 *
	 * @param queryType
	 * @return queryString
	 */
	private String matchQueryTypeRequest(String queryType) {
		if (queryType.equals(GerritQuery.ALL_OPEN_CHANGES)) {
			return ALL_OPEN_CHANGES_STRING;
		} else if (queryType.equals(GerritQuery.MY_CHANGES)) {
			return MY_CHANGES_STRING;
		} else if (queryType.equals(GerritQuery.MY_WATCHED_CHANGES)) {
			return MY_WATCHED_CHANGES_STRING;
		}
		return queryType;
	}

	private ChangeInfo[] getReviewList(GerritServerInformation repository, String aQuery) throws GerritQueryException {

		ChangeInfo[] reviews = null;

		try {
//			reviews = performQuery(convertGerritInfoToTaskRepository(repository), aQuery, new NullProgressMonitor());
			reviews = performQuery(aQuery, new NullProgressMonitor());
		} catch (MalformedURLException e) {
			EGerritCorePlugin.logError(e.getMessage());

		}

		return reviews;
	}

	//lmcgupe
//	public ChangeInfo[] performQuery(TaskRepository repository, String query, IProgressMonitor monitor)
	public ChangeInfo[] performQuery(String query, IProgressMonitor monitor) throws MalformedURLException {
		try {
			monitor.beginTask("Executing query", IProgressMonitor.UNKNOWN);

			Gerrit gerrit = null;
			try {
				gerrit = GerritFactory.create(gerritRepository);
			} catch (EGerritException e) {
				String message = "Server: " + gerritRepository.getURIBuilder(false) + "\n" + e.getLocalizedMessage();
				Utils.displayInformation(null, TITLE, message);
				EGerritCorePlugin.logError(e.getLocalizedMessage(), e);
			}

			ChangeInfo[] res = null;
			if (gerrit != null) {
				// Create query
				QueryChangesCommand command = gerrit.queryChanges();
				command.addOption(ChangeOption.LABELS);
				command.addOption(ChangeOption.CURRENT_REVISION);
				command.addOption(ChangeOption.CURRENT_FILES);
				command.addLimit(101);

				setQuery(query, command);

				try {
					res = command.call();
				} catch (EGerritException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				final String queryText = query;
				Display.getDefault().syncExec(new Runnable() {
					@Override
					public void run() {
						setRepositoryVersionLabel(defaultServerInfo.getName(), gerritRepository.getVersion().toString());
						fSearchRequestText.setText(queryText);

					}
				});
			}
			return res;
		} catch (UnsupportedClassVersionError e) {
//			return new Status(IStatus.ERROR, GerritCorePlugin.PLUGIN_ID, "error", e);
			return null;
		} finally {
			monitor.done();
		}
	}

	private void setQuery(String query, QueryChangesCommand command) {
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
			}

		}
	}

//	private List<GerritQueryResult> convert(List<com.google.gerrit.common.data.ChangeInfo> changes) {
//		List<GerritQueryResult> results = new ArrayList<GerritQueryResult>(changes.size());
//		for (com.google.gerrit.common.data.ChangeInfo changeInfo : changes) {
//			GerritQueryResult result = new GerritQueryResult(changeInfo);
//			results.add(result);
//		}
//		return results;
//	}

// lmcgupe

//Temporary converting method
//	private TaskRepository convertGerritInfoToTaskRepository(GerritServerInformation gerritServerInfo) {
//		TaskRepository taskrepository = new TaskRepository(CONNECTOR_KIND, gerritServerInfo.getServerURL());
//		taskrepository.setAuthenticationCredentials(gerritServerInfo.getUserName(), gerritServerInfo.getPassword());
//		return taskrepository;
//	}

// ------------------------------------------------------------------------
// ITaskListChangeListener
// ------------------------------------------------------------------------

/* (non-Javadoc)
 * @see org.eclipse.mylyn.internal.tasks.core.ITaskListChangeListener#containersChanged(java.util.Set)
 */
//	@Override
//	public void containersChanged(final Set<TaskContainerDelta> deltas) {
//		for (TaskContainerDelta taskContainerDelta : deltas) {
//			IRepositoryElement element = taskContainerDelta.getElement();
//			switch (taskContainerDelta.getKind()) {
//			case ROOT:
//				refresh();
//				break;
//			case ADDED:
//			case CONTENT:
//				if (element != null && element instanceof ChangeInfo) {
//					updateReview((ChangeInfo) element);
//				}
//				refresh();
//				break;
//			case DELETED:
//			case REMOVED:
//				if (element != null && element instanceof ChangeInfo) {
//					deleteReview((ChangeInfo) element);
//				}
//				refresh();
//				break;
//			default:
//				break;
//			}
//		}
//	}
//
//	/**
//	 * Delete a review
//	 */
//	private synchronized void deleteReview(ChangeInfo task) {
//		fReviewTable.deleteReviewItem(task.getChangeId());
//	}
//
//	/**
//	 * Add/update a review
//	 */
//	private synchronized void updateReview(ChangeInfo task) {
//		boolean ourQuery = task.getParentContainers().contains(fCurrentQuery);
//		if (ourQuery && !Strings.isNullOrEmpty(task.getSummary())) {
//			try {
//				TaskData taskData = fConnector.getTaskData(getTaskRepository(), task.getTaskId(),
//						new NullProgressMonitor());
//				GerritTask gtask = new GerritTask(taskData);
//				if (gtask.getAttribute(GerritTask.DATE_COMPLETION) == null) {
//					fReviewTable.updateReviewItem(gtask);
//				}
//			} catch (CoreException e) {
//				StatusHandler.log(new Status(IStatus.ERROR, GerritCorePlugin.PLUGIN_ID, e.getMessage(), e));
//			}
//		}
//
//	}

	private void setRepositoryVersionLabel(String aRepo, String aVersion) {
		if (!fRepositoryVersionResulLabel.isDisposed()) {
			// e.g. "Eclipse.org Reviews - Gerrit 2.6.1"
			fRepositoryVersionResulLabel.setText(NLS.bind(Messages.GerritTableView_gerritLabel, aRepo, aVersion));
		}
	}

	private void setReviewsTotalResultLabel(String aSt) {
		if (!fReviewsTotalResultLabel.isDisposed()) {
			fReviewsTotalResultLabel.setText(aSt);
		}
	}

	private GerritServerInformation getSelectedRepositoryURL(
			final List<GerritServerInformation> listGerritServerInformation) {
		String selection = null;
		SelectionDialog taskSelection = new SelectionDialog(fViewer.getTable().getShell(), listGerritServerInformation);
		if (taskSelection.open() == Window.OK) {
			selection = taskSelection.getSelection();
		}
		return fServerUtil.getServerRepo(selection);
	}

}
