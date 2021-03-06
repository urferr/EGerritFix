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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import org.eclipse.rcptt.ecl.core.Command;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.egerriteclPackage
 * @generated
 */
public class egerriteclSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static egerriteclPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public egerriteclSwitch() {
		if (modelPackage == null) {
			modelPackage = egerriteclPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case egerriteclPackage.CREATE_REVIEW: {
				CreateReview createReview = (CreateReview)theEObject;
				T result = caseCreateReview(createReview);
				if (result == null) result = caseCommand(createReview);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case egerriteclPackage.REVIEW_DESCRIPTION: {
				ReviewDescription reviewDescription = (ReviewDescription)theEObject;
				T result = caseReviewDescription(reviewDescription);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case egerriteclPackage.AMEND_REVIEW: {
				AmendReview amendReview = (AmendReview)theEObject;
				T result = caseAmendReview(amendReview);
				if (result == null) result = caseCommand(amendReview);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case egerriteclPackage.IMPORT_PROJECT: {
				ImportProject importProject = (ImportProject)theEObject;
				T result = caseImportProject(importProject);
				if (result == null) result = caseCommand(importProject);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case egerriteclPackage.ADD_GERRIT_SERVER: {
				AddGerritServer addGerritServer = (AddGerritServer)theEObject;
				T result = caseAddGerritServer(addGerritServer);
				if (result == null) result = caseCommand(addGerritServer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case egerriteclPackage.REMOVE_GERRIT_SERVER: {
				RemoveGerritServer removeGerritServer = (RemoveGerritServer)theEObject;
				T result = caseRemoveGerritServer(removeGerritServer);
				if (result == null) result = caseCommand(removeGerritServer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Create Review</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Create Review</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCreateReview(CreateReview object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Review Description</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Review Description</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseReviewDescription(ReviewDescription object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Amend Review</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Amend Review</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAmendReview(AmendReview object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Import Project</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Import Project</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseImportProject(ImportProject object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Add Gerrit Server</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Add Gerrit Server</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAddGerritServer(AddGerritServer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Remove Gerrit Server</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Remove Gerrit Server</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRemoveGerritServer(RemoveGerritServer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Command</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Command</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCommand(Command object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //egerriteclSwitch
