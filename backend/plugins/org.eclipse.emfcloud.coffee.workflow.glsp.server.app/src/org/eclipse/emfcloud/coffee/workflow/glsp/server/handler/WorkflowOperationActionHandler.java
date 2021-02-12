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
package org.eclipse.emfcloud.coffee.workflow.glsp.server.handler;

import java.util.List;

import org.eclipse.emfcloud.coffee.workflow.glsp.server.handler.operation.ModelserverAwareOperationHandler;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.model.WorkflowModelServerAccess;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.model.WorkflowModelState;
import org.eclipse.glsp.server.actions.Action;
import org.eclipse.glsp.server.model.GModelState;
import org.eclipse.glsp.server.operations.Operation;
import org.eclipse.glsp.server.operations.OperationActionHandler;
import org.eclipse.glsp.server.operations.OperationHandler;
import org.eclipse.glsp.server.operations.OperationHandlerRegistry;

import com.google.inject.Inject;

public class WorkflowOperationActionHandler extends OperationActionHandler {
	@Inject
	protected OperationHandlerRegistry operationHandlerRegistry;

	@Override
	protected List<Action> executeHandler(final Operation operation, final OperationHandler handler,
			final GModelState modelState) {
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
