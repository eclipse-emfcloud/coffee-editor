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

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.emfcloud.coffee.CoffeePackage;
import org.eclipse.emfcloud.coffee.ControlUnit;
import org.eclipse.emfcloud.coffee.Dimension;
import org.eclipse.emfcloud.coffee.Display;
import org.eclipse.emfcloud.coffee.Processor;
import org.eclipse.emfcloud.coffee.RAM;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Control Unit</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emfcloud.coffee.impl.ControlUnitImpl#getProcessor <em>Processor</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.coffee.impl.ControlUnitImpl#getDimension <em>Dimension</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.coffee.impl.ControlUnitImpl#getRam <em>Ram</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.coffee.impl.ControlUnitImpl#getDisplay <em>Display</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.coffee.impl.ControlUnitImpl#getUserDescription <em>User Description</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ControlUnitImpl extends ComponentImpl implements ControlUnit {
	/**
	 * The cached value of the '{@link #getProcessor() <em>Processor</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProcessor()
	 * @generated
	 * @ordered
	 */
	protected Processor processor;

	/**
	 * The cached value of the '{@link #getDimension() <em>Dimension</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDimension()
	 * @generated
	 * @ordered
	 */
	protected Dimension dimension;

	/**
	 * The cached value of the '{@link #getRam() <em>Ram</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRam()
	 * @generated
	 * @ordered
	 */
	protected EList<RAM> ram;

	/**
	 * The cached value of the '{@link #getDisplay() <em>Display</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDisplay()
	 * @generated
	 * @ordered
	 */
	protected Display display;

	/**
	 * The default value of the '{@link #getUserDescription() <em>User Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUserDescription()
	 * @generated
	 * @ordered
	 */
	protected static final String USER_DESCRIPTION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getUserDescription() <em>User Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUserDescription()
	 * @generated
	 * @ordered
	 */
	protected String userDescription = USER_DESCRIPTION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ControlUnitImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CoffeePackage.Literals.CONTROL_UNIT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Processor getProcessor() {
		return processor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProcessor(Processor newProcessor, NotificationChain msgs) {
		Processor oldProcessor = processor;
		processor = newProcessor;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CoffeePackage.CONTROL_UNIT__PROCESSOR, oldProcessor, newProcessor);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProcessor(Processor newProcessor) {
		if (newProcessor != processor) {
			NotificationChain msgs = null;
			if (processor != null)
				msgs = ((InternalEObject)processor).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CoffeePackage.CONTROL_UNIT__PROCESSOR, null, msgs);
			if (newProcessor != null)
				msgs = ((InternalEObject)newProcessor).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CoffeePackage.CONTROL_UNIT__PROCESSOR, null, msgs);
			msgs = basicSetProcessor(newProcessor, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CoffeePackage.CONTROL_UNIT__PROCESSOR, newProcessor, newProcessor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Dimension getDimension() {
		return dimension;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDimension(Dimension newDimension, NotificationChain msgs) {
		Dimension oldDimension = dimension;
		dimension = newDimension;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CoffeePackage.CONTROL_UNIT__DIMENSION, oldDimension, newDimension);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDimension(Dimension newDimension) {
		if (newDimension != dimension) {
			NotificationChain msgs = null;
			if (dimension != null)
				msgs = ((InternalEObject)dimension).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CoffeePackage.CONTROL_UNIT__DIMENSION, null, msgs);
			if (newDimension != null)
				msgs = ((InternalEObject)newDimension).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CoffeePackage.CONTROL_UNIT__DIMENSION, null, msgs);
			msgs = basicSetDimension(newDimension, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CoffeePackage.CONTROL_UNIT__DIMENSION, newDimension, newDimension));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<RAM> getRam() {
		if (ram == null) {
			ram = new EObjectContainmentEList<RAM>(RAM.class, this, CoffeePackage.CONTROL_UNIT__RAM);
		}
		return ram;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Display getDisplay() {
		return display;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDisplay(Display newDisplay, NotificationChain msgs) {
		Display oldDisplay = display;
		display = newDisplay;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CoffeePackage.CONTROL_UNIT__DISPLAY, oldDisplay, newDisplay);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDisplay(Display newDisplay) {
		if (newDisplay != display) {
			NotificationChain msgs = null;
			if (display != null)
				msgs = ((InternalEObject)display).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CoffeePackage.CONTROL_UNIT__DISPLAY, null, msgs);
			if (newDisplay != null)
				msgs = ((InternalEObject)newDisplay).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CoffeePackage.CONTROL_UNIT__DISPLAY, null, msgs);
			msgs = basicSetDisplay(newDisplay, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CoffeePackage.CONTROL_UNIT__DISPLAY, newDisplay, newDisplay));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getUserDescription() {
		return userDescription;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUserDescription(String newUserDescription) {
		String oldUserDescription = userDescription;
		userDescription = newUserDescription;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CoffeePackage.CONTROL_UNIT__USER_DESCRIPTION, oldUserDescription, userDescription));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CoffeePackage.CONTROL_UNIT__PROCESSOR:
				return basicSetProcessor(null, msgs);
			case CoffeePackage.CONTROL_UNIT__DIMENSION:
				return basicSetDimension(null, msgs);
			case CoffeePackage.CONTROL_UNIT__RAM:
				return ((InternalEList<?>)getRam()).basicRemove(otherEnd, msgs);
			case CoffeePackage.CONTROL_UNIT__DISPLAY:
				return basicSetDisplay(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CoffeePackage.CONTROL_UNIT__PROCESSOR:
				return getProcessor();
			case CoffeePackage.CONTROL_UNIT__DIMENSION:
				return getDimension();
			case CoffeePackage.CONTROL_UNIT__RAM:
				return getRam();
			case CoffeePackage.CONTROL_UNIT__DISPLAY:
				return getDisplay();
			case CoffeePackage.CONTROL_UNIT__USER_DESCRIPTION:
				return getUserDescription();
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
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case CoffeePackage.CONTROL_UNIT__PROCESSOR:
				setProcessor((Processor)newValue);
				return;
			case CoffeePackage.CONTROL_UNIT__DIMENSION:
				setDimension((Dimension)newValue);
				return;
			case CoffeePackage.CONTROL_UNIT__RAM:
				getRam().clear();
				getRam().addAll((Collection<? extends RAM>)newValue);
				return;
			case CoffeePackage.CONTROL_UNIT__DISPLAY:
				setDisplay((Display)newValue);
				return;
			case CoffeePackage.CONTROL_UNIT__USER_DESCRIPTION:
				setUserDescription((String)newValue);
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
			case CoffeePackage.CONTROL_UNIT__PROCESSOR:
				setProcessor((Processor)null);
				return;
			case CoffeePackage.CONTROL_UNIT__DIMENSION:
				setDimension((Dimension)null);
				return;
			case CoffeePackage.CONTROL_UNIT__RAM:
				getRam().clear();
				return;
			case CoffeePackage.CONTROL_UNIT__DISPLAY:
				setDisplay((Display)null);
				return;
			case CoffeePackage.CONTROL_UNIT__USER_DESCRIPTION:
				setUserDescription(USER_DESCRIPTION_EDEFAULT);
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
			case CoffeePackage.CONTROL_UNIT__PROCESSOR:
				return processor != null;
			case CoffeePackage.CONTROL_UNIT__DIMENSION:
				return dimension != null;
			case CoffeePackage.CONTROL_UNIT__RAM:
				return ram != null && !ram.isEmpty();
			case CoffeePackage.CONTROL_UNIT__DISPLAY:
				return display != null;
			case CoffeePackage.CONTROL_UNIT__USER_DESCRIPTION:
				return USER_DESCRIPTION_EDEFAULT == null ? userDescription != null : !USER_DESCRIPTION_EDEFAULT.equals(userDescription);
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
		result.append(" (userDescription: ");
		result.append(userDescription);
		result.append(')');
		return result.toString();
	}

} //ControlUnitImpl
