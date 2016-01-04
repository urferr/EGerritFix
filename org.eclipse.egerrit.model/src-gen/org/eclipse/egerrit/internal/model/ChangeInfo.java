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
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Change Info</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.egerrit.internal.model.ChangeInfo#getKind <em>Kind</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.ChangeInfo#getId <em>Id</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.ChangeInfo#getProject <em>Project</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.ChangeInfo#getBranch <em>Branch</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.ChangeInfo#getTopic <em>Topic</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.ChangeInfo#getChange_id <em>Change id</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.ChangeInfo#getSubject <em>Subject</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.ChangeInfo#getStatus <em>Status</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.ChangeInfo#getCreated <em>Created</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.ChangeInfo#getUpdated <em>Updated</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.ChangeInfo#isStarred <em>Starred</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.ChangeInfo#isReviewed <em>Reviewed</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.ChangeInfo#isMergeable <em>Mergeable</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.ChangeInfo#getInsertions <em>Insertions</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.ChangeInfo#getDeletions <em>Deletions</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.ChangeInfo#get_sortkey <em>sortkey</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.ChangeInfo#get_number <em>number</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.ChangeInfo#getOwner <em>Owner</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.ChangeInfo#getActions <em>Actions</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.ChangeInfo#getLabels <em>Labels</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.ChangeInfo#getPermitted_labels <em>Permitted labels</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.ChangeInfo#getRemovable_reviewers <em>Removable reviewers</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.ChangeInfo#getMessages <em>Messages</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.ChangeInfo#getCurrent_revision <em>Current revision</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.ChangeInfo#getRevisions <em>Revisions</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.ChangeInfo#is_more_changes <em>more changes</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.ChangeInfo#getProblems <em>Problems</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.ChangeInfo#getBase_change <em>Base change</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.ChangeInfo#getCodeReviewedTally <em>Code Reviewed Tally</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.ChangeInfo#getVerifiedTally <em>Verified Tally</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.ChangeInfo#getIncludedIn <em>Included In</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.ChangeInfo#getHashtags <em>Hashtags</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.ChangeInfo#getRelatedChanges <em>Related Changes</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.ChangeInfo#getReviewers <em>Reviewers</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.ChangeInfo#getSameTopic <em>Same Topic</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.ChangeInfo#getConflictsWith <em>Conflicts With</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.ChangeInfo#getMergeableInfo <em>Mergeable Info</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.ChangeInfo#getRevision <em>Revision</em>}</li>
 * </ul>
 *
 * @see org.eclipse.egerrit.internal.model.ModelPackage#getChangeInfo()
 * @model
 * @generated
 */
public interface ChangeInfo extends EObject {
	/**
	 * Returns the value of the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Kind</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Kind</em>' attribute.
	 * @see #setKind(String)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getChangeInfo_Kind()
	 * @model
	 * @generated
	 */
	String getKind();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.ChangeInfo#getKind <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Kind</em>' attribute.
	 * @see #getKind()
	 * @generated
	 */
	void setKind(String value);

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
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getChangeInfo_Id()
	 * @model
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.ChangeInfo#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

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
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getChangeInfo_Project()
	 * @model
	 * @generated
	 */
	String getProject();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.ChangeInfo#getProject <em>Project</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Project</em>' attribute.
	 * @see #getProject()
	 * @generated
	 */
	void setProject(String value);

	/**
	 * Returns the value of the '<em><b>Branch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Branch</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Branch</em>' attribute.
	 * @see #setBranch(String)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getChangeInfo_Branch()
	 * @model
	 * @generated
	 */
	String getBranch();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.ChangeInfo#getBranch <em>Branch</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Branch</em>' attribute.
	 * @see #getBranch()
	 * @generated
	 */
	void setBranch(String value);

	/**
	 * Returns the value of the '<em><b>Topic</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Topic</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Topic</em>' attribute.
	 * @see #setTopic(String)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getChangeInfo_Topic()
	 * @model
	 * @generated
	 */
	String getTopic();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.ChangeInfo#getTopic <em>Topic</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Topic</em>' attribute.
	 * @see #getTopic()
	 * @generated
	 */
	void setTopic(String value);

