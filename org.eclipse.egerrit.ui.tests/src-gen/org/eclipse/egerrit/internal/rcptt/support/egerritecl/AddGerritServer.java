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
package org.eclipse.egerrit.internal.rcptt.support.egerritecl;

import org.eclipse.rcptt.ecl.core.Command;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Add Gerrit Server</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.AddGerritServer#getServerURL <em>Server URL</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.AddGerritServer#getServerName <em>Server Name</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.AddGerritServer#getUserName <em>User Name</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.AddGerritServer#getUserPwd <em>User Pwd</em>}</li>
 * </ul>
 *
 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.egerriteclPackage#getAddGerritServer()
 * @model
 * @generated
 */
public interface AddGerritServer extends Command {
	/**
	 * Returns the value of the '<em><b>Server URL</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Server URL</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Server URL</em>' attribute.
	 * @see #setServerURL(String)
	 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.egerriteclPackage#getAddGerritServer_ServerURL()
	 * @model
	 * @generated
	 */
	String getServerURL();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.AddGerritServer#getServerURL <em>Server URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Server URL</em>' attribute.
	 * @see #getServerURL()
	 * @generated
	 */
	void setServerURL(String value);

	/**
	 * Returns the value of the '<em><b>Server Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Server Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Server Name</em>' attribute.
	 * @see #setServerName(String)
	 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.egerriteclPackage#getAddGerritServer_ServerName()
	 * @model
	 * @generated
	 */
	String getServerName();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.AddGerritServer#getServerName <em>Server Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Server Name</em>' attribute.
	 * @see #getServerName()
	 * @generated
	 */
	void setServerName(String value);

	/**
	 * Returns the value of the '<em><b>User Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>User Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>User Name</em>' attribute.
	 * @see #setUserName(String)
	 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.egerriteclPackage#getAddGerritServer_UserName()
	 * @model
	 * @generated
	 */
	String getUserName();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.AddGerritServer#getUserName <em>User Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>User Name</em>' attribute.
	 * @see #getUserName()
	 * @generated
	 */
	void setUserName(String value);

	/**
	 * Returns the value of the '<em><b>User Pwd</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>User Pwd</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>User Pwd</em>' attribute.
	 * @see #setUserPwd(String)
	 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.egerriteclPackage#getAddGerritServer_UserPwd()
	 * @model
	 * @generated
	 */
	String getUserPwd();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.AddGerritServer#getUserPwd <em>User Pwd</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>User Pwd</em>' attribute.
	 * @see #getUserPwd()
	 * @generated
	 */
	void setUserPwd(String value);

} // AddGerritServer
