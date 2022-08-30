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
package org.eclipse.emfcloud.coffee.workflow.glsp.server.handler.actions;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.eclipse.emfcloud.coffee.workflow.glsp.server.WorkflowModelServerAccess;
import org.eclipse.emfcloud.modelserver.glsp.actions.handlers.AbstractEMSActionHandler;
import org.eclipse.glsp.server.actions.Action;
import org.eclipse.glsp.server.features.validation.RequestMarkersAction;

import com.google.inject.Inject;

public class WorkflowRequestMarkersActionHandler extends AbstractEMSActionHandler<RequestMarkersAction> {

   @Inject
   protected WorkflowModelServerAccess modelServerAccess;

   @Override
   public List<Action> executeAction(final RequestMarkersAction action) {
      try {
         modelServerAccess.validateViaFramework().join();
      } catch (IOException | InterruptedException | ExecutionException e) {
         e.printStackTrace();
      }
      return listOf();
   }
}
