/**
 * Copyright (c) 2019 EclipseSource and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 * 
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License v. 2.0 are satisfied: GNU General Public License, version 2
 * with the GNU Classpath Exception which is available at
 * https://www.gnu.org/software/classpath/license.html.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */
package com.eclipsesource.workflow.glsp.server.wfgraph;

import org.eclipse.glsp.graph.GEdge;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Weighted Edge</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.eclipsesource.workflow.glsp.server.wfgraph.WeightedEdge#getProbability <em>Probability</em>}</li>
 * </ul>
 *
 * @see com.eclipsesource.workflow.glsp.server.wfgraph.WfgraphPackage#getWeightedEdge()
 * @model
 * @generated
 */
public interface WeightedEdge extends GEdge {
	/**
	 * Returns the value of the '<em><b>Probability</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Probability</em>' attribute.
	 * @see #setProbability(String)
	 * @see com.eclipsesource.workflow.glsp.server.wfgraph.WfgraphPackage#getWeightedEdge_Probability()
	 * @model
	 * @generated
	 */
	String getProbability();

	/**
	 * Sets the value of the '{@link com.eclipsesource.workflow.glsp.server.wfgraph.WeightedEdge#getProbability <em>Probability</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Probability</em>' attribute.
	 * @see #getProbability()
	 * @generated
	 */
	void setProbability(String value);

} // WeightedEdge
