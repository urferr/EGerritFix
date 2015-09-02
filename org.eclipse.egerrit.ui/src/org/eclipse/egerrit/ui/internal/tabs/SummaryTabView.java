/*******************************************************************************
 * Copyright (c) 2015 Ericsson AB.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Jacques Bouthillier - initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.ui.internal.tabs;

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.property.Properties;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.egerrit.core.EGerritCorePlugin;
import org.eclipse.egerrit.core.GerritClient;
import org.eclipse.egerrit.core.command.AddReviewerCommand;
import org.eclipse.egerrit.core.command.DeleteReviewerCommand;
import org.eclipse.egerrit.core.command.GetIncludedInCommand;
import org.eclipse.egerrit.core.command.GetMergeableCommand;
import org.eclipse.egerrit.core.command.GetRelatedChangesCommand;
import org.eclipse.egerrit.core.command.ListReviewersCommand;
import org.eclipse.egerrit.core.command.QueryChangesCommand;
import org.eclipse.egerrit.core.exception.EGerritException;
import org.eclipse.egerrit.core.rest.AddReviewerInput;
import org.eclipse.egerrit.core.rest.AddReviewerResult;
import org.eclipse.egerrit.core.rest.ChangeInfo;
import org.eclipse.egerrit.core.rest.IncludedInInfo;
import org.eclipse.egerrit.core.rest.MergeableInfo;
import org.eclipse.egerrit.core.rest.RelatedChangeAndCommitInfo;
import org.eclipse.egerrit.core.rest.RelatedChangesInfo;
import org.eclipse.egerrit.core.rest.ReviewerInfo;
import org.eclipse.egerrit.ui.internal.table.UIConflictsWithTable;
import org.eclipse.egerrit.ui.internal.table.UIRelatedChangesTable;
import org.eclipse.egerrit.ui.internal.table.UIReviewersTable;
import org.eclipse.egerrit.ui.internal.table.UISameTopicTable;
import org.eclipse.egerrit.ui.internal.table.provider.ConflictWithTableLabelProvider;
import org.eclipse.egerrit.ui.internal.table.provider.RelatedChangesTableLabelProvider;
import org.eclipse.egerrit.ui.internal.table.provider.ReviewersTableLabelProvider;
import org.eclipse.egerrit.ui.internal.table.provider.SameTopicTableLabelProvider;
import org.eclipse.egerrit.ui.internal.utils.DataConverter;
import org.eclipse.egerrit.ui.internal.utils.UIUtils;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ViewerSupport;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

/**
 * This class is used in the editor to handle the Gerrit message view
 *
 * @since 1.0
 */
public class SummaryTabView {

	private final String TITLE = "Gerrit Server ";

	private static Color RED;

	private final SimpleDateFormat formatTimeOut = new SimpleDateFormat("MMM d, yyyy  hh:mm a"); //$NON-NLS-1$

	private Group summaryGroup;

	private Label genProjectData;

	private Label genBranchData;

	private Text genTopicData;

	private Label genStrategyData;

	private Label genMessageData;

	private Label genUpdatedData;

	private TableViewer tableReviewersViewer;

	private Label genVoteData;

	private Text incBranchesData;

	private Text includedInTagsData;

	private TableViewer tableSameTopicViewer;

	private TableViewer tableRelatedChangesViewer;

	private TableViewer tableConflictsWithViewer;

	private final List<ReviewerInfo> fReviewers = new ArrayList<ReviewerInfo>();

	private final IncludedInInfo fIncludedIn = new IncludedInInfo();

	private final List<ChangeInfo> fSameTopicChangeInfo = new ArrayList<ChangeInfo>();

	private final MergeableInfo fMergeableInfo = new MergeableInfo();

	private final List<ChangeInfo> fConflictsWithChangeInfo = new ArrayList<ChangeInfo>();

	private final RelatedChangesInfo fRelatedChangesInfo = new RelatedChangesInfo();

	private ChangeInfo fChangeInfo;

	private GerritClient gerritClient;

