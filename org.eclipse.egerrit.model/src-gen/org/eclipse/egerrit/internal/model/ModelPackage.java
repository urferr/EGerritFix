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
package org.eclipse.egerrit.internal.model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.egerrit.internal.model.ModelFactory
 * @model kind="package"
 * @generated
 */
public interface ModelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "model"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/egerrit/2015/Egerrit"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "model"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ModelPackage eINSTANCE = org.eclipse.egerrit.internal.model.impl.ModelPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.egerrit.internal.model.impl.RelatedChangeAndCommitInfoImpl <em>Related Change And Commit Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.egerrit.internal.model.impl.RelatedChangeAndCommitInfoImpl
	 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getRelatedChangeAndCommitInfo()
	 * @generated
	 */
	int RELATED_CHANGE_AND_COMMIT_INFO = 0;

	/**
	 * The feature id for the '<em><b>Change id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_CHANGE_AND_COMMIT_INFO__CHANGE_ID = 0;

	/**
	 * The feature id for the '<em><b>Commit</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_CHANGE_AND_COMMIT_INFO__COMMIT = 1;

	/**
	 * The feature id for the '<em><b>change number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_CHANGE_AND_COMMIT_INFO__CHANGE_NUMBER = 2;

	/**
	 * The feature id for the '<em><b>revision number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_CHANGE_AND_COMMIT_INFO__REVISION_NUMBER = 3;

	/**
	 * The feature id for the '<em><b>current revision number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_CHANGE_AND_COMMIT_INFO__CURRENT_REVISION_NUMBER = 4;

	/**
	 * The number of structural features of the '<em>Related Change And Commit Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_CHANGE_AND_COMMIT_INFO_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Related Change And Commit Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_CHANGE_AND_COMMIT_INFO_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.egerrit.internal.model.impl.FetchInfoImpl <em>Fetch Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.egerrit.internal.model.impl.FetchInfoImpl
	 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getFetchInfo()
	 * @generated
	 */
	int FETCH_INFO = 1;

	/**
	 * The feature id for the '<em><b>Url</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FETCH_INFO__URL = 0;

	/**
	 * The feature id for the '<em><b>Ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FETCH_INFO__REF = 1;

	/**
	 * The feature id for the '<em><b>Commands</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FETCH_INFO__COMMANDS = 2;

	/**
	 * The number of structural features of the '<em>Fetch Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FETCH_INFO_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Fetch Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FETCH_INFO_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.egerrit.internal.model.impl.SubmitInfoImpl <em>Submit Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.egerrit.internal.model.impl.SubmitInfoImpl
	 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getSubmitInfo()
	 * @generated
	 */
	int SUBMIT_INFO = 2;

	/**
	 * The feature id for the '<em><b>Status</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBMIT_INFO__STATUS = 0;

	/**
	 * The feature id for the '<em><b>On behalf of</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBMIT_INFO__ON_BEHALF_OF = 1;

	/**
	 * The number of structural features of the '<em>Submit Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBMIT_INFO_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Submit Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBMIT_INFO_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.egerrit.internal.model.impl.StringToStringImpl <em>String To String</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.egerrit.internal.model.impl.StringToStringImpl
	 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getStringToString()
	 * @generated
	 */
	int STRING_TO_STRING = 3;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_STRING__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_STRING__VALUE = 1;

	/**
	 * The number of structural features of the '<em>String To String</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_STRING_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>String To String</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_STRING_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.egerrit.internal.model.impl.ProjectAccessInfoImpl <em>Project Access Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.egerrit.internal.model.impl.ProjectAccessInfoImpl
	 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getProjectAccessInfo()
	 * @generated
	 */
	int PROJECT_ACCESS_INFO = 4;

	/**
	 * The feature id for the '<em><b>Revision</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_ACCESS_INFO__REVISION = 0;

	/**
	 * The feature id for the '<em><b>Inherits from</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_ACCESS_INFO__INHERITS_FROM = 1;

	/**
	 * The feature id for the '<em><b>Is owner</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_ACCESS_INFO__IS_OWNER = 2;

	/**
	 * The feature id for the '<em><b>Owner Of</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_ACCESS_INFO__OWNER_OF = 3;

	/**
	 * The feature id for the '<em><b>Can upload</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_ACCESS_INFO__CAN_UPLOAD = 4;

	/**
	 * The feature id for the '<em><b>Can add</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_ACCESS_INFO__CAN_ADD = 5;

	/**
	 * The feature id for the '<em><b>Config visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_ACCESS_INFO__CONFIG_VISIBLE = 6;

	/**
	 * The number of structural features of the '<em>Project Access Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_ACCESS_INFO_FEATURE_COUNT = 7;

	/**
	 * The number of operations of the '<em>Project Access Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_ACCESS_INFO_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.egerrit.internal.model.impl.CommentRangeImpl <em>Comment Range</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.egerrit.internal.model.impl.CommentRangeImpl
	 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getCommentRange()
	 * @generated
	 */
	int COMMENT_RANGE = 5;

	/**
	 * The feature id for the '<em><b>Start Line</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT_RANGE__START_LINE = 0;

	/**
	 * The feature id for the '<em><b>Start Character</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT_RANGE__START_CHARACTER = 1;

	/**
	 * The feature id for the '<em><b>End Line</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT_RANGE__END_LINE = 2;

	/**
	 * The feature id for the '<em><b>End Character</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT_RANGE__END_CHARACTER = 3;

	/**
	 * The number of structural features of the '<em>Comment Range</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT_RANGE_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Comment Range</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT_RANGE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.egerrit.internal.model.impl.ActionInfoImpl <em>Action Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.egerrit.internal.model.impl.ActionInfoImpl
	 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getActionInfo()
	 * @generated
	 */
	int ACTION_INFO = 6;

	/**
	 * The feature id for the '<em><b>Method</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_INFO__METHOD = 0;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_INFO__LABEL = 1;

	/**
	 * The feature id for the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_INFO__TITLE = 2;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_INFO__ENABLED = 3;

	/**
	 * The number of structural features of the '<em>Action Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_INFO_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Action Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_INFO_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.egerrit.internal.model.impl.CommentInfoImpl <em>Comment Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.egerrit.internal.model.impl.CommentInfoImpl
	 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getCommentInfo()
	 * @generated
	 */
	int COMMENT_INFO = 7;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT_INFO__ID = 0;

	/**
	 * The feature id for the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT_INFO__PATH = 1;

	/**
	 * The feature id for the '<em><b>Side</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT_INFO__SIDE = 2;

	/**
	 * The feature id for the '<em><b>Line</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT_INFO__LINE = 3;

	/**
	 * The feature id for the '<em><b>Range</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT_INFO__RANGE = 4;

	/**
	 * The feature id for the '<em><b>In Reply To</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT_INFO__IN_REPLY_TO = 5;

	/**
	 * The feature id for the '<em><b>Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT_INFO__MESSAGE = 6;

	/**
	 * The feature id for the '<em><b>Updated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT_INFO__UPDATED = 7;

	/**
	 * The feature id for the '<em><b>Author</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT_INFO__AUTHOR = 8;

	/**
	 * The number of structural features of the '<em>Comment Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT_INFO_FEATURE_COUNT = 9;

	/**
	 * The number of operations of the '<em>Comment Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT_INFO_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.egerrit.internal.model.impl.StringToRevisionInfoImpl <em>String To Revision Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.egerrit.internal.model.impl.StringToRevisionInfoImpl
	 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getStringToRevisionInfo()
	 * @generated
	 */
	int STRING_TO_REVISION_INFO = 8;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_REVISION_INFO__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_REVISION_INFO__VALUE = 1;

	/**
	 * The number of structural features of the '<em>String To Revision Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_REVISION_INFO_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>String To Revision Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_REVISION_INFO_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.egerrit.internal.model.impl.ReviewInfoImpl <em>Review Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.egerrit.internal.model.impl.ReviewInfoImpl
	 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getReviewInfo()
	 * @generated
	 */
	int REVIEW_INFO = 9;

	/**
	 * The feature id for the '<em><b>Labels</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVIEW_INFO__LABELS = 0;

	/**
	 * The number of structural features of the '<em>Review Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVIEW_INFO_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Review Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVIEW_INFO_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.egerrit.internal.model.impl.LabelInfoImpl <em>Label Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.egerrit.internal.model.impl.LabelInfoImpl
	 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getLabelInfo()
	 * @generated
	 */
	int LABEL_INFO = 10;

	/**
	 * The feature id for the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_INFO__OPTIONAL = 0;

	/**
	 * The feature id for the '<em><b>Approved</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_INFO__APPROVED = 1;

	/**
	 * The feature id for the '<em><b>Rejected</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_INFO__REJECTED = 2;

	/**
	 * The feature id for the '<em><b>Recommended</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_INFO__RECOMMENDED = 3;

	/**
	 * The feature id for the '<em><b>Disliked</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_INFO__DISLIKED = 4;

	/**
	 * The feature id for the '<em><b>Blocking</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_INFO__BLOCKING = 5;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_INFO__VALUE = 6;

	/**
	 * The feature id for the '<em><b>Default value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_INFO__DEFAULT_VALUE = 7;

	/**
	 * The feature id for the '<em><b>All</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_INFO__ALL = 8;

	/**
	 * The feature id for the '<em><b>Values</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_INFO__VALUES = 9;

	/**
	 * The number of structural features of the '<em>Label Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_INFO_FEATURE_COUNT = 10;

	/**
	 * The number of operations of the '<em>Label Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_INFO_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.egerrit.internal.model.impl.ApprovalInfoImpl <em>Approval Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.egerrit.internal.model.impl.ApprovalInfoImpl
	 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getApprovalInfo()
	 * @generated
	 */
	int APPROVAL_INFO = 11;

	/**
	 * The meta object id for the '{@link org.eclipse.egerrit.internal.model.impl.RelatedChangesInfoImpl <em>Related Changes Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.egerrit.internal.model.impl.RelatedChangesInfoImpl
	 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getRelatedChangesInfo()
	 * @generated
	 */
	int RELATED_CHANGES_INFO = 12;

	/**
	 * The meta object id for the '{@link org.eclipse.egerrit.internal.model.impl.FileInfoImpl <em>File Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.egerrit.internal.model.impl.FileInfoImpl
	 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getFileInfo()
	 * @generated
	 */
	int FILE_INFO = 13;

	/**
	 * The meta object id for the '{@link org.eclipse.egerrit.internal.model.impl.MergeableInfoImpl <em>Mergeable Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.egerrit.internal.model.impl.MergeableInfoImpl
	 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getMergeableInfo()
	 * @generated
	 */
	int MERGEABLE_INFO = 14;

	/**
	 * The meta object id for the '{@link org.eclipse.egerrit.internal.model.impl.ProjectInfoImpl <em>Project Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.egerrit.internal.model.impl.ProjectInfoImpl
	 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getProjectInfo()
	 * @generated
	 */
	int PROJECT_INFO = 15;

	/**
	 * The meta object id for the '{@link org.eclipse.egerrit.internal.model.impl.CommitInfoImpl <em>Commit Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.egerrit.internal.model.impl.CommitInfoImpl
	 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getCommitInfo()
	 * @generated
	 */
	int COMMIT_INFO = 16;

	/**
	 * The meta object id for the '{@link org.eclipse.egerrit.internal.model.impl.AccountInfoImpl <em>Account Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.egerrit.internal.model.impl.AccountInfoImpl
	 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getAccountInfo()
	 * @generated
	 */
	int ACCOUNT_INFO = 17;

	/**
	 * The feature id for the '<em><b>account id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCOUNT_INFO__ACCOUNT_ID = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCOUNT_INFO__NAME = 1;

	/**
	 * The feature id for the '<em><b>Email</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCOUNT_INFO__EMAIL = 2;

	/**
	 * The feature id for the '<em><b>Username</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCOUNT_INFO__USERNAME = 3;

	/**
	 * The number of structural features of the '<em>Account Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCOUNT_INFO_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Account Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCOUNT_INFO_OPERATION_COUNT = 0;

	/**
	 * The feature id for the '<em><b>account id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPROVAL_INFO__ACCOUNT_ID = ACCOUNT_INFO__ACCOUNT_ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPROVAL_INFO__NAME = ACCOUNT_INFO__NAME;

	/**
	 * The feature id for the '<em><b>Email</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPROVAL_INFO__EMAIL = ACCOUNT_INFO__EMAIL;

	/**
	 * The feature id for the '<em><b>Username</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPROVAL_INFO__USERNAME = ACCOUNT_INFO__USERNAME;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPROVAL_INFO__VALUE = ACCOUNT_INFO_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPROVAL_INFO__DATE = ACCOUNT_INFO_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Approval Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPROVAL_INFO_FEATURE_COUNT = ACCOUNT_INFO_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Approval Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPROVAL_INFO_OPERATION_COUNT = ACCOUNT_INFO_OPERATION_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Changes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_CHANGES_INFO__CHANGES = 0;

	/**
	 * The number of structural features of the '<em>Related Changes Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_CHANGES_INFO_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Related Changes Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_CHANGES_INFO_OPERATION_COUNT = 0;

	/**
	 * The feature id for the '<em><b>Status</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_INFO__STATUS = 0;

	/**
	 * The feature id for the '<em><b>Binary</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_INFO__BINARY = 1;

	/**
	 * The feature id for the '<em><b>Old path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_INFO__OLD_PATH = 2;

	/**
	 * The feature id for the '<em><b>Lines inserted</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_INFO__LINES_INSERTED = 3;

	/**
	 * The feature id for the '<em><b>Lines deleted</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_INFO__LINES_DELETED = 4;

	/**
	 * The feature id for the '<em><b>Comments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_INFO__COMMENTS = 5;

	/**
	 * The feature id for the '<em><b>Draft Comments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_INFO__DRAFT_COMMENTS = 6;

	/**
	 * The feature id for the '<em><b>Reviewed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_INFO__REVIEWED = 7;

	/**
	 * The feature id for the '<em><b>Comments Count</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_INFO__COMMENTS_COUNT = 8;

	/**
	 * The feature id for the '<em><b>Drafts Count</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_INFO__DRAFTS_COUNT = 9;

	/**
	 * The number of structural features of the '<em>File Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_INFO_FEATURE_COUNT = 10;

	/**
	 * The operation id for the '<em>Get Path</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_INFO___GET_PATH = 0;

	/**
	 * The operation id for the '<em>Get Revision</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_INFO___GET_REVISION = 1;

	/**
	 * The operation id for the '<em>Get All Comments</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_INFO___GET_ALL_COMMENTS = 2;

	/**
	 * The number of operations of the '<em>File Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_INFO_OPERATION_COUNT = 3;

	/**
	 * The feature id for the '<em><b>Submit type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGEABLE_INFO__SUBMIT_TYPE = 0;

	/**
	 * The feature id for the '<em><b>Mergeable into</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGEABLE_INFO__MERGEABLE_INTO = 1;

	/**
	 * The feature id for the '<em><b>Mergeable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGEABLE_INFO__MERGEABLE = 2;

	/**
	 * The number of structural features of the '<em>Mergeable Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGEABLE_INFO_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Mergeable Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGEABLE_INFO_OPERATION_COUNT = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_INFO__ID = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_INFO__NAME = 1;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_INFO__PARENT = 2;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_INFO__DESCRIPTION = 3;

	/**
	 * The feature id for the '<em><b>State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_INFO__STATE = 4;

	/**
	 * The feature id for the '<em><b>Branches</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_INFO__BRANCHES = 5;

	/**
	 * The number of structural features of the '<em>Project Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_INFO_FEATURE_COUNT = 6;

	/**
	 * The number of operations of the '<em>Project Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_INFO_OPERATION_COUNT = 0;

	/**
	 * The feature id for the '<em><b>Commit</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMIT_INFO__COMMIT = 0;

	/**
	 * The feature id for the '<em><b>Parents</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMIT_INFO__PARENTS = 1;

	/**
	 * The feature id for the '<em><b>Author</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMIT_INFO__AUTHOR = 2;

	/**
	 * The feature id for the '<em><b>Committer</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMIT_INFO__COMMITTER = 3;

	/**
	 * The feature id for the '<em><b>Subject</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMIT_INFO__SUBJECT = 4;

	/**
	 * The feature id for the '<em><b>Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMIT_INFO__MESSAGE = 5;

	/**
	 * The number of structural features of the '<em>Commit Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMIT_INFO_FEATURE_COUNT = 6;

	/**
	 * The number of operations of the '<em>Commit Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMIT_INFO_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.egerrit.internal.model.impl.ReviewerInfoImpl <em>Reviewer Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.egerrit.internal.model.impl.ReviewerInfoImpl
	 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getReviewerInfo()
	 * @generated
	 */
	int REVIEWER_INFO = 18;

	/**
	 * The feature id for the '<em><b>account id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVIEWER_INFO__ACCOUNT_ID = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVIEWER_INFO__NAME = 1;

	/**
	 * The feature id for the '<em><b>Email</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVIEWER_INFO__EMAIL = 2;

	/**
	 * The feature id for the '<em><b>Username</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVIEWER_INFO__USERNAME = 3;

	/**
	 * The feature id for the '<em><b>Approvals</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVIEWER_INFO__APPROVALS = 4;

	/**
	 * The number of structural features of the '<em>Reviewer Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVIEWER_INFO_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Reviewer Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVIEWER_INFO_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.egerrit.internal.model.impl.GitPersonInfoImpl <em>Git Person Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.egerrit.internal.model.impl.GitPersonInfoImpl
	 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getGitPersonInfo()
	 * @generated
	 */
	int GIT_PERSON_INFO = 19;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GIT_PERSON_INFO__NAME = 0;

	/**
	 * The feature id for the '<em><b>Email</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GIT_PERSON_INFO__EMAIL = 1;

	/**
	 * The feature id for the '<em><b>Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GIT_PERSON_INFO__DATE = 2;

	/**
	 * The feature id for the '<em><b>Tz</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GIT_PERSON_INFO__TZ = 3;

	/**
	 * The number of structural features of the '<em>Git Person Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GIT_PERSON_INFO_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Git Person Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GIT_PERSON_INFO_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.egerrit.internal.model.impl.IncludedInInfoImpl <em>Included In Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.egerrit.internal.model.impl.IncludedInInfoImpl
	 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getIncludedInInfo()
	 * @generated
	 */
	int INCLUDED_IN_INFO = 20;

	/**
	 * The feature id for the '<em><b>Branches</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INCLUDED_IN_INFO__BRANCHES = 0;

	/**
	 * The feature id for the '<em><b>Tags</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INCLUDED_IN_INFO__TAGS = 1;

	/**
	 * The number of structural features of the '<em>Included In Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INCLUDED_IN_INFO_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Included In Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INCLUDED_IN_INFO_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.egerrit.internal.model.impl.StringToFileInfoImpl <em>String To File Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.egerrit.internal.model.impl.StringToFileInfoImpl
	 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getStringToFileInfo()
	 * @generated
	 */
	int STRING_TO_FILE_INFO = 21;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_FILE_INFO__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_FILE_INFO__VALUE = 1;

	/**
	 * The number of structural features of the '<em>String To File Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_FILE_INFO_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>String To File Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_FILE_INFO_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.egerrit.internal.model.impl.StringToLabelInfoImpl <em>String To Label Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.egerrit.internal.model.impl.StringToLabelInfoImpl
	 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getStringToLabelInfo()
	 * @generated
	 */
	int STRING_TO_LABEL_INFO = 22;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_LABEL_INFO__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_LABEL_INFO__VALUE = 1;

	/**
	 * The number of structural features of the '<em>String To Label Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_LABEL_INFO_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>String To Label Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_LABEL_INFO_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.egerrit.internal.model.impl.BranchInfoImpl <em>Branch Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.egerrit.internal.model.impl.BranchInfoImpl
	 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getBranchInfo()
	 * @generated
	 */
	int BRANCH_INFO = 23;

	/**
	 * The feature id for the '<em><b>Ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BRANCH_INFO__REF = 0;

	/**
	 * The feature id for the '<em><b>Revision</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BRANCH_INFO__REVISION = 1;

	/**
	 * The feature id for the '<em><b>Can delete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BRANCH_INFO__CAN_DELETE = 2;

	/**
	 * The number of structural features of the '<em>Branch Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BRANCH_INFO_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Branch Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BRANCH_INFO_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.egerrit.internal.model.impl.ChangeInfoImpl <em>Change Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.egerrit.internal.model.impl.ChangeInfoImpl
	 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getChangeInfo()
	 * @generated
	 */
	int CHANGE_INFO = 24;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_INFO__KIND = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_INFO__ID = 1;

	/**
	 * The feature id for the '<em><b>Project</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_INFO__PROJECT = 2;

	/**
	 * The feature id for the '<em><b>Branch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_INFO__BRANCH = 3;

	/**
	 * The feature id for the '<em><b>Topic</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_INFO__TOPIC = 4;

	/**
	 * The feature id for the '<em><b>Change id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_INFO__CHANGE_ID = 5;

	/**
	 * The feature id for the '<em><b>Subject</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_INFO__SUBJECT = 6;

	/**
	 * The feature id for the '<em><b>Status</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_INFO__STATUS = 7;

	/**
	 * The feature id for the '<em><b>Created</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_INFO__CREATED = 8;

	/**
	 * The feature id for the '<em><b>Updated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_INFO__UPDATED = 9;

	/**
	 * The feature id for the '<em><b>Starred</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_INFO__STARRED = 10;

	/**
	 * The feature id for the '<em><b>Reviewed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_INFO__REVIEWED = 11;

	/**
	 * The feature id for the '<em><b>Mergeable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_INFO__MERGEABLE = 12;

	/**
	 * The feature id for the '<em><b>Insertions</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_INFO__INSERTIONS = 13;

	/**
	 * The feature id for the '<em><b>Deletions</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_INFO__DELETIONS = 14;

	/**
	 * The feature id for the '<em><b>sortkey</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_INFO__SORTKEY = 15;

	/**
	 * The feature id for the '<em><b>number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_INFO__NUMBER = 16;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_INFO__OWNER = 17;

	/**
	 * The feature id for the '<em><b>Actions</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_INFO__ACTIONS = 18;

	/**
	 * The feature id for the '<em><b>Labels</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_INFO__LABELS = 19;

	/**
	 * The feature id for the '<em><b>Permitted labels</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_INFO__PERMITTED_LABELS = 20;

	/**
	 * The feature id for the '<em><b>Removable reviewers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_INFO__REMOVABLE_REVIEWERS = 21;

	/**
	 * The feature id for the '<em><b>Messages</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_INFO__MESSAGES = 22;

	/**
	 * The feature id for the '<em><b>Current revision</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_INFO__CURRENT_REVISION = 23;

	/**
	 * The feature id for the '<em><b>Revisions</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_INFO__REVISIONS = 24;

	/**
	 * The feature id for the '<em><b>more changes</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_INFO__MORE_CHANGES = 25;

	/**
	 * The feature id for the '<em><b>Problems</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_INFO__PROBLEMS = 26;

	/**
	 * The feature id for the '<em><b>Base change</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_INFO__BASE_CHANGE = 27;

	/**
	 * The feature id for the '<em><b>Included In</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_INFO__INCLUDED_IN = 28;

	/**
	 * The feature id for the '<em><b>Hashtags</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_INFO__HASHTAGS = 29;

	/**
	 * The feature id for the '<em><b>Related Changes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_INFO__RELATED_CHANGES = 30;

	/**
	 * The feature id for the '<em><b>Reviewers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_INFO__REVIEWERS = 31;

	/**
	 * The feature id for the '<em><b>Same Topic</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_INFO__SAME_TOPIC = 32;

	/**
	 * The feature id for the '<em><b>Conflicts With</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_INFO__CONFLICTS_WITH = 33;

	/**
	 * The feature id for the '<em><b>Mergeable Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_INFO__MERGEABLE_INFO = 34;

	/**
	 * The feature id for the '<em><b>Revision</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_INFO__REVISION = 35;

	/**
	 * The feature id for the '<em><b>Latest Patch Set</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_INFO__LATEST_PATCH_SET = 36;

	/**
	 * The feature id for the '<em><b>User Selected Revision</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_INFO__USER_SELECTED_REVISION = 37;

	/**
	 * The feature id for the '<em><b>Revertable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_INFO__REVERTABLE = 38;

	/**
	 * The feature id for the '<em><b>Abandonable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_INFO__ABANDONABLE = 39;

	/**
	 * The feature id for the '<em><b>Restoreable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_INFO__RESTOREABLE = 40;

	/**
	 * The feature id for the '<em><b>Deleteable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_INFO__DELETEABLE = 41;

	/**
	 * The number of structural features of the '<em>Change Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_INFO_FEATURE_COUNT = 42;

	/**
	 * The operation id for the '<em>Get Revision By Number</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_INFO___GET_REVISION_BY_NUMBER__INT = 0;

	/**
	 * The operation id for the '<em>Is Action Allowed</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_INFO___IS_ACTION_ALLOWED__STRING = 1;

	/**
	 * The number of operations of the '<em>Change Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_INFO_OPERATION_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.egerrit.internal.model.impl.ProblemInfoImpl <em>Problem Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.egerrit.internal.model.impl.ProblemInfoImpl
	 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getProblemInfo()
	 * @generated
	 */
	int PROBLEM_INFO = 25;

	/**
	 * The feature id for the '<em><b>Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBLEM_INFO__MESSAGE = 0;

	/**
	 * The feature id for the '<em><b>Status</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBLEM_INFO__STATUS = 1;

	/**
	 * The feature id for the '<em><b>Outcome</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBLEM_INFO__OUTCOME = 2;

	/**
	 * The number of structural features of the '<em>Problem Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBLEM_INFO_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Problem Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBLEM_INFO_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.egerrit.internal.model.impl.ChangeMessageInfoImpl <em>Change Message Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.egerrit.internal.model.impl.ChangeMessageInfoImpl
	 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getChangeMessageInfo()
	 * @generated
	 */
	int CHANGE_MESSAGE_INFO = 26;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_MESSAGE_INFO__ID = 0;

	/**
	 * The feature id for the '<em><b>Author</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_MESSAGE_INFO__AUTHOR = 1;

	/**
	 * The feature id for the '<em><b>Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_MESSAGE_INFO__DATE = 2;

	/**
	 * The feature id for the '<em><b>Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_MESSAGE_INFO__MESSAGE = 3;

	/**
	 * The feature id for the '<em><b>revision number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_MESSAGE_INFO__REVISION_NUMBER = 4;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_MESSAGE_INFO__COMMENT = 5;

	/**
	 * The number of structural features of the '<em>Change Message Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_MESSAGE_INFO_FEATURE_COUNT = 6;

	/**
	 * The number of operations of the '<em>Change Message Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_MESSAGE_INFO_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.egerrit.internal.model.impl.StringToActionInfoImpl <em>String To Action Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.egerrit.internal.model.impl.StringToActionInfoImpl
	 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getStringToActionInfo()
	 * @generated
	 */
	int STRING_TO_ACTION_INFO = 27;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_ACTION_INFO__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_ACTION_INFO__VALUE = 1;

	/**
	 * The number of structural features of the '<em>String To Action Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_ACTION_INFO_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>String To Action Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_ACTION_INFO_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.egerrit.internal.model.impl.StringToListOfStringImpl <em>String To List Of String</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.egerrit.internal.model.impl.StringToListOfStringImpl
	 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getStringToListOfString()
	 * @generated
	 */
	int STRING_TO_LIST_OF_STRING = 28;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_LIST_OF_STRING__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_LIST_OF_STRING__VALUE = 1;

	/**
	 * The number of structural features of the '<em>String To List Of String</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_LIST_OF_STRING_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>String To List Of String</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_LIST_OF_STRING_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.egerrit.internal.model.impl.StringToFetchInfoImpl <em>String To Fetch Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.egerrit.internal.model.impl.StringToFetchInfoImpl
	 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getStringToFetchInfo()
	 * @generated
	 */
	int STRING_TO_FETCH_INFO = 29;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_FETCH_INFO__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_FETCH_INFO__VALUE = 1;

	/**
	 * The number of structural features of the '<em>String To Fetch Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_FETCH_INFO_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>String To Fetch Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_FETCH_INFO_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.egerrit.internal.model.impl.RevisionInfoImpl <em>Revision Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.egerrit.internal.model.impl.RevisionInfoImpl
	 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getRevisionInfo()
	 * @generated
	 */
	int REVISION_INFO = 30;

	/**
	 * The feature id for the '<em><b>Draft</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVISION_INFO__DRAFT = 0;

	/**
	 * The feature id for the '<em><b>Has draft comments</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVISION_INFO__HAS_DRAFT_COMMENTS = 1;

	/**
	 * The feature id for the '<em><b>number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVISION_INFO__NUMBER = 2;

	/**
	 * The feature id for the '<em><b>Ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVISION_INFO__REF = 3;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVISION_INFO__FETCH = 4;

	/**
	 * The feature id for the '<em><b>Commit</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVISION_INFO__COMMIT = 5;

	/**
	 * The feature id for the '<em><b>Files</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVISION_INFO__FILES = 6;

	/**
	 * The feature id for the '<em><b>Actions</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVISION_INFO__ACTIONS = 7;

	/**
	 * The feature id for the '<em><b>Reviewed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVISION_INFO__REVIEWED = 8;

	/**
	 * The feature id for the '<em><b>Comments Loaded</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVISION_INFO__COMMENTS_LOADED = 9;

	/**
	 * The feature id for the '<em><b>Submitable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVISION_INFO__SUBMITABLE = 10;

	/**
	 * The feature id for the '<em><b>Rebaseable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVISION_INFO__REBASEABLE = 11;

	/**
	 * The feature id for the '<em><b>Cherrypickable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVISION_INFO__CHERRYPICKABLE = 12;

	/**
	 * The feature id for the '<em><b>Deleteable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVISION_INFO__DELETEABLE = 13;

	/**
	 * The feature id for the '<em><b>Publishable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVISION_INFO__PUBLISHABLE = 14;

	/**
	 * The feature id for the '<em><b>Files Loaded</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVISION_INFO__FILES_LOADED = 15;

	/**
	 * The feature id for the '<em><b>Commented</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVISION_INFO__COMMENTED = 16;

	/**
	 * The number of structural features of the '<em>Revision Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVISION_INFO_FEATURE_COUNT = 17;

	/**
	 * The operation id for the '<em>Is Action Allowed</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVISION_INFO___IS_ACTION_ALLOWED__STRING = 0;

	/**
	 * The operation id for the '<em>Get Id</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVISION_INFO___GET_ID = 1;

	/**
	 * The operation id for the '<em>Get Change Info</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVISION_INFO___GET_CHANGE_INFO = 2;

	/**
	 * The number of operations of the '<em>Revision Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVISION_INFO_OPERATION_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.egerrit.internal.model.impl.SuggestReviewerInfoImpl <em>Suggest Reviewer Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.egerrit.internal.model.impl.SuggestReviewerInfoImpl
	 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getSuggestReviewerInfo()
	 * @generated
	 */
	int SUGGEST_REVIEWER_INFO = 31;

	/**
	 * The feature id for the '<em><b>Account</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUGGEST_REVIEWER_INFO__ACCOUNT = 0;

	/**
	 * The feature id for the '<em><b>Group</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUGGEST_REVIEWER_INFO__GROUP = 1;

	/**
	 * The number of structural features of the '<em>Suggest Reviewer Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUGGEST_REVIEWER_INFO_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Suggest Reviewer Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUGGEST_REVIEWER_INFO_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.egerrit.internal.model.impl.GroupBaseInfoImpl <em>Group Base Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.egerrit.internal.model.impl.GroupBaseInfoImpl
	 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getGroupBaseInfo()
	 * @generated
	 */
	int GROUP_BASE_INFO = 32;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP_BASE_INFO__NAME = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP_BASE_INFO__ID = 1;

	/**
	 * The number of structural features of the '<em>Group Base Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP_BASE_INFO_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Group Base Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP_BASE_INFO_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.egerrit.internal.model.impl.ReviewsImpl <em>Reviews</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.egerrit.internal.model.impl.ReviewsImpl
	 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getReviews()
	 * @generated
	 */
	int REVIEWS = 33;

	/**
	 * The feature id for the '<em><b>All Reviews</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVIEWS__ALL_REVIEWS = 0;

	/**
	 * The number of structural features of the '<em>Reviews</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVIEWS_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Reviews</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVIEWS_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.egerrit.internal.model.ActionConstants <em>Action Constants</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.egerrit.internal.model.ActionConstants
	 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getActionConstants()
	 * @generated
	 */
	int ACTION_CONSTANTS = 34;

	/**
	 * Returns the meta object for class '{@link org.eclipse.egerrit.internal.model.RelatedChangeAndCommitInfo <em>Related Change And Commit Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Related Change And Commit Info</em>'.
	 * @see org.eclipse.egerrit.internal.model.RelatedChangeAndCommitInfo
	 * @generated
	 */
	EClass getRelatedChangeAndCommitInfo();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.RelatedChangeAndCommitInfo#getChange_id <em>Change id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Change id</em>'.
	 * @see org.eclipse.egerrit.internal.model.RelatedChangeAndCommitInfo#getChange_id()
	 * @see #getRelatedChangeAndCommitInfo()
	 * @generated
	 */
	EAttribute getRelatedChangeAndCommitInfo_Change_id();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.egerrit.internal.model.RelatedChangeAndCommitInfo#getCommit <em>Commit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Commit</em>'.
	 * @see org.eclipse.egerrit.internal.model.RelatedChangeAndCommitInfo#getCommit()
	 * @see #getRelatedChangeAndCommitInfo()
	 * @generated
	 */
	EReference getRelatedChangeAndCommitInfo_Commit();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.RelatedChangeAndCommitInfo#get_change_number <em>change number</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>change number</em>'.
	 * @see org.eclipse.egerrit.internal.model.RelatedChangeAndCommitInfo#get_change_number()
	 * @see #getRelatedChangeAndCommitInfo()
	 * @generated
	 */
	EAttribute getRelatedChangeAndCommitInfo__change_number();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.RelatedChangeAndCommitInfo#get_revision_number <em>revision number</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>revision number</em>'.
	 * @see org.eclipse.egerrit.internal.model.RelatedChangeAndCommitInfo#get_revision_number()
	 * @see #getRelatedChangeAndCommitInfo()
	 * @generated
	 */
	EAttribute getRelatedChangeAndCommitInfo__revision_number();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.RelatedChangeAndCommitInfo#get_current_revision_number <em>current revision number</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>current revision number</em>'.
	 * @see org.eclipse.egerrit.internal.model.RelatedChangeAndCommitInfo#get_current_revision_number()
	 * @see #getRelatedChangeAndCommitInfo()
	 * @generated
	 */
	EAttribute getRelatedChangeAndCommitInfo__current_revision_number();

	/**
	 * Returns the meta object for class '{@link org.eclipse.egerrit.internal.model.FetchInfo <em>Fetch Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Fetch Info</em>'.
	 * @see org.eclipse.egerrit.internal.model.FetchInfo
	 * @generated
	 */
	EClass getFetchInfo();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.FetchInfo#getUrl <em>Url</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Url</em>'.
	 * @see org.eclipse.egerrit.internal.model.FetchInfo#getUrl()
	 * @see #getFetchInfo()
	 * @generated
	 */
	EAttribute getFetchInfo_Url();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.FetchInfo#getRef <em>Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ref</em>'.
	 * @see org.eclipse.egerrit.internal.model.FetchInfo#getRef()
	 * @see #getFetchInfo()
	 * @generated
	 */
	EAttribute getFetchInfo_Ref();

	/**
	 * Returns the meta object for the map '{@link org.eclipse.egerrit.internal.model.FetchInfo#getCommands <em>Commands</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Commands</em>'.
	 * @see org.eclipse.egerrit.internal.model.FetchInfo#getCommands()
	 * @see #getFetchInfo()
	 * @generated
	 */
	EReference getFetchInfo_Commands();

	/**
	 * Returns the meta object for class '{@link org.eclipse.egerrit.internal.model.SubmitInfo <em>Submit Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Submit Info</em>'.
	 * @see org.eclipse.egerrit.internal.model.SubmitInfo
	 * @generated
	 */
	EClass getSubmitInfo();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.SubmitInfo#getStatus <em>Status</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Status</em>'.
	 * @see org.eclipse.egerrit.internal.model.SubmitInfo#getStatus()
	 * @see #getSubmitInfo()
	 * @generated
	 */
	EAttribute getSubmitInfo_Status();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.SubmitInfo#getOn_behalf_of <em>On behalf of</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>On behalf of</em>'.
	 * @see org.eclipse.egerrit.internal.model.SubmitInfo#getOn_behalf_of()
	 * @see #getSubmitInfo()
	 * @generated
	 */
	EAttribute getSubmitInfo_On_behalf_of();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>String To String</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>String To String</em>'.
	 * @see java.util.Map.Entry
	 * @model keyDataType="org.eclipse.emf.ecore.EString"
	 *        valueDataType="org.eclipse.emf.ecore.EString"
	 * @generated
	 */
	EClass getStringToString();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getStringToString()
	 * @generated
	 */
	EAttribute getStringToString_Key();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getStringToString()
	 * @generated
	 */
	EAttribute getStringToString_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.egerrit.internal.model.ProjectAccessInfo <em>Project Access Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Project Access Info</em>'.
	 * @see org.eclipse.egerrit.internal.model.ProjectAccessInfo
	 * @generated
	 */
	EClass getProjectAccessInfo();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.ProjectAccessInfo#getRevision <em>Revision</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Revision</em>'.
	 * @see org.eclipse.egerrit.internal.model.ProjectAccessInfo#getRevision()
	 * @see #getProjectAccessInfo()
	 * @generated
	 */
	EAttribute getProjectAccessInfo_Revision();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.egerrit.internal.model.ProjectAccessInfo#getInherits_from <em>Inherits from</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Inherits from</em>'.
	 * @see org.eclipse.egerrit.internal.model.ProjectAccessInfo#getInherits_from()
	 * @see #getProjectAccessInfo()
	 * @generated
	 */
	EReference getProjectAccessInfo_Inherits_from();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.ProjectAccessInfo#isIs_owner <em>Is owner</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is owner</em>'.
	 * @see org.eclipse.egerrit.internal.model.ProjectAccessInfo#isIs_owner()
	 * @see #getProjectAccessInfo()
	 * @generated
	 */
	EAttribute getProjectAccessInfo_Is_owner();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.egerrit.internal.model.ProjectAccessInfo#getOwnerOf <em>Owner Of</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Owner Of</em>'.
	 * @see org.eclipse.egerrit.internal.model.ProjectAccessInfo#getOwnerOf()
	 * @see #getProjectAccessInfo()
	 * @generated
	 */
	EAttribute getProjectAccessInfo_OwnerOf();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.ProjectAccessInfo#isCan_upload <em>Can upload</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Can upload</em>'.
	 * @see org.eclipse.egerrit.internal.model.ProjectAccessInfo#isCan_upload()
	 * @see #getProjectAccessInfo()
	 * @generated
	 */
	EAttribute getProjectAccessInfo_Can_upload();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.ProjectAccessInfo#isCan_add <em>Can add</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Can add</em>'.
	 * @see org.eclipse.egerrit.internal.model.ProjectAccessInfo#isCan_add()
	 * @see #getProjectAccessInfo()
	 * @generated
	 */
	EAttribute getProjectAccessInfo_Can_add();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.ProjectAccessInfo#isConfig_visible <em>Config visible</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Config visible</em>'.
	 * @see org.eclipse.egerrit.internal.model.ProjectAccessInfo#isConfig_visible()
	 * @see #getProjectAccessInfo()
	 * @generated
	 */
	EAttribute getProjectAccessInfo_Config_visible();

	/**
	 * Returns the meta object for class '{@link org.eclipse.egerrit.internal.model.CommentRange <em>Comment Range</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Comment Range</em>'.
	 * @see org.eclipse.egerrit.internal.model.CommentRange
	 * @generated
	 */
	EClass getCommentRange();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.CommentRange#getStartLine <em>Start Line</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Start Line</em>'.
	 * @see org.eclipse.egerrit.internal.model.CommentRange#getStartLine()
	 * @see #getCommentRange()
	 * @generated
	 */
	EAttribute getCommentRange_StartLine();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.CommentRange#getStartCharacter <em>Start Character</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Start Character</em>'.
	 * @see org.eclipse.egerrit.internal.model.CommentRange#getStartCharacter()
	 * @see #getCommentRange()
	 * @generated
	 */
	EAttribute getCommentRange_StartCharacter();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.CommentRange#getEndLine <em>End Line</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>End Line</em>'.
	 * @see org.eclipse.egerrit.internal.model.CommentRange#getEndLine()
	 * @see #getCommentRange()
	 * @generated
	 */
	EAttribute getCommentRange_EndLine();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.CommentRange#getEndCharacter <em>End Character</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>End Character</em>'.
	 * @see org.eclipse.egerrit.internal.model.CommentRange#getEndCharacter()
	 * @see #getCommentRange()
	 * @generated
	 */
	EAttribute getCommentRange_EndCharacter();

	/**
	 * Returns the meta object for class '{@link org.eclipse.egerrit.internal.model.ActionInfo <em>Action Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Action Info</em>'.
	 * @see org.eclipse.egerrit.internal.model.ActionInfo
	 * @generated
	 */
	EClass getActionInfo();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.ActionInfo#getMethod <em>Method</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Method</em>'.
	 * @see org.eclipse.egerrit.internal.model.ActionInfo#getMethod()
	 * @see #getActionInfo()
	 * @generated
	 */
	EAttribute getActionInfo_Method();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.ActionInfo#getLabel <em>Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Label</em>'.
	 * @see org.eclipse.egerrit.internal.model.ActionInfo#getLabel()
	 * @see #getActionInfo()
	 * @generated
	 */
	EAttribute getActionInfo_Label();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.ActionInfo#getTitle <em>Title</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Title</em>'.
	 * @see org.eclipse.egerrit.internal.model.ActionInfo#getTitle()
	 * @see #getActionInfo()
	 * @generated
	 */
	EAttribute getActionInfo_Title();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.ActionInfo#isEnabled <em>Enabled</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Enabled</em>'.
	 * @see org.eclipse.egerrit.internal.model.ActionInfo#isEnabled()
	 * @see #getActionInfo()
	 * @generated
	 */
	EAttribute getActionInfo_Enabled();

	/**
	 * Returns the meta object for class '{@link org.eclipse.egerrit.internal.model.CommentInfo <em>Comment Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Comment Info</em>'.
	 * @see org.eclipse.egerrit.internal.model.CommentInfo
	 * @generated
	 */
	EClass getCommentInfo();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.CommentInfo#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.eclipse.egerrit.internal.model.CommentInfo#getId()
	 * @see #getCommentInfo()
	 * @generated
	 */
	EAttribute getCommentInfo_Id();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.CommentInfo#getPath <em>Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Path</em>'.
	 * @see org.eclipse.egerrit.internal.model.CommentInfo#getPath()
	 * @see #getCommentInfo()
	 * @generated
	 */
	EAttribute getCommentInfo_Path();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.CommentInfo#getSide <em>Side</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Side</em>'.
	 * @see org.eclipse.egerrit.internal.model.CommentInfo#getSide()
	 * @see #getCommentInfo()
	 * @generated
	 */
	EAttribute getCommentInfo_Side();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.CommentInfo#getLine <em>Line</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Line</em>'.
	 * @see org.eclipse.egerrit.internal.model.CommentInfo#getLine()
	 * @see #getCommentInfo()
	 * @generated
	 */
	EAttribute getCommentInfo_Line();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.egerrit.internal.model.CommentInfo#getRange <em>Range</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Range</em>'.
	 * @see org.eclipse.egerrit.internal.model.CommentInfo#getRange()
	 * @see #getCommentInfo()
	 * @generated
	 */
	EReference getCommentInfo_Range();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.CommentInfo#getInReplyTo <em>In Reply To</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>In Reply To</em>'.
	 * @see org.eclipse.egerrit.internal.model.CommentInfo#getInReplyTo()
	 * @see #getCommentInfo()
	 * @generated
	 */
	EAttribute getCommentInfo_InReplyTo();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.CommentInfo#getMessage <em>Message</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Message</em>'.
	 * @see org.eclipse.egerrit.internal.model.CommentInfo#getMessage()
	 * @see #getCommentInfo()
	 * @generated
	 */
	EAttribute getCommentInfo_Message();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.CommentInfo#getUpdated <em>Updated</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Updated</em>'.
	 * @see org.eclipse.egerrit.internal.model.CommentInfo#getUpdated()
	 * @see #getCommentInfo()
	 * @generated
	 */
	EAttribute getCommentInfo_Updated();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.egerrit.internal.model.CommentInfo#getAuthor <em>Author</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Author</em>'.
	 * @see org.eclipse.egerrit.internal.model.CommentInfo#getAuthor()
	 * @see #getCommentInfo()
	 * @generated
	 */
	EReference getCommentInfo_Author();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>String To Revision Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>String To Revision Info</em>'.
	 * @see java.util.Map.Entry
	 * @model keyDataType="org.eclipse.emf.ecore.EString"
	 *        valueType="org.eclipse.egerrit.internal.model.RevisionInfo" valueContainment="true"
	 * @generated
	 */
	EClass getStringToRevisionInfo();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getStringToRevisionInfo()
	 * @generated
	 */
	EAttribute getStringToRevisionInfo_Key();

	/**
	 * Returns the meta object for the containment reference '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getStringToRevisionInfo()
	 * @generated
	 */
	EReference getStringToRevisionInfo_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.egerrit.internal.model.ReviewInfo <em>Review Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Review Info</em>'.
	 * @see org.eclipse.egerrit.internal.model.ReviewInfo
	 * @generated
	 */
	EClass getReviewInfo();

	/**
	 * Returns the meta object for the map '{@link org.eclipse.egerrit.internal.model.ReviewInfo#getLabels <em>Labels</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Labels</em>'.
	 * @see org.eclipse.egerrit.internal.model.ReviewInfo#getLabels()
	 * @see #getReviewInfo()
	 * @generated
	 */
	EReference getReviewInfo_Labels();

	/**
	 * Returns the meta object for class '{@link org.eclipse.egerrit.internal.model.LabelInfo <em>Label Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Label Info</em>'.
	 * @see org.eclipse.egerrit.internal.model.LabelInfo
	 * @generated
	 */
	EClass getLabelInfo();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.LabelInfo#isOptional <em>Optional</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Optional</em>'.
	 * @see org.eclipse.egerrit.internal.model.LabelInfo#isOptional()
	 * @see #getLabelInfo()
	 * @generated
	 */
	EAttribute getLabelInfo_Optional();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.egerrit.internal.model.LabelInfo#getApproved <em>Approved</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Approved</em>'.
	 * @see org.eclipse.egerrit.internal.model.LabelInfo#getApproved()
	 * @see #getLabelInfo()
	 * @generated
	 */
	EReference getLabelInfo_Approved();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.egerrit.internal.model.LabelInfo#getRejected <em>Rejected</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Rejected</em>'.
	 * @see org.eclipse.egerrit.internal.model.LabelInfo#getRejected()
	 * @see #getLabelInfo()
	 * @generated
	 */
	EReference getLabelInfo_Rejected();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.egerrit.internal.model.LabelInfo#getRecommended <em>Recommended</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Recommended</em>'.
	 * @see org.eclipse.egerrit.internal.model.LabelInfo#getRecommended()
	 * @see #getLabelInfo()
	 * @generated
	 */
	EReference getLabelInfo_Recommended();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.egerrit.internal.model.LabelInfo#getDisliked <em>Disliked</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Disliked</em>'.
	 * @see org.eclipse.egerrit.internal.model.LabelInfo#getDisliked()
	 * @see #getLabelInfo()
	 * @generated
	 */
	EReference getLabelInfo_Disliked();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.LabelInfo#isBlocking <em>Blocking</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Blocking</em>'.
	 * @see org.eclipse.egerrit.internal.model.LabelInfo#isBlocking()
	 * @see #getLabelInfo()
	 * @generated
	 */
	EAttribute getLabelInfo_Blocking();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.LabelInfo#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.egerrit.internal.model.LabelInfo#getValue()
	 * @see #getLabelInfo()
	 * @generated
	 */
	EAttribute getLabelInfo_Value();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.LabelInfo#getDefault_value <em>Default value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default value</em>'.
	 * @see org.eclipse.egerrit.internal.model.LabelInfo#getDefault_value()
	 * @see #getLabelInfo()
	 * @generated
	 */
	EAttribute getLabelInfo_Default_value();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.egerrit.internal.model.LabelInfo#getAll <em>All</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>All</em>'.
	 * @see org.eclipse.egerrit.internal.model.LabelInfo#getAll()
	 * @see #getLabelInfo()
	 * @generated
	 */
	EReference getLabelInfo_All();

	/**
	 * Returns the meta object for the map '{@link org.eclipse.egerrit.internal.model.LabelInfo#getValues <em>Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Values</em>'.
	 * @see org.eclipse.egerrit.internal.model.LabelInfo#getValues()
	 * @see #getLabelInfo()
	 * @generated
	 */
	EReference getLabelInfo_Values();

	/**
	 * Returns the meta object for class '{@link org.eclipse.egerrit.internal.model.ApprovalInfo <em>Approval Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Approval Info</em>'.
	 * @see org.eclipse.egerrit.internal.model.ApprovalInfo
	 * @generated
	 */
	EClass getApprovalInfo();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.ApprovalInfo#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.egerrit.internal.model.ApprovalInfo#getValue()
	 * @see #getApprovalInfo()
	 * @generated
	 */
	EAttribute getApprovalInfo_Value();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.ApprovalInfo#getDate <em>Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Date</em>'.
	 * @see org.eclipse.egerrit.internal.model.ApprovalInfo#getDate()
	 * @see #getApprovalInfo()
	 * @generated
	 */
	EAttribute getApprovalInfo_Date();

	/**
	 * Returns the meta object for class '{@link org.eclipse.egerrit.internal.model.RelatedChangesInfo <em>Related Changes Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Related Changes Info</em>'.
	 * @see org.eclipse.egerrit.internal.model.RelatedChangesInfo
	 * @generated
	 */
	EClass getRelatedChangesInfo();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.egerrit.internal.model.RelatedChangesInfo#getChanges <em>Changes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Changes</em>'.
	 * @see org.eclipse.egerrit.internal.model.RelatedChangesInfo#getChanges()
	 * @see #getRelatedChangesInfo()
	 * @generated
	 */
	EReference getRelatedChangesInfo_Changes();

	/**
	 * Returns the meta object for class '{@link org.eclipse.egerrit.internal.model.FileInfo <em>File Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>File Info</em>'.
	 * @see org.eclipse.egerrit.internal.model.FileInfo
	 * @generated
	 */
	EClass getFileInfo();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.FileInfo#getStatus <em>Status</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Status</em>'.
	 * @see org.eclipse.egerrit.internal.model.FileInfo#getStatus()
	 * @see #getFileInfo()
	 * @generated
	 */
	EAttribute getFileInfo_Status();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.FileInfo#isBinary <em>Binary</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Binary</em>'.
	 * @see org.eclipse.egerrit.internal.model.FileInfo#isBinary()
	 * @see #getFileInfo()
	 * @generated
	 */
	EAttribute getFileInfo_Binary();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.FileInfo#getOld_path <em>Old path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Old path</em>'.
	 * @see org.eclipse.egerrit.internal.model.FileInfo#getOld_path()
	 * @see #getFileInfo()
	 * @generated
	 */
	EAttribute getFileInfo_Old_path();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.FileInfo#getLines_inserted <em>Lines inserted</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Lines inserted</em>'.
	 * @see org.eclipse.egerrit.internal.model.FileInfo#getLines_inserted()
	 * @see #getFileInfo()
	 * @generated
	 */
	EAttribute getFileInfo_Lines_inserted();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.FileInfo#getLines_deleted <em>Lines deleted</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Lines deleted</em>'.
	 * @see org.eclipse.egerrit.internal.model.FileInfo#getLines_deleted()
	 * @see #getFileInfo()
	 * @generated
	 */
	EAttribute getFileInfo_Lines_deleted();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.egerrit.internal.model.FileInfo#getComments <em>Comments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Comments</em>'.
	 * @see org.eclipse.egerrit.internal.model.FileInfo#getComments()
	 * @see #getFileInfo()
	 * @generated
	 */
	EReference getFileInfo_Comments();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.egerrit.internal.model.FileInfo#getDraftComments <em>Draft Comments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Draft Comments</em>'.
	 * @see org.eclipse.egerrit.internal.model.FileInfo#getDraftComments()
	 * @see #getFileInfo()
	 * @generated
	 */
	EReference getFileInfo_DraftComments();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.FileInfo#isReviewed <em>Reviewed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Reviewed</em>'.
	 * @see org.eclipse.egerrit.internal.model.FileInfo#isReviewed()
	 * @see #getFileInfo()
	 * @generated
	 */
	EAttribute getFileInfo_Reviewed();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.FileInfo#getCommentsCount <em>Comments Count</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Comments Count</em>'.
	 * @see org.eclipse.egerrit.internal.model.FileInfo#getCommentsCount()
	 * @see #getFileInfo()
	 * @generated
	 */
	EAttribute getFileInfo_CommentsCount();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.FileInfo#getDraftsCount <em>Drafts Count</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Drafts Count</em>'.
	 * @see org.eclipse.egerrit.internal.model.FileInfo#getDraftsCount()
	 * @see #getFileInfo()
	 * @generated
	 */
	EAttribute getFileInfo_DraftsCount();

	/**
	 * Returns the meta object for the '{@link org.eclipse.egerrit.internal.model.FileInfo#getPath() <em>Get Path</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Path</em>' operation.
	 * @see org.eclipse.egerrit.internal.model.FileInfo#getPath()
	 * @generated
	 */
	EOperation getFileInfo__GetPath();

	/**
	 * Returns the meta object for the '{@link org.eclipse.egerrit.internal.model.FileInfo#getRevision() <em>Get Revision</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Revision</em>' operation.
	 * @see org.eclipse.egerrit.internal.model.FileInfo#getRevision()
	 * @generated
	 */
	EOperation getFileInfo__GetRevision();

	/**
	 * Returns the meta object for the '{@link org.eclipse.egerrit.internal.model.FileInfo#getAllComments() <em>Get All Comments</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get All Comments</em>' operation.
	 * @see org.eclipse.egerrit.internal.model.FileInfo#getAllComments()
	 * @generated
	 */
	EOperation getFileInfo__GetAllComments();

	/**
	 * Returns the meta object for class '{@link org.eclipse.egerrit.internal.model.MergeableInfo <em>Mergeable Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Mergeable Info</em>'.
	 * @see org.eclipse.egerrit.internal.model.MergeableInfo
	 * @generated
	 */
	EClass getMergeableInfo();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.MergeableInfo#getSubmit_type <em>Submit type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Submit type</em>'.
	 * @see org.eclipse.egerrit.internal.model.MergeableInfo#getSubmit_type()
	 * @see #getMergeableInfo()
	 * @generated
	 */
	EAttribute getMergeableInfo_Submit_type();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.MergeableInfo#getMergeable_into <em>Mergeable into</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Mergeable into</em>'.
	 * @see org.eclipse.egerrit.internal.model.MergeableInfo#getMergeable_into()
	 * @see #getMergeableInfo()
	 * @generated
	 */
	EAttribute getMergeableInfo_Mergeable_into();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.MergeableInfo#isMergeable <em>Mergeable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Mergeable</em>'.
	 * @see org.eclipse.egerrit.internal.model.MergeableInfo#isMergeable()
	 * @see #getMergeableInfo()
	 * @generated
	 */
	EAttribute getMergeableInfo_Mergeable();

	/**
	 * Returns the meta object for class '{@link org.eclipse.egerrit.internal.model.ProjectInfo <em>Project Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Project Info</em>'.
	 * @see org.eclipse.egerrit.internal.model.ProjectInfo
	 * @generated
	 */
	EClass getProjectInfo();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.ProjectInfo#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.eclipse.egerrit.internal.model.ProjectInfo#getId()
	 * @see #getProjectInfo()
	 * @generated
	 */
	EAttribute getProjectInfo_Id();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.ProjectInfo#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.egerrit.internal.model.ProjectInfo#getName()
	 * @see #getProjectInfo()
	 * @generated
	 */
	EAttribute getProjectInfo_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.ProjectInfo#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Parent</em>'.
	 * @see org.eclipse.egerrit.internal.model.ProjectInfo#getParent()
	 * @see #getProjectInfo()
	 * @generated
	 */
	EAttribute getProjectInfo_Parent();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.ProjectInfo#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.egerrit.internal.model.ProjectInfo#getDescription()
	 * @see #getProjectInfo()
	 * @generated
	 */
	EAttribute getProjectInfo_Description();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.ProjectInfo#getState <em>State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>State</em>'.
	 * @see org.eclipse.egerrit.internal.model.ProjectInfo#getState()
	 * @see #getProjectInfo()
	 * @generated
	 */
	EAttribute getProjectInfo_State();

	/**
	 * Returns the meta object for the map '{@link org.eclipse.egerrit.internal.model.ProjectInfo#getBranches <em>Branches</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Branches</em>'.
	 * @see org.eclipse.egerrit.internal.model.ProjectInfo#getBranches()
	 * @see #getProjectInfo()
	 * @generated
	 */
	EReference getProjectInfo_Branches();

	/**
	 * Returns the meta object for class '{@link org.eclipse.egerrit.internal.model.CommitInfo <em>Commit Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Commit Info</em>'.
	 * @see org.eclipse.egerrit.internal.model.CommitInfo
	 * @generated
	 */
	EClass getCommitInfo();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.CommitInfo#getCommit <em>Commit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Commit</em>'.
	 * @see org.eclipse.egerrit.internal.model.CommitInfo#getCommit()
	 * @see #getCommitInfo()
	 * @generated
	 */
	EAttribute getCommitInfo_Commit();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.egerrit.internal.model.CommitInfo#getParents <em>Parents</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parents</em>'.
	 * @see org.eclipse.egerrit.internal.model.CommitInfo#getParents()
	 * @see #getCommitInfo()
	 * @generated
	 */
	EReference getCommitInfo_Parents();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.egerrit.internal.model.CommitInfo#getAuthor <em>Author</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Author</em>'.
	 * @see org.eclipse.egerrit.internal.model.CommitInfo#getAuthor()
	 * @see #getCommitInfo()
	 * @generated
	 */
	EReference getCommitInfo_Author();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.egerrit.internal.model.CommitInfo#getCommitter <em>Committer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Committer</em>'.
	 * @see org.eclipse.egerrit.internal.model.CommitInfo#getCommitter()
	 * @see #getCommitInfo()
	 * @generated
	 */
	EReference getCommitInfo_Committer();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.CommitInfo#getSubject <em>Subject</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Subject</em>'.
	 * @see org.eclipse.egerrit.internal.model.CommitInfo#getSubject()
	 * @see #getCommitInfo()
	 * @generated
	 */
	EAttribute getCommitInfo_Subject();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.CommitInfo#getMessage <em>Message</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Message</em>'.
	 * @see org.eclipse.egerrit.internal.model.CommitInfo#getMessage()
	 * @see #getCommitInfo()
	 * @generated
	 */
	EAttribute getCommitInfo_Message();

	/**
	 * Returns the meta object for class '{@link org.eclipse.egerrit.internal.model.AccountInfo <em>Account Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Account Info</em>'.
	 * @see org.eclipse.egerrit.internal.model.AccountInfo
	 * @generated
	 */
	EClass getAccountInfo();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.AccountInfo#get_account_id <em>account id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>account id</em>'.
	 * @see org.eclipse.egerrit.internal.model.AccountInfo#get_account_id()
	 * @see #getAccountInfo()
	 * @generated
	 */
	EAttribute getAccountInfo__account_id();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.AccountInfo#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.egerrit.internal.model.AccountInfo#getName()
	 * @see #getAccountInfo()
	 * @generated
	 */
	EAttribute getAccountInfo_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.AccountInfo#getEmail <em>Email</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Email</em>'.
	 * @see org.eclipse.egerrit.internal.model.AccountInfo#getEmail()
	 * @see #getAccountInfo()
	 * @generated
	 */
	EAttribute getAccountInfo_Email();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.AccountInfo#getUsername <em>Username</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Username</em>'.
	 * @see org.eclipse.egerrit.internal.model.AccountInfo#getUsername()
	 * @see #getAccountInfo()
	 * @generated
	 */
	EAttribute getAccountInfo_Username();

	/**
	 * Returns the meta object for class '{@link org.eclipse.egerrit.internal.model.ReviewerInfo <em>Reviewer Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Reviewer Info</em>'.
	 * @see org.eclipse.egerrit.internal.model.ReviewerInfo
	 * @generated
	 */
	EClass getReviewerInfo();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.ReviewerInfo#get_account_id <em>account id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>account id</em>'.
	 * @see org.eclipse.egerrit.internal.model.ReviewerInfo#get_account_id()
	 * @see #getReviewerInfo()
	 * @generated
	 */
	EAttribute getReviewerInfo__account_id();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.ReviewerInfo#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.egerrit.internal.model.ReviewerInfo#getName()
	 * @see #getReviewerInfo()
	 * @generated
	 */
	EAttribute getReviewerInfo_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.ReviewerInfo#getEmail <em>Email</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Email</em>'.
	 * @see org.eclipse.egerrit.internal.model.ReviewerInfo#getEmail()
	 * @see #getReviewerInfo()
	 * @generated
	 */
	EAttribute getReviewerInfo_Email();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.ReviewerInfo#getUsername <em>Username</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Username</em>'.
	 * @see org.eclipse.egerrit.internal.model.ReviewerInfo#getUsername()
	 * @see #getReviewerInfo()
	 * @generated
	 */
	EAttribute getReviewerInfo_Username();

	/**
	 * Returns the meta object for the map '{@link org.eclipse.egerrit.internal.model.ReviewerInfo#getApprovals <em>Approvals</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Approvals</em>'.
	 * @see org.eclipse.egerrit.internal.model.ReviewerInfo#getApprovals()
	 * @see #getReviewerInfo()
	 * @generated
	 */
	EReference getReviewerInfo_Approvals();

	/**
	 * Returns the meta object for class '{@link org.eclipse.egerrit.internal.model.GitPersonInfo <em>Git Person Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Git Person Info</em>'.
	 * @see org.eclipse.egerrit.internal.model.GitPersonInfo
	 * @generated
	 */
	EClass getGitPersonInfo();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.GitPersonInfo#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.egerrit.internal.model.GitPersonInfo#getName()
	 * @see #getGitPersonInfo()
	 * @generated
	 */
	EAttribute getGitPersonInfo_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.GitPersonInfo#getEmail <em>Email</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Email</em>'.
	 * @see org.eclipse.egerrit.internal.model.GitPersonInfo#getEmail()
	 * @see #getGitPersonInfo()
	 * @generated
	 */
	EAttribute getGitPersonInfo_Email();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.GitPersonInfo#getDate <em>Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Date</em>'.
	 * @see org.eclipse.egerrit.internal.model.GitPersonInfo#getDate()
	 * @see #getGitPersonInfo()
	 * @generated
	 */
	EAttribute getGitPersonInfo_Date();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.GitPersonInfo#getTz <em>Tz</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Tz</em>'.
	 * @see org.eclipse.egerrit.internal.model.GitPersonInfo#getTz()
	 * @see #getGitPersonInfo()
	 * @generated
	 */
	EAttribute getGitPersonInfo_Tz();

	/**
	 * Returns the meta object for class '{@link org.eclipse.egerrit.internal.model.IncludedInInfo <em>Included In Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Included In Info</em>'.
	 * @see org.eclipse.egerrit.internal.model.IncludedInInfo
	 * @generated
	 */
	EClass getIncludedInInfo();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.egerrit.internal.model.IncludedInInfo#getBranches <em>Branches</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Branches</em>'.
	 * @see org.eclipse.egerrit.internal.model.IncludedInInfo#getBranches()
	 * @see #getIncludedInInfo()
	 * @generated
	 */
	EAttribute getIncludedInInfo_Branches();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.egerrit.internal.model.IncludedInInfo#getTags <em>Tags</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Tags</em>'.
	 * @see org.eclipse.egerrit.internal.model.IncludedInInfo#getTags()
	 * @see #getIncludedInInfo()
	 * @generated
	 */
	EAttribute getIncludedInInfo_Tags();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>String To File Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>String To File Info</em>'.
	 * @see java.util.Map.Entry
	 * @model keyDataType="org.eclipse.emf.ecore.EString"
	 *        valueType="org.eclipse.egerrit.internal.model.FileInfo" valueContainment="true"
	 * @generated
	 */
	EClass getStringToFileInfo();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getStringToFileInfo()
	 * @generated
	 */
	EAttribute getStringToFileInfo_Key();

	/**
	 * Returns the meta object for the containment reference '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getStringToFileInfo()
	 * @generated
	 */
	EReference getStringToFileInfo_Value();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>String To Label Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>String To Label Info</em>'.
	 * @see java.util.Map.Entry
	 * @model keyDataType="org.eclipse.emf.ecore.EString"
	 *        valueType="org.eclipse.egerrit.internal.model.LabelInfo" valueContainment="true"
	 * @generated
	 */
	EClass getStringToLabelInfo();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getStringToLabelInfo()
	 * @generated
	 */
	EAttribute getStringToLabelInfo_Key();

	/**
	 * Returns the meta object for the containment reference '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getStringToLabelInfo()
	 * @generated
	 */
	EReference getStringToLabelInfo_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.egerrit.internal.model.BranchInfo <em>Branch Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Branch Info</em>'.
	 * @see org.eclipse.egerrit.internal.model.BranchInfo
	 * @generated
	 */
	EClass getBranchInfo();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.BranchInfo#getRef <em>Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ref</em>'.
	 * @see org.eclipse.egerrit.internal.model.BranchInfo#getRef()
	 * @see #getBranchInfo()
	 * @generated
	 */
	EAttribute getBranchInfo_Ref();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.BranchInfo#getRevision <em>Revision</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Revision</em>'.
	 * @see org.eclipse.egerrit.internal.model.BranchInfo#getRevision()
	 * @see #getBranchInfo()
	 * @generated
	 */
	EAttribute getBranchInfo_Revision();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.BranchInfo#isCan_delete <em>Can delete</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Can delete</em>'.
	 * @see org.eclipse.egerrit.internal.model.BranchInfo#isCan_delete()
	 * @see #getBranchInfo()
	 * @generated
	 */
	EAttribute getBranchInfo_Can_delete();

	/**
	 * Returns the meta object for class '{@link org.eclipse.egerrit.internal.model.ChangeInfo <em>Change Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Change Info</em>'.
	 * @see org.eclipse.egerrit.internal.model.ChangeInfo
	 * @generated
	 */
	EClass getChangeInfo();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.ChangeInfo#getKind <em>Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Kind</em>'.
	 * @see org.eclipse.egerrit.internal.model.ChangeInfo#getKind()
	 * @see #getChangeInfo()
	 * @generated
	 */
	EAttribute getChangeInfo_Kind();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.ChangeInfo#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.eclipse.egerrit.internal.model.ChangeInfo#getId()
	 * @see #getChangeInfo()
	 * @generated
	 */
	EAttribute getChangeInfo_Id();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.ChangeInfo#getProject <em>Project</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Project</em>'.
	 * @see org.eclipse.egerrit.internal.model.ChangeInfo#getProject()
	 * @see #getChangeInfo()
	 * @generated
	 */
	EAttribute getChangeInfo_Project();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.ChangeInfo#getBranch <em>Branch</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Branch</em>'.
	 * @see org.eclipse.egerrit.internal.model.ChangeInfo#getBranch()
	 * @see #getChangeInfo()
	 * @generated
	 */
	EAttribute getChangeInfo_Branch();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.ChangeInfo#getTopic <em>Topic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Topic</em>'.
	 * @see org.eclipse.egerrit.internal.model.ChangeInfo#getTopic()
	 * @see #getChangeInfo()
	 * @generated
	 */
	EAttribute getChangeInfo_Topic();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.ChangeInfo#getChange_id <em>Change id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Change id</em>'.
	 * @see org.eclipse.egerrit.internal.model.ChangeInfo#getChange_id()
	 * @see #getChangeInfo()
	 * @generated
	 */
	EAttribute getChangeInfo_Change_id();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.ChangeInfo#getSubject <em>Subject</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Subject</em>'.
	 * @see org.eclipse.egerrit.internal.model.ChangeInfo#getSubject()
	 * @see #getChangeInfo()
	 * @generated
	 */
	EAttribute getChangeInfo_Subject();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.ChangeInfo#getStatus <em>Status</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Status</em>'.
	 * @see org.eclipse.egerrit.internal.model.ChangeInfo#getStatus()
	 * @see #getChangeInfo()
	 * @generated
	 */
	EAttribute getChangeInfo_Status();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.ChangeInfo#getCreated <em>Created</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Created</em>'.
	 * @see org.eclipse.egerrit.internal.model.ChangeInfo#getCreated()
	 * @see #getChangeInfo()
	 * @generated
	 */
	EAttribute getChangeInfo_Created();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.ChangeInfo#getUpdated <em>Updated</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Updated</em>'.
	 * @see org.eclipse.egerrit.internal.model.ChangeInfo#getUpdated()
	 * @see #getChangeInfo()
	 * @generated
	 */
	EAttribute getChangeInfo_Updated();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.ChangeInfo#isStarred <em>Starred</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Starred</em>'.
	 * @see org.eclipse.egerrit.internal.model.ChangeInfo#isStarred()
	 * @see #getChangeInfo()
	 * @generated
	 */
	EAttribute getChangeInfo_Starred();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.ChangeInfo#isReviewed <em>Reviewed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Reviewed</em>'.
	 * @see org.eclipse.egerrit.internal.model.ChangeInfo#isReviewed()
	 * @see #getChangeInfo()
	 * @generated
	 */
	EAttribute getChangeInfo_Reviewed();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.ChangeInfo#isMergeable <em>Mergeable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Mergeable</em>'.
	 * @see org.eclipse.egerrit.internal.model.ChangeInfo#isMergeable()
	 * @see #getChangeInfo()
	 * @generated
	 */
	EAttribute getChangeInfo_Mergeable();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.ChangeInfo#getInsertions <em>Insertions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Insertions</em>'.
	 * @see org.eclipse.egerrit.internal.model.ChangeInfo#getInsertions()
	 * @see #getChangeInfo()
	 * @generated
	 */
	EAttribute getChangeInfo_Insertions();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.ChangeInfo#getDeletions <em>Deletions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Deletions</em>'.
	 * @see org.eclipse.egerrit.internal.model.ChangeInfo#getDeletions()
	 * @see #getChangeInfo()
	 * @generated
	 */
	EAttribute getChangeInfo_Deletions();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.ChangeInfo#get_sortkey <em>sortkey</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>sortkey</em>'.
	 * @see org.eclipse.egerrit.internal.model.ChangeInfo#get_sortkey()
	 * @see #getChangeInfo()
	 * @generated
	 */
	EAttribute getChangeInfo__sortkey();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.ChangeInfo#get_number <em>number</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>number</em>'.
	 * @see org.eclipse.egerrit.internal.model.ChangeInfo#get_number()
	 * @see #getChangeInfo()
	 * @generated
	 */
	EAttribute getChangeInfo__number();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.egerrit.internal.model.ChangeInfo#getOwner <em>Owner</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Owner</em>'.
	 * @see org.eclipse.egerrit.internal.model.ChangeInfo#getOwner()
	 * @see #getChangeInfo()
	 * @generated
	 */
	EReference getChangeInfo_Owner();

	/**
	 * Returns the meta object for the map '{@link org.eclipse.egerrit.internal.model.ChangeInfo#getActions <em>Actions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Actions</em>'.
	 * @see org.eclipse.egerrit.internal.model.ChangeInfo#getActions()
	 * @see #getChangeInfo()
	 * @generated
	 */
	EReference getChangeInfo_Actions();

	/**
	 * Returns the meta object for the map '{@link org.eclipse.egerrit.internal.model.ChangeInfo#getLabels <em>Labels</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Labels</em>'.
	 * @see org.eclipse.egerrit.internal.model.ChangeInfo#getLabels()
	 * @see #getChangeInfo()
	 * @generated
	 */
	EReference getChangeInfo_Labels();

	/**
	 * Returns the meta object for the map '{@link org.eclipse.egerrit.internal.model.ChangeInfo#getPermitted_labels <em>Permitted labels</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Permitted labels</em>'.
	 * @see org.eclipse.egerrit.internal.model.ChangeInfo#getPermitted_labels()
	 * @see #getChangeInfo()
	 * @generated
	 */
	EReference getChangeInfo_Permitted_labels();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.egerrit.internal.model.ChangeInfo#getRemovable_reviewers <em>Removable reviewers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Removable reviewers</em>'.
	 * @see org.eclipse.egerrit.internal.model.ChangeInfo#getRemovable_reviewers()
	 * @see #getChangeInfo()
	 * @generated
	 */
	EReference getChangeInfo_Removable_reviewers();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.egerrit.internal.model.ChangeInfo#getMessages <em>Messages</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Messages</em>'.
	 * @see org.eclipse.egerrit.internal.model.ChangeInfo#getMessages()
	 * @see #getChangeInfo()
	 * @generated
	 */
	EReference getChangeInfo_Messages();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.ChangeInfo#getCurrent_revision <em>Current revision</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Current revision</em>'.
	 * @see org.eclipse.egerrit.internal.model.ChangeInfo#getCurrent_revision()
	 * @see #getChangeInfo()
	 * @generated
	 */
	EAttribute getChangeInfo_Current_revision();

	/**
	 * Returns the meta object for the map '{@link org.eclipse.egerrit.internal.model.ChangeInfo#getRevisions <em>Revisions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Revisions</em>'.
	 * @see org.eclipse.egerrit.internal.model.ChangeInfo#getRevisions()
	 * @see #getChangeInfo()
	 * @generated
	 */
	EReference getChangeInfo_Revisions();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.ChangeInfo#is_more_changes <em>more changes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>more changes</em>'.
	 * @see org.eclipse.egerrit.internal.model.ChangeInfo#is_more_changes()
	 * @see #getChangeInfo()
	 * @generated
	 */
	EAttribute getChangeInfo__more_changes();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.egerrit.internal.model.ChangeInfo#getProblems <em>Problems</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Problems</em>'.
	 * @see org.eclipse.egerrit.internal.model.ChangeInfo#getProblems()
	 * @see #getChangeInfo()
	 * @generated
	 */
	EReference getChangeInfo_Problems();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.ChangeInfo#getBase_change <em>Base change</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Base change</em>'.
	 * @see org.eclipse.egerrit.internal.model.ChangeInfo#getBase_change()
	 * @see #getChangeInfo()
	 * @generated
	 */
	EAttribute getChangeInfo_Base_change();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.egerrit.internal.model.ChangeInfo#getIncludedIn <em>Included In</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Included In</em>'.
	 * @see org.eclipse.egerrit.internal.model.ChangeInfo#getIncludedIn()
	 * @see #getChangeInfo()
	 * @generated
	 */
	EReference getChangeInfo_IncludedIn();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.egerrit.internal.model.ChangeInfo#getHashtags <em>Hashtags</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Hashtags</em>'.
	 * @see org.eclipse.egerrit.internal.model.ChangeInfo#getHashtags()
	 * @see #getChangeInfo()
	 * @generated
	 */
	EAttribute getChangeInfo_Hashtags();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.egerrit.internal.model.ChangeInfo#getRelatedChanges <em>Related Changes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Related Changes</em>'.
	 * @see org.eclipse.egerrit.internal.model.ChangeInfo#getRelatedChanges()
	 * @see #getChangeInfo()
	 * @generated
	 */
	EReference getChangeInfo_RelatedChanges();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.egerrit.internal.model.ChangeInfo#getReviewers <em>Reviewers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Reviewers</em>'.
	 * @see org.eclipse.egerrit.internal.model.ChangeInfo#getReviewers()
	 * @see #getChangeInfo()
	 * @generated
	 */
	EReference getChangeInfo_Reviewers();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.egerrit.internal.model.ChangeInfo#getSameTopic <em>Same Topic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Same Topic</em>'.
	 * @see org.eclipse.egerrit.internal.model.ChangeInfo#getSameTopic()
	 * @see #getChangeInfo()
	 * @generated
	 */
	EReference getChangeInfo_SameTopic();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.egerrit.internal.model.ChangeInfo#getConflictsWith <em>Conflicts With</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Conflicts With</em>'.
	 * @see org.eclipse.egerrit.internal.model.ChangeInfo#getConflictsWith()
	 * @see #getChangeInfo()
	 * @generated
	 */
	EReference getChangeInfo_ConflictsWith();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.egerrit.internal.model.ChangeInfo#getMergeableInfo <em>Mergeable Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Mergeable Info</em>'.
	 * @see org.eclipse.egerrit.internal.model.ChangeInfo#getMergeableInfo()
	 * @see #getChangeInfo()
	 * @generated
	 */
	EReference getChangeInfo_MergeableInfo();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.egerrit.internal.model.ChangeInfo#getRevision <em>Revision</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Revision</em>'.
	 * @see org.eclipse.egerrit.internal.model.ChangeInfo#getRevision()
	 * @see #getChangeInfo()
	 * @generated
	 */
	EReference getChangeInfo_Revision();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.egerrit.internal.model.ChangeInfo#getLatestPatchSet <em>Latest Patch Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Latest Patch Set</em>'.
	 * @see org.eclipse.egerrit.internal.model.ChangeInfo#getLatestPatchSet()
	 * @see #getChangeInfo()
	 * @generated
	 */
	EReference getChangeInfo_LatestPatchSet();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.egerrit.internal.model.ChangeInfo#getUserSelectedRevision <em>User Selected Revision</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>User Selected Revision</em>'.
	 * @see org.eclipse.egerrit.internal.model.ChangeInfo#getUserSelectedRevision()
	 * @see #getChangeInfo()
	 * @generated
	 */
	EReference getChangeInfo_UserSelectedRevision();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.ChangeInfo#isRevertable <em>Revertable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Revertable</em>'.
	 * @see org.eclipse.egerrit.internal.model.ChangeInfo#isRevertable()
	 * @see #getChangeInfo()
	 * @generated
	 */
	EAttribute getChangeInfo_Revertable();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.ChangeInfo#isAbandonable <em>Abandonable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Abandonable</em>'.
	 * @see org.eclipse.egerrit.internal.model.ChangeInfo#isAbandonable()
	 * @see #getChangeInfo()
	 * @generated
	 */
	EAttribute getChangeInfo_Abandonable();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.ChangeInfo#isRestoreable <em>Restoreable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Restoreable</em>'.
	 * @see org.eclipse.egerrit.internal.model.ChangeInfo#isRestoreable()
	 * @see #getChangeInfo()
	 * @generated
	 */
	EAttribute getChangeInfo_Restoreable();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.ChangeInfo#isDeleteable <em>Deleteable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Deleteable</em>'.
	 * @see org.eclipse.egerrit.internal.model.ChangeInfo#isDeleteable()
	 * @see #getChangeInfo()
	 * @generated
	 */
	EAttribute getChangeInfo_Deleteable();

	/**
	 * Returns the meta object for the '{@link org.eclipse.egerrit.internal.model.ChangeInfo#getRevisionByNumber(int) <em>Get Revision By Number</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Revision By Number</em>' operation.
	 * @see org.eclipse.egerrit.internal.model.ChangeInfo#getRevisionByNumber(int)
	 * @generated
	 */
	EOperation getChangeInfo__GetRevisionByNumber__int();

	/**
	 * Returns the meta object for the '{@link org.eclipse.egerrit.internal.model.ChangeInfo#isActionAllowed(java.lang.String) <em>Is Action Allowed</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Action Allowed</em>' operation.
	 * @see org.eclipse.egerrit.internal.model.ChangeInfo#isActionAllowed(java.lang.String)
	 * @generated
	 */
	EOperation getChangeInfo__IsActionAllowed__String();

	/**
	 * Returns the meta object for class '{@link org.eclipse.egerrit.internal.model.ProblemInfo <em>Problem Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Problem Info</em>'.
	 * @see org.eclipse.egerrit.internal.model.ProblemInfo
	 * @generated
	 */
	EClass getProblemInfo();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.ProblemInfo#getMessage <em>Message</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Message</em>'.
	 * @see org.eclipse.egerrit.internal.model.ProblemInfo#getMessage()
	 * @see #getProblemInfo()
	 * @generated
	 */
	EAttribute getProblemInfo_Message();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.ProblemInfo#getStatus <em>Status</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Status</em>'.
	 * @see org.eclipse.egerrit.internal.model.ProblemInfo#getStatus()
	 * @see #getProblemInfo()
	 * @generated
	 */
	EAttribute getProblemInfo_Status();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.ProblemInfo#getOutcome <em>Outcome</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Outcome</em>'.
	 * @see org.eclipse.egerrit.internal.model.ProblemInfo#getOutcome()
	 * @see #getProblemInfo()
	 * @generated
	 */
	EAttribute getProblemInfo_Outcome();

	/**
	 * Returns the meta object for class '{@link org.eclipse.egerrit.internal.model.ChangeMessageInfo <em>Change Message Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Change Message Info</em>'.
	 * @see org.eclipse.egerrit.internal.model.ChangeMessageInfo
	 * @generated
	 */
	EClass getChangeMessageInfo();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.ChangeMessageInfo#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.eclipse.egerrit.internal.model.ChangeMessageInfo#getId()
	 * @see #getChangeMessageInfo()
	 * @generated
	 */
	EAttribute getChangeMessageInfo_Id();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.egerrit.internal.model.ChangeMessageInfo#getAuthor <em>Author</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Author</em>'.
	 * @see org.eclipse.egerrit.internal.model.ChangeMessageInfo#getAuthor()
	 * @see #getChangeMessageInfo()
	 * @generated
	 */
	EReference getChangeMessageInfo_Author();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.ChangeMessageInfo#getDate <em>Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Date</em>'.
	 * @see org.eclipse.egerrit.internal.model.ChangeMessageInfo#getDate()
	 * @see #getChangeMessageInfo()
	 * @generated
	 */
	EAttribute getChangeMessageInfo_Date();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.ChangeMessageInfo#getMessage <em>Message</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Message</em>'.
	 * @see org.eclipse.egerrit.internal.model.ChangeMessageInfo#getMessage()
	 * @see #getChangeMessageInfo()
	 * @generated
	 */
	EAttribute getChangeMessageInfo_Message();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.ChangeMessageInfo#get_revision_number <em>revision number</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>revision number</em>'.
	 * @see org.eclipse.egerrit.internal.model.ChangeMessageInfo#get_revision_number()
	 * @see #getChangeMessageInfo()
	 * @generated
	 */
	EAttribute getChangeMessageInfo__revision_number();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.ChangeMessageInfo#isComment <em>Comment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Comment</em>'.
	 * @see org.eclipse.egerrit.internal.model.ChangeMessageInfo#isComment()
	 * @see #getChangeMessageInfo()
	 * @generated
	 */
	EAttribute getChangeMessageInfo_Comment();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>String To Action Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>String To Action Info</em>'.
	 * @see java.util.Map.Entry
	 * @model keyDataType="org.eclipse.emf.ecore.EString"
	 *        valueType="org.eclipse.egerrit.internal.model.ActionInfo" valueContainment="true"
	 * @generated
	 */
	EClass getStringToActionInfo();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getStringToActionInfo()
	 * @generated
	 */
	EAttribute getStringToActionInfo_Key();

	/**
	 * Returns the meta object for the containment reference '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getStringToActionInfo()
	 * @generated
	 */
	EReference getStringToActionInfo_Value();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>String To List Of String</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>String To List Of String</em>'.
	 * @see java.util.Map.Entry
	 * @model keyDataType="org.eclipse.emf.ecore.EString"
	 *        valueDataType="org.eclipse.emf.ecore.EString" valueMany="true"
	 * @generated
	 */
	EClass getStringToListOfString();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getStringToListOfString()
	 * @generated
	 */
	EAttribute getStringToListOfString_Key();

	/**
	 * Returns the meta object for the attribute list '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getStringToListOfString()
	 * @generated
	 */
	EAttribute getStringToListOfString_Value();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>String To Fetch Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>String To Fetch Info</em>'.
	 * @see java.util.Map.Entry
	 * @model keyDataType="org.eclipse.emf.ecore.EString"
	 *        valueType="org.eclipse.egerrit.internal.model.FetchInfo" valueContainment="true"
	 * @generated
	 */
	EClass getStringToFetchInfo();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getStringToFetchInfo()
	 * @generated
	 */
	EAttribute getStringToFetchInfo_Key();

	/**
	 * Returns the meta object for the containment reference '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getStringToFetchInfo()
	 * @generated
	 */
	EReference getStringToFetchInfo_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.egerrit.internal.model.RevisionInfo <em>Revision Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Revision Info</em>'.
	 * @see org.eclipse.egerrit.internal.model.RevisionInfo
	 * @generated
	 */
	EClass getRevisionInfo();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.RevisionInfo#isDraft <em>Draft</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Draft</em>'.
	 * @see org.eclipse.egerrit.internal.model.RevisionInfo#isDraft()
	 * @see #getRevisionInfo()
	 * @generated
	 */
	EAttribute getRevisionInfo_Draft();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.RevisionInfo#isHas_draft_comments <em>Has draft comments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Has draft comments</em>'.
	 * @see org.eclipse.egerrit.internal.model.RevisionInfo#isHas_draft_comments()
	 * @see #getRevisionInfo()
	 * @generated
	 */
	EAttribute getRevisionInfo_Has_draft_comments();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.RevisionInfo#get_number <em>number</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>number</em>'.
	 * @see org.eclipse.egerrit.internal.model.RevisionInfo#get_number()
	 * @see #getRevisionInfo()
	 * @generated
	 */
	EAttribute getRevisionInfo__number();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.RevisionInfo#getRef <em>Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ref</em>'.
	 * @see org.eclipse.egerrit.internal.model.RevisionInfo#getRef()
	 * @see #getRevisionInfo()
	 * @generated
	 */
	EAttribute getRevisionInfo_Ref();

	/**
	 * Returns the meta object for the map '{@link org.eclipse.egerrit.internal.model.RevisionInfo#getFetch <em>Fetch</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Fetch</em>'.
	 * @see org.eclipse.egerrit.internal.model.RevisionInfo#getFetch()
	 * @see #getRevisionInfo()
	 * @generated
	 */
	EReference getRevisionInfo_Fetch();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.egerrit.internal.model.RevisionInfo#getCommit <em>Commit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Commit</em>'.
	 * @see org.eclipse.egerrit.internal.model.RevisionInfo#getCommit()
	 * @see #getRevisionInfo()
	 * @generated
	 */
	EReference getRevisionInfo_Commit();

	/**
	 * Returns the meta object for the map '{@link org.eclipse.egerrit.internal.model.RevisionInfo#getFiles <em>Files</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Files</em>'.
	 * @see org.eclipse.egerrit.internal.model.RevisionInfo#getFiles()
	 * @see #getRevisionInfo()
	 * @generated
	 */
	EReference getRevisionInfo_Files();

	/**
	 * Returns the meta object for the map '{@link org.eclipse.egerrit.internal.model.RevisionInfo#getActions <em>Actions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Actions</em>'.
	 * @see org.eclipse.egerrit.internal.model.RevisionInfo#getActions()
	 * @see #getRevisionInfo()
	 * @generated
	 */
	EReference getRevisionInfo_Actions();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.RevisionInfo#isReviewed <em>Reviewed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Reviewed</em>'.
	 * @see org.eclipse.egerrit.internal.model.RevisionInfo#isReviewed()
	 * @see #getRevisionInfo()
	 * @generated
	 */
	EAttribute getRevisionInfo_Reviewed();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.RevisionInfo#isCommentsLoaded <em>Comments Loaded</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Comments Loaded</em>'.
	 * @see org.eclipse.egerrit.internal.model.RevisionInfo#isCommentsLoaded()
	 * @see #getRevisionInfo()
	 * @generated
	 */
	EAttribute getRevisionInfo_CommentsLoaded();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.RevisionInfo#isSubmitable <em>Submitable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Submitable</em>'.
	 * @see org.eclipse.egerrit.internal.model.RevisionInfo#isSubmitable()
	 * @see #getRevisionInfo()
	 * @generated
	 */
	EAttribute getRevisionInfo_Submitable();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.RevisionInfo#isRebaseable <em>Rebaseable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Rebaseable</em>'.
	 * @see org.eclipse.egerrit.internal.model.RevisionInfo#isRebaseable()
	 * @see #getRevisionInfo()
	 * @generated
	 */
	EAttribute getRevisionInfo_Rebaseable();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.RevisionInfo#isCherrypickable <em>Cherrypickable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cherrypickable</em>'.
	 * @see org.eclipse.egerrit.internal.model.RevisionInfo#isCherrypickable()
	 * @see #getRevisionInfo()
	 * @generated
	 */
	EAttribute getRevisionInfo_Cherrypickable();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.RevisionInfo#isDeleteable <em>Deleteable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Deleteable</em>'.
	 * @see org.eclipse.egerrit.internal.model.RevisionInfo#isDeleteable()
	 * @see #getRevisionInfo()
	 * @generated
	 */
	EAttribute getRevisionInfo_Deleteable();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.RevisionInfo#isPublishable <em>Publishable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Publishable</em>'.
	 * @see org.eclipse.egerrit.internal.model.RevisionInfo#isPublishable()
	 * @see #getRevisionInfo()
	 * @generated
	 */
	EAttribute getRevisionInfo_Publishable();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.RevisionInfo#isFilesLoaded <em>Files Loaded</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Files Loaded</em>'.
	 * @see org.eclipse.egerrit.internal.model.RevisionInfo#isFilesLoaded()
	 * @see #getRevisionInfo()
	 * @generated
	 */
	EAttribute getRevisionInfo_FilesLoaded();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.RevisionInfo#isCommented <em>Commented</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Commented</em>'.
	 * @see org.eclipse.egerrit.internal.model.RevisionInfo#isCommented()
	 * @see #getRevisionInfo()
	 * @generated
	 */
	EAttribute getRevisionInfo_Commented();

	/**
	 * Returns the meta object for the '{@link org.eclipse.egerrit.internal.model.RevisionInfo#isActionAllowed(java.lang.String) <em>Is Action Allowed</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Action Allowed</em>' operation.
	 * @see org.eclipse.egerrit.internal.model.RevisionInfo#isActionAllowed(java.lang.String)
	 * @generated
	 */
	EOperation getRevisionInfo__IsActionAllowed__String();

	/**
	 * Returns the meta object for the '{@link org.eclipse.egerrit.internal.model.RevisionInfo#getId() <em>Get Id</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Id</em>' operation.
	 * @see org.eclipse.egerrit.internal.model.RevisionInfo#getId()
	 * @generated
	 */
	EOperation getRevisionInfo__GetId();

	/**
	 * Returns the meta object for the '{@link org.eclipse.egerrit.internal.model.RevisionInfo#getChangeInfo() <em>Get Change Info</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Change Info</em>' operation.
	 * @see org.eclipse.egerrit.internal.model.RevisionInfo#getChangeInfo()
	 * @generated
	 */
	EOperation getRevisionInfo__GetChangeInfo();

	/**
	 * Returns the meta object for class '{@link org.eclipse.egerrit.internal.model.SuggestReviewerInfo <em>Suggest Reviewer Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Suggest Reviewer Info</em>'.
	 * @see org.eclipse.egerrit.internal.model.SuggestReviewerInfo
	 * @generated
	 */
	EClass getSuggestReviewerInfo();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.egerrit.internal.model.SuggestReviewerInfo#getAccount <em>Account</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Account</em>'.
	 * @see org.eclipse.egerrit.internal.model.SuggestReviewerInfo#getAccount()
	 * @see #getSuggestReviewerInfo()
	 * @generated
	 */
	EReference getSuggestReviewerInfo_Account();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.egerrit.internal.model.SuggestReviewerInfo#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Group</em>'.
	 * @see org.eclipse.egerrit.internal.model.SuggestReviewerInfo#getGroup()
	 * @see #getSuggestReviewerInfo()
	 * @generated
	 */
	EReference getSuggestReviewerInfo_Group();

	/**
	 * Returns the meta object for class '{@link org.eclipse.egerrit.internal.model.GroupBaseInfo <em>Group Base Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Group Base Info</em>'.
	 * @see org.eclipse.egerrit.internal.model.GroupBaseInfo
	 * @generated
	 */
	EClass getGroupBaseInfo();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.GroupBaseInfo#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.egerrit.internal.model.GroupBaseInfo#getName()
	 * @see #getGroupBaseInfo()
	 * @generated
	 */
	EAttribute getGroupBaseInfo_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.egerrit.internal.model.GroupBaseInfo#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.eclipse.egerrit.internal.model.GroupBaseInfo#getId()
	 * @see #getGroupBaseInfo()
	 * @generated
	 */
	EAttribute getGroupBaseInfo_Id();

	/**
	 * Returns the meta object for class '{@link org.eclipse.egerrit.internal.model.Reviews <em>Reviews</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Reviews</em>'.
	 * @see org.eclipse.egerrit.internal.model.Reviews
	 * @generated
	 */
	EClass getReviews();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.egerrit.internal.model.Reviews#getAllReviews <em>All Reviews</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>All Reviews</em>'.
	 * @see org.eclipse.egerrit.internal.model.Reviews#getAllReviews()
	 * @see #getReviews()
	 * @generated
	 */
	EReference getReviews_AllReviews();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.egerrit.internal.model.ActionConstants <em>Action Constants</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Action Constants</em>'.
	 * @see org.eclipse.egerrit.internal.model.ActionConstants
	 * @generated
	 */
	EEnum getActionConstants();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ModelFactory getModelFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.egerrit.internal.model.impl.RelatedChangeAndCommitInfoImpl <em>Related Change And Commit Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.egerrit.internal.model.impl.RelatedChangeAndCommitInfoImpl
		 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getRelatedChangeAndCommitInfo()
		 * @generated
		 */
		EClass RELATED_CHANGE_AND_COMMIT_INFO = eINSTANCE.getRelatedChangeAndCommitInfo();

		/**
		 * The meta object literal for the '<em><b>Change id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RELATED_CHANGE_AND_COMMIT_INFO__CHANGE_ID = eINSTANCE.getRelatedChangeAndCommitInfo_Change_id();

		/**
		 * The meta object literal for the '<em><b>Commit</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RELATED_CHANGE_AND_COMMIT_INFO__COMMIT = eINSTANCE.getRelatedChangeAndCommitInfo_Commit();

		/**
		 * The meta object literal for the '<em><b>change number</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RELATED_CHANGE_AND_COMMIT_INFO__CHANGE_NUMBER = eINSTANCE
				.getRelatedChangeAndCommitInfo__change_number();

		/**
		 * The meta object literal for the '<em><b>revision number</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RELATED_CHANGE_AND_COMMIT_INFO__REVISION_NUMBER = eINSTANCE
				.getRelatedChangeAndCommitInfo__revision_number();

		/**
		 * The meta object literal for the '<em><b>current revision number</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RELATED_CHANGE_AND_COMMIT_INFO__CURRENT_REVISION_NUMBER = eINSTANCE
				.getRelatedChangeAndCommitInfo__current_revision_number();

		/**
		 * The meta object literal for the '{@link org.eclipse.egerrit.internal.model.impl.FetchInfoImpl <em>Fetch Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.egerrit.internal.model.impl.FetchInfoImpl
		 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getFetchInfo()
		 * @generated
		 */
		EClass FETCH_INFO = eINSTANCE.getFetchInfo();

		/**
		 * The meta object literal for the '<em><b>Url</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FETCH_INFO__URL = eINSTANCE.getFetchInfo_Url();

		/**
		 * The meta object literal for the '<em><b>Ref</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FETCH_INFO__REF = eINSTANCE.getFetchInfo_Ref();

		/**
		 * The meta object literal for the '<em><b>Commands</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FETCH_INFO__COMMANDS = eINSTANCE.getFetchInfo_Commands();

		/**
		 * The meta object literal for the '{@link org.eclipse.egerrit.internal.model.impl.SubmitInfoImpl <em>Submit Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.egerrit.internal.model.impl.SubmitInfoImpl
		 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getSubmitInfo()
		 * @generated
		 */
		EClass SUBMIT_INFO = eINSTANCE.getSubmitInfo();

		/**
		 * The meta object literal for the '<em><b>Status</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SUBMIT_INFO__STATUS = eINSTANCE.getSubmitInfo_Status();

		/**
		 * The meta object literal for the '<em><b>On behalf of</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SUBMIT_INFO__ON_BEHALF_OF = eINSTANCE.getSubmitInfo_On_behalf_of();

		/**
		 * The meta object literal for the '{@link org.eclipse.egerrit.internal.model.impl.StringToStringImpl <em>String To String</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.egerrit.internal.model.impl.StringToStringImpl
		 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getStringToString()
		 * @generated
		 */
		EClass STRING_TO_STRING = eINSTANCE.getStringToString();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STRING_TO_STRING__KEY = eINSTANCE.getStringToString_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STRING_TO_STRING__VALUE = eINSTANCE.getStringToString_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.egerrit.internal.model.impl.ProjectAccessInfoImpl <em>Project Access Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.egerrit.internal.model.impl.ProjectAccessInfoImpl
		 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getProjectAccessInfo()
		 * @generated
		 */
		EClass PROJECT_ACCESS_INFO = eINSTANCE.getProjectAccessInfo();

		/**
		 * The meta object literal for the '<em><b>Revision</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROJECT_ACCESS_INFO__REVISION = eINSTANCE.getProjectAccessInfo_Revision();

		/**
		 * The meta object literal for the '<em><b>Inherits from</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROJECT_ACCESS_INFO__INHERITS_FROM = eINSTANCE.getProjectAccessInfo_Inherits_from();

		/**
		 * The meta object literal for the '<em><b>Is owner</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROJECT_ACCESS_INFO__IS_OWNER = eINSTANCE.getProjectAccessInfo_Is_owner();

		/**
		 * The meta object literal for the '<em><b>Owner Of</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROJECT_ACCESS_INFO__OWNER_OF = eINSTANCE.getProjectAccessInfo_OwnerOf();

		/**
		 * The meta object literal for the '<em><b>Can upload</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROJECT_ACCESS_INFO__CAN_UPLOAD = eINSTANCE.getProjectAccessInfo_Can_upload();

		/**
		 * The meta object literal for the '<em><b>Can add</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROJECT_ACCESS_INFO__CAN_ADD = eINSTANCE.getProjectAccessInfo_Can_add();

		/**
		 * The meta object literal for the '<em><b>Config visible</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROJECT_ACCESS_INFO__CONFIG_VISIBLE = eINSTANCE.getProjectAccessInfo_Config_visible();

		/**
		 * The meta object literal for the '{@link org.eclipse.egerrit.internal.model.impl.CommentRangeImpl <em>Comment Range</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.egerrit.internal.model.impl.CommentRangeImpl
		 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getCommentRange()
		 * @generated
		 */
		EClass COMMENT_RANGE = eINSTANCE.getCommentRange();

		/**
		 * The meta object literal for the '<em><b>Start Line</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMMENT_RANGE__START_LINE = eINSTANCE.getCommentRange_StartLine();

		/**
		 * The meta object literal for the '<em><b>Start Character</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMMENT_RANGE__START_CHARACTER = eINSTANCE.getCommentRange_StartCharacter();

		/**
		 * The meta object literal for the '<em><b>End Line</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMMENT_RANGE__END_LINE = eINSTANCE.getCommentRange_EndLine();

		/**
		 * The meta object literal for the '<em><b>End Character</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMMENT_RANGE__END_CHARACTER = eINSTANCE.getCommentRange_EndCharacter();

		/**
		 * The meta object literal for the '{@link org.eclipse.egerrit.internal.model.impl.ActionInfoImpl <em>Action Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.egerrit.internal.model.impl.ActionInfoImpl
		 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getActionInfo()
		 * @generated
		 */
		EClass ACTION_INFO = eINSTANCE.getActionInfo();

		/**
		 * The meta object literal for the '<em><b>Method</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACTION_INFO__METHOD = eINSTANCE.getActionInfo_Method();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACTION_INFO__LABEL = eINSTANCE.getActionInfo_Label();

		/**
		 * The meta object literal for the '<em><b>Title</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACTION_INFO__TITLE = eINSTANCE.getActionInfo_Title();

		/**
		 * The meta object literal for the '<em><b>Enabled</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACTION_INFO__ENABLED = eINSTANCE.getActionInfo_Enabled();

		/**
		 * The meta object literal for the '{@link org.eclipse.egerrit.internal.model.impl.CommentInfoImpl <em>Comment Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.egerrit.internal.model.impl.CommentInfoImpl
		 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getCommentInfo()
		 * @generated
		 */
		EClass COMMENT_INFO = eINSTANCE.getCommentInfo();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMMENT_INFO__ID = eINSTANCE.getCommentInfo_Id();

		/**
		 * The meta object literal for the '<em><b>Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMMENT_INFO__PATH = eINSTANCE.getCommentInfo_Path();

		/**
		 * The meta object literal for the '<em><b>Side</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMMENT_INFO__SIDE = eINSTANCE.getCommentInfo_Side();

		/**
		 * The meta object literal for the '<em><b>Line</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMMENT_INFO__LINE = eINSTANCE.getCommentInfo_Line();

		/**
		 * The meta object literal for the '<em><b>Range</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMMENT_INFO__RANGE = eINSTANCE.getCommentInfo_Range();

		/**
		 * The meta object literal for the '<em><b>In Reply To</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMMENT_INFO__IN_REPLY_TO = eINSTANCE.getCommentInfo_InReplyTo();

		/**
		 * The meta object literal for the '<em><b>Message</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMMENT_INFO__MESSAGE = eINSTANCE.getCommentInfo_Message();

		/**
		 * The meta object literal for the '<em><b>Updated</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMMENT_INFO__UPDATED = eINSTANCE.getCommentInfo_Updated();

		/**
		 * The meta object literal for the '<em><b>Author</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMMENT_INFO__AUTHOR = eINSTANCE.getCommentInfo_Author();

		/**
		 * The meta object literal for the '{@link org.eclipse.egerrit.internal.model.impl.StringToRevisionInfoImpl <em>String To Revision Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.egerrit.internal.model.impl.StringToRevisionInfoImpl
		 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getStringToRevisionInfo()
		 * @generated
		 */
		EClass STRING_TO_REVISION_INFO = eINSTANCE.getStringToRevisionInfo();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STRING_TO_REVISION_INFO__KEY = eINSTANCE.getStringToRevisionInfo_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STRING_TO_REVISION_INFO__VALUE = eINSTANCE.getStringToRevisionInfo_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.egerrit.internal.model.impl.ReviewInfoImpl <em>Review Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.egerrit.internal.model.impl.ReviewInfoImpl
		 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getReviewInfo()
		 * @generated
		 */
		EClass REVIEW_INFO = eINSTANCE.getReviewInfo();

		/**
		 * The meta object literal for the '<em><b>Labels</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REVIEW_INFO__LABELS = eINSTANCE.getReviewInfo_Labels();

		/**
		 * The meta object literal for the '{@link org.eclipse.egerrit.internal.model.impl.LabelInfoImpl <em>Label Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.egerrit.internal.model.impl.LabelInfoImpl
		 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getLabelInfo()
		 * @generated
		 */
		EClass LABEL_INFO = eINSTANCE.getLabelInfo();

		/**
		 * The meta object literal for the '<em><b>Optional</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LABEL_INFO__OPTIONAL = eINSTANCE.getLabelInfo_Optional();

		/**
		 * The meta object literal for the '<em><b>Approved</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LABEL_INFO__APPROVED = eINSTANCE.getLabelInfo_Approved();

		/**
		 * The meta object literal for the '<em><b>Rejected</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LABEL_INFO__REJECTED = eINSTANCE.getLabelInfo_Rejected();

		/**
		 * The meta object literal for the '<em><b>Recommended</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LABEL_INFO__RECOMMENDED = eINSTANCE.getLabelInfo_Recommended();

		/**
		 * The meta object literal for the '<em><b>Disliked</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LABEL_INFO__DISLIKED = eINSTANCE.getLabelInfo_Disliked();

		/**
		 * The meta object literal for the '<em><b>Blocking</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LABEL_INFO__BLOCKING = eINSTANCE.getLabelInfo_Blocking();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LABEL_INFO__VALUE = eINSTANCE.getLabelInfo_Value();

		/**
		 * The meta object literal for the '<em><b>Default value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LABEL_INFO__DEFAULT_VALUE = eINSTANCE.getLabelInfo_Default_value();

		/**
		 * The meta object literal for the '<em><b>All</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LABEL_INFO__ALL = eINSTANCE.getLabelInfo_All();

		/**
		 * The meta object literal for the '<em><b>Values</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LABEL_INFO__VALUES = eINSTANCE.getLabelInfo_Values();

		/**
		 * The meta object literal for the '{@link org.eclipse.egerrit.internal.model.impl.ApprovalInfoImpl <em>Approval Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.egerrit.internal.model.impl.ApprovalInfoImpl
		 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getApprovalInfo()
		 * @generated
		 */
		EClass APPROVAL_INFO = eINSTANCE.getApprovalInfo();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute APPROVAL_INFO__VALUE = eINSTANCE.getApprovalInfo_Value();

		/**
		 * The meta object literal for the '<em><b>Date</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute APPROVAL_INFO__DATE = eINSTANCE.getApprovalInfo_Date();

		/**
		 * The meta object literal for the '{@link org.eclipse.egerrit.internal.model.impl.RelatedChangesInfoImpl <em>Related Changes Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.egerrit.internal.model.impl.RelatedChangesInfoImpl
		 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getRelatedChangesInfo()
		 * @generated
		 */
		EClass RELATED_CHANGES_INFO = eINSTANCE.getRelatedChangesInfo();

		/**
		 * The meta object literal for the '<em><b>Changes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RELATED_CHANGES_INFO__CHANGES = eINSTANCE.getRelatedChangesInfo_Changes();

		/**
		 * The meta object literal for the '{@link org.eclipse.egerrit.internal.model.impl.FileInfoImpl <em>File Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.egerrit.internal.model.impl.FileInfoImpl
		 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getFileInfo()
		 * @generated
		 */
		EClass FILE_INFO = eINSTANCE.getFileInfo();

		/**
		 * The meta object literal for the '<em><b>Status</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE_INFO__STATUS = eINSTANCE.getFileInfo_Status();

		/**
		 * The meta object literal for the '<em><b>Binary</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE_INFO__BINARY = eINSTANCE.getFileInfo_Binary();

		/**
		 * The meta object literal for the '<em><b>Old path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE_INFO__OLD_PATH = eINSTANCE.getFileInfo_Old_path();

		/**
		 * The meta object literal for the '<em><b>Lines inserted</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE_INFO__LINES_INSERTED = eINSTANCE.getFileInfo_Lines_inserted();

		/**
		 * The meta object literal for the '<em><b>Lines deleted</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE_INFO__LINES_DELETED = eINSTANCE.getFileInfo_Lines_deleted();

		/**
		 * The meta object literal for the '<em><b>Comments</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FILE_INFO__COMMENTS = eINSTANCE.getFileInfo_Comments();

		/**
		 * The meta object literal for the '<em><b>Draft Comments</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FILE_INFO__DRAFT_COMMENTS = eINSTANCE.getFileInfo_DraftComments();

		/**
		 * The meta object literal for the '<em><b>Reviewed</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE_INFO__REVIEWED = eINSTANCE.getFileInfo_Reviewed();

		/**
		 * The meta object literal for the '<em><b>Comments Count</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE_INFO__COMMENTS_COUNT = eINSTANCE.getFileInfo_CommentsCount();

		/**
		 * The meta object literal for the '<em><b>Drafts Count</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE_INFO__DRAFTS_COUNT = eINSTANCE.getFileInfo_DraftsCount();

		/**
		 * The meta object literal for the '<em><b>Get Path</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FILE_INFO___GET_PATH = eINSTANCE.getFileInfo__GetPath();

		/**
		 * The meta object literal for the '<em><b>Get Revision</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FILE_INFO___GET_REVISION = eINSTANCE.getFileInfo__GetRevision();

		/**
		 * The meta object literal for the '<em><b>Get All Comments</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FILE_INFO___GET_ALL_COMMENTS = eINSTANCE.getFileInfo__GetAllComments();

		/**
		 * The meta object literal for the '{@link org.eclipse.egerrit.internal.model.impl.MergeableInfoImpl <em>Mergeable Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.egerrit.internal.model.impl.MergeableInfoImpl
		 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getMergeableInfo()
		 * @generated
		 */
		EClass MERGEABLE_INFO = eINSTANCE.getMergeableInfo();

		/**
		 * The meta object literal for the '<em><b>Submit type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MERGEABLE_INFO__SUBMIT_TYPE = eINSTANCE.getMergeableInfo_Submit_type();

		/**
		 * The meta object literal for the '<em><b>Mergeable into</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MERGEABLE_INFO__MERGEABLE_INTO = eINSTANCE.getMergeableInfo_Mergeable_into();

		/**
		 * The meta object literal for the '<em><b>Mergeable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MERGEABLE_INFO__MERGEABLE = eINSTANCE.getMergeableInfo_Mergeable();

		/**
		 * The meta object literal for the '{@link org.eclipse.egerrit.internal.model.impl.ProjectInfoImpl <em>Project Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.egerrit.internal.model.impl.ProjectInfoImpl
		 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getProjectInfo()
		 * @generated
		 */
		EClass PROJECT_INFO = eINSTANCE.getProjectInfo();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROJECT_INFO__ID = eINSTANCE.getProjectInfo_Id();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROJECT_INFO__NAME = eINSTANCE.getProjectInfo_Name();

		/**
		 * The meta object literal for the '<em><b>Parent</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROJECT_INFO__PARENT = eINSTANCE.getProjectInfo_Parent();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROJECT_INFO__DESCRIPTION = eINSTANCE.getProjectInfo_Description();

		/**
		 * The meta object literal for the '<em><b>State</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROJECT_INFO__STATE = eINSTANCE.getProjectInfo_State();

		/**
		 * The meta object literal for the '<em><b>Branches</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROJECT_INFO__BRANCHES = eINSTANCE.getProjectInfo_Branches();

		/**
		 * The meta object literal for the '{@link org.eclipse.egerrit.internal.model.impl.CommitInfoImpl <em>Commit Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.egerrit.internal.model.impl.CommitInfoImpl
		 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getCommitInfo()
		 * @generated
		 */
		EClass COMMIT_INFO = eINSTANCE.getCommitInfo();

		/**
		 * The meta object literal for the '<em><b>Commit</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMMIT_INFO__COMMIT = eINSTANCE.getCommitInfo_Commit();

		/**
		 * The meta object literal for the '<em><b>Parents</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMMIT_INFO__PARENTS = eINSTANCE.getCommitInfo_Parents();

		/**
		 * The meta object literal for the '<em><b>Author</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMMIT_INFO__AUTHOR = eINSTANCE.getCommitInfo_Author();

		/**
		 * The meta object literal for the '<em><b>Committer</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMMIT_INFO__COMMITTER = eINSTANCE.getCommitInfo_Committer();

		/**
		 * The meta object literal for the '<em><b>Subject</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMMIT_INFO__SUBJECT = eINSTANCE.getCommitInfo_Subject();

		/**
		 * The meta object literal for the '<em><b>Message</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMMIT_INFO__MESSAGE = eINSTANCE.getCommitInfo_Message();

		/**
		 * The meta object literal for the '{@link org.eclipse.egerrit.internal.model.impl.AccountInfoImpl <em>Account Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.egerrit.internal.model.impl.AccountInfoImpl
		 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getAccountInfo()
		 * @generated
		 */
		EClass ACCOUNT_INFO = eINSTANCE.getAccountInfo();

		/**
		 * The meta object literal for the '<em><b>account id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACCOUNT_INFO__ACCOUNT_ID = eINSTANCE.getAccountInfo__account_id();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACCOUNT_INFO__NAME = eINSTANCE.getAccountInfo_Name();

		/**
		 * The meta object literal for the '<em><b>Email</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACCOUNT_INFO__EMAIL = eINSTANCE.getAccountInfo_Email();

		/**
		 * The meta object literal for the '<em><b>Username</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACCOUNT_INFO__USERNAME = eINSTANCE.getAccountInfo_Username();

		/**
		 * The meta object literal for the '{@link org.eclipse.egerrit.internal.model.impl.ReviewerInfoImpl <em>Reviewer Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.egerrit.internal.model.impl.ReviewerInfoImpl
		 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getReviewerInfo()
		 * @generated
		 */
		EClass REVIEWER_INFO = eINSTANCE.getReviewerInfo();

		/**
		 * The meta object literal for the '<em><b>account id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REVIEWER_INFO__ACCOUNT_ID = eINSTANCE.getReviewerInfo__account_id();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REVIEWER_INFO__NAME = eINSTANCE.getReviewerInfo_Name();

		/**
		 * The meta object literal for the '<em><b>Email</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REVIEWER_INFO__EMAIL = eINSTANCE.getReviewerInfo_Email();

		/**
		 * The meta object literal for the '<em><b>Username</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REVIEWER_INFO__USERNAME = eINSTANCE.getReviewerInfo_Username();

		/**
		 * The meta object literal for the '<em><b>Approvals</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REVIEWER_INFO__APPROVALS = eINSTANCE.getReviewerInfo_Approvals();

		/**
		 * The meta object literal for the '{@link org.eclipse.egerrit.internal.model.impl.GitPersonInfoImpl <em>Git Person Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.egerrit.internal.model.impl.GitPersonInfoImpl
		 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getGitPersonInfo()
		 * @generated
		 */
		EClass GIT_PERSON_INFO = eINSTANCE.getGitPersonInfo();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GIT_PERSON_INFO__NAME = eINSTANCE.getGitPersonInfo_Name();

		/**
		 * The meta object literal for the '<em><b>Email</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GIT_PERSON_INFO__EMAIL = eINSTANCE.getGitPersonInfo_Email();

		/**
		 * The meta object literal for the '<em><b>Date</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GIT_PERSON_INFO__DATE = eINSTANCE.getGitPersonInfo_Date();

		/**
		 * The meta object literal for the '<em><b>Tz</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GIT_PERSON_INFO__TZ = eINSTANCE.getGitPersonInfo_Tz();

		/**
		 * The meta object literal for the '{@link org.eclipse.egerrit.internal.model.impl.IncludedInInfoImpl <em>Included In Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.egerrit.internal.model.impl.IncludedInInfoImpl
		 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getIncludedInInfo()
		 * @generated
		 */
		EClass INCLUDED_IN_INFO = eINSTANCE.getIncludedInInfo();

		/**
		 * The meta object literal for the '<em><b>Branches</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INCLUDED_IN_INFO__BRANCHES = eINSTANCE.getIncludedInInfo_Branches();

		/**
		 * The meta object literal for the '<em><b>Tags</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INCLUDED_IN_INFO__TAGS = eINSTANCE.getIncludedInInfo_Tags();

		/**
		 * The meta object literal for the '{@link org.eclipse.egerrit.internal.model.impl.StringToFileInfoImpl <em>String To File Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.egerrit.internal.model.impl.StringToFileInfoImpl
		 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getStringToFileInfo()
		 * @generated
		 */
		EClass STRING_TO_FILE_INFO = eINSTANCE.getStringToFileInfo();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STRING_TO_FILE_INFO__KEY = eINSTANCE.getStringToFileInfo_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STRING_TO_FILE_INFO__VALUE = eINSTANCE.getStringToFileInfo_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.egerrit.internal.model.impl.StringToLabelInfoImpl <em>String To Label Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.egerrit.internal.model.impl.StringToLabelInfoImpl
		 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getStringToLabelInfo()
		 * @generated
		 */
		EClass STRING_TO_LABEL_INFO = eINSTANCE.getStringToLabelInfo();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STRING_TO_LABEL_INFO__KEY = eINSTANCE.getStringToLabelInfo_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STRING_TO_LABEL_INFO__VALUE = eINSTANCE.getStringToLabelInfo_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.egerrit.internal.model.impl.BranchInfoImpl <em>Branch Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.egerrit.internal.model.impl.BranchInfoImpl
		 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getBranchInfo()
		 * @generated
		 */
		EClass BRANCH_INFO = eINSTANCE.getBranchInfo();

		/**
		 * The meta object literal for the '<em><b>Ref</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BRANCH_INFO__REF = eINSTANCE.getBranchInfo_Ref();

		/**
		 * The meta object literal for the '<em><b>Revision</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BRANCH_INFO__REVISION = eINSTANCE.getBranchInfo_Revision();

		/**
		 * The meta object literal for the '<em><b>Can delete</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BRANCH_INFO__CAN_DELETE = eINSTANCE.getBranchInfo_Can_delete();

		/**
		 * The meta object literal for the '{@link org.eclipse.egerrit.internal.model.impl.ChangeInfoImpl <em>Change Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.egerrit.internal.model.impl.ChangeInfoImpl
		 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getChangeInfo()
		 * @generated
		 */
		EClass CHANGE_INFO = eINSTANCE.getChangeInfo();

		/**
		 * The meta object literal for the '<em><b>Kind</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHANGE_INFO__KIND = eINSTANCE.getChangeInfo_Kind();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHANGE_INFO__ID = eINSTANCE.getChangeInfo_Id();

		/**
		 * The meta object literal for the '<em><b>Project</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHANGE_INFO__PROJECT = eINSTANCE.getChangeInfo_Project();

		/**
		 * The meta object literal for the '<em><b>Branch</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHANGE_INFO__BRANCH = eINSTANCE.getChangeInfo_Branch();

		/**
		 * The meta object literal for the '<em><b>Topic</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHANGE_INFO__TOPIC = eINSTANCE.getChangeInfo_Topic();

		/**
		 * The meta object literal for the '<em><b>Change id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHANGE_INFO__CHANGE_ID = eINSTANCE.getChangeInfo_Change_id();

		/**
		 * The meta object literal for the '<em><b>Subject</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHANGE_INFO__SUBJECT = eINSTANCE.getChangeInfo_Subject();

		/**
		 * The meta object literal for the '<em><b>Status</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHANGE_INFO__STATUS = eINSTANCE.getChangeInfo_Status();

		/**
		 * The meta object literal for the '<em><b>Created</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHANGE_INFO__CREATED = eINSTANCE.getChangeInfo_Created();

		/**
		 * The meta object literal for the '<em><b>Updated</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHANGE_INFO__UPDATED = eINSTANCE.getChangeInfo_Updated();

		/**
		 * The meta object literal for the '<em><b>Starred</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHANGE_INFO__STARRED = eINSTANCE.getChangeInfo_Starred();

		/**
		 * The meta object literal for the '<em><b>Reviewed</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHANGE_INFO__REVIEWED = eINSTANCE.getChangeInfo_Reviewed();

		/**
		 * The meta object literal for the '<em><b>Mergeable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHANGE_INFO__MERGEABLE = eINSTANCE.getChangeInfo_Mergeable();

		/**
		 * The meta object literal for the '<em><b>Insertions</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHANGE_INFO__INSERTIONS = eINSTANCE.getChangeInfo_Insertions();

		/**
		 * The meta object literal for the '<em><b>Deletions</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHANGE_INFO__DELETIONS = eINSTANCE.getChangeInfo_Deletions();

		/**
		 * The meta object literal for the '<em><b>sortkey</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHANGE_INFO__SORTKEY = eINSTANCE.getChangeInfo__sortkey();

		/**
		 * The meta object literal for the '<em><b>number</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHANGE_INFO__NUMBER = eINSTANCE.getChangeInfo__number();

		/**
		 * The meta object literal for the '<em><b>Owner</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHANGE_INFO__OWNER = eINSTANCE.getChangeInfo_Owner();

		/**
		 * The meta object literal for the '<em><b>Actions</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHANGE_INFO__ACTIONS = eINSTANCE.getChangeInfo_Actions();

		/**
		 * The meta object literal for the '<em><b>Labels</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHANGE_INFO__LABELS = eINSTANCE.getChangeInfo_Labels();

		/**
		 * The meta object literal for the '<em><b>Permitted labels</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHANGE_INFO__PERMITTED_LABELS = eINSTANCE.getChangeInfo_Permitted_labels();

		/**
		 * The meta object literal for the '<em><b>Removable reviewers</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHANGE_INFO__REMOVABLE_REVIEWERS = eINSTANCE.getChangeInfo_Removable_reviewers();

		/**
		 * The meta object literal for the '<em><b>Messages</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHANGE_INFO__MESSAGES = eINSTANCE.getChangeInfo_Messages();

		/**
		 * The meta object literal for the '<em><b>Current revision</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHANGE_INFO__CURRENT_REVISION = eINSTANCE.getChangeInfo_Current_revision();

		/**
		 * The meta object literal for the '<em><b>Revisions</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHANGE_INFO__REVISIONS = eINSTANCE.getChangeInfo_Revisions();

		/**
		 * The meta object literal for the '<em><b>more changes</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHANGE_INFO__MORE_CHANGES = eINSTANCE.getChangeInfo__more_changes();

		/**
		 * The meta object literal for the '<em><b>Problems</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHANGE_INFO__PROBLEMS = eINSTANCE.getChangeInfo_Problems();

		/**
		 * The meta object literal for the '<em><b>Base change</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHANGE_INFO__BASE_CHANGE = eINSTANCE.getChangeInfo_Base_change();

		/**
		 * The meta object literal for the '<em><b>Included In</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHANGE_INFO__INCLUDED_IN = eINSTANCE.getChangeInfo_IncludedIn();

		/**
		 * The meta object literal for the '<em><b>Hashtags</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHANGE_INFO__HASHTAGS = eINSTANCE.getChangeInfo_Hashtags();

		/**
		 * The meta object literal for the '<em><b>Related Changes</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHANGE_INFO__RELATED_CHANGES = eINSTANCE.getChangeInfo_RelatedChanges();

		/**
		 * The meta object literal for the '<em><b>Reviewers</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHANGE_INFO__REVIEWERS = eINSTANCE.getChangeInfo_Reviewers();

		/**
		 * The meta object literal for the '<em><b>Same Topic</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHANGE_INFO__SAME_TOPIC = eINSTANCE.getChangeInfo_SameTopic();

		/**
		 * The meta object literal for the '<em><b>Conflicts With</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHANGE_INFO__CONFLICTS_WITH = eINSTANCE.getChangeInfo_ConflictsWith();

		/**
		 * The meta object literal for the '<em><b>Mergeable Info</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHANGE_INFO__MERGEABLE_INFO = eINSTANCE.getChangeInfo_MergeableInfo();

		/**
		 * The meta object literal for the '<em><b>Revision</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHANGE_INFO__REVISION = eINSTANCE.getChangeInfo_Revision();

		/**
		 * The meta object literal for the '<em><b>Latest Patch Set</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHANGE_INFO__LATEST_PATCH_SET = eINSTANCE.getChangeInfo_LatestPatchSet();

		/**
		 * The meta object literal for the '<em><b>User Selected Revision</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHANGE_INFO__USER_SELECTED_REVISION = eINSTANCE.getChangeInfo_UserSelectedRevision();

		/**
		 * The meta object literal for the '<em><b>Revertable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHANGE_INFO__REVERTABLE = eINSTANCE.getChangeInfo_Revertable();

		/**
		 * The meta object literal for the '<em><b>Abandonable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHANGE_INFO__ABANDONABLE = eINSTANCE.getChangeInfo_Abandonable();

		/**
		 * The meta object literal for the '<em><b>Restoreable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHANGE_INFO__RESTOREABLE = eINSTANCE.getChangeInfo_Restoreable();

		/**
		 * The meta object literal for the '<em><b>Deleteable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHANGE_INFO__DELETEABLE = eINSTANCE.getChangeInfo_Deleteable();

		/**
		 * The meta object literal for the '<em><b>Get Revision By Number</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation CHANGE_INFO___GET_REVISION_BY_NUMBER__INT = eINSTANCE.getChangeInfo__GetRevisionByNumber__int();

		/**
		 * The meta object literal for the '<em><b>Is Action Allowed</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation CHANGE_INFO___IS_ACTION_ALLOWED__STRING = eINSTANCE.getChangeInfo__IsActionAllowed__String();

		/**
		 * The meta object literal for the '{@link org.eclipse.egerrit.internal.model.impl.ProblemInfoImpl <em>Problem Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.egerrit.internal.model.impl.ProblemInfoImpl
		 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getProblemInfo()
		 * @generated
		 */
		EClass PROBLEM_INFO = eINSTANCE.getProblemInfo();

		/**
		 * The meta object literal for the '<em><b>Message</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROBLEM_INFO__MESSAGE = eINSTANCE.getProblemInfo_Message();

		/**
		 * The meta object literal for the '<em><b>Status</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROBLEM_INFO__STATUS = eINSTANCE.getProblemInfo_Status();

		/**
		 * The meta object literal for the '<em><b>Outcome</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROBLEM_INFO__OUTCOME = eINSTANCE.getProblemInfo_Outcome();

		/**
		 * The meta object literal for the '{@link org.eclipse.egerrit.internal.model.impl.ChangeMessageInfoImpl <em>Change Message Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.egerrit.internal.model.impl.ChangeMessageInfoImpl
		 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getChangeMessageInfo()
		 * @generated
		 */
		EClass CHANGE_MESSAGE_INFO = eINSTANCE.getChangeMessageInfo();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHANGE_MESSAGE_INFO__ID = eINSTANCE.getChangeMessageInfo_Id();

		/**
		 * The meta object literal for the '<em><b>Author</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHANGE_MESSAGE_INFO__AUTHOR = eINSTANCE.getChangeMessageInfo_Author();

		/**
		 * The meta object literal for the '<em><b>Date</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHANGE_MESSAGE_INFO__DATE = eINSTANCE.getChangeMessageInfo_Date();

		/**
		 * The meta object literal for the '<em><b>Message</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHANGE_MESSAGE_INFO__MESSAGE = eINSTANCE.getChangeMessageInfo_Message();

		/**
		 * The meta object literal for the '<em><b>revision number</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHANGE_MESSAGE_INFO__REVISION_NUMBER = eINSTANCE.getChangeMessageInfo__revision_number();

		/**
		 * The meta object literal for the '<em><b>Comment</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHANGE_MESSAGE_INFO__COMMENT = eINSTANCE.getChangeMessageInfo_Comment();

		/**
		 * The meta object literal for the '{@link org.eclipse.egerrit.internal.model.impl.StringToActionInfoImpl <em>String To Action Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.egerrit.internal.model.impl.StringToActionInfoImpl
		 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getStringToActionInfo()
		 * @generated
		 */
		EClass STRING_TO_ACTION_INFO = eINSTANCE.getStringToActionInfo();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STRING_TO_ACTION_INFO__KEY = eINSTANCE.getStringToActionInfo_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STRING_TO_ACTION_INFO__VALUE = eINSTANCE.getStringToActionInfo_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.egerrit.internal.model.impl.StringToListOfStringImpl <em>String To List Of String</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.egerrit.internal.model.impl.StringToListOfStringImpl
		 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getStringToListOfString()
		 * @generated
		 */
		EClass STRING_TO_LIST_OF_STRING = eINSTANCE.getStringToListOfString();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STRING_TO_LIST_OF_STRING__KEY = eINSTANCE.getStringToListOfString_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STRING_TO_LIST_OF_STRING__VALUE = eINSTANCE.getStringToListOfString_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.egerrit.internal.model.impl.StringToFetchInfoImpl <em>String To Fetch Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.egerrit.internal.model.impl.StringToFetchInfoImpl
		 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getStringToFetchInfo()
		 * @generated
		 */
		EClass STRING_TO_FETCH_INFO = eINSTANCE.getStringToFetchInfo();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STRING_TO_FETCH_INFO__KEY = eINSTANCE.getStringToFetchInfo_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STRING_TO_FETCH_INFO__VALUE = eINSTANCE.getStringToFetchInfo_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.egerrit.internal.model.impl.RevisionInfoImpl <em>Revision Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.egerrit.internal.model.impl.RevisionInfoImpl
		 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getRevisionInfo()
		 * @generated
		 */
		EClass REVISION_INFO = eINSTANCE.getRevisionInfo();

		/**
		 * The meta object literal for the '<em><b>Draft</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REVISION_INFO__DRAFT = eINSTANCE.getRevisionInfo_Draft();

		/**
		 * The meta object literal for the '<em><b>Has draft comments</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REVISION_INFO__HAS_DRAFT_COMMENTS = eINSTANCE.getRevisionInfo_Has_draft_comments();

		/**
		 * The meta object literal for the '<em><b>number</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REVISION_INFO__NUMBER = eINSTANCE.getRevisionInfo__number();

		/**
		 * The meta object literal for the '<em><b>Ref</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REVISION_INFO__REF = eINSTANCE.getRevisionInfo_Ref();

		/**
		 * The meta object literal for the '<em><b>Fetch</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REVISION_INFO__FETCH = eINSTANCE.getRevisionInfo_Fetch();

		/**
		 * The meta object literal for the '<em><b>Commit</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REVISION_INFO__COMMIT = eINSTANCE.getRevisionInfo_Commit();

		/**
		 * The meta object literal for the '<em><b>Files</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REVISION_INFO__FILES = eINSTANCE.getRevisionInfo_Files();

		/**
		 * The meta object literal for the '<em><b>Actions</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REVISION_INFO__ACTIONS = eINSTANCE.getRevisionInfo_Actions();

		/**
		 * The meta object literal for the '<em><b>Reviewed</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REVISION_INFO__REVIEWED = eINSTANCE.getRevisionInfo_Reviewed();

		/**
		 * The meta object literal for the '<em><b>Comments Loaded</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REVISION_INFO__COMMENTS_LOADED = eINSTANCE.getRevisionInfo_CommentsLoaded();

		/**
		 * The meta object literal for the '<em><b>Submitable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REVISION_INFO__SUBMITABLE = eINSTANCE.getRevisionInfo_Submitable();

		/**
		 * The meta object literal for the '<em><b>Rebaseable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REVISION_INFO__REBASEABLE = eINSTANCE.getRevisionInfo_Rebaseable();

		/**
		 * The meta object literal for the '<em><b>Cherrypickable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REVISION_INFO__CHERRYPICKABLE = eINSTANCE.getRevisionInfo_Cherrypickable();

		/**
		 * The meta object literal for the '<em><b>Deleteable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REVISION_INFO__DELETEABLE = eINSTANCE.getRevisionInfo_Deleteable();

		/**
		 * The meta object literal for the '<em><b>Publishable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REVISION_INFO__PUBLISHABLE = eINSTANCE.getRevisionInfo_Publishable();

		/**
		 * The meta object literal for the '<em><b>Files Loaded</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REVISION_INFO__FILES_LOADED = eINSTANCE.getRevisionInfo_FilesLoaded();

		/**
		 * The meta object literal for the '<em><b>Commented</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REVISION_INFO__COMMENTED = eINSTANCE.getRevisionInfo_Commented();

		/**
		 * The meta object literal for the '<em><b>Is Action Allowed</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation REVISION_INFO___IS_ACTION_ALLOWED__STRING = eINSTANCE.getRevisionInfo__IsActionAllowed__String();

		/**
		 * The meta object literal for the '<em><b>Get Id</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation REVISION_INFO___GET_ID = eINSTANCE.getRevisionInfo__GetId();

		/**
		 * The meta object literal for the '<em><b>Get Change Info</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation REVISION_INFO___GET_CHANGE_INFO = eINSTANCE.getRevisionInfo__GetChangeInfo();

		/**
		 * The meta object literal for the '{@link org.eclipse.egerrit.internal.model.impl.SuggestReviewerInfoImpl <em>Suggest Reviewer Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.egerrit.internal.model.impl.SuggestReviewerInfoImpl
		 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getSuggestReviewerInfo()
		 * @generated
		 */
		EClass SUGGEST_REVIEWER_INFO = eINSTANCE.getSuggestReviewerInfo();

		/**
		 * The meta object literal for the '<em><b>Account</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SUGGEST_REVIEWER_INFO__ACCOUNT = eINSTANCE.getSuggestReviewerInfo_Account();

		/**
		 * The meta object literal for the '<em><b>Group</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SUGGEST_REVIEWER_INFO__GROUP = eINSTANCE.getSuggestReviewerInfo_Group();

		/**
		 * The meta object literal for the '{@link org.eclipse.egerrit.internal.model.impl.GroupBaseInfoImpl <em>Group Base Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.egerrit.internal.model.impl.GroupBaseInfoImpl
		 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getGroupBaseInfo()
		 * @generated
		 */
		EClass GROUP_BASE_INFO = eINSTANCE.getGroupBaseInfo();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GROUP_BASE_INFO__NAME = eINSTANCE.getGroupBaseInfo_Name();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GROUP_BASE_INFO__ID = eINSTANCE.getGroupBaseInfo_Id();

		/**
		 * The meta object literal for the '{@link org.eclipse.egerrit.internal.model.impl.ReviewsImpl <em>Reviews</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.egerrit.internal.model.impl.ReviewsImpl
		 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getReviews()
		 * @generated
		 */
		EClass REVIEWS = eINSTANCE.getReviews();

		/**
		 * The meta object literal for the '<em><b>All Reviews</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REVIEWS__ALL_REVIEWS = eINSTANCE.getReviews_AllReviews();

		/**
		 * The meta object literal for the '{@link org.eclipse.egerrit.internal.model.ActionConstants <em>Action Constants</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.egerrit.internal.model.ActionConstants
		 * @see org.eclipse.egerrit.internal.model.impl.ModelPackageImpl#getActionConstants()
		 * @generated
		 */
		EEnum ACTION_CONSTANTS = eINSTANCE.getActionConstants();

	}

} //ModelPackage
