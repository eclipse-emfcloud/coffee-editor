/*******************************************************************************
 * Copyright (c) 2019-2020 EclipseSource and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ******************************************************************************/
package org.eclipse.emfcloud.coffee.workflow.glsp.server.handler.operation;

import static org.eclipse.glsp.server.protocol.GLSPServerException.getOrThrow;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.emfcloud.coffee.Flow;
import org.eclipse.emfcloud.coffee.Node;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.model.ShapeUtil;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.model.WorkflowModelIndex;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.model.WorkflowModelServerAccess;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.model.WorkflowModelState;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.Edge;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.Point;
import org.eclipse.glsp.graph.GEdge;
import org.eclipse.glsp.server.model.GModelState;
import org.eclipse.glsp.server.operations.BasicOperationHandler;
import org.eclipse.glsp.server.operations.ChangeRoutingPointsOperation;
import org.eclipse.glsp.server.types.ElementAndRoutingPoints;

public class ChangeRoutingPointsOperationHandler extends BasicOperationHandler<ChangeRoutingPointsOperation> {

	@Override
	public void executeOperation(ChangeRoutingPointsOperation operation, GModelState modelState) {
		operation.getNewRoutingPoints().forEach(ear -> applyRoutingPointsChange(ear, modelState));
	}

	private void applyRoutingPointsChange(ElementAndRoutingPoints ear, GModelState modelState) {
		GEdge gEdge = getOrThrow(modelState.getIndex().findElementByClass(ear.getElementId(), GEdge.class),
				"Invalid edge: edge ID " + ear.getElementId());
		// reroute
		WorkflowModelIndex modelIndex = WorkflowModelState.getModelState(modelState).getIndex();
		WorkflowModelServerAccess modelAccess = WorkflowModelState.getModelAccess(modelState);
		Node sourceNode = modelIndex.getSemantic(gEdge.getSourceId(), Node.class).get();
		Node targetNode = modelIndex.getSemantic(gEdge.getTargetId(), Node.class).get();
		Optional<Flow> flow = modelAccess.getFlow(sourceNode, targetNode);
		Edge edge = getOrThrow(flow.flatMap(f -> modelIndex.getNotation(f, Edge.class)),
				"Cannot find edge for GEdge ID " + ear.getElementId());

		List<Point> bendPoints = ear.getNewRoutingPoints().stream().map(ShapeUtil::point).collect(Collectors.toList());
		edge.getBendPoints().clear();
		edge.getBendPoints().addAll(bendPoints);
	}
}
