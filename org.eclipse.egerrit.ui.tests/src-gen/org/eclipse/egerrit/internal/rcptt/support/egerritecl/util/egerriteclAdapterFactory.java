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
package org.eclipse.egerrit.internal.rcptt.support.egerritecl.util;

import org.eclipse.egerrit.internal.rcptt.support.egerritecl.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.rcptt.ecl.core.Command;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.egerriteclPackage
 * @generated
 */
public class egerriteclAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static egerriteclPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public egerriteclAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = egerriteclPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected egerriteclSwitch<Adapter> modelSwitch =
		new egerriteclSwitch<Adapter>() {
			@Override
			public Adapter caseCreateReview(CreateReview object) {
				return createCreateReviewAdapter();
			}
			@Override
			public Adapter caseReviewDescription(ReviewDescription object) {
				return createReviewDescriptionAdapter();
			}
			@Override
			public Adapter caseAmendReview(AmendReview object) {
				return createAmendReviewAdapter();
			}
			@Override
			public Adapter caseImportProject(ImportProject object) {
				return createImportProjectAdapter();
			}
			@Override
			public Adapter caseAddGerritServer(AddGerritServer object) {
				return createAddGerritServerAdapter();
			}
			@Override
			public Adapter caseRemoveGerritServer(RemoveGerritServer object) {
				return createRemoveGerritServerAdapter();
			}
			@Override
			public Adapter caseCommand(Command object) {
				return createCommandAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.CreateReview <em>Create Review</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.CreateReview
	 * @generated
	 */
	public Adapter createCreateReviewAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.ReviewDescription <em>Review Description</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.ReviewDescription
	 * @generated
	 */
	public Adapter createReviewDescriptionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.AmendReview <em>Amend Review</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.AmendReview
	 * @generated
	 */
	public Adapter createAmendReviewAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.ImportProject <em>Import Project</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.ImportProject
	 * @generated
	 */
	public Adapter createImportProjectAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.AddGerritServer <em>Add Gerrit Server</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.AddGerritServer
	 * @generated
	 */
	public Adapter createAddGerritServerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.egerrit.internal.rcptt.support.egerritecl.RemoveGerritServer <em>Remove Gerrit Server</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.RemoveGerritServer
	 * @generated
	 */
	public Adapter createRemoveGerritServerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.rcptt.ecl.core.Command <em>Command</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.rcptt.ecl.core.Command
	 * @generated
	 */
	public Adapter createCommandAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //egerriteclAdapterFactory
