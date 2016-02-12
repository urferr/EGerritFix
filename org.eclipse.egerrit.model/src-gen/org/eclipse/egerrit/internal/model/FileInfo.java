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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>File Info</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.egerrit.internal.model.FileInfo#getStatus <em>Status</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.FileInfo#isBinary <em>Binary</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.FileInfo#getOld_path <em>Old path</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.FileInfo#getLines_inserted <em>Lines inserted</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.FileInfo#getLines_deleted <em>Lines deleted</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.FileInfo#getComments <em>Comments</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.FileInfo#getDraftComments <em>Draft Comments</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.FileInfo#isReviewed <em>Reviewed</em>}</li>
 * </ul>
 *
 * @see org.eclipse.egerrit.internal.model.ModelPackage#getFileInfo()
 * @model
 * @generated
 */
public interface FileInfo extends EObject {
	/**
	 * Returns the value of the '<em><b>Status</b></em>' attribute.
	 * The default value is <code>"M"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Status</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Status</em>' attribute.
	 * @see #setStatus(String)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getFileInfo_Status()
	 * @model default="M"
	 * @generated
	 */
	String getStatus();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.FileInfo#getStatus <em>Status</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Status</em>' attribute.
	 * @see #getStatus()
	 * @generated
	 */
	void setStatus(String value);

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
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getFileInfo_Binary()
	 * @model
	 * @generated
	 */
	boolean isBinary();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.FileInfo#isBinary <em>Binary</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Binary</em>' attribute.
	 * @see #isBinary()
	 * @generated
	 */
	void setBinary(boolean value);

	/**
	 * Returns the value of the '<em><b>Old path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Old path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Old path</em>' attribute.
	 * @see #setOld_path(String)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getFileInfo_Old_path()
	 * @model
	 * @generated
	 */
	String getOld_path();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.FileInfo#getOld_path <em>Old path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Old path</em>' attribute.
	 * @see #getOld_path()
	 * @generated
	 */
	void setOld_path(String value);

	/**
	 * Returns the value of the '<em><b>Lines inserted</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Lines inserted</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Lines inserted</em>' attribute.
	 * @see #setLines_inserted(int)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getFileInfo_Lines_inserted()
	 * @model
	 * @generated
	 */
	int getLines_inserted();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.FileInfo#getLines_inserted <em>Lines inserted</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lines inserted</em>' attribute.
	 * @see #getLines_inserted()
	 * @generated
	 */
	void setLines_inserted(int value);

	/**
	 * Returns the value of the '<em><b>Lines deleted</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Lines deleted</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Lines deleted</em>' attribute.
	 * @see #setLines_deleted(int)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getFileInfo_Lines_deleted()
	 * @model
	 * @generated
	 */
	int getLines_deleted();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.FileInfo#getLines_deleted <em>Lines deleted</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lines deleted</em>' attribute.
	 * @see #getLines_deleted()
	 * @generated
	 */
	void setLines_deleted(int value);

	/**
	 * Returns the value of the '<em><b>Comments</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.egerrit.internal.model.CommentInfo}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Comments</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Comments</em>' containment reference list.
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getFileInfo_Comments()
	 * @model containment="true"
	 * @generated
	 */
	EList<CommentInfo> getComments();

	/**
	 * Returns the value of the '<em><b>Draft Comments</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.egerrit.internal.model.CommentInfo}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Draft Comments</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Draft Comments</em>' containment reference list.
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getFileInfo_DraftComments()
	 * @model containment="true"
	 * @generated
	 */
	EList<CommentInfo> getDraftComments();

	/**
	 * Returns the value of the '<em><b>Reviewed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Reviewed</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Reviewed</em>' attribute.
	 * @see #setReviewed(boolean)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getFileInfo_Reviewed()
	 * @model
	 * @generated
	 */
	boolean isReviewed();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.FileInfo#isReviewed <em>Reviewed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Reviewed</em>' attribute.
	 * @see #isReviewed()
	 * @generated
	 */
	void setReviewed(boolean value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	String getPath();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	RevisionInfo getRevision();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	EList<CommentInfo> getAllComments();

} // FileInfo
