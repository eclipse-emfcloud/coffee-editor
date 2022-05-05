/********************************************************************************
 * Copyright (c) 2021-2022 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package org.eclipse.emfcloud.coffee.workflow.glsp.server.gmodel;

import org.eclipse.emfcloud.coffee.Node;
import org.eclipse.emfcloud.coffee.Task;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.model.CoffeeTypeUtil;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.model.WorkflowModelState;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.util.WorkflowBuilder.ActivityNodeBuilder;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.util.WorkflowBuilder.TaskNodeBuilder;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.ActivityNode;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.TaskNode;
import org.eclipse.emfcloud.modelserver.glsp.notation.Shape;
import org.eclipse.glsp.graph.GNode;
import org.eclipse.glsp.graph.builder.impl.GArguments;
import org.eclipse.glsp.graph.util.GraphUtil;

public class NodeFactory extends AbstractGModelFactory<Node, GNode> {

   public NodeFactory(final WorkflowModelState modelState) {
      super(modelState);
   }

   @Override
   public GNode create(final Node node) {
      if (node instanceof Task) {
         return createTaskNode((Task) node);
      }
      return createActivityNode(node);
   }

   private TaskNode createTaskNode(final Task task) {
      String type = CoffeeTypeUtil.toType(task);
      String nodeType = CoffeeTypeUtil.toNodeType(task);
      TaskNodeBuilder builder = new TaskNodeBuilder(type, task.getName(), nodeType, task.getDuration());
      builder.id(toId(task));

      modelState.getIndex().getNotation(task, Shape.class).ifPresent(shape -> {
         if (shape.getPosition() != null) {
            builder.position(GraphUtil.copy(shape.getPosition()));
         }
         if (shape.getSize() != null) {
            builder.size(GraphUtil.copy(shape.getSize()));
         }
      });
      builder.addArguments(GArguments.cornerRadius(5));

      return builder.build();
   }

   private ActivityNode createActivityNode(final Node node) {
      String type = CoffeeTypeUtil.toType(node);
      String nodeType = CoffeeTypeUtil.toNodeType(node);
      ActivityNodeBuilder builder = new ActivityNodeBuilder(type, nodeType);
      builder.id(toId(node));

      modelState.getIndex().getNotation(node, Shape.class).ifPresent(shape -> {
         if (shape.getPosition() != null) {
            builder.position(GraphUtil.copy(shape.getPosition()));
         }
         if (shape.getSize() != null) {
            builder.size(GraphUtil.copy(shape.getSize()));
         }
      });
      return builder.build();
   }

}
