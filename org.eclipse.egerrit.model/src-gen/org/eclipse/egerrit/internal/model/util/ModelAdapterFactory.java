/**
 *   Copyright (c) 2015-2017 Ericsson AB
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
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.egerrit.internal.model.ModelPackage
 * @generated
 */
public class ModelAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static ModelPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModelAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = ModelPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject) object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ModelSwitch<Adapter> modelSwitch = new ModelSwitch<Adapter>() {
		@Override
		public Adapter caseRelatedChangeAndCommitInfo(RelatedChangeAndCommitInfo object) {
			return createRelatedChangeAndCommitInfoAdapter();
		}

		@Override
		public Adapter caseFetchInfo(FetchInfo object) {
			return createFetchInfoAdapter();
		}

		@Override
		public Adapter caseSubmitInfo(SubmitInfo object) {
			return createSubmitInfoAdapter();
		}

		@Override
		public Adapter caseStringToString(Map.Entry<String, String> object) {
			return createStringToStringAdapter();
		}

		@Override
		public Adapter caseProjectAccessInfo(ProjectAccessInfo object) {
			return createProjectAccessInfoAdapter();
		}

		@Override
		public Adapter caseCommentRange(CommentRange object) {
			return createCommentRangeAdapter();
		}

		@Override
		public Adapter caseActionInfo(ActionInfo object) {
			return createActionInfoAdapter();
		}

		@Override
		public Adapter caseCommentInfo(CommentInfo object) {
			return createCommentInfoAdapter();
		}

		@Override
		public Adapter caseStringToRevisionInfo(Map.Entry<String, RevisionInfo> object) {
			return createStringToRevisionInfoAdapter();
		}

		@Override
		public Adapter caseReviewInfo(ReviewInfo object) {
			return createReviewInfoAdapter();
		}

		@Override
		public Adapter caseLabelInfo(LabelInfo object) {
			return createLabelInfoAdapter();
		}

		@Override
		public Adapter caseApprovalInfo(ApprovalInfo object) {
			return createApprovalInfoAdapter();
		}

		@Override
		public Adapter caseRelatedChangesInfo(RelatedChangesInfo object) {
			return createRelatedChangesInfoAdapter();
		}

		@Override
		public Adapter caseFileInfo(FileInfo object) {
			return createFileInfoAdapter();
		}

		@Override
		public Adapter caseMergeableInfo(MergeableInfo object) {
			return createMergeableInfoAdapter();
		}

		@Override
		public Adapter caseProjectInfo(ProjectInfo object) {
			return createProjectInfoAdapter();
		}

		@Override
		public Adapter caseCommitInfo(CommitInfo object) {
			return createCommitInfoAdapter();
		}

		@Override
		public Adapter caseAccountInfo(AccountInfo object) {
			return createAccountInfoAdapter();
		}

		@Override
		public Adapter caseReviewerInfo(ReviewerInfo object) {
			return createReviewerInfoAdapter();
		}

		@Override
		public Adapter caseGitPersonInfo(GitPersonInfo object) {
			return createGitPersonInfoAdapter();
		}

		@Override
		public Adapter caseIncludedInInfo(IncludedInInfo object) {
			return createIncludedInInfoAdapter();
		}

		@Override
		public Adapter caseStringToFileInfo(Map.Entry<String, FileInfo> object) {
			return createStringToFileInfoAdapter();
		}

		@Override
		public Adapter caseStringToLabelInfo(Map.Entry<String, LabelInfo> object) {
			return createStringToLabelInfoAdapter();
		}

		@Override
		public Adapter caseBranchInfo(BranchInfo object) {
			return createBranchInfoAdapter();
		}

		@Override
		public Adapter caseChangeInfo(ChangeInfo object) {
			return createChangeInfoAdapter();
		}

		@Override
		public Adapter caseProblemInfo(ProblemInfo object) {
			return createProblemInfoAdapter();
		}

		@Override
		public Adapter caseChangeMessageInfo(ChangeMessageInfo object) {
			return createChangeMessageInfoAdapter();
		}

