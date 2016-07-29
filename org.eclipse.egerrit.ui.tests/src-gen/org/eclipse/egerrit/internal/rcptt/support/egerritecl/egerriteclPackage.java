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
 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.egerriteclFactory
 * @model kind="package"
 * @generated
 */
public interface egerriteclPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "egerritecl"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/egerrit/2016/egerritecl.ecore"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.egerrit.ecl"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	egerriteclPackage eINSTANCE = org.eclipse.egerrit.internal.rcptt.support.egerritecl.impl.egerriteclPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.impl.CreateReviewImpl <em>Create Review</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.impl.CreateReviewImpl
	 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.impl.egerriteclPackageImpl#getCreateReview()
	 * @generated
	 */
	int CREATE_REVIEW = 0;

	/**
	 * The feature id for the '<em><b>Host</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_REVIEW__HOST = CorePackage.COMMAND__HOST;

	/**
	 * The feature id for the '<em><b>Bindings</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_REVIEW__BINDINGS = CorePackage.COMMAND__BINDINGS;

	/**
	 * The feature id for the '<em><b>Server</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_REVIEW__SERVER = CorePackage.COMMAND_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Project</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_REVIEW__PROJECT = CorePackage.COMMAND_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Create Review</em>' class.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREATE_REVIEW_FEATURE_COUNT = CorePackage.COMMAND_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Create Review</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated-not
	 * @ordered
	 */
	int CREATE_REVIEW_OPERATION_COUNT = 0; // Was originally generated as CorePackage.COMMAND_OPERATION_COUNT + 0;

	/**
	 * Returns the meta object for class '{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.CreateReview <em>Create Review</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
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
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
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
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.impl.CreateReviewImpl <em>Create Review</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.impl.CreateReviewImpl
		 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.impl.egerriteclPackageImpl#getCreateReview()
		 * @generated
		 */
		EClass CREATE_REVIEW = eINSTANCE.getCreateReview();

		/**
		 * The meta object literal for the '<em><b>Server</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * @generated
		 */
		EAttribute CREATE_REVIEW__SERVER = eINSTANCE.getCreateReview_Server();

		/**
		 * The meta object literal for the '<em><b>Project</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * @generated
		 */
		EAttribute CREATE_REVIEW__PROJECT = eINSTANCE.getCreateReview_Project();

	}

} //egerriteclPackage
