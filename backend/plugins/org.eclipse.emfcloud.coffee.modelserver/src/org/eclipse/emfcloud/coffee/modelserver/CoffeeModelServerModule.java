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
package org.eclipse.emfcloud.coffee.modelserver;

import org.eclipse.emfcloud.coffee.modelserver.commands.contributions.AddAutomatedTaskCommandContribution;
import org.eclipse.emfcloud.coffee.modelserver.commands.contributions.AddDecisionNodeCommandContribution;
import org.eclipse.emfcloud.coffee.modelserver.commands.contributions.AddFlowCommandContribution;
import org.eclipse.emfcloud.coffee.modelserver.commands.contributions.AddManualTaskCommandContribution;
import org.eclipse.emfcloud.coffee.modelserver.commands.contributions.AddMergeNodeCommandContribution;
import org.eclipse.emfcloud.coffee.modelserver.commands.contributions.AddWeightedFlowCommandContribution;
import org.eclipse.emfcloud.coffee.modelserver.commands.contributions.RemoveFlowCommandContribution;
import org.eclipse.emfcloud.coffee.modelserver.commands.contributions.RemoveNodeCommandContribution;
import org.eclipse.emfcloud.coffee.modelserver.commands.contributions.SetFlowSourceCommandContribution;
import org.eclipse.emfcloud.coffee.modelserver.commands.contributions.SetFlowTargetCommandContribution;
import org.eclipse.emfcloud.modelserver.common.utils.MapBinding;
import org.eclipse.emfcloud.modelserver.common.utils.MultiBinding;
import org.eclipse.emfcloud.modelserver.edit.CommandContribution;
import org.eclipse.emfcloud.modelserver.emf.common.ModelResourceManager;
import org.eclipse.emfcloud.modelserver.emf.common.ResourceSetFactory;
import org.eclipse.emfcloud.modelserver.emf.configuration.EPackageConfiguration;
import org.eclipse.emfcloud.modelserver.notation.integration.EMSNotationModelServerModule;
import org.eclipse.emfcloud.modelserver.notation.integration.NotationResource;

public class CoffeeModelServerModule extends EMSNotationModelServerModule {

   @Override
   protected Class<? extends ModelResourceManager> bindModelResourceManager() {
      return CoffeeModelResourceManager.class;
   }

   @Override
   protected void configureEPackages(final MultiBinding<EPackageConfiguration> binding) {
      super.configureEPackages(binding);
      binding.add(CoffeePackageConfiguration.class);
   }

   @Override
   protected Class<? extends ResourceSetFactory> bindResourceSetFactory() {
      return CoffeeResourceSetFactory.class;
   }

   @Override
   protected void configureCommandCodecs(final MapBinding<String, CommandContribution> binding) {
      super.configureCommandCodecs(binding);

      // Nodes
      binding.put(AddManualTaskCommandContribution.TYPE, AddManualTaskCommandContribution.class);
      binding.put(AddAutomatedTaskCommandContribution.TYPE, AddAutomatedTaskCommandContribution.class);
      binding.put(AddDecisionNodeCommandContribution.TYPE, AddDecisionNodeCommandContribution.class);
      binding.put(AddMergeNodeCommandContribution.TYPE, AddMergeNodeCommandContribution.class);
      binding.put(RemoveNodeCommandContribution.TYPE, RemoveNodeCommandContribution.class);

      // Flows (Edges)
      binding.put(AddFlowCommandContribution.TYPE, AddFlowCommandContribution.class);
      binding.put(AddWeightedFlowCommandContribution.TYPE, AddWeightedFlowCommandContribution.class);
      binding.put(RemoveFlowCommandContribution.TYPE, RemoveFlowCommandContribution.class);
      binding.put(SetFlowSourceCommandContribution.TYPE, SetFlowSourceCommandContribution.class);
      binding.put(SetFlowTargetCommandContribution.TYPE, SetFlowTargetCommandContribution.class);
   }

   @Override
   protected String getSemanticFileExtension() { return CoffeeResource.FILE_EXTENSION; }

   @Override
   protected String getNotationFileExtension() { return NotationResource.FILE_EXTENSION; }

}
