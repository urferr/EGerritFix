/*******************************************************************************
 * Copyright (c) 2015-2016 Ericsson AB.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson - initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.internal.ui.editors;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.egerrit.internal.core.EGerritCorePlugin;
import org.eclipse.egerrit.internal.core.GerritClient;
import org.eclipse.egerrit.internal.core.command.ChangeOption;
import org.eclipse.egerrit.internal.core.command.ChangeStatus;
import org.eclipse.egerrit.internal.core.command.DeleteDraftCommand;
import org.eclipse.egerrit.internal.core.command.GetChangeCommand;
import org.eclipse.egerrit.internal.core.command.GetFilesCommand;
import org.eclipse.egerrit.internal.core.command.GetIncludedInCommand;
import org.eclipse.egerrit.internal.core.command.GetMergeableCommand;
import org.eclipse.egerrit.internal.core.command.GetRelatedChangesCommand;
import org.eclipse.egerrit.internal.core.command.GetReviewedFilesCommand;
import org.eclipse.egerrit.internal.core.command.ListCommentsCommand;
import org.eclipse.egerrit.internal.core.command.ListDraftsCommand;
import org.eclipse.egerrit.internal.core.command.ListReviewersCommand;
import org.eclipse.egerrit.internal.core.command.QueryChangesCommand;
import org.eclipse.egerrit.internal.core.exception.EGerritException;
import org.eclipse.egerrit.internal.model.ChangeInfo;
import org.eclipse.egerrit.internal.model.CommentInfo;
import org.eclipse.egerrit.internal.model.FileInfo;
import org.eclipse.egerrit.internal.model.IncludedInInfo;
import org.eclipse.egerrit.internal.model.MergeableInfo;
import org.eclipse.egerrit.internal.model.ModelHelpers;
import org.eclipse.egerrit.internal.model.ModelPackage;
import org.eclipse.egerrit.internal.model.RelatedChangesInfo;
import org.eclipse.egerrit.internal.model.ReviewerInfo;
import org.eclipse.egerrit.internal.model.RevisionInfo;
import org.eclipse.egerrit.internal.ui.utils.Messages;

/**
 * A helper class wrapping the common server queries. All calls in this class are expected to be synchronous.
 */
public class QueryHelpers {

