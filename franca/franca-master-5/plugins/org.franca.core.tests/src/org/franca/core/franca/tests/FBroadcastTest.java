/**
 */
package org.franca.core.franca.tests;

import junit.textui.TestRunner;

import org.franca.core.franca.FBroadcast;
import org.franca.core.franca.FrancaFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>FBroadcast</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class FBroadcastTest extends FModelElementTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(FBroadcastTest.class);
	}

	/**
	 * Constructs a new FBroadcast test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FBroadcastTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this FBroadcast test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected FBroadcast getFixture() {
		return (FBroadcast)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(FrancaFactory.eINSTANCE.createFBroadcast());
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

} //FBroadcastTest
