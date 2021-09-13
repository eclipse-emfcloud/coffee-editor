/*******************************************************************************
 * Copyright (c) 2019-2021 EclipseSource and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ******************************************************************************/
package org.eclipse.emfcloud.coffee.workflow.glsp.server.handler.operation;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;

import org.eclipse.emfcloud.coffee.workflow.glsp.server.model.WorkflowModelServerAccess;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.model.WorkflowModelState;
import org.eclipse.emfcloud.modelserver.client.Response;
import org.eclipse.emfcloud.modelserver.glsp.operations.handlers.EMSBasicCreateOperationHandler;
import org.eclipse.glsp.graph.GPoint;
import org.eclipse.glsp.server.operations.CreateNodeOperation;
import org.eclipse.glsp.server.protocol.GLSPServerException;

public abstract class AbstractCreateNodeHandler
		extends EMSBasicCreateOperationHandler<CreateNodeOperation, WorkflowModelState, WorkflowModelServerAccess> {

	public AbstractCreateNodeHandler(String type) {
		super(type);
	}

	@Override
	public void executeOperation(CreateNodeOperation operation, WorkflowModelState modelState,
			WorkflowModelServerAccess modelAccess) {
		getNodeCreator(modelAccess).apply(modelState, operation.getLocation()).thenAccept(response -> {
			if (!response.body()) {
				throw new GLSPServerException(String.format("Could not execute create operation for a new %s.", getLabel()));
			}
		});
	}

	protected abstract BiFunction<WorkflowModelState, Optional<GPoint>, CompletableFuture<Response<Boolean>>> getNodeCreator(
			WorkflowModelServerAccess modelAccess);
}
