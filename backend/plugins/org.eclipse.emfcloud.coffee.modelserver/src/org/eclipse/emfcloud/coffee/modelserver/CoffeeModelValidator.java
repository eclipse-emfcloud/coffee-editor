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

import java.util.Optional;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emfcloud.jackson.module.EMFModule;
import org.eclipse.emfcloud.modelserver.emf.common.DefaultModelValidator;
import org.eclipse.emfcloud.modelserver.emf.common.ModelRepository;
import org.eclipse.emfcloud.modelserver.emf.common.ValidationMapperModule;
import org.eclipse.emfcloud.modelserver.emf.configuration.FacetConfig;
import org.eclipse.emfcloud.modelserver.jsonschema.Json;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class CoffeeModelValidator extends DefaultModelValidator {
   private static final Diagnostician DIAGNOSTICIAN = Diagnostician.INSTANCE;

   @Inject
   public CoffeeModelValidator(final ModelRepository modelRepository, final FacetConfig facetConfig,
      final Provider<ObjectMapper> mapperProvider) {
      super(modelRepository, facetConfig, mapperProvider);
   }

   @Override
   public JsonNode validate(final String modeluri) {
      Optional<EObject> model = this.modelRepository.getModel(modeluri);
      Optional<Resource> res = this.modelRepository.loadResource(modeluri);
      if (model.isEmpty() || res.isEmpty()) {
         return Json.text("Model not found!");
      }
      ObjectMapper mapper = EMFModule.setupDefaultMapper();
      mapper.registerModule(new ValidationMapperModule(res.get()));
      mapper.setVisibility(PropertyAccessor.FIELD, Visibility.PROTECTED_AND_PUBLIC);
      BasicDiagnostic diagnostics = DIAGNOSTICIAN.createDefaultDiagnostic(model.get());
      DIAGNOSTICIAN.validate(model.get(), diagnostics, DIAGNOSTICIAN.createDefaultContext());
      return mapper.valueToTree(diagnostics);
   }
}
