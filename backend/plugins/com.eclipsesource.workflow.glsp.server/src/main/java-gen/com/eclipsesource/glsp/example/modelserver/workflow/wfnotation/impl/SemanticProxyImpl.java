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

import com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.SemanticProxy;
import com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.WfnotationPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Semantic Proxy</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.impl.SemanticProxyImpl#getUri
 * <em>Uri</em>}</li>
 * <li>{@link com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.impl.SemanticProxyImpl#getResolvedElement
 * <em>Resolved Element</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SemanticProxyImpl extends MinimalEObjectImpl.Container implements SemanticProxy {
	/**
	 * The default value of the '{@link #getUri() <em>Uri</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getUri()
	 * @generated
	 * @ordered
	 */
	protected static final String URI_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getUri() <em>Uri</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getUri()
	 * @generated
	 * @ordered
	 */
	protected String uri = URI_EDEFAULT;

	/**
	 * The cached value of the '{@link #getResolvedElement() <em>Resolved
	 * Element</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getResolvedElement()
	 * @generated
	 * @ordered
	 */
	protected EObject resolvedElement;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public SemanticProxyImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return WfnotationPackage.Literals.SEMANTIC_PROXY;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getUri() {
		return uri;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setUri(String newUri) {
		String oldUri = uri;
		uri = newUri;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, WfnotationPackage.SEMANTIC_PROXY__URI, oldUri, uri));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EObject getResolvedElement() {
		if (resolvedElement != null && resolvedElement.eIsProxy()) {
			InternalEObject oldResolvedElement = (InternalEObject) resolvedElement;
			resolvedElement = eResolveProxy(oldResolvedElement);
			if (resolvedElement != oldResolvedElement) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							WfnotationPackage.SEMANTIC_PROXY__RESOLVED_ELEMENT, oldResolvedElement, resolvedElement));
			}
		}
		return resolvedElement;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EObject basicGetResolvedElement() {
		return resolvedElement;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setResolvedElement(EObject newResolvedElement) {
		EObject oldResolvedElement = resolvedElement;
		resolvedElement = newResolvedElement;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, WfnotationPackage.SEMANTIC_PROXY__RESOLVED_ELEMENT,
					oldResolvedElement, resolvedElement));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case WfnotationPackage.SEMANTIC_PROXY__URI:
			return getUri();
		case WfnotationPackage.SEMANTIC_PROXY__RESOLVED_ELEMENT:
			if (resolve)
				return getResolvedElement();
			return basicGetResolvedElement();
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
		case WfnotationPackage.SEMANTIC_PROXY__URI:
			setUri((String) newValue);
			return;
		case WfnotationPackage.SEMANTIC_PROXY__RESOLVED_ELEMENT:
			setResolvedElement((EObject) newValue);
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
		case WfnotationPackage.SEMANTIC_PROXY__URI:
			setUri(URI_EDEFAULT);
			return;
		case WfnotationPackage.SEMANTIC_PROXY__RESOLVED_ELEMENT:
			setResolvedElement((EObject) null);
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
		case WfnotationPackage.SEMANTIC_PROXY__URI:
			return URI_EDEFAULT == null ? uri != null : !URI_EDEFAULT.equals(uri);
		case WfnotationPackage.SEMANTIC_PROXY__RESOLVED_ELEMENT:
			return resolvedElement != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (uri: ");
		result.append(uri);
		result.append(')');
		return result.toString();
	}

} // SemanticProxyImpl
