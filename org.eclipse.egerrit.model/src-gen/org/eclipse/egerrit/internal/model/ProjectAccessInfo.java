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
package org.eclipse.egerrit.internal.model;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Project Access Info</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.egerrit.internal.model.ProjectAccessInfo#getRevision <em>Revision</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.ProjectAccessInfo#getInherits_from <em>Inherits from</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.ProjectAccessInfo#isIs_owner <em>Is owner</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.ProjectAccessInfo#getOwnerOf <em>Owner Of</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.ProjectAccessInfo#isCan_upload <em>Can upload</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.ProjectAccessInfo#isCan_add <em>Can add</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.model.ProjectAccessInfo#isConfig_visible <em>Config visible</em>}</li>
 * </ul>
 *
 * @see org.eclipse.egerrit.internal.model.ModelPackage#getProjectAccessInfo()
 * @model
 * @generated
 */
public interface ProjectAccessInfo extends EObject {
	/**
	 * Returns the value of the '<em><b>Revision</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Revision</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Revision</em>' attribute.
	 * @see #setRevision(String)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getProjectAccessInfo_Revision()
	 * @model
	 * @generated
	 */
	String getRevision();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.ProjectAccessInfo#getRevision <em>Revision</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Revision</em>' attribute.
	 * @see #getRevision()
	 * @generated
	 */
	void setRevision(String value);

	/**
	 * Returns the value of the '<em><b>Inherits from</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Inherits from</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Inherits from</em>' containment reference.
	 * @see #setInherits_from(ProjectInfo)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getProjectAccessInfo_Inherits_from()
	 * @model containment="true"
	 * @generated
	 */
	ProjectInfo getInherits_from();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.ProjectAccessInfo#getInherits_from <em>Inherits from</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Inherits from</em>' containment reference.
	 * @see #getInherits_from()
	 * @generated
	 */
	void setInherits_from(ProjectInfo value);

	/**
	 * Returns the value of the '<em><b>Is owner</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Is owner</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is owner</em>' attribute.
	 * @see #setIs_owner(boolean)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getProjectAccessInfo_Is_owner()
	 * @model
	 * @generated
	 */
	boolean isIs_owner();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.ProjectAccessInfo#isIs_owner <em>Is owner</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is owner</em>' attribute.
	 * @see #isIs_owner()
	 * @generated
	 */
	void setIs_owner(boolean value);

	/**
	 * Returns the value of the '<em><b>Owner Of</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owner Of</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Owner Of</em>' attribute list.
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getProjectAccessInfo_OwnerOf()
	 * @model
	 * @generated
	 */
	EList<String> getOwnerOf();

	/**
	 * Returns the value of the '<em><b>Can upload</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Can upload</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Can upload</em>' attribute.
	 * @see #setCan_upload(boolean)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getProjectAccessInfo_Can_upload()
	 * @model
	 * @generated
	 */
	boolean isCan_upload();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.ProjectAccessInfo#isCan_upload <em>Can upload</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Can upload</em>' attribute.
	 * @see #isCan_upload()
	 * @generated
	 */
	void setCan_upload(boolean value);

	/**
	 * Returns the value of the '<em><b>Can add</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Can add</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Can add</em>' attribute.
	 * @see #setCan_add(boolean)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getProjectAccessInfo_Can_add()
	 * @model
	 * @generated
	 */
	boolean isCan_add();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.ProjectAccessInfo#isCan_add <em>Can add</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Can add</em>' attribute.
	 * @see #isCan_add()
	 * @generated
	 */
	void setCan_add(boolean value);

	/**
	 * Returns the value of the '<em><b>Config visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Config visible</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Config visible</em>' attribute.
	 * @see #setConfig_visible(boolean)
	 * @see org.eclipse.egerrit.internal.model.ModelPackage#getProjectAccessInfo_Config_visible()
	 * @model
	 * @generated
	 */
	boolean isConfig_visible();

	/**
	 * Sets the value of the '{@link org.eclipse.egerrit.internal.model.ProjectAccessInfo#isConfig_visible <em>Config visible</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Config visible</em>' attribute.
	 * @see #isConfig_visible()
	 * @generated
	 */
	void setConfig_visible(boolean value);

} // ProjectAccessInfo
