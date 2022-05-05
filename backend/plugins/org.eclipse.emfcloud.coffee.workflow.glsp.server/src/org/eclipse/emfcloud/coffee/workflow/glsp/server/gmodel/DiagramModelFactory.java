/*******************************************************************************
 * Copyright (c) 2021 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ******************************************************************************/
package org.eclipse.emfcloud.coffee.workflow.glsp.server.gmodel;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emfcloud.coffee.Workflow;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.model.WorkflowModelState;
import org.eclipse.emfcloud.modelserver.glsp.notation.Diagram;
import org.eclipse.glsp.graph.GGraph;
import org.eclipse.glsp.graph.GModelElement;

public class DiagramModelFactory extends GModelFactory {

   public DiagramModelFactory(final WorkflowModelState modelState) {
      super(modelState);
   }

   @Override
   public GGraph create(final Diagram notationDiagram) {
      GGraph graph = getOrCreateRoot();

      if (notationDiagram.getSemanticElement().getResolvedElement() != null) {
         Workflow workflowModel = (Workflow) notationDiagram.getSemanticElement().getResolvedElement();

         graph.setId(toId(workflowModel));

         // Add Nodes
         List<GModelElement> nodeElements = workflowModel.getNodes().stream().map(node -> nodeFactory.create(node))
            .collect(Collectors.toList());
         graph.getChildren().addAll(nodeElements);

         // Add Flows
         List<GModelElement> flowElements = workflowModel.getFlows().stream().map(flow -> flowFactory.create(flow))
            .collect(Collectors.toList());
         graph.getChildren().addAll(flowElements);

      }
      return graph;

   }

}
