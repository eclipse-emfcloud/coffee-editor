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

import static org.eclipse.glsp.server.protocol.GLSPServerException.getOrThrow;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emfcloud.coffee.Flow;
import org.eclipse.emfcloud.coffee.Node;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.model.WorkflowModelServerAccess;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.model.WorkflowModelState;
import org.eclipse.emfcloud.modelserver.glsp.operations.handlers.EMSBasicOperationHandler;
import org.eclipse.glsp.server.operations.ReconnectEdgeOperation;

public class WorkflowReconnectFlowHandler
		extends EMSBasicOperationHandler<ReconnectEdgeOperation, WorkflowModelState, WorkflowModelServerAccess> {

	@Override
	public void executeOperation(final ReconnectEdgeOperation operation, final WorkflowModelState modelState,
			final WorkflowModelServerAccess modelServerAccess) {

		if (operation.getEdgeElementId() == null || operation.getSourceElementId() == null
				|| operation.getTargetElementId() == null) {
			throw new IllegalArgumentException("Incomplete reconnect flow action");
		}

		String modelId = EcoreUtil.getURI(modelState.getSemanticModel()).fragment();
		if (operation.getSourceElementId().equals(modelId) || operation.getTargetElementId().equals(modelId)) {
			// client tool failure, do nothing
			return;
		}

		Flow flow = getOrThrow(modelState.getIndex().getSemantic(operation.getEdgeElementId()),
				Flow.class, "Could not find Flow for id '" + operation.getEdgeElementId()
						+ "', no reconnecting operation executed.");

		if (!operation.getSourceElementId().equals(EcoreUtil.getURI(flow.getSource()).fragment())) {
			Node newSource = getOrThrow(modelState.getIndex().getSemantic(operation.getSourceElementId()),
					Node.class, "Could not find Node for id '" + operation.getSourceElementId()
							+ "', no reconnecting operation executed.");
			modelServerAccess.reconnectFlowSource(flow, newSource);

		} else if (!operation.getTargetElementId().equals(EcoreUtil.getURI(flow.getTarget()).fragment())) {
			Node newTarget = getOrThrow(modelState.getIndex().getSemantic(operation.getTargetElementId()),
					Node.class,
					"Could not find Node for id '" + operation.getTargetElementId()
							+ "', no reconnecting operation executed.");
			modelServerAccess.reconnectFlowTarget(flow, newTarget);
		}
	}

}
