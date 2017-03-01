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

import org.eclipse.egerrit.internal.model.CommitInfo;
import org.eclipse.egerrit.internal.model.ModelPackage;
import org.eclipse.egerrit.internal.model.RelatedChangeAndCommitInfo;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Related Change And Commit Info</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.RelatedChangeAndCommitInfoImpl#getChange_id <em>Change id</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.RelatedChangeAndCommitInfoImpl#getCommit <em>Commit</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.RelatedChangeAndCommitInfoImpl#get_change_number <em>change number</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.RelatedChangeAndCommitInfoImpl#get_revision_number <em>revision number</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.RelatedChangeAndCommitInfoImpl#get_current_revision_number <em>current revision number</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RelatedChangeAndCommitInfoImpl extends MinimalEObjectImpl.Container implements RelatedChangeAndCommitInfo {
	/**
	 * The default value of the '{@link #getChange_id() <em>Change id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChange_id()
	 * @generated
	 * @ordered
	 */
	protected static final String CHANGE_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getChange_id() <em>Change id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChange_id()
	 * @generated
	 * @ordered
	 */
	protected String change_id = CHANGE_ID_EDEFAULT;

	/**
	 * The cached value of the '{@link #getCommit() <em>Commit</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCommit()
	 * @generated
	 * @ordered
	 */
	protected CommitInfo commit;

	/**
	 * The default value of the '{@link #get_change_number() <em>change number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #get_change_number()
	 * @generated
	 * @ordered
	 */
	protected static final String _CHANGE_NUMBER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #get_change_number() <em>change number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #get_change_number()
	 * @generated
	 * @ordered
	 */
	protected String _change_number = _CHANGE_NUMBER_EDEFAULT;

	/**
	 * The default value of the '{@link #get_revision_number() <em>revision number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #get_revision_number()
	 * @generated
	 * @ordered
	 */
	protected static final String _REVISION_NUMBER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #get_revision_number() <em>revision number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #get_revision_number()
	 * @generated
	 * @ordered
	 */
	protected String _revision_number = _REVISION_NUMBER_EDEFAULT;

	/**
	 * The default value of the '{@link #get_current_revision_number() <em>current revision number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #get_current_revision_number()
	 * @generated
	 * @ordered
	 */
	protected static final String _CURRENT_REVISION_NUMBER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #get_current_revision_number() <em>current revision number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #get_current_revision_number()
	 * @generated
	 * @ordered
	 */
	protected String _current_revision_number = _CURRENT_REVISION_NUMBER_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RelatedChangeAndCommitInfoImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.RELATED_CHANGE_AND_COMMIT_INFO;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getChange_id() {
		return change_id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setChange_id(String newChange_id) {
		String oldChange_id = change_id;
		change_id = newChange_id;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					ModelPackage.RELATED_CHANGE_AND_COMMIT_INFO__CHANGE_ID, oldChange_id, change_id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CommitInfo getCommit() {
		return commit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCommit(CommitInfo newCommit, NotificationChain msgs) {
		CommitInfo oldCommit = commit;
		commit = newCommit;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					ModelPackage.RELATED_CHANGE_AND_COMMIT_INFO__COMMIT, oldCommit, newCommit);
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
	public void setCommit(CommitInfo newCommit) {
		if (newCommit != commit) {
			NotificationChain msgs = null;
			if (commit != null)
				msgs = ((InternalEObject) commit).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - ModelPackage.RELATED_CHANGE_AND_COMMIT_INFO__COMMIT, null, msgs);
			if (newCommit != null)
				msgs = ((InternalEObject) newCommit).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - ModelPackage.RELATED_CHANGE_AND_COMMIT_INFO__COMMIT, null, msgs);
			msgs = basicSetCommit(newCommit, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.RELATED_CHANGE_AND_COMMIT_INFO__COMMIT,
					newCommit, newCommit));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String get_change_number() {
		return _change_number;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void set_change_number(String new_change_number) {
		String old_change_number = _change_number;
		_change_number = new_change_number;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					ModelPackage.RELATED_CHANGE_AND_COMMIT_INFO__CHANGE_NUMBER, old_change_number, _change_number));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String get_revision_number() {
		return _revision_number;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void set_revision_number(String new_revision_number) {
		String old_revision_number = _revision_number;
		_revision_number = new_revision_number;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					ModelPackage.RELATED_CHANGE_AND_COMMIT_INFO__REVISION_NUMBER, old_revision_number,
					_revision_number));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String get_current_revision_number() {
		return _current_revision_number;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void set_current_revision_number(String new_current_revision_number) {
		String old_current_revision_number = _current_revision_number;
		_current_revision_number = new_current_revision_number;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					ModelPackage.RELATED_CHANGE_AND_COMMIT_INFO__CURRENT_REVISION_NUMBER, old_current_revision_number,
					_current_revision_number));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case ModelPackage.RELATED_CHANGE_AND_COMMIT_INFO__COMMIT:
			return basicSetCommit(null, msgs);
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
		case ModelPackage.RELATED_CHANGE_AND_COMMIT_INFO__CHANGE_ID:
			return getChange_id();
		case ModelPackage.RELATED_CHANGE_AND_COMMIT_INFO__COMMIT:
			return getCommit();
		case ModelPackage.RELATED_CHANGE_AND_COMMIT_INFO__CHANGE_NUMBER:
			return get_change_number();
		case ModelPackage.RELATED_CHANGE_AND_COMMIT_INFO__REVISION_NUMBER:
			return get_revision_number();
		case ModelPackage.RELATED_CHANGE_AND_COMMIT_INFO__CURRENT_REVISION_NUMBER:
			return get_current_revision_number();
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
		case ModelPackage.RELATED_CHANGE_AND_COMMIT_INFO__CHANGE_ID:
			setChange_id((String) newValue);
			return;
		case ModelPackage.RELATED_CHANGE_AND_COMMIT_INFO__COMMIT:
			setCommit((CommitInfo) newValue);
			return;
		case ModelPackage.RELATED_CHANGE_AND_COMMIT_INFO__CHANGE_NUMBER:
			set_change_number((String) newValue);
			return;
		case ModelPackage.RELATED_CHANGE_AND_COMMIT_INFO__REVISION_NUMBER:
			set_revision_number((String) newValue);
			return;
		case ModelPackage.RELATED_CHANGE_AND_COMMIT_INFO__CURRENT_REVISION_NUMBER:
			set_current_revision_number((String) newValue);
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
		case ModelPackage.RELATED_CHANGE_AND_COMMIT_INFO__CHANGE_ID:
			setChange_id(CHANGE_ID_EDEFAULT);
			return;
		case ModelPackage.RELATED_CHANGE_AND_COMMIT_INFO__COMMIT:
			setCommit((CommitInfo) null);
			return;
		case ModelPackage.RELATED_CHANGE_AND_COMMIT_INFO__CHANGE_NUMBER:
			set_change_number(_CHANGE_NUMBER_EDEFAULT);
			return;
		case ModelPackage.RELATED_CHANGE_AND_COMMIT_INFO__REVISION_NUMBER:
			set_revision_number(_REVISION_NUMBER_EDEFAULT);
			return;
		case ModelPackage.RELATED_CHANGE_AND_COMMIT_INFO__CURRENT_REVISION_NUMBER:
			set_current_revision_number(_CURRENT_REVISION_NUMBER_EDEFAULT);
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
		case ModelPackage.RELATED_CHANGE_AND_COMMIT_INFO__CHANGE_ID:
			return CHANGE_ID_EDEFAULT == null ? change_id != null : !CHANGE_ID_EDEFAULT.equals(change_id);
		case ModelPackage.RELATED_CHANGE_AND_COMMIT_INFO__COMMIT:
			return commit != null;
		case ModelPackage.RELATED_CHANGE_AND_COMMIT_INFO__CHANGE_NUMBER:
			return _CHANGE_NUMBER_EDEFAULT == null
					? _change_number != null
					: !_CHANGE_NUMBER_EDEFAULT.equals(_change_number);
		case ModelPackage.RELATED_CHANGE_AND_COMMIT_INFO__REVISION_NUMBER:
			return _REVISION_NUMBER_EDEFAULT == null
					? _revision_number != null
					: !_REVISION_NUMBER_EDEFAULT.equals(_revision_number);
		case ModelPackage.RELATED_CHANGE_AND_COMMIT_INFO__CURRENT_REVISION_NUMBER:
			return _CURRENT_REVISION_NUMBER_EDEFAULT == null
					? _current_revision_number != null
					: !_CURRENT_REVISION_NUMBER_EDEFAULT.equals(_current_revision_number);
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
		result.append(" (change_id: "); //$NON-NLS-1$
		result.append(change_id);
		result.append(", _change_number: "); //$NON-NLS-1$
		result.append(_change_number);
		result.append(", _revision_number: "); //$NON-NLS-1$
		result.append(_revision_number);
		result.append(", _current_revision_number: "); //$NON-NLS-1$
		result.append(_current_revision_number);
		result.append(')');
		return result.toString();
	}

} //RelatedChangeAndCommitInfoImpl
