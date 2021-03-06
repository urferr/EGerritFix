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

import org.eclipse.egerrit.internal.rcptt.support.egerritecl.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class egerriteclFactoryImpl extends EFactoryImpl implements egerriteclFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static egerriteclFactory init() {
		try {
			egerriteclFactory theegerriteclFactory = (egerriteclFactory)EPackage.Registry.INSTANCE.getEFactory(egerriteclPackage.eNS_URI);
			if (theegerriteclFactory != null) {
				return theegerriteclFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new egerriteclFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public egerriteclFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case egerriteclPackage.CREATE_REVIEW: return createCreateReview();
			case egerriteclPackage.REVIEW_DESCRIPTION: return createReviewDescription();
			case egerriteclPackage.AMEND_REVIEW: return createAmendReview();
			case egerriteclPackage.IMPORT_PROJECT: return createImportProject();
			case egerriteclPackage.ADD_GERRIT_SERVER: return createAddGerritServer();
			case egerriteclPackage.REMOVE_GERRIT_SERVER: return createRemoveGerritServer();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CreateReview createCreateReview() {
		CreateReviewImpl createReview = new CreateReviewImpl();
		return createReview;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReviewDescription createReviewDescription() {
		ReviewDescriptionImpl reviewDescription = new ReviewDescriptionImpl();
		return reviewDescription;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AmendReview createAmendReview() {
		AmendReviewImpl amendReview = new AmendReviewImpl();
		return amendReview;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ImportProject createImportProject() {
		ImportProjectImpl importProject = new ImportProjectImpl();
		return importProject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AddGerritServer createAddGerritServer() {
		AddGerritServerImpl addGerritServer = new AddGerritServerImpl();
		return addGerritServer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RemoveGerritServer createRemoveGerritServer() {
		RemoveGerritServerImpl removeGerritServer = new RemoveGerritServerImpl();
		return removeGerritServer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public egerriteclPackage getegerriteclPackage() {
		return (egerriteclPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static egerriteclPackage getPackage() {
		return egerriteclPackage.eINSTANCE;
	}

} //egerriteclFactoryImpl
