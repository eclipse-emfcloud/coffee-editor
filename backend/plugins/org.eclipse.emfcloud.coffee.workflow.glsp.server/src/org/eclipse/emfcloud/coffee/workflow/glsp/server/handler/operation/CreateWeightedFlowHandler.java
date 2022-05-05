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

import java.util.concurrent.CompletableFuture;

import org.eclipse.emfcloud.coffee.Node;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.model.WorkflowModelServerAccess;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.model.WorkflowModelState;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.util.ModelTypes;
import org.eclipse.emfcloud.modelserver.client.Response;

public class CreateWeightedFlowHandler extends AbstractCreateEdgeHandler {

   public CreateWeightedFlowHandler() {
      super(ModelTypes.WEIGHTED_EDGE);
   }

   @Override
   public String getLabel() { return "Weighted Edge"; }

   @Override
   protected CompletableFuture<Response<Boolean>> addFlow(final WorkflowModelServerAccess modelAccess,
      final WorkflowModelState modelState, final Node source, final Node target) {
      return modelAccess.addWeightedFlow(modelState, source, target);
   }

}
