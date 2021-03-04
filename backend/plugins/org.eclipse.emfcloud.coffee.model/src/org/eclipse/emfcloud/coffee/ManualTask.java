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
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Manual Task</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emfcloud.coffee.ManualTask#getActor <em>Actor</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emfcloud.coffee.CoffeePackage#getManualTask()
 * @model
 * @generated
 */
public interface ManualTask extends Task {
	/**
	 * Returns the value of the '<em><b>Actor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Actor</em>' attribute.
	 * @see #setActor(String)
	 * @see org.eclipse.emfcloud.coffee.CoffeePackage#getManualTask_Actor()
	 * @model
	 * @generated
	 */
	String getActor();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.coffee.ManualTask#getActor <em>Actor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Actor</em>' attribute.
	 * @see #getActor()
	 * @generated
	 */
	void setActor(String value);

} // ManualTask
