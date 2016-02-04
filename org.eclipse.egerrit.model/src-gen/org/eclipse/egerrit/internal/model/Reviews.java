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

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Reviews</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.egerrit.internal.model.Reviews#getAllReviews <em>All Reviews</em>}</li>
 * </ul>
 *
 * @see org.eclipse.egerrit.internal.model.ModelPackage#getReviews()
 * @model
 * @generated
 */
public interface Reviews extends EObject {
	/**
	 * Returns the value of the '<em><b>All Reviews</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.egerrit.internal.model.ChangeInfo}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>All Reviews</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>All Reviews</em>' reference list.
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getReviews_AllReviews()
	 * @model
	 * @generated
	 */
	EList<ChangeInfo> getAllReviews();

} // Reviews
