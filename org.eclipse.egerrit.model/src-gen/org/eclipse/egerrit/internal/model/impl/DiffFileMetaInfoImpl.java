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

import org.eclipse.egerrit.internal.model.DiffFileMetaInfo;
import org.eclipse.egerrit.internal.model.ModelPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Diff File Meta Info</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.DiffFileMetaInfoImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.DiffFileMetaInfoImpl#getContent_type <em>Content type</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.DiffFileMetaInfoImpl#getLines <em>Lines</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DiffFileMetaInfoImpl extends MinimalEObjectImpl.Container implements DiffFileMetaInfo {
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
	 * The default value of the '{@link #getContent_type() <em>Content type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContent_type()
	 * @generated
	 * @ordered
	 */
	protected static final String CONTENT_TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getContent_type() <em>Content type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContent_type()
	 * @generated
	 * @ordered
	 */
	protected String content_type = CONTENT_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getLines() <em>Lines</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLines()
	 * @generated
	 * @ordered
	 */
	protected static final String LINES_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLines() <em>Lines</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLines()
	 * @generated
	 * @ordered
	 */
	protected String lines = LINES_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DiffFileMetaInfoImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.DIFF_FILE_META_INFO;
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
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.DIFF_FILE_META_INFO__NAME, oldName,
					name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getContent_type() {
		return content_type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setContent_type(String newContent_type) {
		String oldContent_type = content_type;
		content_type = newContent_type;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.DIFF_FILE_META_INFO__CONTENT_TYPE,
					oldContent_type, content_type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getLines() {
		return lines;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLines(String newLines) {
		String oldLines = lines;
		lines = newLines;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.DIFF_FILE_META_INFO__LINES, oldLines,
					lines));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case ModelPackage.DIFF_FILE_META_INFO__NAME:
			return getName();
		case ModelPackage.DIFF_FILE_META_INFO__CONTENT_TYPE:
			return getContent_type();
		case ModelPackage.DIFF_FILE_META_INFO__LINES:
			return getLines();
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
		case ModelPackage.DIFF_FILE_META_INFO__NAME:
			setName((String) newValue);
			return;
		case ModelPackage.DIFF_FILE_META_INFO__CONTENT_TYPE:
			setContent_type((String) newValue);
			return;
		case ModelPackage.DIFF_FILE_META_INFO__LINES:
			setLines((String) newValue);
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
		case ModelPackage.DIFF_FILE_META_INFO__NAME:
			setName(NAME_EDEFAULT);
			return;
		case ModelPackage.DIFF_FILE_META_INFO__CONTENT_TYPE:
			setContent_type(CONTENT_TYPE_EDEFAULT);
			return;
		case ModelPackage.DIFF_FILE_META_INFO__LINES:
			setLines(LINES_EDEFAULT);
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
		case ModelPackage.DIFF_FILE_META_INFO__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
		case ModelPackage.DIFF_FILE_META_INFO__CONTENT_TYPE:
			return CONTENT_TYPE_EDEFAULT == null ? content_type != null : !CONTENT_TYPE_EDEFAULT.equals(content_type);
		case ModelPackage.DIFF_FILE_META_INFO__LINES:
			return LINES_EDEFAULT == null ? lines != null : !LINES_EDEFAULT.equals(lines);
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
		result.append(" (name: "); //$NON-NLS-1$
		result.append(name);
		result.append(", content_type: "); //$NON-NLS-1$
		result.append(content_type);
		result.append(", lines: "); //$NON-NLS-1$
		result.append(lines);
		result.append(')');
		return result.toString();
	}

} //DiffFileMetaInfoImpl
