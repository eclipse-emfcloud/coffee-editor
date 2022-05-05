/*******************************************************************************
 * Copyright (c) 2019-2022 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ******************************************************************************/
package org.eclipse.emfcloud.coffee.workflow.analyzer.coffee;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.google.gson.Gson;

import workflowanalyzer.Decision;
import workflowanalyzer.Node;
import workflowanalyzer.NodeExecutionIterator;
import workflowanalyzer.Task;
import workflowanalyzer.Workflow;

public class WorkflowAnalysisGeneric {

   private class TaskElement {
      @SuppressWarnings({ "unused", "checkstyle:VisibilityModifier" })
      String name, type;

      @SuppressWarnings("checkstyle:VisibilityModifier")
      int size;

      @SuppressWarnings("checkstyle:VisibilityModifier")
      List<TaskElement> children = new ArrayList<>();

      TaskElement(final String name, final String type, final int size) {
         super();
         this.name = name;
         this.type = type;
         this.size = size;
      }
   }

   private final Map<Task, String> taskTypeMap = new HashMap<>();

   public void addTask(final Task task, final String type) {
      taskTypeMap.put(task, type);
   }

   private Task getFirstTask(final Workflow workflow) {
      return (Task) workflow.getNodes().stream()
         .filter((node) -> node instanceof Task && node.getIncoming().isEmpty()).findFirst().get();
   }

   public String generateAnalysisDataAsJson(final List<Node> nodes) {
      final TaskElement rootTask = new TaskElement("Root", "", 1000);
      buildTaskElementHierarchy(getFirstTask(new Workflow(nodes)), rootTask, 1f);
      return new Gson().toJson(rootTask);
   }

   public InputStream generateAnalysisData(final List<Node> nodes) {
      String json = generateAnalysisDataAsJson(nodes);
      return new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
   }

   private void buildTaskElementHierarchy(final Node node, final TaskElement rootTaskElement,
      final float probability) {
      TaskElement currentTaskElement = rootTaskElement;
      int size = Math.round(currentTaskElement.size * probability);
      NodeExecutionIterator iterator = node.getNodeExecutionIterator();
      while (iterator.hasNext()) {
         final Node next = iterator.next();
         if (next instanceof Task) {
            TaskElement taskElement = createTaskElement((Task) next, size);
            currentTaskElement.children.add(taskElement);
            currentTaskElement = taskElement;
         } else if (next instanceof Decision) {
            for (Node nodeAfterDecision : next.getOutgoing()) {
               buildTaskElementHierarchy(nodeAfterDecision, currentTaskElement,
                  nodeAfterDecision.getProbabilityInBranch());
            }
            return;
         }
      }
   }

   private TaskElement createTaskElement(final Task task, final int probability) {
      String type = Optional.ofNullable(taskTypeMap.get(task)).orElse("");
      TaskElement taskElement = new TaskElement(task.getName(), type, probability);
      return taskElement;
   }
}
