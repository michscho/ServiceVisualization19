/**
 */
package org.franca.core.franca.tests;

import junit.textui.TestRunner;

import org.franca.core.franca.FEnumerationType;
import org.franca.core.franca.FrancaFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>FEnumeration Type</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class FEnumerationTypeTest extends FTypeTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(FEnumerationTypeTest.class);
	}

	/**
	 * Constructs a new FEnumeration Type test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FEnumerationTypeTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this FEnumeration Type test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected FEnumerationType getFixture() {
		return (FEnumerationType)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(FrancaFactory.eINSTANCE.createFEnumerationType());
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

} //FEnumerationTypeTest
