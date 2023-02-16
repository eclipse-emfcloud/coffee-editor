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

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import org.eclipse.emfcloud.coffee.workflow.glsp.server.WorkflowModelServerAccess;
import org.eclipse.emfcloud.modelserver.client.Response;
import org.eclipse.emfcloud.modelserver.glsp.operations.handlers.EMSCreateOperationHandler;
import org.eclipse.glsp.graph.GPoint;
import org.eclipse.glsp.server.operations.CreateNodeOperation;
import org.eclipse.glsp.server.types.GLSPServerException;

import com.google.inject.Inject;

public abstract class AbstractCreateNodeHandler extends EMSCreateOperationHandler<CreateNodeOperation> {

   @Inject
   protected WorkflowModelServerAccess modelAccess;

   public AbstractCreateNodeHandler(final String type) {
      super(type);
   }

   @Override
   public void executeOperation(final CreateNodeOperation operation) {
      getNodeCreator().apply(operation.getLocation())
         .thenAccept(response -> {
            if (response.body() == null || response.body().isEmpty()) {
               throw new GLSPServerException("Could not execute CreateOperation on new Task Node");
            }
         });
   }

   protected abstract Function<Optional<GPoint>, CompletableFuture<Response<String>>> getNodeCreator();

}