	/**
	 * Get the changes that have a given subject
	 */
	public static ChangeInfo[] lookupPartialChangeInfoFromSubject(GerritClient gerritClient, String subject,
			IProgressMonitor monitor) throws MalformedURLException {
		try {
			monitor.beginTask(Messages.QueryHelpers_executingQuery, IProgressMonitor.UNKNOWN);

			QueryChangesCommand command = gerritClient.queryChanges();
			command.addOption(ChangeOption.LABELS);
			command.addOption(ChangeOption.DETAILED_LABELS);
			command.addOption(ChangeOption.DETAILED_ACCOUNTS);
			command.addOption(ChangeOption.MESSAGES);
			command.addOption(ChangeOption.ALL_REVISIONS);
			command.addOption(ChangeOption.ALL_COMMITS);
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

	private static String setFreeText(String subject) {
		// use the subject to query the server
		String query = "message:\"" + subject + "\""; //$NON-NLS-1$ //$NON-NLS-2$
		return query;
	}

	/**
	 * Get a review by changeId
	 */
	public static ChangeInfo lookupPartialChangeInfoFromChangeId(GerritClient gerrit, String change_id,
			IProgressMonitor monitor) {
		try {
			monitor.beginTask(Messages.QueryHelpers_executingQuery, IProgressMonitor.UNKNOWN);

			GetChangeCommand command = null;
			command = gerrit.getChange(change_id);
			command.addOption(ChangeOption.LABELS);
			command.addOption(ChangeOption.DETAILED_LABELS);
			command.addOption(ChangeOption.DETAILED_ACCOUNTS);
			command.addOption(ChangeOption.MESSAGES);
			command.addOption(ChangeOption.ALL_REVISIONS);
			command.addOption(ChangeOption.ALL_COMMITS);
			command.addOption(ChangeOption.CURRENT_ACTIONS);

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

		synchronized (revision.getChangeInfo()) {
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

		synchronized (revision.getChangeInfo()) {
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
	}

	public static void markAsReviewed(GerritClient gerrit, FileInfo fileInfo) {
		if (gerrit.getRepository().getServerInfo().isAnonymous()) {
			return;
		}
		synchronized (fileInfo.getRevision().getChangeInfo()) {
			if (fileInfo.isReviewed()) {
				return;
			}
			try {
				gerrit.setReviewed(fileInfo.getRevision().getChangeInfo().getId(), fileInfo.getRevision().getId(),
						fileInfo).call();
			} catch (EGerritException ex) {
				EGerritCorePlugin.logError(gerrit.getRepository().formatGerritVersion() + ex.getMessage());
			}
		}
	}

	public static void markAsNotReviewed(GerritClient gerrit, FileInfo fileInfo) {
		if (gerrit.getRepository().getServerInfo().isAnonymous()) {
			return;
		}
		synchronized (fileInfo.getRevision().getChangeInfo()) {
			if (!fileInfo.isReviewed()) {
				return;
			}

			try {
				gerrit.deleteReviewed(fileInfo.getRevision().getChangeInfo().getId(), fileInfo.getRevision().getId(),
						fileInfo).call();
			} catch (EGerritException ex) {
				EGerritCorePlugin.logError(gerrit.getRepository().formatGerritVersion() + ex.getMessage());
			}
		}
	}

	private static boolean fullyLoaded(ChangeInfo toRefresh) {
		synchronized (toRefresh) {
			if (toRefresh.getLoadingLevel() == 0) {
				return false;
			}
			return true;
		}
	}

	private static void mergeNewInformation(ChangeInfo toRefresh, ChangeInfo newChangeInfo, boolean forceReload) {
		synchronized (toRefresh) {
			if (!forceReload) {
				if (toRefresh.getUpdated() != null && toRefresh.getUpdated().equals(newChangeInfo.getUpdated())
						&& fullyLoaded(toRefresh)) {
					return;
				}
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
			mergeRevisions(toRefresh, newChangeInfo); //need to be before setting the actions and the current revision
			if (newChangeInfo.getCurrent_revision() != null
					&& newChangeInfo.getRevisions().get(newChangeInfo.getCurrent_revision()) != null) {
				toRefresh.setCurrent_revision(newChangeInfo.getCurrent_revision());
			} else {
				toRefresh.setCurrent_revision(toRefresh
						.getRevisionByNumber(ModelHelpers.getHighestRevisionNumber(toRefresh.getRevisions().values()))
						.getId());
			}
			//Re-init the userselected revision after a revert
			if (toRefresh.getUserSelectedRevision() == null) {
				//Initial setting for the user selected revision if not done yet
				toRefresh.setUserSelectedRevision(toRefresh.getRevisions().get(toRefresh.getCurrent_revision()));
			}
			toRefresh.eSet(ModelPackage.Literals.CHANGE_INFO__PERMITTED_LABELS, newChangeInfo.getPermitted_labels());
			toRefresh.eSet(ModelPackage.Literals.CHANGE_INFO__ACTIONS, newChangeInfo.getActions());

			//Replace all the messages if there are new ones.
			if (newChangeInfo.getMessages().size() != toRefresh.getMessages().size()) {
				toRefresh.getMessages().clear();
				toRefresh.getMessages().addAll(newChangeInfo.getMessages());
			}

			//Set the date at the end because it is used to trigger other refreshes
			toRefresh.setUpdated(newChangeInfo.getUpdated());
			toRefresh.setLoadingLevel(1);
		}
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
				rev.setCommit(newChangeInfo.getRevisions().get(revId).getCommit());
				rev.setRef(newChangeInfo.getRevisions().get(revId).getRef());
				rev.setDraft(newChangeInfo.getRevisions().get(revId).isDraft());
			}
		});

		//Identify the revisions that could have been removed
		Set<String> deletedKeys = new HashSet<>();
		for (String key : oldKeys) {
			if (newChangeInfo.getRevisions().get(key) == null) {
				deletedKeys.add(key);
			}
		}

		//Delete all the revisions that have been removed from the server
		deletedKeys.stream().forEach(toRefresh.getRevisions()::removeKey);

		//Isolate the revisions that need to be kept by removing the entries already loaded from the new collection
		//Once this is done we simply add the new remaining of the revisions to the old revision
		oldKeys.stream().forEach(newChangeInfo.getRevisions()::removeKey);

		//Take all the other revisions as they are since they are new
		toRefresh.getRevisions().putAll(newChangeInfo.getRevisions());
	}

	public static void loadBasicInformation(GerritClient gerrit, ChangeInfo toRefresh, boolean forceReload) {
		ChangeInfo newChangeInfo = QueryHelpers.queryBasicInformation(gerrit, toRefresh.getId());
		mergeNewInformation(toRefresh, newChangeInfo, forceReload);
	}

	private static ChangeInfo queryBasicInformation(GerritClient gerrit, String id) {
		GetChangeCommand command = gerrit.getChange(id);
		command.addOption(ChangeOption.LABELS);
		command.addOption(ChangeOption.DETAILED_LABELS);
		command.addOption(ChangeOption.DETAILED_ACCOUNTS);
		command.addOption(ChangeOption.MESSAGES);
		command.addOption(ChangeOption.ALL_REVISIONS);
		command.addOption(ChangeOption.ALL_COMMITS);
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
		loadReviewedFiles(gerrit, revision);
		loadComments(gerrit, revision);
		loadDrafts(gerrit, revision);
	}

	private static void loadReviewedFiles(GerritClient gerrit, RevisionInfo revision) {
		if (gerrit.getRepository().getServerInfo().isAnonymous()) {
			return;
		}
		GetReviewedFilesCommand command = gerrit.getReviewed(revision.getChangeInfo().getId(), revision.getId());
		try {
			String[] reviewedFiles = command.call();
			synchronized (revision.getChangeInfo()) {
				for (String reviewed : reviewedFiles) {
					FileInfo fileToMarkReviewed = revision.getFiles().get(reviewed);
					if (fileToMarkReviewed != null) {
						fileToMarkReviewed.setReviewed(true);
					}
				}
			}
		} catch (EGerritException e) {
			return;
		}
	}

	public static void loadFiles(GerritClient gerrit, RevisionInfo revision) {
		synchronized (revision) {
			if (revision.isFilesLoaded()) {
				return;
			}
		}
		GetFilesCommand command = gerrit.getFiles(revision.getChangeInfo().getId(), revision.getId());
		try {
			Map<String, FileInfo> result = command.call();
			synchronized (revision) {
				revision.getFiles().putAll(result);
				revision.setFilesLoaded(true);
			}
		} catch (EGerritException e) {
			return;
		}
	}

	private static void loadSameTopic(GerritClient gerritClient, ChangeInfo element) {
		ChangeInfo[] sameTopicChangeInfo = null;
		synchronized (element) {
			if (element.getTopic() == null) {
				return;
			}
		}
		try {
			QueryChangesCommand command = gerritClient.queryChanges();
			command.addTopic(element.getTopic());
			sameTopicChangeInfo = command.call();
			synchronized (element) {
				if (sameTopicChangeInfo != null) {
					element.getSameTopic().clear();
					for (ChangeInfo changeInfo : sameTopicChangeInfo) {
						element.getSameTopic().add(changeInfo);
					}
				}
			}
		} catch (EGerritException e) {
			EGerritCorePlugin.logError(gerritClient.getRepository().formatGerritVersion() + e.getLocalizedMessage(), e);
		}
	}

	private static void loadMergeable(GerritClient gerritClient, ChangeInfo element) {
		synchronized (element) {
			if ("MERGED".equals(element.getStatus()) || "ABANDONED".equals(element.getStatus())) { //$NON-NLS-1$ //$NON-NLS-2$
				return;
			}
		}
		try {
			GetMergeableCommand command = gerritClient.getMergeable(element);
			MergeableInfo mergeableInfo;
			mergeableInfo = command.call();
			synchronized (element) {
				element.setMergeableInfo(mergeableInfo);
				element.setMergeable(mergeableInfo.isMergeable());
			}
		} catch (EGerritException e) {
			EGerritCorePlugin.logError(gerritClient.getRepository().formatGerritVersion() + e.getLocalizedMessage(), e);
		}
	}

	private static void loadIncludedIn(GerritClient gerritClient, ChangeInfo element) {
		try {
			GetIncludedInCommand command = gerritClient.getIncludedIn(element.getId());
			IncludedInInfo res = null;
			res = command.call();
			synchronized (element) {
				element.setIncludedIn(res);
			}
		} catch (EGerritException e) {
			EGerritCorePlugin.logError(gerritClient.getRepository().formatGerritVersion() + e.getLocalizedMessage(), e);
		}
	}

	private static void loadReviewers(GerritClient gerritClient, ChangeInfo element) {
		try {
			ListReviewersCommand command = gerritClient.getReviewers(element.getId());
			ReviewerInfo[] reviewers = command.call();
			synchronized (element) {
				element.getComputedReviewers().clear();
				for (ReviewerInfo reviewerInfo : reviewers) {
					element.getComputedReviewers().add(reviewerInfo);
				}
			}
		} catch (EGerritException e) {
			EGerritCorePlugin.logError(gerritClient.getRepository().formatGerritVersion() + e.getMessage());
		}
	}

	private static void loadRelatedChanges(GerritClient gerritClient, ChangeInfo fChangeInfo) {
		if (gerritClient.getRepository().getServerInfo().isAnonymous()) {
			return;
		}
		try {
			GetRelatedChangesCommand command = gerritClient.getRelatedChanges(fChangeInfo.getId(),
					fChangeInfo.getCurrent_revision());
			RelatedChangesInfo res = command.call();
			synchronized (fChangeInfo) {
				fChangeInfo.setRelatedChanges(res);
			}
		} catch (EGerritException e) {
			EGerritCorePlugin.logError(gerritClient.getRepository().formatGerritVersion() + e.getMessage());
		}
	}

	private static void loadConflictsWith(GerritClient gerritClient, ChangeInfo element) {
		ChangeInfo[] conflictsWithChangeInfo = null;

		synchronized (element) {
			if (("MERGED".equals(element.getStatus()) || "ABANDONED".equals(element.getStatus()))) { //$NON-NLS-1$ //$NON-NLS-2$
				return;
			}
		}
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
			synchronized (element) {
				element.getConflictsWith().clear();
				element.getConflictsWith().addAll(conflictsWithChangeInfolistNew);
			}
		} catch (EGerritException e) {
			EGerritCorePlugin.logError(gerritClient.getRepository().formatGerritVersion() + e.getMessage());
		}
	}

	public static void loadDetailedInformation(GerritClient gerritClient, ChangeInfo toLoad) {
		loadSameTopic(gerritClient, toLoad);
		loadConflictsWith(gerritClient, toLoad);
		loadIncludedIn(gerritClient, toLoad);
		loadMergeable(gerritClient, toLoad);
		loadReviewers(gerritClient, toLoad);
		loadRelatedChanges(gerritClient, toLoad);
	}

	public static void deleteDraft(GerritClient gerritClient, CommentInfo toDelete) {
		RevisionInfo revision = ModelHelpers.getRevision(toDelete);
		DeleteDraftCommand deleteDraft = gerritClient.deleteDraft(revision.getChangeInfo().getId(), revision.getId(),
				toDelete.getId());
		try {
			deleteDraft.call();
			ModelHelpers.getFileInfo(toDelete).getDraftComments().remove(toDelete);
		} catch (EGerritException e) {
			//Nothing to do
		}
	}

}
