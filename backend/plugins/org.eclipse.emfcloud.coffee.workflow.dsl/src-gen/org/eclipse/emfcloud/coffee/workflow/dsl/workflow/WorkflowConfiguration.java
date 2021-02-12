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

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Configuration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emfcloud.coffee.workflow.dsl.workflow.WorkflowConfiguration#getMachine <em>Machine</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.coffee.workflow.dsl.workflow.WorkflowConfiguration#getModel <em>Model</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.coffee.workflow.dsl.workflow.WorkflowConfiguration#getProbConf <em>Prob Conf</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.coffee.workflow.dsl.workflow.WorkflowConfiguration#getAssertions <em>Assertions</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emfcloud.coffee.workflow.dsl.workflow.WorkflowPackage#getWorkflowConfiguration()
 * @model
 * @generated
 */
public interface WorkflowConfiguration extends EObject
{
  /**
   * Returns the value of the '<em><b>Machine</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Machine</em>' attribute.
   * @see #setMachine(String)
   * @see org.eclipse.emfcloud.coffee.workflow.dsl.workflow.WorkflowPackage#getWorkflowConfiguration_Machine()
   * @model
   * @generated
   */
  String getMachine();

  /**
   * Sets the value of the '{@link org.eclipse.emfcloud.coffee.workflow.dsl.workflow.WorkflowConfiguration#getMachine <em>Machine</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Machine</em>' attribute.
   * @see #getMachine()
   * @generated
   */
  void setMachine(String value);

  /**
   * Returns the value of the '<em><b>Model</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Model</em>' attribute.
   * @see #setModel(String)
   * @see org.eclipse.emfcloud.coffee.workflow.dsl.workflow.WorkflowPackage#getWorkflowConfiguration_Model()
   * @model
   * @generated
   */
  String getModel();

  /**
   * Sets the value of the '{@link org.eclipse.emfcloud.coffee.workflow.dsl.workflow.WorkflowConfiguration#getModel <em>Model</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Model</em>' attribute.
   * @see #getModel()
   * @generated
   */
  void setModel(String value);

  /**
   * Returns the value of the '<em><b>Prob Conf</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Prob Conf</em>' containment reference.
   * @see #setProbConf(ProbabilityConfiguration)
   * @see org.eclipse.emfcloud.coffee.workflow.dsl.workflow.WorkflowPackage#getWorkflowConfiguration_ProbConf()
   * @model containment="true"
   * @generated
   */
  ProbabilityConfiguration getProbConf();

  /**
   * Sets the value of the '{@link org.eclipse.emfcloud.coffee.workflow.dsl.workflow.WorkflowConfiguration#getProbConf <em>Prob Conf</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Prob Conf</em>' containment reference.
   * @see #getProbConf()
   * @generated
   */
  void setProbConf(ProbabilityConfiguration value);

  /**
   * Returns the value of the '<em><b>Assertions</b></em>' containment reference list.
   * The list contents are of type {@link org.eclipse.emfcloud.coffee.workflow.dsl.workflow.Assertion}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Assertions</em>' containment reference list.
   * @see org.eclipse.emfcloud.coffee.workflow.dsl.workflow.WorkflowPackage#getWorkflowConfiguration_Assertions()
   * @model containment="true"
   * @generated
   */
  EList<Assertion> getAssertions();

} // WorkflowConfiguration
