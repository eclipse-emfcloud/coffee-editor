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
package com.eclipsesource.workflow.glsp.server.wfgraph;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.glsp.graph.GraphPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see com.eclipsesource.workflow.glsp.server.wfgraph.WfgraphFactory
 * @model kind="package"
 * @generated
 */
public interface WfgraphPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "wfgraph";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipsesource.com/glsp/examples/workflow/graph";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "wfgraph";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	WfgraphPackage eINSTANCE = com.eclipsesource.workflow.glsp.server.wfgraph.impl.WfgraphPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.eclipsesource.workflow.glsp.server.wfgraph.impl.ActivityNodeImpl <em>Activity Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.eclipsesource.workflow.glsp.server.wfgraph.impl.ActivityNodeImpl
	 * @see com.eclipsesource.workflow.glsp.server.wfgraph.impl.WfgraphPackageImpl#getActivityNode()
	 * @generated
	 */
	int ACTIVITY_NODE = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY_NODE__ID = GraphPackage.GNODE__ID;

	/**
	 * The feature id for the '<em><b>Css Classes</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY_NODE__CSS_CLASSES = GraphPackage.GNODE__CSS_CLASSES;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY_NODE__CHILDREN = GraphPackage.GNODE__CHILDREN;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY_NODE__PARENT = GraphPackage.GNODE__PARENT;

	/**
	 * The feature id for the '<em><b>Trace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY_NODE__TRACE = GraphPackage.GNODE__TRACE;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY_NODE__TYPE = GraphPackage.GNODE__TYPE;

	/**
	 * The feature id for the '<em><b>Position</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY_NODE__POSITION = GraphPackage.GNODE__POSITION;

	/**
	 * The feature id for the '<em><b>Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY_NODE__SIZE = GraphPackage.GNODE__SIZE;

	/**
	 * The feature id for the '<em><b>Edge Placement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY_NODE__EDGE_PLACEMENT = GraphPackage.GNODE__EDGE_PLACEMENT;

	/**
	 * The feature id for the '<em><b>Layout</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY_NODE__LAYOUT = GraphPackage.GNODE__LAYOUT;

	/**
	 * The feature id for the '<em><b>Layout Options</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY_NODE__LAYOUT_OPTIONS = GraphPackage.GNODE__LAYOUT_OPTIONS;

	/**
	 * The feature id for the '<em><b>Node Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY_NODE__NODE_TYPE = GraphPackage.GNODE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Activity Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY_NODE_FEATURE_COUNT = GraphPackage.GNODE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Activity Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY_NODE_OPERATION_COUNT = GraphPackage.GNODE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.eclipsesource.workflow.glsp.server.wfgraph.impl.TaskNodeImpl <em>Task Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.eclipsesource.workflow.glsp.server.wfgraph.impl.TaskNodeImpl
	 * @see com.eclipsesource.workflow.glsp.server.wfgraph.impl.WfgraphPackageImpl#getTaskNode()
	 * @generated
	 */
	int TASK_NODE = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_NODE__ID = GraphPackage.GNODE__ID;

	/**
	 * The feature id for the '<em><b>Css Classes</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_NODE__CSS_CLASSES = GraphPackage.GNODE__CSS_CLASSES;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_NODE__CHILDREN = GraphPackage.GNODE__CHILDREN;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_NODE__PARENT = GraphPackage.GNODE__PARENT;

	/**
	 * The feature id for the '<em><b>Trace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_NODE__TRACE = GraphPackage.GNODE__TRACE;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_NODE__TYPE = GraphPackage.GNODE__TYPE;

	/**
	 * The feature id for the '<em><b>Position</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_NODE__POSITION = GraphPackage.GNODE__POSITION;

	/**
	 * The feature id for the '<em><b>Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_NODE__SIZE = GraphPackage.GNODE__SIZE;

	/**
	 * The feature id for the '<em><b>Edge Placement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_NODE__EDGE_PLACEMENT = GraphPackage.GNODE__EDGE_PLACEMENT;

	/**
	 * The feature id for the '<em><b>Layout</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_NODE__LAYOUT = GraphPackage.GNODE__LAYOUT;

	/**
	 * The feature id for the '<em><b>Layout Options</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_NODE__LAYOUT_OPTIONS = GraphPackage.GNODE__LAYOUT_OPTIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_NODE__NAME = GraphPackage.GNODE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Expanded</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_NODE__EXPANDED = GraphPackage.GNODE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Duration</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_NODE__DURATION = GraphPackage.GNODE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Task Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_NODE__TASK_TYPE = GraphPackage.GNODE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Reference</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_NODE__REFERENCE = GraphPackage.GNODE_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Task Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_NODE_FEATURE_COUNT = GraphPackage.GNODE_FEATURE_COUNT + 5;

	/**
	 * The number of operations of the '<em>Task Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_NODE_OPERATION_COUNT = GraphPackage.GNODE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.eclipsesource.workflow.glsp.server.wfgraph.impl.IconImpl <em>Icon</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.eclipsesource.workflow.glsp.server.wfgraph.impl.IconImpl
	 * @see com.eclipsesource.workflow.glsp.server.wfgraph.impl.WfgraphPackageImpl#getIcon()
	 * @generated
	 */
	int ICON = 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICON__ID = GraphPackage.GCOMPARTMENT__ID;

	/**
	 * The feature id for the '<em><b>Css Classes</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICON__CSS_CLASSES = GraphPackage.GCOMPARTMENT__CSS_CLASSES;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICON__CHILDREN = GraphPackage.GCOMPARTMENT__CHILDREN;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICON__PARENT = GraphPackage.GCOMPARTMENT__PARENT;

	/**
	 * The feature id for the '<em><b>Trace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICON__TRACE = GraphPackage.GCOMPARTMENT__TRACE;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICON__TYPE = GraphPackage.GCOMPARTMENT__TYPE;

	/**
	 * The feature id for the '<em><b>Position</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICON__POSITION = GraphPackage.GCOMPARTMENT__POSITION;

	/**
	 * The feature id for the '<em><b>Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICON__SIZE = GraphPackage.GCOMPARTMENT__SIZE;

	/**
	 * The feature id for the '<em><b>Layout</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICON__LAYOUT = GraphPackage.GCOMPARTMENT__LAYOUT;

	/**
	 * The feature id for the '<em><b>Layout Options</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICON__LAYOUT_OPTIONS = GraphPackage.GCOMPARTMENT__LAYOUT_OPTIONS;

	/**
	 * The feature id for the '<em><b>Command Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICON__COMMAND_ID = GraphPackage.GCOMPARTMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Icon</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICON_FEATURE_COUNT = GraphPackage.GCOMPARTMENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Icon</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICON_OPERATION_COUNT = GraphPackage.GCOMPARTMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.eclipsesource.workflow.glsp.server.wfgraph.impl.WeightedEdgeImpl <em>Weighted Edge</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.eclipsesource.workflow.glsp.server.wfgraph.impl.WeightedEdgeImpl
	 * @see com.eclipsesource.workflow.glsp.server.wfgraph.impl.WfgraphPackageImpl#getWeightedEdge()
	 * @generated
	 */
	int WEIGHTED_EDGE = 3;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEIGHTED_EDGE__ID = GraphPackage.GEDGE__ID;

	/**
	 * The feature id for the '<em><b>Css Classes</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEIGHTED_EDGE__CSS_CLASSES = GraphPackage.GEDGE__CSS_CLASSES;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEIGHTED_EDGE__CHILDREN = GraphPackage.GEDGE__CHILDREN;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEIGHTED_EDGE__PARENT = GraphPackage.GEDGE__PARENT;

	/**
	 * The feature id for the '<em><b>Trace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEIGHTED_EDGE__TRACE = GraphPackage.GEDGE__TRACE;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEIGHTED_EDGE__TYPE = GraphPackage.GEDGE__TYPE;

	/**
	 * The feature id for the '<em><b>Routing Points</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEIGHTED_EDGE__ROUTING_POINTS = GraphPackage.GEDGE__ROUTING_POINTS;

	/**
	 * The feature id for the '<em><b>Source Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEIGHTED_EDGE__SOURCE_ID = GraphPackage.GEDGE__SOURCE_ID;

	/**
	 * The feature id for the '<em><b>Target Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEIGHTED_EDGE__TARGET_ID = GraphPackage.GEDGE__TARGET_ID;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEIGHTED_EDGE__SOURCE = GraphPackage.GEDGE__SOURCE;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEIGHTED_EDGE__TARGET = GraphPackage.GEDGE__TARGET;

	/**
	 * The feature id for the '<em><b>Router Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEIGHTED_EDGE__ROUTER_KIND = GraphPackage.GEDGE__ROUTER_KIND;

	/**
	 * The feature id for the '<em><b>Probability</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEIGHTED_EDGE__PROBABILITY = GraphPackage.GEDGE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Weighted Edge</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEIGHTED_EDGE_FEATURE_COUNT = GraphPackage.GEDGE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Weighted Edge</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEIGHTED_EDGE_OPERATION_COUNT = GraphPackage.GEDGE_OPERATION_COUNT + 0;

	/**
	 * Returns the meta object for class '{@link com.eclipsesource.workflow.glsp.server.wfgraph.ActivityNode <em>Activity Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Activity Node</em>'.
	 * @see com.eclipsesource.workflow.glsp.server.wfgraph.ActivityNode
	 * @generated
	 */
	EClass getActivityNode();

	/**
	 * Returns the meta object for the attribute '{@link com.eclipsesource.workflow.glsp.server.wfgraph.ActivityNode#getNodeType <em>Node Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Node Type</em>'.
	 * @see com.eclipsesource.workflow.glsp.server.wfgraph.ActivityNode#getNodeType()
	 * @see #getActivityNode()
	 * @generated
	 */
	EAttribute getActivityNode_NodeType();

	/**
	 * Returns the meta object for class '{@link com.eclipsesource.workflow.glsp.server.wfgraph.TaskNode <em>Task Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Task Node</em>'.
	 * @see com.eclipsesource.workflow.glsp.server.wfgraph.TaskNode
	 * @generated
	 */
	EClass getTaskNode();

	/**
	 * Returns the meta object for the attribute '{@link com.eclipsesource.workflow.glsp.server.wfgraph.TaskNode#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.eclipsesource.workflow.glsp.server.wfgraph.TaskNode#getName()
	 * @see #getTaskNode()
	 * @generated
	 */
	EAttribute getTaskNode_Name();

	/**
	 * Returns the meta object for the attribute '{@link com.eclipsesource.workflow.glsp.server.wfgraph.TaskNode#isExpanded <em>Expanded</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Expanded</em>'.
	 * @see com.eclipsesource.workflow.glsp.server.wfgraph.TaskNode#isExpanded()
	 * @see #getTaskNode()
	 * @generated
	 */
	EAttribute getTaskNode_Expanded();

	/**
	 * Returns the meta object for the attribute '{@link com.eclipsesource.workflow.glsp.server.wfgraph.TaskNode#getDuration <em>Duration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Duration</em>'.
	 * @see com.eclipsesource.workflow.glsp.server.wfgraph.TaskNode#getDuration()
	 * @see #getTaskNode()
	 * @generated
	 */
	EAttribute getTaskNode_Duration();

	/**
	 * Returns the meta object for the attribute '{@link com.eclipsesource.workflow.glsp.server.wfgraph.TaskNode#getTaskType <em>Task Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Task Type</em>'.
	 * @see com.eclipsesource.workflow.glsp.server.wfgraph.TaskNode#getTaskType()
	 * @see #getTaskNode()
	 * @generated
	 */
	EAttribute getTaskNode_TaskType();

	/**
	 * Returns the meta object for the attribute '{@link com.eclipsesource.workflow.glsp.server.wfgraph.TaskNode#getReference <em>Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Reference</em>'.
	 * @see com.eclipsesource.workflow.glsp.server.wfgraph.TaskNode#getReference()
	 * @see #getTaskNode()
	 * @generated
	 */
	EAttribute getTaskNode_Reference();

	/**
	 * Returns the meta object for class '{@link com.eclipsesource.workflow.glsp.server.wfgraph.Icon <em>Icon</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Icon</em>'.
	 * @see com.eclipsesource.workflow.glsp.server.wfgraph.Icon
	 * @generated
	 */
	EClass getIcon();

	/**
	 * Returns the meta object for the attribute '{@link com.eclipsesource.workflow.glsp.server.wfgraph.Icon#getCommandId <em>Command Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Command Id</em>'.
	 * @see com.eclipsesource.workflow.glsp.server.wfgraph.Icon#getCommandId()
	 * @see #getIcon()
	 * @generated
	 */
	EAttribute getIcon_CommandId();

	/**
	 * Returns the meta object for class '{@link com.eclipsesource.workflow.glsp.server.wfgraph.WeightedEdge <em>Weighted Edge</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Weighted Edge</em>'.
	 * @see com.eclipsesource.workflow.glsp.server.wfgraph.WeightedEdge
	 * @generated
	 */
	EClass getWeightedEdge();

	/**
	 * Returns the meta object for the attribute '{@link com.eclipsesource.workflow.glsp.server.wfgraph.WeightedEdge#getProbability <em>Probability</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Probability</em>'.
	 * @see com.eclipsesource.workflow.glsp.server.wfgraph.WeightedEdge#getProbability()
	 * @see #getWeightedEdge()
	 * @generated
	 */
	EAttribute getWeightedEdge_Probability();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	WfgraphFactory getWfgraphFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link com.eclipsesource.workflow.glsp.server.wfgraph.impl.ActivityNodeImpl <em>Activity Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.eclipsesource.workflow.glsp.server.wfgraph.impl.ActivityNodeImpl
		 * @see com.eclipsesource.workflow.glsp.server.wfgraph.impl.WfgraphPackageImpl#getActivityNode()
		 * @generated
		 */
		EClass ACTIVITY_NODE = eINSTANCE.getActivityNode();

		/**
		 * The meta object literal for the '<em><b>Node Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACTIVITY_NODE__NODE_TYPE = eINSTANCE.getActivityNode_NodeType();

		/**
		 * The meta object literal for the '{@link com.eclipsesource.workflow.glsp.server.wfgraph.impl.TaskNodeImpl <em>Task Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.eclipsesource.workflow.glsp.server.wfgraph.impl.TaskNodeImpl
		 * @see com.eclipsesource.workflow.glsp.server.wfgraph.impl.WfgraphPackageImpl#getTaskNode()
		 * @generated
		 */
		EClass TASK_NODE = eINSTANCE.getTaskNode();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TASK_NODE__NAME = eINSTANCE.getTaskNode_Name();

		/**
		 * The meta object literal for the '<em><b>Expanded</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TASK_NODE__EXPANDED = eINSTANCE.getTaskNode_Expanded();

		/**
		 * The meta object literal for the '<em><b>Duration</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TASK_NODE__DURATION = eINSTANCE.getTaskNode_Duration();

		/**
		 * The meta object literal for the '<em><b>Task Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TASK_NODE__TASK_TYPE = eINSTANCE.getTaskNode_TaskType();

		/**
		 * The meta object literal for the '<em><b>Reference</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TASK_NODE__REFERENCE = eINSTANCE.getTaskNode_Reference();

		/**
		 * The meta object literal for the '{@link com.eclipsesource.workflow.glsp.server.wfgraph.impl.IconImpl <em>Icon</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.eclipsesource.workflow.glsp.server.wfgraph.impl.IconImpl
		 * @see com.eclipsesource.workflow.glsp.server.wfgraph.impl.WfgraphPackageImpl#getIcon()
		 * @generated
		 */
		EClass ICON = eINSTANCE.getIcon();

		/**
		 * The meta object literal for the '<em><b>Command Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ICON__COMMAND_ID = eINSTANCE.getIcon_CommandId();

		/**
		 * The meta object literal for the '{@link com.eclipsesource.workflow.glsp.server.wfgraph.impl.WeightedEdgeImpl <em>Weighted Edge</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.eclipsesource.workflow.glsp.server.wfgraph.impl.WeightedEdgeImpl
		 * @see com.eclipsesource.workflow.glsp.server.wfgraph.impl.WfgraphPackageImpl#getWeightedEdge()
		 * @generated
		 */
		EClass WEIGHTED_EDGE = eINSTANCE.getWeightedEdge();

		/**
		 * The meta object literal for the '<em><b>Probability</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WEIGHTED_EDGE__PROBABILITY = eINSTANCE.getWeightedEdge_Probability();

	}

} //WfgraphPackage
