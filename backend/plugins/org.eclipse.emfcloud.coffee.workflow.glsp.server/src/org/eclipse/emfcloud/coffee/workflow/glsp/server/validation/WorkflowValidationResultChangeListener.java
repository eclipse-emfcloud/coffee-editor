/*******************************************************************************
 * Copyright (c) 2021-2022 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ******************************************************************************/
package org.eclipse.emfcloud.coffee.workflow.glsp.server.validation;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emfcloud.validation.framework.ValidationResult;
import org.eclipse.emfcloud.validation.framework.ValidationResultChangeListener;
import org.eclipse.glsp.server.actions.ActionDispatcher;
import org.eclipse.glsp.server.actions.ActionMessage;
import org.eclipse.glsp.server.features.validation.Marker;
import org.eclipse.glsp.server.features.validation.MarkerKind;
import org.eclipse.glsp.server.features.validation.SetMarkersAction;

public class WorkflowValidationResultChangeListener extends ValidationResultChangeListener {

   private final String clientId;

   private final ActionDispatcher actionDispatcher;

   @Override
   public void changed(final List<ValidationResult> newResult) {
      actionDispatcher
         .dispatch(new ActionMessage(this.clientId, new SetMarkersAction(createMarkers(newResult))).getAction());
   }

   public WorkflowValidationResultChangeListener(final String clientId, final ActionDispatcher actionDispatcher) {
      this.clientId = clientId;
      this.actionDispatcher = actionDispatcher;
   }

   private List<Marker> createMarkers(final List<ValidationResult> validationResult) {
      List<Marker> markers = new ArrayList<>();
      for (ValidationResult r : validationResult) {
         BasicDiagnostic diagnostic = r.getDiagnostic();
         String message = diagnostic.getMessage();
         markers.add(new Marker(message, message, r.getIdentifier(), getMarkerKind(diagnostic.getSeverity())));
      }
      return markers;
   }

   private String getMarkerKind(final int severity) {
      switch (severity) {
         case Diagnostic.ERROR:
            return MarkerKind.ERROR;
         case Diagnostic.WARNING:
            return MarkerKind.WARNING;
         default:
            return MarkerKind.INFO;
      }
   }

}
