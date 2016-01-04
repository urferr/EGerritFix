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
package org.eclipse.egerrit.internal.model.impl;

import org.eclipse.egerrit.internal.model.ModelPackage;
import org.eclipse.egerrit.internal.model.ReviewerInfo;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Reviewer Info</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.ReviewerInfoImpl#get_account_id <em>account id</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.ReviewerInfoImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.ReviewerInfoImpl#getEmail <em>Email</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.ReviewerInfoImpl#getUsername <em>Username</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.ReviewerInfoImpl#getApprovals <em>Approvals</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ReviewerInfoImpl extends MinimalEObjectImpl.Container implements ReviewerInfo {
	/**
	 * The default value of the '{@link #get_account_id() <em>account id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #get_account_id()
	 * @generated
	 * @ordered
	 */
	protected static final int _ACCOUNT_ID_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #get_account_id() <em>account id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #get_account_id()
	 * @generated
	 * @ordered
	 */
	protected int _account_id = _ACCOUNT_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getEmail() <em>Email</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEmail()
	 * @generated
	 * @ordered
	 */
	protected static final String EMAIL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEmail() <em>Email</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEmail()
	 * @generated
	 * @ordered
	 */
	protected String email = EMAIL_EDEFAULT;

	/**
	 * The default value of the '{@link #getUsername() <em>Username</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUsername()
	 * @generated
	 * @ordered
	 */
	protected static final String USERNAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getUsername() <em>Username</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUsername()
	 * @generated
	 * @ordered
	 */
	protected String username = USERNAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getApprovals() <em>Approvals</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getApprovals()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, String> approvals;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ReviewerInfoImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.REVIEWER_INFO;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int get_account_id() {
		return _account_id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void set_account_id(int new_account_id) {
		int old_account_id = _account_id;
		_account_id = new_account_id;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.REVIEWER_INFO__ACCOUNT_ID,
					old_account_id, _account_id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.REVIEWER_INFO__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getEmail() {
		return email;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setEmail(String newEmail) {
		String oldEmail = email;
		email = newEmail;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.REVIEWER_INFO__EMAIL, oldEmail, email));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getUsername() {
		return username;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setUsername(String newUsername) {
		String oldUsername = username;
		username = newUsername;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.REVIEWER_INFO__USERNAME, oldUsername,
					username));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EMap<String, String> getApprovals() {
		if (approvals == null) {
			approvals = new EcoreEMap<String, String>(ModelPackage.Literals.STRING_TO_STRING, StringToStringImpl.class,
					this, ModelPackage.REVIEWER_INFO__APPROVALS);
		}
		return approvals;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case ModelPackage.REVIEWER_INFO__APPROVALS:
			return ((InternalEList<?>) getApprovals()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case ModelPackage.REVIEWER_INFO__ACCOUNT_ID:
			return get_account_id();
		case ModelPackage.REVIEWER_INFO__NAME:
			return getName();
		case ModelPackage.REVIEWER_INFO__EMAIL:
			return getEmail();
		case ModelPackage.REVIEWER_INFO__USERNAME:
			return getUsername();
		case ModelPackage.REVIEWER_INFO__APPROVALS:
			if (coreType)
				return getApprovals();
			else
				return getApprovals().map();
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
		case ModelPackage.REVIEWER_INFO__ACCOUNT_ID:
			set_account_id((Integer) newValue);
			return;
		case ModelPackage.REVIEWER_INFO__NAME:
			setName((String) newValue);
			return;
		case ModelPackage.REVIEWER_INFO__EMAIL:
			setEmail((String) newValue);
			return;
		case ModelPackage.REVIEWER_INFO__USERNAME:
			setUsername((String) newValue);
			return;
		case ModelPackage.REVIEWER_INFO__APPROVALS:
			((EStructuralFeature.Setting) getApprovals()).set(newValue);
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
		case ModelPackage.REVIEWER_INFO__ACCOUNT_ID:
			set_account_id(_ACCOUNT_ID_EDEFAULT);
			return;
		case ModelPackage.REVIEWER_INFO__NAME:
			setName(NAME_EDEFAULT);
			return;
		case ModelPackage.REVIEWER_INFO__EMAIL:
			setEmail(EMAIL_EDEFAULT);
			return;
		case ModelPackage.REVIEWER_INFO__USERNAME:
			setUsername(USERNAME_EDEFAULT);
			return;
		case ModelPackage.REVIEWER_INFO__APPROVALS:
			getApprovals().clear();
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
		case ModelPackage.REVIEWER_INFO__ACCOUNT_ID:
			return _account_id != _ACCOUNT_ID_EDEFAULT;
		case ModelPackage.REVIEWER_INFO__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
		case ModelPackage.REVIEWER_INFO__EMAIL:
			return EMAIL_EDEFAULT == null ? email != null : !EMAIL_EDEFAULT.equals(email);
		case ModelPackage.REVIEWER_INFO__USERNAME:
			return USERNAME_EDEFAULT == null ? username != null : !USERNAME_EDEFAULT.equals(username);
		case ModelPackage.REVIEWER_INFO__APPROVALS:
			return approvals != null && !approvals.isEmpty();
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
		if (eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (_account_id: "); //$NON-NLS-1$
		result.append(_account_id);
		result.append(", name: "); //$NON-NLS-1$
		result.append(name);
		result.append(", email: "); //$NON-NLS-1$
		result.append(email);
		result.append(", username: "); //$NON-NLS-1$
		result.append(username);
		result.append(')');
		return result.toString();
	}

} //ReviewerInfoImpl
