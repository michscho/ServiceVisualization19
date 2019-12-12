/**
 */
package org.franca.core.franca.tests;

import junit.textui.TestRunner;

import org.franca.core.franca.FIfStatement;
import org.franca.core.franca.FrancaFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>FIf Statement</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class FIfStatementTest extends FStatementTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(FIfStatementTest.class);
	}

	/**
	 * Constructs a new FIf Statement test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FIfStatementTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this FIf Statement test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected FIfStatement getFixture() {
		return (FIfStatement)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(FrancaFactory.eINSTANCE.createFIfStatement());
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

} //FIfStatementTest
