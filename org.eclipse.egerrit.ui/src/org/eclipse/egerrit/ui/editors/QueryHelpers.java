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
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.egerrit.core.EGerritCorePlugin;
import org.eclipse.egerrit.core.GerritClient;
import org.eclipse.egerrit.core.command.ChangeOption;
import org.eclipse.egerrit.core.command.ChangeStatus;
import org.eclipse.egerrit.core.command.DeleteReviewedCommand;
import org.eclipse.egerrit.core.command.GetChangeCommand;
import org.eclipse.egerrit.core.command.GetFilesCommand;
import org.eclipse.egerrit.core.command.GetIncludedInCommand;
import org.eclipse.egerrit.core.command.GetMergeableCommand;
import org.eclipse.egerrit.core.command.GetRelatedChangesCommand;
import org.eclipse.egerrit.core.command.GetReviewedFilesCommand;
import org.eclipse.egerrit.core.command.ListCommentsCommand;
import org.eclipse.egerrit.core.command.ListDraftsCommand;
import org.eclipse.egerrit.core.command.ListReviewersCommand;
import org.eclipse.egerrit.core.command.QueryChangesCommand;
import org.eclipse.egerrit.core.command.SetReviewedCommand;
import org.eclipse.egerrit.core.exception.EGerritException;
import org.eclipse.egerrit.internal.model.ChangeInfo;
import org.eclipse.egerrit.internal.model.CommentInfo;
import org.eclipse.egerrit.internal.model.FileInfo;
import org.eclipse.egerrit.internal.model.IncludedInInfo;
import org.eclipse.egerrit.internal.model.MergeableInfo;
import org.eclipse.egerrit.internal.model.ModelPackage;
import org.eclipse.egerrit.internal.model.ReviewerInfo;
import org.eclipse.egerrit.internal.model.RevisionInfo;

/**
 * A helper class wrapping the common server queries. All calls in this class are expected to be synchronous.
 */
public class QueryHelpers {
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
		loadFiles(gerrit, revision);
		if (revision.isCommentsLoaded()) {
			return;
		}
		ListCommentsCommand command = gerrit.getListComments(revision.getChangeInfo().getId(), revision.getId());
		Map<String, ArrayList<CommentInfo>> comments = null;
		try {
			comments = command.call();
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
		//We only set the comments as loaded once the values have been set
		revision.setCommentsLoaded(true);
	}

	public static void loadDrafts(GerritClient gerrit, RevisionInfo revision) {
		if (gerrit.getRepository().getServerInfo().isAnonymous()) {
			return;
		}
		loadFiles(gerrit, revision);
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
			files.stream().filter(f -> f.getDraftComments().size() != 0).forEach(f -> f.getDraftComments().clear());
			return;
		}

