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
package com.eclipsesource.workflow.glsp.server;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emfcloud.modelserver.edit.CommandCodec;
import org.eclipse.emfcloud.modelserver.edit.DefaultCommandCodec;
import org.eclipse.glsp.api.configuration.ServerConfiguration;
import org.eclipse.glsp.api.diagram.DiagramConfiguration;
import org.eclipse.glsp.api.factory.ModelFactory;
import org.eclipse.glsp.api.handler.ActionHandler;
import org.eclipse.glsp.api.handler.OperationHandler;
import org.eclipse.glsp.api.jsonrpc.GLSPServer;
import org.eclipse.glsp.api.layout.ILayoutEngine;
import org.eclipse.glsp.api.model.ModelStateProvider;
import org.eclipse.glsp.api.provider.CommandPaletteActionProvider;
import org.eclipse.glsp.api.provider.ContextMenuItemProvider;
import org.eclipse.glsp.graph.GraphExtension;
import org.eclipse.glsp.server.actionhandler.OperationActionHandler;
import org.eclipse.glsp.server.actionhandler.SaveModelActionHandler;
import org.eclipse.glsp.server.actionhandler.UndoRedoActionHandler;
import org.eclipse.glsp.server.di.DefaultGLSPModule;
import org.eclipse.glsp.server.di.MultiBindConfig;

import com.eclipsesource.workflow.glsp.server.handler.WorkflowOperationActionHandler;
import com.eclipsesource.workflow.glsp.server.handler.WorkflowSaveModelActionHandler;
import com.eclipsesource.workflow.glsp.server.handler.operation.ApplyLabelEditOperationHandler;
import com.eclipsesource.workflow.glsp.server.handler.operation.ChangeBoundsOperationHandler;
import com.eclipsesource.workflow.glsp.server.handler.operation.ChangeRoutingPointsOperationHandler;
import com.eclipsesource.workflow.glsp.server.handler.operation.CreateAutomatedTaskHandler;
import com.eclipsesource.workflow.glsp.server.handler.operation.CreateDecisionNodeHandler;
import com.eclipsesource.workflow.glsp.server.handler.operation.CreateFlowHandler;
import com.eclipsesource.workflow.glsp.server.handler.operation.CreateManualTaskHandler;
import com.eclipsesource.workflow.glsp.server.handler.operation.CreateMergeNodeHandler;
import com.eclipsesource.workflow.glsp.server.handler.operation.CreateWeightedFlowHandler;
import com.eclipsesource.workflow.glsp.server.handler.operation.DeleteOperationHandler;
import com.eclipsesource.workflow.glsp.server.handler.operation.ReconnectFlowHandler;
import com.eclipsesource.workflow.glsp.server.model.WorkflowModelFactory;
import com.eclipsesource.workflow.glsp.server.model.WorkflowModelStateProvider;

public class WorkflowGLSPModule extends DefaultGLSPModule {

	@Override
	protected Class<? extends ModelFactory> bindModelFactory() {
		return WorkflowModelFactory.class;
	}

	@Override
	protected Class<? extends ServerConfiguration> bindServerConfiguration() {
		return WorkflowServerConfiguration.class;
	}

	@Override
	protected Class<? extends ModelStateProvider> bindModelStateProvider() {
		return WorkflowModelStateProvider.class;
	}

	@Override
	protected Class<? extends GLSPServer> bindGLSPServer() {
		return WorkflowGLSPServer.class;
	}

	@Override
	protected void configureActionHandlers(MultiBindConfig<ActionHandler> bindings) {
		super.configureActionHandlers(bindings);
		bindings.rebind(OperationActionHandler.class, WorkflowOperationActionHandler.class);
		bindings.rebind(SaveModelActionHandler.class, WorkflowSaveModelActionHandler.class);
		// TODO inject own undo/redo once incremental model server is ready
		bindings.remove(UndoRedoActionHandler.class);
	}

	@Override
	protected void configureOperationHandlers(MultiBindConfig<OperationHandler> bindings) {
		bindings.add(CreateAutomatedTaskHandler.class);
		bindings.add(CreateManualTaskHandler.class);
		bindings.add(CreateDecisionNodeHandler.class);
		bindings.add(CreateMergeNodeHandler.class);
		bindings.add(CreateFlowHandler.class);
		bindings.add(CreateWeightedFlowHandler.class);
		bindings.add(ReconnectFlowHandler.class);
		bindings.add(ChangeBoundsOperationHandler.class);
		bindings.add(DeleteOperationHandler.class);
		bindings.add(ApplyLabelEditOperationHandler.class);
		bindings.add(ChangeRoutingPointsOperationHandler.class);

	}

	@Override
	protected Class<? extends GraphExtension> bindGraphExtension() {
		return WorkflowGraphExtension.class;
	}

	@Override
	protected void configureDiagramConfigurations(MultiBindConfig<DiagramConfiguration> bindings) {
		bindings.add(WorfklowDiagramNotationConfiguration.class);
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
		bind(CommandCodec.class).toInstance(new DefaultCommandCodec());
	}

}
