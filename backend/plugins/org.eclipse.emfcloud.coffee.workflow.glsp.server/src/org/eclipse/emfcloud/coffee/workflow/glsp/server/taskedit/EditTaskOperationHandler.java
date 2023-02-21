/********************************************************************************
 * Copyright (c) 2022-2023 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package org.eclipse.emfcloud.coffee.workflow.glsp.server.taskedit;

import static org.eclipse.glsp.server.types.GLSPServerException.getOrThrow;

import org.eclipse.emfcloud.coffee.Task;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.WorkflowModelServerAccess;
import org.eclipse.emfcloud.modelserver.glsp.operations.handlers.EMSOperationHandler;
import org.eclipse.glsp.server.types.GLSPServerException;

import com.google.inject.Inject;

public class EditTaskOperationHandler extends EMSOperationHandler<EditTaskOperation> {

   @Inject
   protected WorkflowModelServerAccess modelServerAccess;

   @Override
   protected void executeOperation(final EditTaskOperation operation) {
      Task task = getOrThrow(modelState.getIndex().getEObject(operation.getTaskId()), Task.class,
         "Could not find Task for id '" + operation.getTaskId() + "', no delete operation executed.");
      switch (operation.getFeature()) {
         case "duration":
            modelServerAccess.setTaskDuration(task, Integer.parseInt(operation.getValue()));
            break;
         default:
            throw new GLSPServerException("Cannot edit task at feature '" + operation.getFeature() + "'");
      }
   }

}
