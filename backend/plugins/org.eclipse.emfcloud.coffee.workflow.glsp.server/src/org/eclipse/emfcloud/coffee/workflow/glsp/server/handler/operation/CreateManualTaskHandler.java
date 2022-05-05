/*******************************************************************************
 * Copyright (c) 2019-2021 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ******************************************************************************/
package org.eclipse.emfcloud.coffee.workflow.glsp.server.handler.operation;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;

import org.eclipse.emfcloud.coffee.workflow.glsp.server.model.WorkflowModelServerAccess;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.model.WorkflowModelState;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.util.ModelTypes;
import org.eclipse.emfcloud.modelserver.client.Response;
import org.eclipse.glsp.graph.GPoint;

public class CreateManualTaskHandler extends AbstractCreateNodeHandler {

   public CreateManualTaskHandler() {
      super(ModelTypes.MANUAL_TASK);
   }

   @Override
   public String getLabel() { return "Manual Task"; }

   @Override
   protected BiFunction<WorkflowModelState, Optional<GPoint>, CompletableFuture<Response<Boolean>>> getNodeCreator(
      final WorkflowModelServerAccess modelAccess) {
      return modelAccess::addManualTask;
   }

}
