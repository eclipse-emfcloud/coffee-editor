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
package org.eclipse.emfcloud.coffee.workflow.glsp.server.handler.operation;

import static org.eclipse.glsp.server.types.GLSPServerException.getOrThrow;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emfcloud.coffee.Flow;
import org.eclipse.emfcloud.coffee.Node;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.model.WorkflowModelServerAccess;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.model.WorkflowModelState;
import org.eclipse.emfcloud.modelserver.glsp.operations.handlers.EMSBasicOperationHandler;
import org.eclipse.glsp.server.operations.DeleteOperation;
import org.eclipse.glsp.server.types.GLSPServerException;

public class WorkflowDeleteOperationHandler
   extends EMSBasicOperationHandler<DeleteOperation, WorkflowModelServerAccess> {

   protected WorkflowModelState getWorkflowModelState() { return (WorkflowModelState) getEMSModelState(); }

   @Override
   public void executeOperation(final DeleteOperation operation, final WorkflowModelServerAccess modelServerAccess) {

      WorkflowModelState modelState = getWorkflowModelState();

      operation.getElementIds().forEach(elementId -> {
         EObject semanticElement = getOrThrow(modelState.getIndex().getSemantic(elementId), EObject.class,
            "Could not find element for id '" + elementId + "', no delete operation executed.");

         if (semanticElement instanceof Node) {
            modelServerAccess.removeNode(modelState, (Node) semanticElement).thenAccept(response -> {
               if (!response.body()) {
                  throw new GLSPServerException(
                     "Could not execute delete operation on Node: " + semanticElement.toString());
               }
            });
         } else if (semanticElement instanceof Flow) {
            modelServerAccess.removeFlow(modelState, (Flow) semanticElement).thenAccept(response -> {
               if (!response.body()) {
                  throw new GLSPServerException(
                     "Could not execute delete operation on Flow: " + semanticElement.toString());
               }
            });
         }
      });

   }

}
