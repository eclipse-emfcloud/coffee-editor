/*******************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
 *  
 *   This program and the accompanying materials are made available under the
 *   terms of the Eclipse Public License v. 2.0 which is available at
 *   http://www.eclipse.org/legal/epl-2.0.
 *  
 *   This Source Code may also be made available under the following Secondary
 *   Licenses when the conditions for such availability set forth in the Eclipse
 *   Public License v. 2.0 are satisfied: GNU General Public License, version 2
 *   with the GNU Classpath Exception which is available at
 *   https://www.gnu.org/software/classpath/license.html.
 *  
 *   SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 ******************************************************************************/
package com.eclipsesource.glsp.example.modelserver.workflow.handler;

import static org.eclipse.glsp.api.jsonrpc.GLSPServerException.getOrThrow;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.emfcloud.modelserver.coffee.model.coffee.Flow;
import org.eclipse.emfcloud.modelserver.coffee.model.coffee.Node;
import org.eclipse.glsp.api.action.kind.AbstractOperationAction;
import org.eclipse.glsp.api.action.kind.ChangeRoutingPointsOperationAction;
import org.eclipse.glsp.api.handler.OperationHandler;
import org.eclipse.glsp.api.model.GraphicalModelState;
import org.eclipse.glsp.api.types.ElementAndRoutingPoints;
import org.eclipse.glsp.graph.GEdge;
import org.eclipse.glsp.graph.GModelIndex;

import com.eclipsesource.glsp.example.modelserver.workflow.model.ModelServerAwareModelState;
import com.eclipsesource.glsp.example.modelserver.workflow.model.ShapeUtil;
import com.eclipsesource.glsp.example.modelserver.workflow.model.WorkflowModelServerAccess;
import com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Edge;
import com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Point;

public class WorkflowRerouteEdgeHandler implements OperationHandler {

	@Override
	public Class<?> handlesActionType() {
		return ChangeRoutingPointsOperationAction.class;
	}

	@Override
	public String getLabel(AbstractOperationAction action) {
		return "Reconnect edge";
	}

	@Override
	public void execute(AbstractOperationAction operationAction, GraphicalModelState modelState) {
		if (!(operationAction instanceof ChangeRoutingPointsOperationAction)) {
			throw new IllegalArgumentException("Unexpected action " + operationAction);
		}

		final ChangeRoutingPointsOperationAction action = (ChangeRoutingPointsOperationAction) operationAction;
		GModelIndex index = modelState.getIndex();
		action.getNewRoutingPoints().forEach(ear-> applyRoutingPointsChange(ear,modelState));
	}

	private void applyRoutingPointsChange(ElementAndRoutingPoints ear, GraphicalModelState modelState) {
		GEdge gEdge = getOrThrow(modelState.getIndex().findElementByClass(ear.getElementId(), GEdge.class),
				"Invalid edge: edge ID " + ear.getElementId());
		// reroute
		WorkflowModelServerAccess modelAccess = ModelServerAwareModelState.getModelAccess(modelState);
		Node sourceNode = modelAccess.getNodeById(gEdge.getSourceId());
		Node targetNode = modelAccess.getNodeById(gEdge.getTargetId());
		Optional<Flow> flow = modelAccess.getFlow(sourceNode, targetNode);
		Edge edge = getOrThrow(flow.flatMap(f -> modelAccess.getWorkflowFacade().findDiagramElement(f, Edge.class)),
				"Cannot find edge for GEdge ID " + ear.getElementId());

		List<Point> bendPoints = ear.getNewRoutingPoints().stream().map(ShapeUtil::point).collect(Collectors.toList());
		edge.getBendPoints().clear();
		edge.getBendPoints().addAll(bendPoints);
	}

}
