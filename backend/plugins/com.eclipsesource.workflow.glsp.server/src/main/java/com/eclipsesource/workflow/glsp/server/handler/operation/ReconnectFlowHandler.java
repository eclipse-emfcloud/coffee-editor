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
import org.eclipse.glsp.api.action.Action;
import org.eclipse.glsp.api.action.kind.AbstractOperationAction;
import org.eclipse.glsp.api.action.kind.ReconnectConnectionOperationAction;
import org.eclipse.glsp.api.handler.OperationHandler;
import org.eclipse.glsp.api.model.GraphicalModelState;
import org.eclipse.glsp.graph.GEdge;
import org.eclipse.glsp.graph.GModelElement;

import com.eclipsesource.workflow.glsp.server.model.WorkflowModelServerAccess;
import com.eclipsesource.workflow.glsp.server.model.WorkflowModelState;

public class ReconnectFlowHandler implements OperationHandler {

	@Override
	public Class<? extends Action> handlesActionType() {
		return ReconnectConnectionOperationAction.class;
	}

	@Override
	public String getLabel(AbstractOperationAction action) {
		return "Reconnect flow";
	}

	@Override
	public void execute(AbstractOperationAction action, GraphicalModelState modelState) {
		ReconnectConnectionOperationAction reconnectAction = (ReconnectConnectionOperationAction) action;
		WorkflowModelServerAccess modelAccess = WorkflowModelState.getModelAccess(modelState);

		Optional<GModelElement> maybeEdge = modelState.getIndex().get(reconnectAction.getConnectionElementId());
		if (maybeEdge.isEmpty() && !(maybeEdge.get() instanceof GEdge)) {
			throw new IllegalArgumentException();
		}

		GEdge oldEdge = (GEdge) maybeEdge.get();
		Node oldSourceNode = modelAccess.getNodeById(oldEdge.getSourceId());
		Node oldTargetNode = modelAccess.getNodeById(oldEdge.getTargetId());

		Node newSourceNode = modelAccess.getNodeById(reconnectAction.getSourceElementId());
		Node newTargetNode = modelAccess.getNodeById(reconnectAction.getTargetElementId());

		Optional<Flow> maybeFlow = modelAccess.getFlow(oldSourceNode, oldTargetNode);
		if (maybeFlow.isEmpty()) {
			throw new IllegalArgumentException();
		}

		maybeFlow.get().setSource(newSourceNode);
		maybeFlow.get().setTarget(newTargetNode);
	}

}
