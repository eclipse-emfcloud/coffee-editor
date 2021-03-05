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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Diagram Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.impl.DiagramElementImpl#getSemanticElement <em>Semantic Element</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.impl.DiagramElementImpl#getGraphicId <em>Graphic Id</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class DiagramElementImpl extends MinimalEObjectImpl.Container
		implements org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.DiagramElement {
	/**
	 * The cached value of the '{@link #getSemanticElement() <em>Semantic Element</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSemanticElement()
	 * @generated
	 * @ordered
	 */
	protected org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.SemanticProxy semanticElement;

	/**
	 * The default value of the '{@link #getGraphicId() <em>Graphic Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGraphicId()
	 * @generated
	 * @ordered
	 */
	protected static final String GRAPHIC_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getGraphicId() <em>Graphic Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGraphicId()
	 * @generated
	 * @ordered
	 */
	protected String graphicId = GRAPHIC_ID_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DiagramElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage.Literals.DIAGRAM_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.SemanticProxy getSemanticElement() {
		return semanticElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSemanticElement(
			org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.SemanticProxy newSemanticElement,
			NotificationChain msgs) {
		org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.SemanticProxy oldSemanticElement = semanticElement;
		semanticElement = newSemanticElement;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage.DIAGRAM_ELEMENT__SEMANTIC_ELEMENT,
					oldSemanticElement, newSemanticElement);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSemanticElement(
			org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.SemanticProxy newSemanticElement) {
		if (newSemanticElement != semanticElement) {
			NotificationChain msgs = null;
			if (semanticElement != null)
				msgs = ((InternalEObject) semanticElement).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage.DIAGRAM_ELEMENT__SEMANTIC_ELEMENT,
						null, msgs);
			if (newSemanticElement != null)
				msgs = ((InternalEObject) newSemanticElement).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage.DIAGRAM_ELEMENT__SEMANTIC_ELEMENT,
						null, msgs);
			msgs = basicSetSemanticElement(newSemanticElement, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage.DIAGRAM_ELEMENT__SEMANTIC_ELEMENT,
					newSemanticElement, newSemanticElement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getGraphicId() {
		return graphicId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setGraphicId(String newGraphicId) {
		String oldGraphicId = graphicId;
		graphicId = newGraphicId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage.DIAGRAM_ELEMENT__GRAPHIC_ID,
					oldGraphicId, graphicId));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage.DIAGRAM_ELEMENT__SEMANTIC_ELEMENT:
			return basicSetSemanticElement(null, msgs);
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
		case org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage.DIAGRAM_ELEMENT__SEMANTIC_ELEMENT:
			return getSemanticElement();
		case org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage.DIAGRAM_ELEMENT__GRAPHIC_ID:
			return getGraphicId();
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
		case org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage.DIAGRAM_ELEMENT__SEMANTIC_ELEMENT:
			setSemanticElement((org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.SemanticProxy) newValue);
			return;
		case org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage.DIAGRAM_ELEMENT__GRAPHIC_ID:
			setGraphicId((String) newValue);
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
		case org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage.DIAGRAM_ELEMENT__SEMANTIC_ELEMENT:
			setSemanticElement((org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.SemanticProxy) null);
			return;
		case org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage.DIAGRAM_ELEMENT__GRAPHIC_ID:
			setGraphicId(GRAPHIC_ID_EDEFAULT);
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
		case org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage.DIAGRAM_ELEMENT__SEMANTIC_ELEMENT:
			return semanticElement != null;
		case org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage.DIAGRAM_ELEMENT__GRAPHIC_ID:
			return GRAPHIC_ID_EDEFAULT == null ? graphicId != null : !GRAPHIC_ID_EDEFAULT.equals(graphicId);
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
		if (eIsProxy())
			return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (graphicId: ");
		result.append(graphicId);
		result.append(')');
		return result.toString();
	}

} //DiagramElementImpl
