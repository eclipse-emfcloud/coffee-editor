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
package org.eclipse.emfcloud.coffee.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emfcloud.coffee.CoffeePackage;
import org.eclipse.emfcloud.coffee.RAM;
import org.eclipse.emfcloud.coffee.RamType;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>RAM</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emfcloud.coffee.impl.RAMImpl#getClockSpeed <em>Clock Speed</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.coffee.impl.RAMImpl#getSize <em>Size</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.coffee.impl.RAMImpl#getRamType <em>Ram Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RAMImpl extends IdentifiableImpl implements RAM {
   /**
    * The default value of the '{@link #getClockSpeed() <em>Clock Speed</em>}' attribute.
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * @see #getClockSpeed()
    * @generated
    * @ordered
    */
   protected static final int CLOCK_SPEED_EDEFAULT = 0;

   /**
    * The cached value of the '{@link #getClockSpeed() <em>Clock Speed</em>}' attribute.
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * @see #getClockSpeed()
    * @generated
    * @ordered
    */
   protected int clockSpeed = CLOCK_SPEED_EDEFAULT;

   /**
    * The default value of the '{@link #getSize() <em>Size</em>}' attribute. <!--
    * begin-user-doc --> <!-- end-user-doc -->
    *
    * @see #getSize()
    * @generated
    * @ordered
    */
   protected static final int SIZE_EDEFAULT = 0;

   /**
    * The cached value of the '{@link #getSize() <em>Size</em>}' attribute. <!--
    * begin-user-doc --> <!-- end-user-doc -->
    *
    * @see #getSize()
    * @generated
    * @ordered
    */
   protected int size = SIZE_EDEFAULT;

   /**
    * The default value of the '{@link #getRamType() <em>Ram Type</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getRamType()
    * @generated
    * @ordered
    */
   protected static final RamType RAM_TYPE_EDEFAULT = RamType.SODIMM;

   /**
    * The cached value of the '{@link #getRamType() <em>Ram Type</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getRamType()
    * @generated
    * @ordered
    */
   protected RamType ramType = RAM_TYPE_EDEFAULT;

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * @generated
    */
   protected RAMImpl() {
      super();
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * @generated
    */
   @Override
   protected EClass eStaticClass() {
      return CoffeePackage.Literals.RAM;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * @generated
    */
   @Override
   public int getClockSpeed() {
      return clockSpeed;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * @generated
    */
   @Override
   public void setClockSpeed(int newClockSpeed) {
      int oldClockSpeed = clockSpeed;
      clockSpeed = newClockSpeed;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, CoffeePackage.RAM__CLOCK_SPEED, oldClockSpeed, clockSpeed));
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * @generated
    */
   @Override
   public int getSize() {
      return size;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * @generated
    */
   @Override
   public void setSize(int newSize) {
      int oldSize = size;
      size = newSize;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, CoffeePackage.RAM__SIZE, oldSize, size));
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public RamType getRamType() {
      return ramType;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public void setRamType(RamType newRamType) {
      RamType oldRamType = ramType;
      ramType = newRamType == null ? RAM_TYPE_EDEFAULT : newRamType;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, CoffeePackage.RAM__RAM_TYPE, oldRamType, ramType));
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Object eGet(int featureID, boolean resolve, boolean coreType) {
      switch (featureID) {
         case CoffeePackage.RAM__CLOCK_SPEED:
            return getClockSpeed();
         case CoffeePackage.RAM__SIZE:
            return getSize();
         case CoffeePackage.RAM__RAM_TYPE:
            return getRamType();
      }
      return super.eGet(featureID, resolve, coreType);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * @generated
    */
   @Override
   public void eSet(int featureID, Object newValue) {
      switch (featureID) {
         case CoffeePackage.RAM__CLOCK_SPEED:
            setClockSpeed((Integer)newValue);
            return;
         case CoffeePackage.RAM__SIZE:
            setSize((Integer)newValue);
            return;
         case CoffeePackage.RAM__RAM_TYPE:
            setRamType((RamType)newValue);
            return;
      }
      super.eSet(featureID, newValue);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * @generated
    */
   @Override
   public void eUnset(int featureID) {
      switch (featureID) {
         case CoffeePackage.RAM__CLOCK_SPEED:
            setClockSpeed(CLOCK_SPEED_EDEFAULT);
            return;
         case CoffeePackage.RAM__SIZE:
            setSize(SIZE_EDEFAULT);
            return;
         case CoffeePackage.RAM__RAM_TYPE:
            setRamType(RAM_TYPE_EDEFAULT);
            return;
      }
      super.eUnset(featureID);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * @generated
    */
   @Override
   public boolean eIsSet(int featureID) {
      switch (featureID) {
         case CoffeePackage.RAM__CLOCK_SPEED:
            return clockSpeed != CLOCK_SPEED_EDEFAULT;
         case CoffeePackage.RAM__SIZE:
            return size != SIZE_EDEFAULT;
         case CoffeePackage.RAM__RAM_TYPE:
            return ramType != RAM_TYPE_EDEFAULT;
      }
      return super.eIsSet(featureID);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * @generated
    */
   @Override
   public String toString() {
      if (eIsProxy()) return super.toString();

      StringBuilder result = new StringBuilder(super.toString());
      result.append(" (clockSpeed: ");
      result.append(clockSpeed);
      result.append(", size: ");
      result.append(size);
      result.append(", ramType: ");
      result.append(ramType);
      result.append(')');
      return result.toString();
   }

} // RAMImpl
