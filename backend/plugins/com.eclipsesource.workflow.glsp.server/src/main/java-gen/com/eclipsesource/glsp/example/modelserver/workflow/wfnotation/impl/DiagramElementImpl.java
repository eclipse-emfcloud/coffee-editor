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

import com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.DiagramElement;
import com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.SemanticProxy;
import com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.WfnotationPackage;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Diagram
 * Element</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.impl.DiagramElementImpl#getSemanticElement
 * <em>Semantic Element</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class DiagramElementImpl extends MinimalEObjectImpl.Container implements DiagramElement {
	/**
	 * The cached value of the '{@link #getSemanticElement() <em>Semantic
	 * Element</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getSemanticElement()
	 * @generated
	 * @ordered
	 */
	protected SemanticProxy semanticElement;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public DiagramElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return WfnotationPackage.Literals.DIAGRAM_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public SemanticProxy getSemanticElement() {
		return semanticElement;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetSemanticElement(SemanticProxy newSemanticElement, NotificationChain msgs) {
		SemanticProxy oldSemanticElement = semanticElement;
		semanticElement = newSemanticElement;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					WfnotationPackage.DIAGRAM_ELEMENT__SEMANTIC_ELEMENT, oldSemanticElement, newSemanticElement);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setSemanticElement(SemanticProxy newSemanticElement) {
		if (newSemanticElement != semanticElement) {
			NotificationChain msgs = null;
			if (semanticElement != null)
				msgs = ((InternalEObject) semanticElement).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - WfnotationPackage.DIAGRAM_ELEMENT__SEMANTIC_ELEMENT, null, msgs);
			if (newSemanticElement != null)
				msgs = ((InternalEObject) newSemanticElement).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - WfnotationPackage.DIAGRAM_ELEMENT__SEMANTIC_ELEMENT, null, msgs);
			msgs = basicSetSemanticElement(newSemanticElement, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, WfnotationPackage.DIAGRAM_ELEMENT__SEMANTIC_ELEMENT,
					newSemanticElement, newSemanticElement));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case WfnotationPackage.DIAGRAM_ELEMENT__SEMANTIC_ELEMENT:
			return basicSetSemanticElement(null, msgs);
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
		case WfnotationPackage.DIAGRAM_ELEMENT__SEMANTIC_ELEMENT:
			return getSemanticElement();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case WfnotationPackage.DIAGRAM_ELEMENT__SEMANTIC_ELEMENT:
			setSemanticElement((SemanticProxy) newValue);
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
		case WfnotationPackage.DIAGRAM_ELEMENT__SEMANTIC_ELEMENT:
			setSemanticElement((SemanticProxy) null);
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
		case WfnotationPackage.DIAGRAM_ELEMENT__SEMANTIC_ELEMENT:
			return semanticElement != null;
		}
		return super.eIsSet(featureID);
	}

} // DiagramElementImpl
