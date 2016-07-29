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

import org.eclipse.rcptt.ecl.core.Command;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Create Review</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.CreateReview#getServer <em>Server</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.CreateReview#getProject <em>Project</em>}</li>
 * </ul>
 *
 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.egerriteclPackage#getCreateReview()
 * @model annotation="http://www.eclipse.org/ecl/docs description='Create a review' returns='ChangeId of the created review'"
 * @generated
 */
public interface CreateReview extends Command {
	/**
	 * Returns the value of the '<em><b>Server</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Server</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Server</em>' attribute.
	 * @see #setServer(String)
	 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.egerriteclPackage#getCreateReview_Server()
	 * @model default=""
	 * @generated
	 */
	String getServer();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.CreateReview#getServer <em>Server</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Server</em>' attribute.
	 * @see #getServer()
	 * @generated
	 */
	void setServer(String value);

	/**
	 * Returns the value of the '<em><b>Project</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Project</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Project</em>' attribute.
	 * @see #setProject(String)
	 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.egerriteclPackage#getCreateReview_Project()
	 * @model
	 * @generated
	 */
	String getProject();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.CreateReview#getProject <em>Project</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Project</em>' attribute.
	 * @see #getProject()
	 * @generated
	 */
	void setProject(String value);

} // CreateReview
