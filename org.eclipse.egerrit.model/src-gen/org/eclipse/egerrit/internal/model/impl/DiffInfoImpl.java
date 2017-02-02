/**
 *   Copyright (c) 2017 Ericsson AB
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

import org.eclipse.egerrit.internal.model.DiffContent;
import org.eclipse.egerrit.internal.model.DiffFileMetaInfo;
import org.eclipse.egerrit.internal.model.DiffInfo;
import org.eclipse.egerrit.internal.model.ModelPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Diff Info</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.DiffInfoImpl#getChange_type <em>Change type</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.DiffInfoImpl#getContent <em>Content</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.DiffInfoImpl#getMeta_a <em>Meta a</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.DiffInfoImpl#getMeta_b <em>Meta b</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.DiffInfoImpl#isBinary <em>Binary</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DiffInfoImpl extends MinimalEObjectImpl.Container implements DiffInfo {
	/**
	 * The default value of the '{@link #getChange_type() <em>Change type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChange_type()
	 * @generated
	 * @ordered
	 */
	protected static final String CHANGE_TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getChange_type() <em>Change type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChange_type()
	 * @generated
	 * @ordered
	 */
	protected String change_type = CHANGE_TYPE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getContent() <em>Content</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContent()
	 * @generated
	 * @ordered
	 */
	protected EList<DiffContent> content;

	/**
	 * The cached value of the '{@link #getMeta_a() <em>Meta a</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMeta_a()
	 * @generated
	 * @ordered
	 */
	protected DiffFileMetaInfo meta_a;

	/**
	 * The cached value of the '{@link #getMeta_b() <em>Meta b</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMeta_b()
	 * @generated
	 * @ordered
	 */
	protected DiffFileMetaInfo meta_b;

	/**
	 * The default value of the '{@link #isBinary() <em>Binary</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isBinary()
	 * @generated
	 * @ordered
	 */
	protected static final boolean BINARY_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isBinary() <em>Binary</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isBinary()
	 * @generated
	 * @ordered
	 */
	protected boolean binary = BINARY_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DiffInfoImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.DIFF_INFO;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getChange_type() {
		return change_type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setChange_type(String newChange_type) {
		String oldChange_type = change_type;
		change_type = newChange_type;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.DIFF_INFO__CHANGE_TYPE, oldChange_type,
					change_type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<DiffContent> getContent() {
		if (content == null) {
			content = new EObjectResolvingEList<DiffContent>(DiffContent.class, this, ModelPackage.DIFF_INFO__CONTENT);
		}
		return content;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DiffFileMetaInfo getMeta_a() {
		if (meta_a != null && meta_a.eIsProxy()) {
			InternalEObject oldMeta_a = (InternalEObject) meta_a;
			meta_a = (DiffFileMetaInfo) eResolveProxy(oldMeta_a);
			if (meta_a != oldMeta_a) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ModelPackage.DIFF_INFO__META_A, oldMeta_a,
							meta_a));
			}
		}
		return meta_a;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DiffFileMetaInfo basicGetMeta_a() {
		return meta_a;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMeta_a(DiffFileMetaInfo newMeta_a) {
		DiffFileMetaInfo oldMeta_a = meta_a;
		meta_a = newMeta_a;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.DIFF_INFO__META_A, oldMeta_a, meta_a));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DiffFileMetaInfo getMeta_b() {
		if (meta_b != null && meta_b.eIsProxy()) {
			InternalEObject oldMeta_b = (InternalEObject) meta_b;
			meta_b = (DiffFileMetaInfo) eResolveProxy(oldMeta_b);
			if (meta_b != oldMeta_b) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ModelPackage.DIFF_INFO__META_B, oldMeta_b,
							meta_b));
			}
		}
		return meta_b;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DiffFileMetaInfo basicGetMeta_b() {
		return meta_b;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMeta_b(DiffFileMetaInfo newMeta_b) {
		DiffFileMetaInfo oldMeta_b = meta_b;
		meta_b = newMeta_b;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.DIFF_INFO__META_B, oldMeta_b, meta_b));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isBinary() {
		return binary;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setBinary(boolean newBinary) {
		boolean oldBinary = binary;
		binary = newBinary;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.DIFF_INFO__BINARY, oldBinary, binary));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case ModelPackage.DIFF_INFO__CHANGE_TYPE:
			return getChange_type();
		case ModelPackage.DIFF_INFO__CONTENT:
			return getContent();
		case ModelPackage.DIFF_INFO__META_A:
			if (resolve)
				return getMeta_a();
			return basicGetMeta_a();
		case ModelPackage.DIFF_INFO__META_B:
			if (resolve)
				return getMeta_b();
			return basicGetMeta_b();
		case ModelPackage.DIFF_INFO__BINARY:
			return isBinary();
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
		case ModelPackage.DIFF_INFO__CHANGE_TYPE:
			setChange_type((String) newValue);
			return;
		case ModelPackage.DIFF_INFO__CONTENT:
			getContent().clear();
			getContent().addAll((Collection<? extends DiffContent>) newValue);
			return;
		case ModelPackage.DIFF_INFO__META_A:
			setMeta_a((DiffFileMetaInfo) newValue);
			return;
		case ModelPackage.DIFF_INFO__META_B:
			setMeta_b((DiffFileMetaInfo) newValue);
			return;
		case ModelPackage.DIFF_INFO__BINARY:
			setBinary((Boolean) newValue);
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
		case ModelPackage.DIFF_INFO__CHANGE_TYPE:
			setChange_type(CHANGE_TYPE_EDEFAULT);
			return;
		case ModelPackage.DIFF_INFO__CONTENT:
			getContent().clear();
			return;
		case ModelPackage.DIFF_INFO__META_A:
			setMeta_a((DiffFileMetaInfo) null);
			return;
		case ModelPackage.DIFF_INFO__META_B:
			setMeta_b((DiffFileMetaInfo) null);
			return;
		case ModelPackage.DIFF_INFO__BINARY:
			setBinary(BINARY_EDEFAULT);
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
		case ModelPackage.DIFF_INFO__CHANGE_TYPE:
			return CHANGE_TYPE_EDEFAULT == null ? change_type != null : !CHANGE_TYPE_EDEFAULT.equals(change_type);
		case ModelPackage.DIFF_INFO__CONTENT:
			return content != null && !content.isEmpty();
		case ModelPackage.DIFF_INFO__META_A:
			return meta_a != null;
		case ModelPackage.DIFF_INFO__META_B:
			return meta_b != null;
		case ModelPackage.DIFF_INFO__BINARY:
			return binary != BINARY_EDEFAULT;
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
		result.append(" (change_type: "); //$NON-NLS-1$
		result.append(change_type);
		result.append(", binary: "); //$NON-NLS-1$
		result.append(binary);
		result.append(')');
		return result.toString();
	}

} //DiffInfoImpl
