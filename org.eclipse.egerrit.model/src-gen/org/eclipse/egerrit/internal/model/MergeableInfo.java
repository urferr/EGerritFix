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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Mergeable Info</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.egerrit.internal.model.MergeableInfo#getSubmit_type <em>Submit type</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.MergeableInfo#getMergeable_into <em>Mergeable into</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.MergeableInfo#isMergeable <em>Mergeable</em>}</li>
 * </ul>
 *
 * @see org.eclipse.egerrit.internal.model.ModelPackage#getMergeableInfo()
 * @model
 * @generated
 */
public interface MergeableInfo extends EObject {
	/**
	 * Returns the value of the '<em><b>Submit type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Submit type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Submit type</em>' attribute.
	 * @see #setSubmit_type(String)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getMergeableInfo_Submit_type()
	 * @model
	 * @generated
	 */
	String getSubmit_type();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.MergeableInfo#getSubmit_type <em>Submit type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Submit type</em>' attribute.
	 * @see #getSubmit_type()
	 * @generated
	 */
	void setSubmit_type(String value);

	/**
	 * Returns the value of the '<em><b>Mergeable into</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mergeable into</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mergeable into</em>' attribute.
	 * @see #setMergeable_into(String)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getMergeableInfo_Mergeable_into()
	 * @model
	 * @generated
	 */
	String getMergeable_into();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.MergeableInfo#getMergeable_into <em>Mergeable into</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mergeable into</em>' attribute.
	 * @see #getMergeable_into()
	 * @generated
	 */
	void setMergeable_into(String value);

	/**
	 * Returns the value of the '<em><b>Mergeable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mergeable</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mergeable</em>' attribute.
	 * @see #setMergeable(boolean)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getMergeableInfo_Mergeable()
	 * @model
	 * @generated
	 */
	boolean isMergeable();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.MergeableInfo#isMergeable <em>Mergeable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mergeable</em>' attribute.
	 * @see #isMergeable()
	 * @generated
	 */
	void setMergeable(boolean value);

} // MergeableInfo
