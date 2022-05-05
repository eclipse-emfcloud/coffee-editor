/**
 * Copyright (c) 2021 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 */
package org.eclipse.emfcloud.coffee;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Automatic Task</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emfcloud.coffee.AutomaticTask#getComponent
 * <em>Component</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emfcloud.coffee.CoffeePackage#getAutomaticTask()
 * @model
 * @generated
 */
public interface AutomaticTask extends Task {
   /**
    * Returns the value of the '<em><b>Component</b></em>' reference. <!--
    * begin-user-doc --> <!-- end-user-doc -->
    *
    * @return the value of the '<em>Component</em>' reference.
    * @see #setComponent(Component)
    * @see org.eclipse.emfcloud.coffee.CoffeePackage#getAutomaticTask_Component()
    * @model
    * @generated
    */
   Component getComponent();

   /**
    * Sets the value of the
    * '{@link org.eclipse.emfcloud.coffee.AutomaticTask#getComponent
    * <em>Component</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
    *
    * @param value the new value of the '<em>Component</em>' reference.
    * @see #getComponent()
    * @generated
    */
   void setComponent(Component value);

} // AutomaticTask
