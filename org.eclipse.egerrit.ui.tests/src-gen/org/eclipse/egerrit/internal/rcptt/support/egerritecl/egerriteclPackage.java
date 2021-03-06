/**
 *   Copyright (c) 2016 Ericsson AB
 *
 *   All rights reserved. This program and the accompanying materials are
 *   made available under the terms of the Eclipse Public License v1.0 which
 *   accompanies this distribution, and is available at
 *   http://www.eclipse.org/legal/epl-v10.html
 *
 *   Contributors:
 *     Ericsson AB - Initial API and implementation
 */
package org.eclipse.egerrit.internal.rcptt.support.egerritecl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.rcptt.ecl.core.CorePackage;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each operation of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * 
 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.egerriteclFactory
 * @model kind="package"
 * @generated
 */
public interface egerriteclPackage extends EPackage {
	/**
	 * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNAME = "egerritecl"; //$NON-NLS-1$

	/**
	 * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/egerrit/2016/egerritecl.ecore"; //$NON-NLS-1$

	/**
	 * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.egerrit.ecl"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	egerriteclPackage eINSTANCE = org.eclipse.egerrit.internal.rcptt.support.egerritecl.impl.egerriteclPackageImpl
			.init();

	/**
	 * The meta object id for the '{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.impl.CreateReviewImpl
	 * <em>Create Review</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.impl.CreateReviewImpl
	 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.impl.egerriteclPackageImpl#getCreateReview()
	 * @generated
	 */
	int CREATE_REVIEW = 0;

	/**
	 * The feature id for the '<em><b>Host</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CREATE_REVIEW__HOST = CorePackage.COMMAND__HOST;

	/**
	 * The feature id for the '<em><b>Bindings</b></em>' containment reference list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CREATE_REVIEW__BINDINGS = CorePackage.COMMAND__BINDINGS;

	/**
	 * The feature id for the '<em><b>Server</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CREATE_REVIEW__SERVER = CorePackage.COMMAND_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Project</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CREATE_REVIEW__PROJECT = CorePackage.COMMAND_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Is Draft</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CREATE_REVIEW__IS_DRAFT = CorePackage.COMMAND_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Filename</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CREATE_REVIEW__FILENAME = CorePackage.COMMAND_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>File Content</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CREATE_REVIEW__FILE_CONTENT = CorePackage.COMMAND_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Create Review</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CREATE_REVIEW_FEATURE_COUNT = CorePackage.COMMAND_FEATURE_COUNT + 5;

	/**
	 * The number of operations of the '<em>Create Review</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CREATE_REVIEW_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.impl.ReviewDescriptionImpl <em>Review
	 * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.impl.ReviewDescriptionImpl
	 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.impl.egerriteclPackageImpl#getReviewDescription()
	 * @generated
	 */
	int REVIEW_DESCRIPTION = 1;

	/**
	 * The feature id for the '<em><b>Gerrit Server URL</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @generated
	 * @ordered
	 */
	int REVIEW_DESCRIPTION__GERRIT_SERVER_URL = 0;

	/**
	 * The feature id for the '<em><b>Project Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REVIEW_DESCRIPTION__PROJECT_NAME = 1;

	/**
	 * The feature id for the '<em><b>Local Clone</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REVIEW_DESCRIPTION__LOCAL_CLONE = 2;

	/**
	 * The feature id for the '<em><b>Last Change Id</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REVIEW_DESCRIPTION__LAST_CHANGE_ID = 3;

	/**
	 * The feature id for the '<em><b>Is Draft</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REVIEW_DESCRIPTION__IS_DRAFT = 4;

	/**
	 * The number of structural features of the '<em>Review Description</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REVIEW_DESCRIPTION_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Review Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @generated
	 * @ordered
	 */
	int REVIEW_DESCRIPTION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.impl.AmendReviewImpl
	 * <em>Amend Review</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.impl.AmendReviewImpl
	 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.impl.egerriteclPackageImpl#getAmendReview()
	 * @generated
	 */
	int AMEND_REVIEW = 2;