		for (Entry<String, ArrayList<CommentInfo>> draftComment : drafts.entrySet()) {
			FileInfo files = revision.getFiles().get(draftComment.getKey());
			if (files != null) {
				if (files.getDraftComments().size() != 0) {
					files.getDraftComments().clear();
				}
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

	private static boolean fullyLoaded(ChangeInfo toRefresh) {
		if (toRefresh.getMessages().size() != 0) {
			return true;
		}
		return false;
	}

	private static void mergeNewInformation(ChangeInfo toRefresh, ChangeInfo newChangeInfo) {
		if (toRefresh.getUpdated() != null && toRefresh.getUpdated().equals(newChangeInfo.getUpdated())
				&& fullyLoaded(toRefresh)) {
			return;
		}
		toRefresh.set_number(newChangeInfo.get_number());
		toRefresh.setChange_id(newChangeInfo.getChange_id());
		toRefresh.setStatus(newChangeInfo.getStatus());
		toRefresh.setStarred(newChangeInfo.isStarred());
		toRefresh.setSubject(newChangeInfo.getSubject());
		toRefresh.setCreated(newChangeInfo.getCreated());
		toRefresh.setReviewed(newChangeInfo.isReviewed());
		toRefresh.setInsertions(newChangeInfo.getInsertions());
		toRefresh.setDeletions(newChangeInfo.getDeletions());
		toRefresh.setTopic(newChangeInfo.getTopic());
		toRefresh.setMergeable(newChangeInfo.isMergeable());

		toRefresh.getLabels().clear();
		toRefresh.getLabels().addAll(newChangeInfo.getLabels());
		toRefresh.getMessages().clear();
		toRefresh.getMessages().addAll(newChangeInfo.getMessages());
		mergeRevisions(toRefresh, newChangeInfo); //need to be before setting the actions and the current revision
		toRefresh.setCurrent_revision(newChangeInfo.getCurrent_revision());
		//Re-init the userselected revision after a revert
		if (toRefresh.getUserSelectedRevision() == null) {
			//Initial setting for the user selected revision if not done yet
			toRefresh.setUserSelectedRevision(toRefresh.getRevisions().get(toRefresh.getCurrent_revision()));
		}
		toRefresh.eSet(ModelPackage.Literals.CHANGE_INFO__PERMITTED_LABELS, newChangeInfo.getPermitted_labels());
		toRefresh.eSet(ModelPackage.Literals.CHANGE_INFO__ACTIONS, newChangeInfo.getActions());

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

	public static void loadBasicInformation(GerritClient gerrit, ChangeInfo toRefresh) {
		ChangeInfo newChangeInfo = QueryHelpers.queryBasicInformation(gerrit, toRefresh.getId());
		mergeNewInformation(toRefresh, newChangeInfo);
	}

	private static ChangeInfo queryBasicInformation(GerritClient gerrit, String id) {
		GetChangeCommand command = gerrit.getChange(id);
		command.addOption(ChangeOption.DOWNLOAD_COMMANDS);
		command.addOption(ChangeOption.ALL_REVISIONS);
		command.addOption(ChangeOption.ALL_COMMITS);
		command.addOption(ChangeOption.MESSAGES);
		command.addOption(ChangeOption.DETAILED_LABELS);
		command.addOption(ChangeOption.CURRENT_ACTIONS);

		ChangeInfo res = null;
		try {
			res = command.call();
		} catch (EGerritException e) {
			EGerritCorePlugin.logError(gerrit.getRepository().formatGerritVersion() + e.getMessage());
		}
		return res;
	}

	public static void loadRevisionDetails(GerritClient gerrit, RevisionInfo revision) {
		if (revision == null) {
			throw new IllegalAccessError("Revision can't be null."); //$NON-NLS-1$
		}
		loadFiles(gerrit, revision);
		String[] reviewedFiles = loadReviewedFiles(gerrit, revision);
		for (String reviewed : reviewedFiles) {
			revision.getFiles().get(reviewed).setReviewed(true);
		}
		loadComments(gerrit, revision);
		loadDrafts(gerrit, revision);
	}

	private static String[] loadReviewedFiles(GerritClient gerrit, RevisionInfo revision) {
		GetReviewedFilesCommand command = gerrit.getReviewed(revision.getChangeInfo().getId(), revision.getId());
		try {
			return command.call();
		} catch (EGerritException e) {
			return null;
		}
	}

	public static void loadFiles(GerritClient gerrit, RevisionInfo revision) {
		if (!revision.isFilesLoaded()) {
			GetFilesCommand command = gerrit.getFiles(revision.getChangeInfo().getId(), revision.getId());
			try {
				revision.getFiles().putAll(command.call());
				revision.setFilesLoaded(true);
			} catch (EGerritException e) {
				return;
			}
		}
	}

	private static void loadSameTopic(GerritClient gerritClient, ChangeInfo element) {
		ChangeInfo[] sameTopicChangeInfo = null;
		if (element.getTopic() != null && element.getChange_id().compareTo(element.getChange_id()) != 0) {
			try {
				QueryChangesCommand command = gerritClient.queryChanges();
				command.addTopic(element.getTopic());
				sameTopicChangeInfo = command.call();
				if (sameTopicChangeInfo != null) {
					element.getSameTopic().clear();
					for (ChangeInfo changeInfo : sameTopicChangeInfo) {
						element.getSameTopic().add(changeInfo);
					}
				}
			} catch (EGerritException e) {
				EGerritCorePlugin.logError(gerritClient.getRepository().formatGerritVersion() + e.getLocalizedMessage(),
						e);
			}
		}
	}

	private static void setMergeable(GerritClient gerritClient, ChangeInfo element) {
		if ("MERGED".equals(element.getStatus()) || "ABANDONED".equals(element.getStatus())) { //$NON-NLS-1$ //$NON-NLS-2$
			return;
		}
		try {
			GetMergeableCommand command = gerritClient.getMergeable(element);
			MergeableInfo mergeableInfo;
			mergeableInfo = command.call();
			element.setMergeableInfo(mergeableInfo);
			element.setMergeable(mergeableInfo.isMergeable());
		} catch (EGerritException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void setIncludedIn(GerritClient gerritClient, ChangeInfo element) {
		try {
			GetIncludedInCommand command = gerritClient.getIncludedIn(element.getId());
			IncludedInInfo res = null;
			res = command.call();
			element.setIncludedIn(res);
		} catch (EGerritException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void setReviewers(GerritClient gerritClient, ChangeInfo element) {
		try {
			ListReviewersCommand command = gerritClient.getReviewers(element.getId());
			ReviewerInfo[] reviewers = command.call();
			element.getReviewers().clear();
			for (ReviewerInfo reviewerInfo : reviewers) {
				element.getReviewers().add(reviewerInfo);
			}
			;
		} catch (EGerritException e) {
			EGerritCorePlugin.logError(gerritClient.getRepository().formatGerritVersion() + e.getMessage());
		}
	}

	private static void setRelatedChanges(GerritClient gerritClient, ChangeInfo fChangeInfo) {
		if (gerritClient.getRepository().getServerInfo().isAnonymous()) {
			return;
		}
		try {
			GetRelatedChangesCommand command = gerritClient.getRelatedChanges(fChangeInfo.getId(),
					fChangeInfo.getCurrent_revision());
			fChangeInfo.setRelatedChanges(command.call());
		} catch (EGerritException e) {
			EGerritCorePlugin.logError(gerritClient.getRepository().formatGerritVersion() + e.getMessage());
		}
	}

	private static void setConflictsWith(GerritClient gerritClient, ChangeInfo element) {
		ChangeInfo[] conflictsWithChangeInfo = null;

		if (!("MERGED".equals(element.getStatus())) && !("ABANDONED".equals(element.getStatus()))) {
			try {
				QueryChangesCommand command = gerritClient.queryChanges();
				command.addConflicts(element.getChange_id()); //Here we keep the change_id because the conflicts call does not accept the full id
				command.addMergeable();
				command.addStatus(ChangeStatus.OPEN);
				conflictsWithChangeInfo = command.call();

				List<ChangeInfo> conflictsWithChangeInfolistNew = new ArrayList<ChangeInfo>();
				if (conflictsWithChangeInfo != null) {
					for (ChangeInfo conflictChangeInfo : conflictsWithChangeInfo) {
						if (conflictChangeInfo.getChange_id().compareTo(element.getChange_id()) != 0) {
							conflictsWithChangeInfolistNew.add(conflictChangeInfo);
						}
					}
				}
				element.getConflictsWith().clear();
				element.getConflictsWith().addAll(conflictsWithChangeInfolistNew);
			} catch (EGerritException e) {
				EGerritCorePlugin.logError(gerritClient.getRepository().formatGerritVersion() + e.getMessage());
			}
		}
	}

	public static void loadDetailedInformation(GerritClient gerritClient, ChangeInfo toLoad) {
		loadSameTopic(gerritClient, toLoad);
		setConflictsWith(gerritClient, toLoad);
		setIncludedIn(gerritClient, toLoad);
		setMergeable(gerritClient, toLoad);
		setReviewers(gerritClient, toLoad);
		setRelatedChanges(gerritClient, toLoad);
	}
}
