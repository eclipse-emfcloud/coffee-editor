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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.eclipsesource.glsp.api.action.kind.AbstractOperationAction;
import com.eclipsesource.glsp.api.action.kind.RerouteConnectionOperationAction;
import com.eclipsesource.glsp.api.handler.OperationHandler;
import com.eclipsesource.glsp.api.model.GraphicalModelState;
import com.eclipsesource.glsp.example.modelserver.workflow.model.ModelServerAwareModelState;
import com.eclipsesource.glsp.example.modelserver.workflow.model.ShapeUtil;
import com.eclipsesource.glsp.example.modelserver.workflow.model.WorkflowModelServerAccess;
import com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.DiagramElement;
import com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Edge;
import com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Point;
import com.eclipsesource.glsp.graph.GEdge;
import com.eclipsesource.glsp.graph.GModelIndex;
import com.eclipsesource.modelserver.coffee.model.coffee.Flow;
import com.eclipsesource.modelserver.coffee.model.coffee.Node;

public class RerouteEdgeHandler implements OperationHandler {

	@Override
	public Class<?> handlesActionType() {
		return RerouteConnectionOperationAction.class;
	}

	@Override
	public String getLabel(AbstractOperationAction action) {
		return "Reconnect edge";
	}

	@Override
	public void execute(AbstractOperationAction operationAction, GraphicalModelState modelState) {
		if (!(operationAction instanceof RerouteConnectionOperationAction)) {
			throw new IllegalArgumentException("Unexpected action " + operationAction);
		}

		// check for null-values
		final RerouteConnectionOperationAction action = (RerouteConnectionOperationAction) operationAction;
		if (action.getConnectionElementId() == null || action.getRoutingPoints() == null) {
			throw new IllegalArgumentException("Incomplete reconnect connection action");
		}

		// check for existence of matching elements
		GModelIndex index = modelState.getIndex();
		Optional<GEdge> gEdge = index.findElementByClass(action.getConnectionElementId(), GEdge.class);
		if (!gEdge.isPresent()) {
			throw new IllegalArgumentException("Invalid edge: edge ID " + action.getConnectionElementId());
		}

		// reroute
		WorkflowModelServerAccess modelAccess = ModelServerAwareModelState.getModelAccess(modelState);
		Node sourceNode = modelAccess.getNodeById(gEdge.get().getSourceId());
		Node targetNode = modelAccess.getNodeById(gEdge.get().getTargetId());
		Optional<Flow> flow = modelAccess.getFlow(sourceNode, targetNode);
		Optional<DiagramElement> maybeEdge = flow.flatMap(f -> modelAccess.getWorkflowFacade().findDiagramElement(f));
		if (maybeEdge.isEmpty() || !(maybeEdge.get() instanceof Edge)) {
			throw new IllegalArgumentException("Cannot find edge for GEdge ID " + action.getConnectionElementId());
		}

		Edge edge2 = (Edge) maybeEdge.get();
		List<Point> bendPoints = action.getRoutingPoints().stream().map(ShapeUtil::point).collect(Collectors.toList());
		edge2.getBendPoints().clear();
		edge2.getBendPoints().addAll(bendPoints);
	}

}
