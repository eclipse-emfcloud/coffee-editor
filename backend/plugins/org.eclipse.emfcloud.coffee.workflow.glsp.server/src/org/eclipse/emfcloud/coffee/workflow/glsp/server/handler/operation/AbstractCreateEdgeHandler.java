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

import java.util.concurrent.CompletableFuture;

import org.eclipse.emfcloud.coffee.Node;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.model.WorkflowModelIndex;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.model.WorkflowModelServerAccess;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.model.WorkflowModelState;
import org.eclipse.emfcloud.modelserver.client.Response;
import org.eclipse.emfcloud.modelserver.glsp.operations.handlers.EMSBasicCreateOperationHandler;
import org.eclipse.glsp.server.operations.CreateEdgeOperation;
import org.eclipse.glsp.server.protocol.GLSPServerException;

public abstract class AbstractCreateEdgeHandler
		extends EMSBasicCreateOperationHandler<CreateEdgeOperation, WorkflowModelState, WorkflowModelServerAccess> {


	public AbstractCreateEdgeHandler(String type) {
		super(type);
	}

	@Override
	public void executeOperation(CreateEdgeOperation operation, WorkflowModelState modelState,
			WorkflowModelServerAccess modelAccess) {
		WorkflowModelIndex modelIndex = modelState.getIndex();

		Node source = modelIndex.getSemantic(operation.getSourceElementId(), Node.class).orElseThrow(
				() -> new GLSPServerException(String.format("No semantic Node found for source element with id %s.",
						operation.getSourceElementId())));
		Node target = modelIndex.getSemantic(operation.getTargetElementId(), Node.class).orElseThrow(
				() -> new GLSPServerException(String.format("No semantic Node found for target element with id %s.",
						operation.getTargetElementId())));

		addFlow(modelAccess, modelState, source, target).thenAccept(response -> {
			if (!response.body()) {
				throw new GLSPServerException(String.format("Could not execute create operation for a new %s.", getLabel()));
			}
		});
	}

	protected abstract CompletableFuture<Response<Boolean>> addFlow(WorkflowModelServerAccess modelAccess,
			WorkflowModelState modelState, Node source, Node target);
}
