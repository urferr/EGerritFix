package org.eclipse.egerrit.internal.model;
/**
 *   Copyright (c) 2015 Ericsson AB
 *  
 *   All rights reserved. This program and the accompanying materials are
 *   made available under the terms of the Eclipse Public License v1.0 which
 *   accompanies this distribution, and is available at
 *   http://www.eclipse.org/legal/epl-v10.html
 *  
 *   Contributors:
 *     Ericsson AB - Initial API and implementation
 */

import java.util.Collection;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.egerrit.internal.model.impl.ChangeInfoImpl;
import org.eclipse.egerrit.internal.model.impl.StringToActionInfoImpl;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EContentAdapter;

public class ModifiedChangeInfoImpl extends ChangeInfoImpl {
	public ModifiedChangeInfoImpl() {
		eAdapters().add(new EContentAdapter() {
			{
				ModifiedChangeInfoImpl.this.eAdapters().add(this);
			}

			@Override
			public void notifyChanged(Notification msg) {
				super.notifyChanged(msg);

				if (msg.getFeature() == null)
					return;

				if (msg.getFeature().equals(ModelPackage.Literals.CHANGE_INFO__CURRENT_REVISION)) {
					InternalEObject modifiedChangeInfo = (InternalEObject) msg.getNotifier();
					notifySet(modifiedChangeInfo, ModelPackage.Literals.CHANGE_INFO__REVISION);
				}
				if (msg.getFeature().equals(ModelPackage.Literals.CHANGE_INFO__REVISIONS)) {
					deriveCommentPresence();
				}

				if (msg.getFeature().equals(ModelPackage.Literals.CHANGE_INFO__MESSAGES)) {
					deriveCommentPresence();
				}

				// Notify that the value of latest patch set could have changed
				if (msg.getFeature().equals(ModelPackage.Literals.FILE_INFO__DRAFT_COMMENTS)) {
					InternalEObject modifiedFileInfo = (InternalEObject) msg.getNotifier();
					if (eNotificationRequired()) {
						modifiedFileInfo.eNotify(new ENotificationImpl(modifiedFileInfo, Notification.SET,
								ModelPackage.Literals.FILE_INFO__DRAFTS_COUNT, null,
								modifiedFileInfo.eGet(ModelPackage.Literals.FILE_INFO__DRAFTS_COUNT)));
					}
				}

				if (msg.getFeature().equals(ModelPackage.Literals.FILE_INFO__COMMENTS)) {
					InternalEObject modifiedFileInfo = (InternalEObject) msg.getNotifier();
					if (eNotificationRequired()) {
						modifiedFileInfo.eNotify(new ENotificationImpl(modifiedFileInfo, Notification.SET,
								ModelPackage.Literals.FILE_INFO__COMMENTS_COUNT, null,
								modifiedFileInfo.eGet(ModelPackage.Literals.FILE_INFO__COMMENTS_COUNT)));
					}
				}

				if (msg.getFeature().equals(ModelPackage.Literals.REVISION_INFO__ACTIONS)) {
					recomputeRevisionInfoActions(msg);
				}

				if (msg.getFeature().equals(ModelPackage.Literals.CHANGE_INFO__ACTIONS)) {
					recomputeChangeInfoActions(msg);
				}
				if (msg.getFeature().equals(ModelPackage.Literals.CHANGE_INFO__REMOVABLE_REVIEWERS)) {
					recomputeChangeInfoRemovalReviewer(msg);
				}
			}

			private void recomputeChangeInfoRemovalReviewer(Notification msg) {
				InternalEObject modifiedChangeInfo = (InternalEObject) msg.getNotifier();
				if ((msg.getEventType() == Notification.ADD) || (msg.getEventType() == Notification.ADD_MANY)) {
					deriveReviewerRemovalPresence();
				}
			}

			private void recomputeChangeInfoActions(Notification msg) {
				InternalEObject modifiedChangeInfo = (InternalEObject) msg.getNotifier();
				if (msg.getEventType() == Notification.REMOVE_MANY || msg.getEventType() == Notification.ADD_MANY) {
					notifySet(modifiedChangeInfo, ModelPackage.Literals.CHANGE_INFO__REVERTABLE);
					notifySet(modifiedChangeInfo, ModelPackage.Literals.CHANGE_INFO__ABANDONABLE);
					notifySet(modifiedChangeInfo, ModelPackage.Literals.CHANGE_INFO__RESTOREABLE);
					notifySet(modifiedChangeInfo, ModelPackage.Literals.CHANGE_INFO__DELETEABLE);
				}

				if (msg.getEventType() == Notification.ADD) {
					String actionName = ((StringToActionInfoImpl) msg.getNewValue()).getKey();
					if (actionName.equals(ActionConstants.REVERT.getName())) {
						notifySet(modifiedChangeInfo, ModelPackage.Literals.CHANGE_INFO__REVERTABLE);
					}
					if (actionName.equals(ActionConstants.ABANDON.getName())) {
						notifySet(modifiedChangeInfo, ModelPackage.Literals.CHANGE_INFO__ABANDONABLE);
					}
					if (actionName.equals(ActionConstants.RESTORE.getName())) {
						notifySet(modifiedChangeInfo, ModelPackage.Literals.CHANGE_INFO__RESTOREABLE);
					}
					if (actionName.equals("/")) {
						notifySet(modifiedChangeInfo, ModelPackage.Literals.CHANGE_INFO__DELETEABLE);
					}
				}
			}

			private void recomputeRevisionInfoActions(Notification msg) {
				InternalEObject modifiedRevision = (InternalEObject) msg.getNotifier();
				if (msg.getEventType() == Notification.REMOVE_MANY || msg.getEventType() == Notification.ADD_MANY) {
					notifySet(modifiedRevision, ModelPackage.Literals.REVISION_INFO__REBASEABLE);
					notifySet(modifiedRevision, ModelPackage.Literals.REVISION_INFO__SUBMITABLE);
					notifySet(modifiedRevision, ModelPackage.Literals.REVISION_INFO__CHERRYPICKABLE);
					notifySet(modifiedRevision, ModelPackage.Literals.REVISION_INFO__DELETEABLE);
					notifySet(modifiedRevision, ModelPackage.Literals.REVISION_INFO__PUBLISHABLE);
				}
				if (msg.getEventType() == Notification.ADD) {
					String actionName = ((StringToActionInfoImpl) msg.getNewValue()).getKey();
					if (actionName.equals(ActionConstants.REBASE.getName())) {
						notifySet(modifiedRevision, ModelPackage.Literals.REVISION_INFO__REBASEABLE);
					}
					if (actionName.equals(ActionConstants.SUBMIT.getName())) {
						notifySet(modifiedRevision, ModelPackage.Literals.REVISION_INFO__SUBMITABLE);
					}
					if (actionName.equals(ActionConstants.CHERRYPICK.getName())) {
						notifySet(modifiedRevision, ModelPackage.Literals.REVISION_INFO__CHERRYPICKABLE);
					}
					if (actionName.equals("/")) {
						notifySet(modifiedRevision, ModelPackage.Literals.REVISION_INFO__DELETEABLE);
					}
					if (actionName.equals(ActionConstants.PUBLISH.getName())) {
						notifySet(modifiedRevision, ModelPackage.Literals.REVISION_INFO__PUBLISHABLE);
					}
				}
			}

			private InternalEObject getModifiedChangeInfo(Object object) {
				if (object instanceof ChangeInfo) {
					return (InternalEObject) object;
				}
				return getModifiedChangeInfo(((EObject) object).eContainer());
			}

		});
	}

