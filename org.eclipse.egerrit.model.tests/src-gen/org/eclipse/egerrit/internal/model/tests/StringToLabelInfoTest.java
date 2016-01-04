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
package org.eclipse.egerrit.internal.model.tests;

import java.util.Map;

import junit.framework.TestCase;

import junit.textui.TestRunner;

import org.eclipse.egerrit.internal.model.LabelInfo;
import org.eclipse.egerrit.internal.model.ModelFactory;
import org.eclipse.egerrit.internal.model.ModelPackage;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>String To Label Info</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class StringToLabelInfoTest extends TestCase {

	/**
	 * The fixture for this String To Label Info test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Map.Entry<String, LabelInfo> fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(StringToLabelInfoTest.class);
	}

	/**
	 * Constructs a new String To Label Info test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StringToLabelInfoTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this String To Label Info test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(Map.Entry<String, LabelInfo> fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this String To Label Info test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Map.Entry<String, LabelInfo> getFixture() {
		return fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected void setUp() throws Exception {
		setFixture((Map.Entry<String, LabelInfo>) ModelFactory.eINSTANCE
				.create(ModelPackage.Literals.STRING_TO_LABEL_INFO));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#tearDown()
	 * @generated
	 */
	@Override
	protected void tearDown() throws Exception {
		setFixture(null);
	}

} //StringToLabelInfoTest