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
 * A representation of the model object '<em><b>Menu Selection Task</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * An interaction with the operator, requesting a selection from a menu.
 * The selection optionally times out, in which case the time-out flow is
 * triggered. Otherwise, the selection flow is triggered.
 * 
 * @see <a href="https://github.com/eclipsesource/coffee-editor/issues/315">Issue 315</a>
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emfcloud.coffee.MenuSelectionTask#getPrompt <em>Prompt</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.coffee.MenuSelectionTask#getMenu <em>Menu</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.coffee.MenuSelectionTask#getTimeout <em>Timeout</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.coffee.MenuSelectionTask#getSelectionFlow <em>Selection Flow</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.coffee.MenuSelectionTask#getTimeoutFlow <em>Timeout Flow</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emfcloud.coffee.CoffeePackage#getMenuSelectionTask()
 * @model
 * @generated
 */
public interface MenuSelectionTask extends ManualTask {
	/**
	 * Returns the value of the '<em><b>Prompt</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Prompt</em>' attribute.
	 * @see #setPrompt(String)
	 * @see org.eclipse.emfcloud.coffee.CoffeePackage#getMenuSelectionTask_Prompt()
	 * @model required="true"
	 * @generated
	 */
	String getPrompt();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.coffee.MenuSelectionTask#getPrompt <em>Prompt</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Prompt</em>' attribute.
	 * @see #getPrompt()
	 * @generated
	 */
	void setPrompt(String value);

	/**
	 * Returns the value of the '<em><b>Menu</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Menu</em>' attribute list.
	 * @see org.eclipse.emfcloud.coffee.CoffeePackage#getMenuSelectionTask_Menu()
	 * @model lower="2"
	 * @generated
	 */
	EList<String> getMenu();

	/**
	 * Returns the value of the '<em><b>Timeout</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Optional time-out, in seconds. This supersedes the inherited {@link Task#getDuration() duration}
	 * attribute, which is unused as manual tasks are generally open-ended.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Timeout</em>' attribute.
	 * @see #isSetTimeout()
	 * @see #unsetTimeout()
	 * @see #setTimeout(int)
	 * @see org.eclipse.emfcloud.coffee.CoffeePackage#getMenuSelectionTask_Timeout()
	 * @model unsettable="true"
	 * @generated
	 */
	int getTimeout();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.coffee.MenuSelectionTask#getTimeout <em>Timeout</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Timeout</em>' attribute.
	 * @see #isSetTimeout()
	 * @see #unsetTimeout()
	 * @see #getTimeout()
	 * @generated
	 */
	void setTimeout(int value);

	/**
	 * Unsets the value of the '{@link org.eclipse.emfcloud.coffee.MenuSelectionTask#getTimeout <em>Timeout</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetTimeout()
	 * @see #getTimeout()
	 * @see #setTimeout(int)
	 * @generated
	 */
	void unsetTimeout();

	/**
	 * Returns whether the value of the '{@link org.eclipse.emfcloud.coffee.MenuSelectionTask#getTimeout <em>Timeout</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Timeout</em>' attribute is set.
	 * @see #unsetTimeout()
	 * @see #getTimeout()
	 * @see #setTimeout(int)
	 * @generated
	 */
	boolean isSetTimeout();

	/**
	 * Returns the value of the '<em><b>Selection Flow</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Selection Flow</em>' reference.
	 * @see #setSelectionFlow(Flow)
	 * @see org.eclipse.emfcloud.coffee.CoffeePackage#getMenuSelectionTask_SelectionFlow()
	 * @model required="true"
	 * @generated
	 */
	Flow getSelectionFlow();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.coffee.MenuSelectionTask#getSelectionFlow <em>Selection Flow</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Selection Flow</em>' reference.
	 * @see #getSelectionFlow()
	 * @generated
	 */
	void setSelectionFlow(Flow value);

	/**
	 * Returns the value of the '<em><b>Timeout Flow</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Timeout Flow</em>' reference.
	 * @see #setTimeoutFlow(Flow)
	 * @see org.eclipse.emfcloud.coffee.CoffeePackage#getMenuSelectionTask_TimeoutFlow()
	 * @model
	 * @generated
	 */
	Flow getTimeoutFlow();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.coffee.MenuSelectionTask#getTimeoutFlow <em>Timeout Flow</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Timeout Flow</em>' reference.
	 * @see #getTimeoutFlow()
	 * @generated
	 */
	void setTimeoutFlow(Flow value);

} // MenuSelectionTask
