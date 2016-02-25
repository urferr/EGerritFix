/*******************************************************************************
 * Copyright (c) 2015 Ericsson AB.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson - initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.ui.editors;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.egerrit.core.EGerritCorePlugin;
import org.eclipse.egerrit.core.GerritClient;
import org.eclipse.egerrit.core.command.ChangeOption;
import org.eclipse.egerrit.core.command.DeleteReviewedCommand;
import org.eclipse.egerrit.core.command.GetChangeCommand;
import org.eclipse.egerrit.core.command.ListCommentsCommand;
import org.eclipse.egerrit.core.command.ListDraftsCommand;
import org.eclipse.egerrit.core.command.QueryChangesCommand;
import org.eclipse.egerrit.core.command.SetReviewedCommand;
import org.eclipse.egerrit.core.exception.EGerritException;
import org.eclipse.egerrit.internal.model.ActionInfo;
import org.eclipse.egerrit.internal.model.ChangeInfo;
import org.eclipse.egerrit.internal.model.CommentInfo;
import org.eclipse.egerrit.internal.model.FileInfo;
import org.eclipse.egerrit.internal.model.ModelPackage;
import org.eclipse.egerrit.internal.model.RevisionInfo;
import org.eclipse.emf.common.util.EMap;

/**
 * A helper class wrapping the common server queries.
 */
public class QueryHelpers {
	private final static String TITLE = "Gerrit Server "; //$NON-NLS-1$

	private static final String EXECUTING_QUERY = "Executing query";

	/**
	 * Obtain the id, and the subject corresponding to a given id
	 *
	 * @param gerritClient,
	 *            the client to use to perform the lookup
	 * @param subject,
	 *            the subject of the change info being looked for
	 * @param monitor
	 *            a progress monitor
	 * @return a {@link ChangeInfo} or null if no match was found
	 */
	public static ChangeInfo[] lookupPartialChangeInfoFromSubject(GerritClient gerritClient, String subject,
			IProgressMonitor monitor) throws MalformedURLException {
		try {
			monitor.beginTask(EXECUTING_QUERY, IProgressMonitor.UNKNOWN);

			QueryChangesCommand command = gerritClient.queryChanges();
			command.addOption(ChangeOption.DETAILED_LABELS);
			command.addOption(ChangeOption.CURRENT_ACTIONS);

			try {
				command.addQuery(setFreeText(subject));
				return command.call();
			} catch (EGerritException e) {
				EGerritCorePlugin.logError(gerritClient.getRepository().formatGerritVersion() + e.getMessage());
			}
			return null;
		} finally {
			monitor.done();
		}
	}

	/**
	 * Create a message text from the subject to allow free text query
	 *
	 * @param subject
	 * @return String
	 */
	private static String setFreeText(String subject) {
		// use the subject to query the server
		String query = "message:\"" + subject + "\""; //$NON-NLS-1$ //$NON-NLS-2$
		return query;
	}

	/**
	 * Obtain the id, and the subject corresponding to a given id
	 *
	 * @param gerritClient,
	 *            the client to use to perform the lookup
	 * @param change_id,
	 *            the long id of the change info being looked for
	 * @param monitor
	 *            a progress monitor
	 * @return a {@link ChangeInfo} or null if no match was found
	 */
	public static ChangeInfo lookupPartialChangeInfoFromChangeId(GerritClient gerrit, String change_id,
			IProgressMonitor monitor) {
		try {
			monitor.beginTask(EXECUTING_QUERY, IProgressMonitor.UNKNOWN);

			GetChangeCommand command = null;
			command = gerrit.getChange(change_id);
			command.addOption(ChangeOption.DETAILED_LABELS);
			command.addOption(ChangeOption.CURRENT_ACTIONS);
			command.addOption(ChangeOption.ALL_COMMITS);

			try {
				return command.call();
			} catch (EGerritException e) {
				EGerritCorePlugin.logError(gerrit.getRepository().formatGerritVersion() + e.getMessage());
			}
			return null;
		} finally {
			monitor.done();
		}
	}

	/**
	 * Load comments for the given revision
	 */
	public static void loadComments(GerritClient gerrit, RevisionInfo revision) {
		ListCommentsCommand command = gerrit.getListComments(revision.getChangeInfo().getId(), revision.getId());
		if (revision.isCommentsLoaded()) {
			return;
		}
		Map<String, ArrayList<CommentInfo>> comments = null;
		try {
			comments = command.call();
			revision.setCommentsLoaded(true);
		} catch (EGerritException e) {
			EGerritCorePlugin.logError(gerrit.getRepository().formatGerritVersion() + e.getMessage());
			return;
		}

		//Load the comments and set them in the fileInfo object
		for (Entry<String, ArrayList<CommentInfo>> fileComment : comments.entrySet()) {
			FileInfo files = revision.getFiles().get(fileComment.getKey());
			if (files != null) {
				files.getComments().clear();
				files.getComments().addAll(fileComment.getValue());
			}
		}
	}

	public static void loadDrafts(GerritClient gerrit, RevisionInfo revision) {
		if (gerrit.getRepository().getServerInfo().isAnonymous()) {
			return;
		}
		ListDraftsCommand command = gerrit.listDraftsComments(revision.getChangeInfo().getId(), revision.getId());
		Map<String, ArrayList<CommentInfo>> drafts = null;
		try {
			drafts = command.call();
		} catch (EGerritException e) {
			EGerritCorePlugin.logError(gerrit.getRepository().formatGerritVersion() + e.getMessage());
			return;
		}

		//There is no more drafts, so we need to clear our data structure
		if (drafts.entrySet().isEmpty()) {
			Collection<FileInfo> files = revision.getFiles().values();
			files.forEach(f -> f.getDraftComments().clear());
			return;
		}

		for (Entry<String, ArrayList<CommentInfo>> draftComment : drafts.entrySet()) {
			FileInfo files = revision.getFiles().get(draftComment.getKey());
			if (files != null) {
				files.getDraftComments().clear();
				files.getDraftComments().addAll(draftComment.getValue());
			}
		}
	}

