/********************************************************************************
 * Copyright (c) 2021 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package org.eclipse.emfcloud.coffee.workflow.glsp.server.handler.operation;

import java.util.Optional;

import org.eclipse.emfcloud.coffee.workflow.glsp.server.model.WorkflowModelServerAccess;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.model.WorkflowModelState;
import org.eclipse.emfcloud.modelserver.glsp.operations.handlers.EMSBasicOperationHandler;
import org.eclipse.emfcloud.modelserver.glsp.operations.handlers.EMSOperationHandler;
import org.eclipse.glsp.server.operations.CompoundOperation;
import org.eclipse.glsp.server.operations.Operation;
import org.eclipse.glsp.server.operations.OperationActionHandler;
import org.eclipse.glsp.server.operations.OperationHandler;
import org.eclipse.glsp.server.operations.OperationHandlerRegistry;

import com.google.inject.Inject;

public class WorkflowCompoundOperationHandler
		extends EMSBasicOperationHandler<CompoundOperation, WorkflowModelState, WorkflowModelServerAccess> {
	@Inject
	protected OperationHandlerRegistry operationHandlerRegistry;

	@Override
	public void executeOperation(final CompoundOperation operation, final WorkflowModelState modelState,
			final WorkflowModelServerAccess modelServerAccess) {
		operation.getOperationList()
				.forEach(nestedOperation -> executeNestedOperation(nestedOperation, modelState, modelServerAccess));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void executeNestedOperation(final Operation operation, final WorkflowModelState modelState,
			final WorkflowModelServerAccess modelServerAccess) {
		Optional<? extends OperationHandler> operationHandler = OperationActionHandler.getOperationHandler(operation,
				operationHandlerRegistry);
		if (operationHandler.isPresent()) {
			if (operationHandler.get() instanceof EMSOperationHandler) {
				((EMSOperationHandler) operationHandler.get()).executeOperation(operation, modelState,
						modelServerAccess);
			} else {
				operationHandler.get().execute(operation, modelState);
			}
		}
	}

}
