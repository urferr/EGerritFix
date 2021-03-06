/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Francois Chouinard - Initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.internal.core;

import org.eclipse.egerrit.internal.core.command.AbandonCommand;
import org.eclipse.egerrit.internal.core.command.AddReviewerCommand;
import org.eclipse.egerrit.internal.core.command.ChangeCommitMsgCommand;
import org.eclipse.egerrit.internal.core.command.CherryPickRevisionCommand;
import org.eclipse.egerrit.internal.core.command.CreateDraftCommand;
import org.eclipse.egerrit.internal.core.command.DeleteDraftChangeCommand;
import org.eclipse.egerrit.internal.core.command.DeleteDraftCommand;
import org.eclipse.egerrit.internal.core.command.DeleteDraftRevisionCommand;
import org.eclipse.egerrit.internal.core.command.DeleteReviewedCommand;
import org.eclipse.egerrit.internal.core.command.DeleteReviewerCommand;
import org.eclipse.egerrit.internal.core.command.DeleteTopicCommand;
import org.eclipse.egerrit.internal.core.command.GetChangeCommand;
import org.eclipse.egerrit.internal.core.command.GetCommitMsgCommand;
import org.eclipse.egerrit.internal.core.command.GetContentCommand;
import org.eclipse.egerrit.internal.core.command.GetContentFromCommitCommand;
import org.eclipse.egerrit.internal.core.command.GetDiffCommand;
import org.eclipse.egerrit.internal.core.command.GetFilesCommand;
import org.eclipse.egerrit.internal.core.command.GetIncludedInCommand;
import org.eclipse.egerrit.internal.core.command.GetMergeableCommand;
import org.eclipse.egerrit.internal.core.command.GetModifiedFilesCommand;
import org.eclipse.egerrit.internal.core.command.GetRelatedChangesCommand;
import org.eclipse.egerrit.internal.core.command.GetReviewedFilesCommand;
import org.eclipse.egerrit.internal.core.command.GetRevisionActionsCommand;
import org.eclipse.egerrit.internal.core.command.ListBranchesCommand;
import org.eclipse.egerrit.internal.core.command.ListCommentsCommand;
import org.eclipse.egerrit.internal.core.command.ListDraftsCommand;
import org.eclipse.egerrit.internal.core.command.ListProjectsCommand;
import org.eclipse.egerrit.internal.core.command.ListReviewersCommand;
import org.eclipse.egerrit.internal.core.command.PublishChangeEditCommand;
import org.eclipse.egerrit.internal.core.command.PublishDraftChangeCommand;
import org.eclipse.egerrit.internal.core.command.QueryChangesCommand;
import org.eclipse.egerrit.internal.core.command.RebaseRevisionCommand;
import org.eclipse.egerrit.internal.core.command.RestoreCommand;
import org.eclipse.egerrit.internal.core.command.RevertCommand;
import org.eclipse.egerrit.internal.core.command.SetReviewCommand;
import org.eclipse.egerrit.internal.core.command.SetReviewedCommand;
import org.eclipse.egerrit.internal.core.command.SetTopicCommand;
import org.eclipse.egerrit.internal.core.command.StarChangeCommand;
import org.eclipse.egerrit.internal.core.command.SubmitCommand;
import org.eclipse.egerrit.internal.core.command.SuggestAccountCommand;
import org.eclipse.egerrit.internal.core.command.SuggestReviewersCommand;
import org.eclipse.egerrit.internal.core.command.UnstarChangeCommand;
import org.eclipse.egerrit.internal.core.command.UpdateDraftCommand;
import org.eclipse.egerrit.internal.core.exception.EGerritException;
import org.eclipse.egerrit.internal.model.ChangeInfo;
import org.eclipse.egerrit.internal.model.FileInfo;

