/*******************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
 *  
 *   This program and the accompanying materials are made available under the
 *   terms of the Eclipse Public License v. 2.0 which is available at
 *   http://www.eclipse.org/legal/epl-2.0.
 *  
 *   This Source Code may also be made available under the following Secondary
 *   Licenses when the conditions for such availability set forth in the Eclipse
 *   Public License v. 2.0 are satisfied: GNU General Public License, version 2
 *   with the GNU Classpath Exception which is available at
 *   https://www.gnu.org/software/classpath/license.html.
 *  
 *   SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 ******************************************************************************/
package com.eclipsesource.glsp.example.modelserver.workflow;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emfcloud.modelserver.edit.CommandCodec;
import org.eclipse.emfcloud.modelserver.edit.DefaultCommandCodec;
import org.eclipse.glsp.api.di.MultiBindings;
import org.eclipse.glsp.api.diagram.DiagramConfiguration;
import org.eclipse.glsp.api.factory.ModelFactory;
import org.eclipse.glsp.api.handler.ActionHandler;
import org.eclipse.glsp.api.handler.OperationHandler;
import org.eclipse.glsp.api.jsonrpc.GLSPServer;
import org.eclipse.glsp.api.model.ModelStateProvider;
import org.eclipse.glsp.example.workflow.WorkflowGLSPModule;
import org.eclipse.glsp.server.actionhandler.OperationActionHandler;
import org.eclipse.glsp.server.actionhandler.SaveModelActionHandler;
import org.eclipse.glsp.server.actionhandler.UndoRedoActionHandler;
import org.eclipse.glsp.server.operationhandler.ApplyLabelEditOperationHandler;
import org.eclipse.glsp.server.operationhandler.ChangeBoundsOperationHandler;
import org.eclipse.glsp.server.operationhandler.DeleteOperationHandler;
import org.eclipse.glsp.server.operationhandler.ReconnectEdgeHandler;

import com.eclipsesource.glsp.example.modelserver.workflow.handler.CreateAutomatedTaskHandler;
import com.eclipsesource.glsp.example.modelserver.workflow.handler.CreateDecisionNodeHandler;
import com.eclipsesource.glsp.example.modelserver.workflow.handler.CreateFlowHandler;
import com.eclipsesource.glsp.example.modelserver.workflow.handler.CreateManualTaskHandler;
import com.eclipsesource.glsp.example.modelserver.workflow.handler.CreateMergeNodeHandler;
import com.eclipsesource.glsp.example.modelserver.workflow.handler.CreateWeightedFlowHandler;
import com.eclipsesource.glsp.example.modelserver.workflow.handler.ModelServerAwareChangeBoundsOperationHandler;
import com.eclipsesource.glsp.example.modelserver.workflow.handler.ModelServerAwareOperationActionHandler;
import com.eclipsesource.glsp.example.modelserver.workflow.handler.ModelServerAwareSaveActionHandler;
import com.eclipsesource.glsp.example.modelserver.workflow.handler.ReconnectFlowHandler;
import com.eclipsesource.glsp.example.modelserver.workflow.handler.WorkflowApplyLabelEditOperationHandler;
import com.eclipsesource.glsp.example.modelserver.workflow.handler.WorkflowDeleteOperationHandler;
import com.eclipsesource.glsp.example.modelserver.workflow.handler.WorkflowRerouteEdgeHandler;
import com.eclipsesource.glsp.example.modelserver.workflow.model.ModelServerAwareModelStateProvider;
import com.eclipsesource.glsp.example.modelserver.workflow.model.WorkflowModelServerModelFactory;

@SuppressWarnings("serial")
public class WorkflowModelServerGLSPModule extends WorkflowGLSPModule {

	@Override
	protected Class<? extends ModelFactory> bindModelFactory() {
		return WorkflowModelServerModelFactory.class;
	}

	@Override
	protected Class<? extends ModelStateProvider> bindModelStateProvider() {
		return ModelServerAwareModelStateProvider.class;
	}

	@Override
	protected Class<? extends GLSPServer> bindGLSPServer() {
		return WorkflowModelServerGLSPServer.class;
	}

	
	@Override
	protected void configureActionHandlers(MultiBindings<ActionHandler> bindings) {
		super.configureActionHandlers(bindings);
		bindings.rebind(OperationActionHandler.class,ModelServerAwareOperationActionHandler.class);
		bindings.rebind(SaveModelActionHandler.class, ModelServerAwareSaveActionHandler.class);
		bindings.remove(UndoRedoActionHandler.class);
		// TODO inject own undo/redo once incremental model server is ready
	}
	

	@Override
	protected void configureOperationHandlers(MultiBindings<OperationHandler> bindings) {
		super.configureOperationHandlers(bindings);
		bindings.add(CreateAutomatedTaskHandler.class);
		bindings.add(CreateManualTaskHandler.class);
		bindings.add(CreateDecisionNodeHandler.class);
		bindings.add(CreateMergeNodeHandler.class);
		bindings.add(CreateFlowHandler.class);
		bindings.add(CreateWeightedFlowHandler.class);
		bindings.rebind(ReconnectEdgeHandler.class, ReconnectFlowHandler.class);
		bindings.rebind(ChangeBoundsOperationHandler.class, ModelServerAwareChangeBoundsOperationHandler.class);
		bindings.rebind(DeleteOperationHandler.class, WorkflowDeleteOperationHandler.class);
		bindings.rebind(ApplyLabelEditOperationHandler.class, WorkflowApplyLabelEditOperationHandler.class);
		bindings.rebind(ChangeBoundsOperationHandler.class, WorkflowRerouteEdgeHandler.class);
		
	}



	@Override
	protected void configureDiagramConfigurations(MultiBindings<DiagramConfiguration> bindings) {
		super.configureDiagramConfigurations(bindings);
		bindings.add(WorfklowDiagramNotationConfiguration.class);
	}

	@Override
	protected void configure() {
		super.configure();
		bind(AdapterFactory.class).toInstance(new ComposedAdapterFactory());
		bind(CommandCodec.class).toInstance(new DefaultCommandCodec());
	}

}
