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
package com.eclipsesource.glsp.example.modelserver.workflow.wfnotation;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Point</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Point#getX
 * <em>X</em>}</li>
 * <li>{@link com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Point#getY
 * <em>Y</em>}</li>
 * </ul>
 *
 * @see com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.WfnotationPackage#getPoint()
 * @model
 * @generated
 */
public interface Point extends EObject {
	/**
	 * Returns the value of the '<em><b>X</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>X</em>' attribute.
	 * @see #setX(double)
	 * @see com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.WfnotationPackage#getPoint_X()
	 * @model
	 * @generated
	 */
	double getX();

	/**
	 * Sets the value of the
	 * '{@link com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Point#getX
	 * <em>X</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>X</em>' attribute.
	 * @see #getX()
	 * @generated
	 */
	void setX(double value);

	/**
	 * Returns the value of the '<em><b>Y</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Y</em>' attribute.
	 * @see #setY(double)
	 * @see com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.WfnotationPackage#getPoint_Y()
	 * @model
	 * @generated
	 */
	double getY();

	/**
	 * Sets the value of the
	 * '{@link com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Point#getY
	 * <em>Y</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Y</em>' attribute.
	 * @see #getY()
	 * @generated
	 */
	void setY(double value);

} // Point
