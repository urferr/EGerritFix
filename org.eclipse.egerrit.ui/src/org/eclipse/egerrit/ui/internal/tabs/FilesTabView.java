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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.TreeMap;

import org.apache.http.client.ClientProtocolException;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.property.Properties;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.egerrit.core.EGerritCorePlugin;
import org.eclipse.egerrit.core.GerritClient;
import org.eclipse.egerrit.core.command.DeleteReviewedCommand;
import org.eclipse.egerrit.core.command.GetReviewedFilesCommand;
import org.eclipse.egerrit.core.command.ListCommentsCommand;
import org.eclipse.egerrit.core.command.ListDraftsCommand;
import org.eclipse.egerrit.core.command.SetReviewedCommand;
import org.eclipse.egerrit.core.exception.EGerritException;
import org.eclipse.egerrit.core.rest.ChangeInfo;
import org.eclipse.egerrit.core.rest.CommentInfo;
import org.eclipse.egerrit.core.rest.FetchInfo;
import org.eclipse.egerrit.core.rest.FileInfo;
import org.eclipse.egerrit.core.rest.RevisionInfo;
import org.eclipse.egerrit.core.utils.DisplayFileInfo;
import org.eclipse.egerrit.ui.editors.OpenCompareEditor;
import org.eclipse.egerrit.ui.internal.table.UIFilesTable;
import org.eclipse.egerrit.ui.internal.table.provider.ComboPatchSetLabelProvider;
import org.eclipse.egerrit.ui.internal.table.provider.FileTableLabelProvider;
import org.eclipse.egerrit.ui.internal.utils.UIUtils;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ViewerSupport;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
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

/**
 * This class is used in the editor to handle the Gerrit history view
 *
 * @since 1.0
 */
public class FilesTabView extends Observable implements PropertyChangeListener {

	// used to fire events of registered properties
	private transient PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	private static final String BASE = "Base"; //$NON-NLS-1$

	private static final String WORKSPACE = "Workspace"; //$NON-NLS-1$

	private static final String PATCHSET = "Patch Sets ";

	private static final String SEPARATOR = "/"; //$NON-NLS-1$

	private final String TITLE = "Gerrit Server ";

	private final String TOTAL = "Total changes: ";

	private final String DRAFTS = "Drafts: ";

	private final String COMMENTS = "Comments: ";

	private RevisionInfo fCurrentRevision = null;

	private Map<String, RevisionInfo> fRevisions = new HashMap<String, RevisionInfo>();

	private Map<String, DisplayFileInfo> fFilesDisplay = new HashMap<String, DisplayFileInfo>();

	private GerritClient gerritClient;

	private ChangeInfo fChangeInfo;

	private TableViewer tableFilesViewer;

	private Label lblDrafts;

	private Label lblComments;

	private Label lblTotal;

	private Combo comboDiffAgainst;

	private Label lblPatchSet;

	private ComboViewer comboPatchsetViewer;

	// ------------------------------------------------------------------------
	// Constructor and life cycle
	// ------------------------------------------------------------------------

	/**
	 * @param tabFolder
	 * @param ChangeInfo
	 *            changeInfo
	 */
	public void create(GerritClient gerritClient, TabFolder tabFolder, ChangeInfo changeInfo) {
		this.gerritClient = gerritClient;
		fChangeInfo = changeInfo;
		filesTab(tabFolder);
	}

