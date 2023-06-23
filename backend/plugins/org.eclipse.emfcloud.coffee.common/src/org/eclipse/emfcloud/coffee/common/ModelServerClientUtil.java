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
package org.eclipse.emfcloud.coffee.common;

import java.net.URI;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emfcloud.modelserver.client.ModelServerClient;
import org.eclipse.emfcloud.modelserver.common.ModelServerPathParametersV2;

public final class ModelServerClientUtil {
   private static final String MODEL_SERVER_BASE_URL = "http://localhost:8081/api/v2/";

   private ModelServerClientUtil() {}

   @SuppressWarnings("IllegalThrows")
   public static EObject loadResource(final URI uri) throws Exception {
      @SuppressWarnings("resource")
      ModelServerClient client = new ModelServerClient(MODEL_SERVER_BASE_URL);
      // FIXME temporary quick fix for OS specific URI issues
      return client.get("superbrewer3000.coffee", ModelServerPathParametersV2.FORMAT_XMI).get().body();
   }

   @SuppressWarnings("IllegalThrows")
   public static <T> Optional<T> loadResource(final URI uri, final Class<T> clazz) throws Exception {
      EObject eObject = loadResource(uri);
      return clazz.isInstance(eObject) ? Optional.of(clazz.cast(eObject)) : Optional.empty();
   }
}
