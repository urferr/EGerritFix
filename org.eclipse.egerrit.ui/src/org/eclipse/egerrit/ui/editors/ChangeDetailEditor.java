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
 *
 *******************************************************************************/

package org.eclipse.egerrit.ui.editors;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.eclipse.compare.CompareUI;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.property.Properties;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.egerrit.core.EGerritCorePlugin;
import org.eclipse.egerrit.core.Gerrit;
import org.eclipse.egerrit.core.GerritFactory;
import org.eclipse.egerrit.core.GerritRepository;
import org.eclipse.egerrit.core.command.ChangeOption;
import org.eclipse.egerrit.core.command.GetChangeCommand;
import org.eclipse.egerrit.core.command.GetContentCommand;
import org.eclipse.egerrit.core.command.GetIncludedInCommand;
import org.eclipse.egerrit.core.command.GetMergeableCommand;
import org.eclipse.egerrit.core.command.GetRelatedChangesCommand;
import org.eclipse.egerrit.core.command.ListReviewersCommand;
import org.eclipse.egerrit.core.command.QueryChangesCommand;
import org.eclipse.egerrit.core.exception.EGerritException;
import org.eclipse.egerrit.core.rest.ApprovalInfo;
import org.eclipse.egerrit.core.rest.ChangeInfo;
import org.eclipse.egerrit.core.rest.ChangeMessageInfo;
import org.eclipse.egerrit.core.rest.CommitInfo;
import org.eclipse.egerrit.core.rest.FetchInfo;
import org.eclipse.egerrit.core.rest.FileInfo;
import org.eclipse.egerrit.core.rest.GitPersonInfo;
import org.eclipse.egerrit.core.rest.IncludedInInfo;
import org.eclipse.egerrit.core.rest.LabelInfo;
import org.eclipse.egerrit.core.rest.MergeableInfo;
import org.eclipse.egerrit.core.rest.RelatedChangeAndCommitInfo;
import org.eclipse.egerrit.core.rest.RelatedChangesInfo;
import org.eclipse.egerrit.core.rest.ReviewerInfo;
import org.eclipse.egerrit.core.rest.RevisionInfo;
import org.eclipse.egerrit.core.utils.Utils;
import org.eclipse.egerrit.ui.EGerritUIPlugin;
import org.eclipse.egerrit.ui.editors.model.ChangeDetailEditorInput;
import org.eclipse.egerrit.ui.editors.model.CompareInput;
import org.eclipse.egerrit.ui.internal.table.UIConflictsWithTable;
import org.eclipse.egerrit.ui.internal.table.UIFilesTable;
import org.eclipse.egerrit.ui.internal.table.UIHistoryTable;
import org.eclipse.egerrit.ui.internal.table.UIPatchSetsTable;
import org.eclipse.egerrit.ui.internal.table.UIRelatedChangesTable;
import org.eclipse.egerrit.ui.internal.table.UIReviewersTable;
import org.eclipse.egerrit.ui.internal.table.UISameTopicTable;
import org.eclipse.egerrit.ui.internal.table.model.SubmitType;
import org.eclipse.egerrit.ui.internal.table.provider.ConflictWithTableLabelProvider;
import org.eclipse.egerrit.ui.internal.table.provider.FileTableLabelProvider;
import org.eclipse.egerrit.ui.internal.table.provider.HistoryTableLabelProvider;
import org.eclipse.egerrit.ui.internal.table.provider.RelatedChangesTableLabelProvider;
import org.eclipse.egerrit.ui.internal.table.provider.ReviewersTableLabelProvider;
import org.eclipse.egerrit.ui.internal.table.provider.SameTopicTableLabelProvider;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ViewerSupport;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

public class ChangeDetailEditor extends EditorPart implements PropertyChangeListener {
	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String EDITOR_ID = "org.eclipse.egerrit.ui.editors.ChangeDetailEditor";

	private static ChangeDetailEditor chDetailEditor = null;

	private static Color RED;

//	private static Color GREEN = fDisplay.getSystemColor(SWT.COLOR_DARK_GREEN);
	private TableViewer tableReviewersViewer;

	private Text incBranchesData;

	private Text includedInTagsData;

	private Text genTopicData;

	private TableViewer tableRelatedChangesViewer;

	private TableViewer tableSameTopicViewer;

	private TableViewer tableConflictsWithViewer;

	private TableViewer tableHistoryViewer;

	private TableViewer tableFilesViewer;

	private final ChangeInfo fChangeInfo = new ChangeInfo();

	private final List<ChangeInfo> fSameTopicChangeInfo = new ArrayList<ChangeInfo>();

	private final List<ChangeInfo> fConflictsWithChangeInfo = new ArrayList<ChangeInfo>();

	private Map<String, RevisionInfo> fRevisions;

	private Map<String, FileInfo> fFiles = new HashMap<String, FileInfo>();

	private final MergeableInfo fMergeableInfo = new MergeableInfo();

	private final RelatedChangesInfo fRelatedChangesInfo = new RelatedChangesInfo();

	private final CommitInfo fCommitInfo = new CommitInfo();

	private Composite fParent;

	private Label genProjectData;

	private Label changeidData;

	private Label genBranchData;

	private Label genUpdatedData;

	private Label genStrategyData;

	private Label genVoteData;

	private Label statusData;

	private Label shortIdData;

	private GerritRepository fGerritRepository;

	private Text msgTextData;

	private Label msgAuthorData;

//	private StyledText msgAuthorData;

	private Label msgCommitterData;

	private Label msgDatePushData;

	private Label msgDatecommitterData;

	private Label msgCommitidData;

	private Label msgParentIdData;

	private Label msgChangeIdData;

	private TabFolder tabFolder;

	private Group summaryGroup;

	private ScrolledComposite scrollView;

	private Composite compButton;

	private Label genMessageData;

	private final List<ReviewerInfo> fReviewers = new ArrayList<ReviewerInfo>();

	private final IncludedInInfo fIncludedIn = new IncludedInInfo();

	private final SimpleDateFormat formatTimeOut = new SimpleDateFormat("MMM d, yyyy  hh:mm a");

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
		RED = parent.getDisplay().getSystemColor(SWT.COLOR_RED);