	private void filesTab(final TabFolder tabFolder) {
		final TabItem tbtmFiles = new TabItem(tabFolder, SWT.NONE);
		tbtmFiles.setText("Files");

		final Group filesGroup = new Group(tabFolder, SWT.NONE);
		tbtmFiles.setControl(filesGroup);

		GridData grid = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		filesGroup.setLayoutData(grid);
		filesGroup.setLayout(new GridLayout(1, false));

		final Composite composite = new Composite(filesGroup, SWT.NONE);
		GridLayout gridLayoutComp = new GridLayout(10, false);
		gridLayoutComp.verticalSpacing = 10;
		composite.setLayout(gridLayoutComp);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 10, 1));

		composite.addControlListener(new ControlListener() {

			@Override
			public void controlResized(ControlEvent e) {
				composite.pack();
			}

			@Override
			public void controlMoved(ControlEvent e) {
				// ignore

			}
		});
		Point pt = UIUtils.computeFontSize(composite);

		lblPatchSet = new Label(composite, SWT.NONE);
		GridData gd_lblPatchSet = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_lblPatchSet.verticalIndent = 5;
		lblPatchSet.setLayoutData(gd_lblPatchSet);
		lblPatchSet.setText("Patch Sets (    /    )");

		comboPatchsetViewer = new ComboViewer(composite, SWT.DROP_DOWN | SWT.READ_ONLY);

		GridData gd_comboPatchSet = new GridData(SWT.LEFT, SWT.TOP, false, false, 3, 1);
		gd_comboPatchSet.verticalIndent = 5;
		int numCharPatchSet = 55;
		gd_comboPatchSet.minimumWidth = numCharPatchSet * pt.x;
		gd_comboPatchSet.widthHint = numCharPatchSet * pt.x;

		comboPatchsetViewer.getCombo().setLayoutData(gd_comboPatchSet);
		comboPatchsetViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				ISelection selection = event.getSelection();
				if (selection instanceof StructuredSelection) {
					IStructuredSelection structSel = (IStructuredSelection) selection;
					if (structSel.getFirstElement() instanceof RevisionInfo) {

						setPatchSetLabelCombo();
						setDiffAgainstCombo();
						RevisionInfo revInfo = (RevisionInfo) structSel.getFirstElement();

						setCurrentRevision(revInfo);
						fillFiles(revInfo.getFiles());
						setChanged();
						notifyObservers();

						setListCommentsPerPatchSet(gerritClient, fChangeInfo.getId(), revInfo.getCommit().getCommit());
						displayFilesTable();

					}
				}
				//selection.

			}
		});

		Label lblDiffAgainst = new Label(composite, SWT.NONE);
		GridData gd_DiffAgainst = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 2, 1);
		gd_DiffAgainst.verticalIndent = 5;
		gd_DiffAgainst.horizontalIndent = 15;
		lblDiffAgainst.setLayoutData(gd_DiffAgainst);
		lblDiffAgainst.setText("Diff against:");

		comboDiffAgainst = new Combo(composite, SWT.NONE);
		GridData gd_combo = new GridData(SWT.FILL, SWT.TOP, false, false, 2, 1);
		gd_combo.verticalIndent = 5;
		int numChar = 17;
		gd_combo.minimumWidth = numChar * pt.x;
		gd_combo.widthHint = numChar * pt.x;
		comboDiffAgainst.setLayoutData(gd_combo);
		System.err.println("Combo mini width = " + (numChar * pt.x));

		Button btnPublish = new Button(composite, SWT.NONE);
		GridData gd_btnPublish = new GridData(SWT.RIGHT, SWT.TOP, false, false, 1, 1);
		gd_btnPublish.verticalIndent = 5;
		gd_btnPublish.horizontalIndent = 25;
		btnPublish.setLayoutData(gd_btnPublish);
		btnPublish.setText("Publish");

		Button btnDelete = new Button(composite, SWT.NONE);
		GridData gd_btnDelete = new GridData(SWT.CENTER, SWT.TOP, false, false, 1, 1);
		gd_btnDelete.verticalIndent = 5;
		gd_btnDelete.horizontalIndent = 15;
		btnDelete.setLayoutData(gd_btnDelete);
		btnDelete.setText("Delete");

		Label lblSummary = new Label(composite, SWT.NONE);
		GridData gd_lblSummary = new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1);
		lblSummary.setLayoutData(gd_lblSummary);
		lblSummary.setText("Summary:");

		lblTotal = new Label(composite, SWT.NONE);
		GridData gd_lblTotal = new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1);
		gd_lblTotal.minimumWidth = 12 * pt.x;
		lblTotal.setLayoutData(gd_lblTotal);
		lblTotal.setText("total");
		lblTotal.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				String old = lblTotal.getText();
				String newCount = ""; //$NON-NLS-1$
				int lineInserted = 0;
				int lineDeleted = 0;
				if (!fFilesDisplay.isEmpty()) {
					Iterator<DisplayFileInfo> itr1 = fFilesDisplay.values().iterator();
					while (itr1.hasNext()) {
						DisplayFileInfo fileInfo = itr1.next();
						lineInserted += fileInfo.getLinesInserted();
						lineDeleted += fileInfo.getLinesDeleted();
					}
					StringBuilder sb = new StringBuilder();
					sb.append(TOTAL);
					sb.append("+"); //$NON-NLS-1$
					sb.append(Integer.toString(lineInserted));
					sb.append("/"); //$NON-NLS-1$
					sb.append("-"); //$NON-NLS-1$
					sb.append(Integer.toString(lineDeleted));
					newCount = sb.toString();
				}
				if (!old.equals(newCount)) {
					lblTotal.setText(newCount);
					lblTotal.pack();
				}
			}
		});

		lblDrafts = new Label(composite, SWT.NONE);
		GridData gd_lblDrafts = new GridData(SWT.CENTER, SWT.TOP, false, false, 2, 1);
		gd_lblDrafts.horizontalIndent = 10;
		lblDrafts.setLayoutData(gd_lblDrafts);
		lblDrafts.setText("drafts:");
		lblDrafts.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				String old = lblDrafts.getText();
				String newCount = ""; //$NON-NLS-1$
				StringBuilder sb = new StringBuilder();

				if (!fFilesDisplay.isEmpty()) {
					Iterator<DisplayFileInfo> itr1 = fFilesDisplay.values().iterator();
					int numDrafts = 0;
					sb.append(DRAFTS);
					while (itr1.hasNext()) {
						DisplayFileInfo displayFileInfo = itr1.next();
						if (displayFileInfo.getDraftComments() != null) {
							numDrafts += displayFileInfo.getDraftComments().size();
						}
					}
					if (numDrafts > 0) {
						sb.append(Integer.toString(numDrafts));
					}
				}
				newCount = sb.toString();
				if (!old.equals(newCount)) {
					lblDrafts.setText(newCount);
					lblDrafts.pack();
				}
			}
		});

		lblComments = new Label(composite, SWT.NONE);
		GridData gd_lblComments = new GridData(SWT.LEFT, SWT.TOP, false, false, 2, 1);
		lblComments.setLayoutData(gd_lblComments);
		lblComments.setText("comments:");
		lblComments.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				String old = lblComments.getText();
				String newCount = ""; //$NON-NLS-1$
				StringBuilder sb = new StringBuilder();

				if (!fFilesDisplay.isEmpty()) {
					Iterator<DisplayFileInfo> itr1 = fFilesDisplay.values().iterator();
					int numComment = 0;
					sb.append(COMMENTS);

					while (itr1.hasNext()) {
						DisplayFileInfo displayFileInfo = itr1.next();
						if (displayFileInfo.getNewComments() != null) {
							numComment += displayFileInfo.getNewComments().size();
						}
					}
					if (numComment > 0) {
						sb.append(Integer.toString(numComment));
					}
				}
				newCount = sb.toString();
				if (!old.equals(newCount)) {
					lblComments.setText(newCount);
					lblComments.pack();
				}
			}
		});

		composite.pack();

		final SashForm sashForm = new SashForm(filesGroup, SWT.VERTICAL);
		sashForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		UIFilesTable tableUIFiles = new UIFilesTable();
		tableUIFiles.createTableViewerSection(sashForm, grid);

		tableFilesViewer = tableUIFiles.getViewer();
		tableFilesViewer.getTable();

		tableFilesViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				IStructuredSelection sel = (IStructuredSelection) event.getSelection();
				Object element = sel.getFirstElement();
				if (element instanceof FileInfo) {
					FileInfo selectedFile = (FileInfo) element;
					OpenCompareEditor compareEditor;
					compareEditor = new OpenCompareEditor(gerritClient, fChangeInfo);
					String diffSource = comboDiffAgainst.getText();
					if (diffSource.equals(WORKSPACE)) {
						compareEditor.compareAgainstWorkspace(selectedFile);
					} else if (diffSource.equals(BASE)) {
						compareEditor.compareAgainstBase(fChangeInfo.getProject(), selectedFile);
					} else {
						RevisionInfo rev = getRevisionInfoByNumber(diffSource);
						if (rev != null) {
							compareEditor.compareTwoRevisions(rev.getFiles().get(selectedFile.getold_path()),
									selectedFile);
							return;
						}
						MessageDialog.openError(null, "Can not open compare editor",
								"The compare editor can not yet show difference with " + diffSource);
					}
					setReviewedFlag(element);
				}
			}

		});

		if (!gerritClient.getRepository().getServerInfo().isAnonymous()) {
			tableFilesViewer.getTable().addMouseListener(toggleReviewedStateListener());
		}

		sashForm.setWeights(new int[] { 100 });

		//Set the binding for this section
		filesTabDataBindings(tableFilesViewer);
	}

	private void setReviewedFlag(Object element) {
		if (!gerritClient.getRepository().getServerInfo().isAnonymous()) {
			DisplayFileInfo fileInfo = (DisplayFileInfo) element;
			if (!fileInfo.getReviewed()) {
				SetReviewedCommand command = gerritClient.setReviewed(fChangeInfo.getId(),
						fCurrentRevision.getCommit().getCommit(), fileInfo.getold_path());
				try {
					command.call();
				} catch (EGerritException | ClientProtocolException ex) {
					UIUtils.displayInformation(null, TITLE, ex.getLocalizedMessage() + "\n " + command.formatRequest());
				}

				fileInfo.setReviewed(true);

				tableFilesViewer.refresh();
			}
		}
	}

	/**
	 * This method is the listener to toggle a file's reviewed state
	 *
	 * @return MouseAdapter
	 */
	private MouseAdapter toggleReviewedStateListener() {
		return new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				ViewerCell viewerCell = tableFilesViewer.getCell(new Point(e.x, e.y));
				if (viewerCell.getColumnIndex() == 0) {
					//Selected the first column, so we can send the delete option
					//Otherwise, do not delete
					ISelection selection = tableFilesViewer.getSelection();
					if (selection instanceof IStructuredSelection) {

						IStructuredSelection structuredSelection = (IStructuredSelection) selection;

						Object element = structuredSelection.getFirstElement();

						DisplayFileInfo fileInfo = (DisplayFileInfo) element;
						toggleReviewed(fileInfo);

					}
				}
			}

		};
	}

	private void toggleReviewed(DisplayFileInfo fileInfo) {
		if (fileInfo.getReviewed()) {
			DeleteReviewedCommand command = gerritClient.deleteReviewed(fChangeInfo.getId(),
					fCurrentRevision.getCommit().getCommit(), fileInfo.getold_path());
			try {
				command.call();
				fileInfo.setReviewed(false);
			} catch (EGerritException | ClientProtocolException ex) {
				UIUtils.displayInformation(null, TITLE, ex.getLocalizedMessage() + "\n " + command.formatRequest());
			}
		} else {
			SetReviewedCommand command = gerritClient.setReviewed(fChangeInfo.getId(),
					fCurrentRevision.getCommit().getCommit(), fileInfo.getold_path());
			try {
				command.call();
			} catch (EGerritException | ClientProtocolException ex) {
				UIUtils.displayInformation(null, TITLE, ex.getLocalizedMessage() + "\n " + command.formatRequest());
			}
			fileInfo.setReviewed(true);
		}
		tableFilesViewer.refresh();
	}

	protected void filesTabDataBindings(TableViewer tableFilesViewer) {

		//Set the PatchSet Combo viewer
		if (comboPatchsetViewer != null) {
			ObservableListContentProvider contentPatchSetProvider = new ObservableListContentProvider();
			comboPatchsetViewer.setContentProvider(contentPatchSetProvider);
			IObservableMap[] observePatchSetMaps = Properties.observeEach(contentPatchSetProvider.getKnownElements(),
					BeanProperties.values(new String[] { "_number", "commit" }));

			comboPatchsetViewer.setLabelProvider(new ComboPatchSetLabelProvider(observePatchSetMaps));
			// input must be a List
			comboPatchsetViewer.setInput(null);

		}

		//Set the FilesViewer
		if (tableFilesViewer != null) {
			ObservableListContentProvider contentProvider = new ObservableListContentProvider();
			tableFilesViewer.setContentProvider(contentProvider);

			WritableList writeInfoList = new WritableList(fFilesDisplay.values(), DisplayFileInfo.class);

//			IObservableMap[] observeMaps = Properties.observeEach(contentProvider.getKnownElements(),
//					BeanProperties.values(new String[] { "fileInfo", "comments" }));
			IObservableMap[] observeMaps = Properties.observeEach(contentProvider.getKnownElements(),
					BeanProperties.values(new String[] { "fileInfo", "comments", "newComments", "draftComments" }));

//			ViewerSupport.bind(tableFilesViewer, writeInfoList, BeanProperties.values(new String[] { "fileInfo" }));
			ViewerSupport.bind(tableFilesViewer, writeInfoList,
					BeanProperties.values(new String[] { "fileInfo", "comments", "newComments", "draftComments" }));

			tableFilesViewer.setLabelProvider(new FileTableLabelProvider(observeMaps));
		}
	}

	/**
	 * Fill the combo box with all patch set to compare with except the selected one. Also, add the Base and the
	 * Workspace which is the default one
	 */
	private void setDiffAgainstCombo() {
		List<String> listPatch = new ArrayList<String>();
		//Always start the list with the following items
		listPatch.add(BASE);
		listPatch.add(WORKSPACE);
		//Find the current Patchset selection
		ISelection selected = comboPatchsetViewer.getSelection();

		if (!selected.isEmpty()) {
			int psSelected = -1;
			if (selected instanceof StructuredSelection) {
				Object element = ((IStructuredSelection) selected).getFirstElement();
				if (element instanceof RevisionInfo) {
					RevisionInfo selectInfo = (RevisionInfo) element;
					psSelected = selectInfo.getNumber();
				}
			}
			//Display the list of patchset without the current one
			List<RevisionInfo> listRevision = new ArrayList<RevisionInfo>(fRevisions.values());
			Collections.sort(listRevision, new Comparator<RevisionInfo>() {

				@Override
				public int compare(RevisionInfo rev1, RevisionInfo rev2) {
					return rev2.getNumber() - rev1.getNumber();
				}
			});

			Iterator<RevisionInfo> it = listRevision.iterator();
			while (it.hasNext()) {
				RevisionInfo rev = it.next();
				if (rev.getNumber() != psSelected) {
					listPatch.add(Integer.toString(rev.getNumber()));
				}
			}

			comboDiffAgainst.setItems(listPatch.toArray(new String[0]));
			comboDiffAgainst.select(0);//Select the Base to compare with by default

		}
	}

	/**
	 * - Fill the label showing which patch set is selected from the total list of patchset
	 */
	private void setPatchSetLabelCombo() {
		//Find the current PatchSet selection
		ISelection selected = comboPatchsetViewer.getSelection();
		if (!selected.isEmpty()) {
			int psSelected = -1;
			if (selected instanceof StructuredSelection) {
				Object element = ((IStructuredSelection) selected).getFirstElement();
				if (element instanceof RevisionInfo) {
					RevisionInfo selectInfo = (RevisionInfo) element;
					psSelected = selectInfo.getNumber();
				}
			}
			StringBuilder sb = new StringBuilder();
			sb.append(PATCHSET);
			sb.append(psSelected);
			sb.append(SEPARATOR);
			sb.append(fRevisions.size());

			//Display the selected patchSet
			lblPatchSet.setText(sb.toString());

		}
	}

	/**
	 * @param map
	 */
	private void fillFiles(Map<String, FileInfo> map) {
		fFilesDisplay.clear();
		//Store the files
		Map<String, DisplayFileInfo> displayFilesMap = new HashMap<String, DisplayFileInfo>();

		if (map != null) {
			//Fill the Files table
			Iterator<Map.Entry<String, FileInfo>> fileIter = map.entrySet().iterator();
			while (fileIter.hasNext()) {
				Entry<String, FileInfo> entryFile = fileIter.next();
				DisplayFileInfo displayFileInfo = new DisplayFileInfo(entryFile.getValue());
				displayFileInfo.setOld_path(entryFile.getKey());
				displayFilesMap.put(entryFile.getKey(), displayFileInfo);
			}
			setFilesDisplay(displayFilesMap);
		}
	}

	private void setFilesDisplay(Map<String, DisplayFileInfo> displayFilesMap) {

		//Sort the file according to the file path
		Map<String, DisplayFileInfo> sortedMap = new TreeMap<String, DisplayFileInfo>(new Comparator<String>() {

			@Override
			public int compare(String key1, String key2) {
				return key1.compareTo(key2);
			}
		});

		sortedMap.putAll(displayFilesMap);
		firePropertyChange("fFilesDisplay", this.fFilesDisplay, this.fFilesDisplay = sortedMap); //$NON-NLS-1$

	}

	/**
	 * Set the data structure to fill the patch set table and display it.
	 */
	private void setAllPatchSet() {
		WritableList writeInfoList;

		List<RevisionInfo> listRevision = new ArrayList<RevisionInfo>(fRevisions.values());
		Collections.sort(listRevision, new Comparator<RevisionInfo>() {

			@Override
			public int compare(RevisionInfo rev1, RevisionInfo rev2) {
				return rev2.getNumber() - rev1.getNumber();
			}
		});
		writeInfoList = new WritableList(listRevision, List.class);

		if (comboPatchsetViewer != null) {
			comboPatchsetViewer.setInput(writeInfoList);
			comboPatchsetViewer.getCombo().select(0); //select by default the first element
			//Will need the following also
			setDiffAgainstCombo();
			setPatchSetLabelCombo();
			setCurrentRevision(listRevision.get(0));
			fillFiles(listRevision.get(0).getFiles());
			setChanged();
			notifyObservers();

			setListCommentsPerPatchSet(gerritClient, fChangeInfo.getId(), fCurrentRevision.getCommit().getCommit());
			displayFilesTable();
		}
	}

	/**
	 * This method fills the commit Id into the RevisionInfo structure since only the current revision patchset is set.
	 */
	private void setPatchSetCommitId() {
		Iterator<Map.Entry<String, RevisionInfo>> itr1 = fRevisions.entrySet().iterator();
		while (itr1.hasNext()) {
			Entry<String, RevisionInfo> entry = itr1.next();
			if (entry.getValue().getCommit() != null) {
				entry.getValue().getCommit().setCommit(entry.getKey());
			}
		}
	}

	/**
	 * Fill the Files table
	 */
	private void displayFilesTable() {
		WritableList writeInfoList;
		Iterator<Entry<String, DisplayFileInfo>> itrFileDisplay = fFilesDisplay.entrySet().iterator();
		while (itrFileDisplay.hasNext()) {
			Entry<String, DisplayFileInfo> entry1 = itrFileDisplay.next();

			entry1.getValue().addPropertyChangeListener("fileInfo", this);
			entry1.getValue().setOld_path(entry1.getKey());
			entry1.getValue().addPropertyChangeListener("DisplayFileInfo.newComments", this);
			entry1.getValue().addPropertyChangeListener("DisplayFileInfo.draftComments", this);
		}

		//Set the data fields in the Files Tab
		setFileTabFields();

		writeInfoList = new WritableList(fFilesDisplay.values(), DisplayFileInfo.class);
		tableFilesViewer.setInput(writeInfoList);
		tableFilesViewer.refresh();
	}

	/**
	 * Fill the data fields included in the Files Tab
	 */
	private void setFileTabFields() {
		lblDrafts.redraw();
		lblComments.redraw();
		lblTotal.redraw();
	}

	/**
	 * @param GerritClient
	 *            gerritClient
	 * @param String
	 *            changeId
	 * @param String
	 *            revisionId
	 */

	private void setListCommentsPerPatchSet(GerritClient gerritClient, String changeId, String revisionId) {

		Iterator<DisplayFileInfo> displayFile = fFilesDisplay.values().iterator();
		Map<String, ArrayList<CommentInfo>> mapCommentInfo = queryComments(gerritClient, changeId, revisionId,
				new NullProgressMonitor());

		//Fill the Files table
		while (displayFile.hasNext() && mapCommentInfo != null) {
			DisplayFileInfo displayFileInfo = displayFile.next();
			displayFileInfo.setCurrentUser(gerritClient.getRepository().getCredentials().getUsername());
			Iterator<Map.Entry<String, ArrayList<CommentInfo>>> commentIter = mapCommentInfo.entrySet().iterator();
			while (commentIter.hasNext()) {
				Entry<String, ArrayList<CommentInfo>> entryComment = commentIter.next();
				//Add comments associated to the file
				if (displayFileInfo.getold_path().equals(entryComment.getKey())) {
					displayFileInfo.setNewComments(entryComment.getValue());
					break;
				}
			}
			// enable reviewed file status
			if (!gerritClient.getRepository().getServerInfo().isAnonymous()) {
				String[] reviewedFiles = queryVerified(gerritClient, changeId, revisionId, new NullProgressMonitor());
				List<String> reviewedFilesList = Arrays.asList(reviewedFiles);
				for (String e : reviewedFilesList) {
					if (e.compareTo(displayFileInfo.getold_path()) == 0) {
						displayFileInfo.setReviewed(true);
					}
				}
			}
		}

		Iterator<DisplayFileInfo> displayFile2 = fFilesDisplay.values().iterator();
		Map<String, ArrayList<CommentInfo>> mapDraftCommentInfo = queryDraftComments(gerritClient, changeId, revisionId,
				new NullProgressMonitor());

		//Fill the Files table
		while (displayFile2.hasNext() && mapDraftCommentInfo != null) {
			DisplayFileInfo displayFileInfo = displayFile2.next();
			Iterator<Map.Entry<String, ArrayList<CommentInfo>>> commentIter = mapDraftCommentInfo.entrySet().iterator();
			while (commentIter.hasNext()) {
				Entry<String, ArrayList<CommentInfo>> entryComment = commentIter.next();
				//Add comments associated to the file
				if (displayFileInfo.getold_path().equals(entryComment.getKey())) {
					displayFileInfo.setDraftComments(entryComment.getValue());
					break;
				}
			}
		}

	}

	/***************************************************************/
	/*                                                             */
	/* Section to QUERY the data structure                         */
	/*                                                             */
	/************************************************************* */
	private Map<String, ArrayList<CommentInfo>> queryComments(GerritClient gerrit, String change_id, String revision_id,
			IProgressMonitor monitor) {
		try {
			monitor.beginTask("Executing query", IProgressMonitor.UNKNOWN);

			ListCommentsCommand command = gerrit.getListComments(change_id, revision_id);

			Map<String, ArrayList<CommentInfo>> res = null;
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

	/***************************************************************/
	/*                                                             */
	/* Section to QUERY the files that were reviewed               */
	/*                                                             */
	/************************************************************* */
	private String[] queryVerified(GerritClient gerrit, String change_id, String revision_id,
			IProgressMonitor monitor) {
		try {
			monitor.beginTask("Executing query", IProgressMonitor.UNKNOWN);

			GetReviewedFilesCommand command = gerrit.getReviewed(change_id, revision_id);

			String[] res = null;
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

	private Map<String, ArrayList<CommentInfo>> queryDraftComments(GerritClient gerrit, String change_id,
			String revision_id, IProgressMonitor monitor) {

		try {
			if (gerrit.getRepository().getServerInfo().isAnonymous()) {
				return null;
			}
			monitor.beginTask("Executing query", IProgressMonitor.UNKNOWN);

			ListDraftsCommand command = gerrit.listDraftsComments(change_id, revision_id);
			Map<String, ArrayList<CommentInfo>> res = null;
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
	 * @return the revisions Map<String, RevisionInfo>
	 */
	public Map<String, RevisionInfo> getRevisions() {
		return fRevisions;
	}

	/**
	 * @param Map
	 *            <String, RevisionInfo> revisions the revisions to set
	 */
	private void setRevisions(Map<String, RevisionInfo> revisions) {
		this.fRevisions = revisions;
	}

	/**
	 * @param GerritClient
	 */
	public void setGerritClient(GerritClient gerrit) {
		this.gerritClient = gerrit;
	}

	/**
	 * @return the tableFilesViewer
	 */
	public TableViewer getTableFilesViewer() {
		return tableFilesViewer;
	}

	/**
	 * This method set this tab view with the current revision among the list of revisions
	 *
	 * @param Map
	 *            <String, RevisionInfo> revisions
	 * @param String
	 *            currentRevision
	 */
	public void setTabs(Map<String, RevisionInfo> revisions, String currentRevision) {
		setRevisions(revisions);
		setCurrentRevision(getRevisions().get(currentRevision));
		//Fill the commitID for each revision
		setPatchSetCommitId();

		//Set the Files tab data structure
		fillFiles(fCurrentRevision.getFiles());

		//Sort the patch sets and display it in the table
		setAllPatchSet();

	}

	private void setCurrentRevision(RevisionInfo revisionInfo) {
		fCurrentRevision = revisionInfo;
	}

	/**
	 * @return current RevisionInfo
	 */
	public RevisionInfo getCurrentRevision() {
		return fCurrentRevision;
	}

	/**
	 * Notify the registered observer
	 */
	@Override
	public void notifyObservers() {
		super.notifyObservers();
	}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		// ignore

	}

	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
	}

	protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
	}

	/**
	 * @return the patchset selection as a string
	 */
	public String getSelectedPatchSet() {
		String psSelected = null;
		ISelection selected = comboPatchsetViewer.getSelection();
		if (!selected.isEmpty()) {
			psSelected = findSelectedPatchsetRef(selected, psSelected);
		}
		return psSelected;
	}

	/**
	 * @return the latest patchset as a string
	 */
	public String getLatestPatchSet() {
		RevisionInfo topmost = (RevisionInfo) comboPatchsetViewer.getElementAt(0);
		return topmost.getId();

	}

	/**
	 * @param selected
	 * @param psSelected
	 * @return String The patch set reference when a patch set is selected
	 */
	private String findSelectedPatchsetRef(ISelection selected, String psSelected) {
		if (selected instanceof StructuredSelection) {
			Object element = ((IStructuredSelection) selected).getFirstElement();
			if (element instanceof RevisionInfo) {
				RevisionInfo selectInfo = (RevisionInfo) element;
				Map<String, FetchInfo> fetchInfoMap = selectInfo.getFetch();
				FetchInfo fetchInfo = fetchInfoMap.get("git"); //$NON-NLS-1$
				if (fetchInfo != null) {
					psSelected = fetchInfo.getRef().toString();
				} else {
					fetchInfo = fetchInfoMap.get("ssh"); //$NON-NLS-1$
					if (fetchInfo != null) {
						psSelected = fetchInfo.getRef().toString();
					} else {
						fetchInfo = fetchInfoMap.get("http"); //$NON-NLS-1$
						if (fetchInfo != null) {
							psSelected = fetchInfo.getRef().toString();
						}
					}
				}
			}
		}
		return psSelected;
	}

	private RevisionInfo getRevisionInfoByNumber(String number) {
		int revisionNumber = Integer.valueOf(number);
		Collection<RevisionInfo> revisions = fRevisions.values();
		for (RevisionInfo candidate : revisions) {
			if (candidate.getNumber() == revisionNumber) {
				return candidate;
			}
		}
		return null;
	}
}
