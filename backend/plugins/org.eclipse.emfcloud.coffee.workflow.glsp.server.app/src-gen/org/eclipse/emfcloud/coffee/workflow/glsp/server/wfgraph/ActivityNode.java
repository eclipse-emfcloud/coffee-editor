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

import org.eclipse.glsp.graph.GNode;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Activity Node</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.ActivityNode#getNodeType <em>Node Type</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.WfgraphPackage#getActivityNode()
 * @model
 * @generated
 */
public interface ActivityNode extends GNode {
	/**
	 * Returns the value of the '<em><b>Node Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Node Type</em>' attribute.
	 * @see #setNodeType(String)
	 * @see org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.WfgraphPackage#getActivityNode_NodeType()
	 * @model
	 * @generated
	 */
	String getNodeType();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.ActivityNode#getNodeType <em>Node Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Node Type</em>' attribute.
	 * @see #getNodeType()
	 * @generated
	 */
	void setNodeType(String value);

} // ActivityNode