	/**
	 * The feature id for the '<em><b>Host</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int AMEND_REVIEW__HOST = CorePackage.COMMAND__HOST;

	/**
	 * The feature id for the '<em><b>Bindings</b></em>' containment reference list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int AMEND_REVIEW__BINDINGS = CorePackage.COMMAND__BINDINGS;

	/**
	 * The feature id for the '<em><b>Review</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int AMEND_REVIEW__REVIEW = CorePackage.COMMAND_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Is Draft</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int AMEND_REVIEW__IS_DRAFT = CorePackage.COMMAND_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Amend Review</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @generated
	 * @ordered
	 */
	int AMEND_REVIEW_FEATURE_COUNT = CorePackage.COMMAND_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Amend Review</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int AMEND_REVIEW_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.impl.ImportProjectImpl
	 * <em>Import Project</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.impl.ImportProjectImpl
	 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.impl.egerriteclPackageImpl#getImportProject()
	 * @generated
	 */
	int IMPORT_PROJECT = 3;

	/**
	 * The feature id for the '<em><b>Host</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IMPORT_PROJECT__HOST = CorePackage.COMMAND__HOST;

	/**
	 * The feature id for the '<em><b>Bindings</b></em>' containment reference list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IMPORT_PROJECT__BINDINGS = CorePackage.COMMAND__BINDINGS;

	/**
	 * The feature id for the '<em><b>Review</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IMPORT_PROJECT__REVIEW = CorePackage.COMMAND_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Project Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IMPORT_PROJECT__PROJECT_NAME = CorePackage.COMMAND_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Branch</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IMPORT_PROJECT__BRANCH = CorePackage.COMMAND_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Import Project</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IMPORT_PROJECT_FEATURE_COUNT = CorePackage.COMMAND_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Import Project</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IMPORT_PROJECT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.impl.AddGerritServerImpl
	 * <em>Add Gerrit Server</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.impl.AddGerritServerImpl
	 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.impl.egerriteclPackageImpl#getAddGerritServer()
	 * @generated
	 */
	int ADD_GERRIT_SERVER = 4;

	/**
	 * The feature id for the '<em><b>Host</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ADD_GERRIT_SERVER__HOST = CorePackage.COMMAND__HOST;

	/**
	 * The feature id for the '<em><b>Bindings</b></em>' containment reference list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ADD_GERRIT_SERVER__BINDINGS = CorePackage.COMMAND__BINDINGS;

	/**
	 * The feature id for the '<em><b>Server URL</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ADD_GERRIT_SERVER__SERVER_URL = CorePackage.COMMAND_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Server Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ADD_GERRIT_SERVER__SERVER_NAME = CorePackage.COMMAND_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>User Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ADD_GERRIT_SERVER__USER_NAME = CorePackage.COMMAND_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>User Pwd</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ADD_GERRIT_SERVER__USER_PWD = CorePackage.COMMAND_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Add Gerrit Server</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ADD_GERRIT_SERVER_FEATURE_COUNT = CorePackage.COMMAND_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>Add Gerrit Server</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ADD_GERRIT_SERVER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.impl.RemoveGerritServerImpl <em>Remove Gerrit
	 * Server</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.impl.RemoveGerritServerImpl
	 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.impl.egerriteclPackageImpl#getRemoveGerritServer()
	 * @generated
	 */
	int REMOVE_GERRIT_SERVER = 5;

	/**
	 * The feature id for the '<em><b>Host</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REMOVE_GERRIT_SERVER__HOST = CorePackage.COMMAND__HOST;

	/**
	 * The feature id for the '<em><b>Bindings</b></em>' containment reference list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REMOVE_GERRIT_SERVER__BINDINGS = CorePackage.COMMAND__BINDINGS;

	/**
	 * The feature id for the '<em><b>Server Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REMOVE_GERRIT_SERVER__SERVER_NAME = CorePackage.COMMAND_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Remove Gerrit Server</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REMOVE_GERRIT_SERVER_FEATURE_COUNT = CorePackage.COMMAND_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Remove Gerrit Server</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @generated
	 * @ordered
	 */
	int REMOVE_GERRIT_SERVER_OPERATION_COUNT = 0;