/**
 * Provides an API to interact with a Gerrit repository using its REST API. The set of available commands is based on
 * Gerrit v2.9.
 * <p>
 * The Gerrit REST commands are described in the
 * <a href= "http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html"> Gerrit Documentation</a>.
 * <p>
 * This class only offers methods to construct so-called command classes. Each Gerrit REST command is represented by one
 * such command class.
 * <p>
 * <u>Example</u>:
 * <p>
 * This class provides a {@code queryChanges()} method returning an instance of the {@code QueryChangesCommand} class.
 * The {@code QueryChangesCommand} class has setters for all its arguments and options. The {@code QueryChangesCommand}
 * base class ({@code GerritCommand}) provides the {@code call()} method which actually performs the request to the
 * Gerrit server.
 * <p>
 * The following code shows how to fetch the list of open changes watched by the caller:
 *
 * <pre>
 * GerritRepository gerritRepository = new GerritRepository(...);
 * Gerrit gerrit = GerritFactory.create(gerritRepository);
 * ChangeInfo[] changes = gerrit.queryChanges()
 *                              .setOwner("self")
 *                              .setStatus(ChangeStatus.OPEN)
 *                              .setState(ChangeState.IS_WATCHED)
 *                              .call();
 * </pre>
 *
 * All mandatory parameters for a given command have to be specified as arguments on this class' corresponding command
 * 'factory'. The optional parameters are supplied by using setter methods of the xxxCommand class.
 * <p>
 * Notes:
 * <p>
 * - Each Gerrit concrete class must provide its version string (GERRIT_VERSION) where [major], [minor] and [micro] are
 * mandatory (@see Gerrit_2_9)
 * <p>
 * - GerritFactory must be aware of each concrete class extending Gerrit to do its job.
 *
 * @since 1.0
 */
public abstract class GerritClient {

	// ------------------------------------------------------------------------
	// Attributes
	// ------------------------------------------------------------------------

	/** The Gerrit repository this class is interacting with */
	private final GerritRepository fGerritRepository;

	// ------------------------------------------------------------------------
	// Constructor
	// ------------------------------------------------------------------------

	/**
	 * Constructs a new {@link GerritClient} object which can interact with the specified Gerrit repository. All command
	 * classes returned by methods of this class will always interact with this Gerrit repository.
	 *
	 * @param gerritRepository
	 *            The gerrit repository this class interacts with ({@code null} is not allowed)
	 */
	protected GerritClient(GerritRepository gerritRepository) throws EGerritException {
		if (gerritRepository == null) {
			throw new EGerritException("Invalid gerrit repository"); //$NON-NLS-1$
		}
		fGerritRepository = gerritRepository;
	}

	// ------------------------------------------------------------------------
	// Getters/Setters
	// ------------------------------------------------------------------------

	/**
	 * @return the Gerrit repository this class is interacting with
	 */
	public GerritRepository getRepository() {
		return fGerritRepository;
	}

	// ------------------------------------------------------------------------
	// Repository queries
	// ------------------------------------------------------------------------

	/**
	 * Returns a command object to execute a {@code queryChanges} command
	 *
	 * @return a default {@link QueryChangesCommand} used to retrieve a list of ChangeInfo:s from the Gerrit repository
	 * @see <a href= "http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#list-changes" >Gerrit
	 *      REST API: Query Changes</a>
	 */
	public QueryChangesCommand queryChanges() {
		return new QueryChangesCommand(fGerritRepository);
	}

	/**
	 * Returns a command object to execute a {@code getChange} command
	 *
	 * @return a default {@link QueryChangesCommand} used to retrieve a ChangeInfo:s from the Gerrit repository
	 * @see <a href= "http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#list-changes" >Gerrit
	 *      REST API: Query Changes</a>
	 */
	public GetChangeCommand getChange(String id) {
		return new GetChangeCommand(fGerritRepository, id);
	}

	/**
	 * Returns a command object to execute a {@code getContent} command
	 *
	 * @param id
	 * @param revision
	 * @param file
	 * @return a default {@link GetChangeCommand} used to retrieve a file's content as String:s from the Gerrit
	 *         repository
	 * @see <a href= "http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#get-content" >Gerrit
	 *      REST API: Get Content</a>
	 */
	public GetContentCommand getContent(String id, String revision, String file) {
		return new GetContentCommand(fGerritRepository, id, revision, file);
	}

	/**
	 * Returns a command object to execute a {@code getCommitMSg} command
	 *
	 * @param id
	 * @param revision
	 * @return a default {@link GetChangeCommand} used to retrieve a change's commitInfo from the Gerrit repository
	 * @see <a href= "http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#get-commit" >Gerrit REST
	 *      API: Get Commit</a>
	 */
	public GetCommitMsgCommand getCommitMsg(String changeId, String commitId) {
		return new GetCommitMsgCommand(fGerritRepository, changeId, commitId);
	}

