/********************************************************************************
 * Copyright (c) 2021-2022 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package org.eclipse.emfcloud.coffee.workflow.glsp.server;

import java.net.MalformedURLException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.eclipse.emfcloud.coffee.modelserver.CoffeeModelServerClient;
import org.eclipse.emfcloud.coffee.modelserver.CoffeeResource;
import org.eclipse.emfcloud.modelserver.client.v1.ModelServerClientV1;
import org.eclipse.emfcloud.modelserver.glsp.EMSGLSPServer;
import org.eclipse.emfcloud.modelserver.glsp.notation.epackage.NotationUtil;
import org.eclipse.glsp.server.protocol.DisposeClientSessionParameters;
import org.eclipse.glsp.server.types.GLSPServerException;
import org.eclipse.glsp.server.utils.ClientOptionsUtil;

public class WorkflowGLSPServer extends EMSGLSPServer {

   @Override
   protected ModelServerClientV1 createModelServerClient(final String modelServerURL) throws MalformedURLException {
      return new CoffeeModelServerClient(modelServerURL);
   }

   @Override
   public CompletableFuture<Void> disposeClientSession(final DisposeClientSessionParameters params) {
      Optional<ModelServerClientV1> modelServerClient = modelServerClientProvider.get();
      if (modelServerClient.isPresent()) {
         String sourceURI = ClientOptionsUtil.getSourceUri(params.getArgs())
            .orElseThrow(() -> new GLSPServerException("No source URI given to dispose client session!"));
         modelServerClient.get()
            .unsubscribe(sourceURI.replace(NotationUtil.NOTATION_EXTENSION, CoffeeResource.FILE_EXTENSION));
      }
      return super.disposeClientSession(params);
   }

}
