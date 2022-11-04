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

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>RAM</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emfcloud.coffee.RAM#getClockSpeed <em>Clock Speed</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.coffee.RAM#getSize <em>Size</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.coffee.RAM#getRamType <em>Ram Type</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emfcloud.coffee.CoffeePackage#getRAM()
 * @model
 * @generated
 */
public interface RAM extends Identifiable {
   /**
    * Returns the value of the '<em><b>Clock Speed</b></em>' attribute. <!--
    * begin-user-doc --> <!-- end-user-doc -->
    *
    * @return the value of the '<em>Clock Speed</em>' attribute.
    * @see #setClockSpeed(int)
    * @see org.eclipse.emfcloud.coffee.CoffeePackage#getRAM_ClockSpeed()
    * @model
    * @generated
    */
   int getClockSpeed();

   /**
    * Sets the value of the '{@link org.eclipse.emfcloud.coffee.RAM#getClockSpeed
    * <em>Clock Speed</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
    * -->
    *
    * @param value the new value of the '<em>Clock Speed</em>' attribute.
    * @see #getClockSpeed()
    * @generated
    */
   void setClockSpeed(int value);

   /**
    * Returns the value of the '<em><b>Size</b></em>' attribute. <!--
    * begin-user-doc --> <!-- end-user-doc -->
    *
    * @return the value of the '<em>Size</em>' attribute.
    * @see #setSize(int)
    * @see org.eclipse.emfcloud.coffee.CoffeePackage#getRAM_Size()
    * @model
    * @generated
    */
   int getSize();

   /**
    * Sets the value of the '{@link org.eclipse.emfcloud.coffee.RAM#getSize <em>Size</em>}' attribute.
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * @param value the new value of the '<em>Size</em>' attribute.
    * @see #getSize()
    * @generated
    */
   void setSize(int value);

   /**
    * Returns the value of the '<em><b>Ram Type</b></em>' attribute.
    * The literals are from the enumeration {@link org.eclipse.emfcloud.coffee.RamType}.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the value of the '<em>Ram Type</em>' attribute.
    * @see org.eclipse.emfcloud.coffee.RamType
    * @see #setRamType(RamType)
    * @see org.eclipse.emfcloud.coffee.CoffeePackage#getRAM_RamType()
    * @model
    * @generated
    */
   RamType getRamType();

   /**
    * Sets the value of the '{@link org.eclipse.emfcloud.coffee.RAM#getRamType <em>Ram Type</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @param value the new value of the '<em>Ram Type</em>' attribute.
    * @see org.eclipse.emfcloud.coffee.RamType
    * @see #getRamType()
    * @generated
    */
   void setRamType(RamType value);

} // RAM