	/**
	 * Returns a command object to execute a {@code getCommitMSg} command
	 *
	 * @param id
	 * @param revision
	 * @return a default {@link GetChangeCommand} used to retrieve a change's MergeableInfo from the Gerrit repository
	 */
	public GetMergeableCommand getMergeable(ChangeInfo change) {
		return new GetMergeableCommand(fGerritRepository, change, null);
	}

	/**
	 * Returns a command object to execute a {@code getCommitMSg} command
	 *
	 * @param id
	 * @return a default {@link GetChangeCommand} used to retrieve a change's ReviewersInfo from the Gerrit repository
	 */
	public ListReviewersCommand getReviewers(String changeId) {
		return new ListReviewersCommand(fGerritRepository, changeId);
	}

	/**
	 * Returns a command object to execute a {@code getCommitMSg} command
	 *
	 * @param id
	 * @return a default {@link SuggestReviewersCommand} used to retrieve a list of possible reviewers from the Gerrit
	 *         repository
	 */
	public SuggestReviewersCommand suggestReviewers(String changeId) {
		return new SuggestReviewersCommand(fGerritRepository, changeId);
	}

	/**
	 * Returns a command object to execute a {@code suggestAccount} command
	 *
	 * @param id
	 * @return a default {@link SuggestAccountCommand} used to retrieve a list of possible users from the Gerrit
	 *         repository
	 */
	public SuggestAccountCommand suggestAccount() {
		return new SuggestAccountCommand(fGerritRepository);
	}

	/**
	 * Returns a command object to execute a {@code getCommitMSg} command
	 *
	 * @param id
	 * @return a default {@link GetChangeCommand} used to retrieve a change's IncludedInInfo from the Gerrit repository
	 */
	public GetIncludedInCommand getIncludedIn(String changeId) {
		return new GetIncludedInCommand(fGerritRepository, changeId);
	}

	/**
	 * Returns a command object to execute a {@code getContent} command
	 *
	 * @param id
	 * @param revision
	 * @return a default {@link GetChangeCommand} used to retrieve a change's content from the Gerrit repository
	 * @see <a href= "http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#get-relatedchanges" >
	 *      Gerrit REST API: Get Related Changes</a>
	 */

	public GetRelatedChangesCommand getRelatedChanges(String id, String revision) {
		return new GetRelatedChangesCommand(fGerritRepository, id, revision);
	}

	/**
	 * Return a command to extract the list of comments related to a revision of a change set
	 *
	 * @param changeId
	 * @param revisionId
	 * @return ListCommentsCommand
	 */
	public ListCommentsCommand getListComments(String changeId, String revisionId) {
		return new ListCommentsCommand(fGerritRepository, changeId, revisionId);
	}

	/**
	 * Return a command to create comments related to a revision of a change
	 *
	 * @param changeId
	 * @param revisionId
	 * @return CreateDraftCommand
	 */
	public CreateDraftCommand createDraftComments(String changeId, String revisionId) {
		return new CreateDraftCommand(fGerritRepository, changeId, revisionId);
	}

	/**
	 * Return a command to set Review a revision of a change
	 *
	 * @param changeId
	 * @param revisionId
	 * @return SetReviewCommand
	 */
	public SetReviewCommand setReview(String changeId, String revisionId) {
		return new SetReviewCommand(fGerritRepository, changeId, revisionId);
	}

	/**
	 * Return a command to extract the list of draft comments related to a revision of a change set
	 *
	 * @param changeId
	 * @param revisionId
	 * @return ListDraftsCommand
	 */
	public ListDraftsCommand listDraftsComments(String changeId, String revisionId) {
		return new ListDraftsCommand(fGerritRepository, changeId, revisionId);
	}

	/**
	 * Returns a command object to execute a {@code listProjects} command
	 *
	 * @param id
	 * @return a default {@link ListProjectsCommand} used to retrieve a list of possible projects from the Gerrit
	 *         repository
	 */
	public ListProjectsCommand listProjects() {
		return new ListProjectsCommand(fGerritRepository);
	}