	// ------------------------------------------------------------------------
	// Constructor and life cycle
	// ------------------------------------------------------------------------

	/**
	 * The constructor.
	 */
	public SummaryTabView() {
	}

	/**
	 * @param tabFolder
	 * @param listMessages
	 *            List<ChangeMessageInfo>
	 */
	public void create(TabFolder tabFolder, ChangeInfo changeInfo) {
		fChangeInfo = changeInfo;
		summaryTab(tabFolder);
	}

//	private void summaryTab(TabFolder tabFolder, int minimumWidth) {
	private void summaryTab(TabFolder tabFolder) {
		RED = tabFolder.getDisplay().getSystemColor(SWT.COLOR_RED);
		TabItem tabSummary = new TabItem(tabFolder, SWT.NONE);
		tabSummary.setText("Summary");

		ScrolledComposite sc = new ScrolledComposite(tabFolder, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		sc.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 10, 1));
		sc.setExpandHorizontal(true);
		sc.setExpandVertical(true);
		summaryGroup = new Group(sc, SWT.NONE);
		sc.setContent(summaryGroup);
		tabSummary.setControl(sc);

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

		Point fontSize = UIUtils.computeFontSize(grpGeneral);

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

		genMessageData = new Label(grpGeneral, SWT.NONE);
		GridData gd_genMessageData = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1);
		int numStrategy = 16; //Max number of chars
		gd_genMessageData.widthHint = fontSize.x * numStrategy;
		genMessageData.setLayoutData(gd_genMessageData);
		genMessageData.setForeground(RED);

		Label lblUpdated = new Label(grpGeneral, SWT.RIGHT);
		lblUpdated.setText("Updated:");

		genUpdatedData = new Label(grpGeneral, SWT.NONE);
		genUpdatedData.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));

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

		final Button buttonPlus = new Button(grpReviewers, SWT.NONE);
		buttonPlus.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		buttonPlus.setText("+"); //$NON-NLS-1$
		buttonPlus.addSelectionListener(buttonPlusListener(buttonPlus));

		UIReviewersTable uiReviewersTable = new UIReviewersTable();
		uiReviewersTable.createTableViewerSection(grpReviewers, grid);

		tableReviewersViewer = uiReviewersTable.getViewer();
		tableReviewersViewer.getTable().addMouseListener(deleteReviewerListener());

		Label lblVoteSummary = new Label(grpReviewers, SWT.RIGHT);
		lblVoteSummary.setLayoutData(new GridData(SWT.RIGHT, SWT.BOTTOM, true, false, 4, 1));
		lblVoteSummary.setText("Vote Summary:");

		genVoteData = new Label(grpReviewers, SWT.LEFT);
		GridData gd_lblLblvote = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 2, 1);
		gd_lblLblvote.widthHint = 70;
		genVoteData.setLayoutData(gd_lblLblvote);

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

		Label lblTags = new Label(grpIncludedIn, SWT.RIGHT);
		lblTags.setText("Tags:");

		includedInTagsData = new Text(grpIncludedIn, SWT.BORDER);
		includedInTagsData.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));

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

	/************************************************************* */
	/*                                                             */
	/* Section to SET the data structure                           */
	/*                                                             */
	/************************************************************* */

	/**
	 * @param gerritClient
	 * @param element
	 */
	private void setMergeable(GerritClient gerritClient, ChangeInfo element) {
		MergeableInfo mergeableInfo = queryMergeable(gerritClient, element.getChange_id(), "",
				new NullProgressMonitor());
		if (mergeableInfo != null) {
			fMergeableInfo.setSubmit_type(mergeableInfo.getSubmit_type());
			fMergeableInfo.setMergeable(mergeableInfo.isMergeable());
		} else {
			fMergeableInfo.setSubmit_type(""); //Reset the field or should we put already merged ??
			fMergeableInfo.setMergeable(true);// Reset the filed to be empty
		}
	}

	/**
	 * @param gerritClient
	 */
	private void setIncludedIn(GerritClient gerritClient) {
		try {
			IncludedInInfo includedIn = queryIncludedIn(gerritClient, fChangeInfo.getChange_id(),
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
	 * @param gerritClient
	 */
	private void setRelatedChanges(GerritClient gerritClient) {
		WritableList writeInfoList;
		RelatedChangesInfo relatedchangesinfo = queryRelatedChanges(gerritClient, fChangeInfo.getChange_id(),
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
	 * @param gerritClient
	 */
	private void setReviewers(GerritClient gerritClient) {
		WritableList writeInfoList;
		ReviewerInfo[] reviewers = queryReviewers(gerritClient, fChangeInfo.getChange_id(), new NullProgressMonitor());
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
	 * @param gerritClient
	 * @param element
	 */
	private void setSameTopic(GerritClient gerritClient, ChangeInfo element) {
		ListIterator<ChangeInfo> litr;
		WritableList writeInfoList;
		ChangeInfo[] sameTopicChangeInfo = null;
		fSameTopicChangeInfo.clear();
		if (element.getTopic() != null) {
			try {
				sameTopicChangeInfo = querySameTopic(gerritClient, element.getTopic(), new NullProgressMonitor());
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
	}

	/**
	 * @param gerritClient
	 * @param element
	 */
	private void setConflictsWith(GerritClient gerritClient, ChangeInfo element) {
		ListIterator<ChangeInfo> litr;
		WritableList writeInfoList;
		ChangeInfo[] conflictsWithChangeInfo = null;
		fConflictsWithChangeInfo.clear();

		try {
			conflictsWithChangeInfo = queryConflictsWith(gerritClient, element.getChange_id(),
					new NullProgressMonitor());
		} catch (MalformedURLException e) {
			EGerritCorePlugin.logError(e.getMessage());
		}

		if (conflictsWithChangeInfo != null && conflictsWithChangeInfo.length > 0) {
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

	/***************************************************************/
	/*                                                             */
	/* Section to QUERY the data structure                         */
	/*                                                             */
	/************************************************************* */

	private MergeableInfo queryMergeable(GerritClient gerritClient, String change_id, String revision_id,
			IProgressMonitor monitor) {
		try {
			monitor.beginTask("Executing query", IProgressMonitor.UNKNOWN);

			// Create query
			GetMergeableCommand command = gerritClient.getMergeable(change_id, revision_id);

			MergeableInfo res = null;
			try {
				res = command.call();
				return res;
			} catch (EGerritException e) {
				EGerritCorePlugin.logError(e.getMessage());
			} catch (ClientProtocolException e) {
				UIUtils.displayInformation(null, TITLE, e.getLocalizedMessage() + "\n " + command.formatRequest()); //$NON-NLS-1$
			}
		} catch (UnsupportedClassVersionError e) {
			return null;
		} finally {
			monitor.done();
		}

		return null;

	}

	/**
	 * @param gerritClient
	 * @param change_id
	 * @param monitor
	 * @return
	 * @throws MalformedURLException
	 */
	private IncludedInInfo queryIncludedIn(GerritClient gerritClient, String change_id, NullProgressMonitor monitor)
			throws MalformedURLException {
		try {
			monitor.beginTask("Executing query", IProgressMonitor.UNKNOWN);

			GetIncludedInCommand command = gerritClient.getIncludedIn(change_id);
			IncludedInInfo res = null;
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

	private RelatedChangesInfo queryRelatedChanges(GerritClient gerritClient, String change_id, String revision_id,
			IProgressMonitor monitor) {
		try {
			monitor.beginTask("Executing query", IProgressMonitor.UNKNOWN);

			GetRelatedChangesCommand command = gerritClient.getRelatedChanges(change_id, revision_id);

			RelatedChangesInfo res = null;
			try {
				res = command.call();
				return res;
			} catch (EGerritException e) {
				EGerritCorePlugin.logError(e.getMessage());
			} catch (ClientProtocolException e) {
				UIUtils.displayInformation(null, TITLE, e.getLocalizedMessage() + "\n " + command.formatRequest()); //$NON-NLS-1$
			}
		} catch (UnsupportedClassVersionError e) {
			return null;
		} finally {
			monitor.done();
		}

		return null;

	}

	private ReviewerInfo[] queryReviewers(GerritClient gerritClient, String change_id, IProgressMonitor monitor) {
		try {
			monitor.beginTask("Executing query", IProgressMonitor.UNKNOWN);

			ListReviewersCommand command = gerritClient.getReviewers(change_id);

			ReviewerInfo[] res = null;
			try {
				res = command.call();
				return res;
			} catch (EGerritException e) {
				EGerritCorePlugin.logError(e.getMessage());
			} catch (ClientProtocolException e) {
				UIUtils.displayInformation(null, TITLE, e.getLocalizedMessage() + "\n " + command.formatRequest()); //$NON-NLS-1$
			}
		} catch (UnsupportedClassVersionError e) {
			return null;
		} finally {
			monitor.done();
		}

		return null;

	}

	private ChangeInfo[] querySameTopic(GerritClient gerritClient, String topic, IProgressMonitor monitor)
			throws MalformedURLException {
		try {
			monitor.beginTask("Executing query", IProgressMonitor.UNKNOWN);

			ChangeInfo[] res = null;
			// Create query
			QueryChangesCommand command = gerritClient.queryChanges();
//				command.addOption(ChangeOption.LABELS);
//				command.addOption(ChangeOption.CURRENT_REVISION);
//				command.addOption(ChangeOption.CURRENT_FILES);
//				command.addLimit(101);
			command.addTopic(topic);

			try {
				res = command.call();
			} catch (EGerritException e) {
				EGerritCorePlugin.logError(e.getLocalizedMessage(), e);
			} catch (ClientProtocolException e) {
				UIUtils.displayInformation(null, TITLE, e.getLocalizedMessage() + "\n " + command.formatRequest()); //$NON-NLS-1$
			}
			return res;
		} catch (UnsupportedClassVersionError e) {
//			return new Status(IStatus.ERROR, GerritCorePlugin.PLUGIN_ID, "error", e);
			return null;
		} finally {
			monitor.done();
		}
	}

	private ChangeInfo[] queryConflictsWith(GerritClient gerritClient, String change_id, IProgressMonitor monitor)
			throws MalformedURLException {
		try {
			monitor.beginTask("Executing query", IProgressMonitor.UNKNOWN);

			ChangeInfo[] res = null;
			// Create query
			QueryChangesCommand command = gerritClient.queryChanges();
			command.addConflicts(change_id);

			try {
				res = command.call();
			} catch (EGerritException e) {
				EGerritCorePlugin.logError(e.getLocalizedMessage(), e);
			} catch (ClientProtocolException e) {
				UIUtils.displayInformation(null, TITLE, e.getLocalizedMessage() + "\n " + command.formatRequest()); //$NON-NLS-1$
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
	 * This method is the listener to add a reviewer or a group of reviewers
	 *
	 * @param buttonPlus
	 * @return SelectionAdapter
	 */
	private SelectionAdapter buttonPlusListener(final Button buttonPlus) {
		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);

				InputDialog inputDialog = new InputDialog(buttonPlus.getParent().getShell(), "EGerrit Reviewer input",
						"Add a reviewer or a review group name", "", null);

				if (inputDialog.open() != Window.OK) {
					return;
				}
				String reviewer = inputDialog.getValue().trim();

				if (!reviewer.isEmpty()) {
					AddReviewerCommand addReviewerCmd = getGerritClient().addReviewer(fChangeInfo.getChange_id());
					AddReviewerInput addReviewerInput = new AddReviewerInput();
					addReviewerInput.setReviewer(inputDialog.getValue());

					addReviewerCmd.setReviewerInput(addReviewerInput);

					AddReviewerResult reviewerCmdResult = null;
					reviewerCmdResult = addReviewerRequest(addReviewerCmd, reviewerCmdResult);
					if (reviewerCmdResult != null && reviewerCmdResult.getConfirm()) {
						//There is an error, just need to know if we should re-send or not
						if (!MessageDialog.openConfirm(buttonPlus.getParent().getShell(),
								"Process the list of reviewers", reviewerCmdResult.getError())) {
							return;
						}
						//Call again but with the flag confirm
						addReviewerInput.setConfirmed(true);
						reviewerCmdResult = addReviewerRequest(addReviewerCmd, reviewerCmdResult);
					}
					setReviewers(gerritClient);
				}
			}

			/**
			 * @param addReviewerCmd
			 * @param reviewerCmdResult
			 * @return
			 */
			private AddReviewerResult addReviewerRequest(AddReviewerCommand addReviewerCmd,
					AddReviewerResult reviewerCmdResult) {
				try {
					reviewerCmdResult = addReviewerCmd.call();
				} catch (EGerritException e3) {
					EGerritCorePlugin.logError(e3.getMessage());
				} catch (ClientProtocolException e3) {
					if (e3 instanceof HttpResponseException) {
						HttpResponseException httpException = (HttpResponseException) e3;
						if (httpException.getStatusCode() == 422) {
							String message = addReviewerCmd.getReviewerInput().getReviewer()
									+ " does not identify a registered user or group";
							UIUtils.displayInformation(null, TITLE, message);
						}
					} else {
						UIUtils.displayInformation(null, TITLE,
								e3.getLocalizedMessage() + "\n " + addReviewerCmd.formatRequest()); //$NON-NLS-1$
					}
				}
				return reviewerCmdResult;
			}
		};
	}

	/**
	 * This method is the listener to delete a reviewer
	 *
	 * @return MouseAdapter
	 */
	private MouseAdapter deleteReviewerListener() {
		return new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				ViewerCell viewerCell = tableReviewersViewer.getCell(new Point(e.x, e.y));
				String cellText = viewerCell.getText();
				if (viewerCell.getColumnIndex() == 0) {
					//Selected the first column, so we can send the delete option
					//Otherwise, do not delete
					ISelection selection = tableReviewersViewer.getSelection();
					if (selection instanceof IStructuredSelection) {

						IStructuredSelection structuredSelection = (IStructuredSelection) selection;

						Object element = structuredSelection.getFirstElement();

						if (element instanceof ReviewerInfo) {

							ReviewerInfo reviewerInfo = (ReviewerInfo) element;

							//Add a safety dialog to confirm the deletion

							if (!MessageDialog.openConfirm(tableReviewersViewer.getTable().getShell(),
									"EGerrit delete reviewer",
									"Are you sure you want to delete [ " + reviewerInfo.getName() + " ]")) {
								return;
							}

							DeleteReviewerCommand deleteReviewerCmd = getGerritClient().deleteReviewer(
									fChangeInfo.getChange_id(), String.valueOf(reviewerInfo.get_account_id()));

							String reviewerCmdResult = null;
							try {
								reviewerCmdResult = deleteReviewerCmd.call();
							} catch (EGerritException e3) {
								EGerritCorePlugin.logError(e3.getMessage());
							} catch (ClientProtocolException e3) {
								UIUtils.displayInformation(null, TITLE,
										e3.getLocalizedMessage() + "\n " + deleteReviewerCmd.formatRequest()); //$NON-NLS-1$
							}
							setReviewers(gerritClient);
						}
					}
				}
			}
		};
	}

	/************************************************************* */
	/*                                                             */
	/* Section adjust the DATA binding                             */
	/*                                                             */
	/************************************************************* */
	protected DataBindingContext sumGenDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();

		IObservableValue observeTextLblLblprojectObserveWidget = WidgetProperties.text().observe(genProjectData);
		IObservableValue projectbytesFChangeInfoObserveValue = BeanProperties.value("project").observe(fChangeInfo); //$NON-NLS-1$
		bindingContext.bindValue(observeTextLblLblprojectObserveWidget, projectbytesFChangeInfoObserveValue, null,
				null);
		//
//		IObservableValue observeTextLblChangeid_1ObserveWidget = WidgetProperties.text().observe(changeidData);
//		IObservableValue changeIdFChangeInfoObserveValue = BeanProperties.value("change_id").observe(fChangeInfo);
//		bindingContext.bindValue(observeTextLblChangeid_1ObserveWidget, changeIdFChangeInfoObserveValue, null, null);
//		//
		IObservableValue observeTextLblBranch_1ObserveWidget = WidgetProperties.text().observe(genBranchData);
		IObservableValue branchFChangeInfoObserveValue = BeanProperties.value("branch").observe(fChangeInfo); //$NON-NLS-1$
		bindingContext.bindValue(observeTextLblBranch_1ObserveWidget, branchFChangeInfoObserveValue, null, null);
		//
		IObservableValue observeTextText_2ObserveWidget = WidgetProperties.text().observe(genTopicData);
		IObservableValue topicFChangeInfoObserveValue = BeanProperties.value("topic").observe(fChangeInfo); //$NON-NLS-1$
		bindingContext.bindValue(observeTextText_2ObserveWidget, topicFChangeInfoObserveValue, null, null);
		//
		IObservableValue observeTextLblUpdated_1ObserveWidget = WidgetProperties.text().observe(genUpdatedData);
		IObservableValue updatedFChangeInfoObserveValue = BeanProperties.value("updated").observe(fChangeInfo); //$NON-NLS-1$
		bindingContext.bindValue(observeTextLblUpdated_1ObserveWidget, updatedFChangeInfoObserveValue, null,
				new UpdateValueStrategy().setConverter(DataConverter.gerritTimeConverter(formatTimeOut)));

		//
		IObservableValue observeTextGenStrategyDataObserveWidget = WidgetProperties.text().observe(genStrategyData);
		IObservableValue bytesMergeableinfogetSubmit_typeObserveValue = BeanProperties.value("submit_type").observe( //$NON-NLS-1$
				fMergeableInfo);
		bindingContext.bindValue( //
				observeTextGenStrategyDataObserveWidget, //
				bytesMergeableinfogetSubmit_typeObserveValue, //
				new UpdateValueStrategy()
						.setConverter(DataConverter.submitTypeConverter(fMergeableInfo.getSubmit_type())),
				new UpdateValueStrategy()
						.setConverter(DataConverter.submitTypeConverter(fMergeableInfo.getSubmit_type())));

		//
		IObservableValue observeTextGenMessageDataObserveWidget = WidgetProperties.text().observe(genMessageData);
		IObservableValue mergeableFMergeableInfoObserveValue = BeanProperties.value("mergeable") //$NON-NLS-1$
				.observe(fMergeableInfo);
		bindingContext.bindValue(observeTextGenMessageDataObserveWidget, mergeableFMergeableInfoObserveValue, null,
				new UpdateValueStrategy().setConverter(DataConverter.cannotMergeConverter()));

		return bindingContext;
	}

	protected void sumReviewerDataBindings() {
		ObservableListContentProvider contentProvider = new ObservableListContentProvider();
		tableReviewersViewer.setContentProvider(contentProvider);
		WritableList writeInfoList = new WritableList(fReviewers, ReviewerInfo.class);

		DataBindingContext bindingContext = new DataBindingContext();

		IObservableValue observeTextLblLblprojectObserveWidget = WidgetProperties.text().observe(genVoteData);
		IObservableValue projectbytesFChangeInfoObserveValue = BeanProperties.value("labels").observe(fChangeInfo); //$NON-NLS-1$
		bindingContext.bindValue(observeTextLblLblprojectObserveWidget, projectbytesFChangeInfoObserveValue, null,
				new UpdateValueStrategy().setConverter(DataConverter.reviewersVoteConverter()));

		IObservableMap[] observeMaps = Properties.observeEach(contentProvider.getKnownElements(),
				BeanProperties.values(new String[] { "name" })); //$NON-NLS-1$

		ViewerSupport.bind(tableReviewersViewer, writeInfoList, BeanProperties.values(new String[] { "name" })); //$NON-NLS-1$
		tableReviewersViewer.setLabelProvider(new ReviewersTableLabelProvider(observeMaps));
	}

	protected DataBindingContext sumIncludedDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();

		IObservableValue observeTextLblLblprojectObserveWidget = WidgetProperties.text().observe(incBranchesData);
		if (fIncludedIn != null) {
			IObservableValue projectbytesFChangeInfoObserveValue = BeanProperties.value("branches") //$NON-NLS-1$
					.observe(fIncludedIn);
			bindingContext.bindValue(observeTextLblLblprojectObserveWidget, projectbytesFChangeInfoObserveValue, null,
					new UpdateValueStrategy().setConverter(DataConverter.stringListConverter()));

			//
			IObservableValue observeTextLblChangeid_1ObserveWidget = WidgetProperties.text()
					.observe(includedInTagsData);
			IObservableValue changeIdFChangeInfoObserveValue = BeanProperties.value("tags").observe(fIncludedIn); //$NON-NLS-1$
			bindingContext.bindValue(observeTextLblChangeid_1ObserveWidget, changeIdFChangeInfoObserveValue, null,
					new UpdateValueStrategy().setConverter(DataConverter.stringListConverter()));
		}
		return bindingContext;
	}

	protected void sumSameTopicDataBindings() {
		ObservableListContentProvider contentProvider = new ObservableListContentProvider();
		tableSameTopicViewer.setContentProvider(contentProvider);

		WritableList writeInfoList = new WritableList(fSameTopicChangeInfo, ChangeInfo.class);

		IObservableMap[] observeMaps = Properties.observeEach(contentProvider.getKnownElements(),
				BeanProperties.values(new String[] { "change_id" })); //$NON-NLS-1$

		ViewerSupport.bind(tableSameTopicViewer, writeInfoList, BeanProperties.values(new String[] { "change_id" })); //$NON-NLS-1$
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
				BeanProperties.values(new String[] { "changes" })); //$NON-NLS-1$

		ViewerSupport.bind(tableRelatedChangesViewer, writeInfoList, BeanProperties.values(new String[] { "changes" })); //$NON-NLS-1$
		tableRelatedChangesViewer.setLabelProvider(new RelatedChangesTableLabelProvider(observeMaps));
	}

	protected void sumConflictWithDataBindings() {
		ObservableListContentProvider contentProvider = new ObservableListContentProvider();
		tableConflictsWithViewer.setContentProvider(contentProvider);

		WritableList writeInfoList = new WritableList(fConflictsWithChangeInfo, ChangeInfo.class);

		IObservableMap[] observeMaps = Properties.observeEach(contentProvider.getKnownElements(),
				BeanProperties.values(new String[] { "change_id" })); //$NON-NLS-1$

		ViewerSupport.bind(tableConflictsWithViewer, writeInfoList,
				BeanProperties.values(new String[] { "change_id" })); //$NON-NLS-1$
		tableConflictsWithViewer.setLabelProvider(new ConflictWithTableLabelProvider(observeMaps));
	}

	/**
	 * This method set this tab view with the current revision;
	 *
	 * @param GerritClient
	 *            gerritClient
	 * @param ChangeInfo
	 *            changeInfo
	 */
	public void setTabs(GerritClient gerritClient, ChangeInfo changeInfo) {
		setGerritClient(gerritClient);
		//Queries to fill the Review tab data
		setSameTopic(gerritClient, changeInfo);

		setConflictsWith(gerritClient, changeInfo);

		setMergeable(gerritClient, changeInfo);

		setReviewers(gerritClient);
		setIncludedIn(gerritClient);

		//Need the current Revision set before setting the RelatedChanges
		setRelatedChanges(gerritClient);

	}

	/**
	 * @return the project string
	 */
	public String getProject() {
		return genProjectData.getText();
	}

	/**
	 * @return the gerritClient
	 */
	private GerritClient getGerritClient() {
		return gerritClient;
	}

	/**
	 * @param gerritClient
	 *            the gerritClient to set
	 */
	private void setGerritClient(GerritClient gerritClient) {
		this.gerritClient = gerritClient;
	}

}
