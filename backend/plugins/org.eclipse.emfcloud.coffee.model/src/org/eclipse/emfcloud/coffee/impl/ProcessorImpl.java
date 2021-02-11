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
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emfcloud.coffee.CoffeePackage;
import org.eclipse.emfcloud.coffee.ManufactoringProcess;
import org.eclipse.emfcloud.coffee.Processor;
import org.eclipse.emfcloud.coffee.SocketConnectorType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Processor</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emfcloud.coffee.impl.ProcessorImpl#getVendor <em>Vendor</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.coffee.impl.ProcessorImpl#getClockSpeed <em>Clock Speed</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.coffee.impl.ProcessorImpl#getNumberOfCores <em>Number Of Cores</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.coffee.impl.ProcessorImpl#getSocketconnectorType <em>Socketconnector Type</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.coffee.impl.ProcessorImpl#getThermalDesignPower <em>Thermal Design Power</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.coffee.impl.ProcessorImpl#getManufactoringProcess <em>Manufactoring Process</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ProcessorImpl extends MinimalEObjectImpl.Container implements Processor {
	/**
	 * The default value of the '{@link #getVendor() <em>Vendor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVendor()
	 * @generated
	 * @ordered
	 */
	protected static final String VENDOR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getVendor() <em>Vendor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVendor()
	 * @generated
	 * @ordered
	 */
	protected String vendor = VENDOR_EDEFAULT;

	/**
	 * The default value of the '{@link #getClockSpeed() <em>Clock Speed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClockSpeed()
	 * @generated
	 * @ordered
	 */
	protected static final int CLOCK_SPEED_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getClockSpeed() <em>Clock Speed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClockSpeed()
	 * @generated
	 * @ordered
	 */
	protected int clockSpeed = CLOCK_SPEED_EDEFAULT;

	/**
	 * The default value of the '{@link #getNumberOfCores() <em>Number Of Cores</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumberOfCores()
	 * @generated
	 * @ordered
	 */
	protected static final int NUMBER_OF_CORES_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getNumberOfCores() <em>Number Of Cores</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumberOfCores()
	 * @generated
	 * @ordered
	 */
	protected int numberOfCores = NUMBER_OF_CORES_EDEFAULT;

	/**
	 * The default value of the '{@link #getSocketconnectorType() <em>Socketconnector Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSocketconnectorType()
	 * @generated
	 * @ordered
	 */
	protected static final SocketConnectorType SOCKETCONNECTOR_TYPE_EDEFAULT = SocketConnectorType.A1T;

	/**
	 * The cached value of the '{@link #getSocketconnectorType() <em>Socketconnector Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSocketconnectorType()
	 * @generated
	 * @ordered
	 */
	protected SocketConnectorType socketconnectorType = SOCKETCONNECTOR_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getThermalDesignPower() <em>Thermal Design Power</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getThermalDesignPower()
	 * @generated
	 * @ordered
	 */
	protected static final int THERMAL_DESIGN_POWER_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getThermalDesignPower() <em>Thermal Design Power</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getThermalDesignPower()
	 * @generated
	 * @ordered
	 */
	protected int thermalDesignPower = THERMAL_DESIGN_POWER_EDEFAULT;

	/**
	 * The default value of the '{@link #getManufactoringProcess() <em>Manufactoring Process</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getManufactoringProcess()
	 * @generated
	 * @ordered
	 */
	protected static final ManufactoringProcess MANUFACTORING_PROCESS_EDEFAULT = ManufactoringProcess.NM18;

	/**
	 * The cached value of the '{@link #getManufactoringProcess() <em>Manufactoring Process</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getManufactoringProcess()
	 * @generated
	 * @ordered
	 */
	protected ManufactoringProcess manufactoringProcess = MANUFACTORING_PROCESS_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProcessorImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CoffeePackage.Literals.PROCESSOR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getVendor() {
		return vendor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVendor(String newVendor) {
		String oldVendor = vendor;
		vendor = newVendor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CoffeePackage.PROCESSOR__VENDOR, oldVendor, vendor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getClockSpeed() {
		return clockSpeed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setClockSpeed(int newClockSpeed) {
		int oldClockSpeed = clockSpeed;
		clockSpeed = newClockSpeed;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CoffeePackage.PROCESSOR__CLOCK_SPEED, oldClockSpeed, clockSpeed));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getNumberOfCores() {
		return numberOfCores;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNumberOfCores(int newNumberOfCores) {
		int oldNumberOfCores = numberOfCores;
		numberOfCores = newNumberOfCores;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CoffeePackage.PROCESSOR__NUMBER_OF_CORES, oldNumberOfCores, numberOfCores));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SocketConnectorType getSocketconnectorType() {
		return socketconnectorType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSocketconnectorType(SocketConnectorType newSocketconnectorType) {
		SocketConnectorType oldSocketconnectorType = socketconnectorType;
		socketconnectorType = newSocketconnectorType == null ? SOCKETCONNECTOR_TYPE_EDEFAULT : newSocketconnectorType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CoffeePackage.PROCESSOR__SOCKETCONNECTOR_TYPE, oldSocketconnectorType, socketconnectorType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getThermalDesignPower() {
		return thermalDesignPower;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setThermalDesignPower(int newThermalDesignPower) {
		int oldThermalDesignPower = thermalDesignPower;
		thermalDesignPower = newThermalDesignPower;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CoffeePackage.PROCESSOR__THERMAL_DESIGN_POWER, oldThermalDesignPower, thermalDesignPower));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ManufactoringProcess getManufactoringProcess() {
		return manufactoringProcess;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setManufactoringProcess(ManufactoringProcess newManufactoringProcess) {
		ManufactoringProcess oldManufactoringProcess = manufactoringProcess;
		manufactoringProcess = newManufactoringProcess == null ? MANUFACTORING_PROCESS_EDEFAULT : newManufactoringProcess;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CoffeePackage.PROCESSOR__MANUFACTORING_PROCESS, oldManufactoringProcess, manufactoringProcess));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CoffeePackage.PROCESSOR__VENDOR:
				return getVendor();
			case CoffeePackage.PROCESSOR__CLOCK_SPEED:
				return getClockSpeed();
			case CoffeePackage.PROCESSOR__NUMBER_OF_CORES:
				return getNumberOfCores();
			case CoffeePackage.PROCESSOR__SOCKETCONNECTOR_TYPE:
				return getSocketconnectorType();
			case CoffeePackage.PROCESSOR__THERMAL_DESIGN_POWER:
				return getThermalDesignPower();
			case CoffeePackage.PROCESSOR__MANUFACTORING_PROCESS:
				return getManufactoringProcess();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case CoffeePackage.PROCESSOR__VENDOR:
				setVendor((String)newValue);
				return;
			case CoffeePackage.PROCESSOR__CLOCK_SPEED:
				setClockSpeed((Integer)newValue);
				return;
			case CoffeePackage.PROCESSOR__NUMBER_OF_CORES:
				setNumberOfCores((Integer)newValue);
				return;
			case CoffeePackage.PROCESSOR__SOCKETCONNECTOR_TYPE:
				setSocketconnectorType((SocketConnectorType)newValue);
				return;
			case CoffeePackage.PROCESSOR__THERMAL_DESIGN_POWER:
				setThermalDesignPower((Integer)newValue);
				return;
			case CoffeePackage.PROCESSOR__MANUFACTORING_PROCESS:
				setManufactoringProcess((ManufactoringProcess)newValue);
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
	public void eUnset(int featureID) {
		switch (featureID) {
			case CoffeePackage.PROCESSOR__VENDOR:
				setVendor(VENDOR_EDEFAULT);
				return;
			case CoffeePackage.PROCESSOR__CLOCK_SPEED:
				setClockSpeed(CLOCK_SPEED_EDEFAULT);
				return;
			case CoffeePackage.PROCESSOR__NUMBER_OF_CORES:
				setNumberOfCores(NUMBER_OF_CORES_EDEFAULT);
				return;
			case CoffeePackage.PROCESSOR__SOCKETCONNECTOR_TYPE:
				setSocketconnectorType(SOCKETCONNECTOR_TYPE_EDEFAULT);
				return;
			case CoffeePackage.PROCESSOR__THERMAL_DESIGN_POWER:
				setThermalDesignPower(THERMAL_DESIGN_POWER_EDEFAULT);
				return;
			case CoffeePackage.PROCESSOR__MANUFACTORING_PROCESS:
				setManufactoringProcess(MANUFACTORING_PROCESS_EDEFAULT);
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
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case CoffeePackage.PROCESSOR__VENDOR:
				return VENDOR_EDEFAULT == null ? vendor != null : !VENDOR_EDEFAULT.equals(vendor);
			case CoffeePackage.PROCESSOR__CLOCK_SPEED:
				return clockSpeed != CLOCK_SPEED_EDEFAULT;
			case CoffeePackage.PROCESSOR__NUMBER_OF_CORES:
				return numberOfCores != NUMBER_OF_CORES_EDEFAULT;
			case CoffeePackage.PROCESSOR__SOCKETCONNECTOR_TYPE:
				return socketconnectorType != SOCKETCONNECTOR_TYPE_EDEFAULT;
			case CoffeePackage.PROCESSOR__THERMAL_DESIGN_POWER:
				return thermalDesignPower != THERMAL_DESIGN_POWER_EDEFAULT;
			case CoffeePackage.PROCESSOR__MANUFACTORING_PROCESS:
				return manufactoringProcess != MANUFACTORING_PROCESS_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (vendor: ");
		result.append(vendor);
		result.append(", clockSpeed: ");
		result.append(clockSpeed);
		result.append(", numberOfCores: ");
		result.append(numberOfCores);
		result.append(", socketconnectorType: ");
		result.append(socketconnectorType);
		result.append(", thermalDesignPower: ");
		result.append(thermalDesignPower);
		result.append(", manufactoringProcess: ");
		result.append(manufactoringProcess);
		result.append(')');
		return result.toString();
	}

} //ProcessorImpl
