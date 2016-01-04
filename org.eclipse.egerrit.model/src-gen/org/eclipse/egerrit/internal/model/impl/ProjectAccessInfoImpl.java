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

import org.eclipse.egerrit.internal.model.ModelPackage;
import org.eclipse.egerrit.internal.model.ProjectAccessInfo;
import org.eclipse.egerrit.internal.model.ProjectInfo;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Project Access Info</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.ProjectAccessInfoImpl#getRevision <em>Revision</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.ProjectAccessInfoImpl#getInherits_from <em>Inherits from</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.ProjectAccessInfoImpl#isIs_owner <em>Is owner</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.ProjectAccessInfoImpl#getOwnerOf <em>Owner Of</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.ProjectAccessInfoImpl#isCan_upload <em>Can upload</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.ProjectAccessInfoImpl#isCan_add <em>Can add</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.impl.ProjectAccessInfoImpl#isConfig_visible <em>Config visible</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ProjectAccessInfoImpl extends MinimalEObjectImpl.Container implements ProjectAccessInfo {
	/**
	 * The default value of the '{@link #getRevision() <em>Revision</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRevision()
	 * @generated
	 * @ordered
	 */
	protected static final String REVISION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRevision() <em>Revision</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRevision()
	 * @generated
	 * @ordered
	 */
	protected String revision = REVISION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getInherits_from() <em>Inherits from</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInherits_from()
	 * @generated
	 * @ordered
	 */
	protected ProjectInfo inherits_from;

	/**
	 * The default value of the '{@link #isIs_owner() <em>Is owner</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIs_owner()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_OWNER_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isIs_owner() <em>Is owner</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIs_owner()
	 * @generated
	 * @ordered
	 */
	protected boolean is_owner = IS_OWNER_EDEFAULT;

	/**
	 * The cached value of the '{@link #getOwnerOf() <em>Owner Of</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwnerOf()
	 * @generated
	 * @ordered
	 */
	protected EList<String> ownerOf;

	/**
	 * The default value of the '{@link #isCan_upload() <em>Can upload</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCan_upload()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CAN_UPLOAD_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isCan_upload() <em>Can upload</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCan_upload()
	 * @generated
	 * @ordered
	 */
	protected boolean can_upload = CAN_UPLOAD_EDEFAULT;

	/**
	 * The default value of the '{@link #isCan_add() <em>Can add</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCan_add()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CAN_ADD_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isCan_add() <em>Can add</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCan_add()
	 * @generated
	 * @ordered
	 */
	protected boolean can_add = CAN_ADD_EDEFAULT;

	/**
	 * The default value of the '{@link #isConfig_visible() <em>Config visible</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isConfig_visible()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CONFIG_VISIBLE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isConfig_visible() <em>Config visible</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isConfig_visible()
	 * @generated
	 * @ordered
	 */
	protected boolean config_visible = CONFIG_VISIBLE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProjectAccessInfoImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.PROJECT_ACCESS_INFO;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getRevision() {
		return revision;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRevision(String newRevision) {
		String oldRevision = revision;
		revision = newRevision;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.PROJECT_ACCESS_INFO__REVISION,
					oldRevision, revision));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ProjectInfo getInherits_from() {
		return inherits_from;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetInherits_from(ProjectInfo newInherits_from, NotificationChain msgs) {
		ProjectInfo oldInherits_from = inherits_from;
		inherits_from = newInherits_from;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					ModelPackage.PROJECT_ACCESS_INFO__INHERITS_FROM, oldInherits_from, newInherits_from);
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
	public void setInherits_from(ProjectInfo newInherits_from) {
		if (newInherits_from != inherits_from) {
			NotificationChain msgs = null;
			if (inherits_from != null)
				msgs = ((InternalEObject) inherits_from).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - ModelPackage.PROJECT_ACCESS_INFO__INHERITS_FROM, null, msgs);
			if (newInherits_from != null)
				msgs = ((InternalEObject) newInherits_from).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - ModelPackage.PROJECT_ACCESS_INFO__INHERITS_FROM, null, msgs);
			msgs = basicSetInherits_from(newInherits_from, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.PROJECT_ACCESS_INFO__INHERITS_FROM,
					newInherits_from, newInherits_from));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isIs_owner() {
		return is_owner;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setIs_owner(boolean newIs_owner) {
		boolean oldIs_owner = is_owner;
		is_owner = newIs_owner;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.PROJECT_ACCESS_INFO__IS_OWNER,
					oldIs_owner, is_owner));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<String> getOwnerOf() {
		if (ownerOf == null) {
			ownerOf = new EDataTypeUniqueEList<String>(String.class, this, ModelPackage.PROJECT_ACCESS_INFO__OWNER_OF);
		}
		return ownerOf;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isCan_upload() {
		return can_upload;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCan_upload(boolean newCan_upload) {
		boolean oldCan_upload = can_upload;
		can_upload = newCan_upload;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.PROJECT_ACCESS_INFO__CAN_UPLOAD,
					oldCan_upload, can_upload));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isCan_add() {
		return can_add;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCan_add(boolean newCan_add) {
		boolean oldCan_add = can_add;
		can_add = newCan_add;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.PROJECT_ACCESS_INFO__CAN_ADD, oldCan_add,
					can_add));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isConfig_visible() {
		return config_visible;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setConfig_visible(boolean newConfig_visible) {
		boolean oldConfig_visible = config_visible;
		config_visible = newConfig_visible;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.PROJECT_ACCESS_INFO__CONFIG_VISIBLE,
					oldConfig_visible, config_visible));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case ModelPackage.PROJECT_ACCESS_INFO__INHERITS_FROM:
			return basicSetInherits_from(null, msgs);
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
		case ModelPackage.PROJECT_ACCESS_INFO__REVISION:
			return getRevision();
		case ModelPackage.PROJECT_ACCESS_INFO__INHERITS_FROM:
			return getInherits_from();
		case ModelPackage.PROJECT_ACCESS_INFO__IS_OWNER:
			return isIs_owner();
		case ModelPackage.PROJECT_ACCESS_INFO__OWNER_OF:
			return getOwnerOf();
		case ModelPackage.PROJECT_ACCESS_INFO__CAN_UPLOAD:
			return isCan_upload();
		case ModelPackage.PROJECT_ACCESS_INFO__CAN_ADD:
			return isCan_add();
		case ModelPackage.PROJECT_ACCESS_INFO__CONFIG_VISIBLE:
			return isConfig_visible();
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
		case ModelPackage.PROJECT_ACCESS_INFO__REVISION:
			setRevision((String) newValue);
			return;
		case ModelPackage.PROJECT_ACCESS_INFO__INHERITS_FROM:
			setInherits_from((ProjectInfo) newValue);
			return;
		case ModelPackage.PROJECT_ACCESS_INFO__IS_OWNER:
			setIs_owner((Boolean) newValue);
			return;
		case ModelPackage.PROJECT_ACCESS_INFO__OWNER_OF:
			getOwnerOf().clear();
			getOwnerOf().addAll((Collection<? extends String>) newValue);
			return;
		case ModelPackage.PROJECT_ACCESS_INFO__CAN_UPLOAD:
			setCan_upload((Boolean) newValue);
			return;
		case ModelPackage.PROJECT_ACCESS_INFO__CAN_ADD:
			setCan_add((Boolean) newValue);
			return;
		case ModelPackage.PROJECT_ACCESS_INFO__CONFIG_VISIBLE:
			setConfig_visible((Boolean) newValue);
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
		case ModelPackage.PROJECT_ACCESS_INFO__REVISION:
			setRevision(REVISION_EDEFAULT);
			return;
		case ModelPackage.PROJECT_ACCESS_INFO__INHERITS_FROM:
			setInherits_from((ProjectInfo) null);
			return;
		case ModelPackage.PROJECT_ACCESS_INFO__IS_OWNER:
			setIs_owner(IS_OWNER_EDEFAULT);
			return;
		case ModelPackage.PROJECT_ACCESS_INFO__OWNER_OF:
			getOwnerOf().clear();
			return;
		case ModelPackage.PROJECT_ACCESS_INFO__CAN_UPLOAD:
			setCan_upload(CAN_UPLOAD_EDEFAULT);
			return;
		case ModelPackage.PROJECT_ACCESS_INFO__CAN_ADD:
			setCan_add(CAN_ADD_EDEFAULT);
			return;
		case ModelPackage.PROJECT_ACCESS_INFO__CONFIG_VISIBLE:
			setConfig_visible(CONFIG_VISIBLE_EDEFAULT);
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
		case ModelPackage.PROJECT_ACCESS_INFO__REVISION:
			return REVISION_EDEFAULT == null ? revision != null : !REVISION_EDEFAULT.equals(revision);
		case ModelPackage.PROJECT_ACCESS_INFO__INHERITS_FROM:
			return inherits_from != null;
		case ModelPackage.PROJECT_ACCESS_INFO__IS_OWNER:
			return is_owner != IS_OWNER_EDEFAULT;
		case ModelPackage.PROJECT_ACCESS_INFO__OWNER_OF:
			return ownerOf != null && !ownerOf.isEmpty();
		case ModelPackage.PROJECT_ACCESS_INFO__CAN_UPLOAD:
			return can_upload != CAN_UPLOAD_EDEFAULT;
		case ModelPackage.PROJECT_ACCESS_INFO__CAN_ADD:
			return can_add != CAN_ADD_EDEFAULT;
		case ModelPackage.PROJECT_ACCESS_INFO__CONFIG_VISIBLE:
			return config_visible != CONFIG_VISIBLE_EDEFAULT;
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
		result.append(" (revision: "); //$NON-NLS-1$
		result.append(revision);
		result.append(", is_owner: "); //$NON-NLS-1$
		result.append(is_owner);
		result.append(", ownerOf: "); //$NON-NLS-1$
		result.append(ownerOf);
		result.append(", can_upload: "); //$NON-NLS-1$
		result.append(can_upload);
		result.append(", can_add: "); //$NON-NLS-1$
		result.append(can_add);
		result.append(", config_visible: "); //$NON-NLS-1$
		result.append(config_visible);
		result.append(')');
		return result.toString();
	}

} //ProjectAccessInfoImpl
