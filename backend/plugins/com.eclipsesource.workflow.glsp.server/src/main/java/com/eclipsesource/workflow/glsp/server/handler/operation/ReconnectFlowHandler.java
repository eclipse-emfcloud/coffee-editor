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
package com.eclipsesource.workflow.glsp.server.handler.operation;

import java.util.Optional;

import org.eclipse.emfcloud.modelserver.coffee.model.coffee.Flow;
import org.eclipse.emfcloud.modelserver.coffee.model.coffee.Node;
import org.eclipse.glsp.api.model.GraphicalModelState;
import org.eclipse.glsp.api.operation.kind.ReconnectEdgeOperation;
import org.eclipse.glsp.graph.GEdge;
import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.server.operationhandler.BasicOperationHandler;

import com.eclipsesource.workflow.glsp.server.model.WorkflowModelServerAccess;
import com.eclipsesource.workflow.glsp.server.model.WorkflowModelState;

public class ReconnectFlowHandler extends BasicOperationHandler<ReconnectEdgeOperation> {

	@Override
	public void executeOperation(ReconnectEdgeOperation operation, GraphicalModelState modelState) {
		WorkflowModelServerAccess modelAccess = WorkflowModelState.getModelAccess(modelState);

		Optional<GModelElement> maybeEdge = modelState.getIndex().get(operation.getConnectionElementId());
		if (maybeEdge.isEmpty() && !(maybeEdge.get() instanceof GEdge)) {
			throw new IllegalArgumentException();
		}

		GEdge oldEdge = (GEdge) maybeEdge.get();
		Node oldSourceNode = modelAccess.getNodeById(oldEdge.getSourceId());
		Node oldTargetNode = modelAccess.getNodeById(oldEdge.getTargetId());

		Node newSourceNode = modelAccess.getNodeById(operation.getSourceElementId());
		Node newTargetNode = modelAccess.getNodeById(operation.getTargetElementId());

		Optional<Flow> maybeFlow = modelAccess.getFlow(oldSourceNode, oldTargetNode);
		if (maybeFlow.isEmpty()) {
			throw new IllegalArgumentException();
		}

		maybeFlow.get().setSource(newSourceNode);
		maybeFlow.get().setTarget(newTargetNode);
	}

}
