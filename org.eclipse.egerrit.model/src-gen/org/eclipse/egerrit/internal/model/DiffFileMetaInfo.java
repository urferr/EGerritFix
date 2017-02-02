/**
 *   Copyright (c) 2017 Ericsson AB
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
 * A representation of the model object '<em><b>Diff File Meta Info</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.egerrit.internal.model.DiffFileMetaInfo#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.DiffFileMetaInfo#getContent_type <em>Content type</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.DiffFileMetaInfo#getLines <em>Lines</em>}</li>
 * </ul>
 *
 * @see org.eclipse.egerrit.internal.model.ModelPackage#getDiffFileMetaInfo()
 * @model
 * @generated
 */
public interface DiffFileMetaInfo extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getDiffFileMetaInfo_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.DiffFileMetaInfo#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Content type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Content type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Content type</em>' attribute.
	 * @see #setContent_type(String)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getDiffFileMetaInfo_Content_type()
	 * @model
	 * @generated
	 */
	String getContent_type();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.DiffFileMetaInfo#getContent_type <em>Content type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Content type</em>' attribute.
	 * @see #getContent_type()
	 * @generated
	 */
	void setContent_type(String value);

	/**
	 * Returns the value of the '<em><b>Lines</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Lines</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Lines</em>' attribute.
	 * @see #setLines(String)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getDiffFileMetaInfo_Lines()
	 * @model
	 * @generated
	 */
	String getLines();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.DiffFileMetaInfo#getLines <em>Lines</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lines</em>' attribute.
	 * @see #getLines()
	 * @generated
	 */
	void setLines(String value);

} // DiffFileMetaInfo
