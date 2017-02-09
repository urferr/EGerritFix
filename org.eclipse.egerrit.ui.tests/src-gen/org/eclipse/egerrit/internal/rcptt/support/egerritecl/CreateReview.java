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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Create Review</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.CreateReview#getServer <em>Server</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.CreateReview#getProject <em>Project</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.CreateReview#isIsDraft <em>Is Draft</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.CreateReview#getFilename <em>Filename</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.CreateReview#getFileContent <em>File Content</em>}</li>
 * </ul>
 *
 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.egerriteclPackage#getCreateReview()
 * @model annotation="http://www.eclipse.org/ecl/docs description='Create a review' returns='A repo description object'"
 * @generated
 */
public interface CreateReview extends Command {
	/**
	 * Returns the value of the '<em><b>Server</b></em>' attribute. The default value is <code>""</code>. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Server</em>' attribute isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Server</em>' attribute.
	 * @see #setServer(String)
	 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.egerriteclPackage#getCreateReview_Server()
	 * @model default=""
	 * @generated
	 */
	String getServer();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.CreateReview#getServer <em>Server</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Server</em>' attribute.
	 * @see #getServer()
	 * @generated
	 */
	void setServer(String value);

	/**
	 * Returns the value of the '<em><b>Project</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Project</em>' attribute isn't clear, there really should be more of a description
	 * here...
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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Project</em>' attribute.
	 * @see #getProject()
	 * @generated
	 */
	void setProject(String value);

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
	 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.egerriteclPackage#getCreateReview_IsDraft()
	 * @model default="false"
	 * @generated
	 */
	boolean isIsDraft();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.CreateReview#isIsDraft <em>Is Draft</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Draft</em>' attribute.
	 * @see #isIsDraft()
	 * @generated
	 */
	void setIsDraft(boolean value);

	/**
	 * Returns the value of the '<em><b>Filename</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Filename</em>' attribute isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Filename</em>' attribute.
	 * @see #setFilename(String)
	 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.egerriteclPackage#getCreateReview_Filename()
	 * @model
	 * @generated
	 */
	String getFilename();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.CreateReview#getFilename <em>Filename</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Filename</em>' attribute.
	 * @see #getFilename()
	 * @generated
	 */
	void setFilename(String value);

	/**
	 * Returns the value of the '<em><b>File Content</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>File Content</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>File Content</em>' attribute.
	 * @see #setFileContent(String)
	 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.egerriteclPackage#getCreateReview_FileContent()
	 * @model
	 * @generated
	 */
	String getFileContent();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.CreateReview#getFileContent <em>File Content</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>File Content</em>' attribute.
	 * @see #getFileContent()
	 * @generated
	 */
	void setFileContent(String value);

} // CreateReview
