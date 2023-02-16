/*******************************************************************************
 * Copyright (c) 2019-2023 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ******************************************************************************/
package org.eclipse.emfcloud.coffee.workflow.glsp.server;

import org.eclipse.emfcloud.coffee.CoffeePackage;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.gmodel.WorkflowGModelFactory;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.gmodel.WorkflowSourceModelStorage;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.handler.actions.WorkflowRequestMarkersActionHandler;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.handler.create.CreateAutomatedTaskHandler;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.handler.create.CreateDecisionNodeHandler;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.handler.create.CreateFlowHandler;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.handler.create.CreateManualTaskHandler;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.handler.create.CreateMergeNodeHandler;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.handler.create.CreateWeightedFlowHandler;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.handler.operations.WorkflowApplyLabelEditOperationHandler;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.handler.operations.WorkflowDeleteOperationHandler;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.handler.operations.WorkflowReconnectFlowHandler;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.layout.WorkflowLayoutEngine;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.palette.WorkflowToolPaletteItemProvider;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.provider.WorkflowCommandPaletteActionProvider;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.provider.WorkflowContextMenuItemProvider;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.taskedit.ApplyTaskEditOperationHandler;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.taskedit.EditTaskOperationHandler;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.taskedit.TaskEditContextActionProvider;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.taskedit.TaskEditValidator;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.validation.WorkflowLabelEditValidator;
import org.eclipse.emfcloud.modelserver.glsp.notation.integration.EMSGLSPNotationDiagramModule;
import org.eclipse.emfcloud.modelserver.glsp.notation.integration.EMSNotationModelServerAccess;
import org.eclipse.emfcloud.modelserver.glsp.notation.integration.EMSNotationSourceModelStorage;
import org.eclipse.glsp.graph.GraphExtension;
import org.eclipse.glsp.server.actions.ActionHandler;
import org.eclipse.glsp.server.di.MultiBinding;
import org.eclipse.glsp.server.diagram.DiagramConfiguration;
import org.eclipse.glsp.server.features.commandpalette.CommandPaletteActionProvider;
import org.eclipse.glsp.server.features.contextactions.ContextActionsProvider;
import org.eclipse.glsp.server.features.contextmenu.ContextMenuItemProvider;
import org.eclipse.glsp.server.features.core.model.GModelFactory;
import org.eclipse.glsp.server.features.directediting.ContextEditValidator;
import org.eclipse.glsp.server.features.directediting.LabelEditValidator;
import org.eclipse.glsp.server.features.toolpalette.ToolPaletteItemProvider;
import org.eclipse.glsp.server.features.validation.RequestMarkersHandler;
import org.eclipse.glsp.server.layout.LayoutEngine;
import org.eclipse.glsp.server.operations.CutOperationHandler;
import org.eclipse.glsp.server.operations.OperationHandler;

import com.google.inject.Singleton;

public class WorkflowDiagramModule extends EMSGLSPNotationDiagramModule {

   @Override
   protected void configureBase() {
      super.configureBase();
      bind(WorkflowHighlightStore.class).in(Singleton.class);
   }

   @Override
   protected void registerEPackages() {
      // register and initialize all used ePackages
      super.registerEPackages();
      CoffeePackage.eINSTANCE.eClass();
   }

   @Override
   protected Class<? extends DiagramConfiguration> bindDiagramConfiguration() {
      // define what operations are allowed with our elements
      return WorkflowDiagramConfiguration.class;
   }

   @Override
   protected Class<? extends GModelFactory> bindGModelFactory() {
      return WorkflowGModelFactory.class;
   }

   @Override
   protected Class<? extends EMSNotationSourceModelStorage> bindSourceModelStorage() {
      return WorkflowSourceModelStorage.class;
   }

   @Override
   protected Class<? extends EMSNotationModelServerAccess> bindModelServerAccess() {
      return WorkflowModelServerAccess.class;
   }

   @Override
   protected void configureActionHandlers(final MultiBinding<ActionHandler> bindings) {
      super.configureActionHandlers(bindings);
      bindings.rebind(RequestMarkersHandler.class, WorkflowRequestMarkersActionHandler.class);
   }

   @Override
   protected void configureOperationHandlers(final MultiBinding<OperationHandler<?>> bindings) {
      super.configureOperationHandlers(bindings);

      // model server-aware operation handlers
      bindings.add(WorkflowApplyLabelEditOperationHandler.class);
      bindings.add(WorkflowDeleteOperationHandler.class);
      bindings.add(WorkflowReconnectFlowHandler.class);

      // unsupported operation handlers
      bindings.remove(CutOperationHandler.class);

      // custom workflow operation handlers
      bindings.add(CreateAutomatedTaskHandler.class);
      bindings.add(CreateManualTaskHandler.class);
      bindings.add(CreateDecisionNodeHandler.class);
      bindings.add(CreateMergeNodeHandler.class);
      bindings.add(CreateFlowHandler.class);
      bindings.add(CreateWeightedFlowHandler.class);

      // task editing
      bindings.add(EditTaskOperationHandler.class);
      bindings.add(ApplyTaskEditOperationHandler.class);
   }

   @Override
   protected Class<? extends GraphExtension> bindGraphExtension() {
      return WorkflowGraphExtension.class;
   }

   @Override
   protected Class<? extends LabelEditValidator> bindLabelEditValidator() {
      return WorkflowLabelEditValidator.class;
   }

   @Override
   protected Class<? extends CommandPaletteActionProvider> bindCommandPaletteActionProvider() {
      return WorkflowCommandPaletteActionProvider.class;
   }

   @Override
   protected Class<? extends ContextMenuItemProvider> bindContextMenuItemProvider() {
      return WorkflowContextMenuItemProvider.class;
   }

   @Override
   protected Class<? extends LayoutEngine> bindLayoutEngine() {
      return WorkflowLayoutEngine.class;
   }

   @Override
   protected Class<? extends ToolPaletteItemProvider> bindToolPaletteItemProvider() {
      return WorkflowToolPaletteItemProvider.class;
   }

   @Override
   protected void configureContextActionsProviders(final MultiBinding<ContextActionsProvider> binding) {
      super.configureContextActionsProviders(binding);
      binding.add(TaskEditContextActionProvider.class);
   }

   @Override
   protected void configureContextEditValidators(final MultiBinding<ContextEditValidator> binding) {
      super.configureContextEditValidators(binding);
      binding.add(TaskEditValidator.class);
   }

   @Override
   public String getDiagramType() { return "workflow-diagram-notation"; }

   @Override
   protected String getSemanticFileExtension() { return "coffee"; }

   @Override
   protected String getNotationFileExtension() { return "notation"; }

}
