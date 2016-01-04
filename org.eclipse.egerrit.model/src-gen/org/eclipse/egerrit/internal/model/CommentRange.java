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
 * A representation of the model object '<em><b>Comment Range</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.egerrit.internal.model.CommentRange#getStartLine <em>Start Line</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.CommentRange#getStartCharacter <em>Start Character</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.CommentRange#getEndLine <em>End Line</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.CommentRange#getEndCharacter <em>End Character</em>}</li>
 * </ul>
 *
 * @see org.eclipse.egerrit.internal.model.ModelPackage#getCommentRange()
 * @model
 * @generated
 */
public interface CommentRange extends EObject {

	/**
	 * Returns the value of the '<em><b>Start Line</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Start Line</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Start Line</em>' attribute.
	 * @see #setStartLine(int)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getCommentRange_StartLine()
	 * @model
	 * @generated
	 */
	int getStartLine();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.CommentRange#getStartLine <em>Start Line</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Start Line</em>' attribute.
	 * @see #getStartLine()
	 * @generated
	 */
	void setStartLine(int value);

	/**
	 * Returns the value of the '<em><b>Start Character</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Start Character</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Start Character</em>' attribute.
	 * @see #setStartCharacter(int)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getCommentRange_StartCharacter()
	 * @model
	 * @generated
	 */
	int getStartCharacter();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.CommentRange#getStartCharacter <em>Start Character</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Start Character</em>' attribute.
	 * @see #getStartCharacter()
	 * @generated
	 */
	void setStartCharacter(int value);

	/**
	 * Returns the value of the '<em><b>End Line</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>End Line</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>End Line</em>' attribute.
	 * @see #setEndLine(int)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getCommentRange_EndLine()
	 * @model
	 * @generated
	 */
	int getEndLine();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.CommentRange#getEndLine <em>End Line</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>End Line</em>' attribute.
	 * @see #getEndLine()
	 * @generated
	 */
	void setEndLine(int value);

	/**
	 * Returns the value of the '<em><b>End Character</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>End Character</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>End Character</em>' attribute.
	 * @see #setEndCharacter(int)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getCommentRange_EndCharacter()
	 * @model
	 * @generated
	 */
	int getEndCharacter();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.CommentRange#getEndCharacter <em>End Character</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>End Character</em>' attribute.
	 * @see #getEndCharacter()
	 * @generated
	 */
	void setEndCharacter(int value);
} // CommentRange
