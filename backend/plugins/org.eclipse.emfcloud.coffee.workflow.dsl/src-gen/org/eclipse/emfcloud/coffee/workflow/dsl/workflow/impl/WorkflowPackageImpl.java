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
package org.eclipse.emfcloud.coffee.workflow.dsl.workflow.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.emfcloud.coffee.workflow.dsl.workflow.Assertion;
import org.eclipse.emfcloud.coffee.workflow.dsl.workflow.ProbabilityConfiguration;
import org.eclipse.emfcloud.coffee.workflow.dsl.workflow.WorkflowConfiguration;
import org.eclipse.emfcloud.coffee.workflow.dsl.workflow.WorkflowFactory;
import org.eclipse.emfcloud.coffee.workflow.dsl.workflow.WorkflowPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class WorkflowPackageImpl extends EPackageImpl implements WorkflowPackage
{
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass workflowConfigurationEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass assertionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass probabilityConfigurationEClass = null;

  /**
   * Creates an instance of the model <b>Package</b>, registered with
   * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
   * package URI value.
   * <p>Note: the correct way to create the package is via the static
   * factory method {@link #init init()}, which also performs
   * initialization of the package, or returns the registered package,
   * if one already exists.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.emf.ecore.EPackage.Registry
   * @see org.eclipse.emfcloud.coffee.workflow.dsl.workflow.WorkflowPackage#eNS_URI
   * @see #init()
   * @generated
   */
  private WorkflowPackageImpl()
  {
    super(eNS_URI, WorkflowFactory.eINSTANCE);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static boolean isInited = false;

  /**
   * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
   *
   * <p>This method is used to initialize {@link WorkflowPackage#eINSTANCE} when that field is accessed.
   * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #eNS_URI
   * @see #createPackageContents()
   * @see #initializePackageContents()
   * @generated
   */
  public static WorkflowPackage init()
  {
    if (isInited) return (WorkflowPackage)EPackage.Registry.INSTANCE.getEPackage(WorkflowPackage.eNS_URI);

    // Obtain or create and register package
    Object registeredWorkflowPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
    WorkflowPackageImpl theWorkflowPackage = registeredWorkflowPackage instanceof WorkflowPackageImpl ? (WorkflowPackageImpl)registeredWorkflowPackage : new WorkflowPackageImpl();

    isInited = true;

    // Create package meta-data objects
    theWorkflowPackage.createPackageContents();

    // Initialize created meta-data
    theWorkflowPackage.initializePackageContents();

    // Mark meta-data to indicate it can't be changed
    theWorkflowPackage.freeze();

    // Update the registry and return the package
    EPackage.Registry.INSTANCE.put(WorkflowPackage.eNS_URI, theWorkflowPackage);
    return theWorkflowPackage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getWorkflowConfiguration()
  {
    return workflowConfigurationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getWorkflowConfiguration_Machine()
  {
    return (EAttribute)workflowConfigurationEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getWorkflowConfiguration_Model()
  {
    return (EAttribute)workflowConfigurationEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getWorkflowConfiguration_ProbConf()
  {
    return (EReference)workflowConfigurationEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getWorkflowConfiguration_Assertions()
  {
    return (EReference)workflowConfigurationEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getAssertion()
  {
    return assertionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getAssertion_Before()
  {
    return (EAttribute)assertionEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getAssertion_After()
  {
    return (EAttribute)assertionEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getProbabilityConfiguration()
  {
    return probabilityConfigurationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getProbabilityConfiguration_Low()
  {
    return (EAttribute)probabilityConfigurationEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getProbabilityConfiguration_Medium()
  {
    return (EAttribute)probabilityConfigurationEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getProbabilityConfiguration_High()
  {
    return (EAttribute)probabilityConfigurationEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public WorkflowFactory getWorkflowFactory()
  {
    return (WorkflowFactory)getEFactoryInstance();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isCreated = false;

  /**
   * Creates the meta-model objects for the package.  This method is
   * guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void createPackageContents()
  {
    if (isCreated) return;
    isCreated = true;

    // Create classes and their features
    workflowConfigurationEClass = createEClass(WORKFLOW_CONFIGURATION);
    createEAttribute(workflowConfigurationEClass, WORKFLOW_CONFIGURATION__MACHINE);
    createEAttribute(workflowConfigurationEClass, WORKFLOW_CONFIGURATION__MODEL);
    createEReference(workflowConfigurationEClass, WORKFLOW_CONFIGURATION__PROB_CONF);
    createEReference(workflowConfigurationEClass, WORKFLOW_CONFIGURATION__ASSERTIONS);

    assertionEClass = createEClass(ASSERTION);
    createEAttribute(assertionEClass, ASSERTION__BEFORE);
    createEAttribute(assertionEClass, ASSERTION__AFTER);

    probabilityConfigurationEClass = createEClass(PROBABILITY_CONFIGURATION);
    createEAttribute(probabilityConfigurationEClass, PROBABILITY_CONFIGURATION__LOW);
    createEAttribute(probabilityConfigurationEClass, PROBABILITY_CONFIGURATION__MEDIUM);
    createEAttribute(probabilityConfigurationEClass, PROBABILITY_CONFIGURATION__HIGH);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isInitialized = false;

  /**
   * Complete the initialization of the package and its meta-model.  This
   * method is guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void initializePackageContents()
  {
    if (isInitialized) return;
    isInitialized = true;

    // Initialize package
    setName(eNAME);
    setNsPrefix(eNS_PREFIX);
    setNsURI(eNS_URI);

    // Create type parameters

    // Set bounds for type parameters

    // Add supertypes to classes

    // Initialize classes and features; add operations and parameters
    initEClass(workflowConfigurationEClass, WorkflowConfiguration.class, "WorkflowConfiguration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getWorkflowConfiguration_Machine(), ecorePackage.getEString(), "machine", null, 0, 1, WorkflowConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getWorkflowConfiguration_Model(), ecorePackage.getEString(), "model", null, 0, 1, WorkflowConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getWorkflowConfiguration_ProbConf(), this.getProbabilityConfiguration(), null, "probConf", null, 0, 1, WorkflowConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getWorkflowConfiguration_Assertions(), this.getAssertion(), null, "assertions", null, 0, -1, WorkflowConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(assertionEClass, Assertion.class, "Assertion", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getAssertion_Before(), ecorePackage.getEString(), "before", null, 0, 1, Assertion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getAssertion_After(), ecorePackage.getEString(), "after", null, 0, 1, Assertion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(probabilityConfigurationEClass, ProbabilityConfiguration.class, "ProbabilityConfiguration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getProbabilityConfiguration_Low(), ecorePackage.getEFloat(), "low", null, 0, 1, ProbabilityConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getProbabilityConfiguration_Medium(), ecorePackage.getEFloat(), "medium", null, 0, 1, ProbabilityConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getProbabilityConfiguration_High(), ecorePackage.getEFloat(), "high", null, 0, 1, ProbabilityConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    // Create resource
    createResource(eNS_URI);
  }

} //WorkflowPackageImpl
