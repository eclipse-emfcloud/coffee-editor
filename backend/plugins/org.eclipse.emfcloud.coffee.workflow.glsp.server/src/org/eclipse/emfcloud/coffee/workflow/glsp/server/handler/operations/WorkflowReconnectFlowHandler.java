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
package org.eclipse.emfcloud.coffee.workflow.glsp.server.handler.operations;

import static org.eclipse.glsp.server.types.GLSPServerException.getOrThrow;

import org.eclipse.emfcloud.coffee.Flow;
import org.eclipse.emfcloud.coffee.Node;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.WorkflowModelServerAccess;
import org.eclipse.emfcloud.modelserver.glsp.notation.integration.EMSNotationModelState;
import org.eclipse.emfcloud.modelserver.glsp.operations.handlers.EMSOperationHandler;
import org.eclipse.glsp.server.emf.EMFIdGenerator;
import org.eclipse.glsp.server.operations.ReconnectEdgeOperation;

import com.google.inject.Inject;

public class WorkflowReconnectFlowHandler extends EMSOperationHandler<ReconnectEdgeOperation> {

   @Inject
   protected EMSNotationModelState modelState;
   @Inject
   protected WorkflowModelServerAccess modelServerAccess;
   @Inject
   protected EMFIdGenerator idGenerator;

   @Override
   @SuppressWarnings("checkstyle:CyclomaticComplexity")
   public void executeOperation(final ReconnectEdgeOperation operation) {

      if (operation.getEdgeElementId() == null || operation.getSourceElementId() == null
         || operation.getTargetElementId() == null) {
         throw new IllegalArgumentException("Incomplete reconnect flow action");
      }

      String modelId = idGenerator.getOrCreateId(modelState.getSemanticModel());
      if (operation.getSourceElementId().equals(modelId) || operation.getTargetElementId().equals(modelId)) {
         // client tool failure, do nothing
         return;
      }

      Flow flow = getOrThrow(modelState.getIndex().getEObject(operation.getEdgeElementId()), Flow.class,
         "Could not find Flow for id '" + operation.getEdgeElementId()
            + "', no reconnecting operation executed.");

      if (!operation.getSourceElementId().equals(idGenerator.getOrCreateId(flow.getSource()))) {
         Node newSource = getOrThrow(modelState.getIndex().getEObject(operation.getSourceElementId()), Node.class,
            "Could not find Node for id '" + operation.getSourceElementId()
               + "', no reconnecting operation executed.");
         modelServerAccess.reconnectFlowSource(flow, newSource);

      } else if (!operation.getTargetElementId().equals(idGenerator.getOrCreateId(flow.getTarget()))) {
         Node newTarget = getOrThrow(modelState.getIndex().getEObject(operation.getTargetElementId()), Node.class,
            "Could not find Node for id '" + operation.getTargetElementId()
               + "', no reconnecting operation executed.");
         modelServerAccess.reconnectFlowTarget(flow, newTarget);
      }
   }

}
