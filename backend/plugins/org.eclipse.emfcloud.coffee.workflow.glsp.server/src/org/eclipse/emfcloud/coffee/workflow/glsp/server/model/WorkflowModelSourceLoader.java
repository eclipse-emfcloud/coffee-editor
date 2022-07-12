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
package org.eclipse.emfcloud.coffee.workflow.glsp.server.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.eclipse.emfcloud.modelserver.client.v1.ModelServerClientV1;
import org.eclipse.emfcloud.modelserver.glsp.model.EMSModelSourceLoader;
import org.eclipse.glsp.server.features.core.model.RequestModelAction;
import org.eclipse.glsp.server.model.GModelState;
import org.eclipse.glsp.server.types.GLSPServerException;
import org.eclipse.glsp.server.utils.ClientOptionsUtil;

import com.google.gson.Gson;

public class WorkflowModelSourceLoader extends EMSModelSourceLoader {

   private static Logger LOGGER = Logger.getLogger(EMSModelSourceLoader.class.getSimpleName());

   @Override
   public void loadSourceModel(final RequestModelAction action) {
      String sourceURI = getSourceURI(action.getOptions());
      if (sourceURI.isEmpty()) {
         LOGGER.error("No source URI given to load source models");
         return;
      }
      Optional<ModelServerClientV1> modelServerClient = modelServerClientProvider.get();
      if (modelServerClient.isEmpty()) {
         LOGGER.error("Connection to modelserver could not be initialized");
         return;
      }

      WorkflowModelServerAccess modelServerAccess = createModelServerAccess(sourceURI, modelServerClient.get());

      WorkflowModelState modelState = createModelState(gModelState);
      modelState.initialize(action.getOptions(), modelServerAccess);

      try {
         modelState.loadSourceModels();
      } catch (GLSPServerException e) {
         LOGGER.error("Error during source model loading");
         e.printStackTrace();
         return;
      }

      if (action.getOptions().get("highlights") != null) {
         HashMap<String, String> map = new Gson().fromJson(action.getOptions().get("highlights"), HashMap.class);
         for (Entry<String, String> entry : map.entrySet()) {
            modelState.addHighlight(entry);
         }
      } else {
         modelServerAccess.subscribe(createSubscriptionListener(modelState, actionDispatcher, submissionHandler));
         modelServerAccess.createValidationFramework(modelState);
         modelServerAccess.subscribeToValidation();
         modelServerAccess.initConstraintList();
      }
   }

   @Override
   public WorkflowModelServerAccess createModelServerAccess(final String sourceURI,
      final ModelServerClientV1 modelServerClient) {
      return new WorkflowModelServerAccess(sourceURI, modelServerClient, actionDispatcher);
   }

   @Override
   public WorkflowModelState createModelState(final GModelState modelState) {
      return WorkflowModelState.getModelState(modelState);
   }

   @Override
   protected String getSourceURI(final Map<String, String> clientOptions) {
      // We want to use the absolute sourceUri instead of the relative one from the
      // super class
      String sourceURI = ClientOptionsUtil.getSourceUri(clientOptions)
         .orElseThrow(() -> new GLSPServerException("No source URI given to load model!"));
      return sourceURI;
   }

}