	private void notifySet(InternalEObject modifiedObject, EStructuralFeature attr) {
		if (eNotificationRequired()) {
			modifiedObject.eNotify(
					new ENotificationImpl(modifiedObject, Notification.SET, attr, null, modifiedObject.eGet(attr)));
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}

		ChangeInfo other = (ChangeInfo) obj;
		if (getId() == null) {
			if (other.getId() != null) {
				return false;
			}
		} else if (!getId().equals(other.getId())) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		return getId().hashCode();
	}

	@Override
	public RevisionInfo basicGetRevision() {
		return getRevisions().get(getCurrent_revision());
	}

	@Override
	public RevisionInfo basicGetLatestPatchSet() {
		Collection<RevisionInfo> revisions = getRevisions().values();
		RevisionInfo match = null;
		int topNumber = 0;
		for (RevisionInfo candidate : revisions) {
			if (candidate.get_number() > topNumber) {
				topNumber = candidate.get_number();
				match = candidate;
			}
		}
		return match;
	}

	@Override
	public RevisionInfo getRevisionByNumber(int revisionNumber) {
		Collection<RevisionInfo> revisions = getRevisions().values();
		for (RevisionInfo candidate : revisions) {
			if (candidate.get_number() == revisionNumber) {
				return candidate;
			}
		}
		return null;
	}

