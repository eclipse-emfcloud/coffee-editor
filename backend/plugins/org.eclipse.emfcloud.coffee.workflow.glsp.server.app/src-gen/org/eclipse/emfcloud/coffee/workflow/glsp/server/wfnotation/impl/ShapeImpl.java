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

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Shape</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.impl.ShapeImpl#getPosition <em>Position</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.impl.ShapeImpl#getSize <em>Size</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ShapeImpl extends DiagramElementImpl
		implements org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.Shape {
	/**
	 * The cached value of the '{@link #getPosition() <em>Position</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPosition()
	 * @generated
	 * @ordered
	 */
	protected org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.Point position;

	/**
	 * The cached value of the '{@link #getSize() <em>Size</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSize()
	 * @generated
	 * @ordered
	 */
	protected org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.Dimension size;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ShapeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage.Literals.SHAPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.Point getPosition() {
		return position;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPosition(
			org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.Point newPosition, NotificationChain msgs) {
		org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.Point oldPosition = position;
		position = newPosition;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage.SHAPE__POSITION,
					oldPosition, newPosition);
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
	public void setPosition(org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.Point newPosition) {
		if (newPosition != position) {
			NotificationChain msgs = null;
			if (position != null)
				msgs = ((InternalEObject) position).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage.SHAPE__POSITION,
						null, msgs);
			if (newPosition != null)
				msgs = ((InternalEObject) newPosition).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage.SHAPE__POSITION,
						null, msgs);
			msgs = basicSetPosition(newPosition, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage.SHAPE__POSITION,
					newPosition, newPosition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.Dimension getSize() {
		return size;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSize(org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.Dimension newSize,
			NotificationChain msgs) {
		org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.Dimension oldSize = size;
		size = newSize;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage.SHAPE__SIZE, oldSize,
					newSize);
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
	public void setSize(org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.Dimension newSize) {
		if (newSize != size) {
			NotificationChain msgs = null;
			if (size != null)
				msgs = ((InternalEObject) size).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage.SHAPE__SIZE,
						null, msgs);
			if (newSize != null)
				msgs = ((InternalEObject) newSize).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage.SHAPE__SIZE,
						null, msgs);
			msgs = basicSetSize(newSize, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage.SHAPE__SIZE, newSize,
					newSize));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage.SHAPE__POSITION:
			return basicSetPosition(null, msgs);
		case org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage.SHAPE__SIZE:
			return basicSetSize(null, msgs);
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
		case org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage.SHAPE__POSITION:
			return getPosition();
		case org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage.SHAPE__SIZE:
			return getSize();
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
		case org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage.SHAPE__POSITION:
			setPosition((org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.Point) newValue);
			return;
		case org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage.SHAPE__SIZE:
			setSize((org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.Dimension) newValue);
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
		case org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage.SHAPE__POSITION:
			setPosition((org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.Point) null);
			return;
		case org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage.SHAPE__SIZE:
			setSize((org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.Dimension) null);
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
		case org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage.SHAPE__POSITION:
			return position != null;
		case org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage.SHAPE__SIZE:
			return size != null;
		}
		return super.eIsSet(featureID);
	}

} //ShapeImpl
