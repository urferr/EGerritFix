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

import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Revision Info</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.egerrit.internal.model.RevisionInfo#isDraft <em>Draft</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.RevisionInfo#isHas_draft_comments <em>Has draft comments</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.RevisionInfo#get_number <em>number</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.RevisionInfo#getRef <em>Ref</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.RevisionInfo#getFetch <em>Fetch</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.RevisionInfo#getCommit <em>Commit</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.RevisionInfo#getFiles <em>Files</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.RevisionInfo#getActions <em>Actions</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.RevisionInfo#isReviewed <em>Reviewed</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.RevisionInfo#getId <em>Id</em>}</li>
 * </ul>
 *
 * @see org.eclipse.egerrit.internal.model.ModelPackage#getRevisionInfo()
 * @model
 * @generated
 */
public interface RevisionInfo extends EObject {
	/**
	 * Returns the value of the '<em><b>Draft</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Draft</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Draft</em>' attribute.
	 * @see #setDraft(boolean)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getRevisionInfo_Draft()
	 * @model
	 * @generated
	 */
	boolean isDraft();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.RevisionInfo#isDraft <em>Draft</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Draft</em>' attribute.
	 * @see #isDraft()
	 * @generated
	 */
	void setDraft(boolean value);

	/**
	 * Returns the value of the '<em><b>Has draft comments</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Has draft comments</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Has draft comments</em>' attribute.
	 * @see #setHas_draft_comments(boolean)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getRevisionInfo_Has_draft_comments()
	 * @model
	 * @generated
	 */
	boolean isHas_draft_comments();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.RevisionInfo#isHas_draft_comments <em>Has draft comments</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Has draft comments</em>' attribute.
	 * @see #isHas_draft_comments()
	 * @generated
	 */
	void setHas_draft_comments(boolean value);

	/**
	 * Returns the value of the '<em><b>number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>number</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>number</em>' attribute.
	 * @see #set_number(int)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getRevisionInfo__number()
	 * @model
	 * @generated
	 */
	int get_number();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.RevisionInfo#get_number <em>number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>number</em>' attribute.
	 * @see #get_number()
	 * @generated
	 */
	void set_number(int value);

	/**
	 * Returns the value of the '<em><b>Ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ref</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ref</em>' attribute.
	 * @see #setRef(String)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getRevisionInfo_Ref()
	 * @model
	 * @generated
	 */
	String getRef();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.RevisionInfo#getRef <em>Ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ref</em>' attribute.
	 * @see #getRef()
	 * @generated
	 */
	void setRef(String value);

	/**
	 * Returns the value of the '<em><b>Fetch</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link org.eclipse.egerrit.internal.model.FetchInfo},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Fetch</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fetch</em>' map.
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getRevisionInfo_Fetch()
	 * @model mapType="org.eclipse.egerrit.internal.model.StringToFetchInfo<org.eclipse.emf.ecore.EString, org.eclipse.egerrit.internal.model.FetchInfo>"
	 * @generated
	 */
	EMap<String, FetchInfo> getFetch();

	/**
	 * Returns the value of the '<em><b>Commit</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Commit</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Commit</em>' containment reference.
	 * @see #setCommit(CommitInfo)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getRevisionInfo_Commit()
	 * @model containment="true"
	 * @generated
	 */
	CommitInfo getCommit();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.RevisionInfo#getCommit <em>Commit</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Commit</em>' containment reference.
	 * @see #getCommit()
	 * @generated
	 */
	void setCommit(CommitInfo value);

	/**
	 * Returns the value of the '<em><b>Files</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link org.eclipse.egerrit.internal.model.FileInfo},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Files</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Files</em>' map.
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getRevisionInfo_Files()
	 * @model mapType="org.eclipse.egerrit.internal.model.StringToFileInfo<org.eclipse.emf.ecore.EString, org.eclipse.egerrit.internal.model.FileInfo>"
	 * @generated
	 */
	EMap<String, FileInfo> getFiles();

	/**
	 * Returns the value of the '<em><b>Actions</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link org.eclipse.egerrit.internal.model.ActionInfo},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Actions</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Actions</em>' map.
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getRevisionInfo_Actions()
	 * @model mapType="org.eclipse.egerrit.internal.model.StringToActionInfo<org.eclipse.emf.ecore.EString, org.eclipse.egerrit.internal.model.ActionInfo>"
	 * @generated
	 */
	EMap<String, ActionInfo> getActions();

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
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getRevisionInfo_Reviewed()
	 * @model
	 * @generated
	 */
	boolean isReviewed();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.RevisionInfo#isReviewed <em>Reviewed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Reviewed</em>' attribute.
	 * @see #isReviewed()
	 * @generated
	 */
	void setReviewed(boolean value);

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
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getRevisionInfo_Id()
	 * @model
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.RevisionInfo#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	boolean isActionAllowed(String action);

} // RevisionInfo
