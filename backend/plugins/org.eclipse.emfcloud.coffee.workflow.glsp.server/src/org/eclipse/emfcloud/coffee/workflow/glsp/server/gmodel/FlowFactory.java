/********************************************************************************
 * Copyright (c) 2021 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package org.eclipse.emfcloud.coffee.workflow.glsp.server.gmodel;

import java.util.ArrayList;

import org.eclipse.emfcloud.coffee.Flow;
import org.eclipse.emfcloud.coffee.WeightedFlow;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.model.WorkflowModelState;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.util.WorkflowBuilder.WeightedEdgeBuilder;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.WeightedEdge;
import org.eclipse.emfcloud.modelserver.glsp.notation.Edge;
import org.eclipse.glsp.graph.GEdge;
import org.eclipse.glsp.graph.GPoint;
import org.eclipse.glsp.graph.builder.impl.GEdgeBuilder;
import org.eclipse.glsp.graph.util.GConstants;
import org.eclipse.glsp.graph.util.GraphUtil;

public class FlowFactory extends AbstractGModelFactory<Flow, GEdge> {

   public FlowFactory(final WorkflowModelState modelState) {
      super(modelState);
   }

   @Override
   public GEdge create(final Flow element) {
      if (element instanceof WeightedFlow) {
         return createWeightedFlowEdge((WeightedFlow) element);
      }
      return createFlowEdge(element);
   }

   protected GEdge createFlowEdge(final Flow flow) {
      String sourceId = toId(flow.getSource());
      String targetId = toId(flow.getTarget());

      GEdgeBuilder builder = new GEdgeBuilder()//
         .id(toId(flow))//
         .sourceId(sourceId)//
         .targetId(targetId)//
         .routerKind(GConstants.RouterKind.POLYLINE);

      modelState.getIndex().getNotation(flow, Edge.class).ifPresent(edge -> {
         if (edge.getBendPoints() != null) {
            ArrayList<GPoint> bendPoints = new ArrayList<>();
            edge.getBendPoints().forEach(p -> bendPoints.add(GraphUtil.copy(p)));
            builder.addRoutingPoints(bendPoints);
         }
      });
      return builder.build();
   }

   protected WeightedEdge createWeightedFlowEdge(final WeightedFlow flow) {
      String sourceId = toId(flow.getSource());
      String targetId = toId(flow.getTarget());

      WeightedEdgeBuilder builder = new WeightedEdgeBuilder()//
         .id(toId(flow))//
         .probability(flow.getProbability().getName())//
         .sourceId(sourceId)//
         .targetId(targetId).routerKind(GConstants.RouterKind.POLYLINE);

      modelState.getIndex().getNotation(flow, Edge.class).ifPresent(edge -> {
         if (edge.getBendPoints() != null) {
            ArrayList<GPoint> bendPoints = new ArrayList<>();
            edge.getBendPoints().forEach(p -> bendPoints.add(GraphUtil.copy(p)));
            builder.addRoutingPoints(bendPoints);
         }
      });
      return builder.build();
   }

}
