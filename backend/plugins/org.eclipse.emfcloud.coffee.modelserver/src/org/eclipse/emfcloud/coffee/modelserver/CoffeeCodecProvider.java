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
package org.eclipse.emfcloud.coffee.modelserver;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

import org.eclipse.emfcloud.coffee.util.CoffeeResource;
import org.eclipse.emfcloud.modelserver.common.ModelServerPathParametersV2;
import org.eclipse.emfcloud.modelserver.common.codecs.Codec;
import org.eclipse.emfcloud.modelserver.emf.common.codecs.CodecProvider;

public class CoffeeCodecProvider implements CodecProvider {

   private final Map<String, Supplier<Codec>> supportedFormats = new LinkedHashMap<>();

   public CoffeeCodecProvider() {
      this.supportedFormats.put(CoffeeResource.FILE_EXTENSION, CoffeeCodec::new);
      this.supportedFormats.put(ModelServerPathParametersV2.FORMAT_JSON, CoffeeTreeJsonCodec::new);

   }

   @Override
   public Optional<Codec> getCodec(final String modelUri, final String format) {
      // if (!modelUri.endsWith(CoffeeResource.FILE_EXTENSION)) {
      // return Optional.empty();
      // }
      Supplier<Codec> codecSupplier = supportedFormats.get(format);
      if (codecSupplier == null) {
         return Optional.empty();
      }
      return Optional.of(codecSupplier.get());
   }

   @Override
   public Set<String> getAllFormats() { return supportedFormats.keySet(); }

   @Override
   public int getPriority(final String modelUri, final String format) {
      return (modelUri.endsWith(CoffeeResource.FILE_EXTENSION) && getAllFormats().contains(format)) ? 10
         : NOT_SUPPORTED;
   }

}
