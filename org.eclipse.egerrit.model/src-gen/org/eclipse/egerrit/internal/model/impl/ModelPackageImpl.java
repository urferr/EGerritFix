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

import org.eclipse.egerrit.internal.model.AccountInfo;
import org.eclipse.egerrit.internal.model.ActionConstants;
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
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ModelPackageImpl extends EPackageImpl implements ModelPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass relatedChangeAndCommitInfoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass fetchInfoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass submitInfoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stringToStringEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass projectAccessInfoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass commentRangeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass actionInfoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass commentInfoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stringToRevisionInfoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass reviewInfoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass labelInfoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass approvalInfoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass relatedChangesInfoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass fileInfoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mergeableInfoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass projectInfoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass commitInfoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass accountInfoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass reviewerInfoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass gitPersonInfoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass includedInInfoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stringToFileInfoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stringToLabelInfoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass branchInfoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass changeInfoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass problemInfoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass changeMessageInfoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stringToActionInfoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stringToListOfStringEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stringToFetchInfoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass revisionInfoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass suggestReviewerInfoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass groupBaseInfoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass reviewsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum actionConstantsEEnum = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ModelPackageImpl() {
		super(eNS_URI, ModelFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link ModelPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ModelPackage init() {
		if (isInited)
			return (ModelPackage) EPackage.Registry.INSTANCE.getEPackage(ModelPackage.eNS_URI);

		// Obtain or create and register package
		ModelPackageImpl theModelPackage = (ModelPackageImpl) (EPackage.Registry.INSTANCE
				.get(eNS_URI) instanceof ModelPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI)
						: new ModelPackageImpl());

		isInited = true;

		// Create package meta-data objects
		theModelPackage.createPackageContents();

		// Initialize created meta-data
		theModelPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theModelPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ModelPackage.eNS_URI, theModelPackage);
		return theModelPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getRelatedChangeAndCommitInfo() {
		return relatedChangeAndCommitInfoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getRelatedChangeAndCommitInfo_Change_id() {
		return (EAttribute) relatedChangeAndCommitInfoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getRelatedChangeAndCommitInfo_Commit() {
		return (EReference) relatedChangeAndCommitInfoEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getRelatedChangeAndCommitInfo__change_number() {
		return (EAttribute) relatedChangeAndCommitInfoEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getRelatedChangeAndCommitInfo__revision_number() {
		return (EAttribute) relatedChangeAndCommitInfoEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getRelatedChangeAndCommitInfo__current_revision_number() {
		return (EAttribute) relatedChangeAndCommitInfoEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getFetchInfo() {
		return fetchInfoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFetchInfo_Url() {
		return (EAttribute) fetchInfoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFetchInfo_Ref() {
		return (EAttribute) fetchInfoEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFetchInfo_Commands() {
		return (EReference) fetchInfoEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSubmitInfo() {
		return submitInfoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSubmitInfo_Status() {
		return (EAttribute) submitInfoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSubmitInfo_On_behalf_of() {
		return (EAttribute) submitInfoEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getStringToString() {
		return stringToStringEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getStringToString_Key() {
		return (EAttribute) stringToStringEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getStringToString_Value() {
		return (EAttribute) stringToStringEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getProjectAccessInfo() {
		return projectAccessInfoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getProjectAccessInfo_Revision() {
		return (EAttribute) projectAccessInfoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getProjectAccessInfo_Inherits_from() {
		return (EReference) projectAccessInfoEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getProjectAccessInfo_Is_owner() {
		return (EAttribute) projectAccessInfoEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getProjectAccessInfo_OwnerOf() {
		return (EAttribute) projectAccessInfoEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getProjectAccessInfo_Can_upload() {
		return (EAttribute) projectAccessInfoEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getProjectAccessInfo_Can_add() {
		return (EAttribute) projectAccessInfoEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getProjectAccessInfo_Config_visible() {
		return (EAttribute) projectAccessInfoEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getCommentRange() {
		return commentRangeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCommentRange_StartLine() {
		return (EAttribute) commentRangeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCommentRange_StartCharacter() {
		return (EAttribute) commentRangeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCommentRange_EndLine() {
		return (EAttribute) commentRangeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCommentRange_EndCharacter() {
		return (EAttribute) commentRangeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getActionInfo() {
		return actionInfoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getActionInfo_Method() {
		return (EAttribute) actionInfoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getActionInfo_Label() {
		return (EAttribute) actionInfoEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getActionInfo_Title() {
		return (EAttribute) actionInfoEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getActionInfo_Enabled() {
		return (EAttribute) actionInfoEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getCommentInfo() {
		return commentInfoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCommentInfo_Id() {
		return (EAttribute) commentInfoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCommentInfo_Path() {
		return (EAttribute) commentInfoEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCommentInfo_Side() {
		return (EAttribute) commentInfoEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCommentInfo_Line() {
		return (EAttribute) commentInfoEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCommentInfo_Range() {
		return (EReference) commentInfoEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCommentInfo_InReplyTo() {
		return (EAttribute) commentInfoEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCommentInfo_Message() {
		return (EAttribute) commentInfoEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCommentInfo_Updated() {
		return (EAttribute) commentInfoEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCommentInfo_Author() {
		return (EReference) commentInfoEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getStringToRevisionInfo() {
		return stringToRevisionInfoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getStringToRevisionInfo_Key() {
		return (EAttribute) stringToRevisionInfoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getStringToRevisionInfo_Value() {
		return (EReference) stringToRevisionInfoEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getReviewInfo() {
		return reviewInfoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getReviewInfo_Labels() {
		return (EReference) reviewInfoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getLabelInfo() {
		return labelInfoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getLabelInfo_Optional() {
		return (EAttribute) labelInfoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getLabelInfo_Approved() {
		return (EReference) labelInfoEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getLabelInfo_Rejected() {
		return (EReference) labelInfoEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getLabelInfo_Recommended() {
		return (EReference) labelInfoEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getLabelInfo_Disliked() {
		return (EReference) labelInfoEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getLabelInfo_Blocking() {
		return (EAttribute) labelInfoEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getLabelInfo_Value() {
		return (EAttribute) labelInfoEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getLabelInfo_Default_value() {
		return (EAttribute) labelInfoEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getLabelInfo_All() {
		return (EReference) labelInfoEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getLabelInfo_Values() {
		return (EReference) labelInfoEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getApprovalInfo() {
		return approvalInfoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getApprovalInfo_Value() {
		return (EAttribute) approvalInfoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getApprovalInfo_Date() {
		return (EAttribute) approvalInfoEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getRelatedChangesInfo() {
		return relatedChangesInfoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getRelatedChangesInfo_Changes() {
		return (EReference) relatedChangesInfoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getFileInfo() {
		return fileInfoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFileInfo_Status() {
		return (EAttribute) fileInfoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFileInfo_Binary() {
		return (EAttribute) fileInfoEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFileInfo_Old_path() {
		return (EAttribute) fileInfoEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFileInfo_Lines_inserted() {
		return (EAttribute) fileInfoEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFileInfo_Lines_deleted() {
		return (EAttribute) fileInfoEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFileInfo_ContainedIn() {
		return (EReference) fileInfoEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFileInfo_NewComments() {
		return (EReference) fileInfoEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFileInfo_DraftComments() {
		return (EReference) fileInfoEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFileInfo_Reviewed() {
		return (EAttribute) fileInfoEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getMergeableInfo() {
		return mergeableInfoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMergeableInfo_Submit_type() {
		return (EAttribute) mergeableInfoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMergeableInfo_Mergeable_into() {
		return (EAttribute) mergeableInfoEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMergeableInfo_Mergeable() {
		return (EAttribute) mergeableInfoEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getProjectInfo() {
		return projectInfoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getProjectInfo_Id() {
		return (EAttribute) projectInfoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getProjectInfo_Name() {
		return (EAttribute) projectInfoEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getProjectInfo_Parent() {
		return (EAttribute) projectInfoEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getProjectInfo_Description() {
		return (EAttribute) projectInfoEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getProjectInfo_State() {
		return (EAttribute) projectInfoEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getProjectInfo_Branches() {
		return (EReference) projectInfoEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getCommitInfo() {
		return commitInfoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCommitInfo_Commit() {
		return (EAttribute) commitInfoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCommitInfo_Parents() {
		return (EReference) commitInfoEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCommitInfo_Author() {
		return (EReference) commitInfoEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCommitInfo_Committer() {
		return (EReference) commitInfoEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCommitInfo_Subject() {
		return (EAttribute) commitInfoEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCommitInfo_Message() {
		return (EAttribute) commitInfoEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAccountInfo() {
		return accountInfoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAccountInfo__account_id() {
		return (EAttribute) accountInfoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAccountInfo_Name() {
		return (EAttribute) accountInfoEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAccountInfo_Email() {
		return (EAttribute) accountInfoEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAccountInfo_Username() {
		return (EAttribute) accountInfoEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getReviewerInfo() {
		return reviewerInfoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getReviewerInfo__account_id() {
		return (EAttribute) reviewerInfoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getReviewerInfo_Name() {
		return (EAttribute) reviewerInfoEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getReviewerInfo_Email() {
		return (EAttribute) reviewerInfoEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getReviewerInfo_Username() {
		return (EAttribute) reviewerInfoEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getReviewerInfo_Approvals() {
		return (EReference) reviewerInfoEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getGitPersonInfo() {
		return gitPersonInfoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getGitPersonInfo_Name() {
		return (EAttribute) gitPersonInfoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getGitPersonInfo_Email() {
		return (EAttribute) gitPersonInfoEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getGitPersonInfo_Date() {
		return (EAttribute) gitPersonInfoEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getGitPersonInfo_Tz() {
		return (EAttribute) gitPersonInfoEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIncludedInInfo() {
		return includedInInfoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getIncludedInInfo_Branches() {
		return (EAttribute) includedInInfoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getIncludedInInfo_Tags() {
		return (EAttribute) includedInInfoEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getStringToFileInfo() {
		return stringToFileInfoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getStringToFileInfo_Key() {
		return (EAttribute) stringToFileInfoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getStringToFileInfo_Value() {
		return (EReference) stringToFileInfoEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getStringToLabelInfo() {
		return stringToLabelInfoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getStringToLabelInfo_Key() {
		return (EAttribute) stringToLabelInfoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getStringToLabelInfo_Value() {
		return (EReference) stringToLabelInfoEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getBranchInfo() {
		return branchInfoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getBranchInfo_Ref() {
		return (EAttribute) branchInfoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getBranchInfo_Revision() {
		return (EAttribute) branchInfoEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getBranchInfo_Can_delete() {
		return (EAttribute) branchInfoEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getChangeInfo() {
		return changeInfoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getChangeInfo_Kind() {
		return (EAttribute) changeInfoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getChangeInfo_Id() {
		return (EAttribute) changeInfoEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getChangeInfo_Project() {
		return (EAttribute) changeInfoEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getChangeInfo_Branch() {
		return (EAttribute) changeInfoEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getChangeInfo_Topic() {
		return (EAttribute) changeInfoEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getChangeInfo_Change_id() {
		return (EAttribute) changeInfoEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getChangeInfo_Subject() {
		return (EAttribute) changeInfoEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getChangeInfo_Status() {
		return (EAttribute) changeInfoEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getChangeInfo_Created() {
		return (EAttribute) changeInfoEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getChangeInfo_Updated() {
		return (EAttribute) changeInfoEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getChangeInfo_Starred() {
		return (EAttribute) changeInfoEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getChangeInfo_Reviewed() {
		return (EAttribute) changeInfoEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getChangeInfo_Mergeable() {
		return (EAttribute) changeInfoEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getChangeInfo_Insertions() {
		return (EAttribute) changeInfoEClass.getEStructuralFeatures().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getChangeInfo_Deletions() {
		return (EAttribute) changeInfoEClass.getEStructuralFeatures().get(14);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getChangeInfo__sortkey() {
		return (EAttribute) changeInfoEClass.getEStructuralFeatures().get(15);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getChangeInfo__number() {
		return (EAttribute) changeInfoEClass.getEStructuralFeatures().get(16);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getChangeInfo_Owner() {
		return (EReference) changeInfoEClass.getEStructuralFeatures().get(17);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getChangeInfo_Actions() {
		return (EReference) changeInfoEClass.getEStructuralFeatures().get(18);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getChangeInfo_Labels() {
		return (EReference) changeInfoEClass.getEStructuralFeatures().get(19);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getChangeInfo_Permitted_labels() {
		return (EReference) changeInfoEClass.getEStructuralFeatures().get(20);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getChangeInfo_Removable_reviewers() {
		return (EReference) changeInfoEClass.getEStructuralFeatures().get(21);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getChangeInfo_Messages() {
		return (EReference) changeInfoEClass.getEStructuralFeatures().get(22);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getChangeInfo_Current_revision() {
		return (EAttribute) changeInfoEClass.getEStructuralFeatures().get(23);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getChangeInfo_Revisions() {
		return (EReference) changeInfoEClass.getEStructuralFeatures().get(24);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getChangeInfo__more_changes() {
		return (EAttribute) changeInfoEClass.getEStructuralFeatures().get(25);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getChangeInfo_Problems() {
		return (EReference) changeInfoEClass.getEStructuralFeatures().get(26);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getChangeInfo_Base_change() {
		return (EAttribute) changeInfoEClass.getEStructuralFeatures().get(27);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getChangeInfo_IncludedIn() {
		return (EReference) changeInfoEClass.getEStructuralFeatures().get(28);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getChangeInfo_Hashtags() {
		return (EAttribute) changeInfoEClass.getEStructuralFeatures().get(29);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getChangeInfo_RelatedChanges() {
		return (EReference) changeInfoEClass.getEStructuralFeatures().get(30);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getChangeInfo_Reviewers() {
		return (EReference) changeInfoEClass.getEStructuralFeatures().get(31);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getChangeInfo_SameTopic() {
		return (EReference) changeInfoEClass.getEStructuralFeatures().get(32);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getChangeInfo_ConflictsWith() {
		return (EReference) changeInfoEClass.getEStructuralFeatures().get(33);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getChangeInfo_MergeableInfo() {
		return (EReference) changeInfoEClass.getEStructuralFeatures().get(34);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getChangeInfo_Revision() {
		return (EReference) changeInfoEClass.getEStructuralFeatures().get(35);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getChangeInfo_LatestPatchSet() {
		return (EReference) changeInfoEClass.getEStructuralFeatures().get(36);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getChangeInfo__GetRevisionByNumber__int() {
		return changeInfoEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getChangeInfo__IsActionAllowed__String() {
		return changeInfoEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getProblemInfo() {
		return problemInfoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getProblemInfo_Message() {
		return (EAttribute) problemInfoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getProblemInfo_Status() {
		return (EAttribute) problemInfoEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getProblemInfo_Outcome() {
		return (EAttribute) problemInfoEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getChangeMessageInfo() {
		return changeMessageInfoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getChangeMessageInfo_Id() {
		return (EAttribute) changeMessageInfoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getChangeMessageInfo_Author() {
		return (EReference) changeMessageInfoEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getChangeMessageInfo_Date() {
		return (EAttribute) changeMessageInfoEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getChangeMessageInfo_Message() {
		return (EAttribute) changeMessageInfoEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getChangeMessageInfo__revision_number() {
		return (EAttribute) changeMessageInfoEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getStringToActionInfo() {
		return stringToActionInfoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getStringToActionInfo_Key() {
		return (EAttribute) stringToActionInfoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getStringToActionInfo_Value() {
		return (EReference) stringToActionInfoEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getStringToListOfString() {
		return stringToListOfStringEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getStringToListOfString_Key() {
		return (EAttribute) stringToListOfStringEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getStringToListOfString_Value() {
		return (EAttribute) stringToListOfStringEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getStringToFetchInfo() {
		return stringToFetchInfoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getStringToFetchInfo_Key() {
		return (EAttribute) stringToFetchInfoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getStringToFetchInfo_Value() {
		return (EReference) stringToFetchInfoEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getRevisionInfo() {
		return revisionInfoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getRevisionInfo_Draft() {
		return (EAttribute) revisionInfoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getRevisionInfo_Has_draft_comments() {
		return (EAttribute) revisionInfoEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getRevisionInfo__number() {
		return (EAttribute) revisionInfoEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getRevisionInfo_Ref() {
		return (EAttribute) revisionInfoEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getRevisionInfo_Fetch() {
		return (EReference) revisionInfoEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getRevisionInfo_Commit() {
		return (EReference) revisionInfoEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getRevisionInfo_Files() {
		return (EReference) revisionInfoEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getRevisionInfo_Actions() {
		return (EReference) revisionInfoEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getRevisionInfo_Reviewed() {
		return (EAttribute) revisionInfoEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getRevisionInfo_Id() {
		return (EAttribute) revisionInfoEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getRevisionInfo__IsActionAllowed__String() {
		return revisionInfoEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSuggestReviewerInfo() {
		return suggestReviewerInfoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSuggestReviewerInfo_Account() {
		return (EReference) suggestReviewerInfoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSuggestReviewerInfo_Group() {
		return (EReference) suggestReviewerInfoEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getGroupBaseInfo() {
		return groupBaseInfoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getGroupBaseInfo_Name() {
		return (EAttribute) groupBaseInfoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getGroupBaseInfo_Id() {
		return (EAttribute) groupBaseInfoEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getReviews() {
		return reviewsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getReviews_AllReviews() {
		return (EReference) reviewsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getActionConstants() {
		return actionConstantsEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ModelFactory getModelFactory() {
		return (ModelFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated)
			return;
		isCreated = true;

		// Create classes and their features
		relatedChangeAndCommitInfoEClass = createEClass(RELATED_CHANGE_AND_COMMIT_INFO);
		createEAttribute(relatedChangeAndCommitInfoEClass, RELATED_CHANGE_AND_COMMIT_INFO__CHANGE_ID);
		createEReference(relatedChangeAndCommitInfoEClass, RELATED_CHANGE_AND_COMMIT_INFO__COMMIT);
		createEAttribute(relatedChangeAndCommitInfoEClass, RELATED_CHANGE_AND_COMMIT_INFO__CHANGE_NUMBER);
		createEAttribute(relatedChangeAndCommitInfoEClass, RELATED_CHANGE_AND_COMMIT_INFO__REVISION_NUMBER);
		createEAttribute(relatedChangeAndCommitInfoEClass, RELATED_CHANGE_AND_COMMIT_INFO__CURRENT_REVISION_NUMBER);

		fetchInfoEClass = createEClass(FETCH_INFO);
		createEAttribute(fetchInfoEClass, FETCH_INFO__URL);
		createEAttribute(fetchInfoEClass, FETCH_INFO__REF);
		createEReference(fetchInfoEClass, FETCH_INFO__COMMANDS);

		submitInfoEClass = createEClass(SUBMIT_INFO);
		createEAttribute(submitInfoEClass, SUBMIT_INFO__STATUS);
		createEAttribute(submitInfoEClass, SUBMIT_INFO__ON_BEHALF_OF);

		stringToStringEClass = createEClass(STRING_TO_STRING);
		createEAttribute(stringToStringEClass, STRING_TO_STRING__KEY);
		createEAttribute(stringToStringEClass, STRING_TO_STRING__VALUE);

		projectAccessInfoEClass = createEClass(PROJECT_ACCESS_INFO);
		createEAttribute(projectAccessInfoEClass, PROJECT_ACCESS_INFO__REVISION);
		createEReference(projectAccessInfoEClass, PROJECT_ACCESS_INFO__INHERITS_FROM);
		createEAttribute(projectAccessInfoEClass, PROJECT_ACCESS_INFO__IS_OWNER);
		createEAttribute(projectAccessInfoEClass, PROJECT_ACCESS_INFO__OWNER_OF);
		createEAttribute(projectAccessInfoEClass, PROJECT_ACCESS_INFO__CAN_UPLOAD);
		createEAttribute(projectAccessInfoEClass, PROJECT_ACCESS_INFO__CAN_ADD);
		createEAttribute(projectAccessInfoEClass, PROJECT_ACCESS_INFO__CONFIG_VISIBLE);

		commentRangeEClass = createEClass(COMMENT_RANGE);
		createEAttribute(commentRangeEClass, COMMENT_RANGE__START_LINE);
		createEAttribute(commentRangeEClass, COMMENT_RANGE__START_CHARACTER);
		createEAttribute(commentRangeEClass, COMMENT_RANGE__END_LINE);
		createEAttribute(commentRangeEClass, COMMENT_RANGE__END_CHARACTER);

		actionInfoEClass = createEClass(ACTION_INFO);
		createEAttribute(actionInfoEClass, ACTION_INFO__METHOD);
		createEAttribute(actionInfoEClass, ACTION_INFO__LABEL);
		createEAttribute(actionInfoEClass, ACTION_INFO__TITLE);
		createEAttribute(actionInfoEClass, ACTION_INFO__ENABLED);

		commentInfoEClass = createEClass(COMMENT_INFO);
		createEAttribute(commentInfoEClass, COMMENT_INFO__ID);
		createEAttribute(commentInfoEClass, COMMENT_INFO__PATH);
		createEAttribute(commentInfoEClass, COMMENT_INFO__SIDE);
		createEAttribute(commentInfoEClass, COMMENT_INFO__LINE);
		createEReference(commentInfoEClass, COMMENT_INFO__RANGE);
		createEAttribute(commentInfoEClass, COMMENT_INFO__IN_REPLY_TO);
		createEAttribute(commentInfoEClass, COMMENT_INFO__MESSAGE);
		createEAttribute(commentInfoEClass, COMMENT_INFO__UPDATED);
		createEReference(commentInfoEClass, COMMENT_INFO__AUTHOR);

		stringToRevisionInfoEClass = createEClass(STRING_TO_REVISION_INFO);
		createEAttribute(stringToRevisionInfoEClass, STRING_TO_REVISION_INFO__KEY);
		createEReference(stringToRevisionInfoEClass, STRING_TO_REVISION_INFO__VALUE);

		reviewInfoEClass = createEClass(REVIEW_INFO);
		createEReference(reviewInfoEClass, REVIEW_INFO__LABELS);

		labelInfoEClass = createEClass(LABEL_INFO);
		createEAttribute(labelInfoEClass, LABEL_INFO__OPTIONAL);
		createEReference(labelInfoEClass, LABEL_INFO__APPROVED);
		createEReference(labelInfoEClass, LABEL_INFO__REJECTED);
		createEReference(labelInfoEClass, LABEL_INFO__RECOMMENDED);
		createEReference(labelInfoEClass, LABEL_INFO__DISLIKED);
		createEAttribute(labelInfoEClass, LABEL_INFO__BLOCKING);
		createEAttribute(labelInfoEClass, LABEL_INFO__VALUE);
		createEAttribute(labelInfoEClass, LABEL_INFO__DEFAULT_VALUE);
		createEReference(labelInfoEClass, LABEL_INFO__ALL);
		createEReference(labelInfoEClass, LABEL_INFO__VALUES);

		approvalInfoEClass = createEClass(APPROVAL_INFO);
		createEAttribute(approvalInfoEClass, APPROVAL_INFO__VALUE);
		createEAttribute(approvalInfoEClass, APPROVAL_INFO__DATE);

		relatedChangesInfoEClass = createEClass(RELATED_CHANGES_INFO);
		createEReference(relatedChangesInfoEClass, RELATED_CHANGES_INFO__CHANGES);

		fileInfoEClass = createEClass(FILE_INFO);
		createEAttribute(fileInfoEClass, FILE_INFO__STATUS);
		createEAttribute(fileInfoEClass, FILE_INFO__BINARY);
		createEAttribute(fileInfoEClass, FILE_INFO__OLD_PATH);
		createEAttribute(fileInfoEClass, FILE_INFO__LINES_INSERTED);
		createEAttribute(fileInfoEClass, FILE_INFO__LINES_DELETED);
		createEReference(fileInfoEClass, FILE_INFO__CONTAINED_IN);
		createEReference(fileInfoEClass, FILE_INFO__NEW_COMMENTS);
		createEReference(fileInfoEClass, FILE_INFO__DRAFT_COMMENTS);
		createEAttribute(fileInfoEClass, FILE_INFO__REVIEWED);

		mergeableInfoEClass = createEClass(MERGEABLE_INFO);
		createEAttribute(mergeableInfoEClass, MERGEABLE_INFO__SUBMIT_TYPE);
		createEAttribute(mergeableInfoEClass, MERGEABLE_INFO__MERGEABLE_INTO);
		createEAttribute(mergeableInfoEClass, MERGEABLE_INFO__MERGEABLE);

		projectInfoEClass = createEClass(PROJECT_INFO);
		createEAttribute(projectInfoEClass, PROJECT_INFO__ID);
		createEAttribute(projectInfoEClass, PROJECT_INFO__NAME);
		createEAttribute(projectInfoEClass, PROJECT_INFO__PARENT);
		createEAttribute(projectInfoEClass, PROJECT_INFO__DESCRIPTION);
		createEAttribute(projectInfoEClass, PROJECT_INFO__STATE);
		createEReference(projectInfoEClass, PROJECT_INFO__BRANCHES);

		commitInfoEClass = createEClass(COMMIT_INFO);
		createEAttribute(commitInfoEClass, COMMIT_INFO__COMMIT);
		createEReference(commitInfoEClass, COMMIT_INFO__PARENTS);
		createEReference(commitInfoEClass, COMMIT_INFO__AUTHOR);
		createEReference(commitInfoEClass, COMMIT_INFO__COMMITTER);
		createEAttribute(commitInfoEClass, COMMIT_INFO__SUBJECT);
		createEAttribute(commitInfoEClass, COMMIT_INFO__MESSAGE);

		accountInfoEClass = createEClass(ACCOUNT_INFO);
		createEAttribute(accountInfoEClass, ACCOUNT_INFO__ACCOUNT_ID);
		createEAttribute(accountInfoEClass, ACCOUNT_INFO__NAME);
		createEAttribute(accountInfoEClass, ACCOUNT_INFO__EMAIL);
		createEAttribute(accountInfoEClass, ACCOUNT_INFO__USERNAME);

		reviewerInfoEClass = createEClass(REVIEWER_INFO);
		createEAttribute(reviewerInfoEClass, REVIEWER_INFO__ACCOUNT_ID);
		createEAttribute(reviewerInfoEClass, REVIEWER_INFO__NAME);
		createEAttribute(reviewerInfoEClass, REVIEWER_INFO__EMAIL);
		createEAttribute(reviewerInfoEClass, REVIEWER_INFO__USERNAME);
		createEReference(reviewerInfoEClass, REVIEWER_INFO__APPROVALS);

		gitPersonInfoEClass = createEClass(GIT_PERSON_INFO);
		createEAttribute(gitPersonInfoEClass, GIT_PERSON_INFO__NAME);
		createEAttribute(gitPersonInfoEClass, GIT_PERSON_INFO__EMAIL);
		createEAttribute(gitPersonInfoEClass, GIT_PERSON_INFO__DATE);
		createEAttribute(gitPersonInfoEClass, GIT_PERSON_INFO__TZ);

		includedInInfoEClass = createEClass(INCLUDED_IN_INFO);
		createEAttribute(includedInInfoEClass, INCLUDED_IN_INFO__BRANCHES);
		createEAttribute(includedInInfoEClass, INCLUDED_IN_INFO__TAGS);

		stringToFileInfoEClass = createEClass(STRING_TO_FILE_INFO);
		createEAttribute(stringToFileInfoEClass, STRING_TO_FILE_INFO__KEY);
		createEReference(stringToFileInfoEClass, STRING_TO_FILE_INFO__VALUE);

		stringToLabelInfoEClass = createEClass(STRING_TO_LABEL_INFO);
		createEAttribute(stringToLabelInfoEClass, STRING_TO_LABEL_INFO__KEY);
		createEReference(stringToLabelInfoEClass, STRING_TO_LABEL_INFO__VALUE);

		branchInfoEClass = createEClass(BRANCH_INFO);
		createEAttribute(branchInfoEClass, BRANCH_INFO__REF);
		createEAttribute(branchInfoEClass, BRANCH_INFO__REVISION);
		createEAttribute(branchInfoEClass, BRANCH_INFO__CAN_DELETE);

		changeInfoEClass = createEClass(CHANGE_INFO);
		createEAttribute(changeInfoEClass, CHANGE_INFO__KIND);
		createEAttribute(changeInfoEClass, CHANGE_INFO__ID);
		createEAttribute(changeInfoEClass, CHANGE_INFO__PROJECT);
		createEAttribute(changeInfoEClass, CHANGE_INFO__BRANCH);
		createEAttribute(changeInfoEClass, CHANGE_INFO__TOPIC);
		createEAttribute(changeInfoEClass, CHANGE_INFO__CHANGE_ID);
		createEAttribute(changeInfoEClass, CHANGE_INFO__SUBJECT);
		createEAttribute(changeInfoEClass, CHANGE_INFO__STATUS);
		createEAttribute(changeInfoEClass, CHANGE_INFO__CREATED);
		createEAttribute(changeInfoEClass, CHANGE_INFO__UPDATED);
		createEAttribute(changeInfoEClass, CHANGE_INFO__STARRED);
		createEAttribute(changeInfoEClass, CHANGE_INFO__REVIEWED);
		createEAttribute(changeInfoEClass, CHANGE_INFO__MERGEABLE);
		createEAttribute(changeInfoEClass, CHANGE_INFO__INSERTIONS);
		createEAttribute(changeInfoEClass, CHANGE_INFO__DELETIONS);
		createEAttribute(changeInfoEClass, CHANGE_INFO__SORTKEY);
		createEAttribute(changeInfoEClass, CHANGE_INFO__NUMBER);
		createEReference(changeInfoEClass, CHANGE_INFO__OWNER);
		createEReference(changeInfoEClass, CHANGE_INFO__ACTIONS);
		createEReference(changeInfoEClass, CHANGE_INFO__LABELS);
		createEReference(changeInfoEClass, CHANGE_INFO__PERMITTED_LABELS);
		createEReference(changeInfoEClass, CHANGE_INFO__REMOVABLE_REVIEWERS);
		createEReference(changeInfoEClass, CHANGE_INFO__MESSAGES);
		createEAttribute(changeInfoEClass, CHANGE_INFO__CURRENT_REVISION);
		createEReference(changeInfoEClass, CHANGE_INFO__REVISIONS);
		createEAttribute(changeInfoEClass, CHANGE_INFO__MORE_CHANGES);
		createEReference(changeInfoEClass, CHANGE_INFO__PROBLEMS);
		createEAttribute(changeInfoEClass, CHANGE_INFO__BASE_CHANGE);
		createEReference(changeInfoEClass, CHANGE_INFO__INCLUDED_IN);
		createEAttribute(changeInfoEClass, CHANGE_INFO__HASHTAGS);
		createEReference(changeInfoEClass, CHANGE_INFO__RELATED_CHANGES);
		createEReference(changeInfoEClass, CHANGE_INFO__REVIEWERS);
		createEReference(changeInfoEClass, CHANGE_INFO__SAME_TOPIC);
		createEReference(changeInfoEClass, CHANGE_INFO__CONFLICTS_WITH);
		createEReference(changeInfoEClass, CHANGE_INFO__MERGEABLE_INFO);
		createEReference(changeInfoEClass, CHANGE_INFO__REVISION);
		createEReference(changeInfoEClass, CHANGE_INFO__LATEST_PATCH_SET);
		createEOperation(changeInfoEClass, CHANGE_INFO___GET_REVISION_BY_NUMBER__INT);
		createEOperation(changeInfoEClass, CHANGE_INFO___IS_ACTION_ALLOWED__STRING);

		problemInfoEClass = createEClass(PROBLEM_INFO);
		createEAttribute(problemInfoEClass, PROBLEM_INFO__MESSAGE);
		createEAttribute(problemInfoEClass, PROBLEM_INFO__STATUS);
		createEAttribute(problemInfoEClass, PROBLEM_INFO__OUTCOME);

		changeMessageInfoEClass = createEClass(CHANGE_MESSAGE_INFO);
		createEAttribute(changeMessageInfoEClass, CHANGE_MESSAGE_INFO__ID);
		createEReference(changeMessageInfoEClass, CHANGE_MESSAGE_INFO__AUTHOR);
		createEAttribute(changeMessageInfoEClass, CHANGE_MESSAGE_INFO__DATE);
		createEAttribute(changeMessageInfoEClass, CHANGE_MESSAGE_INFO__MESSAGE);
		createEAttribute(changeMessageInfoEClass, CHANGE_MESSAGE_INFO__REVISION_NUMBER);

		stringToActionInfoEClass = createEClass(STRING_TO_ACTION_INFO);
		createEAttribute(stringToActionInfoEClass, STRING_TO_ACTION_INFO__KEY);
		createEReference(stringToActionInfoEClass, STRING_TO_ACTION_INFO__VALUE);

		stringToListOfStringEClass = createEClass(STRING_TO_LIST_OF_STRING);
		createEAttribute(stringToListOfStringEClass, STRING_TO_LIST_OF_STRING__KEY);
		createEAttribute(stringToListOfStringEClass, STRING_TO_LIST_OF_STRING__VALUE);

		stringToFetchInfoEClass = createEClass(STRING_TO_FETCH_INFO);
		createEAttribute(stringToFetchInfoEClass, STRING_TO_FETCH_INFO__KEY);
		createEReference(stringToFetchInfoEClass, STRING_TO_FETCH_INFO__VALUE);

		revisionInfoEClass = createEClass(REVISION_INFO);
		createEAttribute(revisionInfoEClass, REVISION_INFO__DRAFT);
		createEAttribute(revisionInfoEClass, REVISION_INFO__HAS_DRAFT_COMMENTS);
		createEAttribute(revisionInfoEClass, REVISION_INFO__NUMBER);
		createEAttribute(revisionInfoEClass, REVISION_INFO__REF);
		createEReference(revisionInfoEClass, REVISION_INFO__FETCH);
		createEReference(revisionInfoEClass, REVISION_INFO__COMMIT);
		createEReference(revisionInfoEClass, REVISION_INFO__FILES);
		createEReference(revisionInfoEClass, REVISION_INFO__ACTIONS);
		createEAttribute(revisionInfoEClass, REVISION_INFO__REVIEWED);
		createEAttribute(revisionInfoEClass, REVISION_INFO__ID);
		createEOperation(revisionInfoEClass, REVISION_INFO___IS_ACTION_ALLOWED__STRING);

		suggestReviewerInfoEClass = createEClass(SUGGEST_REVIEWER_INFO);
		createEReference(suggestReviewerInfoEClass, SUGGEST_REVIEWER_INFO__ACCOUNT);
		createEReference(suggestReviewerInfoEClass, SUGGEST_REVIEWER_INFO__GROUP);

		groupBaseInfoEClass = createEClass(GROUP_BASE_INFO);
		createEAttribute(groupBaseInfoEClass, GROUP_BASE_INFO__NAME);
		createEAttribute(groupBaseInfoEClass, GROUP_BASE_INFO__ID);

		reviewsEClass = createEClass(REVIEWS);
		createEReference(reviewsEClass, REVIEWS__ALL_REVIEWS);

		// Create enums
		actionConstantsEEnum = createEEnum(ACTION_CONSTANTS);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized)
			return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes, features, and operations; add parameters
		initEClass(relatedChangeAndCommitInfoEClass, RelatedChangeAndCommitInfo.class, "RelatedChangeAndCommitInfo", //$NON-NLS-1$
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRelatedChangeAndCommitInfo_Change_id(), ecorePackage.getEString(), "change_id", null, 0, 1, //$NON-NLS-1$
				RelatedChangeAndCommitInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRelatedChangeAndCommitInfo_Commit(), this.getCommitInfo(), null, "commit", null, 0, 1, //$NON-NLS-1$
				RelatedChangeAndCommitInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRelatedChangeAndCommitInfo__change_number(), ecorePackage.getEString(), "_change_number", //$NON-NLS-1$
				null, 0, 1, RelatedChangeAndCommitInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRelatedChangeAndCommitInfo__revision_number(), ecorePackage.getEString(), "_revision_number", //$NON-NLS-1$
				null, 0, 1, RelatedChangeAndCommitInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRelatedChangeAndCommitInfo__current_revision_number(), ecorePackage.getEString(),
				"_current_revision_number", null, 0, 1, RelatedChangeAndCommitInfo.class, !IS_TRANSIENT, !IS_VOLATILE, //$NON-NLS-1$
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(fetchInfoEClass, FetchInfo.class, "FetchInfo", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFetchInfo_Url(), ecorePackage.getEString(), "url", null, 0, 1, FetchInfo.class, !IS_TRANSIENT, //$NON-NLS-1$
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFetchInfo_Ref(), ecorePackage.getEString(), "ref", null, 0, 1, FetchInfo.class, !IS_TRANSIENT, //$NON-NLS-1$
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFetchInfo_Commands(), this.getStringToString(), null, "commands", null, 0, -1, //$NON-NLS-1$
				FetchInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(submitInfoEClass, SubmitInfo.class, "SubmitInfo", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSubmitInfo_Status(), ecorePackage.getEString(), "status", null, 0, 1, SubmitInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSubmitInfo_On_behalf_of(), ecorePackage.getEString(), "on_behalf_of", null, 0, 1, //$NON-NLS-1$
				SubmitInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(stringToStringEClass, Map.Entry.class, "StringToString", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				!IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStringToString_Key(), ecorePackage.getEString(), "key", null, 0, 1, Map.Entry.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStringToString_Value(), ecorePackage.getEString(), "value", null, 0, 1, Map.Entry.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(projectAccessInfoEClass, ProjectAccessInfo.class, "ProjectAccessInfo", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getProjectAccessInfo_Revision(), ecorePackage.getEString(), "revision", null, 0, 1, //$NON-NLS-1$
				ProjectAccessInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getProjectAccessInfo_Inherits_from(), this.getProjectInfo(), null, "inherits_from", null, 0, 1, //$NON-NLS-1$
				ProjectAccessInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProjectAccessInfo_Is_owner(), ecorePackage.getEBoolean(), "is_owner", null, 0, 1, //$NON-NLS-1$
				ProjectAccessInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getProjectAccessInfo_OwnerOf(), ecorePackage.getEString(), "ownerOf", null, 0, -1, //$NON-NLS-1$
				ProjectAccessInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getProjectAccessInfo_Can_upload(), ecorePackage.getEBoolean(), "can_upload", null, 0, 1, //$NON-NLS-1$
				ProjectAccessInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getProjectAccessInfo_Can_add(), ecorePackage.getEBoolean(), "can_add", null, 0, 1, //$NON-NLS-1$
				ProjectAccessInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getProjectAccessInfo_Config_visible(), ecorePackage.getEBoolean(), "config_visible", null, 0, 1, //$NON-NLS-1$
				ProjectAccessInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(commentRangeEClass, CommentRange.class, "CommentRange", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCommentRange_StartLine(), ecorePackage.getEInt(), "startLine", null, 0, 1, CommentRange.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCommentRange_StartCharacter(), ecorePackage.getEInt(), "startCharacter", null, 0, 1, //$NON-NLS-1$
				CommentRange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getCommentRange_EndLine(), ecorePackage.getEInt(), "endLine", null, 0, 1, CommentRange.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCommentRange_EndCharacter(), ecorePackage.getEInt(), "endCharacter", null, 0, 1, //$NON-NLS-1$
				CommentRange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(actionInfoEClass, ActionInfo.class, "ActionInfo", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getActionInfo_Method(), ecorePackage.getEString(), "method", null, 0, 1, ActionInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getActionInfo_Label(), ecorePackage.getEString(), "label", null, 0, 1, ActionInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getActionInfo_Title(), ecorePackage.getEString(), "title", null, 0, 1, ActionInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getActionInfo_Enabled(), ecorePackage.getEBoolean(), "enabled", null, 0, 1, ActionInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(commentInfoEClass, CommentInfo.class, "CommentInfo", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCommentInfo_Id(), ecorePackage.getEString(), "id", null, 0, 1, CommentInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCommentInfo_Path(), ecorePackage.getEString(), "path", null, 0, 1, CommentInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCommentInfo_Side(), ecorePackage.getEString(), "side", null, 0, 1, CommentInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCommentInfo_Line(), ecorePackage.getEInt(), "line", null, 0, 1, CommentInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCommentInfo_Range(), this.getCommentRange(), null, "range", null, 0, 1, CommentInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCommentInfo_InReplyTo(), ecorePackage.getEString(), "inReplyTo", null, 0, 1, //$NON-NLS-1$
				CommentInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getCommentInfo_Message(), ecorePackage.getEString(), "message", null, 0, 1, CommentInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCommentInfo_Updated(), ecorePackage.getEString(), "updated", null, 0, 1, CommentInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCommentInfo_Author(), this.getAccountInfo(), null, "author", null, 0, 1, CommentInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(stringToRevisionInfoEClass, Map.Entry.class, "StringToRevisionInfo", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				!IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStringToRevisionInfo_Key(), ecorePackage.getEString(), "key", null, 0, 1, Map.Entry.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getStringToRevisionInfo_Value(), this.getRevisionInfo(), null, "value", null, 0, 1, //$NON-NLS-1$
				Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(reviewInfoEClass, ReviewInfo.class, "ReviewInfo", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getReviewInfo_Labels(), this.getStringToString(), null, "labels", null, 0, -1, ReviewInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(labelInfoEClass, LabelInfo.class, "LabelInfo", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getLabelInfo_Optional(), ecorePackage.getEBoolean(), "optional", null, 0, 1, LabelInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getLabelInfo_Approved(), this.getAccountInfo(), null, "approved", null, 0, 1, LabelInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getLabelInfo_Rejected(), this.getAccountInfo(), null, "rejected", null, 0, 1, LabelInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getLabelInfo_Recommended(), this.getAccountInfo(), null, "recommended", null, 0, 1, //$NON-NLS-1$
				LabelInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getLabelInfo_Disliked(), this.getAccountInfo(), null, "disliked", null, 0, 1, LabelInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLabelInfo_Blocking(), ecorePackage.getEBoolean(), "blocking", null, 0, 1, LabelInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLabelInfo_Value(), ecorePackage.getEString(), "value", null, 0, 1, LabelInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLabelInfo_Default_value(), ecorePackage.getEString(), "default_value", null, 0, 1, //$NON-NLS-1$
				LabelInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getLabelInfo_All(), this.getApprovalInfo(), null, "all", null, 0, -1, LabelInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getLabelInfo_Values(), this.getStringToString(), null, "values", null, 0, -1, LabelInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(approvalInfoEClass, ApprovalInfo.class, "ApprovalInfo", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getApprovalInfo_Value(), ecorePackage.getEIntegerObject(), "value", null, 0, 1, //$NON-NLS-1$
				ApprovalInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getApprovalInfo_Date(), ecorePackage.getEString(), "date", null, 0, 1, ApprovalInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(relatedChangesInfoEClass, RelatedChangesInfo.class, "RelatedChangesInfo", !IS_ABSTRACT, //$NON-NLS-1$
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRelatedChangesInfo_Changes(), this.getRelatedChangeAndCommitInfo(), null, "changes", null, 0, //$NON-NLS-1$
				-1, RelatedChangesInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(fileInfoEClass, FileInfo.class, "FileInfo", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFileInfo_Status(), ecorePackage.getEString(), "status", "M", 0, 1, FileInfo.class, //$NON-NLS-1$//$NON-NLS-2$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFileInfo_Binary(), ecorePackage.getEBoolean(), "binary", null, 0, 1, FileInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFileInfo_Old_path(), ecorePackage.getEString(), "old_path", null, 0, 1, FileInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFileInfo_Lines_inserted(), ecorePackage.getEInt(), "lines_inserted", null, 0, 1, //$NON-NLS-1$
				FileInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getFileInfo_Lines_deleted(), ecorePackage.getEInt(), "lines_deleted", null, 0, 1, FileInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFileInfo_ContainedIn(), this.getRevisionInfo(), null, "containedIn", null, 0, 1, //$NON-NLS-1$
				FileInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFileInfo_NewComments(), this.getCommentInfo(), null, "newComments", null, 0, -1, //$NON-NLS-1$
				FileInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFileInfo_DraftComments(), this.getCommentInfo(), null, "draftComments", null, 0, -1, //$NON-NLS-1$
				FileInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFileInfo_Reviewed(), ecorePackage.getEBoolean(), "reviewed", null, 0, 1, FileInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mergeableInfoEClass, MergeableInfo.class, "MergeableInfo", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMergeableInfo_Submit_type(), ecorePackage.getEString(), "submit_type", null, 0, 1, //$NON-NLS-1$
				MergeableInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getMergeableInfo_Mergeable_into(), ecorePackage.getEString(), "mergeable_into", null, 0, 1, //$NON-NLS-1$
				MergeableInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getMergeableInfo_Mergeable(), ecorePackage.getEBoolean(), "mergeable", null, 0, 1, //$NON-NLS-1$
				MergeableInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(projectInfoEClass, ProjectInfo.class, "ProjectInfo", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getProjectInfo_Id(), ecorePackage.getEString(), "id", null, 0, 1, ProjectInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProjectInfo_Name(), ecorePackage.getEString(), "name", null, 0, 1, ProjectInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProjectInfo_Parent(), ecorePackage.getEString(), "parent", null, 0, 1, ProjectInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProjectInfo_Description(), ecorePackage.getEString(), "description", null, 0, 1, //$NON-NLS-1$
				ProjectInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getProjectInfo_State(), ecorePackage.getEString(), "state", null, 0, 1, ProjectInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProjectInfo_Branches(), this.getStringToString(), null, "branches", null, 0, -1, //$NON-NLS-1$
				ProjectInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(commitInfoEClass, CommitInfo.class, "CommitInfo", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCommitInfo_Commit(), ecorePackage.getEString(), "commit", null, 0, 1, CommitInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCommitInfo_Parents(), this.getCommitInfo(), null, "parents", null, 0, -1, CommitInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCommitInfo_Author(), this.getGitPersonInfo(), null, "author", null, 0, 1, CommitInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCommitInfo_Committer(), this.getGitPersonInfo(), null, "committer", null, 0, 1, //$NON-NLS-1$
				CommitInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCommitInfo_Subject(), ecorePackage.getEString(), "subject", null, 0, 1, CommitInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCommitInfo_Message(), ecorePackage.getEString(), "message", null, 0, 1, CommitInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(accountInfoEClass, AccountInfo.class, "AccountInfo", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAccountInfo__account_id(), ecorePackage.getEInt(), "_account_id", null, 0, 1, //$NON-NLS-1$
				AccountInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getAccountInfo_Name(), ecorePackage.getEString(), "name", null, 0, 1, AccountInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAccountInfo_Email(), ecorePackage.getEString(), "email", null, 0, 1, AccountInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAccountInfo_Username(), ecorePackage.getEString(), "username", null, 0, 1, AccountInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(reviewerInfoEClass, ReviewerInfo.class, "ReviewerInfo", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getReviewerInfo__account_id(), ecorePackage.getEInt(), "_account_id", null, 0, 1, //$NON-NLS-1$
				ReviewerInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getReviewerInfo_Name(), ecorePackage.getEString(), "name", null, 0, 1, ReviewerInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getReviewerInfo_Email(), ecorePackage.getEString(), "email", null, 0, 1, ReviewerInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getReviewerInfo_Username(), ecorePackage.getEString(), "username", null, 0, 1, //$NON-NLS-1$
				ReviewerInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getReviewerInfo_Approvals(), this.getStringToString(), null, "approvals", null, 0, -1, //$NON-NLS-1$
				ReviewerInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(gitPersonInfoEClass, GitPersonInfo.class, "GitPersonInfo", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getGitPersonInfo_Name(), ecorePackage.getEString(), "name", null, 0, 1, GitPersonInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGitPersonInfo_Email(), ecorePackage.getEString(), "email", null, 0, 1, GitPersonInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGitPersonInfo_Date(), ecorePackage.getEString(), "date", null, 0, 1, GitPersonInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGitPersonInfo_Tz(), ecorePackage.getEInt(), "tz", null, 0, 1, GitPersonInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(includedInInfoEClass, IncludedInInfo.class, "IncludedInInfo", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIncludedInInfo_Branches(), ecorePackage.getEString(), "branches", null, 0, -1, //$NON-NLS-1$
				IncludedInInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getIncludedInInfo_Tags(), ecorePackage.getEString(), "tags", null, 0, -1, IncludedInInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(stringToFileInfoEClass, Map.Entry.class, "StringToFileInfo", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				!IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStringToFileInfo_Key(), ecorePackage.getEString(), "key", null, 0, 1, Map.Entry.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getStringToFileInfo_Value(), this.getFileInfo(), null, "value", null, 0, 1, Map.Entry.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(stringToLabelInfoEClass, Map.Entry.class, "StringToLabelInfo", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				!IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStringToLabelInfo_Key(), ecorePackage.getEString(), "key", null, 0, 1, Map.Entry.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getStringToLabelInfo_Value(), this.getLabelInfo(), null, "value", null, 0, 1, Map.Entry.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(branchInfoEClass, BranchInfo.class, "BranchInfo", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBranchInfo_Ref(), ecorePackage.getEString(), "ref", null, 0, 1, BranchInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBranchInfo_Revision(), ecorePackage.getEString(), "revision", null, 0, 1, BranchInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBranchInfo_Can_delete(), ecorePackage.getEBoolean(), "can_delete", null, 0, 1, //$NON-NLS-1$
				BranchInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(changeInfoEClass, ChangeInfo.class, "ChangeInfo", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getChangeInfo_Kind(), ecorePackage.getEString(), "kind", null, 0, 1, ChangeInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getChangeInfo_Id(), ecorePackage.getEString(), "id", null, 0, 1, ChangeInfo.class, !IS_TRANSIENT, //$NON-NLS-1$
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getChangeInfo_Project(), ecorePackage.getEString(), "project", null, 0, 1, ChangeInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getChangeInfo_Branch(), ecorePackage.getEString(), "branch", null, 0, 1, ChangeInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getChangeInfo_Topic(), ecorePackage.getEString(), "topic", null, 0, 1, ChangeInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getChangeInfo_Change_id(), ecorePackage.getEString(), "change_id", null, 0, 1, ChangeInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getChangeInfo_Subject(), ecorePackage.getEString(), "subject", null, 0, 1, ChangeInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getChangeInfo_Status(), ecorePackage.getEString(), "status", null, 0, 1, ChangeInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getChangeInfo_Created(), ecorePackage.getEString(), "created", null, 0, 1, ChangeInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getChangeInfo_Updated(), ecorePackage.getEString(), "updated", null, 0, 1, ChangeInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getChangeInfo_Starred(), ecorePackage.getEBoolean(), "starred", null, 0, 1, ChangeInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getChangeInfo_Reviewed(), ecorePackage.getEBoolean(), "reviewed", null, 0, 1, ChangeInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getChangeInfo_Mergeable(), ecorePackage.getEBoolean(), "mergeable", null, 0, 1, ChangeInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getChangeInfo_Insertions(), ecorePackage.getEInt(), "insertions", null, 0, 1, ChangeInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getChangeInfo_Deletions(), ecorePackage.getEInt(), "deletions", null, 0, 1, ChangeInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getChangeInfo__sortkey(), ecorePackage.getEString(), "_sortkey", null, 0, 1, ChangeInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getChangeInfo__number(), ecorePackage.getEInt(), "_number", null, 0, 1, ChangeInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getChangeInfo_Owner(), this.getAccountInfo(), null, "owner", null, 0, 1, ChangeInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getChangeInfo_Actions(), this.getStringToActionInfo(), null, "actions", null, 0, -1, //$NON-NLS-1$
				ChangeInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getChangeInfo_Labels(), this.getStringToLabelInfo(), null, "labels", null, 0, -1, //$NON-NLS-1$
				ChangeInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getChangeInfo_Permitted_labels(), this.getStringToListOfString(), null, "permitted_labels", null, //$NON-NLS-1$
				0, -1, ChangeInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getChangeInfo_Removable_reviewers(), this.getAccountInfo(), null, "removable_reviewers", null, 0, //$NON-NLS-1$
				-1, ChangeInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getChangeInfo_Messages(), this.getChangeMessageInfo(), null, "messages", null, 0, -1, //$NON-NLS-1$
				ChangeInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getChangeInfo_Current_revision(), ecorePackage.getEString(), "current_revision", null, 0, 1, //$NON-NLS-1$
				ChangeInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getChangeInfo_Revisions(), this.getStringToRevisionInfo(), null, "revisions", null, 0, -1, //$NON-NLS-1$
				ChangeInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getChangeInfo__more_changes(), ecorePackage.getEBoolean(), "_more_changes", null, 0, 1, //$NON-NLS-1$
				ChangeInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getChangeInfo_Problems(), this.getProblemInfo(), null, "problems", null, 0, -1, ChangeInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getChangeInfo_Base_change(), ecorePackage.getEString(), "base_change", null, 0, 1, //$NON-NLS-1$
				ChangeInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getChangeInfo_IncludedIn(), this.getIncludedInInfo(), null, "includedIn", null, 0, 1, //$NON-NLS-1$
				ChangeInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getChangeInfo_Hashtags(), ecorePackage.getEString(), "hashtags", null, 0, -1, ChangeInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getChangeInfo_RelatedChanges(), this.getRelatedChangesInfo(), null, "relatedChanges", null, 0, 1, //$NON-NLS-1$
				ChangeInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getChangeInfo_Reviewers(), this.getReviewerInfo(), null, "reviewers", null, 0, -1, //$NON-NLS-1$
				ChangeInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getChangeInfo_SameTopic(), this.getChangeInfo(), null, "sameTopic", null, 0, -1, //$NON-NLS-1$
				ChangeInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getChangeInfo_ConflictsWith(), this.getChangeInfo(), null, "conflictsWith", null, 0, -1, //$NON-NLS-1$
				ChangeInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getChangeInfo_MergeableInfo(), this.getMergeableInfo(), null, "mergeableInfo", null, 0, 1, //$NON-NLS-1$
				ChangeInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getChangeInfo_Revision(), this.getRevisionInfo(), null, "revision", null, 0, 1, ChangeInfo.class, //$NON-NLS-1$
				IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
				IS_DERIVED, IS_ORDERED);
		initEReference(getChangeInfo_LatestPatchSet(), this.getRevisionInfo(), null, "latestPatchSet", null, 0, 1, //$NON-NLS-1$
				ChangeInfo.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		EOperation op = initEOperation(getChangeInfo__GetRevisionByNumber__int(), this.getRevisionInfo(),
				"getRevisionByNumber", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEInt(), "revisionId", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = initEOperation(getChangeInfo__IsActionAllowed__String(), ecorePackage.getEBoolean(), "isActionAllowed", 0, //$NON-NLS-1$
				1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "action", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(problemInfoEClass, ProblemInfo.class, "ProblemInfo", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getProblemInfo_Message(), ecorePackage.getEString(), "message", null, 0, 1, ProblemInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProblemInfo_Status(), ecorePackage.getEString(), "status", null, 0, 1, ProblemInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProblemInfo_Outcome(), ecorePackage.getEString(), "outcome", null, 0, 1, ProblemInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(changeMessageInfoEClass, ChangeMessageInfo.class, "ChangeMessageInfo", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getChangeMessageInfo_Id(), ecorePackage.getEString(), "id", null, 0, 1, ChangeMessageInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getChangeMessageInfo_Author(), this.getAccountInfo(), null, "author", null, 0, 1, //$NON-NLS-1$
				ChangeMessageInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getChangeMessageInfo_Date(), ecorePackage.getEString(), "date", null, 0, 1, //$NON-NLS-1$
				ChangeMessageInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getChangeMessageInfo_Message(), ecorePackage.getEString(), "message", null, 0, 1, //$NON-NLS-1$
				ChangeMessageInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getChangeMessageInfo__revision_number(), ecorePackage.getEInt(), "_revision_number", null, 0, 1, //$NON-NLS-1$
				ChangeMessageInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(stringToActionInfoEClass, Map.Entry.class, "StringToActionInfo", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				!IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStringToActionInfo_Key(), ecorePackage.getEString(), "key", null, 0, 1, Map.Entry.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getStringToActionInfo_Value(), this.getActionInfo(), null, "value", null, 0, 1, Map.Entry.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(stringToListOfStringEClass, Map.Entry.class, "StringToListOfString", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				!IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStringToListOfString_Key(), ecorePackage.getEString(), "key", null, 0, 1, Map.Entry.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStringToListOfString_Value(), ecorePackage.getEString(), "value", null, 0, -1, //$NON-NLS-1$
				Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(stringToFetchInfoEClass, Map.Entry.class, "StringToFetchInfo", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				!IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStringToFetchInfo_Key(), ecorePackage.getEString(), "key", null, 0, 1, Map.Entry.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getStringToFetchInfo_Value(), this.getFetchInfo(), null, "value", null, 0, 1, Map.Entry.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(revisionInfoEClass, RevisionInfo.class, "RevisionInfo", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRevisionInfo_Draft(), ecorePackage.getEBoolean(), "draft", null, 0, 1, RevisionInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRevisionInfo_Has_draft_comments(), ecorePackage.getEBoolean(), "has_draft_comments", null, 0, //$NON-NLS-1$
				1, RevisionInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getRevisionInfo__number(), ecorePackage.getEInt(), "_number", null, 0, 1, RevisionInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRevisionInfo_Ref(), ecorePackage.getEString(), "ref", null, 0, 1, RevisionInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRevisionInfo_Fetch(), this.getStringToFetchInfo(), null, "fetch", null, 0, -1, //$NON-NLS-1$
				RevisionInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRevisionInfo_Commit(), this.getCommitInfo(), null, "commit", null, 0, 1, RevisionInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRevisionInfo_Files(), this.getStringToFileInfo(), null, "files", null, 0, -1, //$NON-NLS-1$
				RevisionInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRevisionInfo_Actions(), this.getStringToActionInfo(), null, "actions", null, 0, -1, //$NON-NLS-1$
				RevisionInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRevisionInfo_Reviewed(), ecorePackage.getEBoolean(), "reviewed", null, 0, 1, //$NON-NLS-1$
				RevisionInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getRevisionInfo_Id(), ecorePackage.getEString(), "id", null, 0, 1, RevisionInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = initEOperation(getRevisionInfo__IsActionAllowed__String(), ecorePackage.getEBoolean(), "isActionAllowed", //$NON-NLS-1$
				0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "action", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(suggestReviewerInfoEClass, SuggestReviewerInfo.class, "SuggestReviewerInfo", !IS_ABSTRACT, //$NON-NLS-1$
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSuggestReviewerInfo_Account(), this.getAccountInfo(), null, "account", null, 0, 1, //$NON-NLS-1$
				SuggestReviewerInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSuggestReviewerInfo_Group(), this.getGroupBaseInfo(), null, "group", null, 0, 1, //$NON-NLS-1$
				SuggestReviewerInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(groupBaseInfoEClass, GroupBaseInfo.class, "GroupBaseInfo", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getGroupBaseInfo_Name(), ecorePackage.getEString(), "name", null, 0, 1, GroupBaseInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGroupBaseInfo_Id(), ecorePackage.getEString(), "id", null, 0, 1, GroupBaseInfo.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(reviewsEClass, Reviews.class, "Reviews", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getReviews_AllReviews(), this.getChangeInfo(), null, "allReviews", null, 0, -1, Reviews.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(actionConstantsEEnum, ActionConstants.class, "ActionConstants"); //$NON-NLS-1$
		addEEnumLiteral(actionConstantsEEnum, ActionConstants.ABANDON);
		addEEnumLiteral(actionConstantsEEnum, ActionConstants.CHECKOUT);
		addEEnumLiteral(actionConstantsEEnum, ActionConstants.CHERRYPICK);
		addEEnumLiteral(actionConstantsEEnum, ActionConstants.DRAFT);
		addEEnumLiteral(actionConstantsEEnum, ActionConstants.FOLLOWUP);
		addEEnumLiteral(actionConstantsEEnum, ActionConstants.PUBLISH);
		addEEnumLiteral(actionConstantsEEnum, ActionConstants.REBASE);
		addEEnumLiteral(actionConstantsEEnum, ActionConstants.REFRESH);
		addEEnumLiteral(actionConstantsEEnum, ActionConstants.REPLY);
		addEEnumLiteral(actionConstantsEEnum, ActionConstants.RESTORE);
		addEEnumLiteral(actionConstantsEEnum, ActionConstants.REVERT);
		addEEnumLiteral(actionConstantsEEnum, ActionConstants.SUBMIT);
		addEEnumLiteral(actionConstantsEEnum, ActionConstants.TOPIC);

		// Create resource
		createResource(eNS_URI);
	}

} //ModelPackageImpl
