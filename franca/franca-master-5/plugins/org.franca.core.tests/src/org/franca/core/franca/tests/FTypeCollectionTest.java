/**
 */
package org.franca.core.franca.tests;

import junit.textui.TestRunner;

import org.franca.core.franca.FTypeCollection;
import org.franca.core.franca.FrancaFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>FType Collection</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class FTypeCollectionTest extends FModelElementTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(FTypeCollectionTest.class);
	}

	/**
	 * Constructs a new FType Collection test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FTypeCollectionTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this FType Collection test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected FTypeCollection getFixture() {
		return (FTypeCollection)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(FrancaFactory.eINSTANCE.createFTypeCollection());
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

} //FTypeCollectionTest
