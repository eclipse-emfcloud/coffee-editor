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

import org.eclipse.emfcloud.coffee.workflow.dsl.workflow.Assertion;
import org.eclipse.emfcloud.coffee.workflow.dsl.workflow.WorkflowPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Assertion</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emfcloud.coffee.workflow.dsl.workflow.impl.AssertionImpl#getBefore <em>Before</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.coffee.workflow.dsl.workflow.impl.AssertionImpl#getAfter <em>After</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AssertionImpl extends MinimalEObjectImpl.Container implements Assertion
{
  /**
   * The default value of the '{@link #getBefore() <em>Before</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getBefore()
   * @generated
   * @ordered
   */
  protected static final String BEFORE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getBefore() <em>Before</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getBefore()
   * @generated
   * @ordered
   */
  protected String before = BEFORE_EDEFAULT;

  /**
   * The default value of the '{@link #getAfter() <em>After</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAfter()
   * @generated
   * @ordered
   */
  protected static final String AFTER_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getAfter() <em>After</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAfter()
   * @generated
   * @ordered
   */
  protected String after = AFTER_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected AssertionImpl()
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
    return WorkflowPackage.Literals.ASSERTION;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getBefore()
  {
    return before;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setBefore(String newBefore)
  {
    String oldBefore = before;
    before = newBefore;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, WorkflowPackage.ASSERTION__BEFORE, oldBefore, before));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getAfter()
  {
    return after;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setAfter(String newAfter)
  {
    String oldAfter = after;
    after = newAfter;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, WorkflowPackage.ASSERTION__AFTER, oldAfter, after));
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
      case WorkflowPackage.ASSERTION__BEFORE:
        return getBefore();
      case WorkflowPackage.ASSERTION__AFTER:
        return getAfter();
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
      case WorkflowPackage.ASSERTION__BEFORE:
        setBefore((String)newValue);
        return;
      case WorkflowPackage.ASSERTION__AFTER:
        setAfter((String)newValue);
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
      case WorkflowPackage.ASSERTION__BEFORE:
        setBefore(BEFORE_EDEFAULT);
        return;
      case WorkflowPackage.ASSERTION__AFTER:
        setAfter(AFTER_EDEFAULT);
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
      case WorkflowPackage.ASSERTION__BEFORE:
        return BEFORE_EDEFAULT == null ? before != null : !BEFORE_EDEFAULT.equals(before);
      case WorkflowPackage.ASSERTION__AFTER:
        return AFTER_EDEFAULT == null ? after != null : !AFTER_EDEFAULT.equals(after);
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
    result.append(" (before: ");
    result.append(before);
    result.append(", after: ");
    result.append(after);
    result.append(')');
    return result.toString();
  }

} //AssertionImpl
