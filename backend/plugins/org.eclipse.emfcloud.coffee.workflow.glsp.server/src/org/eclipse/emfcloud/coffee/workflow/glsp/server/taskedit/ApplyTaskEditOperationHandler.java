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

import org.eclipse.emfcloud.modelserver.glsp.operations.handlers.EMSOperationHandler;
import org.eclipse.glsp.server.actions.ActionDispatcher;
import org.eclipse.glsp.server.types.GLSPServerException;

import com.google.inject.Inject;

public class ApplyTaskEditOperationHandler extends EMSOperationHandler<ApplyTaskEditOperation> {

   @Inject
   protected ActionDispatcher actionDispatcher;

   @Override
   protected void executeOperation(final ApplyTaskEditOperation operation) {
      String text = operation.getExpression();
      if (text.startsWith(TaskEditContextActionProvider.DURATION_PREFIX)) {
         String durationString = text.substring(TaskEditContextActionProvider.DURATION_PREFIX.length());
         actionDispatcher.dispatch(new EditTaskOperation(operation.getTaskId(), "duration", durationString));
      } else {
         throw new GLSPServerException(
            "Cannot process 'ApplyTaskEditOperation' expression: " + operation.getExpression());
      }
   }

}
