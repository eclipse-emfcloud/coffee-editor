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

import org.eclipse.emfcloud.coffee.workflow.glsp.server.WorkflowModelServerAccess;
import org.eclipse.emfcloud.modelserver.glsp.notation.integration.EMSNotationSourceModelStorage;

import com.google.inject.Inject;

public class WorkflowSourceModelStorage extends EMSNotationSourceModelStorage {

   @Inject
   protected WorkflowModelServerAccess modelServerAccess;

   @Override
   protected void doSubscribe() {
      // FIXME @sgraband dealing with highlights:
      //
      // if (action.getOptions().get("highlights") != null) {
      // HashMap<String, String> map = new Gson().fromJson(action.getOptions().get("highlights"), HashMap.class);
      // for (Entry<String, String> entry : map.entrySet()) {
      // modelState.addHighlight(entry);
      // }
      // } else { ..Subscribe .. }

      super.doSubscribe();
      modelServerAccess.createValidationFramework(modelState.getClientId(), actionDispatcher);
      modelServerAccess.subscribeToValidation();
      modelServerAccess.initConstraintList();
   }
}
