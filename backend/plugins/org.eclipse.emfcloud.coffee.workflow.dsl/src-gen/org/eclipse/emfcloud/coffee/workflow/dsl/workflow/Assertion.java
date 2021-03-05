/**
 * Copyright (c) 2019-2021 EclipseSource and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 */
package org.eclipse.emfcloud.coffee.workflow.dsl.workflow;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Assertion</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emfcloud.coffee.workflow.dsl.workflow.Assertion#getBefore <em>Before</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.coffee.workflow.dsl.workflow.Assertion#getAfter <em>After</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emfcloud.coffee.workflow.dsl.workflow.WorkflowPackage#getAssertion()
 * @model
 * @generated
 */
public interface Assertion extends EObject
{
  /**
   * Returns the value of the '<em><b>Before</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Before</em>' attribute.
   * @see #setBefore(String)
   * @see org.eclipse.emfcloud.coffee.workflow.dsl.workflow.WorkflowPackage#getAssertion_Before()
   * @model
   * @generated
   */
  String getBefore();

  /**
   * Sets the value of the '{@link org.eclipse.emfcloud.coffee.workflow.dsl.workflow.Assertion#getBefore <em>Before</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Before</em>' attribute.
   * @see #getBefore()
   * @generated
   */
  void setBefore(String value);

  /**
   * Returns the value of the '<em><b>After</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>After</em>' attribute.
   * @see #setAfter(String)
   * @see org.eclipse.emfcloud.coffee.workflow.dsl.workflow.WorkflowPackage#getAssertion_After()
   * @model
   * @generated
   */
  String getAfter();

  /**
   * Sets the value of the '{@link org.eclipse.emfcloud.coffee.workflow.dsl.workflow.Assertion#getAfter <em>After</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>After</em>' attribute.
   * @see #getAfter()
   * @generated
   */
  void setAfter(String value);

} // Assertion