	/**
	 * Returns the value of the '<em><b>Change id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Change id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Change id</em>' attribute.
	 * @see #setChange_id(String)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getChangeInfo_Change_id()
	 * @model
	 * @generated
	 */
	String getChange_id();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.ChangeInfo#getChange_id <em>Change id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Change id</em>' attribute.
	 * @see #getChange_id()
	 * @generated
	 */
	void setChange_id(String value);

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
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getChangeInfo_Subject()
	 * @model
	 * @generated
	 */
	String getSubject();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.ChangeInfo#getSubject <em>Subject</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Subject</em>' attribute.
	 * @see #getSubject()
	 * @generated
	 */
	void setSubject(String value);

	/**
	 * Returns the value of the '<em><b>Status</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Status</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Status</em>' attribute.
	 * @see #setStatus(String)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getChangeInfo_Status()
	 * @model
	 * @generated
	 */
	String getStatus();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.ChangeInfo#getStatus <em>Status</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Status</em>' attribute.
	 * @see #getStatus()
	 * @generated
	 */
	void setStatus(String value);

	/**
	 * Returns the value of the '<em><b>Created</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Created</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Created</em>' attribute.
	 * @see #setCreated(String)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getChangeInfo_Created()
	 * @model
	 * @generated
	 */
	String getCreated();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.ChangeInfo#getCreated <em>Created</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Created</em>' attribute.
	 * @see #getCreated()
	 * @generated
	 */
	void setCreated(String value);

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
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getChangeInfo_Updated()
	 * @model
	 * @generated
	 */
	String getUpdated();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.ChangeInfo#getUpdated <em>Updated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Updated</em>' attribute.
	 * @see #getUpdated()
	 * @generated
	 */
	void setUpdated(String value);

	/**
	 * Returns the value of the '<em><b>Starred</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Starred</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Starred</em>' attribute.
	 * @see #setStarred(boolean)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getChangeInfo_Starred()
	 * @model
	 * @generated
	 */
	boolean isStarred();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.ChangeInfo#isStarred <em>Starred</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Starred</em>' attribute.
	 * @see #isStarred()
	 * @generated
	 */
	void setStarred(boolean value);

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
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getChangeInfo_Reviewed()
	 * @model
	 * @generated
	 */
	boolean isReviewed();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.ChangeInfo#isReviewed <em>Reviewed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Reviewed</em>' attribute.
	 * @see #isReviewed()
	 * @generated
	 */
	void setReviewed(boolean value);

	/**
	 * Returns the value of the '<em><b>Mergeable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mergeable</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mergeable</em>' attribute.
	 * @see #setMergeable(boolean)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getChangeInfo_Mergeable()
	 * @model
	 * @generated
	 */
	boolean isMergeable();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.ChangeInfo#isMergeable <em>Mergeable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mergeable</em>' attribute.
	 * @see #isMergeable()
	 * @generated
	 */
	void setMergeable(boolean value);

	/**
	 * Returns the value of the '<em><b>Insertions</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Insertions</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Insertions</em>' attribute.
	 * @see #setInsertions(int)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getChangeInfo_Insertions()
	 * @model
	 * @generated
	 */
	int getInsertions();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.ChangeInfo#getInsertions <em>Insertions</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Insertions</em>' attribute.
	 * @see #getInsertions()
	 * @generated
	 */
	void setInsertions(int value);

	/**
	 * Returns the value of the '<em><b>Deletions</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Deletions</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Deletions</em>' attribute.
	 * @see #setDeletions(int)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getChangeInfo_Deletions()
	 * @model
	 * @generated
	 */
	int getDeletions();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.ChangeInfo#getDeletions <em>Deletions</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Deletions</em>' attribute.
	 * @see #getDeletions()
	 * @generated
	 */
	void setDeletions(int value);

	/**
	 * Returns the value of the '<em><b>sortkey</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>sortkey</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>sortkey</em>' attribute.
	 * @see #set_sortkey(String)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getChangeInfo__sortkey()
	 * @model
	 * @generated
	 */
	String get_sortkey();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.ChangeInfo#get_sortkey <em>sortkey</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>sortkey</em>' attribute.
	 * @see #get_sortkey()
	 * @generated
	 */
	void set_sortkey(String value);

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
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getChangeInfo__number()
	 * @model
	 * @generated
	 */
	int get_number();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.ChangeInfo#get_number <em>number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>number</em>' attribute.
	 * @see #get_number()
	 * @generated
	 */
	void set_number(int value);