	/**
	 * Returns the meta object for class '{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.CreateReview
	 * <em>Create Review</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Create Review</em>'.
	 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.CreateReview
	 * @generated
	 */
	EClass getCreateReview();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.CreateReview#getServer <em>Server</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Server</em>'.
	 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.CreateReview#getServer()
	 * @see #getCreateReview()
	 * @generated
	 */
	EAttribute getCreateReview_Server();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.CreateReview#getProject <em>Project</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Project</em>'.
	 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.CreateReview#getProject()
	 * @see #getCreateReview()
	 * @generated
	 */
	EAttribute getCreateReview_Project();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.CreateReview#isIsDraft <em>Is Draft</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Is Draft</em>'.
	 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.CreateReview#isIsDraft()
	 * @see #getCreateReview()
	 * @generated
	 */
	EAttribute getCreateReview_IsDraft();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.CreateReview#getFilename <em>Filename</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Filename</em>'.
	 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.CreateReview#getFilename()
	 * @see #getCreateReview()
	 * @generated
	 */
	EAttribute getCreateReview_Filename();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.CreateReview#getFileContent <em>File
	 * Content</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>File Content</em>'.
	 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.CreateReview#getFileContent()
	 * @see #getCreateReview()
	 * @generated
	 */
	EAttribute getCreateReview_FileContent();

	/**
	 * Returns the meta object for class '{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.ReviewDescription
	 * <em>Review Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Review Description</em>'.
	 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.ReviewDescription
	 * @generated
	 */
	EClass getReviewDescription();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.ReviewDescription#getGerritServerURL <em>Gerrit
	 * Server URL</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Gerrit Server URL</em>'.
	 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.ReviewDescription#getGerritServerURL()
	 * @see #getReviewDescription()
	 * @generated
	 */
	EAttribute getReviewDescription_GerritServerURL();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.ReviewDescription#getProjectName <em>Project
	 * Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Project Name</em>'.
	 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.ReviewDescription#getProjectName()
	 * @see #getReviewDescription()
	 * @generated
	 */
	EAttribute getReviewDescription_ProjectName();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.ReviewDescription#getLocalClone <em>Local
	 * Clone</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Local Clone</em>'.
	 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.ReviewDescription#getLocalClone()
	 * @see #getReviewDescription()
	 * @generated
	 */
	EAttribute getReviewDescription_LocalClone();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.ReviewDescription#getLastChangeId <em>Last Change
	 * Id</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Last Change Id</em>'.
	 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.ReviewDescription#getLastChangeId()
	 * @see #getReviewDescription()
	 * @generated
	 */
	EAttribute getReviewDescription_LastChangeId();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.ReviewDescription#isIsDraft <em>Is Draft</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Is Draft</em>'.
	 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.ReviewDescription#isIsDraft()
	 * @see #getReviewDescription()
	 * @generated
	 */
	EAttribute getReviewDescription_IsDraft();

	/**
	 * Returns the meta object for class '{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.AmendReview
	 * <em>Amend Review</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Amend Review</em>'.
	 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.AmendReview
	 * @generated
	 */
	EClass getAmendReview();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.AmendReview#getReview <em>Review</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Review</em>'.
	 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.AmendReview#getReview()
	 * @see #getAmendReview()
	 * @generated
	 */
	EReference getAmendReview_Review();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.AmendReview#isIsDraft <em>Is Draft</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Is Draft</em>'.
	 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.AmendReview#isIsDraft()
	 * @see #getAmendReview()
	 * @generated
	 */
	EAttribute getAmendReview_IsDraft();

	/**
	 * Returns the meta object for class '{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.ImportProject
	 * <em>Import Project</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Import Project</em>'.
	 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.ImportProject
	 * @generated
	 */
	EClass getImportProject();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.ImportProject#getReview <em>Review</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Review</em>'.
	 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.ImportProject#getReview()
	 * @see #getImportProject()
	 * @generated
	 */
	EReference getImportProject_Review();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.ImportProject#getProjectName <em>Project
	 * Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Project Name</em>'.
	 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.ImportProject#getProjectName()
	 * @see #getImportProject()
	 * @generated
	 */
	EAttribute getImportProject_ProjectName();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.ImportProject#getBranch <em>Branch</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Branch</em>'.
	 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.ImportProject#getBranch()
	 * @see #getImportProject()
	 * @generated
	 */
	EAttribute getImportProject_Branch();

