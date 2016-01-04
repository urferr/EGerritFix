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

import org.eclipse.egerrit.internal.model.CommentRange;
import org.eclipse.egerrit.internal.model.ModelPackage;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Comment Range</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.CommentRangeImpl#getStartLine <em>Start Line</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.CommentRangeImpl#getStartCharacter <em>Start Character</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.CommentRangeImpl#getEndLine <em>End Line</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.CommentRangeImpl#getEndCharacter <em>End Character</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CommentRangeImpl extends MinimalEObjectImpl.Container implements CommentRange {
	/**
	 * The default value of the '{@link #getStartLine() <em>Start Line</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStartLine()
	 * @generated
	 * @ordered
	 */
	protected static final int START_LINE_EDEFAULT = 0;
	/**
	 * The cached value of the '{@link #getStartLine() <em>Start Line</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStartLine()
	 * @generated
	 * @ordered
	 */
	protected int startLine = START_LINE_EDEFAULT;
	/**
	 * The default value of the '{@link #getStartCharacter() <em>Start Character</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStartCharacter()
	 * @generated
	 * @ordered
	 */
	protected static final int START_CHARACTER_EDEFAULT = 0;
	/**
	 * The cached value of the '{@link #getStartCharacter() <em>Start Character</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStartCharacter()
	 * @generated
	 * @ordered
	 */
	protected int startCharacter = START_CHARACTER_EDEFAULT;
	/**
	 * The default value of the '{@link #getEndLine() <em>End Line</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEndLine()
	 * @generated
	 * @ordered
	 */
	protected static final int END_LINE_EDEFAULT = 0;
	/**
	 * The cached value of the '{@link #getEndLine() <em>End Line</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEndLine()
	 * @generated
	 * @ordered
	 */
	protected int endLine = END_LINE_EDEFAULT;
	/**
	 * The default value of the '{@link #getEndCharacter() <em>End Character</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEndCharacter()
	 * @generated
	 * @ordered
	 */
	protected static final int END_CHARACTER_EDEFAULT = 0;
	/**
	 * The cached value of the '{@link #getEndCharacter() <em>End Character</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEndCharacter()
	 * @generated
	 * @ordered
	 */
	protected int endCharacter = END_CHARACTER_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CommentRangeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.COMMENT_RANGE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getStartLine() {
		return startLine;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setStartLine(int newStartLine) {
		int oldStartLine = startLine;
		startLine = newStartLine;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.COMMENT_RANGE__START_LINE, oldStartLine,
					startLine));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getStartCharacter() {
		return startCharacter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setStartCharacter(int newStartCharacter) {
		int oldStartCharacter = startCharacter;
		startCharacter = newStartCharacter;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.COMMENT_RANGE__START_CHARACTER,
					oldStartCharacter, startCharacter));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getEndLine() {
		return endLine;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setEndLine(int newEndLine) {
		int oldEndLine = endLine;
		endLine = newEndLine;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.COMMENT_RANGE__END_LINE, oldEndLine,
					endLine));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getEndCharacter() {
		return endCharacter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setEndCharacter(int newEndCharacter) {
		int oldEndCharacter = endCharacter;
		endCharacter = newEndCharacter;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.COMMENT_RANGE__END_CHARACTER,
					oldEndCharacter, endCharacter));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case ModelPackage.COMMENT_RANGE__START_LINE:
			return getStartLine();
		case ModelPackage.COMMENT_RANGE__START_CHARACTER:
			return getStartCharacter();
		case ModelPackage.COMMENT_RANGE__END_LINE:
			return getEndLine();
		case ModelPackage.COMMENT_RANGE__END_CHARACTER:
			return getEndCharacter();
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
		case ModelPackage.COMMENT_RANGE__START_LINE:
			setStartLine((Integer) newValue);
			return;
		case ModelPackage.COMMENT_RANGE__START_CHARACTER:
			setStartCharacter((Integer) newValue);
			return;
		case ModelPackage.COMMENT_RANGE__END_LINE:
			setEndLine((Integer) newValue);
			return;
		case ModelPackage.COMMENT_RANGE__END_CHARACTER:
			setEndCharacter((Integer) newValue);
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
		case ModelPackage.COMMENT_RANGE__START_LINE:
			setStartLine(START_LINE_EDEFAULT);
			return;
		case ModelPackage.COMMENT_RANGE__START_CHARACTER:
			setStartCharacter(START_CHARACTER_EDEFAULT);
			return;
		case ModelPackage.COMMENT_RANGE__END_LINE:
			setEndLine(END_LINE_EDEFAULT);
			return;
		case ModelPackage.COMMENT_RANGE__END_CHARACTER:
			setEndCharacter(END_CHARACTER_EDEFAULT);
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
		case ModelPackage.COMMENT_RANGE__START_LINE:
			return startLine != START_LINE_EDEFAULT;
		case ModelPackage.COMMENT_RANGE__START_CHARACTER:
			return startCharacter != START_CHARACTER_EDEFAULT;
		case ModelPackage.COMMENT_RANGE__END_LINE:
			return endLine != END_LINE_EDEFAULT;
		case ModelPackage.COMMENT_RANGE__END_CHARACTER:
			return endCharacter != END_CHARACTER_EDEFAULT;
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
		result.append(" (startLine: "); //$NON-NLS-1$
		result.append(startLine);
		result.append(", startCharacter: "); //$NON-NLS-1$
		result.append(startCharacter);
		result.append(", endLine: "); //$NON-NLS-1$
		result.append(endLine);
		result.append(", endCharacter: "); //$NON-NLS-1$
		result.append(endCharacter);
		result.append(')');
		return result.toString();
	}

} //CommentRangeImpl
