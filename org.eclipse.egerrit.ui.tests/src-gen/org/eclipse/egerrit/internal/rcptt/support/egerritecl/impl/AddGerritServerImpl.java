/**
 *   Copyright (c) 2016-2017 Ericsson AB
 *  
 *   All rights reserved. This program and the accompanying materials are
 *   made available under the terms of the Eclipse Public License v1.0 which
 *   accompanies this distribution, and is available at
 *   http://www.eclipse.org/legal/epl-v10.html
 *  
 *   Contributors:
 *     Ericsson AB - Initial API and implementation
 */
package org.eclipse.egerrit.internal.rcptt.support.egerritecl.impl;

import org.eclipse.egerrit.internal.rcptt.support.egerritecl.AddGerritServer;
import org.eclipse.egerrit.internal.rcptt.support.egerritecl.egerriteclPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.rcptt.ecl.core.impl.CommandImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Add Gerrit Server</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.impl.AddGerritServerImpl#getServerURL <em>Server URL</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.impl.AddGerritServerImpl#getServerName <em>Server Name</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.impl.AddGerritServerImpl#getUserName <em>User Name</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.impl.AddGerritServerImpl#getUserPwd <em>User Pwd</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AddGerritServerImpl extends CommandImpl implements AddGerritServer {
	/**
	 * The default value of the '{@link #getServerURL() <em>Server URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getServerURL()
	 * @generated
	 * @ordered
	 */
	protected static final String SERVER_URL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getServerURL() <em>Server URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getServerURL()
	 * @generated
	 * @ordered
	 */
	protected String serverURL = SERVER_URL_EDEFAULT;

	/**
	 * The default value of the '{@link #getServerName() <em>Server Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getServerName()
	 * @generated
	 * @ordered
	 */
	protected static final String SERVER_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getServerName() <em>Server Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getServerName()
	 * @generated
	 * @ordered
	 */
	protected String serverName = SERVER_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getUserName() <em>User Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUserName()
	 * @generated
	 * @ordered
	 */
	protected static final String USER_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getUserName() <em>User Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUserName()
	 * @generated
	 * @ordered
	 */
	protected String userName = USER_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getUserPwd() <em>User Pwd</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUserPwd()
	 * @generated
	 * @ordered
	 */
	protected static final String USER_PWD_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getUserPwd() <em>User Pwd</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUserPwd()
	 * @generated
	 * @ordered
	 */
	protected String userPwd = USER_PWD_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AddGerritServerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return egerriteclPackage.Literals.ADD_GERRIT_SERVER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getServerURL() {
		return serverURL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setServerURL(String newServerURL) {
		String oldServerURL = serverURL;
		serverURL = newServerURL;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, egerriteclPackage.ADD_GERRIT_SERVER__SERVER_URL, oldServerURL, serverURL));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getServerName() {
		return serverName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setServerName(String newServerName) {
		String oldServerName = serverName;
		serverName = newServerName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, egerriteclPackage.ADD_GERRIT_SERVER__SERVER_NAME, oldServerName, serverName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUserName(String newUserName) {
		String oldUserName = userName;
		userName = newUserName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, egerriteclPackage.ADD_GERRIT_SERVER__USER_NAME, oldUserName, userName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getUserPwd() {
		return userPwd;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUserPwd(String newUserPwd) {
		String oldUserPwd = userPwd;
		userPwd = newUserPwd;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, egerriteclPackage.ADD_GERRIT_SERVER__USER_PWD, oldUserPwd, userPwd));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case egerriteclPackage.ADD_GERRIT_SERVER__SERVER_URL:
				return getServerURL();
			case egerriteclPackage.ADD_GERRIT_SERVER__SERVER_NAME:
				return getServerName();
			case egerriteclPackage.ADD_GERRIT_SERVER__USER_NAME:
				return getUserName();
			case egerriteclPackage.ADD_GERRIT_SERVER__USER_PWD:
				return getUserPwd();
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
			case egerriteclPackage.ADD_GERRIT_SERVER__SERVER_URL:
				setServerURL((String)newValue);
				return;
			case egerriteclPackage.ADD_GERRIT_SERVER__SERVER_NAME:
				setServerName((String)newValue);
				return;
			case egerriteclPackage.ADD_GERRIT_SERVER__USER_NAME:
				setUserName((String)newValue);
				return;
			case egerriteclPackage.ADD_GERRIT_SERVER__USER_PWD:
				setUserPwd((String)newValue);
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
			case egerriteclPackage.ADD_GERRIT_SERVER__SERVER_URL:
				setServerURL(SERVER_URL_EDEFAULT);
				return;
			case egerriteclPackage.ADD_GERRIT_SERVER__SERVER_NAME:
				setServerName(SERVER_NAME_EDEFAULT);
				return;
			case egerriteclPackage.ADD_GERRIT_SERVER__USER_NAME:
				setUserName(USER_NAME_EDEFAULT);
				return;
			case egerriteclPackage.ADD_GERRIT_SERVER__USER_PWD:
				setUserPwd(USER_PWD_EDEFAULT);
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
			case egerriteclPackage.ADD_GERRIT_SERVER__SERVER_URL:
				return SERVER_URL_EDEFAULT == null ? serverURL != null : !SERVER_URL_EDEFAULT.equals(serverURL);
			case egerriteclPackage.ADD_GERRIT_SERVER__SERVER_NAME:
				return SERVER_NAME_EDEFAULT == null ? serverName != null : !SERVER_NAME_EDEFAULT.equals(serverName);
			case egerriteclPackage.ADD_GERRIT_SERVER__USER_NAME:
				return USER_NAME_EDEFAULT == null ? userName != null : !USER_NAME_EDEFAULT.equals(userName);
			case egerriteclPackage.ADD_GERRIT_SERVER__USER_PWD:
				return USER_PWD_EDEFAULT == null ? userPwd != null : !USER_PWD_EDEFAULT.equals(userPwd);
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
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (serverURL: "); //$NON-NLS-1$
		result.append(serverURL);
		result.append(", serverName: "); //$NON-NLS-1$
		result.append(serverName);
		result.append(", userName: "); //$NON-NLS-1$
		result.append(userName);
		result.append(", userPwd: "); //$NON-NLS-1$
		result.append(userPwd);
		result.append(')');
		return result.toString();
	}

} //AddGerritServerImpl
