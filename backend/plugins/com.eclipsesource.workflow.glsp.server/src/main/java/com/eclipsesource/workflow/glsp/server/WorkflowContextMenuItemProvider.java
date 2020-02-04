/********************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License v. 2.0 are satisfied: GNU General Public License, version 2
 * with the GNU Classpath Exception which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 ********************************************************************************/
package com.eclipsesource.workflow.glsp.server;

import static com.eclipsesource.workflow.glsp.server.util.ModelTypes.AUTOMATED_TASK;
import static com.eclipsesource.workflow.glsp.server.util.ModelTypes.MANUAL_TASK;
import static org.eclipse.glsp.graph.util.GraphUtil.point;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.glsp.api.action.kind.CreateNodeOperationAction;
import org.eclipse.glsp.api.model.GraphicalModelState;
import org.eclipse.glsp.api.provider.ContextMenuItemProvider;
import org.eclipse.glsp.api.types.MenuItem;
import org.eclipse.glsp.graph.GPoint;

import com.google.common.collect.Lists;

public class WorkflowContextMenuItemProvider implements ContextMenuItemProvider {

   @Override
   public List<MenuItem> getItems(final GraphicalModelState modelState, final List<String> selectedElementIds,
      final Optional<GPoint> position, final Map<String, String> args) {
      MenuItem newAutTask = new MenuItem("newAutoTask", "Automated Task",
         Arrays.asList(new CreateNodeOperationAction(AUTOMATED_TASK, position.orElse(point(0, 0)))), true);
      MenuItem newManTask = new MenuItem("newManualTask", "Manual Task",
         Arrays.asList(new CreateNodeOperationAction(MANUAL_TASK, position.orElse(point(0, 0)))), true);
      MenuItem newChildMenu = new MenuItem("new", "New", Arrays.asList(newAutTask, newManTask), "add", "0_new");
      return Lists.newArrayList(newChildMenu);
   }

}