		@Override
		public Adapter caseStringToActionInfo(Map.Entry<String, ActionInfo> object) {
			return createStringToActionInfoAdapter();
		}

		@Override
		public Adapter caseStringToListOfString(Map.Entry<String, EList<String>> object) {
			return createStringToListOfStringAdapter();
		}

		@Override
		public Adapter caseStringToFetchInfo(Map.Entry<String, FetchInfo> object) {
			return createStringToFetchInfoAdapter();
		}

		@Override
		public Adapter caseRevisionInfo(RevisionInfo object) {
			return createRevisionInfoAdapter();
		}

		@Override
		public Adapter caseSuggestReviewerInfo(SuggestReviewerInfo object) {
			return createSuggestReviewerInfoAdapter();
		}

		@Override
		public Adapter caseGroupBaseInfo(GroupBaseInfo object) {
			return createGroupBaseInfoAdapter();
		}

		@Override
		public Adapter caseReviews(Reviews object) {
			return createReviewsAdapter();
		}

		@Override
		public Adapter caseDiffInfo(DiffInfo object) {
			return createDiffInfoAdapter();
		}

		@Override
		public Adapter caseDiffFileMetaInfo(DiffFileMetaInfo object) {
			return createDiffFileMetaInfoAdapter();
		}

		@Override
		public Adapter caseDiffContent(DiffContent object) {
			return createDiffContentAdapter();
		}

