/*******************************************************************************
 * Copyright (c) 2016 Ericsson AB.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson - initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.internal.ui.utils;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.egerrit.internal.core.EGerritCorePlugin;
import org.eclipse.egerrit.internal.core.GerritClient;
import org.eclipse.egerrit.internal.core.command.CreateDraftCommand;
import org.eclipse.egerrit.internal.core.exception.EGerritException;
import org.eclipse.egerrit.internal.model.ChangeInfo;
import org.eclipse.egerrit.internal.model.CommentInfo;
import org.eclipse.egerrit.internal.model.FileInfo;
import org.eclipse.egerrit.internal.model.ModelHelpers;
import org.eclipse.egerrit.internal.model.ModelPackage;
import org.eclipse.egerrit.internal.model.RevisionInfo;
import org.eclipse.egerrit.internal.ui.compare.CommentableCompareItem;
import org.eclipse.egerrit.internal.ui.editors.ChangeDetailEditor;
import org.eclipse.egerrit.internal.ui.editors.EGerritCommentMarkers;
import org.eclipse.egerrit.internal.ui.editors.FindLocalRepository;
import org.eclipse.egerrit.internal.ui.editors.OpenCompareEditor;
import org.eclipse.egerrit.internal.ui.editors.QueryHelpers;
import org.eclipse.egit.ui.internal.decorators.GitQuickDiffProvider;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class regroups common review functionality
 *
 * @since 1.0
 */

public class ActiveWorkspaceRevision {
	private static final String MARKERS_KEY = "markertip"; //$NON-NLS-1$

	private static final String UI_EDITORS = "org.eclipse.ui.editors"; //$NON-NLS-1$

	private static final String QUICKDIFF_QUICKDIFF = "quickdiff.quickDiff"; //$NON-NLS-1$

	private static final String QUICKDIFF_DEFAULT = "quickdiff.defaultProvider"; //$NON-NLS-1$

	private static final String ADDITION_INDICATION_OVERVIEW_RULE = "additionIndicationInOverviewRule"; //$NON-NLS-1$

	private static final String CHANGE_INDICATION_OVERVIEW_RULE = "changeIndicationInOverviewRuler"; //$NON-NLS-1$

	private static final String DELETION_INDICATION_OVERVIEW_RULE = "deletionIndicationInOverviewRuler"; //$NON-NLS-1$

	private static Logger logger = LoggerFactory.getLogger(ChangeDetailEditor.class);

	private static final ActiveWorkspaceRevision INSTANCE = new ActiveWorkspaceRevision();

	private GerritClient fGerritClient = null;

	private ChangeInfo fChangeInfo = null;

	private RevisionInfo fRevisionInContext = null;

	private CommentAndDraftListener listener;

	private Map<String, IMarker> markersManaged = new HashMap<>();

	private boolean fIsAdditionIndicationInOverviewRule;

	private boolean fIsChangeIndicationInOverviewRuler;

	private boolean fIsDeletionIndicationInOverviewRuler;

	private boolean fIsQuickDiffOn;

	private String fDefaultProvider;

	private Repository fActiveRepository;

	private final class CommentAndDraftListener extends EContentAdapter {
		@Override
		public void notifyChanged(Notification msg) {
			super.notifyChanged(msg);
			if (msg.getFeature() == null) {
				return;
			}
			if (msg.getFeature().equals(ModelPackage.Literals.FILE_INFO__COMMENTS)
					|| msg.getFeature().equals(ModelPackage.Literals.FILE_INFO__DRAFT_COMMENTS)) {
				if (msg.getEventType() == Notification.ADD) {
					addMarker((CommentInfo) msg.getNewValue());
					openMarkerView(IWorkbenchPage.VIEW_CREATE);
				}
				if (msg.getEventType() == Notification.REMOVE) {
					deleteMarker((CommentInfo) msg.getOldValue());
				}
			}
		}

