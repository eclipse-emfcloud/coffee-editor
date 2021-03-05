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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.emfcloud.coffee.workflow.dsl.workflow.WorkflowFactory
 * @model kind="package"
 * @generated
 */
public interface WorkflowPackage extends EPackage
{
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "workflow";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "http://www.eclipsesource.com/workflow/dsl/Workflow";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "workflow";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  WorkflowPackage eINSTANCE = org.eclipse.emfcloud.coffee.workflow.dsl.workflow.impl.WorkflowPackageImpl.init();

  /**
   * The meta object id for the '{@link org.eclipse.emfcloud.coffee.workflow.dsl.workflow.impl.WorkflowConfigurationImpl <em>Configuration</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.emfcloud.coffee.workflow.dsl.workflow.impl.WorkflowConfigurationImpl
   * @see org.eclipse.emfcloud.coffee.workflow.dsl.workflow.impl.WorkflowPackageImpl#getWorkflowConfiguration()
   * @generated
   */
  int WORKFLOW_CONFIGURATION = 0;

  /**
   * The feature id for the '<em><b>Machine</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WORKFLOW_CONFIGURATION__MACHINE = 0;

  /**
   * The feature id for the '<em><b>Model</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WORKFLOW_CONFIGURATION__MODEL = 1;

  /**
   * The feature id for the '<em><b>Prob Conf</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WORKFLOW_CONFIGURATION__PROB_CONF = 2;

  /**
   * The feature id for the '<em><b>Assertions</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WORKFLOW_CONFIGURATION__ASSERTIONS = 3;

  /**
   * The number of structural features of the '<em>Configuration</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WORKFLOW_CONFIGURATION_FEATURE_COUNT = 4;

  /**
   * The meta object id for the '{@link org.eclipse.emfcloud.coffee.workflow.dsl.workflow.impl.AssertionImpl <em>Assertion</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.emfcloud.coffee.workflow.dsl.workflow.impl.AssertionImpl
   * @see org.eclipse.emfcloud.coffee.workflow.dsl.workflow.impl.WorkflowPackageImpl#getAssertion()
   * @generated
   */
  int ASSERTION = 1;

  /**
   * The feature id for the '<em><b>Before</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ASSERTION__BEFORE = 0;

  /**
   * The feature id for the '<em><b>After</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ASSERTION__AFTER = 1;

  /**
   * The number of structural features of the '<em>Assertion</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ASSERTION_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link org.eclipse.emfcloud.coffee.workflow.dsl.workflow.impl.ProbabilityConfigurationImpl <em>Probability Configuration</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.emfcloud.coffee.workflow.dsl.workflow.impl.ProbabilityConfigurationImpl
   * @see org.eclipse.emfcloud.coffee.workflow.dsl.workflow.impl.WorkflowPackageImpl#getProbabilityConfiguration()
   * @generated
   */
  int PROBABILITY_CONFIGURATION = 2;

  /**
   * The feature id for the '<em><b>Low</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROBABILITY_CONFIGURATION__LOW = 0;

  /**
   * The feature id for the '<em><b>Medium</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROBABILITY_CONFIGURATION__MEDIUM = 1;

  /**
   * The feature id for the '<em><b>High</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROBABILITY_CONFIGURATION__HIGH = 2;

  /**
   * The number of structural features of the '<em>Probability Configuration</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROBABILITY_CONFIGURATION_FEATURE_COUNT = 3;


  /**
   * Returns the meta object for class '{@link org.eclipse.emfcloud.coffee.workflow.dsl.workflow.WorkflowConfiguration <em>Configuration</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Configuration</em>'.
   * @see org.eclipse.emfcloud.coffee.workflow.dsl.workflow.WorkflowConfiguration
   * @generated
   */
  EClass getWorkflowConfiguration();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.coffee.workflow.dsl.workflow.WorkflowConfiguration#getMachine <em>Machine</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Machine</em>'.
   * @see org.eclipse.emfcloud.coffee.workflow.dsl.workflow.WorkflowConfiguration#getMachine()
   * @see #getWorkflowConfiguration()
   * @generated
   */
  EAttribute getWorkflowConfiguration_Machine();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.coffee.workflow.dsl.workflow.WorkflowConfiguration#getModel <em>Model</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Model</em>'.
   * @see org.eclipse.emfcloud.coffee.workflow.dsl.workflow.WorkflowConfiguration#getModel()
   * @see #getWorkflowConfiguration()
   * @generated
   */
  EAttribute getWorkflowConfiguration_Model();

  /**
   * Returns the meta object for the containment reference '{@link org.eclipse.emfcloud.coffee.workflow.dsl.workflow.WorkflowConfiguration#getProbConf <em>Prob Conf</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Prob Conf</em>'.
   * @see org.eclipse.emfcloud.coffee.workflow.dsl.workflow.WorkflowConfiguration#getProbConf()
   * @see #getWorkflowConfiguration()
   * @generated
   */
  EReference getWorkflowConfiguration_ProbConf();

  /**
   * Returns the meta object for the containment reference list '{@link org.eclipse.emfcloud.coffee.workflow.dsl.workflow.WorkflowConfiguration#getAssertions <em>Assertions</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Assertions</em>'.
   * @see org.eclipse.emfcloud.coffee.workflow.dsl.workflow.WorkflowConfiguration#getAssertions()
   * @see #getWorkflowConfiguration()
   * @generated
   */
  EReference getWorkflowConfiguration_Assertions();

