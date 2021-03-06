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

import org.eclipse.egerrit.internal.rcptt.support.egerritecl.AddGerritServer;
import org.eclipse.egerrit.internal.rcptt.support.egerritecl.AmendReview;
import org.eclipse.egerrit.internal.rcptt.support.egerritecl.CreateReview;
import org.eclipse.egerrit.internal.rcptt.support.egerritecl.ImportProject;
import org.eclipse.egerrit.internal.rcptt.support.egerritecl.RemoveGerritServer;
import org.eclipse.egerrit.internal.rcptt.support.egerritecl.ReviewDescription;
import org.eclipse.egerrit.internal.rcptt.support.egerritecl.egerriteclFactory;
import org.eclipse.egerrit.internal.rcptt.support.egerritecl.egerriteclPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass reviewDescriptionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass amendReviewEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass importProjectEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass addGerritServerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass removeGerritServerEClass = null;

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
		EcorePackage.eINSTANCE.eClass();

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
	public EAttribute getCreateReview_IsDraft() {
		return (EAttribute)createReviewEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCreateReview_Filename() {
		return (EAttribute)createReviewEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCreateReview_FileContent() {
		return (EAttribute)createReviewEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getReviewDescription() {
		return reviewDescriptionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getReviewDescription_GerritServerURL() {
		return (EAttribute)reviewDescriptionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getReviewDescription_ProjectName() {
		return (EAttribute)reviewDescriptionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getReviewDescription_LocalClone() {
		return (EAttribute)reviewDescriptionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getReviewDescription_LastChangeId() {
		return (EAttribute)reviewDescriptionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getReviewDescription_IsDraft() {
		return (EAttribute)reviewDescriptionEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAmendReview() {
		return amendReviewEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAmendReview_Review() {
		return (EReference)amendReviewEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAmendReview_IsDraft() {
		return (EAttribute)amendReviewEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getImportProject() {
		return importProjectEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getImportProject_Review() {
		return (EReference)importProjectEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getImportProject_ProjectName() {
		return (EAttribute)importProjectEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getImportProject_Branch() {
		return (EAttribute)importProjectEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAddGerritServer() {
		return addGerritServerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAddGerritServer_ServerURL() {
		return (EAttribute)addGerritServerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAddGerritServer_ServerName() {
		return (EAttribute)addGerritServerEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAddGerritServer_UserName() {
		return (EAttribute)addGerritServerEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAddGerritServer_UserPwd() {
		return (EAttribute)addGerritServerEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRemoveGerritServer() {
		return removeGerritServerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRemoveGerritServer_ServerName() {
		return (EAttribute)removeGerritServerEClass.getEStructuralFeatures().get(0);
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
		createEAttribute(createReviewEClass, CREATE_REVIEW__IS_DRAFT);
		createEAttribute(createReviewEClass, CREATE_REVIEW__FILENAME);
		createEAttribute(createReviewEClass, CREATE_REVIEW__FILE_CONTENT);

		reviewDescriptionEClass = createEClass(REVIEW_DESCRIPTION);
		createEAttribute(reviewDescriptionEClass, REVIEW_DESCRIPTION__GERRIT_SERVER_URL);
		createEAttribute(reviewDescriptionEClass, REVIEW_DESCRIPTION__PROJECT_NAME);
		createEAttribute(reviewDescriptionEClass, REVIEW_DESCRIPTION__LOCAL_CLONE);
		createEAttribute(reviewDescriptionEClass, REVIEW_DESCRIPTION__LAST_CHANGE_ID);
		createEAttribute(reviewDescriptionEClass, REVIEW_DESCRIPTION__IS_DRAFT);

		amendReviewEClass = createEClass(AMEND_REVIEW);
		createEReference(amendReviewEClass, AMEND_REVIEW__REVIEW);
		createEAttribute(amendReviewEClass, AMEND_REVIEW__IS_DRAFT);

		importProjectEClass = createEClass(IMPORT_PROJECT);
		createEReference(importProjectEClass, IMPORT_PROJECT__REVIEW);
		createEAttribute(importProjectEClass, IMPORT_PROJECT__PROJECT_NAME);
		createEAttribute(importProjectEClass, IMPORT_PROJECT__BRANCH);

		addGerritServerEClass = createEClass(ADD_GERRIT_SERVER);
		createEAttribute(addGerritServerEClass, ADD_GERRIT_SERVER__SERVER_URL);
		createEAttribute(addGerritServerEClass, ADD_GERRIT_SERVER__SERVER_NAME);
		createEAttribute(addGerritServerEClass, ADD_GERRIT_SERVER__USER_NAME);
		createEAttribute(addGerritServerEClass, ADD_GERRIT_SERVER__USER_PWD);

		removeGerritServerEClass = createEClass(REMOVE_GERRIT_SERVER);
		createEAttribute(removeGerritServerEClass, REMOVE_GERRIT_SERVER__SERVER_NAME);
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
		EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		createReviewEClass.getESuperTypes().add(theCorePackage.getCommand());
		amendReviewEClass.getESuperTypes().add(theCorePackage.getCommand());
		importProjectEClass.getESuperTypes().add(theCorePackage.getCommand());
		addGerritServerEClass.getESuperTypes().add(theCorePackage.getCommand());
		removeGerritServerEClass.getESuperTypes().add(theCorePackage.getCommand());

		// Initialize classes, features, and operations; add parameters
		initEClass(createReviewEClass, CreateReview.class, "CreateReview", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getCreateReview_Server(), ecorePackage.getEString(), "server", "", 0, 1, CreateReview.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getCreateReview_Project(), ecorePackage.getEString(), "project", null, 0, 1, CreateReview.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getCreateReview_IsDraft(), ecorePackage.getEBoolean(), "isDraft", "false", 0, 1, CreateReview.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getCreateReview_Filename(), theEcorePackage.getEString(), "filename", null, 0, 1, CreateReview.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getCreateReview_FileContent(), theEcorePackage.getEString(), "fileContent", null, 0, 1, CreateReview.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(reviewDescriptionEClass, ReviewDescription.class, "ReviewDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getReviewDescription_GerritServerURL(), ecorePackage.getEString(), "gerritServerURL", null, 0, 1, ReviewDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getReviewDescription_ProjectName(), ecorePackage.getEString(), "projectName", null, 0, 1, ReviewDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getReviewDescription_LocalClone(), ecorePackage.getEString(), "localClone", null, 0, 1, ReviewDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getReviewDescription_LastChangeId(), ecorePackage.getEString(), "lastChangeId", null, 0, 1, ReviewDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getReviewDescription_IsDraft(), ecorePackage.getEBoolean(), "isDraft", null, 0, 1, ReviewDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(amendReviewEClass, AmendReview.class, "AmendReview", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getAmendReview_Review(), theEcorePackage.getEObject(), null, "review", null, 0, 1, AmendReview.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getAmendReview_IsDraft(), ecorePackage.getEBoolean(), "isDraft", "false", 0, 1, AmendReview.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$

		initEClass(importProjectEClass, ImportProject.class, "ImportProject", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getImportProject_Review(), theEcorePackage.getEObject(), null, "review", null, 0, 1, ImportProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getImportProject_ProjectName(), theEcorePackage.getEString(), "projectName", null, 0, 1, ImportProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getImportProject_Branch(), theEcorePackage.getEString(), "branch", null, 0, 1, ImportProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(addGerritServerEClass, AddGerritServer.class, "AddGerritServer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getAddGerritServer_ServerURL(), theEcorePackage.getEString(), "serverURL", null, 0, 1, AddGerritServer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getAddGerritServer_ServerName(), theEcorePackage.getEString(), "serverName", null, 0, 1, AddGerritServer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getAddGerritServer_UserName(), theEcorePackage.getEString(), "userName", null, 0, 1, AddGerritServer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getAddGerritServer_UserPwd(), theEcorePackage.getEString(), "userPwd", null, 0, 1, AddGerritServer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(removeGerritServerEClass, RemoveGerritServer.class, "RemoveGerritServer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getRemoveGerritServer_ServerName(), theEcorePackage.getEString(), "serverName", null, 0, 1, RemoveGerritServer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

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
			 "returns", "A repo description object" //$NON-NLS-1$ //$NON-NLS-2$
		   });
	}

} //egerriteclPackageImpl
