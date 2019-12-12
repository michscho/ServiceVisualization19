/**
 */
package org.franca.core.franca.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test suite for the '<em><b>franca</b></em>' package.
 * <!-- end-user-doc -->
 * @generated
 */
public class FrancaTests extends TestSuite {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(suite());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static Test suite() {
		TestSuite suite = new FrancaTests("franca Tests");
		suite.addTestSuite(FAnnotationTest.class);
		suite.addTestSuite(FRequiresTest.class);
		suite.addTestSuite(FProvidesTest.class);
		return suite;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FrancaTests(String name) {
		super(name);
	}

} //FrancaTests
