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

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

import org.eclipse.emfcloud.coffee.CoffeePackage;
import org.eclipse.emfcloud.coffee.Flow;
import org.eclipse.emfcloud.coffee.MenuSelectionTask;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Menu Selection Task</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emfcloud.coffee.impl.MenuSelectionTaskImpl#getPrompt <em>Prompt</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.coffee.impl.MenuSelectionTaskImpl#getMenu <em>Menu</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.coffee.impl.MenuSelectionTaskImpl#getTimeout <em>Timeout</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.coffee.impl.MenuSelectionTaskImpl#getSelectionFlow <em>Selection Flow</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.coffee.impl.MenuSelectionTaskImpl#getTimeoutFlow <em>Timeout Flow</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MenuSelectionTaskImpl extends ManualTaskImpl implements MenuSelectionTask {
	/**
	 * The default value of the '{@link #getPrompt() <em>Prompt</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPrompt()
	 * @generated
	 * @ordered
	 */
	protected static final String PROMPT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPrompt() <em>Prompt</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPrompt()
	 * @generated
	 * @ordered
	 */
	protected String prompt = PROMPT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getMenu() <em>Menu</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMenu()
	 * @generated
	 * @ordered
	 */
	protected EList<String> menu;

	/**
	 * The default value of the '{@link #getTimeout() <em>Timeout</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTimeout()
	 * @generated
	 * @ordered
	 */
	protected static final int TIMEOUT_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getTimeout() <em>Timeout</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTimeout()
	 * @generated
	 * @ordered
	 */
	protected int timeout = TIMEOUT_EDEFAULT;

	/**
	 * This is true if the Timeout attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean timeoutESet;

	/**
	 * The cached value of the '{@link #getSelectionFlow() <em>Selection Flow</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSelectionFlow()
	 * @generated
	 * @ordered
	 */
	protected Flow selectionFlow;

	/**
	 * The cached value of the '{@link #getTimeoutFlow() <em>Timeout Flow</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTimeoutFlow()
	 * @generated
	 * @ordered
	 */
	protected Flow timeoutFlow;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MenuSelectionTaskImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CoffeePackage.Literals.MENU_SELECTION_TASK;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPrompt() {
		return prompt;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPrompt(String newPrompt) {
		String oldPrompt = prompt;
		prompt = newPrompt;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CoffeePackage.MENU_SELECTION_TASK__PROMPT, oldPrompt, prompt));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getMenu() {
		if (menu == null) {
			menu = new EDataTypeUniqueEList<String>(String.class, this, CoffeePackage.MENU_SELECTION_TASK__MENU);
		}
		return menu;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getTimeout() {
		return timeout;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTimeout(int newTimeout) {
		int oldTimeout = timeout;
		timeout = newTimeout;
		boolean oldTimeoutESet = timeoutESet;
		timeoutESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CoffeePackage.MENU_SELECTION_TASK__TIMEOUT, oldTimeout, timeout, !oldTimeoutESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetTimeout() {
		int oldTimeout = timeout;
		boolean oldTimeoutESet = timeoutESet;
		timeout = TIMEOUT_EDEFAULT;
		timeoutESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, CoffeePackage.MENU_SELECTION_TASK__TIMEOUT, oldTimeout, TIMEOUT_EDEFAULT, oldTimeoutESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetTimeout() {
		return timeoutESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Flow getSelectionFlow() {
		if (selectionFlow != null && selectionFlow.eIsProxy()) {
			InternalEObject oldSelectionFlow = (InternalEObject)selectionFlow;
			selectionFlow = (Flow)eResolveProxy(oldSelectionFlow);
			if (selectionFlow != oldSelectionFlow) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CoffeePackage.MENU_SELECTION_TASK__SELECTION_FLOW, oldSelectionFlow, selectionFlow));
			}
		}
		return selectionFlow;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Flow basicGetSelectionFlow() {
		return selectionFlow;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSelectionFlow(Flow newSelectionFlow) {
		Flow oldSelectionFlow = selectionFlow;
		selectionFlow = newSelectionFlow;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CoffeePackage.MENU_SELECTION_TASK__SELECTION_FLOW, oldSelectionFlow, selectionFlow));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Flow getTimeoutFlow() {
		if (timeoutFlow != null && timeoutFlow.eIsProxy()) {
			InternalEObject oldTimeoutFlow = (InternalEObject)timeoutFlow;
			timeoutFlow = (Flow)eResolveProxy(oldTimeoutFlow);
			if (timeoutFlow != oldTimeoutFlow) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CoffeePackage.MENU_SELECTION_TASK__TIMEOUT_FLOW, oldTimeoutFlow, timeoutFlow));
			}
		}
		return timeoutFlow;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Flow basicGetTimeoutFlow() {
		return timeoutFlow;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTimeoutFlow(Flow newTimeoutFlow) {
		Flow oldTimeoutFlow = timeoutFlow;
		timeoutFlow = newTimeoutFlow;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CoffeePackage.MENU_SELECTION_TASK__TIMEOUT_FLOW, oldTimeoutFlow, timeoutFlow));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CoffeePackage.MENU_SELECTION_TASK__PROMPT:
				return getPrompt();
			case CoffeePackage.MENU_SELECTION_TASK__MENU:
				return getMenu();
			case CoffeePackage.MENU_SELECTION_TASK__TIMEOUT:
				return getTimeout();
			case CoffeePackage.MENU_SELECTION_TASK__SELECTION_FLOW:
				if (resolve) return getSelectionFlow();
				return basicGetSelectionFlow();
			case CoffeePackage.MENU_SELECTION_TASK__TIMEOUT_FLOW:
				if (resolve) return getTimeoutFlow();
				return basicGetTimeoutFlow();
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
			case CoffeePackage.MENU_SELECTION_TASK__PROMPT:
				setPrompt((String)newValue);
				return;
			case CoffeePackage.MENU_SELECTION_TASK__MENU:
				getMenu().clear();
				getMenu().addAll((Collection<? extends String>)newValue);
				return;
			case CoffeePackage.MENU_SELECTION_TASK__TIMEOUT:
				setTimeout((Integer)newValue);
				return;
			case CoffeePackage.MENU_SELECTION_TASK__SELECTION_FLOW:
				setSelectionFlow((Flow)newValue);
				return;
			case CoffeePackage.MENU_SELECTION_TASK__TIMEOUT_FLOW:
				setTimeoutFlow((Flow)newValue);
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
			case CoffeePackage.MENU_SELECTION_TASK__PROMPT:
				setPrompt(PROMPT_EDEFAULT);
				return;
			case CoffeePackage.MENU_SELECTION_TASK__MENU:
				getMenu().clear();
				return;
			case CoffeePackage.MENU_SELECTION_TASK__TIMEOUT:
				unsetTimeout();
				return;
			case CoffeePackage.MENU_SELECTION_TASK__SELECTION_FLOW:
				setSelectionFlow((Flow)null);
				return;
			case CoffeePackage.MENU_SELECTION_TASK__TIMEOUT_FLOW:
				setTimeoutFlow((Flow)null);
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
			case CoffeePackage.MENU_SELECTION_TASK__PROMPT:
				return PROMPT_EDEFAULT == null ? prompt != null : !PROMPT_EDEFAULT.equals(prompt);
			case CoffeePackage.MENU_SELECTION_TASK__MENU:
				return menu != null && !menu.isEmpty();
			case CoffeePackage.MENU_SELECTION_TASK__TIMEOUT:
				return isSetTimeout();
			case CoffeePackage.MENU_SELECTION_TASK__SELECTION_FLOW:
				return selectionFlow != null;
			case CoffeePackage.MENU_SELECTION_TASK__TIMEOUT_FLOW:
				return timeoutFlow != null;
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
		result.append(" (prompt: ");
		result.append(prompt);
		result.append(", menu: ");
		result.append(menu);
		result.append(", timeout: ");
		if (timeoutESet) result.append(timeout); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //MenuSelectionTaskImpl
