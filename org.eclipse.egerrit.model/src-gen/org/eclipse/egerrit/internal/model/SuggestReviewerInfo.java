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
 * A representation of the model object '<em><b>Suggest Reviewer Info</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.egerrit.internal.model.SuggestReviewerInfo#getAccount <em>Account</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.SuggestReviewerInfo#getGroup <em>Group</em>}</li>
 * </ul>
 *
 * @see org.eclipse.egerrit.internal.model.ModelPackage#getSuggestReviewerInfo()
 * @model
 * @generated
 */
public interface SuggestReviewerInfo extends EObject {
	/**
	 * Returns the value of the '<em><b>Account</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Account</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Account</em>' containment reference.
	 * @see #setAccount(AccountInfo)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getSuggestReviewerInfo_Account()
	 * @model containment="true"
	 * @generated
	 */
	AccountInfo getAccount();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.SuggestReviewerInfo#getAccount <em>Account</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Account</em>' containment reference.
	 * @see #getAccount()
	 * @generated
	 */
	void setAccount(AccountInfo value);

	/**
	 * Returns the value of the '<em><b>Group</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Group</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Group</em>' containment reference.
	 * @see #setGroup(GroupBaseInfo)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getSuggestReviewerInfo_Group()
	 * @model containment="true"
	 * @generated
	 */
	GroupBaseInfo getGroup();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.SuggestReviewerInfo#getGroup <em>Group</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Group</em>' containment reference.
	 * @see #getGroup()
	 * @generated
	 */
	void setGroup(GroupBaseInfo value);

} // SuggestReviewerInfo
