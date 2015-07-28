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

package org.eclipse.egerrit.core;

import org.eclipse.egerrit.core.command.CreateDraftCommand;
import org.eclipse.egerrit.core.command.GetChangeCommand;
import org.eclipse.egerrit.core.command.GetCommitMsgCommand;
import org.eclipse.egerrit.core.command.GetContentCommand;
import org.eclipse.egerrit.core.command.GetIncludedInCommand;
import org.eclipse.egerrit.core.command.GetMergeableCommand;
import org.eclipse.egerrit.core.command.GetRelatedChangesCommand;
import org.eclipse.egerrit.core.command.ListCommentsCommand;
import org.eclipse.egerrit.core.command.ListDraftsCommand;
import org.eclipse.egerrit.core.command.ListReviewersCommand;
import org.eclipse.egerrit.core.command.PublishDraftRevisionCommand;
import org.eclipse.egerrit.core.command.QueryChangesCommand;
import org.eclipse.egerrit.core.command.SetReviewCommand;
import org.eclipse.egerrit.core.command.SubmitCommand;
import org.eclipse.egerrit.core.exception.EGerritException;

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
public abstract class Gerrit {

	// ------------------------------------------------------------------------
	// Attributes
	// ------------------------------------------------------------------------

	/** The Gerrit repository this class is interacting with */
	private final GerritRepository fGerritRepository;

	// ------------------------------------------------------------------------
	// Constructor
	// ------------------------------------------------------------------------

	/**
	 * Constructs a new {@link Gerrit} object which can interact with the specified Gerrit repository. All command
	 * classes returned by methods of this class will always interact with this Gerrit repository.
	 *
	 * @param gerritRepository
	 *            The gerrit repository this class interacts with ({@code null} is not allowed)
	 */
	protected Gerrit(GerritRepository gerritRepository) throws EGerritException {
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
	public GetCommitMsgCommand getCommitMsg(String change_id, String commit_id) {
		return new GetCommitMsgCommand(fGerritRepository, change_id, commit_id);
	}

	/**
	 * Returns a command object to execute a {@code getCommitMSg} command
	 *
	 * @param id
	 * @param revision
	 * @return a default {@link GetChangeCommand} used to retrieve a change's MergeableInfo from the Gerrit repository
	 */
	public GetMergeableCommand getMergeable(String change_id, String revision_id) {
		return new GetMergeableCommand(fGerritRepository, change_id, revision_id);
	}

	/**
	 * Returns a command object to execute a {@code getCommitMSg} command
	 *
	 * @param id
	 * @return a default {@link GetChangeCommand} used to retrieve a change's ReviewersInfo from the Gerrit repository
	 */
	public ListReviewersCommand getReviewers(String change_id) {
		return new ListReviewersCommand(fGerritRepository, change_id);
	}

	/**
	 * Returns a command object to execute a {@code getCommitMSg} command
	 *
	 * @param id
	 * @return a default {@link GetChangeCommand} used to retrieve a change's IncludedInInfo from the Gerrit repository
	 */
	public GetIncludedInCommand getIncludedIn(String change_id) {
		return new GetIncludedInCommand(fGerritRepository, change_id);
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
	 * @param change_id
	 * @param revision_id
	 * @return
	 */
	public ListCommentsCommand getListComments(String change_id, String revision_id) {
		return new ListCommentsCommand(fGerritRepository, change_id, revision_id);
	}

	/**
	 * Return a command to obtain the list of drafts related to a revision of a change set
	 *
	 * @param change_id
	 * @param revision_id
	 * @return
	 */
	public ListDraftsCommand getDraftcomments(String change_id, String revision_id) {
		return new ListDraftsCommand(fGerritRepository, change_id, revision_id);
	}

	/**
	 * Return a command to create comments related to a revision of a change
	 *
	 * @param change_id
	 * @param revision_id
	 * @return
	 */
	public CreateDraftCommand createDraftComments(String change_id, String revision_id) {
		return new CreateDraftCommand(fGerritRepository, change_id, revision_id);
	}

	/**
	 * Return a command to publish draft of a revision of a change
	 *
	 * @param change_id
	 * @param revision_id
	 * @return
	 */
	public PublishDraftRevisionCommand publishDraftRevision(String change_id, String revision_id) {
		return new PublishDraftRevisionCommand(fGerritRepository, change_id, revision_id);
	}

	/**
	 * Return a command to set Review a revision of a change
	 *
	 * @param change_id
	 * @param revision_id
	 * @return
	 */
	public SetReviewCommand setReview(String change_id, String revision_id) {
		return new SetReviewCommand(fGerritRepository, change_id, revision_id);
	}

	/**
	 * Return a command to extract the list of draft comments related to a revision of a change set
	 *
	 * @param change_id
	 * @param revision_id
	 * @return
	 */
	public ListDraftsCommand listDraftsComments(String change_id, String revision_id) {
		return new ListDraftsCommand(fGerritRepository, change_id, revision_id);
	}

	/**
	 * Return a command to submit a change
	 *
	 * @param change_id
	 * @return
	 */
	public SubmitCommand submit(String change_id) {
		return new SubmitCommand(fGerritRepository, change_id);
	}

}
