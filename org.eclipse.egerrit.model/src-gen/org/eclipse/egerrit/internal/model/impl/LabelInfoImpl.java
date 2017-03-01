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

import java.util.Collection;

import org.eclipse.egerrit.internal.model.AccountInfo;
import org.eclipse.egerrit.internal.model.ApprovalInfo;
import org.eclipse.egerrit.internal.model.LabelInfo;
import org.eclipse.egerrit.internal.model.ModelPackage;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Label Info</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.LabelInfoImpl#isOptional <em>Optional</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.LabelInfoImpl#getApproved <em>Approved</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.LabelInfoImpl#getRejected <em>Rejected</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.LabelInfoImpl#getRecommended <em>Recommended</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.LabelInfoImpl#getDisliked <em>Disliked</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.LabelInfoImpl#isBlocking <em>Blocking</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.LabelInfoImpl#getValue <em>Value</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.LabelInfoImpl#getDefault_value <em>Default value</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.LabelInfoImpl#getAll <em>All</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.LabelInfoImpl#getValues <em>Values</em>}</li>
 * </ul>
 *
 * @generated
 */
public class LabelInfoImpl extends MinimalEObjectImpl.Container implements LabelInfo {
	/**
	 * The default value of the '{@link #isOptional() <em>Optional</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isOptional()
	 * @generated
	 * @ordered
	 */
	protected static final boolean OPTIONAL_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isOptional() <em>Optional</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isOptional()
	 * @generated
	 * @ordered
	 */
	protected boolean optional = OPTIONAL_EDEFAULT;

	/**
	 * The cached value of the '{@link #getApproved() <em>Approved</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getApproved()
	 * @generated
	 * @ordered
	 */
	protected AccountInfo approved;

	/**
	 * The cached value of the '{@link #getRejected() <em>Rejected</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRejected()
	 * @generated
	 * @ordered
	 */
	protected AccountInfo rejected;

	/**
	 * The cached value of the '{@link #getRecommended() <em>Recommended</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRecommended()
	 * @generated
	 * @ordered
	 */
	protected AccountInfo recommended;

	/**
	 * The cached value of the '{@link #getDisliked() <em>Disliked</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDisliked()
	 * @generated
	 * @ordered
	 */
	protected AccountInfo disliked;

	/**
	 * The default value of the '{@link #isBlocking() <em>Blocking</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isBlocking()
	 * @generated
	 * @ordered
	 */
	protected static final boolean BLOCKING_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isBlocking() <em>Blocking</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isBlocking()
	 * @generated
	 * @ordered
	 */
	protected boolean blocking = BLOCKING_EDEFAULT;

	/**
	 * The default value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected static final String VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected String value = VALUE_EDEFAULT;

	/**
	 * The default value of the '{@link #getDefault_value() <em>Default value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefault_value()
	 * @generated
	 * @ordered
	 */
	protected static final String DEFAULT_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDefault_value() <em>Default value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefault_value()
	 * @generated
	 * @ordered
	 */
	protected String default_value = DEFAULT_VALUE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getAll() <em>All</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAll()
	 * @generated
	 * @ordered
	 */
	protected EList<ApprovalInfo> all;

