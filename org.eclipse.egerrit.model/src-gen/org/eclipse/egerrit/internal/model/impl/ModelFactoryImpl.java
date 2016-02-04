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
package org.eclipse.egerrit.internal.model.impl;

import java.util.Map;
import org.eclipse.egerrit.internal.model.*;
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
import org.eclipse.egerrit.internal.model.ModelFactory;
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
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ModelFactoryImpl extends EFactoryImpl implements ModelFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ModelFactory init() {
		try {
			ModelFactory theModelFactory = (ModelFactory) EPackage.Registry.INSTANCE.getEFactory(ModelPackage.eNS_URI);
			if (theModelFactory != null) {
				return theModelFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ModelFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModelFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case ModelPackage.RELATED_CHANGE_AND_COMMIT_INFO:
			return createRelatedChangeAndCommitInfo();
		case ModelPackage.FETCH_INFO:
			return createFetchInfo();
		case ModelPackage.SUBMIT_INFO:
			return createSubmitInfo();
		case ModelPackage.STRING_TO_STRING:
			return (EObject) createStringToString();
		case ModelPackage.PROJECT_ACCESS_INFO:
			return createProjectAccessInfo();
		case ModelPackage.COMMENT_RANGE:
			return createCommentRange();
		case ModelPackage.ACTION_INFO:
			return createActionInfo();
		case ModelPackage.COMMENT_INFO:
			return createCommentInfo();
		case ModelPackage.STRING_TO_REVISION_INFO:
			return (EObject) createStringToRevisionInfo();
		case ModelPackage.REVIEW_INFO:
			return createReviewInfo();
		case ModelPackage.LABEL_INFO:
			return createLabelInfo();
		case ModelPackage.APPROVAL_INFO:
			return createApprovalInfo();
		case ModelPackage.RELATED_CHANGES_INFO:
			return createRelatedChangesInfo();
		case ModelPackage.FILE_INFO:
			return createFileInfo();
		case ModelPackage.MERGEABLE_INFO:
			return createMergeableInfo();
		case ModelPackage.PROJECT_INFO:
			return createProjectInfo();
		case ModelPackage.COMMIT_INFO:
			return createCommitInfo();
		case ModelPackage.ACCOUNT_INFO:
			return createAccountInfo();
		case ModelPackage.REVIEWER_INFO:
			return createReviewerInfo();
		case ModelPackage.GIT_PERSON_INFO:
			return createGitPersonInfo();
		case ModelPackage.INCLUDED_IN_INFO:
			return createIncludedInInfo();
		case ModelPackage.STRING_TO_FILE_INFO:
			return (EObject) createStringToFileInfo();
		case ModelPackage.STRING_TO_LABEL_INFO:
			return (EObject) createStringToLabelInfo();
		case ModelPackage.BRANCH_INFO:
			return createBranchInfo();
		case ModelPackage.CHANGE_INFO:
			return createChangeInfo();
		case ModelPackage.PROBLEM_INFO:
			return createProblemInfo();
		case ModelPackage.CHANGE_MESSAGE_INFO:
			return createChangeMessageInfo();
		case ModelPackage.STRING_TO_ACTION_INFO:
			return (EObject) createStringToActionInfo();
		case ModelPackage.STRING_TO_LIST_OF_STRING:
			return (EObject) createStringToListOfString();
		case ModelPackage.STRING_TO_FETCH_INFO:
			return (EObject) createStringToFetchInfo();
		case ModelPackage.REVISION_INFO:
			return createRevisionInfo();
		case ModelPackage.SUGGEST_REVIEWER_INFO:
			return createSuggestReviewerInfo();
		case ModelPackage.GROUP_BASE_INFO:
			return createGroupBaseInfo();
		case ModelPackage.REVIEWS:
			return createReviews();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
		case ModelPackage.ACTION_CONSTANTS:
			return createActionConstantsFromString(eDataType, initialValue);
		default:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
		case ModelPackage.ACTION_CONSTANTS:
			return convertActionConstantsToString(eDataType, instanceValue);
		default:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public RelatedChangeAndCommitInfo createRelatedChangeAndCommitInfo() {
		RelatedChangeAndCommitInfoImpl relatedChangeAndCommitInfo = new RelatedChangeAndCommitInfoImpl();
		return relatedChangeAndCommitInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FetchInfo createFetchInfo() {
		FetchInfoImpl fetchInfo = new FetchInfoImpl();
		return fetchInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SubmitInfo createSubmitInfo() {
		SubmitInfoImpl submitInfo = new SubmitInfoImpl();
		return submitInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map.Entry<String, String> createStringToString() {
		StringToStringImpl stringToString = new StringToStringImpl();
		return stringToString;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ProjectAccessInfo createProjectAccessInfo() {
		ProjectAccessInfoImpl projectAccessInfo = new ProjectAccessInfoImpl();
		return projectAccessInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CommentRange createCommentRange() {
		CommentRangeImpl commentRange = new CommentRangeImpl();
		return commentRange;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ActionInfo createActionInfo() {
		ActionInfoImpl actionInfo = new ActionInfoImpl();
		return actionInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CommentInfo createCommentInfo() {
		CommentInfoImpl commentInfo = new CommentInfoImpl();
		return commentInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map.Entry<String, RevisionInfo> createStringToRevisionInfo() {
		StringToRevisionInfoImpl stringToRevisionInfo = new StringToRevisionInfoImpl();
		return stringToRevisionInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ReviewInfo createReviewInfo() {
		ReviewInfoImpl reviewInfo = new ReviewInfoImpl();
		return reviewInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LabelInfo createLabelInfo() {
		LabelInfoImpl labelInfo = new LabelInfoImpl();
		return labelInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ApprovalInfo createApprovalInfo() {
		ApprovalInfoImpl approvalInfo = new ApprovalInfoImpl();
		return approvalInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public RelatedChangesInfo createRelatedChangesInfo() {
		RelatedChangesInfoImpl relatedChangesInfo = new RelatedChangesInfoImpl();
		return relatedChangesInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FileInfo createFileInfo() {
		FileInfoImpl fileInfo = new FileInfoImpl();
		return fileInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public MergeableInfo createMergeableInfo() {
		MergeableInfoImpl mergeableInfo = new MergeableInfoImpl();
		return mergeableInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ProjectInfo createProjectInfo() {
		ProjectInfoImpl projectInfo = new ProjectInfoImpl();
		return projectInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CommitInfo createCommitInfo() {
		CommitInfoImpl commitInfo = new CommitInfoImpl();
		return commitInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AccountInfo createAccountInfo() {
		AccountInfoImpl accountInfo = new AccountInfoImpl();
		return accountInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ReviewerInfo createReviewerInfo() {
		ReviewerInfoImpl reviewerInfo = new ReviewerInfoImpl();
		return reviewerInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GitPersonInfo createGitPersonInfo() {
		GitPersonInfoImpl gitPersonInfo = new GitPersonInfoImpl();
		return gitPersonInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IncludedInInfo createIncludedInInfo() {
		IncludedInInfoImpl includedInInfo = new IncludedInInfoImpl();
		return includedInInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map.Entry<String, FileInfo> createStringToFileInfo() {
		StringToFileInfoImpl stringToFileInfo = new StringToFileInfoImpl();
		return stringToFileInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map.Entry<String, LabelInfo> createStringToLabelInfo() {
		StringToLabelInfoImpl stringToLabelInfo = new StringToLabelInfoImpl();
		return stringToLabelInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BranchInfo createBranchInfo() {
		BranchInfoImpl branchInfo = new BranchInfoImpl();
		return branchInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ChangeInfo createChangeInfo() {
		ChangeInfoImpl changeInfo = new ChangeInfoImpl();
		return changeInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ProblemInfo createProblemInfo() {
		ProblemInfoImpl problemInfo = new ProblemInfoImpl();
		return problemInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ChangeMessageInfo createChangeMessageInfo() {
		ChangeMessageInfoImpl changeMessageInfo = new ChangeMessageInfoImpl();
		return changeMessageInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map.Entry<String, ActionInfo> createStringToActionInfo() {
		StringToActionInfoImpl stringToActionInfo = new StringToActionInfoImpl();
		return stringToActionInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map.Entry<String, EList<String>> createStringToListOfString() {
		StringToListOfStringImpl stringToListOfString = new StringToListOfStringImpl();
		return stringToListOfString;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map.Entry<String, FetchInfo> createStringToFetchInfo() {
		StringToFetchInfoImpl stringToFetchInfo = new StringToFetchInfoImpl();
		return stringToFetchInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public RevisionInfo createRevisionInfo() {
		RevisionInfoImpl revisionInfo = new RevisionInfoImpl();
		return revisionInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SuggestReviewerInfo createSuggestReviewerInfo() {
		SuggestReviewerInfoImpl suggestReviewerInfo = new SuggestReviewerInfoImpl();
		return suggestReviewerInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GroupBaseInfo createGroupBaseInfo() {
		GroupBaseInfoImpl groupBaseInfo = new GroupBaseInfoImpl();
		return groupBaseInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Reviews createReviews() {
		ReviewsImpl reviews = new ReviewsImpl();
		return reviews;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ActionConstants createActionConstantsFromString(EDataType eDataType, String initialValue) {
		ActionConstants result = ActionConstants.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException(
					"The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertActionConstantsToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ModelPackage getModelPackage() {
		return (ModelPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ModelPackage getPackage() {
		return ModelPackage.eINSTANCE;
	}

} //ModelFactoryImpl
