/********************************************************************************
 * Copyright (c) 2022 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package org.eclipse.emfcloud.coffee.workflow.glsp.server.palette;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.WorkflowModelTypes;
import org.eclipse.glsp.graph.DefaultTypes;
import org.eclipse.glsp.server.actions.TriggerEdgeCreationAction;
import org.eclipse.glsp.server.actions.TriggerNodeCreationAction;
import org.eclipse.glsp.server.features.toolpalette.PaletteItem;
import org.eclipse.glsp.server.features.toolpalette.ToolPaletteItemProvider;

import com.google.common.collect.Lists;

public class WorkflowToolPaletteItemProvider implements ToolPaletteItemProvider {

   private static Logger LOGGER = LogManager.getLogger(WorkflowToolPaletteItemProvider.class.getSimpleName());

   @Override
   public List<PaletteItem> getItems(final Map<String, String> args) {
      LOGGER.info("Create palette");
      return Lists.newArrayList(tasks(), nodes(), edges());
   }

   private PaletteItem tasks() {
      PaletteItem createAutomatedTask = node(WorkflowModelTypes.AUTOMATED_TASK, "Automated Task", "settings-gear");
      PaletteItem createManualTask = node(WorkflowModelTypes.MANUAL_TASK, "Manual Task", "account");

      List<PaletteItem> nodes = Lists.newArrayList(createAutomatedTask, createManualTask);
      return PaletteItem.createPaletteGroup("task-group", "Tasks", nodes, "symbol-property", "a");
   }

   private PaletteItem nodes() {
      PaletteItem createDecisionNode = node(WorkflowModelTypes.DECISION_NODE, "Decision Node", "chevron-up");
      PaletteItem createMergeNode = node(WorkflowModelTypes.MERGE_NODE, "Merge Node", "chevron-down");

      List<PaletteItem> nodes = Lists.newArrayList(createDecisionNode, createMergeNode);
      return PaletteItem.createPaletteGroup("node-group", "Nodes", nodes, "symbol-property", "b");
   }

   private PaletteItem edges() {
      PaletteItem createFlow = edge(DefaultTypes.EDGE, "Flow", "chrome-minimize");
      PaletteItem createWeightedFlow = edge(WorkflowModelTypes.WEIGHTED_EDGE, "Weighted Flow", "grabber");

      List<PaletteItem> edges = Lists.newArrayList(createFlow, createWeightedFlow);
      return PaletteItem.createPaletteGroup("edge-group", "Edges", edges, "symbol-property", "c");
   }

   private PaletteItem node(final String elementTypeId, final String label, final String icon) {
      return new PaletteItem(elementTypeId, label, new TriggerNodeCreationAction(elementTypeId), icon);
   }

   private PaletteItem edge(final String elementTypeId, final String label, final String icon) {
      return new PaletteItem(elementTypeId, label, new TriggerEdgeCreationAction(elementTypeId), icon);
   }
}