	/**
	 * Returns the value of the '<em><b>Owner</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owner</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Owner</em>' containment reference.
	 * @see #setOwner(AccountInfo)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getChangeInfo_Owner()
	 * @model containment="true"
	 * @generated
	 */
	AccountInfo getOwner();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.ChangeInfo#getOwner <em>Owner</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Owner</em>' containment reference.
	 * @see #getOwner()
	 * @generated
	 */
	void setOwner(AccountInfo value);

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
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getChangeInfo_Actions()
	 * @model mapType="org.eclipse.egerrit.internal.model.StringToActionInfo<org.eclipse.emf.ecore.EString, org.eclipse.egerrit.internal.model.ActionInfo>"
	 * @generated
	 */
	EMap<String, ActionInfo> getActions();

	/**
	 * Returns the value of the '<em><b>Labels</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link org.eclipse.egerrit.internal.model.LabelInfo},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Labels</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Labels</em>' map.
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getChangeInfo_Labels()
	 * @model mapType="org.eclipse.egerrit.internal.model.StringToLabelInfo<org.eclipse.emf.ecore.EString, org.eclipse.egerrit.internal.model.LabelInfo>"
	 * @generated
	 */
	EMap<String, LabelInfo> getLabels();

	/**
	 * Returns the value of the '<em><b>Permitted labels</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type list of {@link java.lang.String},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Permitted labels</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Permitted labels</em>' map.
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getChangeInfo_Permitted_labels()
	 * @model mapType="org.eclipse.egerrit.internal.model.StringToListOfString<org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString>"
	 * @generated
	 */
	EMap<String, EList<String>> getPermitted_labels();

	/**
	 * Returns the value of the '<em><b>Removable reviewers</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.egerrit.internal.model.AccountInfo}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Removable reviewers</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Removable reviewers</em>' containment reference list.
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getChangeInfo_Removable_reviewers()
	 * @model containment="true"
	 * @generated
	 */
	EList<AccountInfo> getRemovable_reviewers();

	/**
	 * Returns the value of the '<em><b>Messages</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.egerrit.internal.model.ChangeMessageInfo}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Messages</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Messages</em>' containment reference list.
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getChangeInfo_Messages()
	 * @model containment="true"
	 * @generated
	 */
	EList<ChangeMessageInfo> getMessages();

	/**
	 * Returns the value of the '<em><b>Current revision</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Current revision</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Current revision</em>' attribute.
	 * @see #setCurrent_revision(String)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getChangeInfo_Current_revision()
	 * @model
	 * @generated
	 */
	String getCurrent_revision();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.ChangeInfo#getCurrent_revision <em>Current revision</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Current revision</em>' attribute.
	 * @see #getCurrent_revision()
	 * @generated
	 */
	void setCurrent_revision(String value);

	/**
	 * Returns the value of the '<em><b>Revisions</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link org.eclipse.egerrit.internal.model.RevisionInfo},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Revisions</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Revisions</em>' map.
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getChangeInfo_Revisions()
	 * @model mapType="org.eclipse.egerrit.internal.model.StringToRevisionInfo<org.eclipse.emf.ecore.EString, org.eclipse.egerrit.internal.model.RevisionInfo>"
	 * @generated
	 */
	EMap<String, RevisionInfo> getRevisions();

	/**
	 * Returns the value of the '<em><b>more changes</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>more changes</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>more changes</em>' attribute.
	 * @see #set_more_changes(boolean)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getChangeInfo__more_changes()
	 * @model
	 * @generated
	 */
	boolean is_more_changes();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.ChangeInfo#is_more_changes <em>more changes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>more changes</em>' attribute.
	 * @see #is_more_changes()
	 * @generated
	 */
	void set_more_changes(boolean value);

	/**
	 * Returns the value of the '<em><b>Problems</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.egerrit.internal.model.ProblemInfo}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Problems</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Problems</em>' containment reference list.
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getChangeInfo_Problems()
	 * @model containment="true"
	 * @generated
	 */
	EList<ProblemInfo> getProblems();

	/**
	 * Returns the value of the '<em><b>Base change</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Base change</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Base change</em>' attribute.
	 * @see #setBase_change(String)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getChangeInfo_Base_change()
	 * @model
	 * @generated
	 */
	String getBase_change();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.ChangeInfo#getBase_change <em>Base change</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Base change</em>' attribute.
	 * @see #getBase_change()
	 * @generated
	 */
	void setBase_change(String value);

