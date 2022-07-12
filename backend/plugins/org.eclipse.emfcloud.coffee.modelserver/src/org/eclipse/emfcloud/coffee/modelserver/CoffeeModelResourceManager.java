/*******************************************************************************
 * Copyright (c) 2021 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ******************************************************************************/
package org.eclipse.emfcloud.coffee.modelserver;

import java.io.File;
import java.util.Arrays;
import java.util.Set;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emfcloud.coffee.util.CoffeeResource;
import org.eclipse.emfcloud.modelserver.emf.common.RecordingModelResourceManager;
import org.eclipse.emfcloud.modelserver.emf.common.watchers.ModelWatchersManager;
import org.eclipse.emfcloud.modelserver.emf.configuration.EPackageConfiguration;
import org.eclipse.emfcloud.modelserver.emf.configuration.ServerConfiguration;
import org.eclipse.emfcloud.modelserver.emf.util.JsonPatchHelper;
import org.eclipse.emfcloud.modelserver.glsp.notation.epackage.NotationUtil;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class CoffeeModelResourceManager extends RecordingModelResourceManager {

   @Inject
   public CoffeeModelResourceManager(final Set<EPackageConfiguration> configurations,
      final AdapterFactory adapterFactory,
      final ServerConfiguration serverConfiguration, final ModelWatchersManager watchersManager,
      final Provider<JsonPatchHelper> jsonPatchHelper) {
      super(configurations, adapterFactory, serverConfiguration, watchersManager, jsonPatchHelper);
   }

   @Override
   protected void loadSourceResources(final String directoryPath) {
      if (directoryPath == null || directoryPath.isEmpty()) {
         return;
      }
      File directory = new File(directoryPath);
      File[] list = directory.listFiles();
      Arrays.sort(list);
      for (File file : list) {
         if (isSourceDirectory(file)) {
            loadSourceResources(file.getAbsolutePath());
         } else if (file.isFile()) {
            URI modelURI = createURI(file.getAbsolutePath());
            if (CoffeeResource.FILE_EXTENSION.equals(modelURI.fileExtension())) {
               resourceSets.put(modelURI, resourceSetFactory.createResourceSet(modelURI));
            }
            loadResource(modelURI.toString());
         }
      }
   }

   @Override
   public ResourceSet getResourceSet(final String modeluri) {
      URI modelURI = createURI(modeluri);
      if (NotationUtil.NOTATION_EXTENSION.equals(modelURI.fileExtension())) {
         URI semanticUri = createURI(modeluri).trimFileExtension()
            .appendFileExtension(CoffeeResource.FILE_EXTENSION);
         return resourceSets.get(semanticUri);
      }
      return resourceSets.get(createURI(modeluri));
   }

   @Override
   public boolean save(final String modeluri) {
      boolean result = false;
      for (Resource resource : getResourceSet(modeluri).getResources()) {
         result = saveResource(resource);
      }
      if (result) {
         getEditingDomain(getResourceSet(modeluri)).saveIsDone();
      }
      return result;
   }
}
