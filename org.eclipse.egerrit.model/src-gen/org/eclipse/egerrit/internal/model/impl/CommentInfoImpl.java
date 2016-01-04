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
import org.eclipse.egerrit.internal.model.CommentInfo;
import org.eclipse.egerrit.internal.model.CommentRange;
import org.eclipse.egerrit.internal.model.ModelPackage;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Comment Info</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.CommentInfoImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.CommentInfoImpl#getPath <em>Path</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.CommentInfoImpl#getSide <em>Side</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.CommentInfoImpl#getLine <em>Line</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.CommentInfoImpl#getRange <em>Range</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.CommentInfoImpl#getInReplyTo <em>In Reply To</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.CommentInfoImpl#getMessage <em>Message</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.CommentInfoImpl#getUpdated <em>Updated</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.CommentInfoImpl#getAuthor <em>Author</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CommentInfoImpl extends MinimalEObjectImpl.Container implements CommentInfo {
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
	 * The default value of the '{@link #getPath() <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPath()
	 * @generated
	 * @ordered
	 */
	protected static final String PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPath() <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPath()
	 * @generated
	 * @ordered
	 */
	protected String path = PATH_EDEFAULT;

	/**
	 * The default value of the '{@link #getSide() <em>Side</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSide()
	 * @generated
	 * @ordered
	 */
	protected static final String SIDE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSide() <em>Side</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSide()
	 * @generated
	 * @ordered
	 */
	protected String side = SIDE_EDEFAULT;

	/**
	 * The default value of the '{@link #getLine() <em>Line</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLine()
	 * @generated
	 * @ordered
	 */
	protected static final int LINE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getLine() <em>Line</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLine()
	 * @generated
	 * @ordered
	 */
	protected int line = LINE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getRange() <em>Range</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRange()
	 * @generated
	 * @ordered
	 */
	protected CommentRange range;

	/**
	 * The default value of the '{@link #getInReplyTo() <em>In Reply To</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInReplyTo()
	 * @generated
	 * @ordered
	 */
	protected static final String IN_REPLY_TO_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getInReplyTo() <em>In Reply To</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInReplyTo()
	 * @generated
	 * @ordered
	 */
	protected String inReplyTo = IN_REPLY_TO_EDEFAULT;

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
	 * The default value of the '{@link #getUpdated() <em>Updated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUpdated()
	 * @generated
	 * @ordered
	 */
	protected static final String UPDATED_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getUpdated() <em>Updated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUpdated()
	 * @generated
	 * @ordered
	 */
	protected String updated = UPDATED_EDEFAULT;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CommentInfoImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.COMMENT_INFO;
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
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.COMMENT_INFO__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getPath() {
		return path;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPath(String newPath) {
		String oldPath = path;
		path = newPath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.COMMENT_INFO__PATH, oldPath, path));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getSide() {
		return side;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSide(String newSide) {
		String oldSide = side;
		side = newSide;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.COMMENT_INFO__SIDE, oldSide, side));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getLine() {
		return line;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLine(int newLine) {
		int oldLine = line;
		line = newLine;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.COMMENT_INFO__LINE, oldLine, line));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CommentRange getRange() {
		return range;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRange(CommentRange newRange, NotificationChain msgs) {
		CommentRange oldRange = range;
		range = newRange;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					ModelPackage.COMMENT_INFO__RANGE, oldRange, newRange);
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
	public void setRange(CommentRange newRange) {
		if (newRange != range) {
			NotificationChain msgs = null;
			if (range != null)
				msgs = ((InternalEObject) range).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - ModelPackage.COMMENT_INFO__RANGE, null, msgs);
			if (newRange != null)
				msgs = ((InternalEObject) newRange).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - ModelPackage.COMMENT_INFO__RANGE, null, msgs);
			msgs = basicSetRange(newRange, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.COMMENT_INFO__RANGE, newRange,
					newRange));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getInReplyTo() {
		return inReplyTo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setInReplyTo(String newInReplyTo) {
		String oldInReplyTo = inReplyTo;
		inReplyTo = newInReplyTo;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.COMMENT_INFO__IN_REPLY_TO, oldInReplyTo,
					inReplyTo));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.COMMENT_INFO__MESSAGE, oldMessage,
					message));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getUpdated() {
		return updated;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setUpdated(String newUpdated) {
		String oldUpdated = updated;
		updated = newUpdated;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.COMMENT_INFO__UPDATED, oldUpdated,
					updated));
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
					ModelPackage.COMMENT_INFO__AUTHOR, oldAuthor, newAuthor);
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
						EOPPOSITE_FEATURE_BASE - ModelPackage.COMMENT_INFO__AUTHOR, null, msgs);
			if (newAuthor != null)
				msgs = ((InternalEObject) newAuthor).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - ModelPackage.COMMENT_INFO__AUTHOR, null, msgs);
			msgs = basicSetAuthor(newAuthor, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.COMMENT_INFO__AUTHOR, newAuthor,
					newAuthor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case ModelPackage.COMMENT_INFO__RANGE:
			return basicSetRange(null, msgs);
		case ModelPackage.COMMENT_INFO__AUTHOR:
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
		case ModelPackage.COMMENT_INFO__ID:
			return getId();
		case ModelPackage.COMMENT_INFO__PATH:
			return getPath();
		case ModelPackage.COMMENT_INFO__SIDE:
			return getSide();
		case ModelPackage.COMMENT_INFO__LINE:
			return getLine();
		case ModelPackage.COMMENT_INFO__RANGE:
			return getRange();
		case ModelPackage.COMMENT_INFO__IN_REPLY_TO:
			return getInReplyTo();
		case ModelPackage.COMMENT_INFO__MESSAGE:
			return getMessage();
		case ModelPackage.COMMENT_INFO__UPDATED:
			return getUpdated();
		case ModelPackage.COMMENT_INFO__AUTHOR:
			return getAuthor();
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
		case ModelPackage.COMMENT_INFO__ID:
			setId((String) newValue);
			return;
		case ModelPackage.COMMENT_INFO__PATH:
			setPath((String) newValue);
			return;
		case ModelPackage.COMMENT_INFO__SIDE:
			setSide((String) newValue);
			return;
		case ModelPackage.COMMENT_INFO__LINE:
			setLine((Integer) newValue);
			return;
		case ModelPackage.COMMENT_INFO__RANGE:
			setRange((CommentRange) newValue);
			return;
		case ModelPackage.COMMENT_INFO__IN_REPLY_TO:
			setInReplyTo((String) newValue);
			return;
		case ModelPackage.COMMENT_INFO__MESSAGE:
			setMessage((String) newValue);
			return;
		case ModelPackage.COMMENT_INFO__UPDATED:
			setUpdated((String) newValue);
			return;
		case ModelPackage.COMMENT_INFO__AUTHOR:
			setAuthor((AccountInfo) newValue);
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
		case ModelPackage.COMMENT_INFO__ID:
			setId(ID_EDEFAULT);
			return;
		case ModelPackage.COMMENT_INFO__PATH:
			setPath(PATH_EDEFAULT);
			return;
		case ModelPackage.COMMENT_INFO__SIDE:
			setSide(SIDE_EDEFAULT);
			return;
		case ModelPackage.COMMENT_INFO__LINE:
			setLine(LINE_EDEFAULT);
			return;
		case ModelPackage.COMMENT_INFO__RANGE:
			setRange((CommentRange) null);
			return;
		case ModelPackage.COMMENT_INFO__IN_REPLY_TO:
			setInReplyTo(IN_REPLY_TO_EDEFAULT);
			return;
		case ModelPackage.COMMENT_INFO__MESSAGE:
			setMessage(MESSAGE_EDEFAULT);
			return;
		case ModelPackage.COMMENT_INFO__UPDATED:
			setUpdated(UPDATED_EDEFAULT);
			return;
		case ModelPackage.COMMENT_INFO__AUTHOR:
			setAuthor((AccountInfo) null);
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
		case ModelPackage.COMMENT_INFO__ID:
			return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
		case ModelPackage.COMMENT_INFO__PATH:
			return PATH_EDEFAULT == null ? path != null : !PATH_EDEFAULT.equals(path);
		case ModelPackage.COMMENT_INFO__SIDE:
			return SIDE_EDEFAULT == null ? side != null : !SIDE_EDEFAULT.equals(side);
		case ModelPackage.COMMENT_INFO__LINE:
			return line != LINE_EDEFAULT;
		case ModelPackage.COMMENT_INFO__RANGE:
			return range != null;
		case ModelPackage.COMMENT_INFO__IN_REPLY_TO:
			return IN_REPLY_TO_EDEFAULT == null ? inReplyTo != null : !IN_REPLY_TO_EDEFAULT.equals(inReplyTo);
		case ModelPackage.COMMENT_INFO__MESSAGE:
			return MESSAGE_EDEFAULT == null ? message != null : !MESSAGE_EDEFAULT.equals(message);
		case ModelPackage.COMMENT_INFO__UPDATED:
			return UPDATED_EDEFAULT == null ? updated != null : !UPDATED_EDEFAULT.equals(updated);
		case ModelPackage.COMMENT_INFO__AUTHOR:
			return author != null;
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
		result.append(", path: "); //$NON-NLS-1$
		result.append(path);
		result.append(", side: "); //$NON-NLS-1$
		result.append(side);
		result.append(", line: "); //$NON-NLS-1$
		result.append(line);
		result.append(", inReplyTo: "); //$NON-NLS-1$
		result.append(inReplyTo);
		result.append(", message: "); //$NON-NLS-1$
		result.append(message);
		result.append(", updated: "); //$NON-NLS-1$
		result.append(updated);
		result.append(')');
		return result.toString();
	}

} //CommentInfoImpl
