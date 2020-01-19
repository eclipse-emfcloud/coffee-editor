/**
 * Copyright (c) 2019 EclipseSource and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License v. 2.0 are satisfied: GNU General Public License, version 2
 * with the GNU Classpath Exception which is available at
 * https://www.gnu.org/software/classpath/license.html.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */
package com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.impl;

import com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Edge;
import com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Point;
import com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.WfnotationPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Edge</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.impl.EdgeImpl#getBendPoints
 * <em>Bend Points</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EdgeImpl extends DiagramElementImpl implements Edge {
	/**
	 * The cached value of the '{@link #getBendPoints() <em>Bend Points</em>}'
	 * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getBendPoints()
	 * @generated
	 * @ordered
	 */
	protected EList<Point> bendPoints;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EdgeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return WfnotationPackage.Literals.EDGE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<Point> getBendPoints() {
		if (bendPoints == null) {
			bendPoints = new EObjectContainmentEList<Point>(Point.class, this, WfnotationPackage.EDGE__BEND_POINTS);
		}
		return bendPoints;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case WfnotationPackage.EDGE__BEND_POINTS:
			return ((InternalEList<?>) getBendPoints()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case WfnotationPackage.EDGE__BEND_POINTS:
			return getBendPoints();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case WfnotationPackage.EDGE__BEND_POINTS:
			getBendPoints().clear();
			getBendPoints().addAll((Collection<? extends Point>) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case WfnotationPackage.EDGE__BEND_POINTS:
			getBendPoints().clear();
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case WfnotationPackage.EDGE__BEND_POINTS:
			return bendPoints != null && !bendPoints.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // EdgeImpl
