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
import org.eclipse.egerrit.internal.model.ModelPackage;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Diff Content</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.DiffContentImpl#getA <em>A</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.DiffContentImpl#getB <em>B</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.DiffContentImpl#getAb <em>Ab</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.DiffContentImpl#getSkip <em>Skip</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DiffContentImpl extends MinimalEObjectImpl.Container implements DiffContent {
	/**
	 * The cached value of the '{@link #getA() <em>A</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getA()
	 * @generated
	 * @ordered
	 */
	protected EList<String> a;

	/**
	 * The cached value of the '{@link #getB() <em>B</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getB()
	 * @generated
	 * @ordered
	 */
	protected EList<String> b;

	/**
	 * The cached value of the '{@link #getAb() <em>Ab</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAb()
	 * @generated
	 * @ordered
	 */
	protected EList<String> ab;

	/**
	 * The default value of the '{@link #getSkip() <em>Skip</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSkip()
	 * @generated
	 * @ordered
	 */
	protected static final int SKIP_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getSkip() <em>Skip</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSkip()
	 * @generated
	 * @ordered
	 */
	protected int skip = SKIP_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DiffContentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.DIFF_CONTENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<String> getA() {
		if (a == null) {
			a = new EDataTypeEList<String>(String.class, this, ModelPackage.DIFF_CONTENT__A);
		}
		return a;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<String> getB() {
		if (b == null) {
			b = new EDataTypeEList<String>(String.class, this, ModelPackage.DIFF_CONTENT__B);
		}
		return b;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<String> getAb() {
		if (ab == null) {
			ab = new EDataTypeEList<String>(String.class, this, ModelPackage.DIFF_CONTENT__AB);
		}
		return ab;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getSkip() {
		return skip;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSkip(int newSkip) {
		int oldSkip = skip;
		skip = newSkip;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.DIFF_CONTENT__SKIP, oldSkip, skip));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case ModelPackage.DIFF_CONTENT__A:
			return getA();
		case ModelPackage.DIFF_CONTENT__B:
			return getB();
		case ModelPackage.DIFF_CONTENT__AB:
			return getAb();
		case ModelPackage.DIFF_CONTENT__SKIP:
			return getSkip();
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
		case ModelPackage.DIFF_CONTENT__A:
			getA().clear();
			getA().addAll((Collection<? extends String>) newValue);
			return;
		case ModelPackage.DIFF_CONTENT__B:
			getB().clear();
			getB().addAll((Collection<? extends String>) newValue);
			return;
		case ModelPackage.DIFF_CONTENT__AB:
			getAb().clear();
			getAb().addAll((Collection<? extends String>) newValue);
			return;
		case ModelPackage.DIFF_CONTENT__SKIP:
			setSkip((Integer) newValue);
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
		case ModelPackage.DIFF_CONTENT__A:
			getA().clear();
			return;
		case ModelPackage.DIFF_CONTENT__B:
			getB().clear();
			return;
		case ModelPackage.DIFF_CONTENT__AB:
			getAb().clear();
			return;
		case ModelPackage.DIFF_CONTENT__SKIP:
			setSkip(SKIP_EDEFAULT);
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
		case ModelPackage.DIFF_CONTENT__A:
			return a != null && !a.isEmpty();
		case ModelPackage.DIFF_CONTENT__B:
			return b != null && !b.isEmpty();
		case ModelPackage.DIFF_CONTENT__AB:
			return ab != null && !ab.isEmpty();
		case ModelPackage.DIFF_CONTENT__SKIP:
			return skip != SKIP_EDEFAULT;
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
		result.append(" (a: "); //$NON-NLS-1$
		result.append(a);
		result.append(", b: "); //$NON-NLS-1$
		result.append(b);
		result.append(", ab: "); //$NON-NLS-1$
		result.append(ab);
		result.append(", skip: "); //$NON-NLS-1$
		result.append(skip);
		result.append(')');
		return result.toString();
	}

} //DiffContentImpl
