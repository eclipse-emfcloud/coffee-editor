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

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emfcloud.jackson.module.EMFModule;
import org.eclipse.emfcloud.modelserver.common.codecs.DefaultJsonCodec;
import org.eclipse.emfcloud.modelserver.common.codecs.EncodingException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class CoffeeTreeJsonCodec extends DefaultJsonCodec {

   private final ObjectMapper objectMapper;

   public CoffeeTreeJsonCodec() {
      objectMapper = createObjectMapper();
   }

   @Override
   public JsonNode encode(final EObject obj) throws EncodingException {
      JsonNode jsonNode = encode(obj, getObjectMapper());
      return jsonNode;
   }

   @Override
   protected ObjectMapper getObjectMapper() { return objectMapper; }

   protected ObjectMapper createObjectMapper() {
      final ObjectMapper mapper = new ObjectMapper();
      // same as emf
      final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
      dateFormat.setTimeZone(TimeZone.getDefault());

      mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
      mapper.setDateFormat(dateFormat);
      mapper.setTimeZone(TimeZone.getDefault());

      EMFModule emfModule = new EMFModule();
      // Write XMI ids in property "@id". For customization see
      // https://github.com/eclipse-emfcloud/emfjson-jackson/wiki/Customization#custom-id-field
      emfModule.configure(EMFModule.Feature.OPTION_USE_ID, true);
      emfModule.configure(EMFModule.Feature.OPTION_SERIALIZE_DEFAULT_VALUE, true);

      // FIXME investigate why eClass (type) is not serialized for nested elements
      // e.g. processor child of control unit

      mapper.registerModule(emfModule);
      return mapper;
   }
}
