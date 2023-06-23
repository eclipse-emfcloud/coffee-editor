/*******************************************************************************
 * Copyright (c) 2019-2022 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ******************************************************************************/
package org.eclipse.emfcloud.coffee.workflow.glsp.server;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emfcloud.coffee.Flow;
import org.eclipse.emfcloud.coffee.Node;
import org.eclipse.emfcloud.coffee.modelserver.commands.contributions.AddAutomatedTaskCommandContribution;
import org.eclipse.emfcloud.coffee.modelserver.commands.contributions.AddDecisionNodeCommandContribution;
import org.eclipse.emfcloud.coffee.modelserver.commands.contributions.AddFlowCommandContribution;
import org.eclipse.emfcloud.coffee.modelserver.commands.contributions.AddManualTaskCommandContribution;
import org.eclipse.emfcloud.coffee.modelserver.commands.contributions.AddMergeNodeCommandContribution;
import org.eclipse.emfcloud.coffee.modelserver.commands.contributions.AddWeightedFlowCommandContribution;
import org.eclipse.emfcloud.coffee.modelserver.commands.contributions.RemoveFlowCommandContribution;
import org.eclipse.emfcloud.coffee.modelserver.commands.contributions.RemoveNodeCommandContribution;
import org.eclipse.emfcloud.coffee.modelserver.commands.contributions.SetFlowSourceCommandContribution;
import org.eclipse.emfcloud.coffee.modelserver.commands.contributions.SetFlowTargetCommandContribution;
import org.eclipse.emfcloud.coffee.modelserver.commands.util.SemanticCommandUtil;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.validation.WorkflowValidationResultChangeListener;
import org.eclipse.emfcloud.modelserver.client.Response;
import org.eclipse.emfcloud.modelserver.command.CCommand;
import org.eclipse.emfcloud.modelserver.command.CCompoundCommand;
import org.eclipse.emfcloud.modelserver.emf.common.DefaultModelURIConverter;
import org.eclipse.emfcloud.modelserver.emf.common.EMFFacetConstraints;
import org.eclipse.emfcloud.modelserver.glsp.notation.integration.EMSNotationModelServerAccess;
import org.eclipse.emfcloud.validation.framework.ValidationFilter;
import org.eclipse.emfcloud.validation.framework.ValidationFramework;
import org.eclipse.emfcloud.validation.framework.ValidationResult;
import org.eclipse.glsp.graph.GPoint;
import org.eclipse.glsp.graph.util.GraphUtil;
import org.eclipse.glsp.server.actions.ActionDispatcher;
import org.eclipse.glsp.server.types.GLSPServerException;
import org.eclipse.glsp.server.utils.ClientOptionsUtil;

public class WorkflowModelServerAccess extends EMSNotationModelServerAccess {

   private static Logger LOGGER = LogManager.getLogger(WorkflowModelServerAccess.class);

   private ValidationFramework validationFramework;

   @Override
   public void setClientOptions(final Map<String, String> clientOptions) {
      this.clientOptions = clientOptions;
      String sourceURI = ClientOptionsUtil.getSourceUri(clientOptions)
         .orElseThrow(() -> new GLSPServerException("No source URI given to load model!"));
      // ensure URIs (windows, unix) are parsed correctly
      URI absoluteFilePath = DefaultModelURIConverter.parseURI(sourceURI);
      // remove file scheme if any
      URI uri = URI.createURI(absoluteFilePath.toString().replace("file:/", ""));
      this.baseSourceUri = uri.trimFileExtension();
   }

   public void createValidationFramework(final String clientId, final ActionDispatcher actionDispatcher) {
      WorkflowValidationResultChangeListener changeListener = new WorkflowValidationResultChangeListener(
         clientId, actionDispatcher);
      try {
         this.validationFramework = new ValidationFramework(this.getSemanticURI(), changeListener);
      } catch (MalformedURLException e) {
         LOGGER.error("Creation of ValidationFramework failed!");
         e.printStackTrace();
      }
   }

   public CompletableFuture<Response<String>> addManualTask(final Optional<GPoint> position) {
      CCompoundCommand command = AddManualTaskCommandContribution.create(position.orElse(GraphUtil.point(0, 0)));
      return this.edit(command);
   }

   public CompletableFuture<Response<String>> addAutomatedTask(final Optional<GPoint> position) {
      CCompoundCommand command = AddAutomatedTaskCommandContribution.create(position.orElse(GraphUtil.point(0, 0)));
      return this.edit(command);
   }

