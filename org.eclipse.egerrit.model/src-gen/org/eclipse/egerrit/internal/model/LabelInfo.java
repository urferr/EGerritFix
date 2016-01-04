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
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Label Info</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.egerrit.internal.model.LabelInfo#isOptional <em>Optional</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.LabelInfo#getApproved <em>Approved</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.LabelInfo#getRejected <em>Rejected</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.LabelInfo#getRecommended <em>Recommended</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.LabelInfo#getDisliked <em>Disliked</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.LabelInfo#isBlocking <em>Blocking</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.LabelInfo#getValue <em>Value</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.LabelInfo#getDefault_value <em>Default value</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.LabelInfo#getAll <em>All</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.LabelInfo#getValues <em>Values</em>}</li>
 * </ul>
 *
 * @see org.eclipse.egerrit.internal.model.ModelPackage#getLabelInfo()
 * @model
 * @generated
 */
public interface LabelInfo extends EObject {
	/**
	 * Returns the value of the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Optional</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Optional</em>' attribute.
	 * @see #setOptional(boolean)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getLabelInfo_Optional()
	 * @model
	 * @generated
	 */
	boolean isOptional();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.LabelInfo#isOptional <em>Optional</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Optional</em>' attribute.
	 * @see #isOptional()
	 * @generated
	 */
	void setOptional(boolean value);

	/**
	 * Returns the value of the '<em><b>Approved</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Approved</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Approved</em>' containment reference.
	 * @see #setApproved(AccountInfo)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getLabelInfo_Approved()
	 * @model containment="true"
	 * @generated
	 */
	AccountInfo getApproved();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.LabelInfo#getApproved <em>Approved</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Approved</em>' containment reference.
	 * @see #getApproved()
	 * @generated
	 */
	void setApproved(AccountInfo value);

	/**
	 * Returns the value of the '<em><b>Rejected</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rejected</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rejected</em>' containment reference.
	 * @see #setRejected(AccountInfo)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getLabelInfo_Rejected()
	 * @model containment="true"
	 * @generated
	 */
	AccountInfo getRejected();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.LabelInfo#getRejected <em>Rejected</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rejected</em>' containment reference.
	 * @see #getRejected()
	 * @generated
	 */
	void setRejected(AccountInfo value);

	/**
	 * Returns the value of the '<em><b>Recommended</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Recommended</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Recommended</em>' containment reference.
	 * @see #setRecommended(AccountInfo)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getLabelInfo_Recommended()
	 * @model containment="true"
	 * @generated
	 */
	AccountInfo getRecommended();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.LabelInfo#getRecommended <em>Recommended</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Recommended</em>' containment reference.
	 * @see #getRecommended()
	 * @generated
	 */
	void setRecommended(AccountInfo value);

	/**
	 * Returns the value of the '<em><b>Disliked</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Disliked</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Disliked</em>' containment reference.
	 * @see #setDisliked(AccountInfo)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getLabelInfo_Disliked()
	 * @model containment="true"
	 * @generated
	 */
	AccountInfo getDisliked();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.LabelInfo#getDisliked <em>Disliked</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Disliked</em>' containment reference.
	 * @see #getDisliked()
	 * @generated
	 */
	void setDisliked(AccountInfo value);

	/**
	 * Returns the value of the '<em><b>Blocking</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Blocking</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Blocking</em>' attribute.
	 * @see #setBlocking(boolean)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getLabelInfo_Blocking()
	 * @model
	 * @generated
	 */
	boolean isBlocking();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.LabelInfo#isBlocking <em>Blocking</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Blocking</em>' attribute.
	 * @see #isBlocking()
	 * @generated
	 */
	void setBlocking(boolean value);

	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getLabelInfo_Value()
	 * @model
	 * @generated
	 */
	String getValue();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.LabelInfo#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(String value);

	/**
	 * Returns the value of the '<em><b>Default value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default value</em>' attribute.
	 * @see #setDefault_value(String)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getLabelInfo_Default_value()
	 * @model
	 * @generated
	 */
	String getDefault_value();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.LabelInfo#getDefault_value <em>Default value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Default value</em>' attribute.
	 * @see #getDefault_value()
	 * @generated
	 */
	void setDefault_value(String value);

	/**
	 * Returns the value of the '<em><b>All</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.egerrit.internal.model.ApprovalInfo}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>All</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>All</em>' containment reference list.
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getLabelInfo_All()
	 * @model containment="true"
	 * @generated
	 */
	EList<ApprovalInfo> getAll();

	/**
	 * Returns the value of the '<em><b>Values</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link java.lang.String},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Values</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Values</em>' map.
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getLabelInfo_Values()
	 * @model mapType="org.eclipse.egerrit.internal.model.StringToString<org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString>"
	 * @generated
	 */
	EMap<String, String> getValues();

} // LabelInfo
