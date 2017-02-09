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

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a create method for each non-abstract class of
 * the model. <!-- end-user-doc -->
 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.egerriteclPackage
 * @generated
 */
public interface egerriteclFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	egerriteclFactory eINSTANCE = org.eclipse.egerrit.internal.rcptt.support.egerritecl.impl.egerriteclFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Create Review</em>'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return a new object of class '<em>Create Review</em>'.
	 * @generated
	 */
	CreateReview createCreateReview();

	/**
	 * Returns a new object of class '<em>Review Description</em>'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return a new object of class '<em>Review Description</em>'.
	 * @generated
	 */
	ReviewDescription createReviewDescription();

	/**
	 * Returns a new object of class '<em>Amend Review</em>'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return a new object of class '<em>Amend Review</em>'.
	 * @generated
	 */
	AmendReview createAmendReview();

	/**
	 * Returns a new object of class '<em>Import Project</em>'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return a new object of class '<em>Import Project</em>'.
	 * @generated
	 */
	ImportProject createImportProject();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	egerriteclPackage getegerriteclPackage();

} //egerriteclFactory