   public CompletableFuture<Response<String>> addDecisionNode(final Optional<GPoint> position) {
      CCompoundCommand command = AddDecisionNodeCommandContribution.create(position.orElse(GraphUtil.point(0, 0)));
      return this.edit(command);
   }

   public CompletableFuture<Response<String>> addMergeNode(final Optional<GPoint> position) {
      CCompoundCommand command = AddMergeNodeCommandContribution.create(position.orElse(GraphUtil.point(0, 0)));
      return this.edit(command);
   }

   public CompletableFuture<Response<String>> addFlow(final String sourceId, final String targetId) {
      CCompoundCommand command = AddFlowCommandContribution.create(sourceId, targetId);
      return this.edit(command);
   }

   public CompletableFuture<Response<String>> addWeightedFlow(final String sourceId, final String targetId) {
      CCompoundCommand command = AddWeightedFlowCommandContribution.create(sourceId, targetId);
      return this.edit(command);
   }

   public CompletableFuture<Response<String>> removeFlow(final Flow flow) {
      CCompoundCommand command = RemoveFlowCommandContribution.create(idGenerator.getOrCreateId(flow));
      return this.edit(command);
   }

   public CompletableFuture<Response<String>> removeNode(final Node node) {
      CCompoundCommand command = RemoveNodeCommandContribution.create(idGenerator.getOrCreateId(node));
      return this.edit(command);
   }

   protected String getOwnerRefUri(final EObject element) {
      String absoluteFilePath = baseSourceUri.appendFileExtension(this.semanticFileExtension).toString();
      return DefaultModelURIConverter.parseURI(absoluteFilePath).appendFragment(idGenerator.getOrCreateId(element))
         .toString();
   }

   public CompletableFuture<Response<String>> setTaskName(final Node nodeToRename, final String newName) {
      CCommand setCommand = SemanticCommandUtil.createSetTaskNameCommand(nodeToRename, getOwnerRefUri(nodeToRename),
         newName);
      return this.edit(setCommand);
   }

   public CompletableFuture<Response<String>> setTaskDuration(final Node node, final int newDuration) {
      CCommand setCommand = SemanticCommandUtil.createSetTaskDurationCommand(node, getOwnerRefUri(node),
         newDuration);
      return this.edit(setCommand);
   }

   public CompletableFuture<Response<String>> reconnectFlowSource(final Flow flow, final Node newSource) {
      CCommand command = SetFlowSourceCommandContribution.create(idGenerator.getOrCreateId(flow),
         idGenerator.getOrCreateId(newSource));
      return this.edit(command);
   }

   public CompletableFuture<Response<String>> reconnectFlowTarget(final Flow flow, final Node newTarget) {
      CCommand command = SetFlowTargetCommandContribution.create(idGenerator.getOrCreateId(flow),
         idGenerator.getOrCreateId(newTarget));
      return this.edit(command);
   }

   @Override
   public CompletableFuture<Response<String>> validate() {
      return modelServerClient.validate(getSemanticURI());
   }

   public CompletableFuture<Void> validateViaFramework() throws IOException, InterruptedException, ExecutionException {
      return this.validationFramework.validate();
   }

   public List<ValidationResult> getValidationResult() throws IOException, InterruptedException, ExecutionException {
      return this.validationFramework.getRecentValidationResult();
   }

   public void initConstraintList() {
      this.validationFramework.getConstraintList();
   }

   public EMFFacetConstraints getConstraintList(final String elementID, final String featureID) {
      Map<String, EMFFacetConstraints> featureMap = this.validationFramework.getInputValidationMap().get(elementID);
      if (featureMap != null) {
         return featureMap.get(featureID);
      }
      return null;
   }

   public void subscribeToValidation() {
      this.validationFramework.subscribeToValidation();
   }

   public void addValidationFilter(final List<ValidationFilter> contraintValues)
      throws IOException, InterruptedException, ExecutionException {
      this.validationFramework.addValidationFilter(contraintValues);
   }

   public void removeValidationFilter(final List<ValidationFilter> contraintValues)
      throws IOException, InterruptedException, ExecutionException {
      this.validationFramework.removeValidationFilter(contraintValues);
   }

   public void toggleValidationFilter(final ValidationFilter contraintValue)
      throws IOException, InterruptedException, ExecutionException {
      this.validationFramework.toggleValidationFilter(contraintValue);
   }
}