	/**
	 * Return a command to submit a change
	 *
	 * @param changeId
	 * @return SubmitCommand
	 */
	public SubmitCommand submit(String changeId) {
		return new SubmitCommand(fGerritRepository, changeId);
	}

	/**
	 * Return a command to abandon a change
	 *
	 * @param changeId
	 * @return AbandonCommand
	 */
	public AbandonCommand abandon(String changeId) {
		return new AbandonCommand(fGerritRepository, changeId);
	}

	/**
	 * Return a command to restore a change
	 *
	 * @param changeId
	 * @return RestoreCommand
	 */
	public RestoreCommand restore(String changeId) {
		return new RestoreCommand(fGerritRepository, changeId);
	}

	/**
	 * @param changeId
	 * @return AddReviewerCommand
	 */
	public AddReviewerCommand addReviewer(String changeId) {
		return new AddReviewerCommand(fGerritRepository, changeId);
	}

	/**
	 * @param changeId
	 * @param accountId
	 * @return DeleteReviewerCommand.
	 */
	public DeleteReviewerCommand deleteReviewer(String changeId, String accountId) {
		return new DeleteReviewerCommand(fGerritRepository, changeId, accountId);
	}

	/**
	 * @param changeId
	 * @param accountId
	 * @param draftId
	 * @return DeleteDraftCommand.
	 */
	public DeleteDraftCommand deleteDraft(String changeId, String revisionId, String draftId) {
		return new DeleteDraftCommand(fGerritRepository, changeId, revisionId, draftId);
	}

	/**
	 * @param changeId
	 * @param accountId
	 * @param draftId
	 * @return UpdateDraftCommand.
	 */
	public UpdateDraftCommand updateDraftComments(String changeId, String revisionId, String draftId) {
		return new UpdateDraftCommand(fGerritRepository, changeId, revisionId, draftId);
	}

	/**
	 * @param project
	 * @param commitId
	 * @param fileId
	 * @return GetContentFromCommitCommand a command to getContent of a change
	 * @see <a href= "http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#get-content" >Gerrit
	 *      REST API: Get Content</a>
	 */
	public GetContentFromCommitCommand getContentFromCommit(String project, String commitId, String fileId) {
		return new GetContentFromCommitCommand(fGerritRepository, project, commitId, fileId);
	}

	/**
	 * Return a command to extract the list of actions related to a revision of a change set
	 *
	 * @param changeId
	 * @param revision_id
	 * @return GetRevisionActionsCommand
	 */
	public GetRevisionActionsCommand getRevisionActions(String changeId, String currentRevision) {
		return new GetRevisionActionsCommand(fGerritRepository, changeId, currentRevision);

	}

	/**
	 * @param project
	 * @return ListBranchesCommand a command to list branches of a project
	 * @see <a href= "http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#list-branches" >Gerrit
	 *      REST API: List Branches</a>
	 */
	public ListBranchesCommand listBranches(String project) {
		return new ListBranchesCommand(fGerritRepository, project);
	}

	/**
	 * Return a command to Cherry picks a revision to a destination branch
	 *
	 * @param changeId
	 * @param revisionId
	 * @return CherryPickRevisionCommand
	 */
	public CherryPickRevisionCommand cherryPickRevision(String changeId, String revisionId) {
		return new CherryPickRevisionCommand(fGerritRepository, changeId, revisionId);
	}

	/**
	 * Return a command to edit the commit message of a change
	 *
	 * @param changeId
	 * @return ChangeCommitMsgCommand
	 */
	public ChangeCommitMsgCommand editMessage(String changeId) {
		return new ChangeCommitMsgCommand(fGerritRepository, changeId);
	}

	/**
	 * Return a command to publish the commit message of a change
	 *
	 * @param changeId
	 * @return PublishChangeEditCommand
	 */
	public PublishChangeEditCommand publishChangeEdit(String changeId) {
		return new PublishChangeEditCommand(fGerritRepository, changeId);
	}

	/**
	 * Return a command that allow to mark a file as reviewed
	 *
	 * @param changeId
	 * @param revisionId
	 * @param fileName
	 * @return {@link SetReviewedCommand}
	 */
	public SetReviewedCommand setReviewed(String changeId, String revisionId, FileInfo file) {
		return new SetReviewedCommand(fGerritRepository, changeId, revisionId, file);
	}

