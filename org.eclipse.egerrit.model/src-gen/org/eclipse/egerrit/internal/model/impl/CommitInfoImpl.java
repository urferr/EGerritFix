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

import org.eclipse.egerrit.internal.model.CommitInfo;
import org.eclipse.egerrit.internal.model.GitPersonInfo;
import org.eclipse.egerrit.internal.model.ModelPackage;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Commit Info</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.CommitInfoImpl#getCommit <em>Commit</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.CommitInfoImpl#getParents <em>Parents</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.CommitInfoImpl#getAuthor <em>Author</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.CommitInfoImpl#getCommitter <em>Committer</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.CommitInfoImpl#getSubject <em>Subject</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.CommitInfoImpl#getMessage <em>Message</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CommitInfoImpl extends MinimalEObjectImpl.Container implements CommitInfo {
	/**
	 * The default value of the '{@link #getCommit() <em>Commit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCommit()
	 * @generated
	 * @ordered
	 */
	protected static final String COMMIT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCommit() <em>Commit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCommit()
	 * @generated
	 * @ordered
	 */
	protected String commit = COMMIT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getParents() <em>Parents</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParents()
	 * @generated
	 * @ordered
	 */
	protected EList<CommitInfo> parents;

	/**
	 * The cached value of the '{@link #getAuthor() <em>Author</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAuthor()
	 * @generated
	 * @ordered
	 */
	protected GitPersonInfo author;

	/**
	 * The cached value of the '{@link #getCommitter() <em>Committer</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCommitter()
	 * @generated
	 * @ordered
	 */
	protected GitPersonInfo committer;

	/**
	 * The default value of the '{@link #getSubject() <em>Subject</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubject()
	 * @generated
	 * @ordered
	 */
	protected static final String SUBJECT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSubject() <em>Subject</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubject()
	 * @generated
	 * @ordered
	 */
	protected String subject = SUBJECT_EDEFAULT;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CommitInfoImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.COMMIT_INFO;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getCommit() {
		return commit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCommit(String newCommit) {
		String oldCommit = commit;
		commit = newCommit;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.COMMIT_INFO__COMMIT, oldCommit, commit));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<CommitInfo> getParents() {
		if (parents == null) {
			parents = new EObjectContainmentEList<CommitInfo>(CommitInfo.class, this,
					ModelPackage.COMMIT_INFO__PARENTS);
		}
		return parents;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GitPersonInfo getAuthor() {
		return author;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAuthor(GitPersonInfo newAuthor, NotificationChain msgs) {
		GitPersonInfo oldAuthor = author;
		author = newAuthor;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					ModelPackage.COMMIT_INFO__AUTHOR, oldAuthor, newAuthor);
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
	public void setAuthor(GitPersonInfo newAuthor) {
		if (newAuthor != author) {
			NotificationChain msgs = null;
			if (author != null)
				msgs = ((InternalEObject) author).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - ModelPackage.COMMIT_INFO__AUTHOR, null, msgs);
			if (newAuthor != null)
				msgs = ((InternalEObject) newAuthor).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - ModelPackage.COMMIT_INFO__AUTHOR, null, msgs);
			msgs = basicSetAuthor(newAuthor, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.COMMIT_INFO__AUTHOR, newAuthor,
					newAuthor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GitPersonInfo getCommitter() {
		return committer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCommitter(GitPersonInfo newCommitter, NotificationChain msgs) {
		GitPersonInfo oldCommitter = committer;
		committer = newCommitter;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					ModelPackage.COMMIT_INFO__COMMITTER, oldCommitter, newCommitter);
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
	public void setCommitter(GitPersonInfo newCommitter) {
		if (newCommitter != committer) {
			NotificationChain msgs = null;
			if (committer != null)
				msgs = ((InternalEObject) committer).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - ModelPackage.COMMIT_INFO__COMMITTER, null, msgs);
			if (newCommitter != null)
				msgs = ((InternalEObject) newCommitter).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - ModelPackage.COMMIT_INFO__COMMITTER, null, msgs);
			msgs = basicSetCommitter(newCommitter, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.COMMIT_INFO__COMMITTER, newCommitter,
					newCommitter));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getSubject() {
		return subject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSubject(String newSubject) {
		String oldSubject = subject;
		subject = newSubject;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.COMMIT_INFO__SUBJECT, oldSubject,
					subject));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.COMMIT_INFO__MESSAGE, oldMessage,
					message));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case ModelPackage.COMMIT_INFO__PARENTS:
			return ((InternalEList<?>) getParents()).basicRemove(otherEnd, msgs);
		case ModelPackage.COMMIT_INFO__AUTHOR:
			return basicSetAuthor(null, msgs);
		case ModelPackage.COMMIT_INFO__COMMITTER:
			return basicSetCommitter(null, msgs);
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
		case ModelPackage.COMMIT_INFO__COMMIT:
			return getCommit();
		case ModelPackage.COMMIT_INFO__PARENTS:
			return getParents();
		case ModelPackage.COMMIT_INFO__AUTHOR:
			return getAuthor();
		case ModelPackage.COMMIT_INFO__COMMITTER:
			return getCommitter();
		case ModelPackage.COMMIT_INFO__SUBJECT:
			return getSubject();
		case ModelPackage.COMMIT_INFO__MESSAGE:
			return getMessage();
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
		case ModelPackage.COMMIT_INFO__COMMIT:
			setCommit((String) newValue);
			return;
		case ModelPackage.COMMIT_INFO__PARENTS:
			getParents().clear();
			getParents().addAll((Collection<? extends CommitInfo>) newValue);
			return;
		case ModelPackage.COMMIT_INFO__AUTHOR:
			setAuthor((GitPersonInfo) newValue);
			return;
		case ModelPackage.COMMIT_INFO__COMMITTER:
			setCommitter((GitPersonInfo) newValue);
			return;
		case ModelPackage.COMMIT_INFO__SUBJECT:
			setSubject((String) newValue);
			return;
		case ModelPackage.COMMIT_INFO__MESSAGE:
			setMessage((String) newValue);
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
		case ModelPackage.COMMIT_INFO__COMMIT:
			setCommit(COMMIT_EDEFAULT);
			return;
		case ModelPackage.COMMIT_INFO__PARENTS:
			getParents().clear();
			return;
		case ModelPackage.COMMIT_INFO__AUTHOR:
			setAuthor((GitPersonInfo) null);
			return;
		case ModelPackage.COMMIT_INFO__COMMITTER:
			setCommitter((GitPersonInfo) null);
			return;
		case ModelPackage.COMMIT_INFO__SUBJECT:
			setSubject(SUBJECT_EDEFAULT);
			return;
		case ModelPackage.COMMIT_INFO__MESSAGE:
			setMessage(MESSAGE_EDEFAULT);
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
		case ModelPackage.COMMIT_INFO__COMMIT:
			return COMMIT_EDEFAULT == null ? commit != null : !COMMIT_EDEFAULT.equals(commit);
		case ModelPackage.COMMIT_INFO__PARENTS:
			return parents != null && !parents.isEmpty();
		case ModelPackage.COMMIT_INFO__AUTHOR:
			return author != null;
		case ModelPackage.COMMIT_INFO__COMMITTER:
			return committer != null;
		case ModelPackage.COMMIT_INFO__SUBJECT:
			return SUBJECT_EDEFAULT == null ? subject != null : !SUBJECT_EDEFAULT.equals(subject);
		case ModelPackage.COMMIT_INFO__MESSAGE:
			return MESSAGE_EDEFAULT == null ? message != null : !MESSAGE_EDEFAULT.equals(message);
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
		result.append(" (commit: "); //$NON-NLS-1$
		result.append(commit);
		result.append(", subject: "); //$NON-NLS-1$
		result.append(subject);
		result.append(", message: "); //$NON-NLS-1$
		result.append(message);
		result.append(')');
		return result.toString();
	}

} //CommitInfoImpl
