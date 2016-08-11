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
package org.eclipse.egerrit.internal.rcptt.support.egerritecl.impl;

import org.eclipse.egerrit.internal.rcptt.support.egerritecl.ReviewDescription;
import org.eclipse.egerrit.internal.rcptt.support.egerritecl.egerriteclPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Review Description</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.impl.ReviewDescriptionImpl#getGerritServerURL <em>Gerrit Server URL</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.impl.ReviewDescriptionImpl#getProjectName <em>Project Name</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.impl.ReviewDescriptionImpl#getLocalClone <em>Local Clone</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.impl.ReviewDescriptionImpl#getLastChangeId <em>Last Change Id</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.impl.ReviewDescriptionImpl#isIsDraft <em>Is Draft</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ReviewDescriptionImpl extends MinimalEObjectImpl.Container implements ReviewDescription {
	/**
	 * The default value of the '{@link #getGerritServerURL() <em>Gerrit Server URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGerritServerURL()
	 * @generated
	 * @ordered
	 */
	protected static final String GERRIT_SERVER_URL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getGerritServerURL() <em>Gerrit Server URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGerritServerURL()
	 * @generated
	 * @ordered
	 */
	protected String gerritServerURL = GERRIT_SERVER_URL_EDEFAULT;

	/**
	 * The default value of the '{@link #getProjectName() <em>Project Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProjectName()
	 * @generated
	 * @ordered
	 */
	protected static final String PROJECT_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getProjectName() <em>Project Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProjectName()
	 * @generated
	 * @ordered
	 */
	protected String projectName = PROJECT_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getLocalClone() <em>Local Clone</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocalClone()
	 * @generated
	 * @ordered
	 */
	protected static final String LOCAL_CLONE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLocalClone() <em>Local Clone</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocalClone()
	 * @generated
	 * @ordered
	 */
	protected String localClone = LOCAL_CLONE_EDEFAULT;

	/**
	 * The default value of the '{@link #getLastChangeId() <em>Last Change Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLastChangeId()
	 * @generated
	 * @ordered
	 */
	protected static final String LAST_CHANGE_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLastChangeId() <em>Last Change Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLastChangeId()
	 * @generated
	 * @ordered
	 */
	protected String lastChangeId = LAST_CHANGE_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #isIsDraft() <em>Is Draft</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsDraft()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_DRAFT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isIsDraft() <em>Is Draft</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsDraft()
	 * @generated
	 * @ordered
	 */
	protected boolean isDraft = IS_DRAFT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ReviewDescriptionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return egerriteclPackage.Literals.REVIEW_DESCRIPTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getGerritServerURL() {
		return gerritServerURL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGerritServerURL(String newGerritServerURL) {
		String oldGerritServerURL = gerritServerURL;
		gerritServerURL = newGerritServerURL;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, egerriteclPackage.REVIEW_DESCRIPTION__GERRIT_SERVER_URL, oldGerritServerURL, gerritServerURL));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProjectName(String newProjectName) {
		String oldProjectName = projectName;
		projectName = newProjectName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, egerriteclPackage.REVIEW_DESCRIPTION__PROJECT_NAME, oldProjectName, projectName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLocalClone() {
		return localClone;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLocalClone(String newLocalClone) {
		String oldLocalClone = localClone;
		localClone = newLocalClone;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, egerriteclPackage.REVIEW_DESCRIPTION__LOCAL_CLONE, oldLocalClone, localClone));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLastChangeId() {
		return lastChangeId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLastChangeId(String newLastChangeId) {
		String oldLastChangeId = lastChangeId;
		lastChangeId = newLastChangeId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, egerriteclPackage.REVIEW_DESCRIPTION__LAST_CHANGE_ID, oldLastChangeId, lastChangeId));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isIsDraft() {
		return isDraft;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIsDraft(boolean newIsDraft) {
		boolean oldIsDraft = isDraft;
		isDraft = newIsDraft;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, egerriteclPackage.REVIEW_DESCRIPTION__IS_DRAFT, oldIsDraft, isDraft));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case egerriteclPackage.REVIEW_DESCRIPTION__GERRIT_SERVER_URL:
				return getGerritServerURL();
			case egerriteclPackage.REVIEW_DESCRIPTION__PROJECT_NAME:
				return getProjectName();
			case egerriteclPackage.REVIEW_DESCRIPTION__LOCAL_CLONE:
				return getLocalClone();
			case egerriteclPackage.REVIEW_DESCRIPTION__LAST_CHANGE_ID:
				return getLastChangeId();
			case egerriteclPackage.REVIEW_DESCRIPTION__IS_DRAFT:
				return isIsDraft();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case egerriteclPackage.REVIEW_DESCRIPTION__GERRIT_SERVER_URL:
				setGerritServerURL((String)newValue);
				return;
			case egerriteclPackage.REVIEW_DESCRIPTION__PROJECT_NAME:
				setProjectName((String)newValue);
				return;
			case egerriteclPackage.REVIEW_DESCRIPTION__LOCAL_CLONE:
				setLocalClone((String)newValue);
				return;
			case egerriteclPackage.REVIEW_DESCRIPTION__LAST_CHANGE_ID:
				setLastChangeId((String)newValue);
				return;
			case egerriteclPackage.REVIEW_DESCRIPTION__IS_DRAFT:
				setIsDraft((Boolean)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case egerriteclPackage.REVIEW_DESCRIPTION__GERRIT_SERVER_URL:
				setGerritServerURL(GERRIT_SERVER_URL_EDEFAULT);
				return;
			case egerriteclPackage.REVIEW_DESCRIPTION__PROJECT_NAME:
				setProjectName(PROJECT_NAME_EDEFAULT);
				return;
			case egerriteclPackage.REVIEW_DESCRIPTION__LOCAL_CLONE:
				setLocalClone(LOCAL_CLONE_EDEFAULT);
				return;
			case egerriteclPackage.REVIEW_DESCRIPTION__LAST_CHANGE_ID:
				setLastChangeId(LAST_CHANGE_ID_EDEFAULT);
				return;
			case egerriteclPackage.REVIEW_DESCRIPTION__IS_DRAFT:
				setIsDraft(IS_DRAFT_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case egerriteclPackage.REVIEW_DESCRIPTION__GERRIT_SERVER_URL:
				return GERRIT_SERVER_URL_EDEFAULT == null ? gerritServerURL != null : !GERRIT_SERVER_URL_EDEFAULT.equals(gerritServerURL);
			case egerriteclPackage.REVIEW_DESCRIPTION__PROJECT_NAME:
				return PROJECT_NAME_EDEFAULT == null ? projectName != null : !PROJECT_NAME_EDEFAULT.equals(projectName);
			case egerriteclPackage.REVIEW_DESCRIPTION__LOCAL_CLONE:
				return LOCAL_CLONE_EDEFAULT == null ? localClone != null : !LOCAL_CLONE_EDEFAULT.equals(localClone);
			case egerriteclPackage.REVIEW_DESCRIPTION__LAST_CHANGE_ID:
				return LAST_CHANGE_ID_EDEFAULT == null ? lastChangeId != null : !LAST_CHANGE_ID_EDEFAULT.equals(lastChangeId);
			case egerriteclPackage.REVIEW_DESCRIPTION__IS_DRAFT:
				return isDraft != IS_DRAFT_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (gerritServerURL: "); //$NON-NLS-1$
		result.append(gerritServerURL);
		result.append(", projectName: "); //$NON-NLS-1$
		result.append(projectName);
		result.append(", localClone: "); //$NON-NLS-1$
		result.append(localClone);
		result.append(", lastChangeId: "); //$NON-NLS-1$
		result.append(lastChangeId);
		result.append(", isDraft: "); //$NON-NLS-1$
		result.append(isDraft);
		result.append(')');
		return result.toString();
	}

} //ReviewDescriptionImpl
