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
package org.eclipse.emfcloud.coffee.workflow.glsp.server.handler.create;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import org.eclipse.emfcloud.coffee.workflow.glsp.server.WorkflowModelTypes;
import org.eclipse.emfcloud.modelserver.client.Response;
import org.eclipse.glsp.graph.GPoint;

public class CreateAutomatedTaskHandler extends AbstractCreateNodeHandler {

   public CreateAutomatedTaskHandler() {
      super(WorkflowModelTypes.AUTOMATED_TASK);
   }

   @Override
   public String getLabel() { return "Automated Task"; }

   @Override
   protected Function<Optional<GPoint>, CompletableFuture<Response<String>>> getNodeCreator() {
      return modelAccess::addAutomatedTask;
   }

}
