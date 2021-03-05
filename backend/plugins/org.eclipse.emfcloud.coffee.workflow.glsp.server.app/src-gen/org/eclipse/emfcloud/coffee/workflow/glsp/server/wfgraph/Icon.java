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
package org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph;

import org.eclipse.glsp.graph.GCompartment;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Icon</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.Icon#getCommandId <em>Command Id</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.WfgraphPackage#getIcon()
 * @model
 * @generated
 */
public interface Icon extends GCompartment {
	/**
	 * Returns the value of the '<em><b>Command Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Command Id</em>' attribute.
	 * @see #setCommandId(String)
	 * @see org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.WfgraphPackage#getIcon_CommandId()
	 * @model
	 * @generated
	 */
	String getCommandId();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.Icon#getCommandId <em>Command Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Command Id</em>' attribute.
	 * @see #getCommandId()
	 * @generated
	 */
	void setCommandId(String value);

} // Icon