		private void deleteMarker(CommentInfo commentToDelete) {
			IMarker marker = markersManaged.remove(commentToDelete.getId());
			if (marker != null) {
				try {
					marker.delete();
				} catch (CoreException e) {
					logger.debug("Failed to delete marker", e); //$NON-NLS-1$
				}
			}
		}
	}

	// Private constructor prevents instantiation from other classes
	private ActiveWorkspaceRevision() {
	}

	public static ActiveWorkspaceRevision getInstance() {
		return INSTANCE;
	}

	/**
	 * Set a given revision as a context. This will cause markers to be created for the comments and drafts
	 */
	public void activateCurrentRevision(GerritClient gerrit, RevisionInfo revisionInfo) {
		if (revisionInfo == null) {
			throw new IllegalArgumentException("Revision can't be null."); //$NON-NLS-1$
		}

		//We are reactivating the same review. Do nothing.
		if (fRevisionInContext != null && fRevisionInContext.getId().equals(revisionInfo.getId())) {
			return;
		}

		//Force de-activation if another review is already tracked
		if (fRevisionInContext != null) {
			deactiveCurrentRevision();
		}
		fGerritClient = gerrit;
		fRevisionInContext = revisionInfo;
		fChangeInfo = revisionInfo.getChangeInfo();
		fActiveRepository = new FindLocalRepository(fGerritClient, fChangeInfo.getProject()).getRepository();
		forceLoadRevision();
		createMarkers();
		hookListeners();
		firePropertyChange("activeRevision", null, fRevisionInContext); //$NON-NLS-1$
		boolean b = revisionInfo.isCommented();
		if (b) {
			openMarkerView(IWorkbenchPage.VIEW_CREATE);
		}
		enableQuickDiff();
		informUserAboutMarkers();
	}

	private void informUserAboutMarkers() {
		if (!fGerritClient.getRepository().getServerInfo().isAnonymous()) {
			String activeBranchId = getCurrentGitBranchId();
			String commitId = fRevisionInContext.getId();
			if (commitId.equals(activeBranchId)) {
				Display.getDefault().asyncExec(() -> UIUtils.showDialogTip(MARKERS_KEY, null, Messages.EGerriTip,
						Messages.ChangeDetailEditor_EGerriTipValue, null));
			} else {
				String reviewSelected = ".../" + fRevisionInContext.getChangeInfo().get_number() + "/" //$NON-NLS-1$ //$NON-NLS-2$
						+ fRevisionInContext.get_number();
				String note = NLS.bind(Messages.ActiveWorkspaceRevision_Note,
						new String[] { getActiveBranchName(), reviewSelected });
				Display.getDefault().asyncExec(() -> UIUtils.showDialogTip(MARKERS_KEY, null, Messages.EGerriTip,
						Messages.ChangeDetailEditor_EGerriTipValue, note));
			}
		}
	}

	private boolean isProblemViewOpen() {
		IViewPart problemView = null;
		IWorkbenchWindow[] listWW = PlatformUI.getWorkbench().getWorkbenchWindows();
		for (IWorkbenchWindow element : listWW) {
			problemView = element.getActivePage().findView("org.eclipse.ui.views.ProblemView"); //$NON-NLS-1$
			if (problemView != null) {
				break;
			}
		}
		if (problemView != null) {
			return true;
		}
		return false;
	}

	private void openMarkerView(int mode) {
		//Only open the markers view if the problem view is not present
		if (!isProblemViewOpen()) {
			IWorkbenchWindow[] listWW = PlatformUI.getWorkbench().getWorkbenchWindows();
			for (IWorkbenchWindow element : listWW) {
				Display.getDefault().asyncExec(() -> {
					try {
						element.getActivePage().showView("org.eclipse.ui.views.AllMarkersView", //$NON-NLS-1$
								null, mode);
					} catch (PartInitException e) {
						EGerritCorePlugin
								.logError(fGerritClient.getRepository().formatGerritVersion() + e.getMessage());
					}
				});
			}
		}
	}

