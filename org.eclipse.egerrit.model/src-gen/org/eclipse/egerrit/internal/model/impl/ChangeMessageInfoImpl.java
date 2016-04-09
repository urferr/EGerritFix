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

import org.eclipse.egerrit.internal.model.AccountInfo;
import org.eclipse.egerrit.internal.model.ChangeMessageInfo;
import org.eclipse.egerrit.internal.model.ModelPackage;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Change Message Info</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.ChangeMessageInfoImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.ChangeMessageInfoImpl#getAuthor <em>Author</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.ChangeMessageInfoImpl#getDate <em>Date</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.ChangeMessageInfoImpl#getMessage <em>Message</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.ChangeMessageInfoImpl#get_revision_number <em>revision number</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.ChangeMessageInfoImpl#isComment <em>Comment</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ChangeMessageInfoImpl extends MinimalEObjectImpl.Container implements ChangeMessageInfo {
	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected String id = ID_EDEFAULT;

	/**
	 * The cached value of the '{@link #getAuthor() <em>Author</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAuthor()
	 * @generated
	 * @ordered
	 */
	protected AccountInfo author;

	/**
	 * The default value of the '{@link #getDate() <em>Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDate()
	 * @generated
	 * @ordered
	 */
	protected static final String DATE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDate() <em>Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDate()
	 * @generated
	 * @ordered
	 */
	protected String date = DATE_EDEFAULT;

	/**
	 * The default value of the '{@link #getMessage() <em>Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMessage()
	 * @generated
	 * @ordered
	 */
	protected static final String MESSAGE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMessage() <em>Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMessage()
	 * @generated
	 * @ordered
	 */
	protected String message = MESSAGE_EDEFAULT;

	/**
	 * The default value of the '{@link #get_revision_number() <em>revision number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #get_revision_number()
	 * @generated
	 * @ordered
	 */
	protected static final int _REVISION_NUMBER_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #get_revision_number() <em>revision number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #get_revision_number()
	 * @generated
	 * @ordered
	 */
	protected int _revision_number = _REVISION_NUMBER_EDEFAULT;

	/**
	 * The default value of the '{@link #isComment() <em>Comment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isComment()
	 * @generated
	 * @ordered
	 */
	protected static final boolean COMMENT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isComment() <em>Comment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isComment()
	 * @generated
	 * @ordered
	 */
	protected boolean comment = COMMENT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ChangeMessageInfoImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.CHANGE_MESSAGE_INFO;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setId(String newId) {
		String oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.CHANGE_MESSAGE_INFO__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AccountInfo getAuthor() {
		return author;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAuthor(AccountInfo newAuthor, NotificationChain msgs) {
		AccountInfo oldAuthor = author;
		author = newAuthor;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					ModelPackage.CHANGE_MESSAGE_INFO__AUTHOR, oldAuthor, newAuthor);
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
	public void setAuthor(AccountInfo newAuthor) {
		if (newAuthor != author) {
			NotificationChain msgs = null;
			if (author != null)
				msgs = ((InternalEObject) author).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - ModelPackage.CHANGE_MESSAGE_INFO__AUTHOR, null, msgs);
			if (newAuthor != null)
				msgs = ((InternalEObject) newAuthor).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - ModelPackage.CHANGE_MESSAGE_INFO__AUTHOR, null, msgs);
			msgs = basicSetAuthor(newAuthor, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.CHANGE_MESSAGE_INFO__AUTHOR, newAuthor,
					newAuthor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getDate() {
		return date;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDate(String newDate) {
		String oldDate = date;
		date = newDate;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.CHANGE_MESSAGE_INFO__DATE, oldDate,
					date));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getMessage() {
		return message;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMessage(String newMessage) {
		String oldMessage = message;
		message = newMessage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.CHANGE_MESSAGE_INFO__MESSAGE, oldMessage,
					message));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int get_revision_number() {
		return _revision_number;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void set_revision_number(int new_revision_number) {
		int old_revision_number = _revision_number;
		_revision_number = new_revision_number;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.CHANGE_MESSAGE_INFO__REVISION_NUMBER,
					old_revision_number, _revision_number));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isComment() {
		return comment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setComment(boolean newComment) {
		boolean oldComment = comment;
		comment = newComment;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.CHANGE_MESSAGE_INFO__COMMENT, oldComment,
					comment));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case ModelPackage.CHANGE_MESSAGE_INFO__AUTHOR:
			return basicSetAuthor(null, msgs);
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
		case ModelPackage.CHANGE_MESSAGE_INFO__ID:
			return getId();
		case ModelPackage.CHANGE_MESSAGE_INFO__AUTHOR:
			return getAuthor();
		case ModelPackage.CHANGE_MESSAGE_INFO__DATE:
			return getDate();
		case ModelPackage.CHANGE_MESSAGE_INFO__MESSAGE:
			return getMessage();
		case ModelPackage.CHANGE_MESSAGE_INFO__REVISION_NUMBER:
			return get_revision_number();
		case ModelPackage.CHANGE_MESSAGE_INFO__COMMENT:
			return isComment();
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
		case ModelPackage.CHANGE_MESSAGE_INFO__ID:
			setId((String) newValue);
			return;
		case ModelPackage.CHANGE_MESSAGE_INFO__AUTHOR:
			setAuthor((AccountInfo) newValue);
			return;
		case ModelPackage.CHANGE_MESSAGE_INFO__DATE:
			setDate((String) newValue);
			return;
		case ModelPackage.CHANGE_MESSAGE_INFO__MESSAGE:
			setMessage((String) newValue);
			return;
		case ModelPackage.CHANGE_MESSAGE_INFO__REVISION_NUMBER:
			set_revision_number((Integer) newValue);
			return;
		case ModelPackage.CHANGE_MESSAGE_INFO__COMMENT:
			setComment((Boolean) newValue);
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
		case ModelPackage.CHANGE_MESSAGE_INFO__ID:
			setId(ID_EDEFAULT);
			return;
		case ModelPackage.CHANGE_MESSAGE_INFO__AUTHOR:
			setAuthor((AccountInfo) null);
			return;
		case ModelPackage.CHANGE_MESSAGE_INFO__DATE:
			setDate(DATE_EDEFAULT);
			return;
		case ModelPackage.CHANGE_MESSAGE_INFO__MESSAGE:
			setMessage(MESSAGE_EDEFAULT);
			return;
		case ModelPackage.CHANGE_MESSAGE_INFO__REVISION_NUMBER:
			set_revision_number(_REVISION_NUMBER_EDEFAULT);
			return;
		case ModelPackage.CHANGE_MESSAGE_INFO__COMMENT:
			setComment(COMMENT_EDEFAULT);
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
		case ModelPackage.CHANGE_MESSAGE_INFO__ID:
			return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
		case ModelPackage.CHANGE_MESSAGE_INFO__AUTHOR:
			return author != null;
		case ModelPackage.CHANGE_MESSAGE_INFO__DATE:
			return DATE_EDEFAULT == null ? date != null : !DATE_EDEFAULT.equals(date);
		case ModelPackage.CHANGE_MESSAGE_INFO__MESSAGE:
			return MESSAGE_EDEFAULT == null ? message != null : !MESSAGE_EDEFAULT.equals(message);
		case ModelPackage.CHANGE_MESSAGE_INFO__REVISION_NUMBER:
			return _revision_number != _REVISION_NUMBER_EDEFAULT;
		case ModelPackage.CHANGE_MESSAGE_INFO__COMMENT:
			return comment != COMMENT_EDEFAULT;
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
		result.append(" (id: "); //$NON-NLS-1$
		result.append(id);
		result.append(", date: "); //$NON-NLS-1$
		result.append(date);
		result.append(", message: "); //$NON-NLS-1$
		result.append(message);
		result.append(", _revision_number: "); //$NON-NLS-1$
		result.append(_revision_number);
		result.append(", comment: "); //$NON-NLS-1$
		result.append(comment);
		result.append(')');
		return result.toString();
	}

} //ChangeMessageInfoImpl
