/**
 */
package org.franca.core.franca.tests;

import junit.framework.TestCase;

import junit.textui.TestRunner;

import org.franca.core.franca.FUnit;
import org.franca.core.franca.FrancaFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>FUnit</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class FUnitTest extends TestCase {

	/**
	 * The fixture for this FUnit test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FUnit fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(FUnitTest.class);
	}

	/**
	 * Constructs a new FUnit test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FUnitTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this FUnit test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(FUnit fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this FUnit test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FUnit getFixture() {
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
		setFixture(FrancaFactory.eINSTANCE.createFUnit());
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

} //FUnitTest
