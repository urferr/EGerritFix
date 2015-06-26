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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observable;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.map.WritableMap;
import org.eclipse.core.databinding.property.Properties;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.egerrit.core.EGerritCorePlugin;
import org.eclipse.egerrit.core.Gerrit;
import org.eclipse.egerrit.core.GerritRepository;
import org.eclipse.egerrit.core.command.ListCommentsCommand;
import org.eclipse.egerrit.core.exception.EGerritException;
import org.eclipse.egerrit.core.rest.ChangeInfo;
import org.eclipse.egerrit.core.rest.CommentInfo;
import org.eclipse.egerrit.core.rest.FetchInfo;
import org.eclipse.egerrit.core.rest.FileInfo;
import org.eclipse.egerrit.core.rest.RevisionInfo;
import org.eclipse.egerrit.core.utils.DisplayFileInfo;
import org.eclipse.egerrit.ui.editors.OpenCompareEditor;
import org.eclipse.egerrit.ui.internal.table.UIFilesTable;
import org.eclipse.egerrit.ui.internal.table.UIPatchSetsTable;
import org.eclipse.egerrit.ui.internal.table.provider.FilePatchSetTableLabelProvider;
import org.eclipse.egerrit.ui.internal.table.provider.FileTableLabelProvider;
import org.eclipse.egerrit.ui.internal.utils.UIUtils;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ViewerSupport;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
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

	private RevisionInfo fCurrentRevision = null;

	private Map<String, RevisionInfo> fRevisions = new HashMap<String, RevisionInfo>();

	private Map<String, DisplayFileInfo> fFilesDisplay = new HashMap<String, DisplayFileInfo>();

	private GerritRepository fGerritRepository;

	private ChangeInfo fChangeInfo;

	private TableViewer tableFilesViewer;

	private Label lblDrafts;

	private Label lblComments;

	private Label lblTotal;

	private Combo comboDiffAgainst;

	private TableViewer tablePatchSetsViewer;

	// ------------------------------------------------------------------------
	// Constructor and life cycle
	// ------------------------------------------------------------------------

	/**
	 * The constructor.
	 */
	public FilesTabView() {
	}

	/**
	 * @param tabFolder
	 * @param ChangeInfo
	 *            changeInfo
	 */
	public void create(TabFolder tabFolder, ChangeInfo changeInfo) {
		fChangeInfo = changeInfo;
		filesTab(tabFolder);
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
					OpenCompareEditor compareEditor;
					try {
						compareEditor = new OpenCompareEditor(getGerritRepository(), fChangeInfo);
						String diffSource = comboDiffAgainst.getText();
						if (diffSource.equals(WORKSPACE)) {
							compareEditor.compareAgainstWorkspace((FileInfo) element);
						} else {
							MessageDialog.openError(null, "Can not open compare editor",
									"The compare editor can not yet show difference with " + diffSource);
						}
					} catch (EGerritException e) {
						EGerritCorePlugin.logError(e.getLocalizedMessage(), e);
					}
				}
			}
		});

		Label lblSummary = new Label(filesGroup, SWT.NONE);
		GridData gd_lblSummary = new GridData(SWT.RIGHT, SWT.TOP, true, false, 6, 1);
		gd_lblSummary.horizontalSpan = 6;
		lblSummary.setLayoutData(gd_lblSummary);
		lblSummary.setText("Summary:");

		lblDrafts = new Label(filesGroup, SWT.NONE);
		GridData gd_lblDrafts = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
		gd_lblDrafts.horizontalSpan = 1;
		lblDrafts.setLayoutData(gd_lblDrafts);
		lblDrafts.setText("drafts:");

		lblComments = new Label(filesGroup, SWT.NONE);
		GridData gd_lblComments = new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1);
		gd_lblComments.horizontalSpan = 1;
		lblComments.setLayoutData(gd_lblComments);
		lblComments.setText("comments:");
		lblComments.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				String old = lblComments.getText();
				String newCount = ""; //$NON-NLS-1$
				StringBuilder sb = new StringBuilder();
				sb.append("comments:");

				if (!fFilesDisplay.isEmpty()) {
					Iterator<DisplayFileInfo> itr1 = fFilesDisplay.values().iterator();
					int numComment = 0;
					while (itr1.hasNext()) {
						DisplayFileInfo displayFileInfo = itr1.next();
						if (displayFileInfo.getComments() != null) {
							numComment += displayFileInfo.getComments().size();
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

		lblTotal = new Label(filesGroup, SWT.NONE);
		GridData gd_lblTotal = new GridData(SWT.CENTER, SWT.TOP, false, false, 2, 1);
		gd_lblTotal.horizontalSpan = 2;
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

		comboDiffAgainst = new Combo(filesGroup, SWT.NONE);
		GridData gd_combo = new GridData(SWT.FILL, SWT.TOP, false, false, 2, 1);
		gd_combo.verticalIndent = 5;
		int numChar = 15;
		Point pt = UIUtils.computeFontSize(comboDiffAgainst);
		gd_combo.minimumWidth = numChar * pt.x;
		gd_combo.horizontalSpan = 1;
		comboDiffAgainst.setLayoutData(gd_combo);
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

		UIPatchSetsTable tableUIPatchSetsTable = new UIPatchSetsTable();
		tableUIPatchSetsTable.createTableViewerSection(filesGroup, new GridData(SWT.FILL, SWT.FILL, true, true, 10, 1));

		tablePatchSetsViewer = tableUIPatchSetsTable.getViewer();
		patchSetSelection();

		filesGroup.pack();
		scFilesTab.setMinSize(filesGroup.getSize());

		//Set the binding for this section
		filesTabDataBindings(tableFilesViewer, tablePatchSetsViewer);
	}

	protected void filesTabDataBindings(TableViewer tableFilesViewer, TableViewer tablePatchSetsViewer) {
		//Set the PatchSetViewer
		ObservableListContentProvider contentPatchSetProvider = new ObservableListContentProvider();
		tablePatchSetsViewer.setContentProvider(contentPatchSetProvider);
		WritableMap writeInfoMap = new WritableMap();
		writeInfoMap.putAll(fRevisions);
		WritableList writeInfoList = new WritableList(writeInfoMap.values(), Map.class);

		DataBindingContext bindingContext = new DataBindingContext();

		IObservableMap[] observePatchSetMaps = Properties.observeEach(contentPatchSetProvider.getKnownElements(),
				BeanProperties.values(new String[] { "_number", "commit" }));

		ViewerSupport.bind(tablePatchSetsViewer, writeInfoList, BeanProperties.values(new String[] { "commit" }));
		tablePatchSetsViewer.setLabelProvider(new FilePatchSetTableLabelProvider(observePatchSetMaps));

		//Set the FilesViewer
		ObservableListContentProvider contentProvider = new ObservableListContentProvider();
		tableFilesViewer.setContentProvider(contentProvider);

		writeInfoList = new WritableList(fFilesDisplay.values(), DisplayFileInfo.class);

		IObservableMap[] observeMaps = Properties.observeEach(contentProvider.getKnownElements(),
				BeanProperties.values(new String[] { "fileInfo", "comments" }));

		ViewerSupport.bind(tableFilesViewer, writeInfoList, BeanProperties.values(new String[] { "fileInfo" }));

		tableFilesViewer.setLabelProvider(new FileTableLabelProvider(observeMaps));
		//
//		IObservableValue lblTotalTextDataWidget = WidgetProperties.text().observe(lblTotal);
////		IObservableValue lblTotalDataValue = BeanProperties.value("fFilesDisplay").observe(
////				fFilesDisplay.entrySet().iterator());
//
//		IObservableValue lblTotalDataValue = BeanProperties.value("fFilesDisplay").observe(writeListFiles);
//		bindingContext.bindValue(lblTotalTextDataWidget, lblTotalDataValue, null,
//				new UpdateValueStrategy().setConverter(DataConverter.commentLineCounter()));

	}

	private Map<String, DisplayFileInfo> getfFilesDisplay() {
		return fFilesDisplay;
	}

	/**
	 *
	 */
	private void patchSetSelection() {
		tablePatchSetsViewer.addDoubleClickListener(new IDoubleClickListener() {

			@Override
			public void doubleClick(DoubleClickEvent event) {
				IStructuredSelection sel = (IStructuredSelection) event.getSelection();

				Object element = sel.getFirstElement();
				if (element instanceof RevisionInfo) {
					RevisionInfo selInfo = (RevisionInfo) element;
					setCurrentRevision(selInfo);
					fillFiles(selInfo.getFiles());
					setChanged();
					notifyObservers();

					try {
						setListCommentsPerPatchSet(getGerritRepository(), fChangeInfo.getId(), selInfo.getCommit()
								.getCommit());
						displayFilesTable();
						setDiffAgainstCombo();
					} catch (EGerritException e) {
						EGerritCorePlugin.logError(e.getLocalizedMessage(), e);
					}
				}
			}

		});
	}

	/**
	 * Fill the combo box with all patch set to compare with except the selected one. Also, add the Base and the
	 * Workspace which is the default one
	 */
	private void setDiffAgainstCombo() {
		List<String> listPatch = new ArrayList<String>();
		//Always start the list with the following items
		listPatch.add(WORKSPACE);
		listPatch.add(BASE);
		//Find the current Patchset selection
		ISelection selected = tablePatchSetsViewer.getSelection();
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

			Iterator<RevisionInfo> it = listRevision.iterator();
			while (it.hasNext()) {
				RevisionInfo rev = it.next();
				if (rev.getNumber() != psSelected) {
					listPatch.add(Integer.toString(rev.getNumber()));
				}
			}

			comboDiffAgainst.setItems(listPatch.toArray(new String[0]));
			comboDiffAgainst.select(0);//Select the Workspace to compare with by default

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
//				fFilesDisplay.put(entryFile.getKey(), displayFileInfo);
				displayFilesMap.put(entryFile.getKey(), displayFileInfo);
			}
			setFilesDisplay(displayFilesMap);
		}
	}

	private void setFilesDisplay(Map<String, DisplayFileInfo> displayFilesMap) {
		firePropertyChange("fFilesDisplay", this.fFilesDisplay, this.fFilesDisplay = displayFilesMap);
//		writeListFiles.setValue(fFilesDisplay.values());
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

		tablePatchSetsViewer.setInput(writeInfoList);
		//If we have a patch set, we need to select the current which is the top one
		if (!listRevision.isEmpty()) {
			tablePatchSetsViewer.getTable().setSelection(0);
			setDiffAgainstCombo();
			fCurrentRevision = listRevision.get(0);
			try {
				setListCommentsPerPatchSet(getGerritRepository(), fChangeInfo.getId(), fCurrentRevision.getCommit()
						.getCommit());
			} catch (EGerritException e) {
				EGerritCorePlugin.logError(e.getLocalizedMessage(), e);
			}
			displayFilesTable();
		}

		tablePatchSetsViewer.refresh();
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
//					entry1.getValue().addPropertyChangeListener("old_path", this);
		Iterator<Entry<String, DisplayFileInfo>> itrFileDisplay = fFilesDisplay.entrySet().iterator();
		while (itrFileDisplay.hasNext()) {
			Entry<String, DisplayFileInfo> entry1 = itrFileDisplay.next();

			entry1.getValue().addPropertyChangeListener("fileInfo", this);
			entry1.getValue().setOld_path(entry1.getKey());
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
	 * @param GerritRepository
	 *            gerritRepository
	 * @param String
	 *            changeId
	 * @param String
	 *            revisionId
	 */

	private void setListCommentsPerPatchSet(GerritRepository gerritRepository, String changeId, String revisionId) {

		Iterator<DisplayFileInfo> displayFile = fFilesDisplay.values().iterator();
		Map<String, ArrayList<CommentInfo>> mapCommentInfo = queryComments(gerritRepository, changeId, revisionId,
				new NullProgressMonitor());

		//Fill the Files table
		while (displayFile.hasNext()) {
			DisplayFileInfo displayFileInfo = displayFile.next();
			Iterator<Map.Entry<String, ArrayList<CommentInfo>>> commentIter = mapCommentInfo.entrySet().iterator();
			while (commentIter.hasNext()) {
				Entry<String, ArrayList<CommentInfo>> entryComment = commentIter.next();
				//Add comments associated to the file
				if (displayFileInfo.getold_path().equals(entryComment.getKey())) {
					displayFileInfo.setComments(entryComment.getValue());
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
	private Map<String, ArrayList<CommentInfo>> queryComments(GerritRepository gerritRepository, String change_id,
			String revision_id, IProgressMonitor monitor) {
		try {
			monitor.beginTask("Executing query", IProgressMonitor.UNKNOWN);

			Gerrit gerrit = gerritRepository.instantiateGerrit();

			// Create query
			if (gerrit != null) {
				ListCommentsCommand command = gerrit.getListComments(change_id, revision_id);

				Map<String, ArrayList<CommentInfo>> res = null;
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
	 * @return the GerritRepository
	 * @throws EGerritException
	 */
	public GerritRepository getGerritRepository() throws EGerritException {
		if (fGerritRepository == null) {
			throw new EGerritException("Gerrit repository not set"); //$NON-NLS-1$
		}

		return fGerritRepository;
	}

	/**
	 * @param GerritRepository
	 *            gerritRepository the gerritRepository to set
	 */
	public void setGerritRepository(GerritRepository gerritRepository) {
		this.fGerritRepository = gerritRepository;
	}

	/**
	 * @return the tableFilesViewer
	 */
	public TableViewer getTableFilesViewer() {
		return tableFilesViewer;
	}

	/**
	 * @return the tablePatchSetsViewer
	 */
	public TableViewer getTablePatchSetsViewer() {
		return tablePatchSetsViewer;
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
		ISelection selected = getTablePatchSetsViewer().getSelection();
		if (!selected.isEmpty()) {
			psSelected = findSelectedPatchsetRef(selected, psSelected);
		}
		return psSelected;
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

}
