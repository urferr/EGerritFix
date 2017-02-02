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
 * A representation of the model object '<em><b>Diff Content</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.egerrit.internal.model.DiffContent#getA <em>A</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.DiffContent#getB <em>B</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.DiffContent#getAb <em>Ab</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.DiffContent#getSkip <em>Skip</em>}</li>
 * </ul>
 *
 * @see org.eclipse.egerrit.internal.model.ModelPackage#getDiffContent()
 * @model
 * @generated
 */
public interface DiffContent extends EObject {
	/**
	 * Returns the value of the '<em><b>A</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>A</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>A</em>' attribute list.
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getDiffContent_A()
	 * @model unique="false"
	 * @generated
	 */
	EList<String> getA();

	/**
	 * Returns the value of the '<em><b>B</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>B</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>B</em>' attribute list.
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getDiffContent_B()
	 * @model unique="false"
	 * @generated
	 */
	EList<String> getB();

	/**
	 * Returns the value of the '<em><b>Ab</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ab</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ab</em>' attribute list.
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getDiffContent_Ab()
	 * @model unique="false"
	 * @generated
	 */
	EList<String> getAb();

	/**
	 * Returns the value of the '<em><b>Skip</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Skip</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Skip</em>' attribute.
	 * @see #setSkip(int)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getDiffContent_Skip()
	 * @model
	 * @generated
	 */
	int getSkip();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.DiffContent#getSkip <em>Skip</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Skip</em>' attribute.
	 * @see #getSkip()
	 * @generated
	 */
	void setSkip(int value);

} // DiffContent