	/**
	 * Returns the value of the '<em><b>Code Reviewed Tally</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Code Reviewed Tally</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Code Reviewed Tally</em>' attribute.
	 * @see #setCodeReviewedTally(int)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getChangeInfo_CodeReviewedTally()
	 * @model
	 * @generated
	 */
	int getCodeReviewedTally();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.ChangeInfo#getCodeReviewedTally <em>Code Reviewed Tally</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Code Reviewed Tally</em>' attribute.
	 * @see #getCodeReviewedTally()
	 * @generated
	 */
	void setCodeReviewedTally(int value);

	/**
	 * Returns the value of the '<em><b>Verified Tally</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Verified Tally</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Verified Tally</em>' attribute.
	 * @see #setVerifiedTally(int)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getChangeInfo_VerifiedTally()
	 * @model
	 * @generated
	 */
	int getVerifiedTally();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.ChangeInfo#getVerifiedTally <em>Verified Tally</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Verified Tally</em>' attribute.
	 * @see #getVerifiedTally()
	 * @generated
	 */
	void setVerifiedTally(int value);

	/**
	 * Returns the value of the '<em><b>Included In</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Included In</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Included In</em>' containment reference.
	 * @see #setIncludedIn(IncludedInInfo)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getChangeInfo_IncludedIn()
	 * @model containment="true"
	 * @generated
	 */
	IncludedInInfo getIncludedIn();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.ChangeInfo#getIncludedIn <em>Included In</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Included In</em>' containment reference.
	 * @see #getIncludedIn()
	 * @generated
	 */
	void setIncludedIn(IncludedInInfo value);

	/**
	 * Returns the value of the '<em><b>Hashtags</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Hashtags</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Hashtags</em>' attribute list.
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getChangeInfo_Hashtags()
	 * @model
	 * @generated
	 */
	EList<String> getHashtags();

	/**
	 * Returns the value of the '<em><b>Related Changes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Related Changes</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Related Changes</em>' containment reference.
	 * @see #setRelatedChanges(RelatedChangesInfo)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getChangeInfo_RelatedChanges()
	 * @model containment="true"
	 * @generated
	 */
	RelatedChangesInfo getRelatedChanges();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.ChangeInfo#getRelatedChanges <em>Related Changes</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Related Changes</em>' containment reference.
	 * @see #getRelatedChanges()
	 * @generated
	 */
	void setRelatedChanges(RelatedChangesInfo value);

	/**
	 * Returns the value of the '<em><b>Reviewers</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.egerrit.internal.model.ReviewerInfo}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Reviewers</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Reviewers</em>' containment reference list.
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getChangeInfo_Reviewers()
	 * @model containment="true"
	 * @generated
	 */
	EList<ReviewerInfo> getReviewers();

	/**
	 * Returns the value of the '<em><b>Same Topic</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.egerrit.internal.model.ChangeInfo}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Same Topic</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Same Topic</em>' reference list.
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getChangeInfo_SameTopic()
	 * @model
	 * @generated
	 */
	EList<ChangeInfo> getSameTopic();

	/**
	 * Returns the value of the '<em><b>Conflicts With</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.egerrit.internal.model.ChangeInfo}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Conflicts With</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Conflicts With</em>' reference list.
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getChangeInfo_ConflictsWith()
	 * @model
	 * @generated
	 */
	EList<ChangeInfo> getConflictsWith();

	/**
	 * Returns the value of the '<em><b>Mergeable Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mergeable Info</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mergeable Info</em>' containment reference.
	 * @see #setMergeableInfo(MergeableInfo)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getChangeInfo_MergeableInfo()
	 * @model containment="true"
	 * @generated
	 */
	MergeableInfo getMergeableInfo();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.ChangeInfo#getMergeableInfo <em>Mergeable Info</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mergeable Info</em>' containment reference.
	 * @see #getMergeableInfo()
	 * @generated
	 */
	void setMergeableInfo(MergeableInfo value);

	/**
	 * Returns the value of the '<em><b>Revision</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Revision</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Revision</em>' reference.
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getChangeInfo_Revision()
	 * @model transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	RevisionInfo getRevision();

} // ChangeInfo