	@Override
	public boolean isActionAllowed(String action) {
		EMap<String, ActionInfo> actionsAvailable = getActions();

		if (actionsAvailable != null) {
			ActionInfo actionInfo = actionsAvailable.get(action);

			if (actionInfo != null) {
				return actionInfo.isEnabled();
			}
		}
		return false;
	}

	public boolean isRevertable() {
		return isActionAllowed(ActionConstants.REVERT.getName());
	}

	@Override
	public boolean isAbandonable() {
		return isActionAllowed(ActionConstants.ABANDON.getName());
	}

	@Override
	public boolean isRestoreable() {
		return isActionAllowed(ActionConstants.RESTORE.getName());
	}

	@Override
	public boolean isDeleteable() {
		// We can't use a constant here because EMF does not allow to have a
		// constant for '/'
		return isActionAllowed("/");
	}

	@Override
	public void setUserSelectedRevision(RevisionInfo newUserSelectedRevision) {
		if (newUserSelectedRevision == null)
			return;
		if (userSelectedRevision == null) {
			super.setUserSelectedRevision(newUserSelectedRevision);
			return;
		}
		if (userSelectedRevision.get_number() != newUserSelectedRevision.get_number()) {
			super.setUserSelectedRevision(newUserSelectedRevision);
		}
	}

	@Override
	public void setUpdated(String newUpdated) {
		if (updated == null) {
			super.setUpdated(newUpdated);
			return;
		}
		if (!updated.equals(newUpdated)) {
			super.setUpdated(newUpdated);
		}
	}

	private static ApprovalInfo NO_VOTE = ModelFactory.eINSTANCE.createApprovalInfo();

	@Override
	public ApprovalInfo getMostRelevantVote(String label) {
		if (labels == null) {
			return NO_VOTE;
		}
		LabelInfo labelInfo = labels.get(label);
		if (labelInfo == null) {
			return NO_VOTE;
		}
		//As per the gerrit doc, the votes are always considered in this order of priority.
		//https://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#get-change-detail
		//The combined label vote is calculated in the following order (from highest to lowest): REJECTED > APPROVED > DISLIKED > RECOMMENDED.
		if (labelInfo.getRejected() != null) {
			return fromVoteToValue(labelInfo, labelInfo.getRejected());
		}
		if (labelInfo.getApproved() != null) {
			return fromVoteToValue(labelInfo, labelInfo.getApproved());
		}
		if (labelInfo.getDisliked() != null) {
			return fromVoteToValue(labelInfo, labelInfo.getDisliked());
		}
		if (labelInfo.getRecommended() != null) {
			return fromVoteToValue(labelInfo, labelInfo.getRecommended());
		}
		return NO_VOTE;
	}

	private ApprovalInfo fromVoteToValue(LabelInfo match, AccountInfo voter) {
		for (ApprovalInfo candidate : match.getAll()) {
			if (candidate.get_account_id() == voter.get_account_id())
				return candidate;
		}
		return NO_VOTE;
	}

	//Helper method setting if a comment is contained in a message
	//Also update the comment flag on a revision
	private void deriveCommentPresence() {
		EList<ChangeMessageInfo> messages = getMessages();
		for (ChangeMessageInfo m : messages) {
			m.setComment(hasComments(m.getMessage()));
			if (m.isComment()) {
				RevisionInfo rev = ((ChangeInfo) m.eContainer()).getRevisionByNumber(m.get_revision_number());
				if (rev != null)
					rev.setCommented(m.isComment());
			}
		}
	}

	//Helper method setting if a reviewer is removeable or not
	//Also update the isDeleatable in Revisioninfo
	private void deriveReviewerRemovalPresence() {
		EList<AccountInfo> removalList = getRemovable_reviewers();
		EList<ReviewerInfo> reviewersInfo = getComputedReviewers();
		for (int i = 0; i < removalList.size(); i++) {
			for (ReviewerInfo revInfo : reviewersInfo) {
				if (revInfo.get_account_id() == removalList.get(i).get_account_id()) {
					revInfo.setDeleteable(true);
				}
			}
		}
	}

	private static final Pattern COMMENT_PATTERN = Pattern.compile("(\\d+ comme[nt])(\\w+)"); // $NON-NLS-0$

	private boolean hasComments(String msg) {
		if (msg == null) {
			return false;
		}
		Matcher matcher = COMMENT_PATTERN.matcher(msg.toLowerCase());
		return matcher.find(0);
	}
}
