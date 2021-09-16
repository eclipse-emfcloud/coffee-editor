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

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emfcloud.coffee.Flow;
import org.eclipse.emfcloud.coffee.Node;
import org.eclipse.emfcloud.coffee.modelserver.CoffeeResource;
import org.eclipse.emfcloud.coffee.modelserver.commands.contributions.AddAutomatedTaskCommandContribution;
import org.eclipse.emfcloud.coffee.modelserver.commands.contributions.AddDecisionNodeCommandContribution;
import org.eclipse.emfcloud.coffee.modelserver.commands.contributions.AddFlowCommandContribution;
import org.eclipse.emfcloud.coffee.modelserver.commands.contributions.AddManualTaskCommandContribution;
import org.eclipse.emfcloud.coffee.modelserver.commands.contributions.AddMergeNodeCommandContribution;
import org.eclipse.emfcloud.coffee.modelserver.commands.contributions.AddWeightedFlowCommandContribution;
import org.eclipse.emfcloud.coffee.modelserver.commands.contributions.RemoveFlowCommandContribution;
import org.eclipse.emfcloud.coffee.modelserver.commands.contributions.RemoveNodeCommandContribution;
import org.eclipse.emfcloud.coffee.modelserver.commands.contributions.SetTaskNameCommandContribution;
import org.eclipse.emfcloud.modelserver.client.ModelServerClient;
import org.eclipse.emfcloud.modelserver.client.Response;
import org.eclipse.emfcloud.modelserver.command.CCommand;
import org.eclipse.emfcloud.modelserver.command.CCommandFactory;
import org.eclipse.emfcloud.modelserver.command.CCompoundCommand;
import org.eclipse.emfcloud.modelserver.glsp.notation.Edge;
import org.eclipse.emfcloud.modelserver.glsp.notation.commands.contribution.ChangeRoutingPointsCommandContribution;
import org.eclipse.emfcloud.modelserver.glsp.notation.integration.EMSNotationModelServerAccess;
import org.eclipse.emfcloud.modelserver.glsp.notation.model.NotationUtil;
import org.eclipse.glsp.graph.GPoint;
import org.eclipse.glsp.graph.util.GraphUtil;
import org.eclipse.glsp.server.protocol.GLSPServerException;
import org.eclipse.glsp.server.types.ElementAndRoutingPoints;

import com.google.common.base.Preconditions;

public class WorkflowModelServerAccess extends EMSNotationModelServerAccess {

	private static Logger LOGGER = Logger.getLogger(WorkflowModelServerAccess.class);

	public WorkflowModelServerAccess(String sourceURI, ModelServerClient modelServerClient) {
		super(sourceURI, modelServerClient, CoffeeResource.FILE_EXTENSION, NotationUtil.NOTATION_EXTENSION);
		Preconditions.checkNotNull(modelServerClient);
	}

	@Override
	public EObject getSemanticModel() {
		try {
			// fetch model in dedicated coffee format
			return modelServerClient.get(getSemanticURI(), CoffeeResource.FILE_EXTENSION).thenApply(res -> res.body())
					.get();
		} catch (InterruptedException | ExecutionException e) {
			LOGGER.error(e);
			throw new GLSPServerException("Error during model loading", e);
		}
	}

	protected String getSemanticUriFragment(final EObject element) {
		return EcoreUtil.getURI(element).fragment();
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

	public CompletableFuture<Response<Boolean>> addMergeNode(WorkflowModelState modelState, Optional<GPoint> position) {
		CCompoundCommand command = AddMergeNodeCommandContribution.create(position.orElse(GraphUtil.point(0, 0)));
		return this.edit(command);
	}

	public CompletableFuture<Response<Boolean>> addFlow(WorkflowModelState modelState, Node source, Node target) {
		String sourceUriFragment = getSemanticUriFragment(source);
		String targetUriFragment = getSemanticUriFragment(target);
		CCompoundCommand command = AddFlowCommandContribution.create(sourceUriFragment, targetUriFragment);
		return this.edit(command);
	}

	public CompletableFuture<Response<Boolean>> addWeightedFlow(WorkflowModelState modelState, Node source,
			Node target) {
		String sourceUriFragment = getSemanticUriFragment(source);
		String targetUriFragment = getSemanticUriFragment(target);
		CCompoundCommand command = AddWeightedFlowCommandContribution.create(sourceUriFragment, targetUriFragment);
		return this.edit(command);
	}

	public CompletableFuture<Response<Boolean>> removeFlow(WorkflowModelState modelState, Flow flow) {
		CCompoundCommand command = RemoveFlowCommandContribution.create(getSemanticUriFragment(flow));
		return this.edit(command);
	}

	public CompletableFuture<Response<Boolean>> removeNode(WorkflowModelState modelState, Node node) {
		CCompoundCommand command = RemoveNodeCommandContribution.create(getSemanticUriFragment(node));
		return this.edit(command);
	}

	public CompletableFuture<Response<Boolean>> setTaskName(final WorkflowModelState modelState,
			final Node nodeToRename, final String newName) {

		CCommand setCommand = SetTaskNameCommandContribution.create(getSemanticUriFragment(nodeToRename), newName);
		return this.edit(setCommand);
	}

	/*
	 * Change Routing Points
	 */
	public CompletableFuture<Response<Boolean>> changeRoutingPoints(
			final Map<Edge, ElementAndRoutingPoints> changeBendPointsMap) {
		CCompoundCommand compoundCommand = CCommandFactory.eINSTANCE.createCompoundCommand();
		compoundCommand.setType(ChangeRoutingPointsCommandContribution.TYPE);

		changeBendPointsMap.forEach((edge, elementAndRoutingPoints) -> {
			CCommand changeRoutingPointsCommand = ChangeRoutingPointsCommandContribution
					.create(edge.getSemanticElement().getUri(), elementAndRoutingPoints.getNewRoutingPoints());
			compoundCommand.getCommands().add(changeRoutingPointsCommand);
		});
		return this.edit(compoundCommand);
	}
}
