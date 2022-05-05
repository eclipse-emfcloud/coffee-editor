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

import org.eclipse.emfcloud.coffee.Task;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.model.WorkflowModelServerAccess;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.model.WorkflowModelState;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.util.ModelTypes;
import org.eclipse.emfcloud.modelserver.glsp.operations.handlers.EMSBasicOperationHandler;
import org.eclipse.glsp.graph.GLabel;
import org.eclipse.glsp.server.features.directediting.ApplyLabelEditOperation;
import org.eclipse.glsp.server.types.GLSPServerException;

public class WorkflowApplyLabelEditOperationHandler
   extends EMSBasicOperationHandler<ApplyLabelEditOperation, WorkflowModelServerAccess> {

   protected WorkflowModelState getWorkflowModelState() { return (WorkflowModelState) getEMSModelState(); }

   @Override
   public void executeOperation(final ApplyLabelEditOperation operation,
      final WorkflowModelServerAccess modelServerAccess) {

      String inputText = operation.getText().trim();
      String graphicalElementId = operation.getLabelId();
      GLabel label = getOrThrow(
         getWorkflowModelState().getIndex().findElementByClass(graphicalElementId, GLabel.class), GLabel.class,
         "Element with provided ID cannot be found or is not a GLabel");

      if (label.getType() == ModelTypes.LABEL_HEADING) {
         String elementId = graphicalElementId.replace("_classname", "");
         Task semanticElement = getOrThrow(getWorkflowModelState().getIndex().getSemantic(elementId), Task.class,
            "Could not find Task for id '" + elementId + "', no delete operation executed.");
         modelServerAccess.setTaskName(getWorkflowModelState(), semanticElement, inputText).thenAccept(response -> {
            if (!response.body()) {
               throw new GLSPServerException("Could not rename Task to: " + inputText);
            }
         });
      }
   }

}
