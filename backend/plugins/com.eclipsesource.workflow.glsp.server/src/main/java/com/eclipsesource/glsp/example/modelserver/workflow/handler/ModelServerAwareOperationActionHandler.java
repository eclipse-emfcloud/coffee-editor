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
package com.eclipsesource.glsp.example.modelserver.workflow.handler;

import java.util.Optional;

import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.action.kind.AbstractOperationAction;
import com.eclipsesource.glsp.api.handler.OperationHandler;
import com.eclipsesource.glsp.api.model.GraphicalModelState;
import com.eclipsesource.glsp.api.provider.OperationHandlerProvider;
import com.eclipsesource.glsp.example.modelserver.workflow.model.ModelServerAwareModelState;
import com.eclipsesource.glsp.example.modelserver.workflow.model.WorkflowModelServerAccess;
import com.eclipsesource.glsp.server.actionhandler.OperationActionHandler;
import com.google.inject.Inject;

public class ModelServerAwareOperationActionHandler extends OperationActionHandler {

	@Inject
	private OperationHandlerProvider operationHandlerProvider;

	@Override
	public Optional<Action> doHandle(AbstractOperationAction action, GraphicalModelState modelState) {
		if (operationHandlerProvider.isHandled(action)) {
			OperationHandler handler = operationHandlerProvider.getHandler(action).get();
			handler.execute(action, modelState);
			if(!(handler instanceof ModelStateAwareOperationHandler)) {
				// if the handler is not model state aware, we simply update the whole model
				// this can be removed as soon as we ensure that all handlers that make updates, update accordingly
				WorkflowModelServerAccess modelAccess = ModelServerAwareModelState.getModelAccess(modelState);
				modelAccess.update();				
			}
		}
		return Optional.empty();
	}
}
