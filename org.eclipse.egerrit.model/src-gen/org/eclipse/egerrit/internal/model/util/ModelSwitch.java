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
package org.eclipse.egerrit.internal.model.util;

import java.util.Map;
import org.eclipse.egerrit.internal.model.AccountInfo;
import org.eclipse.egerrit.internal.model.ActionInfo;
import org.eclipse.egerrit.internal.model.ApprovalInfo;
import org.eclipse.egerrit.internal.model.BranchInfo;
import org.eclipse.egerrit.internal.model.ChangeInfo;
import org.eclipse.egerrit.internal.model.ChangeMessageInfo;
import org.eclipse.egerrit.internal.model.CommentInfo;
import org.eclipse.egerrit.internal.model.CommentRange;
import org.eclipse.egerrit.internal.model.CommitInfo;
import org.eclipse.egerrit.internal.model.FetchInfo;
import org.eclipse.egerrit.internal.model.FileInfo;
import org.eclipse.egerrit.internal.model.GitPersonInfo;
import org.eclipse.egerrit.internal.model.GroupBaseInfo;
import org.eclipse.egerrit.internal.model.IncludedInInfo;
import org.eclipse.egerrit.internal.model.LabelInfo;
import org.eclipse.egerrit.internal.model.MergeableInfo;
import org.eclipse.egerrit.internal.model.ModelPackage;
import org.eclipse.egerrit.internal.model.ProblemInfo;
import org.eclipse.egerrit.internal.model.ProjectAccessInfo;
import org.eclipse.egerrit.internal.model.ProjectInfo;
import org.eclipse.egerrit.internal.model.RelatedChangeAndCommitInfo;
import org.eclipse.egerrit.internal.model.RelatedChangesInfo;
import org.eclipse.egerrit.internal.model.ReviewInfo;
import org.eclipse.egerrit.internal.model.ReviewerInfo;
import org.eclipse.egerrit.internal.model.Reviews;
import org.eclipse.egerrit.internal.model.RevisionInfo;
import org.eclipse.egerrit.internal.model.SubmitInfo;
import org.eclipse.egerrit.internal.model.SuggestReviewerInfo;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.eclipse.egerrit.internal.model.ModelPackage
 * @generated
 */
