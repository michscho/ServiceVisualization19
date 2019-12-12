/**
 */
package org.franca.core.franca.tests;

import junit.textui.TestRunner;

import org.franca.core.franca.FState;
import org.franca.core.franca.FrancaFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>FState</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class FStateTest extends FModelElementTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(FStateTest.class);
	}

	/**
	 * Constructs a new FState test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FStateTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this FState test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected FState getFixture() {
		return (FState)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(FrancaFactory.eINSTANCE.createFState());
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

} //FStateTest
