/********************************************************************************
 * Copyright (c) 2021 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package org.eclipse.emfcloud.coffee.modelserver;

import java.net.MalformedURLException;
import java.util.Map;

import org.eclipse.emfcloud.modelserver.client.v1.ModelServerClientV1;
import org.eclipse.emfcloud.modelserver.common.ModelServerPathParametersV1;
import org.eclipse.emfcloud.modelserver.common.codecs.Codec;
import org.eclipse.emfcloud.modelserver.common.codecs.DefaultJsonCodec;
import org.eclipse.emfcloud.modelserver.common.codecs.XmiCodec;
import org.eclipse.emfcloud.modelserver.emf.configuration.EPackageConfiguration;

public class CoffeeModelServerClient extends ModelServerClientV1 {

   private static final Map<String, Codec> SUPPORTED_COFFEE_FORMATS = Map.of(
      ModelServerPathParametersV1.FORMAT_JSON, new DefaultJsonCodec(),
      ModelServerPathParametersV1.FORMAT_XMI, new XmiCodec(),
      CoffeeResource.FILE_EXTENSION, new CoffeeCodec());

   public CoffeeModelServerClient(final String baseUrl, final EPackageConfiguration... configurations)
      throws MalformedURLException {
      super(baseUrl, SUPPORTED_COFFEE_FORMATS, configurations);
   }

}
