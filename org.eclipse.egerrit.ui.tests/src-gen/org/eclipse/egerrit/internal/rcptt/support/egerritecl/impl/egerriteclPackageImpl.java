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
import org.eclipse.egerrit.internal.rcptt.support.egerritecl.egerriteclFactory;
import org.eclipse.egerrit.internal.rcptt.support.egerritecl.egerriteclPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.rcptt.ecl.core.CorePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class egerriteclPackageImpl extends EPackageImpl implements egerriteclPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass createReviewEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.egerrit.internal.rcptt.support.egerritecl.egerriteclPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private egerriteclPackageImpl() {
		super(eNS_URI, egerriteclFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link egerriteclPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static egerriteclPackage init() {
		if (isInited) return (egerriteclPackage)EPackage.Registry.INSTANCE.getEPackage(egerriteclPackage.eNS_URI);

		// Obtain or create and register package
		egerriteclPackageImpl theegerriteclPackage = (egerriteclPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof egerriteclPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new egerriteclPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		CorePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theegerriteclPackage.createPackageContents();

		// Initialize created meta-data
		theegerriteclPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theegerriteclPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(egerriteclPackage.eNS_URI, theegerriteclPackage);
		return theegerriteclPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCreateReview() {
		return createReviewEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCreateReview_Server() {
		return (EAttribute)createReviewEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCreateReview_Project() {
		return (EAttribute)createReviewEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public egerriteclFactory getegerriteclFactory() {
		return (egerriteclFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		createReviewEClass = createEClass(CREATE_REVIEW);
		createEAttribute(createReviewEClass, CREATE_REVIEW__SERVER);
		createEAttribute(createReviewEClass, CREATE_REVIEW__PROJECT);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		CorePackage theCorePackage = (CorePackage)EPackage.Registry.INSTANCE.getEPackage(CorePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		createReviewEClass.getESuperTypes().add(theCorePackage.getCommand());

		// Initialize classes, features, and operations; add parameters
		initEClass(createReviewEClass, CreateReview.class, "CreateReview", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getCreateReview_Server(), ecorePackage.getEString(), "server", "", 0, 1, CreateReview.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getCreateReview_Project(), ecorePackage.getEString(), "project", null, 0, 1, CreateReview.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http://www.eclipse.org/ecl/docs
		createDocsAnnotations();
	}

	/**
	 * Initializes the annotations for <b>http://www.eclipse.org/ecl/docs</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createDocsAnnotations() {
		String source = "http://www.eclipse.org/ecl/docs"; //$NON-NLS-1$	
		addAnnotation
		  (createReviewEClass, 
		   source, 
		   new String[] {
			 "description", "Create a review", //$NON-NLS-1$ //$NON-NLS-2$
			 "returns", "ChangeId of the created review" //$NON-NLS-1$ //$NON-NLS-2$
		   });
	}

} //egerriteclPackageImpl
