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
package com.eclipsesource.workflow.glsp.server.handler;

import java.util.List;

import org.eclipse.glsp.api.action.Action;
import org.eclipse.glsp.api.handler.OperationHandler;
import org.eclipse.glsp.api.model.GraphicalModelState;
import org.eclipse.glsp.api.operation.Operation;
import org.eclipse.glsp.api.registry.OperationHandlerRegistry;
import org.eclipse.glsp.server.actionhandler.OperationActionHandler;

import com.eclipsesource.workflow.glsp.server.handler.operation.ModelserverAwareOperationHandler;
import com.eclipsesource.workflow.glsp.server.model.WorkflowModelServerAccess;
import com.eclipsesource.workflow.glsp.server.model.WorkflowModelState;
import com.google.inject.Inject;

public class WorkflowOperationActionHandler extends OperationActionHandler {
	@Inject
	protected OperationHandlerRegistry operationHandlerRegistry;

	@Override
	protected List<Action> executeHandler(final Operation operation, final OperationHandler handler,
			final GraphicalModelState modelState) {
		handler.execute(operation, modelState);
		if (!(handler instanceof ModelserverAwareOperationHandler)) {
			// if the handler is not model server aware, we simply update the whole model
			// this can be removed as soon as we ensure that all handlers that make updates,
			// update accordingly
			WorkflowModelServerAccess modelAccess = WorkflowModelState.getModelAccess(modelState);
			modelAccess.update();
		}
		return none();
	}

}