	/**
	 * Returns the meta object for class '{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.AddGerritServer
	 * <em>Add Gerrit Server</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Add Gerrit Server</em>'.
	 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.AddGerritServer
	 * @generated
	 */
	EClass getAddGerritServer();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.AddGerritServer#getServerURL <em>Server URL</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Server URL</em>'.
	 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.AddGerritServer#getServerURL()
	 * @see #getAddGerritServer()
	 * @generated
	 */
	EAttribute getAddGerritServer_ServerURL();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.AddGerritServer#getServerName <em>Server
	 * Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Server Name</em>'.
	 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.AddGerritServer#getServerName()
	 * @see #getAddGerritServer()
	 * @generated
	 */
	EAttribute getAddGerritServer_ServerName();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.AddGerritServer#getUserName <em>User Name</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>User Name</em>'.
	 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.AddGerritServer#getUserName()
	 * @see #getAddGerritServer()
	 * @generated
	 */
	EAttribute getAddGerritServer_UserName();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.AddGerritServer#getUserPwd <em>User Pwd</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>User Pwd</em>'.
	 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.AddGerritServer#getUserPwd()
	 * @see #getAddGerritServer()
	 * @generated
	 */
	EAttribute getAddGerritServer_UserPwd();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.RemoveGerritServer <em>Remove Gerrit Server</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Remove Gerrit Server</em>'.
	 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.RemoveGerritServer
	 * @generated
	 */
	EClass getRemoveGerritServer();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.RemoveGerritServer#getServerName <em>Server
	 * Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Server Name</em>'.
	 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.RemoveGerritServer#getServerName()
	 * @see #getRemoveGerritServer()
	 * @generated
	 */
	EAttribute getRemoveGerritServer_ServerName();

	/**
	 * Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	egerriteclFactory getegerriteclFactory();

	/**
	 * <!-- begin-user-doc --> Defines literals for the meta objects that represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each operation of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.impl.CreateReviewImpl <em>Create Review</em>}'
		 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.impl.CreateReviewImpl
		 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.impl.egerriteclPackageImpl#getCreateReview()
		 * @generated
		 */
		EClass CREATE_REVIEW = eINSTANCE.getCreateReview();

		/**
		 * The meta object literal for the '<em><b>Server</b></em>' attribute feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute CREATE_REVIEW__SERVER = eINSTANCE.getCreateReview_Server();

		/**
		 * The meta object literal for the '<em><b>Project</b></em>' attribute feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute CREATE_REVIEW__PROJECT = eINSTANCE.getCreateReview_Project();

		/**
		 * The meta object literal for the '<em><b>Is Draft</b></em>' attribute feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute CREATE_REVIEW__IS_DRAFT = eINSTANCE.getCreateReview_IsDraft();

		/**
		 * The meta object literal for the '<em><b>Filename</b></em>' attribute feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute CREATE_REVIEW__FILENAME = eINSTANCE.getCreateReview_Filename();

		/**
		 * The meta object literal for the '<em><b>File Content</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute CREATE_REVIEW__FILE_CONTENT = eINSTANCE.getCreateReview_FileContent();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.impl.ReviewDescriptionImpl <em>Review
		 * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.impl.ReviewDescriptionImpl
		 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.impl.egerriteclPackageImpl#getReviewDescription()
		 * @generated
		 */
		EClass REVIEW_DESCRIPTION = eINSTANCE.getReviewDescription();