	/**
	 * Return a command that allow to remove a reviewed mark from a file
	 *
	 * @param changeId
	 * @param revisionId
	 * @param fileName
	 * @return {@link DeleteReviewedCommand}
	 */
	public DeleteReviewedCommand deleteReviewed(String changeId, String revisionId, FileInfo file) {
		return new DeleteReviewedCommand(fGerritRepository, changeId, revisionId, file);
	}

	/**
	 * Return a command that allow to get the list of files that have been mark as reviewed
	 *
	 * @param changeId
	 * @param revisionId
	 * @param fileName
	 * @return {@link GetReviewedFilesCommand}
	 */
	public GetReviewedFilesCommand getReviewed(String changeId, String revisionId) {
		return new GetReviewedFilesCommand(fGerritRepository, changeId, revisionId);
	}

	/**
	 * Return a command that allows to set the topic of a review
	 *
	 * @param changeId
	 * @return {@link SetTopicCommand}
	 */
	public SetTopicCommand setTopic(String changeId) {
		return new SetTopicCommand(fGerritRepository, changeId);
	}

	/**
	 * Return a command that allows to delete the topic of a review
	 *
	 * @param changeId
	 * @return {@link DeleteTopicCommand}
	 */
	public DeleteTopicCommand deleteTopic(String changeId) {
		return new DeleteTopicCommand(fGerritRepository, changeId);
	}

	/**
	 * Return a command to revert a change
	 *
	 * @param changeId
	 * @return RevertCommand
	 */
	public RevertCommand revert(String changeId) {
		return new RevertCommand(fGerritRepository, changeId);
	}

	/**
	 * Return a command to delete a draft change
	 *
	 * @param changeId
	 * @return DeleteDraftChangeCommand
	 */
	public DeleteDraftChangeCommand deleteDraftChange(String changeId) {
		return new DeleteDraftChangeCommand(fGerritRepository, changeId);
	}

	/**
	 * Return a command to publish a draft change
	 *
	 * @param changeId
	 * @return PublishDraftChangeCommand
	 */
	public PublishDraftChangeCommand publishDraftChange(String changeId) {
		return new PublishDraftChangeCommand(fGerritRepository, changeId);
	}

	/**
	 * Return a command to delete a draft revision change
	 *
	 * @param changeId
	 * @return DeleteDraftRevisionCommand
	 */
	public DeleteDraftRevisionCommand deleteDraftRevision(String changeId, String commitId) {
		return new DeleteDraftRevisionCommand(fGerritRepository, changeId, commitId);
	}

	/**
	 * Return a command to get the list of modified files since a given revision
	 */
	public GetModifiedFilesCommand getFilesModifiedSince(String changeId, String revisionId, String compareAgainst) {
		return new GetModifiedFilesCommand(fGerritRepository, changeId, revisionId, compareAgainst);
	}

	/**
	 * Return a command to get the list of files
	 */
	public GetFilesCommand getFiles(String changeId, String revisionId) {
		return new GetFilesCommand(fGerritRepository, changeId, revisionId);
	}

	/**
	 * Return a command to star a change
	 *
	 * @param change_id
	 * @return StarChangeCommand
	 */
	public StarChangeCommand starChange(ChangeInfo changeInfo) {
		return new StarChangeCommand(fGerritRepository, changeInfo);
	}

	/**
	 * Return a command to unstar a change
	 *
	 * @param change_id
	 * @return UnstarChangeCommand
	 */
	public UnstarChangeCommand unstarChange(ChangeInfo changeInfo) {
		return new UnstarChangeCommand(fGerritRepository, changeInfo);
	}

	/**
	 * Return a command to rebase a revision of a change
	 *
	 * @param changeId
	 * @param revisionId
	 * @return RebaseRevisionCommand
	 */
	public RebaseRevisionCommand rebase(String changeId, String revisionId) {
		return new RebaseRevisionCommand(fGerritRepository, changeId, revisionId);
	}

	public GetDiffCommand getDiff(String changeId, String revisionId, String fileName, int base) {
		return new GetDiffCommand(fGerritRepository, changeId, revisionId, fileName, base);
	}
}
