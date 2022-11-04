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
package org.eclipse.emfcloud.coffee.workflow.glsp.server.provider;

import static org.eclipse.emfcloud.coffee.workflow.glsp.server.WorkflowModelTypes.AUTOMATED_TASK;
import static org.eclipse.emfcloud.coffee.workflow.glsp.server.WorkflowModelTypes.MANUAL_TASK;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.eclipse.glsp.graph.GPoint;
import org.eclipse.glsp.server.features.contextmenu.ContextMenuItemProvider;
import org.eclipse.glsp.server.features.contextmenu.MenuItem;
import org.eclipse.glsp.server.model.GModelState;
import org.eclipse.glsp.server.operations.CreateNodeOperation;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

public class WorkflowContextMenuItemProvider implements ContextMenuItemProvider {

   @Inject
   protected GModelState modelState;

   @Override
   public List<MenuItem> getItems(final List<String> selectedElementIds, final GPoint position,
      final Map<String, String> args) {
      MenuItem newAutTask = new MenuItem("newAutoTask", "Automated Task",
         Arrays.asList(new CreateNodeOperation(AUTOMATED_TASK, position)), true);
      MenuItem newManTask = new MenuItem("newManualTask", "Manual Task",
         Arrays.asList(new CreateNodeOperation(MANUAL_TASK, position)), true);
      MenuItem newChildMenu = new MenuItem("new", "New", Arrays.asList(newAutTask, newManTask), "add", "0_new");
      return Lists.newArrayList(newChildMenu);
   }
}