	public static void markAsReviewed(GerritClient gerrit, FileInfo fileInfo) {
		if (gerrit.getRepository().getServerInfo().isAnonymous()) {
			return;
		}
		if (fileInfo.isReviewed()) {
			return;
		}
		SetReviewedCommand command = gerrit.setReviewed(fileInfo.getRevision().getChangeInfo().getId(),
				fileInfo.getRevision().getId(), fileInfo.getPath());
		try {
			command.call();
			fileInfo.setReviewed(true);
		} catch (EGerritException ex) {
			EGerritCorePlugin.logError(gerrit.getRepository().formatGerritVersion() + ex.getMessage());
		}
	}

	public static void markAsNotReviewed(GerritClient gerrit, FileInfo fileInfo) {
		if (gerrit.getRepository().getServerInfo().isAnonymous()) {
			return;
		}
		if (!fileInfo.isReviewed()) {
			return;
		}

		DeleteReviewedCommand command = gerrit.deleteReviewed(fileInfo.getRevision().getChangeInfo().getId(),
				fileInfo.getRevision().getId(), fileInfo.getPath());
		try {
			command.call();
			fileInfo.setReviewed(false);
		} catch (EGerritException ex) {
			EGerritCorePlugin.logError(gerrit.getRepository().formatGerritVersion() + ex.getMessage());
		}
	}

	public static void reload(GerritClient gerrit, ChangeInfo toRefresh) {
		ChangeInfo newChangeInfo = QueryHelpers.queryMessageTab(gerrit, toRefresh.getId());
		toRefresh.set_number(newChangeInfo.get_number());
		toRefresh.setChange_id(newChangeInfo.getChange_id());
		toRefresh.setStatus(newChangeInfo.getStatus());
		toRefresh.setStarred(newChangeInfo.isStarred());
		toRefresh.setSubject(newChangeInfo.getSubject());
		toRefresh.setCreated(newChangeInfo.getCreated());
		toRefresh.setReviewed(newChangeInfo.isReviewed());
		toRefresh.setInsertions(newChangeInfo.getInsertions());
		toRefresh.setDeletions(newChangeInfo.getDeletions());
		toRefresh.setCurrent_revision(newChangeInfo.getCurrent_revision());
		toRefresh.setTopic(newChangeInfo.getTopic());
		toRefresh.setMergeable(newChangeInfo.isMergeable());

		toRefresh.getLabels().clear();
		toRefresh.getLabels().addAll(newChangeInfo.getLabels());
		toRefresh.getMessages().clear();
		toRefresh.getMessages().addAll(newChangeInfo.getMessages());
		toRefresh.eSet(ModelPackage.Literals.CHANGE_INFO__PERMITTED_LABELS, newChangeInfo.getPermitted_labels());
		toRefresh.eSet(ModelPackage.Literals.CHANGE_INFO__ACTIONS, newChangeInfo.getActions());
		mergeRevisions(toRefresh, newChangeInfo);

		//Set the date at the end because it is used to trigger other refreshes
		toRefresh.setUpdated(newChangeInfo.getUpdated());
	}

	private static void mergeRevisions(ChangeInfo toRefresh, ChangeInfo newChangeInfo) {
		//Reset the flags that indicates that comments have been loaded
		Set<String> oldKeys = toRefresh.getRevisions().keySet();
		toRefresh.getRevisions().values().forEach(rev -> rev.setCommentsLoaded(false));

		//Copy the new values of the actions
		toRefresh.getRevisions().values().forEach(rev -> {
			String revId = rev.getId();
			if (newChangeInfo.getRevisions().get(revId) != null) {
				rev.getActions().clear();
				rev.getActions().putAll(newChangeInfo.getRevisions().get(revId).getActions());
			}
		});

		//Isolate the revisions that need to be kept by removing the entries already loaded from the new collection
		//Once this is done we simply add the new remaining of the revisions to the old revision
		oldKeys.stream().forEach(newChangeInfo.getRevisions()::removeKey);

		//Take all the other revisions as they are since they are new
		toRefresh.getRevisions().putAll(newChangeInfo.getRevisions());
	}

	private static ChangeInfo queryMessageTab(GerritClient gerrit, String id) {
		GetChangeCommand command = gerrit.getChange(id);
		command.addOption(ChangeOption.DETAILED_LABELS);
		command.addOption(ChangeOption.ALL_FILES);
		command.addOption(ChangeOption.ALL_REVISIONS);
		command.addOption(ChangeOption.ALL_COMMITS);
		command.addOption(ChangeOption.REVIEWED);
		command.addOption(ChangeOption.MESSAGES);
		command.addOption(ChangeOption.DOWNLOAD_COMMANDS);
		command.addOption(ChangeOption.CURRENT_ACTIONS);

		ChangeInfo res = null;
		try {
			res = command.call();
		} catch (EGerritException e) {
			EGerritCorePlugin.logError(gerrit.getRepository().formatGerritVersion() + e.getMessage());
		}
		return res;
	}
}
