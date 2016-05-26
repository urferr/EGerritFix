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
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
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
import org.eclipse.egerrit.internal.ui.editors.OpenCompareEditor;
import org.eclipse.egerrit.internal.ui.editors.QueryHelpers;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class regroups common review functionality
 *
 * @since 1.0
 */

public class ActiveWorkspaceRevision {
	private static Logger logger = LoggerFactory.getLogger(ChangeDetailEditor.class);

	private static final ActiveWorkspaceRevision INSTANCE = new ActiveWorkspaceRevision();

	private GerritClient fGerritClient = null;

	private ChangeInfo fChangeInfo = null;

	private RevisionInfo fRevisionInContext = null;

	private CommentAndDraftListener listener;

	private Map<String, IMarker> markersManaged = new HashMap<>();

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
					addMarker((CommentInfo) msg.getNewValue(), null);
				}
				if (msg.getEventType() == Notification.REMOVE) {
					deleteMarker((CommentInfo) msg.getOldValue());
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
			throw new IllegalAccessError("Revision can't be null."); //$NON-NLS-1$
		}

		//Force deactivation if another review is already tracked
		if (fRevisionInContext != null) {
			deactiveCurrentRevision();
		}
		fGerritClient = gerrit;
		fRevisionInContext = revisionInfo;
		fChangeInfo = revisionInfo.getChangeInfo();
		forceLoadRevision();
		createMarkers();
		hookListeners();
		firePropertyChange("activeRevision", null, fRevisionInContext); //$NON-NLS-1$
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

	private FileInfo getFileInfoInRevision(String path) {
		Collection<FileInfo> files = fRevisionInContext.getFiles().values();
		for (FileInfo aFile : files) {
			if (aFile.getPath() != null && ("/" + aFile.getPath()).compareTo(path) == 0) { //$NON-NLS-1$
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
			IResource workspaceFile = new OpenCompareEditor(fGerritClient, fChangeInfo)
					.getCorrespondingWorkspaceFile(fileInfo);
			if (workspaceFile == null) {
				workspaceFile = ResourcesPlugin.getWorkspace().getRoot();
			}
			EList<CommentInfo> sortedComments = ModelHelpers.sortComments(fileInfo.getAllComments());
			for (CommentInfo commentInfo : sortedComments) {
				addMarker(commentInfo, workspaceFile);
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
					fGerritClient);
		}
		fRevisionInContext = null;
		firePropertyChange("activeRevision", null, null); //$NON-NLS-1$
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

	//Add a marker for the specified comment.
	//The workspacefile is optional. If it is not specified the associated file will be derived the comment
	private void addMarker(CommentInfo newComment, IResource workspaceFile) {
		if (workspaceFile == null) {
			workspaceFile = new OpenCompareEditor(fGerritClient, fChangeInfo)
					.getCorrespondingWorkspaceFile(ModelHelpers.getFileInfo(newComment));
		}
		if (workspaceFile == null) {
			workspaceFile = ResourcesPlugin.getWorkspace().getRoot();
		}
		try {
			IMarker commentMarker = workspaceFile.createMarker(EGerritCommentMarkers.COMMENT_MARKER_ID);
			commentMarker.setAttribute(IMarker.LINE_NUMBER, newComment.getLine());
			if (workspaceFile == ResourcesPlugin.getWorkspace().getRoot()) {
				commentMarker.setAttribute(IMarker.MESSAGE,
						resourceMissingMessage(newComment) + UIUtils.formatMessageForMarkerView(newComment));
			} else {
				commentMarker.setAttribute(IMarker.MESSAGE, UIUtils.formatMessageForMarkerView(newComment));
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

	private String resourceMissingMessage(CommentInfo comment) {
		return Messages.ActiveWorkspaceRevision_1 + ModelHelpers.getFileInfo(comment).getPath()
				+ Messages.ActiveWorkspaceRevision_2;
	}

	public RevisionInfo getActiveRevision() {
		return fRevisionInContext;
	}

	//The following code is used to enable the databinding
	private transient PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
	}

	protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
	}
}