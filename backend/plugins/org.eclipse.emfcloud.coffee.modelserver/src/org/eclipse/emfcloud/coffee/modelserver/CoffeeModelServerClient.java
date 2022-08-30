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
package org.eclipse.emfcloud.coffee.modelserver;

import java.net.MalformedURLException;
import java.util.Map;

import org.eclipse.emfcloud.coffee.util.CoffeeResource;
import org.eclipse.emfcloud.modelserver.client.v2.ModelServerClientV2;
import org.eclipse.emfcloud.modelserver.common.ModelServerPathParametersV2;
import org.eclipse.emfcloud.modelserver.common.codecs.Codec;
import org.eclipse.emfcloud.modelserver.common.codecs.DefaultJsonCodec;
import org.eclipse.emfcloud.modelserver.common.codecs.XmiCodec;
import org.eclipse.emfcloud.modelserver.emf.configuration.EPackageConfiguration;

public class CoffeeModelServerClient extends ModelServerClientV2 {

   private static final Map<String, Codec> SUPPORTED_COFFEE_FORMATS = Map.of(
      ModelServerPathParametersV2.FORMAT_JSON, new DefaultJsonCodec(),
      ModelServerPathParametersV2.FORMAT_XMI, new XmiCodec(),
      CoffeeResource.FILE_EXTENSION, new CoffeeCodec());

   public CoffeeModelServerClient(final String baseUrl, final EPackageConfiguration... configurations)
      throws MalformedURLException {
      super(baseUrl, SUPPORTED_COFFEE_FORMATS, configurations);
   }

}