public class ModelSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static ModelPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModelSwitch() {
		if (modelPackage == null) {
			modelPackage = ModelPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
		case ModelPackage.RELATED_CHANGE_AND_COMMIT_INFO: {
			RelatedChangeAndCommitInfo relatedChangeAndCommitInfo = (RelatedChangeAndCommitInfo) theEObject;
			T result = caseRelatedChangeAndCommitInfo(relatedChangeAndCommitInfo);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case ModelPackage.FETCH_INFO: {
			FetchInfo fetchInfo = (FetchInfo) theEObject;
			T result = caseFetchInfo(fetchInfo);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case ModelPackage.SUBMIT_INFO: {
			SubmitInfo submitInfo = (SubmitInfo) theEObject;
			T result = caseSubmitInfo(submitInfo);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case ModelPackage.STRING_TO_STRING: {
			@SuppressWarnings("unchecked")
			Map.Entry<String, String> stringToString = (Map.Entry<String, String>) theEObject;
			T result = caseStringToString(stringToString);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case ModelPackage.PROJECT_ACCESS_INFO: {
			ProjectAccessInfo projectAccessInfo = (ProjectAccessInfo) theEObject;
			T result = caseProjectAccessInfo(projectAccessInfo);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case ModelPackage.COMMENT_RANGE: {
			CommentRange commentRange = (CommentRange) theEObject;
			T result = caseCommentRange(commentRange);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case ModelPackage.ACTION_INFO: {
			ActionInfo actionInfo = (ActionInfo) theEObject;
			T result = caseActionInfo(actionInfo);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case ModelPackage.COMMENT_INFO: {
			CommentInfo commentInfo = (CommentInfo) theEObject;
			T result = caseCommentInfo(commentInfo);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case ModelPackage.STRING_TO_REVISION_INFO: {
			@SuppressWarnings("unchecked")
			Map.Entry<String, RevisionInfo> stringToRevisionInfo = (Map.Entry<String, RevisionInfo>) theEObject;
			T result = caseStringToRevisionInfo(stringToRevisionInfo);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case ModelPackage.REVIEW_INFO: {
			ReviewInfo reviewInfo = (ReviewInfo) theEObject;
			T result = caseReviewInfo(reviewInfo);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case ModelPackage.LABEL_INFO: {
			LabelInfo labelInfo = (LabelInfo) theEObject;
			T result = caseLabelInfo(labelInfo);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case ModelPackage.APPROVAL_INFO: {
			ApprovalInfo approvalInfo = (ApprovalInfo) theEObject;
			T result = caseApprovalInfo(approvalInfo);
			if (result == null)
				result = caseAccountInfo(approvalInfo);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case ModelPackage.RELATED_CHANGES_INFO: {
			RelatedChangesInfo relatedChangesInfo = (RelatedChangesInfo) theEObject;
			T result = caseRelatedChangesInfo(relatedChangesInfo);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case ModelPackage.FILE_INFO: {
			FileInfo fileInfo = (FileInfo) theEObject;
			T result = caseFileInfo(fileInfo);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case ModelPackage.MERGEABLE_INFO: {
			MergeableInfo mergeableInfo = (MergeableInfo) theEObject;
			T result = caseMergeableInfo(mergeableInfo);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case ModelPackage.PROJECT_INFO: {
			ProjectInfo projectInfo = (ProjectInfo) theEObject;
			T result = caseProjectInfo(projectInfo);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case ModelPackage.COMMIT_INFO: {
			CommitInfo commitInfo = (CommitInfo) theEObject;
			T result = caseCommitInfo(commitInfo);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case ModelPackage.ACCOUNT_INFO: {
			AccountInfo accountInfo = (AccountInfo) theEObject;
			T result = caseAccountInfo(accountInfo);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case ModelPackage.REVIEWER_INFO: {
			ReviewerInfo reviewerInfo = (ReviewerInfo) theEObject;
			T result = caseReviewerInfo(reviewerInfo);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case ModelPackage.GIT_PERSON_INFO: {
			GitPersonInfo gitPersonInfo = (GitPersonInfo) theEObject;
			T result = caseGitPersonInfo(gitPersonInfo);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case ModelPackage.INCLUDED_IN_INFO: {
			IncludedInInfo includedInInfo = (IncludedInInfo) theEObject;
			T result = caseIncludedInInfo(includedInInfo);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case ModelPackage.STRING_TO_FILE_INFO: {
			@SuppressWarnings("unchecked")
			Map.Entry<String, FileInfo> stringToFileInfo = (Map.Entry<String, FileInfo>) theEObject;
			T result = caseStringToFileInfo(stringToFileInfo);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case ModelPackage.STRING_TO_LABEL_INFO: {
			@SuppressWarnings("unchecked")
			Map.Entry<String, LabelInfo> stringToLabelInfo = (Map.Entry<String, LabelInfo>) theEObject;
			T result = caseStringToLabelInfo(stringToLabelInfo);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case ModelPackage.BRANCH_INFO: {
			BranchInfo branchInfo = (BranchInfo) theEObject;
			T result = caseBranchInfo(branchInfo);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case ModelPackage.CHANGE_INFO: {
			ChangeInfo changeInfo = (ChangeInfo) theEObject;
			T result = caseChangeInfo(changeInfo);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case ModelPackage.PROBLEM_INFO: {
			ProblemInfo problemInfo = (ProblemInfo) theEObject;
			T result = caseProblemInfo(problemInfo);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case ModelPackage.CHANGE_MESSAGE_INFO: {
			ChangeMessageInfo changeMessageInfo = (ChangeMessageInfo) theEObject;
			T result = caseChangeMessageInfo(changeMessageInfo);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case ModelPackage.STRING_TO_ACTION_INFO: {
			@SuppressWarnings("unchecked")
			Map.Entry<String, ActionInfo> stringToActionInfo = (Map.Entry<String, ActionInfo>) theEObject;
			T result = caseStringToActionInfo(stringToActionInfo);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case ModelPackage.STRING_TO_LIST_OF_STRING: {
			@SuppressWarnings("unchecked")
			Map.Entry<String, EList<String>> stringToListOfString = (Map.Entry<String, EList<String>>) theEObject;
			T result = caseStringToListOfString(stringToListOfString);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case ModelPackage.STRING_TO_FETCH_INFO: {
			@SuppressWarnings("unchecked")
			Map.Entry<String, FetchInfo> stringToFetchInfo = (Map.Entry<String, FetchInfo>) theEObject;
			T result = caseStringToFetchInfo(stringToFetchInfo);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case ModelPackage.REVISION_INFO: {
			RevisionInfo revisionInfo = (RevisionInfo) theEObject;
			T result = caseRevisionInfo(revisionInfo);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case ModelPackage.SUGGEST_REVIEWER_INFO: {
			SuggestReviewerInfo suggestReviewerInfo = (SuggestReviewerInfo) theEObject;
			T result = caseSuggestReviewerInfo(suggestReviewerInfo);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case ModelPackage.GROUP_BASE_INFO: {
			GroupBaseInfo groupBaseInfo = (GroupBaseInfo) theEObject;
			T result = caseGroupBaseInfo(groupBaseInfo);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case ModelPackage.REVIEWS: {
			Reviews reviews = (Reviews) theEObject;
			T result = caseReviews(reviews);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		default:
			return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Related Change And Commit Info</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Related Change And Commit Info</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRelatedChangeAndCommitInfo(RelatedChangeAndCommitInfo object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Fetch Info</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Fetch Info</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFetchInfo(FetchInfo object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Submit Info</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Submit Info</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSubmitInfo(SubmitInfo object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>String To String</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>String To String</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStringToString(Map.Entry<String, String> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Project Access Info</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Project Access Info</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProjectAccessInfo(ProjectAccessInfo object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Comment Range</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Comment Range</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCommentRange(CommentRange object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Action Info</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Action Info</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseActionInfo(ActionInfo object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Comment Info</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Comment Info</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCommentInfo(CommentInfo object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>String To Revision Info</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>String To Revision Info</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStringToRevisionInfo(Map.Entry<String, RevisionInfo> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Review Info</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Review Info</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseReviewInfo(ReviewInfo object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Label Info</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Label Info</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLabelInfo(LabelInfo object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Approval Info</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Approval Info</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseApprovalInfo(ApprovalInfo object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Related Changes Info</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Related Changes Info</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRelatedChangesInfo(RelatedChangesInfo object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>File Info</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>File Info</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFileInfo(FileInfo object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Mergeable Info</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Mergeable Info</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMergeableInfo(MergeableInfo object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Project Info</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Project Info</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProjectInfo(ProjectInfo object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Commit Info</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Commit Info</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCommitInfo(CommitInfo object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Account Info</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Account Info</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAccountInfo(AccountInfo object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Reviewer Info</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Reviewer Info</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseReviewerInfo(ReviewerInfo object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Git Person Info</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Git Person Info</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGitPersonInfo(GitPersonInfo object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Included In Info</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Included In Info</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIncludedInInfo(IncludedInInfo object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>String To File Info</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>String To File Info</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStringToFileInfo(Map.Entry<String, FileInfo> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>String To Label Info</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>String To Label Info</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStringToLabelInfo(Map.Entry<String, LabelInfo> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Branch Info</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Branch Info</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBranchInfo(BranchInfo object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Change Info</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Change Info</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseChangeInfo(ChangeInfo object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Problem Info</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Problem Info</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProblemInfo(ProblemInfo object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Change Message Info</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Change Message Info</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseChangeMessageInfo(ChangeMessageInfo object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>String To Action Info</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>String To Action Info</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStringToActionInfo(Map.Entry<String, ActionInfo> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>String To List Of String</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>String To List Of String</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStringToListOfString(Map.Entry<String, EList<String>> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>String To Fetch Info</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>String To Fetch Info</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStringToFetchInfo(Map.Entry<String, FetchInfo> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Revision Info</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Revision Info</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRevisionInfo(RevisionInfo object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Suggest Reviewer Info</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Suggest Reviewer Info</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSuggestReviewerInfo(SuggestReviewerInfo object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Group Base Info</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Group Base Info</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGroupBaseInfo(GroupBaseInfo object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Reviews</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Reviews</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseReviews(Reviews object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //ModelSwitch
