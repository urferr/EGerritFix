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

package org.eclipse.egerrit.ui.internal.utils;

import java.text.SimpleDateFormat;
import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.egerrit.core.GerritClient;
import org.eclipse.egerrit.core.command.CreateDraftCommand;
import org.eclipse.egerrit.core.exception.EGerritException;
import org.eclipse.egerrit.core.utils.Utils;
import org.eclipse.egerrit.internal.model.ChangeInfo;
import org.eclipse.egerrit.internal.model.CommentInfo;
import org.eclipse.egerrit.internal.model.FileInfo;
import org.eclipse.egerrit.internal.model.ModelPackage;
import org.eclipse.egerrit.internal.model.RevisionInfo;
import org.eclipse.egerrit.internal.ui.compare.CommentableCompareItem;
import org.eclipse.egerrit.ui.editors.ChangeDetailEditor;
import org.eclipse.egerrit.ui.editors.OpenCompareEditor;
import org.eclipse.egerrit.ui.editors.QueryHelpers;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class regroups common review functionality
 *
 * @since 1.0
 */

public class ActiveWorkspaceRevision {
	private static Logger logger = LoggerFactory.getLogger(ChangeDetailEditor.class);

	private static final String ORG_ECLIPSE_EGERRIT_UI_COMMENT_MARKER = "org.eclipse.egerrit.ui.commentMarker"; //$NON-NLS-1$

	private static final ActiveWorkspaceRevision INSTANCE = new ActiveWorkspaceRevision();

	private GerritClient fGerritClient = null;

	private ChangeInfo fChangeInfo = null;

	private RevisionInfo fRevisionInContext = null;

	private CommentAndDraftListener listener;

	private final class CommentAndDraftListener extends EContentAdapter {
		@Override
		public void notifyChanged(Notification msg) {
			super.notifyChanged(msg);
			if (msg.getFeature() == null) {
				return;
			}
			if (msg.getFeature().equals(ModelPackage.Literals.FILE_INFO__COMMENTS)
					|| msg.getFeature().equals(ModelPackage.Literals.FILE_INFO__DRAFT_COMMENTS)) {
				createMarkers();
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
		fGerritClient = gerrit;
		fRevisionInContext = revisionInfo;
		fChangeInfo = revisionInfo.getChangeInfo();
		forceLoadRevision();
		createMarkers();
		hookListeners();
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
		newComment.setSide("right");
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
			if (aFile.getPath() != null && ("/" + aFile.getPath()).compareTo(path) == 0) {
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
	private void deleteCommentMarkers() {
		Collection<FileInfo> files = fRevisionInContext.getFiles().values();
		for (FileInfo fileInfo : files) {
			IFile workspaceFile = new OpenCompareEditor(fGerritClient, fChangeInfo)
					.getCorrespondingWorkspaceFile(fileInfo);
			if (workspaceFile == null) {
				continue;
			}
			IMarker[] currentCommentMarkers = null;
			try {
				currentCommentMarkers = workspaceFile.findMarkers(ORG_ECLIPSE_EGERRIT_UI_COMMENT_MARKER, false,
						IResource.DEPTH_ZERO);
				if (currentCommentMarkers != null) {
					for (IMarker m : currentCommentMarkers) {
						m.delete();
					}
				}
			} catch (CoreException e1) {
				logger.debug("Failed to delete marker", e1); //$NON-NLS-1$
			}
		}
	}

	/**
	 * Helper method to create markers for each comments and drafts from the active review
	 */
	private void createMarkers() {
		Collection<FileInfo> files = fRevisionInContext.getFiles().values();
		deleteCommentMarkers();
		for (FileInfo fileInfo : files) {
			IFile workspaceFile = new OpenCompareEditor(fGerritClient, fChangeInfo)
					.getCorrespondingWorkspaceFile(fileInfo);

			if (workspaceFile == null) {
				logger.debug("Could not find workspace file matching " + fileInfo.getPath()); //$NON-NLS-1$
				continue;
			}
			for (CommentInfo element : fileInfo.getComments()) {
				try {
					IMarker commentMarker = workspaceFile.createMarker(ORG_ECLIPSE_EGERRIT_UI_COMMENT_MARKER);
					commentMarker.setAttribute(IMarker.LINE_NUMBER, element.getLine());
					commentMarker.setAttribute(IMarker.MESSAGE,
							element.getAuthor().getUsername() + " "
									+ Utils.formatDate(element.getUpdated(), new SimpleDateFormat("yyyy-MM-dd")) + " "
									+ element.getMessage());
					commentMarker.setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_HIGH);
					commentMarker.setAttribute("commentInfo", element); //$NON-NLS-1$
					commentMarker.setAttribute("fileInfo", fileInfo); //$NON-NLS-1$
					commentMarker.setAttribute("gerritClient", fGerritClient); //$NON-NLS-1$
					commentMarker.setAttribute("isDraft", false); //$NON-NLS-1$
				} catch (CoreException e) {
					logger.debug("Failed to create marker", e); //$NON-NLS-1$
				}
			}
			for (CommentInfo element : fileInfo.getDraftComments()) {
				try {
					IMarker draftMarker = workspaceFile.createMarker(ORG_ECLIPSE_EGERRIT_UI_COMMENT_MARKER);
					draftMarker.setAttribute(IMarker.LINE_NUMBER, element.getLine());
					draftMarker.setAttribute(IMarker.MESSAGE, element.getMessage());
					draftMarker.setAttribute("commentInfo", element); //$NON-NLS-1$
					draftMarker.setAttribute("fileInfo", fileInfo); //$NON-NLS-1$
					draftMarker.setAttribute("gerritClient", fGerritClient); //$NON-NLS-1$
					draftMarker.setAttribute("isDraft", true); //$NON-NLS-1$
				} catch (CoreException e) {
					logger.debug("Failed to create marker", e); //$NON-NLS-1$
				}
			}
		}
	}

	/**
	 * Remove the current revision
	 */
	public void deactiveCurrentRevision() {
		deleteCommentMarkers();
		fRevisionInContext.eAdapters().remove(listener);
	}
}