	private void forceLoadRevision() {
		QueryHelpers.loadRevisionDetails(fGerritClient, fRevisionInContext);
	}

	private void hookListeners() {
		listener = new CommentAndDraftListener();
		fRevisionInContext.eAdapters().add(listener);
	}

	/**
	 * Create a comment on the active review
	 */
	public void newComment(CommentInfo newComment) {
		if (fRevisionInContext == null) {
			logger.debug("No active revision set."); //$NON-NLS-1$
			return;
		}
		// identify which file is in the review
		FileInfo fileInfo = getFileInfoInRevision(newComment.getPath());

		if (fileInfo == null) {
			logger.debug("Problem creating the marker. The active revision does not contain the expected file." //$NON-NLS-1$
					+ newComment.getPath());
			return;
		}

		CreateDraftCommand publishDraft = fGerritClient.createDraftComments(fChangeInfo.getId(),
				fRevisionInContext.getId());
		newComment.setSide("right"); //$NON-NLS-1$
		publishDraft.setCommandInput(newComment);
		newComment.setPath(fileInfo.getPath());
		try {
			fileInfo.getDraftComments().add(publishDraft.call());
		} catch (EGerritException e) {
			//The throwable is an additional trick that allows to detect, in case of failure, which side failed persisting.
			throw new RuntimeException(CommentableCompareItem.class.getName(),
					new Throwable(String.valueOf(hashCode())));
		}
	}

	private FileInfo getFileInfoInRevision(String workspacePath) {
		Collection<FileInfo> files = fRevisionInContext.getFiles().values();
		for (FileInfo aFile : files) {
			if (aFile.getPath() != null && ("/" + aFile.getPath()).compareTo(workspacePath) == 0) { //$NON-NLS-1$
				return aFile;
			}
		}

		//Looking for an approximate match.
		for (FileInfo aFile : files) {
			if (aFile.getPath() != null && (aFile.getPath().endsWith(workspacePath))) {
				return aFile;
			}
		}
		return null;
	}

	public boolean isFilePartOfReview(String reviewFileName) {
		if (fRevisionInContext == null) {
			logger.debug("No active revision set."); //$NON-NLS-1$
			return false;
		}
		return getFileInfoInRevision(reviewFileName) != null;
	}

	/**
	 * Helper method to delete markers associated with each comments and drafts from the active review
	 */
	private void deleteAllMarkers() {
		Set<String> allMarkers = new HashSet<>(markersManaged.keySet()); //We need to take a copy or we get a concurrent modification exception
		for (String entry : allMarkers) {
			IMarker m = markersManaged.remove(entry);
			if (m != null) {
				try {
					m.delete();
				} catch (CoreException e) {
					logger.debug("Failed to delete marker", e); //$NON-NLS-1$
				}
			}
		}
	}

	/**
	 * Helper method to create markers for each comments and drafts from the active review
	 */
	private void createMarkers() {
		Collection<FileInfo> files = fRevisionInContext.getFiles().values();
		deleteAllMarkers();
		for (FileInfo fileInfo : files) {
			EList<CommentInfo> sortedComments = ModelHelpers.sortComments(fileInfo.getAllComments());
			boolean addingMarker = false;
			for (CommentInfo commentInfo : sortedComments) {
				addMarker(commentInfo);
				addingMarker = true;
			}
			if (addingMarker) {
				openMarkerView(IWorkbenchPage.VIEW_CREATE);
			}
		}
	}

	/**
	 * Remove the current revision
	 */
	public void deactiveCurrentRevision() {
		if (fRevisionInContext == null) {
			return;
		}
		fRevisionInContext.eAdapters().remove(listener);
		deleteAllMarkers();

		if (hasDrafts()) {
			final Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
			UIUtils.replyToChange(shell, fRevisionInContext,
					Messages.ActiveWorkspaceRevision_0 + fRevisionInContext.getChangeInfo().getSubject() + "\"\n\n", //$NON-NLS-1$
					fGerritClient, false, null);
		}
		deactivateQuickDiff();
		fRevisionInContext = null;
		firePropertyChange("activeRevision", null, null); //$NON-NLS-1$
	}

