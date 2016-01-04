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
import org.eclipse.egerrit.internal.model.CommentInfo;
import org.eclipse.egerrit.internal.model.FileInfo;
import org.eclipse.egerrit.internal.model.ModelPackage;
import org.eclipse.egerrit.internal.model.RevisionInfo;
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
 * An implementation of the model object '<em><b>File Info</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.FileInfoImpl#getStatus <em>Status</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.FileInfoImpl#isBinary <em>Binary</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.FileInfoImpl#getOld_path <em>Old path</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.FileInfoImpl#getLines_inserted <em>Lines inserted</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.FileInfoImpl#getLines_deleted <em>Lines deleted</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.FileInfoImpl#getContainedIn <em>Contained In</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.FileInfoImpl#getNewComments <em>New Comments</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.FileInfoImpl#getDraftComments <em>Draft Comments</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.FileInfoImpl#isReviewed <em>Reviewed</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FileInfoImpl extends MinimalEObjectImpl.Container implements FileInfo {
	/**
	 * The default value of the '{@link #getStatus() <em>Status</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStatus()
	 * @generated
	 * @ordered
	 */
	protected static final String STATUS_EDEFAULT = "M";

	/**
	 * The cached value of the '{@link #getStatus() <em>Status</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStatus()
	 * @generated
	 * @ordered
	 */
	protected String status = STATUS_EDEFAULT;

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
	 * The default value of the '{@link #getOld_path() <em>Old path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOld_path()
	 * @generated
	 * @ordered
	 */
	protected static final String OLD_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getOld_path() <em>Old path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOld_path()
	 * @generated
	 * @ordered
	 */
	protected String old_path = OLD_PATH_EDEFAULT;

	/**
	 * The default value of the '{@link #getLines_inserted() <em>Lines inserted</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLines_inserted()
	 * @generated
	 * @ordered
	 */
	protected static final int LINES_INSERTED_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getLines_inserted() <em>Lines inserted</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLines_inserted()
	 * @generated
	 * @ordered
	 */
	protected int lines_inserted = LINES_INSERTED_EDEFAULT;

	/**
	 * The default value of the '{@link #getLines_deleted() <em>Lines deleted</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLines_deleted()
	 * @generated
	 * @ordered
	 */
	protected static final int LINES_DELETED_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getLines_deleted() <em>Lines deleted</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLines_deleted()
	 * @generated
	 * @ordered
	 */
	protected int lines_deleted = LINES_DELETED_EDEFAULT;

	/**
	 * The cached value of the '{@link #getContainedIn() <em>Contained In</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContainedIn()
	 * @generated
	 * @ordered
	 */
	protected RevisionInfo containedIn;

	/**
	 * The cached value of the '{@link #getNewComments() <em>New Comments</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNewComments()
	 * @generated
	 * @ordered
	 */
	protected EList<CommentInfo> newComments;

	/**
	 * The cached value of the '{@link #getDraftComments() <em>Draft Comments</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDraftComments()
	 * @generated
	 * @ordered
	 */
	protected EList<CommentInfo> draftComments;

	/**
	 * The default value of the '{@link #isReviewed() <em>Reviewed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isReviewed()
	 * @generated
	 * @ordered
	 */
	protected static final boolean REVIEWED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isReviewed() <em>Reviewed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isReviewed()
	 * @generated
	 * @ordered
	 */
	protected boolean reviewed = REVIEWED_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FileInfoImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.FILE_INFO;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getStatus() {
		return status;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setStatus(String newStatus) {
		String oldStatus = status;
		status = newStatus;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.FILE_INFO__STATUS, oldStatus, status));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.FILE_INFO__BINARY, oldBinary, binary));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getOld_path() {
		return old_path;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setOld_path(String newOld_path) {
		String oldOld_path = old_path;
		old_path = newOld_path;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.FILE_INFO__OLD_PATH, oldOld_path,
					old_path));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getLines_inserted() {
		return lines_inserted;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLines_inserted(int newLines_inserted) {
		int oldLines_inserted = lines_inserted;
		lines_inserted = newLines_inserted;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.FILE_INFO__LINES_INSERTED,
					oldLines_inserted, lines_inserted));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getLines_deleted() {
		return lines_deleted;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLines_deleted(int newLines_deleted) {
		int oldLines_deleted = lines_deleted;
		lines_deleted = newLines_deleted;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.FILE_INFO__LINES_DELETED,
					oldLines_deleted, lines_deleted));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public RevisionInfo getContainedIn() {
		if (containedIn != null && containedIn.eIsProxy()) {
			InternalEObject oldContainedIn = (InternalEObject) containedIn;
			containedIn = (RevisionInfo) eResolveProxy(oldContainedIn);
			if (containedIn != oldContainedIn) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ModelPackage.FILE_INFO__CONTAINED_IN,
							oldContainedIn, containedIn));
			}
		}
		return containedIn;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RevisionInfo basicGetContainedIn() {
		return containedIn;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setContainedIn(RevisionInfo newContainedIn) {
		RevisionInfo oldContainedIn = containedIn;
		containedIn = newContainedIn;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.FILE_INFO__CONTAINED_IN, oldContainedIn,
					containedIn));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<CommentInfo> getNewComments() {
		if (newComments == null) {
			newComments = new EObjectContainmentEList<CommentInfo>(CommentInfo.class, this,
					ModelPackage.FILE_INFO__NEW_COMMENTS);
		}
		return newComments;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<CommentInfo> getDraftComments() {
		if (draftComments == null) {
			draftComments = new EObjectContainmentEList<CommentInfo>(CommentInfo.class, this,
					ModelPackage.FILE_INFO__DRAFT_COMMENTS);
		}
		return draftComments;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isReviewed() {
		return reviewed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setReviewed(boolean newReviewed) {
		boolean oldReviewed = reviewed;
		reviewed = newReviewed;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.FILE_INFO__REVIEWED, oldReviewed,
					reviewed));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case ModelPackage.FILE_INFO__NEW_COMMENTS:
			return ((InternalEList<?>) getNewComments()).basicRemove(otherEnd, msgs);
		case ModelPackage.FILE_INFO__DRAFT_COMMENTS:
			return ((InternalEList<?>) getDraftComments()).basicRemove(otherEnd, msgs);
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
		case ModelPackage.FILE_INFO__STATUS:
			return getStatus();
		case ModelPackage.FILE_INFO__BINARY:
			return isBinary();
		case ModelPackage.FILE_INFO__OLD_PATH:
			return getOld_path();
		case ModelPackage.FILE_INFO__LINES_INSERTED:
			return getLines_inserted();
		case ModelPackage.FILE_INFO__LINES_DELETED:
			return getLines_deleted();
		case ModelPackage.FILE_INFO__CONTAINED_IN:
			if (resolve)
				return getContainedIn();
			return basicGetContainedIn();
		case ModelPackage.FILE_INFO__NEW_COMMENTS:
			return getNewComments();
		case ModelPackage.FILE_INFO__DRAFT_COMMENTS:
			return getDraftComments();
		case ModelPackage.FILE_INFO__REVIEWED:
			return isReviewed();
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
		case ModelPackage.FILE_INFO__STATUS:
			setStatus((String) newValue);
			return;
		case ModelPackage.FILE_INFO__BINARY:
			setBinary((Boolean) newValue);
			return;
		case ModelPackage.FILE_INFO__OLD_PATH:
			setOld_path((String) newValue);
			return;
		case ModelPackage.FILE_INFO__LINES_INSERTED:
			setLines_inserted((Integer) newValue);
			return;
		case ModelPackage.FILE_INFO__LINES_DELETED:
			setLines_deleted((Integer) newValue);
			return;
		case ModelPackage.FILE_INFO__CONTAINED_IN:
			setContainedIn((RevisionInfo) newValue);
			return;
		case ModelPackage.FILE_INFO__NEW_COMMENTS:
			getNewComments().clear();
			getNewComments().addAll((Collection<? extends CommentInfo>) newValue);
			return;
		case ModelPackage.FILE_INFO__DRAFT_COMMENTS:
			getDraftComments().clear();
			getDraftComments().addAll((Collection<? extends CommentInfo>) newValue);
			return;
		case ModelPackage.FILE_INFO__REVIEWED:
			setReviewed((Boolean) newValue);
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
		case ModelPackage.FILE_INFO__STATUS:
			setStatus(STATUS_EDEFAULT);
			return;
		case ModelPackage.FILE_INFO__BINARY:
			setBinary(BINARY_EDEFAULT);
			return;
		case ModelPackage.FILE_INFO__OLD_PATH:
			setOld_path(OLD_PATH_EDEFAULT);
			return;
		case ModelPackage.FILE_INFO__LINES_INSERTED:
			setLines_inserted(LINES_INSERTED_EDEFAULT);
			return;
		case ModelPackage.FILE_INFO__LINES_DELETED:
			setLines_deleted(LINES_DELETED_EDEFAULT);
			return;
		case ModelPackage.FILE_INFO__CONTAINED_IN:
			setContainedIn((RevisionInfo) null);
			return;
		case ModelPackage.FILE_INFO__NEW_COMMENTS:
			getNewComments().clear();
			return;
		case ModelPackage.FILE_INFO__DRAFT_COMMENTS:
			getDraftComments().clear();
			return;
		case ModelPackage.FILE_INFO__REVIEWED:
			setReviewed(REVIEWED_EDEFAULT);
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
		case ModelPackage.FILE_INFO__STATUS:
			return STATUS_EDEFAULT == null ? status != null : !STATUS_EDEFAULT.equals(status);
		case ModelPackage.FILE_INFO__BINARY:
			return binary != BINARY_EDEFAULT;
		case ModelPackage.FILE_INFO__OLD_PATH:
			return OLD_PATH_EDEFAULT == null ? old_path != null : !OLD_PATH_EDEFAULT.equals(old_path);
		case ModelPackage.FILE_INFO__LINES_INSERTED:
			return lines_inserted != LINES_INSERTED_EDEFAULT;
		case ModelPackage.FILE_INFO__LINES_DELETED:
			return lines_deleted != LINES_DELETED_EDEFAULT;
		case ModelPackage.FILE_INFO__CONTAINED_IN:
			return containedIn != null;
		case ModelPackage.FILE_INFO__NEW_COMMENTS:
			return newComments != null && !newComments.isEmpty();
		case ModelPackage.FILE_INFO__DRAFT_COMMENTS:
			return draftComments != null && !draftComments.isEmpty();
		case ModelPackage.FILE_INFO__REVIEWED:
			return reviewed != REVIEWED_EDEFAULT;
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
		result.append(" (status: "); //$NON-NLS-1$
		result.append(status);
		result.append(", binary: "); //$NON-NLS-1$
		result.append(binary);
		result.append(", old_path: "); //$NON-NLS-1$
		result.append(old_path);
		result.append(", lines_inserted: "); //$NON-NLS-1$
		result.append(lines_inserted);
		result.append(", lines_deleted: "); //$NON-NLS-1$
		result.append(lines_deleted);
		result.append(", reviewed: "); //$NON-NLS-1$
		result.append(reviewed);
		result.append(')');
		return result.toString();
	}

} //FileInfoImpl
