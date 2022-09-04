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
package org.eclipse.emfcloud.coffee.workflow.glsp.server.taskedit;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.TaskNode;
import org.eclipse.glsp.server.features.contextactions.ContextActionsProvider;
import org.eclipse.glsp.server.features.contextactions.SetAutoCompleteValueAction;
import org.eclipse.glsp.server.features.directediting.LabeledAction;
import org.eclipse.glsp.server.model.GModelState;
import org.eclipse.glsp.server.types.EditorContext;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

public class TaskEditContextActionProvider implements ContextActionsProvider {

   public static final String DURATION_PREFIX = "duration:";

   @Override
   public String getContextId() { return "task-editor"; }

   @Inject
   protected GModelState modelState;

   @Override
   public List<? extends LabeledAction> getActions(final EditorContext editorContext) {
      String text = editorContext.getArgs().getOrDefault("text", "");
      Optional<TaskNode> taskNode = modelState.getIndex()
         .findElementByClass(editorContext.getSelectedElementIds().get(0), TaskNode.class);
      if (taskNode.isEmpty()) {
         return Collections.emptyList();
      }
      if (text.startsWith(DURATION_PREFIX)) {
         return Collections.emptyList();
      }
      int duration = taskNode.get().getDuration();
      return Lists.newArrayList(
         new SetAutoCompleteValueAction(DURATION_PREFIX, "", DURATION_PREFIX + duration));
   }

}
