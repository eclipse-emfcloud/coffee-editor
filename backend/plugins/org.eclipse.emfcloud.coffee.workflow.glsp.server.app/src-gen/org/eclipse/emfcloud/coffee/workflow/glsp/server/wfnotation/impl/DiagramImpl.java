/**
 * Copyright (c) 2019-2020 EclipseSource and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 */
package org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Diagram</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.impl.DiagramImpl#getElements <em>Elements</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DiagramImpl extends DiagramElementImpl
		implements org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.Diagram {
	/**
	 * The cached value of the '{@link #getElements() <em>Elements</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElements()
	 * @generated
	 * @ordered
	 */
	protected EList<org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.DiagramElement> elements;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DiagramImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage.Literals.DIAGRAM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.DiagramElement> getElements() {
		if (elements == null) {
			elements = new EObjectContainmentEList<org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.DiagramElement>(
					org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.DiagramElement.class, this,
					org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage.DIAGRAM__ELEMENTS);
		}
		return elements;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage.DIAGRAM__ELEMENTS:
			return ((InternalEList<?>) getElements()).basicRemove(otherEnd, msgs);
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
		case org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage.DIAGRAM__ELEMENTS:
			return getElements();
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
		case org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage.DIAGRAM__ELEMENTS:
			getElements().clear();
			getElements().addAll(
					(Collection<? extends org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.DiagramElement>) newValue);
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
		case org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage.DIAGRAM__ELEMENTS:
			getElements().clear();
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
		case org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage.DIAGRAM__ELEMENTS:
			return elements != null && !elements.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //DiagramImpl
