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
package org.eclipse.emfcloud.coffee.modelserver.commands.util;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfcloud.coffee.AutomaticTask;
import org.eclipse.emfcloud.coffee.CoffeePackage;
import org.eclipse.emfcloud.coffee.Machine;
import org.eclipse.emfcloud.coffee.ManualTask;
import org.eclipse.emfcloud.coffee.Node;
import org.eclipse.emfcloud.coffee.Task;
import org.eclipse.emfcloud.coffee.Workflow;
import org.eclipse.emfcloud.coffee.util.CoffeeResource;
import org.eclipse.emfcloud.modelserver.command.CCommand;
import org.eclipse.emfcloud.modelserver.edit.command.SetCommandContribution;
import org.eclipse.emfcloud.modelserver.edit.util.CommandUtil;

public final class SemanticCommandUtil {

   // Hide constructor for utility class
   private SemanticCommandUtil() {}

   public static String getSemanticUriFragment(final EObject element) {
      return EcoreUtil.getURI(element).fragment();
   }

   public static Workflow getModel(final URI modelUri, final EditingDomain domain) {
      Resource semanticResource = domain.getResourceSet()
         .getResource(modelUri.trimFileExtension().appendFileExtension(CoffeeResource.FILE_EXTENSION), false);
      EObject semanticRoot = semanticResource.getContents().get(0);
      if (!(semanticRoot instanceof Machine)) {
         return null;
      }
      Machine machine = (Machine) semanticRoot;
      if (machine.getWorkflows().size() < 1) {
         return null;
      }
      // TODO We might want to hand in the index of the used workflow
      return machine.getWorkflows().get(0);
   }

   public static EObject getElement(final Workflow semanticModel, final String semanticUriFragment) {
      return semanticModel.eResource().getEObject(semanticUriFragment);
   }

   public static <C> C getElement(final Workflow semanticModel, final String semanticUriFragment,
      final java.lang.Class<C> clazz) {
      EObject element = getElement(semanticModel, semanticUriFragment);
      return clazz.cast(element);
   }

   public static CCommand createSetTaskNameCommand(final Node taskToRename, final String ownerRefUri,
      final String newName) {
      return SetCommandContribution.clientCommand(CommandUtil.createProxy(getEClass(taskToRename), ownerRefUri),
         CoffeePackage.Literals.TASK__NAME, newName);
   }

   protected static EClass getEClass(final EObject element) {
      if (element instanceof ManualTask) {
         return CoffeePackage.Literals.MANUAL_TASK;
      } else if (element instanceof AutomaticTask) {
         return CoffeePackage.Literals.AUTOMATIC_TASK;
      } else if (element instanceof Task) {
         return CoffeePackage.Literals.TASK;
      }
      return CoffeePackage.Literals.NODE;
   }
}