		/**
		 * The meta object literal for the '<em><b>Gerrit Server URL</b></em>' attribute feature. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute REVIEW_DESCRIPTION__GERRIT_SERVER_URL = eINSTANCE.getReviewDescription_GerritServerURL();

		/**
		 * The meta object literal for the '<em><b>Project Name</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute REVIEW_DESCRIPTION__PROJECT_NAME = eINSTANCE.getReviewDescription_ProjectName();

		/**
		 * The meta object literal for the '<em><b>Local Clone</b></em>' attribute feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute REVIEW_DESCRIPTION__LOCAL_CLONE = eINSTANCE.getReviewDescription_LocalClone();

		/**
		 * The meta object literal for the '<em><b>Last Change Id</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute REVIEW_DESCRIPTION__LAST_CHANGE_ID = eINSTANCE.getReviewDescription_LastChangeId();

		/**
		 * The meta object literal for the '<em><b>Is Draft</b></em>' attribute feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute REVIEW_DESCRIPTION__IS_DRAFT = eINSTANCE.getReviewDescription_IsDraft();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.impl.AmendReviewImpl <em>Amend Review</em>}'
		 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.impl.AmendReviewImpl
		 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.impl.egerriteclPackageImpl#getAmendReview()
		 * @generated
		 */
		EClass AMEND_REVIEW = eINSTANCE.getAmendReview();

		/**
		 * The meta object literal for the '<em><b>Review</b></em>' reference feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference AMEND_REVIEW__REVIEW = eINSTANCE.getAmendReview_Review();

		/**
		 * The meta object literal for the '<em><b>Is Draft</b></em>' attribute feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute AMEND_REVIEW__IS_DRAFT = eINSTANCE.getAmendReview_IsDraft();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.impl.ImportProjectImpl <em>Import
		 * Project</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.impl.ImportProjectImpl
		 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.impl.egerriteclPackageImpl#getImportProject()
		 * @generated
		 */
		EClass IMPORT_PROJECT = eINSTANCE.getImportProject();

		/**
		 * The meta object literal for the '<em><b>Review</b></em>' reference feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference IMPORT_PROJECT__REVIEW = eINSTANCE.getImportProject_Review();

		/**
		 * The meta object literal for the '<em><b>Project Name</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute IMPORT_PROJECT__PROJECT_NAME = eINSTANCE.getImportProject_ProjectName();

		/**
		 * The meta object literal for the '<em><b>Branch</b></em>' attribute feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute IMPORT_PROJECT__BRANCH = eINSTANCE.getImportProject_Branch();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.impl.AddGerritServerImpl <em>Add Gerrit
		 * Server</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.impl.AddGerritServerImpl
		 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.impl.egerriteclPackageImpl#getAddGerritServer()
		 * @generated
		 */
		EClass ADD_GERRIT_SERVER = eINSTANCE.getAddGerritServer();

		/**
		 * The meta object literal for the '<em><b>Server URL</b></em>' attribute feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ADD_GERRIT_SERVER__SERVER_URL = eINSTANCE.getAddGerritServer_ServerURL();

		/**
		 * The meta object literal for the '<em><b>Server Name</b></em>' attribute feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ADD_GERRIT_SERVER__SERVER_NAME = eINSTANCE.getAddGerritServer_ServerName();

		/**
		 * The meta object literal for the '<em><b>User Name</b></em>' attribute feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ADD_GERRIT_SERVER__USER_NAME = eINSTANCE.getAddGerritServer_UserName();

		/**
		 * The meta object literal for the '<em><b>User Pwd</b></em>' attribute feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ADD_GERRIT_SERVER__USER_PWD = eINSTANCE.getAddGerritServer_UserPwd();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.impl.RemoveGerritServerImpl <em>Remove Gerrit
		 * Server</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.impl.RemoveGerritServerImpl
		 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.impl.egerriteclPackageImpl#getRemoveGerritServer()
		 * @generated
		 */
		EClass REMOVE_GERRIT_SERVER = eINSTANCE.getRemoveGerritServer();

		/**
		 * The meta object literal for the '<em><b>Server Name</b></em>' attribute feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute REMOVE_GERRIT_SERVER__SERVER_NAME = eINSTANCE.getRemoveGerritServer_ServerName();

	}

} //egerriteclPackage
