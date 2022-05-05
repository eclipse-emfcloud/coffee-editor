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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emfcloud.coffee.workflow.glsp.server.model.WorkflowModelServerAccess;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.model.WorkflowModelState;
import org.eclipse.emfcloud.modelserver.glsp.notation.Edge;
import org.eclipse.emfcloud.modelserver.glsp.operations.handlers.EMSBasicOperationHandler;
import org.eclipse.glsp.server.operations.ChangeRoutingPointsOperation;
import org.eclipse.glsp.server.types.ElementAndRoutingPoints;

public class WorkflowChangeRoutingPointsOperationHandler
   extends EMSBasicOperationHandler<ChangeRoutingPointsOperation, WorkflowModelServerAccess> {

   protected WorkflowModelState getWorkflowModelState() { return (WorkflowModelState) getEMSModelState(); }

   @Override
   public void executeOperation(final ChangeRoutingPointsOperation operation,
      final WorkflowModelServerAccess modelServerAccess) {

      Map<Edge, ElementAndRoutingPoints> changeRoutingPointsMap = new HashMap<>();
      for (ElementAndRoutingPoints element : operation.getNewRoutingPoints()) {
         getWorkflowModelState().getIndex().getNotation(element.getElementId(), Edge.class)
            .ifPresent(notationElement -> {
               changeRoutingPointsMap.put(notationElement, element);
            });
      }
      modelServerAccess.changeRoutingPoints(changeRoutingPointsMap);
   }

}
