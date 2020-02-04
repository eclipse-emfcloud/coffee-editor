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

import org.eclipse.glsp.graph.GNode;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Task Node</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.eclipsesource.workflow.glsp.server.wfgraph.TaskNode#getName <em>Name</em>}</li>
 *   <li>{@link com.eclipsesource.workflow.glsp.server.wfgraph.TaskNode#isExpanded <em>Expanded</em>}</li>
 *   <li>{@link com.eclipsesource.workflow.glsp.server.wfgraph.TaskNode#getDuration <em>Duration</em>}</li>
 *   <li>{@link com.eclipsesource.workflow.glsp.server.wfgraph.TaskNode#getTaskType <em>Task Type</em>}</li>
 *   <li>{@link com.eclipsesource.workflow.glsp.server.wfgraph.TaskNode#getReference <em>Reference</em>}</li>
 * </ul>
 *
 * @see com.eclipsesource.workflow.glsp.server.wfgraph.WfgraphPackage#getTaskNode()
 * @model
 * @generated
 */
public interface TaskNode extends GNode {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see com.eclipsesource.workflow.glsp.server.wfgraph.WfgraphPackage#getTaskNode_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.eclipsesource.workflow.glsp.server.wfgraph.TaskNode#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Expanded</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Expanded</em>' attribute.
	 * @see #setExpanded(boolean)
	 * @see com.eclipsesource.workflow.glsp.server.wfgraph.WfgraphPackage#getTaskNode_Expanded()
	 * @model default="false" required="true"
	 * @generated
	 */
	boolean isExpanded();

	/**
	 * Sets the value of the '{@link com.eclipsesource.workflow.glsp.server.wfgraph.TaskNode#isExpanded <em>Expanded</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Expanded</em>' attribute.
	 * @see #isExpanded()
	 * @generated
	 */
	void setExpanded(boolean value);

	/**
	 * Returns the value of the '<em><b>Duration</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Duration</em>' attribute.
	 * @see #setDuration(int)
	 * @see com.eclipsesource.workflow.glsp.server.wfgraph.WfgraphPackage#getTaskNode_Duration()
	 * @model default="0" required="true"
	 * @generated
	 */
	int getDuration();

	/**
	 * Sets the value of the '{@link com.eclipsesource.workflow.glsp.server.wfgraph.TaskNode#getDuration <em>Duration</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Duration</em>' attribute.
	 * @see #getDuration()
	 * @generated
	 */
	void setDuration(int value);

	/**
	 * Returns the value of the '<em><b>Task Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Task Type</em>' attribute.
	 * @see #setTaskType(String)
	 * @see com.eclipsesource.workflow.glsp.server.wfgraph.WfgraphPackage#getTaskNode_TaskType()
	 * @model
	 * @generated
	 */
	String getTaskType();

	/**
	 * Sets the value of the '{@link com.eclipsesource.workflow.glsp.server.wfgraph.TaskNode#getTaskType <em>Task Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Task Type</em>' attribute.
	 * @see #getTaskType()
	 * @generated
	 */
	void setTaskType(String value);

	/**
	 * Returns the value of the '<em><b>Reference</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Reference</em>' attribute.
	 * @see #setReference(String)
	 * @see com.eclipsesource.workflow.glsp.server.wfgraph.WfgraphPackage#getTaskNode_Reference()
	 * @model
	 * @generated
	 */
	String getReference();

	/**
	 * Sets the value of the '{@link com.eclipsesource.workflow.glsp.server.wfgraph.TaskNode#getReference <em>Reference</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Reference</em>' attribute.
	 * @see #getReference()
	 * @generated
	 */
	void setReference(String value);

} // TaskNode
