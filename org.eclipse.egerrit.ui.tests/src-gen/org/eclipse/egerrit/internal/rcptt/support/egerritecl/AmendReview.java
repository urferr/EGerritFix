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

import org.eclipse.emf.ecore.EObject;

import org.eclipse.rcptt.ecl.core.Command;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Amend Review</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.AmendReview#getReview <em>Review</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.AmendReview#isIsDraft <em>Is Draft</em>}</li>
 * </ul>
 *
 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.egerriteclPackage#getAmendReview()
 * @model
 * @generated
 */
public interface AmendReview extends Command {
	/**
	 * Returns the value of the '<em><b>Review</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Review</em>' reference isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Review</em>' reference.
	 * @see #setReview(EObject)
	 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.egerriteclPackage#getAmendReview_Review()
	 * @model
	 * @generated
	 */
	EObject getReview();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.AmendReview#getReview <em>Review</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Review</em>' reference.
	 * @see #getReview()
	 * @generated
	 */
	void setReview(EObject value);

	/**
	 * Returns the value of the '<em><b>Is Draft</b></em>' attribute. The default value is <code>"false"</code>. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Is Draft</em>' attribute isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Is Draft</em>' attribute.
	 * @see #setIsDraft(boolean)
	 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.egerriteclPackage#getAmendReview_IsDraft()
	 * @model default="false"
	 * @generated
	 */
	boolean isIsDraft();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.AmendReview#isIsDraft <em>Is Draft</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Draft</em>' attribute.
	 * @see #isIsDraft()
	 * @generated
	 */
	void setIsDraft(boolean value);

} // AmendReview
