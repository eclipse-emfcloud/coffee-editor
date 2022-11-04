/********************************************************************************
 * Copyright (c) 2022 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package org.eclipse.emfcloud.coffee.workflow.glsp.server.taskedit;

import org.eclipse.glsp.server.features.directediting.ContextEditValidator;
import org.eclipse.glsp.server.features.directediting.RequestEditValidationAction;
import org.eclipse.glsp.server.features.directediting.ValidationStatus;
import org.eclipse.glsp.server.model.GModelState;

import com.google.inject.Inject;

public class TaskEditValidator implements ContextEditValidator {

   @Override
   public String getContextId() { return "task-editor"; }

   @Inject
   protected GModelState modelState;

   @SuppressWarnings("checkstyle:cyclomaticComplexity")
   @Override
   public ValidationStatus validate(final RequestEditValidationAction action) {
      String text = action.getText();
      if (text.startsWith(TaskEditContextActionProvider.DURATION_PREFIX)) {
         String durationString = text.substring(TaskEditContextActionProvider.DURATION_PREFIX.length());
         try {
            int duration = Integer.parseInt(durationString);
            if (duration < 0 || duration > 100) {
               return ValidationStatus.warning("'" + durationString + "' should be between 0 and 100.");
            }
         } catch (NumberFormatException e) {
            return ValidationStatus.error("'" + durationString + "' is not a valid number.");
         }
      }
      return ValidationStatus.ok();
   }

}
