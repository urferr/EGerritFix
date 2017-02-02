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

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Diff Info</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.egerrit.internal.model.DiffInfo#getChange_type <em>Change type</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.DiffInfo#getContent <em>Content</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.DiffInfo#getMeta_a <em>Meta a</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.DiffInfo#getMeta_b <em>Meta b</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.DiffInfo#isBinary <em>Binary</em>}</li>
 * </ul>
 *
 * @see org.eclipse.egerrit.internal.model.ModelPackage#getDiffInfo()
 * @model
 * @generated
 */
public interface DiffInfo extends EObject {
	/**
	 * Returns the value of the '<em><b>Change type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Change type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Change type</em>' attribute.
	 * @see #setChange_type(String)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getDiffInfo_Change_type()
	 * @model
	 * @generated
	 */
	String getChange_type();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.DiffInfo#getChange_type <em>Change type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Change type</em>' attribute.
	 * @see #getChange_type()
	 * @generated
	 */
	void setChange_type(String value);

	/**
	 * Returns the value of the '<em><b>Content</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.egerrit.internal.model.DiffContent}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Content</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Content</em>' reference list.
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getDiffInfo_Content()
	 * @model
	 * @generated
	 */
	EList<DiffContent> getContent();

	/**
	 * Returns the value of the '<em><b>Meta a</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Meta a</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Meta a</em>' reference.
	 * @see #setMeta_a(DiffFileMetaInfo)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getDiffInfo_Meta_a()
	 * @model
	 * @generated
	 */
	DiffFileMetaInfo getMeta_a();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.DiffInfo#getMeta_a <em>Meta a</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Meta a</em>' reference.
	 * @see #getMeta_a()
	 * @generated
	 */
	void setMeta_a(DiffFileMetaInfo value);

	/**
	 * Returns the value of the '<em><b>Meta b</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Meta b</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Meta b</em>' reference.
	 * @see #setMeta_b(DiffFileMetaInfo)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getDiffInfo_Meta_b()
	 * @model
	 * @generated
	 */
	DiffFileMetaInfo getMeta_b();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.DiffInfo#getMeta_b <em>Meta b</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Meta b</em>' reference.
	 * @see #getMeta_b()
	 * @generated
	 */
	void setMeta_b(DiffFileMetaInfo value);

	/**
	 * Returns the value of the '<em><b>Binary</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Binary</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Binary</em>' attribute.
	 * @see #setBinary(boolean)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getDiffInfo_Binary()
	 * @model
	 * @generated
	 */
	boolean isBinary();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.DiffInfo#isBinary <em>Binary</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Binary</em>' attribute.
	 * @see #isBinary()
	 * @generated
	 */
	void setBinary(boolean value);

} // DiffInfo
