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
package org.eclipse.egerrit.internal.model;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.egerrit.internal.model.ModelPackage
 * @generated
 */
public interface ModelFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ModelFactory eINSTANCE = org.eclipse.egerrit.internal.model.impl.ModelFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Related Change And Commit Info</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Related Change And Commit Info</em>'.
	 * @generated
	 */
	RelatedChangeAndCommitInfo createRelatedChangeAndCommitInfo();

	/**
	 * Returns a new object of class '<em>Fetch Info</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Fetch Info</em>'.
	 * @generated
	 */
	FetchInfo createFetchInfo();

	/**
	 * Returns a new object of class '<em>Submit Info</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Submit Info</em>'.
	 * @generated
	 */
	SubmitInfo createSubmitInfo();

	/**
	 * Returns a new object of class '<em>Project Access Info</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Project Access Info</em>'.
	 * @generated
	 */
	ProjectAccessInfo createProjectAccessInfo();

	/**
	 * Returns a new object of class '<em>Comment Range</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Comment Range</em>'.
	 * @generated
	 */
	CommentRange createCommentRange();

	/**
	 * Returns a new object of class '<em>Action Info</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Action Info</em>'.
	 * @generated
	 */
	ActionInfo createActionInfo();

	/**
	 * Returns a new object of class '<em>Comment Info</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Comment Info</em>'.
	 * @generated
	 */
	CommentInfo createCommentInfo();

	/**
	 * Returns a new object of class '<em>Review Info</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Review Info</em>'.
	 * @generated
	 */
	ReviewInfo createReviewInfo();

	/**
	 * Returns a new object of class '<em>Label Info</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Label Info</em>'.
	 * @generated
	 */
	LabelInfo createLabelInfo();

	/**
	 * Returns a new object of class '<em>Approval Info</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Approval Info</em>'.
	 * @generated
	 */
	ApprovalInfo createApprovalInfo();

	/**
	 * Returns a new object of class '<em>Related Changes Info</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Related Changes Info</em>'.
	 * @generated
	 */
	RelatedChangesInfo createRelatedChangesInfo();

	/**
	 * Returns a new object of class '<em>File Info</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>File Info</em>'.
	 * @generated
	 */
	FileInfo createFileInfo();

	/**
	 * Returns a new object of class '<em>Mergeable Info</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Mergeable Info</em>'.
	 * @generated
	 */
	MergeableInfo createMergeableInfo();

	/**
	 * Returns a new object of class '<em>Project Info</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Project Info</em>'.
	 * @generated
	 */
	ProjectInfo createProjectInfo();

	/**
	 * Returns a new object of class '<em>Commit Info</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Commit Info</em>'.
	 * @generated
	 */
	CommitInfo createCommitInfo();

	/**
	 * Returns a new object of class '<em>Account Info</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Account Info</em>'.
	 * @generated
	 */
	AccountInfo createAccountInfo();

	/**
	 * Returns a new object of class '<em>Reviewer Info</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Reviewer Info</em>'.
	 * @generated
	 */
	ReviewerInfo createReviewerInfo();

	/**
	 * Returns a new object of class '<em>Git Person Info</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Git Person Info</em>'.
	 * @generated
	 */
	GitPersonInfo createGitPersonInfo();

	/**
	 * Returns a new object of class '<em>Included In Info</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Included In Info</em>'.
	 * @generated
	 */
	IncludedInInfo createIncludedInInfo();

	/**
	 * Returns a new object of class '<em>Branch Info</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Branch Info</em>'.
	 * @generated
	 */
	BranchInfo createBranchInfo();

	/**
	 * Returns a new object of class '<em>Change Info</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Change Info</em>'.
	 * @generated
	 */
	ChangeInfo createChangeInfo();

	/**
	 * Returns a new object of class '<em>Problem Info</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Problem Info</em>'.
	 * @generated
	 */
	ProblemInfo createProblemInfo();

	/**
	 * Returns a new object of class '<em>Change Message Info</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Change Message Info</em>'.
	 * @generated
	 */
	ChangeMessageInfo createChangeMessageInfo();

	/**
	 * Returns a new object of class '<em>Revision Info</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Revision Info</em>'.
	 * @generated
	 */
	RevisionInfo createRevisionInfo();

	/**
	 * Returns a new object of class '<em>Suggest Reviewer Info</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Suggest Reviewer Info</em>'.
	 * @generated
	 */
	SuggestReviewerInfo createSuggestReviewerInfo();

	/**
	 * Returns a new object of class '<em>Group Base Info</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Group Base Info</em>'.
	 * @generated
	 */
	GroupBaseInfo createGroupBaseInfo();

	/**
	 * Returns a new object of class '<em>Reviews</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Reviews</em>'.
	 * @generated
	 */
	Reviews createReviews();

	/**
	 * Returns a new object of class '<em>Diff Info</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Diff Info</em>'.
	 * @generated
	 */
	DiffInfo createDiffInfo();

	/**
	 * Returns a new object of class '<em>Diff File Meta Info</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Diff File Meta Info</em>'.
	 * @generated
	 */
	DiffFileMetaInfo createDiffFileMetaInfo();

	/**
	 * Returns a new object of class '<em>Diff Content</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Diff Content</em>'.
	 * @generated
	 */
	DiffContent createDiffContent();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	ModelPackage getModelPackage();

} //ModelFactory
