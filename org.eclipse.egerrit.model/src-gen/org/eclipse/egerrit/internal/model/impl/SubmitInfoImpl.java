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
import org.eclipse.egerrit.internal.model.SubmitInfo;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Submit Info</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.SubmitInfoImpl#getStatus <em>Status</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.SubmitInfoImpl#getOn_behalf_of <em>On behalf of</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SubmitInfoImpl extends MinimalEObjectImpl.Container implements SubmitInfo {
	/**
	 * The default value of the '{@link #getStatus() <em>Status</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStatus()
	 * @generated
	 * @ordered
	 */
	protected static final String STATUS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getStatus() <em>Status</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStatus()
	 * @generated
	 * @ordered
	 */
	protected String status = STATUS_EDEFAULT;

	/**
	 * The default value of the '{@link #getOn_behalf_of() <em>On behalf of</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOn_behalf_of()
	 * @generated
	 * @ordered
	 */
	protected static final String ON_BEHALF_OF_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getOn_behalf_of() <em>On behalf of</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOn_behalf_of()
	 * @generated
	 * @ordered
	 */
	protected String on_behalf_of = ON_BEHALF_OF_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SubmitInfoImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.SUBMIT_INFO;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getStatus() {
		return status;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setStatus(String newStatus) {
		String oldStatus = status;
		status = newStatus;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SUBMIT_INFO__STATUS, oldStatus, status));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getOn_behalf_of() {
		return on_behalf_of;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setOn_behalf_of(String newOn_behalf_of) {
		String oldOn_behalf_of = on_behalf_of;
		on_behalf_of = newOn_behalf_of;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SUBMIT_INFO__ON_BEHALF_OF,
					oldOn_behalf_of, on_behalf_of));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case ModelPackage.SUBMIT_INFO__STATUS:
			return getStatus();
		case ModelPackage.SUBMIT_INFO__ON_BEHALF_OF:
			return getOn_behalf_of();
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
		case ModelPackage.SUBMIT_INFO__STATUS:
			setStatus((String) newValue);
			return;
		case ModelPackage.SUBMIT_INFO__ON_BEHALF_OF:
			setOn_behalf_of((String) newValue);
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
		case ModelPackage.SUBMIT_INFO__STATUS:
			setStatus(STATUS_EDEFAULT);
			return;
		case ModelPackage.SUBMIT_INFO__ON_BEHALF_OF:
			setOn_behalf_of(ON_BEHALF_OF_EDEFAULT);
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
		case ModelPackage.SUBMIT_INFO__STATUS:
			return STATUS_EDEFAULT == null ? status != null : !STATUS_EDEFAULT.equals(status);
		case ModelPackage.SUBMIT_INFO__ON_BEHALF_OF:
			return ON_BEHALF_OF_EDEFAULT == null ? on_behalf_of != null : !ON_BEHALF_OF_EDEFAULT.equals(on_behalf_of);
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
		result.append(" (status: "); //$NON-NLS-1$
		result.append(status);
		result.append(", on_behalf_of: "); //$NON-NLS-1$
		result.append(on_behalf_of);
		result.append(')');
		return result.toString();
	}

} //SubmitInfoImpl
