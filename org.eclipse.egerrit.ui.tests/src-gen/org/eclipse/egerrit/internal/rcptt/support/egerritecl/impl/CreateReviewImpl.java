/**
 *   Copyright (c) 2016 Ericsson AB
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

import org.eclipse.egerrit.internal.rcptt.support.egerritecl.CreateReview;
import org.eclipse.egerrit.internal.rcptt.support.egerritecl.egerriteclPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.rcptt.ecl.core.impl.CommandImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Create Review</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.impl.CreateReviewImpl#getServer <em>Server</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.impl.CreateReviewImpl#getProject <em>Project</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.impl.CreateReviewImpl#isIsDraft <em>Is Draft</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.impl.CreateReviewImpl#getFilename <em>Filename</em>}</li>
 *   <li>{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.impl.CreateReviewImpl#getFileContent <em>File Content</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CreateReviewImpl extends CommandImpl implements CreateReview {
	/**
	 * The default value of the '{@link #getServer() <em>Server</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getServer()
	 * @generated
	 * @ordered
	 */
	protected static final String SERVER_EDEFAULT = ""; //$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getServer() <em>Server</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getServer()
	 * @generated
	 * @ordered
	 */
	protected String server = SERVER_EDEFAULT;

	/**
	 * The default value of the '{@link #getProject() <em>Project</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProject()
	 * @generated
	 * @ordered
	 */
	protected static final String PROJECT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getProject() <em>Project</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProject()
	 * @generated
	 * @ordered
	 */
	protected String project = PROJECT_EDEFAULT;

	/**
	 * The default value of the '{@link #isIsDraft() <em>Is Draft</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsDraft()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_DRAFT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isIsDraft() <em>Is Draft</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsDraft()
	 * @generated
	 * @ordered
	 */
	protected boolean isDraft = IS_DRAFT_EDEFAULT;

	/**
	 * The default value of the '{@link #getFilename() <em>Filename</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFilename()
	 * @generated
	 * @ordered
	 */
	protected static final String FILENAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFilename() <em>Filename</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFilename()
	 * @generated
	 * @ordered
	 */
	protected String filename = FILENAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getFileContent() <em>File Content</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFileContent()
	 * @generated
	 * @ordered
	 */
	protected static final String FILE_CONTENT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFileContent() <em>File Content</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFileContent()
	 * @generated
	 * @ordered
	 */
	protected String fileContent = FILE_CONTENT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CreateReviewImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return egerriteclPackage.Literals.CREATE_REVIEW;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getServer() {
		return server;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setServer(String newServer) {
		String oldServer = server;
		server = newServer;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, egerriteclPackage.CREATE_REVIEW__SERVER, oldServer, server));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getProject() {
		return project;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProject(String newProject) {
		String oldProject = project;
		project = newProject;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, egerriteclPackage.CREATE_REVIEW__PROJECT, oldProject, project));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isIsDraft() {
		return isDraft;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIsDraft(boolean newIsDraft) {
		boolean oldIsDraft = isDraft;
		isDraft = newIsDraft;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, egerriteclPackage.CREATE_REVIEW__IS_DRAFT, oldIsDraft, isDraft));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFilename(String newFilename) {
		String oldFilename = filename;
		filename = newFilename;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, egerriteclPackage.CREATE_REVIEW__FILENAME, oldFilename, filename));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFileContent() {
		return fileContent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFileContent(String newFileContent) {
		String oldFileContent = fileContent;
		fileContent = newFileContent;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, egerriteclPackage.CREATE_REVIEW__FILE_CONTENT, oldFileContent, fileContent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case egerriteclPackage.CREATE_REVIEW__SERVER:
				return getServer();
			case egerriteclPackage.CREATE_REVIEW__PROJECT:
				return getProject();
			case egerriteclPackage.CREATE_REVIEW__IS_DRAFT:
				return isIsDraft();
			case egerriteclPackage.CREATE_REVIEW__FILENAME:
				return getFilename();
			case egerriteclPackage.CREATE_REVIEW__FILE_CONTENT:
				return getFileContent();
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
			case egerriteclPackage.CREATE_REVIEW__SERVER:
				setServer((String)newValue);
				return;
			case egerriteclPackage.CREATE_REVIEW__PROJECT:
				setProject((String)newValue);
				return;
			case egerriteclPackage.CREATE_REVIEW__IS_DRAFT:
				setIsDraft((Boolean)newValue);
				return;
			case egerriteclPackage.CREATE_REVIEW__FILENAME:
				setFilename((String)newValue);
				return;
			case egerriteclPackage.CREATE_REVIEW__FILE_CONTENT:
				setFileContent((String)newValue);
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
			case egerriteclPackage.CREATE_REVIEW__SERVER:
				setServer(SERVER_EDEFAULT);
				return;
			case egerriteclPackage.CREATE_REVIEW__PROJECT:
				setProject(PROJECT_EDEFAULT);
				return;
			case egerriteclPackage.CREATE_REVIEW__IS_DRAFT:
				setIsDraft(IS_DRAFT_EDEFAULT);
				return;
			case egerriteclPackage.CREATE_REVIEW__FILENAME:
				setFilename(FILENAME_EDEFAULT);
				return;
			case egerriteclPackage.CREATE_REVIEW__FILE_CONTENT:
				setFileContent(FILE_CONTENT_EDEFAULT);
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
			case egerriteclPackage.CREATE_REVIEW__SERVER:
				return SERVER_EDEFAULT == null ? server != null : !SERVER_EDEFAULT.equals(server);
			case egerriteclPackage.CREATE_REVIEW__PROJECT:
				return PROJECT_EDEFAULT == null ? project != null : !PROJECT_EDEFAULT.equals(project);
			case egerriteclPackage.CREATE_REVIEW__IS_DRAFT:
				return isDraft != IS_DRAFT_EDEFAULT;
			case egerriteclPackage.CREATE_REVIEW__FILENAME:
				return FILENAME_EDEFAULT == null ? filename != null : !FILENAME_EDEFAULT.equals(filename);
			case egerriteclPackage.CREATE_REVIEW__FILE_CONTENT:
				return FILE_CONTENT_EDEFAULT == null ? fileContent != null : !FILE_CONTENT_EDEFAULT.equals(fileContent);
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
		result.append(" (server: "); //$NON-NLS-1$
		result.append(server);
		result.append(", project: "); //$NON-NLS-1$
		result.append(project);
		result.append(", isDraft: "); //$NON-NLS-1$
		result.append(isDraft);
		result.append(", filename: "); //$NON-NLS-1$
		result.append(filename);
		result.append(", fileContent: "); //$NON-NLS-1$
		result.append(fileContent);
		result.append(')');
		return result.toString();
	}

} //CreateReviewImpl
