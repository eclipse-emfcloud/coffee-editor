/*******************************************************************************
 * Copyright (c) 2019-2020 EclipseSource and others.
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
import org.eclipse.emfcloud.coffee.workflow.glsp.server.handler.operation.ApplyLabelEditOperationHandler;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.handler.operation.CreateAutomatedTaskHandler;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.handler.operation.CreateDecisionNodeHandler;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.handler.operation.CreateFlowHandler;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.handler.operation.CreateManualTaskHandler;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.handler.operation.CreateMergeNodeHandler;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.handler.operation.CreateWeightedFlowHandler;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.handler.operation.ReconnectFlowHandler;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.handler.operation.WorkflowChangeRoutingPointsOperationHandler;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.handler.operation.WorkflowCompoundOperationHandler;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.handler.operation.WorkflowDeleteOperationHandler;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.model.WorkflowModelFactory;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.model.WorkflowModelSourceLoader;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.model.WorkflowModelStateProvider;
import org.eclipse.emfcloud.modelserver.glsp.notation.integration.EMSNotationGLSPModule;
import org.eclipse.glsp.graph.GraphExtension;
import org.eclipse.glsp.server.diagram.DiagramConfiguration;
import org.eclipse.glsp.server.features.commandpalette.CommandPaletteActionProvider;
import org.eclipse.glsp.server.features.contextmenu.ContextMenuItemProvider;
import org.eclipse.glsp.server.features.core.model.GModelFactory;
import org.eclipse.glsp.server.features.core.model.ModelSourceLoader;
import org.eclipse.glsp.server.layout.ILayoutEngine;
import org.eclipse.glsp.server.model.ModelStateProvider;
import org.eclipse.glsp.server.operations.OperationHandler;
import org.eclipse.glsp.server.operations.gmodel.ChangeRoutingPointsHandler;
import org.eclipse.glsp.server.operations.gmodel.CompoundOperationHandler;
import org.eclipse.glsp.server.operations.gmodel.CutOperationHandler;
import org.eclipse.glsp.server.operations.gmodel.DeleteOperationHandler;
import org.eclipse.glsp.server.operations.gmodel.LayoutOperationHandler;
import org.eclipse.glsp.server.operations.gmodel.PasteOperationHandler;
import org.eclipse.glsp.server.operations.gmodel.ReconnectEdgeOperationHandler;
import org.eclipse.glsp.server.protocol.GLSPServer;
import org.eclipse.glsp.server.utils.MultiBinding;

public class WorkflowGLSPModule extends EMSNotationGLSPModule {

	// TODO need to bind layout configuration?

	@Override
	protected Class<? extends ModelStateProvider> bindModelStateProvider() {
		return WorkflowModelStateProvider.class;
	}

	@Override
	protected void configureOperationHandlers(MultiBinding<OperationHandler> bindings) {
		super.configureOperationHandlers(bindings);

		// model server-aware operation handlers
		bindings.rebind(CompoundOperationHandler.class, WorkflowCompoundOperationHandler.class);
		bindings.rebind(org.eclipse.glsp.server.features.directediting.ApplyLabelEditOperationHandler.class,
				ApplyLabelEditOperationHandler.class);
		bindings.rebind(DeleteOperationHandler.class, WorkflowDeleteOperationHandler.class);
		bindings.rebind(ChangeRoutingPointsHandler.class, WorkflowChangeRoutingPointsOperationHandler.class);
		bindings.rebind(ReconnectEdgeOperationHandler.class, ReconnectFlowHandler.class);

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
	protected void configureDiagramConfigurations(MultiBinding<DiagramConfiguration> bindings) {
		bindings.add(WorkflowDiagramNotationConfiguration.class);
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
	protected Class<? extends ILayoutEngine> bindLayoutEngine() {
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
	protected Class<? extends GLSPServer> bindGLSPServer() {
		return WorkflowGLSPServer.class;
	}

}
