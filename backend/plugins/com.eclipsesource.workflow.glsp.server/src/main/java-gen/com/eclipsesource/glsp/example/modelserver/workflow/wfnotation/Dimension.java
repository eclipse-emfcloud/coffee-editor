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
 * '<em><b>Dimension</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Dimension#getWidth
 * <em>Width</em>}</li>
 * <li>{@link com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Dimension#getHeight
 * <em>Height</em>}</li>
 * </ul>
 *
 * @see com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.WfnotationPackage#getDimension()
 * @model
 * @generated
 */
public interface Dimension extends EObject {
	/**
	 * Returns the value of the '<em><b>Width</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Width</em>' attribute.
	 * @see #setWidth(double)
	 * @see com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.WfnotationPackage#getDimension_Width()
	 * @model
	 * @generated
	 */
	double getWidth();

	/**
	 * Sets the value of the
	 * '{@link com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Dimension#getWidth
	 * <em>Width</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Width</em>' attribute.
	 * @see #getWidth()
	 * @generated
	 */
	void setWidth(double value);

	/**
	 * Returns the value of the '<em><b>Height</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Height</em>' attribute.
	 * @see #setHeight(double)
	 * @see com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.WfnotationPackage#getDimension_Height()
	 * @model
	 * @generated
	 */
	double getHeight();

	/**
	 * Sets the value of the
	 * '{@link com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Dimension#getHeight
	 * <em>Height</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Height</em>' attribute.
	 * @see #getHeight()
	 * @generated
	 */
	void setHeight(double value);

} // Dimension
