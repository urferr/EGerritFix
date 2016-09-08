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

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Approval Info</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.egerrit.internal.model.ApprovalInfo#getValue <em>Value</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.ApprovalInfo#getDate <em>Date</em>}</li>
 * </ul>
 *
 * @see org.eclipse.egerrit.internal.model.ModelPackage#getApprovalInfo()
 * @model
 * @generated
 */
public interface ApprovalInfo extends AccountInfo {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(Integer)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getApprovalInfo_Value()
	 * @model
	 * @generated
	 */
	Integer getValue();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.ApprovalInfo#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(Integer value);

	/**
	 * Returns the value of the '<em><b>Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Date</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Date</em>' attribute.
	 * @see #setDate(String)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getApprovalInfo_Date()
	 * @model
	 * @generated
	 */
	String getDate();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.ApprovalInfo#getDate <em>Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Date</em>' attribute.
	 * @see #getDate()
	 * @generated
	 */
	void setDate(String value);

} // ApprovalInfo
