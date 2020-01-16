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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;

import com.eclipsesource.glsp.api.diagram.DiagramConfiguration;
import com.eclipsesource.glsp.api.factory.ModelFactory;
import com.eclipsesource.glsp.api.handler.ActionHandler;
import com.eclipsesource.glsp.api.handler.OperationHandler;
import com.eclipsesource.glsp.api.jsonrpc.GLSPServer;
import com.eclipsesource.glsp.api.model.ModelStateProvider;
import com.eclipsesource.glsp.example.modelserver.workflow.handler.ApplyLabelEditOperationHandler;
import com.eclipsesource.glsp.example.modelserver.workflow.handler.ChangeBoundsOperationHandler;
import com.eclipsesource.glsp.example.modelserver.workflow.handler.CreateAutomatedTaskHandler;
import com.eclipsesource.glsp.example.modelserver.workflow.handler.CreateDecisionNodeHandler;
import com.eclipsesource.glsp.example.modelserver.workflow.handler.CreateFlowHandler;
import com.eclipsesource.glsp.example.modelserver.workflow.handler.CreateManualTaskHandler;
import com.eclipsesource.glsp.example.modelserver.workflow.handler.CreateMergeNodeHandler;
import com.eclipsesource.glsp.example.modelserver.workflow.handler.CreateWeightedFlowHandler;
import com.eclipsesource.glsp.example.modelserver.workflow.handler.DeleteOperationHandler;
import com.eclipsesource.glsp.example.modelserver.workflow.handler.ModelServerAwareOperationActionHandler;
import com.eclipsesource.glsp.example.modelserver.workflow.handler.ModelServerAwareSaveActionHandler;
import com.eclipsesource.glsp.example.modelserver.workflow.handler.ReconnectFlowHandler;
import com.eclipsesource.glsp.example.modelserver.workflow.handler.RerouteEdgeHandler;
import com.eclipsesource.glsp.example.modelserver.workflow.model.ModelServerAwareModelStateProvider;
import com.eclipsesource.glsp.example.modelserver.workflow.model.WorkflowModelServerModelFactory;
import com.eclipsesource.glsp.example.workflow.WorkflowGLSPModule;
import com.eclipsesource.glsp.server.actionhandler.OperationActionHandler;
import com.eclipsesource.glsp.server.actionhandler.SaveModelActionHandler;
import com.eclipsesource.glsp.server.actionhandler.UndoRedoActionHandler;
import com.eclipsesource.modelserver.edit.CommandCodec;
import com.eclipsesource.modelserver.edit.DefaultCommandCodec;

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
	protected Collection<Class<? extends ActionHandler>> bindActionHandlers() {
		Collection<Class<? extends ActionHandler>> defaultHandlers = super.bindActionHandlers();
		// remove bindings that we are about to overwrite
		defaultHandlers.remove(OperationActionHandler.class);
		defaultHandlers.remove(SaveModelActionHandler.class);
		defaultHandlers.remove(UndoRedoActionHandler.class);
		// add bindings in place of the above
		defaultHandlers.add(ModelServerAwareOperationActionHandler.class);
		defaultHandlers.add(ModelServerAwareSaveActionHandler.class);
		// TODO inject own undo/redo once incremental model server is ready
		return defaultHandlers;
	}

	@Override
	protected Collection<Class<? extends OperationHandler>> bindOperationHandlers() {
		return new ArrayList<Class<? extends OperationHandler>>() {
			{
				add(CreateAutomatedTaskHandler.class);
				add(CreateManualTaskHandler.class);
				add(CreateDecisionNodeHandler.class);
				add(CreateMergeNodeHandler.class);
				add(CreateFlowHandler.class);
				add(CreateWeightedFlowHandler.class);
				add(ReconnectFlowHandler.class);
				add(ChangeBoundsOperationHandler.class);
				add(DeleteOperationHandler.class);
				add(ApplyLabelEditOperationHandler.class);
				add(RerouteEdgeHandler.class);
			}
		};
	}

	@Override
	protected Collection<Class<? extends DiagramConfiguration>> bindDiagramConfigurations() {
		return Arrays.asList(WorfklowDiagramNotationConfiguration.class);
	}
	
	@Override
	protected void configure() {
		super.configure();
		bind(AdapterFactory.class).toInstance(new ComposedAdapterFactory());
		bind(CommandCodec.class).toInstance(new DefaultCommandCodec());
	}

}
