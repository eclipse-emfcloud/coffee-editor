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
package org.eclipse.emfcloud.coffee.workflow.glsp.server;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.handler.operation.CreateAutomatedTaskHandler;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.handler.operation.CreateDecisionNodeHandler;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.handler.operation.CreateFlowHandler;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.handler.operation.CreateManualTaskHandler;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.handler.operation.CreateMergeNodeHandler;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.handler.operation.CreateWeightedFlowHandler;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.handler.operation.WorkflowApplyLabelEditOperationHandler;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.handler.operation.WorkflowChangeRoutingPointsOperationHandler;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.handler.operation.WorkflowCompoundOperationHandler;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.handler.operation.WorkflowDeleteOperationHandler;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.handler.operation.WorkflowReconnectFlowHandler;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.handler.operation.WorkflowRequestMarkersActionHandler;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.model.WorkflowModelFactory;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.model.WorkflowModelSourceLoader;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.model.WorkflowModelState;
import org.eclipse.emfcloud.modelserver.glsp.model.EMSModelState;
import org.eclipse.emfcloud.modelserver.glsp.notation.integration.EMSNotationGLSPModule;
import org.eclipse.glsp.graph.GraphExtension;
import org.eclipse.glsp.server.actions.ActionHandler;
import org.eclipse.glsp.server.di.MultiBinding;
import org.eclipse.glsp.server.diagram.DiagramConfiguration;
import org.eclipse.glsp.server.features.commandpalette.CommandPaletteActionProvider;
import org.eclipse.glsp.server.features.contextmenu.ContextMenuItemProvider;
import org.eclipse.glsp.server.features.core.model.GModelFactory;
import org.eclipse.glsp.server.features.core.model.ModelSourceLoader;
import org.eclipse.glsp.server.features.directediting.ApplyLabelEditOperationHandler;
import org.eclipse.glsp.server.features.directediting.LabelEditValidator;
import org.eclipse.glsp.server.features.validation.RequestMarkersHandler;
import org.eclipse.glsp.server.layout.LayoutEngine;
import org.eclipse.glsp.server.operations.OperationHandler;
import org.eclipse.glsp.server.operations.gmodel.ChangeRoutingPointsHandler;
import org.eclipse.glsp.server.operations.gmodel.CompoundOperationHandler;
import org.eclipse.glsp.server.operations.gmodel.CutOperationHandler;
import org.eclipse.glsp.server.operations.gmodel.DeleteOperationHandler;
import org.eclipse.glsp.server.operations.gmodel.LayoutOperationHandler;
import org.eclipse.glsp.server.operations.gmodel.PasteOperationHandler;
import org.eclipse.glsp.server.operations.gmodel.ReconnectEdgeOperationHandler;

public class WorkflowGLSPModule extends EMSNotationGLSPModule {

   @Override
   protected Class<? extends EMSModelState> bindGModelState() {
      return WorkflowModelState.class;
   }

   @Override
   protected void configureActionHandlers(final MultiBinding<ActionHandler> bindings) {
      super.configureActionHandlers(bindings);
      bindings.rebind(RequestMarkersHandler.class, WorkflowRequestMarkersActionHandler.class);
   }

   @Override
   protected void configureOperationHandlers(final MultiBinding<OperationHandler> bindings) {
      super.configureOperationHandlers(bindings);

      // model server-aware operation handlers
      bindings.rebind(CompoundOperationHandler.class, WorkflowCompoundOperationHandler.class);
      bindings.rebind(ChangeRoutingPointsHandler.class, WorkflowChangeRoutingPointsOperationHandler.class);
      bindings.rebind(ApplyLabelEditOperationHandler.class, WorkflowApplyLabelEditOperationHandler.class);
      bindings.rebind(DeleteOperationHandler.class, WorkflowDeleteOperationHandler.class);
      bindings.rebind(ReconnectEdgeOperationHandler.class, WorkflowReconnectFlowHandler.class);

      // unsupported operation handlers
      bindings.remove(CutOperationHandler.class);
      bindings.remove(PasteOperationHandler.class);
      bindings.remove(LayoutOperationHandler.class);

      // custom workflow operation handlers
      bindings.add(CreateAutomatedTaskHandler.class);
      bindings.add(CreateManualTaskHandler.class);
      bindings.add(CreateDecisionNodeHandler.class);
      bindings.add(CreateMergeNodeHandler.class);
      bindings.add(CreateFlowHandler.class);
      bindings.add(CreateWeightedFlowHandler.class);
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
   protected Class<? extends DiagramConfiguration> bindDiagramConfiguration() {
      return WorkflowDiagramNotationConfiguration.class;
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
   public void configure() {
      super.configure();
      bind(AdapterFactory.class).toInstance(new ComposedAdapterFactory());
   }

   @Override
   protected Class<? extends ModelSourceLoader> bindSourceModelLoader() {
      return WorkflowModelSourceLoader.class;
   }

   @Override
   protected Class<? extends GModelFactory> bindGModelFactory() {
      return WorkflowModelFactory.class;
   }

   @Override
   public String getDiagramType() { return "workflow-diagram-notation"; }

}