		createAdditionalToolbarActions();
		fParent = parent;
		GridLayout gl_parent = new GridLayout(1, false);
		parent.setLayout(gl_parent);
		parent.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				parent.setRedraw(false);
//				System.out.println("Size parent: " + parent.getSize() + "\t button size: " + compButton.getSize());
				scrollView.setSize(scrollView.computeSize(SWT.DEFAULT, SWT.DEFAULT));
				parent.setRedraw(true);
			}
		});

		parent.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1));
		scrollView = new ScrolledComposite(parent, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrollView.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		scrollView.setExpandHorizontal(true);
		scrollView.setExpandVertical(true);

		Composite composite = new Composite(scrollView, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		composite.setLayout(new GridLayout(1, false));

		scrollView.setContent(composite);

		Point fontSize = computeFontSize(composite);

		Composite hd = headerSection(composite, fontSize);

		tabFolder = new TabFolder(composite, SWT.NONE);
		tabFolder.setLayout(new GridLayout(1, false));
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		//Create the button section before filling the tabfolder
		compButton = buttonSection(composite);
		Point ptHeader = hd.getSize();
		Point ptButton = compButton.getSize();
		System.err.println("Header width: " + ptHeader.x + "\t Button width: " + ptButton.x);
		int minimumWidth = Math.max(ptHeader.x, ptButton.x);

		summaryTab(tabFolder, minimumWidth);
		tabFolder.pack();

		Point hScrolBarSize = scrollView.getHorizontalBar().getSize();
		Point firstTabFolderSize = tabFolder.getSize();
		int minimumHeight = ptHeader.y + firstTabFolderSize.y + ptButton.y + hScrolBarSize.y;

		messagesTab(tabFolder, minimumWidth);

		filesTab(tabFolder);

		historyTab(tabFolder);

		scrollView.setMinSize(minimumWidth, minimumHeight);

	}

	private Point computeFontSize(Composite composite) {
		GC gc = new GC(composite);
		FontMetrics fm = gc.getFontMetrics();
		int charWidth = fm.getAverageCharWidth();

		gc.dispose();
		return new Point(charWidth, fm.getHeight());
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
		shortIdData.setText("lblID");
		shortIdData.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				String old = shortIdData.getText();
				int id = fChangeInfo.getNumber();
				if (Integer.toString(id).compareTo(old) != 0) {
					shortIdData.setText(Integer.toString(id));
				}
			}
		});

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

		Button abandon = new Button(c, SWT.PUSH);
		abandon.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		abandon.setText("Abandon");

		Button restore = new Button(c, SWT.PUSH);
		restore.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		restore.setText("Restore");

		Button rebase = new Button(c, SWT.PUSH);
		rebase.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		rebase.setText("Rebase");

		Button checkout = new Button(c, SWT.PUSH);
		checkout.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		checkout.setText("Checkout");

		Button pull = new Button(c, SWT.PUSH);
		pull.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if (fRevisions != null) {
					Iterator<Map.Entry<String, RevisionInfo>> itr1 = fRevisions.entrySet().iterator();
					itr1.hasNext();
					Entry<String, RevisionInfo> entry = itr1.next();
					System.out.println(">>>>>>>>>>>>>>>changeid: " + fChangeInfo.getChange_id() + "commands: "
							+ entry.getValue().getFetch());
					for (Map.Entry<String, FetchInfo> entry1 : entry.getValue().getFetch().entrySet()) {
						System.out.println("protocol : " + entry1.getKey());
						for (Map.Entry<String, String> entry3 : entry1.getValue().getCommands().entrySet()) {
							System.out.println(entry3.getKey() + "/" + entry3.getValue());
						}
					}

				}
			}
		});
		pull.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		pull.setText("Pull");

		Button cherrypick = new Button(c, SWT.PUSH);
		cherrypick.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		cherrypick.setText("Cherry-Pick");

		Button reply = new Button(c, SWT.PUSH);
		reply.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		reply.setText("Reply...");
		c.setSize(c.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		return c;
	}

	private void summaryTab(TabFolder tabFolder, int minimumWidth) {
		TabItem tabSummary = new TabItem(tabFolder, SWT.NONE);
		tabSummary.setText("Summary");

		ScrolledComposite sc = new ScrolledComposite(tabFolder, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		sc.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 10, 1));
		sc.setExpandHorizontal(true);
		sc.setExpandVertical(true);
		summaryGroup = new Group(sc, SWT.NONE);
		sc.setContent(summaryGroup);
		tabSummary.setControl(sc);

