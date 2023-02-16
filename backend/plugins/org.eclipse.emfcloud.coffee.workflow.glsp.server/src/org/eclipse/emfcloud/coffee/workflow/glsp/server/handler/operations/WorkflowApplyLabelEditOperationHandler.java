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

import org.eclipse.emfcloud.coffee.Task;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.WorkflowModelServerAccess;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.WorkflowModelTypes;
import org.eclipse.emfcloud.modelserver.glsp.notation.integration.EMSNotationModelState;
import org.eclipse.emfcloud.modelserver.glsp.operations.handlers.EMSOperationHandler;
import org.eclipse.glsp.graph.GLabel;
import org.eclipse.glsp.server.features.directediting.ApplyLabelEditOperation;
import org.eclipse.glsp.server.types.GLSPServerException;

import com.google.inject.Inject;

public class WorkflowApplyLabelEditOperationHandler extends EMSOperationHandler<ApplyLabelEditOperation> {

   @Inject
   protected EMSNotationModelState modelState;
   @Inject
   protected WorkflowModelServerAccess modelServerAccess;

   @Override
   public void executeOperation(final ApplyLabelEditOperation operation) {

      String inputText = operation.getText().trim();
      String graphicalElementId = operation.getLabelId();
      GLabel label = getOrThrow(
         modelState.getIndex().findElementByClass(graphicalElementId, GLabel.class), GLabel.class,
         "Element with provided ID cannot be found or is not a GLabel");

      if (label.getType() == WorkflowModelTypes.LABEL_HEADING) {
         String elementId = graphicalElementId.replace("_classname", "");
         Task semanticElement = getOrThrow(modelState.getIndex().getEObject(elementId), Task.class,
            "Could not find Task for id '" + elementId + "', no delete operation executed.");
         modelServerAccess.setTaskName(semanticElement, inputText)
            .thenAccept(response -> {
               if (response.body() == null || response.body().isEmpty()) {
                  throw new GLSPServerException("Could not rename Task to: " + inputText);
               }
            });
      }
   }

}
