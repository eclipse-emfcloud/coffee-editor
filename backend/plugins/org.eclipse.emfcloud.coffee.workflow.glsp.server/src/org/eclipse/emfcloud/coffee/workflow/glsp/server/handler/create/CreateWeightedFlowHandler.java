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

import java.util.concurrent.CompletableFuture;

import org.eclipse.emfcloud.coffee.workflow.glsp.server.WorkflowModelServerAccess;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.WorkflowModelTypes;
import org.eclipse.emfcloud.modelserver.client.Response;

public class CreateWeightedFlowHandler extends AbstractCreateEdgeHandler {

   public CreateWeightedFlowHandler() {
      super(WorkflowModelTypes.WEIGHTED_EDGE);
   }

   @Override
   public String getLabel() { return "Weighted Edge"; }

   @Override
   protected CompletableFuture<Response<String>> addFlow(final WorkflowModelServerAccess modelAccess,
      final String sourceId, final String targetId) {
      return modelAccess.addWeightedFlow(sourceId, targetId);
   }

}
