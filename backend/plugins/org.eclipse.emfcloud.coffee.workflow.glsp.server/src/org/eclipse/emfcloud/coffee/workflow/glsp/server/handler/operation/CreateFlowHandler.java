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
import org.eclipse.emfcloud.modelserver.client.Response;
import org.eclipse.glsp.graph.DefaultTypes;

public class CreateFlowHandler extends AbstractCreateEdgeHandler {

   public CreateFlowHandler() {
      super(DefaultTypes.EDGE);
   }

   @Override
   public String getLabel() { return "Flow"; }

   @Override
   protected CompletableFuture<Response<Boolean>> addFlow(final WorkflowModelServerAccess modelAccess,
      final WorkflowModelState modelState, final Node source, final Node target) {
      return modelAccess.addFlow(modelState, source, target);
   }

}
