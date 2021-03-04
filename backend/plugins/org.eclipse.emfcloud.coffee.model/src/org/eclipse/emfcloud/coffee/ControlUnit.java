/**
 * Copyright (c) 2021 EclipseSource and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 * 
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License v. 2.0 are satisfied: GNU General Public License, version 2
 * with the GNU Classpath Exception which is available at
 * https://www.gnu.org/software/classpath/license.html.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */
package org.eclipse.emfcloud.coffee;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Control Unit</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emfcloud.coffee.ControlUnit#getProcessor <em>Processor</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.coffee.ControlUnit#getDimension <em>Dimension</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.coffee.ControlUnit#getRam <em>Ram</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.coffee.ControlUnit#getDisplay <em>Display</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.coffee.ControlUnit#getUserDescription <em>User Description</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emfcloud.coffee.CoffeePackage#getControlUnit()
 * @model
 * @generated
 */
public interface ControlUnit extends Component {
	/**
	 * Returns the value of the '<em><b>Processor</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Processor</em>' containment reference.
	 * @see #setProcessor(Processor)
	 * @see org.eclipse.emfcloud.coffee.CoffeePackage#getControlUnit_Processor()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Processor getProcessor();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.coffee.ControlUnit#getProcessor <em>Processor</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Processor</em>' containment reference.
	 * @see #getProcessor()
	 * @generated
	 */
	void setProcessor(Processor value);

	/**
	 * Returns the value of the '<em><b>Dimension</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dimension</em>' containment reference.
	 * @see #setDimension(Dimension)
	 * @see org.eclipse.emfcloud.coffee.CoffeePackage#getControlUnit_Dimension()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Dimension getDimension();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.coffee.ControlUnit#getDimension <em>Dimension</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dimension</em>' containment reference.
	 * @see #getDimension()
	 * @generated
	 */
	void setDimension(Dimension value);

	/**
	 * Returns the value of the '<em><b>Ram</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emfcloud.coffee.RAM}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ram</em>' containment reference list.
	 * @see org.eclipse.emfcloud.coffee.CoffeePackage#getControlUnit_Ram()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<RAM> getRam();

	/**
	 * Returns the value of the '<em><b>Display</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Display</em>' containment reference.
	 * @see #setDisplay(Display)
	 * @see org.eclipse.emfcloud.coffee.CoffeePackage#getControlUnit_Display()
	 * @model containment="true"
	 * @generated
	 */
	Display getDisplay();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.coffee.ControlUnit#getDisplay <em>Display</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Display</em>' containment reference.
	 * @see #getDisplay()
	 * @generated
	 */
	void setDisplay(Display value);

	/**
	 * Returns the value of the '<em><b>User Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>User Description</em>' attribute.
	 * @see #setUserDescription(String)
	 * @see org.eclipse.emfcloud.coffee.CoffeePackage#getControlUnit_UserDescription()
	 * @model
	 * @generated
	 */
	String getUserDescription();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.coffee.ControlUnit#getUserDescription <em>User Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>User Description</em>' attribute.
	 * @see #getUserDescription()
	 * @generated
	 */
	void setUserDescription(String value);

} // ControlUnit
