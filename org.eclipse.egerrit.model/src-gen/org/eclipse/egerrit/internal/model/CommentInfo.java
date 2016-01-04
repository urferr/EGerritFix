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
 * A representation of the model object '<em><b>Comment Info</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.egerrit.internal.model.CommentInfo#getId <em>Id</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.CommentInfo#getPath <em>Path</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.CommentInfo#getSide <em>Side</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.CommentInfo#getLine <em>Line</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.CommentInfo#getRange <em>Range</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.CommentInfo#getInReplyTo <em>In Reply To</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.CommentInfo#getMessage <em>Message</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.CommentInfo#getUpdated <em>Updated</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.CommentInfo#getAuthor <em>Author</em>}</li>
 * </ul>
 *
 * @see org.eclipse.egerrit.internal.model.ModelPackage#getCommentInfo()
 * @model
 * @generated
 */
public interface CommentInfo extends EObject {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getCommentInfo_Id()
	 * @model
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.CommentInfo#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Path</em>' attribute.
	 * @see #setPath(String)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getCommentInfo_Path()
	 * @model
	 * @generated
	 */
	String getPath();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.CommentInfo#getPath <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Path</em>' attribute.
	 * @see #getPath()
	 * @generated
	 */
	void setPath(String value);

	/**
	 * Returns the value of the '<em><b>Side</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Side</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Side</em>' attribute.
	 * @see #setSide(String)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getCommentInfo_Side()
	 * @model
	 * @generated
	 */
	String getSide();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.CommentInfo#getSide <em>Side</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Side</em>' attribute.
	 * @see #getSide()
	 * @generated
	 */
	void setSide(String value);

	/**
	 * Returns the value of the '<em><b>Line</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Line</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Line</em>' attribute.
	 * @see #setLine(int)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getCommentInfo_Line()
	 * @model
	 * @generated
	 */
	int getLine();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.CommentInfo#getLine <em>Line</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Line</em>' attribute.
	 * @see #getLine()
	 * @generated
	 */
	void setLine(int value);

	/**
	 * Returns the value of the '<em><b>Range</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Range</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Range</em>' containment reference.
	 * @see #setRange(CommentRange)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getCommentInfo_Range()
	 * @model containment="true"
	 * @generated
	 */
	CommentRange getRange();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.CommentInfo#getRange <em>Range</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Range</em>' containment reference.
	 * @see #getRange()
	 * @generated
	 */
	void setRange(CommentRange value);

	/**
	 * Returns the value of the '<em><b>In Reply To</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>In Reply To</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>In Reply To</em>' attribute.
	 * @see #setInReplyTo(String)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getCommentInfo_InReplyTo()
	 * @model
	 * @generated
	 */
	String getInReplyTo();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.CommentInfo#getInReplyTo <em>In Reply To</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>In Reply To</em>' attribute.
	 * @see #getInReplyTo()
	 * @generated
	 */
	void setInReplyTo(String value);

	/**
	 * Returns the value of the '<em><b>Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Message</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Message</em>' attribute.
	 * @see #setMessage(String)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getCommentInfo_Message()
	 * @model
	 * @generated
	 */
	String getMessage();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.CommentInfo#getMessage <em>Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Message</em>' attribute.
	 * @see #getMessage()
	 * @generated
	 */
	void setMessage(String value);

	/**
	 * Returns the value of the '<em><b>Updated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Updated</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Updated</em>' attribute.
	 * @see #setUpdated(String)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getCommentInfo_Updated()
	 * @model
	 * @generated
	 */
	String getUpdated();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.CommentInfo#getUpdated <em>Updated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Updated</em>' attribute.
	 * @see #getUpdated()
	 * @generated
	 */
	void setUpdated(String value);

	/**
	 * Returns the value of the '<em><b>Author</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Author</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Author</em>' containment reference.
	 * @see #setAuthor(AccountInfo)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getCommentInfo_Author()
	 * @model containment="true"
	 * @generated
	 */
	AccountInfo getAuthor();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.CommentInfo#getAuthor <em>Author</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Author</em>' containment reference.
	 * @see #getAuthor()
	 * @generated
	 */
	void setAuthor(AccountInfo value);

} // CommentInfo
