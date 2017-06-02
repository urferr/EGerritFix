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
 * A representation of the model object '<em><b>Related Change And Commit Info</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.egerrit.internal.model.RelatedChangeAndCommitInfo#getChange_id <em>Change id</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.RelatedChangeAndCommitInfo#getCommit <em>Commit</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.RelatedChangeAndCommitInfo#get_change_number <em>change number</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.RelatedChangeAndCommitInfo#get_revision_number <em>revision number</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.RelatedChangeAndCommitInfo#get_current_revision_number <em>current revision number</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.RelatedChangeAndCommitInfo#getStatus <em>Status</em>}</li>
 * </ul>
 *
 * @see org.eclipse.egerrit.internal.model.ModelPackage#getRelatedChangeAndCommitInfo()
 * @model
 * @generated
 */
public interface RelatedChangeAndCommitInfo extends EObject {
	/**
	 * Returns the value of the '<em><b>Change id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Change id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Change id</em>' attribute.
	 * @see #setChange_id(String)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getRelatedChangeAndCommitInfo_Change_id()
	 * @model
	 * @generated
	 */
	String getChange_id();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.RelatedChangeAndCommitInfo#getChange_id <em>Change id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Change id</em>' attribute.
	 * @see #getChange_id()
	 * @generated
	 */
	void setChange_id(String value);

	/**
	 * Returns the value of the '<em><b>Commit</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Commit</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Commit</em>' containment reference.
	 * @see #setCommit(CommitInfo)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getRelatedChangeAndCommitInfo_Commit()
	 * @model containment="true"
	 * @generated
	 */
	CommitInfo getCommit();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.RelatedChangeAndCommitInfo#getCommit <em>Commit</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Commit</em>' containment reference.
	 * @see #getCommit()
	 * @generated
	 */
	void setCommit(CommitInfo value);

	/**
	 * Returns the value of the '<em><b>change number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>change number</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>change number</em>' attribute.
	 * @see #set_change_number(String)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getRelatedChangeAndCommitInfo__change_number()
	 * @model
	 * @generated
	 */
	String get_change_number();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.RelatedChangeAndCommitInfo#get_change_number <em>change number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>change number</em>' attribute.
	 * @see #get_change_number()
	 * @generated
	 */
	void set_change_number(String value);

	/**
	 * Returns the value of the '<em><b>revision number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>revision number</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>revision number</em>' attribute.
	 * @see #set_revision_number(String)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getRelatedChangeAndCommitInfo__revision_number()
	 * @model
	 * @generated
	 */
	String get_revision_number();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.RelatedChangeAndCommitInfo#get_revision_number <em>revision number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>revision number</em>' attribute.
	 * @see #get_revision_number()
	 * @generated
	 */
	void set_revision_number(String value);

	/**
	 * Returns the value of the '<em><b>current revision number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>current revision number</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>current revision number</em>' attribute.
	 * @see #set_current_revision_number(String)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getRelatedChangeAndCommitInfo__current_revision_number()
	 * @model
	 * @generated
	 */
	String get_current_revision_number();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.RelatedChangeAndCommitInfo#get_current_revision_number <em>current revision number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>current revision number</em>' attribute.
	 * @see #get_current_revision_number()
	 * @generated
	 */
	void set_current_revision_number(String value);

	/**
	 * Returns the value of the '<em><b>Status</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Status</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Status</em>' attribute.
	 * @see #setStatus(String)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getRelatedChangeAndCommitInfo_Status()
	 * @model
	 * @generated
	 */
	String getStatus();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.RelatedChangeAndCommitInfo#getStatus <em>Status</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Status</em>' attribute.
	 * @see #getStatus()
	 * @generated
	 */
	void setStatus(String value);

} // RelatedChangeAndCommitInfo
