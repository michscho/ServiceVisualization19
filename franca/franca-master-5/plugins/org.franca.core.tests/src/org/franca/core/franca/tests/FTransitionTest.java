/**
 */
package org.franca.core.franca.tests;

import junit.framework.TestCase;

import junit.textui.TestRunner;

import org.franca.core.franca.FTransition;
import org.franca.core.franca.FrancaFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>FTransition</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class FTransitionTest extends TestCase {

	/**
	 * The fixture for this FTransition test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FTransition fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(FTransitionTest.class);
	}

	/**
	 * Constructs a new FTransition test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FTransitionTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this FTransition test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(FTransition fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this FTransition test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FTransition getFixture() {
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
		setFixture(FrancaFactory.eINSTANCE.createFTransition());
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

} //FTransitionTest