	/**
	 * The cached value of the '{@link #getValues() <em>Values</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValues()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, String> values;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LabelInfoImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.LABEL_INFO;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isOptional() {
		return optional;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setOptional(boolean newOptional) {
		boolean oldOptional = optional;
		optional = newOptional;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.LABEL_INFO__OPTIONAL, oldOptional,
					optional));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AccountInfo getApproved() {
		return approved;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetApproved(AccountInfo newApproved, NotificationChain msgs) {
		AccountInfo oldApproved = approved;
		approved = newApproved;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					ModelPackage.LABEL_INFO__APPROVED, oldApproved, newApproved);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setApproved(AccountInfo newApproved) {
		if (newApproved != approved) {
			NotificationChain msgs = null;
			if (approved != null)
				msgs = ((InternalEObject) approved).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - ModelPackage.LABEL_INFO__APPROVED, null, msgs);
			if (newApproved != null)
				msgs = ((InternalEObject) newApproved).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - ModelPackage.LABEL_INFO__APPROVED, null, msgs);
			msgs = basicSetApproved(newApproved, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.LABEL_INFO__APPROVED, newApproved,
					newApproved));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AccountInfo getRejected() {
		return rejected;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRejected(AccountInfo newRejected, NotificationChain msgs) {
		AccountInfo oldRejected = rejected;
		rejected = newRejected;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					ModelPackage.LABEL_INFO__REJECTED, oldRejected, newRejected);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRejected(AccountInfo newRejected) {
		if (newRejected != rejected) {
			NotificationChain msgs = null;
			if (rejected != null)
				msgs = ((InternalEObject) rejected).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - ModelPackage.LABEL_INFO__REJECTED, null, msgs);
			if (newRejected != null)
				msgs = ((InternalEObject) newRejected).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - ModelPackage.LABEL_INFO__REJECTED, null, msgs);
			msgs = basicSetRejected(newRejected, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.LABEL_INFO__REJECTED, newRejected,
					newRejected));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AccountInfo getRecommended() {
		return recommended;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRecommended(AccountInfo newRecommended, NotificationChain msgs) {
		AccountInfo oldRecommended = recommended;
		recommended = newRecommended;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					ModelPackage.LABEL_INFO__RECOMMENDED, oldRecommended, newRecommended);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRecommended(AccountInfo newRecommended) {
		if (newRecommended != recommended) {
			NotificationChain msgs = null;
			if (recommended != null)
				msgs = ((InternalEObject) recommended).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - ModelPackage.LABEL_INFO__RECOMMENDED, null, msgs);
			if (newRecommended != null)
				msgs = ((InternalEObject) newRecommended).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - ModelPackage.LABEL_INFO__RECOMMENDED, null, msgs);
			msgs = basicSetRecommended(newRecommended, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.LABEL_INFO__RECOMMENDED, newRecommended,
					newRecommended));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AccountInfo getDisliked() {
		return disliked;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDisliked(AccountInfo newDisliked, NotificationChain msgs) {
		AccountInfo oldDisliked = disliked;
		disliked = newDisliked;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					ModelPackage.LABEL_INFO__DISLIKED, oldDisliked, newDisliked);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDisliked(AccountInfo newDisliked) {
		if (newDisliked != disliked) {
			NotificationChain msgs = null;
			if (disliked != null)
				msgs = ((InternalEObject) disliked).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - ModelPackage.LABEL_INFO__DISLIKED, null, msgs);
			if (newDisliked != null)
				msgs = ((InternalEObject) newDisliked).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - ModelPackage.LABEL_INFO__DISLIKED, null, msgs);
			msgs = basicSetDisliked(newDisliked, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.LABEL_INFO__DISLIKED, newDisliked,
					newDisliked));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isBlocking() {
		return blocking;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setBlocking(boolean newBlocking) {
		boolean oldBlocking = blocking;
		blocking = newBlocking;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.LABEL_INFO__BLOCKING, oldBlocking,
					blocking));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setValue(String newValue) {
		String oldValue = value;
		value = newValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.LABEL_INFO__VALUE, oldValue, value));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getDefault_value() {
		return default_value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDefault_value(String newDefault_value) {
		String oldDefault_value = default_value;
		default_value = newDefault_value;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.LABEL_INFO__DEFAULT_VALUE,
					oldDefault_value, default_value));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ApprovalInfo> getAll() {
		if (all == null) {
			all = new EObjectContainmentEList<ApprovalInfo>(ApprovalInfo.class, this, ModelPackage.LABEL_INFO__ALL);
		}
		return all;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EMap<String, String> getValues() {
		if (values == null) {
			values = new EcoreEMap<String, String>(ModelPackage.Literals.STRING_TO_STRING, StringToStringImpl.class,
					this, ModelPackage.LABEL_INFO__VALUES);
		}
		return values;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case ModelPackage.LABEL_INFO__APPROVED:
			return basicSetApproved(null, msgs);
		case ModelPackage.LABEL_INFO__REJECTED:
			return basicSetRejected(null, msgs);
		case ModelPackage.LABEL_INFO__RECOMMENDED:
			return basicSetRecommended(null, msgs);
		case ModelPackage.LABEL_INFO__DISLIKED:
			return basicSetDisliked(null, msgs);
		case ModelPackage.LABEL_INFO__ALL:
			return ((InternalEList<?>) getAll()).basicRemove(otherEnd, msgs);
		case ModelPackage.LABEL_INFO__VALUES:
			return ((InternalEList<?>) getValues()).basicRemove(otherEnd, msgs);
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
		case ModelPackage.LABEL_INFO__OPTIONAL:
			return isOptional();
		case ModelPackage.LABEL_INFO__APPROVED:
			return getApproved();
		case ModelPackage.LABEL_INFO__REJECTED:
			return getRejected();
		case ModelPackage.LABEL_INFO__RECOMMENDED:
			return getRecommended();
		case ModelPackage.LABEL_INFO__DISLIKED:
			return getDisliked();
		case ModelPackage.LABEL_INFO__BLOCKING:
			return isBlocking();
		case ModelPackage.LABEL_INFO__VALUE:
			return getValue();
		case ModelPackage.LABEL_INFO__DEFAULT_VALUE:
			return getDefault_value();
		case ModelPackage.LABEL_INFO__ALL:
			return getAll();
		case ModelPackage.LABEL_INFO__VALUES:
			if (coreType)
				return getValues();
			else
				return getValues().map();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case ModelPackage.LABEL_INFO__OPTIONAL:
			setOptional((Boolean) newValue);
			return;
		case ModelPackage.LABEL_INFO__APPROVED:
			setApproved((AccountInfo) newValue);
			return;
		case ModelPackage.LABEL_INFO__REJECTED:
			setRejected((AccountInfo) newValue);
			return;
		case ModelPackage.LABEL_INFO__RECOMMENDED:
			setRecommended((AccountInfo) newValue);
			return;
		case ModelPackage.LABEL_INFO__DISLIKED:
			setDisliked((AccountInfo) newValue);
			return;
		case ModelPackage.LABEL_INFO__BLOCKING:
			setBlocking((Boolean) newValue);
			return;
		case ModelPackage.LABEL_INFO__VALUE:
			setValue((String) newValue);
			return;
		case ModelPackage.LABEL_INFO__DEFAULT_VALUE:
			setDefault_value((String) newValue);
			return;
		case ModelPackage.LABEL_INFO__ALL:
			getAll().clear();
			getAll().addAll((Collection<? extends ApprovalInfo>) newValue);
			return;
		case ModelPackage.LABEL_INFO__VALUES:
			((EStructuralFeature.Setting) getValues()).set(newValue);
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
		case ModelPackage.LABEL_INFO__OPTIONAL:
			setOptional(OPTIONAL_EDEFAULT);
			return;
		case ModelPackage.LABEL_INFO__APPROVED:
			setApproved((AccountInfo) null);
			return;
		case ModelPackage.LABEL_INFO__REJECTED:
			setRejected((AccountInfo) null);
			return;
		case ModelPackage.LABEL_INFO__RECOMMENDED:
			setRecommended((AccountInfo) null);
			return;
		case ModelPackage.LABEL_INFO__DISLIKED:
			setDisliked((AccountInfo) null);
			return;
		case ModelPackage.LABEL_INFO__BLOCKING:
			setBlocking(BLOCKING_EDEFAULT);
			return;
		case ModelPackage.LABEL_INFO__VALUE:
			setValue(VALUE_EDEFAULT);
			return;
		case ModelPackage.LABEL_INFO__DEFAULT_VALUE:
			setDefault_value(DEFAULT_VALUE_EDEFAULT);
			return;
		case ModelPackage.LABEL_INFO__ALL:
			getAll().clear();
			return;
		case ModelPackage.LABEL_INFO__VALUES:
			getValues().clear();
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
		case ModelPackage.LABEL_INFO__OPTIONAL:
			return optional != OPTIONAL_EDEFAULT;
		case ModelPackage.LABEL_INFO__APPROVED:
			return approved != null;
		case ModelPackage.LABEL_INFO__REJECTED:
			return rejected != null;
		case ModelPackage.LABEL_INFO__RECOMMENDED:
			return recommended != null;
		case ModelPackage.LABEL_INFO__DISLIKED:
			return disliked != null;
		case ModelPackage.LABEL_INFO__BLOCKING:
			return blocking != BLOCKING_EDEFAULT;
		case ModelPackage.LABEL_INFO__VALUE:
			return VALUE_EDEFAULT == null ? value != null : !VALUE_EDEFAULT.equals(value);
		case ModelPackage.LABEL_INFO__DEFAULT_VALUE:
			return DEFAULT_VALUE_EDEFAULT == null
					? default_value != null
					: !DEFAULT_VALUE_EDEFAULT.equals(default_value);
		case ModelPackage.LABEL_INFO__ALL:
			return all != null && !all.isEmpty();
		case ModelPackage.LABEL_INFO__VALUES:
			return values != null && !values.isEmpty();
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
		result.append(" (optional: "); //$NON-NLS-1$
		result.append(optional);
		result.append(", blocking: "); //$NON-NLS-1$
		result.append(blocking);
		result.append(", value: "); //$NON-NLS-1$
		result.append(value);
		result.append(", default_value: "); //$NON-NLS-1$
		result.append(default_value);
		result.append(')');
		return result.toString();
	}

} //LabelInfoImpl
