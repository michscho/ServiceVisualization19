/**
 */
package org.franca.core.franca.tests;

import junit.framework.TestCase;

import junit.textui.TestRunner;

import org.franca.core.franca.FGuard;
import org.franca.core.franca.FrancaFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>FGuard</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class FGuardTest extends TestCase {

	/**
	 * The fixture for this FGuard test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FGuard fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(FGuardTest.class);
	}

	/**
	 * Constructs a new FGuard test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FGuardTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this FGuard test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(FGuard fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this FGuard test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FGuard getFixture() {
		return fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(FrancaFactory.eINSTANCE.createFGuard());
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

} //FGuardTest
