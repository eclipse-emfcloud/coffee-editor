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
package org.eclipse.emfcloud.coffee.workflow.glsp.server.handler.operation;

import java.util.Optional;

import org.eclipse.emfcloud.coffee.workflow.glsp.server.model.WorkflowModelServerAccess;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.model.WorkflowModelState;
import org.eclipse.emfcloud.modelserver.glsp.operations.handlers.EMSBasicOperationHandler;
import org.eclipse.glsp.graph.GEdge;
import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.server.operations.ReconnectEdgeOperation;

public class ReconnectFlowHandler
		extends EMSBasicOperationHandler<ReconnectEdgeOperation, WorkflowModelState, WorkflowModelServerAccess> {

	@Override
	public void executeOperation(final ReconnectEdgeOperation operation, final WorkflowModelState modelState,
			final WorkflowModelServerAccess modelServerAccess) {

		Optional<GModelElement> maybeEdge = modelState.getIndex().get(operation.getEdgeElementId());
		if (maybeEdge.isEmpty() && !(maybeEdge.get() instanceof GEdge)) {
			throw new IllegalArgumentException();
		}

		// TODO
//		GEdge oldEdge = (GEdge) maybeEdge.get();
//		Node oldSourceNode = modelState.getIndex().getSemantic(oldEdge.getSourceId(), Node.class).get();
//		Node oldTargetNode = modelState.getIndex().getSemantic(oldEdge.getTargetId(), Node.class).get();
//
//		Node newSourceNode = modelState.getIndex().getSemantic(operation.getSourceElementId(), Node.class).get();
//		Node newTargetNode = modelState.getIndex().getSemantic(operation.getTargetElementId(), Node.class).get();

//		Optional<Flow> maybeFlow = modelServerAccess.getFlow(oldSourceNode, oldTargetNode);
//		if (maybeFlow.isEmpty()) {
//			throw new IllegalArgumentException();
//		}
//
//		maybeFlow.get().setSource(newSourceNode);
//		maybeFlow.get().setTarget(newTargetNode);
	}

}
