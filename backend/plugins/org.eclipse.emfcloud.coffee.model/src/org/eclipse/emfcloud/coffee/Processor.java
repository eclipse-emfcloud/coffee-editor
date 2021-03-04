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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Processor</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emfcloud.coffee.Processor#getVendor <em>Vendor</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.coffee.Processor#getClockSpeed <em>Clock Speed</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.coffee.Processor#getNumberOfCores <em>Number Of Cores</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.coffee.Processor#getSocketconnectorType <em>Socketconnector Type</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.coffee.Processor#getThermalDesignPower <em>Thermal Design Power</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.coffee.Processor#getManufactoringProcess <em>Manufactoring Process</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emfcloud.coffee.CoffeePackage#getProcessor()
 * @model
 * @generated
 */
public interface Processor extends EObject {
	/**
	 * Returns the value of the '<em><b>Vendor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Vendor</em>' attribute.
	 * @see #setVendor(String)
	 * @see org.eclipse.emfcloud.coffee.CoffeePackage#getProcessor_Vendor()
	 * @model
	 * @generated
	 */
	String getVendor();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.coffee.Processor#getVendor <em>Vendor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Vendor</em>' attribute.
	 * @see #getVendor()
	 * @generated
	 */
	void setVendor(String value);

	/**
	 * Returns the value of the '<em><b>Clock Speed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Clock Speed</em>' attribute.
	 * @see #setClockSpeed(int)
	 * @see org.eclipse.emfcloud.coffee.CoffeePackage#getProcessor_ClockSpeed()
	 * @model
	 * @generated
	 */
	int getClockSpeed();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.coffee.Processor#getClockSpeed <em>Clock Speed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Clock Speed</em>' attribute.
	 * @see #getClockSpeed()
	 * @generated
	 */
	void setClockSpeed(int value);

	/**
	 * Returns the value of the '<em><b>Number Of Cores</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Number Of Cores</em>' attribute.
	 * @see #setNumberOfCores(int)
	 * @see org.eclipse.emfcloud.coffee.CoffeePackage#getProcessor_NumberOfCores()
	 * @model
	 * @generated
	 */
	int getNumberOfCores();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.coffee.Processor#getNumberOfCores <em>Number Of Cores</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Number Of Cores</em>' attribute.
	 * @see #getNumberOfCores()
	 * @generated
	 */
	void setNumberOfCores(int value);

	/**
	 * Returns the value of the '<em><b>Socketconnector Type</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.emfcloud.coffee.SocketConnectorType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Socketconnector Type</em>' attribute.
	 * @see org.eclipse.emfcloud.coffee.SocketConnectorType
	 * @see #setSocketconnectorType(SocketConnectorType)
	 * @see org.eclipse.emfcloud.coffee.CoffeePackage#getProcessor_SocketconnectorType()
	 * @model
	 * @generated
	 */
	SocketConnectorType getSocketconnectorType();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.coffee.Processor#getSocketconnectorType <em>Socketconnector Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Socketconnector Type</em>' attribute.
	 * @see org.eclipse.emfcloud.coffee.SocketConnectorType
	 * @see #getSocketconnectorType()
	 * @generated
	 */
	void setSocketconnectorType(SocketConnectorType value);

	/**
	 * Returns the value of the '<em><b>Thermal Design Power</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Thermal Design Power</em>' attribute.
	 * @see #setThermalDesignPower(int)
	 * @see org.eclipse.emfcloud.coffee.CoffeePackage#getProcessor_ThermalDesignPower()
	 * @model
	 * @generated
	 */
	int getThermalDesignPower();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.coffee.Processor#getThermalDesignPower <em>Thermal Design Power</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Thermal Design Power</em>' attribute.
	 * @see #getThermalDesignPower()
	 * @generated
	 */
	void setThermalDesignPower(int value);

	/**
	 * Returns the value of the '<em><b>Manufactoring Process</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.emfcloud.coffee.ManufactoringProcess}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Manufactoring Process</em>' attribute.
	 * @see org.eclipse.emfcloud.coffee.ManufactoringProcess
	 * @see #setManufactoringProcess(ManufactoringProcess)
	 * @see org.eclipse.emfcloud.coffee.CoffeePackage#getProcessor_ManufactoringProcess()
	 * @model
	 * @generated
	 */
	ManufactoringProcess getManufactoringProcess();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.coffee.Processor#getManufactoringProcess <em>Manufactoring Process</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Manufactoring Process</em>' attribute.
	 * @see org.eclipse.emfcloud.coffee.ManufactoringProcess
	 * @see #getManufactoringProcess()
	 * @generated
	 */
	void setManufactoringProcess(ManufactoringProcess value);

} // Processor
