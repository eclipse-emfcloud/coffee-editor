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

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.emfcloud.coffee.workflow.dsl.workflow.Assertion;
import org.eclipse.emfcloud.coffee.workflow.dsl.workflow.ProbabilityConfiguration;
import org.eclipse.emfcloud.coffee.workflow.dsl.workflow.WorkflowConfiguration;
import org.eclipse.emfcloud.coffee.workflow.dsl.workflow.WorkflowPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Configuration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emfcloud.coffee.workflow.dsl.workflow.impl.WorkflowConfigurationImpl#getMachine <em>Machine</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.coffee.workflow.dsl.workflow.impl.WorkflowConfigurationImpl#getModel <em>Model</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.coffee.workflow.dsl.workflow.impl.WorkflowConfigurationImpl#getProbConf <em>Prob Conf</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.coffee.workflow.dsl.workflow.impl.WorkflowConfigurationImpl#getAssertions <em>Assertions</em>}</li>
 * </ul>
 *
 * @generated
 */
public class WorkflowConfigurationImpl extends MinimalEObjectImpl.Container implements WorkflowConfiguration
{
  /**
   * The default value of the '{@link #getMachine() <em>Machine</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMachine()
   * @generated
   * @ordered
   */
  protected static final String MACHINE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getMachine() <em>Machine</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMachine()
   * @generated
   * @ordered
   */
  protected String machine = MACHINE_EDEFAULT;

  /**
   * The default value of the '{@link #getModel() <em>Model</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getModel()
   * @generated
   * @ordered
   */
  protected static final String MODEL_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getModel() <em>Model</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getModel()
   * @generated
   * @ordered
   */
  protected String model = MODEL_EDEFAULT;

  /**
   * The cached value of the '{@link #getProbConf() <em>Prob Conf</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getProbConf()
   * @generated
   * @ordered
   */
  protected ProbabilityConfiguration probConf;

  /**
   * The cached value of the '{@link #getAssertions() <em>Assertions</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAssertions()
   * @generated
   * @ordered
   */
  protected EList<Assertion> assertions;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected WorkflowConfigurationImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return WorkflowPackage.Literals.WORKFLOW_CONFIGURATION;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getMachine()
  {
    return machine;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setMachine(String newMachine)
  {
    String oldMachine = machine;
    machine = newMachine;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, WorkflowPackage.WORKFLOW_CONFIGURATION__MACHINE, oldMachine, machine));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getModel()
  {
    return model;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setModel(String newModel)
  {
    String oldModel = model;
    model = newModel;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, WorkflowPackage.WORKFLOW_CONFIGURATION__MODEL, oldModel, model));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public ProbabilityConfiguration getProbConf()
  {
    return probConf;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetProbConf(ProbabilityConfiguration newProbConf, NotificationChain msgs)
  {
    ProbabilityConfiguration oldProbConf = probConf;
    probConf = newProbConf;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, WorkflowPackage.WORKFLOW_CONFIGURATION__PROB_CONF, oldProbConf, newProbConf);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setProbConf(ProbabilityConfiguration newProbConf)
  {
    if (newProbConf != probConf)
    {
      NotificationChain msgs = null;
      if (probConf != null)
        msgs = ((InternalEObject)probConf).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - WorkflowPackage.WORKFLOW_CONFIGURATION__PROB_CONF, null, msgs);
      if (newProbConf != null)
        msgs = ((InternalEObject)newProbConf).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - WorkflowPackage.WORKFLOW_CONFIGURATION__PROB_CONF, null, msgs);
      msgs = basicSetProbConf(newProbConf, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, WorkflowPackage.WORKFLOW_CONFIGURATION__PROB_CONF, newProbConf, newProbConf));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<Assertion> getAssertions()
  {
    if (assertions == null)
    {
      assertions = new EObjectContainmentEList<Assertion>(Assertion.class, this, WorkflowPackage.WORKFLOW_CONFIGURATION__ASSERTIONS);
    }
    return assertions;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
    switch (featureID)
    {
      case WorkflowPackage.WORKFLOW_CONFIGURATION__PROB_CONF:
        return basicSetProbConf(null, msgs);
      case WorkflowPackage.WORKFLOW_CONFIGURATION__ASSERTIONS:
        return ((InternalEList<?>)getAssertions()).basicRemove(otherEnd, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case WorkflowPackage.WORKFLOW_CONFIGURATION__MACHINE:
        return getMachine();
      case WorkflowPackage.WORKFLOW_CONFIGURATION__MODEL:
        return getModel();
      case WorkflowPackage.WORKFLOW_CONFIGURATION__PROB_CONF:
        return getProbConf();
      case WorkflowPackage.WORKFLOW_CONFIGURATION__ASSERTIONS:
        return getAssertions();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case WorkflowPackage.WORKFLOW_CONFIGURATION__MACHINE:
        setMachine((String)newValue);
        return;
      case WorkflowPackage.WORKFLOW_CONFIGURATION__MODEL:
        setModel((String)newValue);
        return;
      case WorkflowPackage.WORKFLOW_CONFIGURATION__PROB_CONF:
        setProbConf((ProbabilityConfiguration)newValue);
        return;
      case WorkflowPackage.WORKFLOW_CONFIGURATION__ASSERTIONS:
        getAssertions().clear();
        getAssertions().addAll((Collection<? extends Assertion>)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case WorkflowPackage.WORKFLOW_CONFIGURATION__MACHINE:
        setMachine(MACHINE_EDEFAULT);
        return;
      case WorkflowPackage.WORKFLOW_CONFIGURATION__MODEL:
        setModel(MODEL_EDEFAULT);
        return;
      case WorkflowPackage.WORKFLOW_CONFIGURATION__PROB_CONF:
        setProbConf((ProbabilityConfiguration)null);
        return;
      case WorkflowPackage.WORKFLOW_CONFIGURATION__ASSERTIONS:
        getAssertions().clear();
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case WorkflowPackage.WORKFLOW_CONFIGURATION__MACHINE:
        return MACHINE_EDEFAULT == null ? machine != null : !MACHINE_EDEFAULT.equals(machine);
      case WorkflowPackage.WORKFLOW_CONFIGURATION__MODEL:
        return MODEL_EDEFAULT == null ? model != null : !MODEL_EDEFAULT.equals(model);
      case WorkflowPackage.WORKFLOW_CONFIGURATION__PROB_CONF:
        return probConf != null;
      case WorkflowPackage.WORKFLOW_CONFIGURATION__ASSERTIONS:
        return assertions != null && !assertions.isEmpty();
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString()
  {
    if (eIsProxy()) return super.toString();

    StringBuilder result = new StringBuilder(super.toString());
    result.append(" (machine: ");
    result.append(machine);
    result.append(", model: ");
    result.append(model);
    result.append(')');
    return result.toString();
  }

} //WorkflowConfigurationImpl