		@Override
		public Adapter defaultCase(EObject object) {
			return createEObjectAdapter();
		}
	};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject) target);
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.egerrit.internal.model.RelatedChangeAndCommitInfo <em>Related Change And Commit Info</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.egerrit.internal.model.RelatedChangeAndCommitInfo
	 * @generated
	 */
	public Adapter createRelatedChangeAndCommitInfoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.egerrit.internal.model.FetchInfo <em>Fetch Info</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.egerrit.internal.model.FetchInfo
	 * @generated
	 */
	public Adapter createFetchInfoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.egerrit.internal.model.SubmitInfo <em>Submit Info</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.egerrit.internal.model.SubmitInfo
	 * @generated
	 */
	public Adapter createSubmitInfoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link java.util.Map.Entry <em>String To String</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see java.util.Map.Entry
	 * @generated
	 */
	public Adapter createStringToStringAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.egerrit.internal.model.ProjectAccessInfo <em>Project Access Info</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.egerrit.internal.model.ProjectAccessInfo
	 * @generated
	 */
	public Adapter createProjectAccessInfoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.egerrit.internal.model.CommentRange <em>Comment Range</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.egerrit.internal.model.CommentRange
	 * @generated
	 */
	public Adapter createCommentRangeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.egerrit.internal.model.ActionInfo <em>Action Info</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.egerrit.internal.model.ActionInfo
	 * @generated
	 */
	public Adapter createActionInfoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.egerrit.internal.model.CommentInfo <em>Comment Info</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.egerrit.internal.model.CommentInfo
	 * @generated
	 */
	public Adapter createCommentInfoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link java.util.Map.Entry <em>String To Revision Info</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see java.util.Map.Entry
	 * @generated
	 */
	public Adapter createStringToRevisionInfoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.egerrit.internal.model.ReviewInfo <em>Review Info</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.egerrit.internal.model.ReviewInfo
	 * @generated
	 */
	public Adapter createReviewInfoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.egerrit.internal.model.LabelInfo <em>Label Info</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.egerrit.internal.model.LabelInfo
	 * @generated
	 */
	public Adapter createLabelInfoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.egerrit.internal.model.ApprovalInfo <em>Approval Info</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.egerrit.internal.model.ApprovalInfo
	 * @generated
	 */
	public Adapter createApprovalInfoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.egerrit.internal.model.RelatedChangesInfo <em>Related Changes Info</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.egerrit.internal.model.RelatedChangesInfo
	 * @generated
	 */
	public Adapter createRelatedChangesInfoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.egerrit.internal.model.FileInfo <em>File Info</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.egerrit.internal.model.FileInfo
	 * @generated
	 */
	public Adapter createFileInfoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.egerrit.internal.model.MergeableInfo <em>Mergeable Info</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.egerrit.internal.model.MergeableInfo
	 * @generated
	 */
	public Adapter createMergeableInfoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.egerrit.internal.model.ProjectInfo <em>Project Info</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.egerrit.internal.model.ProjectInfo
	 * @generated
	 */
	public Adapter createProjectInfoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.egerrit.internal.model.CommitInfo <em>Commit Info</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.egerrit.internal.model.CommitInfo
	 * @generated
	 */
	public Adapter createCommitInfoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.egerrit.internal.model.AccountInfo <em>Account Info</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.egerrit.internal.model.AccountInfo
	 * @generated
	 */
	public Adapter createAccountInfoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.egerrit.internal.model.ReviewerInfo <em>Reviewer Info</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.egerrit.internal.model.ReviewerInfo
	 * @generated
	 */
	public Adapter createReviewerInfoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.egerrit.internal.model.GitPersonInfo <em>Git Person Info</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.egerrit.internal.model.GitPersonInfo
	 * @generated
	 */
	public Adapter createGitPersonInfoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.egerrit.internal.model.IncludedInInfo <em>Included In Info</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.egerrit.internal.model.IncludedInInfo
	 * @generated
	 */
	public Adapter createIncludedInInfoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link java.util.Map.Entry <em>String To File Info</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see java.util.Map.Entry
	 * @generated
	 */
	public Adapter createStringToFileInfoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link java.util.Map.Entry <em>String To Label Info</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see java.util.Map.Entry
	 * @generated
	 */
	public Adapter createStringToLabelInfoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.egerrit.internal.model.BranchInfo <em>Branch Info</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.egerrit.internal.model.BranchInfo
	 * @generated
	 */
	public Adapter createBranchInfoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.egerrit.internal.model.ChangeInfo <em>Change Info</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.egerrit.internal.model.ChangeInfo
	 * @generated
	 */
	public Adapter createChangeInfoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.egerrit.internal.model.ProblemInfo <em>Problem Info</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.egerrit.internal.model.ProblemInfo
	 * @generated
	 */
	public Adapter createProblemInfoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.egerrit.internal.model.ChangeMessageInfo <em>Change Message Info</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.egerrit.internal.model.ChangeMessageInfo
	 * @generated
	 */
	public Adapter createChangeMessageInfoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link java.util.Map.Entry <em>String To Action Info</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see java.util.Map.Entry
	 * @generated
	 */
	public Adapter createStringToActionInfoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link java.util.Map.Entry <em>String To List Of String</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see java.util.Map.Entry
	 * @generated
	 */
	public Adapter createStringToListOfStringAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link java.util.Map.Entry <em>String To Fetch Info</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see java.util.Map.Entry
	 * @generated
	 */
	public Adapter createStringToFetchInfoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.egerrit.internal.model.RevisionInfo <em>Revision Info</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.egerrit.internal.model.RevisionInfo
	 * @generated
	 */
	public Adapter createRevisionInfoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.egerrit.internal.model.SuggestReviewerInfo <em>Suggest Reviewer Info</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.egerrit.internal.model.SuggestReviewerInfo
	 * @generated
	 */
	public Adapter createSuggestReviewerInfoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.egerrit.internal.model.GroupBaseInfo <em>Group Base Info</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.egerrit.internal.model.GroupBaseInfo
	 * @generated
	 */
	public Adapter createGroupBaseInfoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.egerrit.internal.model.Reviews <em>Reviews</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.egerrit.internal.model.Reviews
	 * @generated
	 */
	public Adapter createReviewsAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.egerrit.internal.model.DiffInfo <em>Diff Info</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.egerrit.internal.model.DiffInfo
	 * @generated
	 */
	public Adapter createDiffInfoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.egerrit.internal.model.DiffFileMetaInfo <em>Diff File Meta Info</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.egerrit.internal.model.DiffFileMetaInfo
	 * @generated
	 */
	public Adapter createDiffFileMetaInfoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.egerrit.internal.model.DiffContent <em>Diff Content</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.egerrit.internal.model.DiffContent
	 * @generated
	 */
	public Adapter createDiffContentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //ModelAdapterFactory
