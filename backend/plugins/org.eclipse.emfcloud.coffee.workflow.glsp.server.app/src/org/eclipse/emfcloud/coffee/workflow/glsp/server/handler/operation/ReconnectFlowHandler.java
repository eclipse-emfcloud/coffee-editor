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

import org.eclipse.emfcloud.coffee.Flow;
import org.eclipse.emfcloud.coffee.Node;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.model.WorkflowModelIndex;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.model.WorkflowModelServerAccess;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.model.WorkflowModelState;
import org.eclipse.glsp.graph.GEdge;
import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.server.model.GModelState;
import org.eclipse.glsp.server.operations.BasicOperationHandler;
import org.eclipse.glsp.server.operations.ReconnectEdgeOperation;

public class ReconnectFlowHandler extends BasicOperationHandler<ReconnectEdgeOperation> {

	@Override
	public void executeOperation(ReconnectEdgeOperation operation, GModelState modelState) {
		WorkflowModelServerAccess modelAccess = WorkflowModelState.getModelAccess(modelState);
		WorkflowModelIndex modelIndex = WorkflowModelState.getModelState(modelState).getIndex();

		Optional<GModelElement> maybeEdge = modelState.getIndex().get(operation.getEdgeElementId());
		if (maybeEdge.isEmpty() && !(maybeEdge.get() instanceof GEdge)) {
			throw new IllegalArgumentException();
		}

		GEdge oldEdge = (GEdge) maybeEdge.get();
		Node oldSourceNode = modelIndex.getSemantic(oldEdge.getSourceId(), Node.class).get();
		Node oldTargetNode = modelIndex.getSemantic(oldEdge.getTargetId(), Node.class).get();

		Node newSourceNode = modelIndex.getSemantic(operation.getSourceElementId(), Node.class).get();
		Node newTargetNode = modelIndex.getSemantic(operation.getTargetElementId(), Node.class).get();

		Optional<Flow> maybeFlow = modelAccess.getFlow(oldSourceNode, oldTargetNode);
		if (maybeFlow.isEmpty()) {
			throw new IllegalArgumentException();
		}

		maybeFlow.get().setSource(newSourceNode);
		maybeFlow.get().setTarget(newTargetNode);
	}

}
