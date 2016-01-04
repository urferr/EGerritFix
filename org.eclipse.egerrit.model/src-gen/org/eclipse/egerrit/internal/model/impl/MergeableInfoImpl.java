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

import org.eclipse.egerrit.internal.model.MergeableInfo;
import org.eclipse.egerrit.internal.model.ModelPackage;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Mergeable Info</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.MergeableInfoImpl#getSubmit_type <em>Submit type</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.MergeableInfoImpl#getMergeable_into <em>Mergeable into</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.MergeableInfoImpl#isMergeable <em>Mergeable</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MergeableInfoImpl extends MinimalEObjectImpl.Container implements MergeableInfo {
	/**
	 * The default value of the '{@link #getSubmit_type() <em>Submit type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubmit_type()
	 * @generated
	 * @ordered
	 */
	protected static final String SUBMIT_TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSubmit_type() <em>Submit type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubmit_type()
	 * @generated
	 * @ordered
	 */
	protected String submit_type = SUBMIT_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getMergeable_into() <em>Mergeable into</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMergeable_into()
	 * @generated
	 * @ordered
	 */
	protected static final String MERGEABLE_INTO_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMergeable_into() <em>Mergeable into</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMergeable_into()
	 * @generated
	 * @ordered
	 */
	protected String mergeable_into = MERGEABLE_INTO_EDEFAULT;

	/**
	 * The default value of the '{@link #isMergeable() <em>Mergeable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMergeable()
	 * @generated
	 * @ordered
	 */
	protected static final boolean MERGEABLE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isMergeable() <em>Mergeable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMergeable()
	 * @generated
	 * @ordered
	 */
	protected boolean mergeable = MERGEABLE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MergeableInfoImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.MERGEABLE_INFO;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getSubmit_type() {
		return submit_type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSubmit_type(String newSubmit_type) {
		String oldSubmit_type = submit_type;
		submit_type = newSubmit_type;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.MERGEABLE_INFO__SUBMIT_TYPE,
					oldSubmit_type, submit_type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getMergeable_into() {
		return mergeable_into;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMergeable_into(String newMergeable_into) {
		String oldMergeable_into = mergeable_into;
		mergeable_into = newMergeable_into;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.MERGEABLE_INFO__MERGEABLE_INTO,
					oldMergeable_into, mergeable_into));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isMergeable() {
		return mergeable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMergeable(boolean newMergeable) {
		boolean oldMergeable = mergeable;
		mergeable = newMergeable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.MERGEABLE_INFO__MERGEABLE, oldMergeable,
					mergeable));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case ModelPackage.MERGEABLE_INFO__SUBMIT_TYPE:
			return getSubmit_type();
		case ModelPackage.MERGEABLE_INFO__MERGEABLE_INTO:
			return getMergeable_into();
		case ModelPackage.MERGEABLE_INFO__MERGEABLE:
			return isMergeable();
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
		case ModelPackage.MERGEABLE_INFO__SUBMIT_TYPE:
			setSubmit_type((String) newValue);
			return;
		case ModelPackage.MERGEABLE_INFO__MERGEABLE_INTO:
			setMergeable_into((String) newValue);
			return;
		case ModelPackage.MERGEABLE_INFO__MERGEABLE:
			setMergeable((Boolean) newValue);
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
		case ModelPackage.MERGEABLE_INFO__SUBMIT_TYPE:
			setSubmit_type(SUBMIT_TYPE_EDEFAULT);
			return;
		case ModelPackage.MERGEABLE_INFO__MERGEABLE_INTO:
			setMergeable_into(MERGEABLE_INTO_EDEFAULT);
			return;
		case ModelPackage.MERGEABLE_INFO__MERGEABLE:
			setMergeable(MERGEABLE_EDEFAULT);
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
		case ModelPackage.MERGEABLE_INFO__SUBMIT_TYPE:
			return SUBMIT_TYPE_EDEFAULT == null ? submit_type != null : !SUBMIT_TYPE_EDEFAULT.equals(submit_type);
		case ModelPackage.MERGEABLE_INFO__MERGEABLE_INTO:
			return MERGEABLE_INTO_EDEFAULT == null ? mergeable_into != null
					: !MERGEABLE_INTO_EDEFAULT.equals(mergeable_into);
		case ModelPackage.MERGEABLE_INFO__MERGEABLE:
			return mergeable != MERGEABLE_EDEFAULT;
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
		result.append(" (submit_type: "); //$NON-NLS-1$
		result.append(submit_type);
		result.append(", mergeable_into: "); //$NON-NLS-1$
		result.append(mergeable_into);
		result.append(", mergeable: "); //$NON-NLS-1$
		result.append(mergeable);
		result.append(')');
		return result.toString();
	}

} //MergeableInfoImpl
