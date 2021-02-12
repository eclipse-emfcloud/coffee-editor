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

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emfcloud.coffee.workflow.dsl.workflow.ProbabilityConfiguration;
import org.eclipse.emfcloud.coffee.workflow.dsl.workflow.WorkflowPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Probability Configuration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emfcloud.coffee.workflow.dsl.workflow.impl.ProbabilityConfigurationImpl#getLow <em>Low</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.coffee.workflow.dsl.workflow.impl.ProbabilityConfigurationImpl#getMedium <em>Medium</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.coffee.workflow.dsl.workflow.impl.ProbabilityConfigurationImpl#getHigh <em>High</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ProbabilityConfigurationImpl extends MinimalEObjectImpl.Container implements ProbabilityConfiguration
{
  /**
   * The default value of the '{@link #getLow() <em>Low</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLow()
   * @generated
   * @ordered
   */
  protected static final float LOW_EDEFAULT = 0.0F;

  /**
   * The cached value of the '{@link #getLow() <em>Low</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLow()
   * @generated
   * @ordered
   */
  protected float low = LOW_EDEFAULT;

  /**
   * The default value of the '{@link #getMedium() <em>Medium</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMedium()
   * @generated
   * @ordered
   */
  protected static final float MEDIUM_EDEFAULT = 0.0F;

  /**
   * The cached value of the '{@link #getMedium() <em>Medium</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMedium()
   * @generated
   * @ordered
   */
  protected float medium = MEDIUM_EDEFAULT;

  /**
   * The default value of the '{@link #getHigh() <em>High</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getHigh()
   * @generated
   * @ordered
   */
  protected static final float HIGH_EDEFAULT = 0.0F;

  /**
   * The cached value of the '{@link #getHigh() <em>High</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getHigh()
   * @generated
   * @ordered
   */
  protected float high = HIGH_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ProbabilityConfigurationImpl()
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
    return WorkflowPackage.Literals.PROBABILITY_CONFIGURATION;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public float getLow()
  {
    return low;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setLow(float newLow)
  {
    float oldLow = low;
    low = newLow;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, WorkflowPackage.PROBABILITY_CONFIGURATION__LOW, oldLow, low));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public float getMedium()
  {
    return medium;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setMedium(float newMedium)
  {
    float oldMedium = medium;
    medium = newMedium;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, WorkflowPackage.PROBABILITY_CONFIGURATION__MEDIUM, oldMedium, medium));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public float getHigh()
  {
    return high;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setHigh(float newHigh)
  {
    float oldHigh = high;
    high = newHigh;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, WorkflowPackage.PROBABILITY_CONFIGURATION__HIGH, oldHigh, high));
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
      case WorkflowPackage.PROBABILITY_CONFIGURATION__LOW:
        return getLow();
      case WorkflowPackage.PROBABILITY_CONFIGURATION__MEDIUM:
        return getMedium();
      case WorkflowPackage.PROBABILITY_CONFIGURATION__HIGH:
        return getHigh();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case WorkflowPackage.PROBABILITY_CONFIGURATION__LOW:
        setLow((Float)newValue);
        return;
      case WorkflowPackage.PROBABILITY_CONFIGURATION__MEDIUM:
        setMedium((Float)newValue);
        return;
      case WorkflowPackage.PROBABILITY_CONFIGURATION__HIGH:
        setHigh((Float)newValue);
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
      case WorkflowPackage.PROBABILITY_CONFIGURATION__LOW:
        setLow(LOW_EDEFAULT);
        return;
      case WorkflowPackage.PROBABILITY_CONFIGURATION__MEDIUM:
        setMedium(MEDIUM_EDEFAULT);
        return;
      case WorkflowPackage.PROBABILITY_CONFIGURATION__HIGH:
        setHigh(HIGH_EDEFAULT);
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
      case WorkflowPackage.PROBABILITY_CONFIGURATION__LOW:
        return low != LOW_EDEFAULT;
      case WorkflowPackage.PROBABILITY_CONFIGURATION__MEDIUM:
        return medium != MEDIUM_EDEFAULT;
      case WorkflowPackage.PROBABILITY_CONFIGURATION__HIGH:
        return high != HIGH_EDEFAULT;
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
    result.append(" (low: ");
    result.append(low);
    result.append(", medium: ");
    result.append(medium);
    result.append(", high: ");
    result.append(high);
    result.append(')');
    return result.toString();
  }

} //ProbabilityConfigurationImpl