	private void deactivateQuickDiff() {
		//Restore the values captured when we enabled the quickdiff
		Preferences preferences = InstanceScope.INSTANCE.getNode(UI_EDITORS);

		preferences.put(QUICKDIFF_QUICKDIFF, Boolean.toString(fIsQuickDiffOn));
		preferences.put(ADDITION_INDICATION_OVERVIEW_RULE, Boolean.toString(fIsAdditionIndicationInOverviewRule));
		preferences.put(CHANGE_INDICATION_OVERVIEW_RULE, Boolean.toString(fIsChangeIndicationInOverviewRuler));
		preferences.put(DELETION_INDICATION_OVERVIEW_RULE, Boolean.toString(fIsDeletionIndicationInOverviewRuler));
		preferences.put(QUICKDIFF_DEFAULT, fDefaultProvider);

		try {
			// forces the application to save the preferences
			preferences.flush();
		} catch (BackingStoreException e1) {
			EGerritCorePlugin.logError(fGerritClient.getRepository().formatGerritVersion() + e1.getMessage());
		}
	}

	private boolean hasDrafts() {
		Collection<FileInfo> files = fRevisionInContext.getFiles().values();
		for (FileInfo fileInfo : files) {
			if (fileInfo.getDraftsCount() > 0) {
				return true;
			}
		}
		return false;
	}

	//Add a marker for the specified comment.
	//The workspacefile is optional. If it is not specified the associated file will be derived the comment
	private void addMarker(CommentInfo newComment) {
		IResource workspaceFile = null;
		if (workspaceFile == null) {
			workspaceFile = new OpenCompareEditor(fGerritClient, fChangeInfo)
					.getCorrespondingWorkspaceFile(ModelHelpers.getFileInfo(newComment));
		}
		if (workspaceFile == null) {
			workspaceFile = ResourcesPlugin.getWorkspace().getRoot();
		}
		try {
			IMarker commentMarker = workspaceFile.createMarker(EGerritCommentMarkers.COMMENT_MARKER_ID);
			if (workspaceFile == ResourcesPlugin.getWorkspace().getRoot()) {
				commentMarker.setAttribute(IMarker.MESSAGE,
						resourceMissingMessage(newComment) + UIUtils.formatMessageForMarkerView(newComment, 0));
			} else {
				int lineNumber = getLine(newComment);
				commentMarker.setAttribute(IMarker.LINE_NUMBER, Math.abs(lineNumber));
				commentMarker.setAttribute(IMarker.MESSAGE, UIUtils.formatMessageForMarkerView(newComment, lineNumber));
			}
			commentMarker.setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_NORMAL);
			commentMarker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_INFO);

