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
 * A representation of the model object '<em><b>Commit Info</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.egerrit.internal.model.CommitInfo#getCommit <em>Commit</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.CommitInfo#getParents <em>Parents</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.CommitInfo#getAuthor <em>Author</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.CommitInfo#getCommitter <em>Committer</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.CommitInfo#getSubject <em>Subject</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.CommitInfo#getMessage <em>Message</em>}</li>
 * </ul>
 *
 * @see org.eclipse.egerrit.internal.model.ModelPackage#getCommitInfo()
 * @model
 * @generated
 */
public interface CommitInfo extends EObject {
	/**
	 * Returns the value of the '<em><b>Commit</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Commit</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Commit</em>' attribute.
	 * @see #setCommit(String)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getCommitInfo_Commit()
	 * @model
	 * @generated
	 */
	String getCommit();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.CommitInfo#getCommit <em>Commit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Commit</em>' attribute.
	 * @see #getCommit()
	 * @generated
	 */
	void setCommit(String value);

	/**
	 * Returns the value of the '<em><b>Parents</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.egerrit.internal.model.CommitInfo}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parents</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parents</em>' containment reference list.
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getCommitInfo_Parents()
	 * @model containment="true"
	 * @generated
	 */
	EList<CommitInfo> getParents();

	/**
	 * Returns the value of the '<em><b>Author</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Author</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Author</em>' containment reference.
	 * @see #setAuthor(GitPersonInfo)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getCommitInfo_Author()
	 * @model containment="true"
	 * @generated
	 */
	GitPersonInfo getAuthor();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.CommitInfo#getAuthor <em>Author</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Author</em>' containment reference.
	 * @see #getAuthor()
	 * @generated
	 */
	void setAuthor(GitPersonInfo value);

	/**
	 * Returns the value of the '<em><b>Committer</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Committer</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Committer</em>' containment reference.
	 * @see #setCommitter(GitPersonInfo)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getCommitInfo_Committer()
	 * @model containment="true"
	 * @generated
	 */
	GitPersonInfo getCommitter();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.CommitInfo#getCommitter <em>Committer</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Committer</em>' containment reference.
	 * @see #getCommitter()
	 * @generated
	 */
	void setCommitter(GitPersonInfo value);

	/**
	 * Returns the value of the '<em><b>Subject</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Subject</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Subject</em>' attribute.
	 * @see #setSubject(String)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getCommitInfo_Subject()
	 * @model
	 * @generated
	 */
	String getSubject();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.CommitInfo#getSubject <em>Subject</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Subject</em>' attribute.
	 * @see #getSubject()
	 * @generated
	 */
	void setSubject(String value);

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
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getCommitInfo_Message()
	 * @model
	 * @generated
	 */
	String getMessage();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.CommitInfo#getMessage <em>Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Message</em>' attribute.
	 * @see #getMessage()
	 * @generated
	 */
	void setMessage(String value);

} // CommitInfo
