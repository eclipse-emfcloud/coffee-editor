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
package org.eclipse.emfcloud.coffee.workflow.glsp.server.model;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.eclipse.emfcloud.coffee.Flow;
import org.eclipse.emfcloud.coffee.Node;
import org.eclipse.emfcloud.coffee.Workflow;
import org.eclipse.emfcloud.coffee.modelserver.commands.contributions.AddAutomatedTaskCommandContribution;
import org.eclipse.emfcloud.coffee.modelserver.commands.contributions.AddDecisionNodeCommandContribution;
import org.eclipse.emfcloud.coffee.modelserver.commands.contributions.AddManualTaskCommandContribution;
import org.eclipse.emfcloud.coffee.modelserver.commands.contributions.AddMergeNodeCommandContribution;
import org.eclipse.emfcloud.modelserver.client.ModelServerClient;
import org.eclipse.emfcloud.modelserver.client.Response;
import org.eclipse.emfcloud.modelserver.command.CCompoundCommand;
import org.eclipse.emfcloud.modelserver.glsp.notation.integration.EMSNotationModelServerAccess;
import org.eclipse.glsp.graph.GPoint;
import org.eclipse.glsp.graph.util.GraphUtil;

import com.google.common.base.Preconditions;

public class WorkflowModelServerAccess extends EMSNotationModelServerAccess {
	public WorkflowModelServerAccess(String sourceURI, ModelServerClient modelServerClient) {
		super(sourceURI, modelServerClient, "coffee", "coffeenotation");
		Preconditions.checkNotNull(modelServerClient);
	}

	// TODO move functionality to more suitable position?
	public Optional<Flow> getFlow(Node source, Node target) {
		return Workflow.class.cast(getSemanticModel()).getFlows().stream()
				.filter(flow -> source.equals(flow.getSource()) && target.equals(flow.getTarget())).findFirst();
	}

	public CompletableFuture<Response<Boolean>> addManualTask(WorkflowModelState modelState,
			Optional<GPoint> position) {
		CCompoundCommand command = AddManualTaskCommandContribution.create(position.orElse(GraphUtil.point(0, 0)));
		return this.edit(command);
	}

	public CompletableFuture<Response<Boolean>> addAutomatedTask(WorkflowModelState modelState,
			Optional<GPoint> position) {
		CCompoundCommand command = AddAutomatedTaskCommandContribution.create(position.orElse(GraphUtil.point(0, 0)));
		return this.edit(command);
	}
	
	public CompletableFuture<Response<Boolean>> addDecisionNode(WorkflowModelState modelState,
			Optional<GPoint> position) {
		CCompoundCommand command = AddDecisionNodeCommandContribution.create(position.orElse(GraphUtil.point(0, 0)));
		return this.edit(command);
	}
	
	public CompletableFuture<Response<Boolean>> addMergeNode(WorkflowModelState modelState,
			Optional<GPoint> position) {
		CCompoundCommand command = AddMergeNodeCommandContribution.create(position.orElse(GraphUtil.point(0, 0)));
		return this.edit(command);
	}
}