			commentMarker.setAttribute(EGerritCommentMarkers.ATTR_COMMENT_INFO, newComment);
			commentMarker.setAttribute(EGerritCommentMarkers.ATTR_FILE_INFO, newComment);
			commentMarker.setAttribute(EGerritCommentMarkers.ATTR_GERRIT_CLIENT, fGerritClient);
			commentMarker.setAttribute(EGerritCommentMarkers.ATTR_IS_DRAFT, newComment.getAuthor() == null);
			markersManaged.put(newComment.getId(), commentMarker);
		} catch (CoreException e) {
			logger.debug("Failed to create marker", e); //$NON-NLS-1$
		}
	}

	private int getLine(CommentInfo comment) {
		try {
			if (comment.getLine() == 0) {
				return 1;
			}
			Repository repo = new GerritToGitMapping(
					new URIish(fGerritClient.getRepository().getURIBuilder(false).toString()), fChangeInfo.getProject())
							.find();
			int newPosition = new MarkerRepositioner(repo, ModelHelpers.getFileInfo(comment).getPath())
					.getNewPositionFor(comment.getLine());
			return newPosition == 0 ? -1 : newPosition;
		} catch (Exception e) {
			return comment.getLine();
		}
	}

	private String resourceMissingMessage(CommentInfo comment) {
		return NLS.bind(Messages.ActiveWorkspaceRevision_1, ModelHelpers.getFileInfo(comment).getPath());
	}

	public RevisionInfo getActiveRevision() {
		return fRevisionInContext;
	}

	//The following code is used to enable the databinding
	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

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
	 * enables the quickdiff feature inside the editor
	 */
	private void enableQuickDiff() {
		Preferences preferences = InstanceScope.INSTANCE.getNode(UI_EDITORS);

		//Remember the value of the preferences we are modifying
		fIsQuickDiffOn = Platform.getPreferencesService().getBoolean(UI_EDITORS, QUICKDIFF_QUICKDIFF, false, null);
		fIsAdditionIndicationInOverviewRule = Platform.getPreferencesService().getBoolean(UI_EDITORS,
				ADDITION_INDICATION_OVERVIEW_RULE, false, null);
		fIsChangeIndicationInOverviewRuler = Platform.getPreferencesService().getBoolean(UI_EDITORS,
				CHANGE_INDICATION_OVERVIEW_RULE, false, null);
		fIsDeletionIndicationInOverviewRuler = Platform.getPreferencesService().getBoolean(UI_EDITORS,
				DELETION_INDICATION_OVERVIEW_RULE, false, null);
		fDefaultProvider = Platform.getPreferencesService().getString(UI_EDITORS, QUICKDIFF_DEFAULT, "", //$NON-NLS-1$
				null);

		preferences.putBoolean(QUICKDIFF_QUICKDIFF, true);
		preferences.putBoolean(ADDITION_INDICATION_OVERVIEW_RULE, true);
		preferences.putBoolean(CHANGE_INDICATION_OVERVIEW_RULE, true);
		preferences.putBoolean(DELETION_INDICATION_OVERVIEW_RULE, true);
		preferences.put(QUICKDIFF_DEFAULT, "org.eclipse.egit.ui.internal.decorators.GitQuickDiffProvider");//$NON-NLS-1$
		try {
			// forces the application to save the preferences
			preferences.flush();
		} catch (BackingStoreException e1) {
			EGerritCorePlugin.logError(fGerritClient.getRepository().formatGerritVersion() + e1.getMessage());
		}

		try {
			GitQuickDiffProvider.setBaselineReference(fActiveRepository, "HEAD^1"); //$NON-NLS-1$
		} catch (IOException e1) {
			EGerritCorePlugin.logError(fGerritClient.getRepository().formatGerritVersion() + e1.getMessage());
		}
	}

	public GerritClient getGerritClient() {
		return fGerritClient;
	}

	/**
	 * Get the name of the active Git branch
	 *
	 * @return String
	 */
	private String getActiveBranchName() {
		String branchName = null;
		try {
			branchName = fActiveRepository != null ? fActiveRepository.getBranch() : null;
		} catch (IOException e) {
			EGerritCorePlugin.logError(fGerritClient.getRepository().formatGerritVersion() + e.getMessage());
		}
		return branchName;
	}

	/**
	 * Get the id for the current Git active branch
	 *
	 * @return String
	 */
	private String getCurrentGitBranchId() {
		String branchId = null;
		if (getActiveBranchName() != null) {
			Ref ref = null;
			try {
				ref = fActiveRepository.findRef(getActiveBranchName());
			} catch (IOException e) {
				EGerritCorePlugin.logError(fGerritClient.getRepository().formatGerritVersion() + e.getMessage());
			}
			if (ref != null) {
				branchId = ref.getObjectId().getName();
			}
		}
		return branchId;
	}
}