  /**
   * Returns the meta object for class '{@link org.eclipse.emfcloud.coffee.workflow.dsl.workflow.Assertion <em>Assertion</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Assertion</em>'.
   * @see org.eclipse.emfcloud.coffee.workflow.dsl.workflow.Assertion
   * @generated
   */
  EClass getAssertion();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.coffee.workflow.dsl.workflow.Assertion#getBefore <em>Before</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Before</em>'.
   * @see org.eclipse.emfcloud.coffee.workflow.dsl.workflow.Assertion#getBefore()
   * @see #getAssertion()
   * @generated
   */
  EAttribute getAssertion_Before();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.coffee.workflow.dsl.workflow.Assertion#getAfter <em>After</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>After</em>'.
   * @see org.eclipse.emfcloud.coffee.workflow.dsl.workflow.Assertion#getAfter()
   * @see #getAssertion()
   * @generated
   */
  EAttribute getAssertion_After();

  /**
   * Returns the meta object for class '{@link org.eclipse.emfcloud.coffee.workflow.dsl.workflow.ProbabilityConfiguration <em>Probability Configuration</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Probability Configuration</em>'.
   * @see org.eclipse.emfcloud.coffee.workflow.dsl.workflow.ProbabilityConfiguration
   * @generated
   */
  EClass getProbabilityConfiguration();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.coffee.workflow.dsl.workflow.ProbabilityConfiguration#getLow <em>Low</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Low</em>'.
   * @see org.eclipse.emfcloud.coffee.workflow.dsl.workflow.ProbabilityConfiguration#getLow()
   * @see #getProbabilityConfiguration()
   * @generated
   */
  EAttribute getProbabilityConfiguration_Low();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.coffee.workflow.dsl.workflow.ProbabilityConfiguration#getMedium <em>Medium</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Medium</em>'.
   * @see org.eclipse.emfcloud.coffee.workflow.dsl.workflow.ProbabilityConfiguration#getMedium()
   * @see #getProbabilityConfiguration()
   * @generated
   */
  EAttribute getProbabilityConfiguration_Medium();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.emfcloud.coffee.workflow.dsl.workflow.ProbabilityConfiguration#getHigh <em>High</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>High</em>'.
   * @see org.eclipse.emfcloud.coffee.workflow.dsl.workflow.ProbabilityConfiguration#getHigh()
   * @see #getProbabilityConfiguration()
   * @generated
   */
  EAttribute getProbabilityConfiguration_High();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  WorkflowFactory getWorkflowFactory();

  /**
   * <!-- begin-user-doc -->
   * Defines literals for the meta objects that represent
   * <ul>
   *   <li>each class,</li>
   *   <li>each feature of each class,</li>
   *   <li>each enum,</li>
   *   <li>and each data type</li>
   * </ul>
   * <!-- end-user-doc -->
   * @generated
   */
  interface Literals
  {
    /**
     * The meta object literal for the '{@link org.eclipse.emfcloud.coffee.workflow.dsl.workflow.impl.WorkflowConfigurationImpl <em>Configuration</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.emfcloud.coffee.workflow.dsl.workflow.impl.WorkflowConfigurationImpl
     * @see org.eclipse.emfcloud.coffee.workflow.dsl.workflow.impl.WorkflowPackageImpl#getWorkflowConfiguration()
     * @generated
     */
    EClass WORKFLOW_CONFIGURATION = eINSTANCE.getWorkflowConfiguration();

    /**
     * The meta object literal for the '<em><b>Machine</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute WORKFLOW_CONFIGURATION__MACHINE = eINSTANCE.getWorkflowConfiguration_Machine();

    /**
     * The meta object literal for the '<em><b>Model</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute WORKFLOW_CONFIGURATION__MODEL = eINSTANCE.getWorkflowConfiguration_Model();

    /**
     * The meta object literal for the '<em><b>Prob Conf</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference WORKFLOW_CONFIGURATION__PROB_CONF = eINSTANCE.getWorkflowConfiguration_ProbConf();

    /**
     * The meta object literal for the '<em><b>Assertions</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference WORKFLOW_CONFIGURATION__ASSERTIONS = eINSTANCE.getWorkflowConfiguration_Assertions();

    /**
     * The meta object literal for the '{@link org.eclipse.emfcloud.coffee.workflow.dsl.workflow.impl.AssertionImpl <em>Assertion</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.emfcloud.coffee.workflow.dsl.workflow.impl.AssertionImpl
     * @see org.eclipse.emfcloud.coffee.workflow.dsl.workflow.impl.WorkflowPackageImpl#getAssertion()
     * @generated
     */
    EClass ASSERTION = eINSTANCE.getAssertion();

    /**
     * The meta object literal for the '<em><b>Before</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ASSERTION__BEFORE = eINSTANCE.getAssertion_Before();

    /**
     * The meta object literal for the '<em><b>After</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ASSERTION__AFTER = eINSTANCE.getAssertion_After();

    /**
     * The meta object literal for the '{@link org.eclipse.emfcloud.coffee.workflow.dsl.workflow.impl.ProbabilityConfigurationImpl <em>Probability Configuration</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.emfcloud.coffee.workflow.dsl.workflow.impl.ProbabilityConfigurationImpl
     * @see org.eclipse.emfcloud.coffee.workflow.dsl.workflow.impl.WorkflowPackageImpl#getProbabilityConfiguration()
     * @generated
     */
    EClass PROBABILITY_CONFIGURATION = eINSTANCE.getProbabilityConfiguration();

    /**
     * The meta object literal for the '<em><b>Low</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PROBABILITY_CONFIGURATION__LOW = eINSTANCE.getProbabilityConfiguration_Low();

    /**
     * The meta object literal for the '<em><b>Medium</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PROBABILITY_CONFIGURATION__MEDIUM = eINSTANCE.getProbabilityConfiguration_Medium();

    /**
     * The meta object literal for the '<em><b>High</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PROBABILITY_CONFIGURATION__HIGH = eINSTANCE.getProbabilityConfiguration_High();

  }

} //WorkflowPackage
