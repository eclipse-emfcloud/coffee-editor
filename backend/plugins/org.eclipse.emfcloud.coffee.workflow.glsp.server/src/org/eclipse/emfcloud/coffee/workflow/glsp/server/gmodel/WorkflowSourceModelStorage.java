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
package org.eclipse.emfcloud.coffee.workflow.glsp.server.gmodel;

import java.util.HashMap;
import java.util.Map.Entry;

import org.eclipse.emfcloud.coffee.workflow.glsp.server.WorkflowHighlightStore;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.WorkflowModelServerAccess;
import org.eclipse.emfcloud.modelserver.glsp.notation.integration.EMSNotationSourceModelStorage;
import org.eclipse.glsp.server.features.core.model.RequestModelAction;

import com.google.gson.Gson;
import com.google.inject.Inject;

public class WorkflowSourceModelStorage extends EMSNotationSourceModelStorage {

   @Inject
   protected WorkflowModelServerAccess modelServerAccess;

   @Inject
   protected WorkflowHighlightStore highlightStore;

   @Override
   public void loadSourceModel(final RequestModelAction action) {
      if (action.getOptions().get("highlights") != null) {
         HashMap<String, String> map = new Gson().fromJson(action.getOptions().get("highlights"), HashMap.class);
         for (Entry<String, String> entry : map.entrySet()) {
            this.highlightStore.addHighlight(entry);
         }
         // Do not subscribe as we are in the comparison view
         doLoadSourceModel();
      } else {
         doLoadSourceModel();
         doSubscribe();
      }
   }

   @Override
   protected void doSubscribe() {
      super.doSubscribe();
      modelServerAccess.createValidationFramework(modelState.getClientId(), actionDispatcher);
      modelServerAccess.subscribeToValidation();
      modelServerAccess.initConstraintList();
   }
}
