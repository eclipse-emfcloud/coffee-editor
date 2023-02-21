/*******************************************************************************
 * Copyright (c) 2019-2023 EclipseSource and others.
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
import org.eclipse.emfcloud.modelserver.client.Response;
import org.eclipse.emfcloud.modelserver.glsp.operations.handlers.EMSCreateOperationHandler;
import org.eclipse.glsp.server.operations.CreateEdgeOperation;
import org.eclipse.glsp.server.types.GLSPServerException;

import com.google.inject.Inject;

public abstract class AbstractCreateEdgeHandler extends EMSCreateOperationHandler<CreateEdgeOperation> {

   @Inject
   protected WorkflowModelServerAccess modelAccess;

   public AbstractCreateEdgeHandler(final String type) {
      super(type);
   }

   @Override
   public void executeOperation(final CreateEdgeOperation operation) {
      String sourceId = operation.getSourceElementId();
      String targetId = operation.getTargetElementId();

      addFlow(modelAccess, sourceId, targetId)
         .thenAccept(response -> {
            if (response.body() == null || response.body().isEmpty()) {
               throw new GLSPServerException("Could not execute CreateOperation on new Flow Edge");
            }
         });
   }

   protected abstract CompletableFuture<Response<String>> addFlow(WorkflowModelServerAccess modelAccess,
      String sourceId, String targetId);
}
