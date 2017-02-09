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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Import Project</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.ImportProject#getReview <em>Review</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.ImportProject#getProjectName <em>Project Name</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.ImportProject#getBranch <em>Branch</em>}</li>
 * </ul>
 *
 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.egerriteclPackage#getImportProject()
 * @model
 * @generated
 */
public interface ImportProject extends Command {
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
	 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.egerriteclPackage#getImportProject_Review()
	 * @model
	 * @generated
	 */
	EObject getReview();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.ImportProject#getReview <em>Review</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Review</em>' reference.
	 * @see #getReview()
	 * @generated
	 */
	void setReview(EObject value);

	/**
	 * Returns the value of the '<em><b>Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Project Name</em>' attribute isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Project Name</em>' attribute.
	 * @see #setProjectName(String)
	 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.egerriteclPackage#getImportProject_ProjectName()
	 * @model
	 * @generated
	 */
	String getProjectName();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.ImportProject#getProjectName <em>Project Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Project Name</em>' attribute.
	 * @see #getProjectName()
	 * @generated
	 */
	void setProjectName(String value);

	/**
	 * Returns the value of the '<em><b>Branch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Branch</em>' attribute isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Branch</em>' attribute.
	 * @see #setBranch(String)
	 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.egerriteclPackage#getImportProject_Branch()
	 * @model
	 * @generated
	 */
	String getBranch();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.ImportProject#getBranch <em>Branch</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Branch</em>' attribute.
	 * @see #getBranch()
	 * @generated
	 */
	void setBranch(String value);

} // ImportProject