//		Point fontSize = computeFontSize(summaryGroup);

		GridLayout gl_summaryGroup = new GridLayout(10, false);
		gl_summaryGroup.horizontalSpacing = 10;
		gl_summaryGroup.marginBottom = 3;
		summaryGroup.setLayout(gl_summaryGroup);

		summaryGeneral(summaryGroup);
		summaryReviewers(summaryGroup);
		summaryIncluded(summaryGroup);

		summarySameTopic(summaryGroup);
		summaryRelatedChanges(summaryGroup);
		summaryConflicts(summaryGroup);

		summaryGroup.pack();
		new Label(summaryGroup, SWT.NONE);
		System.err.println("SummaryGroup size: " + summaryGroup.getSize());
		Point pt = summaryGroup.getSize();
		sc.setMinSize(pt);

	}

	private void summaryGeneral(Group group) {

		Group grpGeneral = new Group(group, SWT.NONE);
		grpGeneral.setText("General");
		GridLayout gl_grpGeneral = new GridLayout(5, false);
		gl_grpGeneral.verticalSpacing = 10;
		grpGeneral.setLayout(gl_grpGeneral);

		GridData grid = new GridData();
		grid.horizontalAlignment = SWT.LEFT;
		grid.horizontalSpan = 3;
		grid.verticalAlignment = SWT.LEFT;
		grid.grabExcessHorizontalSpace = false;
		grpGeneral.setLayoutData(grid);

		Label lblProject = new Label(grpGeneral, SWT.RIGHT);
		lblProject.setText("Project:");

		Point fontSize = computeFontSize(grpGeneral);

		genProjectData = new Label(grpGeneral, SWT.NONE);
		genProjectData.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));

		Label lblBranch = new Label(grpGeneral, SWT.RIGHT);
		lblBranch.setText("Branch:");

		genBranchData = new Label(grpGeneral, SWT.NONE);
		genBranchData.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));

		Label lblTopic = new Label(grpGeneral, SWT.RIGHT);
		lblTopic.setText("Topic:");

		genTopicData = new Text(grpGeneral, SWT.BORDER);

		GridData gd_txtTopic = new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1);
		int numTopic = 30; //Max number of chars
		gd_txtTopic.widthHint = fontSize.x * numTopic;
		genTopicData.setLayoutData(gd_txtTopic);

		Button btnSave = new Button(grpGeneral, SWT.NONE);
		btnSave.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		btnSave.setText("Save");

		Label lblStrategy = new Label(grpGeneral, SWT.RIGHT);
		lblStrategy.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblStrategy.setText("Strategy:");

		genStrategyData = new Label(grpGeneral, SWT.NONE);
		GridData gd_genStrategyData = new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1);
		gd_genStrategyData.widthHint = 146;
		genStrategyData.setLayoutData(gd_genStrategyData);
		genStrategyData.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				String old = genStrategyData.getText();
				String latest = fMergeableInfo.getSubmit_type();
				if (latest != null && !latest.isEmpty() && latest.compareTo(SubmitType.getEnumName(old)) != 0) {
					genStrategyData.setText(SubmitType.valueOf(latest).getValue());
				}
			}
		});

		genMessageData = new Label(grpGeneral, SWT.NONE);
		GridData gd_genMessageData = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1);
		int numStrategy = 16; //Max number of chars
		gd_genMessageData.widthHint = fontSize.x * numStrategy;
		genMessageData.setLayoutData(gd_genMessageData);
		genMessageData.setForeground(RED);
		genMessageData.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				String old = genMessageData.getText();
				Boolean latest = new Boolean(fMergeableInfo.isMergeable());
				String latestVal;
				latestVal = latest.booleanValue() ? "" : "CANNOT MERGE";
				if (latestVal.toString().compareTo(old) != 0) {
					genMessageData.setText(latestVal);
				}
			}
		});

		Label lblUpdated = new Label(grpGeneral, SWT.RIGHT);
		lblUpdated.setText("Updated:");

		genUpdatedData = new Label(grpGeneral, SWT.NONE);
		genUpdatedData.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));
		genUpdatedData.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				if (fChangeInfo != null) {
					String st = Utils.formatDate(fChangeInfo.getUpdated(), formatTimeOut);
					String old = genUpdatedData.getText();
					if (st.compareTo(old) != 0) {
						System.err.println("Date update: " + st);
						genUpdatedData.setText(st);
					}
				} else {
					genUpdatedData.setText(""); //$NON-NLS-1$
				}
			}
		});

		//Set the binding for this section
		sumGenDataBindings();
	}

	private void summaryReviewers(Group group) {

		Group grpReviewers = new Group(group, SWT.NONE);
		grpReviewers.setText("Reviewers()");
		grpReviewers.setLayout(new GridLayout(7, false));

//		Point fontSize = computeFontSize(grpReviewers);

		GridData grid = new GridData();
		grid.horizontalAlignment = SWT.FILL;
		grid.horizontalSpan = 7;
		grid.verticalAlignment = SWT.TOP;
		grid.grabExcessHorizontalSpace = true;
		grpReviewers.setLayoutData(grid);

		Button buttonPlus = new Button(grpReviewers, SWT.NONE);
		buttonPlus.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		buttonPlus.setText("+");

		UIReviewersTable uiReviewersTable = new UIReviewersTable();
		uiReviewersTable.createTableViewerSection(grpReviewers, grid);

		tableReviewersViewer = uiReviewersTable.getViewer();

		Label lblVoteSummary = new Label(grpReviewers, SWT.RIGHT);
		lblVoteSummary.setLayoutData(new GridData(SWT.RIGHT, SWT.BOTTOM, true, false, 4, 1));
		lblVoteSummary.setText("Vote Summary:");

		genVoteData = new Label(grpReviewers, SWT.LEFT);
		GridData gd_lblLblvote = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 2, 1);
		gd_lblLblvote.widthHint = 70;
		genVoteData.setLayoutData(gd_lblLblvote);
		genVoteData.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				if (fChangeInfo != null && genVoteData.getText().compareTo("INIT") == 0) {
					String old = genVoteData.getText();
					String codeReviewed;
					int verifyState = 0;
					int codeReviewState = 0;
					Map<String, LabelInfo> labels = fChangeInfo.getLabels();
					for (Map.Entry<String, LabelInfo> entry : labels.entrySet()) {
						if (entry.getKey().compareTo("Verified") == 0) {
							LabelInfo labelInfo = entry.getValue();
							for (ApprovalInfo it : labelInfo.getAll()) {
								if (it.getValue() != null) {
									verifyState = Utils.getStateValue(it.getValue(), verifyState);
								}
							}
						} else if (entry.getKey().compareTo("Code-Review") == 0) {
							LabelInfo labelInfo = entry.getValue();
							for (ApprovalInfo it : labelInfo.getAll()) {
								if (it.getValue() != null) {
									codeReviewState = Utils.getStateValue(it.getValue(), codeReviewState);
								}
							}
						}
						String codeRev;
						if (codeReviewState > 0) {
							codeRev = "+" + Integer.toString(codeReviewState);
						} else {
							codeRev = Integer.toString(codeReviewState);
						}
						String verify;
						if (verifyState > 0) {
							verify = "+" + Integer.toString(verifyState);
						} else {
							verify = Integer.toString(verifyState);
						}

						codeReviewed = codeRev + "       " + verify;
						if (codeReviewed.compareTo(old) != 0) {
							genVoteData.setText(codeReviewed);
						}
					}
				}

			}
		});

		//Set the binding for this section
		sumReviewerDataBindings();
	}

	private void summaryIncluded(Group group) {
		Group grpIncludedIn = new Group(group, SWT.NONE);
		grpIncludedIn.setText("Included In");

		GridLayout gl_grpIncludedIn = new GridLayout(5, false);
		gl_grpIncludedIn.verticalSpacing = 10;
		grpIncludedIn.setLayout(gl_grpIncludedIn);

		GridData grid = new GridData();
		grid.horizontalAlignment = SWT.FILL;
		grid.horizontalSpan = 4;
		grid.verticalAlignment = SWT.TOP;
		grid.grabExcessHorizontalSpace = true;
		grpIncludedIn.setLayoutData(grid);

		Label lblBranches = new Label(grpIncludedIn, SWT.RIGHT);
		lblBranches.setText("Branches:");

		incBranchesData = new Text(grpIncludedIn, SWT.BORDER);
		incBranchesData.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));
		incBranchesData.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				String old = incBranchesData.getText();
				String branches = fIncludedIn.getBranches().toString().replace('[', ' ').replace(']', ' ').trim();

				if (branches.compareTo(old) != 0) {
					incBranchesData.setText(branches);
				}
			}
		});

		Label lblTags = new Label(grpIncludedIn, SWT.RIGHT);
		lblTags.setText("Tags:");

		includedInTagsData = new Text(grpIncludedIn, SWT.BORDER);
		includedInTagsData.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));

		includedInTagsData.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				String old = includedInTagsData.getText();
				String tags = fIncludedIn.getTags().toString().replace('[', ' ').replace(']', ' ').trim();

				if (tags.compareTo(old) != 0) {
					includedInTagsData.setText(tags);
				}
			}
		});

		//Set the binding for this section
		sumIncludedDataBindings();
	}

	private void summarySameTopic(Group group) {
		Group grpSameTopic = new Group(group, SWT.NONE);
		GridData grid = new GridData(SWT.FILL, SWT.FILL, true, false, 5, 2);
		grpSameTopic.setLayoutData(grid);
		grpSameTopic.setText("Same Topic()");

		GridLayout gl_grpSameTopic = new GridLayout(5, false);
		gl_grpSameTopic.verticalSpacing = 9;
		grpSameTopic.setLayout(gl_grpSameTopic);

		UISameTopicTable tableUISameTopic = new UISameTopicTable();
		tableUISameTopic.createTableViewerSection(grpSameTopic, grid);

		tableSameTopicViewer = tableUISameTopic.getViewer();

		//Set the binding for this section
		sumSameTopicDataBindings();
	}

	private void summaryRelatedChanges(Group group) {
		new Label(summaryGroup, SWT.NONE);
		Group grpRelatedChanges = new Group(group, SWT.NONE);
		GridData grid = new GridData(SWT.FILL, SWT.FILL, true, true, 4, 2);
		GridLayout gridLayout = new GridLayout(5, false);
		gridLayout.marginTop = 5;
		grpRelatedChanges.setLayout(gridLayout);
		grpRelatedChanges.setLayoutData(grid);
		grpRelatedChanges.setText("Related Changes()");

		UIRelatedChangesTable tableUIRelatedChanges = new UIRelatedChangesTable();
		tableUIRelatedChanges.createTableViewerSection(grpRelatedChanges, grid);

		tableRelatedChangesViewer = tableUIRelatedChanges.getViewer();

		//Set the binding for this section
		sumRelatedChandesDataBindings();
	}

	private void summaryConflicts(Group group) {
		new Label(summaryGroup, SWT.NONE);

		Group grpConflictsWith = new Group(group, SWT.NONE);
		GridData grid = new GridData(SWT.FILL, SWT.FILL, true, true, 5, 1);
		grpConflictsWith.setLayoutData(grid);
		grpConflictsWith.setText("Conflicts With ()");

		GridLayout gridLayout = new GridLayout(5, false);
		gridLayout.verticalSpacing = 9;
		grpConflictsWith.setLayout(gridLayout);

		UIConflictsWithTable tableUIConflictsWith = new UIConflictsWithTable();
		tableUIConflictsWith.createTableViewerSection(grpConflictsWith, grid);

		tableConflictsWithViewer = tableUIConflictsWith.getViewer();

		//Set the binding for this section
		sumConflictWithDataBindings();
	}

	private void historyTab(TabFolder tabFolder) {
		TabItem tbtmHistory = new TabItem(tabFolder, SWT.NONE);
		tbtmHistory.setText("History");

		Group group = new Group(tabFolder, SWT.NONE);
		tbtmHistory.setControl(group);

		GridData grid = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		group.setLayoutData(grid);
		group.setLayout(new GridLayout(1, false));

		Point fontSize = computeFontSize(group);

		UIHistoryTable tableUIHistory = new UIHistoryTable();
		tableUIHistory.createTableViewerSection(group, grid);

		tableHistoryViewer = tableUIHistory.getViewer();

		//Set the binding for this section
		hisTabDataBindings();
	}

	private void messagesTab(final TabFolder tabFolder, int minWidth) {

		final TabItem tabMessages = new TabItem(tabFolder, SWT.NONE);
		tabMessages.setText("Messages");
		final ScrolledComposite sc_msg = new ScrolledComposite(tabFolder, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		sc_msg.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		sc_msg.setExpandHorizontal(true);
		sc_msg.setExpandVertical(true);
		final Group messagesGroup = new Group(sc_msg, SWT.NONE);

		tabMessages.setControl(sc_msg);
		GridLayout gl_messagesGroup = new GridLayout(4, false);
		gl_messagesGroup.horizontalSpacing = 10;
		gl_messagesGroup.marginTop = 3;
		gl_messagesGroup.marginBottom = 3;
		messagesGroup.setLayout(gl_messagesGroup);

		ScrolledComposite sc_msgtxt = new ScrolledComposite(messagesGroup, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		sc_msgtxt.setExpandHorizontal(true);
		sc_msgtxt.setExpandVertical(true);
		sc_msgtxt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1));

		msgTextData = new Text(sc_msgtxt, SWT.BORDER | SWT.WRAP | SWT.MULTI);
		sc_msgtxt.setContent(msgTextData);
		sc_msgtxt.setMinSize(msgTextData.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		Composite composite = new Composite(messagesGroup, SWT.NONE);
		GridLayout gl_composite = new GridLayout(3, false);
		gl_composite.marginTop = 3;
		composite.setLayout(gl_composite);
		GridData gd_composite = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_composite.heightHint = 117;
		composite.setLayoutData(gd_composite);

		Point fontSize = computeFontSize(composite);
		Label lblAuthor = new Label(composite, SWT.NONE);
		lblAuthor.setText("Author:");

		msgAuthorData = new Label(composite, SWT.NONE);
//		msgAuthorData = new StyledText(composite, SWT.NONE);
		msgAuthorData.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		msgAuthorData.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				GitPersonInfo authorInfo = fCommitInfo.getAuthor();
				if (authorInfo != null) {
					String st = authorInfo.getName() + "  < " + authorInfo.getEmail() + " >"; //$NON-NLS-1$ //$NON-NLS-2$
					String old = msgAuthorData.getText();
					if (old.compareTo(st) != 0) {
						msgAuthorData.setText(st);
					}
				} else {
					msgAuthorData.setText(""); //$NON-NLS-1$
				}
			}
		});

		msgDatePushData = new Label(composite, SWT.NONE);
		GridData gd_DatePush = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		int maxDatePush = 22; //Max number of chars
		gd_DatePush.widthHint = fontSize.x * maxDatePush;
		msgDatePushData.setLayoutData(gd_DatePush);
		msgDatePushData.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				GitPersonInfo authorInfo = fCommitInfo.getAuthor();
				if (authorInfo != null) {
					String st = Utils.formatDate(fChangeInfo.getUpdated(), formatTimeOut);
					String old = msgDatePushData.getText();
					if (st.compareTo(old) != 0) {
						msgDatePushData.setText(st);
					}
				} else {
					msgDatePushData.setText(""); //$NON-NLS-1$
				}
			}
		});

		Label lblCommitter = new Label(composite, SWT.NONE);
		lblCommitter.setText("Committer:");

		msgCommitterData = new Label(composite, SWT.NONE);
		msgCommitterData.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		msgCommitterData.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				GitPersonInfo committerInfo = fCommitInfo.getCommitter();
				if (committerInfo != null) {
					String st = committerInfo.getName() + "  < " + committerInfo.getEmail() + " >"; //$NON-NLS-1$ //$NON-NLS-2$
					String old = msgCommitterData.getText();
					if (st.compareTo(old) != 0) {
						msgCommitterData.setText(st);
					}
				} else {
					msgCommitterData.setText(""); //$NON-NLS-1$
				}

			}
		});

		msgDatecommitterData = new Label(composite, SWT.NONE);
		GridData gd_DateCommitter = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		int maxDateCommit = 22; //Max number of chars
		gd_DateCommitter.widthHint = fontSize.x * maxDateCommit;
		msgDatecommitterData.setLayoutData(gd_DateCommitter);
		msgDatecommitterData.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				GitPersonInfo committerInfo = fCommitInfo.getCommitter();
				if (committerInfo != null) {
					String st = Utils.formatDate(fChangeInfo.getUpdated(), formatTimeOut);
					String old = msgDatecommitterData.getText();
					if (st.compareTo(old) != 0) {
						msgDatecommitterData.setText(st);
					}
				} else {
					msgDatecommitterData.setText(""); //$NON-NLS-1$
				}
			}
		});

		Label lblCommit = new Label(composite, SWT.NONE);
		lblCommit.setText("Commit:");

		msgCommitidData = new Label(composite, SWT.NONE);
		msgCommitidData.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(composite, SWT.NONE);

		Label lblParents = new Label(composite, SWT.NONE);
		lblParents.setText("Parent(s):");

		msgParentIdData = new Label(composite, SWT.NONE);
		msgParentIdData.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		msgParentIdData.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				List<CommitInfo> parentInfo = fCommitInfo.getParents();
				CommitInfo commitInfo = (parentInfo != null && parentInfo.size() > 0) ? parentInfo.get(0) : null; //Only get the first level parent to display
				if (commitInfo != null) {
					String old = msgParentIdData.getText();
					if (old.compareTo(commitInfo.getCommit()) != 0) {
						msgParentIdData.setText(commitInfo.getCommit());
					}
				} else {
					msgParentIdData.setText(""); //$NON-NLS-1$
				}
			}
		});

		new Label(composite, SWT.NONE);

		Label lblChangeid = new Label(composite, SWT.NONE);
		lblChangeid.setText("Change-Id:");

		msgChangeIdData = new Label(composite, SWT.NONE);
		msgChangeIdData.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		Button btnCancel = new Button(messagesGroup, SWT.NONE);
		GridData gd_btnCancel = new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1);
		gd_btnCancel.verticalIndent = 10;
		btnCancel.setLayoutData(gd_btnCancel);
		btnCancel.setText("Cancel");

		Button btnSave = new Button(messagesGroup, SWT.NONE);
		GridData gd_btnSave_1 = new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1);
		gd_btnSave_1.verticalIndent = 10;
		btnSave.setLayoutData(gd_btnSave_1);
		btnSave.setText("Save");
		new Label(messagesGroup, SWT.NONE);

		sc_msg.setContent(messagesGroup);
		tabFolder.pack();

		Point scrollSize = sc_msg.getVerticalBar().getSize();
		sc_msg.setMinSize(minWidth - scrollSize.x - 5, 400);

		//Set the binding for this section
		msgTabDataBindings();
	}

	private void filesTab(TabFolder tabFolder) {
		TabItem tbtmFiles = new TabItem(tabFolder, SWT.NONE);
		tbtmFiles.setText("Files");

		ScrolledComposite scFilesTab = new ScrolledComposite(tabFolder, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scFilesTab.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 10, 1));
		scFilesTab.setExpandHorizontal(true);
		scFilesTab.setExpandVertical(true);

		Group filesGroup = new Group(scFilesTab, SWT.NONE);
		GridData grid = new GridData(SWT.FILL, SWT.FILL, true, false, 10, 1);
		filesGroup.setLayoutData(grid);

		GridLayout gridLayout = new GridLayout(10, true);
		gridLayout.verticalSpacing = 10;
		filesGroup.setLayout(gridLayout);

		scFilesTab.setContent(filesGroup);

		tbtmFiles.setControl(scFilesTab);

		UIFilesTable tableUIFiles = new UIFilesTable();
		tableUIFiles.createTableViewerSection(filesGroup, grid);

		tableFilesViewer = tableUIFiles.getViewer();
		tableFilesViewer.getTable();

		tableFilesViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				IStructuredSelection sel = (IStructuredSelection) event.getSelection();

				Object element = sel.getFirstElement();
				if (element instanceof FileInfo) {
					FileInfo fileInfo = (FileInfo) element;
					if (fRevisions != null) {
						Iterator<Map.Entry<String, RevisionInfo>> itr1 = fRevisions.entrySet().iterator();
						itr1.hasNext();
						Entry<String, RevisionInfo> entry = itr1.next();
						System.out.println(">>>>>>>>>>>>>>>changeid: " + fChangeInfo.getChange_id() + "/revisionid: "
								+ entry.getKey());
						String resRight = getFilesContent(fGerritRepository, fChangeInfo.getChange_id(),
								entry.getKey(), fileInfo.getold_path(), new NullProgressMonitor());
						CompareInput ci = new CompareInput();
						if (resRight != null) {
							ci.setRight(StringUtils.newStringUtf8(Base64.decodeBase64(resRight)));
						} else {
							ci.setRight("Could not parse response");
						}

						if (itr1.hasNext()) {
							Entry<String, RevisionInfo> entryLeft = itr1.next();
							System.out.println(">>>>>>>>>>>>>>>changeid: " + fChangeInfo.getChange_id()
									+ "/revisionid: " + entryLeft.getKey());
							String resLeft = getFilesContent(fGerritRepository, fChangeInfo.getChange_id(),
									entryLeft.getKey(), fileInfo.getold_path(), new NullProgressMonitor());
							if (resLeft != null) {
								ci.setLeft(StringUtils.newStringUtf8(Base64.decodeBase64(resLeft)));
							} else {
								ci.setLeft("Could not parse response");
							}
						} else {
							ci.setLeft("no file");

						}

						CompareUI.openCompareEditor(ci);
						fParent.pack(true);
					}
				}
			}
		});

		Label lblSummary = new Label(filesGroup, SWT.NONE);
		GridData gd_lblSummary = new GridData(SWT.RIGHT, SWT.TOP, true, false, 6, 1);
		gd_lblSummary.horizontalSpan = 6;
		lblSummary.setLayoutData(gd_lblSummary);
		lblSummary.setText("Summary:");

		Label lblDrafts = new Label(filesGroup, SWT.NONE);
		GridData gd_lblDrafts = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
		gd_lblDrafts.horizontalSpan = 1;
		lblDrafts.setLayoutData(gd_lblDrafts);
		lblDrafts.setText("drafts:");

		Label lblComments = new Label(filesGroup, SWT.NONE);
		GridData gd_lblComments = new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1);
		gd_lblComments.horizontalSpan = 1;
		lblComments.setLayoutData(gd_lblComments);
		lblComments.setText("comments:");

		Label lblTotal = new Label(filesGroup, SWT.NONE);
		GridData gd_lblTotal = new GridData(SWT.CENTER, SWT.TOP, false, false, 2, 1);
		gd_lblTotal.horizontalSpan = 2;
		lblTotal.setLayoutData(gd_lblTotal);
		lblTotal.setText("total");

		Button btnOpenAll = new Button(filesGroup, SWT.NONE);
		GridData gd_btnOpenAll = new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1);
		gd_btnOpenAll.verticalIndent = 5;
		btnOpenAll.setLayoutData(gd_btnOpenAll);
		btnOpenAll.setText("Open All");

		Label lblDiffAgainst = new Label(filesGroup, SWT.NONE);
		GridData gd_DiffAgainst = new GridData(SWT.RIGHT, SWT.TOP, false, false, 2, 1);
		gd_DiffAgainst.verticalIndent = 5;
		lblDiffAgainst.setLayoutData(gd_DiffAgainst);
		lblDiffAgainst.setText("Diff against:");

		Combo combo = new Combo(filesGroup, SWT.NONE);
		GridData gd_combo = new GridData(SWT.FILL, SWT.TOP, false, false, 2, 1);
		gd_combo.verticalIndent = 5;
		int numChar = 15;
		Point pt = computeFontSize(combo);
		gd_combo.minimumWidth = numChar * pt.x;
		gd_combo.horizontalSpan = 2;
		combo.setLayoutData(gd_combo);
		System.err.println("Combo mini width = " + (numChar * pt.x));

		Button btnPublish = new Button(filesGroup, SWT.NONE);
		GridData gd_btnPublish = new GridData(SWT.RIGHT, SWT.TOP, false, false, 3, 1);
		gd_btnPublish.verticalIndent = 5;
		btnPublish.setLayoutData(gd_btnPublish);
		btnPublish.setText("Publish");

		Button btnDelete = new Button(filesGroup, SWT.NONE);
		GridData gd_btnDelete = new GridData(SWT.CENTER, SWT.TOP, false, false, 2, 1);
		gd_btnDelete.verticalIndent = 5;
		btnDelete.setLayoutData(gd_btnDelete);
		btnDelete.setText("Delete");

		UIPatchSetsTable tablePatchSetsViewer = new UIPatchSetsTable();
		tablePatchSetsViewer.createTableViewerSection(filesGroup, new GridData(SWT.FILL, SWT.FILL, true, true, 10, 1));

		filesGroup.pack();
		scFilesTab.setMinSize(filesGroup.getSize());

		//Set the binding for this section
		filesTabDataBindings(tableFilesViewer);
	}

	@Override
	public void setFocus() {
	}

	public void setChangeInfo(GerritRepository gerritRepository, ChangeInfo element) {
		this.fGerritRepository = gerritRepository;
		genVoteData.setText("INIT");
		fChangeInfo.reset();

		//Fill the data structure
		fChangeInfo.setNumber(element.getNumber());
		fChangeInfo.setId(element.getId());
		fChangeInfo.setChange_id(element.getChange_id());
		fChangeInfo.setStatus(element.getStatus());
		fChangeInfo.setProject(element.getProject());
		fChangeInfo.setBranch(element.getBranch());
		fChangeInfo.setUpdated(element.getUpdated());
		fChangeInfo.setTopic(element.getTopic());

		//Queries to fill the Review tab data
		setSameTopic(gerritRepository, element);

		setConflictsWith(gerritRepository, element);

		setMergeable(gerritRepository, element);

		setReviewers(gerritRepository);
		setIncludedIn(gerritRepository);

		setCurrentRevisionAndMessageTab(gerritRepository, element.getChange_id());

		//Need the current Revision set before setting the RelatedChanges
		setRelatedChanges(gerritRepository);

	}

	/************************************************************* */
	/*                                                             */
	/* Section to SET the data structure                           */
	/*                                                             */
	/************************************************************* */

	/**
	 * @param gerritRepository
	 * @param element
	 */
	private void setMergeable(GerritRepository gerritRepository, ChangeInfo element) {
		MergeableInfo mergeableInfo = queryMergeable(gerritRepository, element.getChange_id(), "",
				new NullProgressMonitor());
		if (mergeableInfo != null) {
			fMergeableInfo.setSubmit_type(mergeableInfo.getSubmit_type());
			fMergeableInfo.setMergeable(mergeableInfo.isMergeable());
		} else {
			fMergeableInfo.setSubmit_type(""); //Reset the field or should we put already merged ??
			fMergeableInfo.setMergeable(true);// Reset the filed to be empty
		}
		//fChangeInfo.setMergeable
	}

	/**
	 * @param gerritRepository
	 */
	private void setIncludedIn(GerritRepository gerritRepository) {
		try {
			IncludedInInfo includedIn = queryIncludedIn(gerritRepository, fChangeInfo.getChange_id(),
					new NullProgressMonitor());
			if (includedIn != null) {
				fIncludedIn.setBranches(includedIn.getBranches());
				fIncludedIn.setTags(includedIn.getTags());
			} else {
				//Reset the fields if they don't exist
				fIncludedIn.setBranches(new ArrayList<String>());
				fIncludedIn.setTags(new ArrayList<String>());
			}
		} catch (MalformedURLException e) {
			EGerritCorePlugin.logError(e.getMessage());
		}
	}

	/**
	 * @param gerritRepository
	 */
	private void setRelatedChanges(GerritRepository gerritRepository) {
		WritableList writeInfoList;
		RelatedChangesInfo relatedchangesinfo = queryRelatedChanges(gerritRepository, fChangeInfo.getChange_id(),
				fChangeInfo.getCurrentRevision(), new NullProgressMonitor());
		fRelatedChangesInfo.setChanges(new ArrayList<RelatedChangeAndCommitInfo>());

		if (relatedchangesinfo != null) {
			if (relatedchangesinfo.getChanges() != null) {
				fRelatedChangesInfo.setChanges(relatedchangesinfo.getChanges());
			} else {
				fRelatedChangesInfo.setChanges(new ArrayList<RelatedChangeAndCommitInfo>());
			}
		}
		writeInfoList = new WritableList(fRelatedChangesInfo.getChanges(), RelatedChangesInfo.class);
		tableRelatedChangesViewer.setInput(writeInfoList);
	}

	/**
	 * @param gerritRepository
	 */
	private void setReviewers(GerritRepository gerritRepository) {
		WritableList writeInfoList;
		ReviewerInfo[] reviewers = queryReviewers(gerritRepository, fChangeInfo.getChange_id(),
				new NullProgressMonitor());
		fReviewers.clear();
		if (reviewers != null) {
			//fReviewers = Arrays.asList(reviewers);
			ListIterator<ReviewerInfo> litrRevInfo = Arrays.asList(reviewers).listIterator();
			while (litrRevInfo.hasNext()) {
				ReviewerInfo cur = litrRevInfo.next();
				ReviewerInfo item = new ReviewerInfo();
				item.setName(cur.getName());
				item.set_account_id(cur.get_account_id());
				item.setApprovals(cur.getApprovals());
				item.setEmail(cur.getEmail());
				fReviewers.add(item);
			}
			writeInfoList = new WritableList(fReviewers, ReviewerInfo.class);
			tableReviewersViewer.setInput(writeInfoList);
		}
	}

	/**
	 * @param gerritRepository
	 * @param element
	 */
	private void setSameTopic(GerritRepository gerritRepository, ChangeInfo element) {
		ListIterator<ChangeInfo> litr;
		WritableList writeInfoList;
		ChangeInfo[] sameTopicChangeInfo = null;
		fSameTopicChangeInfo.clear();
		if (element.getTopic() != null) {
			try {
				sameTopicChangeInfo = querySameTopic(gerritRepository, element.getTopic(), new NullProgressMonitor());
			} catch (MalformedURLException e) {
				EGerritCorePlugin.logError(e.getMessage());
			}
			litr = Arrays.asList(sameTopicChangeInfo).listIterator();
			while (litr.hasNext()) {
				ChangeInfo cur = litr.next();
				if (fChangeInfo.getChange_id().compareTo(cur.getChange_id()) != 0) { // dont' want the current one
					ChangeInfo item = new ChangeInfo();
					item.setChange_id(cur.getChange_id());
					item.setSubject(cur.getSubject());
					fSameTopicChangeInfo.add(item);
				}
			}
			writeInfoList = new WritableList(fSameTopicChangeInfo, ReviewerInfo.class);
			tableSameTopicViewer.setInput(writeInfoList);
		}
		//writeInfoList = new WritableList(fSameTopicChangeInfo, ReviewerInfo.class);
		//tableSameTopicViewer = .setInput(writeInfoList);
		//retrieveData(writeInfoList);
	}

	//retrieve info list

	/**
	 * @param gerritRepository
	 * @param element
	 */
	private void setConflictsWith(GerritRepository gerritRepository, ChangeInfo element) {
		ListIterator<ChangeInfo> litr;
		WritableList writeInfoList;
		ChangeInfo[] conflictsWithChangeInfo = null;
		fConflictsWithChangeInfo.clear();

		try {
			conflictsWithChangeInfo = queryConflictsWith(gerritRepository, element.getChange_id(),
					new NullProgressMonitor());
		} catch (MalformedURLException e) {
			EGerritCorePlugin.logError(e.getMessage());
		}

		if (conflictsWithChangeInfo.length > 0) {
			litr = Arrays.asList(conflictsWithChangeInfo).listIterator();
			while (litr.hasNext()) {
				ChangeInfo cur = litr.next();
				if (fChangeInfo.getChange_id().compareTo(cur.getChange_id()) != 0) { // dont' want the current one
					ChangeInfo item = new ChangeInfo();
					item.setChange_id(cur.getChange_id());
					item.setSubject(cur.getSubject());
					fConflictsWithChangeInfo.add(item);
				}
			}
		} else {
			//Need to reset that structure
			System.err.println("Need to reset that structure fConflictsWithChangeInfo");
		}
		writeInfoList = new WritableList(fConflictsWithChangeInfo, ReviewerInfo.class);
		tableConflictsWithViewer.setInput(writeInfoList);
	}

	/**
	 * Fill the data related to the current revision
	 *
	 * @param gerritRepository
	 * @param change_id
	 */
	private void setCurrentRevisionAndMessageTab(GerritRepository gerritRepository, String change_id) {
		ChangeInfo res = queryMessageTab(gerritRepository, change_id, new NullProgressMonitor());

		//Need to initialise the variables first
		fFiles = new HashMap<String, FileInfo>();
		fCommitInfo.reset();
		if (fRevisions != null) {
			fRevisions.clear();
		}

		if (res != null) {
			fChangeInfo.setCurrent_revision(res.getCurrentRevision());
			fChangeInfo.setLabels(res.getLabels());
			fChangeInfo.setMessages(res.getMessages());
			fRevisions = res.getRevisions();
			Iterator<Map.Entry<String, RevisionInfo>> itr1 = fRevisions.entrySet().iterator();
			while (itr1.hasNext()) {
				Entry<String, RevisionInfo> entry = itr1.next();

				fCommitInfo.setCommit(res.getCurrentRevision());

				//	fCommitInfo = entry.getValue().getCommit();
				//				fCommitInfo.addPropertyChangeListener("message", this);
				fCommitInfo.setMessage(entry.getValue().getCommit().getMessage());
				fCommitInfo.setParents(entry.getValue().getCommit().getParents());
				fCommitInfo.setAuthor(entry.getValue().getCommit().getAuthor());
				fCommitInfo.setCommitter(entry.getValue().getCommit().getCommitter());
				//Store the files
				fFiles.putAll(entry.getValue().getFiles());

				entry.getValue().setFiles(entry.getValue().getFiles());

				Iterator<Map.Entry<String, FileInfo>> itr2 = fFiles.entrySet().iterator();
				while (itr2.hasNext()) {

					Entry<String, FileInfo> entry1 = itr2.next();

					entry1.getValue().addPropertyChangeListener("old_path", this);
					entry1.getValue().setOld_path(entry1.getKey());
				}
			}

			WritableList writeInfoList = new WritableList(fFiles.values(), FileInfo.class);
			tableFilesViewer.setInput(writeInfoList);
			tableFilesViewer.refresh();

			writeInfoList = new WritableList(fChangeInfo.getMessages(), ChangeMessageInfo.class);
			tableHistoryViewer.setInput(writeInfoList);
			tableHistoryViewer.refresh();
		}
	}

	/**
	 * @param gerritRepository
	 * @return
	 */
	private Gerrit setGerritServer(GerritRepository gerritRepository) {
		Gerrit gerrit = null;
		try {
			gerrit = GerritFactory.create(gerritRepository);
		} catch (EGerritException e) {
			EGerritCorePlugin.logError(e.getLocalizedMessage(), e);
		}
		return gerrit;
	}

	/************************************************************* */
	/*                                                             */
	/* Section to QUERY the data structure                         */
	/*                                                             */
	/************************************************************* */

	private MergeableInfo queryMergeable(GerritRepository gerritRepository, String change_id, String revision_id,
			IProgressMonitor monitor) {
		try {
			monitor.beginTask("Executing query", IProgressMonitor.UNKNOWN);

			Gerrit gerrit = setGerritServer(gerritRepository);

			// Create query
			if (gerrit != null) {
				GetMergeableCommand command = gerrit.getMergeable(change_id, revision_id);

				MergeableInfo res = null;
				try {
					res = command.call();
					return res;
				} catch (EGerritException e) {
					EGerritCorePlugin.logError(e.getMessage());
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
	 * @throws MalformedURLException
	 */
	private IncludedInInfo queryIncludedIn(GerritRepository gerritRepository, String change_id,
			NullProgressMonitor monitor) throws MalformedURLException {
		try {
			monitor.beginTask("Executing query", IProgressMonitor.UNKNOWN);

			Gerrit gerrit = setGerritServer(gerritRepository);

			// Create query
			if (gerrit != null) {
				GetIncludedInCommand command = gerrit.getIncludedIn(change_id);
/*			command.addOption(ChangeOption.ALL_FILES);
			command.addOption(ChangeOption.CURRENT_REVISION);
			command.addOption(ChangeOption.CURRENT_COMMIT);
 */
				IncludedInInfo res = null;
				try {
					res = command.call();
				} catch (EGerritException e) {
					EGerritCorePlugin.logError(e.getMessage());
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

	private String getFilesContent(GerritRepository gerritRepository, String change_id, String revision_id,
			String file, IProgressMonitor monitor) {
		try {
			monitor.beginTask("Executing query", IProgressMonitor.UNKNOWN);

			Gerrit gerrit = setGerritServer(gerritRepository);

			// Create query
			if (gerrit != null) {
				GetContentCommand command = gerrit.getContent(change_id, revision_id, file);

				String res = null;
				try {
					res = command.call();
					return res;
				} catch (EGerritException e) {
					EGerritCorePlugin.logError(e.getMessage());
				}
			}
		} catch (UnsupportedClassVersionError e) {
			return null;
		} finally {
			monitor.done();
		}

		return null;

	}

	private RelatedChangesInfo queryRelatedChanges(GerritRepository gerritRepository, String change_id,
			String revision_id, IProgressMonitor monitor) {
		try {
			monitor.beginTask("Executing query", IProgressMonitor.UNKNOWN);

			Gerrit gerrit = setGerritServer(gerritRepository);

			// Create query
			if (gerrit != null) {
				GetRelatedChangesCommand command = gerrit.getRelatedChanges(change_id, revision_id);

				RelatedChangesInfo res = null;
				try {
					res = command.call();
					return res;
				} catch (EGerritException e) {
					EGerritCorePlugin.logError(e.getMessage());
				}
			}
		} catch (UnsupportedClassVersionError e) {
			return null;
		} finally {
			monitor.done();
		}

		return null;

	}

	private ReviewerInfo[] queryReviewers(GerritRepository gerritRepository, String change_id, IProgressMonitor monitor) {
		try {
			monitor.beginTask("Executing query", IProgressMonitor.UNKNOWN);

			Gerrit gerrit = setGerritServer(gerritRepository);

			// Create query
			if (gerrit != null) {
				ListReviewersCommand command = gerrit.getReviewers(change_id);

				ReviewerInfo[] res = null;
				try {
					res = command.call();
					return res;
				} catch (EGerritException e) {
					EGerritCorePlugin.logError(e.getMessage());
				}
			}
		} catch (UnsupportedClassVersionError e) {
			return null;
		} finally {
			monitor.done();
		}

		return null;

	}

	private ChangeInfo[] querySameTopic(GerritRepository gerritRepository, String topic, IProgressMonitor monitor)
			throws MalformedURLException {
		try {
			monitor.beginTask("Executing query", IProgressMonitor.UNKNOWN);

			Gerrit gerrit = setGerritServer(gerritRepository);

			ChangeInfo[] res = null;
			if (gerrit != null) {
				// Create query
				QueryChangesCommand command = gerrit.queryChanges();
//				command.addOption(ChangeOption.LABELS);
//				command.addOption(ChangeOption.CURRENT_REVISION);
//				command.addOption(ChangeOption.CURRENT_FILES);
//				command.addLimit(101);
				command.addTopic(topic);

//				setQuery(query, command);

				try {
					res = command.call();
				} catch (EGerritException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//				final String queryText = query;
//				Display.getDefault().syncExec(new Runnable() {
//					@Override
//					public void run() {
//						setRepositoryVersionLabel(defaultServerInfo.getName(), gerritRepository.getVersion().toString());
//						fSearchRequestText.setText(queryText);
//
//					}
//				});
			}
			return res;
		} catch (UnsupportedClassVersionError e) {
//			return new Status(IStatus.ERROR, GerritCorePlugin.PLUGIN_ID, "error", e);
			return null;
		} finally {
			monitor.done();
		}
	}

	private ChangeInfo[] queryConflictsWith(GerritRepository gerritRepository, String change_id,
			IProgressMonitor monitor) throws MalformedURLException {
		try {
			monitor.beginTask("Executing query", IProgressMonitor.UNKNOWN);

			Gerrit gerrit = setGerritServer(gerritRepository);

			ChangeInfo[] res = null;
			if (gerrit != null) {
				// Create query
				QueryChangesCommand command = gerrit.queryChanges();
//				command.addOption(ChangeOption.LABELS);
//				command.addOption(ChangeOption.CURRENT_REVISION);
//				command.addOption(ChangeOption.CURRENT_FILES);
//				command.addLimit(101);
				command.addConflicts(change_id);

//				setQuery(query, command);

				try {
					res = command.call();
				} catch (EGerritException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//				final String queryText = query;
//				Display.getDefault().syncExec(new Runnable() {
//					@Override
//					public void run() {
//						setRepositoryVersionLabel(defaultServerInfo.getName(), gerritRepository.getVersion().toString());
//						fSearchRequestText.setText(queryText);
//
//					}
//				});
			}
			return res;
		} catch (UnsupportedClassVersionError e) {
//			return new Status(IStatus.ERROR, GerritCorePlugin.PLUGIN_ID, "error", e);
			return null;
		} finally {
			monitor.done();
		}
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

			Gerrit gerrit = setGerritServer(gerritRepository);

			// Create query
			if (gerrit != null) {
				GetChangeCommand command = gerrit.getChange(change_id);
				command.addOption(ChangeOption.ALL_FILES);
				command.addOption(ChangeOption.CURRENT_REVISION);
				command.addOption(ChangeOption.CURRENT_COMMIT);
				command.addOption(ChangeOption.MESSAGES);
				command.addOption(ChangeOption.DOWNLOAD_COMMANDS);

				ChangeInfo res = null;
				try {
					res = command.call();
				} catch (EGerritException e) {
					EGerritCorePlugin.logError(e.getMessage());
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

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		System.out.println("propertyChange :" + evt);

	}

	protected DataBindingContext headerSectionDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();

		//
		IObservableValue observeTextLblLblidObserveWidget = WidgetProperties.text().observe(shortIdData);
//		IObservableValue idFChangeInfoObserveValue = BeanProperties.value("change_id").observe(fChangeInfo);
		IObservableValue idFChangeInfoObserveValue = BeanProperties.value("_number").observe(fChangeInfo);
		bindingContext.bindValue(observeTextLblLblidObserveWidget, idFChangeInfoObserveValue, null, null);
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

	protected DataBindingContext sumGenDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();

		IObservableValue observeTextLblLblprojectObserveWidget = WidgetProperties.text().observe(genProjectData);
		IObservableValue projectbytesFChangeInfoObserveValue = BeanProperties.value("project").observe(fChangeInfo);
		bindingContext.bindValue(observeTextLblLblprojectObserveWidget, projectbytesFChangeInfoObserveValue, null, null);
		//
		IObservableValue observeTextLblChangeid_1ObserveWidget = WidgetProperties.text().observe(changeidData);
		IObservableValue changeIdFChangeInfoObserveValue = BeanProperties.value("change_id").observe(fChangeInfo);
		bindingContext.bindValue(observeTextLblChangeid_1ObserveWidget, changeIdFChangeInfoObserveValue, null, null);
		//
		IObservableValue observeTextLblBranch_1ObserveWidget = WidgetProperties.text().observe(genBranchData);
		IObservableValue branchFChangeInfoObserveValue = BeanProperties.value("branch").observe(fChangeInfo);
		bindingContext.bindValue(observeTextLblBranch_1ObserveWidget, branchFChangeInfoObserveValue, null, null);
		//
		IObservableValue observeTextText_2ObserveWidget = WidgetProperties.text().observe(genTopicData);
		IObservableValue topicFChangeInfoObserveValue = BeanProperties.value("topic").observe(fChangeInfo);
		bindingContext.bindValue(observeTextText_2ObserveWidget, topicFChangeInfoObserveValue, null, null);
		//
		IObservableValue observeTextLblUpdated_1ObserveWidget = WidgetProperties.text().observe(genUpdatedData);
		IObservableValue updatedFChangeInfoObserveValue = BeanProperties.value("updated").observe(fChangeInfo);
		bindingContext.bindValue(observeTextLblUpdated_1ObserveWidget, updatedFChangeInfoObserveValue, null, null);
		//
		IObservableValue observeTextGenStrategyDataObserveWidget = WidgetProperties.text().observe(genStrategyData);
		IObservableValue bytesMergeableinfogetSubmit_typeObserveValue = BeanProperties.value("submit_type").observe(
				fMergeableInfo);
		bindingContext.bindValue(observeTextGenStrategyDataObserveWidget, bytesMergeableinfogetSubmit_typeObserveValue,
				null, null);
		//
		IObservableValue observeTextGenMessageDataObserveWidget = WidgetProperties.text().observe(genMessageData);
		IObservableValue mergeableFMergeableInfoObserveValue = BeanProperties.value("mergeable")
				.observe(fMergeableInfo);
		bindingContext.bindValue(observeTextGenMessageDataObserveWidget, mergeableFMergeableInfoObserveValue, null,
				null);
		//

		return bindingContext;
	}

	protected void sumReviewerDataBindings() {
		ObservableListContentProvider contentProvider = new ObservableListContentProvider();
		tableReviewersViewer.setContentProvider(contentProvider);
		WritableList writeInfoList = new WritableList(fReviewers, ReviewerInfo.class);

		DataBindingContext bindingContext = new DataBindingContext();

		IObservableValue observeTextLblLblprojectObserveWidget = WidgetProperties.text().observe(genVoteData);
		if (fChangeInfo != null && fChangeInfo.getLabels() != null && fChangeInfo.getLabels().get(0) != null) {
			IObservableValue projectbytesFChangeInfoObserveValue = BeanProperties.value("labels").observe(fChangeInfo);
			bindingContext.bindValue(observeTextLblLblprojectObserveWidget, projectbytesFChangeInfoObserveValue, null,
					null);
		}

		IObservableMap[] observeMaps = Properties.observeEach(contentProvider.getKnownElements(),
				BeanProperties.values(new String[] { "name" }));

		ViewerSupport.bind(tableReviewersViewer, writeInfoList, BeanProperties.values(new String[] { "name" }));
		tableReviewersViewer.setLabelProvider(new ReviewersTableLabelProvider(observeMaps));
	}

	protected DataBindingContext sumIncludedDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();

		IObservableValue observeTextLblLblprojectObserveWidget = WidgetProperties.text().observe(incBranchesData);
		ArrayList arrList = new ArrayList<String>();
		arrList.add(0, "");
		fIncludedIn.setBranches(arrList);
		if (fIncludedIn != null && fIncludedIn.getBranches() != null && fIncludedIn.getBranches().get(0) != null) {
			IObservableValue projectbytesFChangeInfoObserveValue = BeanProperties.value("branches")
					.observe(fIncludedIn);
			bindingContext.bindValue(observeTextLblLblprojectObserveWidget, projectbytesFChangeInfoObserveValue, null,
					null);
			//
			IObservableValue observeTextLblChangeid_1ObserveWidget = WidgetProperties.text()
					.observe(includedInTagsData);
			IObservableValue changeIdFChangeInfoObserveValue = BeanProperties.value("tags").observe(fIncludedIn);
			bindingContext.bindValue(observeTextLblChangeid_1ObserveWidget, changeIdFChangeInfoObserveValue, null, null);
		}
		return bindingContext;
	}

	protected void sumSameTopicDataBindings() {
		ObservableListContentProvider contentProvider = new ObservableListContentProvider();
		tableSameTopicViewer.setContentProvider(contentProvider);

		WritableList writeInfoList = new WritableList(fSameTopicChangeInfo, ChangeInfo.class);

		IObservableMap[] observeMaps = Properties.observeEach(contentProvider.getKnownElements(),
				BeanProperties.values(new String[] { "change_id" }));

		ViewerSupport.bind(tableSameTopicViewer, writeInfoList, BeanProperties.values(new String[] { "change_id" }));
		tableSameTopicViewer.setLabelProvider(new SameTopicTableLabelProvider(observeMaps));
	}

	protected void sumRelatedChandesDataBindings() {
		ObservableListContentProvider contentProvider = new ObservableListContentProvider();
		tableRelatedChangesViewer.setContentProvider(contentProvider);

		if (fRelatedChangesInfo.getChanges() == null) {
			fRelatedChangesInfo.setChanges(new ArrayList<RelatedChangeAndCommitInfo>());
		}

		WritableList writeInfoList = new WritableList(fRelatedChangesInfo.getChanges(), RelatedChangesInfo.class);

		IObservableMap[] observeMaps = Properties.observeEach(contentProvider.getKnownElements(),
				BeanProperties.values(new String[] { "changes" }));

		ViewerSupport.bind(tableRelatedChangesViewer, writeInfoList, BeanProperties.values(new String[] { "changes" }));
		tableRelatedChangesViewer.setLabelProvider(new RelatedChangesTableLabelProvider(observeMaps));
	}

	protected void sumConflictWithDataBindings() {
		ObservableListContentProvider contentProvider = new ObservableListContentProvider();
		tableConflictsWithViewer.setContentProvider(contentProvider);

		WritableList writeInfoList = new WritableList(fConflictsWithChangeInfo, ChangeInfo.class);

		IObservableMap[] observeMaps = Properties.observeEach(contentProvider.getKnownElements(),
				BeanProperties.values(new String[] { "change_id" }));

		ViewerSupport.bind(tableConflictsWithViewer, writeInfoList, BeanProperties.values(new String[] { "change_id" }));
		tableConflictsWithViewer.setLabelProvider(new ConflictWithTableLabelProvider(observeMaps));
	}

	protected DataBindingContext msgTabDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();

		//
		IObservableValue observeMsgTextDataWidget = WidgetProperties.text().observe(msgTextData);
		IObservableValue msgTextDataValue = BeanProperties.value("message").observe(fCommitInfo);
		bindingContext.bindValue(observeMsgTextDataWidget, msgTextDataValue, null, null);
		//
		IObservableValue observeTextMsgAuthorDataWidget = WidgetProperties.text().observe(msgAuthorData);
		IObservableValue msgAuthorDataValue = BeanProperties.value("author").observe(fCommitInfo);
		bindingContext.bindValue(observeTextMsgAuthorDataWidget, msgAuthorDataValue, null, null);
		//
		IObservableValue observeTextMsgCommitterDataWidget = WidgetProperties.text().observe(msgCommitterData);
		IObservableValue msgCommitterDataValue = BeanProperties.value("status").observe(fCommitInfo);
		bindingContext.bindValue(observeTextMsgCommitterDataWidget, msgCommitterDataValue, null, null);
		//
		IObservableValue observeTextMsgCommitidDataWidget = WidgetProperties.text().observe(msgCommitidData);
		IObservableValue msgCommitidDataValue = BeanProperties.value("commit").observe(fCommitInfo);
		bindingContext.bindValue(observeTextMsgCommitidDataWidget, msgCommitidDataValue, null, null);
		//
		IObservableValue observeTextMsgParentIdDataWidget = WidgetProperties.text().observe(msgParentIdData);
		IObservableValue msgParentIdDataValue = BeanProperties.value("parents").observe(fCommitInfo);
		bindingContext.bindValue(observeTextMsgParentIdDataWidget, msgParentIdDataValue, null, null);
		//
		IObservableValue observeTextMsgChangeIdDataWidget = WidgetProperties.text().observe(msgChangeIdData);
		IObservableValue msgChangeIdDataValue = BeanProperties.value("change_id").observe(fChangeInfo);
		bindingContext.bindValue(observeTextMsgChangeIdDataWidget, msgChangeIdDataValue, null, null);

		return bindingContext;
	}

	protected void filesTabDataBindings(TableViewer tableViewer) {
		ObservableListContentProvider contentProvider = new ObservableListContentProvider();
		tableViewer.setContentProvider(contentProvider);

		WritableList writeInfoList = new WritableList(fFiles.values(), FileInfo.class);

		IObservableMap[] observeMaps = Properties.observeEach(contentProvider.getKnownElements(),
				BeanProperties.values(new String[] { "old_path" }));

		ViewerSupport.bind(tableViewer, writeInfoList, BeanProperties.values(new String[] { "old_path" }));
		tableViewer.setLabelProvider(new FileTableLabelProvider(observeMaps));
	}

	protected void hisTabDataBindings() {

		ObservableListContentProvider contentProvider = new ObservableListContentProvider();
		tableHistoryViewer.setContentProvider(contentProvider);

		if (fChangeInfo.getMessages() == null) {
			fChangeInfo.setMessages(new ArrayList<ChangeMessageInfo>());
		}

		WritableList writeInfoList = new WritableList(fChangeInfo.getMessages(), ChangeMessageInfo.class);

		IObservableMap[] observeMaps = Properties.observeEach(contentProvider.getKnownElements(),
				BeanProperties.values(new String[] { "messages" }));

		ViewerSupport.bind(tableHistoryViewer, writeInfoList, BeanProperties.values(new String[] { "messages" }));
		tableHistoryViewer.setLabelProvider(new HistoryTableLabelProvider(observeMaps));

	}

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

}
